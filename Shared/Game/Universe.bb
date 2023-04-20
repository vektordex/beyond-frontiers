;----------------------------------------------------------------
;-- Constants
;----------------------------------------------------------------
Const UNIVERSE_SECTOR_SIZE#				= 1000000.0
Const UNIVERSE_DECELARATION#			= 0.999999
Const UNIVERSE_OWNER_SERVER				= 0

Const STATION_TYPE_TRADE_HUB			= 0
Const STATION_TYPE_HEALTH_REPAIR		= 1
Const STATION_TYPE_SHIELD_REPAIR		= 2
Const STATION_TYPE_SHIPYARD				= 3
Const STATION_TYPE_HEADQUARTERS			= 4

;----------------------------------------------------------------
;-- Types
;----------------------------------------------------------------
Type Sector
	Field Position[2]
	
	; List of ...
	Field Systems			; Systems
	Field Zones				; Zones
	Field Stations			; Stations
	Field Tradelane			; Tradelane
	Field UObjects			; UObjects
End Type

Type System
	Field Name$					; Name of the System.
	Field Position#[2]			; Position
	Field Size#[2]				; Size
	Field Priority%				; Priority (Lower = Less, Higher = More)
	
	; Visuals
	Field SkyIndex%				; Which Sky to use.
End Type

Type Zone
	Field Position#[2]
	
	; Dangers
	Field Radiation#					; Hull
	Field Micrometeors#					; Shield, Hull
	Field Gravity#						; Hull, Speed
	Field Biohazard#					; Shield
	Field ElectromagneticInterference#	; Shield, Speed, Systems
End Type

Type UObject
	Field Id%
	
	Field Station.Station
	Field Tradelane.Tradelane
	Field Asteroid.Asteroid
	Field Player.Player
	Field Shot.Shot
	Field Item.ItemObject
End Type

Type Station
	Field OwnerId				; AccountId of the Owner
	Field Position.Position		; Position
	Field Rotation.Rotation		; Rotation
	
	Field Health#, MaxHealth#	; Health / Maximum Health
	Field Shield#, MaxShield#	; Shield / Maximum Shield
	
	Field Name$					; Name of the Station
	Field TypeId%				; Type of the Station
End Type

Type Tradelane
	Field OwnerId				; AccountId of the Owner
	Field Position.Position		; Position
	Field EndPosition.Position	; Ending Position
	
	Field Health#, MaxHealth#	; Health / Maximum Health
	Field Shield#, MaxShield#	; Shield / Maximum Shield
End Type

Type Asteroid
	Field Position.Position
	Field Rotation.Rotation
	
	Field AsteroidType#
	Field Yield#, Quality#
End Type

Type Shot
	Field Position.Position
	
	Field Owner.UObject
End Type

Type Position
	Field Sector%[2]			; Sector
	Field Position#[2]			; Position
	Field Velocity#[2]			; Velocity
End Type

Type Rotation
	Field Rotation#[2]			; Rotation
	Field AngularVelocity#[2]	; Angular Velocity
End Type

;----------------------------------------------------------------
;-- Globals
;----------------------------------------------------------------
Global Transform_Velocity_Pitch#
Global Transform_Velocity_Yaw#
Global Universe_Indexer			= Indexer_Create()

;----------------------------------------------------------------
;-- Functions
;----------------------------------------------------------------
;
;
;Function Transform_Create.Transform(SectorX, SectorY, SectorZ, X#, Y#, Z#, Pitch#, Yaw#, Roll#, SizeX#, SizeY#, SizeZ#)
;	Local Transform.Transform = New Transform
;	Transform\Sector[0] = SectorX
;	Transform\Sector[1] = SectorY
;	Transform\Sector[2] = SectorZ
;	Transform\Position[0] = X
;	Transform\Position[1] = Y
;	Transform\Position[2] = Z
;	Transform\Rotation[0] = Pitch
;	Transform\Rotation[1] = Yaw
;	Transform\Rotation[2] = Roll
;	Transform\Size[0] = SizeX
;	Transform\Size[1] = SizeY
;	Transform\Size[2] = SizeZ
;	Return Transform
;End Function
;
;Function Transform_SetSector(Transform.Transform, SectorX, SectorY, SectorZ)
;	Transform\Sector[0] = SectorX
;	Transform\Sector[1] = SectorY
;	Transform\Sector[2] = SectorZ
;End Function
;
;Function Transform_SetPosition(Transform.Transform, X#, Y#, Z#)
;	Transform\Position[0] = X
;	Transform\Position[1] = Y
;	Transform\Position[2] = Z
;End Function
;
;Function Transform_SetVelocity(Transform.Transform, X#, Y#, Z#)
;	Transform\Velocity[0] = X
;	Transform\Velocity[1] = Y
;	Transform\Velocity[2] = Z
;End Function
;
;Function Transform_SetRotation(Transform.Transform, Pitch#, Yaw#, Roll#)
;	Transform\Rotation[0] = Pitch
;	Transform\Rotation[1] = Yaw
;	Transform\Rotation[2] = Roll
;End Function
;
;Function Transform_SetAngularVelocity(Transform.Transform, Pitch#, Yaw#, Roll#)
;	Transform\AngularVelocity[0] = Pitch
;	Transform\AngularVelocity[1] = Yaw
;	Transform\AngularVelocity[2] = Roll
;End Function
;
;Function Transform_SetSize(Transform.Transform, SizeX#, SizeY#, SizeZ#)
;	Transform\Size[0] = SizeX
;	Transform\Size[1] = SizeY
;	Transform\Size[2] = SizeZ
;End Function
;
;Function Transform_Velocity_GetDirection(Transform.Transform)
;	Transform_Velocity_Pitch = ATan(Transform\Velocity[0] / (-Transform\Velocity[1]))
;	Transform_Velocity_Yaw = ATan(Sqr((Transform\Velocity[0] * Transform\Velocity[0]) + (Transform\Velocity[1] * Transform\Velocity[1])) / Transform\Velocity[2])
;End Function
;
;Function Transform_Velocity_GetSpeed#(Transform.Transform)
;	Return Sqr((Transform\Velocity[0] * Transform\Velocity[0]) + (Transform\Velocity[1] * Transform\Velocity[1]) + (Transform\Velocity[2] * Transform\Velocity[2]))
;End Function
;
;Function Transform_DistanceBetween#(Transform.Transform, Other.Transform)
;	Local Dist# = 0
;	
;	Local XI#, YI#, ZI#
;	XI = (Transform\Sector[0] + Transform\Position[0] / UNIVERSE_SECTOR_SIZE) - (Other\Sector[0] + Other\Position[0] / UNIVERSE_SECTOR_SIZE)
;	YI = (Transform\Sector[1] + Transform\Position[1] / UNIVERSE_SECTOR_SIZE) - (Other\Sector[1] + Other\Position[1] / UNIVERSE_SECTOR_SIZE)
;	ZI = (Transform\Sector[2] + Transform\Position[2] / UNIVERSE_SECTOR_SIZE) - (Other\Sector[2] + Other\Position[2] / UNIVERSE_SECTOR_SIZE)
;	Dist = Sqr((XI*XI)+(YI*YI)+(ZI*ZI))
;	
;	Return Dist
;End Function
;
;Function Transform_Update(Delta#)
;	Local Transform.Transform
;	For Transform.Transform = Each Transform
;	; Update Position
;		Transform\Position[0] = Transform\Position[0] + Transform\Velocity[0] * Delta
;		Transform\Position[1] = Transform\Position[1] + Transform\Velocity[1] * Delta
;		Transform\Position[2] = Transform\Position[2] + Transform\Velocity[2] * Delta
;		
;	; Update Sector
;		Local XSector = Int(Transform\Position[0] / UNIVERSE_SECTOR_SIZE)
;		Local YSector = Int(Transform\Position[1] / UNIVERSE_SECTOR_SIZE)
;		Local ZSector = Int(Transform\Position[2] / UNIVERSE_SECTOR_SIZE)
;		Transform\Position[0] = Transform\Position[0] - XSector * UNIVERSE_SECTOR_SIZE
;		Transform\Position[1] = Transform\Position[1] - YSector * UNIVERSE_SECTOR_SIZE
;		Transform\Position[2] = Transform\Position[2] - ZSector * UNIVERSE_SECTOR_SIZE
;		Transform\Sector[0] = Transform\Sector[0] + XSector
;		Transform\Sector[1] = Transform\Sector[1] + YSector
;		Transform\Sector[2] = Transform\Sector[2] + ZSector
;		
;	; Update Rotation
;		Transform\Rotation[0] = Transform\Rotation[0] + Transform\AngularVelocity[0] * Delta
;		Transform\Rotation[1] = Transform\Rotation[1] + Transform\AngularVelocity[1] * Delta
;		Transform\Rotation[2] = Transform\Rotation[2] + Transform\AngularVelocity[2] * Delta
;		
;	; Update Velocity
;		Local VelocityDragDelta# = (UNIVERSE_DECELARATION * Delta)
;		Transform\Velocity[0] = Transform\Velocity[0] * VelocityDragDelta
;		Transform\Velocity[1] = Transform\Velocity[1] * VelocityDragDelta
;		Transform\Velocity[2] = Transform\Velocity[2] * VelocityDragDelta
;		
;	; Update AngularVelocity
;		Transform\AngularVelocity[0] = Transform\AngularVelocity[0] * VelocityDragDelta
;		Transform\AngularVelocity[1] = Transform\AngularVelocity[1] * VelocityDragDelta
;		Transform\AngularVelocity[2] = Transform\AngularVelocity[2] * VelocityDragDelta
;	Next
;End Function

;~IDEal Editor Parameters:
;~C#BlitzPlus