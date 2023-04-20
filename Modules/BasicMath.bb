Function HexB#(Hexzahl$)
	Local Integer_Result#
	If Left$(Hexzahl$,1)="$" Then Hexzahl$=Mid$(Hexzahl$,2)
	For i=1 To Len(Hexzahl$)
		tmp1$=Upper$(Mid$(Hexzahl$,i,1)):tmp2=tmp1$
		If tmp2=0 And tmp1$<>"0" Then tmp2=Asc(tmp1$)-55
		Integer_Result=Integer_Result*16:Integer_Result=Integer_Result+tmp2
	Next
	Return Integer_Result
End Function

Function Normalize#(value#=128.0,value_min#=0.0,value_max#=255.0,norm_min#=0.0,norm_max#=1.0) 
    
	Return ((value-value_min)/(value_max-value_min))*(norm_max-norm_min)+norm_min 
    
End Function 

Function Pad$(TextToPad$, PadLength%, Direction%, Symbol$)
    Local TTPLen% = Len(TextToPad)
    Local PadLen% = PadLength - TTPLen
    If TTPLen < PadLength Then
        Local PadStr = ""
        For I = 1 To PadLen
            PadStr = PadStr + Symbol
        Next
        
        Select Direction
            Case 0; left
                Return PadStr + TextToPad
            Case 1; right
                Return TextToPad + PadStr
        End Select
    Else
        Return TextToPad
    EndIf
End Function

Function ConvertNumbers$(NumberToConvert%)
    
	Local out$ = ""
    Local val% = NumberToConvert
    While val > 0
        If (out <> "")
            out = "." + out
        EndIf
        If (val > 1000)
            out = Pad(val Mod 1000, 3, 0, "0") + out
        Else
            out = (val Mod 1000) + out
        EndIf
        
        val = val / 1000
    Wend
    Return out
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D