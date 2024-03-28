package utils

import (
	"context"
	"fmt"
	"os"

	"github.com/jackc/pgx/v5"

	"github.com/siddhant-vij/Personal-Book-Library/models"
)

func ConnectToDB(dbName, user, password, host, port string) (*pgx.Conn, error) {
	url := fmt.Sprintf("postgres://%s:%s@%s:%s/%s", user, password, host, port, dbName)
	conn, err := pgx.Connect(context.Background(), url)
	if err != nil {
		return nil, err
	}
	return conn, nil
}

func CloseDB(conn *pgx.Conn) {
	conn.Close(context.Background())
}

func CreateTable(conn *pgx.Conn, tableName string) error {
	_, err := conn.Exec(context.Background(), fmt.Sprintf("CREATE TABLE IF NOT EXISTS %s (book_id SERIAL NOT NULL PRIMARY KEY, isbn VARCHAR(20) NOT NULL, title VARCHAR(50) NOT NULL, author VARCHAR(25) NOT NULL, publication_year INT NOT NULL)", tableName))
	return err
}

func DropTable(conn *pgx.Conn, tableName string) error {
	_, err := conn.Exec(context.Background(), fmt.Sprintf("DROP TABLE IF EXISTS %s", tableName))
	return err
}

func InsertBook(conn *pgx.Conn, tableName string, book *models.Book) error {
	_, err := conn.Exec(context.Background(), fmt.Sprintf("INSERT INTO %s (isbn, title, author, publication_year) VALUES($1, $2, $3, $4)", tableName), book.GetIsbn(), book.GetTitle(), book.GetAuthor(), book.GetPublicationYear())
	return err
}

func ExecuteQuery(conn *pgx.Conn, query string) (pgx.Rows, error) {
	rows, err := conn.Query(context.Background(), query)
	if err != nil {
		return nil, err
	}
	return rows, nil
}

func ExecuteUpdateQuery(conn *pgx.Conn, query string) (int, error) {
	ct, err := conn.Exec(context.Background(), query)
	if err != nil {
		return 0, err
	}
	return int(ct.RowsAffected()), nil
}

func PrintQueryResultToFile(queryStr string, rows pgx.Rows, filePath string) error {
	file, err := os.OpenFile(filePath, os.O_APPEND|os.O_WRONLY|os.O_CREATE, 0644)
	if err != nil {
		return err
	}
	defer file.Close()

	_, err = file.WriteString(queryStr + ":\n")
	if err != nil {
		return err
	}

	for rows.Next() {
		values, err := rows.Values()
		if err != nil {
			return err
		}
		resultString := ""
		for _, value := range values {
			resultString += fmt.Sprintf("%v", value) + "|"
		}
		resultString = resultString[:len(resultString)-1]

		_, err = file.WriteString(resultString + "\n")
		if err != nil {
			return err
		}
	}
	_, err = file.WriteString("\n\n\n\n\n")
	if err != nil {
		return err
	}
	return nil
}

func PrintRowsAffectedToFile(queryStr string, rowsAffected int, filePath string) error {
	file, err := os.OpenFile(filePath, os.O_APPEND|os.O_WRONLY|os.O_CREATE, 0644)
	if err != nil {
		return err
	}
	defer file.Close()

	_, err = file.WriteString(queryStr + ":\n")
	if err != nil {
		return err
	}

	_, err = file.WriteString(fmt.Sprintf("Rows Affected: %d\n\n\n\n\n\n", rowsAffected))
	if err != nil {
		return err
	}
	return nil
}
