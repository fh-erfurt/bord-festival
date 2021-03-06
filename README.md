[![Build Status](https://github.com/fh-erfurt/bord-festival/workflows/bord-festival/badge.svg)](https://github.com/fh-erfurt/bord-festival/actions)
![Heroku](https://pyheroku-badge.herokuapp.com/?app=example&style=flat)

### [Link zur Heroku-Website](https://bord-festival.herokuapp.com/)
## Test-Logindaten
### User
User: `user@gmail.com`

Passwort: `user`

### Admin
User: `admin@gmail.com`

Passwort: `admin`

# BORD - Festival Management System

Im Rahmen des Moduls "Java 1" haben wir ein Festival-Management-System entwickelt.

Im Modul "Java 2" wird dieses Projekt nun um Spring-MVC, eine Datenbank und eine Website erweitert.
## Teammitglieder
Olga Klassen, Benjamin Swarovsky, Daniel Depta, Raphael Freybe, Franziska Schmidt

# 1. Einleitung

BORD wurde zu BORD-Festival erweitert und steht für die Anfangsbuchstaben unserer Vornamen (Benjamin, Olga, Raphael, Daniel, Franziska)

Aus einem ursprünglichen Verwaltungs- und Planungssystem für das „BORD-Festival“ hat sich eine Software für verschiedene Events auf der Bord-Festival Plattform entwickelt. Es ist nun möglich sich als Nutzer oder Admin zu registrieren und einzuloggen.

Das Festival als solches besteht aus mehreren Stages (Bühnen), welche jeweils verschiedene Programme anbieten.

Das System muss Tickets, Kunden, die verschiedenen Bands und Stages sowie die Events an sich verwalten.
Kunden haben eine Adresse und können Tickets für das Festival kaufen. Es gibt Tagestickets, Campingtickets und VIP-Tickets.

Im Verwaltungssystem wird erfasst, wann welche Band spielt und auf welcher Stage sie spielen (Programmplanung). 


## Klassendiagramm

![ClassDiagram](https://raw.githubusercontent.com/fh-erfurt/bord-festival/master/docs/UML_farbe.png)

# 2. Ausgangssituation

## 2.1 Musskriterien

Die zu etablierenden Systemabschnitte lassen sich in die Programmplanung und die Ticketverwaltung untergliedern

### a) Eventverwaltung

Für das Event an sich werden Eckdaten wie 
* Datum und Zeit des Festivals
* Budget Ein- und Ausgaben
* Adresse

verwaltet

Des Weiteren organisiert das Event:
* Verwaltung von Bands
* Verwaltung von Stages

### b) Ticketverwaltung
* Preisverwaltung der Tickets (Anpassung der Preisstufen)
* Allgemeine Informationen über den Ticketbestand
* Ticketverkauf
* Ticketanzahl pro User auf 10 Stück beschränkt

### c) Kundenverwaltung
* Warenkorb
* Ticketkauf
* Möglichkeit zum registrieren und einloggen

## 2.2 Abgrenzungskriterien

Lagerverwaltung, Personalverwaltung und Materialeinkauf sind nicht Teil dieses Projekts.

Gekaufte Tickets können nicht vom Kunden zurückgegeben werden.

# 3. Akteure/Stakeholder

![Akteure](https://raw.githubusercontent.com/fh-erfurt/bord-festival/master/docs/usecase.PNG)

## 3.1 Akteure
* Users (sind die Kunden des Festivals, können einen Account erstellen und Tickets kaufen)
* Admin (verwaltet und plant das Festival, legt Ticketpreise fest)


## 3.2 Stakeholder
* Festival-Veranstalter
* Entwickler
* Festival-Besucher

# 4. Programmfunktionalitäten
## 4.1 Eventverwaltung
Für jedes Event existiert ein Line-Up in dem alle Bands eingetragen sind und zu Bühnen zugeteilt werden. Bands erhalten für die Teilnahme am Festival ein Budget. Dies wird ihnen automatisch zugeteilt, wenn sie dem Event zugewiesen werden. Dadurch steigen die Ausgaben des Events. Die Ausgaben dürfen das Maximalbudget nicht überschreiten.

Für den geregelten Programmablauf hat jede Bühne Zeitslots, denen jeweils eine Band zugewiesen werden kann.

Falls eine Band nicht auftritt, müssen alle Zeitslots der Band gelöscht werden, damit zählt das Budget der Band nicht mehr als Ausgabe.
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

### 4.1.1 Bühnenverwaltung
* Stages können erfasst, geändert und gelöscht werden
* Die ID entspricht der Bühnennummer und kann zurückgegeben werden
* Bühnen sind im Tagesprogramm erfasst, den Bühnen können Bands zugewiesen werden

### 4.1.2 TimeSlot
* Besteht aus Startzeit (Stunden, Minuten, Sekunden), Dauer und spielender Band

### 4.1.3 Geschäftsregeln
* Event verfügt über Start und Enddatum, Start und Endzeit für jeden Tag und eine einheitliche Pause in Minuten (z.B. 30Min.).
* Zwischen zwei Timeslots gibt es immer eine Pause mit gleicher Länge
* Im Line-Up sind die Start- und die Endzeit eines Bandauftritts konfigurierbar.
* Die Spieldauer kann beliebig lang sein und muss in Minuten angegeben werden.
* Wenn die Spieldauer die Endzeit des tägliches Konzerts überschreitet, dann wird eine DateException zurückgegeben
    * Falls die Spieldauer die maximale Konzertlänge überschreitet, dann wird der Zeitslot nicht gespeichert und eine Meldung zurückgegeben.
* Jedes Event benötigt mindestens eine Bühne.
* Start- und Endzeit des jeweiligen Konzerts soll an jedem Festivaltag gleich sein.
* Die Konzerte gehen täglich bis maximal 23:59:59 Uhr.
* Die gleiche Band kann nicht gleichzeitig auf mehr als einer Bühne spielen

## 4.2 Ticketverwaltung
Tickets haben verschiedene Preisklassen und Kategorien:
* Campingticket (gültig an allen Tagen)
* Tagesticket (Gültigkeit: 1 Tag)
* VIP Ticket (gültig an allen Tagen, besseres Festival- und Campingerlebnis)

Bei einer Überschreitung eines gewissen Prozentsatzes von verfügbaren Tickets wird die Preisklasse angehoben. Die Prozentsätze und das Ticketkontingent und die einzelnen Ticketpreise können variabel definiert werden. Außerdem können Preisklassen manuell nach der Eventerstellung angepasst werden.

Jede Ticketkategorie hat die gleichen Preisklassen.
 
Bei der Eventerstellung wird das Ticketkontingent festgelegt, es kann später wieder angezeigt, aber nicht geändert werden.

Die Anzahl der verkauften Tickets und der noch übrigen Tickets kann zurückgegeben werden, diese Werte werden nach einem Ticketverkauf aktualisiert.
Jeder Kunde kann höchstens 10 Tickets pro Ticketkategorie kaufen.

### 4.2.1 Geschäftsregeln
* Event hat Einnahmen durch Ticketverkäufe, diese Werte werden nach einem Ticketverkauf aktualisiert
* Tickets können nicht zurückgegeben werden
* Das Preislevel ist eine Ganzzahl von 0 bis n

## 4.3 Accountverwaltung
Die Accountverwaltung bietet folgende Funktionalitäten:
* Registrierung, Einloggen von Kundenaccounts
* Unterteilung in User und Admin Accounts
* Validierung von E-Mail, Vorname und Nachname
* Validierung des Unique-Properties der E-Mail
* Hinzufügen von Tickets zum Warenkorb
* Kaufen von Tickets
* Hinzufügen von gekauften Tickets zum Kundeninventar
* Hinzufügen einer Adresse während der Registrierung

### 4.3.1 Adressverwaltung
Die Adressverwaltung bietet folgende Funktionalitäten im Backend (aber auf der Website nicht umgesetzt):
* Adressinformationen können erfasst, geändert, gelöscht und zurückgegeben werden

### 4.3.2 Geschäftsregeln
* Ein Client ist entweder ein User oder ein Admin
* User sind die Festival-Kunden, welche Tickets kaufen
* Admins sind die Event-Ersteller, welche neue Events kreieren und aktualisieren können
* Beim Ticketverkauf wird der Warenkorb des Users geleert und die gekauften Tickets werden seinem Inventar hinzugefügt
* Die Ausgaben des Users werden erfasst
* Die Kosten für den Ticketkauf werden seinen Ausgaben hinzugerechnet
