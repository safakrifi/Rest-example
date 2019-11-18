# Rest-example
This is a spring boot app as an excerce that uses API rest services.
# Installation
mvn clean 

# Used Technologies
Most of the technologies are embedded in Spring Boot
Spring Boot-1.5.9-RELEASE.
Java-1.8.
Frameworks : Maven, SpringMVC, Spring-RestTemplate, JSON, Postman.
Data Base :H2 / hsqldb.
Tomcat-8.
IDE : Eclipse-Version: 2019-09 R (4.13.0).

# Specifications for Version 1.0
> [POST] /user 
=>Création d'un utilisateur.  
IN:
request body
{ 
firstName:"John",
lastName:"DOE",
job:"Engineer"
}
OUT:
HTTP CODE: 201
{ 
id:1
firstName:"John",
lastName:"DOE",
job:"Engineer"
}

>[GET] /user
=> retourne toute la liste des utilisateurs
OUT:    
HTTP CODE: 200  
[
{ 
id:1
firstName:"John",
lastName:"DOE",
job:"Engineer"
},
{ 
id:2
firstName:"Jammy",
lastName:"LANISTER",
job:"Student"
},
]

>[GET] /user/:id
  =>récupérer un seul utilisateur par id
Exemple: /user/2
 OUT:    
HTTP CODE: 200
{ 
id:2
firstName:"Jammy",
lastName:"LANISTER",
job:"Student"
}

>[PUT] /user/:id
=> modifier un seul utilisateur par id  
Exemple /user/1
IN:
request body
{ id:1,
firstName:"John",
lastName:"SNOW",
job:"Engineer"
}
OUT:
HTTP CODE: 201
{ 
id:1
firstName:"John",
lastName:"SNOW",
job:"Engineer"
}

>[DELETE] /user/:id
=>suppression d'un seul utilisateur par id  
Exemple: /user/1
 OUT:    
HTTP CODE: 204

#version V2.0

We need to install Mysql and prepare a test databse

