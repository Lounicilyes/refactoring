package org.iut.refactoring.service.impl;

import org.iut.refactoring.model.Developpeur;
import org.iut.refactoring.model.Employe;
import org.iut.refactoring.service.StrategieSalaire;

/**
 * Stratégie de calcul de salaire pour les développeurs.
 * Principe SRP : Responsabilité unique de calculer le salaire des développeurs.
 * Principe OCP : Nouvelle stratégie sans modifier le code existant.
 */
public class StrategieSalaireDeveloppeur implements StrategieSalaire {

    private static final double MULTIPLICATEUR_BASE = 1.2;
    private static final double MULTIPLICATEUR_EXPERIENCE_5_ANS = 1.15;
    private static final double MULTIPLICATEUR_EXPERIENCE_10_ANS = 1.05;
    private static final double TAUX_BONUS = 0.1;
    private static final double MULTIPLICATEUR_BONUS_EXPERIENCE = 1.5;

    @Override
    public double calculer(Employe employe) {
        double salaire = employe.getSalaireDeBase() * MULTIPLICATEUR_BASE;

        if (employe.getExperience() > 5) {
            salaire *= MULTIPLICATEUR_EXPERIENCE_5_ANS;
        }

        if (employe.getExperience() > 10) {
            salaire *= MULTIPLICATEUR_EXPERIENCE_10_ANS;
        }

        return salaire;
    }

    @Override
    public double calculerBonus(Employe employe) {
        double bonus = employe.getSalaireDeBase() * TAUX_BONUS;

        if (employe.getExperience() > 5) {
            bonus *= MULTIPLICATEUR_BONUS_EXPERIENCE;
        }

        return bonus;
    }

    @Override
    public boolean estApplicable(Employe employe) {
        return employe instanceof Developpeur;
    }
}
