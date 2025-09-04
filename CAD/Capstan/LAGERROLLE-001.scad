use <util.scad>;

difference() {

    union() {
        cyl(25,20);        
        tz(   0) cylinder(3,22.5,20);
        tz(   9) cylinder(1.5,20,22.5);
        tz(10.5) cylinder(1.5,22.5,20);
        tz(  22) cylinder(3,20,22.5);
        
        
    }
    
    union() {
        
        tz(-1) cyl(27,4.5);
        
        t(-16,15,6) ry(90) cyl(50,.75);
        t(-25,15,5.5) box(50,30,1);
        t(-27.5,11.25,3) box(30,7.5,6);

        t(-16,15,16.5) ry(90) cyl(50,.75);
        t(-25,15,16) box(50,30,1);
        t( -3,15,16.5) ry(90) cylinder(20,.75,4);

        tz(-1) cyl(7,11);
        tz(19) cyl(7,11);        
    }
    
}