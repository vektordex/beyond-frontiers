; =======
; Created by Dmitry Maslov aka ABTOMAT
; This Function draws round-edged rectangle using MihailV's FastImage dll  
; See Help.txt for instructions
; =======

Global RoundRectBank

Function DrawRoundRect (x#, y#, w#, h#, RoundRectRadius# = 40, RoundRectCorners% = -1, color% = $FFFFFFFF, image% = 0)
				
	RoundRectRadius = Abs(RoundRectRadius)
	
	If Not RoundRectCorners = -1 Then
		RoundRectCorners = Abs(RoundRectCorners)
	Else
		RoundRectCorners = (RoundRectRadius/10)+1
	End If
	
	RoundRectBank = CreateBank (20*(9 + RoundRectCorners*4))
	
	PokeInt RoundRectBank, 0, 8 + RoundRectCorners*4 
	PokeInt RoundRectBank, 4, 0
	PokeInt RoundRectBank, 8, FI_TRIANGLEFAN 
	


	PokeFloat RoundRectBank,20, 0
	PokeFloat RoundRectBank,24, 0+RoundRectRadius
	PokeInt RoundRectBank,28, color
	PokeFloat RoundRectBank,32, 0
	PokeFloat RoundRectBank,36, Float(RoundRectRadius)/h


	PokeFloat RoundRectBank,40, 0
	PokeFloat RoundRectBank,44, h-RoundRectRadius
	PokeInt RoundRectBank,48, color
	PokeFloat RoundRectBank,52, 0
	PokeFloat RoundRectBank,56, Float(h-RoundRectRadius)/h


	For i=1 To RoundRectCorners
		Angle# = 180+(Float(90)/(RoundRectCorners+1)*i)	
		PokeFloat RoundRectBank,20*(i+2), (Cos(Angle)+1)*RoundRectRadius
		PokeFloat RoundRectBank,20*(i+2)+4, ((Sin(Angle)+1)*-1)*RoundRectRadius+h
		PokeInt RoundRectBank,20*(i+2)+8, color
		PokeFloat RoundRectBank,20*(i+2)+12, Float((Cos(Angle)+1)*RoundRectRadius)/w
		PokeFloat RoundRectBank,20*(i+2)+16, Float(((Sin(Angle)+1)*-1)*RoundRectRadius+h)/h
	Next

	
	PokeFloat RoundRectBank,20*(3+RoundRectCorners), RoundRectRadius
	PokeFloat RoundRectBank,20*(3+RoundRectCorners)+4, h
	
	PokeInt RoundRectBank,20*(3+RoundRectCorners)+8, color
	
	PokeFloat RoundRectBank,20*(3+RoundRectCorners)+12, Float(RoundRectRadius)/w
	PokeFloat RoundRectBank,20*(3+RoundRectCorners)+16, 1


	PokeFloat RoundRectBank,20*(4+RoundRectCorners), w-RoundRectRadius
	PokeFloat RoundRectBank,20*(4+RoundRectCorners)+4, h
	
	PokeInt RoundRectBank,20*(4+RoundRectCorners)+8, color
	
	PokeFloat RoundRectBank,20*(4+RoundRectCorners)+12, Float(w - RoundRectRadius)/w
	PokeFloat RoundRectBank,20*(4+RoundRectCorners)+16, 1



	For i=1 To RoundRectCorners
		Angle# = 270+(Float(90)/(RoundRectCorners+1)*i)	
		PokeFloat RoundRectBank,20*(RoundRectCorners+i+4), (Cos(Angle)-1)*RoundRectRadius+w
		PokeFloat RoundRectBank,20*(RoundRectCorners+i+4)+4, ((Sin(Angle)+1)*-1)*RoundRectRadius+h
		PokeInt RoundRectBank,20*(RoundRectCorners+i+4)+8, color
		PokeFloat RoundRectBank,20*(RoundRectCorners+i+4)+12, ((Cos(Angle)-1)*RoundRectRadius+w)/w
		PokeFloat RoundRectBank,20*(RoundRectCorners+i+4)+16, (((Sin(Angle)+1)*-1)*RoundRectRadius+h)/h
	Next


	PokeFloat RoundRectBank,20*(5+RoundRectCorners*2), w
	PokeFloat RoundRectBank,20*(5+RoundRectCorners*2)+4, h-RoundRectRadius
	
	PokeInt RoundRectBank,20*(5+RoundRectCorners*2)+8, color
	
	PokeFloat RoundRectBank,20*(5+RoundRectCorners*2)+12, 1
	PokeFloat RoundRectBank,20*(5+RoundRectCorners*2)+16, Float(h-RoundRectRadius)/h


	PokeFloat RoundRectBank,20*(6+RoundRectCorners*2), w
	PokeFloat RoundRectBank,20*(6+RoundRectCorners*2)+4, RoundRectRadius
	
	PokeInt RoundRectBank,20*(6+RoundRectCorners*2)+8, color
	
	PokeFloat RoundRectBank,20*(6+RoundRectCorners*2)+12, 1
	PokeFloat RoundRectBank,20*(6+RoundRectCorners*2)+16, Float(RoundRectRadius)/h


	For i=1 To RoundRectCorners
		Angle# = (Float(90)/(RoundRectCorners+1)*i)	
		PokeFloat RoundRectBank,20*(RoundRectCorners*2+i+6), (Cos(Angle)-1)*RoundRectRadius+w
		PokeFloat RoundRectBank,20*(RoundRectCorners*2+i+6)+4, ((Sin(Angle)-1)*-1)*RoundRectRadius
		PokeInt RoundRectBank,20*(RoundRectCorners*2+i+6)+8, color
		PokeFloat RoundRectBank,20*(RoundRectCorners*2+i+6)+12, ((Cos(Angle)-1)*RoundRectRadius+w)/w
		PokeFloat RoundRectBank,20*(RoundRectCorners*2+i+6)+16, (((Sin(Angle)-1)*-1)*RoundRectRadius)/h
	Next
	

	PokeFloat RoundRectBank,20*(7+RoundRectCorners*3), w-RoundRectRadius
	PokeFloat RoundRectBank,20*(7+RoundRectCorners*3)+4, 0
	
	PokeInt RoundRectBank,20*(7+RoundRectCorners*3)+8, color
	
	PokeFloat RoundRectBank,20*(7+RoundRectCorners*3)+12, Float(w-RoundRectRadius)/w
	PokeFloat RoundRectBank,20*(7+RoundRectCorners*3)+16, 0


	PokeFloat RoundRectBank,20*(8+RoundRectCorners*3), RoundRectRadius
	PokeFloat RoundRectBank,20*(8+RoundRectCorners*3)+4, 0
	
	PokeInt RoundRectBank,20*(8+RoundRectCorners*3)+8, color
	
	PokeFloat RoundRectBank,20*(8+RoundRectCorners*3)+12, Float(RoundRectRadius)/w
	PokeFloat RoundRectBank,20*(8+RoundRectCorners*3)+16, 0


	For i=1 To RoundRectCorners
		Angle# = 90+(Float(90)/(RoundRectCorners+1)*i)	
		PokeFloat RoundRectBank,20*(RoundRectCorners*3+i+8), (Cos(Angle)+1)*RoundRectRadius
		PokeFloat RoundRectBank,20*(RoundRectCorners*3+i+8)+4, ((Sin(Angle)-1)*-1)*RoundRectRadius
		PokeInt RoundRectBank,20*(RoundRectCorners*3+i+8)+8, color
		PokeFloat RoundRectBank,20*(RoundRectCorners*3+i+8)+12, ((Cos(Angle)+1)*RoundRectRadius)/w
		PokeFloat RoundRectBank,20*(RoundRectCorners*3+i+8)+16, (((Sin(Angle)-1)*-1)*RoundRectRadius)/h
	Next
	
	SetBlend FI_ALPHABLEND
	DrawPoly (x, y, RoundRectBank, image)
	FreeBank RoundRectBank
	
End Function