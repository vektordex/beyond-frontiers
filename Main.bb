;! Bug Fix: Early CTD caused by Blitz3D not being ready at this time.
Delay 100

;----------------------------------------------------------------
;! Includes
;----------------------------------------------------------------
; Libraries
Include "Libraries\BlitzUtility\BlitzUtility.bb"
Include "Libraries\InputEx\InputEx.bb"
Include "Libraries\System\Threading.bb"
Include "Libraries\System\ThreadPool.bb"
Include "Libraries\Draw3D2\Includes\Draw3D2.bb"
Include "Shared\Containers\LinkedList.bb"

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
Include "Modules\Coordinates.bb"
Include "Shared\Math\Delta.bb"
Include "Shared\Math\Shapes.bb"
Include "Shared\Math\Vector2D.bb"
Include "Shared\Math\Vector3D.bb"

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
CameraScene = CreateCamera()
CameraRange CameraScene, CAMERA_NEAR, CAMERA_FAR
CameraZoom CameraScene, 1.0 / Tan(90 / 2.0) ;!ToDo: Field of View settings, see line below.
;CameraZoom CameraScene, 1.0 / Tan(FOV# / 2.0) 

; UI: Camera
CameraUI = CreateCamera()
CameraClsMode CameraUI, 0, 1
CameraClsColor CameraUI, 127, 127, 127
CameraRange CameraUI, DRAWDISTANCE-64, DRAWDISTANCE+64
DrawInit3D(CameraUI)
HideEntity CameraUI

; Scene: Create a VirtualScene Object (hiding and showing where needed)
;Scene.VirtualScene = VirtualScene_Create()

; Data: Data Root (Invisible loaded Geometry)
SceneDataRoot = CreatePivot()
;EntityAlpha SceneDataRoot, 0
HideEntity SceneDataRoot

; Collisions
Collisions_Initialize()

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
Local Loading_BG = LoadImage("Assets\2D\LoadScreens\Loading_Screen.png"):MidHandle(Loading_BG)
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
				;[Block] Ships
				Local LoadOrder = OpenFile("Assets\Manifest\LoadShips.lof")
				Repeat
					Local LoadData$ = ReadLine(LoadOrder)
					LoadMeshAsset("Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Mesh.3DS", SceneDataRoot)
					LoadTextureAsset("Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Color.jpg",1+2)
					LoadTextureAsset("Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Glow.jpg",1+2)
				Until Eof(LoadOrder)
				CloseFile LoadOrder
				;[End Block]
				
				;[Block] Stations
				LoadOrder = OpenFile("Assets\Manifest\LoadStations.lof")
				Repeat
					LoadData$ = ReadLine(LoadOrder)
					LoadMeshAsset("Assets\3D\Stations\"+LoadData$+"\"+LoadData$+"_Mesh.3DS", SceneDataRoot)
					LoadTextureAsset("Assets\3D\Stations\"+LoadData$+"\"+LoadData$+"_Color.jpg",1+2)
					LoadTextureAsset("Assets\3D\Stations\"+LoadData$+"\"+LoadData$+"_Glow.jpg",1+2)
				Until Eof(LoadOrder)
				CloseFile LoadOrder
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
				;[End Block]
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
				Until Eof(LoadOrder)
				CloseFile LoadOrder
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
					LoadSoundAsset("Assets\Sounds\"+LoadData$+".ogg")
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
					LoadSoundAsset("Assets\Music\"+LoadData$+".ogg")
				Until Eof(LoadOrder)
				CloseFile LoadOrder
				;[End Block]
				
				;[Block] Voices -------------------------------------------------
				;----------------------------------------------------------------
				;! Game - Voices
				;----------------------------------------------------------------
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
			Loading_State_Next = LOADING_STATE_CONNECTING
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
	AssetManager_Suspend()
	DrawImage Loading_BG, GwBy2, GhBy2 ; Background
	
;	Color 0, 0, 0:Rect 0, Gh - 45, Gw, 45, 1 ; Bottom Bar
	Color 220,220,220:Text GwBy2, Gh - 15, Loading_Quote, True, True ; Quote
	
	Select Loading_State
		Case LOADING_STATE_LOADING ;[Block]
			Local ls_loading_barY = Gh - 30
			
			Local ls_resources_loaded_prc# = ls_Count_Loaded / Float(ls_Count_Total)
			Local ls_resources_loading_prc# = ls_Count_Loading / Float(ls_Count_Total) + ls_resources_loaded_prc
			Local ls_resources_error_prc# = ls_Count_Error / Float(ls_Count_Total) + ls_resources_loading_prc
			
			Local ls_t = 15, ls_n
			For ls_n = 0 To ls_t
				Local ls_p# = ls_n / Float(ls_t)
				
				; GFX: Bar
;				Color ls_p * 51, ls_p * 51, ls_p * 51
;				Line 0, ls_loading_barY - ls_n, Gw, ls_loading_barY - ls_n
				
				; GFX: Error
				Color 204 + ls_p * 51, ls_p * 51, ls_p * 51
				Line GwBy2 * (1.0 - ls_resources_error_prc), ls_loading_barY - ls_n, GwBy2 + (GwBy2 * ls_resources_error_prc), ls_loading_barY - ls_n
				
				; GFX: Loading
				Color 204 + ls_p * 51, ls_p * 51, 102 + ls_p * 51
				Line GwBy2 * (1.0 - ls_resources_loading_prc), ls_loading_barY - ls_n, GwBy2 + (GwBy2 * ls_resources_loading_prc), ls_loading_barY - ls_n
				
				; GFX: Complete
				Color 51 + ls_p * 51, 204 + ls_p * 51, 204 + ls_p * 51
				Line GwBy2 * (1.0 - ls_resources_loaded_prc), ls_loading_barY - ls_n, GwBy2 + (GwBy2 * ls_resources_loaded_prc), ls_loading_barY - ls_n
			Next
			
			Color 0,0,0
			Text GwBy2, ls_loading_barY - 8, Int(Floor(ls_resources_loaded_prc * 1000) / 10) + "% done", 1, 1
			
			;[End Block]
		Case LOADING_STATE_SERVERLIST ;[Block]
			Loading_State_Next = LOADING_STATE_CONNECTING
			;[End Block]
		Case LOADING_STATE_CONNECTING ;[Block]
			
			;[End Block]
	End Select
	
	Flip 0:Cls
	AssetManager_Resume()
	;[End Block]
	
	;[Block] State Update
	InputEx_Update()
	
	; Advance State
	If Loading_State <> Loading_State_Next Then
		Loading_State = Loading_State_Next
		Loading_State_JustSwitched = True
	Else
		Loading_State_JustSwitched = False
	EndIf
	
	; Wait
	WaitTimer RenderTimer
	;[End Block]
Until (Loading_State = LOADING_STATE_COMPLETE)
;[End Block]

;----------------------------------------------------------------
;! Old Startup
;----------------------------------------------------------------

; 3D: Camera
Const MODE_CAMERA = 0
Const MODE_SHIP  = 1
Const MODE_DOCKED = 2

Global fShipTargetRotation#[2], fCameraRotation#[2]
Global eMode = MODE_CAMERA, eCameraMode = MODE_CAMERA

Global pvShip = CreatePivot()
Global pvShipTarget = CreatePivot()
Global pvtPoint = CreatePivot()
Global pvCamera = CreatePivot(pvShip)
Global pvCameraOrigin = CreatePivot(pvCamera)
MoveEntity pvCameraOrigin, 0, 0, -100
TurnEntity pvCamera, -10, 0, 0

Global WorldCamera = CameraScene
EntityParent WorldCamera, pvCameraOrigin

; Create Local Player
Global Player_Location.Location = Location_Create(0, 0, 0)

; Initialization Code (Old)
 ;!ToDo: AssetManager

;[Block] Old
SHipInertia#=1.085
;ShipPosXYZ = CreatePivot():VirtualScene_Register(Scene, ShipPosXYZ)
;Weapon_Target_Cube = LoadSprite("Content\GFX\Interface\Icons\interface_mouse_aim.png",2)
;VirtualScene_Register(Scene, Weapon_Target_Cube)

;Extensions
;Set Planet Array
Global Player_Value_Inertia_Base#
Global Zoffset, Yoffset
Global eShip = CreatePivot(pvShip)
Global eShipBody = CreatePivot(eShip)
Global tShip = CreatePivot(pvShip)
Global pvGunRange=CreatePivot(pvShip)
MoveEntity pvGunRange,0,0,300 
MoveEntity eShip, 0, 0, 2
MoveEntity eShipBody, 0, 0, 2
EntityType pvShip,1,False
Global pvShipCollision = CreatePivot(pvShip)
EntityRadius pvShipCollision,150
EntityType pvShipCollision,3
Global mPosition[2], mOrigin[2], mDiff[2]:mOrigin[0] = GraphicsWidth()/2:mOrigin[1] = GraphicsHeight()/2
MoveMouse GraphicsWidth()/2, GraphicsHeight()/2
Const MaxSpeed = 180
Global HOTBAR=1
ShowEntity eShipBody
EntityPickMode eShipBody,1,True
EntityRadius eShipBody, 200

Global ChatStream 

Global Planet_Rings[5]



Global D3DOL=GraphicsWidth()/2*-1
Global D3DOR=GraphicsWidth()/2
Global D3DOU=GraphicsHeight()/2
Global D3DOD=GraphicsHeight()/2*-1
Global D3DCX=0
Global D3DCY=0


;[End Block]

Global CharDataLoaded=0
;! SPOT: SaveGame loading and user data ------------------------------------------------------------------------------------------------------------------------

ShowEntity CameraUI

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

;[Block] Character Loading
LoopSound Music_ID[9]
Channel_Music = PlaySound(Music_ID[9])
ChannelVolume Channel_Music,0.0
Music_Volume = 0.0

Repeat
	ChannelVolume Channel_Music,Music_Volume#
	If Music_Enabled = 1 Then
		If Music_Volume#<Desired_MVolume# Then Music_Volume# = Music_Volume# + 0.005
		
		If Music_Volume#>Desired_MVolume# Then Music_Volume# = Music_Volume# - 0.005
	EndIf
	If MouseHit(1) = False And MouseHit(2) = False Then FlushMouse()
	
	DrawImage3D	(GUI_Windows[1],Sin(MilliSecs()/20)*15,Sin(MilliSecs()/18)*15)
	
	DrawImage3D	(GUI_Windows[9],D3DOR-64,D3DOU-64)
	
	Util_Timer
	InputEx_Update
	
	tmx = MouseX()-(GraphicsWidth()/2)
	tmy = MouseY()-(GraphicsHeight()/2)
	
	Select State_Menu_Subcontext
		Case 1 ; >> Menu Main Window
			
			DrawImage3D	(GUI_Windows[2],0,0)
			
			DrawImage3D	(GUI_Windows[3],-376,-250)
			DrawImage3D	(GUI_Windows[4],-228,-250)
			DrawImage3D	(GUI_Windows[5],-80,-250)
			DrawImage3D	(GUI_Windows[6],80,-250)
			DrawImage3D	(GUI_Windows[7],228,-250)
			DrawImage3D	(GUI_Windows[8],376,-250)
			
			;[Block] Discord
			If MouseX()>(GraphicsWidth()/2-440) And MouseX()<(GraphicsWidth()/2-305) And MouseY()>(GraphicsHeight()/2)+230 And MouseY()<(GraphicsHeight()/2)+270  ;Discord Button
				Text3D (Text_Font[8],-376,-250,"D i s c o r d",1)
				If MouseHit(1) Then ExecFile("https://discord.gg/D5e5qRtQkb"): PlaySound(Sound_ID[1])
			Else
				Text3D (Text_Font[7],-376,-250,"D i s c o r d",1)
			EndIf
			;[End Block]
			
			;[Block] Credits
			If MouseX()>(GraphicsWidth()/2-290) And MouseX()<(GraphicsWidth()/2-155) And MouseY()>(GraphicsHeight()/2)+230 And MouseY()<(GraphicsHeight()/2)+270  ;Credits Button
				Text3D (Text_Font[8],-228,-250,"C r e d i t s",1)
				If MouseHit(1) Then State_Menu_Subcontext = 5: PlaySound(Sound_ID[1])
			Else
				Text3D (Text_Font[7],-228,-250,"C r e d i t s",1)
			EndIf
			;[End Block]
			
			;[Block] Create New Game / Character
			If MouseX()>(GraphicsWidth()/2-150) And MouseX()<(GraphicsWidth()/2-5) And MouseY()>(GraphicsHeight()/2)+230 And MouseY()<(GraphicsHeight()/2)+270  ;New Game Button
				Text3D (Text_Font[8], -80,-250,"N e w   G a m e",1)		
				If MouseHit(1) Then State_Menu_Subcontext_Character=1: State_Menu_Subcontext = 3: PlaySound(Sound_ID[1])
			Else
				Text3D (Text_Font[7], -80,-250,"N e w   G a m e",1)			
			EndIf
			;[End Block]
			
			;[Block] Go to Character Overview
			If MouseX()>(GraphicsWidth()/2+5) And MouseX()<(GraphicsWidth()/2+150) And MouseY()>(GraphicsHeight()/2)+230 And MouseY()<(GraphicsHeight()/2)+270  ;Continue Button
				Text3D (Text_Font[8],  80,-250,"C o n t i n u e",1)
			Else
				Text3D (Text_Font[7],  80,-250,"C o n t i n u e",1)
			EndIf
			;[End Block]
			
			;[Block] Options Window
			If MouseX()>(GraphicsWidth()/2+155) And MouseX()<(GraphicsWidth()/2+290) And MouseY()>(GraphicsHeight()/2)+230 And MouseY()<(GraphicsHeight()/2)+270  ;Options Button
				Text3D (Text_Font[8], 228,-250,"O p t i o n s",1)
				If MouseHit(1) Then State_Menu_Subcontext = 2: PlaySound(Sound_ID[1])
			Else
				Text3D (Text_Font[7], 228,-250,"O p t i o n s",1)	
			EndIf
			;[End Block]
			
			;[Block] Exit Button
			If MouseX()>(GraphicsWidth()/2+305) And MouseX()<(GraphicsWidth()/2+440) And MouseY()>(GraphicsHeight()/2)+230 And MouseY()<(GraphicsHeight()/2)+270 ;Exit Button
				Text3D (Text_Font[8], 376,-250,"E x i t",1)	
				If MouseHit(1) Then
					ClearWorld
					End
				EndIf
			Else
				Text3D (Text_Font[7], 376,-250,"E x i t",1)	
			EndIf
			;[End Block]
			
		Case 2 ; >> Options
			;[Block] Options
			DrawImage3D(GUI_Windows[27],0,GraphicsHeight()/2-16,0,0,2)
			Text3D(Text_Font[7],0,GraphicsHeight()/2-16,"S e t t i n g s",1)
			
			DrawImage3D(GUI_Windows[29],140,0)
			DrawImage3D(GUI_Windows[21],-420,0,0,0,1.47)
;			DrawImage3D(GUI_Windows[23],0,0)
			
			Select State_Menu_Subcontext_Settings
				Case 1 ; >> Graphics
					DrawImage3D(GUI_Windows[16],-420,210,0,0,1.8)
					DrawImage3D(GUI_Windows[19],-420,170,0,0,1.8)
					DrawImage3D(GUI_Windows[19],-420,130,0,0,1.8)
					DrawImage3D(GUI_Windows[19],-420, 90,0,0,1.8)
					Text3D(Text_Font[6],140,252,"G r a p h i c s",1)
					
;					Text X -250, Y - 230
					
					Text3D(Text_Font[7],-210,210,"S e t   u p   g r a p h i c s   o p t i o n s   h e r e .")					
				Case 2 ; >> Audio
					DrawImage3D(GUI_Windows[19],-420,210,0,0,1.8)
					DrawImage3D(GUI_Windows[16],-420,170,0,0,1.8)
					DrawImage3D(GUI_Windows[19],-420,130,0,0,1.8)
					DrawImage3D(GUI_Windows[19],-420, 90,0,0,1.8)
					Text3D(Text_Font[6],140,252,"S o u n d",1)
					
					Text3D(Text_Font[7],-210,210,"S e t   u p   a u d i o   o p t i o n s   h e r e .")
					
				Case 3 ; >> Music
					DrawImage3D(GUI_Windows[19],-420,210,0,0,1.8)
					DrawImage3D(GUI_Windows[19],-420,170,0,0,1.8)
					DrawImage3D(GUI_Windows[16],-420,130,0,0,1.8)
					DrawImage3D(GUI_Windows[19],-420, 90,0,0,1.8)
					Text3D(Text_Font[6],140,252,"M u s i c",1)
					
					Text3D(Text_Font[7],-210,210,"S e t   u p   m u s i c   o p t i o n s   h e r e .")
					
					Text3D(Text_Font[7],-210,160,"M u s i c")
					Text3D(Text_Font[7],-210,130,"V o l u m e")
					
					If Music_Enabled = 1 Then
						DrawImage3D(GUI_Windows[24],+490,160)
						Text3D(Text_Font[7],+410,160,"O n",1)
					Else
						DrawImage3D(GUI_Windows[25],+490,160,0,180)
						Text3D(Text_Font[7],+410,160,"O f f",1)
					EndIf
					If MouseY()>GhBy2-170 And MouseY()<GhBy2-150
						If MouseX()>GwBy2+470 And MouseX()<GwBy2+510
							If MouseHit(1)
								If Music_Enabled = 1 Then
									Music_Enabled = 0
									Music_Volume = 0: PlaySound(Sound_ID[4])
								ElseIf Music_Enabled = 0 Then
									Music_Enabled = 1: PlaySound(Sound_ID[4])
								EndIf
							EndIf
						EndIf
					EndIf
					
					DrawImage3D(GUI_Windows[22],+170,130,0,90,1.5)
					DrawImage3D(GUI_Windows[22],+205,130,0,-90,1.5)
					
					DrawImage3D(GUI_Windows[23],+340,130,0,0,1.5)
					
					Local MusicVol = Desired_MVolume#*100
					Text3D(Text_Font[7],+380,130,MusicVol+" %",1)
					
					If MouseY()>GhBy2-140 And MouseY()<GhBy2-120
						If MouseX()>GwBy2+160 And MouseX()<GwBy2+180
							If MouseHit(1) Then Desired_MVolume = Desired_MVolume - 0.05: PlaySound(Sound_ID[1])
						EndIf
						If MouseX()>GwBy2+195 And MouseX()<GwBy2+215
							If MouseHit(1) Then Desired_MVolume = Desired_MVolume + 0.05: PlaySound(Sound_ID[1])
						EndIf
					EndIf
					
					If Desired_MVolume > 1 Then Desired_MVolume = 1
					If Desired_MVolume < 0 Then Desired_MVolume = 0
						
				Case 4 ; >> Other
					DrawImage3D(GUI_Windows[19],-420,210,0,0,1.8)
					DrawImage3D(GUI_Windows[19],-420,170,0,0,1.8)
					DrawImage3D(GUI_Windows[19],-420,130,0,0,1.8)
					DrawImage3D(GUI_Windows[16],-420, 90,0,0,1.8)
					Text3D(Text_Font[6],140,252,"O t h e r",1)
					
					Text3D(Text_Font[7],-210,210,"S e t   u p   o t h e r   o p t i o n s   h e r e .")
					
			End Select
			
			DrawImage3D(GUI_Windows[19],-420,-220,0,0,1.8)
			
			If MouseX()>GraphicsWidth()/2-515 And MouseX()<GraphicsWidth()/2-330
				If MouseY()>(GraphicsHeight()/2)-228 And MouseY()<(GraphicsHeight()/2)-192
					DrawImage3D(GUI_Windows[17],-420,210,0,0,1.8)
					If MouseHit(1) Then State_Menu_Subcontext_Settings = 1: PlaySound(Sound_ID[1])
				ElseIf  MouseY()>(GraphicsHeight()/2)-190 And MouseY()<(GraphicsHeight()/2)-154
					DrawImage3D(GUI_Windows[17],-420,170,0,0,1.8)
					If MouseHit(1) Then State_Menu_Subcontext_Settings = 2: PlaySound(Sound_ID[1])
				ElseIf  MouseY()>(GraphicsHeight()/2)-152 And MouseY()<(GraphicsHeight()/2)-116
					DrawImage3D(GUI_Windows[17],-420,130,0,0,1.8)
					If MouseHit(1) Then State_Menu_Subcontext_Settings = 3: PlaySound(Sound_ID[1])
				ElseIf  MouseY()>(GraphicsHeight()/2)-114 And MouseY()<(GraphicsHeight()/2)-78
					DrawImage3D(GUI_Windows[17],-420, 90,0,0,1.8)
					If MouseHit(1) Then State_Menu_Subcontext_Settings = 4: PlaySound(Sound_ID[1])
				ElseIf  MouseY()>(GraphicsHeight()/2)+202 And MouseY()<(GraphicsHeight()/2)+238
					DrawImage3D(GUI_Windows[16],-420,-220,0,0,1.8)
					If MouseHit(1) Then State_Menu_Subcontext = 1: PlaySound(Sound_ID[3])
				EndIf
			EndIf
			
			
			
			Text3D(Text_Font[7],-420,210,"G r a p h i c s",1)
			Text3D(Text_Font[7],-420,170,"S o u n d s",1)
			Text3D(Text_Font[7],-420,130,"M u s i c",1)
			Text3D(Text_Font[7],-420, 90,"O t h e r",1)
			
			Text3D(Text_Font[7],-420,-220,"C o n f i r m",1)
			
			
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
					
					DrawImage3D(GUI_Windows[21],BaseX,-50,0,0,2)
					Text3D(Text_Font[7],BaseX,400,"T e r r a n   C o n g l o m e r a t e",1)
					Text3D(Text_Font[10],BaseX,370,"Money is Power",1)
					
					Text3D(Text_Font[1],BaseX,260,"A capitalist society composed of and ruled by various corporations,",1)
					Text3D(Text_Font[1],BaseX,240,"with a strong focus on financial And military power. The",1)
					Text3D(Text_Font[1],BaseX,220,"Conglomerate seeks to expand its influence throughout the galaxy",1)
					Text3D(Text_Font[1],BaseX,200,"both through monetary and military conquest.",1)
;					Text3D(Text_Font[1],BaseX,180,"",1)
					Text3D(Text_Font[1],BaseX,140,"Starting System:",1)
					Text3D(Text_Font[2],BaseX,120,"Luna",1)
;					Text3D(Text_Font[1],BaseX,120,"3",1)
					Text3D(Text_Font[1],BaseX, 80,"Starting Money:",1)
					Text3D(Text_Font[3],BaseX, 60,"25.000 Cr.",1)
;					Text3D(Text_Font[1],BaseX, 60,"6",1)
;					Text3D(Text_Font[1],BaseX, 40,"7",1)
					Text3D(Text_Font[1],BaseX, 20,"Terrans have the biggest Empire, but are only connected to the rest",1)
					Text3D(Text_Font[1],BaseX,  0,"of the Network on two chokepoint systems. An abundance of stations",1)
					Text3D(Text_Font[1],BaseX,-20,"accelerates every Traders aspirations",1)
					
					DrawImage3D(GUI_Windows[19], BaseX,-350,0,0,2)
					
					BaseX = 0
					
					DrawImage3D(GUI_Windows[21],BaseX,-50,0,0,2)
					Text3D(Text_Font[7],BaseX,400,"S i r i u s   D o m i n i o n",1)
					Text3D(Text_Font[10],BaseX,370,"Freedom and Liberty",1)
					
					Text3D(Text_Font[1],BaseX,260,"A liberal democracy with a strong emphasis on personal freedom and",1)
					Text3D(Text_Font[1],BaseX,240,"exploration, governed by a president, chancellor, and seven chambers",1)
					Text3D(Text_Font[1],BaseX,220,"of advisors. The Dominion has a mixed economy, with both private",1)
					Text3D(Text_Font[1],BaseX,200,"enterprise and government control, and prioritizes the well-being",1)
					Text3D(Text_Font[1],BaseX,180,"of its citizens.",1)
					
					Text3D(Text_Font[1],BaseX,140,"Starting System:",1)
					Text3D(Text_Font[2],BaseX,120,"Kurai",1)
;					Text3D(Text_Font[1],BaseX,120,"3",1)
					Text3D(Text_Font[1],BaseX, 80,"Starting Money:",1)
					Text3D(Text_Font[3],BaseX, 60,"9.000 Cr.",1)
;					Text3D(Text_Font[1],BaseX, 60,"6",1)
;					Text3D(Text_Font[1],BaseX, 40,"7",1)
					Text3D(Text_Font[1],BaseX, 20,"The Dominion is the first independent Empire and has the most open",1)
					Text3D(Text_Font[1],BaseX,  0,"battlefield sectors in the Network, a good chance for budding",1)
					Text3D(Text_Font[1],BaseX,-20,"privateers.",1)
					
					DrawImage3D(GUI_Windows[19], BaseX,-350,0,0,2)
					
					BaseX = 428
					
					DrawImage3D(GUI_Windows[21],BaseX,-50,0,0,2)
					Text3D(Text_Font[7],BaseX,400,"O r i o n   C o u n c i l",1)
					Text3D(Text_Font[10],BaseX,370,"Better Oneself",1)
					
					Text3D(Text_Font[1],BaseX,260,"A meritocratic society of highly enducated people, with each member",1)
					Text3D(Text_Font[1],BaseX,240,"of the Council representing a different region of the Orionic",1)
					Text3D(Text_Font[1],BaseX,220,"Empire. The Council is responsible for making major decisions regarding",1)
					Text3D(Text_Font[1],BaseX,200,"the empire, with the goal of maintaining a strong and stable society",1)
					Text3D(Text_Font[1],BaseX,180,"based on tradition and order.",1)
					Text3D(Text_Font[1],BaseX,140,"Starting System:",1)
					Text3D(Text_Font[2],BaseX,120,"Maia",1)
;					Text3D(Text_Font[1],BaseX,120,"3",1)
					Text3D(Text_Font[1],BaseX, 80,"Starting Money:",1)
					Text3D(Text_Font[3],BaseX, 60,"10.000 Cr.",1)
;					Text3D(Text_Font[1],BaseX, 60,"6",1)
;					Text3D(Text_Font[1],BaseX, 40,"7",1)
					Text3D(Text_Font[1],BaseX, 20,"As the smallest and newest Empire, the council has many bordering",1)
					Text3D(Text_Font[1],BaseX,  0,"unexplored sectors and can provide many opportunities for",1)
					Text3D(Text_Font[1],BaseX,-20,"adventurers.",1)
					
					DrawImage3D(GUI_Windows[19], BaseX,-350,0,0,2)
					
					
					
					DrawImage3D(GUI_Windows[36],0,0,0,0,1.25)
					If MouseX()>GwBy2-638 And MouseX()<GwBy2-215 Then
						DrawImage3D(GUI_Windows[33],0,Sin(MilliSecs()/50)*15,0,0,1.25)
						DrawImage3D(GUI_Windows[32],-432,0,0,0,1.25)
						
						If MouseY()>GhBy2+330 And MouseY()<GhBy2+370 Then
							DrawImage3D(GUI_Windows[16],-432,-350,0,0,2)
							If MouseHit(1) Then PlaySound(Sound_ID[1]):NewChar_Faction$="Terran Conglomerate": NewChar_Money=12000:NewChar_System$="Luna":State_Menu_Subcontext_Character=2
						EndIf
						
					ElseIf MouseX()>GwBy2-215 And MouseX()<GwBy2+205 Then
						DrawImage3D(GUI_Windows[34],0,Sin(MilliSecs()/50)*15,0,0,1.25)
						DrawImage3D(GUI_Windows[32],0,0,0,0,1.25)
						
						If MouseY()>GhBy2+330 And MouseY()<GhBy2+370 Then
							DrawImage3D(GUI_Windows[16],0,-350,0,0,2)
							If MouseHit(1) Then PlaySound(Sound_ID[1]):NewChar_Faction$="Sirius Dominion": NewChar_Money=8000:NewChar_System$="Kurai":State_Menu_Subcontext_Character=2
						EndIf
						
						
					ElseIf MouseX()>GwBy2+205 And MouseX()<GwBy2+605 Then
						DrawImage3D(GUI_Windows[35],0,Sin(MilliSecs()/50)*15,0,0,1.25)
						DrawImage3D(GUI_Windows[32],428,0,0,0,1.27)
						
						If MouseY()>GhBy2+330 And MouseY()<GhBy2+370 Then
							DrawImage3D(GUI_Windows[16],428,-350,0,0,2)
							If MouseHit(1) Then PlaySound(Sound_ID[1]):NewChar_Faction$="Orionic Council Republic": NewChar_Money=10000:NewChar_System$="Maia":State_Menu_Subcontext_Character=2
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
					
					Text3D(Text_Font[1],0, -110,"I hereby pledge my loyalty to "+NewCharacter_Faction$+" and will do my best to adhere to their values,",1)
					Text3D(Text_Font[1],0, -170,"(supported letters and characters are a-z, A-Z, 0-9 and !-_.:)",1)
					
					DrawImage3D(GUI_Windows[23],0,-140,0,0,2.2)
					Text3D(Text_Font[7],-150, -140,Character_NewName$)
					Text3D(Text_Font[7],-240, -140,"Cmdr.",1)
					
					Text3D(Text_Font[7],0,-300,"Confirm and Play!",1)
					
					If MouseX()>GwBy2-100 And MouseX()<GwBy2+100 And MouseY()>GhBy2+250 And MouseY()<GhBy2+350 Then
						DrawImage3D(GUI_Windows[16], 0,-300,0,0,2)	
						If MouseHit(1) Then Goto Spacegamestart: PlaySound(Sound_ID[1])
					Else
						DrawImage3D(GUI_Windows[19], 0,-300,0,0,2)
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
						If InputEx_KeyHit(KEY_1) Then Character_NewName$=Character_NewName$+"!": PlaySound(Sound_ID[1])
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
						If InputEx_KeyHit(KEY_PERIOD) Then Character_NewName$=Character_NewName$+":": PlaySound(Sound_ID[1])
						If InputEx_KeyHit(KEY_PLUS) Then Character_NewName$=Character_NewName$+"*": PlaySound(Sound_ID[1])
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
						
;						If InputEx_KeyHit(KEY_L) Then Character_NewName$=Character_NewName$+"L"
;						If InputEx_KeyHit(KEY_L) Then Character_NewName$=Character_NewName$+"L"
					EndIf
					
					If InputEx_KeyDown(KEY_BACKSPACE) And Character_NewName$>"" Then 
						Tcounter=Tcounter+1
						If Tcounter>7 Then 
							Character_NewName$=Mid(Character_NewName$,1,Len(Character_NewName$)-1) 
							Tcounter=0
							PlaySound(Sound_ID[1])
						EndIf
					Else 
						Tcounter=4
					EndIf
										;[End Block]
					
			End Select
			
			DrawImage3D(GUI_Windows[27],0,GraphicsHeight()/2-16,0,0,5)
			DrawImage3D(GUI_Windows[16], GraphicsWidth()/2-128,-GraphicsHeight()/2+64)
			
			Text3D(Text_Font[7],0,GraphicsHeight()/2-24,"N e w   G a m e",1)
		Case 5 ; >> Credits
			;[Block]
			DrawImage3D	(GUI_Windows[2],0,GraphicsHeight()/3)
			DrawImage3D(GUI_Windows[16], GraphicsWidth()/2-128,-GraphicsHeight()/2+64)
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
	
	Local WIPString$ = tmx+" | "+tmy
	Text3D (Text_Font[7], 50-GwBy2,-300,key,1)
	
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

Music_Volume#=.2

;UserDataLoad(State_Character_To_Load)

Music_Update()

Lighting_Initialize()

CreateListener(WorldCamera, 0.0025, 8, 20000)



CreateMainDust()
;UI Loading Code
; variables 
Global RollSpeed#   = 2.0 
Global TurnSpeed#	= 3.0
Global MaxRoll#      = 45.0 
Global Speed#      = 500 

Global nearestdist#,nearestscale#,nearestname%,nearestglowscale# 

;Player_Weapon_Array = CreatePivot()



;PlayerSwitchShip(Character_Value_Ship)
;GetPlayerShipValues(Character_Value_Ship)

Story_Pivot = CreatePivot()

;[Block]

CameraZoom WorldCamera,1
mzoom#=1

Global WorldCameraPivot = CreatePivot(pvCamera)
PositionEntity WorldCamera, EntityX(pvCamera), EntityY(pvCamera)+Yoffset, EntityZ(pvCamera)-Zoffset, False
PositionEntity WorldCameraPivot, EntityX(pvCamera), EntityY(pvCamera)+Yoffset, EntityZ(pvCamera), False

Global OldMS, NewMS, TDFMS, MaxMS, MinMS=10000
Global DebugInfo_Enabled

;!Todo: Find out why this doesn't call itself earlier.
;[End Block]

World_Generate(2, 0,0,0)

;!ToDo: Temporary
Local multiplier#

HidePointer

;set camera:
Yoffset=55
PositionEntity WorldCamera, EntityX(pvCamera), EntityY(pvCamera)+Yoffset, EntityZ(WorldCamera), False

HUD = 1

Repeat
	Local ms_Performance = MilliSecs()
	
	;----------------------------------------------------------------
	;[Block] Update
	;----------------------------------------------------------------
	Local ms_Performance_Update = MilliSecs()
	
	;[Block] Networking
	Local ms_Performance_Update_Network = MilliSecs()
	
	
	
	; Measure Time
	Performance_Update_Network = MilliSecs() - ms_Performance_Update_Network
	;[End Block]
	
	
	
	;[Block] Players
	Local ms_Performance_Update_Players = MilliSecs()
	
	; Update own Gunarray
	PositionEntity Player_Weapon_Array, EntityX(pvShip),EntityY(pvShip), EntityZ(pvShip)
	PointEntity Player_Weapon_Array, Weapon_Target_Cube
	RotateEntity Player_Weapon_Array, EntityPitch(Player_Weapon_Array),EntityYaw(Player_Weapon_Array), EntityRoll(pvShip)
	
	; Movement: Strafing
	MoveEntity pvShip,ShipStrafeX#,ShipStrafeY#,Player_Value_Speed_Current#
	TurnEntity pvShip,0,0,ShipRollZ#
	
	; Targeting
	PositionEntity Weapon_Target_Cube,EntityX(pvShip),EntityY(pvShip),EntityZ(pvShip)
	RotateEntity Weapon_Target_Cube,EntityPitch(pvShip),EntityYaw(pvShip),EntityRoll(pvShip)	
	MoveEntity Weapon_Target_Cube,(MouseX()-GraphicsWidth()/2)*1*2,(MouseY()-GraphicsHeight()/2)*-1*2,Weapon_Maxdistance
	
	; Measure Time
	Performance_Update_Players = MilliSecs() - ms_Performance_Update_Players
	;[End Block]
	
	;[Block] Projectiles
	Local ms_Performance_Update_Projectiles = MilliSecs()
	Performance_Update_Projectiles = MilliSecs() - ms_Performance_Update_Projectiles
	;[End Block]
	
	;[Block] Input
	Local ms_Performance_Update_Input = MilliSecs()
	
	InputEx_Update()
	BU_Helper_Window_LockPointerAuto()
	
	Local mSpeedX#, mSpeedY#
	mSpeedX = MouseXSpeed()
	mSpeedY = MouseYSpeed()
	
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
			
			Local TurnXTotal#=t1#*TurnSpeed#
			Local TurnYTotal#=t2#*TurnSpeed#
			
			TurnEntity pvShip,t1#*Player_Value_Inertia_Base#,t2#*Player_Value_Inertia_Base#,roll*TurnSpeed#
			
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
			
			Local RollY#=(MouseX()-(GraphicsWidth()/2))/18*-1
			Local RollX#=(MouseY()-(GraphicsHeight()/2))/18
			RotateEntity eShipBody,RollX#,EntityYaw(eShipBody),RollY#
			RotateEntity tShip,RollX#,EntityYaw(eShipBody),RollY#;*Player_Value_Inertia_Base;SpeedX#
			
			RotateEntity pvCamera, fCameraRotation[0], fCameraRotation[1], 0
			;[End Block]			
			
			;[End Block]
		Case MODE_CAMERA
			;[Block] Kamerasteuerung
			
			PositionEntity WorldCamera, EntityX(pvCamera), EntityY(pvCamera)+Yoffset, EntityZ(WorldCamera), False
			
			If MouseDown(2)<>1 And MouseDown(1)<>1 Then
;				PointEntity(WorldCamera,WorldCameraPivot)
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
			
			
			
			GUI_CZOOM_IN#=GUI_CZOOM_IN#+MouseZSpeed()
			MoveEntity WorldCamera,0,0,GUI_CZOOM_IN#
			GUI_CZOOM_IN#=GUI_CZOOM_IN#*0.9
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
			
			;[End Block]
	End Select
	
	HandleInput()
	
	Performance_Update_Input = MilliSecs() - ms_Performance_Update_Input
	;[End Block]
	
	;[Block] Build User Interface
	Local ms_Performance_Update_UI = MilliSecs()
	
	;[Block] Options Menu
	If Options = 1 Then
		
		eCameraMode = MODE_CAMERA
		
		HUD = 0
		
;		DrawImage3D(GUI_Interface[0],MouseX()-(GraphicsWidth()/2),-MouseY()+(GraphicsHeight()/2))
		
		If Options_Nested = 0 Then
			
			;Draw Main Options Window
			DrawImage3D(GUI_Windows[0],0,0)
			
			If MouseX()>GraphicsWidth()/2-85 And MouseX()<GraphicsWidth()/2+85 Then
				
				;Graphics Settings
				If MouseY()>GraphicsHeight()/2-87 And MouseY()<GraphicsHeight()/2-55 Then
;					DrawImage3D(GUI_Buttons[2],0,0)
					FlushMouse()
					If MouseDown(1) Then
;						If ChannelPlaying(Channel_UI) = False Then Channel_UI=PlaySound(Sound_UI[4])
						Options_Nested=1
					EndIf
				EndIf
				;[Block]
;				;Sound Settings
;				If MouseY()>GraphicsHeight()/2-52 And MouseY()<GraphicsHeight()/2-22 Then
;					DrawImage3D(GUI_Buttons[3],0,0)
;					FlushMouse()
;					If MouseDown(1) Then
;						If ChannelPlaying(Channel_UI) = False Then Channel_UI=PlaySound(Sound_UI[4])
;;						Game_End=1
;					EndIf
;				EndIf
;				
;				;Gameplay Settings
;				If MouseY()>GraphicsHeight()/2-17 And MouseY()<GraphicsHeight()/2+15 Then
;					DrawImage3D(GUI_Buttons[4],0,0)
;					FlushMouse()
;					If MouseDown(1) Then
;						If ChannelPlaying(Channel_UI) = False Then Channel_UI=PlaySound(Sound_UI[4])
;;						Game_End=1
;					EndIf
;				EndIf
;				
;				
;				;Log to Main Menu
;				If MouseY()>GraphicsHeight()/2+55 And MouseY()<GraphicsHeight()/2+87 Then
;					DrawImage3D(GUI_Buttons[1],0,0)
;					FlushMouse()
;					If MouseDown(1) Then
;						If ChannelPlaying(Channel_UI) = False Then Channel_UI=PlaySound(Sound_UI[4])
;;						Game_End=1
;					EndIf
;				EndIf
				;[End Block]
				;Quit
				If MouseY()>GraphicsHeight()/2+90 And MouseY()<GraphicsHeight()/2+122 Then
;					DrawImage3D(GUI_Buttons[0],0,0)
					FlushMouse()
					If MouseDown(1) Then
;						If ChannelPlaying(Channel_UI) = False Then Channel_UI=PlaySound(Sound_UI[4])
						Game_End=1
					EndIf
				EndIf
				
			EndIf
		ElseIf Options_Nested=1
			;Graphics Settings
			DrawImage3D(GUI_Windows[1],0,0)
			Text3D(Text_Font[9],235,73,(PostProcess_GR_Strength#*10)+"x",2)
			Text3D(Text_Font[9],235,51,(PostProcess_GR_RayLength#*10)+"x",2)
			Text3D(Text_Font[9],235,29,(PostProcess_GR_Layers)+"x",2)
			Text3D(Text_Font[9],235,7,(PostProcess_GR_Smoothing)+"x",2)
			
			If MouseY()>GraphicsHeight()/2-164 And MouseY()<GraphicsHeight()/2-137
				
				If MouseX()>GraphicsWidth()/2-253 And MouseX()<GraphicsWidth()/2-131
					If MouseDown(1)
						Switch_Graphics_Preset = 1
					EndIf
				EndIf
				
				If MouseX()>GraphicsWidth()/2-126 And MouseX()<GraphicsWidth()/2-1
					If MouseDown(1)
						Switch_Graphics_Preset = 2
					EndIf
				EndIf
				
				If MouseX()>GraphicsWidth()/2+1 And MouseX()<GraphicsWidth()/2+126
					If MouseDown(1)
						Switch_Graphics_Preset = 3
					EndIf
				EndIf
				
				If MouseX()>GraphicsWidth()/2+131 And MouseX()<GraphicsWidth()/2+253
					If MouseDown(1)
						Switch_Graphics_Preset = 4
					EndIf
				EndIf
				
			EndIf
			
			If Switch_Graphics_Preset = 1 Or Switch_Graphics_Preset > 4 Then Switch_Graphics_Preset = 1
			
			If MouseX()>GraphicsWidth()/2+231 And MouseX()<GraphicsWidth()/2+252 Then
				
				If MouseY()>GraphicsHeight()/2-222 And MouseY()<GraphicsHeight()/2-201 Then
					If MouseDown(1) Then
;						If ChannelPlaying(Channel_UI) = False Then Channel_UI=PlaySound(Sound_UI[5])
						Options_Nested=0
					EndIf
				EndIf
			EndIf
			
		ElseIf Options_Nested=2
			;Sound Settings
			
		ElseIf Options_Nested=3 
			;Gameplay Settings
			
		EndIf
	EndIf
	;[End Block]
	
	Timer_HitRegister= Timer_HitRegister - 1
	
	;[Block] Player Health Checkup
	Select Player_State_Alive
		Case 0			; Player Alive and well
			EntityAlpha eShipBody,1
			If Player_Value_Armor_Current < 1 Then
				Explosion_Create(EntityX(pvShip),EntityY(pvShip),EntityZ(pvShip),1,1,1)
				HUD=0
				Player_State_Alive=1
			EndIf
		Case 1			; Player Just died. Disable all Controls, Display Gameover and allow selection
			ShipStrafeX#=0
			ShipStrafeY#=0
			Player_Value_Speed_Current#=0
			ShipRollZ=0
			eCameraMode = MODE_CAMERA
			HUD=0
			EntityAlpha eShipBody,0
			
			DrawImage3D(GUI_Windows[5],0,0)
;			DrawImage3D(GUI_Interface[0],MouseX()-(GraphicsWidth()/2),-MouseY()+(GraphicsHeight()/2))
			If MouseX()>GraphicsWidth()/2-85 And MouseX()<GraphicsWidth()/2+85 Then
				
				;Quit Game
				If MouseY()>GraphicsHeight()/2+55 And MouseY()<GraphicsHeight()/2+87 Then
;					DrawImage3D(GUI_Buttons[5],0,0)
					FlushMouse()
					If MouseDown(1) Then
;						If ChannelPlaying(Channel_UI) = False Then Channel_UI=PlaySound(Sound_UI[4])
						Game_End=1
					EndIf
				EndIf
				
				;Quit
				If MouseY()>GraphicsHeight()/2+90 And MouseY()<GraphicsHeight()/2+122 Then
;					DrawImage3D(GUI_Buttons[6],0,0)
					FlushMouse()
					If MouseDown(1) Then
;						If ChannelPlaying(Channel_UI) = False Then Channel_UI=PlaySound(Sound_UI[4])
						Player_State_Alive=2
					EndIf
				EndIf
				
			EndIf
		Case 2			; 
			Select Character_Value_Faction
				Case Faction_Terran
					World_Clear()
					World_Generate(7,0,0,0)
				Case Faction_Sirian
					World_Clear()
					World_Generate(3,0,0,0)
			End Select
			GetPlayerShipValues(1)
			Yoffset=55
			PositionEntity WorldCamera, EntityX(pvCamera), EntityY(pvCamera)+Yoffset, EntityZ(WorldCamera), False
			Player_State_Alive=0
			EntityAlpha eShipBody,0
			HUD = 1
	End Select
	
	;[End Block]
	
	;[Block] Scanning Extension
	If Ship_Function_Scanner = 1 Then
		;Create new, more performance oriented Scanner
	EndIf
	;[End Block]
	
	If HUD=1 And MAPHUD = 0 Then
		
		;[Block]  Weapon DIsplay
		
		;[End Block]
		
		If eCameraMode = MODE_CAMERA And TimerRemind > 0
			TimerRemind = TimerRemind - 1
			Text3D(Text_Font[9],0,D3DOD+150,"- MOUSE VIEW MODE -",1,0,Sin(MilliSecs()))
			Text3D(Text_Font[10],0,D3DOD+135,"Press Space to Control your Ship",1)
		EndIf
		
		czonex=Floor(EntityX(pvShip)/2000)
		czoney=Floor(EntityY(pvShip)/2000)
		czonez=Floor(EntityZ(pvShip)/2000)
		
		;map
		Local MapOX =GraphicsWidth()/2-(16+128)
		Local MapOZ =GraphicsHeight()/2-(16+128)
		
;		Color 220,220,220
;		Text3D(Text_Font[10],0, D3DOD+70 , ConvertNumbers(Truspeed)+" m/s", True)
		
		;[Block] Chat
		ChatBase=14
		
		For Add.ChatMSGS = Each ChatMSGS
			ChatBase=ChatBase-1
			Local TchatX=D3DOL+3
			Local TchatY=((GraphicsHeight()/2)*-1)+(ChatBase*20)
			;Check Chat for Stuff
			Local Postname$=Left(add\msg$,10)
			Local PostSRV$=Left(add\msg$,3)
			Local NameFilter=Len(Character_Value_Name$)
			Local NameMSG$=Mid(add\MSG$,NameFilter)
			Text3D(Text_Font[1],TchatX,TchatY,add\Msg$)
			If ChatBase=1 Then Exit
		Next
		
		;[End Block]
		
		;[Block] Map
		
		DrawImage3D(GUI_Windows[4],GraphicsWidth()/2-16-128,GraphicsHeight()/2-(16+128))
		
		;[End Block]
		
		;Information: Environment
		
		If eCameraMode=MODE_SHIP Then
;			DrawImage3D(GUI_Interface[1],MouseX()-(GraphicsWidth()/2),-MouseY()+(GraphicsHeight()/2))
			CameraProject WorldCamera,EntityX(Weapon_Target_Cube),EntityY(Weapon_Target_Cube),EntityZ(Weapon_Target_Cube)
;			DrawImage3D(GUI_Interface[2],ProjectedX()-(GraphicsWidth()/2),-ProjectedY()+(GraphicsHeight()/2))
			If Timer_HitRegister>0 Then
				For Hit_Amount= 1 To Timer_HitRegister
;					DrawImage3D(GUI_Interface[15],ProjectedX()-(GraphicsWidth()/2)+Hit_Amount,-ProjectedY()+(GraphicsHeight()/2),0,180)
;					DrawImage3D(GUI_Interface[15],ProjectedX()-(GraphicsWidth()/2)-Hit_Amount,-ProjectedY()+(GraphicsHeight()/2),0,0)
				Next
			EndIf
		Else
;			DrawImage3D(GUI_Interface[0],MouseX()-(GraphicsWidth()/2),-MouseY()+(GraphicsHeight()/2))
		EndIf
		
		gatelimiter=gatelimiter+1
		
		;[Block] ARC Hud ;NEW HUD IN THE WORKS
;		Text3D(Text_Font[1],0,GraphicsHeight()/2-160,currloc$,True)
		
;		AddChat(Player_Value_Speed_Percentage+"(  "+(Player_Value_Speed_Maximum/100)+" 1%, "+Player_Value_Speed_Current+" current speed",255,255,255,"")
		
		Text3D(Text_Font[19], -256, -140,Player_Value_Armor_Percentage+"%",0)
		Text3D(Text_Font[18], 256, -140,Player_Value_Shield_Percentage+"%",2)
		Text3D(Text_Font[18], 0, -140,Player_Value_Energy_Percentage+"%",2)
		
		Text3D(Text_Font[9], 0, -400,Floor(Player_Value_Speed_Current*5)+"m/sec",1,0)
		
		
		;[End Block]
		
	EndIf
	
	;[Block] Debug Information
	If DebugInfo_Enabled=1 Then
		Local Screenmode$
		Select TGxMODE
			Case 1
				Screenmode$="Fullscreen"
			Case 2
				Screenmode$="Adapted Window Mode"
		End Select
		
		Cores = GetEnv("NUMBER_OF_PROCESSORS")
		Local CoreDesc$
		Select Cores
			Case 1
				CoreDesc$="Single-Core Machine"
			Case 2
				CoreDesc$="Dual-Core Machine"
			Case 3
				CoreDesc$="Defective Phenom. Unlock it, thank me later."
			Case 4
				CoreDesc$="Quadcore or Dual+HT Machine"
			Case 6,5 ; Turbo Mode on FX
				CoreDesc$="Hexacore Machine"
			Case 8,7 ; Turbo Mode on FX
				CoreDesc$="Octacore or i7 Quadcore+HT Machine"
			Default
				CoreDesc$="THIS IS A NASA COMPUTER. I BOW TO THEE!"
		End Select
		
		Text3D(Text_Font[24],D3DOL+10,D3DOU-10,"DEBUG INFORMATION")
		Text3D(Text_Font[24],D3DOL+10,D3DOU-22,"Resolution: "):Text3D(Text_Font[24],D3DOL+120,D3DOU-22,SelectedWidth+"x"+SelectedHeight+"x16 ("+Screenmode$+")")
		Text3D(Text_Font[24],D3DOL+10,D3DOU-34,"Frame Time: "):Text3D(Text_Font[24],D3DOL+120,D3DOU-34,TDFMS+" ms (min/max: "+MinMS+" ms/ "+MaxMS+" ms)")
		Text3D(Text_Font[24],D3DOL+10,D3DOU-46,"Drawlayers: "):Text3D(Text_Font[24],D3DOL+120,D3DOU-46,TrisRendered()+" (Godray Renderpass strength: "+GODRAY_LAYERS%+"x)")
		Text3D(Text_Font[24],D3DOL+10,D3DOU-58,"System Info: "):Text3D(Text_Font[24],D3DOL+120,D3DOU-58,GetEnv("PROCESSOR_Identifier")+" ("+GetEnv("PROCESSOR_ARCHITECTURE")+")")
		Text3D(Text_Font[24],D3DOL+120,D3DOU-70,CoreDesc$)
		
		Text3D(Text_Font[24], D3DOL+10,  D3DOU-100-(12*-1), "Frame")
		Text3D(Text_Font[24], D3DOL+100, D3DOU-100-(12*-1), Performance)
		
		Text3D(Text_Font[24], D3DOL+10,  D3DOU-100-(12*00), "Update")
		Text3D(Text_Font[24], D3DOL+100, D3DOU-100-(12*00), Performance_Update)
		Text3D(Text_Font[24], D3DOL+20,  D3DOU-100-(12*01), "Player")
		Text3D(Text_Font[24], D3DOL+100, D3DOU-100-(12*01), Performance_Update_Players)
		Text3D(Text_Font[24], D3DOL+20,  D3DOU-100-(12*02), "Projectiles")
		Text3D(Text_Font[24], D3DOL+100, D3DOU-100-(12*02), Performance_Update_Projectiles)
		Text3D(Text_Font[24], D3DOL+20,  D3DOU-100-(12*03), "Input")
		Text3D(Text_Font[24], D3DOL+100, D3DOU-100-(12*03), Performance_Update_Input)
		Text3D(Text_Font[24], D3DOL+20,  D3DOU-100-(12*04), "User Interface")
		Text3D(Text_Font[24], D3DOL+100, D3DOU-100-(12*04), Performance_Update_UI)
		Text3D(Text_Font[24], D3DOL+20,  D3DOU-100-(12*05), "Unknown")
		Text3D(Text_Font[24], D3DOL+100, D3DOU-100-(12*05), Performance_RestUpdate)
		Text3D(Text_Font[24], D3DOL+20,  D3DOU-100-(12*06), "Physics")
		Text3D(Text_Font[24], D3DOL+100, D3DOU-100-(12*06), Performance_Physics)
		
		
		Text3D(Text_Font[24], D3DOL+10,  D3DOU-100-(12*10), "Render")
		Text3D(Text_Font[24], D3DOL+100, D3DOU-100-(12*10), Performance_Render)
		Text3D(Text_Font[24], D3DOL+30,  D3DOU-100-(12*11), "3D")
		Text3D(Text_Font[24], D3DOL+100, D3DOU-100-(12*11), Performance_Render_3D + "(Tris: "+Performance_Render_3D_Tris+")")
		Text3D(Text_Font[24], D3DOL+30,  D3DOU-100-(12*12), "PostProcess")
		Text3D(Text_Font[24], D3DOL+100, D3DOU-100-(12*12), Performance_Render_PostProcess + "(" + Effect_Performance_Ms + "/" + Effect_Performance_WorldMs + "/" + Effect_Performance_GodrayMs + "/" + Effect_Performance_BloomMs + "/" + Effect_Performance_FinalMs + ")")
		Text3D(Text_Font[24], D3DOL+30,  D3DOU-100-(12*13), "User Interface")
		Text3D(Text_Font[24], D3DOL+100, D3DOU-100-(12*13), Performance_Render_UI + "(Tris: "+Performance_Render_UI_Tris+")")
		Text3D(Text_Font[24], D3DOL+20,  D3DOU-100-(12*14), "Flip")
		Text3D(Text_Font[24], D3DOL+100, D3DOU-100-(12*14), Performance_Flip)
		
		Text3D(Text_Font[24], D3DOL+20,  D3DOU-100-(12*20), "Wait")
		Text3D(Text_Font[24], D3DOL+100, D3DOU-100-(12*20), Performance_Wait)
		
		Text3D(Text_Font[24], D3DOL+20,  D3DOU-100-(12*22), "MX "+MX)
		Text3D(Text_Font[24], D3DOL+20,  D3DOU-100-(12*23), "MY "+MY)
		
	EndIf
	;[End Block]
	
	Performance_Update_UI = MilliSecs() - ms_Performance_Update_UI
	;[End Block]
	
	;[Block] Unsorted Updates
	Local ms_Performance_RestUpdate = MilliSecs()
	
	If (MilliSecs() - fpsTimer > 1000)
		fpsTimer = MilliSecs()
		Frames = fpsTicks
		fpsTicks = 0
	Else
		fpsTicks = fpsTicks + 1
	EndIf
	
	Util_Timer
	
	mPosition[0] = MouseX():mPosition[1] = MouseY()
	mDiffX = mPosition[0] - mOrigin[0]:mDiffY = mPosition[1] - mOrigin[1]
	
	PlayerX=EntityX(WorldCamera,True)
	PlayerY=EntityY(WorldCamera,True)
	PlayerZ=EntityZ(WorldCamera,True)
	
	PositionEntity ShipPosXYZ, PlayerX, PlayerY, PlayerZ,True
	
	PositionEntity Object_Environment[2],EntityX(ShipPosXYZ), EntityY(ShipPosXYZ), EntityZ(ShipPosXYZ)
	PositionEntity Object_Environment[0],EntityX(ShipPosXYZ), EntityY(ShipPosXYZ), EntityZ(ShipPosXYZ)
	PositionEntity Zone_Dust_Base,EntityX(ShipPosXYZ),EntityY(ShipPosXYZ),EntityZ(ShipPosXYZ)
	
	If Lim1<601 Then Lim1 = Lim1+1
	
	If Recounter<18001 Then Recounter = Recounter + 1
	
	ChannelVolume MUSCHAN,0.2
	
	If Player_Value_Speed_Current#<0.1 And KeyDown(17)=False And KeyDown(45)=False And MouseZSpeed()=False Then
		Player_Value_Speed_Current#=Player_Value_Speed_Current#/SHipInertia#
	EndIf
	
	D3DMouseX=(MouseX()-GraphicsWidth()/2)
	D3DMouseY=(MouseY()-GraphicsHeight()/2)
	
	HLC#=Player_Value_Armor_Current
	HLM#=Player_Value_Armor_Maximum
	Limiter#=( HLC# / (HLM# / 100 ))/100
	
	Player_Value_Energy_Current=Player_Value_Energy_Current+1
	
	If Player_Value_Energy_Current>Player_Value_Energy_Maximum Then Player_Value_Energy_Current=Player_Value_Energy_Maximum
	If Player_Value_Energy_Current<0 Then Player_Value_Energy_Current=0
	
	If Player_Value_Speed_Current#>Player_Value_Speed_Maximum*Limiter# And Ship_Function_Boost=0 And KeyDown(15)=False Then 
		Player_Value_Speed_Current#=Player_Value_Speed_Maximum*Limiter#
	EndIf
	
	TCNT=TCNT+1
	
	If Player_Value_Shield_Current>Player_Value_Shield_Maximum Then Player_Value_Shield_Current=Player_Value_Shield_Maximum
	If Player_Value_Armor_Current>Player_Value_Armor_Maximum Then Player_Value_Armor_Current=Player_Value_Armor_Maximum
	
	Ship_Shield_Reload_Tick_Timer=Ship_Shield_Reload_Tick_Timer+1
	If Ship_Shield_Reload_Tick_Timer>Ship_Shield_Reload_Tick Then
		Player_Value_Shield_Current=Player_Value_Shield_Current+Ship_Shield_Reload_Amount
		Ship_Shield_Reload_Tick_Timer=0
	EndIf
	
	;[Block] World
	NOTE_DURATION=NOTE_DURATION-1
	If NOTE_DURATION<1 Then
		NOTE_ACTIVE=0
	EndIf
	
;	;Staggered Updates
	
	
	
	
	;[End Block]
	
	Performance_RestUpdate = MilliSecs() - ms_Performance_RestUpdate
	;[End Block]
	
	
	;[Block] Physics
	Local ms_Performance_Physics = MilliSecs()
	
	UpdateWorld()
	
	
	
	Performance_Physics = MilliSecs() - ms_Performance_Physics
	;[End Block]
	
	Performance_Update = MilliSecs() - ms_Performance_Update
	;[End Block]
	
	;----------------------------------------------------------------
	;[Block] Render
	;----------------------------------------------------------------
	Local ms_Performance_Render = MilliSecs()
	
	;[Block] 3D 
	Local ms_Performance_Render_3D = MilliSecs()
	
	If WorldClock$<>CurrentTime() Then
		WorldTimers_Update()
		WorldClock$=CurrentTime()
	EndIf
	
;	VirtualScene_Show(Scene):ShowEntity WorldCamera
	
	Worldmap_Display()
	Update_Item()
	Buff_Update()
	Storyline_Update()
	GenAudio_Update()
	UpdateBelt()
	UpdateLevels()
	Explosion_Update()
;	Tutorial_Update()
	UpdateFastTravel()
	Planet_Update(WorldCamera, 0, 0, 0)
	Special_Update()
	UpdateMapPoint()
	DST_Update()
	UpdateShot()
	UpdateShockwave()
	UpdateStation
	UpdateGraphics()
	Scrapfield_Update
	Respawn_Update()
	FactionZone_Update()
	UpdateGates()
	Battlefield_Update()
	RaceTrack_Update()
	
	RenderWorld
;	VirtualScene_Hide(Scene):HideEntity WorldCamera
	
	Performance_Render_3D_Tris = TrisRendered()
	Performance_Render_3D = MilliSecs() - ms_Performance_Render_3D
	
	;[End Block]
	
	;[Block] Post Process
	Local ms_Performance_Render_PostProcess = MilliSecs()
	
	Performance_Render_PostProcess = MilliSecs() - ms_Performance_Render_PostProcess
	;[End Block]
	
	;[Block] UI
	Local ms_Performance_Render_UI = MilliSecs()
	ShowEntity GDrawPivot:ShowEntity CameraUI
	RenderWorld:Clear3D()
	HideEntity GDrawPivot:HideEntity CameraUI
	Performance_Render_UI_Tris = TrisRendered()
	Performance_Render_UI = MilliSecs() - ms_Performance_Render_UI
	;[End Block]
	
	;[Block] Flip to FrontBuffer
	Local ms_Performance_Flip = MilliSecs()
	Flip 0:Cls
	Performance_Flip = MilliSecs() - ms_Performance_Flip
	;[End Block]
	
	;[Block] Wait for Timer
	Local ms_Performance_Wait = MilliSecs()
	WaitTimer(RenderTimer)
	Performance_Wait = MilliSecs() - ms_Performance_Wait
	;[End Block]
	
	; Measure Total Render Time
	Performance_Render = MilliSecs() - ms_Performance_Render
	;[End Block]
	
	Performance = MilliSecs() - ms_Performance
	
	Clear3D()
	
Until Game_End=1
UserDataSave(Character_Profile_Loaded)

ClearWorld()
End
;~IDEal Editor Parameters:
;~F#B7#185#240#249#252#26C#279#3FE#4A5#4D4
;~C#Blitz3D