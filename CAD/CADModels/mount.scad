$fs=0.1;
$fa=0.1;
difference() {
	union() {
		cylinder(3,15/2,15/2);
		translate([-15/2,0,0])
		cube([15,15,3]);
		translate([-15/2,15-3,3])
		rotate([-90,0,0])
		cube([15,15,3]);
	}
	union() {
		translate([0,0,-1])
		cylinder(5,11.5/2,11.5/2);
		translate([0,15-4,-6])
		rotate([-90,0,0])
		cylinder(5,1.5,1.5);
	}
}