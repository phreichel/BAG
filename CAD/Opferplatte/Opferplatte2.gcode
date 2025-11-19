( Pocket 100x70mm, depth -1.5mm, tool 3.175mm, stepdown 0.5mm )
G90
G21
G17
G94

( Sicherheitsposition )
G0 Z5.0

( Startpunkt links oben )
G0 X0 Y0

( -------------------- Z-PASS 1: -0.5 mm -------------------- )
G1 Z-0.5 F100

( Rasterbahnen )
M3

( Bahn 1 )
G1 X100 F300
G1 Y1.27
G1 X0

( Bahn 2 )
G1 Y2.54
G1 X100

( Bahn 3 )
G1 Y3.81
G1 X0

( Bahn 4 )
G1 Y5.08
G1 X100

( Bahn 5 )
G1 Y6.35
G1 X0

( … automatisch fortgesetzt … )

( bis Y70 )

G1 Y70
G1 X100

( -------------------- Z-PASS 2: -1.0 mm -------------------- )
G0 Z5
G0 X0 Y0
G1 Z-1.0 F100

( Rasterbahnen )
G1 X100 F300
G1 Y1.27
G1 X0
G1 Y2.54
G1 X100
G1 Y3.81
G1 X0
G1 Y5.08
G1 X100
G1 Y6.35
G1 X0
( … wieder bis Y70 … )
G1 Y70
G1 X100

( -------------------- Z-PASS 3: -1.5 mm -------------------- )
G0 Z5
G0 X0 Y0
G1 Z-1.5 F100

( Rasterbahnen )
G1 X100 F300
G1 Y1.27
G1 X0
G1 Y2.54
G1 X100
G1 Y3.81
G1 X0
G1 Y5.08
G1 X100
G1 Y6.35
G1 X0
( … bis Y70 … )
G1 Y70
G1 X100

( -------------------- Ende -------------------- )
G0 Z5
M5
G0 X0 Y0
M2
