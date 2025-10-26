use <util.scad>;

Rbase=30;
Nteeth=25;
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

tx(-.5*e) cycloid(h,Rbase,Nteeth,e);
base(h,Rbase,Nteeth,e);
//cycloid(h,Rbase,Nteeth,e);
