package nl.hu.greenify.core.domain.factor;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.greenify.core.domain.Response;

@Getter
@ToString
@EqualsAndHashCode
@Entity
public class Subfactor implements IFactor {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private int number;
    private boolean isSupportingFactor;

    @Setter
    @OneToOne
    private Response response;

    protected Subfactor() {
    }

    public Subfactor(Long id, String title, int number, boolean isSupportingFactor) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.isSupportingFactor = isSupportingFactor;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setNumber(int number) {

    }
}
