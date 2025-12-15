package com.example.kata.domain.service;

import com.example.kata.domain.model.CompteBancaire;
import com.example.kata.domain.model.ReleveCompte;
import com.example.kata.domain.model.enums.TypeCompteEnum;

/**
 * Service permettant de generer un releve de compte bancaire
 */
public class ReleveCompteService {

    /**
     * Permet de generer un releve de compte
     * @param compte Le compte bancaire en question
     * @return  Le releve de compte correspondant au compte bancaire
     */
    public ReleveCompte genererReleve(CompteBancaire compte) {
        TypeCompteEnum typeCompteEnum = TypeCompteEnum.getEnumFromLibelle(compte.getClass().getSimpleName());
        double solde = compte.getSolde();
        return new ReleveCompte(typeCompteEnum, solde, compte.getOperations());
    }
}
