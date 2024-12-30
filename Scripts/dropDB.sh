PSQL="psql --dbname=ps4 -t --no-align -c"

$PSQL "DROP DATABASE ps4 WITH (FORCE);"