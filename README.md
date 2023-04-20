# pravna-informatika
Projekat iz predmeta Pravna informatika 2022/2023

Aplikacija je testirana na Windows 10 operativnom sistemu.
Da bi se koristila potrebno je:

**1. klonirati repozitorijum**

**2. pokrenuti bekend aplikaciju** (koja je spring boot aplikacija, pa se pokreće komandom

./mvnw spring-boot:run

iz foldera pravna-informatika/backend)  

**3. podesiti environment promenljivu za frontend** tako što će se kreirati fajl sa nazivom

.env.development.local

u folderu pravna-informatika/frontend i u njega upisati

REACT_APP_API_URL='http://localhost:8080/'

jer je to port na kome podrazumevano trči bekend, po potrebi konfigurisati drugačije  

**4. pokrenuti frontend aplikaciju** (koja je react aplikacija, pa se pokreće komandama

./npm install  
./npm start

iz foldera pravna-informatika/frontend)

**5. koristiti aplikaciju iz prozora koji se automatski otvorio, odnosno sa localhost:3000**

Za pregled Akoma Ntoso i ostalih fajlova pogledati pravna-informatika\backend\src\main\resources\documents.  
Za demonstraciju automatske ekstrakcije atributa pokrenuti pravna-informatika/extraction.py skriptu koristeći Python 3.x, ne koriste se nikakvi dependency-i.  

