Include "Threading.bb"
Include "ThreadPool.bb"

Graphics3D 1280,720,32,2
SetBuffer(BackBuffer())
SeedRnd 0

Global Timer = CreateTimer(60)


Global ThreadTest_p = 0
Function ThreadTest(MMBank)
	If ThreadTest_p = 0 And MMBank = 0 Then
		ThreadTest_p = BP_GetFunctionPointer()
		Return
	EndIf
	
	Local Lock = PeekInt(MMBank, 0)
	Local Bnk =  PeekInt(MMBank, 4)
	If Mutex_Wait(Lock) = MUTEX_WAIT_OK Then
		PokeInt Bnk, 0, PeekInt(Bnk, 0) + 1
		Value = Value + 1
		Mutex_Release(Lock)
	EndIf
	
	Return
End Function:ThreadTest(0)

Global myThreadPool = ThreadPool_Create(GetEnv("NUMBER_OF_PROCESSORS"))
Global Value%
Global ValueLock% = Mutex_Create()



Local Final = CreateBank(4)
Local MM = CreateBank(8)
PokeInt MM, 0, ValueLock
PokeInt MM, 4, Final

Const WorkCount = 100000
For n = 1 To WorkCount
	ThreadPool_QueueTask(myThreadPool, ThreadTest_p, Int(MM))
Next

;BU_Mutex_Wait(ValueLock)
ThreadPool_Resume(myThreadPool)
Local LockIndex = 0
While Not KeyHit(1)
	Text 0, 0, "Tasks: " + ThreadPool_CountTasks(myThreadPool)
	Text 0,15, "  Pending: " + ThreadPool_CountPendingTasks(myThreadPool)
	Text 0,30, "  Executing: " + ThreadPool_CountExecutingTasks(myThreadPool)
	Text 0,60, "Result: " + PeekInt(Final, 0) + "/" + Value
	
	If ThreadPool_CountTasks(myThreadPool) <= 90000 And LockIndex = 0 Then
		LockIndex = LockIndex + 1
		ThreadPool_Suspend(myThreadPool)
	ElseIf ThreadPool_CountTasks(myThreadPool) <= 80000 And LockIndex = 1 Then
		LockIndex = LockIndex + 1
		ThreadPool_Suspend(myThreadPool)
	ElseIf ThreadPool_CountTasks(myThreadPool) <= 70000 And LockIndex = 2 Then
		LockIndex = LockIndex + 1
		ThreadPool_Suspend(myThreadPool)
	ElseIf ThreadPool_CountTasks(myThreadPool) <= 60000 And LockIndex = 3 Then
		LockIndex = LockIndex + 1
		ThreadPool_Suspend(myThreadPool)
	ElseIf ThreadPool_CountTasks(myThreadPool) <= 50000 And LockIndex = 4 Then
		LockIndex = LockIndex + 1
		ThreadPool_Suspend(myThreadPool)
	ElseIf ThreadPool_CountTasks(myThreadPool) <= 40000 And LockIndex = 5 Then
		LockIndex = LockIndex + 1
		ThreadPool_Suspend(myThreadPool)
	ElseIf ThreadPool_CountTasks(myThreadPool) <= 30000 And LockIndex = 6 Then
		LockIndex = LockIndex + 1
		ThreadPool_Suspend(myThreadPool)
	ElseIf ThreadPool_CountTasks(myThreadPool) <= 20000 And LockIndex = 7 Then
		LockIndex = LockIndex + 1
		ThreadPool_Suspend(myThreadPool)
	ElseIf ThreadPool_CountTasks(myThreadPool) <= 10000 And LockIndex = 8 Then
		LockIndex = LockIndex + 1
		ThreadPool_Suspend(myThreadPool)
	EndIf
	
	Flip 0: Cls
	
	If KeyHit(57) Then
		ThreadPool_Resume(myThreadPool)
	EndIf
	
	ThreadPool_Update(myThreadPool)
	WaitTimer(Timer)
Wend
Mutex_Destroy(ValueLock)
ThreadPool_Destroy(myThreadPool)

End
;~IDEal Editor Parameters:
;~C#Blitz3D