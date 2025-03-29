$fa = 1;
$fs = 0.2;
union() {
    difference() {
        union() {
            cube([40, 40, 20]);
            translate([0, 0, .5]) {
                cube([40, 70, 19]);
            }
        }
        union() {
            translate([7.5, 7.5, 5]) {
                cube([25, 25, 20]);
            }
            translate([12, -1, 10]) {
                cube([16, 10, 20]);
            }
            translate([20, 20, -1]) {
                cylinder(20, 5, 5);
            }
            translate([10, 10, -1]) {
                cylinder(10, 1.5, 1.5);
                translate([0, 0, -1]) {
                    cylinder(5, 3, 3);
                }
                translate([20, 0, 0]) {
                    cylinder(10, 1.5, 1.5);
                    translate([0, 0, -1]) {
                        cylinder(5, 3, 3);
                    }
                }
                translate([0, 20, 0]) {
                    cylinder(10, 1.5, 1.5);
                    translate([0, 0, -1]) {
                        cylinder(5, 3, 3);
                    }
                    translate([20, 0, 0]) {
                        cylinder(10, 1.5, 1.5);
                        translate([0, 0, -1]) {
                            cylinder(5, 3, 3);
                        }
                    }
                }
            }
        }
    }
    union() {
        translate([20, 70, -9]) {
            cylinder(38, 9.5, 9.5);
        }
        translate([20, 70, .5]) {
            cylinder(19, 20, 20);
        }
    }
}

difference() {
    union() {
        translate([0, 70, 20]) {
            cube([40, 80, 10]);
        }
        translate([0, 100, -15]) {
            cube([40, 50, 45]);
        }
        translate([20, 70, -10]) {
            cylinder(5, 20, 18);
            translate([0, 0, 5]) {
                cylinder(5, 18, 20);
            }
        }
        translate([20, 70, 20]) {
            cylinder(10, 20, 20);
        }
    }
    union() {
        translate([20, 70, -11]) {
            cylinder(20, 10, 10);
        }
        translate([20, 70, 20]) {
            cylinder(20, 10, 10);
        }
    }
}
