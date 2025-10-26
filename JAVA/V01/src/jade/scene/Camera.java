//**************************************************************************************************
package jade.scene;
//**************************************************************************************************

import javax.vecmath.Matrix4f;

import jade.render.Renderer;
import lombok.Getter;
import lombok.Setter;

//**************************************************************************************************
public class Camera extends Node {

	//==============================================================================================
	@Getter @Setter private float fovy = 65f;
	@Getter @Setter private float near = .65f;
	@Getter @Setter private float far  = 1000f;
	//==============================================================================================

	//==============================================================================================
	public void set(float fovy, float near, float far) {
		setFovy(fovy);
		setNear(near);
		setFar(far);
	}
	//==============================================================================================

	//==============================================================================================
	public void renderActiveCamera(Renderer renderer) {
		renderer.cameraProjection(fovy, near, far);
		Matrix4f buffer =  new Matrix4f();
		renderer.cameraTransform(getTransformMatrix(this, buffer));
	}
	//==============================================================================================

	//==============================================================================================
	private Matrix4f getTransformMatrix(Node node, Matrix4f buffer) {
		Node parent = node.getParent();
		if (parent == null) {
			buffer.setIdentity();
		} else {
			getTransformMatrix(parent, buffer);
			if (node instanceof Transform) {
				Transform tx = (Transform) node;
				buffer.mul(tx.getRelative());
			}
		}
		return buffer;
	}
	//==============================================================================================
	
}
//**************************************************************************************************
