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

Prepare_Camera()

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

;-- Initialize AssetManager
AssetManager_Initialize()

;-- Loading Screen
Local Loading_State = LOADING_STATE_LOADING, Loading_State_Next, Loading_State_JustSwitched = True

Local Ass.Asset

; Retrieve a random Quote for this Loading Screen
Local Loading_Quote$ = "Preloading Sirius: FRONTIERS. This may take some time."

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
				
				;[Block] Meshes and Textures --------------------------------------------------
				
				
				;[Block] Ships
				Local LoadOrder = OpenFile("Assets\3D\Ships\LoadShips.lof")
				Repeat
					Local LoadData$ = ReadLine(LoadOrder)
					LoadMeshAsset("Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Mesh.3DS", SceneDataRoot)
					LoadTextureAsset("Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Color.jpg",1+2)
					LoadTextureAsset("Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Glow.jpg",1+2)
				Until Eof(LoadOrder)
				CloseFile LoadOrder
				;[End Block]
				
				;[Block] Stations
				LoadOrder = OpenFile("Assets\3D\Stations\LoadStations.lof")
				Repeat
					LoadData$ = ReadLine(LoadOrder)
					LoadMeshAsset("Assets\3D\Stations\"+LoadData$+"\"+LoadData$+"_Mesh.3DS", SceneDataRoot)
					LoadTextureAsset("Assets\3D\Stations\"+LoadData$+"\"+LoadData$+"_Color.jpg",1+2)
					LoadTextureAsset("Assets\3D\Stations\"+LoadData$+"\"+LoadData$+"_Glow.jpg",1+2)
				Until Eof(LoadOrder)
				CloseFile LoadOrder
				;[End Block]
				
				;[Block] Skybox
				LoadOrder = OpenFile("Assets\3D\Skybox\LoadSkybox.lof")
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
				
				;[Block] Fonts
				LoadOrder = OpenFile("Assets\2D\Fonts\LoadFonts.lof")
				Repeat
					LoadData$ = ReadLine(LoadOrder)
					LoadTextureAsset("Assets\2D\Fonts\"+LoadData$+".jpg", 2)
				Until Eof(LoadOrder)
				CloseFile LoadOrder
				;[End Block]
				
				;[Block] Standard Windows Font
				LoadFontAsset("Arial", 14)
				LoadFontAsset("Arial", 20)
				LoadFontAsset("Arial", 24)
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
	
	Color 0, 0, 0:Rect 0, Gh - 45, Gw, 45, 1 ; Bottom Bar
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
				Color ls_p * 51, ls_p * 51, ls_p * 51
				Line 0, ls_loading_barY - ls_n, Gw, ls_loading_barY - ls_n
				
				; GFX: Error
				Color 204 + ls_p * 51, ls_p * 51, ls_p * 51
				Line GwBy2 * (1.0 - ls_resources_error_prc), ls_loading_barY - ls_n, GwBy2 + (GwBy2 * ls_resources_error_prc), ls_loading_barY - ls_n
				
				; GFX: Loading
				Color 204 + ls_p * 51, 102 + ls_p * 51, ls_p * 51
				Line GwBy2 * (1.0 - ls_resources_loading_prc), ls_loading_barY - ls_n, GwBy2 + (GwBy2 * ls_resources_loading_prc), ls_loading_barY - ls_n
				
				; GFX: Complete
				Color ls_p * 51, 204 + ls_p * 51, ls_p * 51
				Line GwBy2 * (1.0 - ls_resources_loaded_prc), ls_loading_barY - ls_n, GwBy2 + (GwBy2 * ls_resources_loaded_prc), ls_loading_barY - ls_n
			Next
			
			Color 0, 0, 0
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

;----------------------------------------------------------------
;! Old Startup
;----------------------------------------------------------------

; 3D: Camera
Const MODE_CAMERA = 0
Const MODE_SHIP  = 1

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

VirtualScene_Register(Scene, pvShip)
VirtualScene_Register(Scene, pvShipTarget)
VirtualScene_Register(Scene, pvtPoint)

; Create Local Player
Global Player_Location.Location = Location_Create(0, 0, 0)

; Initialization Code (Old)
 ;!ToDo: AssetManager

;[Block] Old
SHipInertia#=1.085
ShipPosXYZ = CreatePivot():VirtualScene_Register(Scene, ShipPosXYZ)
Weapon_Target_Cube = LoadSprite("Content\GFX\Interface\Icons\interface_mouse_aim.png",2)
VirtualScene_Register(Scene, Weapon_Target_Cube)

;Extensions
Mesh_Extension[1]=CreatePlane(1)
Global ECLIPTIC_ALPHA#
EntityTexture Mesh_Extension[1], Text_Effects[2]
EntityFX Mesh_Extension[1],1+2+4+8+16
EntityAlpha Mesh_Extension[1],0.6
VirtualScene_Register(Scene, Mesh_Extension[1])

Global FastTravelSpot=CreatePivot()
VirtualScene_Register(Scene,FastTravelSpot)
Global Travel_To_LastSave=0

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


GetWeaponValues(1)

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

Local Character_To_Delete
Local File_Character_Profiles
Local File_Character_ProfileA
Local File_CharacterA_Exists=0

ShowEntity CameraUI

If FileType(UserData+"\Profiles") <> 2 Then CreateDir(UserData+"\Profiles")

If FileType(UserData+"\Profiles\CharDataA.txt") <> 1 Then
	File_Character_ProfileA=WriteFile(UserData+"\Profiles\CharDataA.txt")
	WriteLine File_Character_ProfileA,0
	CloseFile File_Character_ProfileA
Else 
	File_CharacterA_Exists=1
EndIf

;!TODO: Read existing savefiles and make them be recognized as "complete" or "incomplete" when failsafing.

If FileType(UserData+"\Profiles\MainData.txt") <> 1 Then
	File_Character_Profiles=WriteFile(UserData+"\Profiles\MainData.txt")
	WriteLine File_Character_Profiles,File_CharacterA_Exists
	CloseFile File_Character_Profiles
EndIf

Type Character_Save_Data
	Field CreationID, Name$
	Field CreationDate$
	Field Gender, Race
	Field Faction, Job, TutorialStep
	Field LX, LY, LZ, LS
	Field Character_Value_XP
	Field Character_Value_Level
	Field Wallet, Ship, Weapon
	Field Inventory
End Type

;Start Parsing Character Data
File_Character_Profiles = ReadFile(UserData+"\Profiles\MainData.txt") 
Local File_CharacterA_Exist = ReadLine(File_Character_Profiles)
CloseFile File_Character_Profiles

If File_CharacterA_Exist<>0 Then
	File_Character_ProfileA = ReadFile(UserData+"\Profiles\CharDataA.txt")
	File.Character_Save_Data 	= New Character_Save_Data
	File\CreationID    			= ReadLine(File_Character_ProfileA)
	File\Name$	       			= ReadLine(File_Character_ProfileA)
	File\CreationDate$ 			= ReadLine(File_Character_ProfileA)
	File\Faction	   			= ReadLine(File_Character_ProfileA)
	File\TutorialStep		    = ReadLine(File_Character_ProfileA)
	File\LX					    = ReadLine(File_Character_ProfileA)
	File\LY			   			= ReadLine(File_Character_ProfileA)
	File\LZ		    		    = ReadLine(File_Character_ProfileA)
	File\LS		       			= ReadLine(File_Character_ProfileA)
	File\Character_Value_XP     = ReadLine(File_Character_ProfileA)
	File\Character_Value_Level  = ReadLine(File_Character_ProfileA)
	File\Wallet		   			= ReadLine(File_Character_ProfileA)
	File\Ship	       			= ReadLine(File_Character_ProfileA)
	File\Weapon		   			= ReadLine(File_Character_ProfileA)
	File\Inventory     			= ReadLine(File_Character_ProfileA)
	CloseFile File_Character_ProfileA
EndIf

Local Timer_Character_Selection = CreateTimer(60)
Global State_Character_Selection = 1
Global State_Menu_Subcontext = 1

Global Character_NewName$

Cls

Util_InitTimer()

Type Credits
	Field TextLine$, Y
End Type
Local YText = -GraphicsHeight()/2
Local Creditsfile = OpenFile("Content\credits.txt")
While Not Eof(Creditsfile)
	TLine.Credits = New Credits
	Tline\Textline$ = ReadLine$(Creditsfile)
	TLine\Y = YText
	YText = YText - 20
Wend
CloseFile Creditsfile

;[Block] Character Loading
Channel_Music = PlaySound(Music_ID[0])
ChannelVolume Channel_Music,0.5
Repeat
	DrawImage3D(GUI_MainMenu_LImages[0],0,0)
	
	DrawImage3D(GUI_MainMenu_SImages[7],0,D3DOU-158,0,0,1.5,0)
	DrawImage3D(GUI_MainMenu_SImages[3],D3DOR-64,D3DOD+64,0,0,0.5)
	
	Local Toplength=(GraphicsWidth()/128)+1
	
	Select State_Character_Selection
		Case 1
			
			;[Block]
			
			Character_Completion_Toggle=0
			
			DrawImage3D(GUI_MainMenu_Button[1], 0,146) ;Continue
			Select File_CharacterA_Exist
				Case 1
					DrawImage3D(GUI_MainMenu_Button[2], 0,100) ;Load Game
				Case 0
					DrawImage3D(GUI_MainMenu_Button[13], 0,100) ;No Savegame Exists
			End Select
			DrawImage3D(GUI_MainMenu_Button[5], 0,54) ;
			DrawImage3D(GUI_MainMenu_Button[12], 0,8)  ;Settings
			DrawImage3D(GUI_MainMenu_Button[3], 0,-38)   ;Credits
			DrawImage3D(GUI_MainMenu_Button[4], 0,-84) ;Discord
			DrawImage3D(GUI_MainMenu_Button[10],0,-176) ;Quit
			
			Select State_Menu_Subcontext
				Case 1
					DrawImage3D(GUI_MainMenu_Button[0], 0,146) 
				Case 2
					Select File_CharacterA_Exist
						Case 1
							DrawImage3D(GUI_MainMenu_Button[0], 0,100) 
					End Select
				Case 3
					DrawImage3D(GUI_MainMenu_Button[0], 0,54) 
				Case 4
					DrawImage3D(GUI_MainMenu_Button[0], 0,8) 
				Case 5
					DrawImage3D(GUI_MainMenu_Button[0], 0,-38) 
				Case 6
					DrawImage3D(GUI_MainMenu_Button[0], 0,-84) 
				Case 7
					DrawImage3D(GUI_MainMenu_Button[0], 0,-176) 
				Default
					
			End Select
			
			If MouseX()>(GraphicsWidth()/2-200) And MouseX()<(GraphicsWidth()/2+200) Then
				If MouseY()>(GraphicsHeight()/2)-169 And MouseY()<(GraphicsHeight()/2)-122
					State_Menu_Subcontext = 1
					Text3D(Text_Font[31],D3DOL+12,D3DOD+20,"HELP: Starts a new game. This will DELETE your existing savegame.")
					If MouseDown(1) Then
						Channel_UI = PlaySound (Sound_UI[3])
						Select File_CharacterA_Exist
							Case 1
								Character_To_Delete=1
								State_Character_Selection = 3
							Case 0
								State_Character_Selection=4
								Character_Profile_Loaded=1
								Channel_UI = PlaySound (Sound_UI[3])
						End Select
					EndIf
				ElseIf MouseY()>(GraphicsHeight()/2)-122 And MouseY()<(GraphicsHeight()/2)-77
					State_Menu_Subcontext = 2
					Text3D(Text_Font[31],D3DOL+12,D3DOD+20,"HELP: Load your savegame to continue playing.")
					Select File_CharacterA_Exist
						Case 1
							DrawImage3D(GUI_MainMenu_SImages[0],462,-64)
							
							
							File.Character_Save_Data = First Character_Save_Data
							Text3D(Text_Font[31],256,0,"Pilot Name: "+File\Name$,1)
							Select File\Faction
								Case 1
									DrawImage3D(GUI_MainMenu_SImages[5],256,132,0,0,0.25)
									Text3D(Text_Font[31],D3DOR-256,-276,"The Terran Expanse",1)
								Case 2
									DrawImage3D(GUI_MainMenu_SImages[4],D3DOR-64,128,0,0,0.25)
									Text3D(Text_Font[31],D3DOR-256,-276,"Sirian Independent Colonies",1)
								Case 3
									DrawImage3D(GUI_MainMenu_SImages[4],D3DOR-64,128,0,0,0.25)
									Text3D(Text_Font[31],D3DOR-256,-276,"Orion Democratic Council",1)
							End Select
							Select File\Ship
								Case 1
									DrawImage3D(GUI_MainMenu_Data[1],D3DOR-256,0)
								Case 2
									DrawImage3D(GUI_MainMenu_Data[2],D3DOR-256,0)
								Case 3
									DrawImage3D(GUI_MainMenu_Data[3],D3DOR-256,0)
								Case 4
									DrawImage3D(GUI_MainMenu_Data[4],D3DOR-256,0)
									
								Default
									
							End Select
							
							DrawImage3D(GUI_Rank[File\Character_Value_Level],D3DOR-64,64)
							Text3D(Text_Font[31],D3DOR-256,-296,String_Rank[File\Character_Value_Level],1)
							
							
							If MouseDown(1) And ChannelPlaying(Channel_UI) = False Then
								Channel_UI = PlaySound (Sound_UI[3])
								Select File_CharacterA_Exist
									Case 1
										State_Character_To_Load=1
										Character_Profile_Loaded=1
										State_Character_Selection=2
										Channel_UI = PlaySound (Sound_UI[3])
								End Select
							EndIf
					End Select
					
				ElseIf MouseY()>(GraphicsHeight()/2)-77 And MouseY()<(GraphicsHeight()/2)-31
					Text3D(Text_Font[31],D3DOL+12,D3DOD+20,"HELP: No Function yet.")
					State_Menu_Subcontext = 3
					If MouseDown(1) And ChannelPlaying(Channel_UI) = False Then
						Channel_UI = PlaySound(Sound_UI[3])
						Twitch_Integration = 1 - Twitch_Integration
					EndIf
				ElseIf MouseY()>(GraphicsHeight()/2)-31 And MouseY()<(GraphicsHeight()/2)+15
					State_Menu_Subcontext = 4
					Text3D(Text_Font[31],D3DOL+12,D3DOD+20,"HELP: Select Game settings like audio, music and others.")
					If MouseDown(1) And ChannelPlaying(Channel_UI) = False Then
						Channel_UI = PlaySound (Sound_UI[3])
						State_Character_Selection = 5
					EndIf
				ElseIf MouseY()>(GraphicsHeight()/2)+15 And MouseY()<(GraphicsHeight()/2)+61
					State_Menu_Subcontext = 5
					Text3D(Text_Font[31],D3DOL+12,D3DOD+20,"HELP: Join the Game's Discord.")
					If MouseDown(1) And ChannelPlaying(Channel_UI) = False Then
						Channel_UI = PlaySound (Sound_UI[3])
						ExecFile "https://discord.gg/sbFyqVS"
					EndIf
				ElseIf MouseY()>(GraphicsHeight()/2)+61 And MouseY()<(GraphicsHeight()/2)+107
					State_Menu_Subcontext = 6
					Text3D(Text_Font[31],D3DOL+12,D3DOD+20,"HELP: See the people who worked on this.")
					If MouseDown(1) And ChannelPlaying(Channel_UI) = False Then
						Channel_UI = PlaySound (Sound_UI[3])
						State_Character_Selection = 5
					EndIf
				ElseIf MouseY()>(GraphicsHeight()/2)+153 And MouseY()<(GraphicsHeight()/2)+199
					State_Menu_Subcontext = 7
					Text3D(Text_Font[31],D3DOL+12,D3DOD+20,"HELP: Guess what this does. Hint: Its not playing.")
					If MouseDown(1) And ChannelPlaying(Channel_UI) = False Then
						Channel_UI = PlaySound (Sound_UI[3])
						ClearWorld()
						End
					EndIf
				EndIf
			EndIf
			
;			If File_CharacterA_Exist=1 Then
;				
;				File.Character_Save_Data = First Character_Save_Data
;				Text3D(Text_Font[31],D3DOR-256,-256,"Commander "+File\Name$,1)
;				Select File\Faction
;					Case 1
;						DrawImage3D(GUI_MainMenu_SImages[5],D3DOR-64,128,0,0,0.25)
;						Text3D(Text_Font[31],D3DOR-256,-276,"Terran Defence Fleet",1)
;					Case 2
;						DrawImage3D(GUI_MainMenu_SImages[4],D3DOR-64,128,0,0,0.25)
;						Text3D(Text_Font[31],D3DOR-256,-276,"Sirius Surveying Forces",1)
;				End Select
;				Select File\Ship
;					Case 1
;						DrawImage3D(GUI_MainMenu_Data[1],D3DOR-256,0)
;					Case 2
;						DrawImage3D(GUI_MainMenu_Data[2],D3DOR-256,0)
;					Case 3
;						DrawImage3D(GUI_MainMenu_Data[3],D3DOR-256,0)
;					Case 4
;						DrawImage3D(GUI_MainMenu_Data[4],D3DOR-256,0)
;						
;					Default
;						
;				End Select
;				
;				DrawImage3D(GUI_Rank[File\Character_Value_Level],D3DOR-64,64)
;				Text3D(Text_Font[31],D3DOR-256,-296,String_Rank[File\Character_Value_Level],1)
;				
;			Else
			
;			EndIf
			;[End Block]
			
		Case 2
			; > Loading Character
			Goto Spacegamestart
		Case 3
			; > Deleting and Recreating Empty Character Data
			;[Block]
			File.Character_Save_Data = First Character_Save_Data
			Delete File
			Presave=WriteFile(UserData+"\Profiles\CharDataA.txt.nw")
			WriteLine Presave,0
			CloseFile Presave
			
			CopyFile UserData+"\Profiles\CharDataA.txt.nw", UserData+"\Profiles\CharDataA.txt"
			DeleteFile UserData+"\Profiles\CharDataA.txt.nw"
			
			MainReprogram = OpenFile(UserData+"\Profiles\Maindata.txt")
			CA=ReadLine(MainReprogram)
			CloseFile MainReprogram
			DeleteFile UserData+"\Profiles\Maindata.txt"
			
			MainReprogram=WriteFile(UserData+"\Profiles\Maindata.txt")
			WriteLine MainReprogram,0
			CloseFile MainReprogram
			
			File_Character_Profiles = ReadFile(UserData+"\Profiles\MainData.txt") 
			File_CharacterA_Exist = ReadLine(File_Character_Profiles)
			CloseFile File_Character_Profiles
			
			State_Character_Selection=11
			;[End Block]
		Case 4
			; >> New Character
			;[Block]
			
			DrawImage3D(GUI_MainMenu_Button[8], D3DOL+138,+8) 
			DrawImage3D(GUI_MainMenu_Button[9], D3DOL+138,-84) 
			
			DrawImage3D(GUI_MainMenu_LImages[1],GraphicsWidth()/6,0,0,0,1.25)
			
			If MouseX()<GraphicsWidth()-GraphicsWidth()/6 And MouseX()>GraphicsWidth()-GraphicsWidth()/6-256 Then
				DrawImage3D(GUI_MainMenu_LImages[3],GraphicsWidth()/6,0,0,0,1.25)
				If MouseDown(1) And ChannelPlaying(Channel_UI) = False Then
					String_Faction = 2
				EndIf
			ElseIf MouseX()<GraphicsWidth()-GraphicsWidth()/6-256 And MouseX()>GraphicsWidth()-GraphicsWidth()/6-512 Then
				DrawImage3D(GUI_MainMenu_LImages[2],GraphicsWidth()/6,0,0,0,1.25)
				If MouseDown(1) And ChannelPlaying(Channel_UI) = False Then
					String_Faction = 1
				EndIf
			EndIf
			
			If MouseX()<GraphicsWidth()/2
				If MouseY()>(GraphicsHeight()/2)+61 And MouseY()<(GraphicsHeight()/2)+103
					State_Menu_Subcontext = 5
					Text3D(Text_Font[31],D3DOL+312,-84,"Back to Menu.")
					DrawImage3D(GUI_MainMenu_Button[0], D3DOL+512,-84) 
					If MouseDown(1) And ChannelPlaying(Channel_UI) = False Then
						Channel_UI = PlaySound (Sound_UI[3])
						State_Character_Selection = 1
					EndIf
				ElseIf MouseY()>(GraphicsHeight()/2)-31 And MouseY()<(GraphicsHeight()/2)+15
					State_Menu_Subcontext = 4
					
					If String_Faction = 0 And Character_NewName$="" Then
						Text3D(Text_Font[31],D3DOL+312,8,"Please enter a name and select a faction to continue.")
					ElseIf String_Faction = 0 And Character_NewName$>"" 
						Text3D(Text_Font[31],D3DOL+312,8,"Please select a faction to continue.")
					ElseIf String_Faction > 0 And Character_NewName$="" 
						Text3D(Text_Font[31],D3DOL+312,8,"Please enter a name to continue.")
					ElseIf String_Faction > 0 And Character_NewName$>"" 
						Text3D(Text_Font[31],D3DOL+312,8,"Confirm your selection.")
					EndIf
					
					DrawImage3D(GUI_MainMenu_Button[0], D3DOL+512,8) 
					If MouseDown(1) And ChannelPlaying(Channel_UI) = False Then
						If String_Faction > 0 And Character_NewName$>""  Then
							Channel_UI = PlaySound (Sound_UI[3])
							Character_Completion_Toggle = 1
						EndIf
					EndIf
				EndIf
			EndIf
			
			
			Text3D(Text_Font[31],GraphicsWidth()/6,-250,Character_NewName$,1)
			
			Select String_Faction
				Case 1
					DrawImage3D(GUI_MainMenu_LImages[2],GraphicsWidth()/6,0,0,0,1.25)
					DrawImage3D(GUI_MainMenu_LImages[4],GraphicsWidth()/6,0,0,0,1.25)
				Case 2
					DrawImage3D(GUI_MainMenu_LImages[3],GraphicsWidth()/6,0,0,0,1.25)
					DrawImage3D(GUI_MainMenu_LImages[5],GraphicsWidth()/6,0,0,0,1.25)
			End Select
			
			;[Block] Letter Recognition
			Local key=GetKey() 
			If key>=9 And key<=125 Then 
				PlaySound(Sound_UI[1])
				Character_NewName$=Character_NewName$+Chr(key) 
			EndIf
			If KeyHit(71) Then Character_NewName$=Character_NewName$+"7"
			If KeyHit(72) Then Character_NewName$=Character_NewName$+"8"
			If KeyHit(73) Then Character_NewName$=Character_NewName$+"9"
			If KeyHit(75) Then Character_NewName$=Character_NewName$+"4"
			If KeyHit(76) Then Character_NewName$=Character_NewName$+"5"
			If KeyHit(77) Then Character_NewName$=Character_NewName$+"6"
			If KeyHit(79) Then Character_NewName$=Character_NewName$+"1"
			If KeyHit(80) Then Character_NewName$=Character_NewName$+"2"
			If KeyHit(81) Then Character_NewName$=Character_NewName$+"3"
			If KeyHit(82) Then Character_NewName$=Character_NewName$+"0"
			
			If KeyDown(14) Then 
				Tcounter=Tcounter+1
				If Tcounter>3 Then 
					Character_NewName$=Mid(Character_NewName$,1,Len(Character_NewName$)-1) 
					Tcounter=0
				EndIf
			Else 
				Tcounter=4
			EndIf
			
			String_NameLength=Len(Character_NewName$)
			
			If Character_Completion_Toggle=1 Then
				FlushKeys()
				FlushMouse()
				Local TMSG$=Character_NewName$
				Character_NewName$=""
				Tlan=Len(TMSG$)
				Local ChatLetter$
				Local ChatLen=Len(TMSG$)
				
				For CleanseChat = 1 To ChatLen
					ChatLetter$=Mid(TMSG$,CleanseChat,1)
					Select ChatLetter
						Case "a"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "b"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "c"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "d"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "e"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "f"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "g"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "h"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "i"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "j"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "k"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "l"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "m"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "n"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "o"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "p"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "q"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "r"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "s"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "t"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "u"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "v"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "w"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "x"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "y"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "z"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "A"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "B"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "C"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "D"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "E"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "F"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "G"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "H"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "I"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "J"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "K"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "L"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "M"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "N"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "O"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "P"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "Q"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "R"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "S"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "T"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "U"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "V"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "W"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "X"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "Y"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "Z"
							Character_NewName$=Character_NewName$+ChatLetter
							
						Case "1"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "2"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "3"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "4"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "5"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "6"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "7"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "8"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "9"
							Character_NewName$=Character_NewName$+ChatLetter
						Case "0"
							Character_NewName$=Character_NewName$+ChatLetter
							
					End Select
					
				Next
				New_Tutorialstep = 0
				New_x=0
				New_Y=0
				New_Z=0
				New_Combat=1
				Character_Completion_Toggle = 0
				State_Character_Selection=9
				
			EndIf
			;[End Block]
			;[End Block]
			
		Case 5
			; >> Credits
			;[Block]
			DrawImage3D(GUI_MainMenu_Button[11], D3DOL+138,-84) 
			
			If MouseY()>(GraphicsHeight()/2)+61 And MouseY()<(GraphicsHeight()/2)+103
				State_Menu_Subcontext = 5
				Text3D(Text_Font[31],D3DOL+312,-84,"Back to Menu.")
				DrawImage3D(GUI_MainMenu_Button[0], D3DOL+512,-84) 
				If MouseDown(1) And ChannelPlaying(Channel_UI) = False Then
					PlaySound (Sound_UI[3])
					State_Character_Selection = 1
				EndIf
			EndIf
			
			For TLine.Credits = Each Credits
				If TLine\Textline$="PAINT_1" Then
					
					DrawImage3D(GUI_MainMenu_SImages[6],GraphicsWidth()/5,Tline\Y)
					
					TLine\Y = Tline\Y + 1
					
					If TLine\Y > (GraphicsHeight()/2+25) Then
						TLine\Y = YText
						YText=YText - 20
					EndIf
				ElseIf TLine\Textline$="PAINT_2" Then
					DrawImage3D(GUI_MainMenu_SImages[3],GraphicsWidth()/5,Tline\Y)
					
					TLine\Y = Tline\Y + 1
					
					If TLine\Y > (GraphicsHeight()/2+25) Then
						TLine\Y = YText
						YText=YText - 20
					EndIf
				Else
					Text3D(Text_Font[9],GraphicsWidth()/5,Tline\Y,Tline\Textline$,1)
					TLine\Y = Tline\Y + 1
					
					If TLine\Y > (GraphicsHeight()/2+25) Then
						TLine\Y = YText
						YText=YText - 20
					EndIf
				EndIf
			Next
			
			;[End Block]
			
		Case 6
			; >> Settings
			;[Block]
			
			
			
			;[End Block]
			
		Case 9
			;[Block] >> Save New Character Data and return to Main
			Select Character_Profile_Loaded
				Case 1
					Presave=WriteFile(UserData+"\Profiles\CharDataA.txt.nw")
			End Select
			
			WriteLine Presave,MilliSecs()			;ID
			WriteLine Presave,Character_NewName$	;Name
			WriteLine Presave,CurrentDate$()		;DOB
			WriteLine Presave,String_Faction		;Faction
			WriteLine Presave,New_TutorialStep		;Tstep
			WriteLine Presave,New_X					;LastKnown X
			WriteLine Presave,New_Y					;LastKnown Y
			WriteLine Presave,New_Z					;LastKnown Z
			WriteLine Presave,20					;LastKnown StarSystem
			WriteLine Presave,0						;Experience
			WriteLine Presave,1						;Level
			WriteLine Presave,0						;Money
			WriteLine Presave,New_Combat			;Ship
			WriteLine Presave,1						;Weapon
			WriteLine Presave,0						;inventory
			
			CloseFile Presave
			
			CopyFile UserData+"\Profiles\CharDataA.txt.nw", UserData+"\Profiles\CharDataA.txt"
			DeleteFile UserData+"\Profiles\CharDataA.txt.nw"
			
			DeleteFile UserData+"\Profiles\Maindata.txt"
			
			MainReprogram=WriteFile(UserData+"\Profiles\Maindata.txt")
			WriteLine MainReprogram,1
			CloseFile MainReprogram
			
			Character_NewName$="": String_Faction=0
			
			State_Character_Selection=12
			;[End Block]
			
		Case 10
			; > Abort and End Character Creation
			
			State_Character_Selection=1
			
		Case 11
			; > Reload Character Data and Progress to Creation
			;[Block]
			File_Character_Profiles = ReadFile(UserData+"\Profiles\MainData.txt") 
			File_CharacterA_Exist = ReadLine(File_Character_Profiles)
			CloseFile File_Character_Profiles
			
			For File.Character_Save_Data = Each Character_Save_Data
				Delete File
			Next
			
			If File_CharacterA_Exist<>0 Then
				File_Character_ProfileA = ReadFile(UserData+"\Profiles\CharDataA.txt")
				File.Character_Save_Data = New Character_Save_Data
				File\CreationID    = ReadLine(File_Character_ProfileA)
				File\Name$	       = ReadLine(File_Character_ProfileA)
				File\CreationDate$ = ReadLine(File_Character_ProfileA)
				File\Faction	   = ReadLine(File_Character_ProfileA)
				File\TutorialStep  = ReadLine(File_Character_ProfileA)
				File\LX			   = ReadLine(File_Character_ProfileA)
				File\LY			   = ReadLine(File_Character_ProfileA)
				File\LZ		       = ReadLine(File_Character_ProfileA)
				File\LS		       = ReadLine(File_Character_ProfileA)
				File\Character_Value_XP     = ReadLine(File_Character_ProfileA)
				File\Character_Value_Level  = ReadLine(File_Character_ProfileA)
				File\Wallet		   = ReadLine(File_Character_ProfileA)
				File\Ship	       = ReadLine(File_Character_ProfileA)
				File\Weapon		   = ReadLine(File_Character_ProfileA)
				File\Inventory     = ReadLine(File_Character_ProfileA)
				CloseFile File_Character_ProfileA
			EndIf
			
			State_Character_Selection=4
			;[End Block]
		Case 12
			; > Reload Character Data
			;[Block]
			File_Character_Profiles = ReadFile(UserData+"\Profiles\MainData.txt") 
			File_CharacterA_Exist = ReadLine(File_Character_Profiles)
			CloseFile File_Character_Profiles
			
			For File.Character_Save_Data = Each Character_Save_Data
				Delete File
			Next
			
			If File_CharacterA_Exist<>0 Then
				File_Character_ProfileA = ReadFile(UserData+"\Profiles\CharDataA.txt")
				File.Character_Save_Data = New Character_Save_Data
				File\CreationID    = ReadLine(File_Character_ProfileA)
				File\Name$	       = ReadLine(File_Character_ProfileA)
				File\CreationDate$ = ReadLine(File_Character_ProfileA)
				File\Faction	   = ReadLine(File_Character_ProfileA)
				File\TutorialStep  = ReadLine(File_Character_ProfileA)
				File\LX			   = ReadLine(File_Character_ProfileA)
				File\LY			   = ReadLine(File_Character_ProfileA)
				File\LZ		       = ReadLine(File_Character_ProfileA)
				File\LS		       = ReadLine(File_Character_ProfileA)
				File\Character_Value_XP     = ReadLine(File_Character_ProfileA)
				File\Character_Value_Level  = ReadLine(File_Character_ProfileA)
				File\Wallet		   = ReadLine(File_Character_ProfileA)
				File\Ship	       = ReadLine(File_Character_ProfileA)
				File\Weapon		   = ReadLine(File_Character_ProfileA)
				File\Inventory     = ReadLine(File_Character_ProfileA)
				CloseFile File_Character_ProfileA
			EndIf
			
			State_Character_Selection=1
			;[End Block]
	End Select
	
	DrawImage3D(GUI_Interface[0],MouseX()-(GraphicsWidth()/2),-MouseY()+(GraphicsHeight()/2))
	
	Util_Timer
	InputEx_Update
	
	UpdateWorld	
	
	RenderWorld
	
	WaitTimer(Timer_Character_Selection)
	Flip 0: Clear3D()
	
Until CharDataLoaded=1
;[End Block]

;!--------------------------------------------------------------------------------------------------------------------------------------------------------------
.Spacegamestart

StopChannel Channel_Music

Music_Volume#=.2

UserDataLoad(State_Character_To_Load)

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

Player_Weapon_Array = CreatePivot()



PlayerSwitchShip(Character_Value_Ship)
GetPlayerShipValues(Character_Value_Ship)

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

World_Generate(Character_Value_System, Character_Value_LastX#, Character_Value_LastY#, Character_Value_LastZ#)

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
				For WeaponHardpoints = 1 To Ship_Gun_Hardpoints
					Ship_Gun_Timer[WeaponHardpoints] = Ship_Gun_Timer[WeaponHardpoints] - 1
					If Ship_Gun_Timer[WeaponHardpoints] < 1 Then
						NewShot(Ship_Gun_Slot[WeaponHardpoints],pvShip,15,Ship_Gun_HPX[WeaponHardpoints], Ship_Gun_HPY[WeaponHardpoints], Ship_Gun_HPZ[WeaponHardpoints])
						EmitSound(Sound_Guns[Ship_Gun_Slot[WeaponHardpoints]],eShipBody)
						Local Gun_Max_Reload = GetWeaponvalue(Ship_Gun_Slot[WeaponHardpoints])
						Ship_Gun_Timer[WeaponHardpoints] = Gun_Max_Reload
					EndIf
				Next	
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
	
	If GUI_Cooldown[0] > 0 Then
		GUI_Cooldown[0] = GUI_Cooldown[0] - 1
	EndIf
	
	If KeyHit(1) Then 
		Options = 1 - Options
		If Options = 0 Then
			HUD = 1
		EndIf
	EndIf
	
	;[Block] Options Menu
	If Options = 1 Then
		
		eCameraMode = MODE_CAMERA
		
		HUD = 0
		
		DrawImage3D(GUI_Interface[0],MouseX()-(GraphicsWidth()/2),-MouseY()+(GraphicsHeight()/2))
		
		If Options_Nested = 0 Then
			
			;Draw Main Options Window
			DrawImage3D(GUI_Windows[0],0,0)
			
			If MouseX()>GraphicsWidth()/2-85 And MouseX()<GraphicsWidth()/2+85 Then
				
				;Graphics Settings
				If MouseY()>GraphicsHeight()/2-87 And MouseY()<GraphicsHeight()/2-55 Then
					DrawImage3D(GUI_Buttons[2],0,0)
					FlushMouse()
					If MouseDown(1) Then
						If ChannelPlaying(Channel_UI) = False Then Channel_UI=PlaySound(Sound_UI[4])
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
					DrawImage3D(GUI_Buttons[0],0,0)
					FlushMouse()
					If MouseDown(1) Then
						If ChannelPlaying(Channel_UI) = False Then Channel_UI=PlaySound(Sound_UI[4])
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
			Select Switch_Graphics_Preset
				Case 1
					PostProcess_Active = 0
					PostProcess_GR_Strength#=0;0.075
					PostProcess_GR_RayLength#=0;0.2
					PostProcess_GR_Layers=0
					PostProcess_GR_Smoothing=0
					
					PostProcess_GS_Strength#=0
					
					PostProcess_BL_Strength#=0
					Postprocess_BL_Smoothing=0
				Case 2
					PostProcess_Active = 0
					PostProcess_GR_Strength#=0;0.075
					PostProcess_GR_RayLength#=0;0.2
					PostProcess_GR_Layers=0
					PostProcess_GR_Smoothing=0
					
					PostProcess_GS_Strength#=0
					
					PostProcess_BL_Strength#=0
					Postprocess_BL_Smoothing=0
				Case 3
					PostProcess_Active = 0
					PostProcess_GR_Strength#=0;0.075
					PostProcess_GR_RayLength#=0;0.2
					PostProcess_GR_Layers=0
					PostProcess_GR_Smoothing=0
					
					PostProcess_GS_Strength#=0
					
					PostProcess_BL_Strength#=0
					Postprocess_BL_Smoothing=0
				Case 4
					PostProcess_Active = 0
					PostProcess_GR_Strength#=0;0.075
					PostProcess_GR_RayLength#=0;0.2
					PostProcess_GR_Layers=0
					PostProcess_GR_Smoothing=0
					
					PostProcess_GS_Strength#=0
					
					PostProcess_BL_Strength#=0
					Postprocess_BL_Smoothing=0
			End Select
			
			If MouseX()>GraphicsWidth()/2+231 And MouseX()<GraphicsWidth()/2+252 Then
				
				If MouseY()>GraphicsHeight()/2-222 And MouseY()<GraphicsHeight()/2-201 Then
					If MouseDown(1) Then
						If ChannelPlaying(Channel_UI) = False Then Channel_UI=PlaySound(Sound_UI[5])
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
			DrawImage3D(GUI_Interface[0],MouseX()-(GraphicsWidth()/2),-MouseY()+(GraphicsHeight()/2))
			If MouseX()>GraphicsWidth()/2-85 And MouseX()<GraphicsWidth()/2+85 Then
				
				;Quit Game
				If MouseY()>GraphicsHeight()/2+55 And MouseY()<GraphicsHeight()/2+87 Then
					DrawImage3D(GUI_Buttons[5],0,0)
					FlushMouse()
					If MouseDown(1) Then
						If ChannelPlaying(Channel_UI) = False Then Channel_UI=PlaySound(Sound_UI[4])
						Game_End=1
					EndIf
				EndIf
				
				;Quit
				If MouseY()>GraphicsHeight()/2+90 And MouseY()<GraphicsHeight()/2+122 Then
					DrawImage3D(GUI_Buttons[6],0,0)
					FlushMouse()
					If MouseDown(1) Then
						If ChannelPlaying(Channel_UI) = False Then Channel_UI=PlaySound(Sound_UI[4])
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
		Select Character_Value_Weapon
			Case 1
				DrawImage3D(GUI_Windows[15],0,D3DOD+233)
				DrawImage3D(GUI_Windows[16],0,D3DOD+213)
				DrawImage3D(GUI_Windows[17],0,D3DOD+213)
			Case 2
				DrawImage3D(GUI_Windows[15],0,D3DOD+213)
				DrawImage3D(GUI_Windows[16],0,D3DOD+233)
				DrawImage3D(GUI_Windows[17],0,D3DOD+213)
			Case 3
				DrawImage3D(GUI_Windows[15],0,D3DOD+213)
				DrawImage3D(GUI_Windows[16],0,D3DOD+213)
				DrawImage3D(GUI_Windows[17],0,D3DOD+233)
		End Select
		
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
			DrawImage3D(GUI_Interface[1],MouseX()-(GraphicsWidth()/2),-MouseY()+(GraphicsHeight()/2))
			CameraProject WorldCamera,EntityX(Weapon_Target_Cube),EntityY(Weapon_Target_Cube),EntityZ(Weapon_Target_Cube)
			DrawImage3D(GUI_Interface[2],ProjectedX()-(GraphicsWidth()/2),-ProjectedY()+(GraphicsHeight()/2))
			If Timer_HitRegister>0 Then
				For Hit_Amount= 1 To Timer_HitRegister
					DrawImage3D(GUI_Interface[15],ProjectedX()-(GraphicsWidth()/2)+Hit_Amount,-ProjectedY()+(GraphicsHeight()/2),0,180)
					DrawImage3D(GUI_Interface[15],ProjectedX()-(GraphicsWidth()/2)-Hit_Amount,-ProjectedY()+(GraphicsHeight()/2),0,0)
				Next
			EndIf
		Else
			DrawImage3D(GUI_Interface[0],MouseX()-(GraphicsWidth()/2),-MouseY()+(GraphicsHeight()/2))
		EndIf
		
		gatelimiter=gatelimiter+1
		
		;[Block] ARC Hud ;NEW HUD IN THE WORKS
;		Text3D(Text_Font[1],0,GraphicsHeight()/2-160,currloc$,True)
		
		DrawImage3D(GUI_Status[0],0,0,0,0,1)
		
		Local Player_Value_Armor_Percentage# = Player_Value_Armor_Current / (Player_Value_Armor_Maximum / 100)
		Local Player_Value_Shield_Percentage# = Player_Value_Shield_Current / (Player_Value_Shield_Maximum / 100)
		Local Player_Value_Energy_Percentage# = Player_Value_Energy_Current / (Player_Value_Energy_Maximum / 100)
		
		If Player_Value_Armor_Percentage > 100 Then Player_Value_Armor_Percentage = 100
		If Player_Value_Shield_Percentage > 100 Then Player_Value_Shield_Percentage = 100
		If Player_Value_Energy_Percentage > 100 Then Player_Value_Energy_Percentage = 100
		
		Local Temp_Armor_State#=Player_Value_Armor_Percentage*2.56
		Local Temp_Shield_State#=Player_Value_Shield_Percentage*2.56
		Local Temp_Energy_State#=Player_Value_Energy_Percentage*2.56
		
		DrawRect3D(GUI_Status[1],-256,0,0,0,Temp_Armor_State,256)
		DrawRect3D(GUI_Status[2],0,0,0,0,Temp_Energy_State,256)
		DrawRect3D(GUI_Status[3],256,0,0,0,Temp_Shield_State,256)
		
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
	
	VirtualScene_Show(Scene):ShowEntity WorldCamera
	
	Worldmap_Display()
	Update_Item()
	Buff_Update()
	Storyline_Update()
	GenAudio_Update()
	UpdateBelt()
	UpdateLevels()
	Explosion_Update()
	Tutorial_Update()
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
	VirtualScene_Hide(Scene):HideEntity WorldCamera
	
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
;~C#Blitz3D