package com.example.entityservice;

//import java.util.Objects;

public class Entity {
    private int id;
    private String name;
    //private int salary;


    public Entity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Entity entity = (Entity) o;
//        return id == entity.id && name.equals(entity.name);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name);
//    }
//
//    @Override
//    public String toString() {
//        return "Entity{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                '}';
//    }
}
