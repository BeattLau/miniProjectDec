package org.example.Service;

import org.example.Books;
import org.example.DBConnection;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    private static final List<Books> booksList = new ArrayList<>();

    @Override
    public void addBook(Books books) {
        try (DBConnection connection = DBConnection.openConnection()) {
            String sql = "INSERT INTO books (title, author, price, quantity) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, books.getTitle());
                statement.setString(2, books.getAuthor());
                statement.setDouble(3, books.getPrice());
                statement.setInt(4, books.getQuantity());

                // Execute the INSERT statement
                int affectedRows = statement.executeUpdate();

                if (affectedRows > 0) {
                    // Retrieve the generated keys (if any)
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            books.setBookId(id);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        booksList.add(books);
    }
    @Override
    public List<Books> getAllBooks() {
        List<Books> booksList = new ArrayList<>();

        try (DBConnection conn = DBConnection.openConnection();
             PreparedStatement statement = conn.getConnection().prepareStatement("SELECT * FROM books");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                Books book = new Books(id, title, author, price, quantity);
                booksList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksList;
    }


    @Override
    public Books getBookById(int id) {
        try (DBConnection conn = DBConnection.openConnection();
             PreparedStatement statement = conn.getConnection().prepareStatement("SELECT * FROM books WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int bookId = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");

                    return new Books(bookId, title, author, price, quantity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Books> getBooksByTitle(String titleParam) {
        List<Books> booksList = new ArrayList<>();

        try (DBConnection conn = DBConnection.openConnection();
             PreparedStatement statement = conn.getConnection().prepareStatement("SELECT * FROM books WHERE title = ?")) {
            statement.setString(1, titleParam);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookId = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");

                    Books book = new Books(bookId, title, author, price, quantity);
                    booksList.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booksList;
    }
    @Override
    public List<Books> getBooksByAuthor(String authorParam) {
        List<Books> booksList = new ArrayList<>();

        try (DBConnection conn = DBConnection.openConnection();
             PreparedStatement statement = conn.getConnection().prepareStatement("SELECT * FROM books WHERE author = ?")) {
            statement.setString(1, authorParam);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookId = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");

                    Books book = new Books(bookId, title, author, price, quantity);
                    booksList.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booksList;
    }


    @Override
    public List<Books> getBooksByPriceRange(Double minPrice, Double maxPrice) {
        List<Books> booksList = new ArrayList<>();

        try (DBConnection conn = DBConnection.openConnection();
             PreparedStatement statement = conn.getConnection().prepareStatement("SELECT * FROM books WHERE price > ? AND price < ?")) {
            statement.setDouble(1, minPrice);
            statement.setDouble(2, maxPrice);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookId = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");

                    Books book = new Books(bookId, title, author, price, quantity);
                    booksList.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booksList;
    }

    @Override
    public List<Books> getBooksByQuantity(int minQuantity, int maxQuantity) {
        List<Books> booksList = new ArrayList<>();

        try (DBConnection conn = DBConnection.openConnection();
             PreparedStatement statement = conn.getConnection().prepareStatement("SELECT * FROM books WHERE quantity > ? AND quantity < ?")) {
            statement.setInt(1, minQuantity);
            statement.setInt(2, maxQuantity);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookId = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");

                    Books book = new Books(bookId, title, author, price, quantity);
                    booksList.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booksList;
    }
}