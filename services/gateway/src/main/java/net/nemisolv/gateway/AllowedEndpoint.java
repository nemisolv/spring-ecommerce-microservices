package net.nemisolv.gateway;

import org.springframework.http.HttpMethod;

import java.util.List;

public class AllowedEndpoint {
    private String path;
    private List<HttpMethod> methods;

    public AllowedEndpoint(String path, List<HttpMethod> methods) {
        this.path = path;
        this.methods = methods;
    }

    public String getPath() {
        return path;
    }

    public List<HttpMethod> getMethods() {
        return methods;
    }
}