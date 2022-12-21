package com.dreamteam.goodpc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Service {
    @Id
    @GeneratedValue(generator = "id_generator")
    private Long id;
    private String image = "no-content.png";
    private String description;
    private Money price;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    public Service(String image, String description, Money price, String name) {
        this.image = image;
        this.description = description;
        this.price = price;
        this.name = name;
    }

    public Service(String description, Money price, String name) {
        this.description = description;
        this.price = price;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Service service = (Service) o;
        return id != null && Objects.equals(id, service.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    private void setId(Long id) {
    }
}
