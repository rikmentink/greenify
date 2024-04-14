package nl.hu.greenify.core.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Survey extends Template {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Phase phase;

    public Survey(Long id, String name, String description, Integer version, Phase phase) {
        super(id, name, description, version);
        this.phase = phase;
    }

    protected Survey() {

    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", version=" + getVersion() +
                '}';
    }
}
