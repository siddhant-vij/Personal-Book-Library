#! /bin/bash

export PGUSER='postgres'
export PGPASSWORD='admin'
PSQL="psql --dbname=bookslibrary -t --no-align -c"

echo $($PSQL "TRUNCATE books")

cat "./Problem/input_data.csv" | while IFS="," read ISBN TITLE AUTHOR YEAR
do
  if [[ $ISBN != isbn ]]
  then
    echo $($PSQL "INSERT INTO books (isbn, title, author, publication_year) VALUES ('$ISBN', '$TITLE', '$AUTHOR', $YEAR)")
  fi
done