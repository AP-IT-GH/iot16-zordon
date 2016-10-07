# Internet of Things - [Zordon].
## Beschrijving Project

Wij gaan een applicatie dat een relais zal aansturen door middel van voice control. Hiermee kunnen we dus bijvoorbeeld stopcontacten en andere dingen aansturen met behulp van je stem. Hoe we dit gaan tewerk stellen doen we op volgende manier. We hebben een gebruiker, die een smartphone heeft. Je smartphone heb je altijd in de buurt of toch meestal. Die wilt bijvoorbeeld dat er een lichtje in de slaapkamer aangaat wanneer hij wakker wordt. Wat hij dan doet is hij zegt “Zordon, doe het slaapkamer licht aan” en dan zal het licht aangaan van de slaapkamer. De reden waarom we het via een smartphone doen en niet via een Raspberry PI is omdat die een centraal punt in het huis en je dus altijd naar daar moet lopen wil je iets aansturen. Een smartphone is mobieler.

Hoe we dit gaan aanpakken als developer en als hardware producent is door ten eerste een android applicatie maken. De gebruiker logged zich in op de applicatie en verbind zo met de cloud. In de cloud staan al zijn apparaten die hij gekoppeld heeft in het huis. Wanneer hij dan bijvoorbeeld zegt dat hij de lichten aan wilt sturen dan wordt zijn stem opgenomen en doorgestuurd naar de Google cloud service daarin wordt de zin die werd gezegd omgevormd naar tekst, die dan zal doorgestuurd worden naar een cloud die het MQTT protocol ondersteund. In de cloud kijken we dan welke module er moet aangestuurd worden. Dit wordt dan doorgestuurd naar de correcte arduino over wifi, die dan een relais zal schakelen waardoor de lichten zullen aangaan. Wij zullen een PCB maken waarin een Arduino zit samen met een wifi relais module die in het stopcontact past.

Dit is ons basis project, dit willen we uitgevoerd krijgen tegen het einde van het jaar. Als we tijd over hebben kunnen we nog extra features toevoegen. We denken momenteel aan volgende features. Ons idee is momenteel om het via stopcontacten te doen, we denken er aan om het misschien via lichtschakelaars te doen, al denken we wel dat we hier niet voldoende tijd voor zullen hebben. Momenteel is onze applicatie ook één enkele taal dat wordt ondersteund, de bedoeling is om dit uit te breiden naar meerderen. Ook willen we een soort talk-back systeem maken, zodat als je zegt dat de lichten aan moet, dat het systeem dan terug zegt dat de lichten aan zijn, of dat het niet is gelukt. Momenteel is dit ook allemaal voor geprogrammeerde zinnen, we willen dit uit breiden naar dat de gebruiker kan zeggen van dit is de zin die ik wil dat dit aanstuurt, en dit is de zin die ik terug wil krijgen.

Voor meer info:

https://github.com/AP-Elektronica-ICT/iot16-zordon/blob/master/doc/Projectplan.md

## Documentatie
https://github.com/AP-Elektronica-ICT/iot16-zordon/tree/master/doc
## Groepsleden
#####Boyd Franken

#####Kenny Guldentops

#####Gillian Lambrechts

#####Jeroen Rietveld
