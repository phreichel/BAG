use <util.scad>;

r=25;
t=1.5;
n=8;
w=4;

points= [
    for(i=[0:n*360-1])
    for(j=[0:35])
    let (a = j*10)
    [
        (r + t * cos(a))*cos(i),
        (r + t * cos(a))*sin(i),
        w*(i/360)+t*sin(a)
    ]
];

faces = [
    for (i=[0:n*360-1])
        for (j=[0:35])
            [
                ((i+0)*36)+(j+0),
                ((i+0)*36)+(j+1),
                ((i+1)*36)+(j+1)
            ],
    for (i=[0:n*360-2])
        for (j=[0:35])
            [
                ((i+0)*36)+(j+0),
                ((i+1)*36)+(j+1),
                ((i+1)*36)+(j+0)
            ],
    [
        ((n*360-2)*36)+35,
        ((n*360-1)*36)+0,
        ((n*360-1)*36)+35
    ],
    for (j=[0:36]) [0,(j+1),j],
    let (ofs=360*36*n-36)
    for (j=[0:35]) [ofs,ofs+j,ofs+(j+1)]
];

//polyhedron(points, faces);
difference() {
    tz(-4) cyl(40,r);
    tz(-5) {
        difference() {
            cyl(42,2.5);
            t(-5,2,0) box(10,1,42);
        }
    }
    tz(-5) cylinder(15,3,.1);
    polyhedron(points, faces, 2);
    t(r, 0, 0) rx(90) cyl(50,t);
    t(r, 0, 0) ry(-90) cyl(10,t);
    t(r, 0, w*n) ry(-90) cyl(10,t);
    t(r, -10, w*n) rx(-90) cyl(50,t);
    for (i=[0:4]) {
        rz(-90+i*(360/5)) t(0,2.5+7+4.25,-20) cyl(60,7);
    }
}

