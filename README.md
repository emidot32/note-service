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
6) `POST /api/notes/{id}/likes?action=` - like a note by ID. Action can be LIKE or DISLIKE. Available only to authorized user. 

The service can be improved by:
1) Adding Swagger for better documentation and comfortable API testing.
2) Using reactive approach with Spring WebFlux if performance does matter.
3) Implementing real security by using an authentication server.