//**************************************************************************************************
package joda.scene.model;
//**************************************************************************************************

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import de.javagl.obj.FloatTuple;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjFace;
import de.javagl.obj.ObjGroup;
import de.javagl.obj.ObjReader;


//**************************************************************************************************
public class OBJModel {

	//==============================================================================================
	private Obj object = null;
	private float[] vertices = null;
	//==============================================================================================
	
	//==============================================================================================
	public void loadModel(File objFile) throws IOException {

		final int VERTEX_SIZE = 8;
		
		FileInputStream is = new FileInputStream(objFile);
		object = ObjReader.read(is);
		
		int netVerts = 0;
		int numGroups = object.getNumGroups();
		for (int g=0; g<numGroups; g++) {
			ObjGroup group = object.getGroup(g);
			int numFaces = group.getNumFaces();
			for (int f=0; f<numFaces; f++) {
				ObjFace face = group.getFace(f);
				int nVertices = face.getNumVertices();
				netVerts += nVertices;
			}
		}

		int counter = 0;
		vertices = new float[netVerts * VERTEX_SIZE];
		for (int g=0; g<numGroups; g++) {

			ObjGroup group = object.getGroup(g);
			int numFaces = group.getNumFaces();
			for (int f=0; f<numFaces; f++) {

				ObjFace face = group.getFace(f);
				int nVertices = face.getNumVertices();
				for (int vx=0; vx<nVertices; vx++) {
					
					int vIdx = face.getVertexIndex(f);
					FloatTuple v = object.getVertex(vIdx);
					vertices[counter++] = v.getX();
					vertices[counter++] = v.getY();
					vertices[counter++] = v.getZ();

					int nIdx = face.getNormalIndex(f);
					FloatTuple n = object.getNormal(nIdx);
					vertices[counter++] = n.getX();
					vertices[counter++] = n.getY();
					vertices[counter++] = n.getZ();
					
					int tIdx = face.getTexCoordIndex(f);
					FloatTuple t = object.getTexCoord(tIdx);
					vertices[counter++] = t.getX();
					vertices[counter++] = t.getY();
					
				}
			}
		}
		
	}
	//==============================================================================================

}
//**************************************************************************************************
