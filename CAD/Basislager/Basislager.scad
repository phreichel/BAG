$fa = 0.1;
$fs = 0.1;
h1=0;
h2=6;
h3=h2+10;
r1=6;
r2=r1+1.5;
r3=30;
r4=r3+2;
n=6;
union() {
    for (i=[0:n-1]) {
        rotate([0,0,i*(360/n)]) {
            translate([r1+1,-2,0]) {
                cube([r4-r1-2,4,4]);
            }
        }
    }
    rotate_extrude() {
        polygon([[r1,h1],[r2,h1],[r2,h3],[r1,h3]]);
        polygon([[r3,h1],[r4,h1],[r4,h2],[r3,h2]]);
    }
    translate([14, 25, 0]) {
        cube([4, 24, 4]);
    }
    translate([-18, 25, 0]) {
        cube([4, 24, 4]);
    }
    translate([-20, 52, 0]) {
        difference() {
            translate([0,-3,0]) {
                cube([40, 44, 4]);
            }
            translate([20,20,-1]) {
                cylinder(6,12.5,12.5);
            }
            translate([4.5,4.5,-1]) {
                cylinder(6,2,2);
            }
            translate([35.5,4.5,-1]) {
                cylinder(6,2,2);
            }
            translate([4.5,35.5,-1]) {
                cylinder(6,2,2);
            }
            translate([35.5,35.5,-1]) {
                cylinder(6,2,2);
            }
        }        
    }
}
