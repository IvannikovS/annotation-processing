package com.ivannikov;

import java.util.ArrayList;
import java.util.List;

public class ComponentInfo {
    private String componentName;
    private String reference;
    private String componentType;
    private List<String> dependencies = new ArrayList<>();
    private List<String> dependentComponents = new ArrayList<>(); // Добавлено для хранения зависимых компонентов

    // Конструктор
    public ComponentInfo(String componentName, String reference, String componentType) {
        this.componentName = componentName;
        this.reference = reference;
        this.componentType = componentType;
    }

    // Геттеры и сеттеры
    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    public void addDependency(String dependency) {
        dependencies.add(dependency);
    }

    public List<String> getDependentComponents() {
        return dependentComponents;
    }

    public void addDependentComponent(String dependentComponent) {
        dependentComponents.add(dependentComponent);
    }
}
