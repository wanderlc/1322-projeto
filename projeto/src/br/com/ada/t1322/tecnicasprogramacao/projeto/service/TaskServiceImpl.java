package br.com.ada.t1322.tecnicasprogramacao.projeto.service;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.repository.TaskRepository;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.notification.Notifier;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.validation.TaskValidator;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class TaskServiceImpl extends AbstractTaskService {

    public static final Comparator<Task> DEFAULT_TASK_SORT = Comparator.comparing(Task::getDeadline);


    private final Notifier notifier;

    public TaskServiceImpl(TaskRepository taskRepository, TaskValidator validator, Notifier notifier) {
        super(taskRepository, validator);
        if (notifier == null) {
            throw new IllegalArgumentException("Notifier não pode ser nulo.");
        }
        this.notifier = notifier;
    }

    @Override
    public List<Task> findAll(Optional<Comparator<Task>> orderBy) {
        return taskRepository.findAll().stream().sorted(orderBy.orElse(DEFAULT_TASK_SORT)).toList();
    }

    @Override
    public List<Task> findByStatus(Task.Status status, Optional<Comparator<Task>> orderBy) {
        return taskRepository.findByStatus(status).stream().sorted(orderBy.orElse(DEFAULT_TASK_SORT)).toList();
    }

    @Override
    public List<Task> findBy(Predicate<Task> predicate, Optional<Comparator<Task>> orderBy) {
        var stream = taskRepository.findBy(predicate).stream();

        if (orderBy.isPresent()) {
            stream = stream.sorted(orderBy.get());
        }

        return stream.toList();
    }

    @Override
    public void startNotifier() {
        this.notifier.start();
    }

    @Override
    public void stopNotifier() {
        this.notifier.stop();
    }
}