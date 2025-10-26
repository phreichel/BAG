use <util.scad>;

module schelle() {
    difference() {
        union() {
            t(-13,-13,0) box(26,26,30);
            rz(45) t(-30,-3,0) box(60,6,30);
        }
        t(-10,-10,-5) box(20,20,40);
        rz(45) t(-35,-.25,-5) box(70,70,40);
        rz(45) t(23,-1.5,7) rx(90) cyl(3,6);
        rz(45) t(23, 1,7) rx(90) cyl(6,1.6);
        rz(45) t(23,-1.5,23) rx(90) cyl(3,6);
        rz(45) t(23, 1,23) rx(90) cyl(6,1.6);
        rz(45) t(-23,-1.5,7) rx(90) cyl(3,6);
        rz(45) t(-23, 1,7) rx(90) cyl(6,1.6);
        rz(45) t(-23,-1.5,23) rx(90) cyl(3,6);
        rz(45) t(-23, 1,23) rx(90) cyl(6,1.6);
    }
}

module gabel() {
    difference() {
        union() {
            rz(45) t(-15.5,-18.5,0) box(31,15.5,30);
            rz(45) t(-10,-27.5,10) box(20,9,10);
            rz(-45) t(28,10,15) rx(90) cyl(20,5);
        }
        t(-10,-10,-5) box(20,20,40);
        rz(45) t(-35,-.25,-5) box(70,70,40);
        rz(45) t(-2.5,-38.5,-5) box(5,20,40);
        rz(-45) t(28.5,20,15) rx(90) cyl(40,1.6);
    }
}

module gabelschelle() {
    union() {
        gabel();
        schelle();
    }
}

//schelle();
gabelschelle();