$fa=.2;
$fs=.2;

$RED=[1,0,0,1];
$RED_D=[.5,0,0,1];
$RED_L=[1,.5,.5,1];

$GREEN=[0,1,0,1];
$GREEN_D=[0,.5,0,1];
$GREEN_L=[.5,1,.5,1];

$BLUE=[0,0,1,1];
$BLUE_D=[0,0,.5,1];
$BLUE_L=[.5,.5,1,1];

$YELLOW=[1,1,0,1];
$YELLOW_D=[.5,.5,0,1];
$YELLOW_L=[1,1,.5,1];

module t(x,y,z) translate([x,y,z]) children();
module tx(x) t(x,0,0) children();
module ty(y) t(0,y,0) children();
module tz(z) t(0,0,z) children();

module r(x,y,z) rotate([x,y,z]) children();
module rx(x) r(x,0,0) children();
module ry(y) r(0,y,0) children();
module rz(z) r(0,0,z) children();

module s(x,y,z) scale([x,y,z]) children();
module sx(x) s(x,1,1) children();
module sy(y) s(1,y,1) children();
module sz(z) s(1,1,z) children();

module c(r,g,b) color([r,g,b,1]) children();
module ca(r,g,b,a) color([r,g,b,a]) children();
module col(c) color(c) children();

module box(x,y,z) cube([x,y,z]);
module cyl(h,r) cylinder(h,r,r);
module hcyl(h,R,r) difference() { cyl(h,R); tz(-1) cyl(h+2,r); }
module poly(coords) polygon(coords);