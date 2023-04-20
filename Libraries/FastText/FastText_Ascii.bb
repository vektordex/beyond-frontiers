
; Encoding consts
Const FT_ASCII = 0
Const FT_UNICODE = 1

; Smooth const (LoadFont function)
Const FT_DEFAULT = 0
Const FT_NONANTIALIASED = 1
Const FT_ANTIALIASED = 2
Const FT_CLEARTYPE = 3

; Horisontal align consts (X axis)
Const FT_NONE = 0
Const FT_LEFT = 0
Const FT_CENTER = 1
Const FT_RIGHT = 2

; Vertical align consts (Y axis)
Const FT_TOP = 0
Const FT_MID = 1
Const FT_MIDDLE = 1
Const FT_BOTTOM = 2
Const FT_BASELINE = 3

; TextRect Formatting consts
Const FT_NOBREAK = 256
Const FT_NOCLIP = 512
Const FT_CALCRECT = 1024

Function LoadFont(name$="Tahoma", height=13, bold=0, italic=0, underline=0, angle#=0, smooth=FT_ANTIALIASED, encoding=FT_ASCII)
	Return LoadFont_(name,height,bold,italic,underline,angle,smooth,encoding)
End Function

Function SetFont(font)
	SetFont_ font
End Function

Function FreeFont(font)
	FreeFont_ font
End Function

Function FontWidth()
	Return FontWidth_()
End Function

Function FontHeight()
	Return FontHeight_()
End Function

Function Text(x, y, txt$, cx=FT_LEFT, cy=FT_TOP, encoding=FT_ASCII)
	Text_(x,y,txt,cx,cy,encoding)
End Function

Function TextRect(x, y, w, h, txt$, formatting=FT_LEFT, encoding=FT_ASCII)
	Return TextRect_(x,y,w,h,txt,formatting,encoding)
End Function

Function TextBackground(r%=-1, g%=-1, b%=-1)
	TextBackground_ r,g,b
End Function

Function StringWidth(txt$, encoding=FT_ASCII)
	Return StringWidth_(txt,encoding)
End Function

Function StringHeight(txt$, encoding=FT_ASCII)
	Return StringHeight_(txt,encoding)
End Function
