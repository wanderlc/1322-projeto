package br.com.ada.t1322.tecnicasprogramacao.projeto.view;

import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskController;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.command.*;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class ConsoleApp {

    private final View view;
    private final Map<Integer, Command> commands = new HashMap<>();
    private boolean running = true;

    public ConsoleApp(View view, TaskController taskController) {
        this.view = view;

        // Mapeamento dos comandos atualizado
        commands.put(1, new CreateTaskCommand(view, taskController));
        commands.put(2, new UpdateTaskCommand(view, taskController));
        commands.put(3, new UpdateTaskStatusCommand(view, taskController));
        commands.put(4, new ListTasksCommand(view, taskController));
        commands.put(5, new FilterTasksByStatusCommand(view, taskController));
        commands.put(6, new FilterTasksByCustomPredicateCommand(view, taskController));
        commands.put(7, new DeleteTaskCommand(view, taskController));
        commands.put(8, new ShowNotificationsCommand(view));
        commands.put(9, new ExitCommand(view, this));
    }

    public void run() {
        while (running) {
            showMenu();
            try {
                int option = view.getIntInput("Escolha uma opção");
                Command command = commands.get(option);
                if (command != null) {
                    command.execute();
                } else {
                    view.showMessage("❌ Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                view.showMessage("❌ Entrada inválida. Por favor, digite um número.");
            } catch (Exception e) {
                view.showMessage("❌ Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Aplicação encerrada.");
    }

    // Método para permitir que ExitCommand pare o loop
    public void stopRunning() {
        this.running = false;
    }

    private void showMenu() {
        view.showMessage("\n╔══════════════════════════════════════╗");
        view.showMessage("║   🧠 Gerenciador de Tarefas Inteligente 🧠   ║");
        view.showMessage("╠══════════════════════════════════════╣");
        view.showMessage("║ 1 - Criar Tarefa                     ║");
        view.showMessage("║ 2 - Atualizar Tarefa                 ║");
        view.showMessage("║ 3 - Atualizar Status da Tarefa       ║");
        view.showMessage("║ 4 - Listar Todas as Tarefas          ║");
        view.showMessage("║ 5 - Filtrar Tarefas por Status       ║");
        view.showMessage("║ 6 - Buscar Tarefas por Palavra-chave ║");
        view.showMessage("║ 7 - Excluir Tarefa                   ║");
        view.showMessage("║ 8 - Ver Notificações                 ║");
        view.showMessage("║ 9 - Sair                             ║");
        view.showMessage("╚══════════════════════════════════════╝");
    }
}