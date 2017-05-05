TEAM GRAT: Tim Wang, Ryan Mathur, Galen Chen, Adrienne McCloud.

##Project 3: PokemonYelp

Hunt for and capture Pokemon while you discover local businesses. 

####Possible problems with the app
1: The first time when the user open the app and go to the search activity.
It will ask the user the permission to get the current location.
If the user denies the permission, the whole app will not perform the search function.

2: The lollipop version of android phone will not get the current location service.

3: Connecting the leaderboard with users, the signin function dosent work properly consistantly. But unsure if it is because this occuring in a sandbox.

4: The speed of the pokemon APIs can crash the app. 

5: After two hours of the app being open the API token for yelp will not let the user search. To reset will need to close and reopen the app. 




####Home Activity
The user is able to move to the leaderboard, search activity, and collection from the home activity.There is no functionality except for the ability to move to additional activities. 


####Search Activity 
The user is able to move to the leaderboard, home, collection, and share on social media from the search activity. The user is able to search a location by three criteria the category, type, and name of business. The user will see a list of search results with an image of the business, the name of the business, the first line of the address, the number of ratings, and the star rating. The user will be able to click on the business and taken to the detail view of the business. 


####Detail Activity
The user is able to see the detail view of the business and capture a pokemon. The user will be able to see an image, company name, address of a business. A button at the bottom of the screen allows the user to catch a pokemon. The user is able to return to the home screen.The pokemon associated with the stores are determined using a randomizer. This the activity where the process of capturing pokemon begins. When the user clicks on scan for pokemon, a pokemon will be appear on the screen. This pokemon will be attached to that store for the next 24 hours unless the user catches the pokemon(later screen). When the user clicks on catch pokemon two things can happen: 1 - allowed to catch and redirected to another page or 2- told they are too far away to catch. If option 2 occurs, when the user leaves the page and returns the pokemon will be still be there for the next 24 hours. Users are able to catch pokemon from the same location on different days. 

####Capture Activity
The user is able to capture a pokemon. After the user has chosen to "capture" a pokemon on the detail activity, they will be redirected to this page where they will have the option to capture and share the good news on social media. The page loads with the image of the pokemon and the catch button. Once the user clicks on catch, a pop-up will display informing the user of the name of the pokemon and the option to share on social media. The sharing to social media will connect to the apps predetermined by the user. There is pre-populated text that will fill in for the sharing message. The name of the pokemon, the pokemon id, and yelp id are stored in a database and redisplayed in the collection activity.

####Collection Activity
The user is able to move to the leaderboard, search, home and collection activity. The user is able to see all of the pokemon that they have captured. The data displayed here is from the collection table database. It is a collection of all the pokemon caught by the user in totality. There is no function to clear the collection. The collection is displayed in a grid format where the image and name of the pokemon is displayed. There is no on-click functionality for the collection images.  


####Leaderboard Activity
The leaderboard is designed so that users can share how many pokemon they have caught with friends and other gamers. The user must sign-in with a Google Game Services account inorder to avail themselves of all leaderboard functionality. This is a third party website so the user is redirected to Google Game but it overlays ontop of the app so the user can press the back button to return to the app. As the user adds to the thier collection, the leaderboad updates with the total number of pokemon caught. Uninstalling the app wipes the collection clear. From the leaderboard you can connect and compare play to others. 


<img src="images/final detail1.jpg" width="250"/>
<img src="images/final detail2.jpg" width="250"/>
<img src="images/final capture.jpg" width="250"/>
<img src="images/final leaderboard.jpg" width="250"/>
<img src="images/final search.jpg" width="250"/>
<img src="images/final share.jpg" width="250"/>


####User Stories
1- User can receive notifications to play the game(i.e capture pokemon).

2- User can see detail information about a busisness. 

3- User can catch pokemon inside a business detail view. 

4- User can see pokemon they have caught.

5- User can move to the home screen, leaderboard, and collection between the different screens.

6- User can search by name, type, category, and location from the search activity

7- User can share on social media. 

8- User can view results of a business search.





