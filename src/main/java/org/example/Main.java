package org.example;

import org.example.Resource.BookResource;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import java.net.URI;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            DBConnection connection = DBConnection.openConnection();
            System.out.println("Connected to the database successfully!");

            String BASE_URI = "http://localhost:8080/";
            ResourceConfig resourceConfig = new ResourceConfig(BookResource.class);
            GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);
            System.out.println("Server started at: " + BASE_URI);



            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}