package com.phonex.tallyboard.algorithm;

import com.phonex.tallyboard.model.Expense;

import java.util.Collections;
import java.util.List;
import java.time.LocalDate;

public class ExpenseAnalyzer {

    /**
     * Given a list of expenses and an integer k, return the k expenses
     * with the highest amount, sorted by amount descending.
     * If there are fewer than k expenses in the list, return all of them sorted.
     * If k is zero or the list is empty, return an empty list.
     *
     * Examples:
     *   findTopExpenses([150.00, 30.00, 75.50, 200.00], k=2) → [200.00, 150.00]
     *   findTopExpenses([50.00, 80.00], k=5)                 → [80.00, 50.00]
     */
    public List<Expense> findTopExpenses(List<Expense> expenses, int k) {
        return Collections.emptyList();
    }

    /**
     * Given a list of expenses, return the length of the longest run of
     * consecutive calendar days on which at least one expense was submitted.
     * Duplicate dates within the same day count as a single day.
     * If the list is empty, return 0.
     *
     * Examples:
     *   dates [Mar 1, Mar 2, Mar 3, Mar 5]  →  3  (Mar 1–3 is the longest run)
     *   dates [Jan 10, Jan 12, Jan 13]       →  2  (Jan 12–13)
     *   dates [Jun 7]                         →  1
     */
    public int longestExpenseStreak(List<Expense> expenses) {
        return 0;
    }
}
