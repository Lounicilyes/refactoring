package org.iut.refactoring.service;

import org.iut.refactoring.model.Employe;

/**
 * Interface pour les stratégies de calcul de salaire par type d'employé.
 * Principe OCP : Permet d'ajouter de nouvelles stratégies sans modifier le code existant.
 * Principe Strategy Pattern : Encapsule les algorithmes de calcul.
 */
public interface StrategieSalaire {
    /**
     * Calcule le salaire pour un type d'employé spécifique.
     * @param employe L'employé
     * @return Le salaire calculé
     */
    double calculer(Employe employe);

    /**
     * Calcule le bonus pour un type d'employé spécifique.
     * @param employe L'employé
     * @return Le bonus calculé
     */
    double calculerBonus(Employe employe);

    /**
     * Vérifie si cette stratégie s'applique à l'employé donné.
     * @param employe L'employé
     * @return true si la stratégie s'applique
     */
    boolean estApplicable(Employe employe);
}

