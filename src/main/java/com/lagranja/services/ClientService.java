package com.lagranja.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lagranja.models.Client;
import com.lagranja.repositories.ClientRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    
    @PostConstruct
    public void init() {
        if (clientRepository.count() == 0) { 
            clientRepository.save(new Client("12345678", "John", "Doe", "123 Farm Street", "555-1234"));
            clientRepository.save(new Client("87654321", "Jane", "Smith", "456 Rural Road", "555-5678"));
            System.out.println("Datos insertados en la BD");
        }
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(String idCard) {
        return clientRepository.findById(idCard);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(String idCard, Client clientDetails) {
        return clientRepository.findById(idCard)
                .map(client -> {
                    client.setFirstName(clientDetails.getFirstName());
                    client.setLastName(clientDetails.getLastName());
                    client.setAddress(clientDetails.getAddress());
                    client.setPhone(clientDetails.getPhone());
                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public void deleteClient(String idCard) {
        clientRepository.deleteById(idCard);
    }
}

