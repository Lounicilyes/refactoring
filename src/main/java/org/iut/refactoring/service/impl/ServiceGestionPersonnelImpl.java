package org.iut.refactoring.service.impl;

import org.iut.refactoring.factory.EmployeFactory;
import org.iut.refactoring.model.Employe;
import org.iut.refactoring.repository.EmployeRepository;
import org.iut.refactoring.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation du service de gestion du personnel.
 * Principe SRP : Coordonne les services mais ne fait pas le travail lui-même.
 * Principe DIP : Dépend des abstractions (interfaces), pas des implémentations concrètes.
 * Principe OCP : Ouvert à l'extension via l'ajout de nouveaux services.
 */
public class ServiceGestionPersonnelImpl implements ServiceGestionPersonnel {
    
    private static final Logger logger = LoggerFactory.getLogger(ServiceGestionPersonnelImpl.class);
    private static final String ERREUR_EMPLOYE_INTROUVABLE = "ERREUR: impossible de trouver l'employé avec l'ID: ";

    private final EmployeRepository employeRepository;
    private final CalculateurSalaire calculateurSalaire;
    private final ServiceRapport serviceRapport;
    private final ServiceLog serviceLog;
    
    /**
     * Constructeur avec injection de dépendances (DIP).
     * @param employeRepository Le repository des employés
     * @param calculateurSalaire Le calculateur de salaire
     * @param serviceRapport Le service de rapports
     * @param serviceLog Le service de logs
     */
    public ServiceGestionPersonnelImpl(EmployeRepository employeRepository,
                                       CalculateurSalaire calculateurSalaire,
                                       ServiceRapport serviceRapport,
                                       ServiceLog serviceLog) {
        this.employeRepository = employeRepository;
        this.calculateurSalaire = calculateurSalaire;
        this.serviceRapport = serviceRapport;
        this.serviceLog = serviceLog;
    }

    @Override
    public Employe ajouterEmploye(String type, String nom, double salaireDeBase,
                                   int experience, String equipe) {
        Employe employe = EmployeFactory.creerEmploye(type, nom, salaireDeBase, experience, equipe);
        employeRepository.ajouter(employe);
        serviceLog.enregistrer("Ajout de l'employé: " + nom);
        return employe;
    }

    @Override
    public double calculerSalaire(String employeId) {
        Employe employe = employeRepository.trouverParId(employeId)
                .orElseThrow(() -> new IllegalArgumentException(ERREUR_EMPLOYE_INTROUVABLE + employeId));
        return calculateurSalaire.calculerSalaire(employe);
    }

    @Override
    public double calculerBonusAnnuel(String employeId) {
        Employe employe = employeRepository.trouverParId(employeId)
                .orElseThrow(() -> new IllegalArgumentException(ERREUR_EMPLOYE_INTROUVABLE + employeId));
        return calculateurSalaire.calculerBonus(employe);
    }

    @Override
    public void genererRapport(String typeRapport, String filtre) {
        if (typeRapport == null) {
            throw new IllegalArgumentException("Le type de rapport ne peut pas être null");
        }

        switch (typeRapport.toUpperCase()) {
            case "SALAIRE":
                serviceRapport.genererRapportSalaires(employeRepository.trouverTous(), filtre);
                break;
            case "EXPERIENCE":
                serviceRapport.genererRapportExperience(employeRepository.trouverTous(), filtre);
                break;
            case "DIVISION":
                serviceRapport.genererRapportDivision(employeRepository.trouverTous());
                break;
            default:
                throw new IllegalArgumentException("Type de rapport non reconnu: " + typeRapport);
        }

        serviceLog.enregistrer("Rapport généré: " + typeRapport);
    }

    @Override
    public void promouvoirEmploye(String employeId, String nouveauType) {
        Employe ancienEmploye = employeRepository.trouverParId(employeId)
                .orElseThrow(() -> new IllegalArgumentException(ERREUR_EMPLOYE_INTROUVABLE + employeId));

        // Supprimer l'ancien employé et créer un nouveau avec le nouveau type
        employeRepository.supprimer(employeId);

        Employe nouvelEmploye = EmployeFactory.creerEmploye(
                nouveauType,
                ancienEmploye.getNom(),
                ancienEmploye.getSalaireDeBase(),
                ancienEmploye.getExperience(),
                ancienEmploye.getEquipe()
        );

        employeRepository.ajouter(nouvelEmploye);
        serviceLog.enregistrer("Employé promu: " + ancienEmploye.getNom());
        logger.info("Employé promu avec succès!");
    }

    @Override
    public void afficherLogs() {
        serviceLog.afficherLogs();
    }
}

