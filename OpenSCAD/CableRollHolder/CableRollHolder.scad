$fa =.1;
$fs =.1;

module segA() {
	translate([0,0,-7.5]) {
		translate([-5,0,0]) cube([10,10,15]);
		linear_extrude(15) {
			difference() {
				polygon([[-5,0],[-30,100],[30,100],[5,0]]);
				polygon([[-1,0],[-25,97],[25,97],[1,0]]);
			}
		}
	}
}


union() {
	translate([-5,0,-1.5]) cube([10,15,3]);
	difference() {
		segA();
		translate([0,10,-10])  cylinder(20,4,4);
		translate([-4,10,-10]) cube([8,20,20]);
		
	}
}