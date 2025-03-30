package br.com.ada.t1322.tecnicasprogramacao.projeto.view.command;

import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskController;
import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.View;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.StatusViewHelper;

public class UpdateTaskCommand implements Command {

    private final View view;
    private final TaskController taskController;

    public UpdateTaskCommand(View view, TaskController taskController) {
        this.view = view;
        this.taskController = taskController;
    }

    @Override
    public void execute() {
        Long id;
        try {
            id = view.getIntInput("📝 Informe o ID da tarefa para atualizar").longValue();
        } catch (NumberFormatException e) {
            view.showMessage("❌ ID inválido. Por favor, insira um número.");
            return;
        }

        String title = view.getInput("   Novo título (ou pressione Enter para manter)");
        String description = view.getInput("   Nova descrição (ou pressione Enter para manter)");
        String deadline = view.getInput("   📅 Nova data limite (DD/MM/YYYY) (ou pressione Enter para manter)");
        String statusStr = view.getInput("   📊 Novo status (" + StatusViewHelper.getTaskAvailableStatus() + ") (ou pressione Enter para manter)");

        try {
            Task.Status status = null;
            if (!statusStr.isBlank()) {
                status = Task.Status.fromString(statusStr);
            }

            Task updatedTask = taskController.updateTask(
                    id,
                    title.isBlank() ? null : title,
                    description.isBlank() ? null : description,
                    deadline.isBlank() ? null : deadline,
                    status
            );
            view.showMessage("✅ Tarefa atualizada com sucesso!");
            view.showMessage(updatedTask.toString());
        } catch (IllegalArgumentException e) {
            view.showMessage("❌ Erro ao atualizar: " + e.getMessage());
        } catch (Exception e) {
            view.showMessage("❌ Ocorreu um erro inesperado ao atualizar a tarefa: " + e.getMessage());
            e.printStackTrace();
        }
    }
}