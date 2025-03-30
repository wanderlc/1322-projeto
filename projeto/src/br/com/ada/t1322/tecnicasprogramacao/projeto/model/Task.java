package br.com.ada.t1322.tecnicasprogramacao.projeto.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {

    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private Status status;

    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Task(Long id, String title, String description, LocalDate deadline, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }

    public enum Status {
        PENDENTE("Pendente"),
        EM_ANDAMENTO("Em andamento"),
        BLOQUEADO("Bloqueado"),
        CONCLUIDO("Concluído");

        private final String descricao;

        Status(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        public static Status fromString(String status) {
            if (status == null || status.isBlank()) {
                throw new IllegalArgumentException("Status não pode ser vazio ou nulo.");
            }
            for (Status s : values()) {
                if (s.name().equalsIgnoreCase(status) || s.descricao.equalsIgnoreCase(status)) {
                    return s;
                }
            }
            throw new IllegalArgumentException("Status inválido: " + status);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        String formattedDeadline = (deadline != null) ? deadline.format(DEFAULT_DATE_FORMATTER) : "N/A";
        String currentStatusDesc = (status != null) ? status.getDescricao() : "N/A";
        String currentDescription = (description != null && !description.isBlank()) ? description : "Nenhuma";

        return String.format(
                "----------------------------------------\n" +
                        "📌 Tarefa #%d\n" +
                        "   Título: %s\n" +
                        "   Descrição: %s\n" +
                        "   📅 Prazo: %s\n" +
                        "   📊 Status: %s\n" +
                        "----------------------------------------",
                id, title, currentDescription, formattedDeadline, currentStatusDesc
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        if (id != null && task.id != null) {
            return Objects.equals(id, task.id);
        }
        return Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(deadline, task.deadline) &&
                status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id != null ? id : title,
                id != null ? id : description,
                id != null ? id : deadline,
                id != null ? id : status);
    }
}