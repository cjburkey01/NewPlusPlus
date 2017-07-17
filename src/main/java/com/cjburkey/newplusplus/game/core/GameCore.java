package com.cjburkey.newplusplus.game.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import com.cjburkey.newplusplus.GLFWWindow;
import com.cjburkey.newplusplus.NPP;
import com.cjburkey.newplusplus.game.IGameLogic;
import com.cjburkey.newplusplus.math.Transformation;
import com.cjburkey.newplusplus.render.Mesh;
import com.cjburkey.newplusplus.render.Texture;
import com.cjburkey.newplusplus.render.shader.Shader;
import com.cjburkey.newplusplus.world.GameObject;
import com.cjburkey.newplusplus.world.WorldObject;

public class GameCore implements IGameLogic {
	
	Texture texture;
	GameObject obj;
	Shader shader;
	
	public void init(GLFWWindow window) throws Exception {
		float[] verts = {
				-(float) WorldObject.levelSize / 2f, (float) WorldObject.levelSize / 2f, 0.0f,		// V0
				-(float) WorldObject.levelSize / 2f, -(float) WorldObject.levelSize / 2f, 0.0f,		// V1
				(float) WorldObject.levelSize / 2f, -(float) WorldObject.levelSize / 2f, 0.0f,		// V2
				(float) WorldObject.levelSize / 2f, (float) WorldObject.levelSize / 2f, 0.0f,		// V3
		};
		
		int[] tris = {
				0, 1, 3,
				3, 1, 2
		};
		
		float[] uv = {
				0.0f, 0.0f,
				0.0f, 1.0f,
				1.0f, 1.0f,
				1.0f, 0.0f,
		};
		
		glEnable(GL_TEXTURE_2D);
		texture = new Texture("/assets/npp/texture/test.png");
		texture.load();
		
		obj = new GameObject(new Vector3f(), new Mesh(verts, tris, uv));
		
		shader = new Shader("/assets/npp/shader/textured/textured");
		shader.load();
	}
	
	public void tick(GLFWWindow window, double delta) throws Exception {
		
	}
	
	public void render(GLFWWindow window) throws Exception {
		if (glfwGetKey(window.getId(), GLFW_KEY_ESCAPE) == GLFW_PRESS) {
			NPP.instance.stop();
		}
		
		Matrix4f projection = Transformation.getProjection(window.getWidth(), window.getHeight());
		
		int width = window.getWidth();
		int height = window.getHeight();
		
		if(width > height) {
			obj.setScale((float) height / (float) WorldObject.levelSize);
		} else {
			obj.setScale((float) width / (float) WorldObject.levelSize);
		}
		
		texture.bind(0);
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projectionMatrix", projection);
		shader.setUniform("objectMatrix", obj.getObjectMatrix());
		obj.render();
	}
	
	public void cleanup(GLFWWindow window) throws Exception {
		
	}
	
}