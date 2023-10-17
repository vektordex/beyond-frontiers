Function HandleInput()
	
	If Chat_Active=0  Then
		Chat_Counter = Chat_Counter - 1
		
		FlushKeys()
												; CONTROL PARADIGMS;		If KeyHit(20) Then World_Generate(1,1,0,0,0)
		
		If KeyHit(21)  Then
			ShipMake = ShipMake + 1
			If ShipMake > 13 Then ShipMake = 1
			PlayerSwitchShip(ShipMake)
		EndIf
		
		If KeyHit(22) Then ;(Key U)
			Asset_Emitter_Create(0,0,0,0,0,15,1,500)
		EndIf
		
		If KeyHit(23) Then ; Open/Close Inventory
			InventoryShow = 1 - InventoryShow
		EndIf
		
		If KeyHit(24) Then
			For A = 1 To 10
				Container_Create(Rand(-1000,1000),Rand(-1000,1000),Rand(-1000,1000),Rand(1,6),Rand(1,4))
			Next
		EndIf
		
		If KeyHit(57) Then 
			If eCameraMode = MODE_SHIP Then
				TimerRemind=1500
			EndIf
			MoveMouse GraphicsWidth()/2, GraphicsHeight()/2
			eCameraMode = 1 - eCameraMode
			If eCameraMode < 0 Then eCameraMode = 0
		EndIf
		
		If KeyHit(28) And Chat_Counter < 1 Then
			Chat_Message$ = ""
			Chat_Active = Chat_Active + 1
			If Chat_Active > 2 Then Chat_Active = 0
		EndIf
		
		If KeyDown(42)=False And Chat_Active = 0 Then				; GENERAL SHIP CONTROLS
			
			If KeyDown(17) Then							; MOVE FORWARD
				Player_Value_Movement_State=1
				Player_Value_Speed_Target#=Player_Value_Speed_Target#+Player_Environment_BaseAccel#
			EndIf
			
			If KeyDown(31) Then							; BRAKE SHIP
				Player_Value_Movement_State=-1
				Player_Value_Speed_Target#= Player_Value_Speed_Target#-Player_Environment_BaseAccel#
			EndIf
			
			If KeyDown(16) And ShipRollZ#<5 Then		; ROLL SHIP
				ShipRollZ#=ShipRollZ#+(.1*Player_Environment_BaseTurn)
			ElseIf KeyDown(18) And ShipRollZ#>-5 Then
				ShipRollZ#=ShipRollZ#-(.1*Player_Environment_BaseTurn)
			Else 
				ShipRollZ#=ShipRollZ#*0.95
			EndIf
			
			If KeyDown(32) And ShipStrafeX#<7.5 Then	; MOVE LEFT/RIGHT
				ShipStrafeX#=ShipStrafeX#+(.1*Player_Environment_BaseTurn)
			ElseIf KeyDown(30) And ShipStrafeX#>-7.5 Then
				ShipStrafeX#=ShipStrafeX#-(.1*Player_Environment_BaseTurn)
			Else 
				ShipStrafeX#=ShipStrafeX#*0.95
			EndIf
			
			If KeyDown(19) And ShipStrafeY#<7.5 Then	; MOVE UP/DOWN
				ShipStrafeY#=ShipStrafeY#+(.1*Player_Environment_BaseTurn)
			ElseIf KeyDown(33) And ShipStrafeY#>-7.5 Then
				ShipStrafeY#=ShipStrafeY#-(.1*Player_Environment_BaseTurn)
			Else 
				ShipStrafeY#=ShipStrafeY#*0.95
			EndIf
			
			If KeyHit(35) Then donothing=1
			
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
			Else
			EndIf
		EndIf
		
		If KeyDown(42)=False And KeyDown(54)=False		; NON-SHIFT ACTIONS ---
			
			If KeyHit(50) Then 				; MAP RESCALING --- M
				Trigger_Map_Scaling = Trigger_Map_Scaling + 1
				If Trigger_Map_Scaling > 4 Then Trigger_Map_Scaling = 1
				UpdateMapScale(Trigger_Map_Scaling)
				
			EndIf
			
			If KeyDown(15) And eCameraMode<>MODE_CAMERA Then 
;				Player_Value_Boost_State = 1
;				Player_Value_Speed_Target#=Player_Value_Speed_Maximum*6
			ElseIf KeyDown(15)<>1 Then
;				Player_Value_Boost_State = 0
			EndIf
			
			If KeyHit(25) Then 
				Game_Menu_Debug = 1 - Game_Menu_Debug
			EndIf
			
		EndIf
		
	EndIf
	
	If Chat_Active = 1
		;[Block] Key Handling
		If InputEx_KeyDown(KEY_SHIFT_LEFT) = True
			If InputEx_KeyHit(KEY_A) Then Chat_Message$=Chat_Message$+"A": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_B) Then Chat_Message$=Chat_Message$+"B": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_C) Then Chat_Message$=Chat_Message$+"C": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_D) Then Chat_Message$=Chat_Message$+"D": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_E) Then Chat_Message$=Chat_Message$+"E": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_F) Then Chat_Message$=Chat_Message$+"F": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_G) Then Chat_Message$=Chat_Message$+"G": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_H) Then Chat_Message$=Chat_Message$+"H": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_I) Then Chat_Message$=Chat_Message$+"I": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_J) Then Chat_Message$=Chat_Message$+"J": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_K) Then Chat_Message$=Chat_Message$+"K": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_L) Then Chat_Message$=Chat_Message$+"L": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_M) Then Chat_Message$=Chat_Message$+"M": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_N) Then Chat_Message$=Chat_Message$+"N": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_O) Then Chat_Message$=Chat_Message$+"O": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_P) Then Chat_Message$=Chat_Message$+"P": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_Q) Then Chat_Message$=Chat_Message$+"Q": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_R) Then Chat_Message$=Chat_Message$+"R": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_S) Then Chat_Message$=Chat_Message$+"S": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_T) Then Chat_Message$=Chat_Message$+"T": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_U) Then Chat_Message$=Chat_Message$+"U": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_V) Then Chat_Message$=Chat_Message$+"V": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_W) Then Chat_Message$=Chat_Message$+"W": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_X) Then Chat_Message$=Chat_Message$+"X": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_Y) Then Chat_Message$=Chat_Message$+"Z": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_Z) Then Chat_Message$=Chat_Message$+"Y": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_1) Then Chat_Message$=Chat_Message$+"!": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_2) Then Chat_Message$=Chat_Message$+"'": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_3) Then Chat_Message$=Chat_Message$+"§": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_4) Then Chat_Message$=Chat_Message$+"$": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_5) Then Chat_Message$=Chat_Message$+"%": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_6) Then Chat_Message$=Chat_Message$+"&": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_7) Then Chat_Message$=Chat_Message$+"/": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_8) Then Chat_Message$=Chat_Message$+"(": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_9) Then Chat_Message$=Chat_Message$+")": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_0) Then Chat_Message$=Chat_Message$+"?": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_DASH) Then Chat_Message$=Chat_Message$+"_": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_PERIOD) Then Chat_Message$=Chat_Message$+":": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_PLUS) Then Chat_Message$=Chat_Message$+"*": PlaySound(Sound_ID[1])
		Else
			If InputEx_KeyHit(KEY_A) Then Chat_Message$=Chat_Message$+"a": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_B) Then Chat_Message$=Chat_Message$+"b": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_C) Then Chat_Message$=Chat_Message$+"c": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_D) Then Chat_Message$=Chat_Message$+"d": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_E) Then Chat_Message$=Chat_Message$+"e": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_F) Then Chat_Message$=Chat_Message$+"f": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_G) Then Chat_Message$=Chat_Message$+"g": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_H) Then Chat_Message$=Chat_Message$+"h": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_I) Then Chat_Message$=Chat_Message$+"i": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_J) Then Chat_Message$=Chat_Message$+"j": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_K) Then Chat_Message$=Chat_Message$+"k": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_L) Then Chat_Message$=Chat_Message$+"l": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_M) Then Chat_Message$=Chat_Message$+"m": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_N) Then Chat_Message$=Chat_Message$+"n": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_O) Then Chat_Message$=Chat_Message$+"o": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_P) Then Chat_Message$=Chat_Message$+"p": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_Q) Then Chat_Message$=Chat_Message$+"q": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_R) Then Chat_Message$=Chat_Message$+"r": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_S) Then Chat_Message$=Chat_Message$+"s": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_T) Then Chat_Message$=Chat_Message$+"t": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_U) Then Chat_Message$=Chat_Message$+"u": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_V) Then Chat_Message$=Chat_Message$+"v": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_W) Then Chat_Message$=Chat_Message$+"w": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_X) Then Chat_Message$=Chat_Message$+"x": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_Y) Then Chat_Message$=Chat_Message$+"z": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_Z) Then Chat_Message$=Chat_Message$+"y": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_1) Then Chat_Message$=Chat_Message$+"1": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_2) Then Chat_Message$=Chat_Message$+"2": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_3) Then Chat_Message$=Chat_Message$+"3": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_4) Then Chat_Message$=Chat_Message$+"4": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_5) Then Chat_Message$=Chat_Message$+"5": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_6) Then Chat_Message$=Chat_Message$+"6": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_7) Then Chat_Message$=Chat_Message$+"7": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_8) Then Chat_Message$=Chat_Message$+"8": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_9) Then Chat_Message$=Chat_Message$+"9": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_0) Then Chat_Message$=Chat_Message$+"0": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_DASH) Then Chat_Message$=Chat_Message$+"-": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_PERIOD) Then Chat_Message$=Chat_Message$+".": PlaySound(Sound_ID[1])
			If InputEx_KeyHit(KEY_PLUS) Then Chat_Message$=Chat_Message$+"+": PlaySound(Sound_ID[1])
		EndIf
		If InputEx_KeyHit(KEY_SPACE) Then Chat_Message$=Chat_Message$+" ": PlaySound(Sound_ID[1])
		
		If InputEx_KeyDown(KEY_BACKSPACE) And Chat_Message$>"" Then 
			Chat_Usage_Counter=Chat_Usage_Counter+1
			If Chat_Usage_Counter>7 Then 
				Chat_Message$=Mid(Chat_Message$,1,Len(Chat_Message$)-1) 
				Chat_Usage_Counter=0
				PlaySound(Sound_ID[1])
			EndIf
		EndIf
		
		If InputEx_KeyDown(KEY_RETURN) And Chat_Message$>"" Then 
			WriteLine ChatStream, "PRIVMSG #beyondfrontiers :"+Chat_Message$
			AddChat(" ("+System_Name$+")]: "+ Chat_Message$,"["+Character_NewName$)
			Chat_Message$=""
			Chat_Active = 0
			Chat_Counter = 60
			FlushKeys
		EndIf
		
;		;[End Block]
		Text3D(Text_Font[9],0,-50,Chat_Message$,1)
	Else
		WipeKeyEx()
	EndIf
	
	If Player_Value_Movement_State = -1 Then
		If Player_Value_Speed_Current#>Player_Value_Speed_Target# Then
			Player_Value_Speed_Current#=Player_Value_Speed_Current#*(.98)
		EndIf
	EndIf
	
	If Player_Value_Movement_State = 1 Then
		If Player_Value_Speed_Current < .1 Then Player_Value_Speed_Current = .1
		If Player_Value_Speed_Current#<Player_Value_Speed_Target# Then
			Player_Value_Speed_Current#=Player_Value_Speed_Current#+Player_Environment_BaseAccel
		EndIf
	EndIf
	
	If Player_Value_Speed_Target# > Player_Value_Speed_Maximum Then
		Player_Value_Speed_Target = Player_Value_Speed_Maximum
	EndIf
	
	If Player_Value_Speed_Target# > Player_Value_Speed_Maximum*6 And Player_Value_Boost_State = 1 Then
		Player_Value_Speed_Target = Player_Value_Speed_Maximum*6
	EndIf
	
	
	If Player_Value_Speed_Target# < 0 Then
		Player_Value_Speed_Target = 0
	EndIf
	
	If Settings_GFX_Ambience > 11 Then Settings_GFX_Ambience = 11
	If Settings_GFX_Ambience < 1 Then Settings_GFX_Ambience = 1
	
End Function

Function HandleMouse()
	
End Function

Function WipeKeyEx()
	If InputEx_KeyDown(KEY_SHIFT_LEFT) = True
		If InputEx_KeyHit(KEY_A) Then DoNothing = 0
		If InputEx_KeyHit(KEY_B) Then DoNothing = 0
		If InputEx_KeyHit(KEY_C) Then DoNothing = 0
		If InputEx_KeyHit(KEY_D) Then DoNothing = 0
		If InputEx_KeyHit(KEY_E) Then DoNothing = 0
		If InputEx_KeyHit(KEY_F) Then DoNothing = 0
		If InputEx_KeyHit(KEY_G) Then DoNothing = 0
		If InputEx_KeyHit(KEY_H) Then DoNothing = 0
		If InputEx_KeyHit(KEY_I) Then DoNothing = 0
		If InputEx_KeyHit(KEY_J) Then DoNothing = 0
		If InputEx_KeyHit(KEY_K) Then DoNothing = 0
		If InputEx_KeyHit(KEY_L) Then DoNothing = 0
		If InputEx_KeyHit(KEY_M) Then DoNothing = 0
		If InputEx_KeyHit(KEY_N) Then DoNothing = 0
		If InputEx_KeyHit(KEY_O) Then DoNothing = 0
		If InputEx_KeyHit(KEY_P) Then DoNothing = 0
		If InputEx_KeyHit(KEY_Q) Then DoNothing = 0
		If InputEx_KeyHit(KEY_R) Then DoNothing = 0
		If InputEx_KeyHit(KEY_S) Then DoNothing = 0
		If InputEx_KeyHit(KEY_T) Then DoNothing = 0
		If InputEx_KeyHit(KEY_U) Then DoNothing = 0
		If InputEx_KeyHit(KEY_V) Then DoNothing = 0
		If InputEx_KeyHit(KEY_W) Then DoNothing = 0
		If InputEx_KeyHit(KEY_X) Then DoNothing = 0
		If InputEx_KeyHit(KEY_Y) Then DoNothing = 0
		If InputEx_KeyHit(KEY_Z) Then DoNothing = 0
		If InputEx_KeyHit(KEY_1) Then DoNothing = 0
		If InputEx_KeyHit(KEY_2) Then DoNothing = 0
		If InputEx_KeyHit(KEY_3) Then DoNothing = 0
		If InputEx_KeyHit(KEY_4) Then DoNothing = 0
		If InputEx_KeyHit(KEY_5) Then DoNothing = 0
		If InputEx_KeyHit(KEY_6) Then DoNothing = 0
		If InputEx_KeyHit(KEY_7) Then DoNothing = 0
		If InputEx_KeyHit(KEY_8) Then DoNothing = 0
		If InputEx_KeyHit(KEY_9) Then DoNothing = 0
		If InputEx_KeyHit(KEY_0) Then DoNothing = 0
		If InputEx_KeyHit(KEY_DASH) Then DoNothing = 0
		If InputEx_KeyHit(KEY_PERIOD) Then DoNothing = 0
		If InputEx_KeyHit(KEY_PLUS) Then DoNothing = 0
	Else
		If InputEx_KeyHit(KEY_A) Then DoNothing = 0
		If InputEx_KeyHit(KEY_B) Then DoNothing = 0
		If InputEx_KeyHit(KEY_C) Then DoNothing = 0
		If InputEx_KeyHit(KEY_D) Then DoNothing = 0
		If InputEx_KeyHit(KEY_E) Then DoNothing = 0
		If InputEx_KeyHit(KEY_F) Then DoNothing = 0
		If InputEx_KeyHit(KEY_G) Then DoNothing = 0
		If InputEx_KeyHit(KEY_H) Then DoNothing = 0
		If InputEx_KeyHit(KEY_I) Then DoNothing = 0
		If InputEx_KeyHit(KEY_J) Then DoNothing = 0
		If InputEx_KeyHit(KEY_K) Then DoNothing = 0
		If InputEx_KeyHit(KEY_L) Then DoNothing = 0
		If InputEx_KeyHit(KEY_M) Then DoNothing = 0
		If InputEx_KeyHit(KEY_N) Then DoNothing = 0
		If InputEx_KeyHit(KEY_O) Then DoNothing = 0
		If InputEx_KeyHit(KEY_P) Then DoNothing = 0
		If InputEx_KeyHit(KEY_Q) Then DoNothing = 0
		If InputEx_KeyHit(KEY_R) Then DoNothing = 0
		If InputEx_KeyHit(KEY_S) Then DoNothing = 0
		If InputEx_KeyHit(KEY_T) Then DoNothing = 0
		If InputEx_KeyHit(KEY_U) Then DoNothing = 0
		If InputEx_KeyHit(KEY_V) Then DoNothing = 0
		If InputEx_KeyHit(KEY_W) Then DoNothing = 0
		If InputEx_KeyHit(KEY_X) Then DoNothing = 0
		If InputEx_KeyHit(KEY_Y) Then DoNothing = 0
		If InputEx_KeyHit(KEY_Z) Then DoNothing = 0
		If InputEx_KeyHit(KEY_1) Then DoNothing = 0
		If InputEx_KeyHit(KEY_2) Then DoNothing = 0
		If InputEx_KeyHit(KEY_3) Then DoNothing = 0
		If InputEx_KeyHit(KEY_4) Then DoNothing = 0
		If InputEx_KeyHit(KEY_5) Then DoNothing = 0
		If InputEx_KeyHit(KEY_6) Then DoNothing = 0
		If InputEx_KeyHit(KEY_7) Then DoNothing = 0
		If InputEx_KeyHit(KEY_8) Then DoNothing = 0
		If InputEx_KeyHit(KEY_9) Then DoNothing = 0
		If InputEx_KeyHit(KEY_0) Then DoNothing = 0
		If InputEx_KeyHit(KEY_DASH) Then DoNothing = 0
		If InputEx_KeyHit(KEY_PERIOD) Then DoNothing = 0
		If InputEx_KeyHit(KEY_PLUS) Then DoNothing = 0
		
;						If InputEx_KeyHit(KEY_L) Then Character_NewName$=Character_NewName$+"L"
;						If InputEx_KeyHit(KEY_L) Then Character_NewName$=Character_NewName$+"L"
	EndIf	
End Function


;~IDEal Editor Parameters:
;~F#10D
;~C#Blitz3D