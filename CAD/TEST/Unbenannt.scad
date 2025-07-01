$fs=0.1;
$fa=0.1;

rsp=5;
rc1=(4*10)/(2*PI);
rc2=(4*50)/(2*PI);

module hollow_cylinder(h,ro,ri) {
  difference() {
    cylinder(h,ro,ro);
    translate([0,0,-.1]) cylinder(h+.2,ri,ri);
  }
}

module box(x,y,z) {
    translate([-x/2,-y/2,0]) {
        cube([x,y,z]);
    }
}

module cyl(h, r) {
    cylinder(h,r,r);
}

module nema(h) {    
    union() {
        color([.9,.9,.9,1]) {
            cyl(h+15,2);
        }
        difference() {
            union() {
                translate([0, 0, 1]) {
                    color([0,0,0,1]) {
                        box(39,39,h-2);
                    }
                }
                color([.6,.6,.6,1]) {
                    box(40,40,h/2-1);
                    translate([0,0,h/2+1]) {
                        box(40,40,h/2-1);
                    }
                    cyl(h+2,10);
                }
            }
            union() {
                translate([0,0,h+1]) {
                    cyl(0.5,3);
                }
                translate([-15.5,-15.5,0]) {
                    cyl(h,1.5);
                }
                translate([15.5,-15.5,0]) {
                    cyl(h,1.5);
                }
                translate([-15.5,15.5,0]) {
                    cyl(h,1.5);
                }
                translate([15.5,15.5,0]) {
                    cyl(h,1.5);
                }
                translate([-20,-20,0]) {
                    rotate([0,0,45]) {
                        box(2,2,h);
                    }
                }
                translate([20,-20,0]) {
                    rotate([0,0,45]) {
                        box(2,2,h);
                    }
                }
                translate([-20,20,0]) {
                    rotate([0,0,45]) {
                        box(2,2,h);
                    }
                }
                translate([20,20,0]) {
                    rotate([0,0,45]) {
                        box(2,2,h);
                    }
                }
            }
        }
    }
}

translate([0,0,-22]) nema(20);

cylinder(2,rsp,rsp); // spacer
translate([0,0,2]) cylinder(7,rc1,rc1); // 10t gear
translate([0,0,9]) cylinder(11,rsp,rsp); // spacer
translate([0,0,11]) hollow_cylinder(7,rc2,11); // 50t gear

translate([38,0,0]) cylinder(2,rsp,rsp); // spacer
translate([38,0,2]) cylinder(7,rc2,rc2); // 50t gear
translate([38,0,9]) cylinder(2,rsp,rsp); // spacer
translate([38,0,11]) cylinder(7,rc1,rc1); // 10t gear
translate([38,0,18]) cylinder(2,rsp,rsp); // spacer

difference() {
  union() {
    translate([-7.5,-35,-2]) cube([50,70,2]);
    translate([-7.5,0,-2]) cylinder(2,35,35);
    translate([5+rc1+rc2,0,-2]) cylinder(2,35,35);
    translate([rc1+rc2,0,0]) cylinder(2,20,20);
    translate([rc1+rc2,0,9]) cylinder(2,20,20);
  }
  union() {    
    translate([ 15.5, 15.5,-2.1])cylinder(2.2,1.5,1.5);
    translate([ 15.5,-15.5,-2.1])cylinder(2.2,1.5,1.5);
    translate([-15.5, 15.5,-2.1])cylinder(2.2,1.5,1.5);
    translate([-15.5,-15.5,-2.1])cylinder(2.2,1.5,1.5);
    translate([0,0,-2.1])cylinder(2.2,12,12);
    translate([37.5,0,-2.1])cylinder(2.2,12,12);
    translate([rc1+rc2,0,-.1]) cylinder(2.2,19,19);
    translate([rc1+rc2,0,8.9]) cylinder(2.2,19,19);
  }
}

color([1,0,0,1]) translate([0,0,11]) hollow_cylinder(7,11,4);
color([1,0,0,1]) translate([37.5,0,-7]) hollow_cylinder(7,11,4);
color([1,0,0,1]) translate([37.5,0,20]) hollow_cylinder(7,11,4);