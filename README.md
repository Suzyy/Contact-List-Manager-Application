# Contact-List-Manager-Application

This is an application that lets users to Create, Read, Update, and Delete contacts and notes.

To access endpoints, jwt authentication is required which can be accessed at: 
https://contact-list-manager-application.azurewebsites.net/authenticate 

Once authentication is successful with authorized 'userName' and 'password', other endpoints can be accessed.

Contact CRUD endpoints can be accessed at:
(GET) https://contact-list-manager-application.azurewebsites.net/v1/contacts/getAllContacts
(GET) https://contact-list-manager-application.azurewebsites.net/v1/contacts/getContactById/{id}
(POST) https://contact-list-manager-application.azurewebsites.net/v1/contacts/addContact
(POST) https://contact-list-manager-application.azurewebsites.net/v1/contacts/updateContactById/{id}
(DEL) https://contact-list-manager-application.azurewebsites.net/v1/contacts/deleteContactById/{id}

Note CRUD endpoints can be accessed at:
(GET) https://contact-list-manager-application.azurewebsites.net/v1/notes/getNotesById/{id}
(POST) https://contact-list-manager-application.azurewebsites.net/v1/notes/addNotes
(POST) https://contact-list-manager-application.azurewebsites.net/v1/notes/updateNotesById/{id}
(DEL) https://contact-list-manager-application.azurewebsites.net/v1/notes/deleteNotesById/{id}

Note: 'getAllContacts' endpoint is only accessable by a user with 'ROLE_ADMIN'.

For API documentation and testing, mvn clean install, run java, and go to "http://localhost:9090/swagger-ui/html.index".
Alternatively, for testing, use Postman to directly call endpoints above.
 
