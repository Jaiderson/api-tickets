# api-tickets
Desafío de la codificación Golden-Race que consiste en la implementación de una aplicación web que expone una API REST- full la cual permite registrar tickets de apuestas con sus respectivos detalles.

En esta API los usuarios podrán registrar sus tickets de apuestas la cual tendrá su monto total y fecha de creación del ticket, además contendrá cada uno de los detalles de la apuesta, cada detalle contiene la descripción de la apuesta y el monto apostado a dicho ítem, la sumatoria de los ítems corresponderá al valor total del ticket.

#FUNCIONALIDADES
- Crear ticket le permitirá al usuario crear un ticket con sus respectivos detalles ó si lo desea sin detalles los cuales podrá adicionar posteriormente.
- Agregar un ítem de apuesta (detalle) a un ticket existente.
- Eliminar un ítem de apuesta (detalle) a un ticket existente.
- Eliminar un ticket el cual no solo eliminará el ticket si no también sus ítems (detalles) asociados.
- Buscar un ticket y sus detalles de apuesta dado su Id.
- Buscar tickets y sus detalles entre un rango de fechas.

## Estructura del proyecto
```
tickets-api-spring-boot/
 │
 ├── src/main/java/tickets
 │   └── tickets
 │       ├── configuration
 │       │   └── SpringSecurityConfiguration.java
 │       │
 │       ├── controller
 │       │   └── DetailController.java
 │       │   └── TicketController.java
 │       │
 │       ├── dto
 │       │   └── DetailDto.java
 │       │   └── TicketDto.java
 │       │
 │       ├── entities
 │       │   └── Detail.java
 │       │   └── Ticket.java
 │       │
 │       ├── repositories
 │       │   └── IDetailRep.java
 │       │   └── ITicketRep.java
 │       │
 │       ├── services
 │       │   └── IDetailService.java
 │       │   └── ITicketService.java
 │       │   ├── implemtations
 │       │       └── IDetailServiceImpl.java
 │       │       └── ITicketServiceImpl.java
 │       │
 │       ├── utils
 │       │   └── Util.java
 │       │   └── Wraper.java
 │       │   └── ErrorMessage.java
 │       │   └── MessageResponse.java 
 │       │
 │       └── TicketsApplication.java
 │
 ├── src/main/resources/
 │   └── sql
 │        └── DB.sql
 │
 ├── src/test/java/
 │   └── tickets
 │       ├── services
 │       │   └── TestProvider.java
 │       │   └── DetailServiceTest.java
 │       │   └── TicketServiceTest.java
 │       │   
 │       ├── controllers
 │       │   └── TicketControllerTest.java
 │       │
 │       TicketsApplicationTests
 │
 ├───────
 ├── Dokerfile
 ├── .gitignore
 ├── LICENSE
 ├── mvnw/mvnw.cmd
 ├── README.md
 └── pom.xml
```

## Comandos para ejecucion
Compilacion de artefacto, el cual queda ubicado en la carpeta target
- mvn package   [Genera el jar tickets0.0.1-SNAPSHOT.jar ]
- docker build -t tickets-docker .   [Genera la imagen basada en jdk 8]
- docker run -p 8080:8091 tickets-docker   [Corre la imagen en el puerto 8091]

## Funcionamiento de la API
Para acceder a la documentación del API tickets por favor utilice el siguiente link: 
<a href="https://api-ticket-golden.herokuapp.com/tickets-api/v1/swagger-ui.html">Documentacion API tickets</a>

Para acceder al repositorio del proyecto se debe solicitar permiso al rpositorio provado en Github link: 
<a href="https://github.com/Jaiderson/api-tickets">Repositorio Git API tickets</a>

### Autenticacion Basica
``` Usuario - Contraseña ``` 

## License

**Free Software**
