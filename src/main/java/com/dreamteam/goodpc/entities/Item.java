package com.dreamteam.goodpc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Map;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item {
    @Id
    @GeneratedValue(generator = "id_generator")
    protected Long id;

    protected String image = "no-content.png";

    @NotNull
    @Column(nullable = false, unique = true)
    protected String name;

    @NotNull
    @Column(nullable = false)
    protected String description;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    protected Category category;

//    @Column(nullable = false)
//    private String manufacturer;

//    @Digits(integer = 1, fraction = 0)
//    private Integer rating;

    @Column(name = "item_amount", nullable = false)
    protected Integer itemAmount = 0;

    @NotNull
    protected Money price;

    //TODO move to props
    @Transient
    protected String splitter = " - ";

    void reduce(int amount) {
        if(itemAmount < amount)
            throw new IllegalArgumentException("Item amount < buy amount");

        itemAmount -= amount;
    }

    void add(int amount) {
        itemAmount += amount;
    }

    public abstract Map<String, String> getCharacteristics();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;
        return id != null && Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    private void setId(Long id) {
    }
}
