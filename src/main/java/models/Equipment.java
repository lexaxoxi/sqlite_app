package models;

import java.util.Objects;

public class Equipment {
    private int Id;
    private String name;
    private int Well_id;

    public Equipment(int id, String name, int Well_id) {
        Id = id;
        this.name = name;
        this.Well_id = Well_id;
    }

    public Equipment(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getWell_id() {
        return Well_id;
    }

    public void setWell_id(int well_id) {
        Well_id = well_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return Id == equipment.Id && Well_id == equipment.Well_id && Objects.equals(name, equipment.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, Well_id);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "name='" + name + '\'' +
                '}';
    }
}
