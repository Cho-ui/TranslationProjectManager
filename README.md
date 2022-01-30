# Translation Project Manager
### a server-side programming course project

This back end-web application was created as a course project for the Haaga-Helia University of Applies Sciences' introductory Server-Side Programming course 
in the Fall of 2021. The course project did not have a set business/use case, but rather students were encouraged to come up with their own scenarios 
and develop a CRUD back end application to suit the needs they identified in their scenarios. 
The projects were evaluated on a point scale of 0-100, and graded on a scale of 1-5, 
with this project receiving a full point evaluation of 100/100 and being graded 5. 

## Project case

Having previous contacts and acquaintances in the translation industry, 
I discussed the concept of a simple web application for project management with a friend who was starting a small scale translation company. 
In addition to translation, his business model included working as a broker between his freelance translator contacts and his clients 
for smaller projects that he was not able to take on himself due to scheduling and workload requirements. 
He had also hired an employee whose tasks were divided between translation and forwarding said smaller projects to freelancer contacts. 

We agreed that I would use his business case a scenario for my course project, and if the application project seemed to have some feasibility to it, 
we might progress further with the development of a more customised solution based on lessons learned from the project.

## The deployed app

https://translationmanager.herokuapp.com/

Broker role: broker/broker<br/>
Manager role: manager/manager

### Front end mock up

https://translationmanagerfront.herokuapp.com/offers
(offer retrieval may take a while if the back end app is not awake at Heroku)

## Technical requirements

Although students could come up with their own project case scenarios, firm requirements were provided as regards the technical solutions of the project. 
The requirements were as follows:

1. a Spring Boot Web MVC framework<br/>
2. Thymeleaf templates (with HTML5)<br/>
3. JPA or JDBCTemplate (with either H2 or PostgreSQL)<br/>
4. Spring Boot Security<br/>
5. Bean Validation<br/>
6. Spring Boot REST (JSON)<br/>
<br/>
In addition, at least two of the following requirements needed to be met in the project:
<br/>
1. AJAX JS/JQuery/React, or similar<br/>
2. Testing(JUnit)<br/>
3. l18n (language localization)<br/>
4. A freely chosen Spring Boot feature<br/>

## API documentation

It should be noted that the overall functionality of the application was done with manually created endpoints in controllers using the MVC model, 
as requested in the course project requirement documentation. The REST endpoints below were created as per request, 
but only the unassigned offers-endpoint was utilized in a demonstration of communication between a mock up React front end, and the developed server-side app.

Automatically generated endpoints:

#### GET /api - for the automatically generated api index

Manually built endpoints were needed for requirement 6, they are as follows:

### CLIENTS

#### GET -  all clients from /clients
##### - individual client from /clients/{id}

#### POST - add a client to the base client endpoint /clients

#### PUT - update a client with /clients/{id}

#### DELETE - remove a client with /clients/{id}

### FREELANCERS

#### GET -  all freelancers from /freelancers
##### - individual freelancer from /freelancers/{id}

#### POST - add a freelancer to the base freelancer endpoint /freelancers

#### PUT - update a freelancer with /freelancers/{id}

#### DELETE - remove a freelancer with /freelancers/{id}

### LANGUAGES

#### GET -  all languages from /languages
##### - individual language from /languages/{id}

#### POST - add a language to the base language endpoint /languages

#### PUT - update a language with /languages/{id}

#### DELETE - remove a language with /languages/{id}

### OFFERS

#### GET - all offers from /offers
##### - unassigned offers from /offers/unassigned
##### - assigned offers from /offers/assigned
##### - completed offers from /offers/completed
##### - individual offer from /offers/{id}

#### POST - add an offer to the base offer endpoint /offers

#### PUT - update an offer with /offers/{id}

#### DELETE - remove an offer with /offers/{id}

## Project implementation

The project was developed alongside other school work during an eight week period. As this was a single person project, 
the amount of work required proved to be fairly substantial given the scheduling constraints. 
Thus some critical features for a usable application were left for future development. Features realised included:

- H2 Database structures for clients, offers, freelancers and languages<br/>
- CRUD-operations to all of the above via the realised web ui<br/>
- Security features with two user profiles, Broker and Manager.<br/>
Broker has full CRUD access, whereas the Manager profile has limited access to only Freelancers and Work Offers<br/>
- Bean annotation for fields and forms<br/>
- CSS Layout using a customised bootstrap template<br/>
- Basic JUnit tests for components and repositories<br/>
- A mock-up React front for demonstrating REST-call functionality via offer fetching<br/>

These fulfilled all of the requirements laid out for the course project.

## Implemented technologies

SpringBoot - https://spring.io/projects/spring-boot<br/>
Spring Data JPA - https://spring.io/projects/spring-data-jpa<br/>
Spring Security - https://spring.io/projects/spring-security<br/>
Thymeleaf - https://www.thymeleaf.org/<br/>
H2 Database - https://www.h2database.com/html/main.html<br/>
JUnit 5 - https://junit.org/junit5/<br/>
Bootswatch Simplex Layout - https://bootswatch.com/simplex/<br/>
React - https://reactjs.org/ (for the front end-mock up)<br/>
<br/>
Coffee - https://en.wikipedia.org/wiki/Coffee<br/>

## Project workhours
60h

## Conclusions and further development

As the app development progressed, the amount of work required for a fully functional basic project management tool became increasingly apparent. 
Critical features, such as moving from a test database to an actual database, handling simultaneous use, as well as the security features needed for a business solution, 
are high priorities when considering further development. Functional features should also be developed, such as offer archiving, 
as well as user registration and personal information editing. This would also tie into developing features for freelancer users. 
The application architecture itself should also go under a substantial revision, as although this Thymeleaf-based solution was a valuable educational project 
of what may be encountered in use in various projects, a more modern approach might offer increased flexibility and efficiency in development. 
Thus this project can serve as a basis for further work, but mostly as a demonstration of concepts and key issues for a new application built from ground up. 
As I further my skills, I hope to revisit this project in such a form to both hone the basis for my future professional expertise, 
as well as to perhaps offer an actual solution for my friend who provided me with an extremely interesting project scenario. 
Although, to be candid, I hope his business at that point is booming enough that he has more options than just an aspiring developer's one-man project :)<br/><br/>

Cheers,<br/>
Christian
