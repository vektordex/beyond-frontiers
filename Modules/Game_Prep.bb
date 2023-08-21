Function Prepare_Graphics()
	If Settings_Graphics_Size[0]>1920 And Settings_Graphics_Size[1]>1080 Then 
		Settings_Graphics_Size[0]=1920
		Settings_Graphics_Size[1]=1080
	EndIf
	
	Settings_Scaling = (Settings_Graphics_Size[1]/(1080/100))/100
	
	Settings_Graphics_Mode=GRAPHICS_MODE_WINDOWED

	; Parse Command Line
	LogMessage(LOG_INFO, "Parsing CommandLine...")
	ParseCmdLine(CommandLine())

	; Settings: Save so crashes don't corrupt it.
	LogMessage(LOG_INFO, "Saving Settings...")
	If Settings_Save(UserData + "\" + SETTINGS_FILE_NAME) = False Then
		LogMessage(LOG_WARNING, "Settings: Failed to save to file.")
	EndIf


	;-borderless -w=1920 -h=1080


	; Graphics: Initialize
	Select Settings_Graphics_Mode
		Case GRAPHICS_MODE_WINDOWED ;[Block] Windowed
			LogMessage(LOG_INFO, "Initializing Graphics at "+Settings_Graphics_Size[0]+"x"+Settings_Graphics_Size[1]+" in Windowed mode...")
			Graphics3D Settings_Graphics_Size[0], Settings_Graphics_Size[1]-20, 32, 2
			;[End Block]
		Case GRAPHICS_MODE_BORDERLESS ;[Block] Borderless
			LogMessage(LOG_INFO, "Initializing Graphics at "+Settings_Graphics_Size[0]+"x"+Settings_Graphics_Size[1]+" in Borderless mode...")
			Graphics3D Settings_Graphics_Size[0], Settings_Graphics_Size[1], 32, 2
		
;			BU_Helper_Window_MakeBorderless()
			BU_Helper_Window_Resize(Settings_Graphics_Size[0], Settings_Graphics_Size[1])
			BU_Helper_Window_Center(1,1)
			;[End Block]
		Case GRAPHICS_MODE_FULLSCREEN ;[Block] FullScreen
			LogMessage(LOG_INFO, "Initializing Graphics at "+Settings_Graphics_Size[0]+"x"+Settings_Graphics_Size[1]+" in Fullscreen mode...")
			Graphics3D Settings_Graphics_Size[0], Settings_Graphics_Size[1], 32, 1
			;[End Block]
	End Select
	SetBuffer BackBuffer()
	AppTitle "Beyond Frontiers | Early Alpha Playtest v"+VERSION_MAJOR+"."+VERSION_MINOR+"."+VERSION_PATCH
	RenderTimer = CreateTimer(60) ; Graphics: Render Timer

	Gw# = GraphicsWidth()
	Gh# = GraphicsHeight()
	GwBy2# = GraphicsWidth() Shr 1
	GhBy2# = GraphicsHeight() Shr 1
	GwBy3# = GraphicsWidth() / 3
	GhBy3# = GraphicsHeight() / 3
	GwBy4# = GraphicsWidth() Shr 2
	GhBy4# = GraphicsHeight() Shr 2
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D