package org.iut.refactoring.service.impl;

import org.iut.refactoring.factory.EmployeFactory;
import org.iut.refactoring.model.ChefDeProjet;
import org.iut.refactoring.model.Developpeur;
import org.iut.refactoring.model.Employe;
import org.iut.refactoring.model.Stagiaire;
import org.iut.refactoring.repository.EmployeRepository;
import org.iut.refactoring.service.CalculateurSalaire;
import org.iut.refactoring.service.ServiceLog;
import org.iut.refactoring.service.ServiceRapport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour ServiceGestionPersonnelImpl.
 */
@DisplayName("Tests du service de gestion du personnel")
class ServiceGestionPersonnelImplTest {

    @Mock
    private EmployeRepository employeRepository;

    @Mock
    private CalculateurSalaire calculateurSalaire;

    @Mock
    private ServiceRapport serviceRapport;

    @Mock
    private ServiceLog serviceLog;

    private ServiceGestionPersonnelImpl service;
    private Employe developpeur;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new ServiceGestionPersonnelImpl(
                employeRepository,
                calculateurSalaire,
                serviceRapport,
                serviceLog
        );
        developpeur = new Developpeur("Alice", 50000, 5, "IT");
    }

    @Test
    @DisplayName("Devrait ajouter un employé et enregistrer un log")
    void devraitAjouterEmployeEtEnregistrerLog() {
        // When
        Employe result = service.ajouterEmploye("DEVELOPPEUR", "Alice", 50000, 5, "IT");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Alice");
        verify(employeRepository).ajouter(any(Employe.class));
        verify(serviceLog).enregistrer(contains("Alice"));
    }

    @Test
    @DisplayName("Devrait ajouter un chef de projet")
    void devraitAjouterChefDeProjet() {
        // When
        Employe result = service.ajouterEmploye("CHEF DE PROJET", "Bob", 60000, 8, "RH");

        // Then
        assertThat(result).isInstanceOf(ChefDeProjet.class);
        assertThat(result.getType()).isEqualTo("CHEF DE PROJET");
        verify(employeRepository).ajouter(any(ChefDeProjet.class));
    }

    @Test
    @DisplayName("Devrait ajouter un stagiaire")
    void devraitAjouterStagiaire() {
        // When
        Employe result = service.ajouterEmploye("STAGIAIRE", "Charlie", 20000, 0, "IT");

        // Then
        assertThat(result).isInstanceOf(Stagiaire.class);
        verify(employeRepository).ajouter(any(Stagiaire.class));
    }

    @Test
    @DisplayName("Devrait calculer le salaire d'un employé existant")
    void devraitCalculerSalaireEmployeExistant() {
        // Given
        when(employeRepository.trouverParId(anyString())).thenReturn(Optional.of(developpeur));
        when(calculateurSalaire.calculerSalaire(any())).thenReturn(60000.0);

        // When
        double salaire = service.calculerSalaire("id-123");

        // Then
        assertThat(salaire).isCloseTo(60000.0, within(0.01));
        verify(employeRepository).trouverParId("id-123");
        verify(calculateurSalaire).calculerSalaire(developpeur);
    }

    @Test
    @DisplayName("Devrait lever une exception si l'employé n'existe pas lors du calcul de salaire")
    void devraitLeverExceptionSiEmployeInexistantPourCalculSalaire() {
        // Given
        when(employeRepository.trouverParId(anyString())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> service.calculerSalaire("id-inexistant"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ERREUR: impossible de trouver l'employé avec l'ID:");
    }

    @Test
    @DisplayName("Devrait calculer le bonus annuel d'un employé")
    void devraitCalculerBonusAnnuel() {
        // Given
        when(employeRepository.trouverParId(anyString())).thenReturn(Optional.of(developpeur));
        when(calculateurSalaire.calculerBonus(any())).thenReturn(5000.0);

        // When
        double bonus = service.calculerBonusAnnuel("id-123");

        // Then
        assertThat(bonus).isCloseTo(5000.0, within(0.01));
        verify(calculateurSalaire).calculerBonus(developpeur);
    }

    @Test
    @DisplayName("Devrait lever une exception si l'employé n'existe pas lors du calcul de bonus")
    void devraitLeverExceptionSiEmployeInexistantPourCalculBonus() {
        // Given
        when(employeRepository.trouverParId(anyString())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> service.calculerBonusAnnuel("id-inexistant"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ERREUR: impossible de trouver l'employé avec l'ID:");
    }

    @Test
    @DisplayName("Devrait générer un rapport de salaires")
    void devraitGenererRapportSalaires() {
        // When
        service.genererRapport("SALAIRE", "IT");

        // Then
        verify(serviceRapport).genererRapportSalaires(anyList(), eq("IT"));
        verify(serviceLog).enregistrer(contains("SALAIRE"));
    }

    @Test
    @DisplayName("Devrait générer un rapport d'expérience")
    void devraitGenererRapportExperience() {
        // When
        service.genererRapport("EXPERIENCE", null);

        // Then
        verify(serviceRapport).genererRapportExperience(anyList(), isNull());
        verify(serviceLog).enregistrer(contains("EXPERIENCE"));
    }

    @Test
    @DisplayName("Devrait générer un rapport de division")
    void devraitGenererRapportDivision() {
        // When
        service.genererRapport("DIVISION", null);

        // Then
        verify(serviceRapport).genererRapportDivision(anyList());
        verify(serviceLog).enregistrer(contains("DIVISION"));
    }

    @Test
    @DisplayName("Devrait lever une exception pour un type de rapport invalide")
    void devraitLeverExceptionPourTypeRapportInvalide() {
        // When & Then
        assertThatThrownBy(() -> service.genererRapport("TYPE_INVALIDE", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Type de rapport non reconnu");
    }

    @Test
    @DisplayName("Devrait lever une exception si le type de rapport est null")
    void devraitLeverExceptionSiTypeRapportNull() {
        // When & Then
        assertThatThrownBy(() -> service.genererRapport(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ne peut pas être null");
    }

    @Test
    @DisplayName("Devrait promouvoir un employé")
    void devraitPromouvoirEmploye() {
        // Given
        when(employeRepository.trouverParId(anyString())).thenReturn(Optional.of(developpeur));
        when(employeRepository.supprimer(anyString())).thenReturn(true);

        // When
        service.promouvoirEmploye("id-123", "CHEF DE PROJET");

        // Then
        verify(employeRepository).trouverParId("id-123");
        verify(employeRepository).supprimer("id-123");
        verify(employeRepository).ajouter(any(ChefDeProjet.class));
        verify(serviceLog).enregistrer(contains("Alice"));
    }

    @Test
    @DisplayName("Devrait lever une exception si l'employé à promouvoir n'existe pas")
    void devraitLeverExceptionSiEmployeAPromouvoirInexistant() {
        // Given
        when(employeRepository.trouverParId(anyString())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> service.promouvoirEmploye("id-inexistant", "CHEF DE PROJET"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ERREUR: impossible de trouver l'employé avec l'ID:");
    }

    @Test
    @DisplayName("Devrait conserver les données de l'employé lors de la promotion")
    void devraitConserverDonneesEmployeLorsPromotion() {
        // Given
        Developpeur dev = new Developpeur("Alice", 50000, 5, "IT");
        when(employeRepository.trouverParId(anyString())).thenReturn(Optional.of(dev));

        // When
        service.promouvoirEmploye("id-123", "CHEF DE PROJET");

        // Then
        verify(employeRepository).ajouter(argThat(employe ->
                employe.getNom().equals("Alice") &&
                        employe.getSalaireDeBase() == 50000 &&
                        employe.getExperience() == 5 &&
                        employe.getEquipe().equals("IT") &&
                        employe instanceof ChefDeProjet
        ));
    }

    @Test
    @DisplayName("Devrait afficher les logs")
    void devraitAfficherLogs() {
        // When
        service.afficherLogs();

        // Then
        verify(serviceLog).afficherLogs();
    }
}