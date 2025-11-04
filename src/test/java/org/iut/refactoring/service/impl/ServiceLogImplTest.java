package org.iut.refactoring.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests unitaires pour ServiceLogImpl.
 */
@DisplayName("Tests du service de logs")
class ServiceLogImplTest {
    
    private ServiceLogImpl serviceLog;
    
    @BeforeEach
    void setUp() {
        serviceLog = new ServiceLogImpl();
    }
    
    @Test
    @DisplayName("Devrait enregistrer un message de log")
    void devraitEnregistrerMessageLog() {
        // When
        serviceLog.enregistrer("Test message");
        
        // Then
        List<String> logs = serviceLog.obtenirLogs();
        assertThat(logs).hasSize(1);
        assertThat(logs.get(0)).contains("Test message");
    }
    
    @Test
    @DisplayName("Devrait ajouter la date et l'heure au log")
    void devraitAjouterDateHeureAuLog() {
        // When
        serviceLog.enregistrer("Test message");
        
        // Then
        List<String> logs = serviceLog.obtenirLogs();
        String log = logs.get(0);
        assertThat(log).matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} - Test message");
    }
    
    @Test
    @DisplayName("Devrait enregistrer plusieurs messages")
    void devraitEnregistrerPlusieursMessages() {
        // When
        serviceLog.enregistrer("Message 1");
        serviceLog.enregistrer("Message 2");
        serviceLog.enregistrer("Message 3");
        
        // Then
        List<String> logs = serviceLog.obtenirLogs();
        assertThat(logs).hasSize(3);
    }
    
    @Test
    @DisplayName("Devrait retourner les logs dans l'ordre d'ajout")
    void devraitRetournerLogsDansOrdreAjout() {
        // When
        serviceLog.enregistrer("Premier");
        serviceLog.enregistrer("Deuxième");
        serviceLog.enregistrer("Troisième");
        
        // Then
        List<String> logs = serviceLog.obtenirLogs();
        assertThat(logs.get(0)).contains("Premier");
        assertThat(logs.get(1)).contains("Deuxième");
        assertThat(logs.get(2)).contains("Troisième");
    }
    
    @Test
    @DisplayName("Devrait retourner une liste vide au début")
    void devraitRetournerListeVideAuDebut() {
        // When
        List<String> logs = serviceLog.obtenirLogs();
        
        // Then
        assertThat(logs).isEmpty();
    }
    
    @Test
    @DisplayName("Devrait retourner une copie des logs (immutabilité)")
    void devraitRetournerCopieLogs() {
        // Given
        serviceLog.enregistrer("Message 1");
        
        // When
        List<String> logs1 = serviceLog.obtenirLogs();
        logs1.add("Message externe"); // Modification de la liste retournée
        List<String> logs2 = serviceLog.obtenirLogs();
        
        // Then
        assertThat(logs2).hasSize(1); // La modification externe n'affecte pas les logs internes
    }
    
    @Test
    @DisplayName("Devrait gérer les messages vides")
    void devraitGererMessagesVides() {
        // When
        serviceLog.enregistrer("");
        
        // Then
        List<String> logs = serviceLog.obtenirLogs();
        assertThat(logs).hasSize(1);
        assertThat(logs.get(0)).contains(" - ");
    }
    
    @Test
    @DisplayName("Devrait gérer les messages avec caractères spéciaux")
    void devraitGererMessagesAvecCaracteresSpeciaux() {
        // When
        serviceLog.enregistrer("Message avec @#$% & caractères spéciaux !");
        
        // Then
        List<String> logs = serviceLog.obtenirLogs();
        assertThat(logs.get(0)).contains("@#$% & caractères spéciaux !");
    }
    
    @Test
    @DisplayName("afficherLogs ne devrait pas lever d'exception")
    void afficherLogsNeDevraitPasLeverException() {
        // Given
        serviceLog.enregistrer("Test");
        
        // When & Then
        assertThatCode(() -> serviceLog.afficherLogs()).doesNotThrowAnyException();
    }
}

