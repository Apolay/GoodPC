package com.dreamteam.goodpc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(generator = "id_generator")
    private Long id;

    @Setter
    private String image = "no-content.png";

    @Setter
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Category> subCategories = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    @ToString.Exclude
    private Set<Item> items = new HashSet<>();

    public String getPrivateName() {
        return name.replaceAll(" ", "-");
    }

    public void addSubCategory(Category category) {
        subCategories.add(category);
    }

    public void addSubCategories(Collection<Category> categories) {
        subCategories.addAll(categories);
    }

    public void addItem(Item item) {
        items.add(item);
        item.setCategory(this);
    }

    public void addItems(Collection<Item> items) {
        this.items.addAll(items);
        items.forEach(item -> item.setCategory(this));
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void removeItems(Collection<Item> items) {
        this.items.removeAll(items);
    }

    public Category(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Category category = (Category) o;
        return id != null && Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
