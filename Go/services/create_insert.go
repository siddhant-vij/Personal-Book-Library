package services

import (
	"fmt"

	"github.com/siddhant-vij/Personal-Book-Library/utils"
)

func CreateInsert() {
	// Read books from CSV file
	csvReader := utils.NewCsvReader()
	err := csvReader.ReadBooksFromCSV("Problem/input_data.csv")
	if err != nil {
		fmt.Println(err)
	}

	user := "postgres"
	password := "admin"
	host := "localhost"
	port := "5432"
	dbName := "bookslibrary"

	// bash: psql --username=postgres
	// bash: Enter your password
	// ------------------------------------
	// psql: CREATE DATABASE bookslibrary;

	// Connect to database
	connection, err := utils.ConnectToDB(dbName, user, password, host, port)
	if err != nil {
		fmt.Println(err)
	}

	// Create books table
	tableName := "books"
	utils.CreateTable(connection, tableName)

	// Insert books into table
	for _, book := range csvReader.GetBooks() {
		utils.InsertBook(connection, tableName, book)
	}

	// Close database connection
	utils.CloseDB(connection)

	// psql: \c postgres;
	// psql: DROP DATABASE bookslibrary;
}
