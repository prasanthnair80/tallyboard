package com.phonex.tallyboard.model;

public class Project {

    private String id;
    private String name;
    private double budget;

    public Project() {}

    public Project(String id, String name, double budget) {
        this.id = id;
        this.name = name;
        this.budget = budget;
    }

    public String getId()               { return id; }
    public void setId(String id)        { this.id = id; }

    public String getName()             { return name; }
    public void setName(String name)    { this.name = name; }

    public double getBudget()               { return budget; }
    public void setBudget(double budget)    { this.budget = budget; }
}
