package com.postgres;

import java.sql.Connection;
import java.sql.ResultSet;

public class SQLQueriesMain {
  public static void main(String[] args) {
    String dbName = "bookslibrary";
    String user = "postgres";
    String password = "admin";
    Connection con = CrudOperations.connectToDB(dbName, user, password);

    String tableName = "books";

    String queryStr = null;
    String outputFilePath = "output.txt";

    // Retrieve all books
    queryStr = "Retrieve all books";
    getAllBooks(con, tableName, queryStr, outputFilePath);

    // Find books by a specific author
    queryStr = "Find books by a specific author";
    getBooksByAuthor(con, tableName, queryStr, outputFilePath, "Onofredo Flay");

    // List all books published before a certain year
    queryStr = "List all books published before a certain year";
    getBooksBeforeYear(con, tableName, queryStr, outputFilePath, 2010);

    // Count the total number of books in the library
    queryStr = "Count the total number of books in the library";
    countBooks(con, tableName, queryStr, outputFilePath);

    // Find books with titles containing a specific word
    queryStr = "Find books with titles containing a specific word";
    getBooksWithStringInTitle(con, tableName, queryStr, outputFilePath, "sql");

    // List all authors (without duplicates)
    queryStr = "List all authors (without duplicates)";
    getDistinctAuthors(con, tableName, queryStr, outputFilePath);

    // Update the author name for all books by a specific author
    queryStr = "Update the author name for all books by a specific author";
    updateAuthor(con, tableName, queryStr, outputFilePath, "Onofredo Flay", "Homer Simpson");

    // Delete all books published before a specific year
    queryStr = "Delete all books published before a specific year";
    deleteBooksBeforeYear(con, tableName, queryStr, outputFilePath, 2006);

    // Retrieve all books sorted by publication year (newest first)
    queryStr = "Retrieve all books sorted by publication year (newest first)";
    getAllBooksSortedByPublicationYear(con, tableName, queryStr, outputFilePath);

    // Find the oldest book in the library
    queryStr = "Find the oldest book in the library";
    getOldestBook(con, tableName, queryStr, outputFilePath);

    // Find the number of books published each year
    queryStr = "Find the number of books published each year";
    getBooksByYear(con, tableName, queryStr, outputFilePath);

    // List the top 5 most frequently occurring authors in the library
    queryStr = "List the top 5 most frequently occurring authors in the library";
    getTop5Authors(con, tableName, queryStr, outputFilePath);

    // Identify books that have titles longer than 15 characters
    queryStr = "Identify books that have titles longer than 15 characters";
    getLongerTitles(con, tableName, queryStr, outputFilePath);

    // Calculate the average publication year of all books in the library
    queryStr = "Calculate the average publication year of all books in the library";
    getAveragePublicationYear(con, tableName, queryStr, outputFilePath);

    // Select all authors who have published more than one book in the same year
    queryStr = "Select all authors who have published more than one book in the same year";
    getAuthorsWithMultipleBooks(con, tableName, queryStr, outputFilePath);

    // Close database connection
    CrudOperations.closeConnection(con);
  }

  private static void performAction(Connection con, String queryStr, String query, String outputFilePath) {
    if (query.trim().toUpperCase().startsWith("SELECT")) {
      ResultSet rs = CrudOperations.executeQuery(con, query);
      CrudOperations.printQueryResultToFile(queryStr, rs, outputFilePath);
    } else {
      int count = CrudOperations.executeUpdateQuery(con, query);
      CrudOperations.printQueryResultToFile(queryStr, count, outputFilePath);
    }
  }

  private static void getAllBooks(Connection con, String tableName, String queryStr, String outputFilePath) {
    String query = "SELECT * FROM " + tableName;
    performAction(con, queryStr, query, outputFilePath);
  }

  private static void getBooksByAuthor(Connection con, String tableName, String queryStr, String outputFilePath,
      String author) {
    String query = "SELECT * FROM " + tableName + " WHERE author = '" + author + "'";
    performAction(con, queryStr, query, outputFilePath);
  }

  private static void getBooksBeforeYear(Connection con, String tableName, String queryStr, String outputFilePath,
      int year) {
    String query = "SELECT * FROM " + tableName + " WHERE publication_year < " + year;
    performAction(con, queryStr, query, outputFilePath);
  }

  private static void countBooks(Connection con, String tableName, String queryStr, String outputFilePath) {
    String query = "SELECT COUNT(*) FROM " + tableName;
    performAction(con, queryStr, query, outputFilePath);
  }

  private static void getBooksWithStringInTitle(Connection con, String tableName, String queryStr,
      String outputFilePath, String title) {
    String query = "SELECT * FROM " + tableName + " WHERE title LIKE '%" + title + "%'";
    performAction(con, queryStr, query, outputFilePath);
  }

  private static void getDistinctAuthors(Connection con, String tableName, String queryStr, String outputFilePath) {
    String query = "SELECT DISTINCT author FROM " + tableName;
    performAction(con, queryStr, query, outputFilePath);
  }

  private static void updateAuthor(Connection con, String tableName, String queryStr, String outputFilePath,
      String oldAuthor, String newAuthor) {
    String query = "UPDATE " + tableName + " SET author = '" + newAuthor + "' WHERE author = '" + oldAuthor + "'";
    performAction(con, queryStr, query, outputFilePath);
  }

  private static void deleteBooksBeforeYear(Connection con, String tableName, String queryStr, String outputFilePath,
      int year) {
    String query = "DELETE FROM " + tableName + " WHERE publication_year < " + year;
    performAction(con, queryStr, query, outputFilePath);
  }

  private static void getAllBooksSortedByPublicationYear(Connection con, String tableName, String queryStr,
      String outputFilePath) {
    String query = "SELECT * FROM " + tableName + " ORDER BY publication_year DESC";
    performAction(con, queryStr, query, outputFilePath);
  }

  private static void getOldestBook(Connection con, String tableName, String queryStr, String outputFilePath) {
    String query = "SELECT * FROM " + tableName + " ORDER BY publication_year ASC LIMIT 1";
    performAction(con, queryStr, query, outputFilePath);
  }

  private static void getBooksByYear(Connection con, String tableName, String queryStr, String outputFilePath) {
    String query = "SELECT publication_year, COUNT(*) FROM " + tableName + " GROUP BY publication_year";
    performAction(con, queryStr, query, outputFilePath);
  }

  private static void getTop5Authors(Connection con, String tableName, String queryStr, String outputFilePath) {
    String query = "SELECT author, COUNT(*) FROM " + tableName + " GROUP BY author ORDER BY COUNT(*) DESC LIMIT 5";
    performAction(con, queryStr, query, outputFilePath);
  }

  private static void getLongerTitles(Connection con, String tableName, String queryStr, String outputFilePath) {
    String query = "SELECT * FROM " + tableName + " WHERE LENGTH(title) > 15";
    performAction(con, queryStr, query, outputFilePath);
  }

  private static void getAveragePublicationYear(Connection con, String tableName, String queryStr,
      String outputFilePath) {
    String query = "SELECT AVG(publication_year) FROM " + tableName;
    performAction(con, queryStr, query, outputFilePath);
  }

  private static void getAuthorsWithMultipleBooks(Connection con, String tableName, String queryStr,
      String outputFilePath) {
    String query = "SELECT author FROM " + tableName + " GROUP BY author, publication_year HAVING COUNT(*) > 1";
    performAction(con, queryStr, query, outputFilePath);
  }
}
