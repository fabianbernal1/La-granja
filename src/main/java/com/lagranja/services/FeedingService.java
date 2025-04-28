package com.lagranja.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lagranja.models.Feeding;
import com.lagranja.repositories.FeedingRepository;

import jakarta.annotation.PostConstruct;

@Service
public class FeedingService {

    @Autowired
    private FeedingRepository feedingRepository;
    
    @PostConstruct
    public void init() {
        if (feedingRepository.count() == 0) {
            feedingRepository.save(new Feeding("Corn Meal", 2.5));
            feedingRepository.save(new Feeding("Soybean Meal", 3.0));
            feedingRepository.save(new Feeding("Wheat Bran", 1.8));
            System.out.println("Datos de alimentación insertados en la BD ✅");
        }
    }

    public List<Feeding> getAllFeedings() {
        return feedingRepository.findAll();
    }

    public Optional<Feeding> getFeedingById(Long id) {
        return feedingRepository.findById(id);
    }

    public Feeding createFeeding(Feeding feeding) {
        return feedingRepository.save(feeding);
    }

    public Feeding updateFeeding(Long id, Feeding feedingDetails) {
        return feedingRepository.findById(id)
                .map(feeding -> {
                    feeding.setDescription(feedingDetails.getDescription());
                    feeding.setDose(feedingDetails.getDose());
                    return feedingRepository.save(feeding);
                })
                .orElseThrow(() -> new RuntimeException("Feeding not found"));
    }

    public void deleteFeeding(Long id) {
        feedingRepository.deleteById(id);
    }
}
