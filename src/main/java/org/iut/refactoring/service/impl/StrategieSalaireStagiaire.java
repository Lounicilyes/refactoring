package org.iut.refactoring.service.impl;

import org.iut.refactoring.model.Employe;
import org.iut.refactoring.model.Stagiaire;
import org.iut.refactoring.service.StrategieSalaire;

/**
 * Stratégie de calcul de salaire pour les stagiaires.
 * Principe SRP : Responsabilité unique de calculer le salaire des stagiaires.
 * Principe OCP : Nouvelle stratégie sans modifier le code existant.
 */
public class StrategieSalaireStagiaire implements StrategieSalaire {
    
    private static final double MULTIPLICATEUR_BASE = 0.6;
    
    @Override
    public double calculer(Employe employe) {
        return employe.getSalaireDeBase() * MULTIPLICATEUR_BASE;
    }
    
    @Override
    public double calculerBonus(Employe employe) {
        return 0.0; // Pas de bonus pour les stagiaires
    }
    
    @Override
    public boolean estApplicable(Employe employe) {
        return employe instanceof Stagiaire;
    }
}

