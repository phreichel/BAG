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

/*
// bearing
tz(1) c(1,0,0) hcyl(7,11,4);

// disk
R=1;
N=21;
D=R*N*2*PI;
r=.5
;
t=360/N;
u=t/360;
tz(1) difference() {
	linear_extrude(7) {
		polygon([
			for (i=[0:N-1])
				for (j=[0:360-1])
					[
						sin(i*t + j*u) * (D/(2*PI)) + sin(j) * (R-r),
						cos(i*t + j*u) * (D/(2*PI)) + cos(j) * (R-r)
					]
		]);
	}
	tz(-1) cyl(9,11);
	for (i=[0:9]) {
		a=360/10;
		rz(i*a) tx(15) tz(-1) cyl(9,3);
	}
}

for (i=[0:20]) {
	a=360/21;
	tx(r) rz(i*a) tx(D/(2*PI)+1.5*(R)) cyl(7,R+r);
}
*/

linear_extrude(7) {
	polygon([for (i=[0:1024*4]) [
		25*cos(2*PI* (i/4)) + 0.5 * cos((24. + 1.) * 2 * PI * (i/4) / 1.),
		25*sin(2*PI* (i/4)) + 0.5 * sin((24. + 1.) * 2 * PI * (i/4) / 1.)
	]]);
}