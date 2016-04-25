# MagentaTask

##Description

Application allows you to manage entities.

First you need to load an xml file (to test you can use the one in docs folder), that contains cities and distances between them.

You can watch:

1. All cities
2. Crow flight distances between all cities (it will be counted using formulae)
3. Matrix distances between all cities (using DPS algorithm)

##Run

You can run this program using maven:

**`mvn jetty:run`**

Application will start on port **8770**

##Requires

MySQL server, started on port **3306 root/root** with database **distance-calculator**
