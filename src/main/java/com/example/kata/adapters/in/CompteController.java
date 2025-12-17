package com.example.kata.adapters.in;

import com.example.kata.adapters.in.dto.CreerCompteCourantDto;
import com.example.kata.adapters.in.dto.CreerLivretDto;
import com.example.kata.adapters.in.dto.OperationDto;
import com.example.kata.adapters.in.dto.ReleveDto;
import com.example.kata.domain.model.ReleveCompte;
import com.example.kata.ports.in.CompteLecture;
import com.example.kata.ports.in.CompteTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller pour les comptes
 * inbound adapter
 */
@RestController
@RequestMapping("/comptes")
public class CompteController {

   @Autowired
   private CompteTransaction compteTransaction;

   @Autowired
   private CompteLecture compteLecture;

    @PostMapping("/{numeroCompte}/depot")
    public ResponseEntity<String> deposer(@PathVariable String numeroCompte,
                                          @RequestBody OperationDto operationDto) {
        try {
            compteTransaction.deposer(numeroCompte, operationDto.getMontant());
            return ResponseEntity.ok("dépôt bien effectué");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{numeroCompte}/retrait")
    public ResponseEntity<String> retirer(@PathVariable String numeroCompte,
                        @RequestBody OperationDto operationDto) {
        try {
            compteTransaction.retirer(numeroCompte, operationDto.getMontant());
            return ResponseEntity.ok("retrait bien effectué");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{numeroCompte}/releve")
    public ReleveDto getReleve(@PathVariable String numeroCompte) {
        ReleveCompte releve = compteLecture.getReleve(numeroCompte);
        return ReleveDto.mapReleveCompteToReleveDto(releve);
    }

    @PostMapping("/courant")
    public ResponseEntity<String> creerCompteCourant(@RequestBody CreerCompteCourantDto creerCompteCourantDto) {
        try {
            compteTransaction.creerCompteCourant(creerCompteCourantDto);
            return ResponseEntity.ok("compte courant bien crée");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }    }

    @PostMapping("/livret")
    public ResponseEntity<String> creerLivret(@RequestBody CreerLivretDto creerLivretDto) {
        try {
            compteTransaction.creerLivret(creerLivretDto);
            return ResponseEntity.ok("livret bien crée");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}