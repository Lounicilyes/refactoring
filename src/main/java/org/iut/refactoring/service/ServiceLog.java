package org.iut.refactoring.service;

/**
 * Interface pour le service de logs.
 * Principe ISP : Interface spécifique au logging.
 * Principe SRP : Responsabilité unique de gestion des logs.
 */
public interface ServiceLog {
    /**
     * Enregistre un message de log.
     * @param message Le message à enregistrer
     */
    void enregistrer(String message);
    
    /**
     * Récupère tous les logs.
     * @return Liste des logs
     */
    java.util.List<String> obtenirLogs();
    
    /**
     * Affiche les logs.
     */
    void afficherLogs();
}

