package com.example.kata.domain.model.enums;
/**
 * Enum pour les types de comptes pouvant exister
 */
public enum TypeCompteEnum {
    COMPTE_COURANT("CompteCourant"),
    LIVRET_EPARGNE("LivretEpargne");

    private final String libelleTypeCompte;

    TypeCompteEnum(String libelleTypeCompte) {
        this.libelleTypeCompte = libelleTypeCompte;
    }

    public String getLibelleTypeCompte() {
        return libelleTypeCompte;
    }

    public static TypeCompteEnum getEnumFromLibelle(String libelle) {
        for (TypeCompteEnum type : values()) {
            if (type.getLibelleTypeCompte().equals(libelle)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Type de compte inconnu : " + libelle);
    }
}
