package com.lagranja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    @GetMapping("/reportes/clientes")
    public String clientesReport(Model model) {
        return "redirect:/clientes.html"; 
    }
}
