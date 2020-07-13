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

** Improvement ** The user is redirected to an error page in case of wrong short url
(url which has not been added to the system)

---------------------------------------------------------------------------------------------------------

04.07.2020      14:00  -----> Relational Database

Relations have been created between user and urls (/main/{user_id})
Each user can only see urls entered and converted by themselves on the main page.

For now, you need to go to main/{user.id} to see individual user's page/convert urls. However, this will change after
Spring Security login and registration are implemented(there will be no need to pass user_id through path)
Each user can add the same url just once. If they try to add the url which they have converted
before they will be redirect to error page.

---------------------------------------------------------------------------------------------------------
10.07.2020       12:00  ------> Custom Login Page is working now

In my opinion, it did not work because of index.html as it did not include thymeleaf and therefore, post method
could not be forwarded to "/login" properly .
I added the following line to index.html:
<form name="f" method="post" th:action="@{/login}" class="login-section-container-form">
