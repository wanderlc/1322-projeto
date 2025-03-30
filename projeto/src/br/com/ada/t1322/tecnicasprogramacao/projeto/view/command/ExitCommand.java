package br.com.ada.t1322.tecnicasprogramacao.projeto.view.command;

import br.com.ada.t1322.tecnicasprogramacao.projeto.view.ConsoleApp;
import br.com.ada.t1322.tecnicasprogramacao.projeto.view.View;

public class ExitCommand implements Command {

    private final View view;
    private final ConsoleApp app;


    public ExitCommand(View view, ConsoleApp app) {
        this.view = view;
        this.app = app;
    }

    @Override
    public void execute() {
        view.showMessage("Saindo... 👋");
        app.stopRunning();

     try {
            view.close();
        } catch (Exception e) {
            System.err.println("Erro ao fechar a view: " + e.getMessage());
        }
    }
}