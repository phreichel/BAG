use <util.scad>;

R=50;
N=10;
e=2.5;
r=R/N;

function cyclo(R,N,e) = [
	let(r=R/N)
	let(A=360/(N-1))	
	for (i=[0:N-2])
	let(a=i*A)
	for (j=[0:360-1])
	let(b=j*A/360)
	let(x=cos(a+b)*(R-r)+cos(j)*e)
	let(y=sin(a+b)*(R-r)-sin(j)*e)
	[x,y]
];

color("#ffff00")
circle(R+e);

rz(-$t*360/N)
for (i=[0:N])  {
	tz(2)
	rz(i*(360/N))
	tx(R-e)
	color("#ff808080")
	circle(e);		
}
rz($t*360) {
tx(e) {
rz(-$t*360) {
	color("#c0c0ff")
	circle(R);
	tz(.5)
	color("#000080")
	polygon(cyclo(R,N,R/N));
	tz(1)
	color("#00ff00c0")
	polygon(cyclo(R,N,e));
	rz($t*360)
	tz(2)
	tx(R-r) {
		rz(-$t*360*N) {
			color("#ff808080")
			circle(r);	
			tx(e)
			color("#ff0000")
			circle(.5);
		}	
	}
}}}