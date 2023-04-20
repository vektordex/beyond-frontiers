
'------------------------------------------------------------------------------------------
'------------------------------------------------------------------------------------------
'------------------------------------------------------------------------------------------
'- Raknet wrapper for BlitzMax. This wrapper was originally developped by Kurix for       -
'- Blitz3D, adapted to RakNet 3 and BlitzMax by RepeatUntil.                              -
'- This wrapper is open source, and could be modified by anyone who would like to         -
'- improve it. This is a project who should benefit the whole Blitz community, so please  -
'- send all improvements to <a href="mailto:repeatuntil@free.fr">repeatuntil@free.fr</a> (to upload on a web site).                -
'------------------------------------------------------------------------------------------
'------------------------------------------------------------------------------------------
'------------------------------------------------------------------------------------------
'Bitstream example unprofessionally redone by Retimer. Feb 5th,2008 - Rev 2

Strict

Include "RakNet.bmx"


Global txtList:TList = New TList


Global peer = 0
Global serverPort = 61019
Global packet = 0
Global isServer
Global stat = 0
Global BitReader:Int
Global BitWriter:Int

Global systemAddressServer = 0
Global systemAddressClient1 = 0
Global systemAddressClient2 = 0


' Should start after ID_USER_PACKET_ENUM (=82 in Raknet 3.2)
Const ID_KEY_F1 = 100
Const ID_CHAT = 101


Global nbPlayerMax = 32

Graphics 640, 480, 0

Global YourName:String

Local str:String = InputText("(S)erver or (C)lient?  ", 0, 0) 
If Upper(str:String) <> "S" 'Then player gets to choose screen name
	YourName = InputText("ScreenName: ", 0, 0) 
Else
	YourName = "Server"
End If
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
	Local ok = RN_Connect(peer, str, serverPort, "", 0) ;
	If ok Then 
		AddTxt "Client correctly started"
	Else
		AddTxt "Problem when starting the client!"
	EndIf
EndIf

While (Not KeyHit(KEY_ESCAPE))
	Local chatMsg$ = DynamicInput("Enter chat here : ", 20, GraphicsHeight()*0.9)
	If Trim(chatMsg) <> "" Then
		RN_BitStreamDestroy(BitWriter)  'Probobly not necessary, but I destroy it anyways.
		
		BitWriter = RN_BitStreamCreate1(0)      'Create an empty bitstream
		Local I:Int
		RN_BitStreamWriteChar(BitWriter, ID_CHAT)      'Packet Type
		RN_BitStreamWriteShort(BitWriter, Len(YourName))                'Length of the Chat Message
		For I = 1 To Len(YourName)    'Write Each Char out.
			RN_BitStreamWriteChar(BitWriter, Asc(Mid(YourName, I, 1))) 
		Next
		RN_BitStreamWriteShort(BitWriter, (Len(ChatMsg)))           'Length of the Chat Message
		For I = 1 To Len(ChatMsg)     'Write Each Char out
			RN_BitStreamWriteChar(BitWriter, Asc(Mid(ChatMsg, I, 1))) 
		Next
		RN_BitStreamSetNumberOfBitsAllocated(BitWriter, RN_BitStreamGetNumberOfBitsUsed(BitWriter))  'Fix Number of bytes allocated.
		RN_SendBitStream(peer, BitWriter, HIGH_PRIORITY, RELIABLE_ORDERED, 0, UNASSIGNED_SYSTEM_ADDRESS, True) 

		'Small addition I added, so you can actually see what 'YOU' said.
		AddTxt "You Said: " + ChatMsg
	EndIf


	packet = RN_Receive(peer) 


	If (packet) Then
		If BitReader Then rn_bitstreamdestroy(BitReader)   'Destroy the old bitstream
		BitReader = RN_BitStreamCreateFromPacket(packet)  'Read the packet into a bitstream
		Local msgType:Int = (RN_BitStreamReadChar(BitReader))   'Get the packet type id.
		Local systemAddress:Int = RN_PacketGetSystemAddress(packet) 
		Local UserIndex:Int = RN_GetIndexFromSystemAddress(peer, systemAddress) 
		
		'Un-comment the line below if u truly need this for debugging.
		'AddTxt("Packet from = " + RN_PacketGetplayerIndex(packet) + "/" + RN_PacketGetBinaryAddress(packet) + "/" + RN_PacketGetPort(packet)) 
		
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
				Local UserLen:Int = RN_BitStreamReadShort(BitReader)    'Get length of users ScreenName
				Local UserName:String
				Local I:Int
				For I = 1 To userlen
					UserName = UserName + Chr(RN_BitStreamReadChar(BitReader)) 
				Next
				Local ChatLen:Int = RN_BitStreamReadShort(BitReader)    'Get length of chat message
				Local ChatMessage:String
				For I = 1 To ChatLen
			    	ChatMessage = ChatMessage + Chr(RN_BitStreamReadChar(BitReader)) 
				Next
				AddTxt UserName + ": " + ChatMessage
				If (isServer) Then
					RN_BitStreamResetReadPointer(BitReader) 
					RN_SendBitStream(peer, BitReader, HIGH_PRIORITY, RELIABLE_ORDERED, 0, RN_PacketGetSystemAddress(packet), True) ;
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
			DrawText("stat = " + RN_StatisticsGetTotalBitsSent(stat) + " Bits (" + (RN_StatisticsGetTotalBitsSent(stat) / 8) + " Bytes) /" + RN_StatisticsGetPacketsReceived(stat), 20, GraphicsHeight() * 0.65) 
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
