package nl.hu.greenify.core.domain.interfaces;

public interface IFactor {
    Long getId();
    String getTitle();
    int getNumber();
    void setTitle(String title);
    void setNumber(int number);
}