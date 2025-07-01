$fs=.1;
$fa=1;

module plate(width,depth,hole,rim,drill) {
  difference() {
    cube([width,width,depth]);
    union() {
      translate([width/2,width/2,0]) {
        cylinder(depth,hole,hole);
      }
      translate([rim,rim,0]) {
        cylinder(depth,drill,drill);
        translate([0,0,2]) {
          cylinder(depth-1,drill+1,drill+2);
        }
      }
      translate([rim,width-rim,0]) {
        cylinder(depth,drill,drill);
        translate([0,0,2]) {
          cylinder(depth-1,drill+1,drill+2);
        }
      }
      translate([width-rim,rim,0]) {
        cylinder(depth,drill,drill);
        translate([0,0,2]) {
          cylinder(depth-1,drill+1,drill+2);
        }
      }
      translate([width-rim,width-rim,0]) {
        cylinder(depth,drill,drill);
        translate([0,0,2]) {
          cylinder(depth-1,drill+1,drill+2);
        }
      }
    }
  }
}  

difference() {
  union() {
    cylinder(18.5,4,4);
    rotate_extrude() {
      translate([3.25,18.5,0]) {
        circle(1);
      }
    }
    linear_extrude(4) {
      polygon([[0,3],[21,20],[21,-20],[0,-3]]);
    }
    translate([21,-20,0]) {
      plate(40,4,15,4.5,2);
    }
  }
  union() {
    cylinder(20,2.5,2.5);
    translate([0,6,6]) {
      rotate([90,0,0]) {
        cylinder(12,1.5,1.5);
      }
    }
    translate([-1,-6,6]) {
      cube([2,12,14]);
    }
    linear_extrude(4) {
      polygon([[6,3],[17,12],[17,-12],[6,-3]]);
    }
  }
}
