package org.example.Resource;

import org.example.Books;
import org.example.DBConnection;
import org.example.Service.BookServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/Books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    private final BookServiceImpl bookServiceImpl;

    public BookResource(BookServiceImpl bookServiceImpl) {
        this.bookServiceImpl = bookServiceImpl;
    }

    @POST
        @Consumes(MediaType.APPLICATION_JSON)
        public Response addBook(Books books) {
        bookServiceImpl.addBook(books);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        List<Books> booksList = new ArrayList<>();

            try (DBConnection conn = DBConnection.openConnection();
                 PreparedStatement statement = conn.getConnection().prepareStatement("SELECT * FROM books");
                 ResultSet resultSet = statement.executeQuery()) {

                {while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");

                    Books book = new Books(id, title, author, price, quantity);
                    booksList.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to fetch books").build();
        }

        if (!booksList.isEmpty()) {
            return Response.ok(booksList).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
        @Path("/{id}")
        public Response getBookById(@PathParam("id") int bookId) {
        Books book = bookServiceImpl.getBookById(bookId);
        if (book != null) {
            return Response.ok(book).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/title/{title}")
    public Response getBooksByTitle(@PathParam("title") String title) {
        List<Books> books = bookServiceImpl.getBooksByTitle(title);
        if (!books.isEmpty()) {
            return Response.ok(books).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/author/{author}")
    public Response getBooksByAuthor(@PathParam("author") String author) {
        List<Books> books = bookServiceImpl.getBooksByAuthor(author);
        if (!books.isEmpty()) {
            return Response.ok(books).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @GET
    @Path("/price/{min}/{max}")
    public Response getBooksByPriceRange(
            @PathParam("min") Double minPrice,
            @PathParam("max") Double maxPrice) {
        List<Books> books = bookServiceImpl.getBooksByPriceRange(minPrice, maxPrice);
        if (!books.isEmpty()) {
            return Response.ok(books).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/quantity/{min}/{max}")
    public Response getBooksByQuantity(
            @PathParam("min") int minQuantity,
            @PathParam("max") int maxQuantity) {
        List<Books> books = bookServiceImpl.getBooksByQuantity(minQuantity, maxQuantity);
        if (!books.isEmpty()) {
            return Response.ok(books).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
