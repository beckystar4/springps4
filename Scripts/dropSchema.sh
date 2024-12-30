#!/bin/bash

PSQL="psql --dbname=ps4 -t --no-align -c"

$PSQL "DROP TABLE IF EXISTS games CASCADE;"
$PSQL "DROP TABLE IF EXISTS publishers CASCADE;"
$PSQL "DROP TABLE IF EXISTS developers CASCADE;"
$PSQL "CREATE SCHEMA public;"
$PSQL " GRANT ALL ON SCHEMA public TO postgres;"
$PSQL " GRANT ALL ON SCHEMA public TO public;"

VALIDATION=$($PSQL "\d" 2>&1)
[[ "$VALIDATION" == *"Did not find any relations."* ]] && echo -e "\n~~~~PS4 DROP SUCCESSFUL~~~~\n"

