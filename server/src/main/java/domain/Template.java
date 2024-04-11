package domain;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Template {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Integer version;

    @OneToMany
    private List<Category> categories;

    public Template(Long id, String name, String description, Integer version, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.version = version;
        this.categories = categories;
    }
}
