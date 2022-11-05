package com.jsfxr;

import java.lang.reflect.Field;

public class FieldValue {
    private Field field;
    private Object instance;
    public FieldValue(Object instance, Field field) {
        this.field = field;
        this.instance = instance;
    }
    public FieldValue(Field field) {
        this(null, field);
    }
    public FieldValue(Class<?> type, String field) {
        try {
            this.field = type.getField(field);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public FieldValue(Object instance, String field) {
        try {
            this.field = instance.getClass().getField(field);
            this.instance = instance;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void set(Object value) {
        try {
            field.set(instance, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public <T> T get() {
        try {
            return (T)field.get(instance);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getFieldName() {
        return field.getName();
    }
}
