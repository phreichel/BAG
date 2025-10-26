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

m=4;
n1=10;
n2=50;
r1=rgear(4,n1);
r2=rgear(4,n2);

module g1() {
  difference() {
    gear(7,4,n1);
    translate([0,0,-1]) axis(7+2,4);
  }
}

module g2() {
  difference() {
    gear(7,4,n2);
    translate([0,0,-1]) axis(7+2,4);
  }
}

/*
color([1,.5,0,1]) {
  translate([0,0,-5]) {
    g1();
    translate([r1+r2,0,0]) g2();
    translate([2*(r1+r2),0,0]) g2();
  }
}
*/

//translate([-22,-20,-10]) {
  difference() {
    union() {
      cube([145,40,10]);
      cube([10,20,35]);
      translate([135,20,0]) cube([10,20,35]);
    }
    union() {
      translate([22,20,3]) {
        cylinder(8,11,11);
        translate([0,0,-4]) cylinder(12,6,6);
      }
      translate([22+r1+r2,20,3]) {
        cylinder(8,11,11);
        translate([0,0,-4]) cylinder(12,6,6);
      }
      translate([22+2*(r1+r2),20,3]) {
        cylinder(8,11,11);
        translate([0,0,-4]) cylinder(12,6,6);
      }
      translate([2,-1,30]) cube([6,6,3]);
      translate([5,5,30]) cylinder(3,3,3);
      translate([2,35,-1]) cube([6,6,9]);
      translate([5,35,-1]) cylinder(9,3,3);
      translate([137,35,30]) cube([6,6,3]);
      translate([140,35,30]) cylinder(3,3,3);
      translate([137,-1,-1]) cube([6,6,9]);
      translate([140,5,-1]) cylinder(9,3,3);
      translate([5,5,-1]) cylinder(40,2,2);
      translate([5,35,-1]) cylinder(40,2,2);
      translate([140,5,-1]) cylinder(40,2,2);
      translate([140,35,-1]) cylinder(40,2,2);
    }
  }
//}