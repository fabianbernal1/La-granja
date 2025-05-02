package com.lagranja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReportController {

    @GetMapping("/reportes/clientes")
    public String clientesReport(Model model) {
        return "redirect:/clientes.html"; 
    }
    
    // Pantalla para crear un nuevo cerdo
    @GetMapping("/reportes/pig/create")
    public String pigCreation(Model model) {
        // Aquí puedes agregar atributos si los necesitas en la vista
        return "redirect:/pigCreationUpdate.html";  // sin redirect, porque quieres cargar la vista
    }

    // Pantalla para actualizar un cerdo existente (recibe id)
    @GetMapping("/reportes/pig/edit/{id}")
    public String pigUpdate(@PathVariable("id") Long id, Model model) {
        // Pasas el id al modelo para que la vista lo use
        model.addAttribute("pigId", id);
        return "redirect:/pigCreationUpdate.html?pigId=" + id;
    }

    @GetMapping("/reportes/pigsList")
    public String pigsList(Model model) {
        return "redirect:/pigsList.html";
    }
}
