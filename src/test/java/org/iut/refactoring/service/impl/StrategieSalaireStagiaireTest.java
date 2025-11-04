package org.iut.refactoring.service.impl;

import org.iut.refactoring.model.Stagiaire;
import org.iut.refactoring.model.Developpeur;
import org.iut.refactoring.model.ChefDeProjet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests unitaires pour StrategieSalaireStagiaire.
 */
@DisplayName("Tests de la stratégie de salaire des stagiaires")
class StrategieSalaireStagiaireTest {
    
    private final StrategieSalaireStagiaire strategie = new StrategieSalaireStagiaire();
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un stagiaire sans expérience")
    void devraitCalculerSalaireStagiaireSansExperience() {
        // Given
        Stagiaire stagiaire = new Stagiaire("Alice", 20000, 0, "IT");
        
        // When
        double salaire = strategie.calculer(stagiaire);
        
        // Then
        assertThat(salaire).isCloseTo(12000.0, within(0.01)); // 20000 * 0.6
    }
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un stagiaire avec un peu d'expérience")
    void devraitCalculerSalaireStagiaireAvecExperience() {
        // Given
        Stagiaire stagiaire = new Stagiaire("Bob", 22000, 1, "IT");
        
        // When
        double salaire = strategie.calculer(stagiaire);
        
        // Then
        assertThat(salaire).isCloseTo(13200.0, within(0.01)); // 22000 * 0.6
    }
    
    @Test
    @DisplayName("Devrait calculer le salaire avec différents salaires de base")
    void devraitCalculerSalaireAvecDifferentsSalairesDeBase() {
        // Given
        Stagiaire stag1 = new Stagiaire("Alice", 15000, 0, "IT");
        Stagiaire stag2 = new Stagiaire("Bob", 25000, 0, "RH");
        
        // When
        double salaire1 = strategie.calculer(stag1);
        double salaire2 = strategie.calculer(stag2);
        
        // Then
        assertThat(salaire1).isCloseTo(9000.0, within(0.01));  // 15000 * 0.6
        assertThat(salaire2).isCloseTo(15000.0, within(0.01)); // 25000 * 0.6
    }
    
    @Test
    @DisplayName("Devrait retourner zéro pour le bonus des stagiaires")
    void devraitRetournerZeroPourBonus() {
        // Given
        Stagiaire stagiaire = new Stagiaire("Alice", 20000, 0, "IT");
        
        // When
        double bonus = strategie.calculerBonus(stagiaire);
        
        // Then
        assertThat(bonus).isZero();
    }
    
    @Test
    @DisplayName("Devrait retourner zéro pour le bonus même avec expérience")
    void devraitRetournerZeroPourBonusMemeAvecExperience() {
        // Given
        Stagiaire stagiaire = new Stagiaire("Bob", 22000, 2, "IT");
        
        // When
        double bonus = strategie.calculerBonus(stagiaire);
        
        // Then
        assertThat(bonus).isZero();
    }
    
    @Test
    @DisplayName("Devrait être applicable aux stagiaires")
    void devraitEtreApplicableAuxStagiaires() {
        // Given
        Stagiaire stagiaire = new Stagiaire("Alice", 20000, 0, "IT");
        
        // When & Then
        assertThat(strategie.estApplicable(stagiaire)).isTrue();
    }
    
    @Test
    @DisplayName("Ne devrait pas être applicable aux développeurs")
    void neDevraitPasEtreApplicableAuxDeveloppeurs() {
        // Given
        Developpeur dev = new Developpeur("Bob", 50000, 5, "IT");
        
        // When & Then
        assertThat(strategie.estApplicable(dev)).isFalse();
    }
    
    @Test
    @DisplayName("Ne devrait pas être applicable aux chefs de projet")
    void neDevraitPasEtreApplicableAuxChefsDeProjet() {
        // Given
        ChefDeProjet chef = new ChefDeProjet("Charlie", 60000, 5, "RH");
        
        // When & Then
        assertThat(strategie.estApplicable(chef)).isFalse();
    }
}

