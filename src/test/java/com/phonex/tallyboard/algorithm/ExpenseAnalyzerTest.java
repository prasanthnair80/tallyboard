package com.phonex.tallyboard.algorithm;

import com.phonex.tallyboard.model.Expense;
import com.phonex.tallyboard.model.ExpenseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseAnalyzerTest {

    private ExpenseAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new ExpenseAnalyzer();
    }

    @Test
    void findTopExpenses_returnsTopKByAmount() {
        List<Expense> expenses = Arrays.asList(
                expense("e1", 150.00),
                expense("e2", 30.00),
                expense("e3", 75.50),
                expense("e4", 200.00)
        );
        List<Expense> result = analyzer.findTopExpenses(expenses, 2);
        assertEquals(2, result.size());
        assertEquals(200.00, result.get(0).getAmount(), 0.001);
        assertEquals(150.00, result.get(1).getAmount(), 0.001);
    }

    @Test
    void findTopExpenses_resultSortedByAmountDescending() {
        List<Expense> expenses = Arrays.asList(
                expense("e1", 50.00),
                expense("e2", 300.00),
                expense("e3", 150.00)
        );
        List<Expense> result = analyzer.findTopExpenses(expenses, 3);
        assertEquals(3, result.size());
        assertTrue(result.get(0).getAmount() >= result.get(1).getAmount());
        assertTrue(result.get(1).getAmount() >= result.get(2).getAmount());
    }

    @Test
    void findTopExpenses_whenKExceedsListSize_returnsAll() {
        List<Expense> expenses = Arrays.asList(
                expense("e1", 100.00),
                expense("e2", 200.00)
        );
        List<Expense> result = analyzer.findTopExpenses(expenses, 10);
        assertEquals(2, result.size());
    }

    @Test
    void findTopExpenses_singleExpense_returnsIt() {
        List<Expense> expenses = Collections.singletonList(expense("e1", 99.00));
        List<Expense> result = analyzer.findTopExpenses(expenses, 1);
        assertEquals(1, result.size());
        assertEquals(99.00, result.get(0).getAmount(), 0.001);
    }

    @Test
    void findTopExpenses_whenKIsZero_returnsEmpty() {
        List<Expense> expenses = Arrays.asList(expense("e1", 100.00), expense("e2", 200.00));
        List<Expense> result = analyzer.findTopExpenses(expenses, 0);
        assertTrue(result.isEmpty());
    }

    @Test
    void findTopExpenses_withEmptyList_returnsEmpty() {
        List<Expense> result = analyzer.findTopExpenses(Collections.emptyList(), 3);
        assertTrue(result.isEmpty());
    }

    // ── longestExpenseStreak ─────────────────────────────────────────────────

    @Test
    void longestStreak_emptyList_returnsZero() {
        // Stub returns 0 — PASSES
        assertEquals(0, analyzer.longestExpenseStreak(Collections.emptyList()));
    }

    @Test
    void longestStreak_singleExpense_returnsOne() {
        // FAILS (stub returns 0)
        List<Expense> expenses = Collections.singletonList(expenseOn("e1", LocalDate.of(2024, 3, 10)));
        assertEquals(1, analyzer.longestExpenseStreak(expenses));
    }

    @Test
    void longestStreak_consecutiveDaysIsLongestRun() {
        // Mar 1, 2, 3 consecutive then gap then Mar 5 — longest = 3, FAILS (stub returns 0)
        List<Expense> expenses = Arrays.asList(
                expenseOn("e1", LocalDate.of(2024, 3, 1)),
                expenseOn("e2", LocalDate.of(2024, 3, 2)),
                expenseOn("e3", LocalDate.of(2024, 3, 3)),
                expenseOn("e4", LocalDate.of(2024, 3, 5))
        );
        assertEquals(3, analyzer.longestExpenseStreak(expenses));
    }

    @Test
    void longestStreak_nonConsecutiveDaysReturnsLongest() {
        // Jan 10 alone, then Jan 12–13 together — longest = 2, FAILS (stub returns 0)
        List<Expense> expenses = Arrays.asList(
                expenseOn("e1", LocalDate.of(2024, 1, 10)),
                expenseOn("e2", LocalDate.of(2024, 1, 12)),
                expenseOn("e3", LocalDate.of(2024, 1, 13))
        );
        assertEquals(2, analyzer.longestExpenseStreak(expenses));
    }

    @Test
    void longestStreak_duplicateDatesCountedOnce() {
        // Three expenses all on same day — streak = 1, FAILS (stub returns 0)
        List<Expense> expenses = Arrays.asList(
                expenseOn("e1", LocalDate.of(2024, 5, 7)),
                expenseOn("e2", LocalDate.of(2024, 5, 7)),
                expenseOn("e3", LocalDate.of(2024, 5, 7))
        );
        assertEquals(1, analyzer.longestExpenseStreak(expenses));
    }

    // ── helpers ─────────────────────────────────────────────────────────────

    private Expense expense(String id, double amount) {
        return new Expense(id, "proj-1", "tester", "test",
                amount, "OTHER", ExpenseStatus.PENDING, LocalDate.now());
    }

    private Expense expenseOn(String id, LocalDate date) {
        return new Expense(id, "proj-1", "tester", "test",
                50.00, "OTHER", ExpenseStatus.PENDING, date);
    }
}
