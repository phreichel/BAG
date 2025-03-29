$fa = 1;
$fs = 0.1;

module Arm() {    
    module ArmHalf() {
        union() {
            translate([-2.5, 5, 5]) {
                rotate([0, 90, 0]) {
                    union() {
                        cylinder(2.5, 5, 5);
                        translate([0, 0, -2.5]) {
                            cylinder(5, 2.5, 2.5);
                        }
                    }
                }
            }
            translate([-2.5, 0, 5]) {
                cube([2.5, 10, 10]);
            }
        }
    }    
    union() {
        ArmHalf();
        translate([0, 0, 21]) {
            mirror([0, 0, 1]) {
                ArmHalf();
            }
        }
    }
}

Arm();
