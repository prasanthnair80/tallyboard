package com.phonex.tallyboard.controller;

import com.phonex.tallyboard.model.CreateExpenseRequest;
import com.phonex.tallyboard.model.Expense;
import com.phonex.tallyboard.service.ExpenseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<Expense> getAll() {
        return expenseService.getAll();
    }

    @GetMapping("/project/{projectId}")
    public List<Expense> getByProject(@PathVariable String projectId) {
        return expenseService.getByProject(projectId);
    }

    @GetMapping("/project/{projectId}/total")
    public double getApprovedTotal(@PathVariable String projectId) {
        return expenseService.getApprovedTotal(projectId);
    }

    @GetMapping("/range")
    public List<Expense> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return expenseService.getByDateRange(from, to);
    }

    @PostMapping
    public ResponseEntity<Expense> submit(@RequestBody CreateExpenseRequest request) {
        return ResponseEntity.ok(expenseService.submit(request));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Expense> approve(@PathVariable String id) {
        try {
            return ResponseEntity.ok(expenseService.approve(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Expense> reject(@PathVariable String id) {
        try {
            return ResponseEntity.ok(expenseService.reject(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
