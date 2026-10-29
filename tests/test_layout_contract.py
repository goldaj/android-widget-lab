"""Verify Android widget resources without downloading the unavailable historic SDK."""
from pathlib import Path
from xml.etree import ElementTree as ET
import unittest

ROOT = Path(__file__).resolve().parents[1]
SRC = ROOT / "app/src/main"
NS = "{http://schemas.android.com/apk/res/android}"


class WidgetResourceTests(unittest.TestCase):
    def test_widget_has_configuration_activity(self):
        root = ET.parse(SRC / "res/xml/widget_provider.xml").getroot()
        self.assertEqual(root.attrib[NS + "configure"],
                         "com.example.goldaj.firstapp.WidgetActivity")

    def test_widget_has_all_dynamic_fields(self):
        root = ET.parse(SRC / "res/layout/widget_provider.xml").getroot()
        ids = {node.attrib.get(NS + "id") for node in root.iter()}
        for key in ("widgetLayout_id", "cityValue_id", "aqiValue_id",
                    "healthValue_id", "temperatureValue_id", "updatedValue_id"):
            self.assertIn("@+id/" + key, ids)

    def test_manifest_uses_only_https_and_explicit_components(self):
        root = ET.parse(SRC / "AndroidManifest.xml").getroot()
        app = root.find("application")
        self.assertEqual(app.attrib.get(NS + "usesCleartextTraffic"), "false")
        declarations = [item for item in app if item.tag in ("activity", "receiver")]
        self.assertEqual(len(declarations), 3)
        self.assertTrue(all(NS + "exported" in item.attrib for item in declarations))
        sources = [
            SRC / "java/com/example/goldaj/firstapp/AirQualityClient.java",
            SRC / "res/values/strings.xml",
        ]
        self.assertTrue(all("https://api.waqi.info/feed/" in path.read_text(encoding="utf-8")
                            for path in sources))
        self.assertTrue(all("http://api.waqi.info" not in path.read_text(encoding="utf-8")
                            for path in sources))


if __name__ == "__main__":
    unittest.main()
