package com.example.kata.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe representant un compte bancaire
 */
public abstract class CompteBancaire {

    private final String numeroCompte;
    private double solde;
    private final List<Operation> operations = new ArrayList<>();

    protected CompteBancaire(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public double getSolde() {
        return solde;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    protected void crediter(double montant) {
        solde += montant;
        operations.add(Operation.depot(montant));
    }

    protected void debiter(double montant) {
        solde -= montant;
        operations.add(Operation.retrait(montant));
    }

    public abstract void deposer(double montant);
    public abstract void retirer(double montant);

}
