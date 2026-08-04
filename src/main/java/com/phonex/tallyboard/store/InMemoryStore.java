package com.phonex.tallyboard.store;

import com.phonex.tallyboard.model.Expense;
import com.phonex.tallyboard.model.ExpenseStatus;
import com.phonex.tallyboard.model.Project;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class InMemoryStore {

    private final List<Expense> expenses = new ArrayList<>();
    private final List<Project> projects = new ArrayList<>();

    @PostConstruct
    public void seed() {
        projects.add(new Project("proj-1", "Website Redesign", 5000.00));
        projects.add(new Project("proj-2", "Mobile App Launch", 12000.00));
        projects.add(new Project("proj-3", "Internal Tools", 3000.00));

        expenses.add(new Expense("exp-1", "proj-1", "alice", "Design tools subscription",
                149.99, "SOFTWARE", ExpenseStatus.APPROVED, LocalDate.of(2024, 3, 10)));
        expenses.add(new Expense("exp-2", "proj-1", "bob", "Team lunch",
                85.50, "MEALS", ExpenseStatus.APPROVED, LocalDate.of(2024, 3, 15)));
        expenses.add(new Expense("exp-3", "proj-1", "alice", "Travel to client",
                320.00, "TRAVEL", ExpenseStatus.PENDING, LocalDate.of(2024, 3, 20)));
        expenses.add(new Expense("exp-4", "proj-2", "carol", "Conference ticket",
                750.00, "TRAVEL", ExpenseStatus.APPROVED, LocalDate.of(2024, 3, 8)));
        expenses.add(new Expense("exp-5", "proj-2", "david", "UI prototyping tool",
                99.00, "SOFTWARE", ExpenseStatus.REJECTED, LocalDate.of(2024, 3, 12)));
        expenses.add(new Expense("exp-6", "proj-3", "alice", "Office supplies",
                45.75, "SUPPLIES", ExpenseStatus.PENDING, LocalDate.of(2024, 3, 22)));
    }

    // --- Expense operations ---

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<Expense> getExpenses() {
        return new ArrayList<>(expenses);
    }

    public Optional<Expense> findExpenseById(String id) {
        return expenses.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public void updateExpense(Expense updated) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId().equals(updated.getId())) {
                expenses.set(i, updated);
                return;
            }
        }
    }

    public void clear() {
        expenses.clear();
        projects.clear();
    }

    // --- Project operations ---

    public void addProject(Project project) {
        projects.add(project);
    }

    public List<Project> getProjects() {
        return new ArrayList<>(projects);
    }

    public Optional<Project> findProjectById(String id) {
        return projects.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
}
