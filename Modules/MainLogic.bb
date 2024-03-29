Function UpdateGraphics()
	;General UI Overlay
	
	If HUD = 1 Then
		
		
		Local ChatBase=14
		For Chat.Messages = Each Messages
			ChatBase=ChatBase-1
			Local TchatX=D3DOL + 20
			Local TchatY=D3DOD+25+(ChatBase*15)
			If Instr(Chat\message$,"DBStudios") Then
				Text3D(Text_Font[2],TchatX,TchatY,Chat\Message$)
			Else
				Text3D(Text_Font[6],TchatX,TchatY,Chat\Message$)
			EndIf
			If ChatBase=1 Then Exit
		Next
		
		If Force_UI_Mode = 0 Then	
			Local MapOX =GraphicsWidth()/2-(16+128)
			Local MapOZ =GraphicsHeight()/2-(16+128)
			
			DrawImage3D(GUI_Game[1],MapOX,MapOZ)
			
			DrawImage3D(GUI_Windows[30],D3DOL-420,0,0,0,3)
			DrawImage3D(GUI_Windows[21],D3DOL-270,0,0,0,3.8)
			
			Text3D(Text_Font[9],D3DOL+65, D3DOU-45,System_Name$)
			Text3D(Text_Font[6],D3DOL+65, D3DOU-65,System_Owner$)
			
			Text3D(Text_Font[6],0, D3DOU-45,Player_Environment_Shipname$+" ("+Player_Environment_ShipClass$+")",1)
			Text3D(Text_Font[6],0, D3DOU-62,Player_Environment_CurrentCargo+"m� / "+Player_Environment_FullCargo+"m�",1)
			
			Select System_Security
				Case 0
					Text3D(Text_Font[5],D3DOL+65, D3DOU-82,"Security Status: None")
				Case 1
					Text3D(Text_Font[5],D3DOL+65, D3DOU-82,"Security Status: Dangerous")
				Case 2
					Text3D(Text_Font[4],D3DOL+65, D3DOU-82,"Security Status: Low")
				Case 3
					Text3D(Text_Font[4],D3DOL+65, D3DOU-82,"Security Status: Medium")
				Case 4
					Text3D(Text_Font[3],D3DOL+65, D3DOU-82,"Security Status: High")
				Case 5
					Text3D(Text_Font[2],D3DOL+65, D3DOU-82,"Security Status: Fortress")
			End Select
			
			
			DrawImage3D(GUI_Game[10],0,GraphicsHeight()/2*-1+4,0,0,1.2)
			;General Ship Status Display
			Local SpeedMath = Floor(Player_Value_Speed_Current#*6)
			If Timer_Dock > 0 Then
				Text3D(Text_Font[4],0,GraphicsHeight()/2*-1+60,"Docking unavailable for "+Timer_Dock+" sec.",1)
			EndIf
			Text3D(Text_Font[4],0,GraphicsHeight()/2*-1+48,SpeedMath+" m/s",1)
			Text3D(Text_Font[2],-400,GraphicsHeight()/2*-1+48,"%",1)
			Text3D(Text_Font[3],+400,GraphicsHeight()/2*-1+48,"%",1)
			;Player Status Display
			Text3D(Text_Font[1],-550,GraphicsHeight()/2*-1+18,"Money: 3.141.597,34 Cr.")
			
			Select eCameraMode
				Case MODE_SHIP
					DrawImage3D(GUI_Game[17],MouseX3D,MouseY3D)
					DrawImage3D(GUI_Game[18],MouseX3D,MouseY3D)
				Case MODE_CAMERA
					DrawImage3D(GUI_Game[11],MouseX3D,MouseY3D)
				Case MODE_DOCKED
					DrawImage3D(GUI_Game[11],MouseX3D,MouseY3D)
			End Select
		ElseIf Force_UI_Mode= 1 Then
;			FlushMouse()
			Player_Value_Speed_Current = 0
			
			DrawImage3D(GUI_Game[11],MouseX3D,MouseY3D)
			; Company Owner
			DrawImage3D(GUI_Company[Station_Owner],D3DOR-144,D3DOU-144,0,0,1)
			Text3D(Text_Font[9],D3DOR-144, D3DOU-280,Station_Name$,1)
			
			Text3D(Text_Font[6],D3DOR-144, D3DOU-300,"Station offers Services:",1)
			
			Text3D(Text_Font[6],D3DOR-144, D3DOU-320,"Bar and Food Court",1)
			
			Text3D(Text_Font[6],D3DOR-144, D3DOU-340,"Market Import (Ores)",1)
			
			Text3D(Text_Font[6],D3DOR-144, D3DOU-360,"Market Export (Ores)",1)
			
			Text3D(Text_Font[6],D3DOR-144, D3DOU-380,"Equipment Dock",1)
			
			Text3D(Text_Font[6],D3DOR-144, D3DOU-400,"Shipyard",1)
			
			Text3D(Text_Font[6],D3DOR-144, D3DOU-420,"Your Funds:",1)
			
			Text3D(Text_Font[6],D3DOR-144, D3DOU-440,"13.021.215 KyD",1)
			
			Local Randomannouncer = Rand(1,5000)
			If Randomannouncer = 333 Then
				Local RandomAnnouncement = Rand(1,10)
				Local RandomNumber = RandomAnnouncement + 6
				PlaySound Sound_ID[RandomNumber]
			EndIf
			
			; Undock Button
			DrawImage3D(GUI_Game[19],D3DOR-144,D3DOD+128,0,0,0.5)
			If MouseX3D>D3DOR-144-64 And MouseX3D<D3DOR-144+64 And MouseY3D>D3DOD+128-64 And MouseY3D<D3DOD+128+64 Then
				If MouseHit(1) Then 
					Camera_Zoom_Speed#=5
					State_Docked_Submenu = 0
					World_Generate(Player_GlobalX,Player_GlobalY,0,0,0)
					Timer_Dock = 10
					Force_UI_Mode = 0
				EndIf
			EndIf
			
			;Sidebar Left
			DrawImage3D(GUI_Windows[30],D3DOL-420,0,0,0,3)
			DrawImage3D(GUI_Windows[21],D3DOL-270,0,0,0,3.8)
			
			DrawImage3D(GUI_Game[20],D3DOL+27,D3DOU-27,GUI_Game[21],0,0.75) ; Hangar
			
			
			DrawImage3D(GUI_Game[20],D3DOL+27,D3DOU-72,GUI_Game[21],0,0.75) ; Bar/Food Court
			
			
			DrawImage3D(GUI_Game[20],D3DOL+27,D3DOU-117,GUI_Game[21],0,0.75) ; Trader
			
			
			DrawImage3D(GUI_Game[20],D3DOL+27,D3DOU-162,GUI_Game[21],0,0.75) ; Equipment Dock
			
			
			DrawImage3D(GUI_Game[20],D3DOL+27,D3DOU-207,GUI_Game[21],0,0.75) ; Shipyard
			
			
			DrawImage3D(GUI_Game[20],D3DOL+27,D3DOD+72,GUI_Game[21],0,0.75)
			
			
			DrawImage3D(GUI_Game[20],D3DOL+27,D3DOD+27,GUI_Game[21],0,0.75)
			
			
			If MouseX3D > D3DOL+5 And MouseX3D<D3DOL+50 Then 
				If MouseY3D < D3DOU -5 And MouseY3D > D3DOU -49 Then
					If MouseHit(1) Then State_Docked_Submenu = 0
					Text3D(Text_Font[6], MouseX3D+15, MouseY3D,"Hangar")
				EndIf
				
				If MouseY3D < D3DOU -51 And MouseY3D > D3DOU -96 
					If MouseHit(1) Then State_Docked_Submenu = 1
					Text3D(Text_Font[6], MouseX3D+15, MouseY3D,"Bartender")
				EndIf
				
				If MouseY3D < D3DOU -98 And MouseY3D > D3DOU -144 
					If MouseHit(1) Then State_Docked_Submenu = 2
					Text3D(Text_Font[6], MouseX3D+15, MouseY3D,"Trading Shop")
				EndIf
				
				If MouseY3D < D3DOU -146 And MouseY3D > D3DOU -190 
					If MouseHit(1) Then State_Docked_Submenu = 3
					Text3D(Text_Font[6],MouseX3D+15, MouseY3D,"Equipment Workshop")
				EndIf
				
				If MouseY3D < D3DOU -192 And MouseY3D > D3DOU -236
					If MouseHit(1) Then State_Docked_Submenu = 4
					Text3D(Text_Font[6], MouseX3D+15, MouseY3D,"Internal Shipyard")	
				EndIf
				
				If MouseY3D > D3DOD +51 And MouseY3D < D3DOD +96
					If MouseHit(1) Then State_Docked_Submenu = 5
					Text3D(Text_Font[6], MouseX3D+15, MouseY3D,"Ship Cargo")	
				EndIf
				
				If MouseY3D > D3DOD +5 And MouseY3D < D3DOD +49 
					If MouseHit(1) Then State_Docked_Submenu = 6
					Text3D(Text_Font[6], MouseX3D+15, MouseY3D,"Options")	
				EndIf
			End If
			
			;Sidebar Right
			DrawImage3D(GUI_Windows[21],D3DOR-6,-50,0,0,3.8)
			
			;Main Station Services
			Select State_Docked_Submenu
				Case 0
					; Hangar View
					
				Case 1
					; Bartender for Jobs
;					End
				Case 2
					; Trader
					DrawImage3D(GUI_Windows[30],D3DOL+63,0,0,0,1.3)
					DrawImage3D(GUI_Windows[30],D3DOL-53,0,0,0,1.3)
					DrawImage3D(GUI_Windows[31],D3DOL+600,0,0,0,3.65)
;					End
				Case 3
					; Equipment Vendor / Workshop
;					End
				Case 4
					; Shipyard
;					End
				Case 5
					; Inventory
					InventoryShow = 1
;					End
				Case 6
					End
					; Options
			End Select
			
			If MouseHit(1) Then
			EndIf
			
		EndIf
	EndIf
	
	MoveEntity eShipBody,(Sin(Player_Effect_Drift_X)/78), (Sin(Player_Effect_Drift_Y)/110),0
	
	If Music_Enabled = 1 Then
		If Music_Volume#<Desired_MVolume# Then Music_Volume# = Music_Volume# + 0.005
		
		If Music_Volume#>Desired_MVolume# Then Music_Volume# = Music_Volume# - 0.005
	EndIf
	
	ChannelPitch PlayerChannel,8000+250*Player_Value_Speed_Current
	
End Function

Function Update_Camera()
	
End Function

Function FastTravel(FTX, FTY, FTZ)
;	PositionEntity FastTravelSpot, FTX, FTX, FTZ, True
	PositionEntity pvShip,FTX,FTY,FTZ,True
	
	Travel_To_LastSave=1
	Savecounter = Savecounter + 1
End Function

Function Environment_FastTravel_Update()
	
	If Travel_To_LastSave=1 Then
;		If Savecounter = 2 Then RuntimeError EntityX(FastTravelSpot) + " / " + EntityY(FastTravelSpot) + " / " + EntityZ(FastTravelSpot)
		Travel_To_LastSave=0
;		UserDataSave(1)
	EndIf
	
End Function

Function UI_Draw_Options()
	eCameraMode = MODE_DOCKED
	State_Docked_Submenu = 0	InventoryShow = 0
	HUD = 1
;	DrawImage3D(GUI_Windows[27],0,GraphicsHeight()/2-16,0,0,1.3)
;	Text3D(Text_Font[7],0,GraphicsHeight()/2-16,"S e t t i n g s",1)
	
	DrawImage3D(GUI_Windows[29],140,0,0,0,2)
	DrawImage3D(GUI_Windows[21],-420,0,0,0,1.47)
	
	DrawImage3D(GUI_Windows[19],-420,210,0,0,1.8)
	DrawImage3D(GUI_Windows[19],-420,170,0,0,1.8)
	DrawImage3D(GUI_Windows[19],-420,130,0,0,1.8)
	DrawImage3D(GUI_Windows[19],-420, 90,0,0,1.8)
	DrawImage3D(GUI_Windows[19],-420,-130,0,0,1.8)
	DrawImage3D(GUI_Windows[19],-420,-170,0,0,1.8)
	
	Select State_Menu_Subcontext_Settings
		Case 1 ; >> Graphics
			DrawImage3D(GUI_Windows[16],-420,210,0,0,1.8)
			Text3D(Text_Font[6],140,252,"G r a p h i c s",1)
			
;					Text X -250, Y - 230
			
			Text3D(Text_Font[7],-210,210,"S e t   u p   g r a p h i c s   o p t i o n s   h e r e .")
			
			Text3D(Text_Font[7],-210,160,"A m b i e n t   L i g h t")
			
			DrawImage3D(GUI_Windows[22],+170,160,0,90,1.5)
			DrawImage3D(GUI_Windows[22],+205,160,0,-90,1.5)
			
			DrawImage3D(GUI_Windows[23],+340,160,0,0,1.5)
			
			Local AmbientLevel = 10 + (11 - Settings_GFX_Ambience)*-1
			Text3D(Text_Font[7],+380,160,AmbientLevel,1)
			
			If Settings_GFX_Ambience > 11 Then Settings_GFX_Ambience = 11
			If Settings_GFX_Ambience < 1 Then Settings_GFX_Ambience = 1
			
			If MouseY()>GhBy2-170 And MouseY()<GhBy2-140
				If MouseX()>GwBy2+160 And MouseX()<GwBy2+180
					If MouseHit(1) And Settings_GFX_Ambience > 1 Then Settings_GFX_Ambience = Settings_GFX_Ambience - 1: PlaySound(Sound_ID[1]): Ambient_Update(System_SunR,System_SunG,System_SunB)
				EndIf
				If MouseX()>GwBy2+195 And MouseX()<GwBy2+215
					If MouseHit(1) And Settings_GFX_Ambience < 10 Then Settings_GFX_Ambience = Settings_GFX_Ambience + 1: PlaySound(Sound_ID[1]): Ambient_Update(System_SunR,System_SunG,System_SunB)
				EndIf
			EndIf
			
			Text3D(Text_Font[7],-210,130,"O b j e c t   A m o u n t")
			
			DrawImage3D(GUI_Windows[22],+170,130,0,90,1.5)
			DrawImage3D(GUI_Windows[22],+205,130,0,-90,1.5)
			
			DrawImage3D(GUI_Windows[23],+340,130,0,0,1.5)
			
			Local ObjectLevel = (Settings_GFX_Objects# * 100)
			Text3D(Text_Font[7],+380,130,ObjectLevel+"%",1)
			
			If Settings_GFX_Objects# > 2 Then Settings_GFX_Objects# = 2
			If Settings_GFX_Objects# < .2 Then Settings_GFX_Objects# = .2
			
			If MouseY()>GhBy2-140 And MouseY()<GhBy2-110
				If MouseX()>GwBy2+160 And MouseX()<GwBy2+180
					If MouseHit(1) Then Settings_GFX_Objects# = Settings_GFX_Objects# - .1: PlaySound(Sound_ID[1])
				EndIf
				If MouseX()>GwBy2+195 And MouseX()<GwBy2+215
					If MouseHit(1) Then Settings_GFX_Objects# = Settings_GFX_Objects# + .1: PlaySound(Sound_ID[1])
				EndIf
			EndIf
			
		Case 2 ; >> Audio
			
			DrawImage3D(GUI_Windows[16],-420,170,0,0,1.8)
			Text3D(Text_Font[6],140,252,"S o u n d",1)
			
			Text3D(Text_Font[7],-210,210,"S e t   u p   a u d i o   o p t i o n s   h e r e .")
			
		Case 3 ; >> Music
			DrawImage3D(GUI_Windows[16],-420,130,0,0,1.8)
			
			Text3D(Text_Font[6],140,252,"M u s i c",1)
			
			Text3D(Text_Font[7],-210,210,"S e t   u p   m u s i c   o p t i o n s   h e r e .")
			
			Text3D(Text_Font[7],-210,160,"M u s i c")
			Text3D(Text_Font[7],-210,130,"V o l u m e")
			
			If Music_Enabled = 1 Then
				DrawImage3D(GUI_Windows[24],+490,160)
				Text3D(Text_Font[7],+410,160,"O n",1)
			Else
				DrawImage3D(GUI_Windows[25],+490,160,0,180)
				Text3D(Text_Font[7],+410,160,"O f f",1)
			EndIf
			If MouseY()>GhBy2-170 And MouseY()<GhBy2-150
				If MouseX()>GwBy2+470 And MouseX()<GwBy2+510
					If MouseHit(1)
						If Music_Enabled = 1 Then
							Music_Enabled = 0
							Music_Volume = 0: PlaySound(Sound_ID[4])
						ElseIf Music_Enabled = 0 Then
							Music_Enabled = 1: PlaySound(Sound_ID[4])
						EndIf
					EndIf
				EndIf
			EndIf
			
			DrawImage3D(GUI_Windows[22],+170,130,0,90,1.5)
			DrawImage3D(GUI_Windows[22],+205,130,0,-90,1.5)
			
			DrawImage3D(GUI_Windows[23],+340,130,0,0,1.5)
			
			Local MusicVol = Desired_MVolume#*100
			Text3D(Text_Font[7],+380,130,MusicVol+" %",1)
			
			If MouseY()>GhBy2-140 And MouseY()<GhBy2-120
				If MouseX()>GwBy2+160 And MouseX()<GwBy2+180
					If MouseHit(1) Then Desired_MVolume = Desired_MVolume - 0.05: PlaySound(Sound_ID[1])
				EndIf
				If MouseX()>GwBy2+195 And MouseX()<GwBy2+215
					If MouseHit(1) Then Desired_MVolume = Desired_MVolume + 0.05: PlaySound(Sound_ID[1])
				EndIf
			EndIf
			
			If Desired_MVolume > 1 Then Desired_MVolume = 1
			If Desired_MVolume < 0 Then Desired_MVolume = 0
			
		Case 4 ; >> Other
			DrawImage3D(GUI_Windows[16],-420, 90,0,0,1.8)
			
			Text3D(Text_Font[6],140,252,"O t h e r",1)
			
			Text3D(Text_Font[7],-210,210,"S e t   u p   o t h e r   o p t i o n s   h e r e .")
			
	End Select
	
	DrawImage3D(GUI_Windows[19],-420,-220,0,0,1.8)
	
	If MouseX()>GraphicsWidth()/2-515 And MouseX()<GraphicsWidth()/2-330
		If MouseY()>(GraphicsHeight()/2)-228 And MouseY()<(GraphicsHeight()/2)-192
			DrawImage3D(GUI_Windows[17],-420,210,0,0,1.8)
			If MouseHit(1) Then State_Menu_Subcontext_Settings = 1: PlaySound(Sound_ID[1])
		ElseIf  MouseY()>(GraphicsHeight()/2)-190 And MouseY()<(GraphicsHeight()/2)-154
			DrawImage3D(GUI_Windows[17],-420,170,0,0,1.8)
			If MouseHit(1) Then State_Menu_Subcontext_Settings = 2: PlaySound(Sound_ID[1])
		ElseIf  MouseY()>(GraphicsHeight()/2)-152 And MouseY()<(GraphicsHeight()/2)-116
			DrawImage3D(GUI_Windows[17],-420,130,0,0,1.8)
			If MouseHit(1) Then State_Menu_Subcontext_Settings = 3: PlaySound(Sound_ID[1])
		ElseIf  MouseY()>(GraphicsHeight()/2)-114 And MouseY()<(GraphicsHeight()/2)-78
			DrawImage3D(GUI_Windows[17],-420, 90,0,0,1.8)
			If MouseHit(1) Then State_Menu_Subcontext_Settings = 4: PlaySound(Sound_ID[1])
		ElseIf  MouseY()>(GraphicsHeight()/2)+112 And MouseY()<(GraphicsHeight()/2)+148
			DrawImage3D(GUI_Windows[17],-420,-130,0,0,1.8)
			If MouseHit(1) Then PlaySound(Sound_ID[3]): State_Menu_Subcontext=1: Game_End=1 ;Anpassen Men�
		ElseIf  MouseY()>(GraphicsHeight()/2)+152 And MouseY()<(GraphicsHeight()/2)+188
			DrawImage3D(GUI_Windows[17],-420,-170,0,0,1.8)
			If MouseHit(1) Then PlaySound(Sound_ID[3]): Game_End=2 ;Anpassen ENDE
		ElseIf  MouseY()>(GraphicsHeight()/2)+202 And MouseY()<(GraphicsHeight()/2)+238
			DrawImage3D(GUI_Windows[16],-420,-220,0,0,1.8)
			If MouseHit(1) Then eCameraMode = MODE_CAMERA: Game_Menu_Open = 0: PlaySound(Sound_ID[3])
		EndIf
	EndIf
	
	
	If MouseHit(1) Then
		
	EndIf
	Text3D(Text_Font[7],-420,210,"G r a p h i c s",1)
	Text3D(Text_Font[7],-420,170,"S o u n d s",1)
	Text3D(Text_Font[7],-420,130,"M u s i c",1)
	Text3D(Text_Font[7],-420, 90,"O t h e r",1)
	
	Text3D(Text_Font[7],-420,-170,"Q u i t   G a m e",1)
	Text3D(Text_Font[7],-420,-130,"Q u i t  t o   M e n u",1)
	Text3D(Text_Font[7],-420,-220,"C o n f i r m",1)
End Function

;~IDEal Editor Parameters:
;~F#0#E7#EF#F9
;~C#Blitz3D