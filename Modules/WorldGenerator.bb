Type Stargate
	Field Mesh, Mesh_Effect, Mesh_Effect_Horizon
	Field JumpOBB
	Field TX, TY, TZ
	Field Target_SystemX, Target_SystemY
End Type

Function World_Generate(SystemPosX, SystemPosY, TravelPosX, TravelPosY, TravelPosZ)
	;Decide Dock or 
	If SystemPosX<1 And SystemPosY<1 Then
		Asset_Clear_All()
		
		Modify_Fog(0,0,0,0,0,0)
		Lighting_Initialize(0,90,1,255,255,255)
		AmbientLight 130,130,130
		Object_Environment[2] = LoadSkyBox(2)
		ShowEntity Mesh_Environment[1]
		PositionEntity Mesh_Environment[1],EntityX(pvShip,True), EntityY(pvShip,True),EntityZ(pvShip,True)
		RotateEntity Mesh_Environment[1],EntityPitch(pvShip,True), EntityYaw(pvShip,True),EntityRoll(pvShip,True)
		Force_UI_Mode = 1
	Else
		;Read System File and Parse Content
		PlaySound Sound_ID[6]
		Force_UI_Mode = 0
		HideEntity Mesh_Environment[1]
		Local SystemFile = OpenFile("Assets/Universe\x"+SystemPosX+"y"+SystemPosY+".bfs")
		Local TiltX, TiltY, Scale, ColorR, ColorG, ColorB, SystemReadSub$, SName$, StationOwner
		Local PlanetType, Resource, Amount, PosX, PosY, PosZ, TargetX, TargetY, Rotation, AimX, AimY, AimZ, StationType, InventoryOut, InventoryIn, InventoryService
		Asset_Clear_All()
	
		Modify_Fog(0,0,0,0,0,0)
		AmbientLight 30,30,30
		
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
				Lighting_Initialize(TiltX,TiltY,Scale,ColorR,ColorG,ColorB)
			EndIf
		;-> System Foggyness
			If Instr(SystemRead$,"FogSector") Then 
				SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"FogRange=",""): Scale = Int(SystemReadSub$)
				SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"FogColorR=",""): ColorR = Int(SystemReadSub$)
				SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"FogColorG=",""): ColorG = Int(SystemReadSub$)
				SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"FogColorB=",""): ColorB = Int(SystemReadSub$)
				Modify_Fog(1,Scale*0.8,Scale,ColorR,ColorG,ColorB)
				AmbientLight ColorR/1.25,ColorG/1.25,ColorB/1.25
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
				SystemReadSub$ = ReadLine(SystemFile): SystemReadSub$ = Replace$(SystemReadSub$,"StationOwner=",""): StationOwner = Int(SystemReadSub$)
				Asset_Station_Create(SName$, PosX, PosY, PosZ, StationType, Rotation, InventoryIn, InventoryOut, InventoryService, StationOwner)
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
		Player_GlobalX = SystemPosX
		Player_GlobalY = SystemPosY
		UpdateMapScale(4)
	EndIf
End Function

Function Asset_Clear_All()
	
;	FreeEntity Object_Zero
	
	For Cube.DockPort = Each DockPort
		FreeEntity Cube\mesh
		Delete Cube
	Next
	
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
	
	For Portal.Stargate = Each Stargate
		FreeEntity Portal\Mesh_Effect
		FreeEntity Portal\Mesh
		FreeEntity Portal\JumpOBB
		Delete Portal
	Next
	
	For Orbit.Planet = Each Planet
		FreeEntity Orbit\Sprite
		FreeEntity Orbit\Pivot
		Delete Orbit
	Next
	
	For Item.Container = Each Container
		FreeEntity Item\Mesh
		FreeEntity Item\Pivot
		Delete Item
	Next
	
	; Skybox
	FreeEntity Object_Light[0]
	
	FreeEntity Object_Environment[1]
	FreeEntity Object_Environment[0]
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
	
	Portal\JumpOBB = CreateOBB(PosX, PosY, PosZ, 0, Rotation, 0, 10000, 10000, 1000)
	
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
		
		If EntityDistance(pvShip, portal\mesh)<25000 And EntityInView(Portal\Mesh,WorldCamera)=True And HUD = 1 And Force_UI_Mode = 0 Then
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

Function Tweakseed()
    
    Local temp%=(seed[0]+seed[1]+seed[2]) Mod $10000
    seed[0]=seed[1]
    seed[1]=seed[2]
    seed[2]=temp
    
End Function

Function WorldTimers_Update()
	Timer_Gatejump = Timer_Gatejump - 1
	Respawn_Timer = Respawn_Timer - 1
	Music_Aggro_Timer = Music_Aggro_Timer - 1
	Timer_Dock = Timer_Dock - 1
End Function

Function Worldmap_Display()
	
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
	ScaleMesh Mesh, 300000, 300000, 300000
	EntityOrder Mesh, 1
	EntityFX Mesh, 1
	Return Mesh
	
	FreeBrush Brush
	
End Function

Function Lighting_Initialize(RotX,RotY,Scale, SunR, SunG, SunB)
	
	
    ; Sun
	
	Object_Environment[0] = CreatePivot()
	Object_Environment[1] = CopyEntity(Mesh_Environment[0], Object_Environment[0])
	
	Object_Light[0] = CreateLight(1, Object_Environment[0])
	
	EntityColor Object_Environment[1],255,255,255
	EntityFX Object_Environment[1],1
	
	MoveEntity Object_Environment[1],0,0,-600000
	
	RotateEntity Object_Environment[0], RotX, RotY, 0, True
	ScaleSprite Object_Environment[1], Scale, Scale
	
	PointEntity Object_Environment[1], WorldCamera
	
	LightColor Object_Light[0], SunR, SunG, SunB
	AmbientLight SunR/7, SunG/7, SunB/7
	
	EntityColor Object_Environment[1],SunR, SunG, SunB
	
	
	AmbientLight 10,10,10
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

Function Asset_Station_Create(Name$, x,y,z, Station_Subtype, Rotation=0, InventoryIn=1, InventoryOut=1, InventoryService=0, Owner=1)
	
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
			Asset_DockCube_Create(x,y,z,Rotation,3, 14150,190,-6000,Name$, Owner, InventoryIn, InventoryOut, InventoryService)
		Case 2
			ScaleEntity da\mesh,25,25,25
;			ScaleEntity da\utility,2,2,1
			
			Environment_NavMesh_Create(x,y,z,4)
			Asset_DockCube_Create(x,y,z,Rotation,2,0,350,-11500,Name$, Owner, InventoryIn, InventoryOut, InventoryService)
		Case 1
			ScaleEntity da\mesh,35,35,35
			
			Environment_NavMesh_Create(x,y,z,3)
			Asset_DockCube_Create(x,y,z,Rotation,1,0,0,0,Name$, Owner, InventoryIn, InventoryOut, InventoryService)
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
		
		If EntityDistance(pvShip, da\Mesh)<25000 And EntityInView(da\Mesh,WorldCamera)=True And HUD = 1 And Force_UI_Mode = 0 Then
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

Function Asset_Planet_Create(Scale, Texture, RotX, RotY)
	Orbit.Planet = New Planet
	Orbit\Pivot = CreatePivot()
	Orbit\Sprite = CreateSprite(Orbit\Pivot)
	EntityTexture Orbit\Sprite, Mesh_Planets[Texture]
	ScaleSprite Orbit\Sprite,Scale*100,Scale*100
	MoveEntity Orbit\Sprite,0,0,600000
	RotateEntity Orbit\Pivot,RotX, RotY,0
	SpriteViewMode Orbit\Sprite,2
	PointEntity Orbit\Sprite, Orbit\Pivot
	EntityFX Orbit\Sprite,1+16+32
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

Function Asset_DockCube_Create(x,y,z,rot,sizecase,offsetx=0,offsety=0,offsetz=0, Name$="Test Station", Owner = 1, WareBuy=0, WareSell=0, Service=0)
	Cube.DockPort = New DockPort
	Cube\Mesh = CreateCube()
	
	Cube\X = x
	Cube\Y = y
	Cube\Z = z
	Cube\Rot = rot
	Cube\Name$ = Name$
	Cube\Owner = Owner
	Cube\WareBuy=WareBuy
	Cube\WareSell = WareSell
	Cube\Service = Service
	Select sizecase
		Case 1
			ScaleEntity Cube\Mesh,2500,350,2500
		Case 2
			ScaleEntity Cube\Mesh,5900,400,1100
		Case 3
			ScaleEntity Cube\Mesh,500,350,1000
	End Select
	PositionEntity Cube\Mesh, Cube\X, Cube\Y, Cube\Z
	RotateEntity Cube\Mesh,0,Cube\Rot,0
	MoveEntity Cube\mesh, offsetx,offsety, offsetz
	Select sizecase
		Case 1
			Cube\OBB = CreateOBB(EntityX(Cube\Mesh),EntityY(Cube\Mesh),EntityZ(Cube\Mesh),0,rot,0,2500,350,2500)
		Case 2
			Cube\OBB = CreateOBB(EntityX(Cube\Mesh),EntityY(Cube\Mesh),EntityZ(Cube\Mesh),0,rot,0,5900,400,1100)
		Case 3
			Cube\OBB = CreateOBB(EntityX(Cube\Mesh),EntityY(Cube\Mesh),EntityZ(Cube\Mesh),0,rot,0,500,350,1000)
	End Select
	EntityFX Cube\mesh,1
	EntityColor Cube\Mesh,0,255,255
	EntityAutoFade Cube\Mesh,5000,20000
	EntityAlpha Cube\Mesh,0.3
End Function

Function Asset_DockCube_Update()
	For Cube.DockPort = Each DockPort		If EntityInOBB(Cube\OBB,pvShip) And Timer_Dock < 1 Then
			Station_Owner = Cube\Owner
			Station_Name$ = Cube\Name$
			Station_WareImport = Cube\WareBuy
			Station_WareExport = Cube\WareSell
			Station_Services = Cube\Service
			Camera_Zoom_Speed#=-5
			Force_UI_Mode = 1
			World_Generate(0,0,0,0,0)
		EndIf
	Next
End Function
;~IDEal Editor Parameters:
;~F#133#144#158#1B1#1BB#219#226#22D#242#255#268#2AE
;~C#Blitz3D