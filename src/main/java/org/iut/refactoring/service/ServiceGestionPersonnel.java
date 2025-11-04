package org.iut.refactoring.service;

import org.iut.refactoring.model.Employe;

/**
 * Interface pour le service de gestion du personnel.
 * Principe ISP : Interface cliente spécifique.
 * Principe SRP : Coordonne les opérations métier de haut niveau.
 */
public interface ServiceGestionPersonnel {
    /**
     * Ajoute un nouvel employé.
     * @param type Le type d'employé
     * @param nom Le nom
     * @param salaireDeBase Le salaire de base
     * @param experience L'expérience en années
     * @param equipe L'équipe
     * @return L'employé créé
     */
    Employe ajouterEmploye(String type, String nom, double salaireDeBase, int experience, String equipe);
    
    /**
     * Calcule le salaire d'un employé.
     * @param employeId L'ID de l'employé
     * @return Le salaire calculé
     */
    double calculerSalaire(String employeId);
    
    /**
     * Calcule le bonus annuel d'un employé.
     * @param employeId L'ID de l'employé
     * @return Le bonus calculé
     */
    double calculerBonusAnnuel(String employeId);
    
    /**
     * Génère un rapport.
     * @param typeRapport Le type de rapport (SALAIRE, EXPERIENCE, DIVISION)
     * @param filtre Filtre optionnel
     */
    void genererRapport(String typeRapport, String filtre);
    
    /**
     * Promeut un employé à un nouveau type.
     * @param employeId L'ID de l'employé
     * @param nouveauType Le nouveau type
     */
    void promouvoirEmploye(String employeId, String nouveauType);
    
    /**
     * Affiche les logs.
     */
    void afficherLogs();
}

