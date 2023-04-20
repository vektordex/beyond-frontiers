Function Respawn_Create(x,y,z)
	RSP.Respawn = New Respawn
	RSP\x=x
	RSP\y=y
	RSP\z=z
	RSP\Mesh=CreateCube()
	PositionEntity RSP\Mesh,x,y,z
End Function

Function Respawn_Update()
	For RSP.Respawn = Each Respawn
		If EntityDistance (pvShip,RSP\Mesh)<1000 Then
			Respawn_X=RSP\X
			Respawn_Y=RSP\Y
			Respawn_Z=RSP\Z
		EndIf
	Next
	
End Function

Function UpdateLevels()
	Level_XP_Needed = (150*Character_Value_Level*Character_Value_Level)
	
	Level_Progress = ((Character_Value_XP*100)/Level_XP_Needed)
	
	If Character_Value_XP>Level_XP_Needed Then
		Character_Value_XP=Character_Value_XP-(150*Character_Value_Level*Character_Value_Level)
		Character_Value_Level=Character_Value_Level+1
		CreateLevelWave(EntityX(pvShip),EntityY(pvShip), EntityZ(pvShip))
		PlaySound Sound_UI[0]
		PlaySound Sound_Battlefield[9]
		
		Player_Value_Shield_Current = Player_Value_Shield_Maximum
		Player_Value_Armor_Current = Player_Value_Armor_Maximum
	EndIf
	
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D