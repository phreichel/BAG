use <util.scad>;
use <bearings.scad>;
$fa=.1;
$fs=.1;

module klemm() {
    difference() {
        union() {
            cyl(10,6);
            t(4,1,0) box(7,1,10);
            t(4,-1,0) box(7,1,10);
        }
        union() {
            tz(-1) cyl(12,4);
            tz(-1) box(20,20,2);
            box(20,1,20);
            t(8,3,5) rx(90) cyl(5,1.5);
            tz(9) cylinder(1.5,4,5);
        }
    }
}

module capstan() {
    difference() {
        union() {
            rotate_extrude(angle=360) {
                ty(-3.6) square([10,13.2]);
            }
            tz(-3.6) cylinder(3,12,10);
            tz( 6.6) cylinder(3,10,12);
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
    }
}

union() {
    tz(9) klemm();
    tz(-3) ry(180) klemm();
    capstan();
}