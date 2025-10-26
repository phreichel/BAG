SET OPENSCAD=c:\Apps\OpenSCAD\openscad.exe
SET FREECAD=c:\Apps\FreeCAD 1.0\bin\freecadcmd.exe
set SLICER="C:\Apps\AnycubicSlicerNext\AnycubicSlicerNext.exe"

%OPENSCAD% -o %1.stl %1
%FREECAD% doit.py %1.stl %1.stp
%SLICER% -s %1.stl %1.gcode