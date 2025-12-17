package com.example.kata.adapters.in;
import com.example.kata.adapters.in.dto.CreerCompteCourantDto;
import com.example.kata.adapters.in.dto.CreerLivretDto;
import com.example.kata.adapters.in.dto.OperationDto;
import com.example.kata.adapters.in.dto.ReleveDto;
import com.example.kata.domain.model.Operation;
import com.example.kata.domain.model.ReleveCompte;
import com.example.kata.domain.model.enums.TypeCompteEnum;
import com.example.kata.ports.in.CompteLecture;
import com.example.kata.ports.in.CompteTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CompteControllerTest {

    @Mock
    private CompteTransaction compteTransaction;

    @Mock
    private CompteLecture compteLecture;

    @InjectMocks
    private CompteController compteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testRetirerCasPassant() {
        String numeroCompte = "123";
        OperationDto dto = new OperationDto();
        dto.setMontant(50);

        ResponseEntity<String> response = compteController.retirer(numeroCompte, dto);

        verify(compteTransaction, times(1)).retirer(numeroCompte, 50);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("retrait bien effectué"));

    }

    @Test
    void testRetirerCasNonPassant() {
        String numeroCompte = "123";
        OperationDto dto = new OperationDto();
        dto.setMontant(-50);

        doThrow(new IllegalArgumentException("le montant à retirer doit être positif"))
                .when(compteTransaction).retirer(numeroCompte, -50);

        ResponseEntity<String> response = compteController.retirer(numeroCompte, dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("le montant à retirer doit être positif"));
    }

    @Test
    void testGetReleveCasPassant() {
        String numeroCompte = "123";
        ReleveCompte releveCompte = new ReleveCompte(
                TypeCompteEnum.COMPTE_COURANT,
                1000.0,
                List.of(Operation.depot(100), Operation.retrait(50))
        );

        when(compteLecture.getReleve(numeroCompte)).thenReturn(releveCompte);

        ReleveDto releveDto = compteController.getReleve(numeroCompte);

        assertNotNull(releveDto);
        assertEquals("COMPTE_COURANT", releveDto.getTypeCompte());
        assertEquals(1000.0, releveDto.getSolde());
        assertEquals(2, releveDto.getOperations().size());

        verify(compteLecture, times(1)).getReleve(numeroCompte);
    }

    @Test
    void testCreerCompteCourantCasPassant() {
        CreerCompteCourantDto dto = new CreerCompteCourantDto();

        ResponseEntity<String> response = compteController.creerCompteCourant(dto);

        verify(compteTransaction, times(1)).creerCompteCourant(dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("compte courant bien crée"));
    }

    @Test
    void testCreerCompteCourantCasNonPassant() {
        CreerCompteCourantDto dto = new CreerCompteCourantDto();
        dto.setNumeroCompte("456");

        doThrow(new IllegalArgumentException("Numéro de compte déjà existant"))
                .when(compteTransaction).creerCompteCourant(dto);

        ResponseEntity<String> response = compteController.creerCompteCourant(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Numéro de compte déjà existant"));
    }


    @Test
    void testCreerLivretCasPassant() {
        CreerLivretDto dto = new CreerLivretDto();

        ResponseEntity<String> response = compteController.creerLivret(dto);

        verify(compteTransaction, times(1)).creerLivret(dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("livret bien crée"));
    }

    @Test
    void testCreerLivretCasNonPassant() {
        CreerLivretDto dto = new CreerLivretDto();
        dto.setNumeroCompte("789");

        doThrow(new IllegalStateException("Plafond déjà atteint"))
                .when(compteTransaction).creerLivret(dto);

        ResponseEntity<String> response = compteController.creerLivret(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Plafond déjà atteint"));
    }
}
