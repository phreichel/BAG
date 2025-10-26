$fa=0.1;
$fs=0.1;

function rgear(m,n) = (m*n)/(2*PI);

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


l=1;
difference() {  
  union() {
    cylinder(l,5.5,5.5);
    cylinder(1,6,6);
    translate([0,0,l-1]) cylinder(1,6,6);
  }
  translate([0,0,-1]) axis(l+2,4);
}