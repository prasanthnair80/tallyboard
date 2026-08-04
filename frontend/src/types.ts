export type ExpenseStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export interface Expense {
  id: string;
  projectId: string;
  submittedBy: string;
  description: string;
  amount: number;
  category: string;
  status: ExpenseStatus;
  date: string; // ISO 8601 date string e.g. "2024-03-15"
}

export interface Project {
  id: string;
  name: string;
  budget: number;
}

export interface CreateExpenseRequest {
  projectId: string;
  submittedBy: string;
  description: string;
  amount: number;
  category: string;
  date: string; // ISO 8601 date string
}
