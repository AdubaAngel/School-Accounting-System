package com.school.accounting.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    @PreAuthorize("hasAnyRole('OWNER', 'ACCOUNTANT', 'AUDITOR')")
    public String listFeeStructures(Model model) {
        model.addAttribute("feeStructures", feeStructureRepository.findAll());
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

    @PostMapping("/fee-structures/add")
    @PreAuthorize("hasRole('OWNER')")
    public String saveFeeStructure(@ModelAttribute FeeStructure feeStructure){
        feeStructureRepository.save(feeStructure);
        return "redirect:/fee-structures";
    }

    @GetMapping("/fee-structures/edit/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        FeeStructure feeStructure = feeStructureRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fee structure not found"));
        
        model.addAttribute("feeStructure", feeStructure);
        model.addAttribute("classLevels", ClassLevel.values());
        model.addAttribute("terms", Term.values());
        
        return "edit-fee-structure";
    }

    @PostMapping("/fee-structures/update/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String updateFeeStructure(@PathVariable Long id, 
                                    @ModelAttribute FeeStructure updatedFeeStructure) {
        FeeStructure feeStructure = feeStructureRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fee structure not found"));
        
        feeStructure.setClassLevel(updatedFeeStructure.getClassLevel());
        feeStructure.setTerm(updatedFeeStructure.getTerm());
        feeStructure.setAcademicYear(updatedFeeStructure.getAcademicYear());
        feeStructure.setAmount(updatedFeeStructure.getAmount());
        
        feeStructureRepository.save(feeStructure);
        
        return "redirect:/fee-structures";
    }

    @GetMapping("/fee-structures/delete/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String deleteFeeStructure(@PathVariable Long id) {
        FeeStructure feeStructure = feeStructureRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fee structure not found"));
        
        feeStructureRepository.deleteById(id);
        
        return "redirect:/fee-structures";
    }
}
