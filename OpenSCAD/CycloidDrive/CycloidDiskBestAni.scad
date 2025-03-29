use <util.scad>;

e=.5;
i=25;
Nouter  = 30;
Router  = 24;
Ninner  = floor(Nouter-Nouter/i);
Roffset = e;
Rinner  = Router-Roffset;

tf=5000;
division=48;
rpole=2;
dpole=18;

base();
//tz(5) disk();
//color([0,0,1,.5]) tz(5+7)receiver();
//tz(14.5) clamp();

module clamp() {
	c(0,1,0) {
		union() {
			hcyl(1.5,dpole+rpole+5,dpole+rpole+.5);
			hcyl(3.5,dpole+rpole+10,dpole+rpole+9.1);
			tz(1.5) hcyl(2,dpole+rpole+9.1,dpole+rpole+.5);
		}
		for (i=[0:5]) {
			rz(i*(360/6)) {
				tx(dpole+rpole+13) {
					hcyl(1.5,5,1.5);
					hcyl(3.5,5,3);
				}
			}
		}
	}
}

module receiver() {
	c(0,0,1) {
		union() {
			rz(-$t*tf*(1/i)) {
				hcyl(2,dpole+rpole+4,10);
				difference() {
					hcyl(8,dpole+rpole,10);
					for (i=[0:5]) {
						rz((i+.5)*360/6) {
							t(dpole+rpole-4,-2,2)
							box(5,4,4);
							t(dpole+rpole-2.5,0,5)
							cyl(4,1.5);
						}
					}
				}	
				for (i=[0:5]) {
					rz(i*360/6)
					t(dpole,0,-5)
					cyl(5,rpole);
				}
			}
		}
	}
}

module disk() {
	rz($t*tf)
	c(0,1,0) ty(-2*Roffset)
	rz(-$t*tf*(1+1/i))
	difference() {
		cyl(7,Rinner+Roffset);
		tz(-1) cyl(9,11);
		for (i=[0:5]) {
			rz(i*(360/6))
			t(dpole,0,-1)
			cyl(9,2*e+rpole);
		}
		for (i=[0:Ninner-1]) {
			for (j=[0:(360/division)-1]) {
				x=sin((i+(j/(360/division)))*(360/Ninner)) * (Rinner+Roffset) + sin(j*division) * Roffset;
				y=cos((i+(j/(360/division)))*(360/Ninner)) * (Rinner+Roffset) + cos(j*division) * Roffset;
				t(x,y,-1) cyl(9,Roffset);
			}
		}
	}
}

module base() {
	union() {
		difference() {
			cyl(5,Router+2*Roffset+4);
			tz(-1) cyl(7,15);
			t(+15.5,+15.5,-1) cyl(7,1.5);
			t(+15.5,-15.5,-1) cyl(7,1.5);
			t(-15.5,+15.5,-1) cyl(7,1.5);
			t(-15.5,-15.5,-1) cyl(7,1.5);
			t(+15.5,+15.5, 2) cyl(4,3);
			t(+15.5,-15.5, 2) cyl(4,3);
			t(-15.5,+15.5, 2) cyl(4,3);
			t(-15.5,-15.5, 2) cyl(4,3);
		}
		tz(5) {
			hcyl(11,Router+2*Roffset+4,Router+2*Roffset);
			/* old method- pins--
			c(1,0,0)
			for (i=[0:Nouter-1]) {
				rz(i*(360/Nouter))
				ty(-Router-Roffset)
				cyl(7,Roffset);
			}
			*/
			// new method - waves ---
			points = [
				let(steps = 360*5)
				for (i = [0:steps-1])
        let(theta = i * 360 / steps)
        let(r = Router + e - e * cos(Nouter * theta)) // Wellenradius
        [r * cos(theta), r * sin(theta)]
			];			
			rz(3.7) difference() {
				cyl(7, Router+2*e);
				tz(-1)linear_extrude(9)
				polygon(points);				
			}
		}
		for (i=[0:5]) {
			rz(i*(360/6)) {
				tx(dpole+rpole+13) {
					tz(11.5) {
						tz(2) hcyl(1,5,1.5);
						hcyl(3,5,3);
					}
				}
			}
		}
	}
}