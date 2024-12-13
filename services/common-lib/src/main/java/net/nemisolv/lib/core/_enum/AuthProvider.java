package net.nemisolv.lib.core._enum;

public enum AuthProvider {
    LOCAL("local"),
    GOOGLE("google"),
    FACEBOOK("facebook"),
    GITHUB("github");

    private String value;

    AuthProvider(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AuthProvider getEnum(String value) {
        for (AuthProvider v : values()) {
            if (v.getValue().equalsIgnoreCase(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }
}