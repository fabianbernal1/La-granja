package com.lagranja.controllers;

import com.lagranja.models.Feeding;
import com.lagranja.services.FeedingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedings")
public class FeedingController {

    private final FeedingService feedingService;

    public FeedingController(FeedingService feedingService) {
        this.feedingService = feedingService;
    }

    @GetMapping
    public List<Feeding> getAllFeedings() {
        return feedingService.getAllFeedings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feeding> getFeedingById(@PathVariable Long id) {
        Optional<Feeding> feeding = feedingService.getFeedingById(id);
        return feeding.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Feeding> createFeeding(@RequestBody Feeding feeding) {
        Feeding savedFeeding = feedingService.createFeeding(feeding);
        return ResponseEntity.ok(savedFeeding);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feeding> updateFeeding(@PathVariable Long id, @RequestBody Feeding feedingDetails) {
        try {
            Feeding updatedFeeding = feedingService.updateFeeding(id, feedingDetails);
            return ResponseEntity.ok(updatedFeeding);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeeding(@PathVariable Long id) {
        feedingService.deleteFeeding(id);
        return ResponseEntity.noContent().build();
    }
}