;----------------------------------------------------------------
;-- Types
;----------------------------------------------------------------
Type Vector3D
	Field X#, Y#, Z#
End Type

;----------------------------------------------------------------
;-- Globals
;----------------------------------------------------------------
; Internal
Global Vector3D_SerializeBank		= CreateBank(12)

; Utility/Helpers
Global Vector3D_Forward.Vector3D	= Vector3D_Create( 0,  0,  1)
Global Vector3D_Backward.Vector3D	= Vector3D_Create( 0,  0, -1)
Global Vector3D_Left.Vector3D		= Vector3D_Create(-1,  0,  0)
Global Vector3D_Right.Vector3D		= Vector3D_Create( 1,  0,  0)
Global Vector3D_Up.Vector3D			= Vector3D_Create( 0,  1,  0)
Global Vector3D_Down.Vector3D		= Vector3D_Create( 0, -1,  0)

; Results
Global Vector3D_Pitch#, Vector3D_Yaw#

;----------------------------------------------------------------
;-- Functions
;----------------------------------------------------------------
Function Vector3D_Create.Vector3D(X#, Y#, Z#)
	Local V.Vector3D = New Vector3D
	V\X = X
	V\Y = Y
	V\Z = Z
	Return V
End Function
Function Vector3D_Copy.Vector3D(A.Vector3D)
	Return Vector3D_Create(A\X, A\Y, A\Z)
End Function

Function Vector3D_Add.Vector3D(A.Vector3D, B#)
	A\X = A\X + B
	A\Y = A\Y + B
	A\Z = A\Z + B
	Return A
End Function
Function Vector3D_AddV.Vector3D(A.Vector3D, B.Vector3D)
	A\X = A\X + B\X
	A\Y = A\Y + B\Y
	A\Z = A\Z + B\Z
	Return A
End Function

Function Vector3D_Sub.Vector3D(A.Vector3D, B#)
	A\X = A\X - B
	A\Y = A\Y - B
	A\Z = A\Z - B
	Return A
End Function
Function Vector3D_SubV.Vector3D(A.Vector3D, B.Vector3D)
	A\X = A\X - B\X
	A\Y = A\Y - B\Y
	A\Z = A\Z - B\Z
	Return A
End Function

Function Vector3D_Mul.Vector3D(A.Vector3D, B#)
	A\X = A\X * B
	A\Y = A\Y * B
	A\Z = A\Z * B
	Return A
End Function
Function Vector3D_MulV.Vector3D(A.Vector3D, B.Vector3D)
	A\X = A\X * B\X
	A\Y = A\Y * B\Y
	A\Z = A\Z * B\Z
	Return A
End Function

Function Vector3D_Div.Vector3D(A.Vector3D, B#)
	A\X = A\X / B
	A\Y = A\Y / B
	A\Z = A\Z / B
	Return A
End Function
Function Vector3D_DivV.Vector3D(A.Vector3D, B.Vector3D)
	A\X = A\X / B\X
	A\Y = A\Y / B\Y
	A\Z = A\Z / B\Z
	Return A
End Function

Function Vector3D_Length#(A.Vector3D)
	Return Sqr((A\X*A\X)+(A\Y*A\Y)+(A\Z*A\Z))
End Function
Function Vector3D_Distance#(A.Vector3D, B.Vector3D)
	Local X# = (A\X-B\X)
	Local Y# = (A\Y-B\Y)
	Local Z# = (A\Z-B\Z)
	Return Sqr(X*X+Y*Y+Z*Z)
End Function

Function Vector3D_Dot#(A.Vector3D, B.Vector3D)
	Return ((A\X*B\X)+(A\Y*B\Y)+(A\Z*B\Z))
End Function
Function Vector3D_Cross.Vector3D(A.Vector3D, B.Vector3D)
	Local R.Vector3D = New Vector3D
	R\X = (A\Y*B\Z) - (A\Z*B\Y)
	R\Y = (A\Z*B\X) - (A\X*B\Z)
	R\Z = (A\X*B\Y) - (A\Y*B\X)
	Return R
End Function
Function Vector3D_Normalize.Vector3D(A.Vector3D)
	Return Vector3D_Div(A, Vector3D_Length(A))
End Function

Function Vector3D_Rotate.Vector3D(A.Vector3D, Pitch#, Yaw#, Roll#)
	Local MXX#, MXY#, MXZ#
	Local MYX#, MYY#, MYZ#
	Local MZX#, MZY#, MZZ#
	Local X#, Y#, Z#
	
	Yaw = -Yaw
	
	; Calculate Matrix
	MXX = Cos(Yaw) * Cos(Roll)		:MXY = -Sin(Roll)				:MXZ = Sin(Yaw)
	MYX = Sin(Roll)					:MYY = Cos(Pitch) * Cos(Roll)	:MYZ = -Sin(Pitch)
	MZX = -Sin(Yaw)					:MZY = Sin(Pitch)				:MZZ = Cos(Pitch) * Cos(Yaw)
	X = A\X							:Y = A\Y						:Z = A\Z
	
	; Apply Matrix
	A\X = (X * MXX) + (Y * MXY) + (Z * MXZ)
	A\Y = (X * MYX) + (Y * MYY) + (Z * MYZ)
	A\Z = (X * MZX) + (Y * MZY) + (Z * MZZ)
	
	Return A
End Function
Function Vector3D_RotateAround.Vector3D(A.Vector3D, B.Vector3D, Pitch#, Yaw#, Roll#)
	Return Vector3D_AddV(Vector3D_Rotate(Vector3D_SubV(A, B), Pitch, Yaw, Roll), B)
End Function
Function Vector3D_RotateAroundPoint.Vector3D(A.Vector3D, X#, Y#, Z#, Pitch#, Yaw#, Roll#)
	Local V.Vector3D = Vector3D_Create(X, Y, Z)
	Vector3D_RotateAround(A, V, Pitch, Yaw, Roll)
	Delete V:Return A
End Function

Function Vector3D_EulerAngles(A.Vector3D)
	;;tan(Y) = x / (-y)
	Vector3D_Pitch = ATan(A\X / (-A\Y))
	;Vector3D_Pitch = VectorPitch(A\X, A\Y, A\Z)
	;;tan(P) = sqrt(X^2 + y^2) /z
	Vector3D_Yaw = ATan(Sqr((A\X*A\X) + (A\Y*A\Y)) / A\Z)
	;Vector3D_Yaw = VectorYaw(A\X, A\Y, A\Z)
End Function
Function Vector3D_EulerAnglesTo(A.Vector3D, B.Vector3D)
	Local C.Vector3D = Vector3D_Copy(A):Vector3D_SubV(C, B)
	
	Vector3D_Pitch = ATan(C\X / (-C\Y))
	;Vector3D_Pitch = VectorPitch(C\X, C\Y, C\Z)
	Vector3D_Yaw = ATan(Sqr((C\X*C\X) + (C\Y*C\Y)) / C\Z)
	;Vector3D_Yaw = VectorYaw(C\X, C\Y, C\Z)
	Delete C
End Function

Function Vector3D_DeltaPitch#(A.Vector3D, B.Vector3D) ; X
	Local C.Vector3D = Vector3D_Copy(A):Vector3D_SubV(C, B)
	Local Res# = ATan(C\X / (-C\Y))
	;Local Res# = VectorPitch(C\X, C\Y, C\Z)
	Delete C:Return Res
End Function
Function Vector3D_DeltaYaw#(A.Vector3D, B.Vector3D) ; Y
	Local C.Vector3D = Vector3D_Copy(A):Vector3D_SubV(C, B)
	Local Res# = ATan(Sqr((C\X*C\X) + (C\Y*C\Y)) / C\Z)
	;Local Res# = VectorYaw(C\X, C\Y, C\Z)
	Delete C:Return Res
End Function

Function Vector3D_ToString$(A.Vector3D)
	Return "{X:"+A\X+";Y:"+A\Y+";Z:"+A\Z+"}"
End Function
Function Vector3D_Serialize$(A.Vector3D)
	PokeFloat Vector3D_SerializeBank, 0, A\X:PokeFloat Vector3D_SerializeBank, 4, A\Y:PokeFloat Vector3D_SerializeBank, 8, A\Z
	
	Local Out$, Pos%
	For Pos = 0 To 11
		Out = Out + Chr(PeekByte(Vector2D_SerializeBank, Pos))
	Next
	Return Out
End Function
Function Vector3D_Deserialize.Vector3D(A$)
	If Len(A) <> 12 Then Return Null
	
	Local Out.Vector3D = New Vector3D, Pos%
	For Pos = 0 To 11
		PokeByte Vector3D_SerializeBank, Pos, Asc(Mid(A, Pos+1, 1))
	Next
	
	Out\X = PeekFloat(Vector3D_SerializeBank, 0)
	Out\Y = PeekFloat(Vector3D_SerializeBank, 4)
	Out\Z = PeekFloat(Vector3D_SerializeBank, 8)
	Return Out
End Function

;~IDEal Editor Parameters:
;~F#1B#22#26#2C#33#39#40#46#4D#53#5A#5D#64#67#6E#72#87#8A#90#98
;~F#A2#A8#AF#B2#BB
;~C#Blitz3D