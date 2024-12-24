package net.nemisolv.lib.core._enum;

import lombok.Getter;

@Getter
public enum AuthProvider {
    LOCAL("local"),
    GOOGLE("google"),
    FACEBOOK("facebook"),
    GITHUB("github"),
    AZURE("azure");

    private final String value;

    AuthProvider(String value) {
        this.value = value;
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