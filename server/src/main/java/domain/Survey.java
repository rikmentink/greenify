package domain;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Survey extends Template {
    @Id
    @GeneratedValue
    private Long id;

    public Survey(Long id, String name, String description, Integer version, List<Category> questions) {
        super(id, name, description, version, questions);
    }
}
