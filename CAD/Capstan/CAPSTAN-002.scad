use <util.scad>;
use <bearings.scad>;
$fa=.1;
$fs=.1;

/*
c(.4,.4,.4) tz(-16) cyl(88,4);
tz(-16) bearing_22x8x7();
tz(15) bearing_22x8x7();
*/
module capstan() {
    difference() {
        rotate_extrude(angle=360) {
            ty(-8.6) square([7,23.2]);
            ty(-3.6) square([10,13.2]);
            ty(-3.6) square([12,2]);
            ty(7.8) square([12,2]);
        }
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
        t(0,7,12) rx(90) cyl(14,2);
        t(-7,0,-6) ry(90) cyl(14,2);
    }
}

capstan();