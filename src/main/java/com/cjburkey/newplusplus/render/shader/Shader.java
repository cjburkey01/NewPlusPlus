package com.cjburkey.newplusplus.render.shader;

import static org.lwjgl.opengl.GL20.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class Shader {
	
	private int programId;
	private int vertexId;
	private int fragmentId;
	private String filenameBase;
	
	public Shader(String filenameBase) {
		this.filenameBase = filenameBase;
	}
	
	public void load() throws Exception {
		programId = glCreateProgram();
		
		vertexId = createShader(GL_VERTEX_SHADER, filenameBase + ".vs");
		fragmentId = createShader(GL_FRAGMENT_SHADER, filenameBase + ".fs");

		glAttachShader(programId, vertexId);
		glAttachShader(programId, fragmentId);
		
		glBindAttribLocation(programId, 0, "position");
		glBindAttribLocation(programId, 1, "uv");
		
		glLinkProgram(programId);
		if (glGetProgrami(programId, GL_LINK_STATUS) != 1) {
			throw new Exception("Program could not be linked: " + glGetProgramInfoLog(programId));
		}
		
		glValidateProgram(programId);
		if (glGetProgrami(programId, GL_VALIDATE_STATUS) != 1) {
			System.err.println("Shader program warning: " + glGetProgramInfoLog(programId));
		}
	}
	
	public void bind() {
		glUseProgram(programId);
	}
	
	public void setUniform(String key, int value) throws Exception {
		glUniform1i(getUniformLocation(key), value);
	}
	
	public void setUniform(String key, Matrix4f value) throws Exception {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);
		glUniformMatrix4fv(getUniformLocation(key), false, buffer);
	}
	
	private int getUniformLocation(String key) throws Exception {
		int at = glGetUniformLocation(programId, key);
		if(at < 0) {
			throw new Exception("Uniform " + key + " could not be found");
		}
		return at;
	}
	
	private int createShader(int type, String name) throws Exception {
		int id = glCreateShader(type);
		String source = readFile(name);
		
		glShaderSource(id, source);
		glCompileShader(id);
		if (glGetShaderi(id, GL_COMPILE_STATUS) != 1) {
			throw new Exception("Shader could not be compiled: " + glGetShaderInfoLog(id));
		}
		
		return id;
	}
	
	private String readFile(String file) throws IOException {
		StringBuilder builder = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(Shader.class.getResourceAsStream(file)));
			String line;
			while((line = br.readLine()) != null) {
				builder.append(line);
				builder.append('\n');
			}
		} catch(Exception e) {
			throw e;
		} finally {
			if(br != null) {
				br.close();
			}
		}
		return builder.toString();
	}
	
}