#!/bin/bash

PSQL="psql --dbname=ps4 -t --no-align -c"

MAIN_MENU(){
    # ADD Menu for user to access specific information:
        # Get all information
        # Get all games titles
        # Get all publishers
        # Get all developers
        # Get all information about all games
        # Get specific information about games
            # based on their name
            # based on their release date
            # based on their publisher
            # based on their developer
        # Get all games by one publisher
        # Get all games by one developer
        # Get the most popular game
        # Get the most recent game
        # Get the oldest game
    INFO=$($PSQL "select game_id, title, publisher, developer from games inner join publishers using(game_id) inner join developers using(game_id);")
    IFS='|' read -r game_id title publisher developer <<< "$INFO"

    while IFS='|' read -r game_id title publisher developer; do
        # Clean up the values (strip leading/trailing spaces)
        GAME_ID=$(echo "$game_id" | xargs)
        TITLE=$(echo "$title" | xargs)
        PUBLISHER=$(echo "$publisher" | xargs)
        DEVELOPER=$(echo "$developer" | xargs)
        
        # Output the formatted string
        echo -e "\n$GAME_ID: $TITLE was developed by $DEVELOPER and published by $PUBLISHER.\n"
    done <<< "$INFO"
}

MAIN_MENU