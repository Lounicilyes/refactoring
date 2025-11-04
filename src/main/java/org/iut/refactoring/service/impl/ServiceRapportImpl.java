package org.iut.refactoring.service.impl;

import org.iut.refactoring.model.Employe;
import org.iut.refactoring.service.CalculateurSalaire;
import org.iut.refactoring.service.ServiceRapport;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implémentation du service de rapports.
 * Principe SRP : Responsabilité unique de générer des rapports.
 * Principe DIP : Dépend de l'abstraction CalculateurSalaire.
 */
public class ServiceRapportImpl implements ServiceRapport {
    
    private final CalculateurSalaire calculateurSalaire;
    
    /**
     * Constructeur avec injection de dépendances (DIP).
     * @param calculateurSalaire Le calculateur de salaire
     */
    public ServiceRapportImpl(CalculateurSalaire calculateurSalaire) {
        this.calculateurSalaire = calculateurSalaire;
    }
    
    @Override
    public void genererRapportSalaires(List<Employe> employes, String filtre) {
        System.out.println("=== RAPPORT: SALAIRE ===");
        
        employes.stream()
                .filter(emp -> filtre == null || filtre.isEmpty() || emp.getEquipe().equals(filtre))
                .forEach(emp -> {
                    double salaire = calculateurSalaire.calculerSalaire(emp);
                    System.out.printf("%s: %.2f €%n", emp.getNom(), salaire);
                });
    }
    
    @Override
    public void genererRapportExperience(List<Employe> employes, String filtre) {
        System.out.println("=== RAPPORT: EXPERIENCE ===");
        
        employes.stream()
                .filter(emp -> filtre == null || filtre.isEmpty() || emp.getEquipe().equals(filtre))
                .forEach(emp -> System.out.printf("%s: %d années%n", emp.getNom(), emp.getExperience()));
    }
    
    @Override
    public void genererRapportDivision(List<Employe> employes) {
        System.out.println("=== RAPPORT: DIVISION ===");
        
        Map<String, Long> compteurParEquipe = employes.stream()
                .collect(Collectors.groupingBy(Employe::getEquipe, Collectors.counting()));
        
        compteurParEquipe.forEach((equipe, count) -> 
                System.out.printf("%s: %d employés%n", equipe, count));
    }
}

