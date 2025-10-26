$fa =.1;
$fs =.1;
difference() {
	union() {
		cube([10,10,55]);
		translate([2,2,55])
		cylinder(8,2,2);
	}
	union() {
		translate([2,2,2])
		cube([10,10,51]);
		translate([6,6,-1])
		cylinder(4,1.5,1.5);
		translate([-1,2,55+4+1.1])
		rotate([0,90,0])
		cylinder(6,1.1,1.1);
	}
}
