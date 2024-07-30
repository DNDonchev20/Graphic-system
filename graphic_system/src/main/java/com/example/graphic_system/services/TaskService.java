package com.example.graphic_system.services;

import com.example.graphic_system.models.Task;
import com.example.graphic_system.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        if (task.getPriority() == null) {
            task.setPriority(0);
        }
        taskRepository.save(task);
        return task;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
}
