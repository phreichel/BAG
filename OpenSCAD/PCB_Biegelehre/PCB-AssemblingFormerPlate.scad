// Bestückungshilfe für Elektronikbauteile
// Platinen Bestückungs Biege Tool
// PCB-Assembling Forming Tool
// STEVE6330 > 12.10.2013
use <MCAD/bitmap/bitmap.scad>

difference(){
 BasisKoerper(); 
 translate([0,0,8.1])Ausnehmung(); 
 //Sign - Bottom
 translate([65,0,6]) rotate([0,180,90]) Signier();
 //Rillen
  for(i = [4,8,12,16,20,24,28, 
           36,40,44,48,52,56,60,64,
           72,76,80,84,88,92,96,100,
           106,110,114,118, 125,130])
  {
  translate([i,-15,10.5]) rotate([0,0,90]) cube([30,1,4]);
  }
}

// Basis Körper
 module  BasisKoerper(){ 
  color  ([1,0.2,0.2]){ 
  linear_extrude( height = 12, center=false, 
               convexity = 10, twist = 0,$fn = 20)
  polygon(points = [
  [0.00,0.00],
  [0.00,8.75],
  [32.00,8.75],
  [33.00,7.30],
  [67.00,7.30],
  [68.00,6.30],
  [102.00,6.30],
  [103.00,4.75],
  [120.00,4.75],
  [121.00,3.70],
  [133.00,3.70],
  [133.00,-3.70],
  [121.00,-3.75],
  [120.00,-4.75],
  [103.00,-4.75],
  [102.00,-6.30],
  [68.00,-6.30],
  [67.00,-7.30],
  [33.00,-7.30],
  [32.00,-8.75],
  [0.00,-8.75]
  ]
 ,paths = 
 [[0,1,2,3,4,5,6,7,8,9,10,11,
   12,13,14,15,16,17,18,19,20]]);
 }
}

// AUSNEHMUNG (Vertiefung)
 module  Ausnehmung() 
  { 
  color  ([0.4,0.4,0.8]) 
  { 
  linear_extrude( height = 4, center=false,
         convexity = 10, twist = 0,$fn = 10)
  polygon(points = [
  [0.00,0.00],
  [0.00,4.50],
  [31.00,7.00],
  [33.00,3.00],
  [66.00,6.00],
  [68.00,5.00],
  [101.00,3.50],
  [102.00,2.50],
  [119.00,3.50],
  [121.00,2.00],
  [133.00,2.00],
  [133.00,-2.00],
  [121.00,-2.00],
  [119.00,-3.50],
  [102.00,-2.50],
  [101.00,-3.50],
  [68.00,-5.00],
  [66.00,-6.00],
  [33.00,-3.00],
  [31.00,-7.00],
  [0.00,-4.50],
  [0.00,0.00]
  ]
 ,paths = 
 [[0,1,2,3,4,5,6,7,8,9,10,11,
   12,13,14,15,16,17,18,19,20]]);
 }
}


 module Signier(){

 //change chars array and char_count
 //OpenSCAD has no string or length methods :(
   chars = ["S","T","S","-","2","0","1","3"];
   char_count = 8;
 //block size 1 will result in 8mm per letter
   block_size = 1;
 //height is the Z height of each letter
   height = 4;
  union() {
	translate(v = [0,-block_size*8*char_count/2
             +block_size*8/2,3]){
	  8bit_str(chars, char_count, block_size, height);
	}
  }
 }

