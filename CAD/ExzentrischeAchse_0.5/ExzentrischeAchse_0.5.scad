use <util.scad>;

// spacer
difference () {
	union() {
		cyl(1,6);
		tz(1) cyl(14,4);
	}
	ty(.5) difference() {
		cyl(21,2.5);
		t(-2,2,5) box(4,4,16);
	}
}

