;----------------------------------------------------------------
;! Constants
;----------------------------------------------------------------
Const THREAD_STACKSIZE% = 0
Const THREAD_WAIT_OK = True
Const THREAD_WAIT_FAILED = False
Const THREAD_WAIT_TIMEDOUT = -1
Const MUTEX_WAIT_OK = True
Const MUTEX_WAIT_FAILED = False
Const MUTEX_WAIT_TIMEDOUT = -1
Const MUTEX_WAIT_ABANDONED = -2
Const SEMAPHORE_WAIT_OK = True
Const SEMAPHORE_WAIT_FAILED = False
Const SEMAPHORE_WAIT_TIMEDOUT = -1

Const TIME_INFINITE = $FFFFFFFF
;----------------------------------------------------------------
;! Types
;----------------------------------------------------------------

;----------------------------------------------------------------
;! Globals
;----------------------------------------------------------------

;----------------------------------------------------------------
;! Functions
;----------------------------------------------------------------
Function Thread_Create(pFunction%, pData%, bSuspended%=False)
	Return BU_Thread_Create(pFunction, pData, THREAD_STACKSIZE, bSuspended)
End Function

Function Thread_Destroy(pThread%)
	BU_Thread_Destroy(pThread)
End Function

Function Thread_Terminate(pThread%, ExitCode%=-1)
	BU_Thread_Terminate(pThread, ExitCode)
End Function

Function Thread_Exit(ExitCode%)
	BU_Thread_Exit(ExitCode)
End Function

Function Thread_GetExitCode(pThread%)
	BU_Thread_GetExitCode(pThread)
End Function

Function Thread_Wait(pThread%, Timeout%=TIME_INFINITE)
	Select BU_Thread_Wait(pThread, Timeout)
		Case 0
			Return THREAD_WAIT_OK
		Case $102
			Return THREAD_WAIT_TIMEDOUT
		Default
			Return THREAD_WAIT_FAILED
	End Select
End Function

Function Thread_Suspend(pThread%)
	BU_Thread_Suspend(pThread)
End Function

Function Thread_Resume(pThread%)
	BU_Thread_Resume(pThread)
End Function

Function Mutex_Create(bOwner%=False)
	Return Kernel32_CreateMutex2(0, bOwner, 0)
End Function

Function Mutex_Destroy(Mutex%)
	Kernel32_CloseHandle(Mutex)
End Function

Function Mutex_Lock(Mutex%)
	Return Mutex_TryLockFor(Mutex, TIME_INFINITE)
End Function

Function Mutex_TryLock(Mutex)
	Return Mutex_TryLockFor(Mutex, 0)
End Function

Function Mutex_TryLockFor(Mutex, Timeout%=TIME_INFINITE)
	Select Kernel32_WaitForSingleObject(Mutex, Timeout%)
		Case 0
			Return MUTEX_WAIT_OK
		Case $80
			Return MUTEX_WAIT_ABANDONED
		Case $102
			Return MUTEX_WAIT_TIMEDOUT
		Case $FFFFFFFF
			Return MUTEX_WAIT_FAILED
	End Select
End Function

Function Mutex_Unlock(Mutex%)
	Return Kernel32_ReleaseMutex(Mutex)
End Function

Function Mutex_Wait(Mutex%, Timeout%=TIME_INFINITE)
	Return Mutex_TryLockFor(Mutex, Timeout)
End Function

Function Mutex_Release(Mutex%)
	Return Mutex_Unlock(Mutex)
End Function

Function Semaphore_Create(InitialCount%, MaximumCount%)
	Return Kernel32_CreateSemaphore2(0, InitialCount, MaximumCount, 0)
End Function

Function Semaphore_Destroy(Semaphore%)
	Kernel32_CloseHandle(Semaphore)
End Function

Function Semaphore_Wait(Semaphore%, Timeout%=TIME_INFINITE)
	Select Kernel32_WaitForSingleObject(Semaphore, Timeout)
		Case 0
			Return SEMAPHORE_WAIT_OK
		Case $102
			Return SEMAPHORE_WAIT_TIMEDOUT
		Default
			Return SEMAPHORE_WAIT_FAILED
	End Select
End Function

Function Semaphore_Release(Semaphore%, Count%=1)
	Kernel32_ReleaseSemaphore(Semaphore, Count, 0)
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D