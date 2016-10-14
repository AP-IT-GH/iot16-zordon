# Internet of Things - [Zordon].
## Beschrijving Project

Zordon zal in staat zijn een relais of een transistor aan te sturen doormiddel van stem bediening. Hiermee kunnen we dus bijvoorbeeld stopcontacten en andere dingen aansturen met behulp van een stem. Je smartphone heb je bijna altijd bij je en is hierdoor het ideale apparaat om dingen aan en uit te schakelen in en rond het huis. Dit aan en uitzetten zal gaan via simpele stemcommando's bijvoorbeeld door te zeggen Zordon, doe het slaapkamerlicht aan  en dan zal het licht aangaan in de slaapkamer. De reden waarom we het via een smartphone doen en niet via een Raspberry PI is omdat die een centraal punt in het huis moet inemen en je er dus altijd naartoe moet lopen mocht je iets willen aansturen. Een smartphone is mobieler en hierdoor niet alleen in het huis te gebruiken maar ook onderweg naar huis of waar ook maar internet te vinden is.

Hoe we dit gaan aanpakken als developer en als hardware producent is door ten eerste een Android applicatie maken. De gebruiker logged zich in op de applicatie en verbind via MQTT met de cloud. In de Android applicatie staan al zijn apparaten die hij gekoppeld heeft met het cloud platform. Wanneer hij dan bijvoorbeeld zegt dat hij de lichten aan wil sturen dan wordt zijn stem opgenomen en doorgestuurd naar de Google cloud service daarin wordt de zin die werd gezegd omgevormd naar tekst, die dan zal doorgestuurd worden naar een cloud die het MQTT protocol ondersteund. In de cloud kijken we dan welke module er moet aangestuurd worden. Dit wordt dan doorgestuurd naar de correcte arduino via MQTT, die dan een relais zal schakelen waardoor de lichten zullen aangaan. Wij zullen een PCB maken waarin een Arduino zit samen met een wifi relais module die in het stopcontact past.

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
