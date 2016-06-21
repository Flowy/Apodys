package com.flowyk.apodys.bussiness.entity;

import java.util.Arrays;

public class LocalizationUnit {
    private String key;
    private Object[] parameters;

    public LocalizationUnit(String key, Object... parameters) {
        this.key = key;
        this.parameters = parameters;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "LocalizationUnit{" +
                "key='" + key + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }

    public Object[] getArguments() {
        return parameters;
    }
}
