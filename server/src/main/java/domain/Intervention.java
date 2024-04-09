package domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

public class Intervention {
    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private String description;

    public Intervention(String name, String description) {
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
