package services

import (
	"fmt"
	"strings"

	"github.com/jackc/pgx/v5"
	"github.com/siddhant-vij/Personal-Book-Library/utils"
)

func SqlQueries() {
	user := "postgres"
	password := "admin"
	host := "localhost"
	port := "5432"
	dbName := "bookslibrary"

	// Connect to database
	connection, err := utils.ConnectToDB(dbName, user, password, host, port)
	if err != nil {
		fmt.Println(err)
	}

	tableName := "books"
	queryStr := ""
	outputFilePath := "output.txt"

	queryStr = "Retrieve all books"
	getAllBooks(connection, tableName, queryStr, outputFilePath)

	queryStr = "Find books by a specific author"
	getBooksByAuthor(connection, tableName, queryStr, outputFilePath, "Onofredo Flay")

	queryStr = "List all books published before a certain year"
	getBooksBeforeYear(connection, tableName, queryStr, outputFilePath, 2010)

	queryStr = "Count the total number of books in the library"
	countBooks(connection, tableName, queryStr, outputFilePath)

	queryStr = "Find books with titles containing a specific word"
	getBooksWithStringInTitle(connection, tableName, queryStr, outputFilePath, "sql")

	queryStr = "List all authors (without duplicates)"
	getDistinctAuthors(connection, tableName, queryStr, outputFilePath)

	queryStr = "Update the author name for all books by a specific author"
	updateAuthor(connection, tableName, queryStr, outputFilePath, "Onofredo Flay", "Homer Simpson")

	queryStr = "Delete all books published before a specific year"
	deleteBooksBeforeYear(connection, tableName, queryStr, outputFilePath, 2006)

	queryStr = "Retrieve all books sorted by publication year (newest first)"
	getAllBooksSortedByPublicationYear(connection, tableName, queryStr, outputFilePath)

	queryStr = "Find the oldest book in the library"
	getOldestBook(connection, tableName, queryStr, outputFilePath)

	queryStr = "Find the number of books published each year"
	getBooksByYear(connection, tableName, queryStr, outputFilePath)

	queryStr = "List the top 5 most frequently occurring authors in the library"
	getTop5Authors(connection, tableName, queryStr, outputFilePath)

	queryStr = "Identify books that have titles longer than 15 characters"
	getLongerTitles(connection, tableName, queryStr, outputFilePath)

	queryStr = "Calculate the average publication year of all books in the library"
	getAveragePublicationYear(connection, tableName, queryStr, outputFilePath)

	queryStr = "Select all authors who have published more than one book in the same year"
	getAuthorsWithMultipleBooks(connection, tableName, queryStr, outputFilePath)

	// Close database connection
	utils.CloseDB(connection)
}

func performAction(connection *pgx.Conn, queryStr string, query string, outputFilePath string) {
	query = strings.TrimSpace(query)
	if strings.HasPrefix(query, "SELECT") {
		rows, err := utils.ExecuteQuery(connection, query)
		if err != nil {
			fmt.Println(err)
			return
		}
		err = utils.PrintQueryResultToFile(queryStr, rows, outputFilePath)
		if err != nil {
			fmt.Println(err)
			return
		}
	} else {
		count, err := utils.ExecuteUpdateQuery(connection, query)
		if err != nil {
			fmt.Println(err)
			return
		}
		err = utils.PrintRowsAffectedToFile(queryStr, count, outputFilePath)
		if err != nil {
			fmt.Println(err)
			return
		}
	}
}

func getAllBooks(con *pgx.Conn, tableName, queryStr, outputFilePath string) {
	query := fmt.Sprintf("SELECT * FROM %s", tableName)
	performAction(con, queryStr, query, outputFilePath)
}

func getBooksByAuthor(con *pgx.Conn, tableName, queryStr, outputFilePath, author string) {
	query := fmt.Sprintf("SELECT * FROM %s WHERE author = '%s'", tableName, author)
	performAction(con, queryStr, query, outputFilePath)
}

func getBooksBeforeYear(con *pgx.Conn, tableName, queryStr, outputFilePath string, year int) {
	query := fmt.Sprintf("SELECT * FROM %s WHERE publication_year < %d", tableName, year)
	performAction(con, queryStr, query, outputFilePath)
}

func countBooks(con *pgx.Conn, tableName, queryStr, outputFilePath string) {
	query := fmt.Sprintf("SELECT COUNT(*) FROM %s", tableName)
	performAction(con, queryStr, query, outputFilePath)
}

func getBooksWithStringInTitle(con *pgx.Conn, tableName, queryStr, outputFilePath, title string) {
	query := fmt.Sprintf("SELECT * FROM %s WHERE title LIKE '%%%s%%'", tableName, title)
	performAction(con, queryStr, query, outputFilePath)
}

func getDistinctAuthors(con *pgx.Conn, tableName, queryStr, outputFilePath string) {
	query := fmt.Sprintf("SELECT DISTINCT author FROM %s", tableName)
	performAction(con, queryStr, query, outputFilePath)
}

func updateAuthor(con *pgx.Conn, tableName, queryStr, outputFilePath, oldAuthor, newAuthor string) {
	query := fmt.Sprintf("UPDATE %s SET author = '%s' WHERE author = '%s'", tableName, newAuthor, oldAuthor)
	performAction(con, queryStr, query, outputFilePath)
}

func deleteBooksBeforeYear(con *pgx.Conn, tableName, queryStr, outputFilePath string, year int) {
	query := fmt.Sprintf("DELETE FROM %s WHERE publication_year < %d", tableName, year)
	performAction(con, queryStr, query, outputFilePath)
}

func getAllBooksSortedByPublicationYear(con *pgx.Conn, tableName, queryStr, outputFilePath string) {
	query := fmt.Sprintf("SELECT * FROM %s ORDER BY publication_year DESC", tableName)
	performAction(con, queryStr, query, outputFilePath)
}

func getOldestBook(con *pgx.Conn, tableName, queryStr, outputFilePath string) {
	query := fmt.Sprintf("SELECT * FROM %s ORDER BY publication_year ASC LIMIT 1", tableName)
	performAction(con, queryStr, query, outputFilePath)
}

func getBooksByYear(con *pgx.Conn, tableName, queryStr, outputFilePath string) {
	query := fmt.Sprintf("SELECT publication_year, COUNT(*) FROM %s GROUP BY publication_year", tableName)
	performAction(con, queryStr, query, outputFilePath)
}

func getTop5Authors(con *pgx.Conn, tableName, queryStr, outputFilePath string) {
	query := fmt.Sprintf("SELECT author, COUNT(*) FROM %s GROUP BY author ORDER BY COUNT(*) DESC LIMIT 5", tableName)
	performAction(con, queryStr, query, outputFilePath)
}

func getLongerTitles(con *pgx.Conn, tableName, queryStr, outputFilePath string) {
	query := fmt.Sprintf("SELECT * FROM %s WHERE LENGTH(title) > 15", tableName)
	performAction(con, queryStr, query, outputFilePath)
}

func getAveragePublicationYear(con *pgx.Conn, tableName, queryStr, outputFilePath string) {
	query := fmt.Sprintf("SELECT AVG(publication_year) FROM %s", tableName)
	performAction(con, queryStr, query, outputFilePath)
}

func getAuthorsWithMultipleBooks(con *pgx.Conn, tableName, queryStr, outputFilePath string) {
	query := fmt.Sprintf("SELECT author FROM %s GROUP BY author, publication_year HAVING COUNT(*) > 1", tableName)
	performAction(con, queryStr, query, outputFilePath)
}
