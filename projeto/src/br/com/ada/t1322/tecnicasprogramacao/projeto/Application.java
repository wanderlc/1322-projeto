package br.com.ada.t1322.tecnicasprogramacao.projeto;

import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskController;
import br.com.ada.t1322.tecnicasprogramacao.projeto.controller.TaskControllerImpl;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.TaskService;
import br.com.ada.t1322.tecnicasprogramacao.projeto.service.TaskServiceFactory;
import br.com.ada.t1322.tecnicasprogramacao.projeto.util.SampleDataInitializer;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.ConsoleApp;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.ConsoleView;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.View;

public class Application {
    public static void main(String[] args) {
        TaskService taskService = null;
        try (View view = new ConsoleView()) {
            taskService = TaskServiceFactory.createTaskService();

            taskService.startNotifier();

            final TaskService finalTaskService = taskService;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nParando o notificador...");
                if (finalTaskService != null) {
                    finalTaskService.stopNotifier();
                }
                System.out.println("Notificador parado.");
            }));

            TaskController controller = new TaskControllerImpl(taskService);

            SampleDataInitializer.initializeSampleTasks(controller);

            ConsoleApp app = new ConsoleApp(view, controller);
            app.run();

        } catch (Exception e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // if (taskService != null) {
            //     taskService.stopNotifier();
            // }
        }
    }
}