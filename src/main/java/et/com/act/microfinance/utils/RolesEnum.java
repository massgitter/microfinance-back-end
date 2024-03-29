package et.com.act.microfinance.utils;

public enum RolesEnum {
    ADMIN("admin");

    private final String name;

    RolesEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
