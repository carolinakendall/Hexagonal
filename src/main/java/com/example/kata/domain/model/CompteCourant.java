package com.example.kata.domain.model;

/**
 * Classe representant un compte bancaire de type compte courant
 */
public class CompteCourant extends CompteBancaire {

    private final double decouvertAutorise;

    private final boolean indicateurDecouvertAutorise;


    public CompteCourant(String numeroCompte, double decouvertAutorise, boolean indicateurDecouvertAutorise) {
        super(numeroCompte);
        this.decouvertAutorise = decouvertAutorise;
        this.indicateurDecouvertAutorise = indicateurDecouvertAutorise;
    }

    @Override
    public void deposer(double montant) {
        if (montant <= 0) throw new IllegalArgumentException("le montant à déposer doit être positif");
        crediter(montant);
    }

    @Override
    public void retirer(double montant) {
        if (montant <= 0) throw new IllegalArgumentException("le montant à retirer doit être positif");
        else if (!indicateurDecouvertAutorise && getSolde() - montant < 0) {
            throw new IllegalStateException("découvert non autorisé pour ce compte");
        } else if (indicateurDecouvertAutorise && getSolde() - montant < -decouvertAutorise) {
            throw new IllegalStateException("montant du découvert dépassé");
        }
        debiter(montant);
    }
}
