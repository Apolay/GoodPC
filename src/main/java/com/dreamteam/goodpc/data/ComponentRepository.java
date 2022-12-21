package com.dreamteam.goodpc.data;

import com.dreamteam.goodpc.entities.Category;
import com.dreamteam.goodpc.entities.Component;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComponentRepository extends JpaRepository<Component, Long> {
    List<Component> findByCategoryName(String name);

}