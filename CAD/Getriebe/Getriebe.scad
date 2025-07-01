$fs=.1;
$fa=.1;

module gear(h,m,n,c,t) {
  r = m*n/(PI*2);
  union() {
    difference() {
      cylinder(h,r,r);
      cylinder(h,c,c);
      difference() {
        cylinder(h,r-t,r-t);
        cylinder(h,c+t,c+t);
      }
    }
    for (i=[0:5]) {
      rotate([0,0,i*60]) {
        translate([c+t-1,-t/2,0]) {
          cube([r-c-2*t+2,t,h]);
        }
      }
    }
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

gear(7,2,100,11,4);