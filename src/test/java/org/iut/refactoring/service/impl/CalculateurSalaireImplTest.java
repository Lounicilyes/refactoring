package org.iut.refactoring.service.impl;

import org.iut.refactoring.model.Developpeur;
import org.iut.refactoring.model.ChefDeProjet;
import org.iut.refactoring.model.Stagiaire;
import org.iut.refactoring.model.Employe;
import org.iut.refactoring.service.StrategieSalaire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests unitaires pour CalculateurSalaireImpl.
 */
@DisplayName("Tests du calculateur de salaire")
class CalculateurSalaireImplTest {
    
    private CalculateurSalaireImpl calculateur;
    
    @BeforeEach
    void setUp() {
        calculateur = new CalculateurSalaireImpl();
    }
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un développeur")
    void devraitCalculerSalaireDeveloppeur() {
        // Given
        Employe dev = new Developpeur("Alice", 50000, 6, "IT");
        
        // When
        double salaire = calculateur.calculerSalaire(dev);
        
        // Then
        assertThat(salaire).isCloseTo(69000.0, within(0.01)); // 50000 * 1.2 * 1.15
    }
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un chef de projet")
    void devraitCalculerSalaireChefDeProjet() {
        // Given
        Employe chef = new ChefDeProjet("Bob", 60000, 5, "RH");
        
        // When
        double salaire = calculateur.calculerSalaire(chef);
        
        // Then
        assertThat(salaire).isCloseTo(104000.0, within(0.01)); // 60000 * 1.5 * 1.1 + 5000
    }
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un stagiaire")
    void devraitCalculerSalaireStagiaire() {
        // Given
        Employe stagiaire = new Stagiaire("Charlie", 20000, 0, "IT");
        
        // When
        double salaire = calculateur.calculerSalaire(stagiaire);
        
        // Then
        assertThat(salaire).isCloseTo(12000.0, within(0.01)); // 20000 * 0.6
    }
    
    @Test
    @DisplayName("Devrait calculer le bonus d'un développeur")
    void devraitCalculerBonusDeveloppeur() {
        // Given
        Employe dev = new Developpeur("Alice", 50000, 6, "IT");
        
        // When
        double bonus = calculateur.calculerBonus(dev);
        
        // Then
        assertThat(bonus).isCloseTo(7500.0, within(0.01)); // 50000 * 0.1 * 1.5
    }
    
    @Test
    @DisplayName("Devrait calculer le bonus d'un chef de projet")
    void devraitCalculerBonusChefDeProjet() {
        // Given
        Employe chef = new ChefDeProjet("Bob", 60000, 5, "RH");
        
        // When
        double bonus = calculateur.calculerBonus(chef);
        
        // Then
        assertThat(bonus).isCloseTo(15600.0, within(0.01)); // 60000 * 0.2 * 1.3
    }
    
    @Test
    @DisplayName("Devrait calculer le bonus d'un stagiaire (zéro)")
    void devraitCalculerBonusStagiaire() {
        // Given
        Employe stagiaire = new Stagiaire("Charlie", 20000, 0, "IT");
        
        // When
        double bonus = calculateur.calculerBonus(stagiaire);
        
        // Then
        assertThat(bonus).isZero();
    }
    
    @Test
    @DisplayName("Devrait accepter des stratégies personnalisées via le constructeur")
    void devraitAccepterStrategiesPersonnalisees() {
        // Given
        List<StrategieSalaire> strategies = Arrays.asList(
            new StrategieSalaireDeveloppeur(),
            new StrategieSalaireChefDeProjet()
        );
        CalculateurSalaireImpl calc = new CalculateurSalaireImpl(strategies);
        Employe dev = new Developpeur("Alice", 50000, 5, "IT");
        
        // When
        double salaire = calc.calculerSalaire(dev);
        
        // Then
        assertThat(salaire).isGreaterThan(0);
    }
    
    @Test
    @DisplayName("Devrait lever une exception si aucune stratégie n'est applicable")
    void devraitLeverExceptionSiAucuneStrategieApplicable() {
        // Given
        List<StrategieSalaire> strategiesVides = Arrays.asList();
        CalculateurSalaireImpl calc = new CalculateurSalaireImpl(strategiesVides);
        Employe dev = new Developpeur("Alice", 50000, 5, "IT");
        
        // When & Then
        assertThatThrownBy(() -> calc.calculerSalaire(dev))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Aucune stratégie trouvée");
    }
    
    @Test
    @DisplayName("Devrait calculer des salaires différents pour différents types")
    void devraitCalculerSalairesDifferentsPourDifferentsTypes() {
        // Given
        Employe dev = new Developpeur("Alice", 50000, 5, "IT");
        Employe chef = new ChefDeProjet("Bob", 50000, 5, "RH");
        Employe stag = new Stagiaire("Charlie", 50000, 0, "IT");
        
        // When
        double salaireDev = calculateur.calculerSalaire(dev);
        double salaireChef = calculateur.calculerSalaire(chef);
        double salaireStag = calculateur.calculerSalaire(stag);
        
        // Then
        assertThat(salaireDev).isNotEqualTo(salaireChef);
        assertThat(salaireChef).isNotEqualTo(salaireStag);
        assertThat(salaireDev).isNotEqualTo(salaireStag);
    }
}

