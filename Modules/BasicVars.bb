Const VERSION_MAJOR% = 0
Const VERSION_MINOR% = 1
Const VERSION_PATCH% = 4
Const VERSION_BUILD% = 100026
Const VERSION_STRING$ = VERSION_MAJOR+"."+VERSION_MINOR+"."+VERSION_PATCH+"."+VERSION_BUILD

Global System_Flashlight
Global Object_Charge, KeyMode

Global Testentity

Global MAPHUD

Global RenderTimer
Global D3DMouseX, D3DMouseY

;[Block] Basics
Global Player_Effect_Drift_X, Player_Effect_Drift_Y, PlayerX, PlayerY, PlayerZ
Global Player_Value_Speed_Target#, Player_Value_Speed_Main#, SpeedY#, SpeedX#
Global Player_Value_Movement_State, Player_Value_Speed_Current#,Player_Value_Boost_State, Player_Value_Speed_Maximum#=80

Global Effect_Camera_Zoom# = 1

Global System_Name$, System_Owner$, System_Security

Global FastTravelSpot, Travel_To_LastSave, Savecounter

Global ShipMake

Global Game_Menu_Open=0, Game_End=0, Game_Menu_Debug=0, Force_UI_Mode=0, State_Docked_Submenu=0
;[End Block]

;[Block] Chat Connection
Global ChatServer$ = "irc.libera.chat" 
Global ChatPort = 6665
Global ChatHostName$ = "sirius.dbstudios.com"
Global ChatNickName$
Global ChatChannel$ = "#beyondfrontiers"

Global IRCChatData$, IRCChatName$

Type Messages
	Field Message$, From$, ID
End Type 

;[End Block]

;[Block] Weapon/Module/Item Names and Descriptions --- ToDo: Move this in a moddable file, and add translation efforts, at least DE/EN
Global ItemName$[50]
;[End BLOCK]

;[Block] Performance Tracking
Global Performance
Global Performance_Update
Global Performance_Update_Network
Global Performance_Update_Players
Global Performance_Update_Projectiles
Global Performance_Update_Input
Global Performance_Update_UI
Global Performance_RestUpdate
Global Performance_Physics
Global Performance_Render
Global Performance_Render_3D, Performance_Render_3D_Tris
Global Performance_Render_PostProcess
Global Performance_Render_UI, Performance_Render_UI_Tris
Global Performance_Flip
Global Performance_Wait
Global Settings_Scaling
;[End Block]

;[Block] Meshes
Global Mesh_Roid[5]
Global Mesh_Ship[20]
Global Mesh_Gate[4]
Global Mesh_Station[10]
Global Mesh_Asteroid[4]
Global Mesh_Planets[25]
Global Planet_Surface_Exist = 0, Planet_Surface_Distance = 0
Global Text_Effects[5]
Global Mesh_Environment[5]
Global Mesh_Loot
;[End Block]

;[Block] Objects
Global Object_Environment[5]
Global Object_Light[1]
Global Object_Zero
;[End Block]

;[Block] Textures
Global Text_Ship[11]
Global Text_Ship_FX[11]
Global Text_Station[10]
Global Text_Station_FX[10]

Global Text_Gate[2]
Global Text_Gate_FX[2]
Global Text_Asteroid[4]
Global Text_Asteroid_FX[4]

Global Text_SkyBK[8]
Global Text_SkyDN[8]
Global Text_SkyFT[8]
Global Text_SkyLF[8]
Global Text_SkyRT[8]
Global Text_SkyUP[8]
Global Text_SkyCM[8]
Global Text_Font[35]

Global Text_Environment[5]

Global Text_Loot, Text_Loot_FX
;[End Block]

;[Block] Sounds
Global Sound_ID[50]
Global Sound_Game[80]
Global Sound3D_ID[10]
;[End Block]

;[Block] Channels
Global Channel_UI
Global Channel_Music
Global Channel_Sound
Global Channel_Voice
Global Channel_Tutorial
Global Channel_Tutorial_Name
Global Channel_Story
Global Channel_SystemInit
;[End Block]

;[Block] Music
Global Music_ID[11], Music_Enabled=1, Desired_MVolume# = 0.5
;[End Block]

;[Block] Interface
Global GUI_Windows[50]
Global GUI_Game[50]
Global GUI_Company[33]
Global GUI_Items[20]
Global HUD, InventoryShow
;[End Block]

;[Block] Character Values
;- > Unchangeable Variables
Global Player_Environment_Shipname$ 
Global Player_Environment_ShipClass$

;- > Ship Equipment
Global Player_Environment_BaseSpeed, Player_Environment_FullSpeed#
Global Player_Environment_BaseInertia#, Player_Environment_FullInertia# 
Global Player_Environment_BaseMobility#, Player_Environment_FullMobility# 
Global Player_Environment_BaseAccel#, Player_Environment_FullAccel# 
Global Player_Environment_BaseMSpeed, Player_Environment_FullMSpeed
Global Player_Environment_BaseTurn#, Player_Environment_FullTurn#

;- > Ship Influenced Equipment with Current Values
Global Player_Environment_BaseArmor, Player_Environment_CurrentArmor, Player_Environment_FullArmor
Global Player_Environment_BaseShield, Player_Environment_CurrentShield, Player_Environment_FullShield
Global Player_Environment_BaseEnergy, Player_Environment_CurrentEnergy, Player_Environment_FullEnergy
Global Player_Environment_BaseCargo, Player_Environment_CurrentCargo, Player_Environment_FullCargo 

Global Player_GlobalX, Player_GlobalY
;[End Block]

;[Block] Ship Expansions

;[End Block]

;[Block] AI Voices

;[End Block]

;[Block] Globalized System Vars
Global Station_Owner, Station_Services, Station_WareImport, Station_WareExport, Station_Rumors, Station_Name$

;[End Block]

;[Block] Ship Movement Identifiers
Global ShipStrafeX#, ShipStrafeY#, ShipSpeedZ#, ShipRollZ#, Player_Value_Inertia_Modifier#
Global Weapon_Target_Cube, ShipPosXYZ, Ship_Shield_Reload_Tick, Ship_Shield_Reload_Amount, Ship_Shield_Reload_Tick_Timer
Global Ship_Value_ScanningStrength
;[End Block]

;[Block] Storyline Bonus Values
Global Bonus_Value_ScanningStrength
Global Tutorial_Mission[5]
;[End Block]

;[Block] Player States
Global Player_State_Alive
;[End Block]

;[Block] Temp Upgrades
Global Upgrade_Weapon_Damage, Upgrade_Weapon_Multiplier#
Global Upgrade_Shield_Recharge, Upgrade_XP_Modifier#
Global Upgrade_Armor_Repair, Upgrade_GlobalMap
;[End Block]

;[Block] Strings
Global String_Rank$[60]
Global String_Status$[2]
String_Status[0]="-"
String_Status[1]="+"
;[End Block]

;[Block] Environment
Global Zone_Dust_Base, Zone_Dust_Handle, Zone_Dust_External[15], Zone_Dust_Created=0
Global Light_Pivot, Weapon_Maxdistance, System_TextID, RaceTrack_BaseID=0, RaceTrack_Pivot, RandRot, RaceTrack_Active=1, Racetrack_Max
Global Camera_Zoom_Speed#
;[End Block]

;[Block] Timers
Global Timer_Update, Timer_Texture#, Weapon_Maxtimer, Timer_HitRegister, Timer_MoveTexture#, Timer_Scanwave, Respawn_Timer
Global WorldClock$, Timer_Dock, Timer_Gatejump
;[End Block]

;[Block] Various <- Sort THIS
Global Existing_Terran = 0
Global Existing_Sirian = 0
Global Existing_Pirate = 0
Global Existing_Total = 0
Global Existing_POI = 0
Global State_System_Ownership = 0
Global Existing_Ratio_Terran = 0.0
Global Existing_Ratio_Sirian = 0.0
Global Existing_Ratio_Total = 0 
Global Level_XP_Needed, Level_Progress

Global Trigger_Map_Scaling=1
Global Spawn_MaxRank = 4
;[End Block]

Local LinePick_Message

Global Options, Options_Nested=0

Global Render_A#, Render_B#

Global String_NameLength, String_Faction

Global Temp_Gunspeed

Global Laser_Guidance[4]

Global MX%,MY% 

Global ButtonStartY[30]
Global ButtonEndY[30]

Global Global_Port=2222

Global ButtonTempY=112

Const Card_Nvidia=False

Global Timer_Criminal
Global State_Criminal
Global Reset_AI_Target=0

Global MapOriginX, MapOriginZ

Global CurrentZone

Global Respawn_X, Respawn_Y, Respawn_Z

Global Music_Theme=1, Music_Volume#=.2, Music_Aggro_Timer=0

For A = 1 To 30
	ButtonStartY[A]=ButtonTempY
	ButtonTempY=ButtonTempY-11
	ButtonEndY[A]=ButtonTempY
	ButtonTempY=ButtonTempY-2
Next
Global DST_Looptime#,DST_TIMER,DST_CHECKTIMER,dst_check,DST_TempPivot
Const dst_sharesize=200

Global PCL_Timer

Global Gw#, Gh#
Global GwBy2#, GhBy2#
Global GwBy3#,  GhBy3#
Global GwBy4#, GhBy4#

Global CameraScene

Const pcl_count=200			; change here to get more than 200 Particles per Cloud

;Translate.bb
Global DLFILE
Global NAME_YIELD$

Global Universe_Seed

Global Item_Icon[200]

Global seed%[2]
Global syllables$="..lexegezacebisousesarmaindirea.eratenberalavetiedorquanteisrionsiom"

seed[0]=Rand($ffff)
seed[1]=Rand($ffff)
seed[2]=Rand($ffff)

Global MyAU, RealAU, AUTime, AUConversion

RealAU=149597870.7

Global Counter_Siphon#, SiphonSine#

;Ship Utilities
Global Ship_Rad_Resistance#, Ship_TD_Boost#, Ship_Heal_Boost#, Ship_Loot_Boost, TotalBots

Global ShoppingCategory, ShoppingOre, ShoppingShip, ShoppingImplant, OreTradeAmount, CountUpBuy, CountUpSell
Global ShoppedShipID, TotalDiscoveries, TLxMSH, TLxMSX, TLxENT, TLxENT2, TLxTEX, CARGOxLEFT, Tcounter, Tlan, TradeInRange

Const MinRoids=7
Const MaxRoids=10

Global Lim1, Lim2, Lim3, Lim4, Lim5, Lim6

Global AllowFlush=0, CargBoost#, Limiter#, Suicided, AdTimer

Global MESH_CONTAINER[3]

Global Target_Active, Target_Active_Timer
;Temporary, REmove After Use
Global AIFleetSpd#, AIFleetSpdRel#
;End of Temp Part


;~IDEal Editor Parameters:
;~F#B8#BD#C1#C7
;~C#Blitz3D