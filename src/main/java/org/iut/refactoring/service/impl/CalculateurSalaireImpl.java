package org.iut.refactoring.service.impl;

import org.iut.refactoring.model.Employe;
import org.iut.refactoring.service.CalculateurSalaire;
import org.iut.refactoring.service.StrategieSalaire;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du calculateur de salaire utilisant des stratégies.
 * Principe SRP : Responsabilité unique de coordonner le calcul de salaire.
 * Principe OCP : Ouvert à l'extension (nouvelles stratégies) sans modification.
 * Principe DIP : Dépend de l'abstraction StrategieSalaire.
 */
public class CalculateurSalaireImpl implements CalculateurSalaire {
    
    private final List<StrategieSalaire> strategies;
    
    public CalculateurSalaireImpl() {
        this.strategies = new ArrayList<>();
        // Enregistrement des stratégies
        strategies.add(new StrategieSalaireDeveloppeur());
        strategies.add(new StrategieSalaireChefDeProjet());
        strategies.add(new StrategieSalaireStagiaire());
    }
    
    /**
     * Constructeur pour injection de dépendances (DIP).
     * @param strategies Liste des stratégies de salaire
     */
    public CalculateurSalaireImpl(List<StrategieSalaire> strategies) {
        this.strategies = strategies;
    }
    
    @Override
    public double calculerSalaire(Employe employe) {
        return trouverStrategie(employe).calculer(employe);
    }
    
    @Override
    public double calculerBonus(Employe employe) {
        return trouverStrategie(employe).calculerBonus(employe);
    }
    
    private StrategieSalaire trouverStrategie(Employe employe) {
        return strategies.stream()
                .filter(strategie -> strategie.estApplicable(employe))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aucune stratégie trouvée pour le type d'employé: " + employe.getType()));
    }
}

