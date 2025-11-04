package org.iut.refactoring.repository;

import org.iut.refactoring.model.Employe;

import java.util.List;
import java.util.Optional;

/**
 * Interface pour le repository des employés.
 * Principe ISP : Interface spécifique à la gestion des employés.
 * Principe DIP : Abstraction pour la persistance des données.
 */
public interface EmployeRepository {
    /**
     * Ajoute un employé.
     * @param employe L'employé à ajouter
     */
    void ajouter(Employe employe);
    
    /**
     * Trouve un employé par son ID.
     * @param id L'ID de l'employé
     * @return Optional contenant l'employé si trouvé
     */
    Optional<Employe> trouverParId(String id);
    
    /**
     * Récupère tous les employés.
     * @return Liste de tous les employés
     */
    List<Employe> trouverTous();
    
    /**
     * Trouve les employés d'une équipe spécifique.
     * @param equipe Le nom de l'équipe
     * @return Liste des employés de l'équipe
     */
    List<Employe> trouverParEquipe(String equipe);
    
    /**
     * Supprime un employé.
     * @param id L'ID de l'employé à supprimer
     * @return true si supprimé avec succès
     */
    boolean supprimer(String id);
}

