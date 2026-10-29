package com.example.goldaj.firstapp;

/** Pure Java validation and WHO-independent US EPA AQI category labels. */
public final class AirQualityRules {
    private AirQualityRules() {}

    public static String normalizeCity(String raw) {
        if (raw == null) throw new IllegalArgumentException("Ville manquante");
        // Reject control characters before trimming or collapsing whitespace:
        // otherwise a pasted newline could become a valid city name.
        for (int i = 0; i < raw.length(); i++) {
            if (Character.isISOControl(raw.charAt(i))) {
                throw new IllegalArgumentException("Nom de ville invalide : caractère de contrôle");
            }
        }
        String value = raw.trim().replaceAll("\\s+", " ");
        if (value.length() == 0 || value.length() > 80 ||
                !value.matches("[\\p{L}\\p{N} .'-]+")) {
            throw new IllegalArgumentException("Nom de ville invalide (1-80 caractères)");
        }
        return value;
    }

    public static String label(int aqi) {
        if (aqi < 0) return "Indice indisponible";
        if (aqi <= 50) return "Bonne";
        if (aqi <= 100) return "Modérée";
        if (aqi <= 150) return "Mauvaise pour les personnes sensibles";
        if (aqi <= 200) return "Mauvaise";
        if (aqi <= 300) return "Très mauvaise";
        return "Dangereuse";
    }
}
