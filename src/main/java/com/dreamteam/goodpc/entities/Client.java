package com.dreamteam.goodpc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client {
    @Id
    @GeneratedValue(generator = "id_generator")
    private Long id;
    private String email;
    private String password;
    private String cardNumber;
    private String cardDate;
    private String cvv;
    @Transient
    private Map<Item, Integer> basket;

    public void addItem(Item item, int amount) {
        basket.put(item, amount);
    }

    public void addItems(Map<Item, Integer> items) {
        basket.putAll(items);
    }

    public void buyAllItems() {
        basket.forEach(Item::reduce);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;
        return id != null && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    private void setId(Long id) {
    }
    private void setBasket(Map<Item, Integer> basket) {
    }
}
