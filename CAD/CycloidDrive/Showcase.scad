use <util.scad>;

No = 21;
Ni = No - 1;
e  = .5;

Ro = 25;
Rr = Ro / No;
Cr = Rr * 2 * PI;
Nr = Cr / .2;
Ri = Rr * Ni;

Nh = 6;

module base() {
	d  = 31;
	d2 = d/2;
	difference() {
		t(-20,-20,0) box(40,40,4);
		tz(-1) cyl(6,10);
		t(+d2,+d2,0) {
			tz(-1) cyl(6,1.5);
			tz(1) cyl(4,3);
		}
		t(+d2,-d2,0) {
			tz(-1) cyl(6,1.5);
			tz(1) cyl(4,3);
		}
		t(-d2,+d2,0) {
			tz(-1) cyl(6,1.5);
			tz(1) cyl(4,3);
		}
		t(-d2,-d2,0) {
			tz(-1) cyl(6,1.5);
			tz(1) cyl(4,3);
		}
	}
}

module cdisk(h,N,R,e,n) {
	linear_extrude(h)
	polygon([
		for (i=[0:N-1])
		for (j=[0:n-1])
		[
			sin((i+j/n)*(360/N)) * R + sin((j/n)*360) * e,
			cos((i+j/n)*(360/N)) * R + cos((j/n)*360) * e
		]
	]);
}

module idisk(h,t,N,R,r,e,n) {
	difference() {	
		cyl(h,R+r+t);
		tz(-1) cdisk(h+2,N,R,e,n);
	}
}
	
module case() {
	union() {
		base();
		difference() {
			cyl(4,Ro+Rr+3);
			t(-20,-20,-1) box(40,40,6);
		}
		hcyl(16,Ro+Rr+3,Ro+Rr);
		tz(4) rz(45) idisk(7,3,No,Ro,Rr,e,Nr);
		tz(14) for (i=[0:6]) {
			rz(i*360/6)
			tx(Ro+4.2+1.5)
			hcyl(2,3.5,1.5);
		}
	}
}

module disk(Nh) {
	c(1,0,0)
	rz($t*360)
	rz(45) 
	ty(2*e)
	rz(-$t*360*No/Ni)
	difference() {
		cdisk(7,Ni,Ri,e,Nr);
		tz(-1)cyl(9,11);
		for (i=[0:Nh-1])
			rz(i*360/Nh)
			tx(11+(Ri-11)/2)
			tz(-1) cyl(9,3);
	}
}

module axis() {
	rz($t*360)
	rz(45)
		ty(2*e)
		difference() {
			union() {
				c(.5,.5,1) tz(3.1) hcyl(7,11,4);
				c(0,0,1) {
					cyl(20,4);
					hcyl(3,6,4);
				}
			}
			ty(-2*e)
			difference() {
				tz(-1) cyl(20+2,2.5);
				t(-2,2,5) box(4,4,16);
			}
		}
}

module receiver() {
	c(0,1,0)
	rz(-$t*360*(No/Ni-1))
	{
		rz(45) {
			difference() {
				union() {
					hcyl(3,Ri,5+2*e);
					hcyl(9,11+(Ri-11)/2+2.5,5+2*e);
				}
				for (i=[0:5]) {
					tz(-1) 
					rz(30+i*360/6)
					tx(11+(Ri-11)/3)
					cyl(11,1.5);
				}
			}
			for (i=[0:Nh-1]) {
				rz(i*360/Nh)
				t(11+(Ro-11)/2, 0, -6)
				cyl(13,1.5);
			}
		}
	}
}

module cap() {
	tz(.1) hcyl(3.9,Ro+.9,Ri-3.5);
	tz(2) {
		hcyl(2,Ro+4.2,Ri-3.5);
		for (i=[0:6]) {
			rz(i*360/6)
			tx(Ro+4.2+1.5)
			hcyl(2,3.5,1.5);
		}
	}
}

tz(1) axis();
case();
tz(4)
disk(Nh);
tz(11) receiver();
tz(14) cap();