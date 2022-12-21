package com.dreamteam.goodpc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Computer extends Item {

    @OneToMany
    @ToString.Exclude
    private Set<Component> components = new HashSet<>();

    public void addComponent(Component component) throws Exception {
        boolean isAlreadyExists = components.stream()
                .anyMatch(c -> c.getCategory().equals(component.getCategory()));
        if(isAlreadyExists)
            throw new Exception("Component " + component.getName() + " already added in computer " + getName());
    }

    public void addComponents(Iterable<Component> components) throws Exception {
        for (Component component : components) {
            addComponent(component);
        }
    }

    public Set<Component> getComponents() {
        return Collections.unmodifiableSet(components);
    }

    @Builder
    public Computer(String image, String name, String description, Category category, Integer itemAmount, Money price) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.category = category;
        this.itemAmount = itemAmount;
        this.price = price;
    }

    @Override
    public Map<String, String> getCharacteristics() {
        Map<String, String> characteristics = components.stream().collect(
                Collectors.toMap(
                        Component::getName,
                        x -> x.getCharacteristics().entrySet().stream()
                                .limit(1)
                                .map(entry -> entry.getKey() + splitter + entry.getValue())
                                .findFirst().orElse("")
                )
        );

        return Collections.unmodifiableMap(characteristics);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Computer computer = (Computer) o;
        return id != null && Objects.equals(id, computer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
