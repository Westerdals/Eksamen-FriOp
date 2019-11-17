# Eksamen Avansert Java FriOp

*Dette er en mal som det anbefales at dere følger for `README.md`-filen til hovedinnleveringen i PGR203* 

[![Build Status](https://travis-ci.com/Westerdals/Eksamen-FriOp.svg?token=oQD1cuGX1pop1pu9V5PF&branch=master)](https://travis-ci.com/Westerdals/Eksamen-FriOp/builds/136891645)

## Hvordan kjøre dette programmet

### Bygg og test executable jar-fil

1. Bygg prosjektet ved å kjøre mvn package
2. java -jar target/eksamen-friop-1.0-SNAPSHOT.jar
3. Gå til localhost:8080

### Funksjonalitet

* I vårt program kan man legge til members med både fornanvn og etternavn som blir lagret i en tabell, 
* Kan også opprette ett eller flere prosjekt som vi kan sette status på når man oppretter prosjektet.
* Vi kan også hente ut medlemmer fra DB (og projects), og sette dem til én eller flere eksisterende projects, og 
    unngå at ikke det samme skjer flere ganger.
    


## Designbeskrivelse

![](docs/FriOp.uml)

     For se på bilde må du gå in på docs og klikke på uml bidet.

     Veldig kort forklart er det er "kart" over alle klassene vi har i programmet.
       
         På bildet kan du se streker i forskjellige farger og mønsteret som beskriver
         koblinen mellom klassene.
         kort fortalt:
             Blå pil/strek betyr at den klassen "extends an abstract class"
             Grønn pil/strek betyr at klassen "implements an Interface"
             Hvit pil/strek betyr at det er en en "connection" mellom dem.  
    
## Egenevaluering

    * Vi jobbet mye på én datamaskin i starten, ettersom at vi hadde problemer med den ene
    i voldsom lang tid, og git var vanskelig å forstå. Etterhvert som vi fikk den andre datamaskinen
    opp og gå, og lærte oss mer av git, gikk kodingen ekstremt mye fortere. Det var dog fortsatt
    best å sitte på hver vår maskin, men ved siden av hverandre for å hjelpe til med det vi kunne. 
    

### Hva vi lærte underveis
    * Vi har lært mye om å sette opp server og db, samt at det ikke er alltid like lett
      å gjøre ting som man ser for seg. men det vi kanskje har lært aller mest under 
      denne eksamen er par programmering. Vi har lært hverandre shortcuts og hvordan
      ting fungerer i programmet. det har vært lettere og komme fram til en løsning når 
      vi er to syntes vi. Videre har vi også lært mye om hvordan git fungerer, og hvordan
      å pushe fra begges datamaskin uten å bruke 5 timer på å fikse alt som gikk galt.

### Hva vi fikk til bra i koden vår
     * Synes ikke det er noe spesielt "bra" med koden vår uten om det som er forventet av
       oss, men vi er veldig fornøyd med at vi har klart å hente verider fra dropdown menu
       og bruke dette til ny sql insert, koden er ikke så veldig avansert, men vi brukte 
       mange, mange timer på å sette oss inn i dette, samt spurt flere grupper om hjelp
       / diskutert / hjulpet. 

### Hva vi skulle ønske vi hadde gjort annerledes
     * Det vi skulle ha gjort anndelredes er å kanskje sette av litt mer tid til denne 
        eksamen, men med 2 andre eksamner så har denne blitt litt mindre prioritert. 
     * vi skulle gjerne ha redirected en side, når man prøver å gi medlem samme project
        2 ganger. det er fordi feil mld som kommer opp kan bli brukt til muligens hacking.
        Vi skulle også gjerne ha fått til å bruke alter tables på allerede eksisterende data i 
        tabellen, men det hadde vi ikke tid til.  

## Evaluering fra annen gruppe
    1. Godt inndelt i mapper/oversiktlig
    2. Bra at dere har fått til å legge til medlem på prosjekter
    3. Fungerer enkelt å sette opp med lokal database
    4. Kan ikke se at jar fungerer
    5. Er en del kode som er kommentert ut, kan ryddes opp i?
    6. Måtte kommentere ut tester før mvn kjører

## Evaluering gitt til annen gruppe
    1. Tester
        - Du har skrevet en del tester som dekker mye av koden 👍 🥇
    2. Greie navn på metoder og klasser
    3. Fjerne unødvendig kode
    4. organiser "Classes"
        - Du kunne gruppere "Classes" dine, det kan bli litt
          uoversiktlig hvis prosjektet blir litt større
