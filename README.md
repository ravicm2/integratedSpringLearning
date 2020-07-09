# integratedSpringLearning
* My learning on multiple spring dependencies.
Will be updating one by one .

#Spring Security
##Basic Auth

* authorization- basic
* HTTPS recommended
* Simple and fast
* can't logout

* There are some limitations between basic auth, form based auth and JWT auth. will go one by one here . 
* We need to pass the username and password in the request header.
* This has to be done for every request to the server. Now, the server makes the validation and sends the response.

#####the master branch will have code for basic auth.

* There is no way to logout in basic auth. Because we send username and password for every request. THis is how basic auth works.

####Specifying username and password inside the request headers.
* Using postman we can test multiple level of security events and status codes and pass and retrieve headers.
####white listing uris with antMatchers()
*  For example: the index.html should be accessed by all the users either logged in or not.

##User roles and authorites and permission based authentications:
###git checout user-roles-and-authorities
* We are gonna user in memory to provide username and password.
* It checks for the username, password and roles stored in inMemory and will give access to the requested operation.

###password encode with bycrypt
The password must be encoded. here i ecrypt wit BCryrpt class which is very popular.

###Roles and permissions
* defining different permissions for different roles.
Linda an admin can have read and write access for the users and also to the contents. But a user with student role cannot have write permission to the content and can have read/write for his own.
eg: 
* Role: admin , Permission: read/write
* Role: student, Permission: read

###Roles and permissions using Enums

* google guava dependency is used to add a parameter for the Enums.

###Role Based Authentication
* make csrf.disable()
* .hasRole()
if we give role as ADMIN the roles() method in UserDetails will append the prefix of ROLE_. So the role will be ROLE_ADMIN.
###Permission based authentication
.hasAuthorities()
or by annotations at the method level @PreAuthorize.

###Order does matter
How the ant matchers works ?
* When a user logs in , in basewebsecurityconfig it checks line by line antmatchers.
Whether the user has access for the first antmatcher and then next
* So its important  to maintain the orders of antMatchers
* So the best practise is to keep the authorities first and then roles in the antMatchers.
* Refer the BaseWebSecurityCOnfig class in this application.

###Permission based authentication using annotation
* Using @PreAuthorize("ROlE_" and "AUTHORITY")
checks the role and permission before executing the methods/ service.
THis annotation replaces the antMatchers() for particular method call and for authority.
and change configuration accordingly that you want to use @PreAuthorize instead .antMatchers
Add @EnableGlobalMethodSecurity at BaseWebSecurityConfig class and set prepostEnabled as true.

##Understanding CSRF
CSRF- cross site request forgery
* when the csrf isEnabled and the frontend logs in to our application,
the spring security in our application server sends the csrf token to the frontend.
* By using that token frontend does form submission(PUT,POST,DELETE).
* Now the server validates the token whether it matches the sent one.
* when the csrf token isEnabled there is no way the attacker can know what our application does with the form submittion from the front end and also he cant know what the server has generated for the csrf token.

* So, if we enable the csrf , then we need to pass again the token back with the form submission to make the above functionalities work.
Thats why , when we execute service in postman with csrf disabled , it can perform only the read operation alone not others(PUT,POST,DELETE). Because we aren't sending back the csrf token and the validation at server fails and throws 403.

######According to csrf, they recommend using it when there is a request has to be processed by a browser. For non-browser clients its likely good to disable it.

To do post request from postman:
* install postman intercepter in the postman
* do a get call
* in the cookies section, take the xsrf token
* and pass that in the headers tab with the key X-XSRF-TOKEN and the value copied from cookies.

###Form based authentication
* username and password
* standard in most websites
* forms(full control)
* can logout
* Https recommended

#####How this works:
* the client sends the user name and password to the server.
* the server validates the credentials and sends OK and a cookie SESSIONID
* All other calls sent with the same cookie ID and it is validated in server and sends response.
* **This cookie session id expires after 30 mins of inactivity.**

It uses the inMemoryDatabase to validate the cookie sessionId.
It's good to validate using real database.

* Spring thyme leaf dependency used to work with html files in spring boot.
* Here we are going to use it to override the default login page from  spring security.
* create a html file as a login page
* create a new controller class to return the login page. See TemplateController.java for reference.

**Todo:incomplete will learn after jwt** 

##Database authentication
* Instead of inMemory users , its good to create users in real DB.

##Jwt authentication
* jwt - json web token
* jwt used when multiple 3rd party application tries to access our servers.
* Using form based or basic auth is very much difficult to use in this scenario.
* Because all application will now have same handling methodology of security.

####Pros
* Fast
* Stateless - no need to store in DB . everything stored in token like session.
* used across many services

####Cons
* compromised secret key
* no visibility to logged in users
* token can be stolen

How JWT works?
* Client sends the credentials to the server
* the server validates token, creates and signs token
* sends token to the client
* client sends the token for each request

####Encoded token `refer jwt.io site`
* the encoded token has 3 parts
* the header - has an algorithm and token type
* the payload - actual information(sub, name & iat)
* the signature - signature base64UrIEncode and base64 secret signature

claim method in jwts is to specify the body(payload) of the token.

####Request filters
* when a request tries to reach the destination( server/api)
* the filters in between the request and api used to validate the request before reaching the api
* the filters can be as many as possible from 1 to n.
* In our case, we created a filter `JwtUsernameAndPasswordAuthenticationFilter` which extends `UsernamePasswordAuthenticationFilter` from spring security and validates the incoming credentials,
creates token for it and sends back to client.
* Once the work of 1 filter gets over then it needs to pass the request and response to the next filter.

#######In post man you do post request of login and in headers you can find the jwt token. Paste that in the jwt.io page and check the decoded results of the encoded token.
