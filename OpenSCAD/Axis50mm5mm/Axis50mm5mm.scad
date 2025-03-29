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

axis(50,5);