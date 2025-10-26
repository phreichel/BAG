$fs=0.1;
$fa=0.1;

module box(x,y,z) {
    translate([-x/2,-y/2,0]) {
        cube([x,y,z]);
    }
}

module cyl(h, r) {
    cylinder(h,r,r);
}

module nema(h) {    
    union() {
        color([.9,.9,.9,1]) {
            cyl(h+15,2);
        }
        difference() {
            union() {
                translate([0, 0, 1]) {
                    color([0,0,0,1]) {
                        box(39,39,h-2);
                    }
                }
                color([.6,.6,.6,1]) {
                    box(40,40,h/2-1);
                    translate([0,0,h/2+1]) {
                        box(40,40,h/2-1);
                    }
                    cyl(h+2,10);
                }
            }
            union() {
                translate([0,0,h+1]) {
                    cyl(0.5,3);
                }
                translate([-15.5,-15.5,0]) {
                    cyl(h,1.5);
                }
                translate([15.5,-15.5,0]) {
                    cyl(h,1.5);
                }
                translate([-15.5,15.5,0]) {
                    cyl(h,1.5);
                }
                translate([15.5,15.5,0]) {
                    cyl(h,1.5);
                }
                translate([-20,-20,0]) {
                    rotate([0,0,45]) {
                        box(2,2,h);
                    }
                }
                translate([20,-20,0]) {
                    rotate([0,0,45]) {
                        box(2,2,h);
                    }
                }
                translate([-20,20,0]) {
                    rotate([0,0,45]) {
                        box(2,2,h);
                    }
                }
                translate([20,20,0]) {
                    rotate([0,0,45]) {
                        box(2,2,h);
                    }
                }
            }
        }
    }
}

nema(20);