# Audit contradictoire GitHub et passation — goldaj/android-widget-lab

30 octobre 2026. Lecture GitHub des branches, PR, README et instructions AGENTS ; aucune exécution locale de tests Android.

## Branches
Cible `main` : `098e3ccbb580c0c0a793f783c99c2314a2de1a7f`.
- `feat/functional-air-quality-widget-20261029` : statut **behind**, avance 0, retard 1 ; historique absorbé, branche candidate au nettoyage.

## PR
- [#1](https://github.com/goldaj/android-widget-lab/pull/1) fusionnée (closed) : feat: turn legacy air-quality widget into an asynchronous per-city AQI app

## Source revue
Racine : `.github`, `.gitignore`, `AGENTS.md`, `README.md`, `app`, `build.gradle`, `docs`, `gradle.properties`, `gradlew`, `gradlew.bat`, `settings.gradle`, `tests`.

README : # Laboratoire Android — widget de qualité de l’air

Ancien prototype Android personnel en Java : saisie d’une ville, récupération d’un indice de qualité de l’air auprès de WAQI et affichage dans une activité et un widget. Il est distinct du projet de liste de tâches `my_first_android_app`.

État : archive pédagogique avec Android SDK 25 et ancien plugin Gradle. La compilation et l’exécution sur un Android actuel nécessitent une remise à niveau de l’outillage. Aucun APK de production n’est fourni.

## Configuration locale

Le jeton WAQI ne figure dans aucun commit de ce dépôt. Le build lit `android-widget-lab/secrets.properties` sous le dossier désigné par la variable `CODEX_PRIVATE_PROJECT_CONFIG`, ou sous `Documents/Codex-Private` dans le profil utilisateur :

```properties
WAQI_API_TOKEN=
```

La valeur est injectée dans `BuildConfig.WAQI_API_TOKEN`. Le fichier privé reste à l’extérieur du dépôt ; `secrets.properties`, clés de signature et fichiers `.env` sont aussi ignorés.

Le token d’origine a été préservé localement lors de l’import, avec les fichiers originaux. Pour distribuer l’application, remplacer l’appel direct par un service intermédiaire : un token compilé dans un APK est extractible. Remplacer également les ancienne

Consignes AGENTS : # Agent instructions — Android Widget Lab

**Lire README.md puis docs/CHATGPT_PROJECT_HISTORY.md avant chaque changement.**

Projet : Prototype Android historique Java de qualité de l'air WAQI et widget. Outillage Android ancien, encore à moderniser.

Règles : Ne jamais inscrire un jeton WAQI ou secret dans Git ; un token compilé dans APK est extractible. Contrôler migration Gradle/SDK et remplacer HTTP par HTTPS après vérification.

Préserver la provenance et ne jamais écraser les versions existantes. Documenter ce qui a réellement été testé. Après chaque modification, enrichir docs/CHATGPT_PROJECT_HISTORY.md si une instruction, décision ou nouveau dialogue pertinent est disponible ; séparer citations exactes des résumés et noter les dates seulement si certaines. Ne publier ni secrets ni données privées.

## Fonctionnalités AQI 2026-10-29 — reprise

Lire impérativement `docs/AQI_FUNCTIONAL_SLICE.md`. Le chantier actuel est isolé dans `

## Contrôles à produire
1. Tester le widget Android réellement sur plusieurs villes et états de permission/réseau ; comparer données, timestamps et comportement offline.
2. Vérifier l'actualisation périodique, limites système Android, consommation de batterie et absence de trafic/clé indue.
3. Exécuter les tests et le packaging dans la matrice CI puis sur appareil ; relever les échecs au lieu de présumer la réussite.
4. Ne supprimer la branche absorbée qu'après vérification des PR et workflows qui pourraient encore y référer ; pas de merge nécessaire si avance Git = 0.
