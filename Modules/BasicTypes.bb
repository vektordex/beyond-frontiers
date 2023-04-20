Type Respawn ; PlayerLogic
	Field x,y,z
	Field Mesh
End Type


.dustobject ; starfield.bb
Type DST_Dustobject
	Field pivot  ; Stammobjekt für die Partikel und Dustzonen
	Field campivot  ; Steuerobjekt für die Partikelplazierung
	Field camera ; zugewiesenes Cameraobjekt
	Field active ; Dust-Objekt aktiv/inaktiv
	Field dense# ; wird über dst_checkzones() ermittelt
	Field dense_akt# ;dichtesteuerung bei eintritt od.verlassen der zonen
	Field count  ; Anzahl der Partikel
	Field version   ; Art der Partikel (0=2 oder 1=4 Triangles )
	Field count_zone  ; zahl der vergebenen Dustzonen
	Field sharecount ; zahl der sich überschneidenden Zonen an Camera-pos.
	Field oldcount    ; vorher. zahl der sich überschneidenden Zonen an Camera-pos.
	Field zones[dst_sharesize]
	Field dist_akt#  ; positionsdifferenz zw. camera und campivot seit letztem durchlauf
	Field part_count ; zahl der momentan aktiven partikel
End Type
.dustzone
Type DST_Dustzone
	Field pivot  ; Referenzobjekt für Entfernungsmessung
	Field dust_handle    ; Typehandle der Dustzone
	Field priority ; Priorität der Zone
	Field brush   ; Brush mit allen eigenschaften für die Partikel
	Field radius# ; Grösse der Dustzone ( Global )
	Field active ; Dust-Zone aktiv/inaktiv
	Field dense#  ; Dichte der Partikel (part./entf.)
	Field farfade_min#  ; min-Entfern. für Autofade
	Field farfade_max#  ; max-Entfern. für Autofade
	Field clipradius#   ; Entfernung ab der Partikel entfernt bzw. eingesetzt werden
	Field nearfade_min#  ; min-Entfern. für Nearfade
	Field nearfade_max#  ; max-Entfern. für Nearfade
	Field texture  ; texturhandle für Partikel
	Field file$    ; texturdatei (ohne Pfad)
	Field flag     ; texturflags für textur
	Field texblend ; textur-Blendmode
	Field Tex_scale_x# ; Scalefaktor für textur
	Field Tex_scale_y# ; Scalefaktor für textur
	Field size_min#  ; Partikel-Grösse min
	Field size_max#  ; Partikel-Grösse max
	Field alpha_min#  ; Partikel-Alpha min
	Field alpha_max#  ; Partikel-Alpha max
	Field blendmode ; Partikel blendmode
	Field part_flag ; Entityflags für partikel
	Field r1,g1,b1  ; Particle-Color-Range 
	Field r2,g2,b2  ;    - " -					  
	Field dyn_alpha ; dynamisches Alphablending 1/0
	Field speedblur# ;faktor für Verzerrung der partikel durch hohe geschwindigkeit
	Field angle_min#,angle_max# ; Z-Winkel-bereich
	Field rot_min#,rot_max# ; Rotationsgeschwindigkeitsbereich
End Type
.dust_partikel
Type DST_Particle
	Field part ; Partikel Handle
	Field partpivot ; Partikel-pivot Handle
	Field dust_handle ; Typehandle des übergeordeten Dust-objekts
	Field zone_handle   ; Typehandle der Dustzone, zu der das Partikel zur Zeit gehört
	Field active     ; Partikel in Gebrauch 1/0
	Field dist_old# ; bisherige Distanz zur Camera
	Field alpha# ; Alpha-Wert
	Field alphaflag ; gibt an ob der alpha-wert durch Annäherung geändert wird oder durch einblenden/ausblenden nach plazieren
	Field alphaakt# ; akt. errechneter Alpha-faktor für alphaflag=1
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