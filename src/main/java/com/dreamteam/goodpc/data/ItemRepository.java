package com.dreamteam.goodpc.data;

import com.dreamteam.goodpc.entities.Category;
import com.dreamteam.goodpc.entities.Component;
import com.dreamteam.goodpc.entities.Item;
import com.dreamteam.goodpc.entities.Money;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByName(String name);
    @Query("select i from Item i where i.category.name = :name")
    List<Item> findByCategoryName(String name);
    List<Item> findByCategoryOrderByItemAmount(Category category);
    List<Item> findAllByCategoryAndPriceLessThan(Category category, Money money);
}