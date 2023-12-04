package org.example.Service;

import org.example.Books;

import java.util.List;

public interface BookService {
    void addBook(Books books);
    List<Books> getAllBooks();
    Books getBookById(int id);
    List<Books> getBooksByTitle(String title);
    List<Books> getBooksByAuthor(String author);

    List<Books> getBooksByPriceRange (Double minPrice, Double maxPrice);
    List<Books> getBooksByQuantity (int minQuantity, int maxQuantity);
}
