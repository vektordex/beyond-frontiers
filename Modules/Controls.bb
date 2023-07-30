Function HandleInput()
	
	If KeyMode=0 Then
												; CONTROL PARADIGMS
		If KeyHit(52) ;PgUp
			Render_A#=Render_A#+0.025
		EndIf
		
		If KeyHit(51) ;PgDn
			Render_A#=Render_A#-0.025
		EndIf
		
		If KeyHit(38) ;PgUp
			Render_B#=Render_B#+0.025
		EndIf
		
		If KeyHit(37) ;PgDn
			Render_B#=Render_B#-0.025
		EndIf				If KeyHit(20) Then World_Generate(1,1,0,0,0)
		If KeyHit(21) Then
			ShipMake = ShipMake + 1
			If ShipMake > 11 Then ShipMake = 1
			PlayerSwitchShip(ShipMake)
		EndIf
		
		If Render_A#>0.5 Then Render_A#=0.5
		If Render_B#>2 Then Render_B#=2
		If Render_A#<0 Then Render_A#=0
		If Render_B#<0 Then Render_B#=0
		
		
		If KeyHit(57) Then 
			If eCameraMode = MODE_SHIP Then
				TimerRemind=1500
			EndIf
			MoveMouse GraphicsWidth()/2, GraphicsHeight()/2
			eCameraMode = 1 - eCameraMode
			If eCameraMode < 0 Then eCameraMode = 0
		EndIf
		
		If KeyDown(42)=False And KeyMode=0 Then				; GENERAL SHIP CONTROLS
			
			If KeyDown(17) Then							; MOVE FORWARD
				Player_Value_Movement_State=1
				Player_Value_Speed_Target#=Player_Value_Speed_Target#+0.5;(0.5*Player_Value_Inertia_Positive)
			EndIf
			
			If KeyDown(31) Then							; BRAKE SHIP
				Player_Value_Movement_State=-1
				Player_Value_Speed_Target#= Player_Value_Speed_Target# -0.5;(.5*Player_Value_Inertia_Negative)
			EndIf
			
			If KeyDown(16) And ShipRollZ#<5 Then		; ROLL SHIP
				ShipRollZ#=ShipRollZ#+.1;(.1*Player_Value_Inertia_Modifier#)
			ElseIf KeyDown(18) And ShipRollZ#>-5 Then
				ShipRollZ#=ShipRollZ#-.1;(.1*Player_Value_Inertia_Modifier#)
			Else 
				ShipRollZ#=ShipRollZ#*0.9
			EndIf
			
			If KeyDown(32) And ShipStrafeX#<7.5 Then	; MOVE LEFT/RIGHT
				ShipStrafeX#=ShipStrafeX#+.2;(.2*Player_Value_Inertia_Modifier#)
			ElseIf KeyDown(30) And ShipStrafeX#>-7.5 Then
				ShipStrafeX#=ShipStrafeX#-.2;(.2*Player_Value_Inertia_Modifier#)
			Else 
				ShipStrafeX#=ShipStrafeX#*0.9
			EndIf
			
			If KeyDown(19) And ShipStrafeY#<7.5 Then	; MOVE UP/DOWN
				ShipStrafeY#=ShipStrafeY#+.2;(.2*Player_Value_Inertia_Modifier#)
			ElseIf KeyDown(33) And ShipStrafeY#>-7.5 Then
				ShipStrafeY#=ShipStrafeY#-.2;(.2*Player_Value_Inertia_Modifier#)
			Else 
				ShipStrafeY#=ShipStrafeY#*0.9
			EndIf
			
		End If
		
		If KeyDown(42) Or KeyDown(54)  And KeyMode=0			; SHIFT ACTIONS ---
			
			If KeyHit(35) Then 					; HUD DISABLING
				HUD=1-HUD
			ElseIf KeyHit(47) Then 					; SCANNER ON
				Ship_Function_Scanner=1-Ship_Function_Scanner
			ElseIf KeyHit(25)						; Debug Info Screen
				DebugInfo_Enabled=1-DebugInfo_Enabled
			ElseIf KeyHit(34) Then					; ECLIPSE PROJECTING
				Ship_Function_Projector=1-Ship_Function_Projector
			ElseIf KeyHit(48) Then
				Upgrade_GlobalMap = 1 - Upgrade_GlobalMap ;WORLD MAP
				MAPHUD = 1 - MAPHUD
			ElseIf KeyHit(24) Then
				
;				World_Generate(Rand(0,127),0,0,0)
			EndIf
		EndIf
		
		If KeyDown(42)=False And KeyDown(54)=False		; NON-SHIFT ACTIONS ---
			
			If KeyHit(50) Then 				; MAP RESCALING --- M
				Trigger_Map_Scaling = Trigger_Map_Scaling + 1
				If Trigger_Map_Scaling > 4 Then Trigger_Map_Scaling = 1
				UpdateMapScale(Trigger_Map_Scaling)
				
			EndIf
			
			If KeyDown(15) And eCameraMode<>MODE_CAMERA Then 
				Player_Value_Boost_State = 1
				Player_Value_Speed_Target#=Player_Value_Speed_Maximum*6
			ElseIf KeyDown(15)<>1 Then
				Player_Value_Boost_State = 0
			EndIf
			
		EndIf
		
	EndIf
	
	If Player_Value_Movement_State = -1 Then
		If Player_Value_Speed_Current#>Player_Value_Speed_Target# Then
			Player_Value_Speed_Current#=Player_Value_Speed_Current#*.90
		EndIf
	EndIf
	
	If Player_Value_Movement_State = 1 Then
		If Player_Value_Speed_Current < .1 Then Player_Value_Speed_Current = .1
		If Player_Value_Speed_Current#<Player_Value_Speed_Target# Then
			Player_Value_Speed_Current#=Player_Value_Speed_Current#*1.05
		EndIf
		If Player_Value_Boost_State = 0 Then
			If Player_Value_Speed_Current#>Player_Value_Speed_Target# Then
				Player_Value_Speed_Current#=Player_Value_Speed_Target#
			EndIf
			If Player_Value_Speed_Current#>Player_Value_Speed_Maximum Then
				Player_Value_Speed_Current#=Player_Value_Speed_Maximum
			EndIf
		EndIf
	EndIf
	
	If Player_Value_Speed_Target# > Player_Value_Speed_Maximum Then
		If Player_Value_Boost_State = 0 Then
			Player_Value_Speed_Target = Player_Value_Speed_Maximum
		EndIf
	EndIf
	
	If Player_Value_Speed_Target# > Player_Value_Speed_Maximum*6 And Player_Value_Boost_State = 1 Then
		Player_Value_Speed_Target = Player_Value_Speed_Maximum*6
	EndIf
	
	
	If Player_Value_Speed_Target# < 0 Then
		Player_Value_Speed_Target = 0
	EndIf
	
End Function

Function HandleMouse()
	
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D