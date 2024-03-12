from datetime import datetime


class Book:
    def __init__(self, isbn: str, title: str, author: str, year: int):
        self._isbn = self._validate_isbn(isbn)
        self._title = self._validate_title(title)
        self._author = self._validate_author(author)
        self._year = self._validate_year(year)

    @staticmethod
    def _validate_isbn(isbn: str) -> str:
        if not isinstance(isbn, str):
            raise TypeError("ISBN must be a string")

        if len(isbn) != 11:
            raise ValueError("ISBN must be 11 characters long")

        return isbn

    @staticmethod
    def _validate_title(title: str) -> str:
        if not isinstance(title, str):
            raise TypeError("Title must be a string")

        if not title.strip():
            raise ValueError("Title cannot be empty or blank")

        return title

    @staticmethod
    def _validate_author(author: str) -> str:
        if not isinstance(author, str):
            raise TypeError("Author must be a string")

        if not author.strip():
            raise ValueError("Author cannot be empty or blank")

        return author

    @staticmethod
    def _validate_year(year: int) -> int:
        if not isinstance(year, int):
            raise TypeError("Year must be an integer")

        now = datetime.now()
        if year < 1900 or year > now.year:
            raise ValueError("Invalid year")

        return year

    @property
    def isbn(self) -> str:
        return self._isbn

    @property
    def title(self) -> str:
        return self._title

    @property
    def author(self) -> str:
        return self._author

    @property
    def year(self) -> int:
        return self._year
