$fa=0.1;
$fs=0.1;

module axis(l,r) {
  rp=r/2;
  rn=-(r+r/2); 
  difference() {
    cylinder(l,r,r);
    union() {
      translate([rp,rp,-1]) cube([r,r,l+2]);
      translate([rp,rn,-1]) cube([r,r,l+2]);
      translate([rn,rp,-1]) cube([r,r,l+2]);
      translate([rn,rn,-1]) cube([r,r,l+2]);
    }
  }
}

difference() {
  cylinder(32,6,6);
  union() {
    difference() {
      cylinder(20,2.5,2.5);
      translate([-2,2,5]) cube([4,4,16]);
    }
    translate([0,0,22]) axis(10,4);
  }
}