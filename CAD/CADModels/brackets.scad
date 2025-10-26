$fs=.1;
$fa=.1;

module bracketR() {
	difference() {
		union() {
			linear_extrude(3)
			polygon([
				[   0,  0],
				[   8,  0],
				[   8,  8],
				[58.5,  8],
				[58.5, 16],
				[48.5, 16],
				[48.5, 10],
				[   0, 10],
				[   0,  0]
			]);
			linear_extrude(7)
			polygon([
				[   0,  8],
				[50.5,  8],
				[50.5, 16],
				[48.5, 16],
				[48.5, 10],
				[   0, 10],
				[   0,  8]
			]);
			linear_extrude(25)
			polygon([
				[   0,  8],
				[  12,  8],
				[  12, 10],
				[   0, 10],
				[   0,  8]
			]);
			linear_extrude(25)
			polygon([
				[38.5,  8],
				[50.5,  8],
				[50.5, 16],
				[48.5, 16],
				[48.5, 10],
				[38.5, 10],
				[38.5,  8]
			]);
		}
		translate([4,4,-1])
		cylinder(5,1.5,1.5);
		translate([54.5,12,-1])
		cylinder(5,1.5,1.5);
	}
}

module bracketL() {
	mirror([1,0,0])
	bracketR();
}

//translate([-5,0,0])  bracketL();
//translate([+5,0,0]) bracketR();

//bracketL();
bracketR();