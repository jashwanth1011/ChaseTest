package com.example.entityservice;

public class EntityServiceTest {

    public static void main(String[] args) {
        // Create the EntityService instance
        EntityService entityService = new EntityService();

        // Create a couple of Entity objects
        Entity e1 = new Entity(1, "EntityOne");
        Entity e2 = new Entity(2, "EntityTwo");

        // Test add operation
        System.out.println("---- Testing Add ----");
        entityService.add(e1);
        entityService.add(e2);
        System.out.println("Added entities:");
        for (Entity entity : entityService.getAll()) {
            System.out.println(entity);
        }

        // Test get operation
        System.out.println("---- Testing Get ----");
        Entity retrievedEntity = entityService.get(e1);
        System.out.println("Retrieved entity: " + retrievedEntity);

        // Test remove operation
        System.out.println("---- Testing Remove ----");
        entityService.remove(e1);
        System.out.println("Entities after removing e1:");
        for (Entity entity : entityService.getAll()) {
            System.out.println(entity);
        }

        // Test removeAll operation
        System.out.println("---- Testing Remove All ----");
        entityService.removeAll();
        System.out.println("Entities after removing all:");
        System.out.println(entityService.getAll());

        // Test persistence (mocking database persistence with H2 or any assumption)
        System.out.println("---- Testing Persist ----");
        entityService.add(e1);
        entityService.add(e2);
        entityService.persist();
        System.out.println("Persisted entities to the database (check logs).");
    }
}
