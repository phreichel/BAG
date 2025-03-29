$fa=1.0;
$fs=1.0;
union() {
    rotate_extrude() {
        polygon([[8,0],[12,0],[12,10],[8,10]]);
    }
    for (i=[0:5]) {
        rotate([0,0,i*60+30]) {
            translate([8, -2, 0]) {
                cube([38, 4, 10]);
            }
        }
    }
    difference() {
        rotate_extrude() {
            polygon([[45,0],[52,0],[52,1],[50,2],[48,2],[48,9],[50,9],[52,11],[52,12],[45,12]]);
        }
        union() {
            for (i=[0:5]) {
                rotate([0,0,i*60]) {
                    translate([40, -2, 2]) {
                        cube([9, 4, 7]);
                    }
                }
            }
       }
    }
}