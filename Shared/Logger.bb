;----------------------------------------------------------------
;-- Logger
;----------------------------------------------------------------
; Requirements:
;	BlitzUtility

;----------------------------------------------------------------
;-- Constants
;----------------------------------------------------------------
Const LOGGER_HISTORY_SIZE%			= 255

;----------------------------------------------------------------
;-- Types
;----------------------------------------------------------------
Type TLogger
	Field Stream					= 0
	Field HighResolutionClock		= 0
	
	Field Entries$[LOGGER_HISTORY_SIZE]
	Field EntriesOffset				= 0
End Type

;----------------------------------------------------------------
;-- Functions
;----------------------------------------------------------------
Function Logger_Create.TLogger(File$ = "")
	Local L.TLogger = New TLogger
	
	If (File = "") Then
		File = "%d-%m-%Y_%H-%M-%S.log"
	EndIf
	
	; Format
	Local pSC = BU_SystemClock_Now()
	Local pTime = BU_SystemClock_AsTime(pSC)
	File = Replace(File, "\", "\\")
	File = BU_Time_Format(pTime, File)
	File = Replace(File, "\\", "\")
	BU_Time_Destroy(pTime)
	BU_SystemClock_Destroy(pSC)
	
	; Open or Create
	L\Stream = OpenFile(File)
	If (L\Stream = 0) Then
		L\Stream = WriteFile(File)
		If (L\Stream = 0) Then
			Delete L
			Return Null
		EndIf
	Else
		; Seek to end of file
		SeekFile L\Stream, FileSize(File)
	EndIf
	
	L\HighResolutionClock = BU_HighResolutionClock_Now()
	
	Return L
End Function

Function Logger_Destroy.TLogger(L.TLogger)
	BU_HighResolutionClock_Destroy(L\HighResolutionClock)
	CloseFile L\Stream
	Delete L
	
	Return Null
End Function

Function Logger_Log(L.TLogger, Msg$)
	Local pNow = BU_HighResolutionClock_Now()
	Local pDuration = BU_HighResolutionClock_DurationLL(pNow, L\HighResolutionClock)
	
	; Create Time String
	Local Time$ = ""
	
	; - Nanoseconds
	Time = Replace(RSet(BU_Long_ToIL(pDuration) Mod 1000,4)," ", "0")
	BU_Long_DivI(pDuration, 1000) ; Convert to Milliseconds
	
	; - Milliseconds
	Time = Replace(RSet(BU_Long_ToIL(pDuration) Mod 1000,4)," ", "0") + "," + Time
	BU_Long_DivI(pDuration, 1000) ; Convert to Seconds
	
	; - Seconds
	Time = Replace(RSet(BU_Long_ToIL(pDuration) Mod 60,2)," ", "0") + "." + Time
	BU_Long_DivI(pDuration, 60) ; Convert to Minutes
	
	; - Minutes
	Time = Replace(RSet(BU_Long_ToIL(pDuration) Mod 60,2)," ", "0") + ":" + Time
	BU_Long_DivI(pDuration, 60) ; Convert to Hours
	
	; - Hours
	Time = Replace(RSet(BU_Long_ToIL(pDuration) Mod 24,2)," ", "0") + ":" + Time
	BU_Long_DivI(pDuration, 24) ; Convert to Days
	
	; - Days
	Time = BU_Long_ToIL(pDuration) + "d" + Time
	
	; Build Log Message
	Msg = "["+BU_Long_ToString(pDuration)+"]" + Msg
	
	L\EntriesOffset = (L\EntriesOffset + 1) Mod (LOGGER_HISTORY_SIZE + 1)
	L\Entries[L\EntriesOffset] = Msg
	WriteLine L\Stream, Msg
	DebugLog Msg
	
	BU_Long_Destroy(pDuration)
	BU_HighResolutionClock_Destroy(pNow)
End Function

Function Logger_History$(L.TLogger, Index%)
	; Loop Index in RingBuffer
	If (Index < 0) Then Index = 0
	If (Index > LOGGER_HISTORY_SIZE) Then Index = LOGGER_HISTORY_SIZE
	Index = L\EntriesOffset - Index
	If (Index < 0) Then Index = LOGGER_HISTORY_SIZE + Index
	
	Return L\Entries[Index]
End Function
;~IDEal Editor Parameters:
;~C#BlitzPlus