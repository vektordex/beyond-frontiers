Const VERSION_MAJOR% = 0
Const VERSION_MINOR% = 0
Const VERSION_PATCH% = 1
Const VERSION_BUILD% = 100026
Const VERSION_STRING$ = VERSION_MAJOR+"."+VERSION_MINOR+"."+VERSION_PATCH+"."+VERSION_BUILD

Global System_Flashlight
Global Object_Charge, KeyMode

Global MAPHUD

Global RenderTimer
Global D3DMouseX, D3DMouseY

;[Block] Basics
Global Player_Effect_Drift_X, Player_Effect_Drift_Y, PlayerX, PlayerY, PlayerZ
Global Player_Value_Speed_Target#, Player_Value_Speed_Main#, SpeedY#, SpeedX#
Global Player_Value_Movement_State, Player_Value_Speed_Current#,Player_Value_Boost_State, Player_Value_Speed_Maximum#=80

Global System_Name$, System_Owner$, System_Security

Global FastTravelSpot, Travel_To_LastSave, Savecounter

Global ShipMake

;[End Block]

;[Block] Weapon/Module/Item Names and Descriptions --- ToDo: Move this in a moddable file, and add translation efforts, at least DE/EN
Global Weapon_Type$[20]
Global Weapon_Desc$[20]
Global Weapon_Timer[20]

Weapon_Type[0] = "No Weapon Installed"
Weapon_Desc[0] = "No description available"

Weapon_Type[1] = "Light Beam Laser"
Weapon_Desc[1] = "The light beam laser is an ancient weapon of unknown origin. It does weak damage to both shields and armor and lacks serious potential in any usage case, except it is very cheap to produce"
Weapon_Timer[1] = 27

Weapon_Type[2] = "Ion Pulse"
Weapon_Desc[2] = "Ion Pulses are fast, and mostly go for shield damage. Its an efficient weapon to disable any combathungry pilot."
Weapon_Timer[2] = 5

Weapon_Type[3] = "Rapid Shell Gun"
Weapon_Desc[3] = "Basically, this weapon throws a big amount of metallic objects at an already shieldless enemy. It is very inefficient against shields, but can wreak havoc on armor plates.

Weapon_Type[4] = "Hypercoil Accelerator"
Weapon_Desc[4] = "Accelerator-Weapons are an advanced form of Shelling, using tripolic Magnets to accelerate shells to supersonic speeds. They can penetrate shields better, but excel at causing immense explosive damage."

Weapon_Type[5] = "Ray-Infuse-Emitter"
Weapon_Desc[5] = "The RIE is a standard military weapon, designed from a high powered guidance laser with projectile assist and such does equal damage to shields and to armor. This however yields a low fire rate."

Weapon_Type[6] = "Supercelerator"
Weapon_Desc[6] = "Take Projectile. Add Slow Charge for high rotation. Get much speed. And shatter through hulls like no one else"

Weapon_Type[7] = "AEGIS Lance"
Weapon_Desc[7] = "This Weapons does not have a publicly available description."


Global Core_Type$[20]
Global Core_Desc$[20]

Core_Type[0] = "No Core Module"
Core_Desc[0] = "This should not happen."

Global Prop_Type$[20]
Global Prop_Desc$[20]

Prop_Type[0] = "No Prop Module"
Prop_Desc[0] = "Congratulations, you can't move!"

Global Shield_Type$[20]
Global Shield_Desc$[20]

Global Item_Type$[128]
Global Item_Desc$[128]

Item_Type[0] = "Item ERROR"
Item_Desc[0] = "This Item should not be in your Inventory"

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

Global Text_SkyBK[30]
Global Text_SkyDN[30]
Global Text_SkyFT[30]
Global Text_SkyLF[30]
Global Text_SkyRT[30]
Global Text_SkyUP[30]
Global Text_SkyCM[30]
Global Text_Font[35]

Global Text_Environment[5]
;[End Block]

;[Block] Sounds
Global Sound_ID[50]
Global Sound_Game[80]
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

Const DrawLayer_Game_Low = -90
Const DrawLayer_Game_Mid = -89
Const DrawLayer_Game_Top = -88

Global HUD
;[End Block]

;[Block] Character Values

;[End Block]

;[Block] Ship Expansions

;[End Block]

;[Block] AI Voices

;[End Block]

;[Block] Timer
Global Timer_Gatejump
;[End Block]

;[Block] Ship Movement Identifiers
Global ShipStrafeX#, ShipStrafeY#, ShipSpeedZ#, ShipRollZ#, SHipInertia#
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
;[End Block]

;[Block] Timers
Global Timer_Update, Timer_Texture#, Weapon_Maxtimer, Timer_HitRegister, Timer_MoveTexture#, Timer_Scanwave, Respawn_Timer
Global WorldClock$, Timer_Storyline, Storyline_Step, Storyline_Descriptor$, Storyline_Portrait[6], Storyline_Person$, Storyline_Subtitle$
Global Title_Timer, Story_Pivot, Upgrade_Timer, Timer_Hacking, State_Hacking=2
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


;[Block] Fill System Info

Global String_SystemName$[256]
Global String_SystemFaction[256]
Global String_SystemScale[256]
Global String_SystemPosX[256]
Global String_SystemPosY[256]
Global String_SystemGate[256]
Global String_SystemDiscovered[256]
Global String_SystemRegion[256]
Global String_RegionName$[9]

;[Block] Region Names
String_RegionName[1] = "The Galactic Gap"
String_RegionName[2] = "Veil Nebula"
String_RegionName[3] = "Terran Core Systems"
String_RegionName[4] = "Belt of Orion"
String_RegionName[5] = "Gardens of Eden"
String_RegionName[6] = "The Dustlands"
String_RegionName[7] = "Sirius Expanse"
String_RegionName[8] = "Caspia"
String_RegionName[9] = "Vega"
String_RegionName[0] = "ERROR"
;[End Block]

Const Region_Gap 	= 1
Const Region_Veil 	= 2
Const Region_Terra	= 3
Const Region_Orion	= 4
Const Region_Eden	= 5
Const Region_Dust	= 6
Const Region_Sirius = 7
Const Region_Caspia = 8
Const Region_Vega	= 7

;[Block] System Name
String_SystemName[0] = "Unknown"
String_SystemName[1] = "Unknown"
String_SystemName[2] = "Unmapped Sector"
String_SystemName[3] = "Unknown"
String_SystemName[4] = "Unknown"
String_SystemName[5] = "Unknown"
String_SystemName[6] = "Unmapped Sector"
String_SystemName[7] = "Unmapped Sector"
String_SystemName[8] = "Unknown"
String_SystemName[9] = "Greyloks Storm"
String_SystemName[10] = "Lost Shores"
String_SystemName[11] = "Unmapped Sector"
String_SystemName[12] = "Mercury"
String_SystemName[13] = "Venus"
String_SystemName[14] = "Terra"
String_SystemName[15] = "Luna"
String_SystemName[16] = "Unknown"
String_SystemName[17] = "Unmapped Sector"
String_SystemName[18] = "Unmapped Sector"
String_SystemName[19] = "Arctur"
String_SystemName[20] = "Mintaka"
String_SystemName[21] = "Last Guard"
String_SystemName[22] = "Unmapped Sector"
String_SystemName[23] = "Unmapped Sector"
String_SystemName[24] = "Unmapped Sector"
String_SystemName[25] = "Silent Watch"
String_SystemName[26] = "Black Haven"
String_SystemName[27] = "Unmapped Sector"
String_SystemName[28] = "Unmapped Sector"
String_SystemName[29] = "Mars"
String_SystemName[30] = "Unmapped Sector"
String_SystemName[31] = "Unmapped Sector"
String_SystemName[32] = "Unknown"
String_SystemName[33] = "Unmapped Sector"
String_SystemName[34] = "Orion"
String_SystemName[35] = "Maia"
String_SystemName[36] = "Lovelace"
String_SystemName[37] = "Curie"
String_SystemName[38] = "Unmapped Sector"
String_SystemName[39] = "Unmapped Sector"
String_SystemName[40] = "Unmapped Sector"
String_SystemName[41] = "The Barrier"
String_SystemName[42] = "Unmapped Sector"
String_SystemName[43] = "Titan"
String_SystemName[44] = "Saturn"
String_SystemName[45] = "Jupiter"
String_SystemName[46] = "Io"
String_SystemName[47] = "Europa"
String_SystemName[48] = "Unknown"
String_SystemName[49] = "Unmapped Sector"
String_SystemName[50] = "Halcyon"
String_SystemName[51] = "Unmapped Sector"
String_SystemName[52] = "Unmapped Sector"
String_SystemName[53] = "Laika"
String_SystemName[54] = "The Ring"
String_SystemName[55] = "Murkle"
String_SystemName[56] = "Unmapped Sector"
String_SystemName[57] = "Garden"
String_SystemName[58] = "Gateway"
String_SystemName[59] = "Oort Cloud"
String_SystemName[60] = "Unmapped Sector"
String_SystemName[61] = "Unmapped Sector"
String_SystemName[62] = "Return"
String_SystemName[63] = "Unmapped Sector"
String_SystemName[64] = "Tamo"
String_SystemName[65] = "Allegra"
String_SystemName[66] = "Terrible Deep"
String_SystemName[67] = "Newton"
String_SystemName[68] = "Sagan"
String_SystemName[69] = "Gagarin"
String_SystemName[70] = "Tsiolkovski"
String_SystemName[71] = "Hawking"
String_SystemName[72] = "Unmapped Sector"
String_SystemName[73] = "The Path"
String_SystemName[74] = "Crossroad"
String_SystemName[75] = "Travels Rest"
String_SystemName[76] = "Kenney Haven"
String_SystemName[77] = "Graveyard"
String_SystemName[78] = "The Line"
String_SystemName[79] = "Unmapped Sector"
String_SystemName[80] = "Thera"
String_SystemName[81] = "Unmapped Sector"
String_SystemName[82] = "Dayyan"
String_SystemName[83] = "Yumana"
String_SystemName[84] = "Unmapped Sector"
String_SystemName[85] = "Unmapped Sector"
String_SystemName[86] = "Matane"
String_SystemName[87] = "Unmapped Sector"
String_SystemName[88] = "Unmapped Sector"
String_SystemName[89] = "Unmapped Sector"
String_SystemName[90] = "Passage"
String_SystemName[91] = "Unmapped Sector"
String_SystemName[92] = "Unmapped Sector"
String_SystemName[93] = "Signal One"
String_SystemName[94] = "Signal two"
String_SystemName[95] = "Unmapped Sector"
String_SystemName[96] = "Unmapped Sector"
String_SystemName[97] = "Unmapped Sector"
String_SystemName[98] = "Unknown"
String_SystemName[99] = "Densha"
String_SystemName[100] = "Atatakai"
String_SystemName[101] = "Shiba"
String_SystemName[102] = "Sakura"
String_SystemName[103] = "Unmapped Sector"
String_SystemName[104] = "Unmapped Sector"
String_SystemName[105] = "Unknown"
String_SystemName[106] = "Lela"
String_SystemName[107] = "Unmapped Sector"
String_SystemName[108] = "Unmapped Sector"
String_SystemName[109] = "Unmapped Sector"
String_SystemName[110] = "Unmapped Sector"
String_SystemName[111] = "CORE #0D31"
String_SystemName[112] = "Kryos"
String_SystemName[113] = "Nazima"
String_SystemName[114] = "Unknown"
String_SystemName[115] = "Akita"
String_SystemName[116] = "Hiroi"
String_SystemName[117] = "Kurai"
String_SystemName[118] = "Sirius Ni"
String_SystemName[119] = "Heji"
String_SystemName[120] = "Unmapped Sector"
String_SystemName[121] = "Fiwa"
String_SystemName[122] = "Last Refuge"
String_SystemName[123] = "Zarif"
String_SystemName[124] = "Raduan"
String_SystemName[125] = "Vega"
String_SystemName[126] = "HUB #0DF3"
String_SystemName[127] = "HUB #3F8A"
;[End Block]

;[Block] System Scale
SeedRnd 28041988
For Scl = 0 To 127
	String_SystemScale[Scl] = Rand(50000,120000)
Next
;[End Block]

For ReadDiscovery = 0 To 127
	String_SystemDiscovered[ReadDiscovery] = 0
Next

;[Block] System Faction Control
String_SystemFaction[0] = Faction_Pirate
String_SystemFaction[1] = Faction_Unknown
String_SystemFaction[2] = Faction_None
String_SystemFaction[3] = Faction_Unknown
String_SystemFaction[4] = Faction_Unknown
String_SystemFaction[5] = Faction_Pirate
String_SystemFaction[6] = Faction_None
String_SystemFaction[7] = Faction_None
String_SystemFaction[8] = Faction_Unknown
String_SystemFaction[9] = Faction_Pirate
String_SystemFaction[10] = Faction_Neutral
String_SystemFaction[11] = Faction_None
String_SystemFaction[12] = Faction_Terran
String_SystemFaction[13] = Faction_Terran
String_SystemFaction[14] = Faction_Terran
String_SystemFaction[15] = Faction_Terran
String_SystemFaction[16] = Faction_Pirate
String_SystemFaction[17] = Faction_None
String_SystemFaction[18] = Faction_None
String_SystemFaction[19] = Faction_Orion
String_SystemFaction[20] = Faction_Orion
String_SystemFaction[21] = Faction_Neutral
String_SystemFaction[22] = Faction_None
String_SystemFaction[23] = Faction_None
String_SystemFaction[24] = Faction_None
String_SystemFaction[25] = Faction_Pirate
String_SystemFaction[26] = Faction_Neutral
String_SystemFaction[27] = Faction_None
String_SystemFaction[28] = Faction_None
String_SystemFaction[29] = Faction_Terran
String_SystemFaction[30] = Faction_None
String_SystemFaction[31] = Faction_None
String_SystemFaction[32] = Faction_Unknown
String_SystemFaction[33] = Faction_None
String_SystemFaction[34] = Faction_Orion
String_SystemFaction[35] = Faction_Orion
String_SystemFaction[36] = Faction_Neutral
String_SystemFaction[37] = Faction_Neutral
String_SystemFaction[38] = Faction_None
String_SystemFaction[39] = Faction_None
String_SystemFaction[40] = Faction_None
String_SystemFaction[41] = Faction_Pirate
String_SystemFaction[42] = Faction_None
String_SystemFaction[43] = Faction_Terran
String_SystemFaction[44] = Faction_Terran
String_SystemFaction[45] = Faction_Terran
String_SystemFaction[46] = Faction_Terran
String_SystemFaction[47] = Faction_Neutral
String_SystemFaction[48] = Faction_Unknown
String_SystemFaction[49] = Faction_None
String_SystemFaction[50] = Faction_Orion
String_SystemFaction[51] = Faction_None
String_SystemFaction[52] = Faction_None
String_SystemFaction[53] = Faction_Neutral
String_SystemFaction[54] = Faction_Neutral
String_SystemFaction[55] = Faction_Battleground_TO
String_SystemFaction[56] = Faction_None
String_SystemFaction[57] = Faction_Neutral
String_SystemFaction[58] = Faction_Battleground_TS
String_SystemFaction[59] = Faction_Neutral
String_SystemFaction[60] = Faction_None
String_SystemFaction[61] = Faction_None
String_SystemFaction[62] = Faction_Neutral
String_SystemFaction[63] = Faction_None
String_SystemFaction[64] = Faction_Pirate
String_SystemFaction[65] = Faction_Neutral
String_SystemFaction[66] = Faction_Orion
String_SystemFaction[67] = Faction_Neutral
String_SystemFaction[68] = Faction_Neutral
String_SystemFaction[69] = Faction_Battleground_OS
String_SystemFaction[70] = Faction_Battleground_OS
String_SystemFaction[71] = Faction_Battleground_TS
String_SystemFaction[72] = Faction_None
String_SystemFaction[73] = Faction_Battleground_TS
String_SystemFaction[74] = Faction_Battleground_TS
String_SystemFaction[75] = Faction_Neutral
String_SystemFaction[76] = Faction_Neutral
String_SystemFaction[77] = Faction_Neutral
String_SystemFaction[78] = Faction_Battleground_TO
String_SystemFaction[79] = Faction_None
String_SystemFaction[80] = Faction_Pirate
String_SystemFaction[81] = Faction_None
String_SystemFaction[82] = Faction_Neutral
String_SystemFaction[83] = Faction_Neutral
String_SystemFaction[84] = Faction_None
String_SystemFaction[85] = Faction_None
String_SystemFaction[86] = Faction_Battleground_TS
String_SystemFaction[87] = Faction_None
String_SystemFaction[88] = Faction_None
String_SystemFaction[89] = Faction_None
String_SystemFaction[90] = Faction_Battleground_TS
String_SystemFaction[91] = Faction_None
String_SystemFaction[92] = Faction_None
String_SystemFaction[93] = Faction_Terran
String_SystemFaction[94] = Faction_Orion
String_SystemFaction[95] = Faction_None
String_SystemFaction[96] = Faction_None
String_SystemFaction[97] = Faction_None
String_SystemFaction[98] = Faction_Unknown
String_SystemFaction[99] = Faction_Neutral
String_SystemFaction[100] = Faction_Sirian
String_SystemFaction[101] = Faction_Sirian
String_SystemFaction[102] = Faction_Sirian
String_SystemFaction[103] = Faction_None
String_SystemFaction[104] = Faction_None
String_SystemFaction[105] = Faction_Unknown
String_SystemFaction[106] = Faction_Pirate
String_SystemFaction[107] = Faction_None
String_SystemFaction[108] = Faction_None
String_SystemFaction[109] = Faction_None
String_SystemFaction[110] = Faction_None
String_SystemFaction[111] = Faction_Crimson
String_SystemFaction[112] = Faction_Pirate
String_SystemFaction[113] = Faction_Pirate
String_SystemFaction[114] = Faction_Unknown
String_SystemFaction[115] = Faction_Sirian
String_SystemFaction[116] = Faction_Sirian
String_SystemFaction[117] = Faction_Sirian
String_SystemFaction[118] = Faction_Sirian
String_SystemFaction[119] = Faction_Neutral
String_SystemFaction[120] = Faction_None
String_SystemFaction[121] = Faction_Neutral
String_SystemFaction[122] = Faction_Neutral
String_SystemFaction[123] = Faction_Sirian
String_SystemFaction[124] = Faction_Pirate
String_SystemFaction[125] = Faction_Pirate
String_SystemFaction[126] = Faction_Crimson
String_SystemFaction[127] = Faction_Crimson

;[End Block]

;[Block] System Map Index 

; Math for Indexing. 
; SystemID / 16 +1 = Row
; 

For SystemID = 0 To 127
	String_SystemPosX[SystemID] = SystemID Mod 16 
	String_SystemPosY[SystemID] = (SystemID / 16 + 1) * - 1
Next

;[End Block]
;North Gate = 1, East Gate 2, South Gate 4, West Gate 8. NE = 3, NS = 5 usw

;[Block] System Gate Index
String_SystemGate[0] = 4
String_SystemGate[1] = 0
String_SystemGate[2] = 0
String_SystemGate[3] = 0
String_SystemGate[4] = 0
String_SystemGate[5] = 4
String_SystemGate[6] = 0
String_SystemGate[7] = 0
String_SystemGate[8] = 0
String_SystemGate[9] = 4+2
String_SystemGate[10] = 8
String_SystemGate[11] = 0
String_SystemGate[12] = 4+2
String_SystemGate[13] = 2+4+8
String_SystemGate[14] = 2+8
String_SystemGate[15] = 8
String_SystemGate[16] = 1+2+4
String_SystemGate[17] = 0
String_SystemGate[18] = 0
String_SystemGate[19] = 2+4+8
String_SystemGate[20] = 2+4+8
String_SystemGate[21] = 8+1
String_SystemGate[22] = 0
String_SystemGate[23] = 0
String_SystemGate[24] = 0
String_SystemGate[25] = 1+2+4
String_SystemGate[26] = 8
String_SystemGate[27] = 0
String_SystemGate[28] = 0
String_SystemGate[29] = 1+4
String_SystemGate[30] = 0
String_SystemGate[31] = 0
String_SystemGate[32] = 1
String_SystemGate[33] = 0
String_SystemGate[34] = 2+4
String_SystemGate[35] = 1+2+8
String_SystemGate[36] = 1+2+8
String_SystemGate[37] = 4+8
String_SystemGate[38] = 0
String_SystemGate[39] = 0
String_SystemGate[40] = 0
String_SystemGate[41] = 1+4
String_SystemGate[42] = 0
String_SystemGate[43] = 4+2
String_SystemGate[44] = 1+2+8
String_SystemGate[45] = 2+8
String_SystemGate[46] = 2+4+8
String_SystemGate[47] = 8
String_SystemGate[48] = 0
String_SystemGate[49] = 0
String_SystemGate[50] = 1+4
String_SystemGate[51] = 0
String_SystemGate[52] = 0
String_SystemGate[53] = 1+2+4
String_SystemGate[54] = 8+4
String_SystemGate[55] = 4
String_SystemGate[56] = 0
String_SystemGate[57] = 1+2+4
String_SystemGate[58] = 2+4+8
String_SystemGate[59] = 1+4+8
String_SystemGate[60] = 0
String_SystemGate[61] = 0
String_SystemGate[62] = 1+4
String_SystemGate[63] = 0
String_SystemGate[64] = 2
String_SystemGate[65] = 2+4+8
String_SystemGate[66] = 1+2+8
String_SystemGate[67] = 2+4+8
String_SystemGate[68] = 2+8
String_SystemGate[69] = 1+2+8
String_SystemGate[70] = 1+2+4+8
String_SystemGate[71] = 1+2+8
String_SystemGate[72] = 0
String_SystemGate[73] = 1+2+8
String_SystemGate[74] = 1+2+4+8
String_SystemGate[75] = 1+2+8
String_SystemGate[76] = 2+8
String_SystemGate[77] = 4+8
String_SystemGate[78] = 1+4
String_SystemGate[79] = 0
String_SystemGate[80] = 2+4
String_SystemGate[81] = 0
String_SystemGate[82] = 2+8
String_SystemGate[83] = 1+4+8
String_SystemGate[84] = 0
String_SystemGate[85] = 0
String_SystemGate[86] = 1+2+4
String_SystemGate[87] = 0
String_SystemGate[88] = 0
String_SystemGate[89] = 0
String_SystemGate[90] = 1+4
String_SystemGate[91] = 0
String_SystemGate[92] = 0
String_SystemGate[93] = 1+2+4
String_SystemGate[94] = 1+8
String_SystemGate[95] = 0
String_SystemGate[96] = 0
String_SystemGate[97] = 0
String_SystemGate[98] = 2
String_SystemGate[99] = 1+4+8
String_SystemGate[100] = 2+4
String_SystemGate[101] = 2+4+8
String_SystemGate[102] = 1+4+8
String_SystemGate[103] = 0
String_SystemGate[104] = 0
String_SystemGate[105] = 2+4
String_SystemGate[106] = 1+4+8
String_SystemGate[107] = 0
String_SystemGate[108] = 0
String_SystemGate[109] = 0
String_SystemGate[110] = 0
String_SystemGate[111] = 4
String_SystemGate[112] = 1
String_SystemGate[113] = 1+2
String_SystemGate[114] = 8
String_SystemGate[115] = 1+2
String_SystemGate[116] = 1+2+8
String_SystemGate[117] = 1+2+8
String_SystemGate[118] = 1+2+8
String_SystemGate[119] = 2+8
String_SystemGate[120] = 0
String_SystemGate[121] = 1+8
String_SystemGate[122] = 1+2
String_SystemGate[123] = 2+8
String_SystemGate[124] = 2+8
String_SystemGate[125] = 1+2+8
String_SystemGate[126] = 2+8
String_SystemGate[127] = 1+8
;[End Block]

;[Block] System Region Index
String_SystemRegion[0] = 1
String_SystemRegion[1] = 1
String_SystemRegion[2] = 1
String_SystemRegion[3] = 2
String_SystemRegion[4] = 2
String_SystemRegion[5] = 2
String_SystemRegion[6] = 2
String_SystemRegion[7] = 2
String_SystemRegion[8] = 2
String_SystemRegion[9] = 2
String_SystemRegion[10] = 2
String_SystemRegion[11] = 3
String_SystemRegion[12] = 3
String_SystemRegion[13] = 3
String_SystemRegion[14] = 3
String_SystemRegion[15] = 3
String_SystemRegion[16] = 1
String_SystemRegion[17] = 1
String_SystemRegion[18] = 4
String_SystemRegion[19] = 4
String_SystemRegion[20] = 4
String_SystemRegion[21] = 4
String_SystemRegion[22] = 4
String_SystemRegion[23] = 2
String_SystemRegion[24] = 2
String_SystemRegion[25] = 2
String_SystemRegion[26] = 2
String_SystemRegion[27] = 2
String_SystemRegion[28] = 3
String_SystemRegion[29] = 3
String_SystemRegion[30] = 3
String_SystemRegion[31] = 3
String_SystemRegion[32] = 1
String_SystemRegion[33] = 1
String_SystemRegion[34] = 4
String_SystemRegion[35] = 4
String_SystemRegion[36] = 4
String_SystemRegion[37] = 4
String_SystemRegion[38] = 4
String_SystemRegion[39] = 4
String_SystemRegion[40] = 2
String_SystemRegion[41] = 2
String_SystemRegion[42] = 2
String_SystemRegion[43] = 3
String_SystemRegion[44] = 3
String_SystemRegion[45] = 3
String_SystemRegion[46] = 3
String_SystemRegion[47] = 3
String_SystemRegion[48] = 1
String_SystemRegion[49] = 1
String_SystemRegion[50] = 4
String_SystemRegion[51] = 4
String_SystemRegion[52] = 4
String_SystemRegion[53] = 4
String_SystemRegion[54] = 4
String_SystemRegion[55] = 4
String_SystemRegion[56] = 6
String_SystemRegion[57] = 6
String_SystemRegion[58] = 6
String_SystemRegion[59] = 3
String_SystemRegion[60] = 3
String_SystemRegion[61] = 3
String_SystemRegion[62] = 3
String_SystemRegion[63] = 3
String_SystemRegion[64] = 5
String_SystemRegion[65] = 5
String_SystemRegion[66] = 4
String_SystemRegion[67] = 4
String_SystemRegion[68] = 4
String_SystemRegion[69] = 7
String_SystemRegion[70] = 7
String_SystemRegion[71] = 7
String_SystemRegion[72] = 7
String_SystemRegion[73] = 6
String_SystemRegion[74] = 6
String_SystemRegion[75] = 6
String_SystemRegion[76] = 6
String_SystemRegion[77] = 6
String_SystemRegion[78] = 6
String_SystemRegion[79] = 6
String_SystemRegion[80] = 5
String_SystemRegion[81] = 7
String_SystemRegion[82] = 7
String_SystemRegion[83] = 7
String_SystemRegion[84] = 7
String_SystemRegion[85] = 7
String_SystemRegion[86] = 7
String_SystemRegion[87] = 7
String_SystemRegion[88] = 8
String_SystemRegion[89] = 8
String_SystemRegion[90] = 8
String_SystemRegion[91] = 8
String_SystemRegion[92] = 6
String_SystemRegion[93] = 6
String_SystemRegion[94] = 6
String_SystemRegion[95] = 6
String_SystemRegion[96] = 5
String_SystemRegion[97] = 7
String_SystemRegion[98] = 7
String_SystemRegion[99] = 7
String_SystemRegion[100] = 7
String_SystemRegion[101] = 7
String_SystemRegion[102] = 7
String_SystemRegion[103] = 8
String_SystemRegion[104] = 8
String_SystemRegion[105] = 8
String_SystemRegion[106] = 8
String_SystemRegion[107] = 8
String_SystemRegion[108] = 8
String_SystemRegion[109] = 8
String_SystemRegion[110] = 8
String_SystemRegion[111] = 7
String_SystemRegion[112] = 5
String_SystemRegion[113] = 7
String_SystemRegion[114] = 7
String_SystemRegion[115] = 7
String_SystemRegion[116] = 7
String_SystemRegion[117] = 7
String_SystemRegion[118] = 7
String_SystemRegion[119] = 8
String_SystemRegion[120] = 8
String_SystemRegion[121] = 8
String_SystemRegion[122] = 8
String_SystemRegion[123] = 8
String_SystemRegion[124] = 7
String_SystemRegion[125] = 7
String_SystemRegion[126] = 7
String_SystemRegion[127] = 7
;[End Block]

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
;~F#1B#BD#C3#C8#CC#D2#D9#DE#E4#F5#1A6#22A#238#2BB
;~C#Blitz3D