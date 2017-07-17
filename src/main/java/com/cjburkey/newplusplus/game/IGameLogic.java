package com.cjburkey.newplusplus.game;

import com.cjburkey.newplusplus.GLFWWindow;

public interface IGameLogic {
	
	void init(GLFWWindow window) throws Exception;
	void tick(GLFWWindow window, double delta) throws Exception;
	void render(GLFWWindow window) throws Exception;
	void cleanup(GLFWWindow window) throws Exception;
	
}