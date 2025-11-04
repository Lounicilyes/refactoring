package org.iut.refactoring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests d'intégration pour l'application GestionApp.
 */
@DisplayName("Tests d'intégration de l'application")
class GestionAppIntegrationTest {
    
    @Test
    @DisplayName("Devrait exécuter l'application sans erreur")
    void devraitExecuterApplicationSansErreur() {
        // Given
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        try {
            // When & Then
            assertThatCode(() -> GestionApp.main(new String[]{}))
                .doesNotThrowAnyException();
            
            String output = outputStream.toString();
            assertThat(output)
                .contains("SYSTÈME DE GESTION DU PERSONNEL")
                .contains("AJOUT D'EMPLOYÉS")
                .contains("CALCUL DE SALAIRE ET BONUS")
                .contains("RAPPORTS")
                .contains("PROMOTION");
        } finally {
            System.setOut(originalOut);
        }
    }
}


