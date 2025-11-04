package org.iut.refactoring.model;

/**
 * Classe concrète représentant un développeur.
 * Principe LSP : Peut être substituée partout où Employe est attendu.
 */
public class Developpeur extends Employe {

    public Developpeur(String nom, double salaireDeBase, int experience, String equipe) {
        super(nom, salaireDeBase, experience, equipe);
    }

    @Override
    public String getType() {
        return "DEVELOPPEUR";
    }
}

