package com.dreamteam.goodpc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Component extends Item{

    @ElementCollection
    @CollectionTable(name = "characteristics")
    @MapKeyColumn(name = "name", unique = true)
    @Column(name = "value")
    @Builder.ObtainVia(field = "characteristics", method = "addCharacteristic")
    private Map<String , String > characteristics = new HashMap<>();

    @Builder
    public Component(String image, String name, String description, Category category, Integer itemAmount, Money price) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.category = category;
        this.itemAmount = itemAmount;
        this.price = price;
    }

    @Override
    public Map<String, String> getCharacteristics() {
        return Collections.unmodifiableMap(characteristics);
    }


    public void addCharacteristic(String name, String value) {
        characteristics.put(name, value);
    }

    public void removeCharacteristic(Iterable<String> names) {
        names.forEach(characteristics::remove);
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public String getImage() {
        return super.getImage();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public Category getCategory() {
        return super.getCategory();
    }

    @Override
    public Integer getItemAmount() {
        return super.getItemAmount();
    }

    @Override
    public Money getPrice() {
        return super.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Component component = (Component) o;
        return getId() != null && Objects.equals(getId(), component.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
