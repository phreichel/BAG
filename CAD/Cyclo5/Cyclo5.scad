use <util.scad>;

No = 21;
Ni = No - 1;
e  = 1;

Ro = 25;
Rr = Ro / No;
Cr = Rr * 2 * PI;
Nr = Cr / .2;
Ri = Rr * Ni;

Nh = 6;

tfactor=Ni;

tol=0;

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
			t(-6,-2,-1) box(4,4,22);
		}
	}
}

module bearing() {
	c(1,0,0) hcyl(4,11.5,7.5);
}

module axisHub() {
	difference() {
		union() {
			tx(-2*e) cyl(10,7.5);
			tz(9) cyl(1,9);
		}
		difference() {
			tz(-1) cyl(22,2.5);
			t(-6,-2,-1) box(4,4,22);
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
						cyl(18,6);
					}
				}
				tz(5) {
					hcyl(12,30,25+e);
					//tz(.5) idisk(8,3,No,Ro,Rr,e,Nr,tol);
				}
			}
			union() {
				tz(-2)
				for (i=[0:5]) {
					rz(i*360/6)
					tx(32) {
						cyl(26,2.5);
						tz(4) cyl(14,4);
					}
				}
				tz(2) hcyl(12,40,33.5);
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
			cyl(7.5,4*e);
		}
		tz(7.5) difference() {
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
		cdisk(8,Ni,Ri,e,Nr);
		tz(-1) cyl(10,11.5);
		for (i=[0:Nh-1])
			rz(i*360/Nh)
			tx(11+(Ri-11)/2)
			tz(-1) cyl(9,3);
	}
}	

module assembly() {
	//motorBody();
	rz(tfactor*$t*360) {
		motorAxis();
		tz(4) axisHub();
		tz(5) tx(-2*e) bearing();
		tz(9) tx(-2*e) bearing();
		/*
		rz(180) tz(5) tx(+2*e) c(0,0,1)
		rz(-tfactor*$t*360*No/Ni)
		xdisk();
		*/
	}
	case();
	tz(4) receiver(Nh);
	tz(18) cap();
}

assembly();

//bearing();
//xdisk();
//axisHub();
//case();
//receiver(Nh);
//cap();

