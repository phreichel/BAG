$fs=.1;
$fa=.1;

module gear(h,m,n,c,t,ct) {
  r = m*n/(PI*2);
  union() {
    difference() {
      cylinder(h,r,r);
      cylinder(h,c,c);
      difference() {
        cylinder(h,r-t,r-t);
        cylinder(h,c+ct,c+ct);
      }
    }
    for (i=[0:5]) {
      rotate([0,0,i*60]) {
        translate([c+ct-1,-t/2,0]) {
          cube([r-c-t-ct+2,t,h]);
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


//difference() {
  union() {
    gear(7,2,100,4,4,(2*20)/(PI*2)-4);
    translate([0,0,7]) {
      gear(7,2,20,4,4,4);
    }
  }
  /*
  translate([-2,-2,0]) {
    cube([4,4,29]);
  }
}
  */