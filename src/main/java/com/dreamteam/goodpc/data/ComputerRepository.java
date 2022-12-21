package com.dreamteam.goodpc.data;

import com.dreamteam.goodpc.entities.Computer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComputerRepository extends JpaRepository<Computer, Long> {
}