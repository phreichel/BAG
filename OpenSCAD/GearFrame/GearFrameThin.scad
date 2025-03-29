$fa=0.1;
$fs=0.1;

function rgear(m,n) = (m*n)/(2*PI);

m=4;
n1=10;
n2=50;
r1=rgear(4,n1);
r2=rgear(4,n2);

difference() {
  union() {
    cube([145,40,3]);
    translate([22,20,0]) cylinder(11,13,13);
    translate([22+r1+r2,20,0]) cylinder(11,13,13);
    translate([22+2*(r1+r2),20,0]) cylinder(11,13,13);
  }
  union() {
    translate([22,20,-1]) cylinder(13,6,6);
    translate([22+r1+r2,20,-1]) cylinder(13,6,6);
    translate([22+2*(r1+r2),20,-1]) cylinder(13,6,6);
    translate([22,20,3]) cylinder(11,11,11);
    translate([22+r1+r2,20,3]) cylinder(11,11,11);
    translate([22+2*(r1+r2),20,3]) cylinder(11,11,11);
    translate([5,5,-1]) cylinder(5,2,2);
    translate([5,35,-1]) cylinder(5,2,2);
    translate([140,5,-1]) cylinder(5,2,2);
    translate([140,35,-1]) cylinder(5,2,2);
  }
}
