package com.uvaluation.project.common;


public class VueSelectModel<T> {
    private String label;
    private T value;

    public VueSelectModel() {

    }

    public VueSelectModel(String label, T data) {
        this.label = label;
        this.value = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
