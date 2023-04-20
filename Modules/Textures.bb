Function MakeCubeFromXYZTex(Skybox_ID);XPos, XNeg, YPos, YNeg, ZPos, ZNeg)
	Local XPW, XNW, YPW, YNW, ZPW, ZNW
	Local XPH, XNH, YPH, YNH, ZPH, ZNH
	
	XPW = TextureWidth(Text_SkyRT[Skybox_ID])
	XPH = TextureHeight(Text_SkyRT[Skybox_ID])
	
	XNW = TextureWidth(Text_SkyLF[Skybox_ID])
	XNH = TextureHeight(Text_SkyLF[Skybox_ID])
	
	YPW = TextureWidth(Text_SkyUP[Skybox_ID])
	YPH = TextureHeight(Text_SkyUP[Skybox_ID])
	
	YNW = TextureWidth(Text_SkyDN[Skybox_ID])
	YNH = TextureHeight(Text_SkyDN[Skybox_ID])
	
	ZPW = TextureWidth(Text_SkyFT[Skybox_ID])
	ZPH = TextureHeight(Text_SkyFT[Skybox_ID])
	
	ZNH = TextureHeight(Text_SkyBK[Skybox_ID])
	ZNW = TextureWidth(Text_SkyBK[Skybox_ID])
	
	Local FW = XPW+XNW+YPW+YNW+ZPW+ZNW
	Local FH = XPH+XNH+YPH+YNH+ZPH+ZNH
	
	If (FW <> XPW*6) Then
		Return 0
	ElseIf (FH <> XPH*6) Then
		Return 0
	EndIf
	
	Local Cube = CreateTexture(XPW, XPH, 1+128+256)
	
	; Render Sides
	; - Left
	SetCubeFace Cube, 0
	CopyRect 0, 0, XPW, XPH, 0, 0, TextureBuffer(Text_SkyLF[Skybox_ID]), TextureBuffer(Cube)
	
	; - Right
	SetCubeFace Cube, 2
	CopyRect 0, 0, XPW, XPH, 0, 0, TextureBuffer(Text_SkyRT[Skybox_ID]), TextureBuffer(Cube)
	
	; - Front
	SetCubeFace Cube, 1
	CopyRect 0, 0, XPW, XPH, 0, 0, TextureBuffer(Text_SkyFT[Skybox_ID]), TextureBuffer(Cube)
	
	; - Back
	SetCubeFace Cube, 3
	CopyRect 0, 0, XPW, XPH, 0, 0, TextureBuffer(Text_SkyBK[Skybox_ID]), TextureBuffer(Cube)
	
	; - Top
	SetCubeFace Cube, 4
	CopyRect 0, 0, XPW, XPH, 0, 0, TextureBuffer(Text_SkyUP[Skybox_ID]), TextureBuffer(Cube)
	
	; - Bottom
	SetCubeFace Cube, 5
	CopyRect 0, 0, XPW, XPH, 0, 0, TextureBuffer(Text_SkyDN[Skybox_ID]), TextureBuffer(Cube)
	
	Return Cube
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D