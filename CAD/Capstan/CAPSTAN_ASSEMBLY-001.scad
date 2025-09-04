use <util.scad>;
use <bearings.scad>;

bv = true;

module anchor_22x8x7(bearings_visible=false) {
    hcyl(6.5,15,11);
    if (bearings_visible) {
        bearing_22x8x7();
    }
}

module capstan() {
    cyl(30,4);
    tz(6.5) cylinder(3,13,10);
    tz(19.5) cylinder(3,10,13);
    difference() {
        tz(9.5) cyl(10,10);
        tz(10.5) rotate_extrude() tx(10) circle(d=1);
        tz(12.5) rotate_extrude() tx(10) circle(d=1);
        tz(14.5) rotate_extrude() tx(10) circle(d=1);
        tz(16.5) rotate_extrude() tx(10) circle(d=1);
        tz(18.5) rotate_extrude() tx(10) circle(d=1);
    }
}

t( 0,0,0) {
    anchor_22x8x7(bv);
    tz(.5) capstan();
}
t(80,0,0) anchor_22x8x7(bv);
