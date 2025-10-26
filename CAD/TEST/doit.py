import FreeCAD
import Arch
import Draft
import Mesh
import Part
import sys

stl_file=sys.argv[2]
step_file=sys.argv[3]

print("STL  FILE: " + stl_file)
print("STEP FILE: " + step_file)

mesh = Mesh.Mesh()
mesh.read(stl_file)
shape = Part.Shape();
shape.makeShapeFromMesh(mesh.Topology, 0.1)
shape.removeSplitter()
solid = Part.Solid(shape)
Part.export([solid], step_file)

print("DONE.")