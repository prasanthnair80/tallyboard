package com.phonex.tallyboard.service;

import com.phonex.tallyboard.model.Expense;
import com.phonex.tallyboard.model.ExpenseStatus;
import com.phonex.tallyboard.model.Project;
import com.phonex.tallyboard.store.InMemoryStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseServiceTest {

    private InMemoryStore store;
    private ExpenseService service;

    @BeforeEach
    void setUp() {
        store = new InMemoryStore();
        service = new ExpenseService(store);
    }

    // ── getApprovedTotal ────────────────────────────────────────────────────

    @Test
    void getApprovedTotal_returnsZeroWhenNoExpenses() {
        store.addProject(new Project("p1", "Alpha", 1000.00));
        assertEquals(0.0, service.getApprovedTotal("p1"), 0.001);
    }

    @Test
    void getApprovedTotal_excludesPendingAndRejected() {
        // Whole-number amounts — passes even with the bug (int cast has no effect)
        store.addExpense(expense("e1", "p1", 100.00, ExpenseStatus.PENDING, LocalDate.now()));
        store.addExpense(expense("e2", "p1", 200.00, ExpenseStatus.REJECTED, LocalDate.now()));
        store.addExpense(expense("e3", "p1", 300.00, ExpenseStatus.APPROVED, LocalDate.now()));
        assertEquals(300.00, service.getApprovedTotal("p1"), 0.001);
    }

    @Test
    void getApprovedTotal_preservesDecimalAmounts() {
        // Decimal amounts: 45.50 + 30.75 = 76.25
        // Bug: mapToInt truncates to 45 + 30 = 75 → FAILS
        store.addExpense(expense("e1", "p1", 45.50, ExpenseStatus.APPROVED, LocalDate.now()));
        store.addExpense(expense("e2", "p1", 30.75, ExpenseStatus.APPROVED, LocalDate.now()));
        assertEquals(76.25, service.getApprovedTotal("p1"), 0.001);
    }

    // ── canApprove ──────────────────────────────────────────────────────────

    @Test
    void canApprove_returnsTrueForPendingExpense() {
        // Bug: returns false for PENDING (inverted condition) → FAILS
        Expense e = expense("e1", "p1", 100.00, ExpenseStatus.PENDING, LocalDate.now());
        assertTrue(service.canApprove(e));
    }

    @Test
    void canApprove_returnsFalseForAlreadyApprovedExpense() {
        // Bug: returns true for APPROVED (inverted condition) → FAILS
        Expense e = expense("e1", "p1", 100.00, ExpenseStatus.APPROVED, LocalDate.now());
        assertFalse(service.canApprove(e));
    }

    @Test
    void canApprove_returnsFalseForRejectedExpense() {
        // Bug: returns true for REJECTED → FAILS
        Expense e = expense("e1", "p1", 100.00, ExpenseStatus.REJECTED, LocalDate.now());
        assertFalse(service.canApprove(e));
    }

    // ── getByDateRange ──────────────────────────────────────────────────────

    @Test
    void getByDateRange_includesBothBoundaryDates() {
        LocalDate from = LocalDate.of(2024, 3, 1);
        LocalDate to   = LocalDate.of(2024, 3, 31);
        // These are exactly on the boundary — isAfter/isBefore excludes them → FAILS
        store.addExpense(expense("e1", "p1", 50.00, ExpenseStatus.PENDING, from));
        store.addExpense(expense("e2", "p1", 50.00, ExpenseStatus.PENDING, to));
        store.addExpense(expense("e3", "p1", 50.00, ExpenseStatus.PENDING, LocalDate.of(2024, 3, 15)));

        var result = service.getByDateRange(from, to);
        assertEquals(3, result.size(), "Both boundary dates must be included");
    }

    @Test
    void getByDateRange_excludesDatesOutsideRange() {
        // These are clearly outside the range — passes with or without the bug
        LocalDate from = LocalDate.of(2024, 3, 1);
        LocalDate to   = LocalDate.of(2024, 3, 31);
        store.addExpense(expense("e1", "p1", 50.00, ExpenseStatus.PENDING, LocalDate.of(2024, 2, 28)));
        store.addExpense(expense("e2", "p1", 50.00, ExpenseStatus.PENDING, LocalDate.of(2024, 4, 1)));
        store.addExpense(expense("e3", "p1", 50.00, ExpenseStatus.PENDING, LocalDate.of(2024, 3, 15)));

        var result = service.getByDateRange(from, to);
        assertEquals(1, result.size());
    }

    @Test
    void getByDateRange_returnsEmptyWhenNoMatchingDates() {
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to   = LocalDate.of(2024, 1, 31);
        store.addExpense(expense("e1", "p1", 50.00, ExpenseStatus.PENDING, LocalDate.of(2024, 3, 15)));

        var result = service.getByDateRange(from, to);
        assertTrue(result.isEmpty());
    }

    // ── getCurrentMonthExpenses ─────────────────────────────────────────────

    @Test
    void getCurrentMonthExpenses_includesExpensesFromThisMonth() {
        // Passes with and without the bug — same month, current year
        store.addExpense(expense("e1", "p1", 50.00, ExpenseStatus.PENDING, LocalDate.now()));
        var result = service.getCurrentMonthExpenses("p1");
        assertEquals(1, result.size());
    }

    @Test
    void getCurrentMonthExpenses_excludesDifferentMonth() {
        // Passes: two months ago is a clearly different month
        LocalDate twoMonthsAgo = LocalDate.now().minusMonths(2);
        store.addExpense(expense("e1", "p1", 50.00, ExpenseStatus.PENDING, twoMonthsAgo));
        var result = service.getCurrentMonthExpenses("p1");
        assertTrue(result.isEmpty());
    }

    @Test
    void getCurrentMonthExpenses_excludesSameMonthFromPreviousYear() {
        // Bug: getMonthValue() ignores year — same month last year passes the filter → FAILS
        LocalDate sameMonthLastYear = LocalDate.now().minusYears(1);
        store.addExpense(expense("e1", "p1", 100.00, ExpenseStatus.PENDING, sameMonthLastYear));
        var result = service.getCurrentMonthExpenses("p1");
        assertTrue(result.isEmpty(), "Expense from the same month in a prior year must not be included");
    }

    // ── helpers ─────────────────────────────────────────────────────────────

    private Expense expense(String id, String projectId, double amount,
                             ExpenseStatus status, LocalDate date) {
        return new Expense(id, projectId, "tester", "test expense",
                amount, "OTHER", status, date);
    }
}
