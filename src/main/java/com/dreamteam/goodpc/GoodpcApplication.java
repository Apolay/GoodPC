package com.dreamteam.goodpc;

import com.dreamteam.goodpc.data.CategoryRepository;
import com.dreamteam.goodpc.data.ComponentRepository;
import com.dreamteam.goodpc.data.ComputerRepository;
import com.dreamteam.goodpc.data.ServiceRepository;
import com.dreamteam.goodpc.entities.*;
import com.dreamteam.goodpc.entities.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
public class GoodpcApplication {
//TODO: add comparable to components
    public static void main(String[] args) {
        SpringApplication.run(GoodpcApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(CategoryRepository categoryRepository,
                                        ComponentRepository componentRepository,
                                        ServiceRepository serviceRepository) {
        return args -> {
            Category components = new Category("components");
            components.addSubCategories(List.of(
                    generateCategoryWithComponent("processor"),
                    generateCategoryWithComponent("graphic-card"),
                    generateCategoryWithComponent("motherboard"),
                    generateCategoryWithComponent("power-supply-unit"),
                    generateCategoryWithComponent("audio-card"),
                    generateCategoryWithComponent("cooler"),
                    generateCategoryWithComponent("ram"),
                    generateCategoryWithComponent("ssd"),
                    generateCategoryWithComponent("computer-case")
            ));



            Category computers = new Category("computers");
            Category gaming = new Category("gaming.jpg", "gaming");
            Category office = new Category("office.jpg", "office");
            Category home = new Category("home.jpg", "home");

            computers.addSubCategories((List.of(gaming, office, home)));

            log.info("generating computers");
            generateComputers(Collections.singleton("A1"), componentRepository, gaming);
            generateComputers(Collections.singleton("A2"), componentRepository, office);
            generateComputers(Collections.singleton("B1"), componentRepository, home);


            categoryRepository.saveAll(List.of(components, computers));

            serviceRepository.saveAll(
                    List.of(
                    new Service("Installing the software",new Money(10L),
                            "We install all the programs you need"),
                    new Service("Computer repair",new Money(15L),
                            "Diagnose problems and fix them"),
                    new Service("Diagnostics",new Money(5L),
                            "We will check your computer and fix the malfunctions"),
                    new Service("Maintenance",new Money(10L),
                            "We will clean your computer")
                    )
            );
        };

    }

    private Category generateCategoryWithComponent(String name) {
        Path path = Path.of("C:\\Users\\mrloo\\IdeaProjects\\goodpc\\src\\main\\resources\\images\\categories");
        File dir = path.toFile();
        log.info("start generating category with name: {}", name);
        String categoryImg = Arrays.stream(Objects.requireNonNull(dir.list()))
                .filter(s -> s.startsWith(name))
                .findFirst().orElseThrow();
        String categoryName = name;
        if(!categoryName.equals("ram") && !categoryName.equals("ssd"))
            categoryName += "s";

        Category parent = new Category(categoryImg, categoryName);

        path = Path.of("C:\\Users\\mrloo\\IdeaProjects\\goodpc\\src\\main\\resources\\images\\items\\components");
        dir = path.toFile();

        List<String> filenames = Arrays.stream(Objects.requireNonNull(dir.list()))
                .filter(s -> s.startsWith(name))
                .toList();

        Random random = new Random();
        int FILE_TYPE_LEN = 4;
        for (var filename : filenames) {
            String componentName = filename
                    .substring(0, filename.length() - FILE_TYPE_LEN)
                    .replaceAll("-", " ");

            Item component = Component.builder()
                    .category(parent)
                    .description(componentName)
                    .name(componentName)
                    .image(filename)
                    .itemAmount(random.nextInt(10))
                    .price(new Money(random.nextLong(500000L)))
                    .build();

            parent.addItem(component);

            log.info("filename {}", filename);
            log.info("component {}", component);
        }

        log.info("category with name {} successfully generated" , categoryName);
        return parent;
    }

    public void generateComputers(Iterable<String> models,
                                  ComponentRepository componentRepository,
                                  Category category) {
        Path path = Path.of("C:\\Users\\mrloo\\IdeaProjects\\goodpc\\src\\main\\resources\\images\\items\\computers");
        File dir = path.toFile();
        String image;
        Random random = new Random();
        for(String model : models) {
            image = Arrays.stream(Objects.requireNonNull(dir.list()))
                    .filter(file -> file.startsWith(model))
                    .findFirst()
                    .orElseThrow();
            Computer computer = Computer.builder()
                    .description("model " + model)
                    .image(image)
                    .name(model)
                    .itemAmount(random.nextInt(10))
                    .price(new Money(random.nextLong(100000)))
                    .build();
            try {
                computer.addComponents(
                        componentRepository.findAll().stream()
                        .filter(component -> component.getName().contains(model))
                        .collect(Collectors.toSet()));

                category.addItem(computer);
            } catch (Exception e) {
                log.warn("components don't added to model {}", model);
            }
        }
    }

}
