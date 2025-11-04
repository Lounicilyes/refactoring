package org.iut.refactoring.service.impl;

import org.iut.refactoring.service.ServiceLog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du service de logs.
 * Principe SRP : Responsabilité unique de gérer les logs.
 */
public class ServiceLogImpl implements ServiceLog {
    
    private final List<String> logs;
    private final DateTimeFormatter formatter;
    
    public ServiceLogImpl() {
        this.logs = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Override
    public void enregistrer(String message) {
        String logEntry = LocalDateTime.now().format(formatter) + " - " + message;
        logs.add(logEntry);
    }
    
    @Override
    public List<String> obtenirLogs() {
        return new ArrayList<>(logs);
    }
    
    @Override
    public void afficherLogs() {
        System.out.println("=== LOGS ===");
        logs.forEach(System.out::println);
    }
}

