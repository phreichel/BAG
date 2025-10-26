use <util.scad>;
use <screw.scad>;
use <pulleys.scad>;

module slider_bar(l,d) {
	color("#808080")
	cyl(l,d/2);
}

module slider_plate(w,t,d) {
	h=(d+2)/2;
	difference() {
		union() {
			tx(-w/2) cyl(t,h);
			tx(+w/2) cyl(t,h);
			tx(-(w/2)-h) box(w+2*h,h,t);
		}
		t(-w/2,0,-1) cyl(t+2,d/2);
		t(+w/2,0,-1) cyl(t+2,d/2);
	}
}

module slider_plate_bore(w,t,d) {
	h=(d+2)/2;
	tx((w-d-2)/2) box(d+2,11,t);
	tx(-(w+d+2)/2) box(d+2,11,t);
	difference() {
		slider_plate(w,t,d);
		t(0,h,t/2)
		rx(90) {
			hole(-5,+2.5,h,d);
			hole(-5,-2.5,h,d);
			hole(+5,+2.5,h,d);
			hole(+5,-2.5,h,d);
		}
	}
}

module pulley() {
	difference() {
		hcyl(8,8,2.5);
		tz(.5) hcyl(7,9,6);
	}
}

module slider_plate_stop(w,t,d) {
	h=(d+2)/2;
	difference() {
		union() {
			slider_plate(w,t,d);
			t(-(w+2*h)/2,t+2.5,-6)
			box(w+2*h,2,t+6);
			tx(-w/2) cyl(2,h);
			tx(+w/2) cyl(2,h);	
			t(-w/2-h,d/2,0){
				difference() {
					box(w+2*h,11,t);
					t(w/2+h-8.5,1,-1) box(17,8,t+2);
				}
			}
		}
		rx(-90) hole(0,-t/2,13,5);
		rx(-90) hole( w/2,3,13,3);
		rx(-90) hole(-w/2,3,13,3);
	}
}

module slider() {
	slider_cap();
	slider_cap();
	slider_bar();
	slider_bar();
}

module belt() {
	color("#202020") {
		t(6,0,-48) box(2,7,96);
		t(-8,0,-48) box(2,7,96);
		difference() {
			union() {
				t(0,0,-48) rx(-90) hcyl(7,8,6);
				t(0,0,48) rx(-90) hcyl(7,8,6);			
			}
			t(-10,0,-48) box(20,9,96);
		}
	}
}

module adapter(w,t) {
	color("#c0a000")
	difference() {
		union() {
			tx(-(t-27)/2) box(t,4,w);
			t(t/2,0,w/2) rx(-90) cyl(4,12);
			t((t-8)/2,0,w/2) rx(-90) difference() {
				cyl(4,30);
				t(20,-25,-1) box(30,50,6);
				t(-50,-25,-1) box(30,50,6);
			}
		}
		t(11,-1,10) box(5,6,25);
		t(11,-1,w-25-10) box(5,6,25);
		t(1,-1,5) box(3,6,20);
		t(1+22,-1,5) box(3,6,20);
		t(1,-1,w-20-5) box(3,6,20);
		t(1+22,-1,w-20-5) box(3,6,20);
		t((t-8)/2,-1,w/2) rx(-90) cyl(6,15);
		t((t-8)/2,0,w/2)
		rx (-90) {
			hole(-15.5,-15.5,4,3);
			hole(-15.5,+15.5,4,3);
			hole(+15.5,-15.5,4,3);
			hole(+15.5,+15.5,4,3);
		}
	}
}

//================================================
module nema17(h) {
	color("#404040")
	difference() {
		linear_extrude(h)
		polygon([
			[-17,-21],
			[+17,-21],
			[+21,-17],
			[+21,+17],
			[+17,+21],
			[-17,+21],
			[-21,+17],
			[-21,-17],
		]);
		bore(-15.5,-15.5,h,3,3,0);
		bore(-15.5,+15.5,h,3,3,0);
		bore(+15.5,-15.5,h,3,3,0);
		bore(+15.5,+15.5,h,3,3,0);
	}
	color("#c0c0c0") {
		tz(2) hcyl(h,11,5);
		difference() {
			tz(1) cyl(h+20-1,2.5);
			t(-2,2,h+5) box(4,4,16);
		}
	}
}
//================================================

t(-13.5,12.5,-70) adapter(140,35);
t(-11,0,-50) slider_bar(100,3);
t(+11,0,-50) slider_bar(100,3);
tz(-52) slider_plate_stop(22,8,3);
tz(52) ry(180) slider_plate_stop(22,8,3);
tz(-17) slider_plate_bore(22,14,3);
tz(+5)  slider_plate_bore(22,14,3);
t(0,2.5,-48) rx(-90) pulley();
t(0,2.5,+48) rx(-90) pulley();
ty(3) belt();
ty(37) rx(90) nema17(20);