Function LineAs3D(StartX#, StartY#, StartZ#, EndX#, EndY#, EndZ#, Camera%, DEPRECATED_AS_IT_IS_2D_AND_SLOW%=True)
    Local Screen_Start#[2], Screen_End#[2]
	
    CameraProject Camera, StartX, StartY, StartZ
    Screen_Start[0] = ProjectedX()
    Screen_Start[1] = ProjectedY()
    Screen_Start[2] = ProjectedZ()
    CameraProject Camera, EndX, EndY, EndZ
    Screen_End[0] = ProjectedX()
    Screen_End[1] = ProjectedY()
    Screen_End[2] = ProjectedZ()
	
    If Screen_Start[2] = 1 And Screen_End[2] = 1 Then
        Line Screen_Start[0], Screen_Start[1], Screen_End[0], Screen_End[1]
    EndIf
End Function

Function Location_Create.Location(X%, Y%, Z%) ;X,Y,Z must be *256 to be unit accurate.
	Local Loc.Location = New Location
	Loc\X = X
	Loc\Y = Y
	Loc\Z = Z
	Return Loc
End Function
Function Location_GetX#(Loc.Location, RelativeTo%=0)
	Return (Loc\X - RelativeTo) / 256.0
End Function
Function Location_GetY#(Loc.Location, RelativeTo%=0)
	Return (Loc\Y - RelativeTo) / 256.0
End Function
Function Location_GetZ#(Loc.Location, RelativeTo%=0)
	Return (Loc\Z - RelativeTo) / 256.0
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D