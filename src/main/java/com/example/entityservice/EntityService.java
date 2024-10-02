package com.example.entityservice;

import org.h2.tools.Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
//import java.util.Map;

public class EntityService {

    private ConcurrentHashMap<Integer, Entity> entityCollection = new ConcurrentHashMap<>();

    public void add(Entity e1) {
        entityCollection.put(e1.getId(), e1);
    }

    public void remove(Entity e1) {
        if(entityCollection.containsKey(e1.getId())){
            entityCollection.remove(e1.getId());
        }

    }

    public void removeAll() {
        entityCollection.clear();
    }

    public Entity get(Entity e1) {
        if(entityCollection.containsKey(e1.getId())){
            return entityCollection.get(e1.getId());
        }
        return null;
    }

    public Collection<Entity> getAll() {
        return entityCollection.values();
    }

    public void persist() {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
             Statement statement = connection.createStatement()) {


            Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();

            statement.execute("CREATE TABLE IF NOT EXISTS entity (id INT PRIMARY KEY, name VARCHAR(255))");
            statement.execute("DELETE FROM entity");

            for (Entity entity : entityCollection.values()) {
                statement.executeUpdate("INSERT INTO entity (id, name) VALUES (" + entity.getId() + ", '" + entity.getName() + "')");
            }

            System.out.println("Persisted entities to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
