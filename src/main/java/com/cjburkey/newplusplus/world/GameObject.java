package com.cjburkey.newplusplus.world;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import com.cjburkey.newplusplus.render.Mesh;

public class GameObject {
	
	private static final Matrix4f objectMatrix = new Matrix4f().identity();
	
	private Mesh mesh;
	private final Vector3f position = new Vector3f();
	private final Vector3f rotation = new Vector3f();
	private float scale;
	
	public GameObject(Vector3f pos) {
		setPosition(pos);
	}
	
	public GameObject(Vector3f pos, Mesh mesh) {
		setPosition(pos);
		setMesh(mesh);
	}
	
	public GameObject(Vector3f pos, Vector3f rotation, Mesh mesh) {
		setPosition(pos);
		setRotation(pos);
		setMesh(mesh);
	}
	
	public GameObject(Vector3f pos, Vector3f rotation, float scale, Mesh mesh) {
		setPosition(pos);
		setRotation(pos);
		setScale(scale);
		setMesh(mesh);
	}
	
	public void render() {
		if(mesh != null) {
			mesh.render();
		}
	}
	
	public void setMesh(Mesh mesh) {
		mesh.build();
		this.mesh = mesh;
	}
	
	public void setPosition(Vector3f pos) {
		position.set(pos);
	}
	
	public void setRotation(Vector3f rot) {
		rotation.set(rot);
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Vector3f getPosition() {
		return new Vector3f(position);
	}
	
	public Vector3f getRotation() {
		return new Vector3f(rotation);
	}
	
	public float getScale() {
		return scale;
	}
	
	public Matrix4f getObjectMatrix() {
		return objectMatrix.identity().translate(position).rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z).scale(scale);
	}
	
}