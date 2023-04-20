;----------------------------------------------------------------
;-- Types
;----------------------------------------------------------------
Type Vector2D
	Field X#, Y#
End Type

;----------------------------------------------------------------
;-- Globals
;----------------------------------------------------------------
; Internal
Global Vector2D_SerializeBank		= CreateBank(8)

; Utility/Helpers
Global Vector2D_Left.Vector2D		= Vector2D_Create(-1,  0)
Global Vector2D_Right.Vector2D		= Vector2D_Create( 1,  0)
Global Vector2D_Up.Vector2D			= Vector2D_Create( 0,  1)
Global Vector2D_Down.Vector2D		= Vector2D_Create( 0, -1)

;----------------------------------------------------------------
;-- Functions
;----------------------------------------------------------------
Function Vector2D_Create.Vector2D(X#, Y#)
	Local V.Vector2D = New Vector2D
	V\X = X
	V\Y = Y
	Return V
End Function
Function Vector2D_Copy.Vector2D(From.Vector2D)
	Return Vector2D_Create(From\X, From\Y)
End Function

Function Vector2D_Add.Vector2D(A.Vector2D, B#)
	A\X=A\X+B
	A\Y=A\Y+B
	Return A
End Function
Function Vector2D_AddV.Vector2D(A.Vector2D, B.Vector2D)
	A\X = A\X + B\X
	A\Y = A\Y + B\Y
	Return A
End Function

Function Vector2D_Sub.Vector2D(A.Vector2D, B#)
	A\X=A\X-B
	A\Y=A\Y-B
	Return A
End Function
Function Vector2D_SubV.Vector2D(A.Vector2D, B.Vector2D)
	A\X = A\X - B\X
	A\Y = A\Y - B\Y
	Return A
End Function

Function Vector2D_Mul.Vector2D(A.Vector2D, B#)
	A\X=A\X*B
	A\Y=A\Y*B
	Return A
End Function
Function Vector2D_MulV.Vector2D(A.Vector2D, B.Vector2D)
	A\X = A\X * B\X
	A\Y = A\Y * B\Y
	Return A
End Function

Function Vector2D_Div.Vector2D(A.Vector2D, B#)
	A\X=A\X/B
	A\Y=A\Y/B
	Return A
End Function
Function Vector2D_DivV.Vector2D(A.Vector2D, B.Vector2D)
	A\X = A\X / B\X
	A\Y = A\Y / B\Y
	Return A
End Function

Function Vector2D_Length#(A.Vector2D)
	Return Sqr((A\X*A\X)+(A\Y*A\Y))
End Function
Function Vector2D_Distance#(A.Vector2D, B.Vector2D)
	Local X# = (A\X-B\X)
	Local Y# = (A\Y-B\Y)
	Return Sqr(X*X+Y*Y)
End Function

Function Vector2D_Dot#(A.Vector2D, B.Vector2D)
	Return ((A\X*B\X)+(A\Y*B\Y))
End Function
Function Vector2D_Normalize.Vector2D(A.Vector2D)
	Return Vector2D_Div(A, Vector2D_Length(A))
End Function

Function Vector2D_Rotate.Vector2D(A.Vector2D, Rotation#)
	Local M1# = Cos(Rotation)
	Local M2# = Sin(Rotation)
	Local X#, Y#
	
	X = A\X:Y = A\Y
	A\X = (X * M1) + (Y * M2)
	A\Y = (X * M2) + (Y * M1)
	
	Return A
End Function
Function Vector2D_RotateAround.Vector2D(A.Vector2D, B.Vector2D, Rotation#)
	Return Vector2D_AddV(Vector2D_Rotate(Vector2D_SubV(A, B), Rotation), B)
End Function
Function Vector2D_RotateAroundPoint.Vector2D(A.Vector2D, X#, Y#, Rotation#)
	Local V.Vector2D = Vector2D_Create(X, Y)
	Vector2D_RotateAround(A, V, Rotation)
	Delete V:Return A
End Function

Function Vector2D_DeltaRotation#(A.Vector2D, B.Vector2D)
	Return ATan2(A\Y-B\Y, A\X-B\X)
End Function

Function Vector2D_ToString$(A.Vector2D)
	Return "{X:"+A\X+";Y:"+A\Y+"}"
End Function
Function Vector2D_Serialize$(A.Vector2D)
	PokeFloat Vector2D_SerializeBank, 0, A\X:PokeFloat Vector2D_SerializeBank, 4, A\Y
	
	Local Out$, Pos%
	For Pos = 0 To 7
		Out = Out + Chr(PeekByte(Vector2D_SerializeBank, Pos))
	Next
	Return Out
End Function
Function Vector2D_Deserialize.Vector2D(A$)
	If Len(A) <> 8 Then Return Null
	
	Local Out.Vector2D = New Vector2D, Pos%
	For Pos = 0 To 7
		PokeByte Vector2D_SerializeBank, Pos, Asc(Mid(A, Pos+1, 1))
	Next
	
	Out\X = PeekFloat(Vector2D_SerializeBank, 0)
	Out\Y = PeekFloat(Vector2D_SerializeBank, 4)
	Return Out
End Function

;~IDEal Editor Parameters:
;~F#16#1C#20#25#2B#30#36#3B#41#46#4C#4F#55#58#5C#67#6A#70#74#77
;~F#80
;~C#Blitz3D