package com.example.demofirst.controller;

import com.example.demofirst.entity.Application;
import com.example.demofirst.entity.Company;
import com.example.demofirst.service.ApplicationService;
import com.example.demofirst.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final CompanyService companyService;

    public ApplicationController(ApplicationService applicationService, CompanyService companyService) {
        this.applicationService = applicationService;
        this.companyService = companyService;
    }

    @GetMapping
    public List<Application> findAll() {
        return applicationService.findAll();
    }

    @GetMapping("/company/{companyId}")
    public List<Application> findByCompanyId(@PathVariable Long companyId) {
        return applicationService.findByCompanyId(companyId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> findById(@PathVariable Long id) {
        return applicationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Application> create(@RequestBody Application application) {
        if (application.getCompany() == null || application.getCompany().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Company company = companyService.findById(application.getCompany().getId())
                .orElse(null);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        application.setCompany(company);
        Application saved = applicationService.save(application);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> update(@PathVariable Long id, @RequestBody Application application) {
        return applicationService.findById(id)
                .map(existing -> {
                    application.setId(id);
                    application.setCreatedAt(existing.getCreatedAt());
                    application.setCompany(existing.getCompany());
                    return ResponseEntity.ok(applicationService.save(application));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (applicationService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        applicationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}