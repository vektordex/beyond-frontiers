Function UpdateGraphics()
	;General UI Overlay
	
	If HUD = 1 Then
			
		Local MapOX =GraphicsWidth()/2-(16+128)
		Local MapOZ =GraphicsHeight()/2-(16+128)
		DrawImage3D(GUI_Game[1],MapOX,MapOZ)
		
		DrawImage3D(GUI_Game[16],0,GraphicsHeight()/2,0,0,1.5)
		
		Text3D(Text_Font[9],D3DOL+45, D3DOU-45,System_Name$)
		Text3D(Text_Font[6],D3DOL+45, D3DOU-65,System_Owner$)
		
		Text3D(Text_Font[6],0, D3DOU-45,Player_Environment_Shipname$+" ("+Player_Environment_ShipClass$+")",1)
		
		Text3D(Text_Font[6],0, D3DOU-70 ,"Armor: "+Player_Environment_BaseArmor+" mm",1)
		Text3D(Text_Font[6],0, D3DOU-85 ,"Shield: "+Player_Environment_BaseShield+" MJ",1)
		Text3D(Text_Font[6],0, D3DOU-100,"Max Speed: "+(Player_Environment_BaseMSpeed*6)+" m/s (Accel: "+Player_Environment_BaseAccel+")",1)
		Text3D(Text_Font[6],0, D3DOU-115,"TurnRate: "+Player_Environment_BaseTurn#+" °/sec",1)
		Text3D(Text_Font[6],0, D3DOU-130,"Energy Supply: "+Player_Environment_BaseEnergy+" Wh",1)
		Text3D(Text_Font[6],0, D3DOU-145,Zoffset + "Camera Z Offset",1)
	;	Text3D(Text_Font[6],0, D3DOU-160,Player_Environment_Shipname$+" ("+Player_Environment_ShipClass$+")",1)
	;	Text3D(Text_Font[6],0, D3DOU-175,Player_Environment_Shipname$+" ("+Player_Environment_ShipClass$+")",1)
	;	
		
		Select System_Security
			Case 0
				Text3D(Text_Font[5],D3DOL+45, D3DOU-82,"Security Status: None")
			Case 1
				Text3D(Text_Font[5],D3DOL+45, D3DOU-82,"Security Status: Dangerous")
			Case 2
				Text3D(Text_Font[4],D3DOL+45, D3DOU-82,"Security Status: Low")
			Case 3
				Text3D(Text_Font[4],D3DOL+45, D3DOU-82,"Security Status: Medium")
			Case 4
				Text3D(Text_Font[3],D3DOL+45, D3DOU-82,"Security Status: High")
			Case 5
				Text3D(Text_Font[2],D3DOL+45, D3DOU-82,"Security Status: Fortress")
		End Select
		
		
		DrawImage3D(GUI_Game[10],0,GraphicsHeight()/2*-1+4,0,0,1.2)
		;General Ship Status Display
		Local SpeedMath = Floor(Player_Value_Speed_Current#*6)
		
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
	EndIf
	
	MoveEntity eShipBody,(Sin(Player_Effect_Drift_X)/78), (Sin(Player_Effect_Drift_Y)/110),0
	
	If Music_Enabled = 1 Then
		If Music_Volume#<Desired_MVolume# Then Music_Volume# = Music_Volume# + 0.005
		
		If Music_Volume#>Desired_MVolume# Then Music_Volume# = Music_Volume# - 0.005
	EndIf
	
End Function

Function Update_Camera()
	
End Function

Function FastTravel(FTX, FTY, FTZ)
;	PositionEntity FastTravelSpot, FTX, FTX, FTZ, True
	PositionEntity pvShip,FTX,FTY,FTZ
	
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
	
	DrawImage3D(GUI_Windows[27],0,GraphicsHeight()/2-16,0,0,2)
	Text3D(Text_Font[7],0,GraphicsHeight()/2-16,"S e t t i n g s",1)
	
	DrawImage3D(GUI_Windows[29],140,0)
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
			If MouseHit(1) Then PlaySound(Sound_ID[3]): State_Menu_Subcontext=1: Game_End=1 ;Anpassen Menü
		ElseIf  MouseY()>(GraphicsHeight()/2)+152 And MouseY()<(GraphicsHeight()/2)+188
			DrawImage3D(GUI_Windows[17],-420,-170,0,0,1.8)
			If MouseHit(1) Then PlaySound(Sound_ID[3]): Game_End=2 ;Anpassen ENDE
		ElseIf  MouseY()>(GraphicsHeight()/2)+202 And MouseY()<(GraphicsHeight()/2)+238
			DrawImage3D(GUI_Windows[16],-420,-220,0,0,1.8)
			If MouseHit(1) Then eCameraMode = MODE_CAMERA: Game_Menu_Open = 0: PlaySound(Sound_ID[3])
		EndIf
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
;~C#Blitz3D