use <util.scad>;

module motorPart(w,d,h,r) {
	color([1,0,0]) {
		rounded_box(w,d,h,r);
	}
}

module receiverPart(w,d,h,r) {
	color([0,1,0]) {
		rounded_box(w,d,h,r);
	}
}

module harmonicGear(w,d,h,r) {
	t(-w/2,-d/2,0) {
		diffz() {
			union() {
				motorPart(w,d,h/2,r);
				tz(-h/2) receiverPart(w,d,h/2,r);
			}
			tz(-h/2) {
				t(0+r,0+r,0) cyl(h,2);
				t(w-r,0+r,0) cyl(h,2);
				t(0+r,d-r,0) cyl(h,2);
				t(w-r,d-r,0) cyl(h,2);
			}
			tz(3) {
				t(0+r,0+r,0) cyl(h/2-3,4);
				t(w-r,0+r,0) cyl(h/2-3,4);
				t(0+r,d-r,0) cyl(h/2-3,4);
				t(w-r,d-r,0) cyl(h/2-3,4);
			}
			tz(-h/2) {
				t(0+r,0+r,0) cyl(h/2-3,4);
				t(w-r,0+r,0) cyl(h/2-3,4);
				t(0+r,d-r,0) cyl(h/2-3,4);
				t(w-r,d-r,0) cyl(h/2-3,4);
			}
		}
	}
}

harmonicGear(60,60,40,6);