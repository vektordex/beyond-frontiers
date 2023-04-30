Function PreloadAudio()
	;[Block] Weapons
;	Sound_Guns[3]=AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Weapons\Laser_Heavy.wav")
;	SoundVolume Sound_Guns[1],0.4
	;[End Block]
	
	;[Block] Expansions
;	Sound_Explosion[0]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Expansions\Expansion_explosion_distant.wav")
;	Sound_Explosion[1]=AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Expansions\Expansion_explosion_small.wav")
	
	;[End Block]
	
	;[Block] Sounds
	Local LoadOrder = OpenFile("Assets\Manifest\LoadSounds.lof")
	Local LoadCounter = 1
	Repeat
		Local LoadData$ = ReadLine(LoadOrder)
		Sound_ID[LoadCounter] = AssetManager_GetAsset(EAssetType_Sound, "Assets\Sounds\"+LoadData$+".ogg")
		LoadCounter = LoadCounter + 1
	Until Eof(LoadOrder)
	CloseFile LoadOrder
	;[End Block]
	
	;[Block] Music
	LoadOrder = OpenFile("Assets\Manifest\LoadMusic.lof")
	LoadCounter = 1
	Repeat
		LoadData$ = ReadLine(LoadOrder)
		Music_ID[LoadCounter] = AssetManager_GetAsset(EAssetType_Sound, "Assets\Music\"+LoadData$+".ogg")
		LoadCounter = LoadCounter + 1
	Until Eof(LoadOrder)
	CloseFile LoadOrder
	;[End Block]
End Function

Function PreloadUI()
	Local LoadOrder = OpenFile("Assets\Manifest\LoadMenu.lof")
	Local LoadCounter = 1
	Repeat
		Local LoadData$ = ReadLine(LoadOrder)
		GUI_Windows[LoadCounter]=LoadImage3D("Assets\2D\Menu\"+LoadData$,2,2,0,-99);("Assets\2D\Menu\"+LoadData$, 2)
		LoadCounter = LoadCounter + 1
	Until Eof(LoadOrder)
	CloseFile LoadOrder
	
End Function

Function Preload3DFont()
	Local LoadOrder = OpenFile("Assets\Manifest\LoadFonts.lof")
	Local TextID = 1
	Repeat
		Local LoadData$=ReadLine(LoadOrder)
		Text_Font[TextID]=FontRange3D(LoadImage3D("Assets\2D\Fonts\"+LoadData$+".png",2,1,0,-120))
		TextID = TextID+1
	Until Eof(LoadOrder)
	CloseFile LoadOrder
End Function

Function PreloadMesh()
	
	;[Block] Preload Spaceships
	Local LoadOrder = OpenFile("Assets\Manifest\LoadShips.lof")
	Local LoadCounter = 1
	Repeat
		Local LoadData$ = ReadLine(LoadOrder)
		Mesh_Ship[LoadCounter]		=	AssetManager_GetAsset(EAssetType_Model, "Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Mesh.3DS", SceneDataRoot)
		Text_Ship[LoadCounter]		=	AssetManager_GetAsset(EAssetType_Texture,"Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Color.jpg",1+2)
		Text_Ship_FX[LoadCounter]	=	AssetManager_GetAsset(EAssetType_Texture,"Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Glow.jpg",1+2)
		TextureBlend Text_Ship_FX[LoadCounter],3
		EntityTexture Mesh_Ship[LoadCounter],Text_Ship[LoadCounter],0,0
		EntityTexture Mesh_Ship[LoadCounter],Text_Ship_FX[LoadCounter],0,1
		HideEntity Mesh_Ship[LoadCounter]
		LoadCounter = LoadCounter + 1
	Until Eof(LoadOrder)
	CloseFile LoadOrder
	;[End Block]
	
	;[Block] Preload Stations
	LoadOrder = OpenFile("Assets\Manifest\LoadShips.lof")
	LoadCounter = 1
	Repeat
		LoadData$ = ReadLine(LoadOrder)
		Mesh_Station[LoadCounter]		=	AssetManager_GetAsset(EAssetType_Model, "Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Mesh.3DS", SceneDataRoot)
		Text_Station[LoadCounter]		=	AssetManager_GetAsset(EAssetType_Texture,"Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Color.jpg",1+2)
		Text_Station_FX[LoadCounter]	=	AssetManager_GetAsset(EAssetType_Texture,"Assets\3D\Ships\"+LoadData$+"\"+LoadData$+"_Glow.jpg",1+2)
		TextureBlend Text_Station_FX[LoadCounter],3
		EntityTexture Mesh_Station[LoadCounter],Text_Station[LoadCounter],0,0
		EntityTexture Mesh_Station[LoadCounter],Text_Station_FX[LoadCounter],0,1
		HideEntity Mesh_Station[LoadCounter]
		LoadCounter = LoadCounter + 1
	Until Eof(LoadOrder)
	CloseFile LoadOrder
	;[End Block]
	
	;[Block] Skybox Cleanup down to Regional Space
	LoadOrder = OpenFile("Assets\Manifest\LoadSkybox.lof")
	LoadCounter = 1
	Repeat
		LoadData$ = ReadLine(LoadOrder)
		Text_SkyBK[LoadCounter] = AssetManager_GetAsset(EAssetType_Texture, "Assets\3D\Skybox\"+LoadData$+"BK.png", 1+16+32+512)
		Text_SkyDN[LoadCounter] = AssetManager_GetAsset(EAssetType_Texture, "Assets\3D\Skybox\"+LoadData$+"DN.png", 1+16+32+512)
		Text_SkyFT[LoadCounter] = AssetManager_GetAsset(EAssetType_Texture, "Assets\3D\Skybox\"+LoadData$+"FT.png", 1+16+32+512)
		Text_SkyLF[LoadCounter] = AssetManager_GetAsset(EAssetType_Texture, "Assets\3D\Skybox\"+LoadData$+"LF.png", 1+16+32+512)
		Text_SkyRT[LoadCounter] = AssetManager_GetAsset(EAssetType_Texture, "Assets\3D\Skybox\"+LoadData$+"RT.png", 1+16+32+512)
		Text_SkyUP[LoadCounter] = AssetManager_GetAsset(EAssetType_Texture, "Assets\3D\Skybox\"+LoadData$+"UP.png", 1+16+32+512)
	
		Text_SkyCM[LoadCounter] = MakeCubeFromXYZTex(LoadCounter)
		SetCubeMode Text_SkyCM[LoadCounter], 3
	Until Eof(LoadOrder)
	;[End Block]
End Function

;~IDEal Editor Parameters:
;~F#C
;~C#Blitz3D