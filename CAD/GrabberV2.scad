$fs = 0.1;
$fa = 0.1;

$base_length      = 100;
$base_width       =  20;
$base_depth       =   5;
$base_slot_offset =   5;

$slot_radius = 1.5;
$slot_length = $base_length-20;
$slot_fase   = 0.5;

$side_thickness = 10;
$side_height    = 25;
$side_offset    =  5;
$side_extent    =  2;
$side_skirt     = 25;

$rod_radius  = 1.5;
$rod_offset  = 4;
$rod_height1 = $base_depth + $side_extent + $rod_offset;
$rod_height2 = $side_height - $rod_offset;

$block_length = 20;

module base_slot() {
    translate([0,0,-1])
    cylinder(
        $base_depth+2,
        $slot_radius,
        $slot_radius
    );
    translate([0,-$slot_radius,-1])
    cube([
        $slot_length,
        $slot_radius * 2,
        $base_depth+2
    ]);
    translate([$slot_length,0,-1])
    cylinder(
        $base_depth+2,
        $slot_radius,
        $slot_radius
    );
}

module base_body() {
    color([1.0,0.7,0.0])
    cube([
        $base_length,
        $base_width,
        $base_depth
    ]);
}

module base() {
    $slot_stride = ($base_length - $slot_length) / 2;    
    difference() {
        base_body();
        translate([
            $slot_stride,
            $base_slot_offset,
            0
        ])
        base_slot();
        translate([
            $slot_stride,
            $base_width-$base_slot_offset,
            0
        ])
        base_slot();
    }
}

module rod() {
    color([0.7,0.7,0.7])
    cylinder(
        $base_length,
        $rod_radius,
        $rod_radius
    );
}

module rods() {
    rotate([0,90,0]) {
        translate([-$rod_height1,$rod_offset,0]) rod();
        translate([-$rod_height1,$base_width-$rod_offset,0]) rod();
        translate([-$rod_height2,$rod_offset,0]) rod();
        translate([-$rod_height2,$base_width-$rod_offset,0]) rod();
    }
}

module side() {
    color([0,0.7,0])
    difference() {
        translate([
            -$side_thickness+$side_offset,
            -$side_extent,
            -$side_extent
        ]) {
            cube([
                $side_thickness,
                $base_width + 2 * $side_extent,
                $side_height + $side_extent
            ]);
            cube([
                $side_skirt,
                $base_width  + 2 * $side_extent,
                $base_depth  + 2 * $side_extent
            ]);
        }
        base_body();
        rods();
    }
}

module side_left() {
    side();
}

module side_right() {
    translate([
        $base_length,
        0,
        0
    ])
    mirror([1,0,0])
    side();
}

module block_face() {
    cube([
        $block_length,
        $base_width,
        $rod_offset * 2
    ]);
}

module block_down() {
    cube([
        $block_length,        
        $rod_offset * 2,
        $rod_height2 - $rod_height1 + 2 * $rod_offset,
    ]);
}

module block_pinion() {
    cube([
        ($base_length / 2) - 1,
        $rod_offset * 2,
        $rod_offset * 2
    ]);
}

module block() {
    color([0.3,0.3,1]) {
        translate([
            $base_depth+1,
            0,
            $side_height-$rod_offset*2
        ])
        block_face();
        translate([
            $base_depth+1,
            0,
            $side_height-($rod_height2 - $rod_height1 + 2 *    $rod_offset)
        ])
        block_down();
        translate([
            $base_depth+1,
            0,
            $side_height-($rod_height2 - $rod_height1 + 2 * $rod_offset)
        ])
        block_pinion();
    }
}

module blocks() {
    block();
    translate([$base_length,$base_width,0])
    mirror([0,1,0])
    mirror([1,0,0])
    block();
}

base();
side_left();
side_right();
rods();
blocks();