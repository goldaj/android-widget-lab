# Audit de passation GitHub — 30 octobre 2026

> Inventaire vérifié sur GitHub. Cette passe est un audit de références et de PR, pas une exécution des tests ni une autorisation de déploiement. Ne jamais supprimer une branche par intuition.

## Références

- Dépôt : `goldaj/android-widget-lab`.
- Branche par défaut : `main`.
- Branches (2) :
  - `feat/functional-air-quality-widget-20261029`
  - `main` — référence principale
- PR ouvertes : aucune.

## Contre-audit et passation

1. Avant toute suppression, comparer chaque branche à la branche cible par SHA et par `ahead_by` / `behind_by`; vérifier aussi les PR clôturées et les références de déploiement.
2. Ne pas confondre « PR clôturée » et « commits intégrés ». Contrôler le graphe Git et les changements fonctionnels.
3. Rejouer tests, build et CI applicables avant une fusion de code ; ne pas modifier la branche de production sans validation.
4. Les branches de fonctionnalités historiques peuvent rester utiles pour reproduire une version ou retrouver des assets. Décider individuellement.

**Statut :** inventaire et PR ouvertes contrôlés ; revue exhaustive des diffs, tests et droits de suppression restant à réaliser. Ce document n'atteste aucune validation fonctionnelle.
