import FreeCAD, Mesh, Part
import sys
stl_file = sys.argv[1]
step_file = sys.argv[2]
mesh = Mesh.Mesh()
mesh.read(stl_file)
shape = Part.makeShapeFromMesh(mesh.Topology, 0.1)
solid = Part.makeSolid(shape)
Part.export([solid], step_file)
