package com.cjburkey.newplusplus.math;

import org.joml.Matrix4f;

public class Transformation {
	
	private static final Matrix4f projection = new Matrix4f().identity();
	private static final Matrix4f projectionScaled = new Matrix4f().identity();
	
	public static Matrix4f getProjection(int width, int height) {
		return projection.identity().ortho2D(-width / 2, width / 2, -height / 2, height / 2);
	}
	
	public static Matrix4f getProjectionScaled(Matrix4f projection, float scale) {
		projection.scale(scale, projectionScaled.identity());
		return projectionScaled;
	}
	
}