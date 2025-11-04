package org.iut.refactoring.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests unitaires pour la classe ChefDeProjet.
 */
@DisplayName("Tests de la classe ChefDeProjet")
class ChefDeProjetTest {
    
    @Test
    @DisplayName("Devrait créer un chef de projet avec les bonnes valeurs")
    void devraitCreerChefDeProjetAvecBonnesValeurs() {
        // Given
        String nom = "Bob Dupont";
        double salaire = 60000.0;
        int experience = 8;
        String equipe = "Management";
        
        // When
        ChefDeProjet chef = new ChefDeProjet(nom, salaire, experience, equipe);
        
        // Then
        assertThat(chef.getNom()).isEqualTo(nom);
        assertThat(chef.getSalaireDeBase()).isEqualTo(salaire);
        assertThat(chef.getExperience()).isEqualTo(experience);
        assertThat(chef.getEquipe()).isEqualTo(equipe);
        assertThat(chef.getType()).isEqualTo("CHEF DE PROJET");
        assertThat(chef.getId()).isNotNull().isNotEmpty();
    }
    
    @Test
    @DisplayName("Devrait générer un ID unique pour chaque chef de projet")
    void devraitGenererIdUnique() {
        // Given & When
        ChefDeProjet chef1 = new ChefDeProjet("Alice", 60000, 5, "RH");
        ChefDeProjet chef2 = new ChefDeProjet("Bob", 65000, 7, "IT");
        
        // Then
        assertThat(chef1.getId()).isNotEqualTo(chef2.getId());
    }
    
    @Test
    @DisplayName("Devrait créer un chef de projet avec peu d'expérience")
    void devraitCreerChefDeProjetAvecPeuExperience() {
        // Given & When
        ChefDeProjet chef = new ChefDeProjet("Junior Chef", 55000, 2, "IT");
        
        // Then
        assertThat(chef.getExperience()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Devrait retourner le bon type")
    void devraitRetournerBonType() {
        // Given & When
        ChefDeProjet chef = new ChefDeProjet("Chef", 60000, 5, "IT");
        
        // Then
        assertThat(chef.getType()).isEqualTo("CHEF DE PROJET");
    }
    
    @Test
    @DisplayName("ToString devrait contenir toutes les informations")
    void toStringDevraitContenirToutesLesInformations() {
        // Given
        ChefDeProjet chef = new ChefDeProjet("Bob", 60000, 8, "Management");
        
        // When
        String result = chef.toString();
        
        // Then
        assertThat(result)
            .contains("Bob")
            .contains("CHEF DE PROJET")
            .contains("8 ans")
            .contains("Management");
    }
}

