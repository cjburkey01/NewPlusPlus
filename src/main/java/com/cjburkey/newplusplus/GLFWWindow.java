package com.cjburkey.newplusplus;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;

public class GLFWWindow {
	
	private long window;
	private int width;
	private int height;
	
	public GLFWWindow(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void create() throws Exception {
		if (!glfwInit()) {
			throw new Exception("GLFW could not be initialized");
		}
		
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		
		// Not needed yet? (Mac only, anyway)
		//glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		//glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		//glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		//glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		
		window = glfwCreateWindow(width, height, "New PlusPlus", 0, 0);
		
		glfwSetWindowSizeCallback(window, (win, w, h) -> {
			width = w;
			height = h;
		});
		
		glfwMakeContextCurrent(window);
		createCapabilities();
	}
	
	public void show() {
		glfwShowWindow(window);
	}
	
	public void cleanup() {
		glfwSetWindowShouldClose(window, true);
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public long getId() {
		return window;
	}
	
}