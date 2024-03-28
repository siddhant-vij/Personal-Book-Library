package utils

import (
	"encoding/csv"
	"os"
	"strconv"

	"github.com/siddhant-vij/Personal-Book-Library/models"
)

type CsvReader struct {
	books []*models.Book
}

func NewCsvReader() *CsvReader {
	return &CsvReader{
		books: make([]*models.Book, 0),
	}
}

func (c *CsvReader) GetBooks() []*models.Book {
	return c.books
}

func (c *CsvReader) ReadBooksFromCSV(csvFilePath string) error {
	file, err := os.Open(csvFilePath)
	if err != nil {
		return err
	}
	defer file.Close()

	csvReader := csv.NewReader(file)

	records, err := csvReader.ReadAll()
	if err != nil {
		return err
	}

	for _, record := range records {
		if len(record) == 0 || record[0] == "isbn" {
			continue
		}
		isbn := record[0]
		title := record[1]
		author := record[2]
		publicationYear, _ := strconv.Atoi(record[3])

		book := models.NewBook(isbn, title, author, publicationYear)

		c.books = append(c.books, book)
	}

	return nil
}
