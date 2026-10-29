# Parcours fonctionnel AQI — 29 octobre 2026

## Ce qui fonctionne côté code

Ce chantier transforme le prototype précédent en application utilisant une **même source de données WAQI** pour l'activité et les widgets, plutôt qu'un affichage factice / des valeurs lues avant la fin d'un `AsyncTask`.

1. **Activité :** saisir une ville valide, lancer une requête asynchrone ; la boîte de dialogue s'affiche uniquement **après** la réponse, ou montre une erreur claire.
2. **Ajout de widget :** la configuration par `WidgetActivity` s'ouvre depuis le lanceur Android (déclarée via `android:configure`). L'utilisateur valide la ville, stockée sous l'ID propre à cette instance.
3. **Widget :** il affiche Chargement, puis les champs AQI, qualification de la qualité de l'air, température quand disponible et date WAQI. Un appui sur le widget redemande les données. Plusieurs widgets peuvent suivre des villes différentes.
4. **Robustesse :** une requête ancienne ne remplace pas les données d'une ville nouvellement configurée ; la configuration est supprimée quand le widget est retiré. Le réseau utilise HTTPS, des délais bornés, une taille maximale de réponse et un décodage JSON défensif. Aucune URL contenant le token ni réponse distante n'est journalisée.
5. **Sans clé API :** mode **DÉMO**, explicitement affiché, **sans inventer de données**. Les erreurs ou villes inconnues n'affichent pas de fausses mesures.

## Test et lancement

Ce dépôt utilise encore **Gradle 2.2.3, Android SDK 25 et un Gradle Wrapper incomplet**. Un build APK moderne n'est **pas** validé et aucun fichier binaire n'est produit. Ne pas laisser entendre qu'un smartphone récent peut installer cette version sans migration préalable.

Des contrôles indépendants de l'outillage Android ancien sont ajoutés :

```bash
javac --release 8 -d /tmp/aqi-java app/src/main/java/com/example/goldaj/firstapp/AirQualityRules.java app/src/main/java/com/example/goldaj/firstapp/AirQualityData.java tests/AirQualityRulesTest.java
java -cp /tmp/aqi-java com.example.goldaj.firstapp.AirQualityRulesTest
python -m unittest discover -s tests -p 'test_*.py' -v
```

Java 17+ pour l'option `--release 8`. Sous Windows, utiliser un répertoire temporaire local au lieu de `/tmp`. Le workflow `.github/workflows/widget-contract.yml` exécute ces contrôles sur GitHub Actions.

**Couverture :** validation des villes, seuils d'AQI, distinction données réelles/démo/erreur, présence des éléments du widget, manifest, interdiction du HTTP. Ces tests **ne compilent ni n'exécutent le code Android dépendant du SDK**. Ne pas confondre un job CI vert et un APK fonctionnel.

## Sécurité du jeton

Le build lit toujours `WAQI_API_TOKEN` depuis un coffre local externe tel que décrit dans `README.md`. **Ne jamais stocker ce fichier ou sa valeur dans Git.** Même externalisé lors du build, le jeton est incorporé dans `BuildConfig` et donc extractible d'un APK distribué. L'usage de la clé doit rester **strictement personnel/de laboratoire** ; toute distribution publique nécessite d'abord un backend sécurisé avec règles de quota et politique de secrets. Aucun appel WAQI réel n'a été effectué pendant cette intervention.

## Reprise Codex

- Base : `main`. Branche : `feat/functional-air-quality-widget-20261029`, PR associée. Aucun autre intervenant n'était présent sur une PR du dépôt lors de l'inspection.
- Lire `AGENTS.md`, `README.md`, `docs/CHATGPT_PROJECT_HISTORY.md` avant de modifier.
- Compiler un véritable APK avec migration AGP/Gradle/SDK **sur une autre PR** ; ne pas forcer Gradle 2.2.3 à fonctionner sur les runners modernes sans revue des implications.
- Tester l'ajout, la configuration, plusieurs instances du widget, suppression, rotation, réseau hors-ligne, ville inconnue et rafraîchissement sur émulateur ou téléphone.
- Remplacer le `AsyncTask` historique par un exécuteur moderne, ajouter une planification raisonnée des rafraîchissements (WorkManager, contraintes réseau, batterie) puis envisager l'exposition du service seulement après revue du risque de clé. Prévoir un cache d'AQI local, daté et respectueux des quotas.
- Vérifier l'accessibilité, les thèmes et contraintes du widget sur différentes densités d'écran.
- Si le temps le permet, migrer la UI et les tests instrumentés. Ne pas annoncer de compatibilité Android 14/15/16 avant un build et une installation réels.

**Aucune installation, aucun appel réseau, aucun jeton privé ni accès à un appareil n'ont été utilisés.**
