import psycopg2
from typing import Optional

from book import Book


class CrudOperations:
    @staticmethod
    def connect_to_db(db_name: str, user: str, password: str) -> psycopg2.extensions.connection:
        conn: Optional[psycopg2.extensions.connection] = None
        try:
            conn = psycopg2.connect(
                host="localhost",
                port=5432,
                database=db_name,
                user=user,
                password=password
            )
        except (Exception, psycopg2.Error) as error:
            print(f"Error while connecting to the PostgreSQL server: {error}")
        finally:
            return conn

    @staticmethod
    def close_connection(con: psycopg2.extensions.connection):
        if con:
            try:
                con.close()
            except (Exception, psycopg2.Error) as error:
                print(f"Error while closing the database connection: {error}")

    @staticmethod
    def create_table(con: psycopg2.extensions.connection, table_name: str):
        try:
            cur = con.cursor()
            query = f"DROP TABLE IF EXISTS {table_name}; " \
                    f"CREATE TABLE {table_name} (book_id SERIAL NOT NULL PRIMARY KEY, isbn VARCHAR(20) NOT NULL, title VARCHAR(50) NOT NULL, author VARCHAR(25) NOT NULL, publication_year INT NOT NULL)"
            cur.execute(query)
            con.commit()
            print(f"Table {table_name} created successfully")
        except (Exception, psycopg2.DatabaseError) as error:
            print(f"Error: {error}")
        finally:
            if cur:
                cur.close()

    @staticmethod
    def drop_table(con: psycopg2.extensions.connection, table_name: str):
        try:
            cur = con.cursor()
            query = f"DROP TABLE IF EXISTS {table_name}"
            cur.execute(query)
            con.commit()
            print(f"Table {table_name} dropped successfully")
        except (Exception, psycopg2.DatabaseError) as error:
            print(f"Error: {error}")
        finally:
            if cur:
                cur.close()

    @staticmethod
    def insert_book(con: psycopg2.extensions.connection,
                    table_name: str,
                    book_id: int,
                    book: 'Book') -> None:
        cur = None
        try:
            cur = con.cursor()
            query = f"INSERT INTO {table_name} (isbn, title, author, publication_year) VALUES (%s, %s, %s, %s)"
            cur.execute(query, (book.isbn,
                                book.title, book.author, book.year))
            con.commit()
            print(f"Book {book_id} inserted successfully")
        except (Exception, psycopg2.DatabaseError) as error:
            print(f"Error: {error}")
        finally:
            if cur:
                cur.close()

    @staticmethod
    def execute_and_print_query(con: psycopg2.extensions.connection, query: str, queryStr: str, file_path: str) -> None:
        cur = None
        try:
            cur = con.cursor()
            cur.execute(query)
            rows = cur.fetchall() if cur.description else cur.rowcount
            with open(file_path, "a") as f:
                print(f"{queryStr}:", file=f)

                if isinstance(rows, list):
                    for row in rows:
                        print("|".join(map(str, row)), file=f)
                    print("\n\n\n\n", file=f)
                else:
                    print(f"{rows} rows affected.", file=f)
                    print("\n\n\n\n", file=f)

        except (Exception, psycopg2.DatabaseError) as error:
            print(f"Error: {error}")
        finally:
            if cur:
                cur.close()
