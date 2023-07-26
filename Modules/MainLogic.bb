Function UpdateGraphics()
	;General UI Overlay
	Local MapOX =GraphicsWidth()/2-(16+128)
	Local MapOZ =GraphicsHeight()/2-(16+128)
	DrawImage3D(GUI_Game[1],MapOX,MapOZ)
	
	Text3D(Text_Font[9],D3DOL+45, D3DOU-45,System_Name$)
	Text3D(Text_Font[6],D3DOL+45, D3DOU-65,System_Owner$)
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
	
	
	DrawImage3D(GUI_Game[11],MouseX3D,MouseY3D)
	DrawImage3D(GUI_Game[10],0,GraphicsHeight()/2*-1+4,0,0,1.2)
	;General Ship Status Display
	Local SpeedMath = Floor(Player_Value_Speed_Current#*4)
	
	Text3D(Text_Font[4],0,GraphicsHeight()/2*-1+48,SpeedMath+" m/s",1)
	Text3D(Text_Font[2],-400,GraphicsHeight()/2*-1+48,"shield goes here",1)
	Text3D(Text_Font[3],+400,GraphicsHeight()/2*-1+48,"Armor goes here",1)
	;Player Status Display
	Text3D(Text_Font[1],-550,GraphicsHeight()/2*-1+18,"Money: 3.141.597,34 Cr.")
	
	
	MoveEntity eShipBody,(Sin(Player_Effect_Drift_X)/78), (Sin(Player_Effect_Drift_Y)/110),0
	
End Function

Function Update_Camera()
	
End Function

Function FastTravel(FTX, FTY, FTZ)
;	PositionEntity FastTravelSpot, FTX, FTX, FTZ, True
	PositionEntity pvShip,FTX,FTY,FTZ
	Travel_To_LastSave=1
End Function

Function Environment_FastTravel_Update()
	
	If Travel_To_LastSave=1 Then
;		
		Travel_To_LastSave=0
;		UserDataSave(1)
	EndIf
	
End Function


;~IDEal Editor Parameters:
;~C#Blitz3D