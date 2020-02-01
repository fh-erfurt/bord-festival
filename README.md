[![Build Status](https://github.com/fh-erfurt/bord-festival/workflows/bord-festival/badge.svg)](https://github.com/fh-erfurt/bord-festival/actions)

# BORD - Festival Management System

## Teammitglieder
Olga Klassen, Benjamin Swarovsky, Daniel Depta, Raphael Freybe

# 1. Einleitung

BORD steht für die Anfangsbuchstaben unserer Vornamen (Benjamin, Olga, Raphael, Daniel)

Es soll ein Verwaltungs- und Planungssystem für das „BORD-Festival“ entwickelt werden. Das Festival als solches besteht aus mehreren Stages (Bühnen), welche jeweils verschiedene Programme anbieten.

Das System muss Tickets, Kunden, die verschiedenen Bands und Stages sowie das Event an sich verwalten.
Kunden haben eine Adresse und können Tickets für das Festival kaufen. Es gibt Tagestickets, Campingtickets und VIP-Tickets.

Im Verwaltungssystem wird erfasst, wann welche Band spielt und auf welcher Stage sie spielen (Programmplanung). 


## Klassendiagramm

![ClassDiagram](https://raw.githubusercontent.com/fh-erfurt/bord-festival/master/docs/UML_farbe.png)

# 2. Ausgangssituation

## 2.1 Musskriterien

Die zu etablierenden Systemabschnitte lassen sich in die Programmplanung und die Ticketverwaltung untergliedern

### a) Eventverwaltung

Für das Event an sich werden Eckdaten wie 
* Datum des Festivals
* Maximalkapazität 
* Noch verfügbare Karten
* Budget Ein- und Ausgaben

verwaltet

Des Weiteren organisiert das Event:
* Verwaltung von Bands
* Kapazität der einzelnen Bühnen

### b) Ticketverwaltung
* Preisverwaltung der Tickets (Anpassung der Preisstufen)
    * Anpassung der Preisstufen automatisch oder manuell
* Allgemeine Informationen über den Ticketbestand
* Ticketverkauf

### c) Kundenverwaltung
* Verwaltung der Kundenaccounts
* Warenkorb
* Ticketkauf
* Verwaltung der Adresse

## 2.2 Abgrenzungskriterien

Lagerverwaltung, Personalverwaltung und Materialeinkauf sind nicht Teil dieses Projekts.

Gekaufte Tickets können nicht vom Kunden zurückgegeben werden.

# 3. Akteure/Stakeholder

![Akteure](https://raw.githubusercontent.com/fh-erfurt/bord-festival/master/docs/usecase.PNG)

## 3.1 Akteure
* Clients (sind die Kunden des Festivals, können einen Account erstellen und Tickets kaufen)
* Organizer (verwaltet und plant das Festival, legt Ticketpreise fest)


## 3.2 Stakeholder
* Festival-Veranstalter
* Entwickler

# 4. Programmfunktionalitäten
## 4.1 Eventverwaltung
Im Festival existiert ein einheitliches Line-Up in dem alle Bands eingetragen sind und zu Bühnen zugeteilt werden. Bands erhalten für die Teilnahme am Festival ein Budget. Dies wird ihnen automatisch zugeteilt, wenn sie dem Event zugewiesen werden. Dadurch steigen die Ausgaben des Events. Die Ausgaben dürfen das Maximalbudget nicht überschreiten.

Für den geregelten Programmablauf hat jede Bühne Zeitslots, denen jeweils eine Band zugewiesen werden kann.

Falls eine Band nicht auftritt, müssen alle Zeitslots gelöscht werden, damit zählt das Budget der Band nicht mehr als Ausgabe.
Die Band wird unabhängig von der Anzahl der Auftritte bezahlt.

Die Bühnenanzahl ist beliebig, es können jederzeit Bühnen hinzugefügt werden. Bühnen können gelöscht werden, wenn keine Bands zugewiesen sind.

Das Festival als solches besitzt außerdem ein Gesamtbudget, zur übersichtlichen Darstellung der Einnahmen und Kosten. Die Einnahmen werden durch gekaufte Tickets generiert, die Kosten entstehen durch gemietete Bands.


* Das Event verfügt über eine festgelegte Anzahl an Preisstufen
* Jede Preisstufe richtet sich nach einem gewissen Prozentsatz an verkauften Tickets, wird diese überschritten, erhöht sich die Preisstufe und damit auch die Ticketpreise 
    * Die Preisstufe wird automatisch angepasst, dies kann aber auch deaktiviert werden 
* Die Ticketpreise pro Preisstufe sind zum Eventbeginn festzulegen (die Preise der einzelnen Preisstufen können aber noch nach der Erstellung des Events geändert werden)
* Das aktuelle Preislevel kann zurückgegeben werden

Das Programm
* Besteht aus Stage und zugewiesener Liste von TimeSlots

### 4.1.1 Geschäftsregeln
* Event verfügt über Start und Enddatum, Start und Endzeit für jeden Tag und eine einheitliche Pause in Minuten (z.B. 30Min.).
* Zwischen zwei Timeslots gibt es immer eine Pause mit gleicher Länge
* Im Line-Up sind die Start- und die Endzeit eines Bandauftritts konfigurierbar.
* Die Spieldauer kann beliebig lang sein und muss in Minuten angegeben werden.
* Wenn die Spieldauer die Endzeit des tägliches Konzerts überschreitet, dann wird eine DateException zurückgegeben
    * Falls die Spieldauer die maximale Konzertlänge überschreitet, dann wird der Zeitslots nicht gespeichert und eine Meldung zurückgegeben.
* Jedes Event benötigt mindestens eine Bühne.
* Start- und Endzeit des jeweiligen Konzerts soll an jedem Festivaltag gleich sein.
* Die Konzerte gehen täglich bis maximal 23:59:59 Uhr.
* Die gleiche Band kann nicht gleichzeitig auf mehr als einer Bühne spielen

## 4.2 Ticketverwaltung
Tickets haben verschiedene Preisklassen und Kategorien:
* Campingticket (3 Tage)
* Tagesticket (1 Tag)
* VIP Ticket (3 Tage, besseres Festival- und Campingerlebnis)

Bei einer Überschreitung eines gewissen Prozentsatzes von verfügbaren Tickets wird die Preisklasse angehoben. Die Prozentsätze und das Ticketkontingent und die einzelnen Ticketpreise können variabel definiert werden. Außerdem können Preisklassen manuell nach der Eventerstellung angepasst werden.

Jede Ticketkategorie hat die gleichen Preisklassen.
 
Bei der Eventerstellung wird das Ticketkontingent festgelegt, es kann später wieder angezeigt, aber nicht geändert werden.

Die Anzahl der verkauften Tickets und der noch übrigen Tickets kann zurückgegeben werden, diese Werte werden nach einem Ticketverkauf aktualisiert.

### 4.2.1 Geschäftsregeln
* Event hat Einnahmen durch Ticketverkäufe, diese Werte werden nach einem Ticketverkauf aktualisiert
* Tickets können nicht zurückgegeben werden
* Das Preislevel ist eine Ganzzahl von 0 bis n

## 4.3 Kundenverwaltung
Die Kundenverwaltung bietet folgende Funktionalitäten:
* Kundendaten zurückgeben, bearbeiten und anlegen
* Validierung von E-Mail, Vorname und Nachname
* Hinzufügen von Tickets zum Warenkorb
* Kaufen von Tickets
* Hinzufügen von gekauften Tickets zum Kundeninventar
* Hinzufügen einer Adresse

### 4.3.1 Geschäftsregeln
* Beim Ticketverkauf wird der Warenkorb des Kunden geleert und die gekauften Tickets werden seinem Inventar hinzugefügt
* Die Ausgaben des Kunden werden erfasst
* Die Kosten für den Ticketkauf werden seinen Ausgaben hinzugerechnet

### 4.4 Adressverwaltung
Die Adressverwaltung bietet folgende Funktionalitäten:
* Adressinformationen können erfasst, geändert, gelöscht und zurückgegeben werden
* Zwei Adressen können zur Überprüfung miteinander verglichen werden

## 4.5 Bühnenverwaltung
* Stages können erfasst, geändert und gelöscht werden
* Die ID entspricht der Bühnennummer und kann zurückgegeben werden
* Bühnen sind im Tagesprogramm erfasst, den Bühnen können Bands zugewiesen werden

### 4.5.1 TimeSlot
* Besteht aus Startzeit (Stunden, Minuten, Sekunden), Dauer und spielende Band