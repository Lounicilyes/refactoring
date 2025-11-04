package org.iut.refactoring.factory;

import org.iut.refactoring.model.ChefDeProjet;
import org.iut.refactoring.model.Developpeur;
import org.iut.refactoring.model.Employe;
import org.iut.refactoring.model.Stagiaire;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests unitaires pour EmployeFactory.
 */
@DisplayName("Tests de la factory d'employés")
class EmployeFactoryTest {
    
    @Test
    @DisplayName("Devrait créer un développeur")
    void devraitCreerDeveloppeur() {
        // When
        Employe employe = EmployeFactory.creerEmploye("DEVELOPPEUR", "Alice", 50000, 5, "IT");
        
        // Then
        assertThat(employe)
            .isInstanceOf(Developpeur.class)
            .extracting(Employe::getNom, Employe::getSalaireDeBase, Employe::getExperience, Employe::getEquipe)
            .containsExactly("Alice", 50000.0, 5, "IT");
    }
    
    @Test
    @DisplayName("Devrait créer un chef de projet")
    void devraitCreerChefDeProjet() {
        // When
        Employe employe = EmployeFactory.creerEmploye("CHEF DE PROJET", "Bob", 60000, 8, "RH");
        
        // Then
        assertThat(employe)
            .isInstanceOf(ChefDeProjet.class)
            .extracting(Employe::getNom, Employe::getSalaireDeBase, Employe::getExperience, Employe::getEquipe)
            .containsExactly("Bob", 60000.0, 8, "RH");
    }
    
    @Test
    @DisplayName("Devrait créer un stagiaire")
    void devraitCreerStagiaire() {
        // When
        Employe employe = EmployeFactory.creerEmploye("STAGIAIRE", "Charlie", 20000, 0, "IT");
        
        // Then
        assertThat(employe)
            .isInstanceOf(Stagiaire.class)
            .extracting(Employe::getNom, Employe::getSalaireDeBase, Employe::getExperience, Employe::getEquipe)
            .containsExactly("Charlie", 20000.0, 0, "IT");
    }
    
    @Test
    @DisplayName("Devrait être insensible à la casse du type")
    void devraitEtreInsensibleCasseType() {
        // When
        Employe dev1 = EmployeFactory.creerEmploye("developpeur", "Alice", 50000, 5, "IT");
        Employe dev2 = EmployeFactory.creerEmploye("DEVELOPPEUR", "Bob", 50000, 5, "IT");
        Employe dev3 = EmployeFactory.creerEmploye("DevelopPEur", "Charlie", 50000, 5, "IT");
        
        // Then
        assertThat(dev1).isInstanceOf(Developpeur.class);
        assertThat(dev2).isInstanceOf(Developpeur.class);
        assertThat(dev3).isInstanceOf(Developpeur.class);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"TYPE_INVALIDE", "Manager", "Directeur", "", "   "})
    @DisplayName("Devrait lever une exception pour un type invalide")
    void devraitLeverExceptionPourTypeInvalide(String typeInvalide) {
        // When & Then
        assertThatThrownBy(() -> EmployeFactory.creerEmploye(typeInvalide, "Alice", 50000, 5, "IT"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Type d'employé non reconnu");
    }
    
    @Test
    @DisplayName("Devrait lever une exception si le type est null")
    void devraitLeverExceptionSiTypeNull() {
        // When & Then
        assertThatThrownBy(() -> EmployeFactory.creerEmploye(null, "Alice", 50000, 5, "IT"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("ne peut pas être null");
    }
    
    @Test
    @DisplayName("Devrait créer des employés avec différents salaires")
    void devraitCreerEmployesAvecDifferentsSalaires() {
        // When
        Employe emp1 = EmployeFactory.creerEmploye("DEVELOPPEUR", "Alice", 30000, 1, "IT");
        Employe emp2 = EmployeFactory.creerEmploye("DEVELOPPEUR", "Bob", 80000, 15, "IT");
        
        // Then
        assertThat(emp1.getSalaireDeBase()).isEqualTo(30000.0);
        assertThat(emp2.getSalaireDeBase()).isEqualTo(80000.0);
    }
    
    @Test
    @DisplayName("Devrait créer des employés avec différentes expériences")
    void devraitCreerEmployesAvecDifferentesExperiences() {
        // When
        Employe emp1 = EmployeFactory.creerEmploye("DEVELOPPEUR", "Junior", 40000, 0, "IT");
        Employe emp2 = EmployeFactory.creerEmploye("DEVELOPPEUR", "Senior", 80000, 20, "IT");
        
        // Then
        assertThat(emp1.getExperience()).isEqualTo(0);
        assertThat(emp2.getExperience()).isEqualTo(20);
    }
    
    @Test
    @DisplayName("Devrait créer des employés dans différentes équipes")
    void devraitCreerEmployesDansDifferentesEquipes() {
        // When
        Employe emp1 = EmployeFactory.creerEmploye("DEVELOPPEUR", "Alice", 50000, 5, "IT");
        Employe emp2 = EmployeFactory.creerEmploye("DEVELOPPEUR", "Bob", 50000, 5, "RH");
        Employe emp3 = EmployeFactory.creerEmploye("DEVELOPPEUR", "Charlie", 50000, 5, "Finance");
        
        // Then
        assertThat(emp1.getEquipe()).isEqualTo("IT");
        assertThat(emp2.getEquipe()).isEqualTo("RH");
        assertThat(emp3.getEquipe()).isEqualTo("Finance");
    }
    
    @Test
    @DisplayName("Devrait générer des IDs uniques pour chaque employé créé")
    void devraitGenererIdsUniquesPourChaqueEmploye() {
        // When
        Employe emp1 = EmployeFactory.creerEmploye("DEVELOPPEUR", "Alice", 50000, 5, "IT");
        Employe emp2 = EmployeFactory.creerEmploye("DEVELOPPEUR", "Alice", 50000, 5, "IT");
        
        // Then
        assertThat(emp1.getId()).isNotEqualTo(emp2.getId());
    }
}

