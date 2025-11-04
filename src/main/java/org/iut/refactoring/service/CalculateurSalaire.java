package org.iut.refactoring.service;

import org.iut.refactoring.model.Employe;

/**
 * Interface pour le calcul de salaire.
 * Principe DIP : Dépendance sur une abstraction plutôt qu'une implémentation concrète.
 * Principe ISP : Interface spécifique au calcul de salaire.
 */
public interface CalculateurSalaire {
    /**
     * Calcule le salaire d'un employé.
     * @param employe L'employé pour lequel calculer le salaire
     * @return Le salaire calculé
     */
    double calculerSalaire(Employe employe);

    /**
     * Calcule le bonus annuel d'un employé.
     * @param employe L'employé pour lequel calculer le bonus
     * @return Le bonus calculé
     */
    double calculerBonus(Employe employe);
}

