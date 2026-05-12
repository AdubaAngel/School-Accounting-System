package com.school.accounting.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.school.accounting.model.ClassLevel;
import com.school.accounting.model.FeeStructure;
import com.school.accounting.model.Term;
import com.school.accounting.repository.FeeStructureRepository;

@Controller
public class FeeStructureController {
    private final FeeStructureRepository feeStructureRepository;

    public FeeStructureController(FeeStructureRepository feeStructureRepository) {
        this.feeStructureRepository = feeStructureRepository;
    }

    @GetMapping("/fee-structures")
    @PreAuthorize("hasRole('OWNER')")
    public String listFeeStructures(Model model) {
        model.addAttribute("feeStructure", feeStructureRepository.findAll());
        return "fee-structures";
    }

    @GetMapping("/fee-structures/add")
    @PreAuthorize("hasRole('OWNER')")
    public String showAddForm(Model model){
        model.addAttribute("feeStructure", new FeeStructure());
        model.addAttribute("classLevels", ClassLevel.values());
        model.addAttribute("terms", Term.values());
        return "add-fee-structure";
    }
}
