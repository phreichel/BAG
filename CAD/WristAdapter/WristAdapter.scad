use <util.scad>;
use <bearings.scad>;
use <actuators.scad>;

module servo() {
    ry(-90) t(-(22.5+4),-6,-6) sg90();
}

module base_plate() {
    c(0,1,0) {
        tz(-4) hcyl(4,45/2,21/2);
        difference() {
            tz(-8) cyl(8,45/2,21/2);
            servo();
        }
    }
}

module medium_plate() {
    c(.5,.7,0) {
        hcyl(4,45/2,21/2);
    }
}

//bearing_21x15x4();
//medium_plate();
base_plate();
//servo();
