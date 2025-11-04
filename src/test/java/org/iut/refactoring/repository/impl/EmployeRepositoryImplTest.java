package org.iut.refactoring.repository.impl;

import org.iut.refactoring.model.Developpeur;
import org.iut.refactoring.model.Employe;
import org.iut.refactoring.model.ChefDeProjet;
import org.iut.refactoring.model.Stagiaire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests unitaires pour EmployeRepositoryImpl.
 */
@DisplayName("Tests du repository des employés")
class EmployeRepositoryImplTest {
    
    private EmployeRepositoryImpl repository;
    private Employe developpeur;
    private Employe chefDeProjet;
    private Employe stagiaire;
    
    @BeforeEach
    void setUp() {
        repository = new EmployeRepositoryImpl();
        developpeur = new Developpeur("Alice", 50000, 5, "IT");
        chefDeProjet = new ChefDeProjet("Bob", 60000, 8, "RH");
        stagiaire = new Stagiaire("Charlie", 20000, 0, "IT");
    }
    
    @Test
    @DisplayName("Devrait ajouter un employé")
    void devraitAjouterEmploye() {
        // When
        repository.ajouter(developpeur);
        
        // Then
        Optional<Employe> result = repository.trouverParId(developpeur.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(developpeur);
    }
    
    @Test
    @DisplayName("Devrait lever une exception si on ajoute un employé null")
    void devraitLeverExceptionSiEmployeNull() {
        // When & Then
        assertThatThrownBy(() -> repository.ajouter(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("ne peut pas être null");
    }
    
    @Test
    @DisplayName("Devrait trouver un employé par son ID")
    void devraitTrouverEmployeParId() {
        // Given
        repository.ajouter(developpeur);
        
        // When
        Optional<Employe> result = repository.trouverParId(developpeur.getId());
        
        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getNom()).isEqualTo("Alice");
    }
    
    @Test
    @DisplayName("Devrait retourner Optional.empty() si l'employé n'existe pas")
    void devraitRetournerEmptySiEmployeInexistant() {
        // When
        Optional<Employe> result = repository.trouverParId("id-inexistant");
        
        // Then
        assertThat(result).isEmpty();
    }
    
    @Test
    @DisplayName("Devrait retourner tous les employés")
    void devraitRetournerTousLesEmployes() {
        // Given
        repository.ajouter(developpeur);
        repository.ajouter(chefDeProjet);
        repository.ajouter(stagiaire);
        
        // When
        List<Employe> result = repository.trouverTous();
        
        // Then
        assertThat(result)
            .hasSize(3)
            .containsExactlyInAnyOrder(developpeur, chefDeProjet, stagiaire);
    }
    
    @Test
    @DisplayName("Devrait retourner une liste vide si aucun employé")
    void devraitRetournerListeVideSiAucunEmploye() {
        // When
        List<Employe> result = repository.trouverTous();
        
        // Then
        assertThat(result).isEmpty();
    }
    
    @Test
    @DisplayName("Devrait trouver les employés par équipe")
    void devraitTrouverEmployesParEquipe() {
        // Given
        repository.ajouter(developpeur);
        repository.ajouter(chefDeProjet);
        repository.ajouter(stagiaire);
        
        // When
        List<Employe> result = repository.trouverParEquipe("IT");
        
        // Then
        assertThat(result)
            .hasSize(2)
            .containsExactlyInAnyOrder(developpeur, stagiaire);
    }
    
    @Test
    @DisplayName("Devrait retourner une liste vide si aucun employé dans l'équipe")
    void devraitRetournerListeVideSiAucunEmployeDansEquipe() {
        // Given
        repository.ajouter(developpeur);
        
        // When
        List<Employe> result = repository.trouverParEquipe("Finance");
        
        // Then
        assertThat(result).isEmpty();
    }
    
    @Test
    @DisplayName("Devrait supprimer un employé")
    void devraitSupprimerEmploye() {
        // Given
        repository.ajouter(developpeur);
        String id = developpeur.getId();
        
        // When
        boolean result = repository.supprimer(id);
        
        // Then
        assertThat(result).isTrue();
        assertThat(repository.trouverParId(id)).isEmpty();
    }
    
    @Test
    @DisplayName("Devrait retourner false si l'employé à supprimer n'existe pas")
    void devraitRetournerFalseSiEmployeInexistant() {
        // When
        boolean result = repository.supprimer("id-inexistant");
        
        // Then
        assertThat(result).isFalse();
    }
    
    @Test
    @DisplayName("Devrait permettre d'ajouter plusieurs employés")
    void devraitAjouterPlusieursEmployes() {
        // When
        repository.ajouter(developpeur);
        repository.ajouter(chefDeProjet);
        repository.ajouter(stagiaire);
        
        // Then
        assertThat(repository.trouverTous()).hasSize(3);
    }
}

