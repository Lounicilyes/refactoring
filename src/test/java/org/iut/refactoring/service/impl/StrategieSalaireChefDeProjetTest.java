package org.iut.refactoring.service.impl;

import org.iut.refactoring.model.ChefDeProjet;
import org.iut.refactoring.model.Developpeur;
import org.iut.refactoring.model.Stagiaire;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests unitaires pour StrategieSalaireChefDeProjet.
 */
@DisplayName("Tests de la stratégie de salaire des chefs de projet")
class StrategieSalaireChefDeProjetTest {
    
    private final StrategieSalaireChefDeProjet strategie = new StrategieSalaireChefDeProjet();
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un chef de projet junior (< 3 ans)")
    void devraitCalculerSalaireChefDeProjetJunior() {
        // Given
        ChefDeProjet chef = new ChefDeProjet("Alice", 60000, 2, "RH");
        
        // When
        double salaire = strategie.calculer(chef);
        
        // Then
        // 60000 * 1.5 + 5000 = 95000
        assertThat(salaire).isEqualTo(95000.0);
    }
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un chef de projet expérimenté (> 3 ans)")
    void devraitCalculerSalaireChefDeProjetExperimente() {
        // Given
        ChefDeProjet chef = new ChefDeProjet("Bob", 60000, 5, "RH");
        
        // When
        double salaire = strategie.calculer(chef);
        
        // Then
        // 60000 * 1.5 * 1.1 + 5000 = 104000
        assertThat(salaire).isCloseTo(104000.0, within(0.01));
    }
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un chef de projet avec exactement 3 ans")
    void devraitCalculerSalaireChefDeProjetAvec3Ans() {
        // Given
        ChefDeProjet chef = new ChefDeProjet("Charlie", 60000, 3, "RH");
        
        // When
        double salaire = strategie.calculer(chef);
        
        // Then
        assertThat(salaire).isEqualTo(95000.0); // Pas encore > 3
    }
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un chef de projet très expérimenté")
    void devraitCalculerSalaireChefDeProjetTresExperimente() {
        // Given
        ChefDeProjet chef = new ChefDeProjet("Dan", 70000, 15, "IT");
        
        // When
        double salaire = strategie.calculer(chef);
        
        // Then
        // 70000 * 1.5 * 1.1 + 5000 = 120500
        assertThat(salaire).isCloseTo(120500.0, within(0.01));
    }
    
    @Test
    @DisplayName("Devrait calculer le bonus d'un chef de projet junior")
    void devraitCalculerBonusChefDeProjetJunior() {
        // Given
        ChefDeProjet chef = new ChefDeProjet("Alice", 60000, 2, "RH");
        
        // When
        double bonus = strategie.calculerBonus(chef);
        
        // Then
        assertThat(bonus).isCloseTo(12000.0, within(0.01)); // 60000 * 0.2
    }
    
    @Test
    @DisplayName("Devrait calculer le bonus d'un chef de projet expérimenté")
    void devraitCalculerBonusChefDeProjetExperimente() {
        // Given
        ChefDeProjet chef = new ChefDeProjet("Bob", 60000, 5, "RH");
        
        // When
        double bonus = strategie.calculerBonus(chef);
        
        // Then
        assertThat(bonus).isCloseTo(15600.0, within(0.01)); // 60000 * 0.2 * 1.3
    }
    
    @Test
    @DisplayName("Devrait être applicable aux chefs de projet")
    void devraitEtreApplicableAuxChefsDeProjet() {
        // Given
        ChefDeProjet chef = new ChefDeProjet("Alice", 60000, 5, "RH");
        
        // When & Then
        assertThat(strategie.estApplicable(chef)).isTrue();
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
    @DisplayName("Ne devrait pas être applicable aux stagiaires")
    void neDevraitPasEtreApplicableAuxStagiaires() {
        // Given
        Stagiaire stagiaire = new Stagiaire("Charlie", 20000, 0, "IT");
        
        // When & Then
        assertThat(strategie.estApplicable(stagiaire)).isFalse();
    }
}

