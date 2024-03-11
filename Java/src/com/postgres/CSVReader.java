package com.postgres;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
  private final List<Book> books;

  CSVReader() {
    this.books = new ArrayList<>();
  }

  public List<Book> getBooks() {
    return Collections.unmodifiableList(books);
  }

  public void readBooksFromCSV(String csvFilePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        if (line.isBlank() || line.startsWith("isbn")) {
          continue;
        }
        String[] values = line.split(",");
        String isbn = values[0];
        String title = values[1];
        String author = values[2];
        int publicationYear = Integer.parseInt(values[3]);
        Book book = new Book(isbn, title, author, publicationYear);
        this.books.add(book);
      }
    } catch (IOException e) {
      System.out.println("Error reading CSV file: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
