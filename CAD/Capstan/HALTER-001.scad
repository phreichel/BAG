use <util.scad>;

module base() {
    difference() {
        box(16, 48, 3);
        t(8, 8, -1) cyl(5,1.5);
        t(8, 8, 2) cyl(2,6);
        t(8, 40, -1) cyl(5,1.5);
        t(8, 40,  2) cyl(2,6);
    }
}

module arm() {
    difference() {
        union() {
            cyl(5,4);
            tz(5) cylinder(1,3,2.5);
            tx(-4) box(8,12,5);
        }
        tz(-1) cyl(8,1.5);
    }
}

arm();
tz(16) ry(180) arm();
t(-8, 15, -16) rx(90) base();