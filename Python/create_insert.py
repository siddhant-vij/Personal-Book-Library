import psycopg2
from typing import List

from book import Book
from crud_operations import CrudOperations
from csv_reader import CSVReader


def run() -> None:
    # ------------------------
    # bash: psql --username=postgres
    # bash: Enter your password
    # ------------------------
    # psql: CREATE DATABASE bookslibrary;
    con: psycopg2.extensions.connection = connect_to_db()
    # ------------------------
    create_table(con)
    reader: CSVReader = CSVReader()
    books: List[Book] = read_books_from_csv(reader)
    insert_books(books, con)
    # ------------------------
    # While dropping table, comment out the table
    # creation, data reading & insertion above...
    # ------------------------
    # drop_table(con)
    # ------------------------
    # psql: \c postgres;
    # psql: DROP DATABASE bookslibrary;
    close_connection(con)


def connect_to_db() -> psycopg2.extensions.connection:
    db_name: str = "bookslibrary"
    user: str = "postgres"
    password: str = "admin"
    con: psycopg2.extensions.connection = CrudOperations.connect_to_db(
        db_name, user, password)
    return con


def create_table(con: psycopg2.extensions.connection) -> None:
    table_name: str = "books"
    CrudOperations.create_table(con, table_name)


def read_books_from_csv(reader: CSVReader) -> List[Book]:
    reader.read_books_from_csv(
        "Problem/input_data.csv")
    return reader.get_books


def insert_books(books: List[Book], con: psycopg2.extensions.connection) -> None:
    for i, book in enumerate(books, start=1):
        CrudOperations.insert_book(con, "books", i, book)


def drop_table(con: psycopg2.extensions.connection) -> None:
    table_name: str = "books"
    CrudOperations.drop_table(con, table_name)


def close_connection(con: psycopg2.extensions.connection) -> None:
    CrudOperations.close_connection(con)


if __name__ == "__main__":
    run()
