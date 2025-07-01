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

module zdiff() {
	if ($mode==$PROD) {
		children();
	} else {
		tz(-.1)
		sz(1.1) {
			children();
		}
	}
}

module bearing() {
	col($YELLOW) zdiff() hcyl(4,11.5,7.5);
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
	%hcyl(1,R-2*r,7.5+r);
	cycloid_pins(R,N,h,Rp,0);
}

module cycloid_pin_cap(R,N,h,Rp) {
	r=R/N;
	cycloid_pins(R,N,h,Rp,1);
	%tz(h-1) hcyl(1,R-2*r,7.5+r);
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

//$mode=$PROD;
R=30;  // R of disk
N=25;  // N teeth on disk
eF=.6; // excentricity factor
h=4;   // height
t=2;   // wall thickness

// pin axis
tz(-2)
col($GREEN_L)
cycloid_axis(R,N,eF);

// scheiben des zykloidgetriebes
tz(1) {	
	tx(R/N) {
		bearing();
		col($GREEN) cycloid_disk(R,N,eF,h,11.5,7);
	}
	tz(h) rz(180) tx(R/N) {
		bearing();
		col($BLUE_L) cycloid_disk(R,N,eF,h,11.5,7);
	}
}

// aussenwand des zykloidgetriebes
col($RED) {
	r=R/N;
	Rw=r*(N+1);
	e=r*eF;
	hcyl(1,Rw+2*r-e+t,R);
	tz(1)
	cycloid_wall(R,N,eF,2*h+1,t);
}

// pin holder
cycloid_pin_deck(R,N,2*h+1,7);
tz(1)
cycloid_pin_cap(R,N,2*h+1,7);
