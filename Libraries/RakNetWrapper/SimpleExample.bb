;***************************************************
; Raknet sample, press space bar from server window
; to send mouse position.
; Press 1 to send hello message.
; Based on kurix sample and bmax raknet sample
; thanks to Kurix and RepeatUntil!!
; Paco.
;***************************************************

Global peer = 0
Global serverPort = 61019
Global packet = 0
Global isServer
Global stat = 0
Global BitStreamIN = RN_BitStreamCreate1(0)
Global B = RN_BitStreamCreate1(0)

Global systemAddressServer = 0
Global systemAddressClient1 = 0
Global systemAddressClient2 = 0

Const ID_KEY_F1 = 100
Const ID_CHAT = 101

Global nbPlayerMax = 32


Include "raknet.bb"


Graphics 800,600,0,2

Local Stringa$ = Input ("(S)erver or (C)lient?")

peer = RN_GetRakPeerInterface()



If Upper(Stringa$) = "S" Then
	isServer = True
	ok = RN_Startup(peer, nbPlayerMax, 0, serverPort)
	RN_SetMaximumIncomingConnections(peer,nbPlayerMax)
	
	If ok Then 
		Print "Server correctly started"
	Else
		Print "Problem when starting the server!"
	EndIf	
Else
	isServer = False
	Stringa$ = "" ;InputText("Enter server IP Or hit enter For 127.0.0.1:  ", 0, 0);
	If Trim(Stringa) = "" Then Stringa = "127.0.0.1"
	RN_Startup(peer,1,0,0)   ;1 player allowed To connect -> client
	ok = RN_Connect(peer,Stringa, serverPort, "", 0);
	If ok Then 
		Print "client correctly connected"
	Else
		Print "Problem starting the client!!"
	EndIf
EndIf
Print("Press Enter or Space to communicate!")
;******************************************************************************************************
; if Server
While Not KeyHit(1)

If isserver
	
	;sending
		If KeyHit(28)
			Print "sending message from server"
			chatMsg$=" Hello world!!! server here"
			chatMsg$ = Chr(ID_CHAT) + chatMsg
			; and now broadcast it
			ok = RN_Send(peer, chatMsg$, Len(chatMsg$) + 1, HIGH_PRIORITY, RELIABLE_ORDERED, 0, UNASSIGNED_SYSTEM_ADDRESS, True)
		EndIf
		
		If KeyHit(57)
		Print "creating a bitstream"
			;b=RN_BitStreamCreate1(0)
			Print "created ok"
			RN_BitStreamReset(B)		
			RN_BitStreamWriteUnsignedChar(B,125);msg type	
			RN_BitStreamWriteUnsignedShort(B,MouseX())
			RN_BitStreamWriteUnsignedShort(B,MouseY())
			RN_BitStreamWriteInt(B,Rand(15000))		
			ok = RN_SendBitStream(peer, b, HIGH_PRIORITY, RELIABLE_ORDERED, 0, UNASSIGNED_SYSTEM_ADDRESS, True)
		EndIf
		
		
	;receiving	
	packet = RN_Receive(peer)
	If (packet) Then
		msg$ = RN_PacketGetData(packet)
		msgType = Asc(msg$)
		
		Select msgType
			Case ID_CHAT
				Print "Chat Message: " + msg
				RN_Send(peer, chatMsg$, Len(chatMsg$) + 1, HIGH_PRIORITY, RELIABLE_ORDERED, 0, UNASSIGNED_SYSTEM_ADDRESS, True);
		End Select
	EndIf
	
	
	
	
	
	
	
;******************************************************************************************************
; if Client	
Else
		If KeyHit(28)
			chatMsg$=" hello world!!! Client here!!!"
			chatMsg$ = Chr(ID_CHAT) + chatMsg
			ok = RN_Send(peer, chatMsg$, Len(chatMsg$) + 1, HIGH_PRIORITY, RELIABLE_ORDERED, 0, UNASSIGNED_SYSTEM_ADDRESS, True)
		EndIf
		
	;receiving	
	packet = RN_Receive(peer)
	If (packet) Then
		msg$ = RN_PacketGetData(packet)
		
		BitStreamIN = RN_BitStreamCreateFromPacket(packet)
		id = RN_BitStreamReadUnsignedChar(BitStreamIN)
		msgType = Asc(msg$)
		DebugLog "id "+id
		DebugLog "msgtype "+msgtype
		
		Select msgType
			Case ID_CHAT
			Print "Chat Message: " + msg
		End Select
		
		
		Select id
			Case 125
				x = RN_BitStreamReadUnsignedShort(BitStreamIn)
				y = RN_BitStreamReadUnsignedShort(BitStreamIn)
				random = RN_BitStreamReadInt(BitStreamIn)
				Print "MOUSEUPDATE: X = " + x + " Y = " + y + "random number= "+random
		
		End Select
		
		
		
		
		
		
		
	EndIf
	
EndIf


Wend

RN_CloseConnection(peer,RN_GetSystemAddressFromIndex(peer, 0), True)
RN_Shutdown(peer, 100)
peer = RN_DestroyRakPeerInterface(peer)

WaitKey()
End