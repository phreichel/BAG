$fa = 1;
$fs = 0.1;

module Sockel() {
    difference() {
        union() {
            cube([5, 10, 10]);
            translate([0, 5, 10]) {
                rotate([0, 90, 0]) {
                    cylinder(5, 5, 5);
                }
            }
        }
        translate([2.5, 5, 10]) {
            rotate([0, 90, 0]) {
                cylinder(2.6, 5.1, 5.1);
            }
        }
        translate([-1, 5, 10]) {
            rotate([0, 90, 0]) {
                cylinder(8, 2.5, 2.5);
            }
        }
        translate([2.5, -0.5, 4.5]) {
            cube([3, 11, 5]);
        }
    }
}

Sockel();
