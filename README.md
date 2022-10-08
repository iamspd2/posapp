<h1 align="center">Bhojan - A Food Ordering App</h1>

## Disclaimer
This is a personal project aimed at learning and demonstrating Android development skills. All the likeness of restaurant names and their related info are only aimed at reflecting the usability of this app (namely the location feature). Furthermore, these details are obtained from Zomato API, which is open-sourced. None of the transactions shown below involve the restaurants, in fact the menu and prices used are dummy. However, the payments are legit, and I've used my personal GPay accounts. The APK you build from this codebase will not process payments by default, and return back to the Home page with a 'success' status. You can enable payments by providing the appropriate GPay ids in the payments code logic.

Let us see the screens and understand how the app works!

<!-- <img src="" width="270"/> -->

## Sign Up
This is the screen where a new user registers themselves to this app. Proper validation checks have been added to it, besides Firebase Auth taking care of the registration process.

<img src="https://user-images.githubusercontent.com/48145355/100762073-93987100-3419-11eb-9653-5f07cb5e48a0.jpg" width="270"/>

## Sign In
This is the screen where an already registered user signs in to this app. The credentials are checked against the Firebase Auth registered users for the app. Upon successful login, the user is redirected to ‘Profile’ screen, or an appropriate message is displayed.

<img src="https://user-images.githubusercontent.com/48145355/100761992-83809180-3419-11eb-9841-6b98c5dbf306.jpg" width="270"/>

## Firebase Authentication
This is the Firebase Authentication page of the console that the app is registered with. This page contains the list of users that have successfully registered with the app. It contains the user details, with their hashed passwords along with sign-in history.

<img src="https://user-images.githubusercontent.com/48145355/100773480-f0e6ef00-3426-11eb-8f35-d497eac21fb4.gif"/>

## Profile
This is the screen where a user is redirected after logging in, or signing up. This page would contain a history of past orders as shown in left. The list of orders are sorted as ‘latest first’. If the user is yet to make their first order, it’d prompt them as below. The menu on the top-right would allow the user to make a new order or logout from the app.

<img src="https://user-images.githubusercontent.com/48145355/100762259-c80c2d00-3419-11eb-9be5-f8e17ca06d70.jpg" width="270"/>

## Order
This is the screen where a user makes a selection of a restaurant to make an order from. Google Maps API is used for getting the Maps feature, and centers by default to the current location, and displays nearby restaurants, based on user ratings. The user can also view them based on ‘sort by’ distance as shown below.

<img src="https://user-images.githubusercontent.com/48145355/100761548-0a813a00-3419-11eb-8f26-a9e8c4bc89e5.jpg" width="270"/>

## Search Facility
The screen on the left, as it can be seen, is of the results that is shown when the user searches for ‘Seattle’ on the search facility provided within the app. The app, by default, fetches a list of restaurants in the given area based on the average user ratings. But the user can opt to sort the results by ‘distance’ to find the closest restaurants or ‘cost’ to find the cheapest places. The Google Places API was used parallely with the Zomato API to achieve this result.

<img src="https://user-images.githubusercontent.com/48145355/100761895-66e45980-3419-11eb-90c1-b787fa6517c0.jpg" width="270"/>

## Tab
This is the screen for the ’Tab’. This is where the user orders items from the restaurant/bar they selected. It works alongside the Omnivore API, and the Android code takes care of opening, closing tabs, ordering items, generating tickets, etc. The menu, as seen in left, is the menu obtained from the API that generates a virtual restaurant. The user name is obtained from the user data from the Firebase RTDB, that was entered during the registration process.

<img src="https://user-images.githubusercontent.com/48145355/100761608-1836bf80-3419-11eb-9581-9a91e5b0f39a.jpg" width="270"/>

## Checkout
This is the screen that is displayed once the user has ‘closed’ the Tab. It contains a breakdown of the items that are ordered, the quantities in which they are ordered, and their corresponding subtotals. A GST of 5% is added to the subtotal, and a total amount is generated.
 
Now, on clicking on ‘Pay Now’, the user is redirected to the Google Pay payment page without leaving the app.

<img src="https://user-images.githubusercontent.com/48145355/100761318-c68e3500-3418-11eb-9164-a8e412271c82.jpg" width="270"/>

## Payment
This is the payment screen of Google Pay, that is opened upon successful verification of the user. It is done either by fingerprint verification or a Google PIN. This page is opened from within the app without leaving the app. The payment amount and a message with the restaurant name are generated automatically and doesn’t need any user involvement except paying it in a single tap.
 
The user is redirected to their preferred payment option, and upon the completion of the transaction (success/failure), the user is redirected to the ‘Profile’ screen.

<img src="https://user-images.githubusercontent.com/48145355/100778017-8042d100-342c-11eb-8c88-22f7eec94936.jpg" width="270"/>

## Successful Payment
Upon the completion of transaction, the user is redirected back to the ‘Profile’ screen with the updated list of orders and a prompt mentioning the transaction been successful upon successful payment. Otherwise, the prompt mentions that the transaction failed along with the reason for failure, that is provided by Google Pay API.

<img src="https://user-images.githubusercontent.com/48145355/100761772-461c0400-3419-11eb-8e53-5740297e2a7b.jpg" width="270"/>

## Firebase Realtime Database
The animation below is a live recording of the RTDB in Firebase console. As soon as the transaction is successful, the entry of the order is made into the database, with the fields as cost, items, restaurant name, and the timestamp. The timestamp is the server time of the database when the order was placed, and is converted into readable date and time format in the app. Moreover, each order has got a unique 7 character alphanumeric transaction ID that could be used to track the different orders that an user has made.

<img src="https://user-images.githubusercontent.com/48145355/100761635-208efa80-3419-11eb-8d49-84375562cc5b.gif"/>
