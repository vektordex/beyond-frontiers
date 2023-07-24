Function UpdateGraphics()
	;General UI Overlay
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

Function FastTravel(FTX#, FTY#, FTZ#)
	PositionEntity FastTravelSpot, FTX#, FTY#, FTZ#;, True
	Travel_To_LastSave=1
End Function

Function UpdateFastTravel()
	
	If Travel_To_LastSave=1 Then
		PositionEntity pvShip,EntityX(FastTravelSpot),EntityY(FastTravelSpot), EntityZ(FastTravelSpot)
		Travel_To_LastSave=0
		UserDataSave(1)
	EndIf
	
End Function


;~IDEal Editor Parameters:
;~C#Blitz3D