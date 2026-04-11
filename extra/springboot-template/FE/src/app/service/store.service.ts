import { inject, Injectable, signal } from '@angular/core';
import { LogService } from './log.service';

@Injectable({providedIn: 'root'})
export class StoreService {

    logService = inject(LogService);

    private tasks = signal([
        { id: 1, title: 'Task 1', description: 'Description of Task 1' },
        { id: 2, title: 'Task 2', description: 'Description of Task 2' },
        { id: 3, title: 'Task 3', description: 'Description of Task 3' }
    ]);

    isLoading = signal(false);
    error = signal("");
    action = signal("");
    posts: any = signal([]);

    constructor() { }

    // Tasks
    // Read
    getTasks() {
        return this.tasks();
    }

    // Create
    addTask(task: { title: string, description: string }) {
        const newTask = { id: Date.now(), ...task };
        this.tasks.update(tasks => [...tasks, newTask]);
        this.logService.log(`Added task: ${newTask.title}`);
    }

    // Update
    updateTask(id: number, updatedTask: { title?: string, description?: string }) {
        this.tasks.update(tasks => tasks.map(task => task.id === id ? { ...task, ...updatedTask } : task));
        this.logService.log(`Updated task with id: ${id}`);
    }   

    // Delete
    deleteTask(id: number) {
        this.tasks.update(tasks => tasks.filter(task => task.id !== id));
        this.logService.log(`Deleted task with id: ${id}`);
    }
}