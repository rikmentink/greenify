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

    private Subfactor(String title, int number, boolean isSupportingFactor) {
        this.title = title;
        this.number = number;
        this.isSupportingFactor = isSupportingFactor;
    }

    public Subfactor(Long id, String title, int number, boolean isSupportingFactor) {
        this(title, number, isSupportingFactor);
        this.id = id;
    }

    public Subfactor(Long id, String title, int number, boolean isSupportingFactor, Response response) {
        this(id, title, number, isSupportingFactor);
        this.response = response;
    }

    public static Subfactor createSubfactor(String title, int number, boolean isSupportingFactor) {
        return new Subfactor(title, number, isSupportingFactor);
    }

    public static Subfactor copyOf(Subfactor original) {
        Subfactor copy = new Subfactor();
        copy.id = original.id;
        copy.title = original.title;
        copy.number = original.number;
        copy.isSupportingFactor = original.isSupportingFactor;
        return copy;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setNumber(int number) {

    }
}
