package ru.pharus.socnetwork.entity;


/**
 *
 * For example:
 * Getters and Setters, Equals and Hashcode methods
 * are generated by IntelliJ IDEA
 */

public class Vendor {
    private int id;
    private String name;

    public Vendor(){

    }

    public Vendor(int  id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vendor vendor = (Vendor) o;

        return id == vendor.id && name.equals(vendor.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }
}
