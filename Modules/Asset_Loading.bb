Function PreloadAudio()
	;[Block] Weapons
;	Sound_Guns[3]=AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Weapons\Laser_Heavy.wav")
;	SoundVolume Sound_Guns[1],0.4
	;[End Block]
	
	;[Block] Expansions
;	Sound_Explosion[0]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Expansions\Expansion_explosion_distant.wav")
;	Sound_Explosion[1]=AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Expansions\Expansion_explosion_small.wav")
	
	;[End Block]
End Function

Function PreloadUI()
	
;	GUI_Windows[0]=LoadImage3D("Content\GFX\Interface\Menus\Options_Background.png",2,2,0,-99)
	
End Function

Function Preload3DFont()
	Local LoadOrder = OpenFile("Assets\Manifest\LoadFonts.lof")
	Local TextID = 1
	Repeat
		Local LoadData$=ReadLine(LoadOrder)
		Text_Font[TextID]=FontRange3D(LoadImage3D("Assets\2D\Fonts\"+LoadData$+".jpg",2,1,0,-120))
		TextID = TextID+1
	Until Eof(LoadOrder)
	CloseFile LoadOrder
End Function

Function PreloadMesh()
	
	;[Block] Preload Spaceships
	Mesh_Ship[0] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Ships\Ship_flare.3DS", SceneDataRoot)
	Mesh_Ship[1] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Ships\Ship_1.3DS", SceneDataRoot)
	Mesh_Ship[2] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Ships\Ship_2.3DS", SceneDataRoot)
	Mesh_Ship[3] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Ships\Ship_3.3DS", SceneDataRoot)
	Mesh_Ship[5] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Ships\Ship_5.3DS", SceneDataRoot)
	Mesh_Ship[6] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Ships\Ship_6.3DS", SceneDataRoot)
	Mesh_Ship[7] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Ships\Ship_7.3DS", SceneDataRoot)
	Mesh_Ship[8] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Ships\Ship_8.3DS", SceneDataRoot)
	Mesh_Ship[9] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Ships\Ship_9.3DS", SceneDataRoot)
	
	Text_Ship[0]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_flare_clr.png",1+2)
	Text_Ship[1]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_1_clr.jpg")
	Text_Ship[2]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_2_clr.jpg")
	Text_Ship[3]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_3_clr.jpg")
	Text_Ship[5]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_5_clr.jpg")
	Text_Ship[6]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_6_clr.jpg")
	Text_Ship[7]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_7_clr.jpg")
	Text_Ship[8]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_8_clr.jpg")
	Text_Ship[9]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_9_clr.jpg")
	
	Text_Ship_FX[0]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_flare_noise.png",1+2)
	Text_Ship_FX[1]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_1_glow.jpg")
	Text_Ship_FX[2]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_2_glow.jpg")
	Text_Ship_FX[3]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_3_glow.jpg")
	Text_Ship_FX[5]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_5_glow.jpg")
	Text_Ship_FX[6]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_6_glow.jpg")
	Text_Ship_FX[7]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_7_glow.jpg")
	Text_Ship_FX[8]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_8_glow.jpg")
	Text_Ship_FX[9]=AssetManager_GetAsset(EAssetType_Texture,"Content\GFX\3D\Ships\Ship_9_glow.jpg")
	
	
	;[Block] Scale and Texture Ships
	TextureBlend Text_Ship_FX[0],3
	TextureBlend Text_Ship_FX[1],3
	TextureBlend Text_Ship_FX[2],3
	TextureBlend Text_Ship_FX[3],3
	TextureBlend Text_Ship_FX[5],3
	TextureBlend Text_Ship_FX[6],3
	TextureBlend Text_Ship_FX[7],3
	TextureBlend Text_Ship_FX[8],3
	TextureBlend Text_Ship_FX[9],3
	
	EntityTexture Mesh_Ship[0],Text_Ship[0],0,0
	EntityTexture Mesh_Ship[1],Text_Ship[1],0,0
	EntityTexture Mesh_Ship[2],Text_Ship[2],0,0
	EntityTexture Mesh_Ship[3],Text_Ship[3],0,0
	EntityTexture Mesh_Ship[5],Text_Ship[5],0,0
	EntityTexture Mesh_Ship[6],Text_Ship[6],0,0
	EntityTexture Mesh_Ship[7],Text_Ship[7],0,0
	EntityTexture Mesh_Ship[8],Text_Ship[8],0,0
	EntityTexture Mesh_Ship[9],Text_Ship[9],0,0
	
;	EntityTexture Mesh_Ship[0],Text_Ship_FX[0],0,1
	EntityTexture Mesh_Ship[1],Text_Ship_FX[1],0,1
	EntityTexture Mesh_Ship[2],Text_Ship_FX[2],0,1
	EntityTexture Mesh_Ship[3],Text_Ship_FX[3],0,1
	EntityTexture Mesh_Ship[5],Text_Ship_FX[5],0,1
	EntityTexture Mesh_Ship[6],Text_Ship_FX[6],0,1
	EntityTexture Mesh_Ship[7],Text_Ship_FX[7],0,1
	EntityTexture Mesh_Ship[8],Text_Ship_FX[8],0,1
	EntityTexture Mesh_Ship[9],Text_Ship_FX[9],0,1
	
	RotateMesh Mesh_Ship[0],0,180,180
	
	ScaleEntity Mesh_Ship[3],3,3,3
	ScaleEntity Mesh_Ship[5],6,6,6
	
	ScaleTexture Text_Ship_FX[0],10,10
	
	HideEntity Mesh_Ship[0]
	HideEntity Mesh_Ship[1]
	HideEntity Mesh_Ship[2]
	HideEntity Mesh_Ship[3]
	HideEntity Mesh_Ship[5]
	HideEntity Mesh_Ship[6]
	HideEntity Mesh_Ship[7]
	HideEntity Mesh_Ship[8]
	HideEntity Mesh_Ship[9]
	;[End Block]
	
	;[End Block]
	
	;[Block] Prepare Station
	Mesh_Station[1]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Station\Station_Essential.3ds", SceneDataRoot)
	Mesh_Station[2]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Station\Station_Ring.3ds", SceneDataRoot)
	Mesh_Station[3]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Station\Station_Factory.3ds", SceneDataRoot)
	Mesh_Station[4]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Station\Station_Power.3ds", SceneDataRoot)
	
	Text_Station[1]= AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Station\Station_Body_clr.jpg")
	Text_Station[2]= AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Station\Station_Body_glow.jpg")
	
	TextureBlend Text_Station[2],3
	
	EntityTexture Mesh_Station[1], Text_Station[1],0,0
	EntityTexture Mesh_Station[1], Text_Station[2],0,1
	EntityTexture Mesh_Station[3], Text_Station[1],0,0
	EntityTexture Mesh_Station[3], Text_Station[2],0,1
	EntityTexture Mesh_Station[4], Text_Station[1],0,0
	EntityTexture Mesh_Station[4], Text_Station[2],0,1
	EntityTexture Mesh_Station[2], Text_Station[1],0,0
	EntityTexture Mesh_Station[2], Text_Station[2],0,1
	
	HideEntity Mesh_Station[1]
	HideEntity Mesh_Station[3]
	HideEntity Mesh_Station[4]
	HideEntity Mesh_Station[2]
	;[End Block]
	
	;[Block] Prepare Stargate
	Mesh_Gate[1]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\Stargate.3ds", SceneDataRoot)
	Mesh_Gate[2]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\Stargate_Horizon.3ds", SceneDataRoot)
	Mesh_Gate[3]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\Stargate_beams.3ds", SceneDataRoot)
	Mesh_Gate[4]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\racegate.3ds", SceneDataRoot)
	
	ScaleMesh Mesh_Gate[4],5,5,5
	
	Text_Gate[0]= AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\Stargate_Horizon.png",1+2)
	Text_Gate[1]= AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\Stargate_clr.jpg")
	Text_Gate[2]= AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\Stargate_glow.jpg")
	Text_Gate[3]= AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\Blend_TopBot.png",1+2)
	
	TextureBlend Text_Gate[2],3
	ScaleTexture Text_Gate[0],0.05,.5
	ScaleTexture Text_Gate[3],1,.5
	TextureBlend Text_Gate[3],2
	
	EntityTexture Mesh_Gate[1], Text_Gate[1],0,0
	EntityTexture Mesh_Gate[1], Text_Gate[2],0,1
	
	EntityTexture Mesh_Gate[3], Text_Gate[0],0,0
	EntityTexture Mesh_Gate[3], Text_Gate[3],0,1
	
	HideEntity Mesh_Gate[1]
	HideEntity Mesh_Gate[2]
	;[End Block]
	
	;[Block] Skybox Cleanup down to Regional Space
	Text_SkyBK[1] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\G2BK.png", 1+16+32+512)
	Text_SkyBK[2] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\O1BK.png", 1+16+32+512)
	Text_SkyBK[3] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\R1BK.png", 1+16+32+512)
	Text_SkyBK[4] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\R2BK.png", 1+16+32+512)
	Text_SkyBK[5] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W1BK.png", 1+16+32+512)
	Text_SkyBK[6] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W3BK.png", 1+16+32+512)
	Text_SkyBK[7] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W4BK.png", 1+16+32+512)
	Text_SkyBK[8] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\B2BK.png", 1+16+32+512)
	
	Text_SkyDN[1] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\G2DN.png", 1+16+32+512)
	Text_SkyDN[2] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\O1DN.png", 1+16+32+512)
	Text_SkyDN[3] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\R1DN.png", 1+16+32+512)
	Text_SkyDN[4] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\R2DN.png", 1+16+32+512)
	Text_SkyDN[5] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W1DN.png", 1+16+32+512)
	Text_SkyDN[6] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W3DN.png", 1+16+32+512)
	Text_SkyDN[7] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W4DN.png", 1+16+32+512)
	Text_SkyDN[8] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\B2DN.png", 1+16+32+512)
	
	Text_SkyFT[1] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\G2FT.png", 1+16+32+512)
	Text_SkyFT[2] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\O1FT.png", 1+16+32+512)
	Text_SkyFT[3] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\R1FT.png", 1+16+32+512)
	Text_SkyFT[4] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\R2FT.png", 1+16+32+512)
	Text_SkyFT[5] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W1FT.png", 1+16+32+512)
	Text_SkyFT[6] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W3FT.png", 1+16+32+512)
	Text_SkyFT[7] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W4FT.png", 1+16+32+512)
	Text_SkyFT[8] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\B2FT.png", 1+16+32+512)
	
	Text_SkyLF[1] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\G2LF.png", 1+16+32+512)
	Text_SkyLF[2] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\O1LF.png", 1+16+32+512)
	Text_SkyLF[3] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\R1LF.png", 1+16+32+512)
	Text_SkyLF[4] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\R2LF.png", 1+16+32+512)
	Text_SkyLF[5] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W1LF.png", 1+16+32+512)
	Text_SkyLF[6] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W3LF.png", 1+16+32+512)
	Text_SkyLF[7] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W4LF.png", 1+16+32+512)
	Text_SkyLF[8] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\B2LF.png", 1+16+32+512)
	
	Text_SkyRT[1] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\G2RT.png", 1+16+32+512)
	Text_SkyRT[2] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\O1RT.png", 1+16+32+512)
	Text_SkyRT[3] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\R1RT.png", 1+16+32+512)
	Text_SkyRT[4] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\R2RT.png", 1+16+32+512)
	Text_SkyRT[5] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W1RT.png", 1+16+32+512)
	Text_SkyRT[6] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W3RT.png", 1+16+32+512)
	Text_SkyRT[7] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W4RT.png", 1+16+32+512)
	Text_SkyRT[8] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\B2RT.png", 1+16+32+512)
	
	Text_SkyUP[1] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\G2UP.png", 1+16+32+512)
	Text_SkyUP[2] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\O1UP.png", 1+16+32+512)
	Text_SkyUP[3] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\R1UP.png", 1+16+32+512)
	Text_SkyUP[4] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\R2UP.png", 1+16+32+512)
	Text_SkyUP[5] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W1UP.png", 1+16+32+512)
	Text_SkyUP[6] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W3UP.png", 1+16+32+512)
	Text_SkyUP[7] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\W4UP.png", 1+16+32+512)
	Text_SkyUP[8] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\SkyBox\B2UP.png", 1+16+32+512)
	
	For A = 1 To 8
		Text_SkyCM[a] = MakeCubeFromXYZTex(a)
		SetCubeMode Text_SkyCM[a], 3
	Next
	;[End Block]
	
	;[Block] Effects
	Mesh_Effects[0] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\primitives\icosphere.3ds", SceneDataRoot);CopyEntity(Mesh_Effects[0])
	Mesh_Effects[1] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Utilities\object_miningsiphon.3ds", SceneDataRoot);CopyEntity(Mesh_Effects[0])
	Text_Effects[1] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\textures\texture_miningsiphon.png",1+2)
	Text_Effects[2] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Environment\Effects\expansion_Eclipse.png",1+2)
	Text_Effects[3] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Environment\Effects\explosion_wave.png",1+2)
	Text_Effects[6] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Environment\Effects\expansion_scanner_wave.png",1+2)
	Text_Effects[7] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Environment\Effects\explosion_lightflash.png",1+2)
	ScaleTexture Text_Effects[1],1,0.1
	ScaleTexture Text_Effects[2],10000,10000
	EntityTexture Mesh_Effects[1], Text_Effects[1]
	EntityFX Mesh_Effects[1],1
	
	For Explosion_Sprite = 1 To 64
		Text_Explosion[Explosion_Sprite] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Environment\Effects\explosion_frames\"+Explosion_Sprite+".png",1+2)
	Next
	
	Mesh_Effects[4] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\lootbox.3DS", SceneDataRoot)
	Text_Effects[4] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\lootbox_col.png", 1)
	Text_Effects[5] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\lootbox_glo.png", 1)
	
	TextureBlend Text_Effects[5],3
	EntityTexture Mesh_Effects[4],Text_Effects[4],0
	EntityTexture Mesh_Effects[4],Text_Effects[5],1
	HideEntity Mesh_Effects[4]
	
	Mesh_Effects[5] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\lootcan.3DS", SceneDataRoot)
	Text_Effects[8] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\lootcan_blue.jpg")
	Text_Effects[9] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\lootcan_green.jpg")
	Text_Effects[10] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\lootcan_red.jpg")
	Text_Effects[11] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\lootcan_orange.jpg")
	Text_Effects[12] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\lootcan_pink.jpg")
	
	Text_Flag[0] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Interface\MainMenu\Flag_Faction_Neutral.png", 2)
	Text_Flag[1] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Interface\MainMenu\Flag_Faction_Terra.png", 2)
	Text_Flag[2] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Interface\MainMenu\Flag_Faction_Sirius.png", 2)
	Text_Flag[3] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Interface\MainMenu\Flag_Faction_Neutral.png", 2)
	Text_Flag[4] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Interface\MainMenu\Flag_Faction_Neutral.png", 2)
	Text_Flag[5] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Interface\MainMenu\Flag_Faction_Pirate.png", 2)
	Text_Flag[6] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Interface\MainMenu\Flag_Faction_Crimson.png", 2)
	
	;[End Block]
	
	;[Block] Asteroids
	Mesh_Roid[1] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\asteroid_rock.3ds", SceneDataRoot)
	Mesh_Roid[2] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\asteroid_ice.3ds", SceneDataRoot)
	Mesh_Roid[3] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\asteroid_spread.3ds", SceneDataRoot)
	Mesh_Roid[4] = AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\asteroid_hole.3ds", SceneDataRoot)
	HideEntity Mesh_Roid[1]
	HideEntity Mesh_Roid[2]
	HideEntity Mesh_Roid[3]
	HideEntity Mesh_Roid[4]
	
	Text_Roid[1]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Textures\texture_asteroid_iron.png",1)
	Text_Roid[2]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Textures\texture_asteroid_chalk.png",1)
	Text_Roid[3]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Textures\texture_asteroid_lava.png",1)
	Text_Roid[4]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Textures\texture_asteroid_ice.png",1+64)
	
	Text_Roid_Illumination[3]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Textures\texture_asteroid_lava_glow.png",1)
	
	ScaleEntity Mesh_Roid[2],200,200,200
	
	TextureBlend Text_Roid_Illumination[3],3
	
	ScaleTexture Text_Roid_Illumination[3],0.25,0.25
	;[End Block]
	
	;[Block] Scrap Particles
	Mesh_Scrap[1]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\SCRAP_particle_01.3ds", SceneDataRoot)
	Mesh_Scrap[2]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\SCRAP_particle_02.3ds", SceneDataRoot)
	Mesh_Scrap[3]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\SCRAP_particle_03.3ds", SceneDataRoot)
	Mesh_Scrap[4]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\SCRAP_particle_04.3ds", SceneDataRoot)
	
	Text_Scrap=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Textures\texture_scrap_basic.PNG", 1)
	;[End Block]
	
	;[Block] Prepare Planets
	Text_Planet[1]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Alpine_001.png", 1+8)
	Text_Planet[2]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Fungal_001.png", 1+8)
	Text_Planet[3]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Gas_001.png", 1+8)
	Text_Planet[4]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Gas_002.png", 1+8)
	Text_Planet[5]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Gas_003.png", 1+8)
	Text_Planet[6]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Gas_004.png", 1+8)
	Text_Planet[7]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Ice_001.png", 1+8)
	Text_Planet[8]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Moon_001.png", 1+8)
	Text_Planet[9]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Moon_002.png", 1+8)
	Text_Planet[10]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Mud_001.png", 1+8)
	Text_Planet[11]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Oasis_001.png", 1+8)
	Text_Planet[12]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Oasis_002.png", 1+8)
	Text_Planet[13]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Oasis_003.png", 1+8)
	Text_Planet[14]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Oceanic_002.png", 1+8)
	Text_Planet[15]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Primordial_002.png", 1+8)
	Text_Planet[16]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Savannah_001.png", 1+8)
	Text_Planet[17]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Tundra_002.png", 1+8)
	Text_Planet[18]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Planets\Terra_001.png", 1+8)
	
	Text_Brush[1] = LoadBrush("Content\GFX\Planets\Ring_1a.png",32+2)
	Text_Brush[2] = LoadBrush("Content\GFX\Planets\Ring_1b.png",32+2)
	Text_Brush[3] = LoadBrush("Content\GFX\Planets\Ring_2a.png",32+2)
	Text_Brush[4] = LoadBrush("Content\GFX\Planets\Ring_2b.png",32+2)
	Text_Brush[5] = LoadBrush("Content\GFX\Planets\Ring_3a.png",32+2)
	Text_Brush[6] = LoadBrush("Content\GFX\Planets\Ring_3b.png",32+2)
	;[End Block]
	
	Text_Effects[9] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Textures\texture_fog_basic.tga", 1+2)
	Object_Environment[3]=CreateSprite(SceneDataRoot)
	EntityTexture Object_Environment[3], Text_Effects[9]
	
	Mesh_Weapon[0]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Utilities\Object_weapon_mine.3ds", SceneDataRoot)
	Mesh_Weapon[1]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Utilities\Object_weapon_bullet.3ds", SceneDataRoot)
	HideEntity Mesh_Weapon[1]
	
	Text_Weapon[0]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Textures\texture_weapon_low.png",1+2)
	Text_Weapon[1]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Textures\texture_weapon_medium.png",1+2)
	Text_Weapon[2]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Textures\texture_weapon_heavy.png",1+2)
	Text_Weapon[3]=AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Textures\texture_weapon_special.png",1+2)
	
;	ScaleTexture Text_Weapon[2],1,0.01
	
	Text_Special[0] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Textures\texture_special_001.png",2)
	Text_Special[1] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\Solar_Panel.png")
	Text_Special[2] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\Solar_Grid.png",2)
	Text_Special[3] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\Solar_Beam.png",2)
	
	Text_Special[4] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\Pyramid_Body.png")
	Text_Special[5] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\3D\Environment\Pyramid_Eye.png")
	Mesh_Special[3]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\Pyramid_Base.3ds", SceneDataRoot)
	Mesh_Special[4]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\Pyramid_Ring.3ds", SceneDataRoot)
	
	Mesh_Special[0]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\Solar_Panel_XXL.3ds", SceneDataRoot)
	Mesh_Special[1]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\Solar_Panel_Gun.3ds", SceneDataRoot)
	Mesh_Special[2]=AssetManager_GetAsset(EAssetType_Model, "Content\GFX\3D\Environment\Solar_Panel_Frame.3ds", SceneDataRoot)
	EntityTexture Mesh_Special[0], Text_Special[1]
	EntityTexture Mesh_Special[2], Text_Special[2]
	
	ScaleTexture Text_Special[3],1,100
	ScaleTexture Text_Special[4],.1,.1
	ScaleTexture Text_Special[5],.1,.1
	
	EntityTexture Mesh_Special[3], Text_Special[4]
	EntityTexture Mesh_Special[4], Text_Special[5]
	
	RotateMesh Mesh_Special[1],0,180,0
	RotateMesh Mesh_Special[3],90,0,0
	RotateMesh Mesh_Special[4],90,0,0
	
	Text_Special[6] = AssetManager_GetAsset(EAssetType_Texture, "Content\GFX\Textures\texture_fog_basic.tga", 1+2)
End Function

;~IDEal Editor Parameters:
;~F#1E#132
;~C#Blitz3D