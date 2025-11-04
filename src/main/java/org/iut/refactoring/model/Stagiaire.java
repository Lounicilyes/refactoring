package org.iut.refactoring.model;

/**
 * Classe concrète représentant un stagiaire.
 * Principe LSP : Peut être substituée partout où Employe est attendu.
 */
public class Stagiaire extends Employe {

    public Stagiaire(String nom, double salaireDeBase, int experience, String equipe) {
        super(nom, salaireDeBase, experience, equipe);
    }

    @Override
    public String getType() {
        return "STAGIAIRE";
    }
}

