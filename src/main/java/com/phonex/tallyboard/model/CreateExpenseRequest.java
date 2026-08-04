package com.phonex.tallyboard.model;

import java.time.LocalDate;

public class CreateExpenseRequest {

    private String projectId;
    private String submittedBy;
    private String description;
    private double amount;
    private String category;
    private LocalDate date;

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

    public LocalDate getDate()              { return date; }
    public void setDate(LocalDate date)     { this.date = date; }
}
