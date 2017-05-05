####Manuel Tests

Due to the gps permission on Marshmellow and after running Espresso Tests are a bit more complicated. While there are several work around options, I was unable to fully implement any in the time needed to finish the project. Therefore I am going to do manuel tests. 

####Detail Activity - 
// User will be able to see the detail view of a business
Click on the search button at the bottom of the load screen.
Enter text in the EditText "dunkin".
Click on the search button. 
Click on any business.
The name of the company will be displayed on the detail page
Expected result: the detail information for a business. 

Actual result: Total information for busiiness and a pokemon is visible. 

<img src="images/detailactivity.png" width="250"/> 


//User can scan click on "scan for pokemon" and one will appear
Click on the search button at the bottom of the load screen.
Enter text in the EditText "dunkin".
Click on the search button. 
Click on any business.
The name of the company will be displayed on the detail page
Click on the Scan for Pokemon button. 
Expected result: Pokemon will appear. A toast will display the name of the pokemon. 

Actual result: A pokemon appears. 

<img src="images/scanforpokemon.png" width="250"/> 


//User can click on and catch a pokemon
Click on the search button at the bottom of the load screen.
Enter text in the EditText "dunkin".
Click on the search button. 
Click on any business.
The name of the company will be displayed on the detail page
Click on the Scan for Pokemon button.
Click on the capture button
Expected result: taken to another page

Actual result: takes to the Capture Activity

<img src="images/catchactivity.png" width="250"/> 

####Capture Activity:
//User can capture a pokemon
Click on the search button at the bottom of the load screen.
Enter text in the EditText "dunkin".
Click on the search button. 
Click on any business.
The name of the company will be displayed on the detail page
Click on the Scan for Pokemon button.
Click on the capture button
click on the "capture" button
Expected: Alert dialogue pops-up to congratulate the user. 

Actual: Alert dialogue pops-up with the option to share

<img src="images/captureactivity share on social media.png" width="250"/> 


//User can share on social media
Click on the search button at the bottom of the load screen.
Enter text in the EditText "dunkin".
Click on the search button. 
Click on any business.
The name of the company will be displayed on the detail page
Click on the Scan for Pokemon button.
Click on the capture button
click on the "capture" button
click on Share.
Expected: Options for sharing will popup determined by options available on the users phone

Actual: Options show up for sharing based on the users phone.

<img src="images/captureactivity share on social media.png" width="250"/> 



####Search Activity:

Open the app, click on the “SEARCH” button, go to the search_activity.
Type “ new york” in the edittext for location, and click the image of search. 

Expected: It supposed to see a list of business in city of New York.

Actual: It shows a list of business in city of New York.

<img src="images/searchactivity enter location.png" width="250"/> 

Open the app, click on the “SEARCH” button, go to the search_activity.
Don’t type anything in the edittext for location, and click the image of search. 

Expected: It supposed to see a list of business around your current location.

Actual: It shows a list of business around your current location.

<img src="images/searchactivity current location.png" width="250"/> 

Open the app, click on the “SEARCH” button, go to the search_activity.
Type “Eataly” in the edit text for name,
and type “ new york” in the edittext for location, and click the image of search. 

Expected: It supposed to see a list of business have name with “Eataly” in city of New York.

Actual: It shows a list of business have name with “Eataly” in city of New York.

<img src="images/searchactivity business and location entered.png" width="250"/> 









 