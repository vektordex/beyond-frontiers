;----------------------------------------------------------------
;! Constants
;----------------------------------------------------------------
Const EAssetType_Texture%										= 0
Const EAssetType_Image%											= 1 ; Shares Mutex with Texture
Const EAssetType_Font%											= 2 ; Shares Mutex with Texture
Const EAssetType_Model%											= 20
Const EAssetType_AnimModel%										= 21 ; Shares Mutex with Model
Const EAssetType_Sound%											= 30
Const EAssetType_Music%											= 31 ; Shares Mutex with Sound
Const EAssetType_3DSound%										= 32 ; Shares Mutex with Sound

Const EAssetStatus_Unknown										= 0
Const EAssetStatus_Loading										= 1
Const EAssetStatus_Loaded										= 2
Const EAssetStatus_Error										= 3

;----------------------------------------------------------------
;! Types
;----------------------------------------------------------------
Type Asset
	Field EAssetType
	Field Path$, Param1%, Param2%, Param3%, Param4%
	
	Field EAssetStatus
	Field Resource%
	
	Field t_TargetValuePtr%
	
	Field z_Internal_Counter%
	Field z_Internal_Lock%, z_Internal_GlobalLock%
End Type
Global z_Asset_Mutex = Mutex_Create()

;----------------------------------------------------------------
;! Globals
;----------------------------------------------------------------
Global AssetManager_ThreadPool
Global AssetManager_Mutex_Texture%
Global AssetManager_Mutex_Model%
Global AssetManager_Mutex_Sound%
Global z_Internal_AssetManager_Task_p
Global z_AssetManager_IsSuspended

;----------------------------------------------------------------
;! Functions
;----------------------------------------------------------------
Function AssetManager_Initialize()
	If (AssetManager_ThreadPool = 0) Then
		AssetManager_ThreadPool = ThreadPool_Create(3, True)
		AssetManager_Mutex_Texture = Mutex_Create(True)
		AssetManager_Mutex_Model = Mutex_Create(True)
		AssetManager_Mutex_Sound = Mutex_Create(True)
		z_AssetManager_IsSuspended = True
		
		LogMessage(LOG_DEBUG, "AssetManager: Initialized.")
	EndIf
End Function

Function AssetManager_Shutdown()
	If (AssetManager_ThreadPool <> 0) Then
		ThreadPool_Destroy(AssetManager_ThreadPool)
		AssetManager_ThreadPool = 0
		
		Mutex_Destroy(AssetManager_Mutex_Texture)
		Mutex_Destroy(AssetManager_Mutex_Model)
		Mutex_Destroy(AssetManager_Mutex_Sound)
		
		LogMessage(LOG_DEBUG, "AssetManager: Deinitialized.")
	EndIf
End Function

Function AssetManager_Suspend(bForce%=True)
	If AssetManager_ThreadPool = 0 Then
		Return True
	EndIf
	If z_AssetManager_IsSuspended = True Then
		Return True
	EndIf
	
	If bForce Then
		Mutex_Lock(AssetManager_Mutex_Texture)
		Mutex_Lock(AssetManager_Mutex_Model)
		Mutex_Lock(AssetManager_Mutex_Sound)
		ThreadPool_Suspend(AssetManager_ThreadPool)
		z_AssetManager_IsSuspended = True
		Return True
	Else	
		If Mutex_Lock(AssetManager_Mutex_Texture) = MUTEX_WAIT_OK Then
			If Mutex_Lock(AssetManager_Mutex_Model) = MUTEX_WAIT_OK Then
				If Mutex_Lock(AssetManager_Mutex_Sound) = MUTEX_WAIT_OK Then
					ThreadPool_Suspend(AssetManager_ThreadPool)
					z_AssetManager_IsSuspended = True
					Return True
				EndIf
				Mutex_Release(AssetManager_Mutex_Model)
			EndIf
			Mutex_Release(AssetManager_Mutex_Texture)
		EndIf
	EndIf
End Function

Function AssetManager_Resume()
	If AssetManager_ThreadPool = 0 Then
		Return True
	EndIf
	If z_AssetManager_IsSuspended = False Then
		Return True
	EndIf
	
	ThreadPool_Resume(AssetManager_ThreadPool)
	Mutex_Release(AssetManager_Mutex_Sound)
	Mutex_Release(AssetManager_Mutex_Model)
	Mutex_Release(AssetManager_Mutex_Texture)
	z_AssetManager_IsSuspended = False
	
	ThreadPool_Update(AssetManager_ThreadPool)
End Function

Function AssetManager_Load.Asset(EAssetType%, Path$, Param1%=0, Param2%=0, Param3%=0, Param4%=0, t_TargetValuePtr%=0)
	Local t_Ass.Asset = AssetManager_Find(EAssetType, Path$, Param1, Param2, Param3, Param4)
	
	; Not found, so create a new one.
	Mutex_Wait(z_Asset_Mutex)
	If (t_Ass = Null) Then
		t_Ass = New Asset
		t_Ass\EAssetStatus = EAssetStatus_Unknown
		t_Ass\EAssetType = EAssetType
		t_Ass\Path = Path
		t_Ass\Param1 = Param1
		t_Ass\Param2 = Param2
		t_Ass\Param3 = Param3
		t_Ass\Param4 = Param4
		
		Select EAssetType
			Case EAssetType_Image, EAssetType_Texture, EAssetType_Font
				t_Ass\z_Internal_Lock = AssetManager_Mutex_Texture
			Case EAssetType_Model, EAssetType_AnimModel
				t_Ass\z_Internal_Lock = AssetManager_Mutex_Model
			Case EAssetType_Sound, EAssetType_Music, EAssetType_3DSound
				t_Ass\z_Internal_Lock = AssetManager_Mutex_Sound
		End Select
		t_Ass\z_Internal_GlobalLock = z_Asset_Mutex
	EndIf
	t_Ass\z_Internal_Counter = t_Ass\z_Internal_Counter + 1
	t_Ass\t_TargetValuePtr = t_TargetValuePtr
	
	ThreadPool_QueueTask(AssetManager_ThreadPool, z_Internal_AssetManager_Task_p, Int(t_Ass))
	Mutex_Release(z_Asset_Mutex)
	
	LogMessage(LOG_DEBUG, "AssetManager: Queued Asset '" + t_Ass\Path + "' ("+t_Ass\Param1+"/"+t_Ass\Param2+"/"+t_Ass\Param3+"/"+t_Ass\Param4+").")
	
	Return t_Ass
End Function
Function AssetManager_Unload(t_Ass.Asset)
	Mutex_Wait(z_Asset_Mutex)
	t_Ass\z_Internal_Counter = t_Ass\z_Internal_Counter - 1
	If (t_Ass\z_Internal_Counter <= 0) Then
		Mutex_Wait(t_Ass\z_Internal_Lock) ; We might be loading it right now.
		If t_Ass\EAssetStatus = EAssetStatus_Loaded
			Select t_Ass\EAssetType
				Case EAssetType_Texture
					FreeTexture(t_Ass\Resource)
				Case EAssetType_Image
					FreeImage(t_Ass\Resource)
				Case EAssetType_Font
					FreeFont(t_Ass\Resource)
				Case EAssetType_Model, EAssetType_AnimModel
					FreeEntity(t_Ass\Resource)
				Case EAssetType_Sound, EAssetType_3DSound
					FreeSound(t_Ass\Resource)
				Case EAssetType_Music
					StopChannel(t_Ass\Resource)
			End Select
		EndIf
		Mutex_Release(t_Ass\z_Internal_Lock)
		
		Delete t_Ass
	EndIf
	Mutex_Release(z_Asset_Mutex)
End Function

Function AssetManager_Find.Asset(EAssetType%, Path$, Param1%=0, Param2%=0, Param3%=0, Param4%=0)
	Local t_Ass.Asset, rt_Ass.Asset
	
	Mutex_Wait(z_Asset_Mutex)
	For t_Ass = Each Asset
		If t_Ass\EAssetType = EAssetType And Lower(t_Ass\Path) = Lower(Path) And t_Ass\Param1 = Param1 And t_Ass\Param2 = Param2 And t_Ass\Param3 = Param3 And t_Ass\Param4 = Param4
			rt_Ass = t_Ass
		EndIf
	Next
	Mutex_Release(z_Asset_Mutex)
	
	Return rt_Ass
End Function
Function AssetManager_FindByResource.Asset(Resource%)
	Local t_Ass.Asset, rt_Ass.Asset
	
	Mutex_Wait(z_Asset_Mutex)
	For t_Ass = Each Asset
		If t_Ass\Resource = Resource
			rt_Ass = t_Ass
		EndIf
	Next
	Mutex_Release(z_Asset_Mutex)
	
	Return rt_Ass
End Function

Function AssetManager_Count%(EAssetStatus%=$FFFFFFFF)
	Local Cnt%, t_Ass.Asset
	Mutex_Wait(z_Asset_Mutex)
	If EAssetStatus <> $FFFFFFFF Then
		For t_Ass = Each Asset
			If t_Ass\EAssetStatus = EAssetStatus
				Cnt = Cnt + 1
			EndIf
		Next
	Else
		For t_Ass = Each Asset
			Cnt = Cnt + 1
		Next
	EndIf
	Mutex_Release(z_Asset_Mutex)
	Return Cnt
End Function

Function LoadImageAsset.Asset(Path$)
	Return AssetManager_Load(EAssetType_Image, Path$)
End Function
Function LoadTextureAsset.Asset(Path$, Flags%=0)
	Return AssetManager_Load(EAssetType_Texture, Path$, Flags)
End Function
Function LoadFontAsset.Asset(Path$, Height%, Bold%=0, Italic%=0, Underlined%=0)
	Return AssetManager_Load(EAssetType_Font, Path$, Height, Bold, Italic, Underlined)
End Function
Function LoadMeshAsset.Asset(Path$, Parent%=0)
	Return AssetManager_Load(EAssetType_Model, Path$, Parent)
End Function
Function LoadAnimMeshAsset.Asset(Path$, Parent%=0)
	Return AssetManager_Load(EAssetType_AnimModel, Path$, Parent)
End Function
Function LoadSoundAsset.Asset(Path$)
	Return AssetManager_Load(EAssetType_Sound, Path$)
End Function
Function Load3DSoundAsset.Asset(Path$)
	Return AssetManager_Load(EAssetType_3DSound, Path$)
End Function
Function LoadMusicAsset.Asset(Path$)
	Return AssetManager_Load(EAssetType_Music, Path$)
End Function

Function Asset_Resource(t_Ass.Asset)
	If t_Ass = Null Then
		;RuntimeError "Asset is Null"
		Return 0
	EndIf
	Return t_Ass\Resource
End Function
Function AssetManager_GetAsset(EAssetType%, Path$, Param1%=0, Param2%=0, Param3%=0, Param4%=0)
	Local t_Ass.Asset = AssetManager_Find(EAssetType, Path, Param1, Param2, Param3, Param4)
	If t_Ass = Null Then
		Local EAssetTypeStr$
		Select EAssetType
			Case EAssetType_Texture
				EAssetTypeStr = "EAssetType_Texture"
			Case EAssetType_Image
				EAssetTypeStr = "EAssetType_Image"
			Case EAssetType_Font
				EAssetTypeStr = "EAssetType_Font"
			Case EAssetType_Model
				EAssetTypeStr = "EAssetType_Model"
			Case EAssetType_AnimModel
				EAssetTypeStr = "EAssetType_AnimModel"
			Case EAssetType_Sound
				EAssetTypeStr = "EAssetType_Sound"
			Case EAssetType_3DSound
				EAssetTypeStr = "EAssetType_3DSound"
			Case EAssetType_Music
				EAssetTypeStr = "EAssetType_Music"
		End Select
				
		
		RuntimeError "AssetManager: Failed to find Asset."+Chr(10)+Chr(13)+EAssetType+Chr(10)+Chr(13)+Path+Chr(10)+Chr(13)+Param1+"/"+Param2+"/"+Param3+"/"+Param4
	EndIf
	Return Asset_Resource(t_Ass)
End Function

Function z_Internal_AssetManager_Task(Ass.Asset)
	If z_Internal_AssetManager_Task_p = 0 And Int(Ass) = 0 Then
		z_Internal_AssetManager_Task_p = BP_GetFunctionPointer()
		Return
	EndIf
	
	If Mutex_Wait(Ass\z_Internal_Lock, 1) = MUTEX_WAIT_OK Then
		Ass\EAssetStatus = EAssetStatus_Loading
		Select Ass\EAssetType
			Case EAssetType_Texture
				Ass\Resource = LoadTexture(Ass\Path, Ass\Param1)
			Case EAssetType_Image
				Ass\Resource = LoadImage(Ass\Path)
			Case EAssetType_Font
				Ass\Resource = LoadFont(Ass\Path, Ass\Param1, Ass\Param2, Ass\Param3, Ass\Param4)
			Case EAssetType_Model
				Ass\Resource = LoadMesh(Ass\Path, Ass\Param1)
			Case EAssetType_AnimModel
				Ass\Resource = LoadAnimMesh(Ass\Path, Ass\Param1)
			Case EAssetType_Sound
				Ass\Resource = LoadSound(Ass\Path)
			Case EAssetType_3DSound
				Ass\Resource = Load3DSound(Ass\Path)
			Case EAssetType_Music
				Ass\Resource = PlayMusic(Ass\Path)
		End Select
		Mutex_Release(Ass\z_Internal_Lock)
		
		If Ass\Resource = 0 Then
			LogMessage(LOG_ERROR, "AssetManager: Failed to load '" + Ass\Path + "'!")
			Ass\EAssetStatus = EAssetStatus_Error
		Else
			LogMessage(LOG_DEBUG, "AssetManager: Loaded Asset '" + Ass\Path + "'.")
			Ass\EAssetStatus = EAssetStatus_Loaded
		EndIf
	Else
		Return True
	EndIf
End Function:z_Internal_AssetManager_Task(Null)

;~IDEal Editor Parameters:
;~F#14#2F#3B#48#66#77#9A#B6#C3#D1#E3#E6#E9#EC#EF#F2#F5#F8#FC#103
;~F#120
;~C#Blitz3D