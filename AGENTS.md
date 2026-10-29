# Agent instructions — Android Widget Lab

**Lire README.md puis docs/CHATGPT_PROJECT_HISTORY.md avant chaque changement.**

Projet : Prototype Android historique Java de qualité de l'air WAQI et widget. Outillage Android ancien, encore à moderniser.

Règles : Ne jamais inscrire un jeton WAQI ou secret dans Git ; un token compilé dans APK est extractible. Contrôler migration Gradle/SDK et remplacer HTTP par HTTPS après vérification.

Préserver la provenance et ne jamais écraser les versions existantes. Documenter ce qui a réellement été testé. Après chaque modification, enrichir docs/CHATGPT_PROJECT_HISTORY.md si une instruction, décision ou nouveau dialogue pertinent est disponible ; séparer citations exactes des résumés et noter les dates seulement si certaines. Ne publier ni secrets ni données privées.

## Fonctionnalités AQI 2026-10-29 — reprise

Lire impérativement `docs/AQI_FUNCTIONAL_SLICE.md`. Le chantier actuel est isolé dans `feat/functional-air-quality-widget-20261029` (base `main`, aucune PR concurrente détectée lors de l'inspection).

- `AirQualityRules` (Java pur) : validation des villes et seuils AQI ; `AirQualityData` : résultats réels, démo sans chiffres inventés, erreur.
- `AirQualityClient` : WAQI en HTTPS uniquement, limites réseau, parsing JSON et absence de logs sensibles.
- `CitySettings` : ville par ID de widget ; `WidgetActivity` : confirmer ville/ID puis retourner `RESULT_OK` ; `WidgetProvider` : appel asynchrone puis rendu, clic d'actualisation, rejet des réponses périmées et suppression des préférences.
- `MainActivity` attend le callback avant d'ouvrir la boîte de dialogue (plus de lecture trop précoce).
- Tests CI uniquement sur règles Java pures + structure XML (sans compilation Android complète) : `widget-contract.yml`. Ne jamais présenter ces tests comme un APK validé.
- Prochain chantier principal : migration du build Android/Gradle, tests sur appareil et gestion d'un backend secret / quotas. Ne pas activer un token réel dans un workflow public ni publier un APK avec token dans `BuildConfig`.
- Maintenir `docs/CHATGPT_PROJECT_HISTORY.md` et le guide lors d'une reprise ; ne pas merger automatiquement sans review.

## Reprise du 29 octobre 2026

Branche `feat/functional-air-quality-widget-20261029` : parcours widget et client WAQI réécrits, tests Java/XML hors ligne, détails dans `docs/AQI_FUNCTIONAL_SLICE.md`. Ne pas prétendre qu'un APK est compilable sans migration SDK/Gradle. L'utilisateur a demandé d'exclure ce dépôt des améliorations ultérieures dans cette conversation dès la fin de cette livraison.
