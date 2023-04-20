;----------------------------------------------------------------
;-- Userlib
;----------------------------------------------------------------
;.lib "User32.dll"
;InputEx_User32_FindWindow%(class$, title$):"FindWindowA"
;InputEx_User32_GetActiveWindow%():"GetActiveWindow"
;InputEx_User32_GetCursorPosition%(InputEx_Point*):"GetCursorPos"
;InputEx_User32_ScreenToClient%(hwnd%, InputEx_Point*):"ScreenToClient"
;InputEx_User32_MapVirtualKeyEx%(code%, mapType%, dwhkl%):"MapVirtualKeyExA"
;InputEx_User32_GetAsyncKeyState%(vkey%):"GetAsyncKeyState"
;
;.lib " "
;InputEx_Init()
;InputEx_Update()
;InputEx_VKeyTime%(VirtualKey%)
;InputEx_VKeyDownEx%(VirtualKey%)
;InputEx_VKeyDown%(VirtualKey%)
;InputEx_VKeyHitEx%(VirtualKey%)
;InputEx_VKeyHit%(VirtualKey%)
;InputEx_KeyTime%(ScanCode%)
;InputEx_KeyDownEx%(ScanCode%)
;InputEx_KeyDown%(ScanCode%)
;InputEx_KeyHitEx%(ScanCode%)
;InputEx_KeyHit%(ScanCode%)
;InputEx_MouseTime%(Button%)
;InputEx_MouseDownEx%(Button%)
;InputEx_MouseDown%(Button%)
;InputEx_MouseHitEx%(Button%)
;InputEx_MouseHit%(Button%)
;KeyTime%(Key%)
;KeyDownEx%(Key%)
;KeyHitEx%(Key%)
;MouseTime%(Button%)
;MouseDownEx%(Button%)
;MouseHitEx%(Button%)
;----------------------------------------------------------------

;----------------------------------------------------------------
;-- Constants
;----------------------------------------------------------------
;[Block] Virtual Scan Code
; Controls
Const KEY_ESCAPE = 8
Const KEY_TAB = 9

Const KEY_RETURN = 28
Const KEY_BACKSPACE = 14
Const KEY_CONTROL_LEFT  =  29, KEY_CONTROL_RIGHT = 157
Const KEY_SHIFT_LEFT    =  42, KEY_SHIFT_RIGHT   =  54
Const KEY_ALT_LEFT      =  56, KEY_ALT_RIGHT     = 184
Const KEY_WIN_LEFT		= 219, KEY_WIN_RIGHT	 = 220

; Function Keys
Const KEY_F1 = 59, KEY_F2 = 60, KEY_F3 = 61, KEY_F4 = 62
Const KEY_F5 = 63, KEY_F6 = 64, KEY_F7 = 65, KEY_F8 = 66
Const KEY_F9 = 67, KEY_F10= 68, KEY_F11= 69, KEY_F12= 70

; Lock Toggles
Const KEY_CAPSLOCK   = 58
Const KEY_NUMLOCK    = 69
Const KEY_SCROLLLOCK = 70

; Control Block
Const KEY_PRINT   = 183, KEY_PAUSE     = 197
Const KEY_INSERT  = 210, KEY_DELETE    = 211
Const KEY_HOME    = 199, KEY_END       = 207
Const KEY_PAGE_UP = 201, KEY_PAGE_DOWN = 209

; Application Keys
Const KEY_APP_CALCULATOR = 161
Const KEY_APP_MENU = 221
Const KEY_APP_COMPUTER = 235
Const KEY_APP_MEDIA = 237

; Media Keys
Const KEY_MEDIA_PLAYPAUSE = 162
Const KEY_MEDIA_STOP = 164
Const KEY_MEDIA_NEXT_TRACK = 153
Const KEY_MEDIA_MUTE = 160
Const KEY_MEDIA_VOLUME_DOWN = 174
Const KEY_MEDIA_VOLUME_UP = 176

; Internet Keys
Const KEY_INTERNET_HOME = 178
Const KEY_INTERNET_SEARCH = 229
Const KEY_INTERNET_FAVOURITES = 230
Const KEY_INTERNET_REFRESH = 231
Const KEY_INTERNET_STOP = 232
Const KEY_INTERNET_FORWARD = 233
Const KEY_INTERNET_BACK = 234
Const KEY_INTERNET_EMAIL = 236

; System Key
Const KEY_SYSTEM_POWER = 222
Const KEY_SYSTEM_SLEEP = 223
Const KEY_SYSTEM_WAKE = 227

; Cursor Keys
Const KEY_CURSOR_UP = 200
Const KEY_CURSOR_DOWN = 208
Const KEY_CURSOR_LEFT = 203
Const KEY_CURSOR_RIGHT = 205

; Numeric Keypad
Const KEY_NUM_1 = 79, KEY_NUM_2 = 80, KEY_NUM_3 = 81
Const KEY_NUM_4 = 75, KEY_NUM_5 = 76, KEY_NUM_6 = 77
Const KEY_NUM_7 = 71, KEY_NUM_8 = 72, KEY_NUM_9 = 73
Const KEY_NUM_0 = 82
Const KEY_NUM_MINUS  =  74, KEY_NUM_PLUS     =  78, KEY_NUM_COMMA =  83
Const KEY_NUM_DIVIDE = 181, KEY_NUM_MULTIPLY =  55, KEY_NUM_ENTER = 156

; Numbers
Const KEY_0 = 11, KEY_1 =  2, KEY_2 =  3, KEY_3 =  4, KEY_4 =  5
Const KEY_5 =  6, KEY_6 =  7, KEY_7 =  8, KEY_8 =  9, KEY_9 = 10

; Letters
Const KEY_A = 30, KEY_B = 48, KEY_C = 46, KEY_D = 32
Const KEY_E = 18, KEY_F = 33, KEY_G = 34, KEY_H = 35
Const KEY_I = 23, KEY_J = 36, KEY_K = 37, KEY_L = 38
Const KEY_M = 50, KEY_N = 49, KEY_O = 24, KEY_P = 25
Const KEY_Q = 16, KEY_R = 19, KEY_S = 31, KEY_T = 20
Const KEY_U = 22, KEY_V = 47, KEY_W = 17, KEY_X = 45
Const KEY_Y = 44, KEY_Z = 21

; Umlauts
Const KEY_AUmlaut = 40, KEY_APOSTROPHE = KEY_AUmlaut
Const KEY_OUmlaut = 39, KEY_SEMICOLON = KEY_OUmlaut
Const KEY_UUmlaut = 26, KEY_BRACKET = KEY_UUmlaut
Const KEY_SUmlaut = 12, KEY_HYPHEN = KEY_SUmlaut

; Punctuation
Const KEY_SPACE = 57
Const KEY_COMMA = 51, KEY_PERIOD = 52
Const KEY_MINUS = 53, KEY_PLUS   = 27, KEY_DASH = KEY_MINUS
Const KEY_TICK  = 13, KEY_BACKTICK = KEY_TICK
Const KEY_CIRMUMFLEX = 41, KEY_GUILLEMET = 86
Const KEY_OCTOTHORP  = 43, KEY_HASHTAG = KEY_OCTOTHORP, KEY_NUMBERSIGN = KEY_OCTOTHORP
;[End Block]
;[Block] Virtual Key Code

;
; Virtual Keys, Standard Set
;
Const VK_LBUTTON        = $01
Const VK_RBUTTON        = $02
Const VK_CANCEL         = $03
Const VK_MBUTTON        = $04    ; NOT contiguous with L & RBUTTON;

;#if(_WIN32_WINNT >= = $0500)
Const VK_XBUTTON1       = $05    ; NOT contiguous with L & RBUTTON;
Const VK_XBUTTON2       = $06    ; NOT contiguous with L & RBUTTON;
;#endif ; _WIN32_WINNT >= = $0500;

;
; = $07 : unassigned
;

Const VK_BACK           = $08
Const VK_TAB            = $09

;
; = $0A - = $0B : reserved
;

Const VK_CLEAR          = $0C
Const VK_RETURN         = $0D

Const VK_SHIFT          = $10
Const VK_CONTROL        = $11
Const VK_MENU           = $12
Const VK_PAUSE          = $13
Const VK_CAPITAL        = $14

Const VK_KANA           = $15
Const VK_HANGEUL        = $15  ; old name - should be here for compatibility;
Const VK_HANGUL         = $15
Const VK_JUNJA          = $17
Const VK_FINAL          = $18
Const VK_HANJA          = $19
Const VK_KANJI          = $19

Const VK_ESCAPE         = $1B

Const VK_CONVERT        = $1C
Const VK_NONCONVERT     = $1D
Const VK_ACCEPT         = $1E
Const VK_MODECHANGE     = $1F

Const VK_SPACE          = $20
Const VK_PRIOR          = $21
Const VK_NEXT           = $22
Const VK_END            = $23
Const VK_HOME           = $24
Const VK_LEFT           = $25
Const VK_UP             = $26
Const VK_RIGHT          = $27
Const VK_DOWN           = $28
Const VK_SELECT         = $29
Const VK_PRINT          = $2A
Const VK_EXECUTE        = $2B
Const VK_SNAPSHOT       = $2C
Const VK_INSERT         = $2D
Const VK_DELETE         = $2E
Const VK_HELP           = $2F

;
; VK_0 - VK_9 are the same as ASCII '0' - '9' (= $30 - = $39)
; = $40 : unassigned
; VK_A - VK_Z are the same as ASCII 'A' - 'Z' (= $41 - = $5A)
;

Const VK_LWIN           = $5B
Const VK_RWIN           = $5C
Const VK_APPS           = $5D

;
; = $5E : reserved
;

Const VK_SLEEP          = $5F

Const VK_NUMPAD0        = $60
Const VK_NUMPAD1        = $61
Const VK_NUMPAD2        = $62
Const VK_NUMPAD3        = $63
Const VK_NUMPAD4        = $64
Const VK_NUMPAD5        = $65
Const VK_NUMPAD6        = $66
Const VK_NUMPAD7        = $67
Const VK_NUMPAD8        = $68
Const VK_NUMPAD9        = $69
Const VK_MULTIPLY       = $6A
Const VK_ADD            = $6B
Const VK_SEPARATOR      = $6C
Const VK_SUBTRACT       = $6D
Const VK_DECIMAL        = $6E
Const VK_DIVIDE         = $6F
Const VK_F1             = $70
Const VK_F2             = $71
Const VK_F3             = $72
Const VK_F4             = $73
Const VK_F5             = $74
Const VK_F6             = $75
Const VK_F7             = $76
Const VK_F8             = $77
Const VK_F9             = $78
Const VK_F10            = $79
Const VK_F11            = $7A
Const VK_F12            = $7B
Const VK_F13            = $7C
Const VK_F14            = $7D
Const VK_F15            = $7E
Const VK_F16            = $7F
Const VK_F17            = $80
Const VK_F18            = $81
Const VK_F19            = $82
Const VK_F20            = $83
Const VK_F21            = $84
Const VK_F22            = $85
Const VK_F23            = $86
Const VK_F24            = $87

;
; = $88 - = $8F : unassigned
;

Const VK_NUMLOCK        = $90
Const VK_SCROLL         = $91

;
; NEC PC-9800 kbd definitions
;
Const VK_OEM_NEC_EQUAL  = $92   ; '=' key on numpad

;
; Fujitsu/OASYS kbd definitions
;
Const VK_OEM_FJ_JISHO   = $92   ; 'Dictionary' key
Const VK_OEM_FJ_MASSHOU = $93   ; 'Unregister word' key
Const VK_OEM_FJ_TOUROKU = $94   ; 'Register word' key
Const VK_OEM_FJ_LOYA    = $95   ; 'Left OYAYUBI' key
Const VK_OEM_FJ_ROYA    = $96   ; 'Right OYAYUBI' key

;
; = $97 - = $9F : unassigned
;

;
; VK_L* & VK_R* - left and right Alt, Ctrl and Shift virtual keys.
; Used only as parameters to GetAsyncKeyState() and GetKeyState().
; No other API or message will distinguish left and right keys in this way.
;
Const VK_LSHIFT         = $A0
Const VK_RSHIFT         = $A1
Const VK_LCONTROL       = $A2
Const VK_RCONTROL       = $A3
Const VK_LMENU          = $A4
Const VK_RMENU          = $A5

;#if(_WIN32_WINNT >= = $0500)
Const VK_BROWSER_BACK        = $A6
Const VK_BROWSER_FORWARD     = $A7
Const VK_BROWSER_REFRESH     = $A8
Const VK_BROWSER_STOP        = $A9
Const VK_BROWSER_SEARCH      = $AA
Const VK_BROWSER_FAVORITES   = $AB
Const VK_BROWSER_HOME        = $AC

Const VK_VOLUME_MUTE         = $AD
Const VK_VOLUME_DOWN         = $AE
Const VK_VOLUME_UP           = $AF
Const VK_MEDIA_NEXT_TRACK    = $B0
Const VK_MEDIA_PREV_TRACK    = $B1
Const VK_MEDIA_STOP          = $B2
Const VK_MEDIA_PLAY_PAUSE    = $B3
Const VK_LAUNCH_MAIL         = $B4
Const VK_LAUNCH_MEDIA_SELECT = $B5
Const VK_LAUNCH_APP1         = $B6
Const VK_LAUNCH_APP2         = $B7

;#endif ; _WIN32_WINNT >= = $0500;

;
; = $B8 - = $B9 : reserved
;

Const VK_OEM_1          = $BA   ; ';:' for US
Const VK_OEM_PLUS       = $BB   ; '+' any country
Const VK_OEM_COMMA      = $BC   ; ',' any country
Const VK_OEM_MINUS      = $BD   ; '-' any country
Const VK_OEM_PERIOD     = $BE   ; '.' any country
Const VK_OEM_2          = $BF   ; '/?' for US
Const VK_OEM_3          = $C0   ; '`~' for US

;
; = $C1 - = $D7 : reserved
;

;
; = $D8 - = $DA : unassigned
;

Const VK_OEM_4          = $DB  ;  '[{' for US
Const VK_OEM_5          = $DC  ;  '\|' for US
Const VK_OEM_6          = $DD  ;  ']}' for US
Const VK_OEM_7          = $DE  ;  ''"' for US
Const VK_OEM_8          = $DF

;
; = $E0 : reserved
;

;
; Various extended or enhanced keyboards
;
Const VK_OEM_AX         = $E1  ;  'AX' key on Japanese AX kbd
Const VK_OEM_102        = $E2  ;  "<>" or "\|" on RT 102-key kbd.
Const VK_ICO_HELP       = $E3  ;  Help key on ICO
Const VK_ICO_00         = $E4  ;  00 key on ICO

;#if(WINVER >= = $0400)
Const VK_PROCESSKEY     = $E5
;#endif ; WINVER >= = $0400;

Const VK_ICO_CLEAR      = $E6


;#if(_WIN32_WINNT >= = $0500)
Const VK_PACKET         = $E7
;#endif ; _WIN32_WINNT >= = $0500;

;
; = $E8 : unassigned
;

;
; Nokia/Ericsson definitions
;
Const VK_OEM_RESET      = $E9
Const VK_OEM_JUMP       = $EA
Const VK_OEM_PA1        = $EB
Const VK_OEM_PA2        = $EC
Const VK_OEM_PA3        = $ED
Const VK_OEM_WSCTRL     = $EE
Const VK_OEM_CUSEL      = $EF
Const VK_OEM_ATTN       = $F0
Const VK_OEM_FINISH     = $F1
Const VK_OEM_COPY       = $F2
Const VK_OEM_AUTO       = $F3
Const VK_OEM_ENLW       = $F4
Const VK_OEM_BACKTAB    = $F5

Const VK_ATTN           = $F6
Const VK_CRSEL          = $F7
Const VK_EXSEL          = $F8
Const VK_EREOF          = $F9
Const VK_PLAY           = $FA
Const VK_ZOOM           = $FB
Const VK_NONAME         = $FC
Const VK_PA1            = $FD
Const VK_OEM_CLEAR      = $FE

;
; = $FF : reserved
;
;[End Block]

;----------------------------------------------------------------

;----------------------------------------------------------------
;-- Types
;----------------------------------------------------------------
Type InputEx_Point
	Field X,Y
End Type
;----------------------------------------------------------------

;----------------------------------------------------------------
;-- Globals
;----------------------------------------------------------------
Global InputEx_Window = SystemProperty("AppHWND"), InputEx_ForMe = True
Global InputEx_Mouse.InputEx_Point = New InputEx_Point
Global InputEx_LastMouse.InputEx_Point = New InputEx_Point
Global InputEx_MouseSpeed.InputEx_Point = New InputEx_Point
Global InputEx_Width = GraphicsWidth()
Global InputEx_Height = GraphicsHeight()
Global InputEx_Layout = InputEx_User32_LoadKeyboardLayout("00000409", $2)

Dim InputEx_State(256)
Dim InputEx_StateTime(256)
Dim InputEx_StateUpdates(256)
Dim InputEx_Hits(256)
Dim InputEx_VSCAsVK(256)
;----------------------------------------------------------------

;----------------------------------------------------------------
;-- Functions
;----------------------------------------------------------------
Function InputEx_Init()
;@desc: Call this when your program starts to allow InputEx to work.
	InputEx_Window = SystemProperty("AppHWND")
	InputEx_User32_GetCursorPosition(InputEx_Mouse)
	InputEx_User32_ScreenToClient(InputEx_Window, InputEx_Mouse)
	InputEx_ForMe = (InputEx_User32_GetActiveWindow() = InputEx_Window)
	
	For VSC = 0 To 255
		InputEx_VSCAsVK(VSC) = InputEx_User32_MapVirtualKeyEx(VSC, 1, InputEx_Layout)
		InputEx_State(VSC) = 0
		InputEx_StateTime(VSC) = 0
		InputEx_StateUpdates(VSC) = 0
		InputEx_Hits(VSC) = 0
	Next
End Function

Function InputEx_SetResolution(Width, Height)
	InputEx_Width = Width
	InputEx_Height = Height
End Function

Function InputEx_Update()
;@desc: Call this once per frame to update InputExs values.
	Local InputEx_StateNew
	Local InputEx_Time = MilliSecs()
	
	; Update Mouse Stuff
	Local inputEx_t_buf.InputEx_Point = InputEx_Mouse
	InputEx_Mouse = InputEx_LastMouse
	InputEx_LastMouse = inputEx_t_buf
	InputEx_User32_GetCursorPosition(InputEx_Mouse)
	InputEx_User32_ScreenToClient(InputEx_Window, InputEx_Mouse)
	InputEx_MouseSpeed\X = InputEx_LastMouse\X - InputEx_Mouse\X
	InputEx_MouseSpeed\Y = InputEx_LastMouse\Y - InputEx_Mouse\Y
	
	; Check if it's actually for this window (prevent accidental interaction).
	InputEx_ForMe = (InputEx_User32_GetActiveWindow() = InputEx_Window)
;	If ((InputEx_Mouse\X >= 0) And (InputEx_Mouse\Y >= 0) And (InputEx_Mouse\X < InputEx_Width) And (InputEx_Mouse\Y < InputEx_Height)) Then
;		InputEx_ForMe = True
;	Else
;		InputEx_ForMe = False
;	EndIf
	
	; Are those signals even for us?
	Local VK
	If InputEx_ForMe Then 
		For VK = 0 To 255
			InputEx_StateNew = (InputEx_User32_GetAsyncKeyState(VK) <> 0)
			
			; Generic Update Structure
			If (InputEx_StateNew = 1) And (InputEx_State(VK) = 0) Then
				InputEx_Hits(VK) = InputEx_Hits(VK) + 1 ; Register as new key hit.
				InputEx_State(VK) = 1 ; Set State to down.
				InputEx_StateUpdates(VK) = 0 ; Reset updatecount.
				InputEx_StateTime(VK) = InputEx_Time ; Set time at which the state changed.
			ElseIf (InputEx_StateNew = 0) And (InputEx_State(VK) = 1) Then
				InputEx_State(VK) = 0 ; Set State to up.
				InputEx_StateUpdates(VK) = 0 ; Reset Updatecount.
				InputEx_StateTime(VK) = InputEx_Time ; Set time at which the state changed.
			Else
				If (InputEx_State(VK) = 1) Then
					InputEx_StateUpdates(VK) = InputEx_StateUpdates(VK) + 1 ; Increase updatecount because button is down.
				Else
					InputEx_StateUpdates(VK) = InputEx_StateUpdates(VK) - 1 ; Decrease updatecount because button is up.
				EndIf
			EndIf
		Next
	Else ;No
		For VK = 0 To 255 ; Reset stuff
			InputEx_StateNew = (InputEx_User32_GetAsyncKeyState(VK) <> 0)
			
			InputEx_State(VK) = 0
			InputEx_StateTime(VK) = InputEx_Time
			InputEx_StateUpdates(VK) = 0
		Next
	EndIf
	;Some may ask why i didn't put the If into the loop, this is the answer:
	;If I put it outside the loop, it's one less task for the CPU to do for every iteration. Thus increasing speed.
	;If I put it inside the loop, it's one more task for the CPU to do for every iteration. Thus decreasing speed.
End Function

Function InputEx_VKeyTime(VirtualKey)
;@desc: This tells you when the last state of the key was recieved in milliseconds.
;@returns: Time in milliseconds when the state of the key was registered.
	Return InputEx_StateTime(VirtualKey)
End Function

Function InputEx_VKeyDownEx(VirtualKey)
;@desc: This tells you the amount of updates a key has been down for(positive) or the amount of updates a key has been up for(negative).
;@returns: Updates the key has been down for. 
	Return InputEx_StateUpdates(VirtualKey)
End Function

Function InputEx_VKeyDown(VirtualKey)
;@desc: This tells you if a key is down or not.
;@returns: The keys state.
	Return InputEx_State(VirtualKey)
End Function

Function InputEx_VKeyHitEx(VirtualKey, Reduce=1)
;@desc: This tells you the amount of hits a key has recieved, while reducing the amount by <Reduce>.
;@returns: How many times the key has been hit.
	Local Hits = InputEx_Hits(VirtualKey)
	InputEx_Hits(VirtualKey) = InputEx_Hits(VirtualKey) - Reduce
	Return Hits
End Function

Function InputEx_VKeyHit(VirtualKey)
;@desc: This tells you the amount of hits a key has recieved since the last call and setting the amount to zero.
;@returns: How many times the key has been hit.
	Local Hits = InputEx_Hits(VirtualKey)
	InputEx_Hits(VirtualKey) = 0
	Return Hits
End Function

Function InputEx_KeyTime(ScanCode)
;@desc: See [InputEx_VKeyTime].
	Return InputEx_VKeyTime(InputEx_VSCAsVK(ScanCode))
End Function

Function InputEx_KeyDownEx(ScanCode)
;@desc: See [InputEx_VKeyDownEx].
	Return InputEx_VKeyDownEx(InputEx_VSCAsVK(ScanCode))
End Function

Function InputEx_KeyDown(ScanCode)
;@desc: See [InputEx_VKeyDown].
	Return InputEx_VKeyDown(InputEx_VSCAsVK(ScanCode))
End Function

Function InputEx_KeyHitEx(ScanCode)
;@desc: See [InputEx_VKeyHitEx].
	Return InputEx_VKeyHitEx(InputEx_VSCAsVK(ScanCode))
End Function

Function InputEx_KeyHit(ScanCode)
;@desc: See [InputEx_VKeyHit].
	Return InputEx_VKeyHit(InputEx_VSCAsVK(ScanCode))
End Function

Function InputEx_MouseTime(Button)
;@desc: See [InputEx_VKeyTime].
	Select Button
		Case 1,2
			Return InputEx_VKeyTime(Button)
		Case 3,4,5
			Return InputEx_VKeyTime(Button+1)
	End Select
End Function

Function InputEx_MouseDownEx(Button)
;@desc: See [InputEx_VKeyDownEx].
	Select Button
		Case 1,2
			Return InputEx_VKeyDownEx(Button)
		Case 3,4,5
			Return InputEx_VKeyDownEx(Button+1)
	End Select
End Function

Function InputEx_MouseDown(Button)
;@desc: See [InputEx_VKeyDown].
	Select Button
		Case 1,2
			Return InputEx_VKeyDown(Button)
		Case 3,4,5
			Return InputEx_VKeyDown(Button+1)
	End Select
End Function

Function InputEx_MouseHitEx(Button)
;@desc: See [InputEx_VKeyHitEx].
	Select Button
		Case 1,2
			Return InputEx_VKeyHitEx(Button)
		Case 3,4,5
			Return InputEx_VKeyHitEx(Button+1)
	End Select
End Function

Function InputEx_MouseHit(Button)
;@desc: See [InputEx_VKeyHit].
	Select Button
		Case 1,2
			Return InputEx_VKeyHit(Button)
		Case 3,4,5
			Return InputEx_VKeyHit(Button+1)
	End Select
End Function

Function InputEx_MouseXSpeed()
	Return InputEx_MouseSpeed\X
End Function

Function InputEx_MouseYSpeed()
	Return InputEx_MouseSpeed\Y
End Function
;----------------------------------------------------------------

;----------------------------------------------------------------
;-- Helper Functions for ease of use.
;----------------------------------------------------------------
Function MouseTime(Button)
	Return InputEx_MouseTime(Button)
End Function

Function MouseDownEx(Button)
	Return InputEx_MouseDownEx(Button)
End Function

Function MouseDown(Button)
	Return InputEx_MouseDown(Button)
End Function

Function MouseHitEx(Button)
	Return InputEx_MouseHitEx(Button)
End Function

Function MouseHit(Button)
	Return InputEx_MouseHit(Button)
End Function

Function MouseXSpeed()
	Return InputEx_MouseXSpeed()
End Function

Function MouseYSpeed()
	Return InputEx_MouseYSpeed()
End Function

Function KeyTime(Key)
	Return InputEx_KeyTime(Key)
End Function

Function KeyDownEx(Key)
	Return InputEx_KeyDownEx(Key)
End Function

Function KeyDown(Key)
	Return InputEx_KeyDown(Key)
End Function

Function KeyHitEx(Key)
	Return InputEx_KeyHitEx(Key)
End Function

Function KeyHit(Key)
	Return InputEx_KeyHit(Key)
End Function
;----------------------------------------------------------------

;----------------------------------------------------------------
;-- Example
;----------------------------------------------------------------
;Graphics3D 400,300,32,2
;SetBuffer BackBuffer()
;;InputEx_User32_ShowWindow(SystemProperty("AppHWND"), 1)
;
;Local Behaviour
;
;InputEx_Init()
;While Not KeyDown(1)
;	InputEx_Update()
;	
;	Cls
;	
;	If KeyHit(2) Then Behaviour = 0
;	If KeyHit(3) Then Behaviour = 1
;	
;	Local X, y, VK$ = ""
;	Select Behaviour
;		Case 0
;			Color 255,204,204
;			Text 0, 0,"Behaviour: Normal (Press 2 to change to 'Extended')"
;;			Text 0,15,"Mouse L:  "+MouseDown(1)+" "+MouseTime(1)
;;			Text 0,30,"Mouse R:  "+MouseDown(2)+" "+MouseTime(2)
;;			Text 0,45,"Mouse M:  "+MouseDown(3)+" "+MouseTime(3)
;;			Text 0,60,"Mouse X1: "+MouseDown(4)+" "+MouseTime(4)
;;			Text 0,75,"Mouse X2: "+MouseDown(5)+" "+MouseTime(5)
;			
;			For I = 0 To 255
;				X = (I Mod 20) * 20
;				y = Floor(I / 20) * 20
;				
;				Text X, y+20, KeyDown(I)+""+InputEx_VKeyDown(I)
;				If InputEx_VKeyDown(I)
;					VK = VK + "(VK)" + I
;				EndIf
;				If InputEx_KeyDown(I)
;					VK = VK + "(VSC)" + I
;				EndIf
;			Next
;		Case 1
;			Color 204,204,255
;			Text 0, 0,"Behaviour: Extended (Press 1 to change to 'Normal')"
;;			Text 0,15,"Mouse L:  "+MouseDownEx(1)+" "+MouseTime(1)
;;			Text 0,30,"Mouse R:  "+MouseDownEx(2)+" "+MouseTime(2)
;;			Text 0,45,"Mouse M:  "+MouseDownEx(3)+" "+MouseTime(3)
;;			Text 0,60,"Mouse X1: "+MouseDownEx(4)+" "+MouseTime(4)
;;			Text 0,75,"Mouse X2: "+MouseDownEx(5)+" "+MouseTime(5)
;			
;			For I = 0 To 255
;				X = (I Mod 5) * 20
;				y = Floor(I / 15) * 20
;				
;				Text X, y+20, KeyDownEx(I)+""+InputEx_VKeyDownEx(I)
;				If InputEx_VKeyDown(I)
;					VK = VK + "(VK)" + I
;				EndIf
;				If InputEx_KeyDown(I)
;					VK = VK + "(VSC)" + I
;				EndIf
;			Next
;	End Select
;	
;	Text 0, 285, VK
;	
;	Flip
;Wend
;
;End
;----------------------------------------------------------------
;~IDEal Editor Parameters:
;~F#28#8A#1B6#1C6#1CB#207#20D#213#219#221#229#22E#233#238#23D#242#24C#256#260#26A
;~F#274#278#280#284#288#28C#290#294#298#29C#2A0#2A4#2A8#2AC
;~C#Blitz3D