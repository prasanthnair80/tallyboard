import { useState, useEffect } from 'react';
import { Expense, Project, ExpenseStatus } from '../types';
import { getProjects, getProjectExpenses, approveExpense, rejectExpense } from '../api';

/**
 * ExpenseDashboard — the core UI component for this exercise.
 *
 * This component is a stub. Implement it so a user can:
 *   1. See a list of projects and select one.
 *   2. View all expenses for the selected project.
 *   3. Filter expenses by status (PENDING / APPROVED / REJECTED / All).
 *   4. See the total of approved expenses for the selected project.
 *   5. Approve or reject individual PENDING expenses inline.
 *
 * Stretch goal: add a form to submit a new expense.
 */
export default function ExpenseDashboard() {
  const [projects, setProjects] = useState<Project[]>([]);
  const [selectedProjectId, setSelectedProjectId] = useState<string>('');
  const [expenses, setExpenses] = useState<Expense[]>([]);
  const [statusFilter, setStatusFilter] = useState<ExpenseStatus | 'ALL'>('ALL');
  const [approvedTotal, setApprovedTotal] = useState<number>(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  // TODO: fetch projects on mount
  useEffect(() => {}, []);

  // TODO: fetch expenses when selectedProjectId changes
  useEffect(() => {}, [selectedProjectId]);

  async function handleApprove(expenseId: string) {
    // TODO: call approveExpense, then refresh the expense list and approved total
  }

  async function handleReject(expenseId: string) {
    // TODO: call rejectExpense, then refresh the expense list
  }

  // TODO: derive filteredExpenses from expenses + statusFilter
  const filteredExpenses: Expense[] = [];

  // TODO: derive approvedTotal from expenses (hint: filter by APPROVED, sum amounts)

  if (loading) return <p>Loading…</p>;

  return (
    <div style={{ fontFamily: 'sans-serif', maxWidth: 800, margin: '0 auto', padding: 24 }}>
      <h1>TallyBoard</h1>

      {error && <p style={{ color: 'red' }}>{error}</p>}

      {/* Project selector */}
      <div>
        <label htmlFor="project-select">Project: </label>
        <select
          id="project-select"
          value={selectedProjectId}
          onChange={e => setSelectedProjectId(e.target.value)}
        >
          <option value="">— select a project —</option>
          {projects.map(p => (
            <option key={p.id} value={p.id}>{p.name}</option>
          ))}
        </select>
      </div>

      {selectedProjectId && (
        <>
          {/* Status filter */}
          <div style={{ marginTop: 16 }}>
            <label>Filter: </label>
            {(['ALL', 'PENDING', 'APPROVED', 'REJECTED'] as const).map(s => (
              <button
                key={s}
                onClick={() => setStatusFilter(s)}
                style={{ marginRight: 8, fontWeight: statusFilter === s ? 'bold' : 'normal' }}
              >
                {s}
              </button>
            ))}
          </div>

          {/* Approved total */}
          <p style={{ marginTop: 8 }}>
            <strong>Approved total:</strong> ${approvedTotal.toFixed(2)}
          </p>

          {/* Expense list */}
          <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: 12 }}>
            <thead>
              <tr>
                <th style={th}>Date</th>
                <th style={th}>Description</th>
                <th style={th}>Category</th>
                <th style={th}>Amount</th>
                <th style={th}>Status</th>
                <th style={th}>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredExpenses.map(e => (
                <tr key={e.id}>
                  <td style={td}>{e.date}</td>
                  <td style={td}>{e.description}</td>
                  <td style={td}>{e.category}</td>
                  <td style={td}>${e.amount.toFixed(2)}</td>
                  <td style={td}>{e.status}</td>
                  <td style={td}>
                    {e.status === 'PENDING' && (
                      <>
                        <button onClick={() => handleApprove(e.id)} style={{ marginRight: 4 }}>
                          Approve
                        </button>
                        <button onClick={() => handleReject(e.id)}>Reject</button>
                      </>
                    )}
                  </td>
                </tr>
              ))}
              {filteredExpenses.length === 0 && (
                <tr>
                  <td colSpan={6} style={{ ...td, textAlign: 'center', color: '#888' }}>
                    No expenses found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </>
      )}
    </div>
  );
}

const th: React.CSSProperties = {
  borderBottom: '2px solid #ddd', textAlign: 'left', padding: '8px 12px', background: '#f5f5f5',
};
const td: React.CSSProperties = {
  borderBottom: '1px solid #eee', padding: '8px 12px',
};
