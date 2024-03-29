package et.com.act.microfinance.utils;

public enum Statuses {
    ACTIVE("active"),
    INACTIVE("inactive"),
    SUSPENDED("suspended"),
    PENDING("pending"),
    PAID("paid"),
    APPROVED("approved"),
    LOANED("loanded"),
    RETURNED("returned");

    private final String name;

    Statuses(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
