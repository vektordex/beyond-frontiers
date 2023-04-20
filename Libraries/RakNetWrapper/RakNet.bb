;BitStreams are wrapped. If you need them =  add the functions here!

; RAKNET 3
;-------------------------;
;MESSAGE TYPES
;-------------------------;

	; Ping from a connected system.  Update timestamps (internal use only)
	; 0 is reserved For UDT's connect message
	Const ID_INTERNAL_PING =  0 
	; Ping from an unconnected system.  Reply but do Not update timestamps. (internal use only)
	Const ID_PING = 1
	; Ping from an unconnected system.  Only reply If we have open connections. Do Not update timestamps. (internal use only)
	Const ID_PING_OPEN_CONNECTIONS = 2
	; Pong from a connected system.  Update timestamps (internal use only)
	Const ID_CONNECTED_PONG = 3
	; Asking For a New connection (internal use only)
	Const ID_CONNECTION_REQUEST = 4
	; Connecting To a secured server/peer (internal use only)
	Const ID_SECURED_CONNECTION_RESPONSE = 5
	; Connecting To a secured server/peer (internal use only)
	Const ID_SECURED_CONNECTION_CONFIRMATION = 6
	; Packet that tells us the packet contains an integer ID To name mapping For the remote system (internal use only)
	Const ID_RPC_MAPPING = 7
	; A RELIABLE packet To detect lost connections (internal use only)
	Const ID_DETECT_LOST_CONNECTIONS = 8
	; Offline message so we know when To reset And start a New connection (internal use only)
	Const ID_OPEN_CONNECTION_REQUEST = 9
	; Offline message response so we know when To reset And start a New connection (internal use only)
	Const ID_OPEN_CONNECTION_REPLY = 10
	; Remote procedure call (internal use only)
	Const ID_RPC = 11
	; Remote procedure call reply =  For RPCs that Return Data (internal use only)
	Const ID_RPC_REPLY = 12
	; RakPeer - Same as ID_ADVERTISE_SYSTEM =  but intended For internal use rather than being passed To the user. Second byte indicates Type. Used currently For NAT punchthrough For receiver port advertisement. See ID_NAT_ADVERTISE_RECIPIENT_PORT
	Const ID_OUT_OF_BAND_INTERNAL = 13
	

	;
	; USER TYPES - DO Not CHANGE THESE
	;

	; RakPeer - In a client/server environment =  our connection request To the server has been accepted.
	Const ID_CONNECTION_REQUEST_ACCEPTED = 14
	; RakPeer - Sent To the player when a connection request cannot be completed due To inability To connect. 
	Const ID_CONNECTION_ATTEMPT_FAILED = 15
	; RakPeer - Sent a connect request To a system we are currently connected To.
	Const ID_ALREADY_CONNECTED = 16
	; RakPeer - A remote system has successfully connected.
	Const ID_NEW_INCOMING_CONNECTION = 17
	; RakPeer - The system we attempted To connect To is Not accepting New connections.
	Const ID_NO_FREE_INCOMING_CONNECTIONS = 18
	; RakPeer - The system specified in Packet::systemAddress has disconnected from us.  For the client =  this would mean the server has shutdown. 
	Const ID_DISCONNECTION_NOTIFICATION = 19
	; RakPeer - RELIABLE packets cannot be delivered To the system specified in Packet::systemAddress.  The connection To that system has been closed. 
	Const ID_CONNECTION_LOST = 20
	; RakPeer - We preset an RSA public key which does Not match what the system we connected To is using.
	Const ID_RSA_PUBLIC_KEY_MISMATCH = 21
	; RakPeer - We are banned from the system we attempted To connect To.
	Const ID_CONNECTION_BANNED = 22
	; RakPeer - The remote system is using a password And has refused our connection because we did Not set the correct password.
	Const ID_INVALID_PASSWORD = 23
	; RAKNET_PROTOCOL_VERSION in RakNetVersion.h does Not match on the remote system what we have on our system
	; This means the two systems cannot communicate.
	; The 2nd byte of the message contains the value of RAKNET_PROTOCOL_VERSION For the remote system
	Const ID_INCOMPATIBLE_PROTOCOL_VERSION = 24
	; Means that this IP address connected recently =  And can't connect again as a security measure. See RakPeer::SetLimitIPConnectionFrequency()
	Const ID_IP_RECENTLY_CONNECTED = 25
	; RakPeer - A packet has been tampered with in transit.  The sender is contained in Packet::systemAddress.
	Const ID_MODIFIED_PACKET = 26
	; RakPeer - The four bytes following this byte represent an unsigned Int which is automatically modified by the difference in system times between the sender And the recipient. Requires that you call SetOccasionalPing.
	Const ID_TIMESTAMP = 27
    ; RakPeer - Pong from an unconnected system.  First byte is ID_PONG =  second sizeof(RakNetTime) bytes is the ping =  following bytes is system specific enumeration Data.
	Const ID_PONG = 28
	; RakPeer - Inform a remote system of our IP/Port. On the recipient =  all Data past ID_ADVERTISE_SYSTEM is whatever was passed To the Data parameter
	Const ID_ADVERTISE_SYSTEM = 29
	; ConnectionGraph plugin - In a client/server environment =  a client other than ourselves has disconnected gracefully.  Packet::systemAddress is modified To reflect the systemAddress of this client.
	Const ID_REMOTE_DISCONNECTION_NOTIFICATION = 30
	; ConnectionGraph plugin - In a client/server environment =  a client other than ourselves has been forcefully dropped. Packet::systemAddress is modified To reflect the systemAddress of this client.
	Const ID_REMOTE_CONNECTION_LOST = 31
	; ConnectionGraph plugin - In a client/server environment =  a client other than ourselves has connected.  Packet::systemAddress is modified To reflect the systemAddress of the client that is Not connected directly To us. The packet encoding is SystemAddress 1 =  ConnectionGraphGroupID 1 =  SystemAddress 2 =  ConnectionGraphGroupID 2
	; ConnectionGraph2 plugin: Bytes 1-4 = Count. For (Count items) contains {SystemAddress =  RakNetGUID}
	Const ID_REMOTE_NEW_INCOMING_CONNECTION = 32
	; RakPeer - Downloading a large message. Format is ID_DOWNLOAD_PROGRESS (MessageID) =  partCount (unsigned Int) =  partTotal (unsigned Int) =  partLength (unsigned Int) =  First part Data (length <= MAX_MTU_SIZE). See the three parameters partCount =  partTotal And partLength in OnFileProgress in FileListTransferCBInterface.h
	Const ID_DOWNLOAD_PROGRESS = 33
	
	; FileListTransfer plugin - Setup Data
	Const ID_FILE_LIST_TRANSFER_HEADER = 34
	; FileListTransfer plugin - A file
	Const ID_FILE_LIST_TRANSFER_FILE = 35
	; Ack For reference push =  To send more of the file
	Const ID_FILE_LIST_REFERENCE_PUSH_ACK = 36

	; DirectoryDeltaTransfer plugin - Request from a remote system For a download of a directory
	Const ID_DDT_DOWNLOAD_REQUEST = 37
	
	; RakNetTransport plugin - Transport provider message =  used For remote console
	Const ID_TRANSPORT_STRING = 38

	; ReplicaManager plugin - Create an Object
	Const ID_REPLICA_MANAGER_CONSTRUCTION = 39
	; ReplicaManager plugin - Destroy an Object
	Const ID_REPLICA_MANAGER_DESTRUCTION = 40
	; ReplicaManager plugin - Changed scope of an Object
	Const ID_REPLICA_MANAGER_SCOPE_CHANGE = 41
	; ReplicaManager plugin - Serialized Data of an Object
	Const ID_REPLICA_MANAGER_SERIALIZE = 42
	; ReplicaManager plugin - New connection =  about To send all world objects
	Const ID_REPLICA_MANAGER_DOWNLOAD_STARTED = 43
	; ReplicaManager plugin - Finished downloading all serialized objects
	Const ID_REPLICA_MANAGER_DOWNLOAD_COMPLETE = 44

	; ConnectionGraph plugin - Request the connection graph from another system
	Const ID_CONNECTION_GRAPH_REQUEST = 45
	; ConnectionGraph plugin - Reply To a connection graph download request
	Const ID_CONNECTION_GRAPH_REPLY = 46
	; ConnectionGraph plugin - Update edges / nodes For a system with a connection graph
	Const ID_CONNECTION_GRAPH_UPDATE = 47
	; ConnectionGraph plugin - Add a New connection To a connection graph
	Const ID_CONNECTION_GRAPH_NEW_CONNECTION = 48
	; ConnectionGraph plugin - Remove a connection from a connection graph - connection was abruptly lost. Two systems addresses encoded in the Data packet.
	Const ID_CONNECTION_GRAPH_CONNECTION_LOST = 49
	; ConnectionGraph plugin - Remove a connection from a connection graph - connection was gracefully lost. Two systems addresses encoded in the Data packet.
	Const ID_CONNECTION_GRAPH_DISCONNECTION_NOTIFICATION = 50

	; Router plugin - route a message through another system
	Const ID_ROUTE_AND_MULTICAST = 51

	; RakVoice plugin - Open a communication channel
	Const ID_RAKVOICE_OPEN_CHANNEL_REQUEST = 52
	; RakVoice plugin - Communication channel accepted
	Const ID_RAKVOICE_OPEN_CHANNEL_REPLY = 53
	; RakVoice plugin - Close a communication channel
	Const ID_RAKVOICE_CLOSE_CHANNEL = 54
	; RakVoice plugin - Voice Data
	Const ID_RAKVOICE_DATA = 55

	; Autopatcher plugin - Get a list of files that have changed since a certain date
	Const ID_AUTOPATCHER_GET_CHANGELIST_SINCE_DATE = 56
	; Autopatcher plugin - A list of files To create
	Const ID_AUTOPATCHER_CREATION_LIST = 57
	; Autopatcher plugin - A list of files To Delete
	Const ID_AUTOPATCHER_DELETION_LIST = 58
	; Autopatcher plugin - A list of files To get patches For
	Const ID_AUTOPATCHER_GET_PATCH = 59
	; Autopatcher plugin - A list of patches For a list of files
	Const ID_AUTOPATCHER_PATCH_LIST = 60
	; Autopatcher plugin - Returned To the user: An error from the database repository For the autopatcher.
	Const ID_AUTOPATCHER_REPOSITORY_FATAL_ERROR = 61
	; Autopatcher plugin - Finished getting all files from the autopatcher
	Const ID_AUTOPATCHER_FINISHED_INTERNAL = 62
	Const ID_AUTOPATCHER_FINISHED = 63
	; Autopatcher plugin - Returned To the user: You must restart the application To finish patching.
	Const ID_AUTOPATCHER_RESTART_APPLICATION = 64

	; NATPunchthrough plugin: internal
	Const ID_NAT_PUNCHTHROUGH_REQUEST = 65
	; NATPunchthrough plugin: internal
	Const ID_NAT_CONNECT_AT_TIME = 66
	; NATPunchthrough plugin: internal
	Const ID_NAT_GET_MOST_RECENT_PORT = 67
	; NATPunchthrough plugin: internal
	Const ID_NAT_CLIENT_READY = 68

	; NATPunchthrough plugin: Destination system is Not connected To the server. Bytes starting at offset 1 contains the RakNetGUID destination Field of NatPunchthroughClient::OpenNAT().
	Const ID_NAT_TARGET_NOT_CONNECTED = 69
	; NATPunchthrough plugin: Destination system is Not responding To the plugin messages. Possibly the plugin is Not installed. Bytes starting at offset 1 contains the RakNetGUID  destination Field of NatPunchthroughClient::OpenNAT().
	Const ID_NAT_TARGET_UNRESPONSIVE = 70
	; NATPunchthrough plugin: The server lost the connection To the destination system While setting up punchthrough. Possibly the plugin is Not installed. Bytes starting at offset 1 contains the RakNetGUID  destination Field of NatPunchthroughClient::OpenNAT().
	Const ID_NAT_CONNECTION_TO_TARGET_LOST = 71
	; NATPunchthrough plugin: This punchthrough is already in progress. Possibly the plugin is Not installed. Bytes starting at offset 1 contains the RakNetGUID destination Field of NatPunchthroughClient::OpenNAT().
	Const ID_NAT_ALREADY_IN_PROGRESS = 72
	; NATPunchthrough plugin: This message is generated on the Local system =  and does Not come from the network. packet::guid contains the destination Field of NatPunchthroughClient::OpenNAT(). Byte 1 contains 1 If you are the sender =  0 If Not
	Const ID_NAT_PUNCHTHROUGH_FAILED = 73
	; NATPunchthrough plugin: Punchthrough suceeded. See packet::systemAddress And packet::guid. Byte 1 contains 1 If you are the sender =  0 If Not. You can now use RakPeer::Connect() Or other calls To communicate with this system.
	Const ID_NAT_PUNCHTHROUGH_SUCCEEDED = 74

	; LightweightDatabase plugin - Query
	Const ID_DATABASE_QUERY_REQUEST = 75
	; LightweightDatabase plugin - Update
	Const ID_DATABASE_UPDATE_ROW = 76
	; LightweightDatabase plugin - Remove
	Const ID_DATABASE_REMOVE_ROW = 77
	; LightweightDatabase plugin - A serialized table.  Bytes 1+ contain the table.  Pass To TableSerializer::DeserializeTable
	Const ID_DATABASE_QUERY_REPLY = 78
	; LightweightDatabase plugin - Specified table Not found
	Const ID_DATABASE_UNKNOWN_TABLE = 79
	; LightweightDatabase plugin - Incorrect password
	Const ID_DATABASE_INCORRECT_PASSWORD = 80

	; ReadyEvent plugin - Set the ready state For a particular system
	; First 4 bytes After the message contains the id
	Const ID_READY_EVENT_SET = 81
	; ReadyEvent plugin - Unset the ready state For a particular system
	; First 4 bytes After the message contains the id
	Const ID_READY_EVENT_UNSET = 82
	; All systems are in state ID_READY_EVENT_SET
	; First 4 bytes After the message contains the id
	Const ID_READY_EVENT_ALL_SET = 83
	; \internal =  do Not process in your game
	; ReadyEvent plugin - Request of ready event state - used For pulling Data when newly connecting
	Const ID_READY_EVENT_QUERY = 84

	; Lobby packets. Second byte indicates Type.
	Const ID_LOBBY_GENERAL = 85

	; Auto RPC procedure call
	Const ID_AUTO_RPC_CALL = 86

	; Auto RPC functionName To index mapping
	Const ID_AUTO_RPC_REMOTE_INDEX = 87

	; Auto RPC functionName To index mapping =  lookup failed. Will try To auto recover
	Const ID_AUTO_RPC_UNKNOWN_REMOTE_INDEX = 88

	; Auto RPC error code
	; See AutoRPC.h For codes =  stored in packet->Data[1]
	Const ID_RPC_REMOTE_ERROR = 89

	; FileListTransfer transferring large files in chunks that are Read only when needed =  To save memory
	Const ID_FILE_LIST_REFERENCE_PUSH = 90

	; Force the ready event To all set
	Const ID_READY_EVENT_FORCE_ALL_SET = 91

	; Rooms Function
	Const ID_ROOMS_EXECUTE_FUNC = 92
	Const ID_ROOMS_LOGON_STATUS = 93
	Const ID_ROOMS_HANDLE_CHANGE = 94

	; Lobby2 message
	Const ID_LOBBY2_SEND_MESSAGE = 95
	Const ID_LOBBY2_SERVER_ERROR = 96


	; Informs user of a New host GUID. Packet::Guid contains this RakNetGuid
	Const ID_FCM2_NEW_HOST = 97
	; \internal For FullyConnectedMesh2 plugin
	Const ID_FCM2_REQUEST_FCMGUID = 98
	; \internal For FullyConnectedMesh2 plugin
	Const ID_FCM2_RESPOND_CONNECTION_COUNT = 99
	; \internal For FullyConnectedMesh2 plugin
	Const ID_FCM2_INFORM_FCMGUID = 100

	; UDP proxy messages. Second byte indicates Type.
	Const ID_UDP_PROXY_GENERAL = 101

	; SQLite3Plugin - execute
	Const ID_SQLite3_EXEC = 102
	; SQLite3Plugin - Remote database is unknown
	Const ID_SQLite3_UNKNOWN_DB = 103

	; Serialize construction For an Object that already exists on the remote system
	Const ID_REPLICA_MANAGER_3_SERIALIZE_CONSTRUCTION_EXISTING = 104
	Const ID_REPLICA_MANAGER_3_LOCAL_CONSTRUCTION_REJECTED = 105
	Const ID_REPLICA_MANAGER_3_LOCAL_CONSTRUCTION_ACCEPTED = 106

	; Sent To NatTypeDetectionServer
	Const ID_NAT_TYPE_DETECTION_REQUEST = 107

	; Sent To NatTypeDetectionClient. Byte 1 contains the Type of NAT detected.
	Const ID_NAT_TYPE_DETECTION_RESULT = 108

	; Events happening with SQLiteClientLoggerPlugin
	Const ID_SQLLITE_LOGGER = 109

	; Used by the router2 plugin
	Const ID_ROUTER_2_INTERNAL = 110
	; No path is available Or can be established To the remote system
	; Packet::guid contains the endpoint guid that we were trying To reach
	Const ID_ROUTER_2_FORWARDING_NO_PATH = 111
	; \brief You can now call connect =  ping =  Or other operations To the destination system.
	;
	; Connect as follows:
	;
	; RakNet::BitStream bs(packet->Data =  packet->length =  False);
	; bs.IgnoreBytes(sizeof(MessageID));
	; RakNetGUID endpointGuid;
	; bs.Read(endpointGuid);
	; unsigned short sourceToDestPort;
	; bs.Read(sourceToDestPort);
	; char ipAddressString[32];
	; packet->systemAddress.ToString(False =  ipAddressString);
	; rakPeerInterface->Connect(ipAddressString =  sourceToDestPort =  0 = 0);
	Const ID_ROUTER_2_FORWARDING_ESTABLISHED = 112
	; The IP address For a forwarded connection has changed
	; Read endpointGuid And port as per ID_ROUTER_2_FORWARDING_ESTABLISHED
	Const ID_ROUTER_2_REROUTED = 113

	; \internal Used by the team balancer plugin
	Const ID_TEAM_BALANCER_INTERNAL = 114
	; Cannot switch To the desired team because it is full. However =  If someone on that team leaves =  you will get ID_TEAM_BALANCER_SET_TEAM later. Byte 1 contains the team you requested To join.
	Const ID_TEAM_BALANCER_REQUESTED_TEAM_CHANGE_PENDING = 115
	; Cannot switch To the desired team because all teams are locked. However =  If someone on that team leaves =  you will get ID_TEAM_BALANCER_SET_TEAM later. Byte 1 contains the team you requested To join.
	Const ID_TEAM_BALANCER_TEAMS_LOCKED = 116
	; Team balancer plugin informing you of your team. Byte 1 contains the team you requested To join.
	Const ID_TEAM_BALANCER_TEAM_ASSIGNED = 117
	; Gamebryo Lightspeed
	Const ID_LIGHTSPEED_INTEGRATION = 118

	; Plugin based replacement For old RPC system =  no boost required =  but only works with C functions
	Const ID_RPC_4_PLUGIN = 119

	; If RakPeerInterface::Send() is called where PacketReliability contains _WITH_ACK_RECEIPT =  Then on a later call To RakPeerInterface::Receive() you will get ID_SND_RECEIPT_ACKED Or ID_SND_RECEIPT_LOSS. The message will be 5 bytes long =  And bytes 1-4 inclusive will contain a number in native order containing a number that identifies this message. This number will be returned by RakPeerInterface::Send() Or RakPeerInterface::SendList(). ID_SND_RECEIPT_ACKED means that the message arrived
	Const ID_SND_RECEIPT_ACKED = 120

	; If RakPeerInterface::Send() is called where PacketReliability contains _WITH_ACK_RECEIPT =  Then on a later call To RakPeerInterface::Receive() you will get ID_SND_RECEIPT_ACKED Or ID_SND_RECEIPT_LOSS. The message will be 5 bytes long =  And bytes 1-4 inclusive will contain a number in native order containing a number that identifies this message. This number will be returned by RakPeerInterface::Send() Or RakPeerInterface::SendList(). ID_SND_RECEIPT_LOSS means that an ack For the message did Not arrive (it may Or may Not have been delivered =  probably Not). On disconnect Or shutdown =  you will Not get ID_SND_RECEIPT_LOSS For unsent messages =  you should consider those messages as all lost.
	Const ID_SND_RECEIPT_LOSS = 121

	; So I can add more without changing user enumerations
	Const ID_RESERVED_5 = 122
	Const ID_RESERVED_6 = 123
	Const ID_RESERVED_7 = 124
	Const ID_RESERVED_8 = 125
	Const ID_RESERVED_9 = 126

	; For the user To use.  Start your First enumeration at this value.
	Const ID_USER_PACKET_ENUM = 127
	

;-------------------------;
;MESSAGE PRIORITIES
;-------------------------;
Const	HIGH_PRIORITY= 1
Const	MEDIUM_PRIORITY= 2
Const	LOW_PRIORITY= 3

;-------------------------;
;MESSAGE RELIABILITY
;-------------------------;
Const UNRELIABLE= 0
Const UNRELIABLE_SEQUENCED= 1
Const RELIABLE= 2
Const RELIABLE_ORDERED= 3
Const RELIABLE_SEQUENCED= 4

;-------------------------;
;UNASSIGNED PLAYER ID
;-------------------------;
Global UNASSIGNED_SYSTEM_ADDRESS= RN_GetUNASSIGNED_SYSTEM_ADDRESS()
Global UNASSIGNED_NETWORK_ID= RN_GetUNASSIGNED_NETWORK_ID()

Function RN_GetPacketIdentifier%(packet%)
	Local msg$ = RN_PacketGetData(packet)
	;Return Asc(msg[0..1])
	Return Asc(msg)
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D