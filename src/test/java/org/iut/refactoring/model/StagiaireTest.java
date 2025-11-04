package org.iut.refactoring.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests unitaires pour la classe Stagiaire.
 */
@DisplayName("Tests de la classe Stagiaire")
class StagiaireTest {
    
    @Test
    @DisplayName("Devrait créer un stagiaire avec les bonnes valeurs")
    void devraitCreerStagiaireAvecBonnesValeurs() {
        // Given
        String nom = "Charlie Bernard";
        double salaire = 20000.0;
        int experience = 0;
        String equipe = "IT";
        
        // When
        Stagiaire stagiaire = new Stagiaire(nom, salaire, experience, equipe);
        
        // Then
        assertThat(stagiaire.getNom()).isEqualTo(nom);
        assertThat(stagiaire.getSalaireDeBase()).isEqualTo(salaire);
        assertThat(stagiaire.getExperience()).isEqualTo(experience);
        assertThat(stagiaire.getEquipe()).isEqualTo(equipe);
        assertThat(stagiaire.getType()).isEqualTo("STAGIAIRE");
        assertThat(stagiaire.getId()).isNotNull().isNotEmpty();
    }
    
    @Test
    @DisplayName("Devrait créer un stagiaire avec expérience à 0")
    void devraitCreerStagiaireAvecExperienceZero() {
        // Given & When
        Stagiaire stagiaire = new Stagiaire("Nouveau Stagiaire", 20000, 0, "IT");
        
        // Then
        assertThat(stagiaire.getExperience()).isZero();
    }
    
    @Test
    @DisplayName("Devrait créer un stagiaire avec un peu d'expérience")
    void devraitCreerStagiaireAvecUnPeuExperience() {
        // Given & When
        Stagiaire stagiaire = new Stagiaire("Stagiaire Expérimenté", 22000, 1, "IT");
        
        // Then
        assertThat(stagiaire.getExperience()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Devrait générer un ID unique pour chaque stagiaire")
    void devraitGenererIdUnique() {
        // Given & When
        Stagiaire stag1 = new Stagiaire("Alice", 20000, 0, "IT");
        Stagiaire stag2 = new Stagiaire("Bob", 20000, 0, "RH");
        
        // Then
        assertThat(stag1.getId()).isNotEqualTo(stag2.getId());
    }
    
    @Test
    @DisplayName("Devrait retourner le bon type")
    void devraitRetournerBonType() {
        // Given & When
        Stagiaire stagiaire = new Stagiaire("Stagiaire", 20000, 0, "IT");
        
        // Then
        assertThat(stagiaire.getType()).isEqualTo("STAGIAIRE");
    }
    
    @Test
    @DisplayName("ToString devrait contenir toutes les informations")
    void toStringDevraitContenirToutesLesInformations() {
        // Given
        Stagiaire stagiaire = new Stagiaire("Charlie", 20000, 0, "IT");
        
        // When
        String result = stagiaire.toString();
        
        // Then
        assertThat(result)
            .contains("Charlie")
            .contains("STAGIAIRE")
            .contains("0 ans")
            .contains("IT");
    }
}

