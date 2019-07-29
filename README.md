# Jee + Hibernate + JPA + Spring Framework App Sample

This a simple proof of concepts, using Java with JEE definitions, with Hibernate as ORM with JPA and Spring Framework. Its also using Log4J and jUnit, mangaging dependencias with Maven 

The architecture use a Data Access Layer with DAO, DataServices and Entities, orchested by Service Layer, separing services definitions (through interfaces) and implementations (classes), with the help of business helpers, and DTOs for interaction with consumer (clients) that could be the same web front end app or anothers apps consuming his services.

