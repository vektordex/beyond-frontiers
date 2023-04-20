;----------------------------------------------------------------
;! Constants
;----------------------------------------------------------------
Const SETTINGS_FILE_NAME$ = "settings.dat"

Const GRAPHICS_MODE_WINDOWED = 0
Const GRAPHICS_MODE_BORDERLESS = 1
Const GRAPHICS_MODE_FULLSCREEN = 2

;----------------------------------------------------------------
;! Globals
;----------------------------------------------------------------
Global Settings_Graphics_Size%[1]
Global Settings_Graphics_Mode%
Global Settings_Graphics_DeviceName$, Settings_Graphics_DeviceId

;----------------------------------------------------------------
;! Functions
;----------------------------------------------------------------
Function Settings_Default()
	Settings_Graphics_Size[0] = 1024
	Settings_Graphics_Size[1] = 768
	Settings_Graphics_Mode = GRAPHICS_MODE_WINDOWED
	Local DE = BU_DisplayEnumerator_Create()
	BU_DisplayEnumerator_Enumerate(DE)
	
	Local TR.BU_Rectangle = New BU_Rectangle
	BU_DisplayEnumerator_Retrieve(DE, 1, TR)	
	
	Settings_Graphics_Size[0] = TR\X2 - TR\X
	Settings_Graphics_Size[1] = TR\Y2 - TR\Y
	Settings_Graphics_Mode = GRAPHICS_MODE_BORDERLESS
	If Settings_Graphics_Size[0]>1920 And Settings_Graphics_Size[1]>1080 Then 
		Settings_Graphics_Size[0]=1920
		Settings_Graphics_Size[1]=1080
	EndIf
	
	BU_DisplayEnumerator_Destroy(DE)
	Delete TR
End Function

Function Settings_Load(Path$)
	If FileType(Path) <> 1 Then
		Return False
	EndIf
	
	Local Stream = OpenFile(Path)
	If Stream = 0 Then
		Return False
	EndIf
	
	Repeat
		Local Key$ = ReadString(Stream)
		
		Select Key
			Case "Settings_Graphics_Size"
				Settings_Graphics_Size[0] = ReadShort(Stream)
				Settings_Graphics_Size[1] = ReadShort(Stream)
			Case "Settings_Graphics_Mode"
				Settings_Graphics_Mode = ReadByte(Stream)
		End Select
	Until Eof(Stream)
	
	CloseFile Stream
	Return True
End Function

Function Settings_Save(Path$)
	If FileType(Path) = 2 Then
		Return False
	EndIf
	
	Local Stream = WriteFile(Path + ".tmp")
	If Stream = 0 Then
		Return False
	EndIf
	
	; Graphics
	WriteString Stream, "Settings_Graphics_Size"
	WriteShort Stream, Settings_Graphics_Size[0]
	WriteShort Stream, Settings_Graphics_Size[1]
	WriteString Stream, "Settings_Graphics_Mode"
	WriteByte Stream, Settings_Graphics_Mode
	WriteString Stream, "Settings_Graphics_DeviceName"
	WriteString Stream, Settings_Graphics_DeviceName
	WriteByte Stream, Settings_Graphics_DeviceId
	CloseFile Stream
	
	If FileType(Path) = 1 Then
		DeleteFile Path
	EndIf
	CopyFile Path + ".tmp", Path
	DeleteFile Path + ".tmp"
	Return True
End Function
;~IDEal Editor Parameters:
;~F#13#29#43
;~C#Blitz3D