package com.lagranja.controllers;

import com.lagranja.models.Client;
import com.lagranja.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{idCard}")
    public ResponseEntity<Client> getClientById(@PathVariable String idCard) {
        Optional<Client> client = clientService.getClientById(idCard);
        return client.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientService.createClient(client);
        return ResponseEntity.ok(savedClient);
    }

    @PutMapping("/{idCard}")
    public ResponseEntity<Client> updateClient(@PathVariable String idCard, @RequestBody Client clientDetails) {
        try {
            Client updatedClient = clientService.updateClient(idCard, clientDetails);
            return ResponseEntity.ok(updatedClient);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idCard}")
    public ResponseEntity<Void> deleteClient(@PathVariable String idCard) {
        clientService.deleteClient(idCard);
        return ResponseEntity.noContent().build();
    }
}