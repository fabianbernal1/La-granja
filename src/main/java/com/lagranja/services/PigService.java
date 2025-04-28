package com.lagranja.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lagranja.models.Breed;
import com.lagranja.models.Client;
import com.lagranja.models.Feeding;
import com.lagranja.models.Pig;
import com.lagranja.models.PigFeeding;
import com.lagranja.repositories.PigRepository;
import com.lagranja.repositories.ClientRepository;
import com.lagranja.repositories.FeedingRepository;

import jakarta.annotation.PostConstruct;

@Service
public class PigService {

    @Autowired
    private PigRepository pigRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private FeedingRepository feedingRepository;

    @Autowired
    private FeedingService feedingService;

    public List<Pig> getAllPigs() {
        return pigRepository.findAll();
    }

    public Optional<Pig> getPigById(Long id) {
        return pigRepository.findById(id);
    }

    public Pig createPig(Pig pig) {
        List<PigFeeding> originalFeedings = pig.getFeedings();
        pig.setFeedings(null);
        Pig pigNew = pigRepository.save(pig);
        List<PigFeeding> feedings = new ArrayList<>(originalFeedings.stream()
                .map(pf -> {
                    Feeding feeding = feedingService.getFeedingById(pf.getFeeding().getId())
                        .orElseThrow(() -> new RuntimeException("Feeding not found: " + pf.getFeeding().getId()));
                    return new PigFeeding(pigNew, feeding);
                })
                .toList());
        pigNew.setFeedings(feedings);
        return pigRepository.save(pigNew);
    }

    public Pig updatePig(Long id, Pig pigDetails) {
        return pigRepository.findById(id)
                .map(pig -> {
                    pig.setBreed(pigDetails.getBreed());
                    pig.setAge(pigDetails.getAge());
                    pig.setWeight(pigDetails.getWeight());
                    pig.setClient(pigDetails.getClient());
                    List<PigFeeding> originalFeedings = pigDetails.getFeedings();
                    List<PigFeeding> feedings = new ArrayList<>(originalFeedings.stream()
                            .map(pf -> {
                                Feeding feeding = feedingService.getFeedingById(pf.getFeeding().getId())
                                    .orElseThrow(() -> new RuntimeException("Feeding not found: " + pf.getFeeding().getId()));
                                return new PigFeeding(pig, feeding);
                            })
                            .toList());
                    pig.setFeedings(feedings);
                    return pigRepository.save(pig);
                })
                .orElseThrow(() -> new RuntimeException("Pig not found"));
    }

    public void deletePig(Long id) {
        pigRepository.deleteById(id);
    }

    @PostConstruct
    public void init() {
        if (pigRepository.count() == 0) {
            // Obtener clientes y alimentaciones existentes
            List<Client> clients = clientRepository.findAll();
            List<Feeding> feedings = feedingRepository.findAll();

            if (clients.size() < 2 || feedings.size() < 2) {
                System.out.println("⚠️ No hay suficientes clientes o alimentaciones en la BD para inicializar los cerdos.");
                return;
            }

            // Crear 4 cerdos, 2 por cliente
            Pig pig1 = new Pig(Breed.YORK, 2, 120.5, clients.get(0));
            Pig pig2 = new Pig(Breed.DUROC, 3, 150.2, clients.get(0));
            Pig pig3 = new Pig(Breed.HAMP, 1, 100.0, clients.get(1));
            Pig pig4 = new Pig(Breed.YORK, 4, 200.0, clients.get(1));

            // Guardar cerdos
            pigRepository.saveAll(List.of(pig1, pig2, pig3, pig4));

            // Asignar alimentaciones
            pig1.setFeedings(List.of(new PigFeeding(pig1, feedings.get(0))));
            pig2.setFeedings(List.of(new PigFeeding(pig2, feedings.get(1))));
            pig3.setFeedings(List.of(new PigFeeding(pig3, feedings.get(0))));
            pig4.setFeedings(List.of(new PigFeeding(pig4, feedings.get(1))));

            // Guardar cerdos con alimentaciones
            pigRepository.saveAll(List.of(pig1, pig2, pig3, pig4));

            System.out.println("✅ Cerdos y alimentaciones inicializados en la BD");
        }
    }
}
