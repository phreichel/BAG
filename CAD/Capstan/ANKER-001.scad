use <util.scad>;

difference() {
    union() {
        ty(-6) box(12,12,24);
        tz(24) ry(90) cyl(13,6);
        ty(-18) box(12,36,2);
        tx(2) ry(-90) linear_extrude(2) {
            polygon([
                [ 2,-18],
                [ 2, 18],
                [24,  6],
                [24, -6],
                [ 2,-18]
            ]);
        }
    }
    union() {
        t(6,0,24) ry(90) cyl(12,4);
        t(8,-12,1.5) cyl(1,3);
        t(8, 12,1.5) cyl(1,3);
        t(8,-12,-.5) cyl(3,1.5);
        t(8, 12,-.5) cyl(3,1.5);
    }
}