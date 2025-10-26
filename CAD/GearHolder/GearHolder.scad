$fa=.1;
$fs=.1;

difference() {
  cube([10,40,42]);
  translate([5,5,-1]) cylinder(44,2,2);
  translate([5,35,-1]) cylinder(44,2,2);
  translate([-1,2,3]) cube([6,6,36]);
  translate([-1,32,3]) cube([6,6,36]);
  translate([5,5,3]) cylinder(36,3,3);
  translate([5,35,3]) cylinder(36,3,3);
}