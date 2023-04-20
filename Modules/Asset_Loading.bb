Function PreloadAudio()
	;[Block] Weapons
	Sound_Guns[0]=AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Weapons\weapon_mine.wav")
	Sound_Guns[1]=AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Weapons\Laser_low.wav")
	Sound_Guns[2]=AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Weapons\Laser_medium.wav")
	Sound_Guns[3]=AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Weapons\Laser_Heavy.wav")
	
	SoundVolume Sound_Guns[1],0.4
	SoundVolume Sound_Guns[2],0.4
	SoundVolume Sound_Guns[3],0.4
	
	;[End Block]
	
	;[Block] Expansions
	Sound_Explosion[0]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Expansions\Expansion_explosion_distant.wav")
	Sound_Explosion[1]=AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Expansions\Expansion_explosion_small.wav")
	Sound_Explosion[2]=AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Expansions\Expansion_explosion.wav")
	Sound_Explosion[3]=AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Expansions\expansion_warpin_small.wav")
	Sound_Explosion[4]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Expansions\Expansion_warpin_large.wav")
	
	Sound_Expansion[0]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Expansions\Expansion_scanner.wav")
	Sound_Expansion[1]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Expansions\Expansion_miningbeam.wav")
	Sound_Expansion[2]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Interface\scanner_ping.wav")
	SoundVolume Sound_Expansion[2],0.2
	;[End Block]
	
	;[Block] Interface
	Sound_UI[0] = AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Interface\jingle_levelup.wav")
	Sound_UI[1] = AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Interface\click_high.wav")
	Sound_UI[2] = AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Interface\click_low.wav")
	Sound_UI[3] = AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Interface\confirm_high.wav")
	Sound_UI[4] = AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Interface\confirm_low.wav")
	Sound_UI[5] = AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Interface\deny_low.wav")
	Sound_UI[6] = AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Interface\upgrade_pickup.wav")
	;[End Block]
	
	;[Block] AI Voice
	;[Block] Male
	AI_Voice_Track_Male_Aggro[1]		= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Kanak_Aggro_1.wav")
	AI_Voice_Track_Male_Chatter[1]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Kanak_Chatter_1.wav")
	AI_Voice_Track_Male_Victory[1]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Kanak_Victory_1.wav")
	AI_Voice_Track_Male_Death[1]		= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Kanak_Death_1.wav")
	
	AI_Voice_Track_Male_Aggro[2]		= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Alex_Aggro_1.wav")
	AI_Voice_Track_Male_Chatter[2]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Alex_Chatter_1.wav")
	AI_Voice_Track_Male_Victory[2]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Alex_Victory_1.wav")
	AI_Voice_Track_Male_Death[2]		= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Alex_Death_1.wav")
	
	AI_Voice_Track_Male_Aggro[3]		= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_AdamWilkin_Aggro_1.wav")
	AI_Voice_Track_Male_Chatter[3]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_AdamWilkin_Chatter_1.wav")
	AI_Voice_Track_Male_Victory[3]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_AdamWilkin_Victory_1.wav")
	AI_Voice_Track_Male_Death[3]		= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_AdamWilkin_Death_1.wav")
	
	AI_Voice_Track_Male_Aggro[4]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Dex_Aggro_1.wav")
	AI_Voice_Track_Male_Chatter[4]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Dex_Chatter_1.wav")
	AI_Voice_Track_Male_Victory[4]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Dex_Victory_1.wav")
	AI_Voice_Track_Male_Death[4]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Dex_Death_1.wav")
	
	AI_Voice_Track_Male_Aggro[5]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Cal_Aggro_1.wav")
	AI_Voice_Track_Male_Chatter[5]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Cal_Chatter_1.wav")
	AI_Voice_Track_Male_Victory[5]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Cal_Victory_1.wav")
	AI_Voice_Track_Male_Death[5]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Male\VA_Cal_Death_1.wav")
	
	;[End Block]
	
	;[Block] Female
	AI_Voice_Track_Female_Aggro[1]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_mbee24_Aggro_1.wav")
	AI_Voice_Track_Female_Chatter[1]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_mbee24_Chatter_1.wav")
	AI_Voice_Track_Female_Victory[1]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_mbee24_Victory_1.wav")
	AI_Voice_Track_Female_Death[1]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_mbee24_Death_1.wav")
	
	AI_Voice_Track_Female_Aggro[2]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_Kiesel_Aggro_1.wav")
	AI_Voice_Track_Female_Chatter[2]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_Kiesel_Chatter_1.wav")
	AI_Voice_Track_Female_Victory[2]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_Kiesel_Victory_1.wav")
	AI_Voice_Track_Female_Death[2]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_Kiesel_Death_1.wav")
	
	AI_Voice_Track_Female_Aggro[3]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_Devilienchen_Aggro_1.wav")
	AI_Voice_Track_Female_Chatter[3]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_Devilienchen_Chatter_1.wav")
	AI_Voice_Track_Female_Victory[3]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_Devilienchen_Victory_1.wav")
	AI_Voice_Track_Female_Death[3]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_Devilienchen_Death_1.wav")
	
	AI_Voice_Track_Female_Aggro[4]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_Virdra_Aggro_1.wav")
	AI_Voice_Track_Female_Chatter[4]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_Virdra_Chatter_1.wav")
	AI_Voice_Track_Female_Victory[4]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_Virdra_Victory_1.wav")
	AI_Voice_Track_Female_Death[4]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_Virdra_Death_1.wav")
	
	AI_Voice_Track_Female_Aggro[5]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_JenOfFear_Aggro_1.wav")
	AI_Voice_Track_Female_Chatter[5]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_JenOfFear_Chatter_1.wav")
	AI_Voice_Track_Female_Victory[5]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_JenOfFear_Victory_1.wav")
	AI_Voice_Track_Female_Death[5]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_JenOfFear_Death_1.wav")
	
	AI_Voice_Track_Female_Aggro[6]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_PolinaFrosty_Aggro_1.wav")
	AI_Voice_Track_Female_Chatter[6]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_PolinaFrosty_Chatter_1.wav")
	AI_Voice_Track_Female_Victory[6]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_PolinaFrosty_Victory_1.wav")
	AI_Voice_Track_Female_Death[6]	= AssetManager_GetAsset(EAssetType_3DSound, "Content\SFX\Voices\NPC\Female\VA_PolinaFrosty_Death_1.wav")
	
	;[End Block]
	
	;[Block] BRIAN Tutorial
	
	;[End Block]
	
	;[End Block]
	
	;[Block] Music
	
	Music_ID[0]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Music\Intro_01.mp3")
	Music_ID[1]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Music\Ambient_01.mp3")
	Music_ID[2]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Music\Ambient_02.mp3")
	Music_ID[3]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Music\Ambient_03.mp3")
	Music_ID[4]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Music\Ambient_04.mp3")
	Music_ID[5]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Music\Ambient_05.mp3")
	Music_ID[6]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Music\Ambient_06.mp3")
	Music_ID[7]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Music\Ambient_07.mp3")
	Music_ID[8]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Music\Ambient_08.mp3")
	Music_ID[9]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Music\Combat_01.mp3")
	Music_ID[10]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Music\Combat_02.mp3")
	Music_ID[11]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Music\Combat_03.mp3")
	Music_ID[12]=AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Music\Combat_04.mp3")
	
	
	;[End Block]
	
	;[Block] Story Audio
	
	;[End Block]
	
	;[Block] Normal Audio
	
	;[End Block]
	
;	;[Block] System Audio
;	For SysInit = 1 To 28
;		Sound_SystemInit[SysInit] = AssetManager_GetAsset(EAssetType_Sound, "Content\SFX\Voices\Angel\BRIAN_VA_"+SysInit+".wav")
;	Next
;	;[End Block]
	
End Function

Function PreloadUI()
	
	GUI_Windows[0]=LoadImage3D("Content\GFX\Interface\Menus\Options_Background.png",2,2,0,-99)
	GUI_Windows[1]=LoadImage3D("Content\GFX\Interface\Menus\Options_Graphics.png",2,2,0,-99)
	
	GUI_Windows[4]=LoadImage3D("Content\GFX\Interface\Menus\Map_Background.png",2,2,0,-93)
	
	GUI_Windows[5]=LoadImage3D("Content\GFX\Interface\Menus\Gameover_Background.png",2,2,0,-93)
	
	GUI_Windows[6]=LoadImage3D("Content\GFX\Interface\Menus\Twitch_Background.png",2,2,0,-49)
	GUI_Windows[7]=LoadImage3D("Content\GFX\Interface\Menus\Choice_Background.png",2,2,0,-49)
	
	GUI_Windows[14] = LoadImage3D("Content\GFX\Interface\Icons\Battlefield_Background.png",2,2,0,-93)
	
	GUI_Windows[15] = LoadImage3D("Content\GFX\Interface\Menus\Weapon_BG_Left.png",2,2,0,-93)
	GUI_Windows[16] = LoadImage3D("Content\GFX\Interface\Menus\Weapon_BG_Center.png",2,2,0,-93)
	GUI_Windows[17] = LoadImage3D("Content\GFX\Interface\Menus\Weapon_BG_Right.png",2,2,0,-93)
	
	GUI_Buttons[0]=LoadImage3D("Content\GFX\Interface\Menus\Options_Quit_Button.png",2,2,0,-100)
	GUI_Buttons[1]=LoadImage3D("Content\GFX\Interface\Menus\Options_Logoff_Button.png",2,2,0,-100)
	GUI_Buttons[2]=LoadImage3D("Content\GFX\Interface\Menus\Options_Graphics_Button.png",2,2,0,-100)
	GUI_Buttons[3]=LoadImage3D("Content\GFX\Interface\Menus\Options_Sound_Button.png",2,2,0,-100)
	GUI_Buttons[4]=LoadImage3D("Content\GFX\Interface\Menus\Options_Gameplay_Button.png",2,2,0,-100)
	
	GUI_Buttons[5]=LoadImage3D("Content\GFX\Interface\Menus\Gameover_Button_Quit.png",2,2,0,-100)
	GUI_Buttons[6]=LoadImage3D("Content\GFX\Interface\Menus\Gameover_Button_Respawn.png",2,2,0,-100)
	
	GUI_Buttons[7] = LoadImage3D("Content\GFX\Interface\Icons\Battlefield_Friends.png",2,2,0,-95)
	GUI_Buttons[8] = LoadImage3D("Content\GFX\Interface\Icons\Battlefield_Foes.png",2,2,0,-95)
	
	GUI_Interface[0]=LoadImage3D("Content\GFX\Interface\Icons\interface_mouse_standard.png",2,2,0,-190)
	GUI_Interface[1]=LoadImage3D("Content\GFX\Interface\Icons\interface_mouse_aim.png",2,2,0,-120)
	GUI_Interface[2]=LoadImage3D("Content\GFX\Interface\Icons\interface_mouse_hit.png",2,2,0,-120)
	GUI_Interface[15]=LoadImage3D("Content\GFX\Interface\Icons\interface_mouse_hit_register.png",2,2,0,-120)
	
	GUI_Interface[3]=LoadImage3D("Content\GFX\Interface\Icons\global_icon_lframe.png",2,2,0,-120)
	GUI_Interface[4]=LoadImage3D("Content\GFX\Interface\Icons\global_icon_sframe.png",2,2,0,-120)
	
	GUI_Interface[5]=LoadImage3D("Content\GFX\Interface\Icons\global_icon_combat_enemy.png",2,2,0,-120)
	GUI_Interface[6]=LoadImage3D("Content\GFX\Interface\Icons\global_icon_combat_friend.png",2,2,0,-120)
	GUI_Interface[7]=LoadImage3D("Content\GFX\Interface\Icons\global_icon_combat_neut.png",2,2,0,-120)
	GUI_Interface[8]=LoadImage3D("Content\GFX\Interface\Icons\global_icon_combat_police.png",2,2,0,-120)
	
	GUI_Interface[9]=LoadImage3D("Content\GFX\Interface\Icons\global_icon_transport.png",2,2,0,-120)
	GUI_Interface[10]=LoadImage3D("Content\GFX\Interface\Icons\global_icon_tsmall.png",2,2,0,-120)
	GUI_Interface[11]=LoadImage3D("Content\GFX\Interface\Icons\global_icon_cargo.png",2,2,0,-120)
	GUI_Interface[13]=LoadImage3D("Content\GFX\Interface\Icons\global_icon_derelict.png",2,2,0,-120)
	
	GUI_Interface[12]=LoadImage3D("Content\GFX\Interface\Story\chapter_0_title.png",2,2,0,-120)
	
	GUI_Interface[17]=LoadImage3D("Content\GFX\Environment\Effects\expansion_aimguidance.png",2,2,0,-118)
	GUI_Interface[18]=LoadImage3D("Content\GFX\Interface\Icons\map_icon_signature.png",2,2,0,-118)
	GUI_Interface[19]=LoadImage3D("Content\GFX\Interface\Icons\map_icon_base.png",2,2,0,-119)
	GUI_Interface[20]=LoadImage3D("Content\GFX\Interface\Icons\map_icon_dotheight.png",2,2,0,-118)
	
	GUI_Buffs[1]=LoadImage3D("Content\GFX\Interface\Icons\buff_frame_0.png",2,2,0,-16)
	GUI_Buffs[2]=LoadImage3D("Content\GFX\Interface\Icons\buff_frame_1.png",2,2,0,-16)
	GUI_Buffs[3]=LoadImage3D("Content\GFX\Interface\Icons\buff_frame_2.png",2,2,0,-16)
	GUI_Buffs[4]=LoadImage3D("Content\GFX\Interface\Icons\buff_frame_3.png",2,2,0,-16)
	GUI_Buffs[5]=LoadImage3D("Content\GFX\Interface\Icons\buff_frame_4.png",2,2,0,-16)
	
	Local AnyItem
	For AnyItem=1 To 10
		GUI_Items[AnyItem]=LoadImage3D("Content\GFX\Interface\Icons\Item_Icon_000.png",2,2,0,-105)
	Next
	
	GUI_Items[1]=LoadImage3D("Content\GFX\Interface\Icons\Item_Icon_001.png",2,2,0,-105)
	GUI_Items[2]=LoadImage3D("Content\GFX\Interface\Icons\Item_Icon_002.png",2,2,0,-105)
	GUI_Items[3]=LoadImage3D("Content\GFX\Interface\Icons\Item_Icon_003.png",2,2,0,-105)
	GUI_Items[4]=LoadImage3D("Content\GFX\Interface\Icons\Item_Icon_004.png",2,2,0,-105)
	GUI_Items[5]=LoadImage3D("Content\GFX\Interface\Icons\Item_Icon_005.png",2,2,0,-105)
	GUI_Items[6]=LoadImage3D("Content\GFX\Interface\Icons\Item_Icon_006.png",2,2,0,-105)
	GUI_Items[7]=LoadImage3D("Content\GFX\Interface\Icons\Item_Icon_007.png",2,2,0,-105)
	GUI_Items[10]=LoadImage3D("Content\GFX\Interface\Icons\Item_Icon_010.png",2,2,0,-105)
	
	GUI_Status[0]=LoadImage3D("Content\GFX\Interface\Status\Status_Background.png",2,2,0,-96)
	GUI_Status[1]=LoadImage3D("Content\GFX\Interface\Status\Status_hull_frame.png",2,2,0,-96)
	GUI_Status[2]=LoadImage3D("Content\GFX\Interface\Status\Status_energy_frame.png",2,2,0,-96)
	GUI_Status[3]=LoadImage3D("Content\GFX\Interface\Status\Status_shield_frame.png",2,2,0,-96)
	
	GUI_MapPing[1] = LoadImage3D("Content\GFX\Environment\Effects\expansion_Scanner_Ping_S.png",2,2,0,-96)
	GUI_MapPing[2] = LoadImage3D("Content\GFX\Environment\Effects\expansion_Scanner_Ping_M.png",2,2,0,-96)
	GUI_MapPing[3] = LoadImage3D("Content\GFX\Environment\Effects\expansion_Scanner_Ping_L.png",2,2,0,-96)
	GUI_MapPing[4] = LoadImage3D("Content\GFX\Environment\Effects\expansion_Scanner_Ping_XL.png",2,2,0,-96)
	GUI_MapPing[5] = LoadImage3D("Content\GFX\Environment\Effects\expansion_Scanner_Ping_XXL.png",2,2,0,-96)
	
	For A = 1 To 18
		GUI_Rank[A] = LoadImage3D("Content\GFX\Interface\Menus\Rank"+A+".png",2,2,0,-93)
	Next
	GUI_Rank[0] = LoadImage3D("Content\GFX\Interface\Menus\Rank1.png",2,2,0,-93)
	
	String_Rank[0] = "No Rank"
	String_Rank[1] = "Private"
	String_Rank[2] = "Private 1 Star"
	String_Rank[3] = "Private 2 Stars"
	String_Rank[4] = "Private 3 Stars"
	String_Rank[5] = "Corporal"
	String_Rank[6] = "Corporal 1 Star"
	String_Rank[7] = "Corporal 2 Stars"
	String_Rank[8] = "Corporal 3 Stars"
	String_Rank[9] = "Lieutenant"
	String_Rank[10] = "Sergeant"
	String_Rank[11] = "Sgt. 1 Star"
	String_Rank[12] = "Sgt. 2 Stars"
	String_Rank[13] = "Sgt. 3 Stars"
	String_Rank[14] = "Major"
	String_Rank[15] = "Major 1 Star"
	String_Rank[16] = "Major 2 Stars"
	String_Rank[17] = "Major 3 Stars"
	String_Rank[18] = "Captain"
	
	GUI_MapIcon[0] = LoadImage3D("Content\GFX\Interface\Icons\map_icon_player.png",2,2,0,-94)
	GUI_MapIcon[1] = LoadImage3D("Content\GFX\Interface\Icons\map_icon_belt.png",2,2,0,-94)
	GUI_MapIcon[2] = LoadImage3D("Content\GFX\Interface\Icons\map_icon_station.png",2,2,0,-94)
	GUI_MapIcon[3] = LoadImage3D("Content\GFX\Interface\Icons\map_icon_wreck.png",2,2,0,-94)
	
	GUI_MapIcon[4] = LoadImage3D("Content\GFX\Interface\Icons\map_icon_foe.png",2,2,0,-94)
	GUI_MapIcon[5] = LoadImage3D("Content\GFX\Interface\Icons\map_icon_friend.png",2,2,0,-94)
	GUI_MapIcon[6] = LoadImage3D("Content\GFX\Interface\Icons\map_icon_neutral.png",2,2,0,-94)
	GUI_MapIcon[7] = LoadImage3D("Content\GFX\Interface\Icons\map_icon_gate.png",2,2,0,-94)
	GUI_MapIcon[8] = LoadImage3D("Content\GFX\Interface\Icons\map_icon_belt.png",2,2,0,-94)
	GUI_MapIcon[9] = LoadImage3D("Content\GFX\Interface\Icons\map_icon_belt.png",2,2,0,-94)
	
	GUI_MapIcon[10] = LoadImage3D("Content\GFX\Interface\Icons\map_icon_above.png",2,2,0,-94)
	GUI_MapIcon[11] = LoadImage3D("Content\GFX\Interface\Icons\map_icon_below.png",2,2,0,-94)
	
	GUI_MainMenu_Data[0] = LoadImage3D("Content\GFX\Interface\MainMenu\DATA_tier_0.png",2,2,0,-96)
	GUI_MainMenu_Data[1] = LoadImage3D("Content\GFX\Interface\MainMenu\DATA_tier_1.png",2,2,0,-96)
	GUI_MainMenu_Data[2] = LoadImage3D("Content\GFX\Interface\MainMenu\DATA_tier_2.png",2,2,0,-96)
	GUI_MainMenu_Data[3] = LoadImage3D("Content\GFX\Interface\MainMenu\DATA_tier_3.png",2,2,0,-96)
	GUI_MainMenu_Data[4] = LoadImage3D("Content\GFX\Interface\MainMenu\DATA_tier_4.png",2,2,0,-96)
	
	GUI_MainMenu_Button[0] = LoadImage3D("Content\GFX\Interface\MainMenu\Button.png",2,2,0,95)
	GUI_MainMenu_Button[1] = LoadImage3D("Content\GFX\Interface\MainMenu\Button_New.png",2,2,0,95)
	GUI_MainMenu_Button[2] = LoadImage3D("Content\GFX\Interface\MainMenu\Button_Load.png",2,2,0,95)
	GUI_MainMenu_Button[3] = LoadImage3D("Content\GFX\Interface\MainMenu\Button_Discord.png",2,2,0,95)
	GUI_MainMenu_Button[4] = LoadImage3D("Content\GFX\Interface\MainMenu\Button_Credits.png",2,2,0,95)
	GUI_MainMenu_Button[5] = LoadImage3D("Content\GFX\Interface\MainMenu\Button_Twitch.png",2,2,0,95)
	GUI_MainMenu_Button[6] = LoadImage3D("Content\GFX\Interface\MainMenu\Button_Twitch_on.png",2,2,0,95)
	GUI_MainMenu_Button[7] = LoadImage3D("Content\GFX\Interface\MainMenu\Button_Twitch_off.png",2,2,0,95)
	GUI_MainMenu_Button[8] = LoadImage3D("Content\GFX\Interface\MainMenu\Button_Confirm.png",2,2,0,95)
	GUI_MainMenu_Button[9] = LoadImage3D("Content\GFX\Interface\MainMenu\Button_Abort.png",2,2,0,95)
	GUI_MainMenu_Button[10]= LoadImage3D("Content\GFX\Interface\MainMenu\Button_Quit.png",2,2,0,95)
	GUI_MainMenu_Button[11]= LoadImage3D("Content\GFX\Interface\MainMenu\Button_Back.png",2,2,0,95)
	GUI_MainMenu_Button[12]= LoadImage3D("Content\GFX\Interface\MainMenu\Button_SETTINGS.png",2,2,0,95)
	GUI_MainMenu_Button[13]= LoadImage3D("Content\GFX\Interface\MainMenu\Button_LoadDis.png",2,2,0,95)
	
	GUI_MainMenu_LImages[0] = LoadImage3D("Content\GFX\Interface\MainMenu\background.jpg",2,2,0,98)
	GUI_MainMenu_LImages[1] = LoadImage3D("Content\GFX\Interface\MainMenu\Character_Creation_Background.png",2,2,0,98)
	GUI_MainMenu_LImages[2] = LoadImage3D("Content\GFX\Interface\MainMenu\Character_creation_left.png",2,2,0,96)
	GUI_MainMenu_LImages[3] = LoadImage3D("Content\GFX\Interface\MainMenu\Character_creation_right.png",2,2,0,96)
	GUI_MainMenu_LImages[4] = LoadImage3D("Content\GFX\Interface\MainMenu\Character_creation_left_accept.png",2,2,0,94)
	GUI_MainMenu_LImages[5] = LoadImage3D("Content\GFX\Interface\MainMenu\Character_creation_right_accept.png",2,2,0,94)
	
	GUI_MainMenu_SImages[0] = LoadImage3D("Content\GFX\Interface\MainMenu\character_information_capsule.png",2,2,0,96)
	GUI_MainMenu_SImages[1] = LoadImage3D("Content\GFX\Interface\MainMenu\character_frame_cutout.png",2,2,0,96)
	GUI_MainMenu_SImages[2] = LoadImage3D("Content\GFX\Interface\MainMenu\character_creation_select.png",2,2,0,96)
	GUI_MainMenu_SImages[3] = LoadImage3D("Content\GFX\Interface\MainMenu\logo_game_studio.png",2,2,0,96)
	GUI_MainMenu_SImages[4] = LoadImage3D("Content\GFX\Interface\MainMenu\logo_faction_sirius.png",2,2,0,-98)
	GUI_MainMenu_SImages[5] = LoadImage3D("Content\GFX\Interface\MainMenu\logo_faction_terra.png",2,2,0,-98)
	GUI_MainMenu_SImages[6] = LoadImage3D("Content\GFX\Interface\MainMenu\logo_fmod.png",2,2,0,96)
	GUI_MainMenu_SImages[7] = LoadImage3D("Content\GFX\Interface\MainMenu\logo_game.png",2,2,0,96)
	
	GUI_WorldMap[0] = LoadImage3D("Content\GFX\Interface\icons\map_sector_icon_hq.png",2,2,0,93)
	GUI_WorldMap[1] = LoadImage3D("Content\GFX\Interface\icons\map_sector_hostile.png",2,2,0,94)
	GUI_WorldMap[2] = LoadImage3D("Content\GFX\Interface\icons\map_sector_neutral.png",2,2,0,94)
	GUI_WorldMap[3] = LoadImage3D("Content\GFX\Interface\icons\map_sector_terra.png",2,2,0,94)
	GUI_WorldMap[4] = LoadImage3D("Content\GFX\Interface\icons\map_sector_sirius.png",2,2,0,94)
	GUI_WorldMap[5] = LoadImage3D("Content\GFX\Interface\icons\map_sector_battlefield.png",2,2,0,94)
	GUI_WorldMap[6] = LoadImage3D("Content\GFX\Interface\icons\map_sector_gate.png",2,2,0,93)
	GUI_WorldMap[7] = LoadImage3D("Content\GFX\Interface\icons\map_sector_unknown.png",2,2,0,94)
	GUI_WorldMap[8] = LoadImage3D("Content\GFX\Interface\icons\map_sector_orion.png",2,2,0,96)
	GUI_WorldMap[9] = LoadImage3D("Content\GFX\Interface\icons\map_sector_shade.png",2,2,0,94)
	
	
End Function

Function Preload3DFont()
	
	Text_Font[1]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Chatfont_Normal.png",2,1,0,-120))
	Text_Font[2]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Chatfont_whisper.png",2,1,0,-50))
	Text_Font[3]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Chatfont_Dev.png",2,1,0,-50))
	Text_Font[4]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Chatfont_SRV.png",2,1,0,-50))
	Text_Font[5]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Locationfont.png",2,1,0,-120))
	Text_Font[6]=FontRange3D(LoadImage3D("Content\GFX\Fonts\NormFont_TopLayer.png",2,1,0,-120))
	Text_Font[7]=FontRange3D(LoadImage3D("Content\GFX\Fonts\NormFont_GameLayer.png",2,1,0,-50))
	Text_Font[8]=FontRange3D(LoadImage3D("Content\GFX\Fonts\BigFont_Toplayer.png",2,1,0,-120))
	Text_Font[9]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Bigfont_Gamelayer.png",2,1,0,-50))
	Text_Font[10]=FontRange3D(LoadImage3D("Content\GFX\Fonts\InfoFont_GameLayer.png",2,1,0,-50))
	Text_Font[11]=FontRange3D(LoadImage3D("Content\GFX\Fonts\InfoFont_GameLayer.png",2,1,0,-120))
	Text_Font[12]=FontRange3D(LoadImage3D("Content\GFX\Fonts\InfoFont_RedTop.png",2,1,0,-120))
	Text_Font[13]=FontRange3D(LoadImage3D("Content\GFX\Fonts\InfoFont_GreenTop.png",2,1,0,-120))
	Text_Font[14]=FontRange3D(LoadImage3D("Content\GFX\Fonts\QuestFont_GameLayer.png",2,1,0,-150))
	Text_Font[15]=FontRange3D(LoadImage3D("Content\GFX\Fonts\QuestFont_GameLayer_White.png",2,1,0,-150))
	Text_Font[16]=FontRange3D(LoadImage3D("Content\GFX\Fonts\QuestFont_GameLayer_Big.png",2,1,0,-150))
	Text_Font[17]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Locationfont_dist.png",2,1,0,-50))
	Text_Font[18]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Locationfont_SHD.png",2,1,0,-50))
	Text_Font[19]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Locationfont_GRN.png",2,1,0,-50))
	Text_Font[20]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Marketfont_Toplayer.png",2,1,0,-120))
	Text_Font[21]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Marketfont_Deny_Toplayer.png",2,1,0,-120))
	Text_Font[22]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Marketfont_Allow_Toplayer.png",2,1,0,-120))
	Text_Font[23]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Locationfont_dist.png",2,1,0,-120))
	Text_Font[24]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Locationfont_SHD.png",2,1,0,-120))
	Text_Font[25]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Locationfont_GRN.png",2,1,0,-120))
	Text_Font[26]=FontRange3D(LoadImage3D("Content\GFX\Fonts\SmallFont.png",2,1,0,-120))
	
	Text_Font[27]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Locationfont_ENEM.png",2,1,0,-50))
	Text_Font[28]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Locationfont_FRIE.png",2,1,0,-50))
	Text_Font[29]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Locationfont_POLI.png",2,1,0,-50))
	Text_Font[30]=FontRange3D(LoadImage3D("Content\GFX\Fonts\Locationfont_PLAY.png",2,1,0,-50))
	
	Text_Font[31]=FontRange3D(LoadImage3D("Content\GFX\Fonts\StreamFontGrey.png",2,1,0,-50))
	Text_Font[32]=FontRange3D(LoadImage3D("Content\GFX\Fonts\StreamFontRed.png",2,1,0,-50))
	Text_Font[33]=FontRange3D(LoadImage3D("Content\GFX\Fonts\StreamFontGreen.png",2,1,0,-50))
	Text_Font[34]=FontRange3D(LoadImage3D("Content\GFX\Fonts\StreamNamePurple.png",2,1,0,-50))
	Text_Font[35]=FontRange3D(LoadImage3D("Content\GFX\Fonts\StreamFont_large.png",2,1,0,-50))
	
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
;~F#0#1#D#1A#24#68#8B#13C#166#27A
;~C#Blitz3D