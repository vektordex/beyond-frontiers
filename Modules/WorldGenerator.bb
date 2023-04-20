Global DiscoveryID
DiscoveryID=DiscoveryID+1
Global MyDiscoveredBelts
Global Security_Level
Global SystemID_Global

Type Stargate
	Field Mesh, Mesh_Effect, Mesh_Effect_Horizon
	Field JumpOBB
	Field TX, TY, TZ
	Field Target_System, Target_Sky
End Type

Function World_Generate(SystemID, TX#, TY#, TZ#)
	
;	WireFrame True
	
	Zone_Dust_Created = 0
	
	Planet_Surface_Exist = 0
	
	AI_ID=1
	Existing_POI = 0
	
	Universe_Seed = 282103*SystemID
	SeedRnd(Universe_Seed)
	
	Object_Zero=CreatePivot()
	If SystemID<0 Or SystemID>127 Then SystemID = 0
	
	;Modify_Fog(0,0,0,0,0,0)
	SystemID_Global = SystemID
	
	If String_SystemDiscovered[SystemID] = 0
		Character_Value_XP = Character_Value_XP + 75
	EndIf
	
	String_SystemDiscovered[SystemID]=1
	
	Object_Environment[2] = LoadSkyBox(String_SystemRegion[SystemID])
	
	Select SystemID
		Case 2,6,7,11,17,18,22,23,24,27,28,30,31,33,38,39,40,42,49,51,52,56,60,61,63,72,79,81,84,85,87,88,89,91,92,95,96,97,103,104,107,108,109,110,120
			;[Block] Procedural Random Sectors
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=0
			
			
			;Randomizing for Procedural Creation
			Local OldSeed = RndSeed()
			SeedRnd MilliSecs()
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			;Random Sectors Random ores
			MaxBelts = Rand(0,4)
			MaxYield = Rand(5,50)
			If MaxBelts > 0 Then
				For A = 1 To Maxbelts
					Asteroid_Belt_Create(Rand(5,20),Rand(1,4),Rand(-String_SystemScale[SystemID],String_SystemScale[SystemID]),Rand(-String_SystemScale[SystemID],String_SystemScale[SystemID]),Rand(-String_SystemScale[SystemID],String_SystemScale[SystemID]),Rand(8000,25000),Rand(Maxyield/2,maxyield))
				Next
			EndIf
			
			SeedRnd OldSeed
			
			;[End Block]
			
		Case 0 ;[Block] Unknown, South Gate, Light Ore Field - DONE
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level = 0
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			Asteroid_Belt_Create(12,2,25000,800,-14000,12000,8)
			
			;To Do - can I do this mathematically
			
			
			Planet_Create(90000,60000,8,8,-45,0,15,0)
			
;			Minefield_Create(-23000,0,0,1600,250)
			
;			Asset_Station_Create(-15000,0,40000)
			
			;[End Block]
			
		Case 1 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			Asteroid_Belt_Create(15,4,10000,800,0,14000,55)
			
			;[End Block]
			
		Case 3 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			Asteroid_Belt_Create(25,2,10000,0,0,30000,15)
			Asteroid_Belt_Create(25,3,-10000,0,0,30000,15)
			
			
			;[End Block]
			
		Case 4 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			Asteroid_Belt_Create(20,4,0,0,0,80000,25)
			
			
			;[End Block]
			
		Case 5 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			Asteroid_Belt_Create(15,1,0,0,30000,40000,15)
			Asteroid_Belt_Create(15,1,19000,0,50000,40000,45)
			
			;[End Block]
			
		Case 8 ;[Block] Unknown
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			Asteroid_Belt_Create(15,3,10000,0,10000,40000,5)
			Asteroid_Belt_Create(15,2,-50000,0,30000,40000,25)
			
			
			;[End Block]
			
		Case 9 ;[Block] Greyloks Storm
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			Asset_Station_Create(15000,0,25000,-35)
			
			Planet_Create(33000,22000,14,5,5,1,92,2)
			
			
			;[End Block]
			
		Case 10 ;[Block] Lost Shores
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			Asteroid_Belt_Create(60,4,0,0,0,40000,5)
			
			
			;[End Block]
			
		Case 12 ;[Block] Mercury
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,60000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			Planet_Create(13000,5000,8,-20,30,0,0,0)
			
			Asset_Station_Create(5000,0,15000,45)
			
			;[End Block]
			
		Case 13 ;[Block] Venus
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			; Station And Worldbuilding
			
			Planet_Create(13000,8000,16,120,0,0,0,0)
			
			
			;[End Block]
			
		Case 14 ;[Block] Terra
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(180,50,21000)
			Modify_Ambient(228,222,200)
			
			; Station And Worldbuilding
			
			Planet_Create(20000,12000,18,120,0)
			Planet_Create(28000,2000,8,0,180,0)
			
			Asset_Station_Create(0,0,15000,90)
			
			;[End Block]
			
		Case 15 ;[Block] Luna (END OF ROW 1)
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(180,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			Planet_Create(20000,12000,8,0,120,0)
			Planet_Create(28000,1500,18,0,180,0)
			
			;[End Block]
			
		Case 16 ;[Block] Unknown 16
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			Asteroid_Belt_Create(45,1,0,0,0,40000,3)
			
			;[End Block]
			
		Case 19 ;[Block] Arctur
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,22000)
			Modify_Ambient(128,122,200)
			
			
			; Station And Worldbuilding
			
			Asset_Station_Create(0,0,15000,0)
			
			
			;[End Block]
			
		Case 20 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 21 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 25 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 26 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 29 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 32 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 34 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 35 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 36 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 37 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 40 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 41 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 43 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 44 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 45 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 46 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 47 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 48 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 50 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 53 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 54 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 55 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 57 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 58 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 59 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 62 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 64 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 65 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 66 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 67 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 68 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 69 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 70 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 71 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 73 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 74 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 75 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 76 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 77 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 78 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 80 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 82 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 83 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 86 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 90 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 93 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 94 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 98 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 99 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 100 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 101 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 102 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 105 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 106 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 111 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 112 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 113 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 114 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 115 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 116 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 117 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 118 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 119 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 121 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 122 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 123 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 124 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 125 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 126 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
		Case 127 ;[Block] Unknown X1 Y0
			
			; System Properties (Name and Faction)
			
			Switch_System_init = 0
			System_TextID = 2
			
			System_Name$ = String_SystemName[SystemID]
			CurrentZone = String_SystemFaction[SystemID]
			Security_Level=1
			
			;Light and Fog Modification
			
			Modify_Light(-45,50,19000)
			Modify_Ambient(228,222,200)
			
			
			; Station And Worldbuilding
			
			
			
			;[End Block]
			
			
			
		Default ;[Block] Fallback Demo System
			
			System_Name$ = "The Fall"
			CurrentZone = Faction_Pirate
			Security_Level=0
			
			Object_Zero=CreatePivot()
			AmbientLight 124, 111, 100
			
			Object_Environment[2] = LoadSkyBox(2)
			LightColor Object_Light[0],200,200,250
			EntityColor Object_Environment[1],200,200,250
			
			Modify_Light(-5,115,29000)
			
			Asteroid_Belt_Create(Rand(8,10),1,50000,0,50000,15000)
			Asteroid_Belt_Create(Rand(8,10),2,-50000,0,50000,15000)
			Asteroid_Belt_Create(Rand(8,10),3,-50000,0,-50000,15000)
			Asteroid_Belt_Create(Rand(28,50),4,50000,0,-50000,15000)
			
			CreateGate(999,1000,0,10000,0,0,0)
			
;			Override_Spawns = 1
			; Station
			
			; Gates
			;[End Block]
			
	End Select
	
	
	;Remember, check the Map array for next sector each direction
	;Find nearest North Sector, abort if index < 15
	
	;[Block] Gate Creation By Index
	;Filter Goes Here, no need to calculate next north sector for a system that has no north gate
	Local NextGate_N=0
	Local NextGate_E=0
	Local NextGate_S=0
	Local NextGate_W=0
	Local GateFinder
	
	If SystemID > 15 ;[Block] Filter North
		For GateFinder = 1 To 3
			If SystemID-(GateFinder*16) < 0 Then Exit
			If String_SystemName[SystemID-(GateFinder*16)]<>"Unmapped Sector" Then
				NextGate_N=SystemID-(GateFinder*16)
				Exit
			EndIf
		Next
	EndIf ;[End Block]
	If SystemID > 0
		For GateFinder = 1 To 4
			If String_SystemName[SystemID+GateFinder]<>"Unmapped Sector" Then
				NextGate_E=SystemID+(GateFinder)
				Exit
			EndIf
		Next
	EndIf
	If SystemID < 111
		For GateFinder = 1 To 3
			If SystemID+(GateFinder*16)>127 Then Exit
			If String_SystemName[SystemID+(GateFinder*16)]<>"Unmapped Sector" Then
				NextGate_S=SystemID+(GateFinder*16)
				Exit
			EndIf
		Next
	EndIf
	If SystemID < 127
		For GateFinder = 1 To 4
			If String_SystemName[SystemID-GateFinder]<>"Unmapped Sector" Then
				NextGate_W=SystemID-(GateFinder)
				Exit
			EndIf
		Next
	EndIf
	
	If NextGate_N < 0 Then NextGate_N = 0
	If NextGate_N > 127 Then NextGate_N = 127
	
	If NextGate_E < 0 Then NextGate_E = 0
	If NextGate_E > 127 Then NextGate_E = 127
	
	If NextGate_S < 0 Then NextGate_S = 0
	If NextGate_S > 127 Then NextGate_S = 127
	
	If NextGate_W < 0 Then NextGate_W = 0
	If NextGate_W > 127 Then NextGate_W = 127
	String_SystemScale[SystemID] = String_SystemScale[SystemID]
	Select String_SystemGate[SystemID]
		Case 1 ;North
			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
			
		Case 2 ;East
			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
			
		Case 3 ;North + East
			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
			
			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
			
		Case 4 ;South
			CreateGate((NextGate_E),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
			
		Case 5 ;South + North
			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
			
			CreateGate((NextGate_S),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
			
		Case 6 ;South + East
			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
			
			CreateGate((NextGate_S),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
			
		Case 7 ;South + East + North
			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
			
			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
			
			CreateGate((NextGate_S),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
			
		Case 8 ;West
			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
			
		Case 9 ;North + West
			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
			
			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
			
		Case 10 ;West + East
			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
			
			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
			
		Case 11 ; North + East + West
			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
			
			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
			
			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
			
		Case 12 ;West + South
			CreateGate((NextGate_S),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
			
			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
			
		Case 13 ; West + South + North
			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
			
			CreateGate((NextGate_S),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
			
			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
			
		Case 14 ;West + East + South
			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
			
			CreateGate((NextGate_S),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
			
			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
			
		Case 15 ;All gates
			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
			
			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
			
			CreateGate((NextGate_S),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
			
			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
			
	End Select
	;[End Block]
	
	AmbientLight 60, 55, 50
	
	SeedRnd MilliSecs()
	
	;HidingLostSHips
;	Local Random_Shiploss = Rand(1,10)
;	If Random_Shiploss = 5 Then
;	EndIf
	
;	Derelict_Create(10000,2000,0,3)
;	Derelict_Create(10000,3000,0,4)
	FastTravel(TX#,TY#,TZ#)
End Function

Function World_Clear()
	
	FreeEntity Object_Zero
	
;	For dst_zone.DST_Dustzone = Each DST_Dustzone
;		VirtualScene_Unregister(Scene, dst_zone\pivot)
;		FreeEntity dst_zone\pivot
;		Delete dst_zone
;	Next
	
;	For dst_zone.DST_Dustzone = Each DST_Dustzone
;		Delete dst_zone
;	Next
	
;	For Hot.Fire = Each Fire
;		FreeEntity Hot\mesh: VirtualScene_Unregister(Scene, Hot\Mesh)
;		Delete Hot
;	Next
;	
	For Can.ItemDrop = Each ItemDrop
		FreeEntity Can\Mesh: VirtualScene_Unregister(Scene, Can\mesh)
		Delete Can
	Next
	
;	For Engine.Flare = Each Flare
;		FreeEntity Engine\Mesh
;		VirtualScene_Unregister(Scene,Engine\Mesh)
;		Delete Engine
;	Next
	
	For Effect.Explosion = Each Explosion
		FreeEntity Effect\Sprite
		VirtualScene_Unregister(Scene, Effect\Sprite)
		Delete Effect
	Next
	
	For roid.belt = Each Belt
		VirtualScene_Unregister(Scene, roid\mesh) : FreeEntity roid\mesh
		Delete roid
	Next
	
	For da.station = Each Station
		VirtualScene_Unregister(Scene, da\mesh_essential) : FreeEntity da\mesh_essential
		VirtualScene_Unregister(Scene, da\mesh_factory) : FreeEntity da\mesh_Factory
		VirtualScene_Unregister(Scene, da\mesh_power) : FreeEntity da\mesh_Power
		VirtualScene_Unregister(Scene, da\mesh_ring) : FreeEntity da\mesh_Ring
		Delete da
	Next
	
	For SC.ScrapField = Each ScrapField
		VirtualScene_Unregister(Scene, sc\mesh): FreeEntity sc\mesh
		Delete sc
	Next
	
	For Portal.Stargate = Each Stargate
		VirtualScene_Unregister(Scene, Portal\Mesh_Effect_Horizon): FreeEntity Portal\Mesh_Effect_Horizon
		VirtualScene_Unregister(Scene, Portal\Mesh): FreeEntity Portal\Mesh
		VirtualScene_Unregister(Scene, Portal\Mesh_effect): FreeEntity Portal\Mesh_Effect
		Delete Portal
	Next
	
	VirtualScene_Unregister(Scene, Object_Environment[2])
	FreeEntity Object_Environment[2]
	
	For XY.MapWaypoints = Each MapWaypoints
		Delete XY
	Next
	
	For TP.TPlanet = Each TPlanet
		VirtualScene_Unregister(Scene, TP\Ring): FreeEntity TP\Ring
		VirtualScene_Unregister(Scene, TP\Mesh): FreeEntity TP\Mesh
		Delete TP
	Next
	
	For GFlag.Flag = Each Flag
		FreeEntity GFlag\Mesh: VirtualScene_Unregister(Scene, GFlag\Mesh)
		Delete GFlag
	Next
	
End Function

Function CreateGate(Target_System, PosX, PosY, PosZ, TX, TY, TZ)
	
	Portal.Stargate = New Stargate
	Portal\Mesh = CopyEntity(Mesh_Gate[1])
	Portal\Mesh_Effect = CopyEntity(Mesh_Gate[2])
	Portal\Mesh_Effect_Horizon = CopyEntity(Mesh_Gate[3])
	Portal\JumpOBB = CreateOBB(PosX, PosY, PosZ, 0, 0, 0, 4500, 2000, 60)
	Portal\Target_Sky=TSky
	
;	Gateflag_Create(PosX,PosY,PosZ,String_SystemFaction[Target_System],1)
;	Gateflag_Create(PosX,PosY,PosZ,String_SystemFaction[Target_System],2)
	
	ScaleEntity Portal\Mesh,10,10,10
	ScaleEntity Portal\Mesh_Effect_Horizon,10,10,10
	ScaleEntity Portal\Mesh_effect,9.99,9.99,9.99
	
	
	PositionEntity Portal\Mesh,PosX,PosY,PosZ
	PositionEntity Portal\Mesh_Effect,PosX,PosY,PosZ
	PositionEntity Portal\Mesh_Effect_Horizon,PosX,PosY,PosZ
	
	EntityFX Portal\Mesh_Effect,1+16
	EntityFX Portal\Mesh_Effect_Horizon,1
	
	Portal\Target_System = Target_System
	Portal\TX = TX
	Portal\TY = TY
	Portal\TZ = TZ
	Portal\Target_Sky = String_SystemRegion[Target_System]
	
	EntityTexture Portal\Mesh_effect, Text_SkyCM[Portal\Target_Sky],0
	EntityTexture Portal\Mesh_effect_Horizon, Text_Gate[0],0
	
	EntityAlpha Portal\Mesh_effect,0.92
	EntityAlpha Portal\Mesh_effect_Horizon,0.4
	
	VirtualScene_Register(Scene,Portal\Mesh)
	VirtualScene_Register(Scene,Portal\Mesh_Effect)
	VirtualScene_Register(Scene,Portal\Mesh_Effect_HOrizon)
	
	EntityAutoFade portal\mesh_effect, 20000,21000
	
	PointEntity Portal\mesh,Object_Zero
	PointEntity Portal\mesh_effect,Object_Zero
	PointEntity Portal\mesh_effect_horizon,Object_Zero
	PointEntity Portal\JumpOBB,Object_Zero
	
	MoveEntity portal\mesh_effect,0,0,-20
	
	EntityAutoFade portal\mesh_effect_horizon, 35000,37000
	
	
	CreateMapPoint(PosX, PosY, PosZ,4)
	
;	TraderPOI_Create(PosX, PosY, PosZ, 1)
	
End Function

Function UpdateGates()
	Local Gate_Jump = 0
	For Portal.Stargate = Each Stargate
		
		If EntityDistance(pvShip, portal\mesh)<10000 And EntityInView(Portal\Mesh,WorldCamera)=True And HUD=1 Then
			Text3D(Text_Font[31],0,D3DOU-440,"Gate: "+String_SystemName[Portal\Target_System],1)
		EndIf
		
		MoveEntity Portal\mesh_effect,0,0,Sin(MilliSecs())
		
		If EntityInOBB(Portal\JumpOBB, eShip) Then
			Local Future_System = Portal\Target_System
			Local TX = Portal\TX
			Local TY = Portal\TY
			Local TZ = Portal\TZ
			If GUI_Cooldown[0] < 1 Then
				Gate_Jump = 1
				GUI_Cooldown[0] = 60
			EndIf
			Exit
		EndIf
	Next
	If Gate_Jump = 1 Then
		World_Clear()
		Character_Value_System=Future_System
		World_Generate(Future_System, TX, TY, TZ)
	EndIf
End Function

Function Asteroid_Belt_Create(NumberOfRoids, TypeOfYield, BeltX, BeltY, BeltZ, BeltRange, AvgYield=15)
;	TraderPOI_Create(BeltX, BeltY, BeltZ, 0)
	For i = 1 To NumberOfRoids*80
		Roid.Belt=New Belt
		Roid\x#=Rand(-90+Angle,90+Angle)		
		Roid\y#=Rand(-180,180)
		Roid\z#=Rand(BeltRange/10,BeltRange*5)
		
		Roid\Chance=Rand(ChanceofYield-5,ChanceofYield+5)
		Roid\maxyield=Roid\chance
		
		Roid\Toy=TypeOfYield
		
		
		BaseCollisionSize = 2
		
		Local RoidCopy
		Select TypeOfYield
			Case 1
				SpreadOrRes = Rand (1,5)
				If SpreadOrRes>3 Then
					TypeOfYield_Fixed=4
				Else
					TypeOfYield_Fixed = TypeOfYield
				EndIf
			Case 2
				SpreadOrRes = Rand (1,5)
				If SpreadOrRes>2 Then
					TypeOfYield_Fixed=4
				Else
					TypeOfYield_Fixed = TypeOfYield
				EndIf
			Case 3
				SpreadOrRes = Rand (1,5)
				If SpreadOrRes>1 Then
					TypeOfYield_Fixed=4
				Else
					TypeOfYield_Fixed = TypeOfYield
				EndIf
			Case 4
				TypeOfYield_Fixed = 5
		End Select
		
		Roid\siz#=Rnd(.8,3)
		
		Select TypeOfYield_Fixed
			Case 1
				
				roid\Mesh=CopyEntity(Mesh_Roid[1], PVTxNORMAL)
				EntityTexture Roid\Mesh,Text_Roid[1],0,0
;				EntityTexture Roid\Mesh,Text_Roid_Illumination[1],0,1
				roid\YAmount#=AvgYield+Rand(-5,5)
				NameEntity Roid\Mesh, "Iron Asteroid ("+Roid\YAmount#+" m³ left)"
				ScaleEntity Roid\Mesh,Roid\siz#,Roid\siz#,Roid\siz#
			Case 2
				
				roid\Mesh=CopyEntity(Mesh_Roid[1], PVTxNORMAL)
				EntityTexture Roid\Mesh,Text_Roid[2],0,0
				roid\YAmount#=AvgYield+Rand(-5,5)
				NameEntity Roid\Mesh, "Copper Asteroid ("+Roid\YAmount#+" m³ left)"
				ScaleEntity Roid\Mesh,Roid\siz#,Roid\siz#,Roid\siz#
			Case 3
				
				roid\Mesh=CopyEntity(Mesh_Roid[1], PVTxNORMAL)
				EntityTexture Roid\Mesh,Text_Roid[3],0,0
				EntityTexture Roid\Mesh,Text_Roid_Illumination[3],0,1
				roid\YAmount#=AvgYield+Rand(-5,5)
				NameEntity Roid\Mesh, "Gold Asteroid ("+Roid\YAmount#+" m³ left)"
				ScaleEntity Roid\Mesh,Roid\siz#,Roid\siz#,Roid\siz#
			Case 5
				
				roid\Mesh=CopyEntity(Mesh_Roid[2], PVTxNORMAL)
				EntityTexture Roid\Mesh,Text_Roid[4],0,0
;				EntityAlpha Roid\mesh,0.8
				roid\YAmount#=AvgYield+Rand(-5,5)
				NameEntity Roid\Mesh, "Ice Asteroid ("+Roid\YAmount#+" m³ left)"
				EntityAlpha roid\mesh,0.9
				EntityFX roid\mesh,32
				ScaleEntity Roid\Mesh,Roid\siz#*Rnd(1,2),Roid\siz#*Rnd(1,2),Roid\siz#*Rnd(1,2)
			Case 4
				roid\Mesh=CopyEntity(Mesh_Roid[3], PVTxNORMAL)
				EntityTexture Roid\Mesh,Text_Roid[1],0,0
				NameEntity Roid\Mesh, "Asteroid Remnants"
				ScaleEntity Roid\Mesh,Roid\siz#,Roid\siz#,Roid\siz#
			Case 6 ;Icy Debris
				
				
		End Select
		
		VirtualScene_Register(Scene, Roid\Mesh)
		
		PositionEntity roid\mesh, BeltX, BeltY, BeltZ
		Roid\tur#=Rnd(0.1,359)
		RotateEntity Roid\Mesh, Roid\y#, Roid\x#, roid\z#
		
		
		
		Roid\rot#=Rnd(-.021,.021)
		
		
		
		MoveEntity Roid\Mesh,0,0, Roid\z
		TurnEntity roid\mesh,Rand(0,360),Rand(0,360),Rand(0,360)
		EntityAutoFade Roid\Mesh, 160000,161000
		If TypeOfYield_Fixed = 5 Then
			PointEntity Roid\mesh,Object_Light[0]
		EndIf
		
		If TypeOfYield_Fixed = 5 Then TurnEntity roid\mesh,0,0,Rnd(0.0,359.0)
	Next
	
	CreateMapPoint(BeltX, BeltY, BeltZ, 1)
End Function

Function UpdateBelt()
	For Roid.Belt = Each Belt
		Roid\x#=EntityX(Roid\Mesh)
		Roid\y#=EntityY(Roid\Mesh)
		Roid\z#=EntityZ(Roid\Mesh)
		Local NameYield
		CameraProject(WorldCamera,Roid\X#, Roid\Y#, Roid\Z#)
		
		MoveEntity Roid\mesh,0,Sin((MilliSecs()/50)),0
		
		If Ship_Function_Scanner= 1 Then
			Local NameRoid$=EntityName(Roid\Mesh)
			If Instr(NameRoid,"Iron")
				NameYield = 1
			ElseIf Instr(NameRoid,"Copper")
				NameYield = 2
			ElseIf Instr(NameRoid,"Gold")
				NameYield = 3
			ElseIf Instr(NameRoid,"Ice")
				NameYield = 4
			EndIf
			
			Select NameYield
				Case NameYield = 1
					EntityColor roid\mesh,255,100,100
				Case NameYield = 2
					EntityColor roid\mesh,255,150,100
				Case NameYield = 3
					EntityColor roid\mesh,255,255,255
				Case NameYield = 4
					EntityColor roid\mesh,128,128,255
			End Select
			EntityFX roid\mesh,1
		Else
			EntityColor roid\mesh,128,128,128
			EntityFX roid\mesh,0
		EndIf
		
		If EntityDistance(eShipBody,roid\mesh)<9000 And EntityInView(roid\mesh,WorldCamera) = True And Ship_Function_Scanner = 1 And HUD = 1
			Text3D(Text_Font[6],D3DOL-(ProjectedX#()*-1), D3DOU-ProjectedY#(),EntityName(Roid\Mesh))
		EndIf
	Next
End Function

Type ScrapField
	Field X,Y,Z
	Field Mesh, MaxTurn#
	Field decaytimer
End Type

Function Scrapfield_Create(amount,x,y,z,span, MaxScale#=2, Maxturn#=1, Minscale#=0.8, Fading=0)
	For Scraps = 1 To amount
		SC.ScrapField=New ScrapField
		Mesh=Rand(1,4)
		SC\Mesh=CopyEntity(Mesh_Scrap[Mesh])
		SC\Maxturn#=Maxturn#
		PositionEntity SC\Mesh,x,y,z
		RotateEntity SC\Mesh,Rand(-180,180),Rand(-180,180),Rand(-180,180)
		MoveEntity SC\Mesh, 0, 0,Rand(150,span)
		EntityTexture SC\Mesh, Text_Scrap
		Local Scale#=Rnd(Minscale#,MaxScale#)
		ScaleEntity SC\Mesh,Scale#, Scale#, Scale#
		VirtualScene_Register(Scene,SC\Mesh)
		EntityAutoFade SC\Mesh, 70000,72500
		If Fading=1 Then SC\decaytimer=1
	Next
	If Fading=0 Then
		CreateMapPoint(x,y,z,3)
	EndIf
End Function

Function Scrapfield_Update()
	For SC.ScrapField = Each ScrapField
		TurnEntity SC\Mesh,.12+SC\maxturn#,.12+SC\maxturn#,.12+SC\maxturn#
		If SC\Maxturn#>0 Then SC\MAxturn=Sc\MaxTurn-0.01
		If SC\decaytimer>0 Then SC\decaytimer=SC\decaytimer+1
		
		If SC\decaytimer>150 Then
			
			Removestate=1
			
		EndIf
		
		For Player.Player_Bullet = Each Player_Bullet
			If EntityDistance(player\mesh, sc\mesh)<50 Then
				
				LootChance=Rand(1,15)
				If Lootchance=15 Then
					
				Else
					
				EndIf
				
				FreeEntity player\mesh:VirtualScene_Unregister(Scene, player\mesh)
				Delete player
				
				Removestate=1
				
			EndIf
		Next
		
		If Removestate=1 Then
			FreeEntity sc\mesh: VirtualScene_Unregister(Scene, sc\mesh)
			Delete sc
			Exit
		EndIf
		
	Next
End Function

Function Tweakseed()
    
    Local temp%=(seed[0]+seed[1]+seed[2]) Mod $10000
    seed[0]=seed[1]
    seed[1]=seed[2]
    seed[2]=temp
    
End Function

Function CreateName$()
    
    Local longnameflag=seed[0] And $40
    Local planetname$=""
    Local c%,n%,d%
    
    For n=0 To 3
        
        d=((seed[2] Shr 8) And $1f) Shl 1
        Tweakseed()
        
        If n<3 Or longnameflag Then
			
            planetname=planetname+Mid(syllables,1+d,2)
            planetname=Replace(planetname,".","")
            
        EndIf
        
    Next
    
    planetname=Upper(Mid(planetname,1,1))+Mid(planetname,2,Len(planetname)-1)
    
    Return planetname
    
End Function

Function Battlefield_Update()
	
	If CurrentZone = Faction_Police Then
		
		Local Amount_FactionA, Amount_FactionB
		
		Existing_Total = Existing_Sirian + Existing_Terran
		
		Existing_Ratio_Total = Existing_Total
		
		If Existing_Ratio_Total > 0 Then
			Existing_Ratio_Sirian = Existing_Sirian * 100 / Existing_Ratio_Total
			Existing_Ratio_Terran = Existing_Terran * 100 / Existing_Ratio_Total
		EndIf
		
		If Respawn_Timer < 1 Then
			RespawnA=Rand(3,7)
			If Existing_Sirian > 0 And Existing_Sirian < 8
				
				For A = 1 To RespawnA
;					AI_Spawn(Rand(-40000,40000),Rand(-400,400),Rand(-40000,40000),Faction_Sirian, )
				Next
			EndIf
			
			RespawnA=Rand(3,7)
			If Existing_Terran > 0 And Existing_Terran < 8
				For A = 1 To RespawnA
;					AI_Spawn(Rand(-40000,40000),Rand(-400,400),Rand(-40000,40000),Faction_Terran, )
				Next
			EndIf
			Respawn_Timer=50
			Capital_Respawn = Rand(4,6)
			If Capital_Respawn = 6 Then
;				PlaySound Sound_Explosion[4]
;				AI_Spawn(Rand(-40000,40000),Rand(-400,400),Rand(-40000,40000),Rand(Faction_Terran,Faction_Sirian),Job_Capital,1)
			EndIf
			
		EndIf
		
		If State_System_Ownership = 0 Then
			If Existing_Terran < 5 And Existing_Terran < Existing_Sirian Then
;				PlaySound Sound_Battlefield[5]
				State_System_Ownership=-1
			ElseIf Existing_Sirian < 5 And Existing_Sirian < Existing_Terran Then
;				PlaySound Sound_Battlefield[7]
				State_System_Ownership=1
			EndIf
		EndIf
		
		If Existing_Terran > 4 And Existing_Sirian > 4
			State_System_Ownership = 0
		EndIf
		
		If Existing_Terran = 0 And Switch_System_init > 2 Then 
;			PlaySound Sound_Battlefield[6]
			If Character_Value_Faction = Faction_Sirian Then 
				Character_Value_XP = Character_Value_XP + (12500 * Upgrade_XP_Modifier)
				AddChat("Gained "+(12500*Upgrade_XP_Modifier)+"XP for claiming a System!","Reward")
			EndIf
			CurrentZone = Faction_Sirian
		ElseIf Existing_Sirian = 0 And Switch_System_init > 2 Then
;			PlaySound Sound_Battlefield[8]
			If Character_Value_Faction = Faction_Terran Then 
				Character_Value_XP = Character_Value_XP + (12500 * Upgrade_XP_Modifier)
				AddChat("Gained "+(12500*Upgrade_XP_Modifier)+"XP for claiming a System!","Reward")
			EndIf
			CurrentZone = Faction_Terran
		EndIf
		
		
	EndIf
	
	If HUD = 1 
		Select CurrentZone
			Case Faction_Police
				If Existing_Ratio_Total > 0 Then
					Text3D(Text_Font[31],0,D3DOU-40,"Time for reinforcements: "+Respawn_Timer+"s",1)
					DrawImage3D(GUI_Windows[14],0,D3DOU-100)
					Local StartA = -99
					Local StartB =  99
					Select Character_Value_Faction
						Case Faction_Sirian
							DrawImage3D(GUI_MainMenu_SImages[4],-128,D3DOU-100,0,0,0.2)
							DrawImage3D(GUI_MainMenu_SImages[5], 128,D3DOU-100,0,0,0.2)
							
							Amount_FactionA = Existing_Ratio_Sirian*2
							Amount_FactionB = Existing_Ratio_Terran*2
							
							For	PaintX = 1 To Amount_FactionA
								DrawImage3D(GUI_Buttons[7],StartA,D3DOU-100)
								StartA=StartA+1
							Next
							
							For PaintX = 1 To Amount_FactionB
								DrawImage3D(GUI_Buttons[8],StartB,D3DOU-100)
								StartB=StartB-1
							Next
							
							Text3D(Text_Font[31], 128,D3DOU-140,Existing_Sirian+" Ships",1)
							Text3D(Text_Font[31],-128,D3DOU-140,Existing_Terran+" Ships",1)
							
						Case Faction_Terran
							DrawImage3D(GUI_MainMenu_SImages[5],-128,D3DOU-100,0,0,0.2)
							DrawImage3D(GUI_MainMenu_SImages[4], 128,D3DOU-100,0,0,0.2)
							
							Amount_FactionB = Existing_Ratio_Sirian*2
							Amount_FactionA = Existing_Ratio_Terran*2
							
							For PaintX = 1 To Amount_FactionA
								DrawImage3D(GUI_Buttons[7],StartA,D3DOU-100)
								StartA=StartA+1
							Next
							
							For PaintX = 1 To Amount_FactionB
								DrawImage3D(GUI_Buttons[8],StartB,D3DOU-100)
								StartB=StartB-1
							Next
							
							Text3D(Text_Font[31], 128,D3DOU-140,Existing_Sirian+" Ships",1)
							Text3D(Text_Font[31],-128,D3DOU-140,Existing_Terran+" Ships",1)
							
					End Select
				EndIf
				
				DrawImage3D(GUI_Rank[Character_Value_Level],D3DOL+70,D3DOU-70)
				Text3D(Text_Font[14],D3DOL+70,D3DOU-112,String_Rank[Character_Value_Level],1)
				Text3D(Text_Font[11],D3DOL+70,D3DOU-132,Level_Progress+"%",1)
				
				Text3D(Text_Font[14],0, D3DOU - 170,System_Name$,1)
				Text3D(Text_Font[11],0, D3DOU - 190,CurrentZoneText$,1)
			Case Faction_Sirian
				
				If Character_Value_Faction = Faction_Terran	Then
					If Respawn_Timer < 1 Then
						RespawnA=Rand(3,7)
						If Existing_Terran > 0 And Existing_Terran < 8
							For A = 1 To RespawnA
;								AI_Spawn(EntityX(pvShip)+Rand(-10000,10000),EntityY(pvShip)+Rand(-10000,10000),EntityZ(pvShip)+Rand(-10000,10000),Faction_Sirian, )
							Next
						EndIf
						Respawn_Timer=45
					EndIf
					Text3D(Text_Font[31],0,D3DOU-40,"Time for reinforcements: "+Respawn_Timer+"s",1)
					
					Text3D(Text_Font[31],0,D3DOU-260,"--> HOSTILE SPACE <--",1)
				EndIf
				
				DrawImage3D(GUI_MainMenu_SImages[4],0,D3DOU-100,0,0,0.5)
				
				DrawImage3D(GUI_Rank[Character_Value_Level],D3DOL+70,D3DOU-70)
				Text3D(Text_Font[14],D3DOL+70,D3DOU-112,String_Rank[Character_Value_Level],1)
				Text3D(Text_Font[11],D3DOL+70,D3DOU-132,Level_Progress+"%",1)
				
				Text3D(Text_Font[14],0, D3DOU - 170,System_Name$,1)
				Text3D(Text_Font[11],0, D3DOU - 190,CurrentZoneText$,1)
			Case Faction_Terran
				
				If Character_Value_Faction = Faction_Sirian	Then
					If Respawn_Timer < 1 Then
						RespawnA=Rand(3,7)
						If Existing_Terran > 0 And Existing_Terran < 8
							For A = 1 To RespawnA
;								AI_Spawn(EntityX(pvShip)+Rand(-10000,10000),EntityY(pvShip)+Rand(-10000,10000),EntityZ(pvShip)+Rand(-10000,10000),Faction_Terran, )
							Next
						EndIf
						Respawn_Timer=45
					EndIf
					Text3D(Text_Font[31],0,D3DOU-40,"Time for reinforcements: "+Respawn_Timer+"s",1)
					
					Text3D(Text_Font[31],0,D3DOU-260,"--> HOSTILE SPACE <--",1)
				EndIf
				
				DrawImage3D(GUI_MainMenu_SImages[5],0,D3DOU-100,0,0,0.5)
				DrawImage3D(GUI_Rank[Character_Value_Level],D3DOL+70,D3DOU-70)
				Text3D(Text_Font[14],D3DOL+70,D3DOU-112,String_Rank[Character_Value_Level],1)
				Text3D(Text_Font[11],D3DOL+70,D3DOU-132,Level_Progress+"%",1)
				
				Text3D(Text_Font[14],0, D3DOU - 170,System_Name$,1)
				Text3D(Text_Font[11],0, D3DOU - 190,CurrentZoneText$,1)
				
			Case Faction_Neutral
				DrawImage3D(GUI_Rank[Character_Value_Level],D3DOL+70,D3DOU-70)
				Text3D(Text_Font[14],D3DOL+70,D3DOU-112,String_Rank[Character_Value_Level],1)
				Text3D(Text_Font[11],D3DOL+70,D3DOU-132,Level_Progress+"%",1)
				
				Text3D(Text_Font[14],0, D3DOU - 170,System_Name$,1)
				Text3D(Text_Font[11],0, D3DOU - 190,CurrentZoneText$,1)
				
			Default
				
				DrawImage3D(GUI_Rank[Character_Value_Level],D3DOL+70,D3DOU-70)
				Text3D(Text_Font[14],D3DOL+70,D3DOU-112,String_Rank[Character_Value_Level],1)
				Text3D(Text_Font[11],D3DOL+70,D3DOU-132,Level_Progress+"%",1)
				
				Text3D(Text_Font[14],0, D3DOU - 170,System_Name$,1)
				Text3D(Text_Font[11],0, D3DOU - 190,CurrentZoneText$,1)
				
		End Select
	EndIf
	
End Function

Function WorldTimers_Update()
	Twitch_EventTimer = Twitch_EventTimer - 1
	Respawn_Timer = Respawn_Timer - 1
	Music_Aggro_Timer = Music_Aggro_Timer - 1
	Timer_Storyline = Timer_Storyline - 1
	Title_Timer = Title_Timer - 1
	Timer_Hacking = Timer_Hacking - 1
	
	For Buff.Advantage = Each Advantage
		Buff\Duration = Buff\Duration - 1
	Next
	
;	For Mine.MField = Each MField
;		If Mine\Timer > 0 Then Mine\Timer = Mine\Timer - 1
;	Next
	
End Function

Type Flag
	Field Mesh, TLevel
End Type

Function Gateflag_Create(x,y,z,texture,subtype=1)
	GFlag.Flag = New Flag
	GFlag\Mesh = CreateSprite()
	SpriteViewMode GFlag\mesh, 2
	PositionEntity GFlag\Mesh,x,y,z
	PointEntity GFlag\mesh,Object_Zero
	ScaleSprite GFLag\Mesh,1500,1500
	EntityTexture GFlag\Mesh,Text_Flag[texture]
	EntityFX GFLag\Mesh,1+16+32
	If subtype = 1 Then MoveEntity GFlag\Mesh,12000,0,0
	If subtype = 2 Then MoveEntity GFlag\Mesh,-12000,0,0
	VirtualScene_Register(Scene,GFlag\mesh)
End Function

Function Worldmap_Display()
	Local Mapdist=80
	
	If HUD=1 And MAPHUD = 1 And Options = 0 And Upgrade_GlobalMap = 1
		DrawImage3D(GUI_WorldMap[9],0,0)
		DrawImage3D(GUI_Interface[0],MouseX()-(GraphicsWidth()/2),-MouseY()+(GraphicsHeight()/2))
		For A = 0 To 127
			
			If String_SystemDiscovered[a] = 1 And String_SystemName[a]<>"Unmapped Sector" Then
				Select String_SystemFaction[a]
					Case Faction_Pirate, Faction_Crimson
						DrawImage3D(GUI_WorldMap[1],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case Faction_Neutral
						DrawImage3D(GUI_WorldMap[2],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case Faction_Terran
						DrawImage3D(GUI_WorldMap[3],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case Faction_Sirian
						DrawImage3D(GUI_WorldMap[4],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case Faction_Battleground_OS, Faction_Battleground_TO, Faction_Battleground_TS
						DrawImage3D(GUI_WorldMap[5],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case Faction_Unknown
						DrawImage3D(GUI_WorldMap[7],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case Faction_Orion
						DrawImage3D(GUI_WorldMap[8],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
				End Select
				
				Select String_SystemGate[a]
					Case 1
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case 2
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
					Case 3
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
					Case 4
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
					Case 5
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
					Case 6
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
					Case 7
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
					Case 8
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
					Case 9
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case 10
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
					Case 11
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
					Case 12
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
					Case 13
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,0)
					Case 14
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
					Case 15
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
				End Select
				
				Text3D(Text_Font[11],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist-18,String_SystemName[a],1)
				
			EndIf
		Next
		
		DrawImage3D(GUI_WorldMap[0],0,0)
		
	Else
		
	EndIf
End Function

Type RaceTrack
	Field Mesh, ID
End Type

Function RaceTrack_Create(StartX, StartY, StartZ, Segments)
    Local PitchRange# = 60.0    ; Min/Max Pitch Änderung
    Local YawRange# = 60.0        ; Min/Max Yaw Änderung
    Local CurveStrength# = 0.4    ; Stärke der Änderung relativ zu vorher (1.0 = Voll, 2.0 = Doppelt, 0.5 = Hälfte). Niedrigere Werte führen zu besseren Ergebnissen
    Local GateDistance# = 4500.0; Distanz einzelner Tore
    Local LastGateEntity = 0
	Racetrack_Max=Segments
	
    For Idx = 1 To Segments
        Local TrackSegment.RaceTrack = New RaceTrack
        TrackSegment\Mesh = CopyEntity(Mesh_Gate[4])
        VirtualScene_Register(Scene, TrackSegment\Mesh)
        RaceTrack_BaseID = RaceTrack_BaseID + 1
        TrackSegment\ID = RaceTrack_BaseID
		
        If LastGateEntity <> 0
            PositionEntity TrackSegment\Mesh, EntityX(LastGateEntity,1), EntityY(LastGateEntity,1), EntityZ(LastGateEntity,1), 1
            RotateEntity TrackSegment\Mesh, EntityPitch(LastGateEntity,1), EntityYaw(LastGateEntity,1), EntityRoll(LastGateEntity,1), 1
			
            Local RndPitch# = Rnd(-PitchRange, +PitchRange) * CurveStrength
            Local RndYaw# = Rnd(-YawRange, +YawRange) * CurveStrength
            TurnEntity TrackSegment\Mesh, RndPitch, RndYaw, 0
			
            MoveEntity TrackSegment\Mesh, 0, 0, GateDistance
			
            PointEntity LastGateEntity, TrackSegment\Mesh
        Else
            PositionEntity TrackSegment\Mesh, StartX, StartY, StartZ
            RotateEntity TrackSegment\Mesh, Rnd(-360, 360), Rnd(-360, 360), Rnd(-360, 360)
        EndIf
        LastGateEntity = TrackSegment\Mesh
    Next
End Function

Function RaceTrack_Update()
	For TrackSegment.Racetrack = Each RaceTrack
		MoveEntity TrackSegment\Mesh,0,(Sin(MilliSecs()+TrackSegment\ID)/10),0
		If RaceTrack_Active = TrackSegment\ID Then
			EntityColor TrackSegment\Mesh,0,255,0
			If EntityDistance(TrackSegment\Mesh,eShipBody)<850 Then
				RaceTrack_Active = RaceTrack_Active + 1
			EndIf
		Else
			EntityColor TrackSegment\Mesh,255,0,0
		EndIf
		
	Next
	If RaceTrack_Active>Racetrack_Max Then RaceTrack_Active=1
	
End Function


;~IDEal Editor Parameters:
;~C#Blitz3D