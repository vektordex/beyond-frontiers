;! Bug Fix: Early CTD caused by Blitz3D not being ready at this time.
Delay 100

;----------------------------------------------------------------
;! Includes
;----------------------------------------------------------------
; Libraries
Include "Libraries\BlitzUtility\BlitzUtility.bb"
Include "Libraries\InputEx\InputEx.bb"
Include "Libraries\Draw3D2\Includes\Draw3D2.bb"

; Shared
Include "Shared\Version.bb"

; Engine
Include "Engine\LogHandler.bb"
Include "Engine\AssetManager.bb"
Include "Engine\Settings.bb"

; Game Functions
; >> Basic
Include "Modules\BasicTypes.bb"
Include "Modules\BasicVars.bb"
Include "Modules\BasicConst.bb"

; >> Game Logic
Include "Modules\Controls.bb"
Include "Modules\MainLogic.bb"
Include "Modules\PlayerLogic.bb"
Include "Modules\WeaponLogic.bb"

; >> Graphics
Include "Modules\Game_Prep.bb"
Include "Modules\Asset_Loading.bb"
Include "Modules\Starfield.bb"
Include "Modules\Textures.bb"
Include "Modules\Camera_Prep.bb"

; >> Math
Include "Modules\BasicData.bb"
Include "Modules\BasicMath.bb"
Include "Shared\Math\Delta.bb"

; >> Sound

; >> Utility
Include "Modules\Utilities.bb"

; >> World
Include "Modules\Collisions.bb"
Include "Modules\WorldGenerator.bb"

;----------------------------------------------------------------
;! Initialization
;----------------------------------------------------------------

; Set Windows Timer resolution
Global WinMM_timeBeginPeriod_Resolution = 500
While WinMM_timeBeginPeriod(WinMM_timeBeginPeriod_Resolution) <> 0
	WinMM_timeBeginPeriod_Resolution = WinMM_timeBeginPeriod_Resolution + 100
Wend

; Data: Create proper App Data folders
Global LocalAppData$ = GetEnv("LOCALAPPDATA") + "\BeyondFrontiersGame"
If FileType(LocalAppData) <> 2 Then
	CreateDir(LocalAppData)
EndIf

; Data: User Data Folder (Steam)
Global UserData$ = GetEnv("APPDATA") + "\BeyondFrontiersGame"
If FileType(UserData) <> 2 Then
	CreateDir(UserData)
EndIf

; Log: Initialize Handler
LogHandler_Initialize(LocalAppData + "\Logs")
LogMessage(LOG_INFO, "Initialized logging system.")

; Settings: Set defaults and load.
LogMessage(LOG_INFO, "Loading Settings...")
Settings_Default()
If Settings_Load(UserData + "\" + SETTINGS_FILE_NAME) = False Then
	LogMessage(LOG_WARNING, "Settings: Failed to load from file.")
EndIf

Prepare_Graphics()

; InputEx: Initialize
InputEx_Init()
InputEx_SetResolution(Gw, Gh)

;----------------------------------------------------------------
;! Game - Engine
;----------------------------------------------------------------
; Scene: Camera
;CameraScene = CreateCamera()
;CameraRange CameraScene, CAMERA_NEAR, CAMERA_FAR
;CameraZoom CameraScene, 1.0 / Tan(90 / 2.0)

Global WorldCamera = CreateCamera()

Global pvShip = CreatePivot()
Global pvCamera = CreatePivot(pvShip)
Global pvCameraOrigin = CreatePivot(pvCamera)
MoveEntity pvCameraOrigin, 0, 0, -100
TurnEntity pvCamera, -10, 0, 0

EntityParent WorldCamera, pvCameraOrigin
CameraRange WorldCamera, CAMERA_NEAR, CAMERA_FAR
CameraZoom WorldCamera, 1.0 / Tan(90 / 2.0)
CreateListener(WorldCamera, 0.0025, 8, 20000)

DrawInit3D(WorldCamera)

;----------------------------------------------------------------
;! Game - Loading Screen
;----------------------------------------------------------------
Const LOADING_STATE_LOADING		= 0
Const LOADING_STATE_INITIALIZE	= 1
Const LOADING_STATE_SERVERLIST	= 2

Const LOADING_STATE_SELECT_SERVER = 3
Const LOADING_STATE_CONNECTING	= 4
Const LOADING_STATE_COMPLETE	= -1

; First, load the important stuff.
SeedRnd MilliSecs()
Local Loading_BG = LoadImage("Assets\2D\LoadScreens\Loading_Screen.png")
Util_InitTimer()

;-- Initialize AssetManager
AssetManager_Initialize()

;-- Loading Screen
Local Loading_State = LOADING_STATE_LOADING, Loading_State_Next, Loading_State_JustSwitched = True

Local Ass.Asset

; Retrieve a random Quote for this Loading Screen
Local Loading_Quote$ = "Preloading beyond.frontiers - This may take some time."

Local GFXCards = CountGfxModes3D()
For A = 1 To GFXCards
	LogMessage(LOG_DEBUG, "Graphics Drivers: Resolution support for "+GfxModeWidth(A)+"x"+GfxModeHeight(A))
Next

DrawImage Loading_BG,0,0Flip
Repeat
ShowPointer

;[Block] Logical Update
	; Update State
Select Loading_State
	Case LOADING_STATE_LOADING ;[Block]
		If Loading_State_JustSwitched Then
			AssetManager_Suspend()
			
			;[Block] Meshes and Textures ------------------------------------
				;----------------------------------------------------------------
				;! Game - Meshes
				;----------------------------------------------------------------
			;[End Block]
			
			;[Block] Ships
			Local LoadOrder = OpenFile("Assets\Manifest\LoadShips.lof")
			Repeat
				Local LoadData$ = ReadLine(LoadOrder)
				LoadMeshAsset("Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Mesh.3DS")
				LoadTextureAsset("Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Color.jpg",1+2)
				LoadTextureAsset("Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Glow.jpg",1+2)
			Until Eof(LoadOrder)
			CloseFile LoadOrder
			;[End Block]
			
			;[Block] Stations
			LoadOrder = OpenFile("Assets\Manifest\LoadStations.lof")
			Repeat
				LoadData$ = ReadLine(LoadOrder)
				LoadMeshAsset("Assets\3D\Stations\"+LoadData$+"\"+LoadData$+"_Mesh.3DS")
				LoadTextureAsset("Assets\3D\Stations\"+LoadData$+"\"+LoadData$+"_Color.jpg",1+2)
				LoadTextureAsset("Assets\3D\Stations\"+LoadData$+"\"+LoadData$+"_Glow.jpg",1+2)
			Until Eof(LoadOrder)
			CloseFile LoadOrder
			;[End Block]
			
			;[Block] Environmental Objects
			LoadMeshAsset("Assets\3D\Environment\Hangar_Mesh.3DS")
			LoadTextureAsset("Assets\3D\Environment\Hangar_Color.jpg",1+2)
			LoadTextureAsset("Assets\3D\Environment\Hangar_Glow.jpg",1+2)
			LoadMeshAsset("Assets\3D\Environment\Flare.3DS")
			LoadTextureAsset("Assets\3D\Environment\Flare.png",1+2)
			LoadMeshAsset("Assets\3D\Decor\Capital_Ship.3DS")
			LoadTextureAsset("Assets\3D\Decor\Capital_Ship_Glow.jpg",1+2)
			LoadTextureAsset("Assets\3D\Decor\Capital_Ship_Color.jpg",1+2)
			LoadMeshAsset("Assets\3D\Environment\Dingus_Mesh.3DS")
			LoadTextureAsset("Assets\3D\Environment\Dingus_Color.png",1+2)
			;[End Block]
			
			;[Block] Special Textures
			LoadTextureAsset("Assets\3D\Ships\Rust_Surface.png",1+2)
			;[End Block]
			
			;[Block] Planets
			LoadOrder = OpenFile("Assets\Manifest\LoadPlanets.lof")
			Repeat
				LoadData$ = ReadLine(LoadOrder)
				LoadTextureAsset("Assets\3D\Planets\"+LoadData$+".png",1+2)
			Until Eof(LoadOrder)
			CloseFile LoadOrder
			;[End Block]
			
			;[Block] Gate and Horizon
			LoadMeshAsset("Assets\3D\Gate\Gate_Mesh.3DS")
			LoadTextureAsset("Assets\3D\Gate\Gate_Color.jpg",1+2)
			LoadTextureAsset("Assets\3D\Gate\Gate_Glow.jpg",1+2)
			LoadMeshAsset("Assets\3D\Gate\Gate_Beam.3DS")
			LoadTextureAsset("Assets\3D\Gate\Gate_Beam.png",1+2)
			;[End Block]
			
			;[Block] Asteroids
			For A = 1 To 5
				LoadMeshAsset("Assets\3D\Asteroids\Asteroid"+a+"_Mesh.3DS")
				LoadTextureAsset("Assets\3D\Asteroids\Asteroid"+a+"_Color.jpg")
			Next
			;[End Block]
			
			;[Block] Skybox
			LoadOrder = OpenFile("Assets\Manifest\LoadSkybox.lof")
			Repeat
				LoadData$ = ReadLine(LoadOrder)
				LoadTextureAsset("Assets\3D\SkyBox\"+LoadData$+"BK.png",561)
				LoadTextureAsset("Assets\3D\SkyBox\"+LoadData$+"DN.png",561)
				LoadTextureAsset("Assets\3D\SkyBox\"+LoadData$+"FT.png",561)
				LoadTextureAsset("Assets\3D\SkyBox\"+LoadData$+"LF.png",561)
				LoadTextureAsset("Assets\3D\SkyBox\"+LoadData$+"RT.png",561)
				LoadTextureAsset("Assets\3D\SkyBox\"+LoadData$+"UP.png",561)
			Until Eof(LoadOrder)
			CloseFile LoadOrder
			
			LoadTextureAsset("Assets\3D\Environment\sun.png",1+2)
			LoadTextureAsset("Assets\3D\Environment\Smoke_01.png",1+2)
			LoadTextureAsset("Assets\3D\Environment\Smoke_02.png",1+2)
			LoadTextureAsset("Assets\3D\Environment\Smoke_03.png",1+2)
			LoadTextureAsset("Assets\3D\Environment\Flash.png",1+2)
			;[End Block]
			
			;[Block] Loot
			LoadMeshAsset("Assets\3D\Environment\Container_mesh.3DS")
			LoadTextureAsset("Assets\3D\Environment\Container_Color.png",1+2)
			LoadTextureAsset("Assets\3D\Environment\Container_Glow.png",1+2)
			;[End Block]
			
			;[Block] Interface and Menus ------------------------------------
				;----------------------------------------------------------------
				;! Game - Interface
				;----------------------------------------------------------------
			
			;[Block] Fonts
			LoadOrder = OpenFile("Assets\Manifest\LoadFonts.lof")
			Repeat
				LoadData$ = ReadLine(LoadOrder)
				LoadTextureAsset("Assets\2D\Fonts\"+LoadData$+".png", 2)
				LoadData$ = ReadLine(LoadOrder)
			Until Eof(LoadOrder)
			CloseFile LoadOrder
			;[End Block]
			
			;[Block] Items
			LoadOrder = OpenFile("Assets\Manifest\LoadItems.lof")
			Repeat
				LoadData$ = ReadLine(LoadOrder)
				LoadTextureAsset("Assets\2D\Items\"+LoadData$+".png", 2)
				LoadData$ = ReadLine(LoadOrder)
			Until Eof(LoadOrder)
			CloseFile LoadOrder
			;[End Block]
			
			;[Block] Companies
			For A = 1 To 33
				LoadTextureAsset("Assets\2D\Companies\"+a+".png",2)
			Next
			;[End Block]
			
			;[Block] Standard Windows Font
			LoadFontAsset("Arial", 14)
			LoadFontAsset("Arial", 20)
			LoadFontAsset("Arial", 24)
			;[End Block]
			
			;[Block] Main Menu Graphics
			LoadOrder = OpenFile("Assets\Manifest\LoadMenu.lof")
			Repeat
				LoadData$ = ReadLine(LoadOrder)
				LoadTextureAsset("Assets\2D\Menu\"+LoadData$, 2)
				LoadData$ = ReadLine(LoadOrder)
			Until Eof(LoadOrder)
			CloseFile LoadOrder
			;[End Block]
			
			;[Block] Game GUI
			LoadOrder = OpenFile("Assets\Manifest\LoadUI.lof")
			Repeat
				LoadData$ = ReadLine(LoadOrder)
				LoadTextureAsset("Assets\2D\GUI\"+LoadData$+".png", 2)
				LoadData$ = ReadLine(LoadOrder)
			Until Eof(LoadOrder)
			CloseFile LoadOrder
			;[End Block]
			;[End Block]
			
			;[Block] Sounds -------------------------------------------------
				;----------------------------------------------------------------
				;! Game - Sounds
				;----------------------------------------------------------------
			
			LoadOrder = OpenFile("Assets\Manifest\LoadSounds.lof")
			Repeat
				LoadData$ = ReadLine(LoadOrder)
				LoadSoundAsset("Assets\Sounds\"+LoadData$+".mp3")
			Until Eof(LoadOrder)
			CloseFile LoadOrder
			
			LoadOrder = OpenFile("Assets\Manifest\LoadSound3D.lof")
			Repeat
				LoadData$ = ReadLine(LoadOrder)
				Load3DSoundAsset("Assets\Sounds\"+LoadData$+".mp3")
			Until Eof(LoadOrder)
			CloseFile LoadOrder
			;[End Block]
			
			;[Block] Music --------------------------------------------------
				;----------------------------------------------------------------
				;! Game - Music
				;----------------------------------------------------------------
			
			LoadOrder = OpenFile("Assets\Manifest\LoadMusic.lof")
			Repeat
				LoadData$ = ReadLine(LoadOrder)
				LoadSoundAsset("Assets\Music\"+LoadData$+".mp3")
			Until Eof(LoadOrder)
			CloseFile LoadOrder
			;[End Block]
			
			;[Block] Voices -------------------------------------------------
				;----------------------------------------------------------------
				;! Game - Voices
				;----------------------------------------------------------------
			;[End Block]
			
			;[Block] Other Important Stuff
				;----------------------------------------------------------------
				;! Visual - Important
				;----------------------------------------------------------------
			LoadTextureAsset("Assets\3D\Utilities\texture_fog_basic.tga", 1+2)
			;[End Block]
			
				; Advance to next State
			Loading_State_Next = LOADING_STATE_LOADING
			AssetManager_Resume()
		EndIf
		
		If AssetManager_Suspend(False) Then
			ls_Count_Total = 0
			ls_Count_Loaded = 0
			ls_Count_Loading = 0
			ls_Count_Pending = 0
			ls_Count_Error = 0
			
			For Ass.Asset = Each Asset
				ls_Count_Total = ls_Count_Total + 1
				
				Select Ass\EAssetStatus
					Case EAssetStatus_Unknown
						ls_Count_Pending = ls_Count_Pending + 1
					Case EAssetStatus_Loading
						ls_Count_Loading = ls_Count_Loading + 1
					Case EAssetStatus_Loaded
						ls_Count_Loaded = ls_Count_Loaded + 1
					Case EAssetStatus_Error
						ls_Count_Error = ls_Count_Error + 1
				End Select
			Next
			AssetManager_Resume()
			
			If ls_Count_Total = ls_Count_Loaded Then
				Loading_State_Next = LOADING_STATE_INITIALIZE
				AssetManager_Shutdown() ; Kill the AssetManager, it's not needed anymore.
			EndIf
		EndIf
		;[End Block]
	Case LOADING_STATE_INITIALIZE ;[Block]
			; Assign Loaded resources to Globals.
			
			PreloadAudio()
			PreloadUI()
			Preload3DFont()
			PreloadMesh()
			
			
; Advance to connect.
			;!ToDo: Player Initialization, Ship Meshes etc.
			Loading_State_Next = LOADING_STATE_SERVERLIST
			;[End Block]
		Case LOADING_STATE_SERVERLIST ;[Block]
			Loading_State_Next = LOADING_STATE_COMPLETE
			;[End Block]
		Case LOADING_STATE_CONNECTING ;[Block]
			
			
			
			Loading_State_Next = LOADING_STATE_COMPLETE
			;[End Block]
		Case LOADING_STATE_COMPLETE ;[Block]
			; Any Quick-Initialization happens in here. Should not take more than 500ms.
			; NO LOADING OF FILES! That's what the LOADING_STATE_ENQUEUE and LOADING_STATE_LOADING is for.
			;[End Block]
			
	End Select
	;[End Block]
	
	;[Block] Visual Update
;	AssetManager_Suspend()
	
	Flip
;	AssetManager_Resume()
	;[End Block]
	
	;[Block] State Update
	InputEx_Update()
	
;	Advance State
	If Loading_State <> Loading_State_Next Then
		Loading_State = Loading_State_Next
		Loading_State_JustSwitched = True
	Else
		Loading_State_JustSwitched = False
	EndIf
	
;	Wait
	WaitTimer RenderTimer
	;[End Block]
Until (Loading_State = LOADING_STATE_COMPLETE)
;[End Block]

Environment_Dust_Create()


;----------------------------------------------------------------
;! Old Startup
;----------------------------------------------------------------

; 3D: Camera
Const MODE_CAMERA = 0
Const MODE_SHIP  = 1
Const MODE_DOCKED = 2

Global fShipTargetRotation#[2], fCameraRotation#[2]
Global eMode = MODE_CAMERA, eCameraMode = MODE_CAMERA

; Initialization Code (Old)
 ;!ToDo: AssetManager

;[Block] Old
Global Zoffset, Yoffset
;Global eShip = CreatePivot(pvShip)
Global eShipBody = CreatePivot(pvShip)
Global tShip = CreatePivot(pvShip)
ShowEntity eShipBody
EntityRadius eShipBody, 200

;MoveEntity eShip, 0, 0, 2
MoveEntity eShipBody, 0, 0, 2
EntityType pvShip,1,False
Global mPosition[2], mOrigin[2], mDiff[2]:mOrigin[0] = GraphicsWidth()/2:mOrigin[1] = GraphicsHeight()/2
MoveMouse GraphicsWidth()/2, GraphicsHeight()/2
Const MaxSpeed = 180
Global HOTBAR=1


Global ChatStream 

Global D3DOL=GraphicsWidth()/2*-1
Global D3DOR=GraphicsWidth()/2
Global D3DOU=GraphicsHeight()/2
Global D3DOD=GraphicsHeight()/2*-1
Global D3DCX=0
Global D3DCY=0


;[End Block]

Global CharDataLoaded=0
;! SPOT: SaveGame loading and user data ------------------------------------------------------------------------------------------------------------------------

If FileType(UserData+"\Profiles") <> 2 Then CreateDir(UserData+"\Profiles")
If FileType(UserData+"\Profiles\Player.dat") <> 1 Then WriteFile(UserData+"\Profiles\Player.dat")
If FileType(UserData+"\Profiles\Inventory.dat") <> 1 Then WriteFile(UserData+"\Profiles\Inventory.dat")
If FileType(UserData+"\Profiles\Economy.dat") <> 1 Then WriteFile(UserData+"\Profiles\Economy.dat")
If FileType(UserData+"\Profiles\Universe.dat") <> 1 Then WriteFile(UserData+"\Profiles\Universe.dat")


;!TODO: Read existing savefiles and make them be recognized as "complete" or "incomplete" when failsafing.

Type Character_Information
	Field Name$
	Field Faction
	Field Money
	Field LastSystem, LastX, LastY, LastZ
End Type

Type Character_Inventory
	Field ItemID
	Field Amount
End Type

Type Character_Station
	Field SystemID
	Field StationX, StationY, StationZ
	Field StationType, StationProduct
End Type

;String_SystemDiscovered[LoadID]

Local Timer_Character_Selection = CreateTimer(60)
Global State_Character_Selection = 1
Global State_Menu_Subcontext = 1
Global State_Menu_Subcontext_Settings = 1
Global State_Menu_Subcontext_Character = 1

Global Character_NewName$

Cls



Type Credits
	Field TextLine$, Y
End Type
Local YText = -GraphicsHeight()/2
Local Creditsfile = OpenFile("Assets\Manifest\Credits.dat")
While Not Eof(Creditsfile)
	TLine.Credits = New Credits
	Tline\Textline$ = ReadLine$(Creditsfile)
	TLine\Y = YText
	YText = YText - 30
Wend
CloseFile Creditsfile

;FastTravelSpot = CreateCube()
;ScaleEntity FastTravelSpot,10,10,10
;EntityFX FastTravelSpot,1

.MainMenu
Game_End = 0
;[Block] Character Loading
LoopSound Music_ID[1]
Channel_Music = PlaySound(Music_ID[1])
ChannelVolume Channel_Music,0.0
Music_Volume = 0.0

Repeat
	
	ChannelVolume Channel_Music,Music_Volume#
	If Music_Enabled = 1 Then
		If Music_Volume#<Desired_MVolume# Then Music_Volume# = Music_Volume# + 0.005
		
		If Music_Volume#>Desired_MVolume# Then Music_Volume# = Music_Volume# - 0.005
	EndIf
	If MouseHit(1) = False And MouseHit(2) = False Then FlushMouse()
	
	DrawImage3D	(GUI_Windows[1],Sin(MilliSecs()/10)*.1,Sin(MilliSecs()/10)*0.1,0,0,1)
	
	DrawImage3D	(GUI_Windows[10],D3DOR-64,D3DOU-64)
	
	Text3D(Text_Font[6],D3DOL+10, D3DOD+10, "beyond.frontiers by DUALITYBEYOND Studios, Version 0.1.4")
	
	Util_Timer
	InputEx_Update
	
	
	
	
	tmx = MouseX()-(GraphicsWidth()/2)
	tmy = MouseY()-(GraphicsHeight()/2)
	
	Select State_Menu_Subcontext
		Case 1 ; >> Menu Main Window
			WipeKeyEx()
			
			DrawImage3D	(GUI_Windows[2],0,0)
			
			DrawUI3D("MenuButton1",-376,-250): Text3D (Text_Font[7],-376,-250,"D i s c o r d",1)
			DrawUI3D("MenuButton2",-228,-250): Text3D (Text_Font[7],-228,-250,"C r e d i t s",1)
			DrawUI3D("MenuButton3", -80,-250): Text3D (Text_Font[7], -80,-250,"N e w   G a m e",1)
			DrawUI3D("MenuButton4",  80,-250): Text3D (Text_Font[7],  80,-250,"C o n t i n u e",1)
			DrawUI3D("MenuButton5", 228,-250): Text3D (Text_Font[7], 228,-250,"O p t i o n s",1);DrawImage3D	(GUI_Windows[7],228,-250)
			DrawUI3D("MenuButton6", 376,-250): Text3D (Text_Font[7], 376,-250,"E x i t",1)	;DrawImage3D	(GUI_Windows[8],376,-250)
			
			;[Block] Discord
			If MouseX()>(GraphicsWidth()/2-440) And MouseX()<(GraphicsWidth()/2-305) And MouseY()>(GraphicsHeight()/2)+230 And MouseY()<(GraphicsHeight()/2)+270  ;Discord Button
				DrawUI3D("MenuButton1A",-376,-250)
				If MouseHit(1) Then ExecFile("https://discord.gg/D5e5qRtQkb"): PlaySound(Sound_ID[1])
			EndIf
			;[End Block]
			
			;[Block] Credits
			If MouseX()>(GraphicsWidth()/2-290) And MouseX()<(GraphicsWidth()/2-155) And MouseY()>(GraphicsHeight()/2)+230 And MouseY()<(GraphicsHeight()/2)+270  ;Credits Button
				DrawUI3D("MenuButton2A",-228,-250)
				If MouseHit(1) Then State_Menu_Subcontext = 5: PlaySound(Sound_ID[1])
			EndIf
			;[End Block]
			
			;[Block] Create New Game / Character
			If MouseX()>(GraphicsWidth()/2-150) And MouseX()<(GraphicsWidth()/2-5) And MouseY()>(GraphicsHeight()/2)+230 And MouseY()<(GraphicsHeight()/2)+270  ;New Game Button
				DrawUI3D("MenuButton3A", -80,-250)
				If MouseHit(1) Then State_Menu_Subcontext_Character=1: State_Menu_Subcontext = 3: PlaySound(Sound_ID[1])
			EndIf
			;[End Block]
			
			;[Block] Go to Character Overview
			If MouseX()>(GraphicsWidth()/2+5) And MouseX()<(GraphicsWidth()/2+150) And MouseY()>(GraphicsHeight()/2)+230 And MouseY()<(GraphicsHeight()/2)+270  ;Continue Button
				DrawUI3D("MenuButton4A",  80,-250);
			EndIf
			;[End Block]
			
			;[Block] Options Window
			If MouseX()>(GraphicsWidth()/2+155) And MouseX()<(GraphicsWidth()/2+290) And MouseY()>(GraphicsHeight()/2)+230 And MouseY()<(GraphicsHeight()/2)+270  ;Options Button
				DrawUI3D("MenuButton5A", 228,-250)
				If MouseHit(1) Then State_Menu_Subcontext = 2: PlaySound(Sound_ID[1])	
			EndIf
			;[End Block]
			
			;[Block] Exit Button
			If MouseX()>(GraphicsWidth()/2+305) And MouseX()<(GraphicsWidth()/2+440) And MouseY()>(GraphicsHeight()/2)+230 And MouseY()<(GraphicsHeight()/2)+270 ;Exit Button
				DrawUI3D("MenuButton6A", 376,-250)
				If MouseHit(1) Then
					ClearWorld
					End
				EndIf
			EndIf
			;[End Block]
			
		Case 2 ; >> Options
			;[Block] Options
			DrawUI3D("LongBarDark",0,GraphicsHeight()/2-16,2,-90)
			Text3D(Text_Font[7],0,GraphicsHeight()/2-16,"S e t t i n g s",1)
			
			DrawUI3D("WidePanelTitle",90,0,1.5)
			DrawUI3D("SelectionDivider",-370,0,1.5)
			Select State_Menu_Subcontext_Settings
				Case 1 ; >> Graphics
					
					
					WipeKeyEx()
					
					DrawUI3D("BaseButtonGreen",-370,210,1.8)
					DrawUI3D("BaseButtonGrey",-370,170,1.8)
					DrawUI3D("BaseButtonGrey",-370,130,1.8)
					DrawUI3D("BaseButtonGrey",-370, 90,1.8)
					Text3D(Text_Font[6],-180,235,"G r a p h i c s",1)
					
;					Text X -250, Y - 230
					
					Text3D(Text_Font[7],-230,200,"Shader Passes")
					
					DrawUI3D("SliderArrow", 60,200,1, 90)
					DrawUI3D("SliderArrow", 95,200,1,-90)
					
					DrawUI3D("Slider",+230,200,1.5)
					
					Local AmbientLevel = 10 + (11 - Settings_GFX_Ambience)*-1
					Text3D(Text_Font[7],+400,200,AmbientLevel+"x",2)
					
					If Settings_GFX_Ambience > 11 Then Settings_GFX_Ambience = 11
					If Settings_GFX_Ambience < 1 Then Settings_GFX_Ambience = 1
					
					If MouseY()>GhBy2-214 And MouseY()<GhBy2-186
						If MouseX()>GwBy2+39 And MouseX()<GwBy2+81
							DrawUI3D("SliderArrowA", 60,200,1, 90)
							If MouseHit(1) Then Settings_GFX_Ambience = Settings_GFX_Ambience - 1: PlaySound(Sound_ID[1])
						EndIf
						If MouseX()>GwBy2+74 And MouseX()<GwBy2+126
							DrawUI3D("SliderArrowA", 95,200,1,-90)
							If MouseHit(1) Then Settings_GFX_Ambience = Settings_GFX_Ambience + 1: PlaySound(Sound_ID[1])
						EndIf
					EndIf
					
					Text3D(Text_Font[7],-230,170,"Object Amount")
					
					DrawUI3D("SliderArrow", 60,170,1, 90)
					DrawUI3D("SliderArrow", 95,170,1,-90)
					
					DrawUI3D("Slider",+230,170,1.5)
					
					Local ObjectLevel = (Settings_GFX_Objects# * 100)
					Text3D(Text_Font[7],+400,170,ObjectLevel+" %",2)
					
					If Settings_GFX_Objects# > 2 Then Settings_GFX_Objects# = 2
					If Settings_GFX_Objects# < .2 Then Settings_GFX_Objects# = .2
					
					If MouseY()>GhBy2-186 And MouseY()<GhBy2-154
						If MouseX()>GwBy2+39 And MouseX()<GwBy2+81
							DrawUI3D("SliderArrowA", 60,170,1, 90)
							If MouseHit(1) Then Settings_GFX_Objects# = Settings_GFX_Objects# - .1: PlaySound(Sound_ID[1])
						EndIf
						If MouseX()>GwBy2+74 And MouseX()<GwBy2+126
							DrawUI3D("SliderArrowA", 95,170,1,-90)
							If MouseHit(1) Then Settings_GFX_Objects# = Settings_GFX_Objects# + .1: PlaySound(Sound_ID[1])
						EndIf
					EndIf
					
					
					Text3D(Text_Font[7],-230,140,"Viewing Range")
					
					DrawUI3D("SliderArrow", 60,140,1, 90)
					DrawUI3D("SliderArrow", 95,140,1,-90)
					
					DrawUI3D("Slider",+230,140,1.5)
					
					Local ViewLevel = (Settings_GFX_Range# * 100)
					Text3D(Text_Font[7],+400,140,ViewLevel+" %",2)
					
					If Settings_GFX_Range# > 2 Then Settings_GFX_Range# = 2
					If Settings_GFX_Range# < .2 Then Settings_GFX_Range# = .2
					
					If MouseY()>GhBy2-156 And MouseY()<GhBy2-124
						If MouseX()>GwBy2+39 And MouseX()<GwBy2+81
							DrawUI3D("SliderArrowA", 60,140,1, 90)
							If MouseHit(1) Then Settings_GFX_Range# = Settings_GFX_Range# - .1: PlaySound(Sound_ID[1])
						EndIf
						If MouseX()>GwBy2+74 And MouseX()<GwBy2+126
							DrawUI3D("SliderArrowA", 95,140,1,-90)
							If MouseHit(1) Then Settings_GFX_Range# = Settings_GFX_Range# + .1: PlaySound(Sound_ID[1])
						EndIf
					EndIf
					
					Text3D(Text_Font[7],-230,110,"WBuffer")
					
					If WBuffer_Enabled = 1 Then
						DrawUI3D("SwitchGreen",+77,110)
						Text3D(Text_Font[7],+77,110,"On",1)
					Else
						DrawUI3D("SwitchRed",+77,110,1,180)
						Text3D(Text_Font[7],+77,110,"Off",1)
					EndIf
					If MouseY()>GhBy2-126 And MouseY()<GhBy2-94
						If MouseX()>GwBy2+38 And MouseX()<GwBy2+126
							If MouseHit(1)
								WBuffer_Enabled = 1-WBuffer_Enabled: PlaySound(Sound_ID[4])
							EndIf
						EndIf
					EndIf
					
				Case 2 ; >> Audio
					
					WipeKeyEx()
					
					DrawUI3D("BaseButtonGrey",-370,210,1.8)
					DrawUI3D("BaseButtonGreen",-370,170,1.8)
					DrawUI3D("BaseButtonGrey",-370,130,1.8)
					DrawUI3D("BaseButtonGrey",-370, 90,1.8)
					Text3D(Text_Font[6],-180,235,"S o u n d",1)
					
				Case 3 ; >> Music
					
					WipeKeyEx()
					
					DrawUI3D("BaseButtonGrey",-370,210,1.8)
					DrawUI3D("BaseButtonGrey",-370,170,1.8)
					DrawUI3D("BaseButtonGreen",-370,130,1.8)
					DrawUI3D("BaseButtonGrey",-370, 90,1.8)
					Text3D(Text_Font[6],-180,235,"M u s i c",1)
					
					Text3D(Text_Font[7],-230,200,"Music Volume")
					
					DrawUI3D("SliderArrow", 60,200,1, 90)
					DrawUI3D("SliderArrow", 95,200,1,-90)
					
					DrawUI3D("Slider",+230,200,1.5)
					
					Text3D(Text_Font[7],+400,200,(Desired_MVolume*100)+"%",2)
					
					If Settings_GFX_Ambience > 11 Then Settings_GFX_Ambience = 11
					If Settings_GFX_Ambience < 1 Then Settings_GFX_Ambience = 1
					
					If MouseY()>GhBy2-214 And MouseY()<GhBy2-186
						If MouseX()>GwBy2+39 And MouseX()<GwBy2+81
							DrawUI3D("SliderArrowA", 60,200,1, 90)
							If MouseHit(1) Then Desired_MVolume = Desired_MVolume - .05: PlaySound(Sound_ID[1])
						EndIf
						If MouseX()>GwBy2+74 And MouseX()<GwBy2+126
							DrawUI3D("SliderArrowA", 95,200,1,-90)
							If MouseHit(1) Then Desired_MVolume = Desired_MVolume + .05: PlaySound(Sound_ID[1])
						EndIf
					EndIf
					
					If Desired_MVolume > 1 Then Desired_MVolume = 1
					If Desired_MVolume < 0 Then Desired_MVolume = 0
						
				Case 4 ; >> Other
					DrawUI3D("BaseButtonGrey",-370,210,1.8)
					DrawUI3D("BaseButtonGrey",-370,170,1.8)
					DrawUI3D("BaseButtonGrey",-370,130,1.8)
					DrawUI3D("BaseButtonGreen",-370, 90,1.8)
					Text3D(Text_Font[6],-180,235,"O t h e r",1)
					
			End Select
			
			DrawUI3D("BaseButtonGrey",-370,-220,1.8)
			
			If MouseX()>GraphicsWidth()/2-465 And MouseX()<GraphicsWidth()/2-280
				If MouseY()>(GraphicsHeight()/2)-228 And MouseY()<(GraphicsHeight()/2)-192
					DrawUI3D("BaseButtonBlue",-370,210,1.8)
					If MouseHit(1) Then State_Menu_Subcontext_Settings = 1: PlaySound(Sound_ID[1])
				ElseIf  MouseY()>(GraphicsHeight()/2)-190 And MouseY()<(GraphicsHeight()/2)-154
					DrawUI3D("BaseButtonBlue",-370,170,1.8)
					If MouseHit(1) Then State_Menu_Subcontext_Settings = 2: PlaySound(Sound_ID[1])
				ElseIf  MouseY()>(GraphicsHeight()/2)-152 And MouseY()<(GraphicsHeight()/2)-116
					DrawUI3D("BaseButtonBlue",-370,130,1.8)
					If MouseHit(1) Then State_Menu_Subcontext_Settings = 3: PlaySound(Sound_ID[1])
				ElseIf  MouseY()>(GraphicsHeight()/2)-114 And MouseY()<(GraphicsHeight()/2)-78
					DrawUI3D("BaseButtonBlue",-370, 90,1.8)
					If MouseHit(1) Then State_Menu_Subcontext_Settings = 4: PlaySound(Sound_ID[1])
				ElseIf  MouseY()>(GraphicsHeight()/2)+202 And MouseY()<(GraphicsHeight()/2)+238
					DrawUI3D("BaseButtonRed",-370,-220,1.8)
					If MouseHit(1) Then State_Menu_Subcontext = 1: PlaySound(Sound_ID[3])
				EndIf
			EndIf
			
			
			
			Text3D(Text_Font[7],-370,210,"G r a p h i c s",1)
			Text3D(Text_Font[7],-370,170,"S o u n d s",1)
			Text3D(Text_Font[7],-370,130,"M u s i c",1)
			Text3D(Text_Font[7],-370, 90,"O t h e r",1)
			
			Text3D(Text_Font[7],-370,-220,"C o n f i r m",1)
			
			
			;[End Block]
			
		Case 3 ; >> New Game
			If MouseX()>GraphicsWidth()-192 And MouseX()<GraphicsWidth()-64 And MouseY()>(GraphicsHeight()/2)+455 And MouseY()<(GraphicsHeight()/2)+480 Then
				Text3D(Text_Font[8],GraphicsWidth()/2-128,-GraphicsHeight()/2+65,"B a c k",1)
				If MouseHit(1) Then State_Menu_Subcontext = 1: PlaySound(Sound_ID[1])
			Else
				Text3D(Text_Font[7],GraphicsWidth()/2-128,-GraphicsHeight()/2+65,"B a c k",1)
			EndIf
			
			Select State_Menu_Subcontext_Character
				Case 1
					;[Block] Faction Selection
					Local BaseX = -432
					WipeKeyEx()
					
					DrawUI3D("SelectionDivider",BaseX,-50,2)
					Text3D(Text_Font[7],BaseX,400,"T e r r a n   C o n g l o m e r a t e",1)
					Text3D(Text_Font[10],BaseX,370,"Money is Power",1)
					
					WordWrap3D(-432,260,46,16,1,6,"A capitalist society composed of and ruled by various corporations, with a strong focus on  financial and military power. The Conglomerate seeks to expand its influence throughout the galaxy both through monetary and military     conquest. ")
					
					Text3D(Text_Font[6],BaseX,140,"Starting System:",1)
					Text3D(Text_Font[2],BaseX,120,"Luna",1)
;					Text3D(Text_Font[1],BaseX,120,"3",1)
					Text3D(Text_Font[6],BaseX, 80,"Starting Money:",1)
					Text3D(Text_Font[3],BaseX, 60,"25.000 Cr.",1)
;					Text3D(Text_Font[1],BaseX, 60,"6",1)
;					Text3D(Text_Font[1],BaseX, 40,"7",1)
					Text3D(Text_Font[6],BaseX, 20,"Terrans have the biggest Empire, but are only connected to the rest",1)
					Text3D(Text_Font[6],BaseX,  0,"of the Network on two chokepoint systems. An abundance of stations",1)
					Text3D(Text_Font[6],BaseX,-20,"accelerates every Traders aspirations",1)
					
					DrawUI3D("BaseButtonGrey", BaseX,-350,2)
					
					BaseX = 0
					
					DrawUI3D("SelectionDivider",BaseX,-50,2)
					Text3D(Text_Font[7],BaseX,400,"S i r i u s   D o m i n i o n",1)
					Text3D(Text_Font[10],BaseX,370,"Freedom and Liberty",1)
					
					WordWrap3D(0,260,46,16,1,6,"A liberal democracy with a strong emphasis on personal freedom and exploration, governed by a president, chancellor, and seven chambers of advisors. The Dominion has a mixed economy, with both private enterprise and government control, and prioritizes the well-being of its citizens.")
					
					Text3D(Text_Font[6],BaseX,140,"Starting System:",1)
					Text3D(Text_Font[2],BaseX,120,"Kurai",1)
;					Text3D(Text_Font[1],BaseX,120,"3",1)
					Text3D(Text_Font[6],BaseX, 80,"Starting Money:",1)
					Text3D(Text_Font[3],BaseX, 60,"9.000 Cr.",1)
;					Text3D(Text_Font[1],BaseX, 60,"6",1)
;					Text3D(Text_Font[1],BaseX, 40,"7",1)
					Text3D(Text_Font[6],BaseX, 20,"The Dominion is the first independent Empire and has the most open",1)
					Text3D(Text_Font[6],BaseX,  0,"battlefield sectors in the Network, a good chance for budding",1)
					Text3D(Text_Font[6],BaseX,-20,"privateers.",1)
					
					DrawUI3D("BaseButtonGrey", BaseX,-350,2)
					
					BaseX = 428
					
					DrawUI3D("SelectionDivider",BaseX,-50,2)
					Text3D(Text_Font[7],BaseX,400,"O r i o n   C o u n c i l",1)
					Text3D(Text_Font[10],BaseX,370,"Better Oneself",1)
					
					WordWrap3D(428,260,46,16,1,6,"A meritocratic society of highly enducated people, with each member of the Council representing a different region of the Orionic Empire. The Council is responsible for making major decisions regarding the empire, with the goal of maintaining a strong and stable society based on tradition and order.")
					Text3D(Text_Font[6],BaseX,140,"Starting System:",1)
					Text3D(Text_Font[2],BaseX,120,"Maia",1)
;					Text3D(Text_Font[1],BaseX,120,"3",1)
					Text3D(Text_Font[6],BaseX, 80,"Starting Money:",1)
					Text3D(Text_Font[3],BaseX, 60,"10.000 Cr.",1)
;					Text3D(Text_Font[1],BaseX, 60,"6",1)
;					Text3D(Text_Font[1],BaseX, 40,"7",1)
					Text3D(Text_Font[6],BaseX, 20,"As the smallest and newest Empire, the council has many bordering",1)
					Text3D(Text_Font[6],BaseX,  0,"unexplored sectors and can provide many opportunities for",1)
					Text3D(Text_Font[6],BaseX,-20,"adventurers.",1)
					
					DrawUI3D("BaseButtonGrey", BaseX,-350,2)
					
					
					
					DrawImage3D(GUI_Windows[6],0,0,0,0,1.25)
					If MouseX()>GwBy2-638 And MouseX()<GwBy2-215 Then
						DrawRect3D(GUI_Windows[7],-426,Sin(MilliSecs()/50)*15,6,0,333,1024,0,0,1.25)
						
						If MouseY()>GhBy2+330 And MouseY()<GhBy2+370 Then
							DrawUI3D("BaseButtonGreen",-428,-350,2)
							If MouseHit(1) Then PlaySound(Sound_ID[1]):NewChar_Faction$="Terran Conglomerate": NewChar_Money=12000:NewChar_System$="Luna":State_Menu_Subcontext_Character=2:Player_GlobalX=14 :Player_GlobalY=6
						EndIf
						
					ElseIf MouseX()>GwBy2-215 And MouseX()<GwBy2+205 Then
						DrawRect3D(GUI_Windows[7],0,Sin(MilliSecs()/50)*15,345,0,333,1024,0,0,1.25)
						
						If MouseY()>GhBy2+330 And MouseY()<GhBy2+370 Then
							DrawUI3D("BaseButtonGreen",0,-350,2)
							If MouseHit(1) Then PlaySound(Sound_ID[1]):NewChar_Faction$="Sirius Dominion": NewChar_Money=8000:NewChar_System$="Kurai":State_Menu_Subcontext_Character=2:Player_GlobalX=5 :Player_GlobalY=16
						EndIf
						
						
					ElseIf MouseX()>GwBy2+205 And MouseX()<GwBy2+605 Then
						DrawRect3D(GUI_Windows[7],426,Sin(MilliSecs()/50)*15,685,0,333,1024,0,0,1.25)
;						
						If MouseY()>GhBy2+330 And MouseY()<GhBy2+370 Then
							DrawUI3D("BaseButtonGreen",428,-350,2)
							If MouseHit(1) Then PlaySound(Sound_ID[1]):NewChar_Faction$="Orionic Council Republic": NewChar_Money=10000:NewChar_System$="Maia":State_Menu_Subcontext_Character=2:Player_GlobalX=5 :Player_GlobalY=7
						EndIf
						
					EndIf
					
					Text3D(Text_Font[7],-432,-350,"S e l e c t",1)
					Text3D(Text_Font[7],   0,-350,"S e l e c t",1)
					Text3D(Text_Font[7], 428,-350,"S e l e c t",1)
					;[End Block]
					
				Case 2
					
					DrawImage3D(GUI_Windows[31],0,-50,0,0,3)
					Text3D(Text_Font[7],0,150,"E n t e r   y o u r   n a m e",1)
					
					Text3D(Text_Font[1],0,100,"With this name, you will be known to friends and foes in the gate network alike. Please be aware that this name represents you and will be seen by other players should you choose to enable networking.",1)
					Text3D(Text_Font[1],0, 80,"To enable Twitch-Assisted Gameplay, please select so in the settings window.",1)
					Text3D(Text_Font[3],-200, 20,"You start in",1)
					Text3D(Text_Font[4],-200,  0,NewChar_System$,1)
					
					Text3D(Text_Font[3],   0, 40,"You selected",1)
					Text3D(Text_Font[4],   0, 20,NewChar_Faction$,1)
					
					Text3D(Text_Font[3], 200, 20,"Your Starting Money",1)
					Text3D(Text_Font[4], 200, 0,Newchar_Money+" Cr.",1)
					
					Text3D(Text_Font[1],0, -110,"I hereby pledge my loyalty to the "+NewChar_Faction$+" and will do my best to adhere to their values,",1)
					Text3D(Text_Font[1],0, -170,"(supported letters and characters are a-z, A-Z, 0-9 and !-_.:)",1)
					
					DrawImage3D(GUI_Windows[23],0,-140,0,0,2.2)
					Text3D(Text_Font[7],-150, -140,Character_NewName$)
					Text3D(Text_Font[7],-240, -140,"Cmdr.",1)
					
					Text3D(Text_Font[7],0,-300,"Confirm and Play!",1)
					
					If MouseX()>GwBy2-100 And MouseX()<GwBy2+100 And MouseY()>GhBy2+250 And MouseY()<GhBy2+350 Then
						DrawUI3D("BaseButtonGreen", 0,-300,2)	
						If MouseHit(1) Then Goto Spacegamestart: PlaySound(Sound_ID[1])
					Else
						DrawUI3D("BaseButtonGrey", 0,-300,2)
					EndIf
					
					
					;[Block] Key Polling
					
					If InputEx_KeyDown(KEY_SHIFT_LEFT) = True
						If InputEx_KeyHit(KEY_A) Then Character_NewName$=Character_NewName$+"A": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_B) Then Character_NewName$=Character_NewName$+"B": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_C) Then Character_NewName$=Character_NewName$+"C": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_D) Then Character_NewName$=Character_NewName$+"D": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_E) Then Character_NewName$=Character_NewName$+"E": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_F) Then Character_NewName$=Character_NewName$+"F": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_G) Then Character_NewName$=Character_NewName$+"G": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_H) Then Character_NewName$=Character_NewName$+"H": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_I) Then Character_NewName$=Character_NewName$+"I": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_J) Then Character_NewName$=Character_NewName$+"J": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_K) Then Character_NewName$=Character_NewName$+"K": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_L) Then Character_NewName$=Character_NewName$+"L": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_M) Then Character_NewName$=Character_NewName$+"M": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_N) Then Character_NewName$=Character_NewName$+"N": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_O) Then Character_NewName$=Character_NewName$+"O": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_P) Then Character_NewName$=Character_NewName$+"P": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_Q) Then Character_NewName$=Character_NewName$+"Q": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_R) Then Character_NewName$=Character_NewName$+"R": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_S) Then Character_NewName$=Character_NewName$+"S": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_T) Then Character_NewName$=Character_NewName$+"T": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_U) Then Character_NewName$=Character_NewName$+"U": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_V) Then Character_NewName$=Character_NewName$+"V": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_W) Then Character_NewName$=Character_NewName$+"W": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_X) Then Character_NewName$=Character_NewName$+"Y": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_Y) Then Character_NewName$=Character_NewName$+"X": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_Z) Then Character_NewName$=Character_NewName$+"Z": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_2) Then Character_NewName$=Character_NewName$+"2": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_3) Then Character_NewName$=Character_NewName$+"3": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_4) Then Character_NewName$=Character_NewName$+"4": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_5) Then Character_NewName$=Character_NewName$+"5": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_6) Then Character_NewName$=Character_NewName$+"6": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_7) Then Character_NewName$=Character_NewName$+"7": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_8) Then Character_NewName$=Character_NewName$+"8": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_9) Then Character_NewName$=Character_NewName$+"9": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_0) Then Character_NewName$=Character_NewName$+"0": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_DASH) Then Character_NewName$=Character_NewName$+"_": PlaySound(Sound_ID[1])
					Else
						If InputEx_KeyHit(KEY_A) Then Character_NewName$=Character_NewName$+"a": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_B) Then Character_NewName$=Character_NewName$+"b": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_C) Then Character_NewName$=Character_NewName$+"c": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_D) Then Character_NewName$=Character_NewName$+"d": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_E) Then Character_NewName$=Character_NewName$+"e": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_F) Then Character_NewName$=Character_NewName$+"f": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_G) Then Character_NewName$=Character_NewName$+"g": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_H) Then Character_NewName$=Character_NewName$+"h": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_I) Then Character_NewName$=Character_NewName$+"i": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_J) Then Character_NewName$=Character_NewName$+"j": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_K) Then Character_NewName$=Character_NewName$+"k": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_L) Then Character_NewName$=Character_NewName$+"l": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_M) Then Character_NewName$=Character_NewName$+"m": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_N) Then Character_NewName$=Character_NewName$+"n": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_O) Then Character_NewName$=Character_NewName$+"o": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_P) Then Character_NewName$=Character_NewName$+"p": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_Q) Then Character_NewName$=Character_NewName$+"q": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_R) Then Character_NewName$=Character_NewName$+"r": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_S) Then Character_NewName$=Character_NewName$+"s": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_T) Then Character_NewName$=Character_NewName$+"t": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_U) Then Character_NewName$=Character_NewName$+"u": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_V) Then Character_NewName$=Character_NewName$+"v": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_W) Then Character_NewName$=Character_NewName$+"w": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_X) Then Character_NewName$=Character_NewName$+"x": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_Y) Then Character_NewName$=Character_NewName$+"y": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_Z) Then Character_NewName$=Character_NewName$+"z": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_1) Then Character_NewName$=Character_NewName$+"1": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_2) Then Character_NewName$=Character_NewName$+"2": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_3) Then Character_NewName$=Character_NewName$+"3": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_4) Then Character_NewName$=Character_NewName$+"4": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_5) Then Character_NewName$=Character_NewName$+"5": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_6) Then Character_NewName$=Character_NewName$+"6": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_7) Then Character_NewName$=Character_NewName$+"7": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_8) Then Character_NewName$=Character_NewName$+"8": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_9) Then Character_NewName$=Character_NewName$+"9": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_0) Then Character_NewName$=Character_NewName$+"0": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_DASH) Then Character_NewName$=Character_NewName$+"-": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_PERIOD) Then Character_NewName$=Character_NewName$+".": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_PLUS) Then Character_NewName$=Character_NewName$+"+": PlaySound(Sound_ID[1])
					EndIf
					
					If InputEx_KeyDown(KEY_BACKSPACE) And Character_NewName$>"" Then 
						Chat_Usage_Counter=Chat_Usage_Counter+1
						If Chat_Usage_Counter>7 Then 
							Character_NewName$=Mid(Character_NewName$,1,Len(Character_NewName$)-1) 
							Chat_Usage_Counter=0
							PlaySound(Sound_ID[1])
						EndIf
					Else 
						Chat_Usage_Counter=4
					EndIf
										;[End Block]
					
			End Select
			
			DrawUI3D("LongBarDark",0,GraphicsHeight()/2-16,2,-90)
			DrawUI3D("BaseButtonGreen", GraphicsWidth()/2-128,-GraphicsHeight()/2+64)
			
			Text3D(Text_Font[7],0,GraphicsHeight()/2-24,"N e w   G a m e",1)
		Case 5 ; >> Credits
			;[Block]
			
			WipeKeyEx()
			
			DrawImage3D	(GUI_Windows[2],0,GraphicsHeight()/3)
			DrawUI3D("BaseButtonGreen", GraphicsWidth()/2-128,-GraphicsHeight()/2+64)
			DrawImage3D(GUI_Windows[37], D3DOL+256,D3DOD+64,0,0,0.5)
			If MouseX()>GraphicsWidth()-192 And MouseX()<GraphicsWidth()-64 And MouseY()>(GraphicsHeight()/2)+455 And MouseY()<(GraphicsHeight()/2)+480 Then
				Text3D(Text_Font[8],GraphicsWidth()/2-128,-GraphicsHeight()/2+65,"B a c k",1)
				If MouseHit(1) Then State_Menu_Subcontext = 1: PlaySound(Sound_ID[1])
			Else
				Text3D(Text_Font[7],GraphicsWidth()/2-128,-GraphicsHeight()/2+65,"B a c k",1)
			EndIf
			
			For TLine.Credits = Each Credits
				Text3D(Text_Font[9],0,Tline\Y,Tline\Textline$,1)
				TLine\Y = Tline\Y + 1
				
				If TLine\Y > (GraphicsHeight()/2+25) Then
					TLine\Y = YText
					YText=YText - 20
				EndIf
			Next
			;[End Block]
			
	End Select
	
	InputEx_Update
	
	UpdateWorld	
	
	RenderWorld
	
	WaitTimer(Timer_Character_Selection)
	Flip 0: 
	Clear3D()
	
Until CharDataLoaded=1
;[End Block]

;!--------------------------------------------------------------------------------------------------------------------------------------------------------------
.Spacegamestart

StopChannel Channel_Music
LogMessage(LOG_DEBUG,"Game Prep: Stopped Menu Music")

ChatNickName$ = "bfgame_"+Character_NewName$
LogMessage(LOG_DEBUG,"Game Prep: Set Chat Nickname")

If Chat_Enabled = 1 Then
	Chat_Connect()
	LogMessage(LOG_DEBUG,"Game Prep: Connecting to Chat Server")
EndIf

Music_Volume#=.2
LogMessage(LOG_DEBUG,"Game Prep: Lowered Music volume")



CameraZoom WorldCamera,1
LogMessage(LOG_DEBUG,"Game Prep: Set FOV")

Global WorldCameraPivot = CreatePivot(pvCamera)
LogMessage(LOG_DEBUG,"Game Prep: Creating Camera Pivoting")

PositionEntity WorldCamera, EntityX(pvCamera), EntityY(pvCamera)+Yoffset, EntityZ(pvCamera)-Zoffset, False
PositionEntity WorldCameraPivot, EntityX(pvCamera), EntityY(pvCamera)+Yoffset, EntityZ(pvCamera), False
LogMessage(LOG_DEBUG,"Game Prep: Positioning Pivot")

Global PlayerChannel = EmitSound(Sound3D_ID[1],pvShip)
LogMessage(LOG_DEBUG,"Game Prep: Start Engine Sound")


Global OldMS, NewMS, TDFMS, MaxMS, MinMS=10000
Global DebugInfo_Enabled

PlayerSwitchShip(1)
LogMessage(LOG_DEBUG,"Game Prep: Switching Ship")

World_Generate(Player_GlobalX,Player_GlobalY,0,0,0)
LogMessage(LOG_DEBUG,"Game Prep: Generated World")

HidePointer
LogMessage(LOG_DEBUG,"Game Prep: Hid Mouse")


;set camera:
PositionEntity WorldCamera, EntityX(pvCamera), EntityY(pvCamera)+Yoffset, EntityZ(WorldCamera), False
LogMessage(LOG_DEBUG,"Game Prep: Positioned Camera")


UpdateMapScale(4)
LogMessage(LOG_DEBUG,"Game Prep: Updated Map Scale")


HUD = 1
LogMessage(LOG_DEBUG,"Game Prep: Entering Game")

Repeat
	
	Local ms_Performance = MilliSecs()
	
	;----------------------------------------------------------------
	;[Block] Update
	;----------------------------------------------------------------
	Local ms_Performance_Update = MilliSecs()
	
	;[Block] Networking
	If Chat_Enabled = 1 Then Chat_GetData()
	
	BU_Helper_Window_LockPointerAuto(0)
	
	If KeyHit(1) Then 
		Game_Menu_Open=1-Game_Menu_Open
	EndIf
		
	If Game_Menu_Open = 1 Then FlushMouse(): eCameraMode = MODE_DOCKED
	;[End Block]
	
	;[Block] Players
	; Movement: Strafing
	MoveEntity pvShip,ShipStrafeX#,ShipStrafeY#,Player_Value_Speed_Current#
	TurnEntity pvShip,0,0,ShipRollZ#
	
	;[End Block]
	
	;[Block] Input
	Local ms_Performance_Update_Input = MilliSecs()
	
	InputEx_Update()
	
	
	
	Local mSpeedX#, mSpeedY#
	mSpeedX = MouseXSpeed()
	mSpeedY = MouseYSpeed()
	If Force_UI_Mode = 1 Then
		eCameraMode = MODE_CAMERA
		Player_Value_Speed_Target = 0:  Player_Value_Speed_Current = 0
		TurnEntity pvCamera,0,.05,0
		MoveEntity WorldCamera,0,0,Camera_Zoom_Speed#
		Camera_Zoom_Speed#=Camera_Zoom_Speed#*0.9
		ShipStrafeX = 0
		ShipStrafeY = 0
		ShipRollZ =0
	EndIf
	
	Select eCameraMode
		Case MODE_SHIP 
			;[Block] Control the Ship
			
			;[Block] Shooting
			If MouseDown(1) Or KeyDown(29) Then 
			EndIf
			;[End Block]
			
			;[Block] Turning the SHip
			MX=MouseX() 
			MY=MouseY() 
			
			Local t1#
			Local t2# 
			
			t1#=Normalize(MY,0,Gh,-1,1) 
			t2#=Normalize(MX,0,Gw,1,-1) 
			
			If t1#<0 Then t1#=(Abs(t1#)^2.0)*-1 Else t1#=t1#^2.0 
			If t2#<0 Then t2#=(Abs(t2#)^2.0)*-1 Else t2#=t2#^2.0 
			
			Local TurnXTotal#=t1#*(Player_Environment_BaseTurn#*2)
			Local TurnYTotal#=t2#*(Player_Environment_BaseTurn#*2)
			
;			TurnEntity pvShip,t1#,t2#,roll*Player_Environment_BaseTurn#
			TurnEntity pvShip,TurnXTotal#,TurnYTotal#,roll
			tmSpeedX#=mSpeedY*2
			tmSpeedy#=mSpeedX*-4
			
			If SpeedX#<tmSpeedX# Then
				SpeedX#=SpeedX#+1
			ElseIf SpeedX#>tmSpeedX#
				SpeedX#=SpeedX#-1
			ElseIf SpeedX#<0.99 And SpeedX#>-0.99
				SpeedX#=0
			EndIf
			
			If SpeedY#<tmSpeedy# Then
				SpeedY#=SpeedY#+1
			ElseIf SpeedY#>tmSpeedy#
				SpeedY#=SpeedY#-1
			ElseIf SpeedY#<0.99 And SpeedY#>-0.99
				SpeedY#=0
			EndIf
			
			Local RollY#=(MouseX()-(GraphicsWidth()/2))/50*-1
			Local RollX#=(MouseY()-(GraphicsHeight()/2))/50
			RotateEntity eShipBody,RollX#,EntityYaw(eShipBody),RollY#
			RotateEntity tShip,RollX#,EntityYaw(eShipBody),RollY#;SpeedX#
			
			RotateEntity pvCamera, fCameraRotation[0]/2, fCameraRotation[1]/2, 0
			;[End Block]			
			
			;[End Block]
		Case MODE_CAMERA
			;[Block] Kamerasteuerung
			If Force_UI_Mode = 0 Then 
				PositionEntity WorldCamera, EntityX(pvCamera), EntityY(pvCamera)+Yoffset, EntityZ(WorldCamera), False
				
				If MouseDown(2)<>1 And MouseDown(1)<>1 Then
					PointEntity(WorldCamera,WorldCameraPivot)
					RotateEntity WorldCamera, EntityPitch(WorldCameraPivot), EntityYaw(WorldCameraPivot),EntityRoll(WorldCameraPivot)
				EndIf
				
				If MouseDown(1) And MouseDown(2)<>1 And MouseX()>40 And MenuStat=0 And Options=0 Then
					
					MX=MouseX() 
					MY=MouseY() 
					
					Local c1#
					Local c2# 
					
					c1#=Normalize(MY,0,Gh,-1,1) 
					c2#=Normalize(MX,0,Gw,1,-1) 
					
					TurnEntity pvCamera,c1#,c2#,0
					
					RotateEntity pvCamera,EntityPitch(pvCamera), EntityYaw(pvCamera),0
					
					If EntityPitch(pvCamera)> 85 Then RotateEntity pvCamera,  85, EntityYaw(pvCamera),0
					
					If EntityPitch(pvCamera)<-85 Then RotateEntity pvCamera, -85, EntityYaw(pvCamera),0
					
				EndIf
				
				If MouseDown(2) And MouseDown(1)<>1 And MouseX()>40 And MenuStat=0 And Options=0  Then
					
					
					MX=MouseX() 
					MY=MouseY() 
					
					c1#=Normalize(MY,0,Gh,-1,1) 
					c2#=Normalize(MX,0,Gw,1,-1) 
					
					TurnEntity WorldCamera,c1#,c2#,0
					
					RotateEntity WorldCamera,EntityPitch(WorldCamera), EntityYaw(WorldCamera),EntityRoll(pvCamera)
					
					If EntityPitch(pvCamera)> 85 Then RotateEntity pvCamera,  85, EntityYaw(pvCamera),0
					
					If EntityPitch(pvCamera)<-85 Then RotateEntity pvCamera, -85, EntityYaw(pvCamera),0
					
				EndIf
				
				
				
				Camera_Zoom_Speed#=Camera_Zoom_Speed#+MouseZSpeed()
				MoveEntity WorldCamera,0,0,Camera_Zoom_Speed#
				Camera_Zoom_Speed#=Camera_Zoom_Speed#*0.9
				
				RotateEntity eShipBody,SpeedX#,EntityYaw(eShipBody),SpeedY#*Player_Value_Inertia_Base	;SpeedX#
				RotateEntity tShip,SpeedX#,EntityYaw(eShipBody),SpeedY#*Player_Value_Inertia_Base	;SpeedX#
				
				tmSpeedX#=0
				tmSpeedy#=0
				
				If SpeedX#<tmSpeedX# Then
					SpeedX#=SpeedX+1
				ElseIf SpeedX#>tmSpeedX#
					SpeedX#=SpeedX#-1
				ElseIf SpeedX#<0.99 And SpeedX#>-0.99
					SpeedX#=0
				EndIf
				
				If SpeedY#<tmSpeedy# Then
					SpeedY#=SpeedY#+1
				ElseIf SpeedY#>tmSpeedy#
					SpeedY#=SpeedY#-1
				ElseIf SpeedY#<0.99 And SpeedY#>-0.99
					SpeedY#=0
				EndIf
			EndIf	
			;[End Block]
		Case MODE_DOCKED
			RotateEntity eShipBody,SpeedX#,EntityYaw(eShipBody),SpeedY#*Player_Value_Inertia_Base	;SpeedX#
			RotateEntity tShip,SpeedX#,EntityYaw(eShipBody),SpeedY#*Player_Value_Inertia_Base	;SpeedX#
			
			tmSpeedX#=0
			tmSpeedy#=0
			
			If SpeedX#<tmSpeedX# Then
				SpeedX#=SpeedX+1
			ElseIf SpeedX#>tmSpeedX#
				SpeedX#=SpeedX#-1
			ElseIf SpeedX#<0.99 And SpeedX#>-0.99
				SpeedX#=0
			EndIf
			
			If SpeedY#<tmSpeedy# Then
				SpeedY#=SpeedY#+1
			ElseIf SpeedY#>tmSpeedy#
				SpeedY#=SpeedY#-1
			ElseIf SpeedY#<0.99 And SpeedY#>-0.99
				SpeedY#=0
			EndIf
	End Select

	HandleInput()
	
	;[Block] Player Health Checkup
	Select Player_State_Alive
		Case 0			; Player Alive and well
		Case 1			; Player Just died. Disable all Controls, Display Gameover and allow selection
		Case 2			; Display Insurance Screen, reinitialize location
	End Select
	
	;[End Block]
	
	;[Block] Debug Information
	If Game_Menu_Debug = 1 Then
		Text3D(Text_Font[1], D3DOL+100,  D3DOU-100-(16*02), "Frame")
		Text3D(Text_Font[1], D3DOL+190, D3DOU-100-(16*02), Performance)
		
		FPS# = (1000.0 / Float(Performance)) * 0.9 + (FPS * 0.1)
		
		Text3D(Text_Font[1], D3DOL+100,  D3DOU-100-(16*03), "FPS")
		Text3D(Text_Font[1], D3DOL+190, D3DOU-100-(16*03), Floor(FPS#))
		Text3D(Text_Font[1], D3DOL+100,  D3DOU-100-(16*04), "Render")
		Text3D(Text_Font[1], D3DOL+190, D3DOU-100-(16*04), Performance_Render)
		Text3D(Text_Font[1], D3DOL+100,  D3DOU-100-(16*05), "Tris On Screen")
		Text3D(Text_Font[1], D3DOL+190, D3DOU-100-(16*05), TrisRendered())
		Text3D(Text_Font[1], D3DOL+100,  D3DOU-100-(16*06), "X")
		Text3D(Text_Font[1], D3DOL+190, D3DOU-100-(16*06), Floor(EntityX(pvShip,True)))
		Text3D(Text_Font[1], D3DOL+100,  D3DOU-100-(16*07), "Y")
		Text3D(Text_Font[1], D3DOL+190, D3DOU-100-(16*07), Floor(EntityY(pvShip,True)))
		Text3D(Text_Font[1], D3DOL+100,  D3DOU-100-(16*08), "Z")
		Text3D(Text_Font[1], D3DOL+190, D3DOU-100-(16*08), Floor(EntityZ(pvShip,True)))
		
		Text3D(Text_Font[1], D3DOL+100,  D3DOU-100-(16*10), "Wait")
		Text3D(Text_Font[1], D3DOL+190, D3DOU-100-(16*10), Performance_Wait)
	EndIf
	;[End Block]
	
	Performance_Update_UI = MilliSecs() - ms_Performance_Update_UI
	
	;[Block] Unsorted Updates
	Local ms_Performance_RestUpdate = MilliSecs()
	
	Util_Timer
	
	mPosition[0] = MouseX():mPosition[1] = MouseY()
	mDiffX = mPosition[0] - mOrigin[0]:mDiffY = mPosition[1] - mOrigin[1]
	
	PlayerX=EntityX(WorldCamera,True)
	PlayerY=EntityY(WorldCamera,True)
	PlayerZ=EntityZ(WorldCamera,True)
	
	PositionEntity Object_Environment[2],PlayerX, PlayerY, PlayerZ, True
	PositionEntity Object_Environment[0],PlayerX, PlayerY, PlayerZ, True
	PositionEntity Zone_Dust_Base, PlayerX, PlayerY, PlayerZ, True
	
	D3DMouseX=(MouseX()-GraphicsWidth()/2)
	D3DMouseY=(MouseY()-GraphicsHeight()/2)
	
	UpdateWorld()
	Performance_Update = MilliSecs() - ms_Performance_Update
	
	;[Block] Render
	Local ms_Performance_Render = MilliSecs()
	
	If WorldClock$<>CurrentTime() Then
		WorldTimers_Update()
		WorldClock$=CurrentTime()
	EndIf
	
	Music_Update()
	
	UpdatePlayerShipValues()
	
	Asset_Belt_Update()
	Asset_Station_Update()
	Asset_DockCube_Update()
	Asset_Planet_Update()
	Asset_Gate_Update()
	Asset_Special_Update()
	Asset_Emitter_Update()
	
	Emitter_Particle_Update()
	
	Mechanic_Weapon_Update()
	
	Container_Update()
	
	Environment_FastTravel_Update()
	Environment_NavMesh_Update()
	
	Chat_Output()
	
	If Force_UI_Mode = 0
		Inventory_Show(1)
	Else
		Inventory_Show(2)
	EndIf
	
	If Game_Menu_Open = 1 Then UI_Draw_Options()
	
	DST_Update()
	
	Asset_Flare_Update()
	
	UpdateGraphics()
	
	Collisions Collision_Player, Collision_Object,2,2
	
	Interface_Container_Display()
	RenderWorld
	
	Flip
	
	;[Block] Wait for Timer
	Local ms_Performance_Wait = MilliSecs()
	WaitTimer(RenderTimer)
	Performance_Wait = MilliSecs() - ms_Performance_Wait
	;[End Block]
	
	Performance_Render = MilliSecs() - ms_Performance_Render
	;[End Block]
	
	Performance = MilliSecs() - ms_Performance
	
	Clear3D()
	
Until Game_End>0

Select Game_End	
	Case 1
		ShowPointer
		Goto MainMenu
	Case 2
		Asset_Clear_All()
		Clear3D()
		ClearWorld()
		End
End Select
State_Menu_Subcontext = 1
ShowPointer


End
;~IDEal Editor Parameters:
;~F#A5#B0#E4#F8#103#10D#117#11D#123#12D#258#25F#266#26D#273#27A#3D9#4A9#4B5#4D3
;~F#509#571#57A#5AB#5DF
;~B#3AB
;~C#Blitz3D