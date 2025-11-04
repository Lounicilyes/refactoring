package org.iut.refactoring;

import org.iut.refactoring.model.Employe;

import java.util.List;

/**
 * Application principale démontrant l'utilisation du système de gestion du personnel.
 * Cette classe sert d'exemple pour un TP d'université sur les principes SOLID.
 */
class GestionApp {
    public static void main(String[] args) {
        System.out.println("=== SYSTÈME DE GESTION DU PERSONNEL ===\n");

        GestionPersonnel gestion = new GestionPersonnel();

        // Ajout d'employés de différents types
        System.out.println("1. AJOUT D'EMPLOYÉS");
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice Martin", 50000, 6, "IT");
        gestion.ajouteSalarie("CHEF DE PROJET", "Bob Dupont", 60000, 4, "RH");
        gestion.ajouteSalarie("STAGIAIRE", "Charlie Bernard", 20000, 0, "IT");
        gestion.ajouteSalarie("DEVELOPPEUR", "Diana Lopez", 55000, 12, "IT");
        System.out.println("✓ 4 employés ajoutés\n");

        // Récupération des employés IT pour obtenir les IDs
        List<Employe> employesIT = gestion.getEmployesParDivision("IT");
        String aliceId = employesIT.get(0).getId();
        String charlieId = employesIT.get(2).getId();

        // Calcul de salaire et bonus
        System.out.println("2. CALCUL DE SALAIRE ET BONUS");
        double salaireAlice = gestion.calculSalaire(aliceId);
        double bonusAlice = gestion.calculBonusAnnuel(aliceId);
        System.out.println("Alice Martin:");
        System.out.printf("  - Salaire: %.2f €%n", salaireAlice);
        System.out.printf("  - Bonus annuel: %.2f €%n", bonusAlice);
        System.out.println();

        // Génération de rapports
        System.out.println("3. RAPPORTS");
        gestion.generationRapport("SALAIRE", "IT");
        System.out.println();

        gestion.generationRapport("EXPERIENCE", null);
        System.out.println();

        gestion.generationRapport("DIVISION", null);
        System.out.println();

        // Promotion d'un employé
        System.out.println("4. PROMOTION");
        System.out.println("Promotion d'Alice Martin à CHEF DE PROJET:");
        gestion.avancementEmploye(aliceId, "CHEF DE PROJET");
        System.out.println();

        // Affichage des logs
        System.out.println("5. HISTORIQUE");
        gestion.printLogs();
    }
}
