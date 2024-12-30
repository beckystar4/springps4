#!/bin/bash

PSQL="psql --dbname=ps4 -t --no-align -c"

MAIN_MENU(){
    if INSERT_GAMES; then
        echo -e "\n~~~~GAME INSERTION SUCCESSFUL~~~~\n"
    else
        echo -e "\n~~~~GAME INSERTION FAILED~~~~\n"
        exit 1
    fi

    if INSERT_PUBLISHERS; then
        echo -e "\n~~~~PUBLISHER INSERTION SUCCESSFUL~~~~\n"
    else
        echo -e "\n~~~~PUBLISHER INSERTION FAILED~~~~\n"
        exit 1
    fi

    if INSERT_DEVELOPERS; then
        echo -e "\n~~~~DEVELOPER INSERTION SUCCESSFUL~~~~\n"
    else
        echo -e "\n~~~~DEVELOPER INSERTION FAILED~~~~\n"
        exit 1
    fi

    VALIDATION=$($PSQL "SELECT COUNT(*) from games;" 2>&1)
   # Check if the query succeeded
   if [[ $? -ne 0 ]]; then
       echo "Error executing query: $VALIDATION"
       exit 1
   fi

   if [[ "$VALIDATION" == "32" ]]; then
       echo -e "\n~~~~PS4 INSERT SUCCESSFUL~~~~\n"
   else
       echo -e "\n~~~~PS4 INSERT FAILED~~~~\n"
       echo "Expected 32 games, but found $VALIDATION games."
   fi
}

INSERT_GAMES(){
    skip_headers=1
    while IFS=, read -r games copies_sold release_date genres developers publishers
    do
        if ((skip_headers))
        then
            ((skip_headers--))
        else
            # Escape single quotes in game title and genres
            games_escaped=$(echo "$games" | sed "s/'/''/g")
            genres_escaped=$(echo "$genres" | sed "s/'/''/g")

            # Ensure release_date is properly formatted (you can format it beforehand as needed)
            # You can also validate and reformat the release_date if required

            # Prepare the query
            SQL_QUERY="INSERT INTO games(title, millions_of_copies_sold, release_date, genres) VALUES ('$games_escaped', $copies_sold, '$release_date', '$genres_escaped');"

            # Run the SQL query
            $PSQL "$SQL_QUERY"
            if [[ $? -ne 0 ]]; then
                echo "Error inserting game: $games"
                echo "Failed SQL: $SQL_QUERY"
                return 1
            fi
        fi
    done < games.csv
}


INSERT_PUBLISHERS(){
    skip_headers=1
    while IFS=, read -r games copies_sold release_date genres developers publishers
    do
        if ((skip_headers))
        then
            ((skip_headers--))
        else
            GAME_ID=$($PSQL "SELECT game_id from games WHERE title='$games';")
            if [[ $? -ne 0 || -z "$GAME_ID" ]]; then
                echo "Error retrieving game ID for: $games"
                return 1
            fi
            $PSQL "INSERT INTO publishers(publisher, game_id) VALUES ('$publishers', '$GAME_ID');"
            if [[ $? -ne 0 ]]; then
               echo "Error inserting publisher for game: $games"
               return 1
            fi
            $PSQL "UPDATE publishers SET publisher = RTRIM(publisher, E'\r');"
            if [[ $? -ne 0 ]]; then
                echo "Error updating publisher for game: $games"
                return 1
            fi
        fi
    done < games.csv
}

INSERT_DEVELOPERS(){
    skip_headers=1
    while IFS=, read -r games copies_sold release_date genres developers publishers
    do
        if ((skip_headers))
        then
            ((skip_headers--))
        else
            GAME_ID=$($PSQL "SELECT game_id from games WHERE title='$games';")
            if [[ $? -ne 0 || -z "$GAME_ID" ]]; then
                echo "Error retrieving game ID for: $games"
                return 1
            fi
           $PSQL "INSERT INTO developers(developer, game_id) VALUES ('$developers', '$GAME_ID');"
            if [[ $? -ne 0 ]]; then
               echo "Error inserting developer for game: $games"
               return 1
            fi
        fi
    done < games.csv 
}

MAIN_MENU