use <util.scad>;

No = 33;
Ni = No - 1;
e  = .5;

Ro = 25;
Rr = Ro / No;
Cr = Rr * 2 * PI;
Nr = Cr / .2;
Ri = Rr * Ni;

Nh = 6;

tfactor=Ni;

tol=0.1;

module motorBody() {
	c(.7,.7,.7) {
		tz(-1) cyl(1,10);
		t(-20,-20,-11) box(40,40,10);
	}
}

module motorAxis() {
	c(.7,.7,.7) {
		difference() {
			cyl(20,2.5);
			t(-2,2,-1) box(4,4,22);
		}
	}
}

module bearing() {
	c(1,0,0) hcyl(7,11,4);
}

module axisSpacer() {
	c(0,0,1) hcyl(5,12,2.5);
}

module axisHub() {
	difference() {
		union() {
			t(-1,0,0) cyl(7,4);
			t( 1,0,7) cyl(7,4);
		}
		difference() {
			tz(-1) cyl(22,2.5);
			t(-2,2,-1) box(4,4,22);
		}
	}
}

module cdisk(h,N,R,e,n) {
	difference() {
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
}

module idisk(h,t,N,R,r,e,n,tol) {
	difference() {	
		cyl(h,R+r+t);
		tz(-1) cdisk(h+2,N,R+tol,e,n);
	}
}

module case() {
	d2=15.5;
	union() {
		difference() {
			union() {
				tz(-1) {
					hcyl(6,30,12.5);
					for (i=[0:5]) {
						rz(i*360/6)
						tx(32)
						cyl(23.5,6);
					}
				}
				tz(5) {
					hcyl(17.5,30,25+e);
					tz(.5) idisk(6,3,No,Ro,Rr,e,Nr,tol);
					rz(180/Ro) tz(7.5) idisk(6,3,No,Ro,Rr,e,Nr,tol);
				}
			}
			union() {
				tz(-2)
				for (i=[0:5]) {
					rz(i*360/6)
					tx(32)
					cyl(26,2.5);
				}
				t(+d2,+d2,-1) cyl(7,2);
				t(+d2,+d2, 2) cyl(5,3.5);
				t(+d2,-d2,-1) cyl(7,2);
				t(+d2,-d2, 2) cyl(5,3.5);
				t(-d2,+d2,-1) cyl(7,2);
				t(-d2,+d2, 2) cyl(5,3.5);
				t(-d2,-d2,-1) cyl(7,2);
				t(-d2,-d2, 2) cyl(5,3.5);
			}
		}
	}
}

module receiver(Nh) {
	c(0,1,0) {
		for (i=[0:Nh-1]) {
			rz(i*360/Nh)
			tx(11+(Ri-11)/2)
			cyl(14,4*e);
		}
		tz(13.5) difference() {
			union() {
				hcyl(3,Ri,5+2*e);
				hcyl(9,11+(Ri-11)/2+2.5,5+2*e);
			}
			for (i=[0:Nh-1]) {
				rz(180/Nh+i*360/Nh)
				t(11+(Ri-11)/3,0,-1)
				cyl(13,2.5-tol);
			}
		}
	}
}

module cap() {
	c(1,.5,0) {
		difference() {
			union() {
				hcyl(4,30,21);
				for (i=[0:5]) {
					rz(i*360/6) {
						tx(32) cyl(4,6);
					}
				}
			}
			for (i=[0:5]) {
				rz(i*360/6) {
					tx(32) {
						tz(-1) cyl(6,2);
						tz(1)  cyl(5,4);
					}
				}
			}
		}
	}
}

module xdisk() {
	difference() {
		cdisk(7,Ni,Ri,e,Nr);
		tz(-1) cyl(9,11);
		for (i=[0:Nh-1])
			rz(i*360/Nh)
			tx(11+(Ri-11)/2)
			tz(-1) cyl(9,3);
	}
}	

module assembly() {
	motorBody();
	axisSpacer();
	rz(tfactor*$t*360) {
		motorAxis();
		tz(5) axisHub();
		tz(5) tx(-2*e) bearing();
		rz(180) tz(5) tx(+2*e) c(0,0,1)
		rz(-tfactor*$t*360*No/Ni)
		xdisk();
		tz(12) tx(+2*e) bearing();
		tz(12) tx(+2*e) c(0,0,1)
		rz(-tfactor*$t*360*No/Ni)
		xdisk();
	}
	case();
	tz(5) receiver(Nh);
	tz(23) cap();
}

//assembly();

//xdisk();
//axisSpacer();
//axisHub();
case();
//receiver(Nh);
//cap();

