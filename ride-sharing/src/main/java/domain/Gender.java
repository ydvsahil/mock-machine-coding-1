package domain;

public enum Gender {
    MALE("M"),
    FEMALE("F");

    private String genderId;

    Gender(String genderId) {
        this.genderId = genderId;
    }

    public String getGenderId() {
        return genderId;
    }
}
