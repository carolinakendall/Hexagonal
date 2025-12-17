package com.example.kata.adapters.in;

import com.example.kata.adapters.in.dto.CreerCompteCourantDto;
import com.example.kata.adapters.in.dto.CreerLivretDto;
import com.example.kata.adapters.in.dto.OperationDto;
import com.example.kata.adapters.in.dto.ReleveDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompteControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreerCompteCourantCasPassant() {
        CreerCompteCourantDto dto = new CreerCompteCourantDto();
        dto.setNumeroCompte("111");
        dto.setDecouvertAutorise(500);
        dto.setIndicateurDecouvertAutorise(true);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/comptes/courant", dto, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("compte courant bien crée");
    }

    @Test
    void testCreerLivretCasPassant() {
        CreerLivretDto dto = new CreerLivretDto();
        dto.setNumeroCompte("222");
        dto.setPlafond(3000);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/comptes/livret", dto, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("livret bien crée");
    }

    @Test
    void testDepotCasPassant() {
        CreerCompteCourantDto courantDto = new CreerCompteCourantDto();
        courantDto.setNumeroCompte("123");
        courantDto.setDecouvertAutorise(500);
        courantDto.setIndicateurDecouvertAutorise(true);
        restTemplate.postForEntity("/comptes/courant", courantDto, String.class);

        OperationDto depot = new OperationDto();
        depot.setMontant(200);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/comptes/123/depot", depot, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("dépôt bien effectué");
    }

    @Test
    void testDepotNegatifCasNonPassant() {
        CreerCompteCourantDto courantDto = new CreerCompteCourantDto();
        courantDto.setNumeroCompte("124");
        courantDto.setDecouvertAutorise(500);
        courantDto.setIndicateurDecouvertAutorise(true);
        restTemplate.postForEntity("/comptes/courant", courantDto, String.class);

        OperationDto depotNegatif = new OperationDto();
        depotNegatif.setMontant(-100);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/comptes/124/depot", depotNegatif, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("le montant à déposer doit être positif");
    }

    @Test
    void testRetraitPositifCasPassant() {
        CreerCompteCourantDto courantDto = new CreerCompteCourantDto();
        courantDto.setNumeroCompte("125");
        courantDto.setDecouvertAutorise(500);
        courantDto.setIndicateurDecouvertAutorise(true);
        restTemplate.postForEntity("/comptes/courant", courantDto, String.class);

        OperationDto depot = new OperationDto();
        depot.setMontant(300);
        restTemplate.postForEntity("/comptes/125/depot", depot, String.class);

        OperationDto retrait = new OperationDto();
        retrait.setMontant(100);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/comptes/125/retrait", retrait, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("retrait bien effectué");
    }

    @Test
    void testRetraitNegatifCasNonPassant() {
        CreerCompteCourantDto courantDto = new CreerCompteCourantDto();
        courantDto.setNumeroCompte("126");
        courantDto.setDecouvertAutorise(500);
        courantDto.setIndicateurDecouvertAutorise(true);
        restTemplate.postForEntity("/comptes/courant", courantDto, String.class);

        OperationDto retraitNegatif = new OperationDto();
        retraitNegatif.setMontant(-50);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/comptes/126/retrait", retraitNegatif, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("le montant à retirer doit être positif");
    }

    @Test
    void testGetReleveCompteCasPassant() {
        CreerCompteCourantDto courantDto = new CreerCompteCourantDto();
        courantDto.setNumeroCompte("127");
        courantDto.setDecouvertAutorise(500);
        courantDto.setIndicateurDecouvertAutorise(true);
        restTemplate.postForEntity("/comptes/courant", courantDto, String.class);

        OperationDto depot = new OperationDto();
        depot.setMontant(500);
        restTemplate.postForEntity("/comptes/127/depot", depot, String.class);

        OperationDto retrait = new OperationDto();
        retrait.setMontant(200);
        restTemplate.postForEntity("/comptes/127/retrait", retrait, String.class);

        ResponseEntity<ReleveDto> response = restTemplate.getForEntity("/comptes/127/releve", ReleveDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ReleveDto releve = response.getBody();
        assertThat(releve).isNotNull();
        assertThat(releve.getSolde()).isEqualTo(300); // 500 - 200
        assertThat(releve.getOperations()).hasSize(2);
        assertThat(releve.getOperations().get(0).getMontant()).isEqualTo(500);
        assertThat(releve.getOperations().get(1).getMontant()).isEqualTo(200);
    }
}
