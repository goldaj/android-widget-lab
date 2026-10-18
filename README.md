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
