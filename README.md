# BORD - Festival Management System

## Teammitglieder
Olga Klassen, Benjamin Swarovsky, Daniel Depta, Raphael Freybe

## Beschreibung

BORD steht für die Anfangsbuchstaben unserer Vornamen (Benjamin, Olga, Raphael, Daniel)

Es soll ein Verwaltungs- und Planungssystem für das „BORD-Festival“ entwickelt werden. 
Das Festival als solches besteht aus mehreren Stages (Bühnen), welche jeweils verschiedene Programme anbieten.
Das System muss Tickets, Kunden, die verschiedenen Bands und Stages sowie das Event an sich verwalten.
Kunden haben eine (oder mehrere) Adressen und können Tickets für das Festival kaufen. 
Es gibt Tagestickets und verschiedene Campingtickets.
Im Verwaltungssystem wird erfasst, wann welche Band spielt und auf welcher Stage sie spielen (Programmplanung). 
Für das Event an sich werden Eckdaten wie Datum des Festivals, Maximalkapazität,Budget noch verfügbare Karten usw. verwaltet.

## Klassendiagramm

![ClassDiagramm](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/fh-erfurt/robd-festival/master/classDiagram.iuml)


# Ausgangssituation
Es soll ein Verwaltungs- und Planungssystem für das „BORD-Festival“ entwickelt werden. Das Festival als solches besteht aus mehreren Stages (Bühnen), welche jeweils verschiedene Programme anbieten.
Das System muss Tickets, Kunden, die verschiedenen Bands und Stages sowie das Event an sich verwalten.

Kunden haben eine Adresse und können Tickets für das Festival kaufen. Es gibt Tagestickets und verschiedene Campingtickets.
Im Verwaltungssystem wird erfasst, wann welche Band spielt und auf welcher Stage sie spielen (Programmplanung). 
Für das Event an sich werden Eckdaten wie Datum des Festivals, Maximalkapazität und noch verfügbare Karten verwaltet.

## Musskriterien

### Festivalverwaltung

Im Festival existiert ein einheitliches Line-Up in dem alle Bands eingetragen sind, welche auf jeweils Bühne zugeteilt werden. Derzeit sind drei Bühnen geplant, die Bühnenanzahl muss aber variabel sein. Das Festival als solches besitzt außerdem ein Gesamtbudget, zur übersichtlichen Darstellung der Einnahmen und Kosten. Die Einnahmen werden durch gekaufte Tickets generiert, die Kosten entstehen durch gemietete Bands.

### Ticketverwaltung
Tickets haben verschiedene Preisklassen und Kategorien:
* Standardticket (3 Tage)
* Tagesticket (1 Tag)
* VIP Ticket

Die Preisklasse der Tickets wird über die Zeit, sobald weniger Tickets verfügbar sind, angepasst. Ab einem gewissen Prozentsatz von verfügbaren Tickets wird die Preisklasse angehoben. Die Prozentsätze und das Ticketkontingent können variabel definiert werden.
Der Ticketverkauf soll über eine geeignete, kompatible Applikation erfolgen.


## Wunschkriterien


## Abgrenzungskriterien


## Aktuere/Stakeholder

### Aktuere
* Clients (Kunden beim Erwerb von Tickets, Beschaffen von Informationen über das Festival)
* Bands (Werden gebucht um auf einer bestimmten Stage in einem bestimmten Timeslot aufzutreten)

### Stakeholder
* Festival-Veranstalter
* Bands
* Entwickler
