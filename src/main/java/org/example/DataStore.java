package org.example;

import java.util.HashMap;
import java.util.Map;

public class DataStore {
  private Map<String, Books> booksMap = new HashMap<>();

    private static DataStore instance = new DataStore();

    public static DataStore getInstance() {
        return instance;
    }


    private DataStore() {
        //dummy data
        booksMap.put("6", new Books(0, "Title 6", "Author 6", 13.99, 17));
        booksMap.put("7", new Books(0, "Title 7", "Author 7", 8.99, 24));
        booksMap.put("8", new Books(0, "Title 8", "Author 8", 7.99, 30));
        booksMap.put("9", new Books(0, "Title 9", "Author 9", 9.99, 14));
    }

    public Books getBookById(int bookId) {
        return booksMap.get(bookId);
    }

    public void putBooks(Books books) {
        booksMap.put(String.valueOf(books.getBookId()), books);
    }


}