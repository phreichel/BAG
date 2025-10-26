use <util.scad>;

function cycloid(R,N,eFactor) = [
	let(r=R/N)
	let(A=360/N)
	let(n=A/.5)
	for (i=[0:N-1])
		let(a=i*A)
		for (j=[0:n-1])
			let (alpha=a+j*(A/n))
			let (theta=alpha*(R+r)/r)
			let(x=(R+r)*cos(alpha) - eFactor*r*cos(theta))
			let(y=(R+r)*sin(alpha) - eFactor*r*sin(theta))
			[x,y]
];

mode=1;
module zdiff() {
	if (mode==0) {
		children();
	} else {
		tz(-.1)
		sz(1.1) {
			children();
		}
	}
}

module bearing() {
	col($YELLOW) zdiff() hcyl(4,10.5,7.5);
}

module cycloid_disk(R,N,eF,h,Ri,Rp) {
	r=R/N;
	difference() {
		linear_extrude(h) {
			polygon(cycloid(R,N,eF));
		}
		zdiff() {
			cyl(h,Ri);
			for (i=[0:5]) {
				rz(i*60) tx(R-Rp-r) cyl(h,Rp);
			}
		}
	}
}

module cycloid_wall(R,N,eF,h,t) {
	r=R/N;
	e=eF*r;
	Nw=N+1;
	Rw=R/N*Nw;
	Rx=Rw+2*r-e+t;
	difference() {
		cyl(h,Rx);
		zdiff() {
			linear_extrude(h)
			rz(180)
			polygon(cycloid(Rw,Nw,eF));
		}
	}
}

module cycloid_pins(R,N,h,Rp,d) {
	r=R/N;
	for (i=[0:5]) {
		rz(i*60)
		tx(R-Rp-r)
		hcyl(h,Rp-r-d,Rp-r-d-1);
	}
}

module cycloid_pin_deck(R,N,h,Rp) {
	r=R/N;
	hcyl(1,R-2*r,7.5+r);
	cycloid_pins(R,N,h,Rp,0);
}

module cycloid_pin_cap(R,N,h,Rp) {
	r=R/N;
	cycloid_pins(R,N,h,Rp,1);
	tz(h-1) hcyl(1,R-2*r,7.5+r);
}

module cycloid_axis(R,N,eF) {
	r=R/N;
	e=r*eF;
	difference() {
		union() {
			cyl(14,7.5-r);
			t(+r,0,3) cyl(4,7.5);
			t(-r,0,7) cyl(4,7.5);
		}
		zdiff()
		difference() {
			cyl(14,2.5);
			t(-2,2,5)
			box(4,4,14);
		}
	}
}

module cycloid_case(R,N,eF,h,t) {
	col($RED) {
		r=R/N;
		Rw=r*(N+1);
		e=r*eF;
		hcyl(1,Rw+2*r-e+t,R);
		tz(1)
		cycloid_wall(R,N,eF,2*h+1,t);
	}
}

module cycloid_cover(R,N,eF,h,t) {
	col($RED_D) {
		r=R/N;
		Rw=r*(N+1);
		e=r*eF;
		Rx=Rw+2*r-e+t;
		hcyl(1,Rx+1,R);
		hcyl(3,Rx+1,Rx);
		difference() {
			union() {
				tz(h*2+3.1) cyl(1,Rx+1);
				hcyl(h*2+4.1,Rx+1,Rx);
			}
			union() {
				tz(h*2+3.1) cylinder(1,Rx-2,Rx);
				for (i=[0:5]) {
					rz(i*60) {
						tx(Rx-10) {
							ty(-15) box(10,1,h*2+4);
							ty(+14) box(10,1,h*2+4);
							tz(h*2+3) ty(-15) box(20,30,10);
						}	
					}
				}
			}
		}
	}
}

R=30;  // R of disk
N=25;  // N teeth on disk
eF=.6; // excentricity factor
h=4;   // height
t=2;   // wall thickness

module cycloid_assembly() {
	// pin axis
	tz(-2)
	col($GREEN_L)
	cycloid_axis(R,N,eF);

	// scheiben des zykloidgetriebes
	tz(1) {	
		tx(R/N) {
			bearing();
			col($GREEN) cycloid_disk(R,N,eF,h,10.5,7);
		}
		tz(h) rz(180) tx(R/N) {
			bearing();
			col($BLUE_L) cycloid_disk(R,N,eF,h,10.5,7);
		}
	}

	// aussenwand des zykloidgetriebes
	cycloid_case(R,N,eF,h,t);
	rx(180) tz(-11)cycloid_cover(R,N,eF,h,t);
	
	// pin holder
	cycloid_pin_deck(R,N,2*h+1,7);
	tz(1)
	cycloid_pin_cap(R,N,2*h+1,7);
}

cycloid_assembly();

//cycloid_axis(R,N,eF);
//cycloid_disk(R,N,eF,h,10.5,7);
//cycloid_pin_deck(R,N,2*h+1,7);
//cycloid_pin_cap(R,N,2*h+1,7);
//cycloid_case(R,N,eF,h,t);
//cycloid_cover(R,N,eF,h,t);