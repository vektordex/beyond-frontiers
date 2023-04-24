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

Function WordWrap(content$, maxWidth)
	
    ; Split the text into words
    Local words$ = SplitString(Text, " ")
	
    ; Build the wrapped text
    Local wrapped$ = ""
    Local Linestring$ = ""
    For Local i = 0 To words$-1
        Local word$ = words[i]
        If Len(Linestring) + Len(word) + 1 > maxWidth Then
            ; Add the current line to the wrapped text and start a new line
            wrapped = wrapped + Line + "\n"
            Line = word
        Else
            ; Add the current word to the current line
            If Len(Linestring) > 0 Then Linestring = Linestring + " "
            Linestring = Linestring + word
        EndIf
    Next
	
    ; Add the final line to the wrapped text
    wrapped = wrapped + Linestring
	
    ; Return the wrapped text
    Return wrapped
	
End Function

;------------------------------------------------------------------------------
;! Virtual Scene Management
;------------------------------------------------------------------------------

Global VirtualSceneEntity_Empty.VirtualSceneEntity = New VirtualSceneEntity

;Function VirtualScene_Create.VirtualScene()
;	Local VS.VirtualScene = New VirtualScene
;	Return VS
;End Function
;
;Function VirtualScene_Destroy(VS.VirtualScene)
;	Local VSE.VirtualSceneEntity
;	For VSE.VirtualSceneEntity = Each VirtualSceneEntity
;		If VSE\VS = VS Then Delete VSE
;	Next
;	Delete VS
;End Function
;
;Function VirtualScene_Register(VS.VirtualScene, Entity%)
;	Local VSE.VirtualSceneEntity = New VirtualSceneEntity
;	VSE\VS = VS
;	VSE\Entity = Entity
;	VSE\State = BS_Memory_PeekByte(VSE\Entity, 24)
;End Function
;
;Function VirtualScene_Unregister(VS.VirtualScene, Entity%)
;	Local VSE.VirtualSceneEntity
;	For VSE = Each VirtualSceneEntity
;		If (VSE\VS = VS) And (VSE\Entity = Entity) Then
;			Delete VSE:Exit
;		EndIf
;	Next
;End Function
;
;Function VirtualScene_Hide(VS.VirtualScene)
;	Local VSE.VirtualSceneEntity
;	For VSE = Each VirtualSceneEntity
;		If (VSE\VS = VS) Then
;			VSE\State = BS_Memory_PeekByte(VSE\Entity, 24)
;			HideEntity VSE\Entity
;		EndIf
;	Next
;End Function
;
;Function VirtualScene_Show(Vs.VirtualScene)
;	Local VSE.VirtualSceneEntity
;	For VSE = Each VirtualSceneEntity
;		If (VSE\VS = Vs) And (VSE\State = 1) Then
;			ShowEntity VSE\Entity
;		EndIf
;	Next
;End Function

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
;	VirtualScene_Unregister(Scene, eShipBody)
	
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
	
;	VirtualScene_Register(Scene,eShipBody)
	
End Function

Function GetPlayerShipValues(ShipID)
;	
;	
;	SHIxREL=Player_Value_Shield_Maximum/100
;	SHIxTIC=80
;	
;	Select ShipID
;			;CombatShips
;			
;		Case 1
;			Player_Value_Shield_Maximum=25000 
;			Player_Value_Armor_Maximum=12500 
;			Player_Value_Energy_Maximum=25000
;			Ship_Shield_Reload_Tick = 25
;			Ship_Shield_Reload_Amount = 190
;			
;			Player_Value_Miner_Strength#=.1
;			Player_Value_Inertia_Modifier#=1
;			
;			Ship_Value_ScanningStrength = 18000
;			
;			Player_Value_Inertia_Negative# = 0.94
;			Player_Value_Inertia_Positive# = 1.08
;			Player_Value_Inertia_Base# = 3
;			Player_Value_Speed_Maximum=15
;			
;			Ship_Gun_Hardpoints = 2
;;			Ship_Gun_Slot[1] = 1
;;			Ship_Gun_Slot[2] = 3
;;			Ship_Gun_Timer[1] = 26
;;			Ship_Gun_Timer[2] = 0
;			
;;			Ship_Gun_HPX[1]=-18
;;			Ship_Gun_HPY[1]=-3
;;			Ship_Gun_HPZ[1]=7
;			
;;			Ship_Gun_HPX[2]=18
;;			Ship_Gun_HPY[2]=-3
;;			Ship_Gun_HPZ[2]=7
;			
;			Ship_Rad_Resistance#=1
;		Case 2
;			Player_Value_Shield_Maximum=50000
;			Player_Value_Armor_Maximum=2500 
;			Player_Value_Energy_Maximum=2500
;			
;			Ship_Shield_Reload_Tick = 25
;			Ship_Shield_Reload_Amount = 190
;			
;			Player_Value_Miner_Strength#=.1
;			Player_Value_Inertia_Modifier#=1
;			
;			Ship_Value_ScanningStrength = 18000
;			
;			Player_Value_Inertia_Negative# = 0.99
;			Player_Value_Inertia_Positive# = 1.1
;			Player_Value_Inertia_Base# = 3
;			Player_Value_Speed_Maximum=18
;			
;			Ship_Rad_Resistance#=.75
;		Case 3
;			Player_Value_Shield_Maximum=45000
;			Player_Value_Armor_Maximum=16000 
;			Player_Value_Energy_Maximum=2000
;			
;			Ship_Shield_Reload_Tick = 25
;			Ship_Shield_Reload_Amount = 190
;			
;			Player_Value_Miner_Strength#=.1
;			Player_Value_Inertia_Modifier#=1
;			
;			Ship_Value_ScanningStrength = 18000
;			
;			Player_Value_Inertia_Negative# = 0.94
;			Player_Value_Inertia_Positive# = 1.04
;			Player_Value_Inertia_Base# = 3
;			Player_Value_Speed_Maximum=10
;			
;			Ship_Rad_Resistance#=.5
;		Case 4
;			Player_Value_Shield_Maximum=52500
;			Player_Value_Armor_Maximum=25000 
;			Player_Value_Energy_Maximum=2000
;			
;			Ship_Shield_Reload_Tick = 25
;			Ship_Shield_Reload_Amount = 190
;			
;			
;			
;			Player_Value_Miner_Strength#=.1
;			Player_Value_Inertia_Negative# = 0.99
;			Player_Value_Inertia_Positive# = 1.3
;			Player_Value_Inertia_Modifier#=1
;			
;			Ship_Value_ScanningStrength = 18000
;			
;			Player_Value_Inertia_Base# = 2.7
;			Player_Value_Speed_Maximum=8
;			Ship_Rad_Resistance#=.25
;			
;		Default
;			Player_Value_Shield_Maximum=25000: Player_Value_Armor_Maximum=12500 :Player_Value_Energy_Maximum=2500
;			Ship_Shield_Reload_Tick = 25: Ship_Shield_Reload_Amount = 190
;			
;			Player_Value_Miner_Strength#=.1
;			Player_Value_Inertia_Modifier#=1
;			
;			Ship_Value_ScanningStrength = 18000
;			
;			Player_Value_Inertia_Base# = 3.5: Player_Value_Speed_Maximum=15: Ship_Rad_Resistance#=1
;	End Select
;	
;	GetMiningBonusValues(ShipID)
;	
;	Player_Value_Shield_Current=Player_Value_Shield_Maximum 
;	Player_Value_Armor_Current=Player_Value_Armor_Maximum
;	Player_Value_Energy_Current=Player_Value_Energy_Maximum
;	
;	Zoffset=140
;	Yoffset=22
	
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
;		DrawImage3D(GUI_MapIcon[0],MapOriginX, MapOriginZ,False,EntityYaw(pvShip)*-1,2)
	EndIf
	
	For Portal.Stargate = Each Stargate
		Local AIX=EntityX(Portal\Mesh)/MP_SCALE
		Local AIY=EntityY(Portal\Mesh)/MP_SCALE
		Local AIZ=EntityZ(Portal\Mesh)/MP_SCALE
		
		If AIX<CposX+128 And AIX>CposX-128 And AIY<CposY+128 And AIY>CposY-128 And AIZ<CposZ+128 And AIZ>CposZ-128 And HUD=1
			MapYPoint = ( AIY - EntityY(pvShip,True) ) / 500
			
;			DrawImage3D(GUI_Interface[19],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ,0,0,1)
			
			If MapYPoint > -128 And MapYPoint< 128 Then
				If MapYPoint>0 Then
					For MapLine = 1 To MapYPoint
;						DrawImage3D(GUI_Interface[20],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ+mapline,0,0,1)
					Next
				Else
					For MapLine = 1 To -MapYPoint
;						DrawImage3D(GUI_Interface[20],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ-+mapline,0,0,1)
					Next
				EndIf
			EndIf
			
			MapIconPosX=AIX-CposX
			MapIconPosZ=AIZ-CposZ
			
;			DrawImage3D(GUI_MapIcon[7],MapOriginX+MapIconPosX, MapOriginZ+MapIconPosZ + MapYPoint,0,0,1)
			
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

Function Storyline_Update()
End Function

Function GenAudio_Update()
End Function


;~IDEal Editor Parameters:
;~F#5#46#DC#E4#F4#10D#12C#13D#14D#160#216#23C#271#324
;~C#Blitz3D