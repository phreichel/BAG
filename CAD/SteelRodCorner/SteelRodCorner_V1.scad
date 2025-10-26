use <util.scad>;

module pivot() {
t(-11,-11,-11) box(22, 22, 22);
}

module infill() {
    t(-8,-5,11) box(16, 10, 25);
    t(-5,-8,11) box(10, 16, 25);
    t(-5,-5,11) cyl(25, 3);
    t(-5, 5,11) cyl(25, 3);
    t( 5,-5,11) cyl(25, 3);
    t( 5, 5,11) cyl(25, 3);
}

module cap() {
    t(11+25,-5,-5) sphere(r=3);
    t(11+25, 5,-5) sphere(r=3);
    t(11+25,-5, 5) sphere(r=3);
    t(11+25, 5, 5) sphere(r=3);
    t(0,-5,-5) box(11+25+3,10,10);
    t(11+25,2+3,-5) cyl(10,3);
    t(11+25,-2-3,-5) cyl(10,3);
    rx(90) {
        t(11+25,2+3,-5) cyl(10,3);
        t(11+25,-2-3,-5) cyl(10,3);
    }
}

module corner() {
    union() {
        pivot();
        infill();
        rx(90) infill();
        ry(90) infill();
        cap();
        rz(-90) cap();
        ry(-90) cap();
    }
}

corner();