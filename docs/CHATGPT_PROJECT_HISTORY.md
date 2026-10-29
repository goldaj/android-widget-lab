# Historique de projet ChatGPT — Android Widget Lab

Archive de transmission à Codex créée le 2026-10-24, **non exhaustive**. Aucun export intégral des anciens échanges n'est disponible dans cette session.

## Périmètre vérifié dans README.md
Prototype Android historique Java de qualité de l'air WAQI et widget. Outillage Android ancien, encore à moderniser.

## Règles de reprise
Ne jamais inscrire un jeton WAQI ou secret dans Git ; un token compilé dans APK est extractible. Contrôler migration Gradle/SDK et remplacer HTTP par HTTPS après vérification.

## Échanges attestés
- **2026-10-18** — Assistant (résumé reconstruit, non verbatim) : regroupement d'archives d'apprentissage/prototypes dans des dépôts distincts, conservation de la provenance et documentation honnête du niveau de validation.
- **2026-10-18 13:24:13 UTC** — Utilisateur (extrait verbatim) : « N'écrase aucun repo existant. »
- **2026-10-24** — Utilisateur (résumé reconstruit, non verbatim) : demande un AGENTS.md propre au dépôt, un historique lisible par Codex avec messages utilisateur/assistant et horodatages disponibles, ainsi qu'une mise à jour récurrente de la documentation.

## Limites et maintenance
Les transcriptions exactes des échanges précédents et leurs horaires ne sont pas disponibles ici ; aucune réponse de l'assistant n'a été inventée. Lorsque le propriétaire fournit des exports authentiques, conserver la distinction rôle/date/texte et vérifier les informations sensibles avant commit. Ajouter les nouveaux échanges sourcés en fin de document, sans réécrire le passé.

## Intervention 2026-10-29 — réhabilitation du widget AQI (résumé non verbatim)

- Demande : poursuivre le développement de fonctionnalités GitHub existantes, inspecter branches et PR et documenter les reprises. L'utilisateur a exclu trois autres projets pendant la conversation ; pas de lien fonctionnel avec Android Widget Lab.
- État initial constaté sur `main` : une seule branche, aucune PR ouverte ; `DataRetriever` était interrogé avant la fin de l'AsyncTask, la ville du widget n'était pas transmise ni persistée et `WidgetProvider` écrivait « Hello Provider! » puis lançait le réseau après l'affichage.
- Implémentation sur `feat/functional-air-quality-widget-20261029` : modèles/règles AQI, appel HTTPS asynchrone, gestion des erreurs et absence de token, vue et configuration persistantes par ID, rafraîchissement via PendingIntent, activité principale réparée, ressources et manifest adaptés.
- Tests ajoutés : Java pur (validation et qualification AQI) et structure Android XML, CI hors ligne. **Ne couvrent pas le build APK ni un appareil réel.** L'ancien outillage (AGP 2.2.3/SDK25 et Gradle Wrapper incomplet) demeure. Tester le résultat de la CI réelle avant d'affirmer qu'elle passe.
- Aucune clé/token, aucun appel WAQI réel et aucun build/install Android exécutés. Risque du token extractible dans BuildConfig documenté.
- Codex : lire `docs/AQI_FUNCTIONAL_SLICE.md`, vérifier les checks, migrer l'outillage dans une PR distincte, tester sur smartphone/émulateur et ne distribuer qu'avec un backend qui garde les secrets hors APK.
