package com.cjburkey.newplusplus;

import static org.lwjgl.opengl.GL11.*;
import com.cjburkey.newplusplus.game.GameHandler;
import com.cjburkey.newplusplus.game.core.GameCore;

public class NPP {
	
	public static NPP instance;
	
	private boolean running = false;
	private final GameHandler game = new GameHandler();
	
	public void run() throws Exception {
		if (running) {
			return;
		}
		running = true;
		
		System.out.println("Starting game..");
		
		GLFWWindow window = new GLFWWindow(640, 400);
		window.create();
		window.show();
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		game.add(new GameCore());
		
		System.out.println("Game started.");
		game.startGameLoop(window);
		System.out.println("Game ended.");
	}
	
	public void stop() {
		running = false;
		game.stop();
	}
	
	public static void main(String[] args) {
		instance = new NPP();
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> err(e));
		try {
			instance.run();
		} catch(Exception e) {
			err(e);
		}
	}
	
	private static void err(Throwable t) {
		System.err.println("A fatal error occurred: " + t.getMessage());
		t.printStackTrace();
		System.exit(-1);
	}
	
}