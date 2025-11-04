package org.iut.refactoring.factory;

import org.iut.refactoring.model.ChefDeProjet;
import org.iut.refactoring.model.Developpeur;
import org.iut.refactoring.model.Employe;
import org.iut.refactoring.model.Stagiaire;

/**
 * Factory pour créer des employés.
 * Principe SRP : Responsabilité unique de créer des employés.
 * Principe OCP : Peut être étendue pour de nouveaux types d'employés.
 * Pattern Factory : Encapsule la logique de création.
 */
public class EmployeFactory {
    
    /**
     * Crée un employé selon son type.
     * @param type Le type d'employé
     * @param nom Le nom
     * @param salaireDeBase Le salaire de base
     * @param experience L'expérience en années
     * @param equipe L'équipe
     * @return L'employé créé
     * @throws IllegalArgumentException Si le type n'est pas reconnu
     */
    public static Employe creerEmploye(String type, String nom, double salaireDeBase, 
                                       int experience, String equipe) {
        if (type == null) {
            throw new IllegalArgumentException("Le type ne peut pas être null");
        }
        
        switch (type.toUpperCase()) {
            case "DEVELOPPEUR":
                return new Developpeur(nom, salaireDeBase, experience, equipe);
            case "CHEF DE PROJET":
                return new ChefDeProjet(nom, salaireDeBase, experience, equipe);
            case "STAGIAIRE":
                return new Stagiaire(nom, salaireDeBase, experience, equipe);
            default:
                throw new IllegalArgumentException("Type d'employé non reconnu: " + type);
        }
    }
}

