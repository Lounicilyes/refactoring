package org.iut.refactoring.service.impl;

import org.iut.refactoring.model.Developpeur;
import org.iut.refactoring.model.ChefDeProjet;
import org.iut.refactoring.model.Stagiaire;
import org.iut.refactoring.model.Employe;
import org.iut.refactoring.service.CalculateurSalaire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour ServiceRapportImpl.
 */
@DisplayName("Tests du service de rapports")
class ServiceRapportImplTest {
    
    @Mock
    private CalculateurSalaire calculateurSalaire;
    
    private ServiceRapportImpl serviceRapport;
    private List<Employe> employes;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceRapport = new ServiceRapportImpl(calculateurSalaire);
        
        employes = Arrays.asList(
            new Developpeur("Alice", 50000, 5, "IT"),
            new ChefDeProjet("Bob", 60000, 8, "RH"),
            new Stagiaire("Charlie", 20000, 0, "IT"),
            new Developpeur("Diana", 55000, 10, "IT")
        );
        
        // Rediriger System.out pour tester les affichages
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }
    
    @BeforeEach
    void tearDown() {
        System.setOut(originalOut);
    }
    
    @Test
    @DisplayName("Devrait générer un rapport de salaires pour tous les employés")
    void devraitGenererRapportSalairesPourTous() {
        // Given
        when(calculateurSalaire.calculerSalaire(any())).thenReturn(60000.0);
        
        // When
        serviceRapport.genererRapportSalaires(employes, null);
        
        // Then
        verify(calculateurSalaire, times(4)).calculerSalaire(any());
        String output = outputStream.toString();
        assertThat(output).contains("=== RAPPORT: SALAIRE ===");
    }
    
    @Test
    @DisplayName("Devrait générer un rapport de salaires filtré par équipe")
    void devraitGenererRapportSalairesFiltreParEquipe() {
        // Given
        when(calculateurSalaire.calculerSalaire(any())).thenReturn(60000.0);
        
        // When
        serviceRapport.genererRapportSalaires(employes, "IT");
        
        // Then
        verify(calculateurSalaire, times(3)).calculerSalaire(any()); // 3 employés IT
    }
    
    @Test
    @DisplayName("Devrait générer un rapport de salaires avec filtre vide")
    void devraitGenererRapportSalairesAvecFiltreVide() {
        // Given
        when(calculateurSalaire.calculerSalaire(any())).thenReturn(60000.0);
        
        // When
        serviceRapport.genererRapportSalaires(employes, "");
        
        // Then
        verify(calculateurSalaire, times(4)).calculerSalaire(any());
    }
    
    @Test
    @DisplayName("Devrait générer un rapport d'expérience pour tous")
    void devraitGenererRapportExperiencePourTous() {
        // When
        serviceRapport.genererRapportExperience(employes, null);
        
        // Then
        String output = outputStream.toString();
        assertThat(output)
            .contains("=== RAPPORT: EXPERIENCE ===")
            .contains("Alice")
            .contains("5 années")
            .contains("Bob")
            .contains("8 années");
    }
    
    @Test
    @DisplayName("Devrait générer un rapport d'expérience filtré par équipe")
    void devraitGenererRapportExperienceFiltreParEquipe() {
        // When
        serviceRapport.genererRapportExperience(employes, "IT");
        
        // Then
        String output = outputStream.toString();
        assertThat(output)
            .contains("Alice")
            .contains("Charlie")
            .contains("Diana")
            .doesNotContain("Bob"); // Bob est dans RH
    }
    
    @Test
    @DisplayName("Devrait générer un rapport d'expérience avec filtre vide")
    void devraitGenererRapportExperienceAvecFiltreVide() {
        // When
        serviceRapport.genererRapportExperience(employes, "");

        // Then
        String output = outputStream.toString();
        assertThat(output)
            .contains("=== RAPPORT: EXPERIENCE ===")
            .contains("Alice")
            .contains("Bob")
            .contains("Charlie")
            .contains("Diana");
    }

    @Test
    @DisplayName("Devrait générer un rapport par division")
    void devraitGenererRapportDivision() {
        // When
        serviceRapport.genererRapportDivision(employes);
        
        // Then
        String output = outputStream.toString();
        assertThat(output)
            .contains("=== RAPPORT: DIVISION ===")
            .contains("IT: 3 employés")
            .contains("RH: 1 employés");
    }
    
    @Test
    @DisplayName("Devrait gérer une liste vide pour le rapport de salaires")
    void devraitGererListeVidePourRapportSalaires() {
        // When
        serviceRapport.genererRapportSalaires(Arrays.asList(), null);
        
        // Then
        verify(calculateurSalaire, never()).calculerSalaire(any());
        String output = outputStream.toString();
        assertThat(output).contains("=== RAPPORT: SALAIRE ===");
    }
    
    @Test
    @DisplayName("Devrait gérer une liste vide pour le rapport de division")
    void devraitGererListeVidePourRapportDivision() {
        // When
        serviceRapport.genererRapportDivision(Arrays.asList());
        
        // Then
        String output = outputStream.toString();
        assertThat(output).contains("=== RAPPORT: DIVISION ===");
    }
    
    @Test
    @DisplayName("Devrait filtrer correctement même si aucun employé ne correspond")
    void devraitFiltrerCorrectementMemeAucunEmployeCorrespond() {
        // Given
        when(calculateurSalaire.calculerSalaire(any())).thenReturn(60000.0);
        
        // When
        serviceRapport.genererRapportSalaires(employes, "Finance");
        
        // Then
        verify(calculateurSalaire, never()).calculerSalaire(any());
    }
    
    @Test
    @DisplayName("Devrait calculer les salaires pour chaque employé dans le rapport")
    void devraitCalculerSalairesPourChaqueEmploye() {
        // Given
        when(calculateurSalaire.calculerSalaire(any()))
            .thenReturn(60000.0, 90000.0, 12000.0, 80000.0);
        
        // When
        serviceRapport.genererRapportSalaires(employes, null);
        
        // Then
        String output = outputStream.toString();
        assertThat(output)
            .contains("60000")
            .contains("90000")
            .contains("12000")
            .contains("80000");
    }
}


