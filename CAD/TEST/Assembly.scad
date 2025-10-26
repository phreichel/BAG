$fs=.2;
$fa=.2;

module red() color([1,0,0,1]) children();
module green() color([0,1,0,1]) children();
module blue() color([0,0,1,1]) children();
module orange() color([1,.5,0,1]) children();
module tx(x,y,z) translate([x,y,z]) children();
module rx(x,y,z) rotate([x,y,z]) children();
module box(dx,dy,dz) cube([dx,dy,dz]);
module cyl(h,r) cylinder(h,r,r);
module hcyl(h,ro,ri) {
  difference() {
    cyl(h,ro);
    tx(0,0,-1) cyl(h+2,ri);
  }
}

module gear(h,m,n,c) {
  d=m*n;
  r=d/(2*PI);
  union() {
    hcyl(h,r,c);
    for (i=[0:n-1]) {
      rx(0,0,i*(360/n)) {
        tx(r-1,-m/4,0) box(m/2+1,m/2,h);
        tx(r+m/2,0,0) cyl(h,m/4);
      }
    }
  }
}

module hgear(h,m,n,c,s) {
  d=m*n;
  r=d/(2*PI);
  union() {
    hcyl(h,r,r-2);
    hcyl(h,c+2,c);
    for (i=[0:s]) {
      rx(0,0,i*(360/s)) tx(c+1,-1.5,0) box(r-c-2,2,h);
    }
    for (i=[0:n-1]) {
      rx(0,0,i*(360/n)) {
        tx(r-1,-m/4,0) box(m/2+1,m/2,h);
        tx(r+m/2,0,0) cyl(h,m/4);
      }
    }
  }
}

module spindle(a,b) {
  module gears() {
    red() hcyl(7,11,5);
    tx(0,0,28) red() hcyl(7,11,5);
  }
  rx(0,0,-$t*360) {
    gears();
    hcyl(35,5,3);
    tx(0,0,7) hcyl(21,6,3);
    tx(0,0,8) hgear(8,4,b,5,6);
    tx(0,0,19) gear(8,4,a,5);    
  }
}

a=10;
b=50;
m=4;

da=m*a;
ra=da/(2*PI);

db=m*b;
rb=db/(2*PI);

//tx(ra+rb+m,0,0) spindle(a,b);

/*

tx(0,0,17.5) red() hcyl(7,11,5);
tx(0,0,26) red() hcyl(7,11,5);
tx(0,0,7) hcyl(10.5,6,3);
tx(0,0,17.5) hcyl(15.5,5,3);
tx(0,0,24.5) hcyl(1.5,6,3);
rx(0,0,(360*5)*$t) {
  tx(0,0,8) rx(0,0,17) gear(8,4,a,3);
}
rx(0,0,(360/5)*$t) {
  tx(0,0,17) hgear(8,4,b,11,6);
  tx(0,0,25) hcyl(10,13,11);
  tx(0,0,33) hcyl(2,13,9);
  tx(0,0,33) hcyl(2,25,13);
  tx(0,0,23) hcyl(10,25,23);
  tx(0,0,23) hcyl(2,32,25);
  tx(10,-5,33) box(10,10,10);
}
*/

/*
color([.5,.5,1,.5]) {

  difference() {
    union() {
      tx(-5,-36,16) box(ra+rb+m+10,72,1);
      tx(-5,0,16) cyl(1,36);
      tx(ra+rb+m+5,0,16) cyl(1,36);
    }
    union() {
      tx(0,0,15) cyl(3,8);
      tx(ra+rb+m,0,15) cyl(3,8);
    }
  }

  difference() {
    union() {
      tx(-5,-36,25) box(ra+rb+m+10,72,1);
      tx(-5,0,25) cyl(1,36);
      tx(ra+rb+m+5,0,25) cyl(1,36);
    }
    union() {
      tx(0,0,24) cyl(3,27);
      tx(ra+rb+m,0,24) cyl(3,8);
    }
  }

}
*/

tx(0,0,5) {
  color([.5,1,.5, .5]) {
    difference() {
      union() {
        
        tx(0,-39,0) box(ra+rb+m,78,23);
        tx(ra+rb+m,0,0) cyl(23,39);
        tx(0,0,0) cyl(23,39);
        
        tx(ra+rb+m,0,-7) cyl(9,13);
        
      }
      union() {

        tx(ra+rb+m,0,-5) cyl(7,11);
        tx(ra+rb+m,0,-8) cyl(4,7);

        tx(0,-36,2) box(ra+rb+m,72,22);
        tx(0,-37,12) box(ra+rb+m,74,12);
        
        tx(ra+rb+m,0,2) cyl(22,36);
        tx(ra+rb+m,0,12) cyl(12,37);
        
        tx(0,0,2) cyl(22,36);
        tx(0,0,12) cyl(12,37);

        tx(0,0,-1) cyl(25,8);
        
        tx(-15.5,-15.5,-1) cyl(4,1.5);
        tx(-15.5,+15.5,-1) cyl(4,1.5);
        tx(+15.5,-15.5,-1) cyl(4,1.5);
        tx(+15.5,+15.5,-1) cyl(4,1.5);

        tx(-125,-125,-25) box(250,250,25);
        tx(ra+rb+m,0,-1) cyl(4,13);
        tx(ra+rb+m,0,1) cyl(2,15);

      }
    }
  }
}