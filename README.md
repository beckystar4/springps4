This project is to practice writing Java Springboot APIs and relational database management. The project is based aroudn Playstation 4 games based on a dataset from Kaggle. 

Project Overview:
This project includes shelll scripts to manage the database and test data along with the actual Java files. 

**Database Overview:**
The database is comprised of three tables: _**Games**, **Publishers**, and **Developers**._ The Publishers and Developers tables have a foreign key constraint linking them to the Games table.

Games columns: game_id, title, millions_of_copies_sold, genres, release_date

Publishers columns: publisher_id, publisher, game_id

Developers columns: developer_id, developer, game_id

<img width="990" alt="Screenshot 2025-01-10 at 9 27 52 AM" src="https://github.com/user-attachments/assets/0e5f20d0-b2c0-4745-94f6-5f0387b63e1d" />

**Setting up the Database:**
To use the script files, you must have Postgres installed. On Mac, I used Postgres.app. 

To run Scripts:
  1. Navigate to Scripts folder inside springps4 project folder.
  2. Make sure you have permissions to execute. If you get an error try this command "chmod +x "
  3. To run script: ./ -- For example, ./createDatabase.sh

Scripts Included:
  - createDatabase.sh -- Create a database called ps4 if one does not already exist
  - createTables.sh -- Creates tables for database called ps4. Will present an error if tables already exist or other errors occur.
  - insert.sh -- inserts the test data from games.csv Will present an error if number of test games is not correct or for other errors.
  - truncate.sh -- removes all test data from database and resets SERIAL sequences on all tables
  - dropSchema.sh -- drops all tables from the database
  - dropDB.sh -- drops whole DB
  - ps4.sh -- outputs all the test data in database in a legible format: Ex: 32: Kingdom Hearts III was developed by Square Enix and published by Square Enix.

**Project Structure**

Each table has their own:
  - Request model: used when user can input all information pertaining to a table
  - Response model: used when information is being sent to the user from the database
  - Mapper: Uses RowMapper to set Response values to columns in the database
  - DAO: The business layer that communicates directly with the database to execute the queries
  - Service: Connects the DAO to the Controller
  - Controller: Provides mapping information and formats the data for the user to see

The Complex Dao, Service, and Controller holds queries that span across the tables such as an inner join. 

**Running Queries**

To run a query, start the server by going to the Springps4Application class. Using Postman, you are able to send requests. 
 - Some queries allow you to send a ResponseBody which you can input into Postman in JSON format.
 - Other times, you can put the parameter directly in the path like so, http://localhost:8080/api/v1/?title=God+of+War

**Active Endpoints**
_Games Controller_
 - GET api/v1/games/{title} : Allows user to input title to get details on game. http://localhost:8080/api/v1/games/?title=God+of+War
 - GET api/v1/games/id={game_id} : Allows user to input id to get details on game. http://localhost:8080/api/v1/games/?id=1
 - GET api/v1/games : gets all game details
 - GET api/v1/games/titles : gets all game titles
 - GET api/v1/games/titles-copies-sold : gets all titles and how many copies they sold 
 - GET api/v1/games/titles-release-date : gets all titles and their release dates
 - GET api/v1/games/genres : gets all game genres
 - POST api/v1/games/add-games : inserts game(s)
 - PUT api/v1/games/{game_id} : Updates a game based on game_id. Depending on what is inputted in the response body, the query will update that information
 - DELETE api/v1/games/{game_id} : Deletes a game based on game_id
_Publishers Controller_
 - GET api/v1/publishers : Gets all publisher details
 - GET api/v1/publishers/ : Gets distinct publishers names
 - GET api/v1/publishers/number-of-games-specific : Gets publisher and their game count. /number-of-games-specific?publisher=Sony+Interactive+Entertainment
 - PUT api/v1/publishers/ : Updates publisher by game_id
 - POST api/v1/publishers/ : Inserts publisher(s)
 - DELETE api/v1/publishers/ : Deletes publisher by game_id
_Developers Controller_
 - GET api/v1/developers : Gets all developers details
 - GET api/v1/developers/ : Gets distinct developers names
 - GET api/v1/developers/number-of-games-specific : Gets developers and their game count. /number-of-games-specific?publisher=Sony+Interactive+Entertainment
 - PUT api/v1/developers/ : Updates developers by game_id
 - POST api/v1/developers/ : Inserts developers(s)
 - DELETE api/v1/developers/ : Deletes developers by game_id
_Complex Controller_
 - GET api/vi/ : gets all games, publisher, and developer information. If you input a title, it will get information only for that game.
 - GET api/vi/titles-and-publishers : gets game titles and their publishers
 - GET api/vi/titles-and-developers : gets game titles and their developers
 - GET api/vi/titles-developers-publishers : gets game titles, developers, and publishers
