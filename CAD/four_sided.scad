use <util.scad>

difference() {

    t(-10,-10,-15)
    box(20, 20, 30);

    t( 10, 0, 0) sphere(2);
    
    t(-10,-3,-3) sphere(2);
    t(-10, 3,-3) sphere(2);
    t(-10,-3, 3) sphere(2);
    t(-10, 3, 3) sphere(2);

    t( -3,10,-3) sphere(2);
    t(  3,10, 3) sphere(2);

    t( 0,-10, 0) sphere(2);
    t( 5,-10,-5) sphere(2);
    t(-5,-10, 5) sphere(2);

}

tz( 15) sphere(10);
tz(-15) sphere(10);