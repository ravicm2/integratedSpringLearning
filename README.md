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
