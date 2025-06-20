import { rest } from 'msw';
import { setupServer } from 'msw/node';
import { getAllTasks, createTask, updateTask, deleteTask, toggleTask } from './taskHandlers';

const server = setupServer(
  rest.get('/api/tasks', (req, res, ctx) => {
    return res(
      ctx.json([
        { id: 1, description: 'Test task', completed: false }
      ])
    );
  }),
  rest.post('/api/tasks', (req, res, ctx) => {
    return res(
      ctx.json({ id: 2, description: req.body, completed: false })
    );
  })
);

beforeAll(() => server.listen());
afterEach(() => server.resetHandlers());
afterAll(() => server.close());

describe('Task API Handlers', () => {
  it('fetches all tasks', async () => {
    const tasks = await getAllTasks();
    expect(tasks).toHaveLength(1);
    expect(tasks[0].description).toBe('Test task');
  });

  it('creates a new task', async () => {
    const newTask = await createTask('New task');
    expect(newTask.id).toBe(2);
    expect(newTask.description).toBe('New task');
  });

  it('handles server error on fetch', async () => {
    server.use(
      rest.get('/api/tasks', (req, res, ctx) => {
        return res(ctx.status(500));
      })
    );
    
    await expect(getAllTasks()).rejects.toThrow('Failed to fetch tasks');
  });
});