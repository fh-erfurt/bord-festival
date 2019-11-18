# BORD - Festival Management System

## Teammitglieder
Olga Klassen, Benjamin Swarovsky, Daniel Depta, Raphael Freybe

## Beschreibung

BORD steht f�r die Anfangsbuchstaben unserer Vornamen (Benjamin, Olga, Raphael, Daniel)

Es soll ein Verwaltungs- und Planungssystem f�r das �BORD-Festival� entwickelt werden. 
Das Festival als solches besteht aus mehreren Stages (B�hnen), welche jeweils verschiedene Programme anbieten.
Das System muss Tickets, Kunden, die verschiedenen Bands und Stages sowie das Event an sich verwalten.
Kunden haben eine (oder mehrere) Adressen und k�nnen Tickets f�r das Festival kaufen. 
Es gibt Tagestickets und verschiedene Campingtickets.
Im Verwaltungssystem wird erfasst, wann welche Band spielt und auf welcher Stage sie spielen (Programmplanung). 
F�r das Event an sich werden Eckdaten wie Datum des Festivals, Maximalkapazit�t,Budget noch verf�gbare Karten usw. verwaltet.

## Klassendiagramm

![ClassDiagramm](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/fh-erfurt/robd-festival/master/classDiagram.iuml)


# Ausgangssituation
Es soll ein Verwaltungs- und Planungssystem f�r das �BORD-Festival� entwickelt werden. Das Festival als solches besteht aus mehreren Stages (B�hnen), welche jeweils verschiedene Programme anbieten.
Das System muss Tickets, Kunden, die verschiedenen Bands und Stages sowie das Event an sich verwalten.

Kunden haben eine Adresse und k�nnen Tickets f�r das Festival kaufen. Es gibt Tagestickets und verschiedene Campingtickets.
Im Verwaltungssystem wird erfasst, wann welche Band spielt und auf welcher Stage sie spielen (Programmplanung). 
F�r das Event an sich werden Eckdaten wie Datum des Festivals, Maximalkapazit�t und noch verf�gbare Karten verwaltet.

## Musskriterien

### Festivalverwaltung

Im Festival existiert ein einheitliches Line-Up in dem alle Bands eingetragen sind, welche auf jeweils B�hne zugeteilt werden. Derzeit sind drei B�hnen geplant, die B�hnenanzahl muss aber variabel sein. Das Festival als solches besitzt au�erdem ein Gesamtbudget, zur �bersichtlichen Darstellung der Einnahmen und Kosten. Die Einnahmen werden durch gekaufte Tickets generiert, die Kosten entstehen durch gemietete Bands.

### Ticketverwaltung
Tickets haben verschiedene Preisklassen und Kategorien:
* Standardticket (3 Tage)
* Tagesticket (1 Tag)
* VIP Ticket

Die Preisklasse der Tickets wird �ber die Zeit, sobald weniger Tickets verf�gbar sind, angepasst. Ab einem gewissen Prozentsatz von verf�gbaren Tickets wird die Preisklasse angehoben. Die Prozents�tze und das Ticketkontingent k�nnen variabel definiert werden.
Der Ticketverkauf soll �ber eine geeignete, kompatible Applikation erfolgen.


## Wunschkriterien


## Abgrenzungskriterien