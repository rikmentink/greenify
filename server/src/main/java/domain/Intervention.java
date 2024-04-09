package domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

public class Intervention { // Add relationship with Phase
    @GeneratedValue
    @Id
    private Long id;
    private final String name;
    private final String description;

    public Intervention(String name, String description) {
        if(name == null || description == null) {
            throw new IllegalArgumentException("Intervention should have a name and description");
        }
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Intervention that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "Intervention{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
