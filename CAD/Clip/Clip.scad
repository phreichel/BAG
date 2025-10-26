$fa = 2;
$fs = 0.1;
union() {
    translate([-6,-6,-2]) {
        cube([12,12,2]);
    }
    difference() {
        union() {
            cylinder(10, 5, 5);
            translate([0,0,10]) {
                rotate([0,0,90]) {
                    rotate_extrude(angle=360) {
                        translate([4.5,0,0]) {
                            circle(r = 0.7);
                        }
                    }
                }
            }
        }
        translate([0,0,-1]) {
            cylinder(12, 4, 4);
        }
        translate([-0.5,-6,1]) {
            cube([1,12,10]);
            translate([-0.5,0,0]) {
                cube([2,12,1]);
            }
        }
    }
}
