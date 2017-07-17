package com.cjburkey.newplusplus.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Texture {
	
	private int id;
	private String filename;
	
	public Texture(String filename) {
		this.filename = filename;
	}
	
	public void load() throws IOException {
		InputStream i = Texture.class.getResourceAsStream(filename);
		if (i == null) {
			throw new IOException("File not found: " + filename);
		}
		
		PNGDecoder decoder = new PNGDecoder(i);
		ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
		decoder.decode(buffer, decoder.getWidth() * 4, Format.RGBA);
		buffer.flip();
		initInOpenGL(decoder.getWidth(), decoder.getHeight(), buffer);
	}
	
	private void initInOpenGL(int width, int height, ByteBuffer colors) {
		id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, colors);
	}
	
	public void bind(int sampler) throws Exception {
		if (sampler < 0 || sampler >= 32) {
			throw new Exception("Sampler out of range: " + sampler);
		}
		glActiveTexture(GL_TEXTURE0 + sampler);
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
}