import psycopg2

from crud_operations import CrudOperations


def get_all_books(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str) -> None:
    query = """
    SELECT * FROM """ + table_name
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def get_books_by_author(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str, author: str) -> None:
    query = """
    SELECT * FROM """ + table_name + """ 
    WHERE author = '""" + author + """'"""
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def get_books_before_year(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str, year: int) -> None:
    query = """
    SELECT * FROM """ + table_name + """ 
    WHERE publication_year < """ + str(year)
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def count_books(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str) -> None:
    query = """
    SELECT COUNT(*) FROM """ + table_name
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def get_books_with_string_in_title(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str, str_search: str) -> None:
    query = """
    SELECT * FROM """ + table_name + """ 
    WHERE title LIKE '%""" + str_search + """%'"""
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def get_distinct_authors(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str) -> None:
    query = """
    SELECT DISTINCT author FROM """ + table_name
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def update_author(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str, old_author: str, new_author: str) -> None:
    query = """
    UPDATE """ + table_name + """ 
    SET author = '""" + new_author + """' 
    WHERE author = '""" + old_author.strip() + """'"""
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def delete_books_before_year(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str, year: int) -> None:
    query = """
    DELETE FROM """ + table_name + """ 
    WHERE publication_year < """ + str(year)
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def get_all_books_sorted_by_publication_year(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str) -> None:
    query = """
    SELECT * FROM """ + table_name + """ 
    ORDER BY publication_year DESC"""
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def get_oldest_book(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str) -> None:
    query = """
    SELECT * FROM """ + table_name + """ 
    ORDER BY publication_year LIMIT 1"""
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def ge_books_by_year(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str) -> None:
    query = """
    SELECT publication_year, COUNT(*) 
    FROM """ + table_name + """
    GROUP BY publication_year
    """
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def get_top_5_authors(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str) -> None:
    query = """
    SELECT author, COUNT(*) 
    FROM """ + table_name + """
    GROUP BY author
    ORDER BY COUNT(*) DESC
    LIMIT 5
    """
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def get_longer_titles(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str) -> None:
    query = """
    SELECT title
    FROM """ + table_name + """
    WHERE LENGTH(title) > 15
    """
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def get_average_publication_year(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str) -> None:
    query = """
    SELECT AVG(publication_year)
    FROM """ + table_name + """
    """
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def get_authors_with_multiple_books(con: psycopg2.extensions.connection, table_name: str, queryStr: str, file_path: str) -> None:
    query = """
    SELECT author
    FROM """ + table_name + """
    GROUP BY author, publication_year
    HAVING COUNT(*) > 1
    """
    CrudOperations.execute_and_print_query(con, query, queryStr, file_path)


def run():
    dbName = "bookslibrary"
    user = "postgres"
    password = "admin"
    con = CrudOperations.connect_to_db(dbName, user, password)

    table_name = "books"

    queryStr = None
    file_path = "output.txt"

    # Retrieve all books
    queryStr = "Retrieve all books"
    get_all_books(con, table_name, queryStr, file_path)

    # Find books by a specific author
    queryStr = "Find books by a specific author"
    get_books_by_author(con, table_name, queryStr, file_path, "Onofredo Flay")

    # List all books published before a certain year
    queryStr = "List all books published before a certain year"
    get_books_before_year(con, table_name, queryStr, file_path, 2010)

    # Count the total number of books in the library
    queryStr = "Count the total number of books in the library"
    count_books(con, table_name, queryStr, file_path)

    # Find books with titles containing a specific word
    queryStr = "Find books with titles containing a specific word"
    get_books_with_string_in_title(con, table_name, queryStr, file_path, "sql")

    # List all authors (without duplicates)
    queryStr = "List all authors (without duplicates)"
    get_distinct_authors(con, table_name, queryStr, file_path)

    # Update the author name for all books by a specific author
    queryStr = "Update the author name for all books by a specific author"
    update_author(con, table_name, queryStr, file_path,
                  "Onofredo Flay", "Homer Simpson")

    # Delete all books published before a specific year
    queryStr = "Delete all books published before a specific year"
    delete_books_before_year(con, table_name, queryStr, file_path, 2006)

    # Retrieve all books sorted by publication year (newest first)
    queryStr = "Retrieve all books sorted by publication year (newest first)"
    get_all_books_sorted_by_publication_year(
        con, table_name, queryStr, file_path)

    # Find the oldest book in the library
    queryStr = "Find the oldest book in the library"
    get_oldest_book(con, table_name, queryStr, file_path)

    # Find the number of books published each year
    queryStr = "Find the number of books published each year"
    ge_books_by_year(con, table_name, queryStr, file_path)

    # List the top 5 most frequently occurring authors in the library
    queryStr = "List the top 5 most frequently occurring authors in the library"
    get_top_5_authors(con, table_name, queryStr, file_path)

    # Identify books that have titles longer than 15 characters
    queryStr = "Identify books that have titles longer than 15 characters"
    get_longer_titles(con, table_name, queryStr, file_path)

    # Calculate the average publication year of all books in the library
    queryStr = "Calculate the average publication year of all books in the library"
    get_average_publication_year(con, table_name, queryStr, file_path)

    # Select all authors who have published more than one book in the same year
    queryStr = "Select all authors who have published more than one book in the same year"
    get_authors_with_multiple_books(con, table_name, queryStr, file_path)

    CrudOperations.close_connection(con)


if __name__ == '__main__':
    run()
