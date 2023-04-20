Function NewShot(gun, sourceid, btyp, offsetx, offsety, offsetz)
	Player.Player_Bullet=New Player_Bullet
	Player\pivot = CreatePivot()
;	ScaleEntity player\Pivot,2,2,1000
	
	player\SID=sourceid
	player\mesh=CopyEntity(Mesh_Weapon[1], player\pivot)
	player\scale=20
	player\guntype=gun
	
	EntityFX player\mesh,1+16
	If Twitch_Event_Timer > 0  Then
		Player_Value_Speed_Current=Twitch_Recoilspeed
	EndIf
	Select gun
		Case 1
			EntityTexture player\mesh, Text_Weapon[0]
			player\distance=6000
			ScaleEntity player\mesh,player\scale,player\scale,player\distance
			
			player\decay=50
		Case 2
			
			EntityTexture player\mesh, Text_Weapon[1]
			player\distance=6000
			ScaleEntity player\mesh,player\scale,player\scale,player\distance
			
			player\decay=40
		Case 3
			EntityTexture player\mesh, Text_Weapon[2]
			player\distance=6000
			ScaleEntity player\mesh,player\scale,player\scale,player\distance
			
			player\decay=30
		Case 4
			EntityTexture player\mesh, Text_Weapon[3]
			player\distance=6000
			ScaleEntity player\mesh,player\scale,player\scale,player\distance
			
			player\decay=30
			
		Default
			EntityTexture player\mesh, Text_Weapon[1]
			player\distance=6000
			ScaleEntity player\mesh,player\scale,player\scale,player\distance
			
			player\decay=Rnd(200,300)
	End Select
	
	VirtualScene_Register(Scene, player\pivot)
	
	PositionEntity player\pivot, EntityX(sourceid,True), EntityY(sourceid,True), EntityZ(sourceid,True)
	PointEntity player\Pivot,Weapon_Target_Cube
	
	TFormPoint offsetx, offsety, offsetz, sourceid, 0
	
	PositionEntity player\mesh, TFormedX(), TFormedY(), TFormedZ(),True
	RotateEntity player\mesh,EntityPitch(Player_Weapon_Array,True), EntityYaw(Player_Weapon_Array,True), EntityRoll(Player_Weapon_Array,True),True
	TurnEntity player\mesh,0,0,45
	
End Function

Function UpdateShot()
	
	For player.Player_Bullet = Each Player_Bullet
		
		PositionEntity player\pivot, EntityX(Player_Weapon_Array,True), EntityY(Player_Weapon_Array,True), EntityZ(Player_Weapon_Array,True)
		PointEntity player\Pivot,Weapon_Target_Cube
		
		TFormPoint(0, 0, player\distance, Player_Weapon_Array, 0)
		Local Linepick_Message = EntityPick(player\pivot, player\distance)
		
		player\decay=player\decay-1
		player\scale=player\scale/1.45
		
		ScaleEntity player\mesh,player\scale,player\scale, player\distance
		
		If player\decay < 1 Then
			FreeEntity player\mesh
			FreeEntity player\pivot:VirtualScene_Unregister(Scene, player\pivot)
			Delete player
		EndIf
	Next
	
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D