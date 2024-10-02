package com.example.entityservice;

import java.util.Collection;

public class EntityServiceTest {

    public static void main(String[] args) {
        EntityService service = new EntityService();

        // 1. Test adding entities
        System.out.println("----- ADD ENTITIES TEST -----");
        Entity entity1 = new Entity(1, "Entity1");
        Entity entity2 = new Entity(2, "Entity2");
        service.add(entity1);
        service.add(entity2);
        System.out.println("All Entities after add: " + service.getAll());

        // 2. Test adding null entity
        System.out.println("\n----- ADD NULL ENTITY TEST -----");
        service.add(null);
        System.out.println("All Entities after adding null: " + service.getAll());

        // 3. Test removing entities
        System.out.println("\n----- REMOVE ENTITY TEST -----");
        service.remove(entity1);
        System.out.println("All Entities after removing Entity1: " + service.getAll());

        // 4. Test removing entity not in collection
        System.out.println("\n----- REMOVE NON-EXISTENT ENTITY TEST -----");
        Entity entity3 = new Entity(3, "Entity3"); // Not added to collection
        service.remove(entity3);
        System.out.println("All Entities after attempting to remove non-existent Entity3: " + service.getAll());

        // 5. Test getting an entity
        System.out.println("\n----- GET ENTITY TEST -----");
        Entity retrievedEntity = service.get(entity2);
        System.out.println("Retrieved Entity2: " + retrievedEntity);

        // 6. Test getting non-existent entity
        System.out.println("\n----- GET NON-EXISTENT ENTITY TEST -----");
        Entity nonExistentEntity = service.get(entity3);
        System.out.println("Attempt to get non-existent Entity3: " + nonExistentEntity);

        // 7. Test getting all entities
        System.out.println("\n----- GET ALL ENTITIES TEST -----");
        Collection<Entity> allEntities = service.getAll();
        System.out.println("All Entities: " + allEntities);

        // 8. Test removing all entities
        System.out.println("\n----- REMOVE ALL ENTITIES TEST -----");
        service.removeAll();
        System.out.println("All Entities after removeAll: " + service.getAll());

        // 9. Test persistence to database
        System.out.println("\n----- PERSISTENCE TEST -----");
        service.add(entity1);
        service.add(entity2);
        service.persist(); // Will persist entity1 and entity2 to the database
        System.out.println("Persisted entities to the database.");

        // 10. Test shutdown of H2 server
//        System.out.println("\n----- SHUTDOWN TEST -----");
//        service.shutdown();
//        System.out.println("H2 server stopped.");
    }
}
