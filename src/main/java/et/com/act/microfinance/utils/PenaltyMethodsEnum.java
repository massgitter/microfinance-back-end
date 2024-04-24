package et.com.act.microfinance.utils;

public enum PenaltyMethodsEnum {
    FIXED("FIXED", "Customers will be penalized once per month"),
    INCREMENTAL("INCREMENTAL", "Customers will be penalized with an amount with in period of days"),
    PERCENTAGE_FIXED("PERCENTAGE_FIXED", "Customers will be penalized once in a month with an amount equivalent to a percentage of total monthly saving"),
    PERCENTAGE_INCREMENTAL("PERCENTAGE_INCREMENTAL", "Customers will be penalized to an incremental amount equivalent to a percentage of total monthly saving with in period of time");

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    private final String name;
    private final String description;

    PenaltyMethodsEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }
}