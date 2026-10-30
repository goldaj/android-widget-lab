# Audit GitHub et passation — 30 octobre 2026

Audit **statique** fondé sur les références GitHub, le README et les PR. Tests non exécutés, code non modifié, aucun comportement de production validé. Lire `AGENTS.md` et `docs/CHATGPT_PROJECT_HISTORY.md` à la reprise.

## Branches (base : `main`)
| Branche | SHA | Comparaison | Consigne |
|---|---|---|---|
| `main` | `2a837db00a23` | Cible courante | Conserver |
| `feat/functional-air-quality-widget-20261029` | `135720e94f13` | `behind`, ahead 0, behind 3 | Entièrement ancêtre : candidate à l'archivage de branche, pas un travail à fusionner |

## PR historiques
- [#1](https://github.com/goldaj/android-widget-lab/pull/1) : fusionnée, `feat/functional-air-quality-widget-20261029` → `main`.

## Analyse critique
Widget WAQI et qualité de l'air. Vérifier configuration par widget ID, cycle de vie Android, réponses asynchrones tardives, affichage d'état erreur et saisie ville ; l'usage d'une clé WAQI côté APK est exposable. Aucun build Android ni test sur appareil réalisé ici.

## Priorités pour le prochain agent
1. Lire docs/AQI_FUNCTIONAL_SLICE.md et le journal de conversation.
2. Compiler réellement le projet avec un SDK/Gradle compatible, lancer sur appareil/émulateur et tester réseau absent/quotas et multi-widgets.
3. Ne jamais embarquer un token réel dans une application publique : définir une architecture sûre et vérifier les CI avant diffusion.

## Prudence Git et test
Aucune suppression de branche ni changement de code exécuté. Contrôler références externes/CI avant tout nettoyage et réactualiser les comparaisons : elles sont datées du 30/10/2026. Distinguer `ahead=0` (aucun commit exclusif) et validation de fonctionnement (non prouvée). Source documentaire : `README.md` (« # Laboratoire Android — widget de qualité de l’air ») et `AGENTS.md`.
