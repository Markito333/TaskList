import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import TaskComponent from './TaskComponent';

describe('TaskComponent', () => {
  const task = {
    id: 1,
    description: 'Test task',
    completed: false,
    createdAt: '2023-01-01T00:00:00'
  };

  it('renders task description', () => {
    render(<TaskComponent task={task} />);
    expect(screen.getByText('Test task')).toBeInTheDocument();
  });

  it('toggles completion status', () => {
    const mockToggle = jest.fn();
    render(<TaskComponent task={task} onToggle={mockToggle} />);
    
    fireEvent.click(screen.getByRole('checkbox'));
    expect(mockToggle).toHaveBeenCalledWith(1);
  });

  it('shows edit form when edit button clicked', () => {
    render(<TaskComponent task={task} />);
    fireEvent.click(screen.getByLabelText('Edit'));
    expect(screen.getByDisplayValue('Test task')).toBeInTheDocument();
  });
});