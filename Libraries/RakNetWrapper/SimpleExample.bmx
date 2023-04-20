'------------------------------------------------------------------------------------------
'------------------------------------------------------------------------------------------
'------------------------------------------------------------------------------------------
'- Raknet wrapper for BlitzMax. This wrapper was originally developped by Kurix for       -
'- Blitz3D, adapted to RakNet 3 and BlitzMax by RepeatUntil.                              -
'- This wrapper is open source, and could be modified by anyone who would like to         -
'- improve it. This is a project who should benefit the whole Blitz community, so please  -
'- send all improvements to repeatuntil@free.fr (to upload on a web site).                -
'------------------------------------------------------------------------------------------
'------------------------------------------------------------------------------------------
'------------------------------------------------------------------------------------------


Strict

Include "RakNet.bmx"


Global txtList:TList = New TList


Global peer = 0
Global serverPort = 61019
Global packet = 0
Global isServer
Global stat = 0

Global systemAddressServer = 0
Global systemAddressClient1 = 0
Global systemAddressClient2 = 0


' Should start after ID_USER_PACKET_ENUM (=82 in Raknet 3.2)
Const ID_KEY_F1 = 100
Const ID_CHAT = 101


Global nbPlayerMax = 32

'SetGraphicsDriver GLMax2DDriver()
 
Graphics 640, 480, 0

Local str$ = InputText("(S)erver or (C)lient?  ", 0, 0);

peer = RN_GetRakPeerInterface()


If Upper(str$) = "S" Then
	isServer = True
	Local ok = RN_Startup(peer, nbPlayerMax, 0, serverPort)
	RN_SetMaximumIncomingConnections(peer,nbPlayerMax)
	
	If ok Then 
		AddTxt "Server correctly started"
	Else
		AddTxt "Problem when starting the server!"
	EndIf	
Else
	isServer = False
	str$ = "" 'InputText("Enter server IP Or hit enter For 127.0.0.1:  ", 0, 0);
	If Trim(str) = "" Then str = "127.0.0.1"
	RN_Startup(peer,1,0,0)   ' 1 player allowed to connect -> client
	Local ok = RN_Connect(peer,str, serverPort, "", 0);
	If ok Then 
		AddTxt "Client correctly started"
	Else
		AddTxt "Problem when starting the client!"
	EndIf
EndIf



While (Not KeyHit(KEY_ESCAPE))
	Local chatMsg$ = DynamicInput("Enter chat here : ", 20, GraphicsHeight()*0.9)
	If Trim(chatMsg) <> "" Then
		chatMsg = Chr(ID_CHAT) + chatMsg
		Local ok = RN_Send(peer, chatMsg$, Len(chatMsg$) + 1, HIGH_PRIORITY, RELIABLE_ORDERED, 0, UNASSIGNED_SYSTEM_ADDRESS, True)
	EndIf


	packet = RN_Receive(peer)


	If (packet) Then
		Local msg$ = RN_PacketGetData(packet)
		Local msgType = Asc(msg[0..1])

				
		Local systemAddress = RN_PacketGetSystemAddress(packet)
		AddTxt("Packet from = " + systemAddress + "/" + RN_PacketGetBinaryAddress(packet) + "/" + RN_PacketGetPort(packet))
' We could do as well:
'		AddTxt("binary address 2 = " + RN_SystemAddressGetBinaryAddress(systemAddress))
'		AddTxt("port 2 = " + RN_SystemAddressGetPort(systemAddress))
		
				
		Select msgType
			Case ID_REMOTE_DISCONNECTION_NOTIFICATION
				AddTxt("Another client has disconnected.")
			Case ID_REMOTE_CONNECTION_LOST
				AddTxt("Another client has lost the connection.")
			Case ID_REMOTE_NEW_INCOMING_CONNECTION
				AddTxt("Another client has connected.")
			Case ID_CONNECTION_REQUEST_ACCEPTED
				systemAddressServer = RN_PacketGetSystemAddress(packet)
				AddTxt("Our connection request has been accepted.")
				'AddTxt("extern = " + RN_GetExternalID(peer,systemAddressServer))
			Case ID_NEW_INCOMING_CONNECTION
				If Not systemAddressClient1 Then
					systemAddressClient1 = RN_PacketGetSystemAddress(packet)
					stat = RN_ServerGetStatistics(peer,systemAddressClient1)
				Else
					systemAddressClient2 = RN_PacketGetSystemAddress(packet)
				EndIf
				AddTxt("A connection is incoming.")
			Case ID_NO_FREE_INCOMING_CONNECTIONS
				AddTxt("The server is full.")
			Case ID_DISCONNECTION_NOTIFICATION
				If (isServer)
					AddTxt("A client has disconnected.")
				Else
					AddTxt("We have been disconnected.")
				EndIf
			Case ID_CONNECTION_LOST
				If (isServer)
					AddTxt("A client lost the connection.")
				Else
					AddTxt("Connection lost.")
				EndIf
			Case ID_CHAT
				AddTxt "Chat Message: " + msg[1..]
				' The server relays this message to other clients
				' Ideally, we need to add RN_PacketGetSystemAddress(packet) to the message so that the client knows who is the original sender
				If (isServer) Then
					RN_Send(peer,msg$,Len(msg)+1,HIGH_PRIORITY,RELIABLE_ORDERED,0,RN_PacketGetSystemAddress(packet),True);
				EndIf
			Default
				AddTxt("Message with identifier " + msgType + " has arrived.")

		End Select


		packet = RN_DeallocatePacket(peer, packet)

	EndIf

'	DrawText(ServerConnectionCount() + " people connected", 20, GraphicsHeight()*0.55)
	If (isServer) Then
		If (systemAddressClient1)  DrawText("Ping: " + RN_GetAveragePing(peer, systemAddressClient1) + "/" + RN_GetLowestPing(peer,systemAddressClient1) + "/" + RN_GetLastPing(peer,systemAddressClient1), 20, GraphicsHeight()*0.6)
		If (stat) Then
			DrawText("stat = " + RN_StatisticsGetTotalBitsSent(stat) + "/" + RN_StatisticsGetPacketsReceived(stat), 20, GraphicsHeight()*0.65)
		EndIf
	EndIf

	DrawTxt()
	Flip
	
	Delay 1
	
	Cls
Wend

RN_CloseConnection(peer,RN_GetSystemAddressFromIndex(peer, 0), True)
RN_Shutdown(peer, 100)
peer = RN_DestroyRakPeerInterface(peer)




Function ServerConnectionCount%()
	Local count = 0
	For Local i = 0 To nbPlayerMax
		If RN_GetSystemAddressFromIndex(peer, i) <> -1  count = count + 1
	Next
	Return count
End Function





'-------------- NOT RELATED TO RAKNET - JUST USEFUL STUFF --------------------


Rem 
bbdoc: InputText works just as a normal input but in graphicsmode. It waits for you to press enter then returns a string.
EndRem 
Function InputText$(Text$,X,Y)
Local Inp$
	Repeat
		Inp = TInput.Text(Text$,X,Y)
		Flip;Cls
	Until Inp <> ""

Return Inp
EndFunction

Rem 
bbdoc: InputText works just as a normal Textinput but it does NOT stop the program! Returns "" until ENTER is pressed then the message you have written is returned as a string.
endrem 
Function DynamicInput$(Text$,X,Y)
	Return TInput.Text(Text$,X,Y)
EndFunction




Type TInput

	Global tempText$

	Function Text$(Text$,X,Y)
		
			Local aKeytoGet = GetChar()
			If aKeytoGet'Anykey was pressed
			
				If aKeytoGet = 13 'ENTER
					Text$ = tempText$
					If Text$ = "" Then Text = " "
					tempText$ = ""
					FlushKeys
					Return Text$
				Else If aKeytoGet = 8 Or aKeytoGet = 4 'AscII For Backspace And Delete
					If Len( tempText$ ) > 0 Then tempText$ = Left$( tempText$, Len(tempText$) -1 )	
				Else' If aKeytoGet>=32 And aKeytoGet<=122 And Len(Message)<52
					tempText$:+ Chr$(aKeytoGet)
				EndIf
	
			EndIf
			
			DrawText Text$ + tempText,X,Y
			Return ""

	EndFunction

EndType



'********************************************
' Object used to draw some text on the screen
'********************************************
Type Txt
	Field content$
	Field red%, green%, blue%
End Type



'**************************************************
' Function allowing to add some text to the txtList
'*************************************************
Function AddTxt(content$, red% = 255, green% = 255, blue% = 255, limit = 10)
	Local t:Txt = New Txt
	t.content = content
	t.red = red
	t.green = green
	t.blue = blue
	txtList.AddLast t
	If txtList.Count() > limit Then txtList.RemoveFirst()
End Function



'*******************************************
' Function to draw the txtList to the screen
'*******************************************
Function DrawTxt(x% = 20, y% = 20, yStep% = 20)
  SetScale 1,1
  SetRotation 0
	For Local t:Txt = EachIn txtList
		SetColor t.red, t.green, t.blue
		DrawText t.content, x, y
		y :+ yStep
	Next 
End Function