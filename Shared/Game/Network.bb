;----------------------------------------------------------------
;-- Constants
;----------------------------------------------------------------
Const NET_SERVER_PORT%			= 2222
Const NET_SERVER_MAX_PLAYERS%	= 256

;-- Primary Identifiers (Byte 0)
Const NET_ID_AUTHENTICATE%		= 128
Const NET_ID_CHAT%				= 129
Const NET_ID_WORLD%				= 130							; Information about the world around you (Stationary Objects)
Const NET_ID_OBJECT%			= 131							; Information about the objects around you (Dynamic Objects)
Const NET_ID_ACTORS%			= 132							; Information about the actors around you (Players, AI, ...)

;-- Secondary Identifiers (Byte 1)
; NET_ID_CHAT
Const NET_ID_CHAT_LOCAL%		= 0
Const NET_ID_CHAT_SECTOR%		= 1
;Const NET_ID_CHAT_

; NET_ID_WORLD
Const NET_ID_WORLD_CREATE%		= 0
Const NET_ID_WORLD_DESTROY%		= 1
Const NET_ID_WORLD_UPDATE%		= 2

; NET_ID_OBJECT

; NET_ID_PLAYER
Const NET_ID_PLAYER_CREATE%		= 0
Const NET_ID_PLAYER_DESTROY%	= 1
Const NET_ID_PLAYER_UPDATE%		= 2
Const NET_ID_PLAYER_SHOOT%		= 3
Const NET_ID_PLAYER_SHOT_HIT%	= 4

; NET_ID_SECTOR
Const NET_ID_SECTOR_CREATE%		= 0
Const NET_ID_SECTOR_DESTROY%	= 1

; NET_ID_ZONE

; NET_ID_OBJECT
Const NET_ID_OBJECT_CREATE%		= 0
Const NET_ID_OBJECT_UPDATE%		= 1
Const NET_ID_OBJECT_DESTROY%	= 2
Const NET_OBJECT_TYPE			= 2
Const NET_ID_ZONE_CREATE%		= 0
Const NET_ID_ZONE_DESTROY%		= 1
Const NET_ID_STATION_CREATE%	= 10
Const NET_ID_STATION_DESTROY%	= 11
Const NET_ID_TRADELANE_CREATE%	= 20
Const NET_ID_TRADELANE_DESTROY%	= 21
Const NET_ID_BELT_CREATE%		= 30
Const NET_ID_BELT_DESTROY%		= 31
Const NET_ID_ASTEROID_CREATE%	= 2

;-- How each Packet is made up:



;----------------------------------------------------------------
;-- Global
;----------------------------------------------------------------
Global Net_PeerInterface		= 0
Global Net_BitStream			= RN_BitStreamCreate1(0)
Global Net_Error$				= ""
Global Net_InternalId%			= 0

;----------------------------------------------------------------
;-- Functions
;----------------------------------------------------------------
;-- Client
Function Client_Initialize()
	If Net_PeerInterface = 0
		Net_PeerInterface = RN_GetRakPeerInterface()
	EndIf
End Function

Function Client_Connect(Host$, Port%, Password$ = "")
	If Net_PeerInterface
		RN_Connect(Net_PeerInterface, Host, Port, Password, Len(Password) + 1)
	EndIf
End Function

Function Client_Status()
	If Net_PeerInterface Then
		If RN_IsActive(Net_PeerInterface)
			Return True
		Else
			Return False
		EndIf
	EndIf
End Function

Function Client_Disconnect()
	If RN_IsActive(Net_PeerInterface)
		RN_CloseConnection(Net_PeerInterface, UNASSIGNED_SYSTEM_ADDRESS, True)
	EndIf
End Function

Function Client_Deinitalize()
	If Net_PeerInterface
		If RN_IsActive(Net_PeerInterface) Then Client_Disconnect()
		
		RN_Shutdown(Net_PeerInterface, 0)
		Net_PeerInterface = RN_DestroyRakPeerInterface(Net_PeerInterface)
	EndIf
End Function

Function Client_Update()
	
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D