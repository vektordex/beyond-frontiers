;----------------------------------------------------------------
;-- Includes
;----------------------------------------------------------------
;Include "Vector2D.bb"
;Include "Vector3D.bb"

;----------------------------------------------------------------
;-- Types
;----------------------------------------------------------------
;--- Shapes (2D)
Type Line2D
	Field Start.Vector2D = New Vector2D
	Field End.Vector2D = New Vector2D
End Type

Type Triangle2D
	Field A.Vector2D = New Vector2D
	Field B.Vector2D = New Vector2D
	Field C.Vector2D = New Vector2D
	Field Rotation#
End Type

Type Rectangle
	Field Position.Vector2D = New Vector2D
	Field Size.Vector2D = New Vector2D
	Field Rotation#
End Type

Type Ellipse
	Field Position.Vector2D = New Vector2D
	Field Radius.Vector2D = New Vector2D
	Field Rotation#
End Type

;--- Shapes (3D)
Type Line3D
	Field Start.Vector3D = New Vector3D
	Field End.Vector3D = New Vector3D
End Type

Type Triangle3D
	Field A.Vector3D = New Vector3D
	Field B.Vector3D = New Vector3D
	Field C.Vector3D = New Vector3D
	Field Rotation.Vector3D = New Vector3D
End Type

Type Box
	Field Position.Vector3D = New Vector3D
	Field Length.Vector3D = New Vector3D
	Field Rotation.Vector3D = New Vector3D
End Type

Type Ellipsoid
	Field Position.Vector3D = New Vector3D
	Field Radius.Vector3D = New Vector3D
	Field Rotation.Vector3D = New Vector3D
End Type




;~IDEal Editor Parameters:
;~C#Blitz3D