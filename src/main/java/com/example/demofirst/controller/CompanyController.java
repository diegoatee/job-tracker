package com.example.demofirst.controller;

import com.example.demofirst.entity.Company;
import com.example.demofirst.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> findAll() {
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> findById(@PathVariable Long id) {
        return companyService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Company> create(@RequestBody Company company) {
        Company saved = companyService.save(company);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> update(@PathVariable Long id, @RequestBody Company company) {
        return companyService.findById(id)
                .map(existing -> {
                    company.setId(id);
                    company.setCreatedAt(existing.getCreatedAt());
                    return ResponseEntity.ok(companyService.save(company));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (companyService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
