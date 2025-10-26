use <util.scad>;

module bearing(h,a,b) {
	color("#ff0000")
	hcyl(h,a,b);
}

module bearing_22x8x7() {
	bearing(7,11,4);
}

module bearing_21x15x4() {
	bearing(4,10.5,7.5);
}

module bearing_11x5x4() {
	bearing(4,5.5,2.5);
}

module bearing_8x3x4() {
	bearing(4,4,1.5);
}