use <util.scad>;

module pipe() {
	hcyl(100,12.5,12.5-3);
}

module case() {
	c(0,0,1)
	difference() {
		hcyl(25,16.5,12.5);
		t(-20,-1,5) box(40,2,21);
		tz(5) hcyl(5,17,15.5);
		tz(15) hcyl(5,17,15.5);
	}
}

module bearings() {
	tz(-17.1) c(1,0,0) hcyl(7.1,11,4);
	tz(+10) c(1,0,0) hcyl(7.1,11,4);
}

module hinge() {
	difference() {
		union() {
			tz(-17) c(0,0,1) cyl(7,17);
			tz(+10) c(0,0,1) cyl(7,17);
			t(-15,-17,-17) c(0,0,1) box(15,34,7);
			t(-15,-17,+10) c(0,0,1) box(15,34,7);
			t(-21,-17,-17) c(0,0,1) box(7,34,34);
			tx(-21) ry(-90) case();
		}
		tz(-18) c(0,0,1) cyl(40,11);
	}
}

module head() {
	difference() {
		union() {
			tz(-9.9) c(0,1,0) cyl(19.8,13);
			t(0,-13,-9.9) c(0,1,0) box(18,26,19.8);
			tx(18) ry(90) c(0,1,0) cyl(7,16.5);
			tx(25) ry(90) c(0,1,0) case();
		}
		union() {
			tz(-20) c(0,1,0) cyl(40,4);
			tz(+5) rx(90) tz(-15) cyl(30,2);
			tz(-5) rx(90) tz(-15) cyl(30,2);
			tz(+5) rx( 90) tz(+7) cyl(15,4);
			tz(+5) rx(-90) tz(+7) cyl(15,4);
			tz(-5) rx( 90) tz(+7) cyl(15,4);
			tz(-5) rx(-90) tz(+7) cyl(15,4);
		}
	}
}

module axis() {
	difference() {
		tz(-20) c(0,1,1) cyl(40,3.9);
		union() {
			tz(+5) rx(90) tz(-15) c(0,1,1) cyl(30,2);
			tz(-5) rx(90) tz(-15) c(0,1,1) cyl(30,2);
		}
	}
}

//head();
//hinge();
axis();
//bearings();
