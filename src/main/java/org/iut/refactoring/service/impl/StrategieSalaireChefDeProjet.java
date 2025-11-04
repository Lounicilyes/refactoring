package org.iut.refactoring.service.impl;

import org.iut.refactoring.model.ChefDeProjet;
import org.iut.refactoring.model.Employe;
import org.iut.refactoring.service.StrategieSalaire;

/**
 * Stratégie de calcul de salaire pour les chefs de projet.
 * Principe SRP : Responsabilité unique de calculer le salaire des chefs de projet.
 * Principe OCP : Nouvelle stratégie sans modifier le code existant.
 */
public class StrategieSalaireChefDeProjet implements StrategieSalaire {
    
    private static final double MULTIPLICATEUR_BASE = 1.5;
    private static final double MULTIPLICATEUR_EXPERIENCE = 1.1;
    private static final double BONUS_FIXE = 5000.0;
    private static final double TAUX_BONUS = 0.2;
    private static final double MULTIPLICATEUR_BONUS_EXPERIENCE = 1.3;
    
    @Override
    public double calculer(Employe employe) {
        double salaire = employe.getSalaireDeBase() * MULTIPLICATEUR_BASE;
        
        if (employe.getExperience() > 3) {
            salaire *= MULTIPLICATEUR_EXPERIENCE;
        }
        
        salaire += BONUS_FIXE;
        
        return salaire;
    }
    
    @Override
    public double calculerBonus(Employe employe) {
        double bonus = employe.getSalaireDeBase() * TAUX_BONUS;
        
        if (employe.getExperience() > 3) {
            bonus *= MULTIPLICATEUR_BONUS_EXPERIENCE;
        }
        
        return bonus;
    }
    
    @Override
    public boolean estApplicable(Employe employe) {
        return employe instanceof ChefDeProjet;
    }
}

