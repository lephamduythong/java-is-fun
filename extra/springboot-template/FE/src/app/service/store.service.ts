import { Injectable, signal } from '@angular/core';

@Injectable({providedIn: 'root'})
export class StoreService {

    private tasks = signal([
        { id: 1, title: 'Task 1', description: 'Description of Task 1' },
        { id: 2, title: 'Task 2', description: 'Description of Task 2' },
        { id: 3, title: 'Task 3', description: 'Description of Task 3' }
    ]);

    constructor() { }

    // Read
    getTasks() {
        return this.tasks();
    }

    // Create
    addTask(task: { title: string, description: string }) {
        const newTask = { id: Date.now(), ...task };
        this.tasks.update(tasks => [...tasks, newTask]);
    }

    // Update
    updateTask(id: number, updatedTask: { title?: string, description?: string }) {
        this.tasks.update(tasks => tasks.map(task => task.id === id ? { ...task, ...updatedTask } : task));
    }   

    // Delete
    deleteTask(id: number) {
        this.tasks.update(tasks => tasks.filter(task => task.id !== id));
    }
}