To start the application, type the following in the browser:http://localhost:8080/login
The following mappings have been implemented(they are subject to change and have been created just for test purposes):
/login
/register
/forgot
/main
/start

To make the application work you are required to add the following environment variables:
For more details see: application.properties
#1   PORT
# for db(according to your local db)
#2   USERNAME
#3   PASSWORD
#4   URL

Now you can enter your url(/main) and convert your url to shorter one.
** Visit count increments when request to short url is made **

PLEASE NOTE: You need to make the following change

Inside MainPageController there is a handler with Model.Please adjust the value of the attribute called
"mapping" to your local server(I mean change port number written there)



