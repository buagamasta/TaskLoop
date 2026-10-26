TaskLoop 2.3 — Quellcode

Android-Projekt (minSdk 26, targetSdk 36, compileSdk 35). Versionscode 13.

Änderungen in 2.3:
- Jede Aufgabe hat den Bereich „Dienstlich“ oder „Privat“. Neue und bisher nicht kategorisierte Aufgaben gelten standardmäßig als dienstlich.
- In der Aufgabenübersicht und in den Einstellungen gibt es klar erreichbare Pausensteuerungen: alle Erinnerungen gemeinsam, nur Dienstlich oder nur Privat.
- Pausen können 24 Stunden, 3, 7, 14 oder 30 Tage, bis einschließlich eines gewählten Datums oder bis zur manuellen Reaktivierung dauern. Bei gewähltem Datum wird am Folgetag automatisch wieder aktiviert.
- Eine Pause lässt Aufgaben und deren Termine bestehen; sie verhindert währenddessen nur die Erinnerung. Nach Ende der Pause werden aktive Erinnerungen wieder eingeplant.
- Vorherige Änderungen der Version 2.2 bleiben enthalten: Bearbeiten per stabiler Aufgaben-ID, Zurück-Navigation, Alarmaktionen, Verlängerungen 5/30/120 Minuten mit individueller Dauer sowie standardmäßig ausgeschalteter Ton und Vibration.

Bauen:
- SDK 35, JDK 8-kompatible Quellcodeausgabe, Android Build Tools 35.0.0.
- build.py verwendet ECJ_JAR, wenn gesetzt, ansonsten javac.
- Für signierte Updates den bestehenden TaskLoop-2.0-Release-Schlüssel verwenden. Der Schlüssel selbst ist nicht im Quellcodearchiv enthalten.
- Zertifikat SHA-256: 2e2dfe7cde71129b11160bd3811f2891eb97d9ad6e93a18bcb809cf97d24dc41

Aktueller Buildstatus dieses Arbeitslaufs: APK nicht erstellt, da Android SDK und Java-Compiler in der verfügbaren Umgebung fehlen. Die Quellcodeänderungen sind daher nicht kompiliert oder auf einem Gerät geprüft.
