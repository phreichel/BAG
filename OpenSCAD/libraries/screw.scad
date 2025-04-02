use <util.scad>;

module screw(hh,hd,sh,sd) {
	color("#800000") {
		cyl(hh,hd/2);
		cyl(hh+sh,sd/2);
	}
}

module nut(h,do,di) {
	color("#800000")
	difference() {
		linear_extrude(h)
		polygon([
			let(r=do/2)
			for (i=[0:5])	
			let(a=i*60)
			let(x=r*sin(a))
			let(y=r*cos(a))
			[x,y]
		]);

		tz(-1)
		cyl(h+2, di/2);
	}
}

module washer(h,do,di) {
	color("#804000")
	hcyl(h,do/2,di/2);
}

module screwM3(h) {
	difference() {
		screw(3,5,h,3);
		tz(-1)
		linear_extrude(2)
		polygon([
			let(r=1.35)
			for (i=[0:5])	
			let(a=i*60)
			let(x=r*sin(a))
			let(y=r*cos(a))
			[x,y]
		]);
	}
}

module screwM3x5(h) screwM3(5);
module screwM3x6(h) screwM3(6);
module screwM3x8(h) screwM3(8);
module screwM3x10(h) screwM3(10);
module screwM3x12(h) screwM3(12);
module screwM3x14(h) screwM3(14);
module screwM3x16(h) screwM3(16);
module screwM3x18(h) screwM3(18);
module screwM3x20(h) screwM3(20);
module screwM3x25(h) screwM3(25);
module screwM3x30(h) screwM3(30);
module nutM3() nut(2.4,6,3);
module washerM3() washer(.6,6,3);

screwM3x10();
tz(5) washerM3();
tz(6) nutM3();
