package models;

import java.util.Objects;

public class Well {
    private int Id;
    private String name;

    public Well(int id, String name) {
        Id = id;
        this.name = name;
    }

    public Well(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
        Well well = (Well) o;
        return Objects.equals(Id, well.Id) && Objects.equals(name, well.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name);
    }

    @Override
    public String toString() {
        return "Well{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }
}
