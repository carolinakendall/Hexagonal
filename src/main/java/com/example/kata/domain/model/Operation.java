package com.example.kata.domain.model;

import com.example.kata.domain.model.enums.TypeOperationEnum;
import java.time.LocalDateTime;

/**
 * Classe representant une operation bancaire
 */
public class Operation {

    private final LocalDateTime date;
    private final TypeOperationEnum typeOperationEnum;
    private final double montant;

    private Operation(TypeOperationEnum typeOperationEnum, double montant) {
        if (montant <= 0) throw new IllegalArgumentException("le montant doit être positif");
        this.date = LocalDateTime.now();
        this.typeOperationEnum = typeOperationEnum;
        this.montant = montant;
    }

    public static Operation depot(double montant) {
        return new Operation(TypeOperationEnum.DEPOT, montant);
    }

    public static Operation retrait(double montant) {
        return new Operation(TypeOperationEnum.RETRAIT, montant);
    }

    public LocalDateTime date() {
        return date;
    }

    public TypeOperationEnum type() {
        return typeOperationEnum;
    }

    public double montant() {
        return montant;
    }

}
