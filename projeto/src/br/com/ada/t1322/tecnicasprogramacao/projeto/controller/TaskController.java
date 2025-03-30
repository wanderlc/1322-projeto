package br.com.ada.t1322.tecnicasprogramacao.projeto.controller;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface TaskController {

    Task createTask(String title, String description, String deadline, Task.Status status);

    Task updateTask(Long id, String title, String description, String deadline, Task.Status status);

    Task updateTaskStatus(Long id, Task.Status newStatus);

    List<Task> getAllTasks(Optional<Comparator<Task>> orderBy);

    List<Task> getTasksByStatus(Task.Status status, Optional<Comparator<Task>> orderBy);

    List<Task> getTasksBy(Predicate<Task> predicate, Optional<Comparator<Task>> orderBy);

    boolean deleteTask(Long id);
}