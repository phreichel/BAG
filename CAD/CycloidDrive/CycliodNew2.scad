use <util.scad>;

Rbase=30;
Nteeth=10;
h=7;

Npoles=Nteeth+1;
Rtooth=Rbase/Nteeth;
e=Rtooth/2;

module cycloid(h,Rbase,Nteeth,e) {
	Npoles=Nteeth+1;
	difference() {
		linear_extrude(h) {
			poly([
				for (i=[0:Nteeth])
				for (j=[0:360-1])
					[
						cos(i*(360/Npoles)+(j/Npoles))*Rbase + cos(j)*(Rbase/Npoles-e),
						sin(i*(360/Npoles)+(j/Npoles))*Rbase + sin(j)*(Rbase/Npoles-e)
					]
			]);
		}
		cyl(h+2,11);
		for (i=[0:6]) {
			rz(i*(360/6))
			t(Rbase/1.5,0,-1)
			cyl(h+2,2*e+Rtooth);
		}
	}
	c(1,0,0) {
		hcyl(h+.1,11,4);
	}	
	c(0,1,0) {
		cyl(h+3,4);
	}
}

module base(h,Rbase,Nteeth,e) {
	Npoles=Nteeth+1;
	c(0,0,1) {
		union() {
			hcyl(h,Rbase+2*e+5,Rbase+2*e);
			for (i=[0:Npoles]) {
				a=i*(360/Npoles);
				rz(a) tx(Rbase+e) {
					cyl(h,e);
					ty(-e) box(e,2*e,h);
				}
			}
		}
	}
}

base(2*h,Rbase,Nteeth,e);
c(0,1,0) {
	tz(7) cyl(24,4);
}

rz(-$t*3600)
	tx(-e) rz($t*3600*(1+(1/Nteeth)))
	cycloid(h+0.1,Rbase,Nteeth,e);

tz(h)
	rz(180/Nteeth)
	rz(-$t*3600)
	tx(e)
	rz(180)
	rz($t*3600*(1+(1/Nteeth)))
	cycloid(h+0.1,Rbase,Nteeth,e);

