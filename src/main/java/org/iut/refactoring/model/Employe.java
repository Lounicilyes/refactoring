package org.iut.refactoring.model;

import java.util.UUID;

/**
 * Classe abstraite représentant un employé.
 * Principe SRP : Responsabilité unique de contenir les données d'un employé.
 * Principe OCP : Ouverte à l'extension (nouvelles sous-classes) mais fermée à la modification.
 */
public abstract class Employe {
    private final String id;
    private final String nom;
    private final double salaireDeBase;
    private final int experience;
    private final String equipe;

    protected Employe(String nom, double salaireDeBase, int experience, String equipe) {
        this.id = UUID.randomUUID().toString();
        this.nom = nom;
        this.salaireDeBase = salaireDeBase;
        this.experience = experience;
        this.equipe = equipe;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public double getSalaireDeBase() {
        return salaireDeBase;
    }

    public int getExperience() {
        return experience;
    }

    public String getEquipe() {
        return equipe;
    }

    /**
     * Méthode abstraite pour obtenir le type d'employé.
     * Permet le polymorphisme (LSP).
     */
    public abstract String getType();

    @Override
    public String toString() {
        return String.format("%s [%s] - %s, Expérience: %d ans, Équipe: %s",
                nom, getType(), id, experience, equipe);
    }
}

