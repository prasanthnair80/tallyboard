import { CreateExpenseRequest, Expense, Project } from './types';

const BASE = '/api';

async function request<T>(path: string, options?: RequestInit): Promise<T> {
  const res = await fetch(`${BASE}${path}`, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  });
  if (!res.ok) {
    const text = await res.text().catch(() => res.statusText);
    throw new Error(`${res.status}: ${text}`);
  }
  return res.json() as Promise<T>;
}

export function getProjects(): Promise<Project[]> {
  return request<Project[]>('/projects');
}

export function getProjectExpenses(projectId: string): Promise<Expense[]> {
  return request<Expense[]>(`/expenses/project/${projectId}`);
}

export function getApprovedTotal(projectId: string): Promise<number> {
  return request<number>(`/expenses/project/${projectId}/total`);
}

export function approveExpense(expenseId: string): Promise<Expense> {
  return request<Expense>(`/expenses/${expenseId}/approve`, { method: 'POST' });
}

export function rejectExpense(expenseId: string): Promise<Expense> {
  return request<Expense>(`/expenses/${expenseId}/reject`, { method: 'POST' });
}

export function submitExpense(data: CreateExpenseRequest): Promise<Expense> {
  return request<Expense>('/expenses', { method: 'POST', body: JSON.stringify(data) });
}
