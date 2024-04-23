package nl.hu.greenify.core.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
@EqualsAndHashCode
public class Template {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private int version;

    @OneToMany
    private List<Category> categories = new ArrayList<>();

    public Template(Long id, String name, String description, int version, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.version = version;
        this.categories = categories;
    }

    public static Template copyOf(Template original) {
        Template copy = new Template();
        copy.id = original.id;
        copy.name = original.name;
        copy.description = original.description;
        copy.version = original.version;
        // Make a deep copy of the categories using its static named constructor
        for (Category category : original.categories) {
            copy.categories.add(Category.copyOf(category));
        }
        return copy;
    }

    protected Template() {

    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }
}
