use <util.scad>;
use <cyclo5.scad>;

module bearing() {
	ca(1,0,0,.7) tz(-.1) hcyl(7.2,11,4);
}

module pipe() {
	ca(.7,.7,.7,.5)
	hcyl(100,12.5,10.5);
}

module connector() {
	difference() {
		union() {
			t(-15,-15,0) box(30,30,30);
			tx(-25) tz(15) ry(90) cyl(50,4);
			t(.5,-23,0) box(2,8,30);
			t(-2.5,-23,0) box(2,8,30);
			t(12,0,15)
			for (i=[0:5]) {
				a=i*60;
				rx(a) t(10,-1.5,-1) box(3,17,2);
			}
			t(22,0,15)
			ry(90) {
				difference() {
					union() {
						hcyl(3,16,13);
						for (i=[0:5]) {
							a=i*60;
							rz(a) t(16,0,0) cyl(3,3.5);
						}
					}
					union() {
						for (i=[0:5]) {
							a=i*60;
							rz(a) t(16,0,-1) cyl(5,1.5);
						}
					}
				}
			}
		}
		union() {
			tz(-1) cyl(32,12.5);
			t(-.5,-20,-1) box(1,10,32);
			t(-3.5,-19,4) ry(90) cyl(7,1.5);
			t(-3.5,-19,26) ry(90) cyl(7,1.5);
		}
	}
}

module anchor_plate01() {
	bearing();
	c(.6,1,.6) {
		difference() {
			union() {
				t(-15,0,0) box(4,66,7);
				t(+11,0,0) box(4,66,7);
				cyl(7,15);
			}
			union() {
				tz(-1) cyl(9,11);
				t(10,62,3.5) ry(90) cyl(6,1.5);
				t(10,40,3.5) ry(90) cyl(6,1.5);
				t(-16,62,3.5) ry(90) cyl(6,1.5);
				t(-16,40,3.5) ry(90) cyl(6,1.5);
			}
		}
	}
}

module anchor_plate02() {
	bearing();
	c(.6,1,.6) { 
		difference() {
			union() {
				t(-15,-30,0) box(4,96,7);
				t(+11,-30,0) box(4,96,7);
				rz(90) t(-2,-30,0) box(4,60,7);
				hcyl(7,34,30);
				hcyl(7,14,11);
				for (i=[0:5]) {
					a=i*60;
					rz(a) t(32,0,0) cyl(7,3.5);
				}
			}
			union() {
				tz(-1) cyl(9,11);
				t(10,62,3.5) ry(90) cyl(6,1.5);
				t(10,40,3.5) ry(90) cyl(6,1.5);
				t(-16,62,3.5) ry(90) cyl(6,1.5);
				t(-16,40,3.5) ry(90) cyl(6,1.5);
				for (i=[0:5]) {
					a=i*60;
					rz(a) t(32,0,-1) cyl(9,1.5);
				}
			}
		}
	}
}

module anchor_block() {
	c(0,1,0) {
		difference() {
			union() {
				t(-15,-15,0) box(30,30,30);
				t(-22,-11,0) box(44,3,30);
				t(-22,+8,0) box(44,3,30);
				t(-2.5,12,0) box(5,9,30);
			}
			union() {
				t(-.5,12,-1) box(1,11,32);
				tz(-1) cyl(32,12.5);
				t(20,0,0) cyl(6,2);
				t(18.5,12,4) rx(90) cyl(5,1.5);
				t(18.5,-7,4) rx(90) cyl(5,1.5);
				t(-18.5,12,4) rx(90) cyl(5,1.5);
				t(-18.5,-7,4) rx(90) cyl(5,1.5);
				t(18.5,12,26) rx(90) cyl(5,1.5);
				t(18.5,-7,26) rx(90) cyl(5,1.5);
				t(-18.5,12,26) rx(90) cyl(5,1.5);
				t(-18.5,-7,26) rx(90) cyl(5,1.5);
				t(-3.5,18,4) ry(90) cyl(7,1.5);
				t(-3.5,18,26) ry(90) cyl(7,1.5);
			}
		}
	}
}

module anchor() {
	rx(-90) {
		tx(-22) ry(90) anchor_plate01();
		tx(+15) ry(90) anchor_plate02();
	}
	tz(-66) anchor_block();
}

module montage() {
	// connector
	rx(-45) {
		tz(-50) pipe();
		tz(-15) connector();
	}
	// anchor
	tz(-150) pipe();
	anchor();
	//tx(50) ry(-90) rz(-45)assembly();
}

montage();
//rx(-45) tz(200) montage();
//rx(-90) t(0,-141.5, 300) rz(180) montage();
