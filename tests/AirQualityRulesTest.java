package com.example.goldaj.firstapp;

public final class AirQualityRulesTest {
    private static int assertions = 0;

    private static void check(boolean ok, String description) {
        assertions++;
        if (!ok) throw new AssertionError(description);
    }

    public static void main(String[] args) {
        check("Strasbourg".equals(AirQualityRules.normalizeCity("  Strasbourg  ")), "trimming");
        check("Saint-Étienne".equals(AirQualityRules.normalizeCity("Saint-Étienne")), "accents");
        check("New York".equals(AirQualityRules.normalizeCity("New   York")), "spaces");
        check("L'Haÿ-les-Roses".equals(AirQualityRules.normalizeCity("L'Haÿ-les-Roses")), "apostrophe");
        for (String invalid : new String[] {"", " ", "../Paris", "Paris/../x", "Paris?", "x\nnewline"}) {
            boolean rejected = false;
            try {
                AirQualityRules.normalizeCity(invalid);
            } catch (IllegalArgumentException expected) {
                rejected = true;
            }
            check(rejected, "invalid city: " + invalid);
        }
        check("Bonne".equals(AirQualityRules.label(0)), "aqi 0");
        check("Bonne".equals(AirQualityRules.label(50)), "aqi 50");
        check("Modérée".equals(AirQualityRules.label(51)), "aqi 51");
        check("Modérée".equals(AirQualityRules.label(100)), "aqi 100");
        check("Mauvaise pour les personnes sensibles".equals(AirQualityRules.label(101)), "aqi 101");
        check("Mauvaise".equals(AirQualityRules.label(151)), "aqi 151");
        check("Très mauvaise".equals(AirQualityRules.label(201)), "aqi 201");
        check("Dangereuse".equals(AirQualityRules.label(301)), "aqi 301");
        check("Indice indisponible".equals(AirQualityRules.label(-1)), "unknown aqi");

        AirQualityData demo = AirQualityData.demo("Strasbourg");
        check(demo.demo, "demo labelled");
        check(demo.aqi == -1 && "—".equals(demo.indexLabel()), "demo never invents AQI");
        check(demo.description.contains("DÉMO"), "demo visibly labelled");
        AirQualityData failure = AirQualityData.failure("Strasbourg");
        check(failure.error != null && failure.aqi == -1, "network error not misrepresented");
        AirQualityData live = AirQualityData.live("Strasbourg", 60, "17", "2026-10-29");
        check(!live.demo && "60".equals(live.indexLabel()), "actual value");
        System.out.println("PASS " + assertions + " Java AQI rules assertions");
    }
}
