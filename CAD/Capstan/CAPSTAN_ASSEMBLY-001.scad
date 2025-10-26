use <util.scad>;
use <bearings.scad>;

bv = false;

module roller() {
    c(1,0,0)
    difference() {
        union() {
            tz(-2) cylinder(2,6,4.25);
            tz( 0) cylinder(2,4.25,6);
        }
        tz(-3) cyl(6,1.5);
    }
}

module anchor_22x8x7(bearings_visible=false) {
    hcyl(6.5,15,11);
    if (bearings_visible) {
        bearing_22x8x7();
    }
}

module anchor_roller(rollers_visible=false) {
    hcyl(8,5,1.5);
    hcyl(9,3,1.5);
    if (rollers_visible) {
        tz(11) roller();
        //tz(19) roller();
    }
}

module capstan() {
    c (0,.5,0) {
        cyl(29,4);
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
}

module leading() {
    c(0,.5,0) {
        union() {
            cyl(28.5,1.5);
            tz(12) cyl(4,3);
        }
    }
}

module retainer() {
    c(0,.5,0) {
        union() {
            tz(.5) cyl(29,4);
            tz(7) cyl(16,20);
            tz(   7) cylinder(3,23,20);
            tz(12) cylinder(3,20,23);
            tz(15) cylinder(3,23,20);
            tz(  20) cylinder(3,20,23);
        }
    }
}

module bottom(bv = false) {
    c(.4,.4,1) {
        tz(-3) {
            difference() {
                union() {
                    linear_extrude(3)
                    polygon([
                        [ 90, 30],
                        [-20, 30],
                        [-20,-25],
                        [ 5, -74],
                        [90, -30]
                    ]);
                    t( 88, 28,0) cyl(33,4);
                    t( 88,-28,0) cyl(33,4);
                    t(-18, 28,0) cyl(33,4);
                    t(-18,-27,0) cyl(33,4);
                    t(7.5,-71,0) cyl(33,4);
                }
                union() {
                    t( 88, 28,-1) cyl(35,1.5);
                    t( 88,-28,-1) cyl(35,1.5);
                    t(-18, 28,-1) cyl(35,1.5);
                    t(-18,-27,-1) cyl(35,1.5);
                    t(7.5,-71,-1) cyl(35,1.5);
                }
            }
        }
        anchor_22x8x7(bv);
        tx(25) anchor_roller(bv);
        tx(60) anchor_22x8x7(bv);
        t(20,-40,0)
        rz(30)
        t(-20,-16,0)
        difference() {
            union() {
                t(-3,-3,0) box(46,3,30);
                t(-3,32,0) box(46,3,30);
                t(-3,-3,0) box(3,38,30);
                t(40,-3,0) box(3,38,30);
            }
            t(-4,8,-1) box(48,16,32);
        }
    }
}

module top(bv = false) {
    c(.6,.6,1) {
        tz(30) {
            difference() {
                union() {
                    linear_extrude(3)
                    polygon([
                        [ 90, 30],
                        [-20, 30],
                        [-20,-25],
                        [ 5, -74],
                        [90, -30]
                    ]);
                    t( 88, 28,0) cyl(3,4);
                    t( 88,-28,0) cyl(3,4);
                    t(-18, 28,0) cyl(3,4);
                    t(-18,-27,0) cyl(3,4);
                    t(7.5,-71,0) cyl(3,4);
                }
                union() {
                    t( 88, 28,-1) cyl(5,1.5);
                    t( 88,-28,-1) cyl(5,1.5);
                    t(-18, 28,-1) cyl(5,1.5);
                    t(-18,-27,-1) cyl(5,1.5);
                    t(7.5,-71,-1) cyl(5,1.5);
                }
            }
            rx(180) {
                anchor_22x8x7(bv);
                tx(25) anchor_roller(bv);
                tx(60) anchor_22x8x7(bv);
            }
        }
    }
}

//tx(0) tz(.5) capstan();
//tx(25) tz(1) leading();
tx(60) retainer();

//top(bv);
//bottom(bv);

/*
t(20,-40,0)
rz(30)
t(-20,-16,0)
c(1,0,0)
tz(13)
box(40,32,12);
*/