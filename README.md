# note-service
Test task for an interview

This service is an implementation of secured CRUD operations for notes. 

It is implemented using `Java 17`, `Spring Boot`, `Spring Security`, `Spring Data MongoDB`, `Docker`, `JUnit5`.

The service provides the following API: 
1) `GET /api/notes` - get a list of all notes sorted by creation date in descending order. Available to all users.
2) `GET /api/notes/{id}` - get a specific note by ID. Available to all users.
3) `POST /api/notes` - create a new note. Body example: ```{"value": "Note", "likes": 4}```. Available to all users.
4) `PUT /api/notes/{id}` - update an existing note. Available only to authorized user. 
5) `DELETE /api/notes/{id}` delete a note by ID. Available only to authorized user. 
6) `PATCH /api/notes/{id}/like` - like a note by ID. Available only to authorized user. 
7) `PATCH /api/notes/{id}/dislike` - dislike a note by ID. Available only to authorized user.

For running the service you need:
1) Install Docker and docker-compose
2) Clone project `git clone https://github.com/emidot32/note-service.git`
3) Run `docker-compose up --build` in target folder

You can authenticate as user with username 'name' and password '123'.

The service can be improved by:
1) Adding Swagger for better documentation and comfortable API testing.
2) Using reactive approach with Spring WebFlux if performance does matter.
3) Implementing real security by using an authentication server.
4) Adding count for each user who like a note for avoiding endless liking of a note by one user.
5) Using DTO for the Note entity with MapStruct for their mapping.