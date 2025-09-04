use <util.scad>;
use <bearings.scad>;
$fa=.1;
$fs=.1;

module capstan() {
    difference() {
        union() {
            rotate_extrude(angle=360) {
                ty(-3.6) square([10,13.2]);
            }
            tz(-3.6) cylinder(3,12,10);
            tz( 6.6) cylinder(3,10,12);
        }
        tz(-4.6) cylinder(2,6,4);
        tz( 8.6) cylinder(2,4,6);
        rotate_extrude(angle=360) {
            t(0,-9.6,0) square([4,25.2]);
            tx(10) {
                ty(  0) circle(d=1.2);
                ty(1.5) circle(d=1.2);
                ty(  3) circle(d=1.2);
                ty(4.5) circle(d=1.2);
                ty(  6) circle(d=1.2);
            }
        }
    }
}

capstan();