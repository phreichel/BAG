$fa=1;
$fs=1;

function rgear(m,n) = (m*n)/(2*PI);
function mgear(r,n) = (2*PI*r)/n;

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

module gear(h,m,n) {
  r=rgear(m,n);
  m4=m/4;
  stp=360/n;
  difference() {
    union() {
      cylinder(h,r,r);
      for (i=[0:n-1]) {
        rotate(i*stp) {
          translate([r,0,0]) cylinder(h,m4,m4);
        }
      }
    }
    union() {
      for (i=[0:n-1]) {
        rotate((stp/2)+(i*stp)) {
          translate([r,0,-1]) cylinder(h+2,m4,m4);
        }
      }
    }
  }
}

r = rgear(4,40);
m = mgear(r,41);
difference() {
  cylinder(17,30,30 );
  union() {
    translate([0,0,2]) cylinder(2,28,28);
    translate([0,0,3]) gear(9,4,40);
    translate([0,0,11]) cylinder(7,28,28);
  }
}

module planet() {
  difference () {
    gear(16,m,19);
    translate([0,0,-1]) cylinder(18,5,5);
  }
}
translate([-(21/40)*r,0,3]) planet();
translate([+(21/40)*r,0,3]) planet();
