;------------------------------------------------------------------------------
;! Command Line Parsing
;------------------------------------------------------------------------------

;[Block] Command Line Stuff
Function ParseCmdLine(CmdLine$) ; Correctly parse the commandline that Blitz murders.
	Local t_Pos%, t_Option$, t_Quoted%, t_Escaped%
	Local t_Key$, t_Value$, t_Equal%
	
	LogMessage(LOG_DEBUG, "ParseCmdLine: " + CmdLine)
	For t_Pos = 1 To Len(CmdLine)
		Local t_Char$ = Mid(CmdLine, t_Pos, 1)
		
		If t_Escaped = False
			Select Asc(t_Char)
				Case 92 ; \ for escaping
					t_Escaped = True
				Case 92 ; " for quoting text.
					t_Quoted = True - t_Quoted
				Case 32 ; Space splits options unless quoted.
					If t_Quoted Then
						t_Option = t_Option + t_Char
					Else
						t_Equal = Instr(t_Option$, "=")
						If t_Equal > 0 Then
							t_Key = Left(t_Option$, t_Equal-1)
							t_Value = Mid(t_Option$, t_Equal+1)
						Else
							t_Key = t_Option
						EndIf
						
						ParseCmdLineOption(t_Key, t_Value)
						t_Option = ""
					EndIf
				Default
					t_Option = t_Option + t_Char
			End Select
		ElseIf t_Escaped = True
			Select Asc(t_Char)
				Case 34 ;\" - A quote character.
					t_Option = t_Option + Chr(34)
				Case 92 ;\\ - A backslash
					t_Option = t_Option + Chr(92)
				Case 110 ;\n - New Line
					t_Option = t_Option + Chr(10)
				Case 114 ;\r - Column Reset
					t_Option = t_Option + Chr(13)
				Case 116 ;\t - Horizontal Tabular
					t_Option = t_Option + Chr(9)
				Default
					t_Option = t_Option + t_Char
			End Select
			t_Escaped = False
		End If
		
		If t_Pos = Len(CmdLine) Then
			t_Equal = Instr(t_Option$, "=")
			If t_Equal > 0 Then
				t_Key = Left(t_Option$, t_Equal-1)
				t_Value = Mid(t_Option$, t_Equal+1)
			Else
				t_Key = t_Option
			EndIf
			
			ParseCmdLineOption(t_Key, t_Value)
			t_Option = ""
		EndIf
	Next
End Function

Function ParseCmdLineOption(Key$, Value$) ; Parse Options
	Local adapterIndex, adapterCount, adapterText$
	LogMessage(LOG_DEBUG, "ParseCmdLine: " + Key + " = " + Value)
	Select Key
		Case "-help"
			Local usageText$
			
			usageText = "Command Line Parameters:"
			usageText = usageText + Chr(10)+Chr(13) + " -help - This window."
			usageText = usageText + Chr(10)+Chr(13) + " -adapter=<ID> - Select a specific Adapter to use, <ID> being the index of the adapter."
			usageText = usageText + Chr(10)+Chr(13) + " -adapter-list - List all known Adapters that we can force usage of."
			usageText = usageText + Chr(10)+Chr(13) + " -width=<x>,-w=<x> - Sets Width to be <x>."
			usageText = usageText + Chr(10)+Chr(13) + " -height=<x>,-h=<x> - Sets Width to be <x>."
			usageText = usageText + Chr(10)+Chr(13) + " -fullscreen - Force fullscreen mode."
			usageText = usageText + Chr(10)+Chr(13) + " -windowed - Force windowed mode."
			usageText = usageText + Chr(10)+Chr(13) + " -borderless - Force borderless windowed mode."
			
			
			RuntimeError usageText
			End
		Case "-adapter"
			adapterCount = CountGfxDrivers()
			adapterIndex = Int(Value)
			If (adapterIndex > adapterCount) Or (adapterIndex <= 0) Then
				RuntimeError "Adapter could not be found, make sure you are passing a valid id." + Chr(10) + Chr(13) + "If you are not sure what to pass, use -adapter-list to find out."
				End
			EndIf
			
			SetGfxDriver adapterIndex
		Case "-adapter-list"
			adapterCount = CountGfxDrivers()
			
			adapterText = "The following Adapters are installed on your system:"
			For adapterIndex = 1 To adapterCount
				adapterText = adapterText + Chr(10)+Chr(13) + "[ID "+adapterIndex + "] " + GfxDriverName(adapterIndex) + " (3D Capable: " + BoolToString(GfxDriver3D(adapterIndex)) + ")"
			Next
			
			RuntimeError adapterText
			End
			
		Case "-fullscreen"
			Settings_Graphics_Mode = GRAPHICS_MODE_FULLSCREEN
			LogMessage(LOG_INFO, "CmdLine: Set Graphics Mode to Fullscreen.")
		Case "-windowed"
			Settings_Graphics_Mode = GRAPHICS_MODE_WINDOWED
			LogMessage(LOG_INFO, "CmdLine: Set Graphics Mode to Windowed.")
		Case "-borderless"
			Settings_Graphics_Mode = GRAPHICS_MODE_BORDERLESS
			LogMessage(LOG_INFO, "CmdLine: Set Graphics Mode to Borderless.")
		Case "-width", "-w"
			Settings_Graphics_Size[0] = Value
			LogMessage(LOG_INFO, "CmdLine: Set Graphics Resolution to "+Settings_Graphics_Size[0]+"x"+Settings_Graphics_Size[1]+".")
		Case "-height", "-h"
			Settings_Graphics_Size[1] = Value
			LogMessage(LOG_INFO, "CmdLine: Set Graphics Resolution to "+Settings_Graphics_Size[0]+"x"+Settings_Graphics_Size[1]+".")
		Case "-preset", "-p"
			Settings_Graphics_Detail$ = Value
			LogMessage(LOG_INFO, "CmdLine: Set Graphics Detail to "+Value+".")
		Case "-IP", "-ip"
			;Implement Manual Server Connection
	End Select
End Function
;[End Block]

;------------------------------------------------------------------------------
;! Virtual Scene Management
;------------------------------------------------------------------------------

Global VirtualSceneEntity_Empty.VirtualSceneEntity = New VirtualSceneEntity

Function VirtualScene_Create.VirtualScene()
	Local VS.VirtualScene = New VirtualScene
	Return VS
End Function

Function VirtualScene_Destroy(VS.VirtualScene)
	Local VSE.VirtualSceneEntity
	For VSE.VirtualSceneEntity = Each VirtualSceneEntity
		If VSE\VS = VS Then Delete VSE
	Next
	Delete VS
End Function

Function VirtualScene_Register(VS.VirtualScene, Entity%)
	Local VSE.VirtualSceneEntity = New VirtualSceneEntity
	VSE\VS = VS
	VSE\Entity = Entity
	VSE\State = BS_Memory_PeekByte(VSE\Entity, 24)
End Function

Function VirtualScene_Unregister(VS.VirtualScene, Entity%)
	Local VSE.VirtualSceneEntity
	For VSE = Each VirtualSceneEntity
		If (VSE\VS = VS) And (VSE\Entity = Entity) Then
			Delete VSE:Exit
		EndIf
	Next
End Function

Function VirtualScene_Hide(VS.VirtualScene)
	Local VSE.VirtualSceneEntity
	For VSE = Each VirtualSceneEntity
		If (VSE\VS = VS) Then
			VSE\State = BS_Memory_PeekByte(VSE\Entity, 24)
			HideEntity VSE\Entity
		EndIf
	Next
End Function

Function VirtualScene_Show(Vs.VirtualScene)
	Local VSE.VirtualSceneEntity
	For VSE = Each VirtualSceneEntity
		If (VSE\VS = Vs) And (VSE\State = 1) Then
			ShowEntity VSE\Entity
		EndIf
	Next
End Function

;------------------------------------------------------------------------------
;! Utility Functions
;------------------------------------------------------------------------------
Function BoolToString$(Bool%)
	Select Bool
		Case False
			Return "False"
		Default
			Return "True"
	End Select
End Function
Function PeekStringC$(Bank%, Offset%, MaxLength%)
	If Bank = 0 Then RuntimeError "Invalid Bank"
	If Offset > BankSize(Bank) Then RuntimeError "Offset must be smaller than the banksize."
	If Offset+MaxLength > BankSize(Bank) Then RuntimeError "Total length can't be longer than banksize."
	
	Local Out$ = ""
	For P = Offset To Offset+MaxLength
		Local C = PeekByte(Bank, P)
		If C = 0
			Return Out
		Else
			Out = Out + Chr(C)
		EndIf
	Next
	Return Out
End Function
Function SetBit(bank,offset) ; Was ist das denn für ein langsamer blödsinn?
	;Local pos = Floor(offset / 8.0)
	Local pos = offset Shr 3 ; Fast divide by 8
	Local old_byte = PeekByte(bank, pos)
	Local local_pos = offset Mod 8
	Local i
	
	Local new_byte
	Local bit[7]
	
	For i=7 To 0 Step -1
		If old_byte - (2^i) >= 0 Then bit[7-i] = 1 : old_byte = old_byte - (2^i)
	Next
	
	bit[local_pos] = 1
	
	For i = 7 To 0 Step -1
		If bit[7-i] = 1 Then new_byte = new_byte + (2^i)
	Next
	
	PokeByte(bank,pos,new_byte)
End Function

Global SplitCount
Dim SplittedString(0)
Function SplitString(In$, StringSplitter$ = "|")
	Local Pos,Ch$,All$
	SplitCount = 0
	For I = 1 To Len(In)-1
		Ch = Mid(In,I,1)
		If Ch = StringSplitter Then SplitCount = SplitCount + 1
	Next
	Dim SplittedString(SplitCount)
	SplitCount = 0
	While Not Pos = Len(In)
		Pos = Pos + 1
		Ch = Mid(In,Pos,1)
		If Ch = StringSplitter Or Pos = Len(In)
			If Pos = Len(In) Then All = All+Ch
			SplittedString(SplitCount) = All
			All = ""
			SplitCount = SplitCount + 1
		Else
			All = All+Ch
		EndIf
	Wend
End Function

;------------------------------------------------------------------------------
;! Location
;------------------------------------------------------------------------------


;------------------------------------------------------------------------------
;! Oriented Bounding Box
;------------------------------------------------------------------------------
Function CreateOBB( x#, y#, z#, pitch#, yaw#, roll#, width#, height#, depth#, parent = 0 )
; Creates an OBB and returns its entity handle. Note that an OBB is just a pivot entity.
; The position, rotation, and size of the OBB will be set using the supplied values. Note that these values are initially applied in global space, but will be relative to the parent space after the obb has been created.
;	An optional parent entity can be specified. The obb will be attached To this parent, And its position, rotation, And size will update when the position, rotation, And\Or size of the parent changes.
;	PositionEntity obb_debug, x#, y#, z#, True
;	RotateEntity obb_debug, pitch#, yaw#, roll#, True
;	ScaleEntity obb_debug, width#, height#, depth#, True
	
	Local obb_pivot = CreatePivot( parent )
	
	;Local obb_debug =CreateCube( obb_pivot )
	
	PositionEntity obb_pivot, x#, y#, z#, True
	RotateEntity obb_pivot, pitch#, yaw#, roll#, True
	ScaleEntity obb_pivot, width#, height#, depth#, True
	Return obb_pivot
End Function
Function PointInOBB( obb_pivot, x#, y#, z# )
; Tests if the specified x, y, z, point is inside the space of the specified OBB pivot entity.
; If so, a true result is returned. If not, a false result is returned.
	
	TFormPoint x#, y#, z#, 0, obb_pivot
	x# = TFormedX() : y# = TFormedY() : z# = TFormedZ()
	If ( x# >= -1.0 ) And ( x# <= 1.0 )
		If ( y# >= -1.0 ) And ( y# <= 1.0 )
			If ( z# >= -1.0 ) And ( z# <= 1.0 )
				Return True
			EndIf
		EndIf
	EndIf
	Return False
End Function

Function EntityInOBB( obb_pivot, entity_to_test )
; Tests if the specified entity to test is inside the space of the specified OBB pivot entity.
; If so, a true result is returned. If not, a false result is returned.
	
	TFormPoint EntityX#( entity_to_test, True ), EntityY#( entity_to_test, True ), EntityZ#( entity_to_test, True ), 0, obb_pivot
	x# = TFormedX() : y# = TFormedY() : z# = TFormedZ()
	If ( x# >= -1.0 ) And ( x# <= 1.0 )
		If ( y# >= -1.0 ) And ( y# <= 1.0 )
			If ( z# >= -1.0 ) And ( z# <= 1.0 )
				Return True
			EndIf
		EndIf
	EndIf
	Return False
End Function

;------------------------------------------------------------------------------
;! Hashing & Cryptography
;------------------------------------------------------------------------------
Function MD5$(my_string$)
	Local output$
	
	;Länge des Strings in Bit
	Local bit_lenght = (Len(my_string)*8)
	
	;Bankgröße in Bit, es gilt: (Stringlänge - 64) MOD 512 = 0
	Local bank_size = Ceil((bit_lenght+65) / 512.0) * 512
	
	;Speicherbank erstellen
	Local md5_bank = CreateBank(Ceil(bank_size / 8.0))
	
	;Laufvariablen
	Local l,j,i
	
	;Anzahl der 512-Bit Blocks
	Local blocks = bank_size/512
	
	;String in Bank speichern
	For l=1 To Len(my_string)
		PokeByte(md5_bank,l-1,Asc(Mid(my_string,l,1)))
	Next
	
	; Konstanten
	Local h0% = $67452301
	Local h1% = $EFCDAB89
	Local h2% = $98BADCFE
	Local h3% = $10325476
	
	Local A% = h0
	Local B% = h1
	Local C% = h2
	Local D% = h3
	
	;-----------------------------------
	; Konstanten zuweisen
	;-----------------------------------
	Local R[63],k[63]
	
	R[0] = 7:R[1] = 12:R[2] = 17:R[3] = 22:R[4] = 7:R[5] = 12:R[6] = 17:R[7] = 22
	R[8] = 7:R[9] = 12:R[10] = 17:R[11] = 22:R[12] = 7:R[13] = 12:R[14] = 17:R[15] = 22
	R[16] = 5:R[17] = 9:R[18] = 14:R[19] = 20:R[20] = 5:R[21] = 9:R[22] = 14:R[23] = 20
	R[24] = 5:R[25] = 9:R[26] = 14:R[27] = 20:R[28] = 5:R[29] = 9:R[30] = 14:R[31] = 20
	R[32]= 4:R[33]= 11:R[34]= 16:R[35]= 23:R[36]= 4:R[37]= 11:R[38]= 16:R[39]= 23
	R[40]= 4:R[41]= 11:R[42]= 16:R[43]= 23:R[44]= 4:R[45]= 11:R[46]= 16:R[47]= 23
	R[48] = 6:R[49] = 10:R[50] = 15:R[51] = 21:R[52] = 6:R[53] = 10:R[54] = 15:R[55] = 21
	R[56] = 6:R[57] = 10:R[58] = 15:R[59] = 21:R[60] = 6:R[61] = 10:R[62] = 15:R[63] = 21
	
	k[0] = $d76aa478:k[1] = $e8c7b756:k[2] = $242070db:k[3] = $c1bdceee:k[4] = $f57c0faf:k[5] = $4787c62a
	k[6] = $a8304613:k[7] = $fd469501:k[8] = $698098d8:k[9] = $8b44f7af:k[10] = $ffff5bb1:k[11] = $895cd7be
	k[12] = $6b901122:k[13] = $fd987193:k[14] = $a679438e:k[15] = $49b40821:k[16] = $f61e2562:k[17] = $c040b340
	k[18] = $265e5a51:k[19] = $e9b6c7aa:k[20] = $d62f105d:k[21] = $2441453:k[22] = $d8a1e681:k[23] = $e7d3fbc8
	k[24] = $21e1cde6:k[25] = $c33707d6:k[26] = $f4d50d87:k[27] = $455a14ed:k[28] = $a9e3e905:k[29] = $fcefa3f8
	k[30] = $676f02d9:k[31] = $8d2a4c8a:k[32] = $fffa3942:k[33] = $8771f681:k[34] = $6d9d6122:k[35] = $fde5380c
	k[36] = $a4beea44:k[37] = $4bdecfa9:k[38] = $f6bb4b60:k[39] = $bebfbc70:k[40] = $289b7ec6:k[41] = $eaa127fa
	k[42] = $d4ef3085:k[43] = $4881d05:k[44] = $d9d4d039:k[45] = $e6db99e5:k[46] = $1fa27cf8:k[47] = $c4ac5665
	k[48] = $f4292244:k[49] = $432aff97:k[50] = $ab9423a7:k[51] = $fc93a039:k[52] = $655b59c3:k[53] = $8f0ccc92
	k[54] = $ffeff47d:k[55] = $85845dd1:k[56] = $6fa87e4f:k[57] = $fe2ce6e0:k[58] = $a3014314:k[59] = $4e0811a1
	k[60] = $f7537e82:k[61] = $bd3af235:k[62] = $2ad7d2bb:k[63] = $eb86d391
	
	;-----------------------------------
	
	
	;ein gesetztes Bit an String anhängen
	SetBit(md5_bank, Len(my_string)*8)
	
	
	;-----------------------------------
	;Länge des Strings anhängen:
	;-----------------------------------
	
	Local strlenght[64]
	Local num,bit64[63]
	
	num=Len(my_string)*8
	
	;Zahl in Bits speichern
	For i=63 To 0 Step -1
		If num - Int(2^i) >= 0 Then bit64[63-i] = 1 : num = num - Int(2^i)
	Next
	
	;Zahl in 64-Bit Little Endian konvertieren
	l=0
	For i=63 To 0 Step -8
		For j=0 To 7
			If bit64[i-7+j]=1 Then strlenght[l] = 1
			l =l + 1
		Next
	Next
	
	;Zahl anhängen
	For l=0 To 63
		If strlenght[l] Then
			SetBit(md5_bank,bank_size-64+l)
		EndIf
	Next
	
	;-----------------------------------
	; MD5
	;-----------------------------------
	
	;alle Blocks durchgehen
	For i=1 To blocks
		
		;Blocks in 16x 32-Bit Teile unterteilen
		Local parts[15]
		For j=0 To 15
			parts[j] = PeekInt(md5_bank,(i-1)*64+j*4)
		Next
		
		
		
		
		; Anfangsbelegung
		A% = h0
		B% = h1
		C% = h2
		D% = h3
		
		Local f,g
		For l=0 To 63
			If l >= 0 And l <=15 Then
			;Runde 1
				f = (B And C) Or ((~B) And D)
				g = l
			ElseIf l >= 16 And l <=31 Then
			;Runde 2
				f = (D And B) Or ((~D) And C)
				g = (l*5 + 1) Mod 16
			ElseIf l >= 32 And l <=47 Then
			;Runde 3
				f = B Xor C Xor D
				g = (l*3 + 5) Mod 16
			ElseIf l >= 48 And l <=63 Then
			;Runde 4
				f = C Xor (B Or (~D))
				g = (l*7) Mod 16
			EndIf
			
			;Verschiebung/Linksrotation
			Local temp = D
			D = C
			C = B
			B=(((A + f + k[l] + parts[g]) Shl  R[l]) Or ((A + f + k[l] + parts[g]) Shr (32 -  R[l]))) + B
			A = temp
		Next
		
		; Werte aller Blocks werden addiert
		h0 = h0 + A
		h1 = h1 + B
		h2 = h2 + C
		h3 = h3 + D
	Next
	
	
	
	;-----------------------------------
	;16-Bit Word in 128-Bit Hexcode
	;-----------------------------------
	Local tmp
	
	For i=0 To 3
		Select i
			Case 0 : tmp = h0
			Case 1 : tmp = h1
			Case 2 : tmp = h2
			Case 3 : tmp = h3
		End Select
		
		output = output +  Right(Hex((tmp And $FF)),2)
		output = output +  Right(Hex((tmp And $FF00)/$100),2)
		output = output +  Right(Hex((tmp And $FF0000)/$10000),2)
		output = output +  Right(Hex((tmp And $FF000000)/$1000000),2)
	Next
	
	;Rückgabe
	Return Lower(output)
End Function

;------------------------------------------------------------------------------
;! Save Data
;------------------------------------------------------------------------------
Function UserDataLoad(ProfSlot)
	; Convert old Savedata.
	
	Local SaveFile
	SaveFile=ReadFile(UserData+"\Profiles\CharDataA.txt")
	
	Character_Value_ID      = ReadLine(SaveFile)
	Character_Value_Name$   = ReadLine(SaveFile)
	Character_Value_DOC$    = ReadLine(SaveFile)
	Character_Value_Faction = ReadLine(SaveFile)
	Storyline_Step		= ReadLine(SaveFile)
	Character_Value_LastX#	= ReadLine(SaveFile)
	Character_Value_LastY#	= ReadLine(SaveFile)
	Character_Value_LastZ#	= ReadLine(SaveFile)
	Character_Value_System  = ReadLine(SaveFile)
	Character_Value_XP      = ReadLine(SaveFile)
	Character_Value_Level  = ReadLine(SaveFile)
	MYxMONEY	  = ReadLine(SaveFile)
	Character_Value_Ship	= ReadLine(SaveFile)
	Character_Value_Weapon		= ReadLine(SaveFile)
	Tutorial_Step = ReadLine(SaveFile)
	
	Temp_Inventory_Contents = 0 
	
	CloseFile SaveFile
	
;	FastTravel(Character_Value_LastX#,Character_Value_LastY#,Character_Value_LastZ#)
	Tutorial_Streamer_Name$=Lower(Character_Value_Name$)
	ChatChannel$="#"+Lower(Character_Value_Name$)
	
	;	UserDataSave(Character_Profile_Loaded)
	
;	AIData_Create(Character_Value_LastX,Character_Value_LastY,Character_Value_LastZ,Character_Value_Faction,0)
	
	CurrName$=Character_Value_Name$
	
End Function

Function UserDataSave(ProfSlot)
	Character_Value_LastX=EntityX(pvShip,True)
	Character_Value_LastY=EntityY(pvShip,True)
	Character_Value_LastZ=EntityZ(pvShip,True)
	Local DataFile
	DataFile = WriteFile(UserData+"\Profiles\CharDataA.txt.nw")
	
	Temp_Inventory_Contents=0
	
	
	WriteLine DataFile,Character_Value_ID
	WriteLine DataFile,Character_Value_Name$
	WriteLine DataFile,Character_Value_DOC$
	WriteLine DataFile,Character_Value_Faction
	WriteLine DataFile,Storyline_Step
	WriteLine DataFile,Character_Value_LastX
	WriteLine DataFile,Character_Value_LastY
	WriteLine DataFile,Character_Value_LastZ
	WriteLine DataFile,Character_Value_System
	WriteLine DataFile,Character_Value_XP
	WriteLine DataFile,Character_Value_Level
	WriteLine DataFile,MYxMONEY
	WriteLine DataFile,Character_Value_Ship
	WriteLine DataFile,Character_Value_Weapon
	WriteLine DataFile,Tutorial_Step
	
;	Inventory
;	Local InvItem.Inventory
	
;	WriteInt Stream, $FFFFFFFF
	CloseFile DataFile
	
	CopyFile UserData+"\Profiles\CharDataA.txt.nw", UserData+"\Profiles\CharDataA.txt"
	DeleteFile UserData+"\Profiles\CharDataA.txt.nw"
End Function



;------------------------------------------------------------------------------
;! Planets
;------------------------------------------------------------------------------

Function Util_InitTimer()
	util_lastmsc = MilliSecs()
End Function

Function Util_Timer()
	msc = MilliSecs()
	main_mscleft = Float#(msc-util_lastmsc)
	main_gspe# = main_mscleft/13.00
	util_lastmsc = msc
End Function

Function CreateOBJName()
	ALetter=Rand(1,26)
	Select ALetter
		Case 1
			AZ$="A"
		Case 2
			AZ$="B"
		Case 3
			AZ$="C"
		Case 4
			AZ$="D"
		Case 5
			AZ$="E"
		Case 6
			AZ$="F"
		Case 7
			AZ$="G"
		Case 8
			AZ$="H"
		Case 9
			AZ$="I"
		Case 10
			AZ$="J"
		Case 11
			AZ$="K"
		Case 12
			AZ$="L"
		Case 13
			AZ$="M"
		Case 14
			AZ$="N"
		Case 15
			AZ$="O"
		Case 16
			AZ$="P"
		Case 17
			AZ$="Q"
		Case 18
			AZ$="R"
		Case 19
			AZ$="S"
		Case 20
			AZ$="T"
		Case 21
			AZ$="U"
		Case 22
			AZ$="V"
		Case 23
			AZ$="W"
		Case 24
			AZ$="X"
		Case 25
			AZ$="Y"
		Case 26
			AZ$="Z"
	End Select
	
	BLetter=Rand(1,26)
	Select BLetter
		Case 1
			BZ$="A"
		Case 2
			BZ$="B"
		Case 3
			BZ$="C"
		Case 4
			BZ$="D"
		Case 5
			BZ$="E"
		Case 6
			BZ$="F"
		Case 7
			BZ$="G"
		Case 8
			BZ$="H"
		Case 9
			BZ$="I"
		Case 10
			BZ$="J"
		Case 11
			BZ$="K"
		Case 12
			BZ$="L"
		Case 13
			BZ$="M"
		Case 14
			BZ$="N"
		Case 15
			BZ$="O"
		Case 16
			BZ$="P"
		Case 17
			BZ$="Q"
		Case 18
			BZ$="R"
		Case 19
			BZ$="S"
		Case 20
			BZ$="T"
		Case 21
			BZ$="U"
		Case 22
			BZ$="V"
		Case 23
			BZ$="W"
		Case 24
			BZ$="X"
		Case 25
			BZ$="Y"
		Case 26
			BZ$="Z"
	End Select
	
	CLetter=Rand(1,26)
	Select CLetter
		Case 1
			CZ$="A"
		Case 2
			CZ$="B"
		Case 3
			CZ$="C"
		Case 4
			CZ$="D"
		Case 5
			CZ$="E"
		Case 6
			CZ$="F"
		Case 7
			CZ$="G"
		Case 8
			CZ$="H"
		Case 9
			CZ$="I"
		Case 10
			CZ$="J"
		Case 11
			CZ$="K"
		Case 12
			CZ$="L"
		Case 13
			CZ$="M"
		Case 14
			CZ$="N"
		Case 15
			CZ$="O"
		Case 16
			CZ$="P"
		Case 17
			CZ$="Q"
		Case 18
			CZ$="R"
		Case 19
			CZ$="S"
		Case 20
			CZ$="T"
		Case 21
			CZ$="U"
		Case 22
			CZ$="V"
		Case 23
			CZ$="W"
		Case 24
			CZ$="X"
		Case 25
			CZ$="Y"
		Case 26
			CZ$="Z"
	End Select
	
	Num$=Rand(1,9999)
	NLen=Len(Num$)
	If NLen=3 Then Num$="0"+Num$
	If NLen=2 Then Num$="00"+Num$
	If NLen=1 Then Num$="000"+Num$
	
	Fname$=AZ$+BZ$+"-"+Num$+"-"+CZ$
	
End Function

Function ConvertNumerics(InputNumber)
	
End Function




Function CreateMyShipGun(ShipVersion, WID)
	Select ShipVersion
		Case 1
			
			
			NewShot(2,pvShip,15,18,-3,-7)
			
		Case 2
			
			NewShot(WID,pvShip,0,0, 0, 45)
			NewShot(WID,pvShip,0,0, 0, 43)
			NewShot(WID,pvShip,0,0, 0, 41)
			
			
		Case 3
			
			NewShot(WID,pvShip,15,  0, 6,57)
			NewShot(WID,pvShip,15, 28,-6,37)
			NewShot(WID,pvShip,15, 28,-6,37)
			NewShot(WID,pvShip,15,-28,-6,37)
			NewShot(WID,pvShip,15,-28,-6,37)
			
		Case 4
			
			NewShot(1,pvShip,15, 35,-6,37)
			NewShot(2,pvShip,15, 37, 3,37)
			NewShot(3,pvShip,15, 39,-6,37)
			NewShot(3,pvShip,15,-35,-6,37)
			NewShot(2,pvShip,15,-37, 3,37)
			NewShot(1,pvShip,15,-39,-6,37)
			
		Case 5
			
		Default
			
			NewShot(WID,pvShip,15,-50,13,0)
			NewShot(WID,pvShip,15,50,13,0)
			
	End Select
	
End Function

Function GetWeaponvalue(WeaponID)
	
	Select WeaponID
		Case 1
			Return 27
		Case 2
			Return 12
		Case 3
			Return 18
		Case 4
			Return 29
	End Select
	
End Function

Function PlayerSwitchShip(sid)
;	p.pdata = First pdata
	VirtualScene_Unregister(Scene, eShipBody)
	
	FreeEntity eShipBody
	Character_Value_Ship = sid
	Select sid
		Case 1
			eShipBody = CopyEntity (Mesh_Ship[1],eShip)
		Case 2
			eShipBody = CopyEntity (Mesh_Ship[2],eShip)
		Case 3
			eShipBody = CopyEntity (Mesh_Ship[6],eShip)
		Case 4
			eShipBody = CopyEntity (Mesh_Ship[7],eShip)
		Case 5
			eShipBody = CopyEntity (Mesh_Ship[5],eShip)
	End Select
	GetPlayerShipValues(sid)
	
	
	
	Yoffset=55
	Zoffset=-150
	
	VirtualScene_Register(Scene,eShipBody)
	
End Function

Function GetPlayerShipValues(ShipID)
	
	
	SHIxREL=Player_Value_Shield_Maximum/100
	SHIxTIC=80
	
	Select ShipID
			;CombatShips
			
		Case 1
			Player_Value_Shield_Maximum=25000 
			Player_Value_Armor_Maximum=12500 
			Player_Value_Energy_Maximum=25000
			Ship_Shield_Reload_Tick = 25
			Ship_Shield_Reload_Amount = 190
			
			Player_Value_Miner_Strength#=.1
			Player_Value_Inertia_Modifier#=1
			
			Ship_Value_ScanningStrength = 18000
			
			Player_Value_Inertia_Negative# = 0.94
			Player_Value_Inertia_Positive# = 1.08
			Player_Value_Inertia_Base# = 3
			Player_Value_Speed_Maximum=15
			
			Ship_Gun_Hardpoints = 2
			Ship_Gun_Slot[1] = 1
			Ship_Gun_Slot[2] = 3
			Ship_Gun_Timer[1] = 26
			Ship_Gun_Timer[2] = 0
			
			Ship_Gun_HPX[1]=-18
			Ship_Gun_HPY[1]=-3
			Ship_Gun_HPZ[1]=7
			
			Ship_Gun_HPX[2]=18
			Ship_Gun_HPY[2]=-3
			Ship_Gun_HPZ[2]=7
			
			Ship_Rad_Resistance#=1
		Case 2
			Player_Value_Shield_Maximum=50000
			Player_Value_Armor_Maximum=2500 
			Player_Value_Energy_Maximum=2500
			
			Ship_Shield_Reload_Tick = 25
			Ship_Shield_Reload_Amount = 190
			
			Player_Value_Miner_Strength#=.1
			Player_Value_Inertia_Modifier#=1
			
			Ship_Value_ScanningStrength = 18000
			
			Player_Value_Inertia_Negative# = 0.99
			Player_Value_Inertia_Positive# = 1.1
			Player_Value_Inertia_Base# = 3
			Player_Value_Speed_Maximum=18
			
			Ship_Rad_Resistance#=.75
		Case 3
			Player_Value_Shield_Maximum=45000
			Player_Value_Armor_Maximum=16000 
			Player_Value_Energy_Maximum=2000
			
			Ship_Shield_Reload_Tick = 25
			Ship_Shield_Reload_Amount = 190
			
			Player_Value_Miner_Strength#=.1
			Player_Value_Inertia_Modifier#=1
			
			Ship_Value_ScanningStrength = 18000
			
			Player_Value_Inertia_Negative# = 0.94
			Player_Value_Inertia_Positive# = 1.04
			Player_Value_Inertia_Base# = 3
			Player_Value_Speed_Maximum=10
			
			Ship_Rad_Resistance#=.5
		Case 4
			Player_Value_Shield_Maximum=52500
			Player_Value_Armor_Maximum=25000 
			Player_Value_Energy_Maximum=2000
			
			Ship_Shield_Reload_Tick = 25
			Ship_Shield_Reload_Amount = 190
			
			
			
			Player_Value_Miner_Strength#=.1
			Player_Value_Inertia_Negative# = 0.99
			Player_Value_Inertia_Positive# = 1.3
			Player_Value_Inertia_Modifier#=1
			
			Ship_Value_ScanningStrength = 18000
			
			Player_Value_Inertia_Base# = 2.7
			Player_Value_Speed_Maximum=8
			Ship_Rad_Resistance#=.25
			
		Default
			Player_Value_Shield_Maximum=25000: Player_Value_Armor_Maximum=12500 :Player_Value_Energy_Maximum=2500
			Ship_Shield_Reload_Tick = 25: Ship_Shield_Reload_Amount = 190
			
			Player_Value_Miner_Strength#=.1
			Player_Value_Inertia_Modifier#=1
			
			Ship_Value_ScanningStrength = 18000
			
			Player_Value_Inertia_Base# = 3.5: Player_Value_Speed_Maximum=15: Ship_Rad_Resistance#=1
	End Select
	
	GetMiningBonusValues(ShipID)
	
	Player_Value_Shield_Current=Player_Value_Shield_Maximum 
	Player_Value_Armor_Current=Player_Value_Armor_Maximum
	Player_Value_Energy_Current=Player_Value_Energy_Maximum
	
	Zoffset=140
	Yoffset=22
	
End Function

Function GetMiningBonusValues(ShipID)
	
	Select ShipID
		Case 1
			
			Player_Value_Miner_Bonus#=1
			
		Case 2
			
			Player_Value_Miner_Bonus#=0
			
		Case 3
			
			Player_Value_Miner_Bonus#=0
			
		Case 4
			
			Player_Value_Miner_Bonus#=0
			
		Case 5
			
			Player_Value_Miner_Bonus#=0
			
		Case 6
			
			Player_Value_Miner_Bonus#=0
			
		Case 7
			
			Player_Value_Miner_Bonus#=2
			
		Case 8
			
			Player_Value_Miner_Bonus#=2
			
		Case 9
			
			Player_Value_Miner_Bonus#=3
			
		Default 
			
			Player_Value_Miner_Bonus#=3
			
	End Select
	
End Function

Function GetWeaponValues(WID)
	
	Select WID
		Case 1
			ActiveGun=1
			Weapon_Maxtimer=20
			Weapon_Maxdistance=6000
		Case 2
			ActiveGun=2
			Weapon_Maxtimer=27
			Weapon_Maxdistance=9000
		Case 3
			ActiveGun=3
			Weapon_Maxtimer=34
			Weapon_Maxdistance=6000
		Case 4
			ActiveGun=4
			Weapon_Maxtimer=41
			Weapon_Maxdistance=3000
		Case 5
			ActiveGun=5
			Weapon_Maxtimer=48
	End Select
	
End Function

Type MapWaypoints
	Field X, Y, Z, PT, MPX, MPY, MPZ
End Type

Global MP_SCALE=100
Global MP_TOGGL=3

Function CreateMapPoint(x,y,z, styp)
	XY.MapWaypoints = New MapWaypoints
	XY\MPX=x
	XY\MPY=y
	XY\MPZ=z
	XY\X=(x/MP_SCALE)
	XY\Y=(y/MP_SCALE)
	XY\Z=(z/MP_SCALE)
	XY\PT=styp
End Function

Function UpdateMapScale()
	For XY.MapWaypoints = Each MapWaypoints
		XY\X=(XY\MPX/MP_SCALE)
		XY\Y=(XY\MPY/MP_SCALE)
		XY\Z=(XY\MPZ/MP_SCALE)
	Next
End Function

Function UpdateMapPoint()
	If Trigger_Map_Scaling > 4 Then
		Trigger_Map_Scaling = 1
	EndIf
	
	Select Trigger_Map_Scaling
		Case 1
			MP_SCALE=1000
		Case 2
			MP_SCALE=600
		Case 3
			MP_SCALE=300
		Case 4
			MP_SCALE=100
	End Select
	
	Local CposX=EntityX(pvShip)/MP_SCALE
	Local CposY=EntityY(pvShip)/300
	Local CposZ=EntityZ(pvShip)/MP_SCALE
	
	MapOriginX =GraphicsWidth()/2-(16+128)
	MapOriginZ=GraphicsHeight()/2-(16+128)
	
	Local MapScale#=MP_SCALE/500
	Local MapString$=Left(MapScale#,4)
	
	If HUD=1 And MAPHUD = 0 Then 
		DrawImage3D(GUI_MapIcon[0],MapOriginX, MapOriginZ,False,EntityYaw(pvShip)*-1,2)
	EndIf
	
	For Portal.Stargate = Each Stargate
		Local AIX=EntityX(Portal\Mesh)/MP_SCALE
		Local AIY=EntityY(Portal\Mesh)/MP_SCALE
		Local AIZ=EntityZ(Portal\Mesh)/MP_SCALE
		
		If AIX<CposX+128 And AIX>CposX-128 And AIY<CposY+128 And AIY>CposY-128 And AIZ<CposZ+128 And AIZ>CposZ-128 And HUD=1
			MapYPoint = ( AIY - EntityY(pvShip,True) ) / 500
			
			DrawImage3D(GUI_Interface[19],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ,0,0,1)
			
			If MapYPoint > -128 And MapYPoint< 128 Then
				If MapYPoint>0 Then
					For MapLine = 1 To MapYPoint
						DrawImage3D(GUI_Interface[20],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ+mapline,0,0,1)
					Next
				Else
					For MapLine = 1 To -MapYPoint
						DrawImage3D(GUI_Interface[20],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ-+mapline,0,0,1)
					Next
				EndIf
			EndIf
			
			MapIconPosX=AIX-CposX
			MapIconPosZ=AIZ-CposZ
			
			DrawImage3D(GUI_MapIcon[7],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ + MapYPoint,0,0,1)
			
		EndIf
	Next
	
	;[Block] Ship Map Points
;	For Ship.AI = Each AI
;		AIX=EntityX(Ship\Shipmesh)/MP_SCALE
;		AIY=EntityY(Ship\Shipmesh)/MP_SCALE
;		AIZ=EntityZ(Ship\Shipmesh)/MP_SCALE
;		
;		If AIX<CposX+128 And AIX>CposX-128 And AIY<CposY+128 And AIY>CposY-128 And AIZ<CposZ+128 And AIZ>CposZ-128 And HUD=1
;			
;			MapYPoint = ( AIY - EntityY(pvShip,True) ) / 500
;			
;			DrawImage3D(GUI_Interface[19],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ,0,0,1)
;			
;			If MapYPoint > -128 And MapYPoint< 128 Then
;				If MapYPoint>0 Then
;					For MapLine = 1 To MapYPoint
;						DrawImage3D(GUI_Interface[20],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ+mapline,0,0,1)
;					Next
;				Else
;					For MapLine = 1 To -MapYPoint
;						DrawImage3D(GUI_Interface[20],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ-+mapline,0,0,1)
;					Next
;				EndIf
;			EndIf
;			
;			MapIconPosX=AIX-CposX
;			MapIconPosZ=AIZ-CposZ
;			Select Ship\Faction
;				Case Faction_Crimson
;					DrawImage3D(GUI_MapIcon[4],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ+MapYPoint,0,EntityYaw(Ship\Shipmesh)*-1,1)
;				Case Faction_Neutral
;					DrawImage3D(GUI_MapIcon[6],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ+MapYPoint,0,EntityYaw(Ship\Shipmesh),1)
;				Case Faction_Pirate
;					DrawImage3D(GUI_MapIcon[4],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ+MapYPoint,0,EntityYaw(Ship\Shipmesh),1)
;				Case Faction_Police
;					DrawImage3D(GUI_MapIcon[6],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ+MapYPoint,0,EntityYaw(Ship\Shipmesh),1)
;				Case Faction_Sirian
;					If Character_Value_Faction = Faction_Sirian Then
;						DrawImage3D(GUI_MapIcon[5],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ+MapYPoint,0,EntityYaw(Ship\Shipmesh),1)
;					ElseIf Character_Value_Faction = Faction_Terran Then
;						DrawImage3D(GUI_MapIcon[4],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ+MapYPoint,0,EntityYaw(Ship\Shipmesh),1)
;					EndIf
;				Case Faction_Terran
;					If Character_Value_Faction = Faction_Terran Then
;						DrawImage3D(GUI_MapIcon[5],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ+MapYPoint,0,EntityYaw(Ship\Shipmesh),1)
;					ElseIf Character_Value_Faction = Faction_Sirian Then
;						DrawImage3D(GUI_MapIcon[4],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ+MapYPoint,0,EntityYaw(Ship\Shipmesh),1)
;					EndIf
;				Case Faction_Unknown
;					DrawImage3D(GUI_MapIcon[6],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ+MapYPoint,0,EntityYaw(Ship\Shipmesh),1)
;			End Select
;			
;		EndIf
;		
;	Next
;	;[End Block]
	
End Function

Function AlignEntity(x_objekt1, x_objekt2, Abstufungen = 0) 
	
	If Abstufungen > 1 Then 
		
		Pitch# = EntityPitch(x_objekt1) 
		Yaw# = EntityYaw(x_objekt1) 
		
		NeuPitch# = Pitch# + (DeltaPitch(x_objekt1, x_objekt2) / Abstufungen) 
		NeuYaw# = Yaw# + (DeltaYaw(x_objekt1, x_objekt2) / Abstufungen) 
		
	Else 
		
		NeuPitch# = EntityPitch(x_objekt1) + DeltaPitch(x_objekt1,x_objekt2) 
		NeuYaw# = EntityYaw(x_objekt1) + DeltaYaw(x_objekt1,x_objekt2) 
		
	EndIf 
    
	RotateEntity (x_objekt1, NeuPitch#, NeuYaw#, EntityRoll(x_objekt1)) 
	
End Function 

Global NOTE_ACTIVE=0, NOTE_HEADER$, NOTE_LINE1$, NOTE_LINE2$, NOTE_LINE3$, NOTE_LINE4$, NOTE_LINE5$, NOTE_DURATION

Function download_file(source$,target$)
	
	
	Local max_download_bytes = 1024, host$, file$ ; 16777216 , host$, file$; 1024 , host$, file$
	
	host$=splitt_fqdn(source$,1)
	file$=splitt_fqdn(source$,2)
	
	tcp=OpenTCPStream(host$,80) ;seite öffnen
	
	If Not tcp Then Return -1
	
	
	crlf$=Chr(13)+Chr(10)
	WriteLine tcp,"GET "+file$+" HTTP/1.1"+crlf$+"Host: "+host$+crlf$+"Connection: close"+crlf$+"User-Agent: bb-dwnldr"+crlf$+"Cache-Control: no-cache"+crlf$
	
	If Eof(tcp) Then Return -2
	
	
	Repeat
		response$=ReadLine(tcp)
	Until response$=""
	
	
	Delay(2)
	
	
	file=WriteFile(target$)
	If file=0 Then Return -3
	
	buffer = CreateBank(max_download_bytes)
	
	While Not Eof(tcp)
		Size = ReadBytes(buffer, tcp, 0, max_download_bytes)
		WriteBytes(buffer, file, 0, Size)
	Wend 
	
	CloseFile(file)
	
	FreeBank buffer
	
	Return 1
	
End Function 

Function splitt_fqdn$(url$,part)
	Local pos=0
	;url$=Lower(url$)
	
	If Left(url$,7)="http://" Then pos=7
	If Left(url$,8)="https://"  Then pos=8
	
	slash_pos=Instr(url$,"/",pos+1)
	If part=1
		Return Mid(url$,pos+1,slash_pos-pos-1)
	ElseIf part=2
		Return Mid(url$,slash_pos)
	Else
		Return "Invalid part parameter!"
	EndIf 
End Function 

Type Zone
	Field range, pivot, faction
End Type

Global CurrentZoneText$, System_Name$

Function FactionZone_Update()
	
	Select CurrentZone
		Case 0
			CurrentZoneText$="Unknown"
		Case Faction_Terran
			CurrentZoneText$="Terran Empire"
		Case Faction_Sirian
			CurrentZoneText$="Sirius Colonies"
		Case Faction_Police
			CurrentZoneText$="Contested Battleground"
		Case Faction_Neutral
			CurrentZoneText$="Neutral Zone"
		Case Faction_Crimson
			CurrentZoneText$="Refuge of the Crimson Dawn"
		Default
			CurrentZoneText$="Development Sector"
	End Select
	
End Function

Function Randomsong(number)
	Select number
		Case 1
			Channel_Music = PlaySound(Music_ID[1])
			Music_Description$="Wolfgang Miakoda - Through the Void"
		Case 2
			Channel_Music = PlaySound(Music_ID[2])
			Music_Description$="Track 2"
		Case 3
			Channel_Music = PlaySound(Music_ID[3])
			Music_Description$="Track 3"
		Case 4
			Channel_Music = PlaySound(Music_ID[4])
			Music_Description$="Track 4"
		Case 5
			Channel_Music = PlaySound(Music_ID[5])
			Music_Description$="Elv - Into the Abyss"
		Case 6
			Channel_Music = PlaySound(Music_ID[6])
			Music_Description$="Elv - The Thrill of War"
	End Select		
	
End Function

Function Music_Update()
	
	Select Music_Theme
		Case 0
			
		Case 1
			If ChannelPlaying(Channel_Music)=False
				Randomsong(Rand(1,8))
				ChannelVolume Channel_Music,Music_Volume#
			EndIf
			If Music_Aggro_Timer > 0 Then Music_Theme=2
		Case 2
			Music_Volume#=Music_Volume#-0.01
			ChannelVolume Channel_Music,Music_Volume#
			If Music_Volume#<0.01 Then 
				StopChannel Channel_Music
				Music_Volume#=0.1
				Music_Theme=3
			EndIf
		Case 3
			If ChannelPlaying(Channel_Music)=False
				Randomsong(Rand(9,12))
				ChannelVolume Channel_Music,Music_Volume#
				EndIf
			If Music_Aggro_Timer < 1 Then Music_Theme=4
		Case 4
			Music_Volume#=Music_Volume#-0.01
			ChannelVolume Channel_Music,Music_Volume#
			If Music_Volume#<0.01 Then 
				StopChannel Channel_Music
				Music_Volume#=0.1
				Music_Theme=1
			EndIf
		Case 5
			
		Case 6
			
	End Select
End Function

Function Tutorial_Update()
	Select Tutorial_Step
		Case 0
			Tutorial_Step = 1
		Case 1	;Opening
			Channel_Tutorial = PlaySound(Sound_Tutorial[1])
			Tutorial_Step = 2
			Storyline_Subtitle$=""
		Case 2
			If ChannelPlaying(Channel_Tutorial) = False Then
				Tutorial_Step = 3
				Storyline_Subtitle$=""
			EndIf
		Case 3	;Opening
			Channel_Tutorial = PlaySound(Sound_Tutorial[2])
			Tutorial_Step = 4
			Storyline_Subtitle$=""
		Case 4
			If ChannelPlaying(Channel_Tutorial) = False Then
				Tutorial_Step = 5
				Storyline_Subtitle$=""
			EndIf
		Case 5
			Storyline_Descriptor = "Switch to Flight mode ("+Tutorial_Mission[0]+"/1)"
			If KeyDown(57) Then
				Tutorial_Mission[0]=1
				Tutorial_Step = 6
			EndIf
		Case 6
			Storyline_Descriptor = ""
			If ChannelPlaying(Channel_Tutorial) = False Then
				Tutorial_Step = 7
				Storyline_Subtitle$=""
			EndIf
		Case 7
			Channel_Tutorial = PlaySound(Sound_Tutorial[3])
			Tutorial_Step = 8
			Storyline_Subtitle$=""
		Case 8
			Storyline_Descriptor = "Fly full speed with W."
			If Player_Value_Shield_Current > Player_Value_Speed_Maximum-1 Then
				Tutorial_Mission[0]=1
				Tutorial_Step = 9
			EndIf
		Case 9
			Storyline_Descriptor = ""
			If ChannelPlaying(Channel_Tutorial) = False Then
				Tutorial_Step = 10
				Storyline_Subtitle$=""
			EndIf
		Case 10
			Channel_Tutorial = PlaySound(Sound_Tutorial[4])
			Tutorial_Step = 11
			Storyline_Subtitle$=""
		Case 11
			Storyline_Descriptor = "Stop the Ship with S."
			If Player_Value_Speed_Current < 1 Then
				Tutorial_Mission[0]=0
				Tutorial_Step = 12
			EndIf
		Case 12
			Storyline_Descriptor = ""
			If ChannelPlaying(Channel_Tutorial) = False Then
				Tutorial_Step = 13
				Storyline_Subtitle$=""
			EndIf
		Case 13
			Channel_Tutorial = PlaySound(Sound_Tutorial[5])
			Tutorial_Step = 14
			Storyline_Subtitle$=""	
		Case 14
			Storyline_Descriptor = "Strafe with A ("+String_Status[Tutorial_Mission[0]]+"), D ("+String_Status[Tutorial_Mission[1]]+"), R ("+String_Status[Tutorial_Mission[2]]+"), F ("+String_Status[Tutorial_Mission[3]]+"), Q ("+String_Status[Tutorial_Mission[4]]+") And E ("+String_Status[Tutorial_Mission[5]]+")."
			If KeyDown(30) Then
				Tutorial_Mission[0]=1
			ElseIf KeyDown(32) Then
				Tutorial_Mission[1]=1
			ElseIf KeyDown(16) Then
				Tutorial_Mission[2]=1
			ElseIf KeyDown(18) Then
				Tutorial_Mission[3]=1
			ElseIf KeyDown(19) Then
				Tutorial_Mission[4]=1
			ElseIf KeyDown(33) Then
				Tutorial_Mission[5]=1
			EndIf
			
			Local Progress = Tutorial_Mission[0]+Tutorial_Mission[1]+Tutorial_Mission[2]+Tutorial_Mission[3]+Tutorial_Mission[4]+Tutorial_Mission[5]
			
			If Progress = 6 Then
				Tutorial_Step = 15
			EndIf
		Case 15
			Storyline_Descriptor = ""
			If ChannelPlaying(Channel_Tutorial) = False Then
				Tutorial_Step = 16
				Storyline_Subtitle$=""
			EndIf
		Case 16
			Channel_Tutorial = PlaySound(Sound_Tutorial[6])
			Tutorial_Step = 17
			Storyline_Subtitle$=""
			
		Case 17
			If ChannelPlaying(Channel_Tutorial) = False Then
				Tutorial_Step = 18
				Storyline_Subtitle$=""
			EndIf
		Case 18
			Channel_Tutorial = PlaySound(Sound_Tutorial[7])
			Tutorial_Step = 19
			Storyline_Subtitle$=""
		Case 19
			If ChannelPlaying(Channel_Tutorial) = False Then
				If Tutorial_Crates_Destroyed > 9
					Tutorial_Step = 20
				EndIf
				Storyline_Subtitle$=""
			EndIf
		Case 20
			Channel_Tutorial = PlaySound(Sound_Tutorial[8])
			Tutorial_Step = 21
			Storyline_Subtitle$=""
		Case 21
			If ChannelPlaying(Channel_Tutorial) = False Then
				Tutorial_Step = 22
				Storyline_Subtitle$=""
			EndIf
		Case 22
			Channel_Tutorial = PlaySound(Sound_Easteregg[0]);ANGL EASTEREGG
			Tutorial_Step = 23
			Storyline_Subtitle$="Really... Commander? Really?"
		Case 23
			If ChannelPlaying(Channel_Tutorial) = False Then
				Tutorial_Step = 24
				Storyline_Subtitle$=""
			EndIf
		Case 24
			Channel_Tutorial = PlaySound(Sound_Tutorial[9])
			Tutorial_Step = 25
			Storyline_Subtitle$=""
		Case 25
			If ChannelPlaying(Channel_Tutorial) = False Then
				Tutorial_Step = 26
				Storyline_Subtitle$=""
			EndIf
		Case 26
			Select Character_Value_Faction
				Case Faction_Terran
					Channel_Tutorial = PlaySound(Sound_Tutorial[12])
				Case Faction_Sirian
					Channel_Tutorial = PlaySound(Sound_Tutorial[13])
			End Select
			Tutorial_Step = 27
			Storyline_Subtitle$=""
		Case 27
			If ChannelPlaying(Channel_Tutorial) = False Then
				Tutorial_Step = 28
				Storyline_Subtitle$=""
			EndIf
		Case 28
			Channel_Tutorial = PlaySound(Sound_Tutorial[14])
			Tutorial_Step = 29
			Storyline_Subtitle$=""
		Case 29
			If ChannelPlaying(Channel_Tutorial) = False Then
				Tutorial_Step = 30
				Storyline_Subtitle$=""
			EndIf
			
	End Select
End Function

Function Storyline_Update()
	If HUD = 1
		Text3D(Text_Font[14], 0, -320,Storyline_Person,1,0)
		Text3D(Text_Font[9], 0, -340,Storyline_Subtitle,1,0)
		If Twitch_EventState = 0 Then Text3D(Text_Font[9], 0, +50,Storyline_Descriptor,1,0)
	EndIf
	
	If Storyline_Step > 28 Then
		String_SystemName[9] = "Machine Sector #0C4"
		String_SystemFaction[9] = Faction_Crimson
	EndIf
	
	Select Storyline_Step
		Case 0
			If Character_Value_Level > 3 And Switch_System_init > 2 Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[0])
				Storyline_Step = 1
			EndIf
		Case 1
			If ChannelPlaying(Channel_Story) = False And Switch_System_init > 2 Then
				Select Character_Value_Faction
					Case Faction_Terran
						Channel_Story = PlaySound(Sound_Storyline_OLeary[11])
						Storyline_Person$="Cmdr. Ian O'Leary"
						Storyline_Subtitle$="Pilot, We've been informed of your outstanding deeds. Head to Terra for a new mission!"
						Storyline_Descriptor$ = "Head To Terra to receive further instructions."
					Case Faction_Sirian
						Channel_Story = PlaySound(Sound_Storyline_Gull[6])
						Storyline_Person$="Adm. Karen Gull"
						Storyline_Subtitle$="Finally I'm able to get ahold of you. Head to Sirius Prime to assist us!"
						Storyline_Descriptor$ = "Head To Sirius Prime to receive further instructions."
				End Select
				Storyline_Step = 2
			EndIf
		Case 2
			If ChannelPlaying(Channel_Story) = False And Switch_System_init > 2 Then
				Storyline_Person$=""
				Storyline_Subtitle$=""
				Storyline_Step = 3
			EndIf
		Case 3
			If ChannelPlaying(Channel_Story) = False And Switch_System_init > 2 Then
				If System_Name$ = "Terra" And Character_Value_Faction = Faction_Terran Then
					Channel_Story = PlaySound(Sound_Storyline_OLeary[12])
					Storyline_Person$="Cmdr. Ian O'Leary"
					Storyline_Subtitle$="Thank you for coming so fast. It seems there is a new Gate in Clashing Fates. I'd like you to investigate."
					Storyline_Step = 4
					Storyline_Descriptor$ = ""
				ElseIf System_Name$ = "Sirius Prime" And Character_Value_Faction = Faction_Sirian Then
					Channel_Story = PlaySound(Sound_Storyline_Gull[7])
					Storyline_Person$="Adm. Karen Gull"
					Storyline_Subtitle$="There you are. There are reports of a new Gate in Clashing Fates. Would you please investigate it?"
					Storyline_Step = 4
					Storyline_Descriptor$ = ""
				EndIf
				Title_Timer = 15
			EndIf
		Case 4
			If ChannelPlaying(Channel_Story) = False Then
				DrawImage3D(GUI_Interface[12],0,0,0,0,1.5)
			EndIf
			If Title_Timer < 1 Then 
				Storyline_Step = 5
			EndIf
		Case 5
			If ChannelPlaying(Channel_Story) = False And Switch_System_init > 2 Then
				Storyline_Descriptor$ = "Find the System Clashing Fates and Scan for a new gate."
				Storyline_Person$=""
				Storyline_Subtitle$=""
				If System_Name$ = "Clashing Fates" Then
					Storyline_Step = 6
				EndIf
			EndIf
		Case 6
			Storyline_Descriptor$ = "Use Shift+V to Scan. Check for an Orange Ping."
			If System_Name$ = "Unknown System" Then
				Storyline_Step = 7
			EndIf
		Case 7
			Storyline_Descriptor$ = ""
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[1])
				Storyline_Person$="BR14N"
				Storyline_Subtitle$="There are unusual Structures around here. Incoming Communication."
				Storyline_Step = 8
			EndIf
		Case 8
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[2])
				Storyline_Person$="BR14N"
				Storyline_Subtitle$="It is encrypted with the Colonial War standard."
				Storyline_Step = 9
			EndIf
		Case 9
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[3])
				Storyline_Person$="BR14N"
				Storyline_Subtitle$="According to the Datestamp, it originates from someone called 'Crimson'"
				Storyline_Step = 10
			EndIf
		Case 10
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[4])
				Storyline_Person$="BR14N"
				Storyline_Subtitle$="No further Information available. Transmitting Data to Faction Authorities."
				Storyline_Step = 11
			EndIf
		Case 11
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[5])
				Storyline_Person$="BR14N"
				Storyline_Subtitle$="Transmission might have been Intercepted. Detecting Unusual Noise on Secure Frequency."
				Storyline_Step = 12
			EndIf
		Case 12
			If ChannelPlaying(Channel_Story) = False And Switch_System_init > 2 Then
				Channel_Story = PlaySound(Sound_Storyline_OLeary[1])
				Storyline_Person$="Cmdr. Ian O'Leary"
				Storyline_Subtitle$="Fleet CMD of the TFC Eris. We have received your Data and are ready to jump in. We'd like to inves"
				Storyline_Step = 13
			EndIf
		Case 13
			If ChannelPlaying(Channel_Story) = False And Switch_System_init > 2 Then
				Channel_Story = PlaySound(Sound_Storyline_Gull[1])
				Storyline_Person$="Adm. Karen Gull"
				Storyline_Subtitle$="Not so fast. This is Adm. Karen Gull from the Twofold. We are ready to claim the Sector. Any Resistance will be met with deadly force."
				Storyline_Step = 14
			EndIf
		Case 14
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[6])
				Storyline_Person$="BR14N"
				Storyline_Subtitle$="Captain, I advise you to clear the area around the Stargate. I expect a full on battle happening."
				Storyline_Step = 15
			EndIf
		Case 15
			If ChannelPlaying(Channel_Story) = False Then
				If System_Name="Unknown System" Then
					Storyline_Descriptor="Get some distance from the gate. A fight will happen."
					Storyline_Person=""
					Storyline_Subtitle=""
					Storyline_Step=16
				Else
					Storyline_Descriptor="Head back to the unknown system."
				EndIf
			EndIf
		Case 16
			Portal.Stargate = First Stargate
			Storyline_Descriptor="Get some distance from the gate. A fight will happen. ( "+Floor(EntityDistance(eShipBody,Portal\Mesh))+" of 30.000m )"
;			If Floor(EntityDistance(eShipBody,Portal\Mesh)) > 30000 Then
;				AI_Spawn(-14000,0,-110000,Faction_Terran,Job_Capital,1,"TFC Eris")
;				AI_Spawn(14000,0,-110000,Faction_Sirian,Job_Capital,1,"Commandship Twofold")
;				For A = 1 To 15
;					AI_Spawn(14000+Rnd(-1000.0,1000.1),Rnd(-1000.0,1000.1),-110000+Rnd(-1000.0,1000.1),Faction_Sirian,Job_Fighter,Rand(1,2),"Sirius Claim Fleet")
;				Next
;				For A = 1 To 15
;					AI_Spawn(-14000+Rnd(-1000.0,1000.1),Rnd(-1000.0,1000.1),-110000+Rnd(-1000.0,1000.1),Faction_Terran,Job_Fighter,Rand(1,2),"Terran Recon Fleet")
;				Next
;				Storyline_Step=17
;			EndIf
		Case 17
			Storyline_Descriptor$ = ""
			Channel_Story = PlaySound(Sound_Storyline_BRIAN[0])
			Storyline_Person$="BRIAN"
			Storyline_Subtitle$="We are being hailed."
			Storyline_Step = 18
		Case 18
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Crimson[1])
				Storyline_Person$="????"
				Storyline_Subtitle$="WE ARE THE CRIMSON. YOU ARE TRESPASSING. WE DO NOT WARN. WE DO NOT YIELD. FINAL TRANSMISSION."
				Storyline_Step = 19
			EndIf
		Case 19
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_OLeary[3])
				Storyline_Person$="O'Leary"
				Storyline_Subtitle$="All Stations, red alert. *beep beep* Prepare for combat readiness!"
;				For Ship.AI = Each AI
;					If Ship\Job = Job_Fighter Then
;						Ship\State_Destruction = Rand(0,1)
;					EndIf
;				Next
				CurrentZone = Faction_Crimson
				Storyline_Step=20
			EndIf
		Case 20
			Storyline_Descriptor=""
			Storyline_Person=""
			Storyline_Subtitle=""
			Storyline_Step=21
		Case 21
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gull[3])
				PlaySound(Sound_Storyline_OLeary[4])
				Storyline_Person$="O'Leary and Gull"
				Storyline_Subtitle$="WHAT THE HELL!? // What... just... happened?"
				Storyline_Step = 22
			EndIf
		Case 22
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gull[4])
				Storyline_Person$="Karen Gull"
				Storyline_Subtitle$="Remaining ships of the Fleet: ENter Expedition Mode and get out of this system."
				Storyline_Step = 23
			EndIf
		Case 23
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_OLeary[5])
				Storyline_Person$="O'Leary"
				Storyline_Subtitle$="All ships in the vincinity: Priority Message. Retreat Immediately, Retreat!"
				Storyline_Step = 24
			EndIf
		Case 24
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_OLeary[6])
				Storyline_Person$="O'Leary"
				Storyline_Subtitle$="Why haven't we jumped out yet? WHAT?"
				Storyline_Step = 25
			EndIf
		Case 25
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_OLeary[7])
				Storyline_Person$="O'Leary"
				Storyline_Subtitle$="Our Jumpdrive is gone? How? I don't"
				Storyline_Step = 26
			EndIf
		Case 26
			If ChannelPlaying(Channel_Story) = False Then
;				For Ship.AI = Each AI
;					If Ship\Job = Job_Capital And Ship\Name="TFC Eris" Then
;						Ship\State_Destruction = 1
;					EndIf
;				Next
				Storyline_Step=27
			EndIf
		Case 27
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[7])
				Storyline_Person$="BRIAN"
				Storyline_Subtitle$="That was an Impressive show of force. I advise you to leave."
				Storyline_Step = 28
			EndIf
		Case 28
			If ChannelPlaying(Channel_Story) = False Then
				Storyline_Person$=""
				Storyline_Subtitle$=""
				If System_Name <> "Unknown System" And  System_Name <> "Machine Installation #0F3" 
					Storyline_Step = 29
				EndIf
			EndIf		;ENd of Interfaction COnflict Story
		Case 29
			Storyline_Descriptor = "Rank up and Explore to receive more Information about what happened."
			If Character_Value_Level > 6 And Switch_System_init > 2 And System_Name = "Farout" Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[0])
				Storyline_Person$="BRIAN"
				Storyline_Subtitle$="We are being hailed."
				Storyline_Step = 30
			EndIf
		Case 30
			If ChannelPlaying(Channel_Story) = False Then
				Storyline_Person$="BRIAN"
				Storyline_Subtitle$="Hey! You're the one that escaped the Crimson thing, right?"
				Channel_Story = PlaySound(Sound_Storyline_Burnert[1])
				Storyline_Step = 31
			EndIf
		Case 31
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[8])
				Storyline_Person$="BRIAN"
				Storyline_Subtitle$="Signal seems to originate from that really run down freighter."
				Storyline_Step = 32
				Special_Create(0,0,85000,5)
				Storyline_Descriptor = "Investigate the Freighter near the North Area of the System"
			EndIf
		Case 32
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_Burnert[2])
					Storyline_Descriptor = "Listen to Kyle's proposal"
					Storyline_Person$="Kyle James Burnert"
					Storyline_Subtitle$="Names' Kyle James Burnard, Private Investigator. Heard there's a bounty on your head for blowing up two fleets..."
					Storyline_Step = 33
				EndIf
			EndIf
		Case 33
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_BRIAN[9])
					Storyline_Person$="BRIAN"
					Storyline_Subtitle$="Captain, I advise you to stay cautious."
					Storyline_Step = 34
				EndIf
			EndIf
		Case 34
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_Burnert[3])
					Storyline_Person$="Kyle James Burnert"
					Storyline_Subtitle$="It's alright. No one knows you're here. Something seems to mask your Ship ID."
					Storyline_Step = 35
					Storyline_Descriptor = ""
				EndIf
			EndIf
		Case 35
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_BRIAN[10])
					Storyline_Person$="BRIAN"
					Storyline_Subtitle$="Mask our ship ID? Running internal diagnostics."
					Storyline_Step = 36
				EndIf
			EndIf
		Case 36
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_Burnert[4])
					Storyline_Person$="Kyle"
					Storyline_Subtitle$="You can call me Kyle. And don't worry, I don't think you're capable of blowing up two fleets of ships."
					Storyline_Step = 37
				EndIf
			EndIf
		Case 37
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_BRIAN[11])
					Storyline_Person$="BRIAN"
					Storyline_Subtitle$="Did he just insult the capabilities of our ship?"
					Storyline_Step = 38
				EndIf
			EndIf
		Case 38
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_Burnert[5])
					Storyline_Person$="Kyle"
					Storyline_Subtitle$="Oh no, oh no. I didn't want to devalue your power. But instantly destryoing, what, 40 ships? Thats... improbable."
					Storyline_Step = 39
				EndIf
			EndIf
		Case 39
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_Burnert[6])
					Storyline_Person$="Kyle"
					Storyline_Subtitle$="You see, I want to investigate it. Check whats left, pick the scrap, you know."
					Storyline_Step = 40
				EndIf
			EndIf
		Case 40
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_Burnert[7])
					Storyline_Person$="Kyle"
					Storyline_Subtitle$="Could be Quite the profit in for us, maybe even an upgrade for your heap of junk."
					Storyline_Step = 41
				EndIf
			EndIf
		Case 41
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_BRIAN[12])
					Storyline_Person$="BRIAN"
					Storyline_Subtitle$="Well, it would not hurt getting an Upgrade."
					Storyline_Step = 42
				EndIf
			EndIf
		Case 42
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_Burnert[8])
					Storyline_Person$="Kyle"
					Storyline_Subtitle$="You in or can I report your Location to the Authorities?"
					Storyline_Step = 43
				EndIf
			EndIf
		Case 43
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_Burnert[9])
					Storyline_Person$="Kyle"
					Storyline_Subtitle$="Good. Meet me at the east gate of the Clashing Fates."
					Storyline_Step = 44
				EndIf
			EndIf
		Case 44
			If ChannelPlaying(Channel_Story) = False Then
				Storyline_Descriptor = "Head to the East Gate of Clashing Fates"
				Storyline_Person$=""
				Storyline_Subtitle$=""
				If System_Name = "Clashing Fates" Then
					Storyline_Step = 45
				EndIf
			EndIf
		Case 45
			If System_Name = "Clashing Fates"
;				PositionEntity Story_Pivot,85000,0,0
				Storyline_Step = 46
				Storyline_Descriptor = ""
			EndIf
		Case 46
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_Burnert[10])
					Storyline_Person$="Kyle"
					Storyline_Subtitle$="Oh, you're here. I did a bit of digging and it looks like someone has been here already sifting through what was left."
					Storyline_Step = 47
				EndIf
			EndIf
		Case 47
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_Burnert[11])
					Storyline_Person$="Kyle"
					Storyline_Subtitle$="Theres nothing left other than scrap metal and old lunch pails. I'm sending you a... modified upgrade to your scanner."
					Storyline_Step = 48
				EndIf
			EndIf
		Case 48
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_BRIAN[15])
					Storyline_Person$="BRIAN"
					Storyline_Subtitle$="Receiving Software Update."
					Storyline_Step = 49
				EndIf
			EndIf
		Case 49
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_BRIAN[16])
					Storyline_Person$="BRIAN"
					Storyline_Subtitle$="Software Update completed. Scanning Range Increased."
					Storyline_Step = 50
				EndIf
			EndIf
		Case 50
			If EntityDistance(WorldCamera, Story_Pivot)<2500 Then
				If ChannelPlaying(Channel_Story) = False Then
					Channel_Story = PlaySound(Sound_Storyline_Burnert[12])
					Storyline_Person$="BRIAN"
					Storyline_Subtitle$="See if you can Scan for something. I'm outta here."
					Storyline_Step = 51
				EndIf
			EndIf
		Case 51
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[14])
				Storyline_Person$="BRIAN"
				Storyline_Subtitle$="Remember, Use Shift+V to scan for objects."
				Storyline_Step = 52
			EndIf
		Case 52
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[17])
				Storyline_Person$="BRIAN"
				Storyline_Subtitle$="That was certainly an interesting Character. Name and ID have been registered."
				Storyline_Step = 53
			EndIf
		Case 53
			If ChannelPlaying(Channel_Story) = False Then
				Storyline_Person$=""
				Storyline_Subtitle$=""
			EndIf
			Storyline_Descriptor = "Rank up and Explore to receive more Information about what happened."
			If Character_Value_Level > 9 And Switch_System_init > 2 And System_Name = "Irreversible Thought" Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[0])
				Storyline_Person$="BRIAN"
				Storyline_Subtitle$="We are being hailed."
				Storyline_Step = 54
			EndIf
		Case 54
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gardner[1])
				Storyline_Person$="????"
				Storyline_Subtitle$="Pilot, Stop your ship."
				Storyline_Step = 55
			EndIf
		Case 55
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gardner[2])
				Storyline_Person$="????"
				Storyline_Subtitle$="If you don't stop, I will mark your ship as hostile to everyone."
				Storyline_Step = 56
			EndIf
		Case 56
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[18])
				Storyline_Descriptor = "Listen to Miss Gardners Proposal."
				Storyline_Person$="BRiaN"
				Storyline_Subtitle$="Captain, to preserve our Life, I will stop the ship now."
				Storyline_Step = 57
				Player_Value_Speed_Current = 0
			EndIf
		Case 57
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gardner[3])
				Storyline_Person$="????"
				Storyline_Subtitle$="Right, I wanted to have a word with you."
				Storyline_Step = 58
			EndIf
		Case 58
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gardner[4])
				Storyline_Person$="Sp. Agent Tina Gardner"
				Storyline_Subtitle$="I'm Special Agent Tina Gardner. Sent by your faction to investigate the Crimson Incident."
				Storyline_Step = 59
			EndIf
		Case 59
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[19])
				Storyline_Person$="BrIaan"
				Storyline_Subtitle$="The Crimson Incident?"
				Storyline_Step = 60
			EndIf
		Case 60
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gardner[5])
				Storyline_Person$="Sp. Agent Tina Gardner"
				Storyline_Subtitle$="Yeah, it does have a name now. It went around in pretty big circles."
				Storyline_Step = 61
			EndIf
		Case 61
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gardner[6])
				Storyline_Person$="Sp. Agent Tina Gardner"
				Storyline_Subtitle$="I've been tasked to seek you out about information what happened in that new unknown sector."
				Storyline_Step = 62
			EndIf
		Case 62
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gardner[7])
				Storyline_Person$="Sp. Agent Tina Gardner"
				Storyline_Subtitle$="It looks like your ship computer has been infected with a kind of ... virus, that immunized it against the attack."
				Storyline_Step = 63
			EndIf
		Case 63
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[20])
				Storyline_Person$="BR1A|\|"
				Storyline_Subtitle$="Infected? I? Impossible. I already ran diagnostics and can clearly say, Miss Gardna is insane."
				Storyline_Step = 64
			EndIf
		Case 64
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gardner[8])
				Storyline_Person$="Sp. Agent Tina 'Insane' Gardner"
				Storyline_Subtitle$="I want you to go back. Maybe we can find some Evidence to help understand what we are facing."
				Storyline_Descriptor = "Go back to the Unknown Sector you found."
				Storyline_Step = 65
			EndIf
		Case 65
			If ChannelPlaying(Channel_Story) = False Then
				Storyline_Person$=""
				Storyline_Subtitle$=""
				If System_Name$="Machine Installation #0F3" Then
					Storyline_Step = 66
				EndIf
			EndIf
		Case 66
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[22])
				Storyline_Person$="BrIaan"
				Storyline_Subtitle$="I detect another Transmission. It is unencrypted and only contains audio."
				Storyline_Step = 67
			EndIf
		Case 67
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_OLeary[8])
				Storyline_Person$="Injured O'Leary"
				Storyline_Subtitle$="I do not know what happened or where they are taking our rescue pods..."
				Storyline_Step = 68
			EndIf
		Case 68
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_OLeary[9])
				Storyline_Person$="Injured O'Leary"
				Storyline_Subtitle$="The Sky is dark. The system is strewn with strange Installations."
				Storyline_Step = 69
			EndIf
		Case 69
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_OLeary[10])
				Storyline_Person$="Injured O'Leary"
				Storyline_Subtitle$="We didn't com through any gate... I dont know, i donno, idonno[...]"
				
				Storyline_Step = 70
			EndIf
		Case 70
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[23])
				Storyline_Person$="BR|4N"
				Storyline_Subtitle$="End of Decrypted Transmission"
				
				Storyline_Step = 71
			EndIf
		Case 71
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gardner[9])
				Storyline_Person$="Sp. Agent Tina Gardner"
				Storyline_Subtitle$="That is interesting. O'leary still alive, not just... in space of our star charts."
				Storyline_Step = 72
			EndIf
		Case 72
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gardner[10])
				Storyline_Person$="Sp. Agent Tina Gardner"
				Storyline_Subtitle$="I don't recognize the background data on this location Document."
				Storyline_Step = 73
			EndIf
		Case 73
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gardner[11])
				Storyline_Person$="Sp. Agent Tina Gardner"
				Storyline_Subtitle$="I have to talk to my superiors. We'll talk again soon, you're a valueable asset."
				Storyline_Step = 74
			EndIf	
		Case 74
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gardner[12])
				Storyline_Person$="Sp. Agent Tina Gardner"
				Storyline_Subtitle$="Some funds have been transmitted to your account for your troubles. Don't run away."
				Storyline_Step = 75
			EndIf
		Case 75
			If ChannelPlaying(Channel_Story) = False Then
				Character_Value_XP = Character_Value_XP + 2500
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[24])
				Storyline_Person$="8R1AN"
				Storyline_Subtitle$="We have received funds and a promotion."
				Storyline_Step = 76
			EndIf
		Case 76
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Gardner[13])
				Storyline_Person$="Sp. Agent Tina Gardner"
				Storyline_Subtitle$="Special Agent Gardna - out."
				Storyline_Step = 77
			EndIf
		Case 77
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[25])
				Storyline_Person$="8R1AN"
				Storyline_Subtitle$="Captain, detecting unknown charge in my system."
				Storyline_Step = 78
			EndIf
		Case 78
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[26])
				Storyline_Person$="8R1AN"
				Storyline_Subtitle$="Unlicensed attempt to activate a nonexistant jumpdrive.
				Storyline_Step = 79
			EndIf
		Case 79
			If ChannelPlaying(Channel_Story) = False Then
				World_Clear()
				World_Generate(19,0,0,0)
				Storyline_Step = 80
			EndIf
		Case 80
			Player_Value_Speed_Current = 0
			HUD = 0: eCameraMode = MODE_CAMERA
			
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[27])
				Storyline_Person$="8R1AN"
				Storyline_Subtitle$="Scanning Data. Checking nearby stars for correlaasjibpivbpivbpriszbvsiorzudolvbtdro"
				Storyline_Step = 81
			EndIf
		Case 81
			Player_Value_Speed_Current = 0
			HUD = 0: eCameraMode = MODE_CAMERA
			
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Messenger[1])
				Storyline_Person$="The Messenger"
				Storyline_Subtitle$="This world is not as small as you seem to make it."
				Storyline_Step = 82
			EndIf
		Case 82
			Player_Value_Speed_Current = 0
			HUD = 0: eCameraMode = MODE_CAMERA
			
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Messenger[2])
				Storyline_Person$="The Messenger"
				Storyline_Subtitle$="Your Journey has just started and your species still has to evolve."
				Storyline_Step = 83
			EndIf
		Case 83
			Player_Value_Speed_Current = 0
			HUD = 0: eCameraMode = MODE_CAMERA
			
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Messenger[3])
				Storyline_Person$="The Messenger"
				Storyline_Subtitle$="I am the messenger."
				Storyline_Step = 84
			EndIf
		Case 84
			Player_Value_Speed_Current = 0
			HUD = 0: eCameraMode = MODE_CAMERA
			
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Messenger[4])
				Storyline_Person$="The Messenger"
				Storyline_Subtitle$="I bring to you a message from an entity beyond your comprehension."
				Storyline_Step = 85
			EndIf
		Case 85
			Player_Value_Speed_Current = 0
			HUD = 0: eCameraMode = MODE_CAMERA
			
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Messenger[5])
				Storyline_Person$="The Messenger"
				Storyline_Subtitle$="But do not worry, young one. Do not despair."
				Storyline_Step = 86
			EndIf
		Case 86
			Player_Value_Speed_Current = 0
			HUD = 0: eCameraMode = MODE_CAMERA
			
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Messenger[6])
				Storyline_Person$="The Messenger"
				Storyline_Subtitle$="It is, what it is. And nothing can change that."
				Storyline_Step = 87
			EndIf
		Case 87
			Player_Value_Speed_Current = 0
			HUD = 0: eCameraMode = MODE_CAMERA
			
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Messenger[7])
				Storyline_Person$="The Messenger"
				Storyline_Subtitle$="Until we meet. Again."
				Storyline_Step = 88
			EndIf
		Case 88
			Player_Value_Speed_Current = 0
			HUD = 0: eCameraMode = MODE_CAMERA
			
			If ChannelPlaying(Channel_Story) = False Then
				Channel_Story = PlaySound(Sound_Storyline_Messenger[8])
				Storyline_Person$="The Messenger"
				Storyline_Subtitle$="Messenger End."
				Storyline_Step = 89
			EndIf
		Case 89
			HUD = 1
			If ChannelPlaying(Channel_Story) = False Then
				World_Clear()
				Select Character_Value_Faction
					Case Faction_Terran
						World_Generate(7,0,0,0)
					Case Faction_Sirian
						World_Generate(3,0,0,0)
				End Select
				Channel_Story = PlaySound(Sound_Storyline_BRIAN[28])
				Storyline_Person$="Personal Assistant Computer BR-14-N"
				Storyline_Subtitle$="Bootup sequence Complete. Welcome aboard Captain."
				Storyline_Step = 90
				UserDataSave(1)
			EndIf
		Case 90
			If ChannelPlaying(Channel_Story) = False Then
				Storyline_Person$=""
				Storyline_Subtitle$=""
				Storyline_Descriptor$=""
			EndIf
			
	End Select
End Function

Function GenAudio_Update()
	Select Switch_System_init
		Case 0
			
			Channel_SystemInit = PlaySound(Sound_SystemInit[1])
			Switch_System_init = 1
			
		Case 1
			If ChannelPlaying(Channel_SystemInit) = False Then
				Switch_System_init = 2
			EndIf
		Case 2
			
			Channel_SystemInit = PlaySound(Sound_SystemInit[System_TextID])
			Switch_System_init = 3
			
			
	End Select
	
End Function


;~IDEal Editor Parameters:
;~F#5#46#BF#C7#D7#F0#10F#120#130#143#1F9#21F#254#307
;~C#Blitz3D