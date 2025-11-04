package org.iut.refactoring.service.impl;

import org.iut.refactoring.model.Developpeur;
import org.iut.refactoring.model.ChefDeProjet;
import org.iut.refactoring.model.Stagiaire;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests unitaires pour StrategieSalaireDeveloppeur.
 */
@DisplayName("Tests de la stratégie de salaire des développeurs")
class StrategieSalaireDeveloppeurTest {
    
    private final StrategieSalaireDeveloppeur strategie = new StrategieSalaireDeveloppeur();
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un développeur junior (< 5 ans)")
    void devraitCalculerSalaireDeveloppeurJunior() {
        // Given
        Developpeur dev = new Developpeur("Alice", 50000, 3, "IT");
        
        // When
        double salaire = strategie.calculer(dev);
        
        // Then
        assertThat(salaire).isCloseTo(60000.0, within(0.01)); // 50000 * 1.2
    }
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un développeur intermédiaire (5-10 ans)")
    void devraitCalculerSalaireDeveloppeurIntermediaire() {
        // Given
        Developpeur dev = new Developpeur("Bob", 50000, 6, "IT");
        
        // When
        double salaire = strategie.calculer(dev);
        
        // Then
        assertThat(salaire).isCloseTo(69000.0, within(0.01)); // 50000 * 1.2 * 1.15
    }
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un développeur senior (> 10 ans)")
    void devraitCalculerSalaireDeveloppeurSenior() {
        // Given
        Developpeur dev = new Developpeur("Charlie", 50000, 12, "IT");
        
        // When
        double salaire = strategie.calculer(dev);
        
        // Then
        // 50000 * 1.2 * 1.15 * 1.05 = 72450
        assertThat(salaire).isCloseTo(72450.0, within(0.01));
    }
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un développeur avec exactement 5 ans")
    void devraitCalculerSalaireDeveloppeurAvec5Ans() {
        // Given
        Developpeur dev = new Developpeur("Dan", 50000, 5, "IT");
        
        // When
        double salaire = strategie.calculer(dev);
        
        // Then
        assertThat(salaire).isCloseTo(60000.0, within(0.01)); // 50000 * 1.2 (pas encore > 5)
    }
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un développeur avec exactement 10 ans")
    void devraitCalculerSalaireDeveloppeurAvec10Ans() {
        // Given
        Developpeur dev = new Developpeur("Eve", 50000, 10, "IT");
        
        // When
        double salaire = strategie.calculer(dev);
        
        // Then
        assertThat(salaire).isCloseTo(69000.0, within(0.01)); // 50000 * 1.2 * 1.15 (pas encore > 10)
    }
    
    @Test
    @DisplayName("Devrait calculer le bonus d'un développeur junior")
    void devraitCalculerBonusDeveloppeurJunior() {
        // Given
        Developpeur dev = new Developpeur("Alice", 50000, 3, "IT");
        
        // When
        double bonus = strategie.calculerBonus(dev);
        
        // Then
        assertThat(bonus).isCloseTo(5000.0, within(0.01)); // 50000 * 0.1
    }
    
    @Test
    @DisplayName("Devrait calculer le bonus d'un développeur expérimenté (> 5 ans)")
    void devraitCalculerBonusDeveloppeurExperimente() {
        // Given
        Developpeur dev = new Developpeur("Bob", 50000, 6, "IT");
        
        // When
        double bonus = strategie.calculerBonus(dev);
        
        // Then
        assertThat(bonus).isCloseTo(7500.0, within(0.01)); // 50000 * 0.1 * 1.5
    }
    
    @Test
    @DisplayName("Devrait être applicable aux développeurs")
    void devraitEtreApplicableAuxDeveloppeurs() {
        // Given
        Developpeur dev = new Developpeur("Alice", 50000, 5, "IT");
        
        // When & Then
        assertThat(strategie.estApplicable(dev)).isTrue();
    }
    
    @Test
    @DisplayName("Ne devrait pas être applicable aux chefs de projet")
    void neDevraitPasEtreApplicableAuxChefsDeProjet() {
        // Given
        ChefDeProjet chef = new ChefDeProjet("Bob", 60000, 5, "IT");
        
        // When & Then
        assertThat(strategie.estApplicable(chef)).isFalse();
    }
    
    @Test
    @DisplayName("Ne devrait pas être applicable aux stagiaires")
    void neDevraitPasEtreApplicableAuxStagiaires() {
        // Given
        Stagiaire stagiaire = new Stagiaire("Charlie", 20000, 0, "IT");
        
        // When & Then
        assertThat(strategie.estApplicable(stagiaire)).isFalse();
    }
}

