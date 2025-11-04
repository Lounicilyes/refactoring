package org.iut.refactoring.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests unitaires pour la classe Developpeur.
 */
@DisplayName("Tests de la classe Developpeur")
class DeveloppeurTest {

    @Test
    @DisplayName("Devrait créer un développeur avec les bonnes valeurs")
    void devraitCreerDeveloppeurAvecBonnesValeurs() {
        // Given
        String nom = "Alice Martin";
        double salaire = 50000.0;
        int experience = 5;
        String equipe = "IT";

        // When
        Developpeur developpeur = new Developpeur(nom, salaire, experience, equipe);

        // Then
        assertThat(developpeur.getNom()).isEqualTo(nom);
        assertThat(developpeur.getSalaireDeBase()).isEqualTo(salaire);
        assertThat(developpeur.getExperience()).isEqualTo(experience);
        assertThat(developpeur.getEquipe()).isEqualTo(equipe);
        assertThat(developpeur.getType()).isEqualTo("DEVELOPPEUR");
        assertThat(developpeur.getId()).isNotNull().isNotEmpty();
    }

    @Test
    @DisplayName("Devrait générer un ID unique pour chaque développeur")
    void devraitGenererIdUnique() {
        // Given & When
        Developpeur dev1 = new Developpeur("Alice", 50000, 5, "IT");
        Developpeur dev2 = new Developpeur("Bob", 60000, 3, "IT");

        // Then
        assertThat(dev1.getId()).isNotEqualTo(dev2.getId());
    }

    @Test
    @DisplayName("Devrait créer un développeur avec expérience à 0")
    void devraitCreerDeveloppeurAvecExperienceZero() {
        // Given & When
        Developpeur developpeur = new Developpeur("Junior Dev", 30000, 0, "IT");

        // Then
        assertThat(developpeur.getExperience()).isZero();
    }

    @Test
    @DisplayName("Devrait créer un développeur avec beaucoup d'expérience")
    void devraitCreerDeveloppeurAvecBeaucoupExperience() {
        // Given & When
        Developpeur developpeur = new Developpeur("Senior Dev", 80000, 20, "IT");

        // Then
        assertThat(developpeur.getExperience()).isEqualTo(20);
    }

    @Test
    @DisplayName("Devrait retourner le bon type")
    void devraitRetournerBonType() {
        // Given & When
        Developpeur developpeur = new Developpeur("Dev", 50000, 5, "IT");

        // Then
        assertThat(developpeur.getType()).isEqualTo("DEVELOPPEUR");
    }

    @Test
    @DisplayName("ToString devrait contenir toutes les informations")
    void toStringDevraitContenirToutesLesInformations() {
        // Given
        Developpeur developpeur = new Developpeur("Alice", 50000, 5, "IT");

        // When
        String result = developpeur.toString();

        // Then
        assertThat(result)
                .contains("Alice")
                .contains("DEVELOPPEUR")
                .contains("5 ans")
                .contains("IT");
    }
}