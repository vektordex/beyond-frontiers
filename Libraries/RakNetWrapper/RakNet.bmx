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


Global lib:Int = LoadLibraryA("RakNet.dll")

Global RN_GetRakPeerInterface%()"Win32" = GetProcAddress(lib, "RN_GetRakPeerInterface@0")
Global RN_DestroyRakPeerInterface%(rakPeerInterface%)"Win32" = GetProcAddress(lib, "RN_DestroyRakPeerInterface@4")

Global RN_Startup%(rakPeerInterface%,maxConnections%, _threadSleepTimer%, localPort%)"Win32" = GetProcAddress(lib, "RN_Startup@16")
Global RN_IsActive%(rakPeerInterface%)"Win32" = GetProcAddress(lib, "RN_IsActive@4")
Global RN_GetMaximumNumberOfPeers%(rakPeerInterface%)"Win32" = GetProcAddress(lib, "RN_GetMaximumNumberOfPeers@4")
Global RN_SetMaximumIncomingConnections(rakPeerInterface%,numberAllowed%)"Win32" = GetProcAddress(lib, "RN_SetMaximumIncomingConnections@8")
Global RN_GetMaximumIncomingConnections%(rakPeerInterface%)"Win32" = GetProcAddress(lib, "RN_GetMaximumIncomingConnections@4")
Global RN_Connect%(rakPeerInterface%,host$z,serverport%,passwordData$z,passwordDataLength%)"Win32" = GetProcAddress(lib, "RN_Connect@20")
Global RN_Shutdown(rakPeerInterface%,blockDuration%)"Win32" = GetProcAddress(lib, "RN_Shutdown@8")
Global RN_CloseConnection(rakPeerInterface%,target%,sendDisconnectionNotification%)"Win32" = GetProcAddress(lib, "RN_CloseConnection@16")
Global RN_GetConnectionList%(rakPeerInterface%,remoteSystems%,numberOfSystems%)"Win32" = GetProcAddress(lib, "RN_GetConnectionList@12") 
Global RN_Send%(rakPeerInterface%,data$z,length%,priority%,reliability%,orderingStream%,systemAddress%,broadcast%)"Win32" = GetProcAddress(lib, "RN_Send@32")
Global RN_SendBitStream%(rakPeerInterface%,bitStream%,priority%,reliability%,orderingChannel%,systemAddress%,broadcast%)"Win32" = GetProcAddress(lib, "RN_SendBitStream@28")
Global RN_Receive%(rakPeerInterface%)"Win32" = GetProcAddress(lib, "RN_Receive@4")
Global RN_DeallocatePacket%(rakPeerInterface%,packet%)"Win32" = GetProcAddress(lib, "RN_DeallocatePacket@8")

Global RN_GetInternalID%(rakPeerInterface%)"Win32" = GetProcAddress(lib, "RN_GetInternalID@4") 
Global RN_GetExternalID%(rakPeerInterface%,target%)"Win32" = GetProcAddress(lib, "RN_GetExternalID@12") 
Global RN_PingPlayer(rakPeerInterface:Int,systemAddress:Int)"Win32" = GetProcAddress(lib, "RN_PingPlayer@8")
Global RN_PingHost(rakPeerInterface:Int,host$z,remotePort:Int,onlyReplyOnAcceptingConnections:Int)"Win32" = GetProcAddress(lib, "RN_PingHost@16")
Global RN_GetAveragePing%(rakPeerInterface:Int,systemAddress:Int)"Win32" = GetProcAddress(lib, "RN_GetAveragePing@8")
Global RN_GetLastPing%(rakPeerInterface:Int,systemAddress:Int)"Win32" = GetProcAddress(lib, "RN_GetLastPing@8")
Global RN_GetLowestPing%(rakPeerInterface:Int,systemAddress:Int)"Win32" = GetProcAddress(lib, "RN_GetLowestPing@8")
Global RN_SetOccasionalPing(rakPeerInterface:Int,doPing:Int)"Win32" = GetProcAddress(lib, "RN_SetOccasionalPing@8")
Global RN_SetOfflinePingResponse(rakPeerInterface:Int,data$z,length:Int)"Win32" = GetProcAddress(lib, "RN_SetOfflinePingResponse@12")
Global RN_GetNumberOfAddresses%(rakPeerInterface%)"Win32" = GetProcAddress(lib, "RN_GetNumberOfAddresses@4")
Global RN_GetLocalIP$z(rakPeerInterface:Int,index:Int)"Win32" = GetProcAddress(lib, "RN_GetLocalIP@8")
Global RN_GetIndexFromSystemAddress%(rakPeerInterface:Int,systemAddress:Int)"Win32" = GetProcAddress(lib, "RN_GetIndexFromSystemAddress@8")
Global RN_GetSystemAddressFromIndex%(rakPeerInterface:Int,index:Int)"Win32" = GetProcAddress(lib, "RN_GetSystemAddressFromIndex@8")

Global RN_GetUNASSIGNED_SYSTEM_ADDRESS%()"Win32" = GetProcAddress(lib, "RN_GetUNASSIGNED_SYSTEM_ADDRESS@0")
Global RN_GetUNASSIGNED_NETWORK_ID%()"Win32" = GetProcAddress(lib, "RN_GetUNASSIGNED_NETWORK_ID@0")
Global RN_GetTime%()"Win32" = GetProcAddress(lib, "RN_GetTime@0")


Global RN_PacketGetData$z(packet%)"Win32" = GetProcAddress(lib, "RN_PacketGetData@4")
Global RN_PacketGetBitSize%(packet%)"Win32" = GetProcAddress(lib, "RN_PacketGetBitSize@4")
Global RN_PacketGetplayerIndex%(packet%)"Win32" = GetProcAddress(lib, "RN_PacketGetplayerIndex@4")  ' Server only according to RakNetTypes.h
Global RN_PacketGetSystemAddress%(packet%)"Win32" = GetProcAddress(lib, "RN_PacketGetSystemAddress@4")
Global RN_PacketGetBinaryAddress(packet%)"Win32" = GetProcAddress(lib, "RN_PacketGetBinaryAddress@4")
Global RN_PacketGetPort(packet%)"Win32" = GetProcAddress(lib, "RN_PacketGetPort@4")
Global RN_SystemAddressGetBinaryAddress%(systemAddress%)"Win32" = GetProcAddress(lib, "RN_SystemAddressGetBinaryAddress@4")
Global RN_SystemAddressGetPort%(systemAddress%)"Win32" = GetProcAddress(lib, "RN_SystemAddressGetPort@4")


Global RN_InitializeSecurity(rakPeerInterface%, pubKeyE$z, pubKeyN$z, privKeyP$z, privKeyQ$z)"Win32" =  GetProcAddress(lib, "RN_InitializeSecurity@20")
Global RN_DisableSecurity(rakPeerInterface%)"Win32" =  GetProcAddress(lib, "RN_DisableSecurity@4")
Global RN_SetIncomingPassword(rakPeerInterface%, passwordData$z, passwordDataLength%)"Win32" = GetProcAddress(lib, "RN_SetIncomingPassword@12")
Global RN_GetIncomingPassword(rakPeerInterface%, passwordData$z, passwordDataLength%)"Win32" = GetProcAddress(lib, "RN_GetIncomingPassword@12")

Global RN_ApplyNetworkSimulator(rakPeerInterface%,maxSendBPS%,minExtraPing%,extraPingVariance%)"Win32" = GetProcAddress(lib, "RN_ApplyNetworkSimulator@16")
Global RN_IsNetworkSimulatorActive%(rakPeerInterface%)"Win32" = GetProcAddress(lib, "RN_IsNetworkSimulatorActive@4")


Global RN_AddToBanList(rakPeerInterface%,ip$z,milliseconds%)"Win32" = GetProcAddress(lib, "RN_AddToBanList@12")
Global RN_RemoveFromBanList(rakPeerInterface%,ip$z)"Win32" = GetProcAddress(lib, "RN_RemoveFromBanList@8")
Global RN_ClearBanList(rakPeerInterface%)"Win32" = GetProcAddress(lib, "RN_ClearBanList@4")
Global RN_IsBanned%(rakPeerInterface%,ip$z)"Win32" = GetProcAddress(lib, "RN_IsBanned@8")


Global RN_AdvertiseSystem(rakPeerInterface%,host$z,remotePort%, data$z,dataLength%)"Win32" = GetProcAddress(lib, "RN_AdvertiseSystem@20")

' Statistics. More functions are wrapped (you can add them here if needed!)
Global RN_ServerGetStatistics(rakPeerInterface%,systemAddress%)"Win32" = GetProcAddress(lib, "RN_ServerGetStatistics@8")
Global RN_StatisticsGetMessagesSent(stat%,queue%)"Win32" = GetProcAddress(lib, "RN_StatisticsGetmessagesSent@8")
Global RN_StatisticsGetMessageDataBitsSent(stat%,queue%)"Win32" = GetProcAddress(lib, "RN_StatisticsGetmessageDataBitsSent@8")
Global RN_StatisticsGetMessageTotalBitsSent(stat%,queue%)"Win32" = GetProcAddress(lib, "RN_StatisticsGetmessageTotalBitsSent@8")
Global RN_StatisticsGetTotalBitsSent(stat%)"Win32" = GetProcAddress(lib, "RN_StatisticsGetTotalBitsSent@4")
Global RN_StatisticsGetBitsReceived(stat%)"Win32" = GetProcAddress(lib, "RN_StatisticsGetBitsReceived@4")
Global RN_StatisticsGetPacketsSent(stat%)"Win32" = GetProcAddress(lib, "RN_StatisticsGetPacketsSent@4")
Global RN_StatisticsGetPacketsReceived(stat%)"Win32" = GetProcAddress(lib, "RN_StatisticsGetPacketsReceived@4")

' BitStreams
Global RN_BitStreamCreate1:Int(initialBytesToAllocate:Int) "Win32" = GetProcAddress(lib, "RN_BitStreamCreate1@4") 
Global RN_BitStreamCreate2:Int(data$z,length:Int,copydata:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamCreate2@12")
Global RN_BitStreamReset(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReset@4")
Global RN_BitStreamDestroy(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamDestroy@4")
Global RN_BitStreamCreateFromPacket:Int(packet:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamCreateFromPacket@4")
Global RN_BitStreamWriteBool (bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteBool@8")
Global RN_BitStreamWriteUnsignedChar (bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteUnsignedChar@8")
Global RN_BitStreamWriteChar (bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteChar@8")
Global RN_BitStreamWriteUnsignedShort (bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteUnsignedShort@8")
Global RN_BitStreamWriteShort (bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteShort@8")
Global RN_BitStreamWriteUnsignedInt (bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteUnsignedInt@8")
Global RN_BitStreamWriteInt (bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteInt@8")
Global RN_BitStreamWriteUnsignedLong (bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteUnsignedLong@8")
Global RN_BitStreamWriteLong (bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteLong@8")
Global RN_BitStreamWriteFloat (bitstream:Int,Inp:Float)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteFloat@8")
Global RN_BitStreamWriteDouble (bitstream:Int,Inp:Float)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteDouble@8")
Global RN_BitStreamWrite (bitstream:Int,Inp$z,numberOfBytes:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWrite@12")
Global RN_BitStreamWriteCompressedUnsignedChar(bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteCompressedUnsignedChar@8")
Global RN_BitStreamWriteCompressedChar(bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteCompressedChar@8")
Global RN_BitStreamWriteCompressedUnsignedShort(bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteCompressedUnsignedShort@8")
Global RN_BitStreamWriteCompressedShort(bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteCompressedShort@8")
Global RN_BitStreamWriteCompressedUnsignedInt(bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteCompressedUnsignedInt@8")
Global RN_BitStreamWriteCompressedInt(bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteCompressedInt@8")
Global RN_BitStreamWriteCompressedUnsignedLong (bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteCompressedUnsignedLong@8")
Global RN_BitStreamWriteCompressedLong(bitstream:Int,Inp:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteCompressedLong@8")
Global RN_BitStreamWriteCompressedFloat(bitstream:Int,Inp:Float)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteCompressedFloat@8")
Global RN_BitStreamWriteCompressedDouble(bitstream:Int,Inp:Float)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteCompressedDouble@8")
Global RN_BitStreamReadBool:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadBool@4")
Global RN_BitStreamReadUnsignedChar:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadUnsignedChar@4")
Global RN_BitStreamReadChar:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadChar@4")
Global RN_BitStreamReadUnsignedShort:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadUnsignedShort@4")
Global RN_BitStreamReadShort:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadShort@4")
Global RN_BitStreamReadUnsignedInt:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadUnsignedInt@4")
Global RN_BitStreamReadInt:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadInt@4")
Global RN_BitStreamReadUnsignedLong:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadUnsignedLong@4")
Global RN_BitStreamReadLong:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadLong@4")
Global RN_BitStreamReadFloat:Float(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadFloat@4")
Global RN_BitStreamReadDouble:Float(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadDouble@4")
Global RN_BitStreamRead$z(bitstream:Int,numberOfBytes:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamRead@8")
Global RN_BitStreamReadCompressedUnsignedChar:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadCompressedUnsignedChar@4")
Global RN_BitStreamReadCompressedChar:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadCompressedChar@4")
Global RN_BitStreamReadCompressedUnsignedShort:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadCompressedUnsignedShort@4")
Global RN_BitStreamReadCompressedShort:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadCompressedShort@4")
Global RN_BitStreamReadCompressedUnsignedInt:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadCompressedUnsignedInt@4")
Global RN_BitStreamReadCompressedInt:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadCompressedInt@4")
Global RN_BitStreamReadCompressedUnsignedLong:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadCompressedUnsignedLong@4")
Global RN_BitStreamReadCompressedLong:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadCompressedLong@4")
Global RN_BitStreamReadCompressedFloat:Float(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadCompressedFloat@4")
Global RN_BitStreamReadCompressedDouble:Float(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadCompressedDouble@4")
Global RN_BitStreamResetReadPointer(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamResetReadPointer@4")
Global RN_BitStreamAssertStreamEmpty(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamAssertStreamEmpty@4")
Global RN_BitStreamPrintBits(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamPrintBits@4")
Global RN_BitStreamIgnoreBits(bitstream:Int,numberOfBits:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamIgnoreBits@8")
Global RN_BitStreamSetWriteOffset(bitstream:Int,offset:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamSetWriteOffset@8")
Global RN_BitStreamGetNumberOfBitsUsed:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamGetNumberOfBitsUsed@4")
Global RN_BitStreamGetNumberOfBytesUsed:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamGetNumberOfBytesUsed@4")
Global RN_BitStreamGetReadOffset:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamGetReadOffset@4")
Global RN_BitStreamGetNumberOfUnreadBits:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamGetNumberOfUnreadBits@4")
Global RN_BitStreamSetData(bitstream:Int,data$z,numberOfBits:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamSetData@12")
Global RN_BitStreamGetData$z(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamGetData@4")
Global RN_BitStreamGetDataPointer:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamGetDataPointer@4")
Global RN_BitStreamWriteBits(bitstream:Int,Inp$z,numberOfBitsToWrite:Int,rightAlignedBits:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteBits@16")
Global RN_BitStreamWriteAlignedBytes(bitstream:Int,Inpt$z,numberOfBytesToWrite:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWriteAlignedBytes@12")
Global RN_BitStreamReadAlignedBytes$z(bitstream:Int,numberOfBytesToRead:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadAlignedBytes@8")
Global RN_BitStreamAlignWriteToByteBoundary(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamAlignWriteToByteBoundary@4")
Global RN_BitStreamAlignReadToByteBoundary(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamAlignReadToByteBoundary@4")
Global RN_BitStreamReadBits$z(bitstream:Int,numberOfBitsToRead:Int,alignBitsToRight:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadBits@12")
Global RN_BitStreamWrite0(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWrite0@4")
Global RN_BitStreamWrite1(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamWrite1@4")
Global RN_BitStreamReadBit:Int(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamReadBit@4")
Global RN_BitStreamAssertCopyData(bitstream:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamAssertCopyData@4")
Global RN_BitStreamSetNumberOfBitsAllocated(bitstream:Int,lengthInBits:Int)"Win32"= GetProcAddress(lib,"RN_BitStreamSetNumberOfBitsAllocated@8")




'-------------------------'
'MESSAGE TYPES
'-------------------------'


	'//
	'// RESERVED TYPES - DO Not CHANGE THESE
	'// All types from RakPeer
	'//
	'/// These types are never returned To the user.
	'/// Ping from a connected system.  Update timestamps (internal use only)
	Const ID_INTERNAL_PING:Int = 0  
	'/// Ping from an unconnected system.  Reply but do Not update timestamps. (internal use only)
	Const ID_PING:Int = 1
	'/// Ping from an unconnected system.  Only reply If we have open connections. Do Not update timestamps. (internal use only)
	Const ID_PING_OPEN_CONNECTIONS:Int = 2
	'/// Pong from a connected system.  Update timestamps (internal use only)
	Const ID_CONNECTED_PONG:Int = 3
	'/// Asking For a New connection (internal use only)
	Const ID_CONNECTION_REQUEST:Int = 4
	'/// Connecting To a secured server/peer (internal use only)
	Const ID_SECURED_CONNECTION_RESPONSE:Int = 5
	'/// Connecting To a secured server/peer (internal use only)
	Const ID_SECURED_CONNECTION_CONFIRMATION:Int = 6
	'/// Packet that tells us the packet contains an integer ID To name mapping For the remote system (internal use only)
	Const ID_RPC_MAPPING:Int = 7
	'/// A reliable packet To detect lost connections (internal use only)
	Const ID_DETECT_LOST_CONNECTIONS:Int = 8
	'/// Offline message so we know when To reset And start a New connection (internal use only)
	Const ID_OPEN_CONNECTION_REQUEST:Int = 9
	'/// Offline message response so we know when To reset And start a New connection (internal use only)
	Const ID_OPEN_CONNECTION_REPLY:Int = 10
	'/// Remote procedure call (internal use only)
	Const ID_RPC:Int = 11
	'/// Remote procedure call reply =  For RPCs that Return data (internal use only)
	Const ID_RPC_REPLY:Int = 12
	'/// RakPeer - Same as ID_ADVERTISE_SYSTEM, but intended For internal use rather than being passed To the user. Second Byte indicates type. Used currently For NAT punchthrough For receiver port advertisement. See ID_NAT_ADVERTISE_RECIPIENT_PORT
	Const ID_OUT_OF_BAND_INTERNAL:Int = 13


	'//
	'// USER TYPES - DO Not CHANGE THESE
	'//

	'/// RakPeer - In a client/server environment =  our connection request To the server has been accepted.
	Const ID_CONNECTION_REQUEST_ACCEPTED:Int = 14
	'/// RakPeer - Sent To the player when a connection request cannot be completed due To inability To connect.
	Const ID_CONNECTION_ATTEMPT_FAILED:Int = 15
	'/// RakPeer - Sent a connect request To a system we are currently connected to.
	Const ID_ALREADY_CONNECTED:Int = 16
	'/// RakPeer - A remote system has successfully connected.
	Const ID_NEW_INCOMING_CONNECTION:Int = 17
	'/// RakPeer - The system we attempted To connect To is Not accepting New connections.
	Const ID_NO_FREE_INCOMING_CONNECTIONS:Int = 18
	'/// RakPeer - The system specified in Packet::systemAddress has disconnected from us.  For the client =  this would mean the server has shutdown.
	Const ID_DISCONNECTION_NOTIFICATION:Int = 19
	'/// RakPeer - Reliable packets cannot be delivered To the system specified in Packet::systemAddress.  The connection To that system has been closed.
	Const ID_CONNECTION_LOST:Int = 20
	'/// RakPeer - We preset an RSA Public key which does Not match what the system we connected To is using.
	Const ID_RSA_PUBLIC_KEY_MISMATCH:Int = 21
	'/// RakPeer - We are banned from the system we attempted To connect to.
	Const ID_CONNECTION_BANNED:Int = 22
	'/// RakPeer - The remote system is using a password And has refused our connection because we did Not set the correct password.
	Const ID_INVALID_PASSWORD:Int = 23
	'/// RakPeer - A packet has been tampered with in transit.  The sender is contained in Packet::systemAddress.
	Const ID_MODIFIED_PACKET:Int = 24
	'/// RakPeer - The four bytes following this Byte represent an unsigned Int which is automatically modified by the difference in system times between the sender And the recipient. Requires that you call SetOccasionalPing.
	Const ID_TIMESTAMP:Int = 25
    '/// RakPeer - Pong from an unconnected system.  First Byte is const ID_PONG =  second SizeOf(RakNetTime) bytes is the ping =  following bytes is system specific enumeration data.
	Const ID_PONG:Int = 26
	'/// RakPeer - Inform a remote system of our IP/Port =  plus some offline data
	Const ID_ADVERTISE_SYSTEM:Int = 27
	'/// ConnectionGraph plugin - In a client/server environment =  a client other than ourselves has disconnected gracefully.  Packet::systemAddress is modified To reflect the systemAddress of this client.
	Const ID_REMOTE_DISCONNECTION_NOTIFICATION:Int = 28
	'/// ConnectionGraph plugin - In a client/server environment =  a client other than ourselves has been forcefully dropped. Packet::systemAddress is modified To reflect the systemAddress of this client.
	Const ID_REMOTE_CONNECTION_LOST:Int = 29
	'/// ConnectionGraph plugin - In a client/server environment =  a client other than ourselves has connected.  Packet::systemAddress is modified To reflect the systemAddress of this client.
	Const ID_REMOTE_NEW_INCOMING_CONNECTION:Int = 30
	'// RakPeer - Downloading a large message. Format is Const ID_DOWNLOAD_PROGRESS (MessageID) =  partCount (unsigned Int) =  partTotal (unsigned Int) =  partLength (unsigned Int) =  first part data (length <= MAX_MTU_SIZE)
	Const ID_DOWNLOAD_PROGRESS:Int = 31

	'/// FileListTransfer plugin - Setup data
	Const ID_FILE_LIST_TRANSFER_HEADER:Int = 32
	'/// FileListTransfer plugin - A file
	Const ID_FILE_LIST_TRANSFER_FILE:Int = 33

	'/// DirectoryDeltaTransfer plugin - Request from a remote system For a download of a directory
	Const ID_DDT_DOWNLOAD_REQUEST:Int = 34
	
	'/// RakNetTransport plugin - Transport provider message =  used For remote console
	Const ID_TRANSPORT_STRING:Int = 35

	'/// ReplicaManager plugin - Create an Object
	Const ID_REPLICA_MANAGER_CONSTRUCTION:Int = 36
	'/// ReplicaManager plugin - Destroy an Object
	Const ID_REPLICA_MANAGER_DESTRUCTION:Int = 37
	'/// ReplicaManager plugin - Changed scope of an Object
	Const ID_REPLICA_MANAGER_SCOPE_CHANGE:Int = 38
	'/// ReplicaManager plugin - Serialized data of an Object
	Const ID_REPLICA_MANAGER_SERIALIZE:Int = 39
	'/// ReplicaManager plugin - New connection, about To send all world objects
	Const ID_REPLICA_MANAGER_DOWNLOAD_STARTED:Int = 40
	'/// ReplicaManager plugin - Finished downloading all serialized objects
	Const ID_REPLICA_MANAGER_DOWNLOAD_COMPLETE:Int = 41

	'/// ConnectionGraph plugin - Request the connection graph from another system
	Const ID_CONNECTION_GRAPH_REQUEST:Int = 42
	'/// ConnectionGraph plugin - Reply To a connection graph download request
	Const ID_CONNECTION_GRAPH_REPLY:Int = 43
	'/// ConnectionGraph plugin - Update edges / nodes For a system with a connection graph
	Const ID_CONNECTION_GRAPH_UPDATE:Int = 44
	'/// ConnectionGraph plugin - Add a New connection To a connection graph
	Const ID_CONNECTION_GRAPH_NEW_CONNECTION:Int = 45
	'/// ConnectionGraph plugin - Remove a connection from a connection graph - connection was abruptly lost
	Const ID_CONNECTION_GRAPH_CONNECTION_LOST:Int = 46
	'/// ConnectionGraph plugin - Remove a connection from a connection graph - connection was gracefully lost
	Const ID_CONNECTION_GRAPH_DISCONNECTION_NOTIFICATION:Int = 47

	'/// Router plugin - route a message through another system
	Const ID_ROUTE_AND_MULTICAST:Int = 48

	'/// RakVoice plugin - Open a communication channel
	Const ID_RAKVOICE_OPEN_CHANNEL_REQUEST:Int = 49
	'/// RakVoice plugin - Communication channel accepted
	Const ID_RAKVOICE_OPEN_CHANNEL_REPLY:Int = 50
	'/// RakVoice plugin - Close a communication channel
	Const ID_RAKVOICE_CLOSE_CHANNEL:Int = 51
	'/// RakVoice plugin - Voice data
	Const ID_RAKVOICE_DATA:Int = 52

	'/// Autopatcher plugin - Get a list of files that have changed since a certain date
	Const ID_AUTOPATCHER_GET_CHANGELIST_SINCE_DATE:Int = 53
	'/// Autopatcher plugin - A list of files To create
	Const ID_AUTOPATCHER_CREATION_LIST:Int = 54
	'/// Autopatcher plugin - A list of files To Delete
	Const ID_AUTOPATCHER_DELETION_LIST:Int = 55
	'/// Autopatcher plugin - A list of files To get patches For
	Const ID_AUTOPATCHER_GET_PATCH:Int = 56
	'/// Autopatcher plugin - A list of patches For a list of files
	Const ID_AUTOPATCHER_PATCH_LIST:Int = 57
	'/// Autopatcher plugin - Returned To the user: An error from the database repository For the autopatcher.
	Const ID_AUTOPATCHER_REPOSITORY_FATAL_ERROR:Int = 58
	'/// Autopatcher plugin - Finished getting all files from the autopatcher
	Const ID_AUTOPATCHER_FINISHED_INTERNAL:Int = 59
	Const ID_AUTOPATCHER_FINISHED:Int = 60
	'/// Autopatcher plugin - Returned To the user: You must restart the application To finish patching.
	Const ID_AUTOPATCHER_RESTART_APPLICATION:Int = 61

	'/// NATPunchthrough plugin - Intermediary got a request To help punch through a nat
	Const ID_NAT_PUNCHTHROUGH_REQUEST:Int = 62
	'/// NATPunchthrough plugin - Intermediary cannot complete the request because the target system is Not connected
	Const ID_NAT_TARGET_NOT_CONNECTED:Int = 63
	'/// NATPunchthrough plugin - While attempting To connect =  we lost the connection To the target system
	Const ID_NAT_TARGET_CONNECTION_LOST:Int = 64
	'/// NATPunchthrough plugin - Internal message To connect at a certain time
	Const ID_NAT_CONNECT_AT_TIME:Int = 65
	'/// NATPunchthrough plugin - Internal message To send a message (To punch through the nat) at a certain time
	Const ID_NAT_SEND_OFFLINE_MESSAGE_AT_TIME:Int = 66
	'/// NATPunchthrough plugin - The facilitator is already attempting this connection
	Const ID_NAT_IN_PROGRESS:Int = 67


	'/// LightweightDatabase plugin - Query
	Const ID_DATABASE_QUERY_REQUEST:Int = 68
	'/// LightweightDatabase plugin - Update
	Const ID_DATABASE_UPDATE_ROW:Int = 69
	'/// LightweightDatabase plugin - Remove
	Const ID_DATABASE_REMOVE_ROW:Int = 70
	'/// LightweightDatabase plugin - A serialized table.  Bytes 1+ contain the table.  Pass To TableSerializer::DeserializeTable
	Const ID_DATABASE_QUERY_REPLY:Int = 71
	'/// LightweightDatabase plugin - Specified table Not found
	Const ID_DATABASE_UNKNOWN_TABLE:Int = 72
	'/// LightweightDatabase plugin - Incorrect password
	Const ID_DATABASE_INCORRECT_PASSWORD:Int = 73
	
	'/// ReadyEvent plugin - Set the ready state For a particular system
	Const ID_READY_EVENT_SET:Int = 74
	'/// ReadyEvent plugin - Unset the ready state For a particular system
	Const ID_READY_EVENT_UNSET:Int = 75
	'/// All systems are in state ID_READY_EVENT_SET
	Const ID_READY_EVENT_ALL_SET:Int = 76
	'/// ReadyEvent plugin - Request of ready event state - used For pulling data when newly connecting
	Const ID_READY_EVENT_QUERY:Int = 77

	'/// Lobby packets. Second Byte indicates type.
	Const ID_LOBBY_GENERAL:Int = 78

	'/// Auto RPC procedure call
	Const ID_AUTO_RPC_CALL:Int = 79

	'/// Auto RPC functionName To index mapping
	Const ID_AUTO_RPC_REMOTE_INDEX:Int = 80

	'/// Auto RPC error code
	'/// See AutoRPC.h For codes, stored in packet->data[1]
	Const ID_RPC_REMOTE_ERROR:Int = 81

	
	'// For the user To use.  Start your first enumeration at this value.
	Const ID_USER_PACKET_ENUM:Int = 82



'-------------------------'
'MESSAGE PRIORITIES
'-------------------------'
Const	HIGH_PRIORITY:Int = 1
Const	MEDIUM_PRIORITY:Int = 2
Const	LOW_PRIORITY:Int = 3

'-------------------------'
'MESSAGE RELIABILITY
'-------------------------'
Const UNRELIABLE:Int = 0
Const UNRELIABLE_SEQUENCED:Int = 1
Const RELIABLE:Int = 2
Const RELIABLE_ORDERED:Int = 3
Const RELIABLE_SEQUENCED:Int = 4

'-------------------------'
'UNASSIGNED PLAYER ID
'-------------------------'
Global UNASSIGNED_SYSTEM_ADDRESS:Int = RN_GetUNASSIGNED_SYSTEM_ADDRESS()
Global UNASSIGNED_NETWORK_ID:Int = RN_GetUNASSIGNED_NETWORK_ID()

Function RN_GetPacketIdentifier%(packet%)
	Local msg$ = RN_PacketGetData(packet)
	Return Asc(msg[0..1])
End Function
