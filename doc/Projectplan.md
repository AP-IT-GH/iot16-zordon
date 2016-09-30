# Projectplan

## **Team:** 

Zordon

## **Project keuze:** 

EDI

## **Project beschrijving:**

Wij gaan een applicatie dat een relais zal aansturen door middel van voice control. Hiermee kunnen we dus bijvoorbeeld stopcontacten en andere dingen aansturen met behulp van je stem. Hoe we dit gaan tewerk stellen doen we op volgende manier. We hebben een gebruiker, die een smartphone heeft. Je smartphone heb je altijd in de buurt of toch meestal. Die wilt bijvoorbeeld dat er een lichtje in de slaapkamer aangaat wanneer hij wakker wordt. Wat hij dan doet is hij zegt “Zordon, doe het slaapkamer licht aan” en dan zal het licht aangaan van de slaapkamer. De reden waarom we het via een smartphone doen en niet via een Raspberry PI is omdat die een centraal punt in het huis en je dus altijd naar daar moet lopen wil je iets aansturen. Een smartphone is mobieler.

Hoe we dit gaan aanpakken als developer en als hardware producent is door ten eerste een android applicatie maken. De gebruiker logged zich in op de applicatie en verbind zo met de cloud. In de cloud staan al zijn apparaten die hij gekoppeld heeft in het huis. Wanneer hij dan bijvoorbeeld zegt dat hij de lichten aan wilt sturen dan wordt zijn stem opgenomen en doorgestuurd naar de Google cloud service daarin wordt de zin die werd gezegd omgevormd naar tekst, die dan zal doorgestuurd worden naar een cloud die het MQTT protocol ondersteund. In de cloud kijken we dan welke module er moet aangestuurd worden. Dit wordt dan doorgestuurd naar de correcte arduino over wifi, die dan een relais zal schakelen waardoor de lichten zullen aangaan. Wij zullen een PCB maken waarin een Arduino zit samen met een wifi relais module die in het stopcontact past.

Dit is ons basis project, dit willen we uitgevoerd krijgen tegen het einde van het jaar. Als we tijd over hebben kunnen we nog extra features toevoegen. We denken momenteel aan volgende features. Ons idee is momenteel om het via stopcontacten te doen, we denken er aan om het misschien via lichtschakelaars te doen, al denken we wel dat we hier niet voldoende tijd voor zullen hebben. Momenteel is onze applicatie ook één enkele taal dat wordt ondersteund, de bedoeling is om dit uit te breiden naar meerderen. Ook willen we een soort talk-back systeem maken, zodat als je zegt dat de lichten aan moet, dat het systeem dan terug zegt dat de lichten aan zijn, of dat het niet is gelukt. Momenteel is dit ook allemaal voor geprogrammeerde zinnen, we willen dit uit breiden naar dat de gebruiker kan zeggen van dit is de zin die ik wil dat dit aanstuurt, en dit is de zin die ik terug wil krijgen. 



## **Functionele analyse:**

#### Analyse
Wij gaan gebruik maken van volgende technologieën. Ten eerste gaan we een Android applicatie maken, dus maken we gebruik van Android. In deze applicatie gaan we gebruik maken van Cloud services, MQTT protocol en voor de Android applicatie gaan we gebruik maken van Android Cloud Speech API. Verder maken we gebruik van Wifi. Voor hardware maken we gebruik van een Wifi Relais Module (ESP8266). Om dan deze te programmeren maken we gebruik van een Arduino Nano. 

---

#### High level blokdiagram

![alt text](https://sakshambhatla.files.wordpress.com/2014/08/mqtt_fig11-e1409777282815.jpg)

---

#### Flowchart

![alt text](https://github.com/AP-Elektronica-ICT/iot16-zordon/blob/master/doc/images/Flowchart.png?raw=true)

---

#### Backlog

**Als gebruiker wil ik via spraak apparaten kunnen activeren (Aanzetten)**  
*Het activeren van de apparaten zal via MQTT naar de cloud gaan, de spraak detectie wordt gemaakt met behulp van Google Cloud Speech API*  
**Als gebruiker wil ik via spraak apparaten kunnen deactiveren (Uitzetten)**  
*Het deactiveren van de apparaten zal via MQTT naar de cloud gaan, de spraak detectie wordt gemaakt met behulp van Google Cloud Speech API*  
**Als gebruiker wil ik apparaten kunnen toevoegen**  
*Als gebruiker wil ik apparaten kunnen toevoegen*  
**Als gebruiker wil ik kunnen zien welke apparaten aan en uit zijn**  
*Een cloud platform instellen die MQTT apparaten kan beheren*  
**Als gebruiker wil ik kunnen zien welke apparaten er van mij gekend zijn**  
*Een korte lijst in de android app die de status van de apparaten in de cloud uitleest*  
**Als gebruiker wil ik spraak confirmatie van mijn spraak opdracht**  
*De ESP8266 zal via MQTT een confirmatie bericht sturen naar de cloud, die dan aan de Android app zal laten weten of het apparaat aanstaat via spraak*  
**Als gebruiker wil ik kunnen inloggen zodat ik kan zien welke apparaten er beschikbaar zijn en welke ik kan besturen**  
*Er zal een database zijn met beschikbare gebruikers zodat zij alleen toegang hebben tot hun beschikbare apparaten*  
**Als gebruiker wil ik via touch input mijn apparaten manueel kunnen besturen**  
*Een lijst die al de beschikbare apparaten weergeeft en ze hiermee ook kunt besturen*  

---
## Planning Sprint 1 & 2
https://github.com/AP-Elektronica-ICT/iot16-zordon/projects/2 