Type Respawn ; PlayerLogic
	Field x,y,z
	Field Mesh
End Type

;----------------------------------------------------------------
;! Constants
;----------------------------------------------------------------

Const CAMERA_NEAR#	= 1.0
Const CAMERA_FAR#	= Float(2 Shl 21) ; 23 bits is maximum representable depth value.

;----------------------------------------------------------------
;! Types
;----------------------------------------------------------------

;[Block] Old

Type EnvDust
	Field x#,y#,z#,Rad,Tex,DustHan
	Field FadeN1,FadeN2,FadeF1,FadeF2
	Field Scale1, Scale2, FX, Blend
	Field R1,G1,B1,R2,G2,B2,Alpha1,Alpha2
End Type

Type Belt
	Field x#,y#,z#, Pit#, rol#, yaw#
	Field Mesh, FXMesh, Chance, Maxyield
	Field tur#,rot#,siz#,minsiz#,toy, Model_State
	Field yamount, homepiv, momentum#, spin#, touchedtrg, touchedcnt, retrievecnt, retrievetrg
End Type

Type beltorigin
	Field x,y,z
	Field mesh
End Type

Type SpcObj
	Field mesh
	Field fxmesh
	Field x,y,z
End Type

Type Spotmark
	Field X#,y#,z#, tpar, showdist
	Field mesh, TOfSpot, ruby
End Type

Type ChatMSGS
	Field msg$, id, age
	Field ColorR,ColorG,ColorB
End Type

Type WalletLog
	Field amount$, Msg$, timestamp$, ID, SubType
End Type

Type Player_Bullet
	Field mesh, Pivot
	Field speed
	Field scale#, distance
	Field dmg,guntype,decay,viable, btyp, SID, OBB
	
End Type

Type hardpoint
	Field plusX,plusy,plusz
	Field pivot
End Type

Type Planet
	Field X,Y,Z
	Field Scale, ProPiv, SphereA, SphereB, SpriteA
End Type

Type MainZone
	Field x,y,z
	Field range,name$,pha
End Type

Type Profiles
	Field Name$, Pass$
End Type

Type SubZone
	Field x,y,z
	Field range,name$,pha
End Type

Type NebZone
	Field x,y,z
	Field trange,vrange,faderange
	Field colorr,colorg,colorb
	Field tpiv
End Type

Type Info
	Field txt$
End Type

Global GlobalSID=9

Type RZone
	Field x,y,z, rate
	Field mesh, insmesh
	Field size
	Field P1#,  P2#,  P3#,  P4#,  P5#, P6#,  P7#
	Field StationID
End Type

Type SBubble
	Field pivot, sphere
	Field durat#, fade#
End Type

Type Station
	Field Mesh_Essential, Mesh_Power, Mesh_Factory, Mesh_Ring, stt, x, y, z
	Field enablehull, hullx, hully, hullz, hrange
	Field enableshield, shieldx, shieldy,shieldz, srange
End Type

Type Shockwave
	Field x,y,z
	Field mesh,scale,age, alpha#, Speed, level
End Type

Type trailpivot
	Field parent, srcid, x, y, z, sprite
End Type

Type SafeZone
	Field mesh, range
End Type

Type DropZone
	Field mesh, size, maxsize, typ, timer, maxtimer
End Type

Type DebrisBelt
	Field Mesh, x,y,z
End Type

Type ExplosionLight
	Field X,Y,Z
	Field R,G,B
	Field Range
End Type

;[End Block]

Type GFXModes
	Field GFXM_Width, GFXM_Height
	Field IndexNR
End Type

.dustobject ; starfield.bb
Type DST_Dustobject
	Field pivot  ; Stammobjekt f�r die Partikel und Dustzonen
	Field campivot  ; Steuerobjekt f�r die Partikelplazierung
	Field camera ; zugewiesenes Cameraobjekt
	Field active ; Dust-Objekt aktiv/inaktiv
	Field dense# ; wird �ber dst_checkzones() ermittelt
	Field dense_akt# ;dichtesteuerung bei eintritt od.verlassen der zonen
	Field count  ; Anzahl der Partikel
	Field version   ; Art der Partikel (0=2 oder 1=4 Triangles )
	Field count_zone  ; zahl der vergebenen Dustzonen
	Field sharecount ; zahl der sich �berschneidenden Zonen an Camera-pos.
	Field oldcount    ; vorher. zahl der sich �berschneidenden Zonen an Camera-pos.
	Field zones[dst_sharesize]
	Field dist_akt#  ; positionsdifferenz zw. camera und campivot seit letztem durchlauf
	Field part_count ; zahl der momentan aktiven partikel
End Type
.dustzone
Type DST_Dustzone
	Field pivot  ; Referenzobjekt f�r Entfernungsmessung
	Field dust_handle    ; Typehandle der Dustzone
	Field priority ; Priorit�t der Zone
	Field brush   ; Brush mit allen eigenschaften f�r die Partikel
	Field radius# ; Gr�sse der Dustzone ( Global )
	Field active ; Dust-Zone aktiv/inaktiv
	Field dense#  ; Dichte der Partikel (part./entf.)
	Field farfade_min#  ; min-Entfern. f�r Autofade
	Field farfade_max#  ; max-Entfern. f�r Autofade
	Field clipradius#   ; Entfernung ab der Partikel entfernt bzw. eingesetzt werden
	Field nearfade_min#  ; min-Entfern. f�r Nearfade
	Field nearfade_max#  ; max-Entfern. f�r Nearfade
	Field texture  ; texturhandle f�r Partikel
	Field file$    ; texturdatei (ohne Pfad)
	Field flag     ; texturflags f�r textur
	Field texblend ; textur-Blendmode
	Field Tex_scale_x# ; Scalefaktor f�r textur
	Field Tex_scale_y# ; Scalefaktor f�r textur
	Field size_min#  ; Partikel-Gr�sse min
	Field size_max#  ; Partikel-Gr�sse max
	Field alpha_min#  ; Partikel-Alpha min
	Field alpha_max#  ; Partikel-Alpha max
	Field blendmode ; Partikel blendmode
	Field part_flag ; Entityflags f�r partikel
	Field r1,g1,b1  ; Particle-Color-Range 
	Field r2,g2,b2  ;    - " -					  
	Field dyn_alpha ; dynamisches Alphablending 1/0
	Field speedblur# ;faktor f�r Verzerrung der partikel durch hohe geschwindigkeit
	Field angle_min#,angle_max# ; Z-Winkel-bereich
	Field rot_min#,rot_max# ; Rotationsgeschwindigkeitsbereich
End Type
.dust_partikel
Type DST_Particle
	Field part ; Partikel Handle
	Field partpivot ; Partikel-pivot Handle
	Field dust_handle ; Typehandle des �bergeordeten Dust-objekts
	Field zone_handle   ; Typehandle der Dustzone, zu der das Partikel zur Zeit geh�rt
	Field active     ; Partikel in Gebrauch 1/0
	Field dist_old# ; bisherige Distanz zur Camera
	Field alpha# ; Alpha-Wert
	Field alphaflag ; gibt an ob der alpha-wert durch Ann�herung ge�ndert wird oder durch einblenden/ausblenden nach plazieren
	Field alphaakt# ; akt. errechneter Alpha-faktor f�r alphaflag=1
	Field alphadest#  ; gibt den ziel alpha-faktor an, der erreicht werden soll, wenn alphaflag auf 1 steht
	Field rot#    ; Rotationsgeschwindigkeit
	Field angle#  ; akt. Z-Winkel
	Field startangle#  ; anfangs-Z-Winkel
	Field old_x#,old_y#,old_z#  ;vorherige pos. des partikels
	Field old_pi#,old_ya#,old_ro#  ;vorherige ausricht. des partikels
End Type

Type PCL_Cloud ;Starfield.bb
	Field pivot 				; cloud-center
	Field light 				; light-entity. must Not be a light
	Field camera 				; camera for nearfade and to face for
	Field r_amb#,g_amb#,b_amb#	; Ambient-Light Color (50)
	Field auto					; automatic Light/Shadow Calc. enabled/disabled (0)
	Field akt_part				; internal Var. for automatic Lightning 
	Field anz_part 			; Amount of Particles
	Field point	 			; particle facing camera on/off
	Field random	 			; particle facing camera with random z-angle
	Field speed#	 			; speed to change colors  (.5/sec.)
	Field renderstep			; stepwitdh to darken a particle during UpdateCloud() ( associates with particle-alpha) (10)
	Field groups				; amount of groups for this cloud (1)
	Field part1[pcl_count]		; particle-handle
	Field part2[pcl_count]		; r \
	Field part3[pcl_count]		; g  > Particle-Color
	Field part4[pcl_count]		; b /
	Field part5#[pcl_count]	; Particle-size (1)
	Field part6#[pcl_count]	; radial distance from center ( for later pos.offset operations)
	Field part7#[pcl_count]	; old shadow-value (1)
	Field part8#[pcl_count]	; new shadow value for smooth adjust (1)
	Field part9#[pcl_count]	; particle-alpha (1)
	Field part10#[pcl_count]	; random range-index
	Field part11[pcl_count]	; group-number
	Field part12[pcl_count]	; nearfading on/off
	Field part_n#[pcl_count]	; nearfading near
	Field part_f#[pcl_count]	; nearfading far
	Field partx#[pcl_count]	; x-pos.
	Field party#[pcl_count]	; y-pos.
	Field partz#[pcl_count]	; z-pos.
End Type

Type pcl_groupbrush
	Field pivot
	Field number
	Field near
	Field near_n#
	Field near_f#
	Field autofade
	Field far_n#
	Field far_f#
	Field size_min#,size_max#
	Field size_mode
	Field pos_min#,pos_max#
	Field rot_x,rot_y,rot_z,rand_x,rand_y,rand_z
	Field alpha_min#,alpha_max#
	Field shini_min#,shini_max#
	Field r1,g1,b1 			; Particle-Color-Range (100)
	Field r2,g2,b2 			;    - II -			  (255)
	Field blend				; Particle Blendmode Alpha/Additiv (0)
	Field fx					; particle Fx 
	Field p_type	 			; Entitytype
	Field pick		 			; Pickmode
	Field obscure	 			; obscurer for pickmode
	Field texture$ [7]			; particle-texture-file
	Field texflag [7]			; particle-texture-flag
	Field texmode [7]			; particle-texture-blendmode
	Field texscx# [7]			; particle-texture-scale-x
	Field texscy# [7]			; particle-texture-scale-y
	Field texposx# [7]			; particle-texture-pos-x
	Field texposy# [7]			; particle-texture-pos-y
	Field texrot# [7]			; particle-texture-rotation
End Type

Type VirtualScene ; Utilities.bb
End Type

Type VirtualSceneEntity
	Field VS.VirtualScene
	Field Entity%, State%
End Type

Type Location ; Coordinates.bb
	Field X,Y,Z
End Type

Type TPlanet ; WorldGenerator.bb
	Field Pivot, Mesh, Ring
	Field Size#, tilt
	Field z_Entity%, Ring_Exist
End Type

Type PRing
	Field X,Y,Z
	Field Tilt
	Field Alignmesh
	Field RingSprite
End Type

Type Explosion
	Field x,y,z,subtype, Sprite, Stage, StageTimer
End Type
;~IDEal Editor Parameters:
;~C#Blitz3D