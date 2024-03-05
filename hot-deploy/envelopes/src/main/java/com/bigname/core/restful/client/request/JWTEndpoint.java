package com.bigname.core.restful.client.request;

public enum JWTEndpoint implements ApiEndpoint {
    JWT("oauth", "ro"),
    JWT_PASSWORD_REALM("oauth", "token");

    private final String[] paths;

    JWTEndpoint(String... paths) {
        this.paths = paths;
    }
    @Override
    public String[] getPaths() {
        return paths;
    }
}
