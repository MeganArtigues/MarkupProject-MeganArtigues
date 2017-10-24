MarkupProject
=============

Info
----
Create a class in the language of your choice that will read HTML content input and score and give
an arbitrary score based on a set of rules. The content should be assigned a unique id based on the prefix described below.
Changes to the content can be re-run over time to determine improvement or regression of the score. Each unique run should be stored with the date and time it was run along with the score received for the content.

You may use external libraries if you feel they will help, but you must place them in the appropriate folder based on the project layout section.

Languages and tools used:
----------------------------
-Java
-MySQL Workbench 5.7.17
    (You will also need MySql Community Installer if you are using a Windodws machine)
-Java IDE for Java EE Developers
    (https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/keplersr2)
-Using mysql-connector-java-5.1.39-bin as the JDBC driver
    (located in WebContent->WEB_INF-> lib
-Windows 10

Instructions:
-------------
1. Import SQL dump file into MySql database
    a. Click "Data Import/Restore"
    b. Select "Import from Self-Contained File"
    c. "Start Import"
2. Find class "DBAccessConfig" in the persistance package
    a. make sure you change your username and password accordingly in order to connect to the database
3. Copy all files into a new "Dynamic Web Project" in Eclipse and run on the server
    Note:
        classes, object, persistance, and sevlet packages go under Java Resources - > src
        WebContent can replace the auto-generated folder that is created when you make a new web project
4. Go to "http://http://localhost:8080/MarkupProject/" to use the web project

--Or you can run the .war file on a private server