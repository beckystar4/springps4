#!/bin/bash

PSQL="psql --dbname=ps4 -t --no-align -c"

# Run TRUNCATE command
$PSQL "TRUNCATE games, publishers, developers;"
if [[ $? -ne 0 ]]; then
    echo "Error truncating tables"
    exit 1
else
    echo "Tables truncated successfully"
fi

# Restart sequence for games
$PSQL "ALTER sequence games_game_id_seq RESTART WITH 1;"
if [[ $? -ne 0 ]]; then
    echo "Error resetting games sequence"
    exit 1
else
    echo "Games sequence reset successfully"
fi

# Restart sequence for publishers
$PSQL "ALTER sequence publishers_publisher_id_seq RESTART WITH 1;"
if [[ $? -ne 0 ]]; then
    echo "Error resetting publishers sequence"
    exit 1
else
    echo "Publishers sequence reset successfully"
fi

# Restart sequence for developers
$PSQL "ALTER sequence developers_developer_id_seq RESTART WITH 1;"
if [[ $? -ne 0 ]]; then
    echo "Error resetting developers sequence"
    exit 1
else
    echo "Developers sequence reset successfully"
fi
