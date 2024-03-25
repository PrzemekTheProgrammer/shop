package pl.com.coders.shop2.domain;

public enum CategoryType {
    ELEKTRONIKA(1), MOTORYZACJA(2), EDUKACJA(3), INNE(4);

    private final long value;

    CategoryType(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
