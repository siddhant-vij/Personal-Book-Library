package com.postgres;

public class Book {
  private String isbn;
  private String title;
  private String author;
  private int publicationYear;

  public Book(String isbn, String title, String author, int publicationYear) {
    this.isbn = isbn;
    this.title = title;
    this.author = author;
    this.publicationYear = publicationYear;
  }

  public String getIsbn() {
    return isbn;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public int getPublicationYear() {
    return publicationYear;
  }
}
