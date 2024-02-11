package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.services.PromotionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;

import java.util.List;

@RestController
@PreAuthorize("hasRole('SECRETAIRE')")
@RequestMapping("/api/v1/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    // Create a new Promotion
    @PostMapping
    public ResponseEntity<ApiResponse<Promotion>> createPromotion(@RequestBody Promotion promotion) {
        Promotion createdPromotion = promotionService.createPromotion(promotion);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(createdPromotion));
    }

    // Read all Promotions
    @GetMapping
    public ResponseEntity<ApiResponse<List<Promotion>>> getAllPromotions() {
        List<Promotion> promotions = promotionService.getAllPromotions();
        return ResponseEntity.ok(ApiResponse.ok(promotions));
    }

    // Read a Promotion by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Promotion>> getPromotionById(@PathVariable String id) {
        try {
            Promotion promotion = promotionService.getPromotionById(id).orElseThrow(() -> new ResourceNotFoundException("Promotion not found"));
            return ResponseEntity.ok(ApiResponse.ok(promotion));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Promotion not found", null));
        }
    }

    // Update a Promotion
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Promotion>> updatePromotion(@PathVariable String id, @RequestBody Promotion promotion) throws ResourceNotFoundException {
        Promotion updatedPromotion = promotionService.updatePromotion(id, promotion);
        return ResponseEntity.ok(ApiResponse.ok(updatedPromotion));
    }

    // Delete a Promotion by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePromotion(@PathVariable String id) throws ResourceNotFoundException {
        promotionService.deletePromotion(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
