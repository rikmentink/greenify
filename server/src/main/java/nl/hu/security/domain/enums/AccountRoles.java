package nl.hu.security.domain.enums;

public enum AccountRoles {

    ROLE_USER("ROLE_USER"),
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_VUMEDEWERKER("ROLE_VUMEDEWERKER");
    private final String simpleGrantedAuthority;

    AccountRoles(String simpleGrantedAuthority) {
        this.simpleGrantedAuthority = simpleGrantedAuthority;
    }
    public String getAuthority() {
        return simpleGrantedAuthority;
    }
}
