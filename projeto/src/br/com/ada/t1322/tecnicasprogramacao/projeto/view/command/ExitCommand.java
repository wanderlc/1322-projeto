package br.com.ada.t1322.tecnicasprogramacao.projeto.view.command;

import br.com.ada.t1322.tecnicasprogramacao.projeto.view.View;

public class ExitCommand implements Command {

    private final View view;

    public ExitCommand(View view /*, TaskService taskService */) {
        this.view = view;
    }

    @Override
    public void execute() {
        view.showMessage("Saindo... 👋");

        try {
            view.close();
        } catch (Exception e) {
            System.err.println("Erro ao fechar a view: " + e.getMessage());
        }
        System.exit(0);
    }
}