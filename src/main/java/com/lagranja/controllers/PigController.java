package com.lagranja.controllers;

import com.lagranja.models.Pig;
import com.lagranja.repositories.FeedingRepository;
import com.lagranja.services.FeedingService;
import com.lagranja.services.PigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pigs")
public class PigController {

    private final PigService pigService;
    private final FeedingService feedingService;
   

    public PigController(PigService pigService, FeedingService feedingService) {
		super();
		this.pigService = pigService;
		this.feedingService = feedingService;
	}


    @GetMapping
    public List<Pig> getAllPigs() {
        return pigService.getAllPigs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pig> getPigById(@PathVariable Long id) {
        Optional<Pig> pig = pigService.getPigById(id);
        return pig.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pig> createPig(@RequestBody Pig pig) {
        Pig savedPig = pigService.createPig(pig);
        return ResponseEntity.ok(savedPig);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pig> updatePig(@PathVariable Long id, @RequestBody Pig pigDetails) {
        try {
            Pig updatedPig = pigService.updatePig(id, pigDetails);
            return ResponseEntity.ok(updatedPig);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePig(@PathVariable Long id) {
        pigService.deletePig(id);
        return ResponseEntity.noContent().build();
    }
}