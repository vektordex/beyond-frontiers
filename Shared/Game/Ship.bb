;----------------------------------------------------------------
;-- Constants
;----------------------------------------------------------------
Const SHIP_HEALTH#					= 100.0
Const SHIP_SHIELD#					= 100.0
Const SHIP_ENERGY#					= 100.0
Const SHIP_DEFAULT_RECHARGE#		=   1.0
Const SHIP_DEFAULT_SPEED#			= 100.0
Const SHIP_HEALTH_DAMAGE_SMALL#		=  25.0
Const SHIP_SHIELD_DAMAGE_SMALL#		=  50.0
Const SHIP_HEALTH_DAMAGE_MEDIUM#	=  50.0
Const SHIP_SHIELD_DAMAGE_MEDIUM#	= 100.0
Const SHIP_HEALTH_DAMAGE_LARGE#		= 100.0
Const SHIP_SHIELD_DAMAGE_LARGE#		= 200.0
Const SHIP_DEFAULT_DAMAGEMEDIUM#	= 500.0

Const SHIP_BOOST_MULTIPLIER#		= 	3.0

;----------------------------------------------------------------
;-- Types
;----------------------------------------------------------------
Type Ship
	Field Id%
	
	Field Name$, Description$
	
	Field Hardpoints.ShipHardpoint
	
	; Modifiers from Default
	Field HealthMod#, ShieldMod#
	Field EnergyMod#, RechargeMod#
	Field SpeedMod#
	Field DamageModSmall#, DamageModMedium#, DamageModLarge#, YieldMod#
	Field CargoMod#, ScanRangeMod#
	
	;-- Client only
	Field ClientData.ClientShip
End Type

Type ShipHardpoint
	Field Position#[3]
End Type

Type ClientShip
	Field MeshPath$, TexturePath$
	Field MeshObj%, TextureObj%
End Type

;----------------------------------------------------------------
;-- Functions
;----------------------------------------------------------------
;Function Ship_Load.Ship(File$)
;	If FileType(File) = 1 Then
;		Local Stream = ReadFile(File)
;		If Stream Then
;			Local Ship.Ship = New Ship
;			;Ship\Hardpoints = TList_Create()
;			If CLIENT Then
;				Ship\ClientData = New ClientShip
;			EndIf
;			
;			While Not Eof(Stream)
;				Local Ln$ = ReadLine(Stream)
;				
;				Select Lower(Left(Ln$, 4))
;					Case "indx"
;						Ship\Id = Right(Ln$, Len(Ln$) - 5)
;					Case "name"
;						Ship\Name = Right(Ln$, Len(Ln$) - 5)
;					Case "desc"
;						Ship\Description = Right(Ln$, Len(Ln$) - 5)
;					Case "hpts"
;						Ship\Health = Right(Ln$, Len(Ln$) - 5)
;					Case "spts"
;						Ship\Shield = Right(Ln$, Len(Ln$) - 5)
;					Case "sped"
;						Ship\Speed = Right(Ln$, Len(Ln$) - 5)
;					Case "hard"
;						Local HP.ShipHardpoint = New ShipHardpoint
;						HP\Position[0] = ReadLine(Stream)
;						HP\Position[1] = ReadLine(Stream)
;						HP\Position[2] = ReadLine(Stream)
;					;-- Client only
;					Case "mesh"
;						If CLIENT Then Ship\ClientData\MeshPath = Right(Ln$, Len(Ln$) - 5)
;					Case "txtr"
;						If CLIENT Then Ship\ClientData\TexturePath = Right(Ln$, Len(Ln$) - 5)
;				End Select
;			Wend
;			
;			Return Ship
;		Else
;			RuntimeError "Ship_Load: Unable to read file '" + File + "'."
;		EndIf
;	Else
;		RuntimeError "Ship_Load: No such file '" + File + "'."
;	EndIf
;End Function
;
;Function Ship_LoadContent(Ship.Ship, TextureFlags% = 0)
;	If CLIENT Then
;		Ship\ClientData\MeshObj = LoadMesh(Ship\ClientData\MeshPath)
;		Ship\ClientData\TextureObj = LoadTexture(Ship\ClientData\TexturePath, TextureFlags)
;	EndIf
;End Function
;~IDEal Editor Parameters:
;~C#Blitz3D