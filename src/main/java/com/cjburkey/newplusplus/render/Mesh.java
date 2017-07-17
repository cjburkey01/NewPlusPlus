package com.cjburkey.newplusplus.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

public class Mesh {
	
	private boolean built = false;
	private int triCount;
	private float[] verts;
	private int[] tris;
	private float[] uv;
	
	private int vaoId;
	private int uvId;
	private int triId;
	
	public Mesh(float[] verts, int[] tris, float[] uv) {
		triCount = tris.length;
		this.verts = verts;
		this.tris = tris;
		this.uv = uv;
	}
	
	public void build() {
		if(!built) {
			built = true;
			vaoId = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vaoId);
			glBufferData(GL_ARRAY_BUFFER, arrayFloatToBuffer(verts), GL_STATIC_DRAW);
			
			uvId = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, uvId);
			glBufferData(GL_ARRAY_BUFFER, arrayFloatToBuffer(uv), GL_STATIC_DRAW);
			
			triId = glGenBuffers();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, triId);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, arrayIntToBuffer(tris), GL_STATIC_DRAW);
			
			glBindBuffer(GL_ARRAY_BUFFER, 0);
		}
	}
	
	public void render() {
		bind();
		glDrawElements(GL_TRIANGLES, triCount, GL_UNSIGNED_INT, 0);
		unbind();
	}
	
	private void bind() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER, vaoId);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, uvId);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, triId);
	}
	
	private void unbind() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	private FloatBuffer arrayFloatToBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private IntBuffer arrayIntToBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
}