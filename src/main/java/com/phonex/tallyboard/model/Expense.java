package com.phonex.tallyboard.model;

import java.time.LocalDate;

public class Expense {

    private String id;
    private String projectId;
    private String submittedBy;
    private String description;
    private double amount;
    private String category;
    private ExpenseStatus status;
    private LocalDate date;

    public Expense() {}

    public Expense(String id, String projectId, String submittedBy,
                   String description, double amount, String category,
                   ExpenseStatus status, LocalDate date) {
        this.id = id;
        this.projectId = projectId;
        this.submittedBy = submittedBy;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.status = status;
        this.date = date;
    }

    public String getId()               { return id; }
    public void setId(String id)        { this.id = id; }

    public String getProjectId()                    { return projectId; }
    public void setProjectId(String projectId)      { this.projectId = projectId; }

    public String getSubmittedBy()                  { return submittedBy; }
    public void setSubmittedBy(String submittedBy)  { this.submittedBy = submittedBy; }

    public String getDescription()                  { return description; }
    public void setDescription(String description)  { this.description = description; }

    public double getAmount()               { return amount; }
    public void setAmount(double amount)    { this.amount = amount; }

    public String getCategory()                 { return category; }
    public void setCategory(String category)    { this.category = category; }

    public ExpenseStatus getStatus()                { return status; }
    public void setStatus(ExpenseStatus status)     { this.status = status; }

    public LocalDate getDate()              { return date; }
    public void setDate(LocalDate date)     { this.date = date; }
}
