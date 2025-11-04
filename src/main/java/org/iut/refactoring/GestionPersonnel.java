package org.iut.refactoring;

import org.iut.refactoring.model.Employe;
import org.iut.refactoring.repository.EmployeRepository;
import org.iut.refactoring.repository.impl.EmployeRepositoryImpl;
import org.iut.refactoring.service.CalculateurSalaire;
import org.iut.refactoring.service.ServiceGestionPersonnel;
import org.iut.refactoring.service.ServiceLog;
import org.iut.refactoring.service.ServiceRapport;
import org.iut.refactoring.service.impl.CalculateurSalaireImpl;
import org.iut.refactoring.service.impl.ServiceGestionPersonnelImpl;
import org.iut.refactoring.service.impl.ServiceLogImpl;
import org.iut.refactoring.service.impl.ServiceRapportImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Classe Facade pour la gestion du personnel.
 * Cette classe maintient la compatibilité avec l'ancienne API tout en utilisant
 * la nouvelle architecture refactorisée respectant les principes SOLID.
 *
 * PRINCIPES SOLID APPLIQUÉS :
 * - SRP : Cette classe délègue les responsabilités aux services appropriés
 * - OCP : Extensible via l'ajout de nouveaux types d'employés et services
 * - LSP : Les sous-classes d'Employe sont substituables
 * - ISP : Interfaces spécifiques pour chaque service
 * - DIP : Dépend des abstractions (interfaces) et non des implémentations
 */
public class GestionPersonnel {

    private static final Logger logger = LoggerFactory.getLogger(GestionPersonnel.class);

    private final ServiceGestionPersonnel serviceGestionPersonnel;
    private final EmployeRepository employeRepository;

    /**
     * Constructeur par défaut initialisant tous les services.
     * Utilise l'injection de dépendances manuelle pour créer la chaîne de services.
     */
    public GestionPersonnel() {
        // Initialisation des dépendances (Pattern Dependency Injection)
        this.employeRepository = new EmployeRepositoryImpl();
        CalculateurSalaire calculateurSalaire = new CalculateurSalaireImpl();
        ServiceLog serviceLog = new ServiceLogImpl();
        ServiceRapport serviceRapport = new ServiceRapportImpl(calculateurSalaire);

        this.serviceGestionPersonnel = new ServiceGestionPersonnelImpl(
                employeRepository,
                calculateurSalaire,
                serviceRapport,
                serviceLog
        );
    }

    /**
     * Constructeur pour injection de dépendances (utile pour les tests).
     * @param serviceGestionPersonnel Le service de gestion du personnel
     * @param employeRepository Le repository des employés
     */
    public GestionPersonnel(ServiceGestionPersonnel serviceGestionPersonnel,
                           EmployeRepository employeRepository) {
        this.serviceGestionPersonnel = serviceGestionPersonnel;
        this.employeRepository = employeRepository;
    }

    /**
     * Ajoute un salarié.
     * @param type Le type d'employé (DEVELOPPEUR, CHEF DE PROJET, STAGIAIRE)
     * @param nom Le nom
     * @param salaireDeBase Le salaire de base
     * @param experience L'expérience en années
     * @param equipe L'équipe
     */
    public void ajouteSalarie(String type, String nom, double salaireDeBase, int experience, String equipe) {
        serviceGestionPersonnel.ajouterEmploye(type, nom, salaireDeBase, experience, equipe);
    }

    /**
     * Calcule le salaire d'un employé.
     * @param employeId L'ID de l'employé
     * @return Le salaire calculé
     */
    public double calculSalaire(String employeId) {
        try {
            return serviceGestionPersonnel.calculerSalaire(employeId);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return 0;
        }
    }

    /**
     * Génère un rapport.
     * @param typeRapport Le type de rapport (SALAIRE, EXPERIENCE, DIVISION)
     * @param filtre Filtre optionnel par équipe
     */
    public void generationRapport(String typeRapport, String filtre) {
        serviceGestionPersonnel.genererRapport(typeRapport, filtre);
    }

    /**
     * Promeut un employé.
     * @param employeId L'ID de l'employé
     * @param newType Le nouveau type
     */
    public void avancementEmploye(String employeId, String newType) {
        try {
            serviceGestionPersonnel.promouvoirEmploye(employeId, newType);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Récupère les employés d'une division.
     * @param division Le nom de la division
     * @return Liste des employés
     */
    public List<Employe> getEmployesParDivision(String division) {
        return employeRepository.trouverParEquipe(division);
    }

    /**
     * Affiche les logs.
     */
    public void printLogs() {
        serviceGestionPersonnel.afficherLogs();
    }

    /**
     * Calcule le bonus annuel d'un employé.
     * @param employeId L'ID de l'employé
     * @return Le bonus calculé
     */
    public double calculBonusAnnuel(String employeId) {
        try {
            return serviceGestionPersonnel.calculerBonusAnnuel(employeId);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return 0;
        }
    }
}

