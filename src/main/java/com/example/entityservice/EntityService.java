package com.example.entityservice;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class EntityService {

    private final ConcurrentHashMap<Integer, Entity> entityCollection = new ConcurrentHashMap<>();
    private Server webServer;

    // Add an entity to the collection
    public void add(Entity e1) {
        if (e1 == null) {
            System.out.println("Cannot add null entity");
            return;
        }
        entityCollection.put(e1.getId(), e1);
    }

    // Remove a specific entity from the collection
    public void remove(Entity e1) {
        if (e1 == null || !entityCollection.containsKey(e1.getId())) {
            System.out.println("Cannot remove null entity or remove entity that is not present");
            return;
        }
        entityCollection.remove(e1.getId());
    }

    // Remove all entities from the collection
    public void removeAll() {
        entityCollection.clear();
    }

    // Get a specific entity from the collection
    public Entity get(Entity e1) {
        if (e1 == null || !entityCollection.containsKey(e1.getId())) {
            System.out.println("Cannot get null entity or entity that is not present");
            return null;
        }
        return entityCollection.get(e1.getId());
    }

    // Get all entities from the collection
    public Collection<Entity> getAll() {
        return entityCollection.values();
    }

    // Persist entities to an in-memory H2 database
    public void persist() {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
             Statement statement = connection.createStatement()) {

            // Start the web server if not already started
            if (webServer == null || !webServer.isRunning(true)) {
                webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
            }

            // Create the table if it doesn't exist
            statement.execute("CREATE TABLE IF NOT EXISTS entity (id INT PRIMARY KEY, name VARCHAR(255))");

            // Clear existing records before inserting new ones
            statement.execute("DELETE FROM entity");

            // Insert all entities using prepared statements to prevent SQL injection
            String insertSQL = "INSERT INTO entity (id, name) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                for (Entity entity : entityCollection.values()) {
                    preparedStatement.setInt(1, entity.getId());
                    preparedStatement.setString(2, entity.getName());
                    preparedStatement.addBatch(); // Batch insert for performance
                }
                preparedStatement.executeBatch();
            }

            System.out.println("Persisted entities to the database.");
        } catch (SQLException e) {
            System.err.println("Error persisting entities: " + e.getMessage());
        }
    }

    // Shutdown the web server gracefully
    public void shutdown() {
        if (webServer != null && webServer.isRunning(true)) {
            webServer.stop();
            System.out.println("H2 web server stopped.");
        }
    }
}
