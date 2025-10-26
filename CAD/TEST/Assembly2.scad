$fs=.2;
$fa=.2;

//====================================
module t(x,y,z) translate([x,y,z]) children();
module tx(x) t(x,0,0) children();
module ty(y) t(0,y,0) children();
module tz(z) t(0,0,z) children();
//====================================

//====================================
module r(x,y,z) rotate([x,y,z]) children();
module rx(x) r(x,0,0) children();
module ry(y) r(0,y,0) children();
module rz(z) r(0,0,z) children();
//====================================

//====================================
module cyl(h,r) cylinder(h,r,r);
module hcyl(h,ro,ri) {
  difference() {
    cyl(h,ro);
    tz(-1) cyl(h+2,ri);
   }
 }
module box(x,y,z) cube([x,y,z]);
module cbox(x,y,z) t(-x/2,-y/2,0) box(x,y,z);
//====================================

//====================================
module axis(h,r) {
  r2=r/2;
  difference() {
    cyl(h,r);
    t(+r2,+r2,-1) box(r,r,h+2);
    t(+r2,-r-r2,-1) box(r,r,h+2);
    t(-r-r2,+r2,-1) box(r,r,h+2);
    t(-r-r2,-r-r2,-1) box(r,r,h+2);
  }
}
//====================================

//====================================
module gear(m,n,h) {
  m4=m/4;
  r=(m*n)/(2*PI);
  difference() {
    union() {
      cyl(h,r);
      for (i=[0:n-1]) {
        rz(i*(360/n)) {
          tx(r) cyl(h,m4);
        }
      }
    }
    union() {
      for (i=[0:n-1]) {
        rz(i*(360/n)+(360/(n*2))) {
          tz(-1) {
            tx(r) cyl(h+2,m4);
          }
        }
      }
    }
  }
}
//====================================

//====================================
module bearing() {
  color([1,0,0,1]) {
    hcyl(7,11,5);
  }
}
//====================================

m=4;
n1=10;
n2=50;
r1=(m*n1)/(2*PI);
r2=(m*n2)/(2*PI);

//====================================
module column1() {
  union() {
    bearing();
    cyl(39,5);
    tz(7) cyl(1,6);
    tz(8) gear(m,n1,7);
    tz(15) cyl(1,6);
    tz(23) cyl(1,6);
    tz(24) gear(m,n1,7);
    tz(31) cyl(1,6);
    tz(32) bearing();
  }
}
//====================================

//====================================
module column2() {
  tx(r1+r2) union() {
    bearing();
    cyl(39,5);
    tz(7) cyl(1,6);
    tz(8) gear(m,n2,7);
    tz(15) cyl(1,6);
    tz(16) gear(m,n1,7);
    tz(23) cyl(1,6);
    tz(24) gear(m,n2,7);
    tz(31) cyl(1,6);
    tz(32) bearing();
  }
}
//====================================

//====================================
module column3() {
  tx(2*(r1+r2)) union() {
    bearing();
    cyl(39,5);
    tz(7) cyl(1,6);
    tz(15) cyl(1,6);
    tz(16) gear(m,n2,7);
    tz(23) cyl(1,6);
    tz(31) cyl(1,6);
    tz(32) bearing();
  }
}
//====================================

//====================================
module plate() {
  difference() {
    union() {
      t(-30,-20,0) box(152,40,2);
      hcyl(9,13,11);
      tx(r1+r2) hcyl(9,13,11);
      tx(2*r1+2*r2) hcyl(9,13,11);
    }
    union() {
      tz (-1) {
        cyl(4,8);
        tx(r1+r2) cyl(4,8);
        tx(2*r1+2*r2) cyl(4,8);
      }
      t(-15.5,-15.5,-1) cyl(4,1.5);
      t(-15.5,+15.5,-1) cyl(4,1.5);
      t(+15.5,-15.5,-1) cyl(4,1.5);
      t(+15.5,+15.5,-1) cyl(4,1.5);
    }
  }
}
//====================================

plate();
tz(2) column1();
tz(2) column2();
tz(2) column3();
tz(43) mirror([0,0,1]) plate();

t(-30,-20,2) box(10,40,40);
t(112,-20,2) box(10,40,40);
