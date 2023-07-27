Global DiscoveryID
DiscoveryID=DiscoveryID+1
Global MyDiscoveredBelts
Global Security_Level
Global SystemID_Global

Type Stargate
	Field Mesh, Mesh_Effect, Mesh_Effect_Horizon
	Field JumpOBB
	Field TX, TY, TZ
	Field Target_SystemX, Target_SystemY
End Type

Function World_Generate(SystemPosX, SystemPosY, TravelPosX, TravelPosY, TravelPosZ)
	
	;Read System File and Parse Content
	Local SystemFile = OpenFile("Assets/Universe\x"+SystemPosX+"y"+SystemPosY+".bfs")
	Local TiltX, TiltY, Scale, ColorR, ColorG, ColorB, SystemReadSub$, SName$
	Local PlanetType, Resource, Amount, PosX, PosY, PosZ, TargetX, TargetY, Rotation, AimX, AimY, AimZ, StationType, InventoryOut, InventoryIn, InventoryService
	
	Repeat
		Local SystemRead$ = ReadLine(SystemFile)
		;System Seed
		If Instr(SystemRead$,"SystemSeed=") Then SystemRead$ = Replace$(SystemRead$,"SystemSeed=",""): Local SystemSeed=Int SystemRead$: SeedRnd SystemSeed
		;System Name and Values
		If Instr(SystemRead$,"SystemName=") Then SystemRead$ = Replace$(SystemRead$,"SystemName=",""): System_Name$ = SystemRead$
		If Instr(SystemRead$,"SystemSecurity=") Then SystemRead$ = Replace$(SystemRead$,"SystemSecurity=",""): System_Security = SystemRead$
		If Instr(SystemRead$,"OwnerRace=") Then SystemRead$ = Replace$(SystemRead$,"OwnerRace=",""): System_Owner$ = SystemRead$
		;Visual Background
		If Instr(SystemRead$,"BackgroundIndex=") Then SystemRead$ = Replace$(SystemRead$,"BackgroundIndex=",""): Local SystemInt = Int SystemRead$: Object_Environment[2] = LoadSkyBox(SystemInt)
		;-> Sun Color and Orientation
		If Instr(SystemRead$,"SunSetup") Then 
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"SunTiltX=",""): TiltX = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"SunTiltY=",""): TiltY = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"SunScale=",""): Scale = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"SunColorR=",""): ColorR = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"SunColorG=",""): ColorG = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"SunColorB=",""): ColorB = Int(SystemReadSub$)
			Modify_Light(TiltX,TiltY,Scale,ColorR,ColorG,ColorB)
		EndIf
		;-> Planets
		If Instr(SystemRead$,"PlanetSetup") Then 
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"PlanetTiltX=",""): TiltX = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"PlanetTiltY=",""): TiltY = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"PlanetScale=",""): Scale = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"PlanetType=",""): PlanetType = Int(SystemReadSub$)
			Asset_Planet_Create(Scale,PlanetType,TiltX,TiltY)
		EndIf
		;-> Asteroids
		If Instr(SystemRead$,"BeltSetup") Then 
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"BeltPosX=",""): PosX = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"BeltPosY=",""): PosY = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"BeltPosZ=",""): PosZ = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"BeltResource=",""): Resource = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"BeltRange=",""): Scale = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"BeltAmount=",""): Amount = Int(SystemReadSub$)
			Asset_Belt_Create(Amount,Resource,PosX,PosY,PosZ,Scale)
		EndIf
		;-> Gates
		If Instr(SystemRead$,"GateSetup") Then 
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"SystemX=",""): TargetX = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"SystemY=",""): TargetY = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"GatePosX=",""): PosX = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"GatePosY=",""): PosY = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"GatePosZ=",""): PosZ = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"GateRotation=",""): Rotation = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"GateAimX=",""): AimX = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"GateAimY=",""): AimY = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"GateAimZ=",""): AimZ = Int(SystemReadSub$)
			Asset_Gate_Create(TargetX, TargetY, PosX, PosY, PosZ, Rotation, AimX, AimY, AimZ)
		EndIf
		;-> Stations
		If Instr(SystemRead$,"StationSetup") Then 
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"StationName=",""): SName$ = SystemReadSub$
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"StationPosX=",""): PosX = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"StationPosY=",""): PosY = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"StationPosZ=",""): PosZ = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"StationType=",""): StationType = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"StationRotation=",""): Rotation = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"StationBuy=",""): InventoryIn = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"StationSell=",""): InventoryOut = Int(SystemReadSub$)
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"StationService=",""): InventoryService = Int(SystemReadSub$)
			Asset_Station_Create(SName$, PosX, PosY, PosZ, StationType, Rotation, InventoryIn, InventoryOut, InventoryService)
		EndIf
		;-> RandomBelt
		If Instr(SystemRead$,"RandomBelt") Then
			SeedRnd MilliSecs()
			SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"BeltResource=",""): Resource = Int(SystemReadSub$)
			Asset_Belt_Create(Rand(1000,15000),Resource,Rand(-500000,500000),Rand(-1000,1000),Rand(-500000,500000),Rand(15000,500000))
			SeedRnd SystemSeed
		EndIf
		
		
	Until Eof(SystemFile)
	CloseFile SystemFile
	
	SeedRnd MilliSecs()
	
	FastTravel(TravelPosX,TravelPosY,TravelPosZ)
	
End Function

Function Asset_Clear_All()
	
	FreeEntity Object_Zero
	
	For Effect.Explosion = Each Explosion
		FreeEntity Effect\Sprite
		Delete Effect
	Next
	
	For roid.belt = Each Belt
		FreeEntity roid\mesh
		Delete roid
	Next
	
	For da.station = Each Station
		FreeEntity da\tables
		FreeEntity da\utility
		FreeEntity da\effect
		FreeEntity da\mesh
		Delete da
	Next
	
	For SC.ScrapField = Each ScrapField
		FreeEntity sc\mesh
		Delete sc
	Next
	
	For Portal.Stargate = Each Stargate
;		FreeEntity Portal\Mesh_Effect
		FreeEntity Portal\Mesh
		FreeEntity Portal\JumpOBB
		Delete Portal
	Next
	
	For Orbit.Planet = Each Planet
;		FreeEntity Orbit\Pivot
		FreeEntity Orbit\Sprite
		Delete Orbit
	Next
	
	; Skybox
	FreeEntity Object_Environment[2]
	
	For XY.MapWaypoints = Each MapWaypoints
		Delete XY
	Next
	
	
End Function

Function Asset_Gate_Create(Target_SystemX, Target_SystemY, PosX, PosY, PosZ, Rotation, TX, TY, TZ)
	
	Portal.Stargate = New Stargate
	Portal\Mesh = CopyEntity(Mesh_Gate[1])
	Portal\Mesh_Effect = CopyEntity(Mesh_Gate[2],Portal\Mesh)
	
	ScaleEntity Portal\Mesh,40,40,40
	
	RotateEntity Portal\Mesh, 0,Rotation,0	
	PositionEntity Portal\Mesh,PosX,PosY,PosZ
	
	Portal\JumpOBB = CreateOBB(PosX, PosY, PosZ, 0, Rotation, 0, 20000, 20000, 1000)
	
	EntityFX Portal\Mesh_Effect,1+16
	
	EntityType Portal\Mesh,Collision_Object
	
	EntityAutoFade Portal\mesh,200000,250000
	EntityAutoFade Portal\mesh_Effect,500000,550000
	
	Local TSystemName = OpenFile("Assets/Universe/x"+Target_SystemX+"y"+Target_SystemY+".bfs")
	Empty$=ReadLine(TSystemName)
	Local ConnectedSystem$=ReadLine(TSystemName):ConnectedSystem$ = Replace$(ConnectedSystem$,"SystemName=","")
	NameEntity Portal\Mesh,ConnectedSystem$
	CloseFile TSystemName
	
	Portal\Target_SystemX = Target_SystemX
	Portal\Target_SystemY = Target_SystemY
	Portal\TX = TX
	Portal\TY = TY
	Portal\TZ = TZ
	
	Environment_NavMesh_Create(PosX, PosY, PosZ, 2)
	
End Function

Function Asset_Gate_Update()
	Local Gate_Jump = 0
	For Portal.Stargate = Each Stargate
		
		If EntityDistance(pvShip, portal\mesh)<25000 And EntityInView(Portal\Mesh,WorldCamera)=True Then
			Text3D(Text_Font[6],0,275,"- Location entered -",1)
			Text3D(Text_Font[9],0,256,"Gate: "+EntityName(Portal\mesh),1)
		EndIf
		
		If EntityInOBB(Portal\JumpOBB, eShip) Then
;			RuntimeError "Jumped"
			Local Future_SystemX = Portal\Target_SystemX
			Local Future_SystemY = Portal\Target_SystemY
			Local TX = Portal\TX
			Local TY = Portal\TY
			Local TZ = Portal\TZ
			If Timer_Gatejump < 1 Then
				Gate_Jump = 1
				Timer_Gatejump = 3
			EndIf
			Exit
		EndIf
	Next
	If Gate_Jump = 1 Then
		Asset_Clear_All()
		World_Generate(Future_SystemX, Future_SystemY, TX, TY, TZ)
	EndIf
End Function

Function Asset_Belt_Create(NumberOfRoids, TypeOfYield, BeltX, BeltY, BeltZ, BeltRange)
	For i = 1 To NumberOfRoids
		Roid.Belt=New Belt
		Roid\x#=Rand(-180,180)		
		Roid\y#=Rand(-180,180)
		Roid\z#=Rand(BeltRange/10,BeltRange)
		
		Roid\Chance=Rand(ChanceofYield-5,ChanceofYield+5)
		Roid\maxyield=Roid\chance
		
		Roid\ToY=TypeOfYield
		BaseCollisionSize = 2
		
		Local RoidCopy = Rand(1,4)
		Roid\mesh = CopyEntity(Mesh_Asteroid[RoidCopy])
		
		Roid\siz#=Rnd(.8,7)
		
		PositionEntity roid\mesh, BeltX, BeltY, BeltZ
		Roid\tur#=Rnd(0.1,359)
		RotateEntity Roid\Mesh, Roid\y#, Roid\x#, roid\z#
		ScaleEntity Roid\mesh, Roid\siz#, Roid\siz#, Roid\siz#
		
		Select Roid\ToY
			Case 0 ;Astralite Cheap
				EntityColor roid\mesh,155,155,155
			Case 1 ;Ferronite Fe
				EntityColor roid\mesh,170,155,155
			Case 2 ;Silichrone Si/Mn
				EntityColor roid\mesh,155,155,170
			Case 3 ;Cryospar H2/O2
				EntityColor roid\mesh,140,155,170
			Case 4 ;Voltium Ag/Au
				EntityColor roid\mesh,170,170,140
			Case 5 ;Valorisite MONEY
				EntityColor roid\mesh,155,140,170
		End Select
		
		Roid\rot#=Rnd(-.021,.021)
		
		MoveEntity Roid\Mesh,0,0, Roid\z
		TurnEntity roid\mesh,Rand(0,360),Rand(0,360),Rand(0,360)
		
		Environment_NavMesh_Create(EntityX(Roid\Mesh), EntityY(Roid\mesh), EntityZ(roid\Mesh), 1)
		
		EntityAutoFade roid\mesh,90000,115000
		EntityType roid\mesh,Collision_Object,True
		
	Next
	
	
End Function

Function Asset_Belt_Update()
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
		Environment_NavMesh_Create(x,y,z,3)
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
	Timer_Gatejump = Timer_Gatejump - 1
	Respawn_Timer = Respawn_Timer - 1
	Music_Aggro_Timer = Music_Aggro_Timer - 1
	Timer_Storyline = Timer_Storyline - 1
	Title_Timer = Title_Timer - 1
	Timer_Hacking = Timer_Hacking - 1
	
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
	
	Local SunScale# = 90000
	Local SunColorR = 200
	Local SunColorG = 210
	Local SunColorB = 255
	
    ; Sun
	
	Object_Environment[0] = CreatePivot()
	Object_Environment[1] = CopyEntity(Mesh_Environment[0], Object_Environment[0])
	
	Object_Light[0] = CreateLight(1, Object_Environment[0])
	Object_Light[1] = CreateLight(1, Object_Environment[0]):RotateEntity Object_Light[1],0,180,0
	
;	ScaleEntity Object_Environment[0],5,5,5
	EntityColor Object_Environment[1],255,255,255
	EntityFX Object_Environment[1],1
	
	MoveEntity Object_Environment[1],0,0,-600000
	
	ScaleSprite Object_Environment[1], SunScale, SunScale
	LightColor Object_Light[0],250,250,250
	LightColor Object_Light[1],50,50,50
	
	AmbientLight 10,10,10
End Function

Function Modify_Light(RotX,RotY,Scale, SunR, SunG, SunB)
	
	
	RotateEntity Object_Environment[0], RotX, RotY, 0, True
	ScaleSprite Object_Environment[1], Scale, Scale
	
	PointEntity Object_Environment[1], WorldCamera
	
	LightColor Object_Light[0], SunR, SunG, SunB
	LightColor Object_Light[1], SunR/7, SunG/7, SunB/7	
	EntityColor Object_Environment[1],SunR, SunG, SunB
	
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


Function Environment_Dust_Create()
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


Function Asset_Station_Create(Name$, x,y,z, Station_Subtype, Rotation=0, InventoryIn=1, InventoryOut=1, InventoryService=0)
	
	da.Station = New Station
	da\Mesh = CopyEntity(Mesh_Station[Station_Subtype])
	da\StaSub = Station_Subtype
	Select Station_Subtype
		Case 3
			da\tables = CopyEntity(Mesh_Station[4],da\mesh)
			ScaleEntity da\tables,2,2,2
			MoveEntity da\tables,0,-17.5,1
			da\effect = CopyEntity(Mesh_Asteroid[1], da\mesh)
			da\Utility = CopyEntity(Mesh_Asteroid[2], da\mesh)
		Case 2
			da\Tables = CreatePivot()
			da\Utility = CopyEntity(Mesh_Station[5],da\mesh) 
			da\effect = CreatePivot()
		Default 
			da\Tables = CreatePivot()
			da\Utility = CreatePivot()
			da\effect = CreatePivot()
	End Select
	
	
	Select Station_Subtype
		Case 3
			ScaleEntity da\mesh,35,35,35
			ScaleEntity da\utility, 0.75,0.75,0.75
			ScaleEntity da\effect, 0.3,0.3,0.3
			MoveEntity da\effect, 330,30,415
			Environment_NavMesh_Create(x,y,z,5)
		Case 2
			ScaleEntity da\mesh,25,25,25
;			ScaleEntity da\utility,2,2,1
			Environment_NavMesh_Create(x,y,z,4)
			
			
		Case 1
			
			ScaleEntity da\mesh,35,35,35
			
			
			Environment_NavMesh_Create(x,y,z,3)
		Default 
			ScaleEntity da\mesh,35,35,35
			
			Environment_NavMesh_Create(x,y,z,3)
			
	End Select
	
	PositionEntity da\mesh,x,y,z
	RotateEntity da\mesh,0,Rotation,0
	
	EntityType da\mesh,Collision_Object,True
	EntityAutoFade da\mesh,200000,250000
	
	NameEntity da\Mesh,Name$
	
End Function

Function Asset_Station_Update()
	For da.station= Each Station
		
		If EntityDistance(pvShip, da\Mesh)<25000 And EntityInView(da\Mesh,WorldCamera)=True Then
			Text3D(Text_Font[6],0,275,"- Location entered -",1)
			Text3D(Text_Font[9],0,256,"Station: "+EntityName(da\mesh),1)
		EndIf
		
		Select da\StaSub
			Case 3
				TurnEntity da\utility,0,-0.025,0.025
				TurnEntity da\effect,0,0.025,-0.025
			Case 2
				TurnEntity da\utility,0,0,0.15
		End Select
	Next
End Function

Function Asset_Planet_Create(Scale, Texture, RotX, RotY);( PosX%, PosY%, PosZ%, Size#, SurfaceTexture%, Name$, AtmosR, AtmosG, AtmosB, Ring=0, Ringtilt=0, Ringtype=0)
	Orbit.Planet = New Planet
	Orbit\Pivot = CreatePivot()
	Orbit\Sprite = CreateSprite(Orbit\Pivot)
	EntityTexture Orbit\Sprite, Mesh_Planets[Texture]
	ScaleSprite Orbit\Sprite,Scale*100,Scale*100
	MoveEntity Orbit\Sprite,0,0,600000
	RotateEntity Orbit\Pivot,RotX, RotY,0
	SpriteViewMode Orbit\Sprite,2
	PointEntity Orbit\Sprite, Orbit\Pivot
	EntityFX Orbit\Sprite,1+16
End Function

Function Asset_Planet_Update()
	For Orbit.Planet = Each Planet
		PositionEntity Orbit\Pivot, EntityX(pvShip), EntityY(pvShip), EntityZ(pvShip)
;		MoveEntity Orbit\Sprite,0,0,600000
	Next
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
;~F#66#BC#10E#11F#125#13A#163#16C#186#18B#195#1ED#229#245#254#25F#2BC#2C9#2D0#2E5
;~F#2F8#30B#351
;~C#Blitz3D