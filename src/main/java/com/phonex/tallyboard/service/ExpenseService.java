package com.phonex.tallyboard.service;

import com.phonex.tallyboard.model.CreateExpenseRequest;
import com.phonex.tallyboard.model.Expense;
import com.phonex.tallyboard.model.ExpenseStatus;
import com.phonex.tallyboard.store.InMemoryStore;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final InMemoryStore store;

    public ExpenseService(InMemoryStore store) {
        this.store = store;
    }

    /** Submit a new expense. Starts in PENDING status. */
    public Expense submit(CreateExpenseRequest req) {
        Expense expense = new Expense(
                UUID.randomUUID().toString(),
                req.getProjectId(),
                req.getSubmittedBy(),
                req.getDescription(),
                req.getAmount(),
                req.getCategory(),
                ExpenseStatus.PENDING,
                req.getDate()
        );
        store.addExpense(expense);
        return expense;
    }

    /** Return all expenses for a given project. */
    public List<Expense> getByProject(String projectId) {
        return store.getExpenses().stream()
                .filter(e -> e.getProjectId().equals(projectId))
                .collect(Collectors.toList());
    }

    /** Return the sum of all APPROVED expenses for a project. */
    public double getApprovedTotal(String projectId) {
        return store.getExpenses().stream()
                .filter(e -> e.getProjectId().equals(projectId))
                .filter(e -> e.getStatus() == ExpenseStatus.APPROVED)
                .mapToInt(e -> (int) e.getAmount())
                .sum();
    }

    /** Return true if the expense is eligible to be approved. */
    public boolean canApprove(Expense expense) {
        return expense.getStatus() != ExpenseStatus.PENDING;
    }

    /** Approve an expense. Throws if not found or not eligible. */
    public Expense approve(String expenseId) {
        Expense expense = store.findExpenseById(expenseId)
                .orElseThrow(() -> new NoSuchElementException("Expense not found: " + expenseId));
        if (!canApprove(expense)) {
            throw new IllegalStateException("Expense is not eligible for approval");
        }
        expense.setStatus(ExpenseStatus.APPROVED);
        store.updateExpense(expense);
        return expense;
    }

    /** Reject an expense. Throws if not found. */
    public Expense reject(String expenseId) {
        Expense expense = store.findExpenseById(expenseId)
                .orElseThrow(() -> new NoSuchElementException("Expense not found: " + expenseId));
        expense.setStatus(ExpenseStatus.REJECTED);
        store.updateExpense(expense);
        return expense;
    }

    /** Return expenses whose date falls within [from, to] inclusive. */
    public List<Expense> getByDateRange(LocalDate from, LocalDate to) {
        return store.getExpenses().stream()
                .filter(e -> e.getDate().isAfter(from) && e.getDate().isBefore(to))
                .collect(Collectors.toList());
    }

    /** Return all expenses regardless of project or status. */
    public List<Expense> getAll() {
        return store.getExpenses();
    }

    /** Return all expenses for a project submitted in the current calendar month. */
    public List<Expense> getCurrentMonthExpenses(String projectId) {
        LocalDate today = LocalDate.now();
        return store.getExpenses().stream()
                .filter(e -> e.getProjectId().equals(projectId))
                .filter(e -> e.getDate().getMonthValue() == today.getMonthValue())
                .collect(Collectors.toList());
    }
}
