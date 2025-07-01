$fs=.1;
$fa=1;

module gear(h,m,n) {
  r = m*n/(PI*2);
  union() {
    cylinder(h,r,r);
    for (i=[0:n]) {
      rotate([0,0,i*(360/n)]) {
        translate([r-1,-m/2,0]) {
          cube([2,m/2,h]);
        }
        translate([r+1,-m/4,0]) {
          cylinder(h,m/4,m/4);
        }
      }
    }
  }
}

difference() {
  union() {
    cylinder(2,10,10);
    translate([0,0,2]) {
      gear(7,4,10);
    }
    translate([0,0,9]) {
      cylinder(11,4,4);
    }
  }
  difference() {
    cylinder(20,2.5,2.5);
    translate([2,-2.5,3]) {
      cube([1,5,20]);
    }
  }
  rotate_extrude() {
    translate([4,18.5,0]) {
      circle(1);
    }
  }
}

