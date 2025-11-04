package org.iut.refactoring.model;

/**
 * Classe concrète représentant un chef de projet.
 * Principe LSP : Peut être substituée partout où Employe est attendu.
 */
public class ChefDeProjet extends Employe {

    public ChefDeProjet(String nom, double salaireDeBase, int experience, String equipe) {
        super(nom, salaireDeBase, experience, equipe);
    }

    @Override
    public String getType() {
        return "CHEF DE PROJET";
    }
}

