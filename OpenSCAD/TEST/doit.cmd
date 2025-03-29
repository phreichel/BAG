SET OPENSCAD="C:\Apps\OpenSCAD\openscad.exe"
SET FREECAD="C:\Apps\FreeCAD 1.0\bin\freecadcmd.exe"
SET SLICER="C:\Apps\AnycubicSlicerNext\AnycubicSlicerNext.exe"

REM %OPENSCAD% -o %1.stl %1.scad
%FREECAD% doit.py %1.stl %1.step
%SLICER% -s %1.stl %1.gcode
