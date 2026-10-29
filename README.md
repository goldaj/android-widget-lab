# Laboratoire Android — widget de qualité de l’air

Ancien prototype Android personnel en Java : saisie d’une ville, récupération d’un indice de qualité de l’air auprès de WAQI et affichage dans une activité et un widget. Il est distinct du projet de liste de tâches `my_first_android_app`.

État : archive pédagogique avec Android SDK 25 et ancien plugin Gradle. La compilation et l’exécution sur un Android actuel nécessitent une remise à niveau de l’outillage. Aucun APK de production n’est fourni.

## Configuration locale

Le jeton WAQI ne figure dans aucun commit de ce dépôt. Le build lit `android-widget-lab/secrets.properties` sous le dossier désigné par la variable `CODEX_PRIVATE_PROJECT_CONFIG`, ou sous `Documents/Codex-Private` dans le profil utilisateur :

```properties
WAQI_API_TOKEN=
```

La valeur est injectée dans `BuildConfig.WAQI_API_TOKEN`. Le fichier privé reste à l’extérieur du dépôt ; `secrets.properties`, clés de signature et fichiers `.env` sont aussi ignorés.

Le token d’origine a été préservé localement lors de l’import, avec les fichiers originaux. Pour distribuer l’application, remplacer l’appel direct par un service intermédiaire : un token compilé dans un APK est extractible. Remplacer également les anciennes URL HTTP par HTTPS après vérification du service, et prévoir états d’erreur, chargement et absence de données.

## À poursuivre

Migrer Gradle/SDK et les dépendances, remplacer `AsyncTask`, ajouter une gestion robuste des erreurs réseau et tester le cycle de vie du widget. Les contrôles réalisés pour l’import portent sur les secrets et l’intégrité des sources ; le build Android historique reste non vérifié.

Le dossier d’origine ne contenait plus le JAR du Gradle Wrapper : son rétablissement fait également partie de la remise en route. Les archives RAR de variantes anciennes restent préservées localement et doivent être inspectées avant un éventuel import.

## Prototype fonctionnel AQI (branche de travail, 29 octobre 2026)

Une évolution réelle du widget a été implémentée sur `feat/functional-air-quality-widget-20261029`, sans modifier `main` : la configuration de chaque widget mémorise désormais **sa propre ville**. Un clic déclenche une requête HTTPS WAQI ; le résultat arrive par rappel asynchrone avant l'affichage AQI/température/date. L'activité principale attend également la réponse avant d'afficher la boîte de dialogue. Absence de jeton : mode **DÉMO clairement identifié, sans AQI simulé**. Les villes et erreurs invalides sont gérées.

Le flux n'est pas encore certifié sur un smartphone moderne : Gradle 2.2.3, SDK 25 et wrapper incomplet nécessitent une migration. **Ne pas annoncer d'APK installable.** Tests hors ligne : `tests/AirQualityRulesTest.java`, `tests/test_layout_contract.py`, workflow `widget-contract.yml`. Guide détaillé et passation : [docs/AQI_FUNCTIONAL_SLICE.md](docs/AQI_FUNCTIONAL_SLICE.md).

L'injection du token dans `BuildConfig` persiste pour le développement **privé** ; la compilation dans un APK le rend extractible, donc **interdit de distribuer le binaire avec cette clé**. Prévoir un backend avant toute publication.

## Évolution en PR (29 octobre 2026)

Une branche ajoute un parcours AQI asynchrone, configuration de ville par widget, actualisation au toucher, HTTPS et mode démo sans données fictives. Voir [documentation fonctionnelle](docs/AQI_FUNCTIONAL_SLICE.md). **APK non compilé** : l'ancien SDK/Gradle reste à migrer.
