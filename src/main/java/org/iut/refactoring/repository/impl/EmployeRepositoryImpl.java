package org.iut.refactoring.repository.impl;

import org.iut.refactoring.model.Employe;
import org.iut.refactoring.repository.EmployeRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implémentation en mémoire du repository des employés.
 * Principe SRP : Responsabilité unique de gérer la persistance des employés.
 * Principe DIP : Implémentation concrète de l'abstraction EmployeRepository.
 */
public class EmployeRepositoryImpl implements EmployeRepository {
    
    private final Map<String, Employe> employes;
    
    public EmployeRepositoryImpl() {
        this.employes = new HashMap<>();
    }
    
    @Override
    public void ajouter(Employe employe) {
        if (employe == null) {
            throw new IllegalArgumentException("L'employé ne peut pas être null");
        }
        employes.put(employe.getId(), employe);
    }
    
    @Override
    public Optional<Employe> trouverParId(String id) {
        return Optional.ofNullable(employes.get(id));
    }
    
    @Override
    public List<Employe> trouverTous() {
        return new ArrayList<>(employes.values());
    }
    
    @Override
    public List<Employe> trouverParEquipe(String equipe) {
        return employes.values().stream()
                .filter(emp -> emp.getEquipe().equals(equipe))
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean supprimer(String id) {
        return employes.remove(id) != null;
    }
}

