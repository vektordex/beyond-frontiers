;	BlitzUtility - Expanding the normal Blitz functionality.
;	Copyright (C) 2015 Xaymar (Michael Fabian Dirks)
;
;	This program is free software: you can redistribute it and/or modify
;	it under the terms of the GNU Lesser General Public License as
;	published by the Free Software Foundation, either version 3 of the 
;	License, or (at your option) any later version.
;
;	This program is distributed in the hope that it will be useful,
;	but WITHOUT ANY WARRANTY; without even the implied warranty of
;	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;	GNU General Public License for more details.
;
;	You should have received a copy of the GNU Lesser General Public License
;	along with this program.  If not, see <http:;www.gnu.org/licenses/>.

; Utility ----------------------------------------------------------------------
;-- Types
Const BU_TYPES_COMPARE_INEQUAL								= 0
Const BU_TYPES_COMPARE_EQUAL								= 1
Const BU_TYPES_COMPARE_SMALLER								= 2
Const BU_TYPES_COMPARE_GREATER								= 4

;-- Mass Operation
; Constants: Supported Types
Const BU_MASSOP_TYPE_DOUBLE									= 0
Const BU_MASSOP_TYPE_LONGLONG								= 1
Const BU_MASSOP_TYPE_VECTOR2								= 10
Const BU_MASSOP_TYPE_VECTOR3								= 11
; Constants: Supported OpCodes
Const BU_MASSOP_OPCODE_CREATE								= 0
Const BU_MASSOP_OPCODE_DESTROY								= 1
Const BU_MASSOP_OPCODE_COPY									= 2
Const BU_MASSOP_OPCODE_TEMPCREATE							= 5
Const BU_MASSOP_OPCODE_TEMPDESTROY							= 6
Const BU_MASSOP_OPCODE_TEMPCLEANUP							= 7
Const BU_MASSOP_OPCODE_SET									= 10
Const BU_MASSOP_OPCODE_ADD									= 11
Const BU_MASSOP_OPCODE_SUB									= 12
Const BU_MASSOP_OPCODE_MUL									= 13
Const BU_MASSOP_OPCODE_DIV									= 14
Const BU_MASSOP_OPCODE_COMPARE								= 15
; GoTo
;	leftOperand: new massop index
Const BU_MASSOP_OPCODE_GOTO									= 249
; SetOpCode
;	leftOperand: target massop index
;	rightOperand: new OpCode
Const BU_MASSOP_OPCODE_SETOPCODE							= 250
; CopyLeft
;	leftOperand: source massop index
;	rightOperand: target massop index
;	result: target position (0/leftOperand, 1/rightOperand, 2/result)
Const BU_MASSOP_OPCODE_COPYLEFT								= 251
; CopyRight
;	leftOperand: source massop index
;	rightOperand: target massop index
;	result: target position (0/leftOperand, 1/rightOperand, 2/result)
Const BU_MASSOP_OPCODE_COPYRIGHT							= 252
; CopyOut
;	leftOperand: source massop index
;	rightOperand: target massop index
;	result: target position (0/leftOperand, 1/rightOperand, 2/result)
Const BU_MASSOP_OPCODE_COPYOUT								= 253
; If - Compares the result of <leftOperand> with the result of <rightOperand>, 
;  skipping over <result> instructions if the results match.
;	leftOperand: source massop index
;	rightOperand: target massop index
;	result: amount to skip
Const BU_MASSOP_OPCODE_IF									= 254
; IfValue - Compares the result of <leftOperand> with <rightOperand>, skipping
;  over <result> instructions if the results match.
;	leftOperand: source massop index
;	rightOperand: target massop index
;	result: amount to skip
Const BU_MASSOP_OPCODE_IFVALUE								= 255

; Helpers ----------------------------------------------------------------------
Type BU_Point
	Field X,Y
End Type

Type BU_Rectangle
	Field X,Y,X2,Y2
End Type

Function BU_Helper_Window_LockPointer(HWND% = 0)
	If HWND = 0 Then
		BU_User32_ClipCursorEx(0)
	Else
		Local tRect.BU_Rectangle = New BU_Rectangle
		
		; Retrieve Information about the selected window.
		BU_User32_GetWindowRect(HWND, tRect)
		
		; Correct Rectangle to be inside the selected Window.
		tRect\X2=tRect\X2-1
		tRect\Y2=tRect\Y2-1
		
		; Update clipping area.
		BU_User32_ClipCursor(tRect)
		
		Delete tRect
	EndIf
End Function

Function BU_Helper_Window_LockPointerAuto(HWND=0)
	If HWND = 0 Then HWND = SystemProperty("AppHwnd")
	
	If BU_User32_GetActiveWindow() = HWND Then
		BU_Helper_Window_LockPointer(HWND)
	Else
		BU_Helper_Window_LockPointer(0)
	EndIf
End Function

Function BU_Helper_Window_MakeBorderless(HWND=0)
	If HWND = 0 Then HWND = SystemProperty("AppHwnd")
	
	; Create Storage
	Local tRect.BU_Rectangle = New BU_Rectangle
	
	; Client Information
	BU_User32_GetClientRect(HWND, tRect)
	Local clientX, clientY, clientW, clientH
	clientX = tRect\X
	clientY = tRect\Y
	clientW = tRect\X2 - clientX
	clientH = tRect\Y2 - clientY
	
	; Convert Client coordinates to screen position.
	Local tPoint.BU_Point = New BU_Point
	BU_User32_ClientToScreen(HWND, tPoint)
	Local realClientX, realClientY
	realClientX = tPoint\X
	realClientY = tPoint\Y
	Delete tPoint
	
	; Window Information
	BU_User32_GetWindowRect(HWND, tRect)
	Local windowX, windowY, windowW, windowH
	windowX = tRect\X
	windowY = tRect\Y
	windowW = tRect\X2 - windowX
	windowH = tRect\Y2 - windowY
	
	; Delete Storage
	Delete tRect
	
	; Calculate new position and size.
	Local newX, newY, newW, newH
	newX = windowX + (realClientX - windowX)
	newY = windowY + (realClientY - windowY)
	newW = clientW
	newH = clientH
	
	; Apply changes
	BU_User32_SetWindowLong HWND, -16, $10000000
	BU_User32_SetWindowLong HWND, -20, $02840020
	BU_User32_SetWindowPos HWND, 0, 0, 0, 0, 0, $1 Or $2 Or $4 Or $10 Or $40; Or $20 Or $40; Or $2000
	BU_User32_SetWindowPos HWND, 0, newX, newY, newW, newH, $4
End Function

Function BU_Helper_Window_Resize(W, H, HWND=0)
	If HWND = 0 Then HWND = SystemProperty("AppHwnd")
	
	; Create Storage
	Local tRect.BU_Rectangle = New BU_Rectangle
	
	; Window Information
	BU_User32_GetWindowRect(HWND, tRect)
	Local windowX, windowY, windowW, windowH
	windowX = tRect\X
	windowY = tRect\Y
	windowW = tRect\X2 - windowX
	windowH = tRect\Y2 - windowY
	
	; Delete Storage
	Delete tRect
	
	; Apply changes
	BU_User32_SetWindowLong HWND, -16, $10000000
	BU_User32_SetWindowLong HWND, -20, $02840020
	BU_User32_SetWindowPos HWND, 0, 0, 0, 0, 0, $1 Or $2 Or $4 Or $10 Or $40; Or $20 Or $40; Or $2000
	BU_User32_SetWindowPos HWND, 0, windowX, windowY, W, H, $4
End Function

Function BU_Helper_Window_Center(HWND=0, Monitor=0)
	If HWND = 0 Then HWND = SystemProperty("AppHwnd")
	
	Local displayEnumerator = BU_DisplayEnumerator_Create()
	Local displayAmount = BU_DisplayEnumerator_Enumerate(displayEnumerator)
	If Monitor >= displayAmount Then Monitor = displayAmount - 1
	If Monitor < 0 Then Monitor = 0
	
	; Get Display Information
	Local displayRectangle.BU_Rectangle = New BU_Rectangle
	BU_DisplayEnumerator_Retrieve displayEnumerator, Monitor, displayRectangle
	BU_DisplayEnumerator_Destroy displayEnumerator
	Local displayX, displayY, displayW, displayH
	displayX = displayRectangle\X
	displayY = displayRectangle\Y
	displayW = displayRectangle\X2 - displayRectangle\X
	displayH = displayRectangle\Y2 - displayRectangle\Y
	Delete displayRectangle
	
	; Get Window Information
	Local windowRectangle.BU_Rectangle = New BU_Rectangle
	BU_User32_GetWindowRect(HWND, windowRectangle)
	Local windowW%, windowH%
	windowW = windowRectangle\X2 - windowRectangle\X
	windowH = windowRectangle\Y2 - windowRectangle\Y
	Delete windowRectangle
	
	; Calculate new position and size.
	Local newX, newY, newW, newH
	newX = displayX + (displayW/2 - windowW/2)
	newY = displayY + (displayH/2 - windowH/2)
	newW = windowW
	newH = windowH
	
	; Apply changes
	BU_User32_SetWindowPos HWND, 0, newX, newY, newW, newH, $1 + $4 + $10 + $20 + $40 
End Function

Function BU_Helper_Window_Fill(HWND=0, Monitor=0)
	If HWND = 0 Then HWND = SystemProperty("AppHwnd")
	
	Local displayEnumerator = BU_DisplayEnumerator_Create()
	Local displayAmount = BU_DisplayEnumerator_Enumerate(displayEnumerator)
	If Monitor >= displayAmount Then Monitor = displayAmount - 1
	If Monitor < 0 Then Monitor = 0
	
	; Get Display Information
	Local displayRectangle.BU_Rectangle = New BU_Rectangle
	BU_DisplayEnumerator_Retrieve displayEnumerator, Monitor, displayRectangle
	BU_DisplayEnumerator_Destroy displayEnumerator
	Local displayX, displayY, displayW, displayH
	displayX = displayRectangle\X
	displayY = displayRectangle\Y
	displayW = displayRectangle\X2 - displayRectangle\X
	displayH = displayRectangle\Y2 - displayRectangle\Y
	Delete displayRectangle
	
	; Calculate new position and size.
	Local newX, newY, newW, newH
	newX = displayX
	newY = displayY
	newW = displayW
	newH = displayH
	
	; Apply changes
	BU_User32_SetWindowPos HWND, 0, newX, newY, newW, newH, $4 + $10 + $20 + $40 
End Function

; Database ---------------------------------------------------------------------
;-- SQLite3
;[Block] Return Codes / Error Codes
; Many SQLite functions return an integer result code from the set shown
; here in order to indicate success or failure.
;
; New error codes may be added in future versions of SQLite.
Const BU_SQLITE_OK%											= 0   ; Successful result
Const BU_SQLITE_ERROR%										= 1   ; SQL error or missing database
Const BU_SQLITE_INTERNAL%									= 2   ; Internal logic error in SQLite
Const BU_SQLITE_PERM%										= 3   ; Access permission denied
Const BU_SQLITE_ABORT%										= 4   ; Callback routine requested an abort
Const BU_SQLITE_BUSY%										= 5   ; The database file is locked
Const BU_SQLITE_LOCKED%										= 6   ; A table in the database is locked
Const BU_SQLITE_NOMEM%										= 7   ; A malloc() failed
Const BU_SQLITE_READONLY%									= 8   ; Attempt to write a readonly database
Const BU_SQLITE_INTERRUPT%									= 9   ; Operation terminated by SQLite3_Interrupt()
Const BU_SQLITE_IOERR%										= 10   ; Some kind of disk I/O error occurred
Const BU_SQLITE_CORRUPT%									= 11   ; The database disk image is malformed
Const BU_SQLITE_NOTFOUND%									= 12   ; Unknown opcode in sqlite3_file_control()
Const BU_SQLITE_FULL%										= 13   ; Insertion failed because database is full
Const BU_SQLITE_CANTOPEN%									= 14   ; Unable to open the database file
Const BU_SQLITE_PROTOCOL%									= 15   ; Database lock protocol error
Const BU_SQLITE_EMPTY%										= 16   ; Database is empty
Const BU_SQLITE_SCHEMA%										= 17   ; The database schema changed
Const BU_SQLITE_TOOBIG%										= 18   ; String or BLOB exceeds size limit
Const BU_SQLITE_CONSTRAINT%									= 19   ; Abort due to constraint violation
Const BU_SQLITE_MISMATCH%									= 20   ; Data type mismatch
Const BU_SQLITE_MISUSE%										= 21   ; Library used incorrectly
Const BU_SQLITE_NOLFS%										= 22   ; Uses OS features not supported on host
Const BU_SQLITE_AUTH%										= 23   ; Authorization denied
Const BU_SQLITE_FORMAT%										= 24   ; Auxiliary database format error
Const BU_SQLITE_RANGE%										= 25   ; 2nd parameter to sqlite3_bind out of range
Const BU_SQLITE_NOTADB%										= 26   ; File opened that is not a database file
Const BU_SQLITE_NOTICE%										= 27   ; Notifications from sqlite3_log()
Const BU_SQLITE_WARNING%									= 28   ; Warnings from sqlite3_log()
Const BU_SQLITE_ROW%										= 100  ; sqlite3_step() has another row ready
Const BU_SQLITE_DONE%										= 101  ; sqlite3_step() has finished executing
;[End Block]

;[Block] Extended Result Codes
; In its default configuration, SQLite API routines return one of 30 integer
; [result codes].  However, experience has shown that many of
; these result codes are too coarse-grained.  They do not provide as
; much information about problems as programmers might like.  In an effort to
; address this, newer versions of SQLite (version 3.3.8 and later) include
; support for additional result codes that provide more detailed information
; about errors. These [extended result codes] are enabled or disabled
; on a per database connection basis using the [sqlite3_extended_result_codes()]
; API. Or, the extended code for the most recent error can be obtained using
; [sqlite3_extended_errcode()].
Const BU_SQLITE_IOERR_READ									= (BU_SQLITE_IOERR Or (1 Shl 8))
Const BU_SQLITE_IOERR_SHORT_READ							= (BU_SQLITE_IOERR Or (2 Shl 8))
Const BU_SQLITE_IOERR_WRITE									= (BU_SQLITE_IOERR Or (3 Shl 8))
Const BU_SQLITE_IOERR_FSYNC									= (BU_SQLITE_IOERR Or (4 Shl 8))
Const BU_SQLITE_IOERR_DIR_FSYNC								= (BU_SQLITE_IOERR Or (5 Shl 8))
Const BU_SQLITE_IOERR_TRUNCATE								= (BU_SQLITE_IOERR Or (6 Shl 8))
Const BU_SQLITE_IOERR_FSTAT									= (BU_SQLITE_IOERR Or (7 Shl 8))
Const BU_SQLITE_IOERR_UNLOCK								= (BU_SQLITE_IOERR Or (8 Shl 8))
Const BU_SQLITE_IOERR_RDLOCK								= (BU_SQLITE_IOERR Or (9 Shl 8))
Const BU_SQLITE_IOERR_DELETE								= (BU_SQLITE_IOERR Or (10 Shl 8))
Const BU_SQLITE_IOERR_BLOCKED								= (BU_SQLITE_IOERR Or (11 Shl 8))
Const BU_SQLITE_IOERR_NOMEM									= (BU_SQLITE_IOERR Or (12 Shl 8))
Const BU_SQLITE_IOERR_ACCESS								= (BU_SQLITE_IOERR Or (13 Shl 8))
Const BU_SQLITE_IOERR_CHECKRESERVEDLOCK						= (BU_SQLITE_IOERR Or (14 Shl 8))
Const BU_SQLITE_IOERR_LOCK									= (BU_SQLITE_IOERR Or (15 Shl 8))
Const BU_SQLITE_IOERR_CLOSE									= (BU_SQLITE_IOERR Or (16 Shl 8))
Const BU_SQLITE_IOERR_DIR_CLOSE								= (BU_SQLITE_IOERR Or (17 Shl 8))
Const BU_SQLITE_IOERR_SHMOPEN								= (BU_SQLITE_IOERR Or (18 Shl 8))
Const BU_SQLITE_IOERR_SHMSIZE								= (BU_SQLITE_IOERR Or (19 Shl 8))
Const BU_SQLITE_IOERR_SHMLOCK								= (BU_SQLITE_IOERR Or (20 Shl 8))
Const BU_SQLITE_IOERR_SHMMAP								= (BU_SQLITE_IOERR Or (21 Shl 8))
Const BU_SQLITE_IOERR_SEEK									= (BU_SQLITE_IOERR Or (22 Shl 8))
Const BU_SQLITE_IOERR_DELETE_NOENT							= (BU_SQLITE_IOERR Or (23 Shl 8))
Const BU_SQLITE_IOERR_MMAP									= (BU_SQLITE_IOERR Or (24 Shl 8))
Const BU_SQLITE_IOERR_GETTEMPPATH							= (BU_SQLITE_IOERR Or (25 Shl 8))
Const BU_SQLITE_IOERR_CONVPATH								= (BU_SQLITE_IOERR Or (26 Shl 8))
Const BU_SQLITE_IOERR_VNODE									= (BU_SQLITE_IOERR Or (27 Shl 8))
Const BU_SQLITE_IOERR_AUTH									= (BU_SQLITE_IOERR Or (28 Shl 8))
Const BU_SQLITE_LOCKED_SHAREDCACHE							= (BU_SQLITE_LOCKED Or  (1 Shl 8))
Const BU_SQLITE_BUSY_RECOVERY								= (BU_SQLITE_BUSY   Or  (1 Shl 8))
Const BU_SQLITE_BUSY_SNAPSHOT								= (BU_SQLITE_BUSY   Or  (2 Shl 8))
Const BU_SQLITE_CANTOPEN_NOTEMPDIR							= (BU_SQLITE_CANTOPEN Or (1 Shl 8))
Const BU_SQLITE_CANTOPEN_ISDIR								= (BU_SQLITE_CANTOPEN Or (2 Shl 8))
Const BU_SQLITE_CANTOPEN_FULLPATH							= (BU_SQLITE_CANTOPEN Or (3 Shl 8))
Const BU_SQLITE_CANTOPEN_CONVPATH							= (BU_SQLITE_CANTOPEN Or (4 Shl 8))
Const BU_SQLITE_CORRUPT_VTAB								= (BU_SQLITE_CORRUPT Or (1 Shl 8))
Const BU_SQLITE_READONLY_RECOVERY							= (BU_SQLITE_READONLY Or (1 Shl 8))
Const BU_SQLITE_READONLY_CANTLOCK							= (BU_SQLITE_READONLY Or (2 Shl 8))
Const BU_SQLITE_READONLY_ROLLBACK							= (BU_SQLITE_READONLY Or (3 Shl 8))
Const BU_SQLITE_READONLY_DBMOVED							= (BU_SQLITE_READONLY Or (4 Shl 8))
Const BU_SQLITE_ABORT_ROLLBACK								= (BU_SQLITE_ABORT Or (2 Shl 8))
Const BU_SQLITE_CONSTRAINT_CHECK							= (BU_SQLITE_CONSTRAINT Or (1 Shl 8))
Const BU_SQLITE_CONSTRAINT_COMMITHOOK						= (BU_SQLITE_CONSTRAINT Or (2 Shl 8))
Const BU_SQLITE_CONSTRAINT_FOREIGNKEY						= (BU_SQLITE_CONSTRAINT Or (3 Shl 8))
Const BU_SQLITE_CONSTRAINT_FUNCTION							= (BU_SQLITE_CONSTRAINT Or (4 Shl 8))
Const BU_SQLITE_CONSTRAINT_NOTNULL							= (BU_SQLITE_CONSTRAINT Or (5 Shl 8))
Const BU_SQLITE_CONSTRAINT_PRIMARYKEY						= (BU_SQLITE_CONSTRAINT Or (6 Shl 8))
Const BU_SQLITE_CONSTRAINT_TRIGGER							= (BU_SQLITE_CONSTRAINT Or (7 Shl 8))
Const BU_SQLITE_CONSTRAINT_UNIQUE							= (BU_SQLITE_CONSTRAINT Or (8 Shl 8))
Const BU_SQLITE_CONSTRAINT_VTAB								= (BU_SQLITE_CONSTRAINT Or (9 Shl 8))
Const BU_SQLITE_CONSTRAINT_ROWID							= (BU_SQLITE_CONSTRAINT Or (10 Shl 8))
Const BU_SQLITE_NOTICE_RECOVER_WAL							= (BU_SQLITE_NOTICE Or (1 Shl 8))
Const BU_SQLITE_NOTICE_RECOVER_ROLLBACK						= (BU_SQLITE_NOTICE Or (2 Shl 8))
Const BU_SQLITE_WARNING_AUTOINDEX							= (BU_SQLITE_WARNING Or (1 Shl 8))
Const BU_SQLITE_AUTH_USER									= (BU_SQLITE_AUTH Or (1 Shl 8))
;[End Block]

;[Block] Flags For File Open Operations
; These bit values are intended for use in the
; 3rd parameter to the [sqlite3_open_v2()] interface.
Const BU_SQLITE_OPEN_READONLY								= $00000001  ;  Ok for sqlite3_open_v2() */
Const BU_SQLITE_OPEN_READWRITE								= $00000002  ;  Ok for sqlite3_open_v2() */
Const BU_SQLITE_OPEN_CREATE									= $00000004  ;  Ok for sqlite3_open_v2() */
Const BU_SQLITE_OPEN_DELETEONCLOSE							= $00000008  ;  VFS only */
Const BU_SQLITE_OPEN_EXCLUSIVE								= $00000010  ;  VFS only */
Const BU_SQLITE_OPEN_AUTOPROXY								= $00000020  ;  VFS only */
Const BU_SQLITE_OPEN_URI									= $00000040  ;  Ok for sqlite3_open_v2() */
Const BU_SQLITE_OPEN_MEMORY									= $00000080  ;  Ok for sqlite3_open_v2() */
Const BU_SQLITE_OPEN_MAIN_DB								= $00000100  ;  VFS only */
Const BU_SQLITE_OPEN_TEMP_DB								= $00000200  ;  VFS only */
Const BU_SQLITE_OPEN_TRANSIENT_DB							= $00000400  ;  VFS only */
Const BU_SQLITE_OPEN_MAIN_JOURNAL							= $00000800  ;  VFS only */
Const BU_SQLITE_OPEN_TEMP_JOURNAL							= $00001000  ;  VFS only */
Const BU_SQLITE_OPEN_SUBJOURNAL								= $00002000  ;  VFS only */
Const BU_SQLITE_OPEN_MASTER_JOURNAL							= $00004000  ;  VFS only */
Const BU_SQLITE_OPEN_NOMUTEX								= $00008000  ;  Ok for sqlite3_open_v2() */
Const BU_SQLITE_OPEN_FULLMUTEX								= $00010000  ;  Ok for sqlite3_open_v2() */
Const BU_SQLITE_OPEN_SHAREDCACHE							= $00020000  ;  Ok for sqlite3_open_v2() */
Const BU_SQLITE_OPEN_PRIVATECACHE							= $00040000  ;  Ok for sqlite3_open_v2() */
Const BU_SQLITE_OPEN_WAL									= $00080000  ;  VFS only */
;[End Block]

;[Block] Authorizer Return Codes
; The [sqlite3_set_authorizer | authorizer callback function] must
; return either [SQLITE_OK] or one of these two constants in order
; to signal SQLite whether or not the action is permitted.  See the
; [sqlite3_set_authorizer | authorizer documentation] for additional
; information.
;
; Note that SQLITE_IGNORE is also used as a [conflict resolution mode]
; returned from the [sqlite3_vtab_on_conflict()] interface.
Const BU_SQLITE_DENY										= 1 ; Abort the SQL statement with an error */
Const BU_SQLITE_IGNORE										= 2 ; Don't allow access, but don't generate an error */
;[End Block]

;[Block] Authorizer Action Codes
; The [sqlite3_set_authorizer()] interface registers a callback function
; that is invoked to authorize certain SQL statement actions.  The
; second parameter to the callback is an integer code that specifies
; what action is being authorized.  These are the integer action codes that
; the authorizer callback may be passed.
;
; These action code values signify what kind of operation is to be
; authorized.  The 3rd and 4th parameters to the authorization
; callback function will be parameters or NULL depending on which of these
; codes is used as the second parameter.  ^(The 5th parameter to the
; authorizer callback is the name of the database ("main", "temp",
; etc.) if applicable.)^  ^The 6th parameter to the authorizer callback
; is the name of the inner-most trigger or view that is responsible for
; the access attempt or NULL if this access attempt is directly from
; top-level SQL code.
; ;;;;;;;;;;;;;;;;;;;;; 3rd ;;;;;; 4th ;;;;;*/
Const BU_SQLITE_CREATE_INDEX         =  1   ;  Index Name      Table Name      */
Const BU_SQLITE_CREATE_TABLE         =  2   ;  Table Name      NULL            */
Const BU_SQLITE_CREATE_TEMP_INDEX    =  3   ;  Index Name      Table Name      */
Const BU_SQLITE_CREATE_TEMP_TABLE    =  4   ;  Table Name      NULL            */
Const BU_SQLITE_CREATE_TEMP_TRIGGER  =  5   ;  Trigger Name    Table Name      */
Const BU_SQLITE_CREATE_TEMP_VIEW     =  6   ;  View Name       NULL            */
Const BU_SQLITE_CREATE_TRIGGER       =  7   ;  Trigger Name    Table Name      */
Const BU_SQLITE_CREATE_VIEW          =  8   ;  View Name       NULL            */
Const BU_SQLITE_DELETE               =  9   ;  Table Name      NULL            */
Const BU_SQLITE_DROP_INDEX           = 10   ;  Index Name      Table Name      */
Const BU_SQLITE_DROP_TABLE           = 11   ;  Table Name      NULL            */
Const BU_SQLITE_DROP_TEMP_INDEX      = 12   ;  Index Name      Table Name      */
Const BU_SQLITE_DROP_TEMP_TABLE      = 13   ;  Table Name      NULL            */
Const BU_SQLITE_DROP_TEMP_TRIGGER    = 14   ;  Trigger Name    Table Name      */
Const BU_SQLITE_DROP_TEMP_VIEW       = 15   ;  View Name       NULL            */
Const BU_SQLITE_DROP_TRIGGER         = 16   ;  Trigger Name    Table Name      */
Const BU_SQLITE_DROP_VIEW            = 17   ;  View Name       NULL            */
Const BU_SQLITE_INSERT               = 18   ;  Table Name      NULL            */
Const BU_SQLITE_PRAGMA               = 19   ;  Pragma Name     1st arg or NULL */
Const BU_SQLITE_READ                 = 20   ;  Table Name      Column Name     */
Const BU_SQLITE_SELECT               = 21   ;  NULL            NULL            */
Const BU_SQLITE_TRANSACTION          = 22   ;  Operation       NULL            */
Const BU_SQLITE_UPDATE               = 23   ;  Table Name      Column Name     */
Const BU_SQLITE_ATTACH               = 24   ;  Filename        NULL            */
Const BU_SQLITE_DETACH               = 25   ;  Database Name   NULL            */
Const BU_SQLITE_ALTER_TABLE          = 26   ;  Database Name   Table Name      */
Const BU_SQLITE_REINDEX              = 27   ;  Index Name      NULL            */
Const BU_SQLITE_ANALYZE              = 28   ;  Table Name      NULL            */
Const BU_SQLITE_CREATE_VTABLE        = 29   ;  Table Name      Module Name     */
Const BU_SQLITE_DROP_VTABLE          = 30   ;  Table Name      Module Name     */
Const BU_SQLITE_FUNCTION             = 31   ;  NULL            Function Name   */
Const BU_SQLITE_SAVEPOINT            = 32   ;  Operation       Savepoint Name  */
Const BU_SQLITE_COPY                 =  0   ;  No longer used */
Const BU_SQLITE_RECURSIVE            = 33   ;  NULL            NULL            */
;[End Block]

;[Block] Run-Time Limit Categories
; KEYWORDS: {limit category} {*limit categories}
;
; These constants define various performance limits
; that can be lowered at run-time using [sqlite3_limit()].
; The synopsis of the meanings of the various limits is shown below.
; Additional information is available at [limits | Limits in SQLite].
;
; <dl>
; [[SQLITE_LIMIT_LENGTH]] ^(<dt>SQLITE_LIMIT_LENGTH</dt>
; <dd>The maximum size of any string or BLOB or table row, in bytes.<dd>)^
;
; [[SQLITE_LIMIT_SQL_LENGTH]] ^(<dt>SQLITE_LIMIT_SQL_LENGTH</dt>
; <dd>The maximum length of an SQL statement, in bytes.</dd>)^
;
; [[SQLITE_LIMIT_COLUMN]] ^(<dt>SQLITE_LIMIT_COLUMN</dt>
; <dd>The maximum number of columns in a table definition or in the
; result set of a [SELECT] or the maximum number of columns in an index
; or in an ORDER BY or GROUP BY clause.</dd>)^
;
; [[SQLITE_LIMIT_EXPR_DEPTH]] ^(<dt>SQLITE_LIMIT_EXPR_DEPTH</dt>
; <dd>The maximum depth of the parse tree on any expression.</dd>)^
;
; [[SQLITE_LIMIT_COMPOUND_SELECT]] ^(<dt>SQLITE_LIMIT_COMPOUND_SELECT</dt>
; <dd>The maximum number of terms in a compound SELECT statement.</dd>)^
;
; [[SQLITE_LIMIT_VDBE_OP]] ^(<dt>SQLITE_LIMIT_VDBE_OP</dt>
; <dd>The maximum number of instructions in a virtual machine program
; used to implement an SQL statement.  This limit is not currently
; enforced, though that might be added in some future release of
; SQLite.</dd>)^
;
; [[SQLITE_LIMIT_FUNCTION_ARG]] ^(<dt>SQLITE_LIMIT_FUNCTION_ARG</dt>
; <dd>The maximum number of arguments on a function.</dd>)^
;
; [[SQLITE_LIMIT_ATTACHED]] ^(<dt>SQLITE_LIMIT_ATTACHED</dt>
; <dd>The maximum number of [ATTACH | attached databases].)^</dd>
;
; [[SQLITE_LIMIT_LIKE_PATTERN_LENGTH]]
; ^(<dt>SQLITE_LIMIT_LIKE_PATTERN_LENGTH</dt>
; <dd>The maximum length of the pattern argument to the [LIKE] or
; [GLOB] operators.</dd>)^
;
; [[SQLITE_LIMIT_VARIABLE_NUMBER]]
; ^(<dt>SQLITE_LIMIT_VARIABLE_NUMBER</dt>
; <dd>The maximum index number of any [parameter] in an SQL statement.)^
;
; [[SQLITE_LIMIT_TRIGGER_DEPTH]] ^(<dt>SQLITE_LIMIT_TRIGGER_DEPTH</dt>
; <dd>The maximum depth of recursion for triggers.</dd>)^
;
; [[SQLITE_LIMIT_WORKER_THREADS]] ^(<dt>SQLITE_LIMIT_WORKER_THREADS</dt>
; <dd>The maximum number of auxiliary worker threads that a single
; [prepared statement] may start.</dd>)^
; </dl>
Const BU_SQLITE_LIMIT_LENGTH                   =  0
Const BU_SQLITE_LIMIT_SQL_LENGTH               =  1
Const BU_SQLITE_LIMIT_COLUMN                   =  2
Const BU_SQLITE_LIMIT_EXPR_DEPTH               =  3
Const BU_SQLITE_LIMIT_COMPOUND_SELECT          =  4
Const BU_SQLITE_LIMIT_VDBE_OP                  =  5
Const BU_SQLITE_LIMIT_FUNCTION_ARG             =  6
Const BU_SQLITE_LIMIT_ATTACHED                 =  7
Const BU_SQLITE_LIMIT_LIKE_PATTERN_LENGTH      =  8
Const BU_SQLITE_LIMIT_VARIABLE_NUMBER          =  9
Const BU_SQLITE_LIMIT_TRIGGER_DEPTH            = 10
Const BU_SQLITE_LIMIT_WORKER_THREADS           = 11
;[End Block]

;[Block] Column Type
Const BU_SQLITE_UNKNOWN											= 0
Const BU_SQLITE_INTEGER											= 1
Const BU_SQLITE_REAL											= 2
Const BU_SQLITE_FLOAT											= 2
Const BU_SQLITE_DOUBLE											= 2
Const BU_SQLITE_TEXT											= 3
Const BU_SQLITE_STRING											= 3
Const BU_SQLITE_BLOB											= 4
Const BU_SQLITE_BANK											= 4
Const BU_SQLITE_NULL											= 5
;[End Block]

; Math -------------------------------------------------------------------------
;-- Vector2
Type Vector2
	Field X#, Y#
End Type

Function Vector2_Create.Vector2(X#, Y#)
	Local vec.Vector2 = New Vector2
	vec\X = X
	vec\Y = Y
	Return vec
End Function

;-- Vector3
Type Vector3
	Field X#, Y#, Z#
End Type

Function Vector3_Create.Vector3(X#, Y#, Z#)
	Local vec.Vector3 = New Vector3
	vec\X = X
	vec\Y = Y
	vec\Z = Z
	Return vec
End Function


;~IDEal Editor Parameters:
;~C#Blitz3D