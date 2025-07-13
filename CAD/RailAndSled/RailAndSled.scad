use <util.scad>;

module sled() {
    union() {
        tz(-2)  box(20,20,7);
        tx(-3)  box(26,20,3);
        tz(-10) 
        difference() {
            box(3,20,8);
            t(-1,10,4) ry(90) cyl(5,1.5);
        }
    }
}

module rail() {
    tz(-11) box(32,106,3);
    tz(-10) box(3,106,10);
    t(29,0,-10) box(3,106,10);
    difference() {
        box(32,106,6);
        t(5,3,-1) box(22,100,9);
        t(2,3,3) box(28,100,9);
    }
}

sled();
//rail();
//t(6,3,3) sled();
//t(26,103,3) rz(180) sled();