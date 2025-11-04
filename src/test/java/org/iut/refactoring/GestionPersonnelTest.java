package org.iut.refactoring;

import org.iut.refactoring.model.Employe;
import org.iut.refactoring.repository.EmployeRepository;
import org.iut.refactoring.service.ServiceGestionPersonnel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour la classe facade GestionPersonnel.
 */
@DisplayName("Tests de la facade GestionPersonnel")
class GestionPersonnelTest {
    
    @Mock
    private ServiceGestionPersonnel serviceGestionPersonnel;
    
    @Mock
    private EmployeRepository employeRepository;
    
    @Mock
    private Employe employe;
    
    private GestionPersonnel gestionPersonnel;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gestionPersonnel = new GestionPersonnel(serviceGestionPersonnel, employeRepository);
    }
    
    @Test
    @DisplayName("Devrait créer une instance avec le constructeur par défaut")
    void devraitCreerInstanceAvecConstructeurParDefaut() {
        // When
        GestionPersonnel gp = new GestionPersonnel();
        
        // Then
        assertThat(gp).isNotNull();
    }
    
    @Test
    @DisplayName("Devrait ajouter un salarié")
    void devraitAjouterSalarie() {
        // Given
        when(serviceGestionPersonnel.ajouterEmploye(anyString(), anyString(), anyDouble(), anyInt(), anyString()))
            .thenReturn(employe);
        
        // When
        gestionPersonnel.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 5, "IT");
        
        // Then
        verify(serviceGestionPersonnel).ajouterEmploye("DEVELOPPEUR", "Alice", 50000.0, 5, "IT");
    }
    
    @Test
    @DisplayName("Devrait calculer le salaire d'un employé")
    void devraitCalculerSalaire() {
        // Given
        when(serviceGestionPersonnel.calculerSalaire(anyString())).thenReturn(60000.0);
        
        // When
        double salaire = gestionPersonnel.calculSalaire("id-123");
        
        // Then
        assertThat(salaire).isCloseTo(60000.0, within(0.01));
        verify(serviceGestionPersonnel).calculerSalaire("id-123");
    }
    
    @Test
    @DisplayName("Devrait retourner 0 si l'employé n'existe pas lors du calcul de salaire")
    void devraitRetournerZeroSiEmployeInexistantPourSalaire() {
        // Given
        when(serviceGestionPersonnel.calculerSalaire(anyString()))
            .thenThrow(new IllegalArgumentException("Employé non trouvé"));
        
        // When
        double salaire = gestionPersonnel.calculSalaire("id-inexistant");
        
        // Then
        assertThat(salaire).isZero();
    }
    
    @Test
    @DisplayName("Devrait calculer le bonus annuel d'un employé")
    void devraitCalculerBonusAnnuel() {
        // Given
        when(serviceGestionPersonnel.calculerBonusAnnuel(anyString())).thenReturn(5000.0);
        
        // When
        double bonus = gestionPersonnel.calculBonusAnnuel("id-123");
        
        // Then
        assertThat(bonus).isCloseTo(5000.0, within(0.01));
        verify(serviceGestionPersonnel).calculerBonusAnnuel("id-123");
    }
    
    @Test
    @DisplayName("Devrait retourner 0 si l'employé n'existe pas lors du calcul de bonus")
    void devraitRetournerZeroSiEmployeInexistantPourBonus() {
        // Given
        when(serviceGestionPersonnel.calculerBonusAnnuel(anyString()))
            .thenThrow(new IllegalArgumentException("Employé non trouvé"));
        
        // When
        double bonus = gestionPersonnel.calculBonusAnnuel("id-inexistant");
        
        // Then
        assertThat(bonus).isZero();
    }
    
    @Test
    @DisplayName("Devrait générer un rapport")
    void devraitGenererRapport() {
        // When
        gestionPersonnel.generationRapport("SALAIRE", "IT");
        
        // Then
        verify(serviceGestionPersonnel).genererRapport("SALAIRE", "IT");
    }
    
    @Test
    @DisplayName("Devrait générer un rapport sans filtre")
    void devraitGenererRapportSansFiltre() {
        // When
        gestionPersonnel.generationRapport("EXPERIENCE", null);
        
        // Then
        verify(serviceGestionPersonnel).genererRapport("EXPERIENCE", null);
    }
    
    @Test
    @DisplayName("Devrait promouvoir un employé")
    void devraitPromouvoirEmploye() {
        // When
        gestionPersonnel.avancementEmploye("id-123", "CHEF DE PROJET");
        
        // Then
        verify(serviceGestionPersonnel).promouvoirEmploye("id-123", "CHEF DE PROJET");
    }
    
    @Test
    @DisplayName("Ne devrait pas lever d'exception si la promotion échoue")
    void neDevraitPasLeverExceptionSiPromotionEchoue() {
        // Given
        doThrow(new IllegalArgumentException("Employé non trouvé"))
            .when(serviceGestionPersonnel).promouvoirEmploye(anyString(), anyString());
        
        // When & Then
        assertThatCode(() -> gestionPersonnel.avancementEmploye("id-inexistant", "CHEF DE PROJET"))
            .doesNotThrowAnyException();
    }
    
    @Test
    @DisplayName("Devrait récupérer les employés par division")
    void devraitRecupererEmployesParDivision() {
        // Given
        List<Employe> employes = Arrays.asList(employe);
        when(employeRepository.trouverParEquipe("IT")).thenReturn(employes);
        
        // When
        List<Employe> result = gestionPersonnel.getEmployesParDivision("IT");
        
        // Then
        assertThat(result).hasSize(1).contains(employe);
        verify(employeRepository).trouverParEquipe("IT");
    }
    
    @Test
    @DisplayName("Devrait retourner une liste vide si aucun employé dans la division")
    void devraitRetournerListeVideSiAucunEmployeDansDivision() {
        // Given
        when(employeRepository.trouverParEquipe("Finance")).thenReturn(Arrays.asList());
        
        // When
        List<Employe> result = gestionPersonnel.getEmployesParDivision("Finance");
        
        // Then
        assertThat(result).isEmpty();
    }
    
    @Test
    @DisplayName("Devrait afficher les logs")
    void devraitAfficherLogs() {
        // When
        gestionPersonnel.printLogs();
        
        // Then
        verify(serviceGestionPersonnel).afficherLogs();
    }
}

