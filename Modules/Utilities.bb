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

Function WordWrap(content$, maxWidth, StartX, StartY)
	Local CompleteLength = Len(content$)
	;Use Left to cut string into bits
	Repeat
		
	Until Instr (content$, " ") = False
	
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

Function PlayerSwitchShip(sid)
	FreeEntity eShipBody
	
	eShipBody = CopyEntity (Mesh_Ship[sid],eShip)
	GetPlayerShipValues(sid)
	
	EntityType pvShip,Collision_Player
	
End Function

Function GetPlayerShipValues(ShipID)
	Local Shipinfo$
	
	For Engine.Flare = Each Flare
		FreeEntity Engine\mesh
		Delete Engine
	Next
	
	EntityType WorldCamera,0
	Local ShipFile = OpenFile("Assets\Manifest\LoadShips.lof")
	For A = 1 To ShipID
		Shipinfo$ = ReadLine(ShipFile)
	Next
	CloseFile ShipFile
	Local Path$ ="Assets\Config\"+Shipinfo$+".bfc"
	
	Local ShipData = ReadFile(Path$)
	Local XCollision, YCollision, FlareX, FlareY, FlareZ, FlareSX#, FlareSY#, FlareRot,FlareWgt
	
	Repeat
		Local ShipRead$ = ReadLine(ShipData)
		If Instr(ShipRead$,"ShipName=") Then ShipRead$ = Replace$(ShipRead$,"ShipName=",""): Player_Environment_Shipname$ = ShipRead$
		If Instr(ShipRead$,"ShipClass=") Then ShipRead$ = Replace$(ShipRead$,"ShipClass=",""): Player_Environment_ShipClass$ = ShipRead$
		If Instr(ShipRead$,"BaseArmor=") Then ShipRead$ = Replace$(ShipRead$,"BaseArmor=",""): Player_Environment_BaseArmor = Int(ShipRead$)
		If Instr(ShipRead$,"BaseShield=") Then ShipRead$ = Replace$(ShipRead$,"BaseShield=",""): Player_Environment_BaseShield = Int(ShipRead$)
		If Instr(ShipRead$,"BaseAccel=") Then ShipRead$ = Replace$(ShipRead$,"BaseAccel=",""): Player_Environment_BaseAccel# = Int(ShipRead$): Player_Environment_BaseAccel#= Player_Environment_BaseAccel#/10
		If Instr(ShipRead$,"BaseMSpeed=") Then ShipRead$ = Replace$(ShipRead$,"BaseMSpeed=",""): Player_Environment_BaseMSpeed = Int(ShipRead$): Player_Environment_BaseMSpeed = Player_Environment_BaseMSpeed/3
		If Instr(ShipRead$,"BaseTurn=") Then ShipRead$ = Replace$(ShipRead$,"BaseTurn=",""): Player_Environment_BaseTurn# = Int(ShipRead$): Player_Environment_BaseTurn#= Player_Environment_BaseTurn#/10
		If Instr(ShipRead$,"BaseEnergy=") Then ShipRead$ = Replace$(ShipRead$,"BaseEnergy=",""): Player_Environment_BaseEnergy = Int(ShipRead$)
		If Instr(ShipRead$,"SlotsWeapon=") Then ShipRead$ = Replace$(ShipRead$,"SlotsWeapon=","")
		If Instr(ShipRead$,"SlotsArmor=") Then ShipRead$ = Replace$(ShipRead$,"SlotsArmor=","")
		If Instr(ShipRead$,"SlotsShield=") Then ShipRead$ = Replace$(ShipRead$,"SlotsShield=","")
		If Instr(ShipRead$,"SlotsCore=") Then ShipRead$ = Replace$(ShipRead$,"SlotsCore=","")
		If Instr(ShipRead$,"SlotsEngine=") Then ShipRead$ = Replace$(ShipRead$,"SlotsEngine=","")
		If Instr(ShipRead$,"SlotsMining=") Then ShipRead$ = Replace$(ShipRead$,"SlotsMining=","")
		If Instr(ShipRead$,"SlotsResearch=") Then ShipRead$ = Replace$(ShipRead$,"SlotsResearch=","")
		If Instr(ShipRead$,"SlotsCargo=") Then ShipRead$ = Replace$(ShipRead$,"SlotsCargo=","")
		If Instr(ShipRead$,"CameraOffsetZ=") Then ShipRead$ = Replace$(ShipRead$,"CameraOffsetZ=",""): Zoffset = Int(ShipRead$)
		If Instr(ShipRead$,"CameraOffsetY=") Then ShipRead$ = Replace$(ShipRead$,"CameraOffsetY=",""): Yoffset = Int(ShipRead$)
		If Instr(ShipRead$,"CollisionSphereX=") Then ShipRead$ = Replace$(ShipRead$,"CollisionSphereX=",""): XCollision = Int(ShipRead$)
		If Instr(ShipRead$,"CollisionSphereY=") Then ShipRead$ = Replace$(ShipRead$,"CollisionSphereY=",""): YCollision = Int(ShipRead$)
		If Instr(ShipRead$,"EngineConfig") Then 
			ShipRead$ = ReadLine(ShipData): If Instr(ShipRead$,"FlareX=") Then ShipRead$ = Replace$(ShipRead$,"FlareX=",""): FlareX = Int(ShipRead$)
			ShipRead$ = ReadLine(ShipData): If Instr(ShipRead$,"FlareY=") Then ShipRead$ = Replace$(ShipRead$,"FlareY=",""): FlareY = Int(ShipRead$)
			ShipRead$ = ReadLine(ShipData): If Instr(ShipRead$,"FlareZ=") Then ShipRead$ = Replace$(ShipRead$,"FlareZ=",""): FlareZ = Int(ShipRead$)
			ShipRead$ = ReadLine(ShipData): If Instr(ShipRead$,"FlareScaleX=") Then ShipRead$ = Replace$(ShipRead$,"FlareScaleX=",""): FlareSX# = Int(ShipRead$): FlareSX# = FlareSX# / 10
			ShipRead$ = ReadLine(ShipData): If Instr(ShipRead$,"FlareScaleY=") Then ShipRead$ = Replace$(ShipRead$,"FlareScaleY=",""): FlareSY# = Int(ShipRead$): FlareSY# = FlareSY# / 10
			ShipRead$ = ReadLine(ShipData): If Instr(ShipRead$,"FlareRotation=") Then ShipRead$ = Replace$(ShipRead$,"FlareRotation=",""): FlareRot = Int(ShipRead$)
			ShipRead$ = ReadLine(ShipData): If Instr(ShipRead$,"FlareWeight=") Then ShipRead$ = Replace$(ShipRead$,"FlareWeight=",""): FlareWgt = Int(ShipRead$)
			Asset_Flare_Create( FlareX, FlareY, FlareZ,FlareSX#,FlareSY#,FlareRot,FlareWgt,255,255,255)
		EndIf
	Until Eof(ShipData)
	
	EntityRadius pvShip,XCollision, YCollision
	CloseFile ShipData
	Player_Value_Speed_Maximum = Player_Environment_BaseMSpeed
	
End Function

Type MapWaypoints
	Field X, Y, Z, PT, MPX, MPY, MPZ
End Type

Global MP_SCALE=1000
Global MP_TOGGL=3

Function Environment_NavMesh_Create(x,y,z, styp)
	XY.MapWaypoints = New MapWaypoints
	XY\MPX=x
	XY\MPY=y
	XY\MPZ=z
	XY\X = 0
	XY\Y = 0
	XY\Z = 0
	XY\PT=styp
End Function

Function UpdateMapScale(Scale)
	
	If Scale > 4  Or Scale < 1 Then
		Scale = 1
	EndIf
	
    If Scale > 0 And Scale < 5
        MP_SCALE = 1000 / Scale
    EndIf
	
	For XY.MapWaypoints = Each MapWaypoints
		XY\X = XY\MPX / MP_SCALE
		XY\Y = XY\MPY / MP_SCALE
		XY\Z = XY\MPZ / MP_SCALE
	Next
End Function

Function Environment_NavMesh_Update()
	If HUD = 1
		Local CPosX#=EntityX(pvShip)/MP_SCALE
		Local CPosY#=EntityY(pvShip)/MP_SCALE
		Local CPosZ#=EntityZ(pvShip)/MP_SCALE
		
		MapOriginX=GraphicsWidth()/2-(16+128)
		MapOriginZ=GraphicsHeight()/2-(16+128)
		
		DrawImage3D(GUI_Game[9],MapOriginX,MapOriginZ,0,-EntityYaw(eShipBody, True),2)
		
		For XY.MapWaypoints = Each MapWaypoints
			Local WaypointX# = XY\X
			Local WaypointY# = XY\Y
			Local WaypointZ# = XY\Z
			
			If WaypointX# > CPosX# - 113 And WaypointX# < CPosX# + 113 And WaypointZ# > CPosZ# - 113 And WaypointZ# < CPosZ# + 113 And WaypointY# > CPosY# - 113 And WaypointY# < CPosY# + 113
				Local DrawPosX# = WaypointX# - CPosX#
				Local DrawPosY# = WaypointY# - CPosY#
				Local DrawPosZ# = WaypointZ# - CPosZ#
				
	;;			If (DrawPosZ# + DrawPosY#)*-1 < MapOriginZ+113 Then DrawPosZ = MapOriginZ+113
				
				DrawImage3D(GUI_Game[12], MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1)))
				
				Select XY\PT
					Case 1 ;Asteroid
						DrawImage3D(GUI_Game[7], MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1) - (DrawPosY# * -1)))
						Line3D(GUI_Game[4],MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1)), MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1) - (DrawPosY# * -1)),0.5)
					Case 2 ;Gate
						DrawImage3D(GUI_Game[5], MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1)), 0, 0, 1)
						Line3D(GUI_Game[4],MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1)), MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1) - (DrawPosY# * -1)),0.5)
					Case 3 ;Border Post
						DrawImage3D(GUI_Game[15], MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1)), 0, 0, 1)
						Line3D(GUI_Game[4],MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1)), MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1) - (DrawPosY# * -1)),0.5)
					Case 4 ;Trade Station
						DrawImage3D(GUI_Game[13], MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1)), 0, 0, 1)
						Line3D(GUI_Game[4],MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1)), MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1) - (DrawPosY# * -1)),0.5)
					Case 5 ;Mining Dock
						DrawImage3D(GUI_Game[14], MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1)), 0, 0, 1)
						Line3D(GUI_Game[4],MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1)), MapOriginX - (DrawPosX# * -1), (MapOriginZ - (DrawPosZ# * -1) - (DrawPosY# * -1)),0.5)
						
					;Add other cases as needed
				End Select        
			EndIf
		Next
	EndIf
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

Function Randomsong(number)
	StopChannel Channel_Music
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
	ChannelVolume Channel_Music,Music_Volume
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

Function Storyline_Update()
End Function

Function GenAudio_Update()
End Function

Function Asset_Flare_Create(x,y,z,scalex#,scaley#,rotation,weight=33,r=255,b=255,g=255)
	Engine.Flare = New Flare
	Engine\Mesh = CopyEntity(Mesh_Environment[2])
	Engine\X = x
	Engine\Y = y
	Engine\Z = z
	Engine\ScaleX# = scalex#
	Engine\ScaleY# = scaley#
	Engine\Rotation = rotation
	Engine\Weight = weight
	PositionEntity Engine\Mesh,EntityX(ShipPosXYZ)+engine\x, EntityY(ShipPosXYZ)+engine\y, EntityZ(ShipPosXYZ)+Engine\z
	EntityColor Engine\Mesh,r,g,b
End Function

Function Asset_Flare_Update()
	
	For Engine.Flare = Each Flare
		Local Speedpercent# = (Player_Value_Speed_Current/(Player_Value_Speed_Maximum/100))/Engine\Weight
		PositionEntity Engine\Mesh,EntityX(pvShip), EntityY(pvShip), EntityZ(pvShip)
		RotateEntity Engine\Mesh,EntityPitch(pvShip), EntityYaw(pvShip), EntityRoll(pvShip)
		TurnEntity Engine\Mesh,EntityPitch(eShipBody), EntityYaw(eShipBody), EntityRoll(eShipBody)
		MoveEntity Engine\Mesh, Engine\X, Engine\Y, Engine\Z
		TurnEntity Engine\Mesh,0,0,Engine\Rotation
		ScaleEntity Engine\Mesh,Engine\ScaleX,Engine\ScaleY,Speedpercent#+2
	Next
	
	PositionTexture Text_Environment[2], (MilliSecs()),1
End Function
;~IDEal Editor Parameters:
;~F#5#46#86#94#9C#AC#C6#E5#F7#107#11A#1D0#1F6#220#224#22B#2DE#327#32E#339
;~F#37B#390#3A9
;~C#Blitz3D