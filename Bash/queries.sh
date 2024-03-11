#! /bin/bash

export PGUSER='postgres'
export PGPASSWORD='admin'
PSQL="psql --dbname=bookslibrary -t --no-align -c"

echo -e "Retrieve all books:" > output.txt
echo "$($PSQL "SELECT * FROM books;")" >> output.txt

echo -e "\n\n\n\n\nFind books by a specific author:" >> output.txt
echo "$($PSQL "SELECT * FROM books WHERE author = 'Onofredo Flay';")" >> output.txt

echo -e "\n\n\n\n\nList all books published before a certain year:" >> output.txt
echo "$($PSQL "SELECT * FROM books WHERE publication_year < 2010;")" >> output.txt

echo -e "\n\n\n\n\nCount the total number of books in the library:" >> output.txt
echo "$($PSQL "SELECT COUNT(*) FROM books;")" >> output.txt

echo -e "\n\n\n\n\nFind books with titles containing a specific word:" >> output.txt
echo "$($PSQL "SELECT * FROM books WHERE title LIKE '%sql%';")" >> output.txt

echo -e "\n\n\n\n\nList all authors (without duplicates):" >> output.txt
echo "$($PSQL "SELECT DISTINCT author FROM books;")" >> output.txt

echo -e "\n\n\n\n\nUpdate the author name for all books by a specific author:" >> output.txt
echo "$($PSQL "UPDATE books SET author = 'Homer Simpson' WHERE author = 'Onofredo Flay';")" >> output.txt

echo -e "\n\n\n\n\nDelete all books published before a specific year:" >> output.txt
echo "$($PSQL "DELETE FROM books WHERE publication_year < 2006;")" >> output.txt

echo -e "\n\n\n\n\nRetrieve all books sorted by publication year (newest first):" >> output.txt
echo "$($PSQL "SELECT * FROM books ORDER BY publication_year DESC;")" >> output.txt

echo -e "\n\n\n\n\nFind the oldest book in the library:" >> output.txt
echo "$($PSQL "SELECT * FROM books ORDER BY publication_year ASC LIMIT 1;")" >> output.txt

echo -e "\n\n\n\n\nFind the number of books published each year:" >> output.txt
echo "$($PSQL "SELECT publication_year, COUNT(*) FROM books GROUP BY publication_year;")" >> output.txt

echo -e "\n\n\n\n\nList the top 5 most frequently occurring authors in the library:" >> output.txt
echo "$($PSQL "SELECT author, COUNT(*) FROM books GROUP BY author ORDER BY COUNT(*) DESC LIMIT 5;")" >> output.txt

echo -e "\n\n\n\n\nIdentify books that have titles longer than 15 characters:" >> output.txt
echo "$($PSQL "SELECT * FROM books WHERE LENGTH(title) > 15;")" >> output.txt

echo -e "\n\n\n\n\nCalculate the average publication year of all books in the library:" >> output.txt
echo "$($PSQL "SELECT AVG(publication_year) FROM books;")" >> output.txt

echo -e "\n\n\n\n\nSelect all authors who have published more than one book in the same year." >> output.txt
echo "$($PSQL "SELECT author FROM books GROUP BY author, publication_year HAVING COUNT(*) > 1;")" >> output.txt