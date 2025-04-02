use <util.scad>;

function pulley_sketch(Rpulley,Rbelt) = 
let(rp=Rpulley)
let(rb=Rbelt)
[	
	[0       ,-(rb+1)],
	[rp+ 2*rb,-(rb+1)],
	[rp+ 2*rb,-(rb+0)],
	[rp+ 1*rb,-(rb+0)],
	[rp+.5*rb,0      ],
	[rp+ 1*rb,+(rb+0)],
	[rp+ 2*rb,+(rb+0)],
	[rp+ 2*rb,+(rb+1)],
	[0       ,+(rb+1)]
];

module belt_pulley(Rpulley,Rbelt) {	
	tz(Rbelt+1)
	rotate_extrude($fn=360)
	polygon(pulley_sketch(Rpulley,Rbelt));
}

module small_pulley() {
	difference() {
		belt_pulley(5,2.5);
		tz(-1) cyl(8,2.5);
	}
}

module wide_pulley() {
	difference() {
		belt_pulley(20,2.5);
		tz(-1) cyl(8,2.5);
		tz(-1) hcyl(8,20,10);
	}
	for (i=[0:5]) {
		rz(i*60)
		t(5,-1,0)
		box(15,2,7);
	}
}

module double_pulley() {
	tz(7) small_pulley();
	tz(0) wide_pulley();
}

/*
t( 0,0,-10) cyl(50,2.5);
t(40,0,-10) cyl(50,2.5);

c(1,0,0) t(70, 0, 0) small_pulley();
c(1,1,0) t( 0, 0, 0) double_pulley();
c(0,1,0) t(40, 0, 7) double_pulley();
c(0,1,1) t( 0, 0,14) double_pulley();
c(0,0,1) t(40, 0,21) wide_pulley();
*/

double_pulley();