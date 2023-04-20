;----------------------------------------------------------------
;! Constants
;----------------------------------------------------------------
Const LOG_FATAL		= 0
Const LOG_ERROR		= 100
Const LOG_WARNING	= 200
Const LOG_INFO		= 300
Const LOG_CONFIG	= 400
Const LOG_DEBUG		= 500

;----------------------------------------------------------------
;! Globals
;----------------------------------------------------------------
Global LogHandler_Stream
Global LogHandler_Mutex

Global LogHandler_History

;----------------------------------------------------------------
;! Functions
;----------------------------------------------------------------
Function LogHandler_Initialize(Path$)
	If FileType(Path) <> 2 Then CreateDir(Path)
	
	Local tNow = BU_Time_Now()
	Local tNowS$ = BU_Time_Format(tNow, "%Y-%m-%d_%H-%M-%S.log")
	BU_Time_Destroy(tNow)
	
	Local logFile$ = Path + "\" + tNowS
	
	LogHandler_Stream = WriteFile(logFile)
	;LogHandler_Stream = BU_FileSystem_WriteFile(logFile)
	If LogHandler_Stream = 0 Then
		RuntimeError "Failed to create log file '" + Path + "\" + tNowS + "'."
	EndIf
	
	LogHandler_Mutex = Mutex_Create()
End Function

Function LogMessage(Level%, Message$)
	If LogHandler_Stream = 0 Then
		;RuntimeError "LogHandler: Message logged before initialization!"
	Else
		Mutex_Wait(LogHandler_Mutex)
		
		Local LevelStr$ = ""
		If Level >= LOG_DEBUG Then
			LevelStr = "DEBUG"
		ElseIf Level >= LOG_CONFIG Then
			LevelStr = "CONFIG"
		ElseIf Level >= LOG_INFO Then
			LevelStr = "INFO"
		ElseIf Level >= LOG_WARNING Then
			LevelStr = "WARNING"
		ElseIf Level >= LOG_ERROR Then
			LevelStr = "ERROR"
		Else
			LevelStr = "FATAL"
		EndIf
		
		Local tNow = BU_Time_Now()
		Local TimeStr$ = BU_Time_Format(tNow, "%Y.%m.%dT%H:%M:%S")
		BU_Time_Destroy(tNow)
		
		Local lMsg$ = "["+TimeStr+"] ["+LevelStr+"] " + Message
		WriteLine LogHandler_Stream, lMsg
;		For I = 1 To Len(lMsg)
;			Local C = Asc(Mid(lMsg, I, 1))
;			BU_FileSystem_WriteByte LogHandler_Stream, C
;		Next
;		BU_FileSystem_FlushFile LogHandler_Stream
		
		BU_Kernel32_FlushFileBuffers(LogHandler_Stream)
		Mutex_Release(LogHandler_Mutex)
	EndIf
End Function
;~IDEal Editor Parameters:
;~F#15#27
;~C#Blitz3D