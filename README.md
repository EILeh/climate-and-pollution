# Software Design - Group assignment
Done in a group of four people!

# ClimateAndPollutionApplication

This project is an application that retrieves air pollution and health data using
OpenWeather, World Health Organization (WHO) and Economic Co-operation and Development
(OECD). The application processes and displays the data for users to get better
understanding of the environmental impact on health.

The application backend is built using Java Spring Boot and frontend using Angular 17.

# System requirements

- Java JDK: Version 17 or newer
- Node.js (LTS)
- Maven to manage dependecies and to build the project
- OpenWeather requires an API key

# Dependencies

All the necessary dependencies are included in the pom.xml file. They are automatically
loaded through Maven.

# Instructions

Before running the program, Java and Node js needs to be installed. Node js can be installed from here:

    https://nodejs.org/en

### Start Program

To start the program, open two commandlines. In one commandline, navigate to

    syntax-squad\backend1\climate-and-pollution

and run thise commands:

`mvn clean install`

`mvn spring-boot:run`

In the second commandline, navigate to

    syntax-squad\frontend\climate-and-pollution

and run these commands:

`npm install`

and

`npm start`

Once the project has been built, this link

    http://localhost:4200/

appears. Put your mouse cursor on top of it, either ctrl + click the link or wait until "Follow this link" appears and click that.
The program opens on the browser.

# Features

The application contains three pages, home, map and chart.
The home page provides an overview of the application.

The map page provides specific and current air pollution data of different cities.

- You can search different cities and choose what air pollution data you want to see by clicking the boxes. To see the data for the
  chosen city, click the blue marker that appears on the map after you have searched for a city.
- If you want to save these choices (city and air pollution values you have chosen), click the "Save Settings" button which saves your choices and shows them when you want to come back.

The chart page provides health data and air pollution data of different countries, shown through charts and graphs.

- You can search different countries and choose what air pollution and health data you want to see by clicking the boxes.
- NOTE!! The chart is a bit slow so the values show up after about 5 seconds.
- Application loads the data after clicking "Search"
- You can also change the timeline you want to examine the data.
- If you want to save these choices (country, air pollution and health data values you have chosen and the time range), click the "Save Settings" button which saves your choises and shows them when you come back.
- To get more specific values, for example you want to see 2012 data, just hover your mouse over the graph and the data will appear.
