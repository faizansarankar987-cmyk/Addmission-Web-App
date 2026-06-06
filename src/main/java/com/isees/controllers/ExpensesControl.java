package com.isees.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.isees.dtos.ExpensesDto;
import com.isees.entities.Expenses;
import com.isees.services.ExpensesServi;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

@RestController
@RequestMapping("/expenses")   // ✅ lowercase
@CrossOrigin("*")              // ✅ required for frontend
public class ExpensesControl {

    @Autowired 
    ExpensesServi s;

    // ---------------- ADD EXPENSE ----------------
    @PostMapping("/add")
    public ResponseEntity<?> uploadExpense(
            @RequestPart("data") String data,
            @RequestPart(value="file", required=false) MultipartFile file
    ){
        try{
            ObjectMapper mapper = new ObjectMapper();
            ExpensesDto dto = mapper.readValue(data, ExpensesDto.class);

            s.save(dto, file);
            return ResponseEntity.ok("Expense uploaded successfully");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    // ---------------- GET ALL ----------------
    @GetMapping("/all")
    public List<Expenses> getAll() {
        return s.getAll();
    }
    @GetMapping("/{id}")
    public Expenses getById(@PathVariable int id) {
        return s.getById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable int id,
            @RequestPart("data") ExpensesDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {
        s.update(id, dto, file);
        return ResponseEntity.ok("Expense updated successfully");
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            s.delete(id);
            return ResponseEntity.ok("Deleted Successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Expense not found");
        }
    }
    @GetMapping("/total-expense")
    public Double totalExpense() {
        return s.getTotalExpense();
    }

    // ---------------- MONTHLY EXPENSE ----------------
    @GetMapping("/monthly-expense")
    public Double monthlyExpense() {
        return s.getMonthlyExpense();
    }

}
