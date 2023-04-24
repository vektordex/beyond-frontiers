; DST_Dust-Engine V1.0



Function DST_Create_Dust(dst_cam, dst_count, dst_typ=0, Parent=0)
	If DST_TempPivot=0 Then DST_TempPivot=CreatePivot(Parent)
;	VirtualScene_Register(Scene, DST_TempPivot)
	dst_dust.DST_Dustobject = New DST_Dustobject
	dst_dust\camera=dst_cam
	dst_dust\pivot=CreatePivot(Parent)
;	VirtualScene_Register(Scene, dst_dust\pivot)
	dst_dust\campivot=CreatePivot(dst_dust\camera)
; PositionEntity dst_dust\campivot,0,0,0 : RotateEntity dst_dust\campivot,0,0,0
	EntityParent dst_dust\campivot,dst_dust\pivot
	dst_dust\version=dst_typ
	dst_dust\count=dst_count
	dst_dust\active=1
	dst_x#=EntityX#(dst_dust\camera,1) : dst_y#=EntityY#(dst_dust\camera,1) : dst_z#=EntityZ#(dst_dust\camera,1)
	dst_dust\dense#=1 : dst_dust\dense_akt#=0
	For dst_a=1 To dst_count
		dst_part.DST_Particle = New DST_Particle
		dst_part\partpivot=CreatePivot(Parent)
		dst_part\dust_handle=Handle(dst_dust.DST_Dustobject)
		If dst_dust\version=0 Then dst_part\part=dst_createflat1(dst_part\partpivot) Else dst_part\part=dst_createflat2(dst_part\partpivot)
		PositionEntity dst_part\partpivot,Rand(-100,100)+dst_x#,Rand(-100,100)+dst_y#,Rand(-100,100)+dst_z#
		EntityAutoFade dst_part\part,80,100
		dst_part\alpha#=1.0 : dst_part\alphaakt#=1.0
		dst_part\dist_old = EntityDistance(dst_part\part,dst_dust\camera)
		HideEntity dst_part\partpivot
	Next
	Return dst_dust\pivot
End Function

Function DST_Create_Dustzone(dst_dust_handle,dst_clamp=0)
	dst_zone.DST_Dustzone = New DST_Dustzone
	dst_zone\pivot=CreatePivot()
;	VirtualScene_Register(Scene, dst_zone\pivot)
	If dst_clamp>0 Then EntityParent dst_zone\pivot,dst_dust_handle
	dst_zone\brush=CreateBrush()
	For dst_dust.DST_Dustobject = Each DST_Dustobject
		If dst_dust\pivot=dst_dust_handle
			dst_zone\dust_handle=Handle(dst_dust.DST_Dustobject)
			Exit
		EndIf
	Next
	dst_zone\size_min#=1 : dst_zone\size_max#=1
	dst_zone\alpha_min#=1 : dst_zone\alpha_max#=1
	dst_zone\r1=255 : dst_zone\g1=255 : dst_zone\b1=255
	dst_zone\r2=255 : dst_zone\g2=255 : dst_zone\b2=255
	dst_zone\active=1 : dst_zone\dense#=1
	dst_zone\farfade_min#=80 : dst_zone\farfade_max#=100
	dst_zone\clipradius#=(dst_zone\farfade_max#+dst_zone\size_max#)
	Return dst_zone\pivot
End Function

Function DST_Free_Dust(dst_dust)
	For dst_dust2.DST_Dustobject = Each DST_Dustobject
		If dst_dust2\pivot=dst_dust
			For dst_part.DST_Particle = Each DST_Particle
				If dst_part\dust_handle=Handle (dst_dust2.DST_Dustobject)
					FreeEntity dst_part\partpivot : Delete dst_part
;					VirtualScene_Unregister(Scene, dst_part\partpivot)
				EndIf
			Next
			For dst_zone.DST_Dustzone = Each DST_Dustzone
				If dst_zone\dust_handle=Handle (dst_dust2.DST_Dustobject)
					FreeEntity dst_zone\pivot : FreeTexture dst_zone\texture : FreeBrush dst_zone\brush
;					VirtualScene_Unregister(Scene, dst_zone\pivot)
					Delete dst_zone
				EndIf
			Next
			FreeEntity dst_dust2\pivot : Delete dst_dust2
;			VirtualScene_Unregister(Scene, dst_dust2\pivot)
			Exit
		EndIf
	Next
	If First DST_Dustobject=Null And DST_TempPivot<>0 Then FreeEntity DST_TempPivot : DST_TempPivot=0 
;	VirtualScene_Unregister(Scene, DST_TempPivot)
End Function

Function DST_Free_DustZone(dst_zone)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
;			VirtualScene_Unregister(Scene, dst_zone2\pivot)
			FreeEntity dst_zone2\pivot : FreeTexture dst_zone2\texture : FreeBrush dst_zone2\brush
			Delete dst_zone2 : Exit
		EndIf
	Next
	DST_CheckZones()
End Function

Function DST_SetTimer()
	DST_TIMER=MilliSecs()
End Function

Function DST_Set_Intervall(dst_val)
	DST_CHECKTIMER=dst_val
End Function

Function DST_Set_ZoneRadius(dst_zone,dst_radius#)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			dst_zone2\radius#=dst_radius#
			Exit
		EndIf
	Next
	DST_CheckZones()
End Function

Function DST_Change_Camera(dst_dust,dst_camera)
	For dst_dust2.DST_Dustobject = Each DST_Dustobject
		If dst_dust2\pivot=dst_dust
			dst_dust2\camera=dst_camera
			EntityParent dst_dust2\campivot,dst_dust2\camera
			PositionEntity dst_dust2\campivot,0,0,0
			Exit
		EndIf
	Next
	DST_CheckZones()
End Function


Function DST_Set_DustStatus(dst_dust,dst_flag)
	For dst_dust2.DST_Dustobject = Each DST_Dustobject
		If dst_dust2\pivot=dst_dust
			If dst_flag>=1 Then dst_flag=1
			If dst_flag<1 Then dst_flag=0
			dst_dust2\active=dst_flag
			Exit
		EndIf
	Next
	DST_CheckZones()
End Function

Function DST_Set_DustZoneStatus(dst_zone,dst_flag)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			If dst_flag>=1 Then dst_flag=1
			If dst_flag<1 Then dst_flag=0
			dst_zone2\active=dst_flag
			Exit
		EndIf
	Next
	DST_CheckZones()
End Function

Function DST_Set_DustZonePriority(dst_zone,dst_flag)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			dst_zone2\priority=dst_flag
			Exit
		EndIf
	Next
	DST_CheckZones()
End Function

Function DST_Set_Dense(dst_zone,dst_val#)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			dst_zone2\dense#=dst_val#
			Exit
		EndIf
	Next
End Function


Function DST_Set_texture(dst_zone,dst_file$,dst_flag)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
;			dst_zone2\texture=Text_Effects[9]
			BrushTexture dst_zone2\brush,dst_zone2\texture
		EndIf
	Next
End Function

Function DST_Set_textureBlend(dst_zone,dst_blend)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			TextureBlend dst_zone2\texture,dst_blend
			dst_zone2\texblend=dst_blend
			Exit
		EndIf
	Next
End Function

Function DST_Set_textureScale(dst_zone,dst_scx#,dst_scy#)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			ScaleTexture dst_zone2\texture,dst_scx#,dst_scy#
			dst_zone2\tex_scale_x#=dst_scx#
			dst_zone2\tex_scale_y#=dst_scy#
			Exit
		EndIf
	Next
End Function

Function DST_Set_ColorRange(dst_zone,dst_r1,dst_g1,dst_b1,dst_r2,dst_g2,dst_b2)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			dst_zone2\r1=dst_r1 : dst_zone2\g1=dst_g1 : dst_zone2\b1=dst_b1
			dst_zone2\r2=dst_r2 : dst_zone2\g2=dst_g2 : dst_zone2\b2=dst_b2
			Exit
		EndIf
	Next
End Function

Function DST_Set_ScaleRange(dst_zone,dst_sc1#,dst_sc2#)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			dst_zone2\size_min#=dst_sc1# : dst_zone2\size_max#=dst_sc2#
			Exit
		EndIf
	Next
End Function

Function DST_Set_AlphaRange(dst_zone,dst_a1#,dst_a2#)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			dst_zone2\alpha_min#=dst_a1# : dst_zone2\alpha_max#=dst_a2#
			Exit
		EndIf
	Next
End Function

Function DST_Set_FadingFar(dst_zone,dst_near#,dst_far#)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			If dst_far#<dst_near#
				dst_zone2\farfade_max#=dst_near# : dst_zone2\farfade_min#=dst_far#
			Else
				dst_zone2\farfade_min#=dst_near# : dst_zone2\farfade_max#=dst_far#
			EndIf
			dst_zone2\clipradius#=(dst_zone2\farfade_max#+dst_zone2\size_max#)
			Exit
		EndIf
	Next
End Function

Function DST_Set_FadingNear(dst_zone,dst_near#,dst_far#)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			If dst_far#<dst_near#
				dst_zone2\nearfade_max#=dst_near# : dst_zone2\nearfade_min#=dst_far#
			Else
				dst_zone2\nearfade_min#=dst_near# : dst_zone2\nearfade_max#=dst_far#
			EndIf
			Exit
		EndIf
	Next
End Function

Function DST_Set_FX(dst_zone,dst_flag)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			dst_zone2\part_flag=dst_flag
			BrushFX dst_zone2\brush,dst_flag
			Exit
		EndIf
	Next
End Function


Function DST_Set_Blend(dst_zone,dst_blend)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			dst_zone2\blendmode=dst_blend
			BrushBlend dst_zone2\brush,dst_blend
			Exit
		EndIf
	Next
End Function

Function DST_Set_SpeedBlur(dst_zone,dst_factor#)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			dst_zone2\speedblur#=dst_factor#
			Exit
		EndIf
	Next
End Function

Function DST_Set_Rotation(dst_zone,dst_w1#,dst_w2#)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			dst_zone2\rot_min#=dst_w1# : dst_zone2\rot_max#=dst_w2#
			Exit
		EndIf
	Next
End Function

Function DST_Set_Angle(dst_zone,dst_w1#,dst_w2#)
	For dst_zone2.DST_Dustzone = Each DST_Dustzone
		If dst_zone2\pivot=dst_zone
			dst_zone2\angle_min#=dst_w1# : dst_zone2\angle_max#=dst_w2#
			Exit
		EndIf
	Next
End Function

Function DST_CheckZones()
	For dst_dust.DST_Dustobject = Each DST_Dustobject
		If dst_dust\active=1
			dst_dust\zones[0]=0 : dst_dust\oldcount=dst_dust\sharecount : dst_dust\sharecount=0
			dst_dust\dense#=0
		EndIf
	Next
	For dst_zone.DST_Dustzone = Each DST_Dustzone
		If dst_zone\active=1
			dst_dust.DST_Dustobject=Object.DST_Dustobject(dst_zone\dust_handle)
			If dst_zone\radius > EntityDistance(dst_dust\camera,dst_zone\pivot)
				If dst_zone\priority=dst_dust\zones[0]
					If dst_zone\dense#>dst_dust\dense# Then dst_dust\dense#=dst_zone\dense#
					dst_dust\sharecount=dst_dust\sharecount+1
					dst_dust\zones[dst_dust\sharecount]=Handle(dst_zone.DST_Dustzone)
				Else
					If dst_zone\priority>dst_dust\zones[0]
						dst_dust\dense#=dst_zone\dense#
						dst_dust\sharecount=1 : dst_dust\zones[0]=dst_zone\priority
						dst_dust\zones[dst_dust\sharecount]=Handle(dst_zone.DST_Dustzone)
					EndIf
				EndIf
			EndIf
		EndIf
	Next
End Function

Function DST_Update(dst_looptime#=0)
	dst_t=MilliSecs()-DST_TIMER
	DST_TIMER=MilliSecs()
	If dst_looptime#=0
		dst_looptime#=dst_t/1000.0
	EndIf
	
	If DST_CHECKTIMER>-1
		dst_check=dst_check+dst_t
		If dst_check>DST_CHECKTIMER Then dst_check=0 : DST_CheckZones()
	EndIf
	
;dustobjekte vorbereiten
	For dst_dust.DST_Dustobject = Each DST_Dustobject
		dst_dust\dist_akt#=EntityDistance(dst_dust\camera,dst_dust\campivot)
		PointEntity dst_dust\campivot,dst_dust\camera
		EntityParent dst_dust\campivot,dst_dust\camera
		PositionEntity dst_dust\campivot,0,0,0
		EntityParent dst_dust\campivot,dst_dust\pivot
		dst_dust\part_count=0
		If dst_dust\sharecount>0 And dst_dust\dense_akt#<1
			dst_dust\dense_akt#=dst_dust\dense_akt#+0.5*dst_looptime#
			If dst_dust\dense_akt#>1 Then dst_dust\dense_akt#=1
		EndIf
		If dst_dust\sharecount=0 And dst_dust\dense_akt#>0
			dst_dust\dense_akt#=dst_dust\dense_akt#-0.5*dst_looptime#
			If dst_dust\dense_akt#<0 Then dst_dust\dense_akt#=0
		EndIf
	Next
	
;partikel bearbeiten
	For dst_part.DST_Particle = Each DST_Particle
		dst_dust.DST_Dustobject=Object.DST_Dustobject(dst_part\dust_handle)
		dst_dist#=EntityDistance (dst_dust\camera,dst_part\part)
		
		If dst_part\active=1
			dst_dust\part_count=dst_dust\part_count+1
			dst_zone.DST_Dustzone=Object.DST_Dustzone(dst_part\zone_handle)
			If dst_tmp<>dst_part\zone_handle
				dst_ok=0
				For dst_a=1 To dst_dust\sharecount
					If dst_part\zone_handle=dst_dust\zones[dst_a] Then dst_ok=1 : Exit
				Next
			EndIf
			dst_tmp=dst_part\zone_handle
  ;aktive partikel ausblenden falls objekt oder zone inaktiv wurde
			If dst_ok=0 And dst_part\alphaflag=0
				dst_part\alphaflag=1 : dst_part\alphadest#=0 : dst_part\alphaakt#=1
			EndIf
			If dst_part\alphaflag=1
				If dst_part\alphaakt#>dst_part\alphadest#
					dst_part\alphaakt#=dst_part\alphaakt#-0.5*dst_looptime#
					If dst_part\alphaakt#<=dst_part\alphadest#
						dst_part\alphaakt#=0 : dst_part\alphaflag=0 : dst_part\active=0 : HideEntity dst_part\partpivot
						dst_dust\part_count=dst_dust\part_count-1 : ScaleEntity dst_part\partpivot,1,1,1
					EndIf
				Else
					dst_part\alphaakt#=dst_part\alphaakt#+0.5*dst_looptime#
					If dst_part\alphaakt#>=dst_part\alphadest# Then dst_part\alphaflag=0
				EndIf
				EntityAlpha dst_part\part,dst_part\alpha#*dst_part\alphaakt#
			EndIf
			
  ;nearfading für aktive partikel
			If dst_dist#<dst_zone\farfade_max#
				If dst_dist#>dst_zone\nearfade_min# And dst_dist#<dst_zone\nearfade_max#
					dst_alpha#=(dst_dist#-dst_zone\nearfade_min#)/(dst_zone\nearfade_max#-dst_zone\nearfade_min#)*dst_part\alpha#*dst_part\alphaakt#
					EntityAlpha dst_part\part,dst_alpha#
				Else
					If dst_dist#<dst_zone\nearfade_min# Then EntityAlpha dst_part\part,0 
					If dst_dist#>dst_zone\nearfade_max# Then EntityAlpha dst_part\part,dst_part\alpha#*dst_part\alphaakt# 
				EndIf
			EndIf
			If dst_dist#>dst_zone\clipradius# And dst_part\dist_old#<dst_dist#
				dst_part\active=0 : HideEntity dst_part\partpivot : ScaleEntity dst_part\partpivot,1,1,1
				dst_dust\part_count=dst_dust\part_count-1
			EndIf
  ;speedbluring und pointing
			If dst_zone\speedblur#>0 And dst_part\active=1
				EntityParent dst_part\partpivot,dst_dust\campivot
				RotateEntity dst_part\partpivot,0,0,0
				EntityParent dst_part\partpivot,dst_dust\pivot
				dst_blur#=(dst_dust\dist_akt#/(dst_zone\speedblur#*dst_looptime#))*2
				If dst_blur#<1 Then dst_blur#=1
				ScaleEntity dst_part\partpivot,1,1,dst_blur#
			EndIf
			dst_part\angle#=dst_part\angle#+dst_part\rot#*dst_looptime#
			If dst_part\angle#>360 Then dst_part\angle#=dst_part\angle#-360
			PointEntity dst_part\part,dst_dust\camera,dst_part\startangle#+dst_part\angle#
			dst_part\dist_old#=dst_dist#
		EndIf
 ;partikel einsetzen
		If dst_part\active=0
			If dst_dust\active=1
				If dst_dust\sharecount>0
					If dst_dust\part_count<(dst_dust\count*dst_dust\dense#*dst_dust\dense_akt#)
						dst_part\active=1
						dst_dust\part_count=dst_dust\part_count+1
						EntityParent dst_part\partpivot,dst_dust\campivot
						dst_part\zone_handle=dst_dust\zones[Rand(1,dst_dust\sharecount)]
						dst_zone.DST_Dustzone=Object.DST_Dustzone(dst_part\zone_handle)
						If dst_dust\dense_akt#<1
							dst_w#=(1-dst_dust\dense_akt#)*130+50
							PositionEntity dst_part\partpivot,0,0,0
							RotateEntity dst_part\partpivot,Rand(-dst_w#,dst_w#),Rand(-dst_w#,dst_w#),0
							EntityParent dst_part\partpivot,0
							MoveEntity dst_part\partpivot,0,0,dst_zone\clipradius#*Rnd(dst_dust\dense_akt#,1.0)
							dst_part\alphaflag=1 : dst_part\alphaakt#=0 : dst_part\alphadest#=1
						Else
							PositionEntity dst_part\partpivot,0,0,0
							RotateEntity dst_part\partpivot,Rand(-50,50),Rand(-50,50),0
							EntityParent dst_part\partpivot,0
							MoveEntity dst_part\partpivot,0,0,dst_zone\clipradius#
							dst_part\alphaflag=0 : dst_part\alphaakt#=1 
						EndIf
						EntityAutoFade dst_part\part,dst_zone\farfade_min#,dst_zone\farfade_max#
						ShowEntity dst_part\partpivot : ShowEntity dst_part\part
						dst_part\dist_old#=EntityDistance(dst_part\partpivot,dst_dust\camera)
						EntityParent dst_part\partpivot,dst_dust\pivot
						PaintEntity dst_part\part,dst_zone\brush
						dst_part\alpha#=Rnd(dst_zone\alpha_min#,dst_zone\alpha_max#)
						EntityAlpha dst_part\part,dst_part\alpha#*dst_part\alphaakt#
						dst_sc#=Rnd(dst_zone\size_min#,dst_zone\size_max#)
						ScaleEntity dst_part\part,dst_sc#,dst_sc#,dst_sc#
						dst_f#=Rnd(0,1)
						If dst_zone\r1<dst_zone\r2 Then dst_r=(dst_zone\r2-dst_zone\r1)*dst_f#+dst_zone\r1 Else dst_r=(dst_zone\r1-dst_zone\r2)*dst_f#+dst_zone\r2
						If dst_zone\g1<dst_zone\g2 Then dst_g=(dst_zone\g2-dst_zone\g1)*dst_f#+dst_zone\g1 Else dst_g=(dst_zone\g1-dst_zone\g2)*dst_f#+dst_zone\g2
						If dst_zone\b1<dst_zone\b2 Then dst_b=(dst_zone\b2-dst_zone\b1)*dst_f#+dst_zone\b1 Else dst_b=(dst_zone\b1-dst_zone\b2)*dst_f#+dst_zone\b2
						EntityColor dst_part\part,dst_r,dst_g,dst_b
						dst_part\rot#=Rnd(dst_zone\rot_min#,dst_zone\rot_max#)
						dst_part\startangle#=Rnd(dst_zone\angle_min#,dst_zone\angle_max#)
					EndIf
				EndIf
			EndIf
		EndIf
	Next 
End Function

Function dst_createflat2(DST_parent)
	DST_obj=CreateMesh(DST_parent)
	DST_surf=CreateSurface(DST_obj)
	DST_v1=AddVertex(DST_surf,.5,0,.5) : DST_v2=AddVertex(DST_surf,-.5,0,.5) : DST_v3=AddVertex(DST_surf,-.5,0,-.5) : DST_v4=AddVertex(DST_surf,.5,0,-.5)
	DST_v5=AddVertex(DST_surf,0,0,0)
	VertexTexCoords DST_surf,v1,0,1 : VertexTexCoords DST_surf,DST_v2,1,1 : VertexTexCoords DST_surf,DST_v3,1,0 : VertexTexCoords DST_surf,DST_v4,0,0
	VertexTexCoords DST_surf,DST_v5,.5,.5
	DST_t1=AddTriangle(DST_surf,DST_v2,DST_v1,DST_v5) : DST_t1=AddTriangle(DST_surf,DST_v3,DST_v2,DST_v5) : DST_t1=AddTriangle(DST_surf,DST_v4,DST_v3,DST_v5) : DST_t1=AddTriangle(DST_surf,DST_v1,DST_v4,DST_v5)
	UpdateNormals DST_obj
	DST_au=5 : DST_av=2 : DST_mi=5
	VertexNormal DST_surf,0,DST_au,DST_av,DST_au : VertexNormal DST_surf,1,-DST_au,DST_av,DST_au : VertexNormal DST_surf,2,-DST_au,DST_av,-DST_au : VertexNormal DST_surf,3,DST_au,DST_av,-DST_au
	VertexNormal DST_surf,4,0,DST_mi,0
	RotateMesh DST_obj,90,0,0
	Return DST_obj
End Function

Function dst_createflat1(DST_parent)
	DST_obj=CreateMesh(DST_parent)
	DST_surf=CreateSurface(DST_obj)
	DST_v1=AddVertex(DST_surf,.5,0.001,.5) : DST_v2=AddVertex(DST_surf,-.5,0.001,.5) : DST_v3=AddVertex(DST_surf,-.5,0.001,-.5) : DST_v4=AddVertex(DST_surf,.5,0.001,-.5)
	VertexTexCoords DST_surf,DST_v1,0,1,0,0 : VertexTexCoords DST_surf,DST_v2,1,1,0,0 : VertexTexCoords DST_surf,DST_v3,1,0,0,0 : VertexTexCoords DST_surf,DST_v4,0,0,0,0
	VertexTexCoords DST_surf,DST_v1,0,1,0,1 : VertexTexCoords DST_surf,DST_v2,1,1,0,1 : VertexTexCoords DST_surf,DST_v3,1,0,0,1 : VertexTexCoords DST_surf,DST_v4,0,0,0,1
	DST_t1=AddTriangle(DST_surf,DST_v3,DST_v2,DST_v1) : DST_t2=AddTriangle(DST_surf,DST_v1,DST_v4,DST_v3)
	UpdateNormals DST_obj
	VertexNormal DST_surf,0,1,.01,1 : VertexNormal DST_surf,1,-1,.01,1 : VertexNormal DST_surf,2,-1,.01,-1 : VertexNormal DST_surf,3,1,.01,-1
	RotateMesh DST_obj,90,0,0 
	Return DST_obj
End Function

Function dst_flipstring$(dst_f$)
	dst_l=Len(dst_f$)
	For dst_a=dst_l To 1 Step -1
		dst_f2$=dst_f2$+Mid$(dst_f$,dst_a,1)
	Next
	Return dst_f2$
End Function

Function CreateNewDust(ID,DustX,DustY,DustZ,DustRad, DustScale1, DustScale2, DA1#, DA2#, DR1, DG1, DB1, DR2, DG2, DB2)
	Zone_Dust_External[ID]=DST_Create_Dustzone(Zone_Dust_Handle,1)
	PositionEntity Zone_Dust_External[ID],DustX,DustY,DustZ
	DST_Set_ZoneRadius(Zone_Dust_External[ID],DustRad)
;	DST_Set_texture(Zone_Dust_External[ID],Text_Effects[9],2)
	DST_Set_FadingFar(Zone_Dust_External[ID],250,350)
	DST_Set_FadingNear(Zone_Dust_External[ID],30,60)
	DST_Set_ScaleRange(Zone_Dust_External[ID],150,200)
	DST_Set_FX(Zone_Dust_External[ID],1)
	DST_Set_Blend(Zone_Dust_External[ID],1+2)
	DST_Set_AlphaRange(Zone_Dust_External[ID],DA1#,DA2#)
	DST_Set_ColorRange(Zone_Dust_External[ID],DR1,DG1,DB1,DR2,DG2,DB2)
End Function

Function PCL_NewCloud(pcl_mesh,pcl_anz,pcl_groups=1)
	Local pcl_vertex[3]
	Local pcl_dummy2=CreatePivot()
	PCL_NewCloud.pcl_cloud=New PCL_Cloud
	PCL_NewCloud\pivot=CreatePivot():
;	VirtualScene_Register(Scene, PCL_NewCloud\pivot)
	For a=1 To pcl_anz
		SeedRnd Rand(10,999999)
		PCL_NewCloud\part1[a]=PCL_Createflat(PCL_NewCloud\pivot)
		s=CountSurfaces(pcl_mesh)
		s=GetSurface(pcl_mesh,Rand(1,s))
		t=Rand(0,CountTriangles(s)-1)
		For d=0 To 2
			pcl_vertex[d]=TriangleVertex(s,t,d)
		Next
		TFormPoint VertexX#(s,pcl_vertex[0]),VertexY#(s,pcl_vertex[0]),VertexZ#(s,pcl_vertex[0]),pcl_mesh,0
		PositionEntity PCL_NewCloud\part1[a],TFormedX#(),TFormedY#(),TFormedZ#()
		TFormPoint VertexX#(s,pcl_vertex[1]),VertexY#(s,pcl_vertex[1]),VertexZ#(s,pcl_vertex[1]),pcl_mesh,0
		PositionEntity pcl_dummy2,TFormedX#(),TFormedY#(),TFormedZ#()
		PointEntity PCL_NewCloud\part1[a],pcl_dummy2
		MoveEntity PCL_NewCloud\part1[a],0,0,Rnd(0,EntityDistance#(PCL_NewCloud\part1[a],pcl_dummy2))
		TFormPoint VertexX#(s,pcl_vertex[2]),VertexY#(s,pcl_vertex[2]),VertexZ#(s,pcl_vertex[2]),pcl_mesh,0
		PositionEntity pcl_dummy2,TFormedX#(),TFormedY#(),TFormedZ#()
		PointEntity PCL_NewCloud\part1[a],pcl_dummy2
		MoveEntity PCL_NewCloud\part1[a],0,0,Rnd(0,EntityDistance#(PCL_NewCloud\part1[a],pcl_dummy2))
		PCL_NewCloud\part10#[a]=Rnd(0.0,1.0)
		PCL_NewCloud\part5#[a]=1 : PCL_NewCloud\part6#[a]=EntityDistance#(PCL_NewCloud\part1[a],PCL_NewCloud\pivot)
		PCL_NewCloud\part7#[a]=1 : PCL_NewCloud\part8#[a]=1 : PCL_NewCloud\part9#[a]=1
		pcl_group=pcl_group+1
		If pcl_group>pcl_groups Then pcl_group=1
		PCL_NewCloud\part11[a]=pcl_group
		PCL_NewCloud\partx#[a]=EntityX#(PCL_NewCloud\part1[a])
		PCL_NewCloud\party#[a]=EntityY#(PCL_NewCloud\part1[a])
		PCL_NewCloud\partz#[a]=EntityZ#(PCL_NewCloud\part1[a])
		pcl_sx#=PCL_NewCloud\part5#[a]*PCL_EntityScale#(PCL_NewCloud\pivot,0)
		pcl_sy#=PCL_NewCloud\part5#[a]*PCL_EntityScale#(PCL_NewCloud\pivot,1)
		pcl_sz#=PCL_NewCloud\part5#[a]*PCL_EntityScale#(PCL_NewCloud\pivot,2)
		EntityBox PCL_NewCloud\part1[a],pcl_sx#/(-2),pcl_sy#/(-2),pcl_sz#/(-2),pcl_sx#,pcl_sy#,pcl_sz#
		EntityRadius PCL_NewCloud\part1[a],(pcl_sx#/2+pcl_sy#/2+pcl_sz#/2)/3.0
	Next
	FreeEntity pcl_dummy2
	For a=1 To pcl_groups
		temp.pcl_groupbrush=New pcl_groupbrush
		temp\pivot=PCL_NewCloud\pivot
		temp\number=a
		temp\r1=100 : temp\g1=100 : temp\b1=100
		temp\r2=255 : temp\g2=255 : temp\b2=255
		temp\size_min#=1 : temp\size_max#=1
		temp\pos_min#=1 : temp\pos_max#=1
		temp\rot_x=0 : temp\rot_y=0 : temp\rot_z=0 : temp\rand_x=0 : temp\rand_y=0 : temp\rand_z=0
		temp\alpha_min#=1 : temp\alpha_max#=1
		temp\shini_min#=0 : temp\shini_max#=0
	Next
	
	
	PCL_NewCloud\anz_part=pcl_anz : PCL_NewCloud\akt_part=1
	PCL_NewCloud\r_amb=50 : PCL_NewCloud\g_amb=50 : PCL_NewCloud\b_amb=50
	PCL_NewCloud\speed=0.50
	PCL_NewCloud\renderstep=10
	PCL_NewCloud\groups=pcl_groups
	PCL_SetParticleColor(PCL_NewCloud\pivot,100,100,100,255,255,255)
	
	PositionEntity PCL_NewCloud\pivot,0,0,0
	Return PCL_NewCloud\pivot
End Function

Function PCL_SaveCloud(pcl_pivot,pcl_save$)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			pcl_f=WriteFile(pcl_save$)
			If pcl_f<>0
				WriteLine pcl_f,pcl_cld\anz_part
				WriteLine pcl_f,pcl_cld\groups
				For a=1 To pcl_cld\anz_part
					WriteLine pcl_f,pcl_cld\partx#[a]
					WriteLine pcl_f,pcl_cld\party#[a]
					WriteLine pcl_f,pcl_cld\partz#[a]
					WriteLine pcl_f,EntityPitch#(pcl_cld\part1[a])
					WriteLine pcl_f,EntityYaw#(pcl_cld\part1[a])
					WriteLine pcl_f,EntityRoll#(pcl_cld\part1[a])
					WriteLine pcl_f,pcl_cld\part11[a]
					WriteLine pcl_f,pcl_cld\part12[a]
					WriteLine pcl_f,pcl_cld\part10#[a]
				Next
				WriteLine pcl_f,pcl_cld\r_amb#
				WriteLine pcl_f,pcl_cld\g_amb#
				WriteLine pcl_f,pcl_cld\b_amb#
   ;WriteLine pcl_f,pcl_cld\autofade
   ;WriteLine pcl_f,pcl_cld\fade_min#
   ;WriteLine pcl_f,pcl_cld\fade_max#
				WriteLine pcl_f,pcl_cld\auto	 
				WriteLine pcl_f,pcl_cld\point
				WriteLine pcl_f,pcl_cld\random
				WriteLine pcl_f,pcl_cld\speed#
				WriteLine pcl_f,pcl_cld\renderstep
				For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
					If pcl_cld\pivot=pcl_gr\pivot
						WriteLine pcl_f,"group" 
						WriteLine pcl_f,pcl_gr\number
						WriteLine pcl_f,pcl_gr\near
						WriteLine pcl_f,pcl_gr\near_n#
						WriteLine pcl_f,pcl_gr\near_f#
						WriteLine pcl_f,pcl_gr\autofade
						WriteLine pcl_f,pcl_gr\far_n#
						WriteLine pcl_f,pcl_gr\far_f#
						WriteLine pcl_f,pcl_gr\size_min#
						WriteLine pcl_f,pcl_gr\size_max#
						WriteLine pcl_f,pcl_gr\size_mode
						WriteLine pcl_f,pcl_gr\pos_min#
						WriteLine pcl_f,pcl_gr\pos_max#
						WriteLine pcl_f,pcl_gr\rot_x
						WriteLine pcl_f,pcl_gr\rot_y
						WriteLine pcl_f,pcl_gr\rot_z
						WriteLine pcl_f,pcl_gr\rand_x
						WriteLine pcl_f,pcl_gr\rand_y
						WriteLine pcl_f,pcl_gr\rand_z
						WriteLine pcl_f,pcl_gr\alpha_min#
						WriteLine pcl_f,pcl_gr\alpha_max#
						WriteLine pcl_f,pcl_gr\shini_min#
						WriteLine pcl_f,pcl_gr\shini_max#
						WriteLine pcl_f,pcl_gr\r1
						WriteLine pcl_f,pcl_gr\g1
						WriteLine pcl_f,pcl_gr\b1
						WriteLine pcl_f,pcl_gr\r2
						WriteLine pcl_f,pcl_gr\g2
						WriteLine pcl_f,pcl_gr\b2
						WriteLine pcl_f,pcl_gr\blend
						WriteLine pcl_f,pcl_gr\fx
						WriteLine pcl_f,pcl_gr\p_type
						WriteLine pcl_f,pcl_gr\pick
						WriteLine pcl_f,pcl_gr\obscure
						For a=0 To 7
							WriteLine pcl_f,pcl_gr\texture$[a]
							WriteLine pcl_f,pcl_gr\texflag[a]
							WriteLine pcl_f,pcl_gr\texmode[a]	
							WriteLine pcl_f,pcl_gr\texscx#[a]	
							WriteLine pcl_f,pcl_gr\texscy#[a]	
							WriteLine pcl_f,pcl_gr\texposx#[a]	
							WriteLine pcl_f,pcl_gr\texposy#[a]	
							WriteLine pcl_f,pcl_gr\texrot#[a]	
						Next
					EndIf
				Next
				CloseFile(pcl_f)
			EndIf
			Exit
		EndIf
	Next
End Function

Function PCL_LoadCloud(pcl_load$,parent=0)
	pcl_f=ReadFile(pcl_load$)
	If pcl_f<>0
		PCL_NewCloud.pcl_cloud=New PCL_Cloud
		PCL_NewCloud\pivot=CreatePivot()
		PCL_NewCloud\anz_part=ReadLine (pcl_f)
		PCL_NewCloud\akt_part=1
		PCL_NewCloud\groups=ReadLine (pcl_f)
		For a=1 To PCL_NewCloud\anz_part
			PCL_NewCloud\partx#[a]=ReadLine (pcl_f)
			PCL_NewCloud\party#[a]=ReadLine (pcl_f)
			PCL_NewCloud\partz#[a]=ReadLine (pcl_f)
			pcl_rx=ReadLine (pcl_f) : pcl_ry=ReadLine (pcl_f) : pcl_rz=ReadLine (pcl_f)
			PCL_NewCloud\part11[a]=ReadLine (pcl_f)
			PCL_NewCloud\part12[a]=ReadLine (pcl_f)
			PCL_NewCloud\part10#[a]=ReadLine (pcl_f)
			PCL_NewCloud\part1[a]=PCL_Createflat(PCL_NewCloud\pivot)
			PositionEntity PCL_NewCloud\part1[a],PCL_NewCloud\partx#[a],PCL_NewCloud\party#[a],PCL_NewCloud\partz#[a]
			RotateEntity PCL_NewCloud\part1[a],pcl_rx,pcl_ry,pcl_rz
			PCL_NewCloud\part6#[a]=EntityDistance#(PCL_NewCloud\part1[a],PCL_NewCloud\pivot)
			PCL_NewCloud\part7#[a]=1 : PCL_NewCloud\part8#[a]=1
		Next
		PCL_NewCloud\r_amb#=ReadLine (pcl_f)
		PCL_NewCloud\g_amb#=ReadLine (pcl_f)
		PCL_NewCloud\b_amb#=ReadLine (pcl_f)
 ;pcl_newcloud\autofade=ReadLine (pcl_f)
 ;pcl_newcloud\fade_min#=ReadLine (pcl_f)
 ;pcl_newcloud\fade_max#=ReadLine (pcl_f)
 ;PCL_SetAutofade(pcl_newcloud\pivot,pcl_newcloud\fade_min#,pcl_newcloud\fade_max#)
		PCL_NewCloud\auto=ReadLine (pcl_f)
		PCL_NewCloud\point=ReadLine (pcl_f)
		PCL_NewCloud\random=ReadLine (pcl_f)
		PCL_NewCloud\speed#=ReadLine (pcl_f)
		PCL_NewCloud\renderstep=ReadLine (pcl_f)
		Repeat 
			b$=ReadLine$ (pcl_f)
			If b$="group" 
				pcl_gr.pcl_groupbrush=New pcl_groupbrush
				pcl_gr\pivot=PCL_NewCloud\pivot
				pcl_gr\number=ReadLine (pcl_f)
				pcl_gr\near=ReadLine (pcl_f)
				pcl_gr\near_n#=ReadLine (pcl_f)
				pcl_gr\near_f#=ReadLine (pcl_f)
				If pcl_gr\near>0 Then PCL_SetNearFade(pcl_gr\pivot,pcl_gr\near,pcl_gr\near_n#,pcl_gr\near_f#,pcl_gr\number)
				pcl_gr\autofade=ReadLine (pcl_f)
				pcl_gr\far_n#=ReadLine (pcl_f)
				pcl_gr\far_f#=ReadLine (pcl_f)
				If pcl_gr\autofade=1 Then PCL_SetAutofade(pcl_gr\pivot,pcl_gr\far_n#,pcl_gr\far_f#,pcl_gr\number)
				pcl_gr\size_min#=ReadLine (pcl_f)
				pcl_gr\size_max#=ReadLine (pcl_f)
				pcl_gr\size_mode=ReadLine (pcl_f)
				PCL_SetParticleScale(pcl_gr\pivot,pcl_gr\size_min#,pcl_gr\size_max#,pcl_gr\size_mode,pcl_gr\number)
				pcl_gr\pos_min#=ReadLine (pcl_f)
				pcl_gr\pos_max#=ReadLine (pcl_f)
				PCL_SetRandomPos(pcl_gr\pivot,pcl_gr\pos_min#,pcl_gr\pos_max#,pcl_gr\number)
				pcl_gr\rot_x=ReadLine (pcl_f)
				pcl_gr\rot_y=ReadLine (pcl_f)
				pcl_gr\rot_z=ReadLine (pcl_f)
				pcl_gr\rand_x=ReadLine (pcl_f)
				pcl_gr\rand_y=ReadLine (pcl_f)
				pcl_gr\rand_z=ReadLine (pcl_f)
				If PCL_NewCloud\point=0 Then PCL_RotateParticle(pcl_gr\pivot,pcl_gr\rot_x,pcl_gr\rot_y,pcl_gr\rot_z,pcl_gr\rand_x,pcl_gr\rand_y,pcl_gr\rand_z,pcl_gr\number)
				pcl_gr\alpha_min#=ReadLine (pcl_f)
				pcl_gr\alpha_max#=ReadLine (pcl_f)
				PCL_SetCloudAlpha(pcl_gr\pivot,pcl_gr\alpha_min#,pcl_gr\alpha_max#,pcl_gr\number)
				pcl_gr\shini_min#=ReadLine (pcl_f)
				pcl_gr\shini_max#=ReadLine (pcl_f)
				PCL_SetCloudShininess(pcl_gr\pivot,pcl_gr\shini_min#,pcl_gr\shini_max#,pcl_gr\number)
				pcl_gr\r1=ReadLine (pcl_f)
				pcl_gr\g1=ReadLine (pcl_f)
				pcl_gr\b1=ReadLine (pcl_f)
				pcl_gr\r2=ReadLine (pcl_f)
				pcl_gr\g2=ReadLine (pcl_f)
				pcl_gr\b2=ReadLine (pcl_f)
				PCL_SetParticleColor(pcl_gr\pivot,pcl_gr\r1,pcl_gr\g1,pcl_gr\b1,pcl_gr\r2,pcl_gr\g2,pcl_gr\b2,pcl_gr\number)
				pcl_gr\blend=ReadLine (pcl_f)
				PCL_SetCloudBlend(pcl_gr\pivot,pcl_gr\blend,pcl_gr\number)
				pcl_gr\fx=ReadLine (pcl_f)
				PCL_SetCloudFX(pcl_gr\pivot,pcl_gr\fx,pcl_gr\number)
				pcl_gr\p_type=ReadLine (pcl_f)
				pcl_gr\pick=ReadLine (pcl_f)
				pcl_gr\obscure=ReadLine (pcl_f)
				PCL_SetParticleType(pcl_gr\pivot,pcl_gr\p_type,pcl_gr\pick,pcl_gr\obscure,pcl_gr\number)
				For a=0 To 7
					pcl_gr\texture$[a]=ReadLine (pcl_f)
					pcl_gr\texflag[a]=ReadLine (pcl_f)
					pcl_gr\texmode[a]=ReadLine (pcl_f)
					pcl_gr\texscx#[a]=ReadLine (pcl_f)	
					pcl_gr\texscy#[a]=ReadLine (pcl_f)	
					pcl_gr\texposx#[a]=ReadLine (pcl_f)	
					pcl_gr\texposy#[a]=ReadLine (pcl_f)	
					pcl_gr\texrot#[a]=ReadLine (pcl_f)	
					If pcl_gr\texture$[a]<>"" Then PCL_SetCloudtexture(pcl_gr\pivot,pcl_gr\texture$[a],pcl_gr\texflag[a],pcl_gr\texmode[a],pcl_gr\texscx#[a],pcl_gr\texscy#[a],pcl_gr\texrot#[a],pcl_gr\texposx#[a],pcl_gr\texposy#[a],a,pcl_gr\number)
				Next
			EndIf
		Until b$=""
		CloseFile(pcl_f)
	EndIf
	Return PCL_NewCloud\pivot
End Function

Function PCL_CopyCloud(pcl_pivot)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			PCL_NewCloud.pcl_cloud=New PCL_Cloud
			PCL_NewCloud\pivot=CreatePivot():
;			VirtualScene_Register(Scene, PCL_NewCloud\pivot)
;			VirtualScene_Register(Scene, PCL_NewCloud\pivot)
			For a=1 To pcl_cld\anz_part
				PCL_NewCloud\part1[a]=CopyEntity(pcl_cld\part1[a], PCL_NewCloud\pivot)
				PCL_NewCloud\part2[a]=pcl_cld\part2[a]
				PCL_NewCloud\part3[a]=pcl_cld\part3[a]
				PCL_NewCloud\part4[a]=pcl_cld\part4[a]
				PCL_NewCloud\part5#[a]=pcl_cld\part5#[a]
				PCL_NewCloud\part6#[a]=pcl_cld\part6#[a]
				PCL_NewCloud\part7#[a]=pcl_cld\part7#[a]
				PCL_NewCloud\part8#[a]=pcl_cld\part8#[a]
				PCL_NewCloud\part9#[a]=pcl_cld\part9#[a]
				PCL_NewCloud\part10#[a]=pcl_cld\part10#[a]
				PCL_NewCloud\part11[a]=pcl_cld\part11[a]
				PCL_NewCloud\part12[a]=pcl_cld\part12[a]
				PCL_NewCloud\part_n#[a]=pcl_cld\part_n#[a]
				PCL_NewCloud\part_f#[a]=pcl_cld\part_f#[a]
				PCL_NewCloud\partx#[a]=pcl_cld\partx#[a]
				PCL_NewCloud\party#[a]=pcl_cld\party#[a]
				PCL_NewCloud\partz#[a]=pcl_cld\partz#[a]
				EntityParent PCL_NewCloud\part1[a],PCL_NewCloud\pivot
				PositionEntity PCL_NewCloud\part1[a],EntityX#(pcl_cld\part1[a]),EntityY#(pcl_cld\part1[a]),EntityZ#(pcl_cld\part1[a])
				RotateEntity PCL_NewCloud\part1[a],EntityPitch#(pcl_cld\part1[a]),EntityYaw#(pcl_cld\part1[a]),EntityRoll#(pcl_cld\part1[a])
			Next
			PCL_NewCloud\light=pcl_cld\light
			PCL_NewCloud\camera=pcl_cld\camera
			PCL_NewCloud\r_amb#=pcl_cld\r_amb#
			PCL_NewCloud\g_amb#=pcl_cld\g_amb#
			PCL_NewCloud\b_amb#=pcl_cld\b_amb#
			PCL_NewCloud\auto=pcl_cld\auto
			PCL_NewCloud\akt_part=pcl_cld\akt_part
			PCL_NewCloud\anz_part=pcl_cld\anz_part
			PCL_NewCloud\point=pcl_cld\point
			PCL_NewCloud\random=pcl_cld\random
			PCL_NewCloud\speed#=pcl_cld\speed#
			PCL_NewCloud\renderstep=pcl_cld\renderstep
			PCL_NewCloud\groups=pcl_cld\groups
			For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
				If pcl_cld\pivot=pcl_gr\pivot
					temp.pcl_groupbrush=New pcl_groupbrush
					temp\pivot=PCL_NewCloud\pivot
					temp\number=pcl_gr\number
					temp\near=pcl_gr\near
					temp\near_n#=pcl_gr\near_n#
					temp\near_f#=pcl_gr\near_f#
					temp\autofade=pcl_gr\autofade
					temp\far_n#=pcl_gr\far_n#
					temp\far_f#=pcl_gr\far_f#
					temp\size_min#=pcl_gr\size_min#
					temp\size_max#=pcl_gr\size_max#
					temp\size_mode=pcl_gr\size_mode
					temp\pos_min#=pcl_gr\pos_min#
					temp\pos_max#=pcl_gr\pos_max#
					temp\rot_x=pcl_gr\rot_x
					temp\rot_y=pcl_gr\rot_y
					temp\rot_z=pcl_gr\rot_z
					temp\rand_x=pcl_gr\rand_x
					temp\rand_y=pcl_gr\rand_y
					temp\rand_z=pcl_gr\rand_z
					temp\alpha_min#=pcl_gr\alpha_min#
					temp\alpha_max#=pcl_gr\alpha_max#
					temp\shini_min#=pcl_gr\shini_min#
					temp\shini_max#=pcl_gr\shini_max#
					temp\r1=pcl_gr\r1 : temp\g1=pcl_gr\g1 : temp\b1=pcl_gr\b1
					temp\r2=pcl_gr\r2 : temp\g2=pcl_gr\g2 : temp\b2=pcl_gr\b2
					temp\blend=pcl_gr\blend
					temp\fx=pcl_gr\fx
					temp\p_type=pcl_gr\p_type
					temp\pick=pcl_gr\pick
					temp\obscure=pcl_gr\obscure
					For a=1 To 7
						temp\texture$[a]=pcl_gr\texture$[a]
						temp\texflag[a]=pcl_gr\texflag[a]
						temp\texmode[a]=pcl_gr\texmode[a]
					Next
				EndIf
			Next
			Exit
		EndIf
	Next
	PositionEntity PCL_NewCloud\pivot,0,0,0
	Return PCL_NewCloud\pivot
End Function

Function PCL_DeleteCloud(pcl_pivot)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
;			VirtualScene_Unregister(Scene, pcl_cld\pivot)
			FreeEntity pcl_cld\pivot
			Delete pcl_cld
			Exit
		EndIf
	Next
End Function

Function PCL_SetTimer()
	PCL_Timer=MilliSecs()
End Function

Function PCL_UpdateClouds(pcl_looptime#)
	If pcl_looptime#=0
		pcl_looptime#=(MilliSecs()-PCL_Timer)/1000.0
		PCL_Timer=MilliSecs()
	EndIf
	pcl_cld.pcl_cloud=First PCL_Cloud
	If pcl_cld<>Null
		If pcl_cld\auto=1 And pcl_cld\light<>0 And pcl_cld\camera<>0
			For a=pcl_cld\akt_part To pcl_cld\akt_part+10
				If a>pcl_cld\anz_part Then a=1 : Exit
				If EntityInView(pcl_cld\part1[a],pcl_cld\camera)=1
					pcl_rx=EntityPitch#(pcl_cld\part1[a])
					pcl_ry=EntityYaw#(pcl_cld\part1[a])
					pcl_rz=EntityRoll#(pcl_cld\part1[a])
					PointEntity pcl_cld\part1[a],pcl_cld\light
					c=100
					For b=1 To pcl_cld\anz_part 
						If b<>a And EntityDistance(pcl_cld\part1[b],pcl_cld\light) < EntityDistance(pcl_cld\part1[a],pcl_cld\light)
							TFormPoint EntityX#(pcl_cld\part1[b],1),EntityY#(pcl_cld\part1[b],1),EntityZ#(pcl_cld\part1[b],1),0,pcl_cld\part1[a]
							ztemp#=TFormedZ#() 
							If ztemp#>0
								xtemp#=TFormedX#() : ytemp#=TFormedY#()
								xtemp_a#=xtemp#*(pcl_cld\part5#[a]+pcl_cld\part5#[b])
								ytemp_a#=ytemp#*(pcl_cld\part5#[a]+pcl_cld\part5#[b])
								If Abs(xtemp_a#)<pcl_cld\part5#[a] And Abs(ytemp_a#)<pcl_cld\part5#[a]
									c=c-pcl_cld\renderstep*pcl_cld\part9#[b]
									If c<0 Then c=0 : Exit
								EndIf
							EndIf
						EndIf
					Next
					pcl_cld\part8#[a]=c/100.0
					RotateEntity pcl_cld\part1[a],pcl_rx,pcl_ry,pcl_rz
				EndIf
			Next
			pcl_cld\akt_part=a
		EndIf
		Insert pcl_cld.pcl_cloud After Last PCL_Cloud	
	EndIf
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		pcl_piv_scale#=(PCL_EntityScale#(pcl_cld\pivot,0)+PCL_EntityScale#(pcl_cld\pivot,1)+PCL_EntityScale#(pcl_cld\pivot,2))/3.0
		For a=1 To pcl_cld\anz_part
			If pcl_cld\part8#[a]<>pcl_cld\part7#[a]
				If pcl_cld\part8#[a]<pcl_cld\part7#[a]
					pcl_cld\part7#[a]=pcl_cld\part7#[a]-pcl_cld\speed#*pcl_looptime#
					If pcl_cld\part7#[a]<pcl_cld\part8#[a] Then pcl_cld\part7#[a]=pcl_cld\part8#[a]
				EndIf
				If pcl_cld\part8#[a]>pcl_cld\part7#[a]
					pcl_cld\part7#[a]=pcl_cld\part7#[a]+pcl_cld\speed#*pcl_looptime#
					If pcl_cld\part7#[a]>pcl_cld\part8#[a] Then pcl_cld\part7#[a]=pcl_cld\part8#[a]
				EndIf
				r=pcl_cld\part2[a]*((1.0-pcl_cld\r_amb#)*pcl_cld\part7#[a]+pcl_cld\r_amb#)
				g=pcl_cld\part3[a]*((1.0-pcl_cld\g_amb#)*pcl_cld\part7#[a]+pcl_cld\g_amb#)
				b=pcl_cld\part4[a]*((1.0-pcl_cld\b_amb#)*pcl_cld\part7#[a]+pcl_cld\b_amb#)
				EntityColor pcl_cld\part1[a],r,g,b
			EndIf
			If pcl_cld\camera<>0
				pcl_dist#=EntityDistance#(pcl_cld\part1[a],pcl_cld\camera)
				pcl_alpha#=1
				Select pcl_cld\part12[a]
					Case 1
						pcl_border#=pcl_piv_scale*(pcl_cld\part5#[a]/1.5)
						If pcl_dist#<pcl_border#
							pcl_alpha#=(((pcl_dist#-(pcl_border#/2))*2)/pcl_border#)*pcl_cld\part9#[a]
							EntityAlpha pcl_cld\part1[a],pcl_alpha#
						Else
							EntityAlpha pcl_cld\part1[a],pcl_cld\part9#[a]
							pcl_alpha#=1
						EndIf
					Case 2
						If pcl_dist#>pcl_cld\part_n#[a] And pcl_dist#<pcl_cld\part_f#[a]
							pcl_alpha#=(pcl_dist#-pcl_cld\part_n#[a])/(pcl_cld\part_f#[a]-pcl_cld\part_n#[a])*pcl_cld\part9#[a]
							EntityAlpha pcl_cld\part1[a],pcl_alpha#
						Else
							If pcl_dist#<pcl_cld\part_n#[a] And pcl_alpha#>0 Then EntityAlpha pcl_cld\part1[a],0 : pcl_alpha#=0
							If pcl_dist#>pcl_cld\part_f#[a] And pcl_alpha#<1 Then EntityAlpha pcl_cld\part1[a],pcl_cld\part9#[a] : pcl_alpha#=1
						EndIf
				End Select
				If pcl_cld\point=1 And pcl_alpha#>0 Then PointEntity pcl_cld\part1[a],pcl_cld\camera,(360*pcl_cld\part10#[a])*pcl_cld\random
			EndIf
		Next
	Next
End Function

Function PCL_UpdateCloud(pcl_pivot)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			If pcl_cld\light<>0
				For a=1 To pcl_cld\anz_part
					pcl_rx=EntityPitch#(pcl_cld\part1[a])
					pcl_ry=EntityYaw#(pcl_cld\part1[a])
					pcl_rz=EntityRoll#(pcl_cld\part1[a])
					PointEntity pcl_cld\part1[a],pcl_cld\light
					c=100
					For b=1 To pcl_cld\anz_part
						If b<>a And EntityDistance(pcl_cld\part1[b],pcl_cld\light) < EntityDistance(pcl_cld\part1[a],pcl_cld\light)
							TFormPoint EntityX#(pcl_cld\part1[b],1),EntityY#(pcl_cld\part1[b],1),EntityZ#(pcl_cld\part1[b],1),0,pcl_cld\part1[a]
							ztemp#=TFormedZ#() 
							If ztemp#>0
								xtemp#=TFormedX#() : ytemp#=TFormedY#()
								xtemp_a#=xtemp#*(pcl_cld\part5#[a]+pcl_cld\part5#[b])
								ytemp_a#=ytemp#*(pcl_cld\part5#[a]+pcl_cld\part5#[b])
								If Abs(xtemp_a#)<pcl_cld\part5#[a] And Abs(ytemp_a#)<pcl_cld\part5#[a]
									c=c-pcl_cld\renderstep*pcl_cld\part9#[b]
									If c<0 Then c=0 : Exit
								EndIf
							EndIf
						EndIf
					Next
					pcl_cld\part8#[a]=c/100.0
					RotateEntity pcl_cld\part1[a],pcl_rx,pcl_ry,pcl_rz
				Next
			EndIf
			For a=1 To pcl_cld\anz_part
				If pcl_cld\part8#[a]<>pcl_cld\part7#[a]
					pcl_cld\part7#[a]=pcl_cld\part8#[a]
					r=pcl_cld\part2[a]*((1.0-pcl_cld\r_amb#)*pcl_cld\part7#[a]+pcl_cld\r_amb#)
					g=pcl_cld\part3[a]*((1.0-pcl_cld\g_amb#)*pcl_cld\part7#[a]+pcl_cld\g_amb#)
					b=pcl_cld\part4[a]*((1.0-pcl_cld\b_amb#)*pcl_cld\part7#[a]+pcl_cld\b_amb#)
					EntityColor pcl_cld\part1[a],r,g,b
				EndIf
			Next
			Exit
		EndIf
	Next
End Function

Function PCL_SetLight(pcl_pivot,pcl_entity,pcl_renderstep)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			pcl_cld\light=pcl_entity
			pcl_cld\renderstep=pcl_renderstep
			Exit
		EndIf
	Next
End Function

Function PCL_SetAmbientLight(pcl_pivot,pcl_r,pcl_g,pcl_b)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			pcl_cld\r_amb#=pcl_r/255.0
			pcl_cld\g_amb#=pcl_g/255.0
			pcl_cld\b_amb#=pcl_b/255.0
			Exit
		EndIf
	Next
End Function

Function PCL_SetAutoLightning(pcl_pivot,pcl_auto)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			pcl_cld\auto=pcl_auto
			If pcl_cld\auto=0
				For a=1 To pcl_cld\anz_part
					EntityColor pcl_cld\part1[a],pcl_cld\part2[a],pcl_cld\part3[a],pcl_cld\part4[a]
					pcl_cld\part7#[a]=1 : pcl_cld\part8#[a]=1
				Next
			EndIf   
			Exit
		EndIf
	Next
End Function

Function PCL_SetCamera(pcl_pivot,pcl_cam,pcl_point,pcl_random=0)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			pcl_cld\camera=pcl_cam
			pcl_cld\point=pcl_point
			pcl_cld\random=pcl_random
			If pcl_cld\camera<>0 And pcl_cld\point=1
				For a=1 To pcl_cld\anz_part
					PointEntity pcl_cld\part1[a],pcl_cld\camera,(360*pcl_cld\part10#[a])*pcl_cld\random
				Next
			EndIf
			Exit
		EndIf
	Next
End Function

Function PCL_PointParticle(pcl_pivot,pcl_entity,pcl_random=0,pcl_group=0)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot And pcl_entity>0
			For a=1 To pcl_cld\anz_part
				If pcl_group=0 Or pcl_group=pcl_cld\part11[a]
					PointEntity pcl_cld\part1[a],pcl_entity,(360*pcl_cld\part10#[a])*pcl_random
				EndIf
			Next
			Exit
		EndIf
	Next
End Function

Function PCL_SetNearFade(pcl_pivot,pcl_nearfade,pcl_near#=0,pcl_far#=0,pcl_group=0)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
				If pcl_cld\pivot=pcl_gr\pivot And (pcl_group=0 Or pcl_group=pcl_gr\number)
					pcl_gr\near=pcl_nearfade : pcl_gr\near_n#=pcl_near# : pcl_gr\near_f#=pcl_far#
				EndIf
			Next
			For a=1 To pcl_cld\anz_part
				If pcl_group=0 Or pcl_group=pcl_cld\part11[a] 
					pcl_cld\part12[a]=pcl_nearfade : pcl_cld\part_n#[a]=pcl_near# : pcl_cld\part_f#[a]=pcl_far#
					If pcl_nearfade=0 Then EntityAlpha pcl_cld\part1[a],pcl_cld\part9#[a]
				EndIf
			Next
			Exit
		EndIf
	Next
End Function

Function PCL_SetAutofade(pcl_pivot,pcl_near#,pcl_far#,pcl_group=0)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
				If pcl_cld\pivot=pcl_gr\pivot And (pcl_group=0 Or pcl_group=pcl_gr\number)
					If pcl_near#<0 And pcl_far#<0
						pcl_gr\far_n#=-1 : pcl_gr\far_f#=-1 : pcl_gr\autofade=0 : pcl_autofade=0
					Else
						pcl_gr\far_n#=pcl_near# : pcl_gr\far_f#=pcl_far# : pcl_gr\autofade=1 : pcl_autofade=1
					EndIf
				EndIf
			Next
			
			For a=1 To pcl_cld\anz_part
				If pcl_group=0 Or pcl_group=pcl_cld\part11[a]
					If pcl_autofade=1
						EntityAutoFade pcl_cld\part1[a],pcl_near#,pcl_far#
					Else
						b=PCL_Createflat(pcl_cld\pivot)
						PositionEntity b,EntityX#(pcl_cld\part1[a]),EntityY#(pcl_cld\part1[a]),EntityZ#(pcl_cld\part1[a])
						RotateEntity b,EntityPitch#(pcl_cld\part1[a]),EntityYaw#(pcl_cld\part1[a]),EntityRoll#(pcl_cld\part1[a])
						PaintEntity b,GetEntityBrush(pcl_cld\part1[a])
						ScaleEntity b,pcl_cld\part5#[a],pcl_cld\part5#[a],pcl_cld\part5#[a]
;						:VirtualScene_Register(Scene, pcl_cld\part1[a])
						FreeEntity pcl_cld\part1[a] : pcl_cld\part1[a]=b
						For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
							If pcl_cld\pivot=pcl_gr\pivot And pcl_group=pcl_gr\number
								EntityPickMode pcl_cld\part1[a],pcl_gr\pick,pcl_gr\obscure
								EntityType pcl_cld\part1[a],pcl_gr\p_type
							EndIf
							Exit
						Next
					EndIf
				EndIf
			Next
			Exit
		EndIf
	Next
End Function

Function PCL_RotateParticle(pcl_pivot,pcl_x,pcl_y,pcl_z,pcl_rx,pcl_ry,pcl_rz,pcl_group=0)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
				If pcl_cld\pivot=pcl_gr\pivot And (pcl_group=0 Or pcl_group=pcl_gr\number)
					pcl_gr\rot_x=pcl_x : pcl_gr\rot_y=pcl_y : pcl_gr\rot_z=pcl_z : pcl_gr\rand_x=pcl_rx : pcl_gr\rand_y=pcl_ry : pcl_gr\rand_z=pcl_rz
				EndIf
			Next
			If pcl_cld\point=0
				For a=1 To pcl_cld\anz_part
					If pcl_group=0 Or pcl_group=pcl_cld\part11[a]
						RotateEntity pcl_cld\part1[a],pcl_x+(pcl_cld\part10#[a]*pcl_rx-pcl_rx/2),pcl_y+(pcl_cld\part10#[a]*pcl_ry-pcl_ry/2),pcl_z+(pcl_cld\part10#[a]*pcl_rz-pcl_rz/2)
					EndIf
				Next
			EndIf
			Exit
		EndIf
	Next
End Function

Function PCL_SetRandomPos(pcl_pivot,pcl_min#,pcl_max#,pcl_group=0)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
				If pcl_cld\pivot=pcl_gr\pivot And (pcl_group=0 Or pcl_group=pcl_gr\number)
					pcl_gr\pos_min#=pcl_min# : pcl_gr\pos_max#=pcl_max#
				EndIf
			Next
			pcl_sx#=PCL_EntityScale#(pcl_cld\pivot,0)
			pcl_sy#=PCL_EntityScale#(pcl_cld\pivot,1)
			pcl_sz#=PCL_EntityScale#(pcl_cld\pivot,2)
			For a=1 To pcl_cld\anz_part
				If pcl_group=0 Or pcl_group=pcl_cld\part11[a]
					pcl_rx=EntityPitch#(pcl_cld\part1[a])
					pcl_ry=EntityYaw#(pcl_cld\part1[a])
					pcl_rz=EntityRoll#(pcl_cld\part1[a])
					PositionEntity pcl_cld\part1[a],pcl_cld\partx#[a],pcl_cld\party#[a],pcl_cld\partz#[a]
					PointEntity pcl_cld\part1[a],pcl_cld\pivot
					PositionEntity pcl_cld\part1[a],0,0,0
					TurnEntity pcl_cld\part1[a],0,180,0
					If pcl_min#<pcl_max#
						MoveEntity pcl_cld\part1[a],0,0,pcl_cld\part6#[a]*((pcl_max#-pcl_min#)*pcl_cld\part10#[a]+pcl_min#)
					Else
						MoveEntity pcl_cld\part1[a],0,0,pcl_cld\part6#[a]*((pcl_min#-pcl_max#)*pcl_cld\part10#[a]+pcl_max#)
					EndIf
					RotateEntity pcl_cld\part1[a],pcl_rx,pcl_ry,pcl_rz
				EndIf
			Next
			Exit
		EndIf
	Next
End Function

Function PCL_SetParticleScale(pcl_pivot,pcl_min#,pcl_max#,pcl_mode,pcl_group=0)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
				If pcl_cld\pivot=pcl_gr\pivot And (pcl_group=0 Or pcl_group=pcl_gr\number)
					pcl_gr\size_min#=pcl_min# : pcl_gr\size_max#=pcl_max# : pcl_gr\size_mode=pcl_mode
				EndIf
			Next
			For a=1 To pcl_cld\anz_part
				If pcl_group=0 Or pcl_group=pcl_cld\part11[a]
					If pcl_min#<pcl_max#
						pcl_cld\part5#[a]=(pcl_max#-pcl_min#)*pcl_cld\part10#[a]+pcl_min#
					Else
						pcl_cld\part5#[a]=(pcl_min#-pcl_max#)*pcl_cld\part10#[a]+pcl_max#
					EndIf
					ScaleEntity pcl_cld\part1[a],pcl_cld\part5#[a],pcl_cld\part5#[a],pcl_cld\part5#[a]
					pcl_sx#=pcl_cld\part5#[a]*PCL_EntityScale#(pcl_cld\pivot,0)
					pcl_sy#=pcl_cld\part5#[a]*PCL_EntityScale#(pcl_cld\pivot,1)
					pcl_sz#=pcl_cld\part5#[a]*PCL_EntityScale#(pcl_cld\pivot,2)
					EntityBox pcl_cld\part1[a],pcl_sx#/(-2),pcl_sy#/(-2),pcl_sz#/(-2),pcl_sx#,pcl_sy#,pcl_sz#
					EntityRadius pcl_cld\part1[a],(pcl_sx#/2+pcl_sy#/2+pcl_sz#/2)/3.0
					Select pcl_mode
						Case 2
							PositionEntity pcl_cld\part1[a],pcl_cld\partx#[a],pcl_cld\party#[a],pcl_cld\partz#[a]
							pcl_rx#=EntityPitch#(pcl_cld\part1[a])
							pcl_ry#=EntityYaw#(pcl_cld\part1[a])
							pcl_rz#=EntityRoll#(pcl_cld\part1[a])
							PointEntity pcl_cld\part1[a],pcl_cld\pivot
							MoveEntity pcl_cld\part1[a],0,0,pcl_cld\part5#[a]/2
							RotateEntity pcl_cld\part1[a],pcl_rx,pcl_ry,pcl_rz
						Case 3
							PositionEntity pcl_cld\part1[a],pcl_cld\partx#[a],pcl_cld\party#[a],pcl_cld\partz#[a]
							pcl_rx#=EntityPitch#(pcl_cld\part1[a])
							pcl_ry#=EntityYaw#(pcl_cld\part1[a])
							pcl_rz#=EntityRoll#(pcl_cld\part1[a])
							PointEntity pcl_cld\part1[a],pcl_cld\pivot
							MoveEntity pcl_cld\part1[a],0,0,-pcl_cld\part5#[a]/2
							RotateEntity pcl_cld\part1[a],pcl_rx,pcl_ry,pcl_rz
					End Select
				EndIf
			Next
			Exit
		EndIf
	Next
End Function

Function PCL_UpdateCollisionzones(pcl_pivot)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			pcl_px#=PCL_EntityScale#(pcl_cld\pivot,0)
			pcl_py#=PCL_EntityScale#(pcl_cld\pivot,1)
			pcl_pz#=PCL_EntityScale#(pcl_cld\pivot,2)
			For a=1 To pcl_cld\anz_part   
				pcl_sx#=pcl_cld\part5#[a]*pcl_px#
				pcl_sy#=pcl_cld\part5#[a]*pcl_py#
				pcl_sz#=pcl_cld\part5#[a]*pcl_pz#
				EntityBox pcl_cld\part1[a],pcl_sx#/(-2),pcl_sy#/(-2),pcl_sz#/(-2),pcl_sx#,pcl_sy#,pcl_sz#
				EntityRadius pcl_cld\part1[a],(pcl_sx#/2+pcl_sy#/2+pcl_sz#/2)/3.0
			Next
			Exit
		EndIf
	Next
End Function

Function PCL_SetParticleColor(pcl_pivot,pcl_r1,pcl_g1,pcl_b1,pcl_r2,pcl_g2,pcl_b2,pcl_group=0)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
				If pcl_cld\pivot=pcl_gr\pivot And (pcl_group=0 Or pcl_group=pcl_gr\number)
					pcl_gr\r1=pcl_r1 : pcl_gr\g1=pcl_g1 : pcl_gr\b1=pcl_b1
					pcl_gr\r2=pcl_r2 : pcl_gr\g2=pcl_g2 : pcl_gr\b2=pcl_b2
				EndIf
			Next
			For a=1 To pcl_cld\anz_part
				If pcl_group=0 Or pcl_group=pcl_cld\part11[a]
					pcl_f#=pcl_cld\part10#[a]
					If pcl_r1<pcl_r2 Then pcl_r=(pcl_r2-pcl_r1)*pcl_f#+pcl_r1 Else pcl_r=(pcl_r1-pcl_r2)*pcl_f#+pcl_r2
					If pcl_g1<pcl_g2 Then pcl_g=(pcl_g2-pcl_g1)*pcl_f#+pcl_g1 Else pcl_g=(pcl_g1-pcl_g2)*pcl_f#+pcl_g2
					If pcl_b1<pcl_b2 Then pcl_b=(pcl_b2-pcl_b1)*pcl_f#+pcl_b1 Else pcl_b=(pcl_b1-pcl_b2)*pcl_f#+pcl_b2
					pcl_cld\part2[a]=pcl_r : pcl_cld\part3[a]=pcl_g : pcl_cld\part4[a]=pcl_b
					r=pcl_cld\part2[a]*((1.0-pcl_cld\r_amb#)*pcl_cld\part7#[a]+pcl_cld\r_amb#)
					g=pcl_cld\part3[a]*((1.0-pcl_cld\g_amb#)*pcl_cld\part7#[a]+pcl_cld\g_amb#)
					b=pcl_cld\part4[a]*((1.0-pcl_cld\b_amb#)*pcl_cld\part7#[a]+pcl_cld\b_amb#)
					EntityColor pcl_cld\part1[a],r,g,b
;EntityColor pcl_cld\part1[a],255,255,255
				EndIf
			Next
			Exit
		EndIf
	Next
End Function

Function PCL_SetColorSpeed(pcl_pivot,pcl_speed#)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			pcl_cld\speed#=pcl_speed#
			Exit
		EndIf
	Next
End Function

Function PCL_SetCloudAlpha(pcl_pivot,pcl_min#,pcl_max#,pcl_group=0)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
				If pcl_cld\pivot=pcl_gr\pivot And (pcl_group=0 Or pcl_group=pcl_gr\number)
					pcl_gr\alpha_min#=pcl_min# : pcl_gr\alpha_max#=pcl_max#
				EndIf
			Next
			For a=1 To pcl_cld\anz_part
				If pcl_group=0 Or pcl_group=pcl_cld\part11[a]
					If pcl_min#<pcl_max#
						pcl_cld\part9#[a]=(pcl_max#-pcl_min#)*pcl_cld\part10#[a]+pcl_min#
					Else
						pcl_cld\part9#[a]=(pcl_min#-pcl_max#)*pcl_cld\part10#[a]+pcl_max#
					EndIf
					EntityAlpha pcl_cld\part1[a],pcl_cld\part9#[a]
				EndIf
			Next
			Exit
		EndIf
	Next
End Function

Function PCL_SetCloudShininess(pcl_pivot,pcl_min#,pcl_max#,pcl_group=0)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
				If pcl_cld\pivot=pcl_gr\pivot And (pcl_group=0 Or pcl_group=pcl_gr\number)
					pcl_gr\shini_min#=pcl_min# : pcl_gr\shini_max#=pcl_max#
				EndIf
			Next
			For a=1 To pcl_cld\anz_part
				If pcl_group=0 Or pcl_group=pcl_cld\part11[a]
					If pcl_min#<pcl_max#
						b#=(pcl_max#-pcl_min#)*pcl_cld\part10#[a]+pcl_min#
					Else
						b#=(pcl_min#-pcl_max#)*pcl_cld\part10#[a]+pcl_max#
					EndIf
					EntityShininess pcl_cld\part1[a],b#
				EndIf
			Next
			Exit
		EndIf
	Next
End Function

Function PCL_SetCloudtexture(pcl_pivot,pcl_texture$,pcl_flag=1,pcl_blend=2,pcl_scalex#=1,pcl_scaley#=1,pcl_rot#=0,pcl_x#=0,pcl_y#=0,pcl_layer=0,pcl_group=0)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			pcl_tex=LoadTexture(pcl_texture$,pcl_flag)
			If pcl_tex<>0
				TextureBlend pcl_tex,pcl_blend : ScaleTexture pcl_tex,pcl_scalex#,pcl_scaley# : RotateTexture pcl_tex,pcl_rot#
				PositionTexture pcl_tex,pcl_x#,pcl_y#
			EndIf
			For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
				If pcl_cld\pivot=pcl_gr\pivot And (pcl_group=0 Or pcl_group=pcl_gr\number) And pcl_tex<>0
					b$=PCL_FlipString$(pcl_texture$)
					a=Instr(b$,"\",1)
					If a>0 Then b$=PCL_FlipString$(Left$(b$,a-1)) Else b$=pcl_texture$
					pcl_gr\texture$[pcl_layer]=b$ : pcl_gr\texflag[pcl_layer]=pcl_flag : pcl_gr\texmode[pcl_layer]=pcl_blend
					pcl_gr\texscx#[pcl_layer]=pcl_scalex# : pcl_gr\texscy#[pcl_layer]=pcl_scaley# : pcl_gr\texposx#[pcl_layer]=pcl_x#
					pcl_gr\texposy#[pcl_layer]=pcl_y# : pcl_gr\texrot#[pcl_layer]=pcl_rot#
				EndIf
			Next
			For a=1 To pcl_cld\anz_part
				If pcl_group=0 Or pcl_group=pcl_cld\part11[a] And pcl_tex<>0 Then EntityTexture pcl_cld\part1[a],pcl_tex,0,pcl_layer
			Next
			If pcl_tex<>0 Then FreeTexture pcl_tex
			Exit
		EndIf
	Next
End Function

Function PCL_SetCloudBlend(pcl_pivot,pcl_blend,pcl_group=0)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
				If pcl_cld\pivot=pcl_gr\pivot And (pcl_group=0 Or pcl_group=pcl_gr\number)
					pcl_gr\blend=pcl_blend
				EndIf
			Next
			For a=1 To pcl_cld\anz_part
				If pcl_group=0 Or pcl_group=pcl_cld\part11[a] Then EntityBlend pcl_cld\part1[a],pcl_blend
			Next
			Exit
		EndIf
	Next
End Function

Function PCL_SetCloudFX(pcl_pivot,pcl_flag,pcl_group=0)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
				If pcl_cld\pivot=pcl_gr\pivot And (pcl_group=0 Or pcl_group=pcl_gr\number)
					pcl_gr\fx=pcl_flag
				EndIf
			Next
			For a=1 To pcl_cld\anz_part
				If pcl_group=0 Or pcl_group=pcl_cld\part11[a] Then EntityFX pcl_cld\part1[a],pcl_flag
			Next
			Exit
		EndIf
	Next
End Function

Function PCL_SetParticleType(pcl_pivot,pcl_type,pcl_pickmode,pcl_obscure,pcl_group=0)
	For pcl_cld.pcl_cloud=Each PCL_Cloud
		If pcl_cld\pivot=pcl_pivot
			For pcl_gr.pcl_groupbrush=Each pcl_groupbrush
				If pcl_cld\pivot=pcl_gr\pivot And (pcl_group=0 Or pcl_group=pcl_gr\number)
					pcl_gr\p_type=pcl_type : pcl_gr\pick=pcl_pickmode
					pcl_gr\obscure=pcl_obscure
				EndIf
			Next
			For a=1 To pcl_cld\anz_part
				If pcl_group=0 Or pcl_group=pcl_cld\part11[a]
					EntityPickMode pcl_cld\part1[a],pcl_pickmode,pcl_obscure : EntityType pcl_cld\part1[a],pcl_type
				EndIf
			Next
			Exit
		EndIf
	Next
End Function

Function PCL_EntityScale#(entity,axis)
	x#=GetMatElement#(entity,axis,0)
	y#=GetMatElement#(entity,axis,1)
	z#=GetMatElement#(entity,axis,2)
	Return Sqr(x*x+y*y+z*z)
End Function

Function PCL_Createflat(parent)
	obj=CreateMesh(parent)
	surf=CreateSurface(obj)
	v1=AddVertex(surf,.5,0,.5) : v2=AddVertex(surf,-.5,0,.5) : v3=AddVertex(surf,-.5,0,-.5) : v4=AddVertex(surf,.5,0,-.5)
	v5=AddVertex(surf,0,0,0)
	VertexTexCoords surf,v1,0,1 : VertexTexCoords surf,v2,1,1 : VertexTexCoords surf,v3,1,0 : VertexTexCoords surf,v4,0,0
	VertexTexCoords surf,v5,.5,.5
	t1=AddTriangle(surf,v2,v1,v5) : t1=AddTriangle(surf,v3,v2,v5) : t1=AddTriangle(surf,v4,v3,v5) : t1=AddTriangle(surf,v1,v4,v5)
	UpdateNormals obj
	au=5 : av=2 : mi=5
	VertexNormal surf,0,au,av,au : VertexNormal surf,1,-au,av,au : VertexNormal surf,2,-au,av,-au : VertexNormal surf,3,au,av,-au
	VertexNormal surf,4,0,mi,0
	RotateMesh obj,90,0,0
	Return obj
End Function

Function PCL_FlipString$(f$)
	l=Len(f$)
	For a=l To 1 Step -1
		f2$=f2$+Mid$(f$,a,1)
	Next
	Return f2$
End Function



;~IDEal Editor Parameters:
;~C#Blitz3D