#!/bin/bash

PSQL="psql --dbname=ps4 -t --no-align -c"

MAIN_MENU(){
   if CREATE_GAMES;
   then
    echo -e "\n~~~~GAMES TABLE CREATION SUCCESSFUL~~~~\n"
   else
     echo -e "\n~~~~GAMES TABLE CREATION FAILED~~~~\n"
     exit 1
   fi

   if CREATE_PUBLISHERS;
   then
      echo -e "\n~~~~PUBLISHERS TABLE CREATION SUCCESSFUL~~~~\n"
   else
      echo -e "\n~~~~PUBLISHERS TABLE CREATION FAILED~~~~\n"
      exit 1
   fi

   if CREATE_DEVELOPERS;
   then
     echo -e "\n~~~~DEVELOPERS TABLE CREATION SUCCESSFUL~~~~\n"
   else
     echo -e "\n~~~~DEVELOPERS TABLE CREATION FAILED~~~~\n"
     exit 1
   fi
}

CREATE_GAMES(){
   $PSQL "CREATE TABLE games(game_id SERIAL PRIMARY KEY);" && \
   $PSQL "ALTER TABLE games ADD COLUMN title VARCHAR(100) NOT NULL;" && \
   $PSQL "ALTER TABLE games ADD UNIQUE(title);" && \
   $PSQL "ALTER TABLE games ADD COLUMN genres VARCHAR(100);" && \
   $PSQL "ALTER TABLE games ADD COLUMN millions_of_copies_sold int;" && \
   $PSQL "ALTER TABLE games ADD COLUMN release_date date NOT NULL;"
}

CREATE_PUBLISHERS(){
    $PSQL "CREATE TABLE publishers(publisher_id SERIAL PRIMARY KEY);" && \
    $PSQL "ALTER TABLE publishers ADD COLUMN publisher VARCHAR(100) NOT NULL;" && \
    $PSQL "ALTER TABLE publishers ADD COLUMN game_id INT REFERENCES games(game_id) ON DELETE CASCADE;"
}

CREATE_DEVELOPERS(){
    $PSQL "CREATE TABLE developers(developer_id SERIAL PRIMARY KEY);" && \
    $PSQL "ALTER TABLE developers ADD COLUMN developer VARCHAR(100) NOT NULL;" && \
    $PSQL "ALTER TABLE developers ADD COLUMN game_id INT REFERENCES games(game_id) ON DELETE CASCADE;"
}

MAIN_MENU