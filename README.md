# integratedSpringLearning
My learning on multiple spring dependencies.
Will be updating one by one .

#Spring Security
##Basic Auth

There are some limitations between basic auth, form based auth and JWT auth. will go one by one here . 

We need to pass the username and password in the request header.
This has to be done for every request to the server. Now, the server makes the validation and sends the response.

#####the master branch will have code for basic auth.

There is no way to logout in basic auth. Because we send username and password for every request. THis is how basic auth works.

####Specifying username and password inside the request headers.
Using postman we can test multiple level of security events and status codes and pass and retrieve headers.
####white listing uris with antMatchers()
For example: the index.html should be accessed by all the users either logged in or not.

##User roles and authorites:
###git checout user-roles-and-authorities
We are gonna user in memory to provide username and password.
It checks for the username, password and roles stored in inMemory and will give access to the requested operation.

###password encode with bycrypt
The password must be encoded. here i ecrypt wit BCryrpt class which is very popular.

###Roles and permissions
defining different permissions for different roles.
Linda an admin can have read and write access for the users and also to the contents. But a user with student role cannot have write permission to the content and can have read/write for his own.
eg: 
* Role: admin , Permission: read/write
* Role: student, Permission: read

###Roles and permissions using Enums

google guava dependency is used to add a parameter for the Enums.

###Role Based Authentication
    



 
