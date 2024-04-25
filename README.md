# Sales Taxes App Testing Guide

The Sales Taxes App is designed to calculate the receipt details for shopping baskets based on specific tax rules. Basic sales tax is applicable at a rate of 10% on all goods, except books, food, and medical products that are exempt. Import duty is an additional sales tax applicable on all imported goods at a rate of 5%, with no exemptions.

# Getting Started

To try the Sales Taxes App, follow these steps:

 - Clone the code source from: *https://github.com/soufianeAe55/sales-taxes-app*
 - To facilitate the testing process, I have deployed the app on OneRender server, you will find the URL below : <br />
  *https://soufiane-images-latest.onrender.com/swagger-ui/index.html*
 - You can test the API directly from the swagger, you will find also a full documentation of api there.
 - **Note: Due to the inactivity, the requests may be delayed for 50 seconds (it's a free server-.-)**

##Project Structure

I developed the application on several layers that are explained below, and I wrote units tests for all the class that contains some logic with (Junit and mockito), I also handled exceptions globally in the layer of exception to return to the client comprehensive responses:

 - **src/main/java -** Contains the application's source code.
 - **api -** Contains classes application's REST API.
 - **business -** Contains the application's business logic.
 - **config -** Contains configuration files for the application.
 - **dto -** Contains data transfer objects (DTOs) used by the application.
 - **exception -** Contains the application's custom exception classes.
 - **utils -** Contains utility classes used by the application.
 - **validation -** Contains validation logic for the requests received by the application.
 - **src/test/java -** Contains the application's unit tests.

##Running the application

Navigate to the project directory.
Run: mvn spring-boot:run
This will start the application on port 8080 by default.

### Happy Testing! I hope you like the application and enjoy exploring its features. 