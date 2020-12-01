<h1 align="center">PoS - Restaurant App</h1>

This Android project is a complete restaurant ordering app that takes care of all the Point of Sales (PoS) in a system.

## PoS and its needs
A point of sale system (PoS) is the place where the customer makes a payment for products or services at our store. Every time a customer makes a purchase at your store, they’re completing a PoS transaction.
It serves as the central component for your business: it’s the hub where everything, like sales, inventory and customer management merge. An efficient PoS system ensures that all operations are running smoothly in a retail environment, like bars and restaurants.

Let us see the screens and understand how the app works.

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
