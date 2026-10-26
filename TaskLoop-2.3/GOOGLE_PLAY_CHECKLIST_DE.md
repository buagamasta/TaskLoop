# TaskLoop 1.6 – Vorbereitung für Google Play

## Bereits im APK geprüft

- Paketname `at.pinzker.taskloop`; Versionscode 7, Version 1.6.
- Ziel-API 36. Seit 31. August 2026 verlangt Google Play für neue Apps und Updates Android 16 / API 36 oder höher. (Offizielle Richtlinie: https://support.google.com/googleplay/android-developer/answer/11926878)
- Signaturzertifikat entspricht TaskLoop 1.5; damit ist das APK als Update installierbar.
- Benachrichtigungen, genaue Alarme, Vollbild-Erinnerungen und Alarmkanal werden im Headerstatus geprüft.
- App-Inhalte und Einstellungen sind lokal; die installierte Spracherkennung kann selbst Netzwerkzugriff verwenden.

## Vor einer Play-Einreichung noch erforderlich

- Ein Android App Bundle (`.aab`) mit Produktionsschlüssel erzeugen und in Play Console prüfen. Diese lokale Version liefert das installierbare APK; ein AAB ist nicht Teil dieses Pakets.
- Vollständige Anbieter- und Kontaktangaben im Impressum ergänzen. Derzeit ist nur „Andreas Pinzker“ hinterlegt; ladungsfähige Anschrift und Kontaktadresse fehlen.
- Datenschutzerklärung auf einer öffentlich erreichbaren Website bereitstellen und den Play-Datensicherheitsfragebogen mit dem tatsächlichen Verhalten der Spracherkennung abgleichen.
- In Play Console Zielgruppe, Inhaltsangaben, App-Zugriff, Anzeigen und Berechtigungsdeklarationen wahrheitsgemäß ausfüllen. Insbesondere Vollbild-Intent und genaue Alarme auf Zulässigkeit und Zweck prüfen.
- Lizenz und Nutzungsrechte aller im finalen Release verwendeten Töne/Assets dokumentieren.
- Auf realen Geräten Android 8 bis 16 testen: Neuinstallation, Update, Sprache, Widget, exakte Alarme, Sperrbildschirm, Hintergrundbetrieb, Wiederherstellung nach Neustart, Alarmlautstärke und Benachrichtigungen.
- Store-Eintrag, Screenshots, Symbol, Support- und Datenschutz-URL ergänzen.

Dieses Dokument ist eine Arbeitscheckliste und keine Bestätigung einer Play-Freigabe.
