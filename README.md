# TallyBoard — Coding Exercise

A team expense tracking platform. Employees submit expenses for approval, grouped by project.

## Prerequisites

- Java 11+
- Maven 3.6+
- Node.js 18+

## Running the backend

```bash
# From the project root
mvn spring-boot:run
```

The API starts on http://localhost:8080.

You will see some failing tests — that is expected. Run them with:

```bash
mvn test
```

## Running the frontend

```bash
cd frontend
npm install
npm run dev
```

The UI starts on http://localhost:5173. It proxies `/api` requests to the backend on port 8080, so both must be running.

## Project structure

```
tallyboard/
├── pom.xml
├── src/
│   ├── main/java/com/phonex/tallyboard/
│   │   ├── model/          # Expense, Project, ExpenseStatus, CreateExpenseRequest
│   │   ├── store/          # InMemoryStore
│   │   ├── service/        # ExpenseService, ProjectService
│   │   ├── controller/     # ExpenseController, ProjectController
│   │   └── algorithm/      # ExpenseAnalyzer (stub)
│   └── test/java/com/phonex/tallyboard/
│       ├── service/        # ExpenseServiceTest
│       └── algorithm/      # ExpenseAnalyzerTest
└── frontend/
    └── src/
        ├── types.ts
        ├── api.ts
        └── components/
            └── ExpenseDashboard.tsx  (stub)
```

## API overview

| Method | Path | Description |
|--------|------|-------------|
| GET | /api/projects | List all projects |
| GET | /api/projects/{id} | Get project by id |
| GET | /api/expenses | List all expenses |
| GET | /api/expenses/project/{id} | Expenses for a project |
| GET | /api/expenses/project/{id}/total | Approved total for a project |
| GET | /api/expenses/range?from=&to= | Expenses within a date range |
| POST | /api/expenses | Submit new expense |
| POST | /api/expenses/{id}/approve | Approve an expense |
| POST | /api/expenses/{id}/reject | Reject an expense |
