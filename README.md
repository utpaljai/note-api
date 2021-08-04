# note-api
Spring boot restful api to manage notes

Technology used: Spring boot(2.3.1), spring data JPA, In memory DB(H2)

Prerequisites: Java 8, Maven 3, Git Bash(for checking out Git source code)

# Build and run: 

From git bash terminal, clone note-api from git.

$ git clone https://github.com/utpaljai/note-api.git

Go to the project's root folder, then type:

$ cd note-api

$ mvn spring-boot:run

This will start embedded Apache Tomcat/9.0.36 server

# Swagger documentation - spring rest open api
    http://localhost:8080/swagger-ui-custom.html
    
# Test rest end points using postman

Create one or more notes:

    POST http://localhost:8080/notes/
   
     Under Headers tab in postman- create new http header "user". Give any value to user. This will act like logged  in user. Ideally, this should be JWT token but for simplicity, I am passing user in header. For example, I have given user=utpal

    Request Body-
    [
      {
        "noteId": null,
        "noteText": "test1"
      },
      {
        "noteId": null,
        "noteText": "test2"
      }
    ]

    Response returned:

     [
      {
        "noteId": 1,
        "noteText": "test1",
        "createdByUser": "utpal",
        "updatedByUser": "utpal",
        "createdDate": "2021-08-02",
        "updatedDate": "2021-08-02"
      },
      {
        "noteId": 2,
        "noteText": "test2",
        "createdByUser": "utpal",
        "updatedByUser": "utpal",
        "createdDate": "2021-08-02",
        "updatedDate": "2021-08-02"
      }
    ]


Return all notes

    GET http://localhost:8080/notes/

    Response returned

     [
      {
        "noteId": 1,
        "noteText": "test1",
        "createdByUser": "utpal",
        "updatedByUser": "utpal",
        "createdDate": "2021-08-02",
        "updatedDate": "2021-08-02"
      },
      {
        "noteId": 2,
        "noteText": "test2",
        "createdByUser": "utpal",
        "updatedByUser": "utpal",
        "createdDate": "2021-08-02",
        "updatedDate": "2021-08-02"
      }
    ]



Edit a note with user=utpal(same user who created note)

    PUT http://localhost:8080/notes/1

    Request:
    {
        "noteId": 1,
        "noteText": "test updated"
    }
    
    Response:
    {
    "noteId": 1,
    "noteText": "test updated",
    "createdByUser": "utpal",
    "updatedByUser": "utpal",
    "createdDate": "2021-08-03",
    "updatedDate": "2021-08-03"
    }
    


Edit a note with different user=raj(in request header)

     PUT http://localhost:8080/notes/1
    
    Request:
    {
        "noteId": 1,
        "noteText": "test updated again"
    }
    
    Response:
    {
    "noteId": 1,
    "noteText": "test updated again",
    "createdByUser": "utpal",
    "updatedByUser": "raj",
    "createdDate": "2021-08-03",
    "updatedDate": "2021-08-03"
    }
    


Search note by created by and updated by or note Id or note text

     GET http://localhost:8080/notes/1
    
    Response:
    {
    "noteId": 1,
    "noteText": "test updated again",
    "createdByUser": "utpal",
    "updatedByUser": "raj",
    "createdDate": "2021-08-03",
    "updatedDate": "2021-08-03"
    }
    
    GET http://localhost:8080/notes?createdBy=utpal&updatedBy=utpal
    
    Response:
    [
    {
        "noteId": 2,
        "noteText": "test2",
        "createdByUser": "utpal",
        "updatedByUser": "utpal",
        "createdDate": "2021-08-03",
        "updatedDate": "2021-08-03"
    }
   ]
   
     GET http://localhost:8080/notes?createdBy=utpal&updatedBy=raj&text=test2
    
    Response:
    []
    


Delete one or more notes
    
    DELETE http://localhost:8080/notes?ids=1,2
    

Get all notes - This should now not return any notes

    GET http://localhost:8080/notes
