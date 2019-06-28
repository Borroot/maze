package com.borroot;

import com.borroot.controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage window;

    public static void main(String[] args) {
    	Application.launch();
    }

	@Override
	public void start(Stage stage) throws Exception {
    	this.window = stage;
		new GameController();
	}

	/**
	 * @return the main stage/window.
	 */
	public static Stage getWindow(){
    	return window;
	}
}
