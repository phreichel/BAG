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

module cycloid_pins(R,N,h,Rp,d,t) {
	r=R/N;
	for (i=[0:5]) {
		rz(i*60)
		tx(R-Rp-r)
		hcyl(h,Rp-r-d,Rp-r-d-t);
	}
}

module cycloid_pin_deck(R,N,h,Rp) {
	r=R/N;
	cycloid_pins(R,N,h,Rp,0,1);
	tz(-3) {
		difference() {
			hcyl(3,R-2*r,R-2*Rp);
			for (i=[0:5]) {
				rz(i*60) {
					tx(R-Rp-r) {
						zdiff() cyl(3,1.5);
						tz(-.1) cyl(2.1,3);
					}
				}
			}
		}
	}
}

module cycloid_pin_cap(R,N,h,Rp) {
	r=R/N;
	tz(-3) {
		difference() {
			hcyl(3,R-2*r,R-2*Rp);
			for (i=[0:5]) {
				rz(i*60) {
					tx(R-Rp-r) {
						zdiff() cyl(3,1.5);
						tz(-.1) cyl(2.1,3);
					}
				}
			}
		}
	}
	cycloid_pins(R,N,h,Rp,1,1);
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

module cycloid_case(R,N,eF,h,h2,t) {
	col($RED) {
		r=R/N;
		Rw=r*(N+1);
		e=r*eF;
		hcyl(1,Rw+2*r-e+t,R);
		tz(1)
		cycloid_wall(R,N,eF,h,t);
		Rx=Rw+2*r-e+t;
		for (i=[0:5]) {
			rz(i*60) {
				tx(Rx+3.5) {
					difference() {
						union() {
							t(-4,-3.5,0) box(4,7,h2);
							cyl(h2,3.5);
						}
						union() {
							zdiff() cyl(h2,1.5);
							tz(-.1) cyl(2.1,2.5);
						}
					}
				}
			}
		}
	}
}

module cycloid_cover(R,N,eF,h,h2,t) {
	col($RED_D) {
		r=R/N;
		Rw=r*(N+1);
		e=r*eF;
		Rx=Rw+2*r-e+t;
		hcyl(1,Rx+1,R);
		difference() {
			hcyl(h,Rx+1,Rx);
			for (i=[0:5]) {
				rz(i*60) {
					t(Rx-5,-3.75,h-h2) box(10,7.5,h);
				}
			}
		}
		for (i=[0:5]) {
			rz(i*60) {
				tx(Rx+3.5) {
					difference() {
						union() {
							t(-3.5,-3.5,0) box(3.5,7,h-h2);
							cyl(h-h2,3.5);
						}
						union() {
							zdiff() cyl(h-h2,1.5);
							tz(-.1) cyl(2.1,2.5);
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
h=4;   // disk height
t=2;   // wall thickness

hc=2*h+1; // case height

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
	cycloid_case(R,N,eF,hc,(hc+2)/2,t);
	rx(180) tz(-11)cycloid_cover(R,N,eF,hc+2,(hc+2)/2,t);
	
	// pin holder
	tz(.5) cycloid_pin_deck(R,N,hc+1,7);
	rx(180) tz(-hc-1) cycloid_pin_cap(R,N,hc+1,7);
	
}

module inner_adapter(R,N,h,Rp) {
	r=R/N;
	difference() {
		hcyl(h,R-2*r,R-2*Rp);
		for (i=[0:11]) {
			rz(i*30) {
				t(R-Rp-2*r,0,1) cyl(h-.9,3);
				t(R-Rp-2*r,-3,1) box(2*r,6,h-.9);
				t(R-Rp,0,1) cyl(h-.9,3);
			}
		}
		zdiff() {
			for (i=[0:11]) {
				rz(i*30) {
					tx(R-Rp-2*r) cyl(h,1.5);
					t(R-Rp-2*r,-1.5,0) box(2*r,3,h);
					tx(R-Rp) cyl(h,1.5);
				}
			}
		}
	}
	difference() {
		union() {
			for (i=[0:5]) {
				rz(i*60) {
					tx(R) cyl(min(h,3),4);
					t(R-4,-4,0) box(4,8,min(h,3));
				}
			}
		}
		for (i=[0:5]) {
			rz(i*60) {
				tx(R) zdiff() cyl(min(h,3),1.5);
				t(R,0,1) cyl(min(h,3)-.9,3);
			}
		}
	}
}

cycloid_axis(R,N,eF);
//cycloid_disk(R,N,eF,h,10.5,7);
//cycloid_pin_deck(R,N,hc+1,7);
//cycloid_pin_cap(R,N,hc+1,7);
//cycloid_case(R,N,eF,hc,(hc+2)/2,t);
//cycloid_cover(R,N,eF,hc+2,(hc+2)/2,t);
//inner_adapter(R,N,h,7);

//cycloid_assembly();
