package br.com.ada.t1322.tecnicasprogramacao.projeto.view.command;

import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskController;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.View;

public class DeleteTaskCommand implements Command {

    private final View view;
    private final TaskController taskController;

    public DeleteTaskCommand(View view, TaskController taskController) {
        this.view = view;
        this.taskController = taskController;
    }

    @Override
    public void execute() {
        Long id;
        try {
            id = view.getIntInput("🗑️ Informe o ID da tarefa para excluir").longValue();
        } catch (NumberFormatException e) {
            view.showMessage("❌ ID inválido. Por favor, insira um número.");
            return;
        }

        try {
            boolean deleted = taskController.deleteTask(id);
            if (deleted) {
                view.showMessage("✅ Tarefa #" + id + " excluída com sucesso!");
            } else {
                view.showMessage("⚠️ Tarefa #" + id + " não encontrada ou não pôde ser excluída.");
            }
        } catch (IllegalArgumentException e) {
            view.showMessage("❌ Erro: " + e.getMessage());
        } catch (Exception e) {
            view.showMessage("❌ Ocorreu um erro inesperado ao tentar excluir a tarefa: " + e.getMessage());
            e.printStackTrace();
        }
    }
}