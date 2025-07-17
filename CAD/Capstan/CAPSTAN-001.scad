use <util.scad>

r=20;
n=8;
t=40;
cr=5;

difference() {
    union() {
        cyl(t,8);
        rz(45) ty(-4) box(r*n*.5,8,t);
        rz(-45) ty(-4) box(r*n*.5,8,t);
        ty(-4) box(r*n,8,t);
    }
    tz(-1) cyl(t+2,4);
}
difference() {
    hcyl(t,r*n,r*n-10);
    t(-r*n*1.3,-r*n,-1) box(r*n*2+2,r*n*2+2,t+2);
}
rz(45) tx(r*n*.75) hcyl(t,r*n*.25,r*n*.25-10);
rz(-45) tx(r*n*.75) hcyl(t,r*n*.25,r*n*.25-10);
tx(r*(n+1)+2*cr) cyl(t,r);
