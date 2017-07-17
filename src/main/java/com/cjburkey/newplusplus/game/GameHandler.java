package com.cjburkey.newplusplus.game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import java.util.ArrayList;
import java.util.List;
import com.cjburkey.newplusplus.GLFWWindow;

public class GameHandler {
	
	private final GameLoop loop = new GameLoop();
	private final List<IGameLogic> logic = new ArrayList<>();
	
	public void startGameLoop(GLFWWindow window) throws Exception {
		loop.start(window);
	}
	
	public void stop() {
		loop.running = false;
	}
	
	public void add(IGameLogic logic) {
		this.logic.add(logic);
	}
	
	private void init(GLFWWindow window) throws Exception {
		for(IGameLogic l : logic) {
			l.init(window);
		}
	}
	
	private void tick(GLFWWindow window, double delta) throws Exception {
		for(IGameLogic l : logic) {
			l.tick(window, delta);
		}
	}
	
	private void render(GLFWWindow window) throws Exception {
		glfwPollEvents();
		glClear(GL_COLOR_BUFFER_BIT);
		for(IGameLogic l : logic) {
			l.render(window);
		}
		glfwSwapBuffers(window.getId());
	}
	
	private void cleanup(GLFWWindow window) throws Exception {
		for(IGameLogic l : logic) {
			l.cleanup(window);
		}
		window.cleanup();
	}
	
	private class GameLoop {
		
		private long lastLoopTime = System.nanoTime();
		private boolean running = false;
		private final int TARGET = 60;
		private final long OPTIMAL_TIME = 1000000000 / TARGET;
		
		public void start(GLFWWindow window) throws Exception {
			running = true;
			init(window);
			while(!window.shouldClose() && running) {
				long now = System.nanoTime();
				long updateLength = now - lastLoopTime;
				lastLoopTime = now;
				double delta = updateLength / ((double) OPTIMAL_TIME);
				tick(window, delta);
				render(window);
				long timeout = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000000;
				if(timeout > 0) {
					Thread.sleep(timeout);
				}
			}
			cleanup(window);
		}
		
	}
	
}