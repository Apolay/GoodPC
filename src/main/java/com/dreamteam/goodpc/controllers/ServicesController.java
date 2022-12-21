package com.dreamteam.goodpc.controllers;

import com.dreamteam.goodpc.data.ServiceRepository;
import com.dreamteam.goodpc.entities.Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/services")
public class ServicesController {
    private ServiceRepository repository;

    public ServicesController(ServiceRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String addServicesToModel(Model model) {
        List<Service> services = repository.findAll();
        model.addAttribute("services", services);
        return "services";
    }
}
