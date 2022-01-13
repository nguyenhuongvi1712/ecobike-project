# Installation Guideline ##  

## 1. Pre-Requisites 
-	Hardware: laptop or PC
-	Internet connections
-	Database : mysql
-	IDE : IntelliJ 

    **1.1	Install java** 
    -  https://www.oracle.com/java/technologies/downloads/

    **1.2	Setting JAVA_HOME :**
    -  https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux

    **1.3	Install Maven**
    - Apache Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information.
    - In this project, we use Maven to manage a project’s build. 
    - The installation of Apache Maven : 
    https://maven.apache.org/install.html

    **1.4	Install Mysql :**
    - https://www.mysql.com/downloads/

## 2. How to run
-	Setup database: 
-	Create new database (CMD : create database ecobike) 
-	If you are already running the SQL shell, you can use the source command to import data:

        use database_name
        source path_to_data.sql
  

- 	We have provided file ”ecobike.sql” in the folder named that “FinalProject/Construction/ Programming/database”
- 	Then change your database URL port, username, password in "Config.java" file .
For example : 
 
- 	Run : Build project 

## 3. Demo
Link demo project: 
https://www.youtube.com/watch?v=W6oxmRg5Z7Q
