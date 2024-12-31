This project is to practice writing Java Springboot APIs and relational database management. The project is based aroudn Playstation 4 games based on a dataset from Kaggle. 

Project Overview:
This project includes shelll scripts to manage the database and test data along with the actual Java files. 

**Database Overview:**
The database is comprised of three tables: _**Games**, **Publishers**, and **Developers**._ The Publishers and Developers tables have a foreign key constraint linking them to the Games table. 

Games columns: game_id, title, millions_of_copies_sold, genres, release_date

Publishers columns: publisher_id, publisher, game_id

Developers columns: developer_id, developer, game_id

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
