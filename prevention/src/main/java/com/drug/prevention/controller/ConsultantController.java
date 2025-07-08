// 📄 controller/ConsultantController.java

package com.drug.prevention.controller;

import com.drug.prevention.entity.Consultant;
import com.drug.prevention.repository.ConsultantRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultants")
public class ConsultantController {

    private final ConsultantRepository consultantRepo;

    public ConsultantController(ConsultantRepository consultantRepo) {
        this.consultantRepo = consultantRepo;
    }

    @GetMapping
    public List<Consultant> getAll() {
        return consultantRepo.findAll();
    }
}
