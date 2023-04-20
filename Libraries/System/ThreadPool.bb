;----------------------------------------------------------------
;! Constants
;----------------------------------------------------------------
Const THREADPOOL_DEBUG_MODE		= True							; True = Execute Single-Threaded, False = Execute Multi-Threaded. True is needed for the Blitz-Debugger to work.

Const z_Internal_WaitForObject_INFINITE = $FFFFFFFF
Const THREADPOOL_TASK_WAITTIME	= 5
Const THREADPOOL_EXIT_WAITTIME	= 50
Const THREADPOOL_MAXIMUM_TASKS	= 2147483647

Const THREADPOOL_FLAG_SHUTDOWN%	= $00000001

Const ETaskStatus_Pending		= 0
Const ETaskStatus_Executing		= 1
Const ETaskStatus_Finished		= 2

;----------------------------------------------------------------
;! Types
;----------------------------------------------------------------
Type ThreadPool
	Field Threads
	Field ThreadHandles ; Bank, Size: Threads * 4
	
	Field SignalSemaphore%
	
	Field z_Suspended
	Field z_Internal_LastTotalCount%
	Field z_Internal_LastPendingCount%
	Field z_Internal_LastExecutingCount%
End Type
Global z_Type_ThreadPool_Mutex

Type ThreadTask
	Field Pool.ThreadPool
	Field Pointer%
	Field PassedData%
	
	Field ETaskStatus%
End Type
Global z_Type_ThreadTask_Mutex

Type ThreadMemory
	Field Id%
	Field Thread
	Field Pool.ThreadPool
	
	Field z_Internal_Semaphore%
	Field z_Internal_Mutex%
	Field z_Internal_SignalFlags%
End Type
Global z_Type_ThreadMemory_Mutex

If Not THREADPOOL_DEBUG_MODE
	z_Type_ThreadPool_Mutex = Mutex_Create()
	z_Type_ThreadTask_Mutex = Mutex_Create()
	z_Type_ThreadMemory_Mutex = Mutex_Create()
EndIf

;----------------------------------------------------------------
;! Globals
;----------------------------------------------------------------
Global z_Internal_ThreadPoolLoop_p
Global z_Internal_ThreadPoolSignal

;----------------------------------------------------------------
;! Functions
;----------------------------------------------------------------
Function ThreadPool_Create(ThreadCount%, bIsSuspended%=True)
	Local Thread
	
	; Fix Parameters
	If ThreadCount <= 0 Then ThreadCount = 1
	;If ThreadCount > 255 Then ThreadCount = 255 ; Artificial Limit
	
	;! ThreadPool Stuff
	If Not THREADPOOL_DEBUG_MODE
		Mutex_Lock(z_Type_ThreadPool_Mutex)
	EndIf
	
	; Create new Object
	Local TP.ThreadPool = New ThreadPool
	If THREADPOOL_DEBUG_MODE = False Then
		TP\SignalSemaphore = Semaphore_Create(0, THREADPOOL_MAXIMUM_TASKS)
	
		; Create Threads
		TP\Threads = ThreadCount
		TP\ThreadHandles = CreateBank(TP\Threads Shl 2)
		For Thread = 1 To TP\Threads
			Local TM.ThreadMemory = New ThreadMemory
			TM\Id = Thread
			TM\Pool = TP
			TM\z_Internal_Mutex = z_Type_ThreadTask_Mutex
			TM\z_Internal_Semaphore = TP\SignalSemaphore
			TM\Thread = Thread_Create(z_Internal_ThreadPoolLoop_p, Int(TM), bIsSuspended)
			
			PokeInt TP\ThreadHandles, (Thread - 1) Shl 2, Handle(TM)
		Next
	EndIf
	
	If Not THREADPOOL_DEBUG_MODE
		Mutex_Unlock(z_Type_ThreadPool_Mutex)
	EndIf
	
	Return Handle(TP)
End Function
Function ThreadPool_Destroy(hTP%)
	Local Thread
	
	;! Parameters
	If hTP = 0 Then Return -1
	
	;! Logic
	If Not THREADPOOL_DEBUG_MODE
		Mutex_Wait(z_Type_ThreadPool_Mutex)
	EndIf
	Local TP.ThreadPool = Object.ThreadPool(hTP)
	If Int(TP) = 0 Then 
		If Not THREADPOOL_DEBUG_MODE
			Mutex_Release(z_Type_ThreadPool_Mutex)
		EndIf
		Return -2
	EndIf
	
	If THREADPOOL_DEBUG_MODE = False Then
		For Thread = 1 To TP\Threads
			Local TM.ThreadMemory = Object.ThreadMemory(PeekInt(TP\ThreadHandles, (Thread - 1) Shl 2))
			If Int(TM) = 0 Then
				Mutex_Release(z_Type_ThreadPool_Mutex)
				Return -3
			EndIf
			
			; Terminate Thread
			TM\z_Internal_SignalFlags = TM\z_Internal_SignalFlags Or THREADPOOL_FLAG_SHUTDOWN
			Thread_Wait(TM\Thread, THREADPOOL_EXIT_WAITTIME)
			Thread_Destroy(TM\Thread)
			
			Delete TM
		Next
		FreeBank TP\ThreadHandles:TP\ThreadHandles = 0
		TP\Threads = 0
		
		Semaphore_Destroy(TP\SignalSemaphore)
	EndIf
	
	Delete TP
	If Not THREADPOOL_DEBUG_MODE
		Mutex_Release(z_Type_ThreadPool_Mutex)
	EndIf
End Function

Function ThreadPool_Update(hTP_deprecated_unused%=$DEADBEEF)
	If Not THREADPOOL_DEBUG_MODE
		Mutex_Wait(z_Type_ThreadPool_Mutex)
		Mutex_Wait(z_Type_ThreadTask_Mutex)
	EndIf
	Local TT.ThreadTask = Null
	For TT = Each ThreadTask
		Select TT\ETaskStatus
			Case ETaskStatus_Finished
				Delete TT
			Case ETaskStatus_Pending
				;Semaphore_Release(
		End Select
	Next
	If Not THREADPOOL_DEBUG_MODE
		Mutex_Release(z_Type_ThreadTask_Mutex)
		Mutex_Release(z_Type_ThreadPool_Mutex)
	EndIf
	
	; Debug Mode has no Threads, so everything executes in the Main Thread.
	If THREADPOOL_DEBUG_MODE Then
		Local ms = MilliSecs()
		For TT = Each ThreadTask
			If TT\ETaskStatus = ETaskStatus_Pending And TT\Pool\z_Suspended = False Then
				TT\ETaskStatus = ETaskStatus_Executing
				If TT\Pointer <> 0 Then
					; Return True from the task to be requeued
					If BP_CallFunctionII(TT\Pointer, TT\PassedData) Then
						TT\ETaskStatus = ETaskStatus_Pending
					Else
						ThreadPool_FinishTask(TT)
					EndIf
				Else
					ThreadPool_FinishTask(TT)
				EndIf
			EndIf
			
			If (MilliSecs() - ms) >= THREADPOOL_TASK_WAITTIME Then Exit
		Next
	EndIf
End Function

Function ThreadPool_Suspend(hTP%)
	Local TP.ThreadPool = ThreadPool_Get(hTP)
	Local Thread
	
	If THREADPOOL_DEBUG_MODE = False Then
		Mutex_Wait(z_Type_ThreadPool_Mutex)
		Mutex_Wait(z_Type_ThreadTask_Mutex)
		For Thread = 1 To TP\Threads
			Local TM.ThreadMemory = Object.ThreadMemory(PeekInt(TP\ThreadHandles, (Thread - 1) Shl 2))
			
			Thread_Suspend(TM\Thread)
		Next
		Mutex_Release(z_Type_ThreadTask_Mutex)
		Mutex_Release(z_Type_ThreadPool_Mutex)
	EndIf
	TP\z_Suspended = True
End Function
Function ThreadPool_Resume(hTP%)
	Local TP.ThreadPool = ThreadPool_Get(hTP)
	Local Thread
	
	If THREADPOOL_DEBUG_MODE = False Then
		Mutex_Wait(z_Type_ThreadPool_Mutex)
		Mutex_Wait(z_Type_ThreadTask_Mutex)
		For Thread = 1 To TP\Threads
			Local TM.ThreadMemory = Object.ThreadMemory(PeekInt(TP\ThreadHandles, (Thread - 1) Shl 2))
			
			Thread_Resume(TM\Thread)
		Next
		Mutex_Release(z_Type_ThreadTask_Mutex)
		Mutex_Release(z_Type_ThreadPool_Mutex)
	EndIf
	TP\z_Suspended = False
End Function

Function ThreadPool_Get.ThreadPool(hTP%)
	Local TP.ThreadPool
	
	If Not THREADPOOL_DEBUG_MODE
		Mutex_Wait(z_Type_ThreadPool_Mutex)
	EndIf
	TP = Object.ThreadPool(hTP)
	If Not THREADPOOL_DEBUG_MODE
		Mutex_Release(z_Type_ThreadPool_Mutex)
	EndIf
	
	Return TP
End Function

Function ThreadPool_CountTasks(hTP%)
	Local TP.ThreadPool = ThreadPool_Get(hTP)
	
	Local Res = MUTEX_WAIT_OK
	If Not THREADPOOL_DEBUG_MODE Then Res = Mutex_TryLockFor(z_Type_ThreadTask_Mutex, 1)
	If Res = MUTEX_WAIT_OK
		Local TT.ThreadTask = Null, Cnt
		For TT = Each ThreadTask
			If Int(TT\Pool) = Int(TP) Then
				Cnt = Cnt + 1
			EndIf
		Next
		If Not THREADPOOL_DEBUG_MODE Then Mutex_Release(z_Type_ThreadTask_Mutex)
		TP\z_Internal_LastTotalCount = Cnt
	Else
		Return TP\z_Internal_LastTotalCount
	EndIf
	
	Return Cnt
End Function
Function ThreadPool_CountPendingTasks(hTP%)
	Local TP.ThreadPool = ThreadPool_Get(hTP)
	
	Local Res = MUTEX_WAIT_OK
	If Not THREADPOOL_DEBUG_MODE Then Res = Mutex_TryLockFor(z_Type_ThreadTask_Mutex, 1)
	If Res = MUTEX_WAIT_OK
		Local TT.ThreadTask = Null, Cnt
		For TT = Each ThreadTask
			If Int(TT\Pool) = Int(TP) And TT\ETaskStatus = ETaskStatus_Pending Then
				Cnt = Cnt + 1
			EndIf
		Next
		If Not THREADPOOL_DEBUG_MODE Then Mutex_Release(z_Type_ThreadTask_Mutex)
		TP\z_Internal_LastPendingCount = Cnt
	Else
		Return TP\z_Internal_LastPendingCount
	EndIf
	
	Return Cnt
End Function
Function ThreadPool_CountExecutingTasks(hTP%)
	Local TP.ThreadPool = ThreadPool_Get(hTP)
	
	Local Res = MUTEX_WAIT_OK
	If Not THREADPOOL_DEBUG_MODE Then Res = Mutex_TryLockFor(z_Type_ThreadTask_Mutex, 1)
	If Res = MUTEX_WAIT_OK
		Local TT.ThreadTask = Null, Cnt
		For TT = Each ThreadTask
			If Int(TT\Pool) = Int(TP) And TT\ETaskStatus = ETaskStatus_Executing Then
				Cnt = Cnt + 1
			EndIf
		Next
		If Not THREADPOOL_DEBUG_MODE Then Mutex_Release(z_Type_ThreadTask_Mutex)
		TP\z_Internal_LastExecutingCount = Cnt
	Else
		Return TP\z_Internal_LastExecutingCount
	EndIf
	
	Return Cnt
End Function

Function ThreadPool_QueueTask(hTP%, Pointer%, Parameter%)
	Local TP.ThreadPool = ThreadPool_Get(hTP)
	
	If Not THREADPOOL_DEBUG_MODE Then Mutex_Lock(z_Type_ThreadTask_Mutex)
	Local TT.ThreadTask = New ThreadTask
	TT\Pool = TP
	TT\Pointer = Pointer
	TT\PassedData = Parameter
	TT\ETaskStatus = ETaskStatus_Pending
	If Not THREADPOOL_DEBUG_MODE Then Mutex_Release(z_Type_ThreadTask_Mutex)
	
	If Not THREADPOOL_DEBUG_MODE Then Semaphore_Release(TP\SignalSemaphore)
End Function
Function ThreadPool_DrainTask.ThreadTask(TP.ThreadPool)
	Local TT.ThreadTask = Null, rTT.ThreadTask
	
	If Not THREADPOOL_DEBUG_MODE Then Mutex_Wait(z_Type_ThreadTask_Mutex)
	For TT = Each ThreadTask
		If Int(TT\Pool) = Int(TP) Then
			If TT\ETaskStatus = ETaskStatus_Pending
				TT\ETaskStatus = ETaskStatus_Executing
				rTT = TT
				Exit
			EndIf
		EndIf
	Next
	If Not THREADPOOL_DEBUG_MODE Then Mutex_Release(z_Type_ThreadTask_Mutex)
	
	Return rTT
End Function
Function ThreadPool_FinishTask(TT.ThreadTask)
	TT\ETaskStatus = ETaskStatus_Finished
End Function

Function z_Internal_ThreadPoolLoop(TM.ThreadMemory)
	If THREADPOOL_DEBUG_MODE Then Return False
	;Mutex_Wait(z_Internal_ThreadTaskLock)
	If z_Internal_ThreadPoolLoop_p = 0 And Int(TM) = 0 Then
		z_Internal_ThreadPoolLoop_p = BP_GetFunctionPointer()
		Return True
	EndIf
	;Mutex_Release(z_Internal_ThreadTaskLock)
	
	; Work
	Repeat
		Local Res
		
		Res = Semaphore_Wait(TM\z_Internal_Semaphore, THREADPOOL_TASK_WAITTIME)
		If Res = SEMAPHORE_WAIT_OK Then
			Local TT.ThreadTask = ThreadPool_DrainTask(TM\Pool)
			If Int(TT) Then
				If TT\Pointer <> 0 Then
					; Return True from the task to be requeued
					If BP_CallFunctionII(TT\Pointer, TT\PassedData) Then
						Mutex_Lock(z_Type_ThreadTask_Mutex)
						TT\ETaskStatus = ETaskStatus_Pending
						Semaphore_Release(TM\Pool\SignalSemaphore)
						Mutex_Release(z_Type_ThreadTask_Mutex)
					Else
						ThreadPool_FinishTask(TT)
					EndIf
				Else
					ThreadPool_FinishTask(TT)
				EndIf
			EndIf
		EndIf
		
		; Shutdown if requested
	Until (TM\z_Internal_SignalFlags And THREADPOOL_FLAG_SHUTDOWN) <> 0
	
	BU_Thread_Exit(0)
	Return 0
End Function:z_Internal_ThreadPoolLoop(Null)
;~IDEal Editor Parameters:
;~F#C0#D1#E3#F1#105#119#12E#13B#14C
;~C#Blitz3D