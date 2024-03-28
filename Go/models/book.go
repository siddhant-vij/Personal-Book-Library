package models

type Book struct {
	isbn            string
	title           string
	author          string
	publicationYear int
}

func NewBook(isbn string, title string, author string, publicationYear int) *Book {
	return &Book{
		isbn:            isbn,
		title:           title,
		author:          author,
		publicationYear: publicationYear,
	}
}

func (b *Book) GetIsbn() string {
	return b.isbn
}

func (b *Book) GetTitle() string {
	return b.title
}

func (b *Book) GetAuthor() string {
	return b.author
}

func (b *Book) GetPublicationYear() int {
	return b.publicationYear
}
