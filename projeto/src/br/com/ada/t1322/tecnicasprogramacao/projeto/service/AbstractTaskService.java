package br.com.ada.t1322.tecnicasprogramacao.projeto.service;

import br.com.ada.t1322.tecnicasprogramacao.projeto.dto.TaskUpdateRequest;
import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.repository.TaskRepository;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.validation.TaskValidator; // Importar

import java.util.Optional;

public abstract class AbstractTaskService implements TaskService {

    protected final TaskRepository taskRepository;
    protected final TaskValidator validator;

    public AbstractTaskService(TaskRepository taskRepository, TaskValidator validator) {
        if (taskRepository == null) {
            throw new IllegalArgumentException("TaskRepository não pode ser nulo.");
        }
        if (validator == null) {
            throw new IllegalArgumentException("TaskValidator não pode ser nulo.");
        }
        this.taskRepository = taskRepository;
        this.validator = validator;
    }

    @Override
    public Task save(Task task) {
        validate(task);
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    protected void validate(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Tarefa não pode ser nula.");
        }
        this.validator.validate(task);
    }

    @Override
    public Task updateStatus(Long id, Task.Status newStatus) {
        Task existingTask = getById(id);
        applyStatusUpdate(existingTask, newStatus);
        return save(existingTask);
    }

    protected void applyStatusUpdate(Task task, Task.Status newStatus) {
        task.setStatus(newStatus);
    }

    @Override
    public Task updateTask(TaskUpdateRequest updateRequest) {
        Task existingTask = getById(updateRequest.getId());

        if (updateRequest.getTitle() != null) {
            existingTask.setTitle(updateRequest.getTitle());
        }
        if (updateRequest.getDescription() != null) {
            existingTask.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getDeadline() != null) {
            existingTask.setDeadline(updateRequest.getDeadline());
        }
        if (updateRequest.getStatus() != null) {
            applyStatusUpdate(existingTask, updateRequest.getStatus());
        }
        return save(existingTask);
    }

    @Override
    public boolean deleteById(Long id) {
        return taskRepository.deleteById(id);
    }

    @Override
    public void clearAll() {
        taskRepository.deleteAll();
    }

    @Override
    public void notifyUpcomingDeadlines(int daysBefore) {
    }


    @Override
    public abstract void startNotifier();

    @Override
    public abstract void stopNotifier();
}