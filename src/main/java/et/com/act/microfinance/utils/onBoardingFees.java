package et.com.act.microfinance.utils;

public enum onBoardingFees {
    REGISTRATION_FEE("Registration Fee", 500.0),
    INITIAL_DEPOSIT("Initial Deposit", 200.0),
    SHARE_VALUE("Share Value", 500.0);

    private final String description;
    private final Double value;

    onBoardingFees(String description, Double value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public Double getValue() {
        return value;
    }
}
