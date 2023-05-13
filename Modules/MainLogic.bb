Function UpdateGraphics()
	
	Player_Effect_Drift_X = Player_Effect_Drift_X+1
	Player_Effect_Drift_Y = Player_Effect_Drift_Y+1
	
;	Music_Update()
	
	MoveEntity eShipBody,(Sin(Player_Effect_Drift_X)/78), (Sin(Player_Effect_Drift_Y)/110),0
	
End Function

Function Update_Camera()
;	PositionEntity Turret_Pivot, EntityX(Camera_Pivot), EntityY(Camera_Pivot), EntityZ(Camera_Pivot)
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