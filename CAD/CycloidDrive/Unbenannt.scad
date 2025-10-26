use <util.scad>;

module gear(h,m,n) {
	c=m*n;
	r=c/(2*PI);
	union() {
		cyl(h,r);
		for (i=[0:n-1]) {
			rz(i*(360/n)) t(r-1,-m/4,0) box(m/2+1,m/2,h);
		}
	}
}

module staticgear() {
	h=10;
	m=2;
	n=50;
	c=m*n;
	r=c/(2*PI);
	difference() {
		cyl(h,r+3);
		tz(-1) gear(h+2,m,n);
		/*
		for (i=[0:n-1]) {
			rz(i*(360/n)) t(r-1,-m/4,-1) box(m/2+1,m/2,h+2);
		}
		*/
	}
}

module baseplate() {
	l=40;
	w=40;
	h=3;
	d=31;
	r1=3;
	r2=1.5;
	r3=15;
	dhalf=d/2;
	union() {
		difference() {
			t(-l/2,-w/2,0) box(l,w,h);
			union() {
				tz(-1) cyl(h+2,r3);
				t(+dhalf,+dhalf,-1) cyl(h+2,r2);
				t(+dhalf,-dhalf,-1) cyl(h+2,r2);
				t(-dhalf,+dhalf,-1) cyl(h+2,r2);
				t(-dhalf,-dhalf,-1) cyl(h+2,r2);
				t(+dhalf,+dhalf, 1) cyl(h,r1);
				t(+dhalf,-dhalf, 1) cyl(h,r1);
				t(-dhalf,+dhalf, 1) cyl(h,r1);
				t(-dhalf,-dhalf, 1) cyl(h,r1);
			}
		}
		tz(3) staticgear();
	}
}

module motorshaft() {
	cyl(1,10);
	tz(1) difference() {
		cyl(20,2.5);
		t(-2,2,5) box(4,4,16);
	}
}

module motorgear() {
	h=20;
	m=2;
	n=22;
	cyl(2,8);
	tz(2) difference() {
		gear(h,m,n);
		tz(-1) motorshaft();
	}
}

module planetgear() {
	h=10;
	m=2;
	n=14;
	gear(h,m,n);
}

baseplate();
motorshaft();
tz(1) motorgear();
np=5;
for (i=[0:np-1]) {
	rz(i*(360/np)) t(11.5,0,3) planetgear();
}