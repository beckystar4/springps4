#!/bin/bash

# Define the PostgreSQL command
PSQL="psql --username=postgres --no-password --host=localhost --port=5432"

# Database name
DB_NAME="ps4"

# Check if the database already exists
DB_EXISTS=$($PSQL -t -c "SELECT 1 FROM pg_database WHERE datname = '$DB_NAME';")

if [[ -z $DB_EXISTS ]]; then
    echo "Creating database '$DB_NAME'..."
    $PSQL -c "CREATE DATABASE $DB_NAME;"
    if [[ $? -eq 0 ]]; then
        echo "Database '$DB_NAME' created successfully."
    else
        echo "Failed to create database '$DB_NAME'."
        exit 1
    fi
else
    echo "Database '$DB_NAME' already exists."
fi
