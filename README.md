# OOP Datei Explorer
Dieses Projekt fungiert wie ein Datei Explorer, der in Java entwickelt wurde und OOP nutzen. Es
simuliert ein Dateisystem, das über eine Webserver Integration und eine UI zugänglich ist. Die 
Anweldung ermöglicht es dem Benutzer, durch eine hierarchische Ordnerstruktur zu navigieren und
.txt Dateien herunterzuladen.

## Wie es funktioniert?
- Der Backend fungiert als Server Umgebung, in der Dateien gespeichert werden.
- Die UI ermöglicht es den Nutzen, über Buttons mit Ordnern und Dateien zu interagieren.
- Benutzer können auf Dateien klicken, um eine Anfrage an der Server zu senden, woaufhin die
  Dateien in ein lokales Verzeichnis kopiert werden.

## Funktionen
- **OOP Design:** Der Code ist unter Verwendung von OOP Prinzipien organisiert, was für sauberen und wartbaren Code sorgt.
- **Webserver:** Ein einfacher HTTP Server läuft auf localhost:8000 und verarbeitet Datei Anfragen (GET).
- **UI:** Die UI, die mit Java Swing erstellt wurde, ermöglicht es dem Benutzer, durch den Datei Explorer zu navigieren.
- **Dateidownload:** Benutzer können Dateien vom Server auf ihr lokales System herunterladen, indem sie auf den Datei Button klicken.

## Anerkennung
Der Backend dieses Projekts wurde vom Professor bereitgestellt und wurde in keiner Weise verändert.

## Lizenz
Dieses Projekt ist Open-Source und unter der [MIT-Licence](https://opensource.org/licenses/MIT) verfügbar. 
