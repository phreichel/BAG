$fs=.1;
$fa=1;

module halfgear() {
  difference() {
    translate([0,0,-1]) {
      rotate([30,0,0]) {
        rotate([0,10,0]) {
          rotate([0,0,-10]) {
            translate([-2,-1,0]) {
              cube([4,2,10]);
            }
            translate([2,0,0]) {
              cylinder(10,1,1);
            }
          }
        }
      }
    }
    translate([-5,-10,-4]) {
      cube([10,20,4]);
    }
    translate([-5,-10,5]) {
      cube([10,20,4]);
    }
  }
}

module fullgear() {
  translate([0,2,0]) {
    union() {
      halfgear();
      mirror([0,0,1]) {
        halfgear();
      }
    }
  }
}

n=45;
u=2*2.5*n;
r=u/(2*PI);
mirror([1,0,0]) {
  difference() {
    union() {
      translate([0,0,-5]) {
        cylinder(10,r,r);
      }
      for (i=[0:n-1]) {
        rotate([0,0,(360/n)*i]) {
          translate([r,0,0]) {
            fullgear();
          }
        }
      }
    }
    translate([0,0,-5]) {
      cylinder(10,r-4,r-4);
    }
  }
}