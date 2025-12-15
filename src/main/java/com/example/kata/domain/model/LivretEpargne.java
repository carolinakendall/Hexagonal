package com.example.kata.domain.model;

/**
 * Classe representant un compte bancaire de type livret epargne
 */
public class LivretEpargne extends CompteBancaire {

    private final double plafondDepot;

    public LivretEpargne(String numeroCompte, double plafondDepot) {
        super(numeroCompte);
        this.plafondDepot = plafondDepot;
    }

    @Override
    public void deposer(double montant) {
        if (montant <= 0) throw new IllegalArgumentException("le montant à déposer doit être positif");
        if (getSolde() + montant > plafondDepot) {
            throw new IllegalStateException("montant du plafond dépassé");
        }
        crediter(montant);
    }

    @Override
    public void retirer(double montant) {
        if (montant <= 0) throw new IllegalArgumentException("le montant à retirer doit être positif");
        if (getSolde() - montant < 0) {
            throw new IllegalStateException("solde insuffisant");
        }
        debiter(montant);
    }
}