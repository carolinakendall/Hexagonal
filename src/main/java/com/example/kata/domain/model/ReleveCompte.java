package com.example.kata.domain.model;

import com.example.kata.domain.model.enums.TypeCompteEnum;

import java.util.List;

/**
 * Classe representant un releve de compte
 */
public class ReleveCompte {

    private final TypeCompteEnum typeCompteEnum;
    private final double soldeParDate;
    private final List<Operation> operations;

    public ReleveCompte(TypeCompteEnum typeCompteEnum, double soldeParDate, List<Operation> operations) {
        this.typeCompteEnum = typeCompteEnum;
        this.soldeParDate = soldeParDate;
        this.operations = operations;
    }

    public TypeCompteEnum getTypeCompte() {
        return typeCompteEnum;
    }

    public double getSoldeParDate() {
        return soldeParDate;
    }

    public List<Operation> getOperations() {
        return operations;
    }
}
