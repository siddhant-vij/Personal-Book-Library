import csv
from typing import List

from book import Book


class CSVReader:
    def __init__(self) -> None:
        self._books: List[Book] = []

    @property
    def get_books(self) -> List[Book]:
        return self._books

    def read_books_from_csv(self, csv_file_path: str) -> None:

        with open(csv_file_path, 'r', newline='') as csvfile:
            reader = csv.reader(csvfile)
            next(reader)  # skip header

            for row in reader:
                if row[0]:
                    try:
                        year = int(row[3])
                    except ValueError:
                        print(f"Invalid year: {row[3]}")
                        continue

                    book = Book(
                        isbn=row[0],
                        title=row[1],
                        author=row[2],
                        year=year)

                    self._books.append(book)
