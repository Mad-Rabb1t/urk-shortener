To start the application, type the following in the browser:http://localhost:PORT/login
The following mappings have been implemented:
/login
/register
/forgot
/main
/start
/reset
/sh
/confirm
*** IMPORTANT ***
To make the application work you are required to add the following environment variables:
For more details see: application.properties
#1   PORT
# for db(according to your local db)
#2   USERNAME
#3   PASSWORD
#4   URL
------------------------------------------------------------------------------------------------------------------------
PLEASE NOTE: You need to make the following change to the application !!!

In order the application to work properly,please modify String root in the following address:
app->entity->ApplicationDetails

!!!! After you register you have to confirm your account via email sent !!!!

------------------------------------------------------------------------------------------------------------------------

Now you can enter your url(/main) and convert your url to shorter one.
** Visit count increments when request to short url is made **
** The user is redirected to an error page in case of wrong short url
(url which has not been added to the system) **
** You can reset your password if you have an access to your registration e-mail **

Link to HerokuApp: https://cut-ly.herokuapp.com

User credentials on HerokuApp(for test purposes):
Email: random_user@mail.ru
Password: 22

User with real email:
Email: r3xf0x@yandex.ru (password of mail: Rexyf0x0505)
Password: 123





Development stages of the application
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

---------------------------------------------------------------------------------------------------------
14.07.2020       10:40  ------> Minor changes

#In order the application to work properly,please modify String root in the following address:
app->entity->ApplicationDetails

#If user enters invalid url into converter, they will get an error page and
their request will not be processed (saved into db)
Invalid URL is the string without protocols(http://,https://,ftp://) and proper URL syntax.
----------------------------------------------------------------------------------------------------------
19.07.2020       11:00  -----> Email verification has been implemented
Improvements are the following:

#1--> After you register you have to confirm your account via email sent.
#2--> Unless you confirm it within a day, your credentials will expire and user details will be removed;
you will need to register again.
#3--> You can not confirm already confirmed account.
#4--> You can not login or use forgot-password function before you confirm your account.
#5--> If any error occurs with external API, user is redirected to Custom Error Page
