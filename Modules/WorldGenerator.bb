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
	
	Existing_POI = 0
	
	Universe_Seed = 282103*SystemID
	SeedRnd(Universe_Seed)
	
	Object_Zero=CreatePivot()
	If SystemID<0 Or SystemID>127 Then SystemID = 0
	
	;Modify_Fog(0,0,0,0,0,0)
	SystemID_Global = SystemID
	
	String_SystemDiscovered[SystemID]=1
	
;	Object_Environment[2] = LoadSkyBox(1)
	
	Select SystemID
		Case 2,6,7,11,17,18,22,23,24,27,28,30,31,33,38,39,40,42,49,51,52,56,60,61,63,72,79,81,84,85,87,88,89,91,92,95,96,97,103,104,107,108,109,110,120
			;[Block] Procedural Random Sectors
			
;			Switch_System_init = 0
;			System_TextID = 2
;			
;			System_Name$ = String_SystemName[SystemID]
;			CurrentZone = String_SystemFaction[SystemID]
;			Security_Level=0
;			
			
			;Randomizing for Procedural Creation
			Local OldSeed = RndSeed()
			SeedRnd MilliSecs()
			
;			Modify_Light(-45,50,19000)
;			Modify_Ambient(228,222,200)
			
			;Random Sectors Random ores
;			MaxBelts = Rand(0,4)
;			MaxYield = Rand(5,50)
;			If MaxBelts > 0 Then
;				For A = 1 To Maxbelts
;					Asteroid_Belt_Create(Rand(5,20),Rand(1,4),Rand(-String_SystemScale[SystemID],String_SystemScale[SystemID]),Rand(-String_SystemScale[SystemID],String_SystemScale[SystemID]),Rand(-String_SystemScale[SystemID],String_SystemScale[SystemID]),Rand(8000,25000),Rand(Maxyield/2,maxyield))
;				Next
;			EndIf
			
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
			
			
			
;			Asteroid_Belt_Create(12,2,25000,800,-14000,12000,8)
			
			;To Do - can I do this mathematically
			
			
;			Planet_Create(90000,60000,8,8,-45,0,15,0)
			
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
			
;			Asteroid_Belt_Create(15,4,10000,800,0,14000,55)
			
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
;	Local NextGate_N=0
;	Local NextGate_E=0
;	Local NextGate_S=0
;	Local NextGate_W=0
;	Local GateFinder
;	
;	If SystemID > 15 ;[Block] Filter North
;		For GateFinder = 1 To 3
;			If SystemID-(GateFinder*16) < 0 Then Exit
;			If String_SystemName[SystemID-(GateFinder*16)]<>"Unmapped Sector" Then
;				NextGate_N=SystemID-(GateFinder*16)
;				Exit
;			EndIf
;		Next
;	EndIf ;[End Block]
;	If SystemID > 0
;		For GateFinder = 1 To 4
;			If String_SystemName[SystemID+GateFinder]<>"Unmapped Sector" Then
;				NextGate_E=SystemID+(GateFinder)
;				Exit
;			EndIf
;		Next
;	EndIf
;	If SystemID < 111
;		For GateFinder = 1 To 3
;			If SystemID+(GateFinder*16)>127 Then Exit
;			If String_SystemName[SystemID+(GateFinder*16)]<>"Unmapped Sector" Then
;				NextGate_S=SystemID+(GateFinder*16)
;				Exit
;			EndIf
;		Next
;	EndIf
;	If SystemID < 127
;		For GateFinder = 1 To 4
;			If String_SystemName[SystemID-GateFinder]<>"Unmapped Sector" Then
;				NextGate_W=SystemID-(GateFinder)
;				Exit
;			EndIf
;		Next
;	EndIf
;	
;	If NextGate_N < 0 Then NextGate_N = 0
;	If NextGate_N > 127 Then NextGate_N = 127
;	
;	If NextGate_E < 0 Then NextGate_E = 0
;	If NextGate_E > 127 Then NextGate_E = 127
;	
;	If NextGate_S < 0 Then NextGate_S = 0
;	If NextGate_S > 127 Then NextGate_S = 127
;	
;	If NextGate_W < 0 Then NextGate_W = 0
;	If NextGate_W > 127 Then NextGate_W = 127
;	String_SystemScale[SystemID] = String_SystemScale[SystemID]
;	Select String_SystemGate[SystemID]
;		Case 1 ;North
;			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
;			
;		Case 2 ;East
;			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
;			
;		Case 3 ;North + East
;			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
;			
;			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
;			
;		Case 4 ;South
;			CreateGate((NextGate_E),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
;			
;		Case 5 ;South + North
;			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
;			
;			CreateGate((NextGate_S),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
;			
;		Case 6 ;South + East
;			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
;			
;			CreateGate((NextGate_S),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
;			
;		Case 7 ;South + East + North
;			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
;			
;			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
;			
;			CreateGate((NextGate_S),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
;			
;		Case 8 ;West
;			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
;			
;		Case 9 ;North + West
;			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
;			
;			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
;			
;		Case 10 ;West + East
;			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
;			
;			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
;			
;		Case 11 ; North + East + West
;			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
;			
;			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
;			
;			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
;			
;		Case 12 ;West + South
;			CreateGate((NextGate_S),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
;			
;			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
;			
;		Case 13 ; West + South + North
;			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
;			
;			CreateGate((NextGate_S),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
;			
;			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
;			
;		Case 14 ;West + East + South
;			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
;			
;			CreateGate((NextGate_S),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
;			
;			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
;			
;		Case 15 ;All gates
;			CreateGate((NextGate_N),0,0,String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_N]) ;North
;			
;			CreateGate((NextGate_E),String_SystemScale[SystemID],0,0,-String_SystemScale[NextGate_E],0,0) ;East
;			
;			CreateGate((NextGate_S),0,0,-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_S]) ;South
;			
;			CreateGate((NextGate_W),-String_SystemScale[SystemID],0,0,String_SystemScale[NextGate_W],0,0) ;West
;			
;	End Select
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
		FreeEntity Can\Mesh: 
;		VirtualScene_Unregister(Scene, Can\mesh)
		Delete Can
	Next
	
;	For Engine.Flare = Each Flare
;		FreeEntity Engine\Mesh
;		VirtualScene_Unregister(Scene,Engine\Mesh)
;		Delete Engine
;	Next
	
	For Effect.Explosion = Each Explosion
		FreeEntity Effect\Sprite
;		VirtualScene_Unregister(Scene, Effect\Sprite)
		Delete Effect
	Next
	
	For roid.belt = Each Belt
;		VirtualScene_Unregister(Scene, roid\mesh) : FreeEntity roid\mesh
		Delete roid
	Next
	
	For da.station = Each Station
;		VirtualScene_Unregister(Scene, da\mesh_essential) : FreeEntity da\mesh_essential
;		VirtualScene_Unregister(Scene, da\mesh_factory) : FreeEntity da\mesh_Factory
;		VirtualScene_Unregister(Scene, da\mesh_power) : FreeEntity da\mesh_Power
;		VirtualScene_Unregister(Scene, da\mesh_ring) : FreeEntity da\mesh_Ring
		Delete da
	Next
	
	For SC.ScrapField = Each ScrapField
;		VirtualScene_Unregister(Scene, sc\mesh): FreeEntity sc\mesh
		Delete sc
	Next
	
	For Portal.Stargate = Each Stargate
;		VirtualScene_Unregister(Scene, Portal\Mesh_Effect_Horizon): FreeEntity Portal\Mesh_Effect_Horizon
;		VirtualScene_Unregister(Scene, Portal\Mesh): FreeEntity Portal\Mesh
;		VirtualScene_Unregister(Scene, Portal\Mesh_effect): FreeEntity Portal\Mesh_Effect
		Delete Portal
	Next
	
;	VirtualScene_Unregister(Scene, Object_Environment[2])
	FreeEntity Object_Environment[2]
	
	For XY.MapWaypoints = Each MapWaypoints
		Delete XY
	Next
	
	For TP.TPlanet = Each TPlanet
;		VirtualScene_Unregister(Scene, TP\Ring): FreeEntity TP\Ring
;		VirtualScene_Unregister(Scene, TP\Mesh): FreeEntity TP\Mesh
		Delete TP
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
	
;	VirtualScene_Register(Scene,Portal\Mesh)
;	VirtualScene_Register(Scene,Portal\Mesh_Effect)
;	VirtualScene_Register(Scene,Portal\Mesh_Effect_HOrizon)
	
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
;			If GUI_Cooldown[0] < 1 Then
;				Gate_Jump = 1
;				GUI_Cooldown[0] = 60
;			EndIf
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
	For i = 1 To NumberOfRoids
		Roid.Belt=New Belt
		Roid\x#=Rand(-90+Angle,90+Angle)		
		Roid\y#=Rand(-180,180)
		Roid\z#=Rand(BeltRange/10,BeltRange)
		
		Roid\Chance=Rand(ChanceofYield-5,ChanceofYield+5)
		Roid\maxyield=Roid\chance
		
		Roid\Toy=TypeOfYield
		
		
		BaseCollisionSize = 2
		
		Local RoidCopy = Rand(1,4)
		Roid\mesh = CopyEntity(Mesh_Asteroid[RoidCopy])
		
		Roid\siz#=Rnd(.8,9)
		
		PositionEntity roid\mesh, BeltX, BeltY, BeltZ
		Roid\tur#=Rnd(0.1,359)
		RotateEntity Roid\Mesh, Roid\y#, Roid\x#, roid\z#
		ScaleEntity Roid\mesh, Roid\siz#, Roid\siz#, Roid\siz#
		
		
		
		Roid\rot#=Rnd(-.021,.021)
		
		
		
		MoveEntity Roid\Mesh,0,0, Roid\z
		TurnEntity roid\mesh,Rand(0,360),Rand(0,360),Rand(0,360)
		EntityAutoFade Roid\Mesh, 90000,100000
;		If TypeOfYield_Fixed = 5 Then
;			PointEntity Roid\mesh,Object_Light[0]
;		EndIf
		
;		If TypeOfYield_Fixed = 5 Then TurnEntity roid\mesh,0,0,Rnd(0.0,359.0)
	Next
	
	CreateMapPoint(BeltX, BeltY, BeltZ, 1)
End Function

Function UpdateBelt()
	For Roid.Belt = Each Belt
;		Roid\x#=EntityX(Roid\Mesh)
;		Roid\y#=EntityY(Roid\Mesh)
;		Roid\z#=EntityZ(Roid\Mesh)
;		Local NameYield
;		CameraProject(WorldCamera,Roid\X#, Roid\Y#, Roid\Z#)
		If EntityInView(Roid\mesh,WorldCamera) = True Then
			ShowEntity Roid\mesh
		Else
			HideEntity roid\mesh
		EndIf
		
		MoveEntity Roid\mesh,0,Sin((MilliSecs()/50)),0
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
;		SC\Mesh=CopyEntity(Mesh_Scrap[Mesh])
		SC\Maxturn#=Maxturn#
		PositionEntity SC\Mesh,x,y,z
		RotateEntity SC\Mesh,Rand(-180,180),Rand(-180,180),Rand(-180,180)
		MoveEntity SC\Mesh, 0, 0,Rand(150,span)
		EntityTexture SC\Mesh, Text_Scrap
		Local Scale#=Rnd(Minscale#,MaxScale#)
		ScaleEntity SC\Mesh,Scale#, Scale#, Scale#
;		VirtualScene_Register(Scene,SC\Mesh)
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
				
				FreeEntity player\mesh:
;				VirtualScene_Unregister(Scene, player\mesh)
				Delete player
				
				Removestate=1
				
			EndIf
		Next
		
		If Removestate=1 Then
			FreeEntity sc\mesh: 
;			VirtualScene_Unregister(Scene, sc\mesh)
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

Function Worldmap_Display()
	Local Mapdist=80
	
	If HUD=1 And MAPHUD = 1 And Options = 0 And Upgrade_GlobalMap = 1
;		DrawImage3D(GUI_WorldMap[9],0,0)
;		DrawImage3D(GUI_Interface[0],MouseX()-(GraphicsWidth()/2),-MouseY()+(GraphicsHeight()/2))
		For A = 0 To 127
			
			If String_SystemDiscovered[a] = 1 And String_SystemName[a]<>"Unmapped Sector" Then
				Select String_SystemFaction[a]
					Case Faction_Pirate, Faction_Crimson
;						DrawImage3D(GUI_WorldMap[1],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case Faction_Neutral
;						DrawImage3D(GUI_WorldMap[2],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case Faction_Terran
;						DrawImage3D(GUI_WorldMap[3],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case Faction_Sirian
;						DrawImage3D(GUI_WorldMap[4],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case Faction_Battleground_OS, Faction_Battleground_TO, Faction_Battleground_TS
;						DrawImage3D(GUI_WorldMap[5],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case Faction_Unknown
;						DrawImage3D(GUI_WorldMap[7],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case Faction_Orion
;						DrawImage3D(GUI_WorldMap[8],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
				End Select
				
				Select String_SystemGate[a]
					Case 1
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case 2
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
					Case 3
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
					Case 4
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
					Case 5
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
					Case 6
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
					Case 7
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
					Case 8
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
					Case 9
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
					Case 10
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
					Case 11
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
					Case 12
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
					Case 13
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,0)
					Case 14
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
					Case 15
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,270)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,180)
;						DrawImage3D(GUI_WorldMap[6],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist,0,90)
				End Select
				
				Text3D(Text_Font[11],(String_SystemPosX[a]-String_SystemPosX[SystemID_Global])*Mapdist,(String_SystemPosY[a]-String_SystemPosY[SystemID_Global])*Mapdist-18,String_SystemName[a],1)
				
			EndIf
		Next
		
;		DrawImage3D(GUI_WorldMap[0],0,0)
		
	Else
		
	EndIf
End Function

Function LoadSkyBox(TxID, Parent%=0)
	Local texFlags=1+512;+16+32+512
	
	Local Mesh = CreateMesh(Parent)
;	VirtualScene_Register(Scene, Mesh)
	Local Brush, Surface
	Brush = CreateBrush()
	
	;Front
	BrushTexture Brush, Text_SkyFT[TxID]
	Surface = CreateSurface(Mesh, Brush)
	AddVertex Surface, -1, 1, 1,  0, 0:AddVertex Surface,  1, 1, 1,  1, 0
	AddVertex Surface, -1,-1, 1,  0, 1:AddVertex Surface,  1,-1, 1,  1, 1
	AddTriangle Surface, 0, 1, 2:AddTriangle Surface, 2, 1, 3
	
	;Back
	BrushTexture Brush, Text_SkyBK[TxID]
	Surface = CreateSurface(Mesh, Brush)
	AddVertex Surface, -1, 1,-1,  1, 0:AddVertex Surface,  1, 1,-1,  0, 0
	AddVertex Surface, -1,-1,-1,  1, 1:AddVertex Surface,  1,-1,-1,  0, 1
	AddTriangle Surface, 0, 2, 1:AddTriangle Surface, 2, 3, 1
	
	;Left
	BrushTexture Brush, Text_SkyLF[TxID]
	Surface = CreateSurface(Mesh, Brush)
	AddVertex Surface, -1, 1,-1,  0, 0:AddVertex Surface, -1, 1, 1,  1, 0
	AddVertex Surface, -1,-1,-1,  0, 1:AddVertex Surface, -1,-1, 1,  1, 1
	AddTriangle Surface, 0, 1, 2:AddTriangle Surface, 2, 1, 3
	
	;Right
	BrushTexture Brush, Text_SkyRT[TxID]
	Surface = CreateSurface(Mesh, Brush)
	AddVertex Surface,  1, 1,-1,  1, 0:AddVertex Surface,  1, 1, 1,  0, 0
	AddVertex Surface,  1,-1,-1,  1, 1:AddVertex Surface,  1,-1, 1,  0, 1
	AddTriangle Surface, 0, 2, 1:AddTriangle Surface, 2, 3, 1
	
	;Top
	BrushTexture Brush, Text_SkyUP[TxID]
	Surface = CreateSurface(Mesh, Brush)
	AddVertex Surface, -1, 1,-1,  0, 0:AddVertex Surface,  1, 1,-1,  1, 0
	AddVertex Surface, -1, 1, 1,  0, 1:AddVertex Surface,  1, 1, 1,  1, 1
	AddTriangle Surface, 0, 1, 2:AddTriangle Surface, 2, 1, 3
	
	;Bottom
	BrushTexture Brush, Text_SkyDN[TxID]
	Surface = CreateSurface(Mesh, Brush)
	AddVertex Surface, -1,-1,-1,  0, 1:AddVertex Surface,  1,-1,-1,  1, 1
	AddVertex Surface, -1,-1, 1,  0, 0:AddVertex Surface,  1,-1, 1,  1, 0
	AddTriangle Surface, 0, 2, 1:AddTriangle Surface, 2, 3, 1
	
	; Stuff
	ScaleMesh Mesh, 30000, 30000, 30000
	EntityOrder Mesh, 1
	EntityFX Mesh, 1
	Return Mesh
	
	FreeBrush Brush
	
End Function

Function Lighting_Initialize()
	
	Local SunScale# = 9000
	Local SunColorR = 200
	Local SunColorG = 210
	Local SunColorB = 255
	
    ; Sun
	
	Object_Environment[0] = CreatePivot()
	
	Object_Light[0] = CreateLight(1, Object_Environment[0]):
;	VirtualScene_Register(Scene, Object_Light[0])
	
;	LightRange Object_Light[0],200000000000
;	
;    Object_Environment[1] = LoadSprite("Content\GFX\Environment\sun\Sun_Sprite_Body.png", 2, Object_Light[0]) ;!ToDo: AssetManager
;	SpriteViewMode Object_Environment[1], 2
;	ScaleSprite Object_Environment[1], SunScale, SunScale
;	
;	EntityFX Object_Environment[1],1 + 4 + 8 + 16
;	Light_Pivot=CreatePivot(Object_Environment[1])
;	
;	Object_Environment[2] = LoadTexture("Content\GFX\Environment\Sun\sun_sprite_effect.png",1+2)
;	TextureBlend Object_Environment[2],2
;	ScaleTexture Object_Environment[2],0.8,0.8
;	EntityTexture Object_Environment[1],Object_Environment[2],0,2
;	
;	LightX=EntityX(Object_Environment[1])
;	LightY=EntityY(Object_Environment[1])
;	LightZ=EntityZ(Object_Environment[1])
;	
;	System_Flashlight = CreateLight(3,WorldCamera)
;	
	
;	EntityOrder Object_Environment[1],1
End Function

Function Modify_Light(RotX,RotY,Scale)
	
	
	RotateEntity Object_Environment[0], RotX, RotY, 0, True
	ScaleSprite Object_Environment[1], Scale, Scale
	
	PositionEntity Object_Light[0], 0,0,0
	MoveEntity Object_Light[0],0,0,150000
	PointEntity Object_Light[0], WorldCamera
	PointEntity Object_Environment[1], WorldCamera
	
	EntityColor Object_Environment[1],255,255,255
;	EntityFX Object_Environment[1],1
	
End Function

Function Modify_Fog(Enable, RangeNear, RangeFar, R, G, B)
	If Enable = 1 Then
		CameraFogMode WorldCamera,1
		CameraFogRange WorldCamera,RangeNear,RangeFar
		CameraFogColor WorldCamera,R,G,B
	Else
		CameraFogMode WorldCamera,0
	EndIf
End Function

Function Modify_Ambient(r,g,b)
	AmbientLight r/2, g/2, b/2
	LightColor Object_Light[0],r,g,b
	EntityColor Object_Environment[1],r,g,b
End Function

Function CreateMainDust()
	Zone_Dust_Handle=DST_Create_Dust(WorldCamera,100,1)
	Zone_Dust_Base=DST_Create_Dustzone(Zone_Dust_Handle)
	DST_Set_ZoneRadius(Zone_Dust_Base,300)
	DST_Set_texture(Zone_Dust_Base,Text_Effects[0],1+2+4)
	DST_Set_FadingFar(Zone_Dust_Base,200,150)
	DST_Set_AlphaRange(Zone_Dust_Base,0.4,0.7)
	DST_Set_Dense(Zone_Dust_Base,1)
	DST_Set_ScaleRange(Zone_Dust_Base,.25,.5)
	DST_Set_FX(Zone_Dust_Base,1)
	DST_Set_Blend(Zone_Dust_Base,3)
	DST_Set_SpeedBlur(Zone_Dust_Base,15)
	DST_Set_ColorRange(Zone_Dust_Base,55,55,55,255,255,255)
End Function


Function Asset_Station_Create(x,y,z,Rotation=0, Station_Subtype=1, Station_Faction=1)
	da.Station = New Station
	da\Mesh_Essential=CopyEntity(Mesh_Station[1])
	da\Mesh_Factory=CopyEntity(Mesh_Station[3])
	da\Mesh_Power=CopyEntity(Mesh_Station[4])
	da\Mesh_RIng=CopyEntity(Mesh_Station[2])
	
	Respawn_Create(x,y,z)
;	TraderPOI_Create(x, y, z, 0)
	
;	AI_Turret_Create(da\Mesh_Ring,9999,Faction_Neutral,-1000,0,-1000)
;	AI_Turret_Create(da\Mesh_Ring,9999,Faction_Neutral,+1000,0,-1000)
	
	PositionEntity da\Mesh_Essential,x,y,z
	PositionEntity da\Mesh_Factory,x,y,z
	PositionEntity da\Mesh_Power,x,y,z
	PositionEntity da\Mesh_Ring,x,y,z
	
	EntityAutoFade da\Mesh_Essential,160000,181500
	EntityAutoFade da\Mesh_Factory,160000,181500
	EntityAutoFade da\Mesh_Power,120000,141500
	EntityAutoFade da\Mesh_Ring,100000,111500
	
	ScaleEntity da\Mesh_Essential,120,120,120
	ScaleEntity da\Mesh_Factory,120,120,120
	ScaleEntity da\Mesh_Power,120,120,120
	ScaleEntity da\Mesh_Ring,120,120,120
	
	RotateEntity da\Mesh_Essential,0,Rotation,0
	RotateEntity da\Mesh_Factory,0,Rotation,0
	RotateEntity da\Mesh_Ring,0,Rotation,0
	RotateEntity da\Mesh_Power,0,Rotation,0
	
;	VirtualScene_Register(Scene, da\Mesh_Essential)
;	VirtualScene_Register(Scene, da\Mesh_Factory)
;	VirtualScene_Register(Scene, da\Mesh_Power)
;	VirtualScene_Register(Scene, da\Mesh_Ring)
	
	CreateMapPoint(x,y,z,2)
	
End Function

Function UpdateStation()
	For da.station= Each Station
		TurnEntity da\Mesh_Ring,0,0,0.04
	Next
End Function

Type SpecialObject
	Field x,y,z
	Field rot#, mesh, var, secmesh, Framemesh, Beammesh
End Type

Function Special_Create(x,y,z,variant)
	Disc.SpecialObject = New SpecialObject
	Disc\x = x
	DIsc\y = y
	Disc\z = z
	Disc\var = variant
	Select variant
		Case 1
			Disc\Mesh = CreateCube()
;			EntityTexture Disc\Mesh,Text_Special[0]
			EntityShininess Disc\Mesh,0.9
			ScaleEntity Disc\Mesh,20,20,0.2
;			VirtualScene_Register(Scene, Disc\Mesh)
			PositionEntity Disc\Mesh,x,y,z
			
		Case 2
			
;			Disc\Mesh = CopyEntity(Mesh_Special[0])
;			Disc\FrameMesh = CopyEntity(Mesh_Special[2])
;			Disc\SecMesh = CopyEntity(Mesh_Special[1])
;			Disc\BeamMesh = CopyEntity(Mesh_Weapon[1])
;			ScaleEntity Disc\Mesh,100,100,100
;			ScaleEntity Disc\SecMesh,100,100,100
;			ScaleEntity Disc\FrameMesh,100,100,100
			
			EntityShininess Disc\Mesh,1
;			VirtualScene_Register(Scene, Disc\Mesh)
;			VirtualScene_Register(Scene, Disc\SecMesh)
;			VirtualScene_Register(Scene, Disc\FrameMesh)
;			VirtualScene_Register(Scene, Disc\BeamMesh)
			PointEntity Disc\Mesh,Object_Light[0]
			PointEntity Disc\FrameMesh,Object_Light[0]
			
			PositionEntity Disc\Mesh,x,y,z
			PositionEntity Disc\SecMesh,x,y,z
			PositionEntity Disc\FrameMesh,x,y,z
			PositionEntity Disc\BeamMesh,x,y,z
			
;			EntityTexture DISc\BeamMesh, Text_Special[3]
			
			ScaleEntity Disc\BeamMesh,50,50,50
			
			MoveEntity Disc\Framemesh,0,0,-120
			
			EntityFX Disc\BeamMesh,1+16
			
		Case 3
			
;			Disc\Mesh = CopyEntity(Mesh_Special[3])
;			Disc\FrameMesh = CopyEntity(Mesh_Special[4])
;			Disc\SecMesh = CopyEntity(Mesh_Special[4])
			
			ScaleEntity Disc\Mesh,1000,1000,1000
			ScaleEntity Disc\SecMesh,1000,1000,1000
			ScaleEntity Disc\FrameMesh,1000,1000,1000
			
;			VirtualScene_Register(Scene, Disc\Mesh)
;			VirtualScene_Register(Scene, Disc\SecMesh)
;			VirtualScene_Register(Scene, Disc\FrameMesh)
			
			PositionEntity Disc\Mesh,x,y,z
			PositionEntity Disc\SecMesh,x,y,z
			PositionEntity Disc\FrameMesh,x,y,z
			
			EntityFX Disc\SecMesh,1+16
			EntityFX Disc\FrameMesh,1+16
			
			RotateEntity Disc\FrameMesh,180,0,0
			
		Case 4
			
;			Disc\Mesh = CopyEntity(Mesh_Special[0])
;			Disc\FrameMesh = CopyEntity(Mesh_Special[2])
;			Disc\SecMesh = CopyEntity(Mesh_Special[1])
			
			ScaleEntity Disc\Mesh,100,100,100
			ScaleEntity Disc\SecMesh,100,100,100
			ScaleEntity Disc\FrameMesh,100,100,100
			
			EntityShininess Disc\Mesh,1
;			VirtualScene_Register(Scene, Disc\Mesh)
;			VirtualScene_Register(Scene, Disc\SecMesh)
;			VirtualScene_Register(Scene, Disc\FrameMesh)
			
			PointEntity Disc\Mesh,Object_Light[0]
			PointEntity Disc\FrameMesh,Object_Light[0]
			
			PositionEntity Disc\Mesh,x,y,z
			PositionEntity Disc\SecMesh,x,y,z
			PositionEntity Disc\FrameMesh,x,y,z
			
			MoveEntity Disc\Framemesh,0,0,-120
			
		Case 5
			Disc\Mesh = CopyEntity(Mesh_Ship[8])
;			VirtualScene_Register(Scene, Disc\Mesh)
			ScaleEntity Disc\Mesh,16,16,16
			PositionEntity Disc\Mesh,x,y,z
			PositionEntity Story_Pivot,x,y,z
			
		Case 6
			Disc\Mesh = CopyEntity(Mesh_Ship[5])
;			VirtualScene_Register(Scene, Disc\Mesh)
			ScaleEntity Disc\Mesh,2,2,2
			PositionEntity Disc\Mesh,x,y,z
			PositionEntity Story_Pivot,x,y,z
	End Select
	
	
End Function

Function Special_Update()
	For Disc.SpecialObject = Each SpecialObject
		Select DIsc\var
			Case 1
				TurnEntity Disc\Mesh,Disc\Rot/30,disc\rot/20,Disc\Rot#/10
				Disc\Rot#=1
			Case 2
				da.Station = First Station
				PointEntity Disc\Secmesh,da\mesh_power
				Local EDist = EntityDistance(DIsc\Mesh,da\mesh_Power)/6
				ScaleEntity Disc\BeamMesh,50,50,EDist
				PointEntity Disc\Beammesh,da\mesh_Power
			Case 3
				TurnEntity DIsc\Secmesh,0,.1,0
				TurnEntity DIsc\Framemesh,0,.1,0
			Case 4
				
		End Select
	Next
End Function
;[End Block]
Type ItemDrop
	Field Mesh, Attribute, AttribTime, Level
End Type

Function Drop_Item(x,y,z,attrib,lvl=1)
	Can.ItemDrop = New ItemDrop
;	Can\Mesh = CopyEntity(Mesh_Effects[5])
	PositionEntity Can\Mesh,x,y,z
	Can\Attribute = attrib
	
	RotateEntity Can\Mesh,Rand(-180,180),Rand(-180,180),Rand(-180,180)
	
	Select attrib
		Case 1
;			EntityTexture can\mesh, Text_Effects[8]
		Case 2
;			EntityTexture can\mesh, Text_Effects[9]
		Case 3
;			EntityTexture can\mesh, Text_Effects[10]
		Case 4
;			EntityTexture can\mesh, Text_Effects[11]
		Case 5
;			EntityTexture can\mesh, Text_Effects[12]
		Default
;			EntityTexture can\mesh, Text_Effects[8]
	End Select
	
	TurnEntity Can\mesh,17,0,0
	Can\Level = lvl
	Can\AttribTime = 30*lvl
;	VirtualScene_Register(Scene, Can\Mesh)
End Function

Function Update_Item()
	For Can.ItemDrop = Each ItemDrop
		
		
		Local Namespace$
		Select Can\Attribute
			Case 1
				Namespace$= "LVL"+Can\Level+" Shield Battery ("+Can\Attribtime+"s)"
			Case 2
				Namespace$= "LVL"+Can\Level+" Repair Nanobots ("+Can\Attribtime+"s)"
			Case 3
				Namespace$= "LVL"+Can\Level+" Weapon Refining ("+Can\Attribtime+"s)"
			Case 4
				Namespace$= "LVL"+Can\Level+" Weapon Efficiency ("+Can\Attribtime+"s)"
			Case 5
				Namespace$= "LVL"+Can\Level+" Experience Gain ("+Can\Attribtime+"s)"
		End Select
		
		If EntityDistance(Can\Mesh,WorldCamera)< 2500 Then
			CameraProject WorldCamera,EntityX(Can\Mesh), EntityY(Can\Mesh), EntityZ(Can\Mesh)
			If HUD = 1 Then Text3D(Text_Font[11],ProjectedX()-(GraphicsWidth()/2),-ProjectedY()+(GraphicsHeight()/2),Namespace,1)
		EndIf
		
		If EntityDistance(Can\mesh,eShipBody) < 600 Then
			AlignEntity(Can\Mesh,eShipBody,15)
			MoveEntity can\mesh,0,0,10
		Else
			TurnEntity Can\mesh,0,1,1
		EndIf
		
		If EntityDistance(Can\mesh,eShipBody) < 100 Then
			
			Select Can\Attribute
				Case 1
					Buff_Add(4,30*Can\Level)
				Case 2
					Buff_Add(5,30*Can\Level)
				Case 3
					Buff_Add(1,30*Can\Level)
				Case 4
					Buff_Add(2,30*Can\Level)
				Case 5
					Buff_Add(3,30*Can\Level)
			End Select
			
;			PlaySound Sound_UI[6]
			FreeEntity Can\Mesh
;			VirtualScene_Unregister(Scene, Can\Mesh)
			Delete Can
			
		EndIf
		
	Next
End Function

Type Advantage
	Field Attribute
	Field Duration
End Type

Function Buff_Add(Attribute, Time)
	Buff.Advantage = New Advantage
	Buff\Attribute = Attribute
	Buff\Duration = Time
End Function

Function Buff_Update()
	Upgrade_Weapon_Damage = 0
	Upgrade_Weapon_Multiplier# = 1
	Upgrade_XP_Modifier# = 1
	Upgrade_Shield_Recharge = 0
	Upgrade_Armor_Repair = 0
	
	For Buff.Advantage = Each Advantage
		
		Select Buff\Attribute
			Case 1
				Upgrade_Weapon_Damage = Upgrade_Weapon_Damage + 1
			Case 2
				Upgrade_Weapon_Multiplier# = Upgrade_Weapon_Multiplier# + 0.05
			Case 3
				Upgrade_XP_Modifier# = Upgrade_XP_Modifier# + 0.1
			Case 4
				Player_Value_Shield_Current = Player_Value_Shield_Current + 1
				Upgrade_Shield_Recharge = Upgrade_Shield_Recharge + 1
			Case 5
				Player_Value_Armor_Current = Player_Value_Armor_Current + 1
				Upgrade_Armor_Repair = Upgrade_Armor_Repair + 1
		End Select
		
		If Buff\Duration < 1 Then
			Delete buff
		EndIf
		
	Next
	
	Local Bonus_Perc_WPMulti = (Upgrade_Weapon_Multiplier - 1) * 100
	Local Bonus_Perc_XPMulti = (Upgrade_XP_Modifier - 1) * 100
	
	If HUD=1 And MAPHUD = 0
		If Upgrade_Weapon_Damage > 0 Then
;			DrawImage3D(GUI_Buffs[3], D3DOL+32, +128)
			Text3D(Text_Font[31],D3DOL+32,128,"+"+Upgrade_Weapon_Damage,1)
		EndIf
		
		If Upgrade_Weapon_Multiplier > 1 Then
;			DrawImage3D(GUI_Buffs[4], D3DOL+32, +64)
			Text3D(Text_Font[31],D3DOL+32,64,"+"+Bonus_Perc_WPMulti+"%",1)
		EndIf
		
		If Upgrade_XP_Modifier > 1 Then
;			DrawImage3D(GUI_Buffs[5], D3DOL+32, +0)
			Text3D(Text_Font[31],D3DOL+32,0,"+"+Bonus_Perc_XPMulti+"%",1)
		EndIf
		
		If Upgrade_Shield_Recharge > 0 Then
;			DrawImage3D(GUI_Buffs[1], D3DOL+32, -64)
			Text3D(Text_Font[31],D3DOL+32,-64,"+"+Upgrade_Shield_Recharge,1)
		EndIf
		
		If Upgrade_Armor_Repair > 0 Then
;			DrawImage3D(GUI_Buffs[2], D3DOL+32, -128)
			Text3D(Text_Font[31],D3DOL+32,-128,"+"+Upgrade_Armor_Repair,1)
		EndIf
	EndIf
	
End Function

Function Planet_Create(DistanceTocenter, Size, SurfaceType, Rotx, RotY, Ring=0, Ring_Tilt=0, Ring_Type=0);( PosX%, PosY%, PosZ%, Size#, SurfaceTexture%, Name$, AtmosR, AtmosG, AtmosB, Ring=0, Ringtilt=0, Ringtype=0)
	TP.TPlanet = New TPlanet
	
; Assign Size
	TP\Size = Size*10
	
	; Create Entity
	TP\Pivot = CreatePivot()
;	TP\Mesh = CopyEntity(Mesh_Effects[0],TP\Pivot)
	ScaleEntity TP\Mesh, TP\Size, TP\Size, TP\Size
	TurnEntity TP\Pivot, Rotx, RotY,0
	
	MoveEntity TP\Mesh, 0, 0, DistanceTocenter*10
	
;	EntityTexture TP\Mesh, Text_Planet[SurfaceType],0,0
	
;	VirtualScene_Register(Scene, TP\Mesh)
	
	TP\Tilt=Ring_Tilt
;	
	Local TPX=EntityX(TP\Mesh,True)
	Local TPY=EntityY(TP\Mesh,True)
	Local TPZ=EntityZ(TP\Mesh,True)
	
	Tp\Ring_Exist = Ring
	
	If Ring=1 Then 
		Tp\Ring = Ring_Create(.9*TP\Size,2.5*TP\Size,180,1+2+32,3,192,224,255,64,128,255,.5,TP\Size,Ring_Type)
;		VirtualScene_Register(Scene,TP\ring)
	EndIf
	
End Function

Function Planet_Update(Camera%, OffsetX%, OffsetY%, OffsetZ%)
	For TP.TPlanet = Each TPlanet
		PositionEntity TP\Pivot,EntityX(eShipBody,True),EntityY(eShipBody,True),EntityZ(eShipBody,True)
		If TP\Ring_Exist=1 Then 
			PositionEntity TP\RING,EntityX(TP\Mesh,True),EntityY(TP\Mesh,True),EntityZ(TP\Mesh,True)
			RotateEntity TP\ring,180+TP\Tilt,0,0
		EndIf
		TurnEntity TP\Mesh,0,0.001,0
	Next
End Function

Function Ring_Create(radius1#=1.0,radius2#=2.0,segments%=360,fx%=0,blend%=0,r1%=255,g1%=255,b1%=255,r2%=0,g2%=0,b2%=0,a#=1.0,scale#=1.0,Brush_Sub=1)
	
	Local a1#,a2#,a3#,a4#,angle% 
    
	Local mesh=CreateMesh() 
	Local surf=CreateSurface(mesh) 
    
   ; Limit segments 
	If segments>360 Then segments=360 
    
	Local Basebrush
	
	Select Brush_Sub
		Case 1
			Basebrush = 1
			
		Case 2
			Basebrush = 3
			
		Case 3
			Basebrush = 5
			
	End Select
	
   ; Create ring 
	For angle=1 To segments 
		
		a1=angle*360.0/segments 
		a2=angle*360.0/segments +360.0/segments 
		a3=angle*360.0/segments +180.0/segments 
		a4=angle*360.0/segments -180.0/segments 
		
      ; Calc vertex points 
		v0=AddVertex(surf,radius1*Cos(a1),radius1*Sin(a1),0,0,0) 
		v1=AddVertex(surf,radius1*Cos(a2),radius1*Sin(a2),0,0,0) 
		v2=AddVertex(surf,radius2*Cos(a3),radius2*Sin(a3),0,1,1) 
		v3=AddVertex(surf,radius2*Cos(a4),radius2*Sin(a4),0,0,1) 
		
      ; Create Triangles 
		AddTriangle surf,v2,v1,v0 
;		PaintSurface surf,Text_Brush[Basebrush]
		AddTriangle surf,v0,v3,v2
;		PaintSurface surf,Text_Brush[Basebrush+1]
	Next 
    
	If fx>0 Then EntityFX mesh,fx 
	If blend>0 Then EntityBlend mesh,blend 
    
	Return mesh 
	
End Function

Function Explosion_Create(x,y,z,subtype=1, Scale=1, Shockwave=0)
	
	Effect.Explosion = New Explosion
	Effect\Sprite = CreateSprite()
	Effect\Stage = 1
;	VirtualScene_Register(Scene,effect\Sprite)
	
	PositionEntity Effect\Sprite, x,y,z
	SpriteViewMode Effect\Sprite,4
	ScaleSprite Effect\Sprite,Scale*500,Scale*500
	
;	EntityTexture Effect\Sprite, Text_Explosion[1]
	
	RotateSprite Effect\Sprite,Rnd(0,360.0)
	
	If Shockwave = 1
		CreateShockwave(x,y,z)
	EndIf
	
End Function

Function Explosion_Update()
	For Effect.Explosion = Each Explosion
		Effect\StageTimer = Effect\StageTimer + 1
		
		If Effect\StageTimer > 1 Then
			Effect\Stage = Effect\Stage + 1
			Effect\StageTimer = 0
		EndIf
		
;		EntityTexture Effect\Sprite, Text_Explosion[Effect\Stage]
		
		If Effect\Stage=64 Then
			FreeEntity Effect\Sprite
;			VirtualScene_Unregister(Scene, Effect\Sprite)
			Delete Effect
		EndIf
	Next
End Function

Function CreateShockwave(x,y,z,level=0)
	sh.Shockwave=New Shockwave
	sh\x=x
	sh\y=y
	sh\z=z
	sh\age=1
	sh\alpha=0.9
	sh\speed=15
	sh\mesh=CreateSprite()
	sh\level=level
	SpriteViewMode sh\mesh,2
	EntityFX sh\mesh,1+16
	PositionEntity sh\mesh,sh\x,sh\y,sh\z
	RotateEntity sh\mesh,Rand(-180,180),Rand(-180,180),Rand(-180,180)
;	EntityTexture sh\mesh,Text_Effects[3]
;	VirtualScene_Register(Scene, sh\mesh)
	;EntityColor sh\mesh,255,0,0
End Function

Function CreateLevelWave(x,y,z,Level=1)
	sh.Shockwave=New Shockwave
	sh\x=x
	sh\y=y
	sh\z=z
	sh\age=1
	sh\alpha=0.9
	sh\speed=30
	sh\mesh=CreateSprite()
	sh\level=Level
	SpriteViewMode sh\mesh,2
	EntityFX sh\mesh,1+16
	PositionEntity sh\mesh,sh\x,sh\y,sh\z
	RotateEntity sh\mesh,0,0,45
;	EntityTexture sh\mesh,Text_Effects[3]
;	VirtualScene_Register(Scene, sh\mesh)
	EntityColor sh\mesh,255,255,0
	
	sh.Shockwave=New Shockwave
	sh\x=x
	sh\y=y
	sh\z=z
	sh\age=1
	sh\alpha=0.9
	sh\speed=30
	sh\mesh=CreateSprite()
	sh\level=Level
	SpriteViewMode sh\mesh,2
	EntityFX sh\mesh,1+16
	PositionEntity sh\mesh,sh\x,sh\y,sh\z
	RotateEntity sh\mesh,0,0,-45
;	EntityTexture sh\mesh,Text_Effects[3]
;	VirtualScene_Register(Scene, sh\mesh)
	EntityColor sh\mesh,255,255,0
	
	sh.Shockwave=New Shockwave
	sh\x=x
	sh\y=y
	sh\z=z
	sh\age=1
	sh\alpha=0.9
	sh\speed=30
	sh\mesh=CreateSprite()
	sh\level=Level
	SpriteViewMode sh\mesh,2
	EntityFX sh\mesh,1+16
	PositionEntity sh\mesh,sh\x,sh\y,sh\z
	RotateEntity sh\mesh,45,0,0
;	EntityTexture sh\mesh,Text_Effects[3]
;	VirtualScene_Register(Scene, sh\mesh)
	EntityColor sh\mesh,255,255,0
	
	sh.Shockwave=New Shockwave
	sh\x=x
	sh\y=y
	sh\z=z
	sh\age=1
	sh\alpha=0.9
	sh\speed=30
	sh\mesh=CreateSprite()
	sh\level=Level
	SpriteViewMode sh\mesh,2
	EntityFX sh\mesh,1+16
	PositionEntity sh\mesh,sh\x,sh\y,sh\z
	RotateEntity sh\mesh,-45,0,0
;	EntityTexture sh\mesh,Text_Effects[3]
;	VirtualScene_Register(Scene, sh\mesh)
	EntityColor sh\mesh,255,255,0
End Function

Function UpdateShockwave()
	For sh.Shockwave = Each Shockwave
		
		sh\age=sh\age+sh\speed
		If sh\age>600 Then sh\speed=sh\speed*0.98
		
		ScaleSprite sh\mesh, sh\age, sh\age
		
		If sh\age>790 Then
			sh\alpha#=sh\alpha#-0.01
			EntityAlpha sh\mesh,sh\alpha#
		EndIf
		
		If 	sh\level = 1 Then
			PositionEntity sh\mesh, EntityX(pvShip),EntityY(pvShip),EntityZ(pvShip)
		EndIf
		
		If sh\alpha#<0.01 Then
			FreeEntity sh\mesh:
;			VirtualScene_Unregister(Scene, sh\mesh)
			Delete sh
		EndIf
		
		
	Next
	
End Function

;~IDEal Editor Parameters:
;~F#44#66#7D#96#AE#C6#DF#F8#110#128#13F#158#170#187#19F#1B6#1CD#1E4#1FB#212
;~F#229#240#257#26E#285#29C#2B3#2CA#2E1#2F8#30F#326#33D#354#36B#382#399#3B0#3C7#3DE
;~F#3F5#40C#423#43A#451#468#47F#496#4AD#4C4#4DB#4F2#509#520#537#54E#565#57C#593#5AA
;~F#5C1#5D8#5EF#606#61D#634#64B#662#679#690#6A7#6BE#6D5#6EC#703#71A#731#748#75F#776
;~F#78D#7A4#7BB#7D2#7EB#8A5#8F3#92D#988#98E#9A3#9CC#9D5#9EF#9F4#A06#A5E#A9A#AC0#AD0
;~F#ADA#AF0#B1A#B20#B25#B94#BAD#BCA#C01#C06#C0C#C4A#C6B#C76#CAA#CBF#CD2#CE5
;~C#Blitz3D