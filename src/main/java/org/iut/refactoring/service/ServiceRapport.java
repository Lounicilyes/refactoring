package org.iut.refactoring.service;

import org.iut.refactoring.model.Employe;

import java.util.List;

/**
 * Interface pour le service de rapports.
 * Principe ISP : Interface spécifique à la génération de rapports.
 * Principe SRP : Responsabilité unique de générer des rapports.
 */
public interface ServiceRapport {
    /**
     * Génère un rapport de salaires.
     * @param employes Liste des employés
     * @param filtre Filtre optionnel par équipe (null pour tous)
     */
    void genererRapportSalaires(List<Employe> employes, String filtre);
    
    /**
     * Génère un rapport d'expérience.
     * @param employes Liste des employés
     * @param filtre Filtre optionnel par équipe (null pour tous)
     */
    void genererRapportExperience(List<Employe> employes, String filtre);
    
    /**
     * Génère un rapport par division/équipe.
     * @param employes Liste des employés
     */
    void genererRapportDivision(List<Employe> employes);
}

