

;DrawCED V.1.0c, for Draw3D2 V.1.0
;29.07.2010
;Autor: hectic
;www.hectic.de


Const OVERDRIVE%=2 ;Stability (Less-Save) (Standard=2)


;[Block] DRAWCED/VARIABLENDEKLARATIONEN




;CED/GRUNDELEMENTE
Global GCEDMesh%=CreateMesh()
Global GCEDFace%=CreateSurface(GCEDMesh)
EntityAlpha GCEDMesh,0
EntityType GCEDMesh,2
Collisions 1,2,2,2

;KP/OFFSET/KONSTANTEN
Const CEDKPBANKHD%=00
Const CEDKPBANKXF%=04
Const CEDKPBANKYF%=08
Const CEDKPBANKXP%=12
Const CEDKPBANKYP%=16
Const CEDKPBANKXS%=20
Const CEDKPBANKYS%=24
Const CEDKPBANKTP%=28
Const CEDKPBANKTS%=32
Const CEDKPBANKTM%=36
Const CEDKPBANKRD%=40
Const CEDKPBANKMS%=44
Const CEDKPBANKID%=48
Const CEDKPBANKSTEP%=52

;VK/OFFSET/KONSTANTEN
Const CEDVKBANKHD%=00
Const CEDVKBANKK1%=04
Const CEDVKBANKK2%=08
Const CEDVKBANKFD%=12
Const CEDVKBANKDF%=16
Const CEDVKBANKLN%=20
Const CEDVKBANKID%=24
Const CEDVKBANKSTEP%=28

;AM/OFFSET/KONSTANTEN
Const CEDAMBANKAT%=00
Const CEDAMBANKID%=04
Const CEDAMBANKFR%=08
Const CEDAMBANKSC%=12
Const CEDAMBANKXS%=16
Const CEDAMBANKYS%=20
Const CEDAMBANKTS%=24
Const CEDAMBANKMD%=28
Const CEDAMBANKSTEP%=32

;BANK/OFFSET/KONSTANTEN
Const CEDKPBANK%=00
Const CEDKPSIZE%=04
Const CEDVKBANK%=08
Const CEDVKSIZE%=12
Const CEDAMBANK%=16
Const CEDAMSIZE%=20
Const CEDIMAGES%=24
Const CEDSCBANK%=28
Const CEDMPIVOT%=32

;BENUTZER/KONSTANTEN
Const ABSOLUTE%=0
Const RELATIVE%=1
Const ACCSPEED%=2




;[End Block]
Function AdaptKPID(FBank%,FID%,FSlide#)
	
	
	
	
	;HALTE/WERTEEINGRENZUNG
	If FSlide<0 Then FSlide=0
	If FSlide>1 Then FSlide=1
	
	;BANK/WERTE<AUS>WEISUNG
	Local IKPBank%=PeekInt(FBank,CEDKPBANK)
	Local IKPSize%=PeekInt(FBank,CEDKPSIZE)
	
	;KP/VARIABLENDEKLARATIONEN
	Local LTS#,LTM#
	
	;VARIABLENDEKLARATIONEN
	Local IKPCount%
	Local ITM#=0
	Local ITS#=0
	
	;MASSE/SPEED/ZUSAMMENZÄHLEN
	For IKPCount=0 To (IKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
		If FID=PeekInt(IKPBank,IKPCount+CEDKPBANKID) Then
			LTS=PeekFloat(IKPBank,IKPCount+CEDKPBANKTS)
			LTM=PeekFloat(IKPBank,IKPCount+CEDKPBANKTM)
			ITS=ITS+LTS*LTM
			ITM=ITM+LTM
		End If
	Next
	
	;KP/ID/KUPPLUNG/VERTEILEN
	For IKPCount=0 To (IKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
		If FID=PeekInt(IKPBank,IKPCount+CEDKPBANKID) Then
			LTS=PeekFloat(IKPBank,IKPCount+CEDKPBANKTS)
			LTM=PeekFloat(IKPBank,IKPCount+CEDKPBANKTM)
			LTS=(FSlide*ITS/ITM)+(1-FSlide)*LTS
			PokeFloat IKPBank,IKPCount+CEDKPBANKTS,LTS
		End If
	Next
	
	
	
	
End Function
Function AddCL(FPhyxIH%,FPhyxX1#,FPhyxY1#,FPhyxX2#,FPhyxY2#)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	FPhyxX1=FPhyxX1*0.1
	FPhyxY1=FPhyxY1*0.1
	FPhyxX2=FPhyxX2*0.1
	FPhyxY2=FPhyxY2*0.1
	
	;VERTEX/POLYGON/ZUWEISUNGEN
	Local IPhyxV0=AddVertex(GCEDFace,FPhyxX1,FPhyxY1,+100)
	Local IPhyxV1=AddVertex(GCEDFace,FPhyxX2,FPhyxY2,+100)
	Local IPhyxV2=AddVertex(GCEDFace,FPhyxX2,FPhyxY2,-100)
	Local IPhyxV3=AddVertex(GCEDFace,FPhyxX1,FPhyxY1,-100)
	AddTriangle(GCEDFace,IPhyxV0,IPhyxV1,IPhyxV2)
	AddTriangle(GCEDFace,IPhyxV2,IPhyxV3,IPhyxV0)
	
	
	
	
End Function
Function AddKP(FBank%,FXP%,FYP%,FRD%,FMS%,FCT%,FTM%,FID%)
	
	
	
	
	;BANK/WERTE<AUS>WEISUNG
	Local IKPBank%=PeekInt(FBank,CEDKPBANK)
	Local IKPSize%=PeekInt(FBank,CEDKPSIZE)
	
	;BANK/WERTE-BEARBEITUNG
	PokeInt FBank,CEDKPSIZE,IKPSize+CEDKPBANKSTEP
	ResizeBank IKPBank,IKPSize+CEDKPBANKSTEP
	
	;SO/ANDERES/ZEUG/MACHEN
	Local IHD%=CreatePivot()
	PositionEntity IHD,FXP,FYP,0
	EntityRadius IHD,FRD
	EntityType IHD,FCT
	
	;BANK/WERTE<EIN>WEISUNG
	PokeInt IKPBank,IKPSize+CEDKPBANKHD,IHD
	PokeFloat IKPBank,IKPSize+CEDKPBANKXF,FXP
	PokeFloat IKPBank,IKPSize+CEDKPBANKYF,FYP
	PokeFloat IKPBank,IKPSize+CEDKPBANKXP,FXP
	PokeFloat IKPBank,IKPSize+CEDKPBANKYP,FYP
	PokeFloat IKPBank,IKPSize+CEDKPBANKXS,0
	PokeFloat IKPBank,IKPSize+CEDKPBANKYS,0
	PokeFloat IKPBank,IKPSize+CEDKPBANKTP,0
	PokeFloat IKPBank,IKPSize+CEDKPBANKTS,0
	PokeFloat IKPBank,IKPSize+CEDKPBANKRD,FRD
	PokeFloat IKPBank,IKPSize+CEDKPBANKTM,FTM
	PokeFloat IKPBank,IKPSize+CEDKPBANKMS,FMS
	PokeInt IKPBank,IKPSize+CEDKPBANKID,FID
	
	;AUSGABEWERT/ALS/IDENTIFIKATION
	Return IKPSize/CEDKPBANKSTEP
	
	
	
	
End Function
Function AddVK(FBank%,FK1%,FK2%,FFD%,FDF%,FID%)
	
	
	
	
	;BANK/WERTE<AUS>WEISUNG
	Local IVKBank%=PeekInt(FBank,CEDVKBANK)
	Local IVKSize%=PeekInt(FBank,CEDVKSIZE)
	Local IKPBank%=PeekInt(FBank,CEDKPBANK)
	Local IKPSize%=PeekInt(FBank,CEDKPSIZE)
	Local LXP1=PeekFloat(IKPBank,FK1*CEDKPBANKSTEP+CEDKPBANKXP)
	Local LYP1=PeekFloat(IKPBank,FK1*CEDKPBANKSTEP+CEDKPBANKYP)
	Local LXP2=PeekFloat(IKPBank,FK2*CEDKPBANKSTEP+CEDKPBANKXP)
	Local LYP2=PeekFloat(IKPBank,FK2*CEDKPBANKSTEP+CEDKPBANKYP)
	
	;EINGEGEBEN-BEARBEITUNG
	If FK1=>IKPSize/CEDKPBANKSTEP Then RuntimeError "Function - AddVK"+Chr$(10)+"Parameter - KP1 - out of range"
	If FK2=>IKPSize/CEDKPBANKSTEP Then RuntimeError "Function - AddVK"+Chr$(10)+"Parameter - KP2 - out of range"
	If FFD<1 Then FFD=1
	If FDF<1 Then FDF=1
	
	;BANK/WERTE-BEARBEITUNG
	PokeInt FBank,CEDVKSIZE,IVKSize+CEDVKBANKSTEP
	ResizeBank IVKBank,IVKSize+CEDVKBANKSTEP
	
	;BANK/WERTE<EIN>WEISUNG
	PokeInt IVKBank,IVKSize+CEDVKBANKK1,FK1
	PokeInt IVKBank,IVKSize+CEDVKBANKK2,FK2
	PokeFloat IVKBank,IVKSize+CEDVKBANKFD,(FFD+1)^2
	PokeFloat IVKBank,IVKSize+CEDVKBANKDF,(FDF+1)^2
	PokeFloat IVKBank,IVKSize+CEDVKBANKLN,Sqr((LXP1-LXP2)^2+(LYP1-LYP2)^2)
	
	;AUSGABEWERT/ALS/IDENTIFIKATION
	Return IVKSize/CEDVKBANKSTEP
	
	
	
	
End Function
Function AddVKIDLen(FBank,FID,FLN#)
	
	
	
	
	;BANK/WERTE<AUS>WEISUNG
	Local IVKBank%=PeekInt(FBank,CEDVKBANK)
	Local IVKSize%=PeekInt(FBank,CEDVKSIZE)
	
	;VARIABLENDEKLARATIONEN
	Local IVKCount%
	Local LVKLN#
	Local LVKID%
	
	;ALLE/VK/LÄNGENÄNDERUNG
	For IVKCount=0 To (IVKSize-CEDVKBANKSTEP) Step CEDVKBANKSTEP
		
		;VK/BANK/WERTE<AUS>WEISUNG
		LVKLN=PeekFloat(IVKBank,IVKCount+CEDVKBANKLN)
		LVKID=PeekInt(IVKBank,IVKCount+CEDVKBANKID)
		If LVKID=FID Then PokeFloat(IVKBank,IVKCount+CEDVKBANKLN,LVKLN+FLN)
	Next
	
	
	
	
End Function
Function AppCED(FBank%,FLoops#=2)
	
	
	
	
	;BANK/WERTE<AUS>WEISUNG
	Local IKPBank%=PeekInt(FBank,CEDKPBANK)
	Local IKPSize%=PeekInt(FBank,CEDKPSIZE)
	Local IVKBank%=PeekInt(FBank,CEDVKBANK)
	Local IVKSize%=PeekInt(FBank,CEDVKSIZE)
	Local IAMBank%=PeekInt(FBank,CEDAMBANK)
	Local IAMSize%=PeekInt(FBank,CEDAMSIZE)
	
	;KP/VARIABLENDEKLARATIONEN
	Local LKPHD%,LKPXF#,LKPYF#,LKPXP#
	Local LKPYP#,LKPXS#,LKPYS#,LKPTP#
	Local LKPTS#,LKPRD#,LKPTM#,LKPMS#
	
	;VK/VARIABLENDEKLARATIONEN
	Local LVKK1%,LVKK2%,LVKFD#,LVKDF#,LVKLN#
	Local LKPXP1#,LKPYP1#,LKPXS1#,LKPYS1#,LKPMS1#
	Local LKPXP2#,LKPYP2#,LKPXS2#,LKPYS2#,LKPMS2#
	
	;AM/VARIABLENDEKLARATIONEN
	Local LAMAT%,LAMID%,LAMFR%,LAMSC#
	Local LAMXS#,LAMYS#,LAMTS#,LAMMD%
	
	;VARIABLENDEKLARATIONEN
	Local ILoops%,ICount%
	Local IKPCount%
	Local IVKCount%
	Local IAMCount%
	Local ISP#,ILN#,IDX#,IDY#
	Local INX#,INY#,IW1#,IW2#
	Local IEH#=1.0/FLoops
	
	
	
	
	;MEHRFACHLOOP/STABILISATION
	For ILoops=1 To FLoops Step 1
		
		;ALLGEMEINE/KP/BEWEGUNGSRECHNUNG
		For IKPCount=0 To (IKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
			
			;KP/BANK/WERTE<AUS>WEISUNG
			LKPHD=PeekInt(IKPBank,IKPCount+CEDKPBANKHD)
			LKPXF=PeekFloat(IKPBank,IKPCount+CEDKPBANKXF)
			LKPYF=PeekFloat(IKPBank,IKPCount+CEDKPBANKYF)
			LKPXP=PeekFloat(IKPBank,IKPCount+CEDKPBANKXP)
			LKPYP=PeekFloat(IKPBank,IKPCount+CEDKPBANKYP)
			LKPXS=PeekFloat(IKPBank,IKPCount+CEDKPBANKXS)
			LKPYS=PeekFloat(IKPBank,IKPCount+CEDKPBANKYS)
			LKPTP=PeekFloat(IKPBank,IKPCount+CEDKPBANKTP)
			LKPTS=PeekFloat(IKPBank,IKPCount+CEDKPBANKTS)
			LKPRD=PeekFloat(IKPBank,IKPCount+CEDKPBANKRD)
			LKPTM=PeekFloat(IKPBank,IKPCount+CEDKPBANKTM)
			LKPMS=PeekFloat(IKPBank,IKPCount+CEDKPBANKMS)
			If LKPMS=0 Then LKPMS=1
			
			;KP/KOLLISIONSBEARBEITUNG
			For ICount=1 To CountCollisions(LKPHD)
				INX=CollisionNX(LKPHD,ICount)
				INY=CollisionNY(LKPHD,ICount)
				IW1=ATan2(INY,INX)
				IW2=ATan2(LKPYS,LKPXS)
				ISP=Sqr(LKPXS^2+LKPYS^2)*FLoops
				LKPTS=LKPTS+(((Sin(IW2-IW1)*ISP)-LKPTS)/LKPTM/LKPTM)
				LKPXS=(LKPXS-INY*LKPTS*IEH)*0.5
				LKPYS=(LKPYS+INX*LKPTS*IEH)*0.5
			Next
			
			;KP/ANPASSUNG
			LKPXP=LKPXP+LKPXS
			LKPYP=LKPYP+LKPYS
			If ILoops=1 Then
				LKPTP=LKPTP-(LKPTS/LKPRD*64)
				If LKPTP>+180 Then LKPTP=LKPTP-360
				If LKPTP<-180 Then LKPTP=LKPTP+360
			End If
			
			;KP/BANK/WERTE<EIN>WEISUNG
			PokeFloat IKPBank,IKPCount+CEDKPBANKXP,LKPXP
			PokeFloat IKPBank,IKPCount+CEDKPBANKYP,LKPYP
			PokeFloat IKPBank,IKPCount+CEDKPBANKXS,LKPXS
			PokeFloat IKPBank,IKPCount+CEDKPBANKYS,LKPYS
			PokeFloat IKPBank,IKPCount+CEDKPBANKTP,LKPTP
			PokeFloat IKPBank,IKPCount+CEDKPBANKTS,LKPTS
			MoveEntity LKPHD,LKPXS,LKPYS,0
		Next
		
		;ALLGEMEINE/VK/ZIELEAUSRICHTUNG
		For IVKCount=0 To (IVKSize-CEDVKBANKSTEP) Step CEDVKBANKSTEP
			
			;VK/QUER/BANK/WERTE<AUS>WEISUNG
			LVKK1=PeekInt(IVKBank,IVKCount+CEDVKBANKK1)
			LVKK2=PeekInt(IVKBank,IVKCount+CEDVKBANKK2)
			LVKFD=PeekFloat(IVKBank,IVKCount+CEDVKBANKFD)
			LVKDF=PeekFloat(IVKBank,IVKCount+CEDVKBANKDF)
			LVKLN=PeekFloat(IVKBank,IVKCount+CEDVKBANKLN)
			LKPXP1=PeekFloat(IKPBank,LVKK1*CEDKPBANKSTEP+CEDKPBANKXP)
			LKPYP1=PeekFloat(IKPBank,LVKK1*CEDKPBANKSTEP+CEDKPBANKYP)
			LKPXS1=PeekFloat(IKPBank,LVKK1*CEDKPBANKSTEP+CEDKPBANKXS)
			LKPYS1=PeekFloat(IKPBank,LVKK1*CEDKPBANKSTEP+CEDKPBANKYS)
			LKPMS1=PeekFloat(IKPBank,LVKK1*CEDKPBANKSTEP+CEDKPBANKMS)
			LKPXP2=PeekFloat(IKPBank,LVKK2*CEDKPBANKSTEP+CEDKPBANKXP)
			LKPYP2=PeekFloat(IKPBank,LVKK2*CEDKPBANKSTEP+CEDKPBANKYP)
			LKPXS2=PeekFloat(IKPBank,LVKK2*CEDKPBANKSTEP+CEDKPBANKXS)
			LKPYS2=PeekFloat(IKPBank,LVKK2*CEDKPBANKSTEP+CEDKPBANKYS)
			LKPMS2=PeekFloat(IKPBank,LVKK2*CEDKPBANKSTEP+CEDKPBANKMS)
			If LKPMS1=0 Then LKPMS1=1
			If LKPMS2=0 Then LKPMS2=1
			
			;VK/VEKTORTRANSFORMATIONSRECHNUNG
			If LKPXP1<>LKPXP2 And LKPYP1<>LKPYP2 Then
				ILN=Sqr((LKPXP1-LKPXP2)^2+(LKPYP1-LKPYP2)^2)
				TFormNormal LKPXP1-LKPXP2,LKPYP1-LKPYP2,0,0,0
				IDX=TFormedX()*OVERDRIVE*(ILN-LVKLN)
				IDY=TFormedY()*OVERDRIVE*(ILN-LVKLN)
				LKPXP1=LKPXP1-(IDX/LVKFD)/LKPMS1
				LKPYP1=LKPYP1-(IDY/LVKFD)/LKPMS1
				LKPXS1=LKPXS1-(IDX/LVKDF)/LKPMS1
				LKPYS1=LKPYS1-(IDY/LVKDF)/LKPMS1
				LKPXP2=LKPXP2+(IDX/LVKFD)/LKPMS2
				LKPYP2=LKPYP2+(IDY/LVKFD)/LKPMS2
				LKPXS2=LKPXS2+(IDX/LVKDF)/LKPMS2
				LKPYS2=LKPYS2+(IDY/LVKDF)/LKPMS2
				
				;VK/BANK/WERTE<EIN>WEISUNG
				PokeFloat IKPBank,LVKK1*CEDKPBANKSTEP+CEDKPBANKXP,LKPXP1
				PokeFloat IKPBank,LVKK1*CEDKPBANKSTEP+CEDKPBANKYP,LKPYP1
				PokeFloat IKPBank,LVKK1*CEDKPBANKSTEP+CEDKPBANKXS,LKPXS1
				PokeFloat IKPBank,LVKK1*CEDKPBANKSTEP+CEDKPBANKYS,LKPYS1
				PokeFloat IKPBank,LVKK2*CEDKPBANKSTEP+CEDKPBANKXP,LKPXP2
				PokeFloat IKPBank,LVKK2*CEDKPBANKSTEP+CEDKPBANKYP,LKPYP2
				PokeFloat IKPBank,LVKK2*CEDKPBANKSTEP+CEDKPBANKXS,LKPXS2
				PokeFloat IKPBank,LVKK2*CEDKPBANKSTEP+CEDKPBANKYS,LKPYS2
			End If
		Next
		
		;KP/DOPPEL/SAUBERKEITSBEARBEITUNG
		For IKPCount=0 To (IKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
			
			;KP/BANK/WERTE<AUS>WEISUNG
			LKPHD=PeekInt(IKPBank,IKPCount+CEDKPBANKHD)
			LKPXF=PeekFloat(IKPBank,IKPCount+CEDKPBANKXF)
			LKPYF=PeekFloat(IKPBank,IKPCount+CEDKPBANKYF)
			LKPXP=PeekFloat(IKPBank,IKPCount+CEDKPBANKXP)
			LKPYP=PeekFloat(IKPBank,IKPCount+CEDKPBANKYP)
			LKPXS=PeekFloat(IKPBank,IKPCount+CEDKPBANKXS)
			LKPYS=PeekFloat(IKPBank,IKPCount+CEDKPBANKYS)
			LKPMS=PeekFloat(IKPBank,IKPCount+CEDKPBANKMS)
			
			;SONDERHEITEN
			If LKPMS=0 Then
				
				;FIXIERTE/KP/POSITION
				LKPXS=(LKPXP-LKPXF)*0.5/FLoops
				LKPYS=(LKPYP-LKPYF)*0.5/FLoops
				LKPXP=LKPXF
				LKPYP=LKPYF
				PokeFloat IKPBank,IKPCount+CEDKPBANKXS,LKPXS
				PokeFloat IKPBank,IKPCount+CEDKPBANKYS,LKPYS
				PokeFloat IKPBank,IKPCount+CEDKPBANKXP,LKPXP
				PokeFloat IKPBank,IKPCount+CEDKPBANKYP,LKPYP
			Else
				
;				;LUFTWIEDERSTAND
;				If ILoops=1 Then
;					LKPXS=LKPXS*0.9999
;					LKPYS=LKPYS*0.9999
;					PokeFloat IKPBank,IKPCount+CEDKPBANKXS,LKPXS
;					PokeFloat IKPBank,IKPCount+CEDKPBANKYS,LKPYS
;				End If
				
				;RÜCKSETZPOSITION/NACH/KOLLISION
				If CountCollisions(LKPHD)>0 Then
					LKPXP=EntityX(LKPHD)
					LKPYP=EntityY(LKPHD)
					PokeFloat IKPBank,IKPCount+CEDKPBANKXP,LKPXP
					PokeFloat IKPBank,IKPCount+CEDKPBANKYP,LKPYP
				End If
			End If
			
			;KP/SICHERHEITSRÜCKSETZUNG
			PositionEntity LKPHD,LKPXP,LKPYP,0
		Next
	Next
	
	
	
	
End Function
Function BreakKPID(FBank%,FID%,FLinear#,FFricty#,FABS#=0)
	
	
	
	
	;HALTE/WERTEEINGRENZUNG
	If FLinear<0 Then FLinear=0
	If FLinear>1 Then FLinear=1
	If FFricty<0 Then FFricty=0
	If FFricty>1 Then FFricty=1
	If FABS<0 Then FABS=0
	If FABS>1 Then FABS=1
	
	;BANK/WERTE<AUS>WEISUNG
	Local IKPBank%=PeekInt(FBank,CEDKPBANK)
	Local IKPSize%=PeekInt(FBank,CEDKPSIZE)
	
	;VARIABLENDEKLARATIONEN
	Local IContact%=1
	Local IKPCount%
	Local IValue#
	Local IHD%
	
	;SO/ANDERES/ZEUG/MACHEN
	If FABS>0 Then
		For IKPCount=0 To (IKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
			If FID=PeekInt(IKPBank,IKPCount+CEDKPBANKID) Then
				IHD=PeekInt(IKPBank,IKPCount+CEDKPBANKHD)
				If CountCollisions(IHD)=0 Then IContact=0
			End If
		Next
	End If
	
	;ABS/VERHÄLTNIS
	If IContact=0 Then
		FLinear=FLinear*(1-FABS)
		FFricty=(FFricty*(1-FABS))+FABS
	End If
	
	;ABS/AUF/RÄDER/ANWENDEN
	For IKPCount=0 To (IKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
		If FID=PeekInt(IKPBank,IKPCount+CEDKPBANKID) Then
			IValue=PeekFloat(IKPBank,IKPCount+CEDKPBANKTS)
			IValue=(IValue-(Sgn(IValue)*FLinear))*FFricty
			If Abs(IValue)<FLinear Then IValue=0
			PokeFloat IKPBank,IKPCount+CEDKPBANKTS,IValue
		End If
	Next
	
	
	
	
End Function
Function DelCED(FBank%)
	
	
	
	
	;BANK/WERTE<AUS>WEISUNG
	Local IVKBank%=PeekInt(FBank,CEDVKBANK)
	Local IVKSize%=PeekInt(FBank,CEDVKSIZE)
	Local IKPBank%=PeekInt(FBank,CEDKPBANK)
	Local IKPSize%=PeekInt(FBank,CEDKPSIZE)
	
	;VARIABLENDEKLARATIONEN
	Local IKPCount%
	
	;FREIGABEN-BERECHNUNGEN
	For IKPCount=0 To (IKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
		FreeEntity PeekInt(IKPBank,IKPCount+CEDKPBANKHD)
	Next
	
	;ALLE-SPEICHERFREIGABEN
	FreeBank PeekInt(FBank,CEDKPBANK)
	FreeBank PeekInt(FBank,CEDVKBANK)
	FreeBank PeekInt(FBank,CEDAMBANK)
	FreeBank FBank
	
	
	
	
End Function
Function DrawCED(FBank%)
	
	
	
	
	;BANK/WERTE<AUS>WEISUNG
	Local IKPBank%=PeekInt(FBank,CEDKPBANK)
	Local IKPSize%=PeekInt(FBank,CEDKPSIZE)
	Local IVKBank%=PeekInt(FBank,CEDVKBANK)
	Local IVKSize%=PeekInt(FBank,CEDVKSIZE)
	Local IAMBank%=PeekInt(FBank,CEDAMBANK)
	Local IAMSize%=PeekInt(FBank,CEDAMSIZE)
	
	;VK/VARIABLENDEKLARATIONEN
	Local LVKK1%,LVKK2%,LVKFD#,LVKDF#,LVKLN#
	Local LKPXP1#,LKPYP1#,LKPXS1#,LKPYS1#,LKPMS1#
	Local LKPXP2#,LKPYP2#,LKPXS2#,LKPYS2#,LKPMS2#
	
	;AM/VARIABLENDEKLARATIONEN
	Local LAMAT%,LAMID%,LAMFR%,LAMSC#
	Local LAMXS#,LAMYS#,LAMTS#,LAMMD%
	
	;VARIABLENDEKLARATIONEN
	Local IAMCount%
	Local IHD%
	Local IXP#
	Local IYP#
	Local ITP#
	Local IRD#
	
	;VARIABLENDEKLARATIONEN
	Local DX1#,DY1#
	Local DX2#,DY2#
	Local DX#,DY#
	
	
	
	
	;ALLE(KP/VK)AM/EINZEICHNEN
	For IAMCount=0 To (IAMSize-CEDAMBANKSTEP) Step CEDAMBANKSTEP
		
		;BANK/WERTE<AUS>WEISUNG
		LAMAT=PeekInt(IAMBank,IAMCount+CEDAMBANKAT)
		LAMID=PeekInt(IAMBank,IAMCount+CEDAMBANKID)
		LAMFR=PeekInt(IAMBank,IAMCount+CEDAMBANKFR)
		LAMSC=PeekFloat(IAMBank,IAMCount+CEDAMBANKSC)
		LAMXS=PeekFloat(IAMBank,IAMCount+CEDAMBANKXS)
		LAMYS=PeekFloat(IAMBank,IAMCount+CEDAMBANKYS)
		LAMTS=PeekFloat(IAMBank,IAMCount+CEDAMBANKTS)
		LAMMD=PeekInt(IAMBank,IAMCount+CEDAMBANKMD)
		
		;WELCHE/STRUKTUR
		If LAMAT=0 Then
			
			;ALLE/KPAM/EINZEICHNEN
			IHD=PeekInt(IKPBank,LAMID*CEDKPBANKSTEP+CEDKPBANKHD)
			IXP#=EntityX(IHD)
			IYP#=EntityY(IHD)
			ITP#=PeekFloat(IKPBank,LAMID*CEDKPBANKSTEP+CEDKPBANKTP)
			IRD#=PeekFloat(IKPBank,LAMID*CEDKPBANKSTEP+CEDKPBANKRD)
			DrawImageKP(PeekInt(FBank,CEDIMAGES),IXP,IYP,ITP,IRD,LAMFR)
		Else
			
			;ALLE/VKAM/EINZEICHNEN
			LVKK1=PeekInt(IVKBank,LAMID*CEDVKBANKSTEP+CEDVKBANKK1)
			LVKK2=PeekInt(IVKBank,LAMID*CEDVKBANKSTEP+CEDVKBANKK2)
			LKPXP1=PeekFloat(IKPBank,LVKK1*CEDKPBANKSTEP+CEDKPBANKXP)
			LKPYP1=PeekFloat(IKPBank,LVKK1*CEDKPBANKSTEP+CEDKPBANKYP)
			LKPXP2=PeekFloat(IKPBank,LVKK2*CEDKPBANKSTEP+CEDKPBANKXP)
			LKPYP2=PeekFloat(IKPBank,LVKK2*CEDKPBANKSTEP+CEDKPBANKYP)
			
			;VEKTOR/NORMALISIERUNGEN
			TFormNormal LKPYP2-LKPYP1,LKPXP1-LKPXP2,0,0,0
			DX1=TFormedX():DY1=TFormedY()
			TFormNormal LKPXP2-LKPXP1,LKPYP2-LKPYP1,0,0,0
			DX2=TFormedX():DY2=TFormedY()
			DX=DX1*LAMXS+DX2*LAMYS
			DY=DY2*LAMYS+DY1*LAMXS
			
			;ZEICHNEN
			Select LAMMD
				Case 0 DrawLine3D(PeekInt(FBank,CEDIMAGES),LKPXP1+DX,LKPYP1+DY,LKPXP2+DX,LKPYP2+DY,LAMTS,0,LAMFR)
				Case 1 DrawLine3D(PeekInt(FBank,CEDIMAGES),LKPXP1+DX,LKPYP1+DY,LKPXP2+DX,LKPYP2+DY,LAMTS,1,LAMFR)
				Case 2 DrawLine3D(PeekInt(FBank,CEDIMAGES),LKPXP1+DX,LKPYP1+DY,LKPXP2+DX,LKPYP2+DY,LAMTS,2,LAMFR)
				Case 3 DrawImageKP(PeekInt(FBank,CEDIMAGES),LKPXP1+DX,LKPYP1+DY,(LAMTS*2)-90-ATan2(LKPYP1-LKPYP2,LKPXP1-LKPXP2),LAMSC*25,LAMFR)
			End Select
		End If
	Next
	
	
	
	
End Function
Function DrawKP(FBank%,FImage%)
	
	
	
	
	;BANK/WERTE<AUS>WEISUNG
	Local IKPBank%=PeekInt(FBank,CEDKPBANK)
	Local IKPSize%=PeekInt(FBank,CEDKPSIZE)
	
	;VARIABLENDEKLARATIONEN
	Local IKPCount%
	Local IHD%
	Local IXP#,IYP#
	Local ITP#,IRD#
	
	;SONSTIGES/ZEUGS/ARBEIT
	For IKPCount=0 To (IKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
		IHD=PeekInt(IKPBank,IKPCount+CEDKPBANKHD)
		IXP=EntityX(IHD)
		IYP=EntityY(IHD)
;		IXP=PeekFloat(IKPBank,IKPCount+CEDKPBANKXP)
;		IYP=PeekFloat(IKPBank,IKPCount+CEDKPBANKYP)
		ITP=PeekFloat(IKPBank,IKPCount+CEDKPBANKTP)
		IRD=PeekFloat(IKPBank,IKPCount+CEDKPBANKRD)
		DrawImageKP(FImage,IXP,IYP,ITP,IRD,0)
	Next
	
	
	
	
End Function
Function FreeCED(FBank%)
	
	
	
	
	;BANK/WERTE<AUS>WEISUNG
	Local LKPBank%=PeekInt(FBank,CEDKPBANK)
	Local LVKBank%=PeekInt(FBank,CEDVKBANK)
	Local LAMBank%=PeekInt(FBank,CEDAMBANK)
	
	;BANK/SPEICHERFREIGABEN
	FreeBank LAMBank
	FreeBank LVKBank
	FreeBank LKPBank
	FreeBank FBank
	
	
	
	
End Function
Function GetCEDXPos#(FBank%,FMode%)
	
	
	
	
	;HALTE/WERTEEINGRENZUNG
	If FMode<0 Then FMode=0
	If FMode>1 Then FMode=1
	
	;BANK/WERTE<AUS>WEISUNG
	Local IKPBank%=PeekInt(FBank,CEDKPBANK)
	Local IKPSize%=PeekInt(FBank,CEDKPSIZE)
	
	;KP/VARIABLENDEKLARATIONEN
	Local IKPCount%,IXPos#
	Local LXP#,LMS#,IMSCount#
	
	;ALLGEMEINE/KP/DURSCHNITTRECHNUNG
	For IKPCount=0 To (IKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
		LXP=PeekFloat(IKPBank,IKPCount+CEDKPBANKXP)
		If FMode=1 Then LMS=PeekFloat(IKPBank,IKPCount+CEDKPBANKMS): Else LMS=1
		IXPos=IXPos+LXP*LMS
		IMSCount=IMSCount+LMS
	Next
	
	;RÜCKGABEWERT/XPOS
	Return IXPos/IMSCount
	
	
	
	
End Function
Function GetCEDYPos#(FBank%,FMode%)
	
	
	
	
	;HALTE/WERTEEINGRENZUNG
	If FMode<0 Then FMode=0
	If FMode>1 Then FMode=1
	
	;BANK/WERTE<AUS>WEISUNG
	Local IKPBank%=PeekInt(FBank,CEDKPBANK)
	Local IKPSize%=PeekInt(FBank,CEDKPSIZE)
	
	;KP/VARIABLENDEKLARATIONEN
	Local IKPCount%,IYPos#
	Local LYP#,LMS#,IMSCount#
	
	;ALLGEMEINE/KP/DURSCHNITTRECHNUNG
	For IKPCount=0 To (IKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
		LYP=PeekFloat(IKPBank,IKPCount+CEDKPBANKYP)
		If FMode=1 Then LMS=PeekFloat(IKPBank,IKPCount+CEDKPBANKMS): Else LMS=1
		If FMode=0 Then LMS=1
		IYPos=IYPos+LYP*LMS
		IMSCount=IMSCount+LMS
	Next
	
	;RÜCKGABEWERT/YPOS
	Return IYPos/IMSCount
	
	
	
	
End Function
Function GetID(FFile$,FID%)
	
	
	
	
	Local IFile=ReadFile(FFile)
	SeekFile IFile,15+FID
	Local IValue=ReadByte(IFile)
	CloseFile IFile
	Return IValue
	
	
	
	
End Function
Function LoadCED(FName$,FScale#=1.0)
	
	
	
	
	If FileType(FName)<>1 Then RuntimeError "File "+FName+" not found!"
	FScale=FScale*2.5
	
	;VARIABLENDEKLARATIONEN
	Local IDrawPivot%=CreatePivot()
	Local IKPBank%
	Local IVKBank%
	Local IAMBank%
	Local IBank%
	Local IFile%
	Local IName$
	Local INSize%
	Local IGSize%
	Local KPSize%
	Local VKSize%
	Local AMSize%
	Local IOffset%
	Local IOffset1%
	Local IOffset2%
	Local IOffset3%
	Local IOffset4%
	Local IOffset5%
	Local IOffset6%
	Local ISize#
	Local Temp1%
	Local Temp2%
	Local Temp3%
	Local Temp4%
	Local Temp5%
	Local Temp6%
	Local Temp7%
	Local Temp8%
	Local IValue%
	Local ICount%
	Local IVerz$
	
	;KP/VARIABLENDEKLARATIONEN
	Local IHD%,ICT%,IID%,IRD#
	Local IXP#,IYP#,ITM#,IMS#
	
	;VK/VARIABLENDEKLARATIONEN
	Local IK1%,IK2%,IFD#,IDF#,ILN#
	Local LXP1#,LYP1#,LXP2#,LYP2#
	
	;AM/VARIABLENDEKLARATIONEN
	Local IAT%,IFR%,ISC#
	Local IXS#,IYS#,ITS#,IMD%
	
	;DATEIHEADER/LESEN
	IFile=ReadFile(FName)
	
	;DO/CHANGE/THE/MAGIC/KEYWORD>>CED<<!!!
	;MAGISCHES/SCHLÜSSELWORT>>CED<<NICHT/ÄNDERN!!!
	If Chr$(ReadByte(IFile))<>"C" Then RuntimeError "It's not conform CED format"
	If Chr$(ReadByte(IFile))<>"E" Then RuntimeError "It's not conform CED format"
	If Chr$(ReadByte(IFile))<>"D" Then RuntimeError "It's not conform CED format"
	If Chr$(ReadByte(IFile))<>"1" Then RuntimeError "You need CED-Version 1.0"
	IOffset1=ReadShort(IFile)
	IOffset2=ReadShort(IFile)
	IOffset3=ReadShort(IFile)
	IOffset4=ReadShort(IFile)
	IOffset5=ReadShort(IFile)
	IOffset6=ReadShort(IFile)
	
	;OFFSETRECHNUNGEN
	INSize=(IOffset2-IOffset1)/1 ;IMAGE/NAME
	IGSize=(IOffset3-IOffset2)/4 ;IMAGE/GRAB
	KPSize=(IOffset4-IOffset3)/7 ;KP/ELEMENT
	VKSize=(IOffset5-IOffset4)/5 ;VK/ELEMENT
	AMSize=(IOffset6-IOffset5)/8 ;AM/ELEMENT
	
	;IDENTIFIKATIONEN
	IBank=CreateBank(36)
	PokeInt IBank,CEDKPBANK,CreateBank(KPSize*CEDKPBANKSTEP): PokeInt IBank,CEDKPSIZE,KPSize*CEDKPBANKSTEP
	PokeInt IBank,CEDVKBANK,CreateBank(VKSize*CEDVKBANKSTEP): PokeInt IBank,CEDVKSIZE,VKSize*CEDVKBANKSTEP
	PokeInt IBank,CEDAMBANK,CreateBank(AMSize*CEDAMBANKSTEP): PokeInt IBank,CEDAMSIZE,AMSize*CEDAMBANKSTEP
	PokeInt IBank,CEDMPIVOT,IDrawPivot
	IKPBank%=PeekInt(IBank,CEDKPBANK)
	IVKBank%=PeekInt(IBank,CEDVKBANK)
	IAMBank%=PeekInt(IBank,CEDAMBANK)
	PokeFloat IBank,CEDSCBANK,FScale
	
	;BILD/NAMEN/LESEN
	SeekFile(IFile,IOffset1)
	For ICount=1 To INSize
		IName=IName+Chr$(255-ReadByte(IFile))
	Next
	
	;VERZEICHNIS/FESTSTELLEN
	For ICount=1 To Len(FName)
		If Mid(FName,ICount,1)="\" Then
			IVerz=Left(FName,ICount)
		End If
	Next
	
	;HAUPT/BILD/LADEN
	If FileType(IVerz+IName)<>1 Then RuntimeError "File "+IName+" from "+FName+" not found!"
	PokeInt IBank,CEDIMAGES,LoadImage3D(IVerz+IName,2,2,IDrawPivot,-400)
	ISize=ImageSize3D(PeekInt(IBank,CEDIMAGES))/256
	
	
	
	
	;ALLE/IG/EINLESEN
	SeekFile(IFile,IOffset2)
	For ICount=0 To IGSize-1
		Temp1=ReadByte(IFile)*ISize
		Temp2=ReadByte(IFile)*ISize
		Temp3=ReadByte(IFile)*ISize+ISize
		Temp4=ReadByte(IFile)*ISize+ISize
		GrabImage3D(PeekInt(IBank,CEDIMAGES),Temp1,Temp2,Temp3,Temp4,FScale/2.5,FScale/2.5)
	Next
	
	
	
	
	;ALLE/KP/EINLESEN
	SeekFile(IFile,IOffset3)
	For ICount=0 To KPSize-1
		
		;DATEIINHALTE<AUS>LESEN
		IXP=ReadByte(IFile)-128
		IYP=ReadByte(IFile)-128
		IRD=ReadByte(IFile)/2.5
		IMS=ReadByte(IFile)
		ICT=ReadByte(IFile)
		ITM=ReadByte(IFile)
		IID=ReadByte(IFile)
		
		;SO/ANDERES/ZEUG/MACHEN
		IHD=CreatePivot()
		PositionEntity IHD,IXP,IYP,0
		EntityRadius IHD,IRD*FScale
		EntityType IHD,ICT
		
		;BANK/WERTE<EIN>WEISUNG
		IOffset=ICount*CEDKPBANKSTEP
		PokeInt IKPBank,IOffset+CEDKPBANKHD,IHD
		PokeFloat IKPBank,IOffset+CEDKPBANKXF,IXP*FScale
		PokeFloat IKPBank,IOffset+CEDKPBANKYF,IYP*FScale
		PokeFloat IKPBank,IOffset+CEDKPBANKXP,IXP*FScale
		PokeFloat IKPBank,IOffset+CEDKPBANKYP,IYP*FScale
		PokeFloat IKPBank,IOffset+CEDKPBANKXS,0
		PokeFloat IKPBank,IOffset+CEDKPBANKYS,0
		PokeFloat IKPBank,IOffset+CEDKPBANKTP,0
		PokeFloat IKPBank,IOffset+CEDKPBANKTS,0
		PokeFloat IKPBank,IOffset+CEDKPBANKRD,IRD*FScale
		PokeFloat IKPBank,IOffset+CEDKPBANKTM,ITM
		PokeFloat IKPBank,IOffset+CEDKPBANKMS,IMS
		PokeInt IKPBank,IOffset+CEDKPBANKID,IID
	Next
	
	
	
	
	;ALLE/VK/EINLESEN
	SeekFile(IFile,IOffset4)
	For ICount=0 To VKSize-1
		
		;DATEIINHALTE<AUS>LESEN
		IK1=ReadByte(IFile)
		IK2=ReadByte(IFile)
		IFD=ReadByte(IFile)
		IDF=ReadByte(IFile)
		IID=ReadByte(IFile)
		
		;BANK/WERTE<AUS>WEISUNG
		LXP1=PeekFloat(IKPBank,IK1*CEDKPBANKSTEP+CEDKPBANKXP)
		LYP1=PeekFloat(IKPBank,IK1*CEDKPBANKSTEP+CEDKPBANKYP)
		LXP2=PeekFloat(IKPBank,IK2*CEDKPBANKSTEP+CEDKPBANKXP)
		LYP2=PeekFloat(IKPBank,IK2*CEDKPBANKSTEP+CEDKPBANKYP)
		
		;BANK/WERTE<EIN>WEISUNG
		IOffset=ICount*CEDVKBANKSTEP
		PokeInt IVKBank,IOffset+CEDVKBANKK1,IK1
		PokeInt IVKBank,IOffset+CEDVKBANKK2,IK2
		PokeFloat IVKBank,IOffset+CEDVKBANKFD,(IFD+1)^2
		PokeFloat IVKBank,IOffset+CEDVKBANKDF,(IDF+1)^2
		PokeFloat IVKBank,IOffset+CEDVKBANKLN,Sqr((LXP1-LXP2)^2+(LYP1-LYP2)^2)
		PokeInt IVKBank,IOffset+CEDVKBANKID,IID
	Next
	
	
	
	
	;ALLE/AM/EINLESEN
	SeekFile(IFile,IOffset5)
	For ICount=0 To AMSize-1
		
		;DATEIINHALTE<AUS>LESEN
		IAT=ReadByte(IFile)
		IID=ReadByte(IFile)
		IFR=ReadByte(IFile)
		ISC=((ReadByte(IFile)-50)*0.01)+1
		IXS=(ReadByte(IFile)-128)*FScale/2.5
		IYS=(ReadByte(IFile)-128)*FScale/2.5
		ITS=(ReadByte(IFile)-128)*FScale/2.5
		IMD=ReadByte(IFile)
		
		If IAT=1 Then ISC=ISC*FScale/2.5
		
		;BANK/WERTE<EIN>WEISUNG
		IOffset=ICount*CEDAMBANKSTEP
		PokeInt IAMBank,IOffset+CEDAMBANKAT,IAT
		PokeInt IAMBank,IOffset+CEDAMBANKID,IID
		PokeInt IAMBank,IOffset+CEDAMBANKFR,IFR+1
		PokeFloat IAMBank,IOffset+CEDAMBANKSC,ISC
		PokeFloat IAMBank,IOffset+CEDAMBANKXS,IXS
		PokeFloat IAMBank,IOffset+CEDAMBANKYS,IYS
		PokeFloat IAMBank,IOffset+CEDAMBANKTS,ITS
		PokeInt IAMBank,IOffset+CEDAMBANKMD,IMD
	Next
	
	;RÜCKZUG/BEREITEN
	CloseFile(IFile)
	Return IBank
	
	
	
	
End Function
Function LoadVED(FDrawFile$,FDrawScale#=1.0,FDrawZStep%=0)
	
	
	
	
	;VARIABLEN/VORPRÜFUNG
	If FileType(FDrawFile)<>1 Then RuntimeError FDrawFile+" not found"
	FDrawScale=FDrawScale*8
	
	;STANDARDVARIABLEN
	Local IDrawPivot%=CreatePivot()
	Local IDrawBank%=CreateBank(12)
	Local IDrawFile%
	Local IDrawName$
	Local IDrawCount%
	Local IDrawValue%
	Local IDrawImage%[1]
	Local IDrawUVScale#
	Local IDrawUSet#
	Local IDrawVSet#
	Local IDrawWSet#
	Local IDrawHSet#
	Local IDrawVerz$
	
	;QUADSVARIABLEN
	Local IDrawDepth%
	Local IDrawFrame%
	Local IDrawLayer%
	Local IDrawCArea%
	Local IDrawFarbe%
	Local IDrawX1#,IDrawY1#
	Local IDrawX2#,IDrawY2#
	Local IDrawX3#,IDrawY3#
	Local IDrawX4#,IDrawY4#
	Local IDrawX5#,IDrawY5#
	Local IDrawX6#,IDrawY6#
	Local IDrawX7#,IDrawY7#
	Local IDrawX8#,IDrawY8#
	
	;MAGISCHE/ZAHL/VERSION/PRÜFEN
	IDrawFile=ReadFile(FDrawFile)
	
	;DO/NOT/CHANGE/THE/MAGIC/KEYWORD>>VED<<!!!
	;MAGISCHES/SCHLÜSSELWORT>>VED<<NICHT/ÄNDERN!!!
	If Chr$(ReadByte(IDrawFile))<>"V" Then RuntimeError "It's not conform VED format"
	If Chr$(ReadByte(IDrawFile))<>"E" Then RuntimeError "It's not conform VED format"
	If Chr$(ReadByte(IDrawFile))<>"D" Then RuntimeError "It's not conform VED format"
	If Chr$(ReadByte(IDrawFile))<>"1" Then RuntimeError "You need VED-Version 1.0"
	
	;BILDDATEINAMEN/LESEN
	IDrawValue=ReadByte(IDrawFile)
	For IDrawCount=1 To IDrawValue
		IDrawName=IDrawName+Chr$(255-ReadByte(IDrawFile))
	Next
	
	;VERZEICHNIS/FESTSTELLEN
	For IDrawCount=1 To Len(FDrawFile)
		If Mid(FDrawFile,IDrawCount,1)="\" Then
			IDrawVerz=Left(FDrawFile,IDrawCount)
		End If
	Next
	
	;BILDDATEI/VORHANDENPRÜFUNG
	If FileType(IDrawVerz+IDrawName)<>1 Then RuntimeError "File "+IDrawVerz+IDrawName+" not found!"
	
	;NEUE/BILDDATEI/LADEN
	IDrawImage[0]=LoadImage3D(IDrawVerz+IDrawName,2,2,IDrawPivot,+800)
	IDrawImage[1]=LoadImage3D(IDrawVerz+IDrawName,2,2,IDrawPivot,-800)
	IDrawUVScale=ImageSize3D(IDrawImage[0])/256.0
	ClearOff3D(IDrawImage[0])
	ClearOff3D(IDrawImage[1])
	
	;ALLE/FRAMES/LESEN
	IDrawValue=ReadByte(IDrawFile)
	For IDrawCount=0 To IDrawValue-1
		IDrawUSet=ReadByte(IDrawFile)*IDrawUVScale
		IDrawVSet=ReadByte(IDrawFile)*IDrawUVScale
		IDrawWSet=ReadByte(IDrawFile)*IDrawUVScale
		IDrawHSet=ReadByte(IDrawFile)*IDrawUVScale
		GrabImage3D(IDrawImage[0],IDrawUSet,IDrawVSet,IDrawWSet,IDrawHSet)
		GrabImage3D(IDrawImage[1],IDrawUSet,IDrawVSet,IDrawWSet,IDrawHSet)
	Next
	
	;KOMPLETTEN/VERLAUF
	While Not Eof(IDrawFile)
		
		;VERLAUF/EINLESEN
		IDrawDepth=ReadByte(IDrawFile)
		IDrawFrame=ReadByte(IDrawFile)
		IDrawLayer=ReadByte(IDrawFile)
		IDrawCArea=ReadByte(IDrawFile)
		IDrawFarbe=ReadInt(IDrawFile)
		IDrawX1=(ReadShort(IDrawFile)-32768)*FDrawScale
		IDrawY1=(ReadShort(IDrawFile)-32768)*FDrawScale
		IDrawX2=(ReadShort(IDrawFile)-32768)*FDrawScale
		IDrawY2=(ReadShort(IDrawFile)-32768)*FDrawScale
		IDrawX3=(ReadShort(IDrawFile)-32768)*FDrawScale
		IDrawY3=(ReadShort(IDrawFile)-32768)*FDrawScale
		IDrawX4=(ReadShort(IDrawFile)-32768)*FDrawScale
		IDrawY4=(ReadShort(IDrawFile)-32768)*FDrawScale
		IDrawX5=(ReadByte(IDrawFile)-128)*FDrawScale
		IDrawY5=(ReadByte(IDrawFile)-128)*FDrawScale
		IDrawX6=(ReadByte(IDrawFile)-128)*FDrawScale
		IDrawY6=(ReadByte(IDrawFile)-128)*FDrawScale
		IDrawX7=(ReadByte(IDrawFile)-128)*FDrawScale
		IDrawY7=(ReadByte(IDrawFile)-128)*FDrawScale
		IDrawX8=(ReadByte(IDrawFile)-128)*FDrawScale
		IDrawY8=(ReadByte(IDrawFile)-128)*FDrawScale
		
		;VERLAUF/EINZEICHNEN
		DeffBzQuad3D(IDrawX1,IDrawY1,IDrawX2,IDrawY2,IDrawX3,IDrawY3,IDrawX4,IDrawY4)
		DeffBzBend3D(IDrawX5,IDrawY5,IDrawX6,IDrawY6,IDrawX7,IDrawY7,IDrawX8,IDrawY8)
		DrawBzQuad3D(IDrawImage[0],2+(IDrawLayer=3)+IDrawFrame*2,IDrawDepth,IDrawFarbe,(3-IDrawLayer)*FDrawZStep)
		If IDrawCArea>0 Then CollBzQuad3D(IDrawCArea,IDrawDepth)
	Wend
	
	;DATEI/SCHLIESSEN
	CloseFile(IDrawFile)
	
	;AUSGABE/VARIABLEN
	PokeInt(IDrawBank,00,IDrawPivot)
	PokeInt(IDrawBank,04,IDrawImage[0])
	PokeInt(IDrawBank,08,IDrawImage[1])
	
	;RÜCKGABEWERT
	Return IDrawBank
	
	
	
	
End Function
Function NewCED()
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IBank%=CreateBank(28)
	PokeInt IBank,CEDKPBANK,CreateBank(0): PokeInt IBank,CEDKPSIZE,0
	PokeInt IBank,CEDVKBANK,CreateBank(0): PokeInt IBank,CEDVKSIZE,0
	PokeInt IBank,CEDAMBANK,CreateBank(0): PokeInt IBank,CEDAMSIZE,0
	Return IBank
	
	
	
	
End Function
Function SetCED(FBank%,FXPos#=0,FYPos#=0,FAngle#=0,FMode%=0)
	
	
	
	
	;HALTE/WERTEEINGRENZUNG
	If FMode<0 Then FMode=0
	If FMode>2 Then FMode=2
	
	;BANK/WERTE<AUS>WEISUNG
	Local LKPBank%=PeekInt(FBank,CEDKPBANK)
	Local LKPSize%=PeekInt(FBank,CEDKPSIZE)
	
	;KP/VARIABLENDEKLARATIONEN
	Local LHD%,LXF#,LYF#,LXP#
	Local LYP#,LXS#,LYS#,LMS#
	
	;VARIABLENDEKLARATIONEN
	Local ICount%
	Local IXP#=0
	Local IYP#=0
	Local IXS#=0
	Local IYS#=0
	Local IMS#=0
	Local IWinkel#=0
	Local IRadius#=0
	
	;POSITION/DURSCHNITT
	For ICount=0 To (LKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
		LXF=PeekFloat(LKPBank,ICount+CEDKPBANKXF)
		LYF=PeekFloat(LKPBank,ICount+CEDKPBANKYF)
		LXP=PeekFloat(LKPBank,ICount+CEDKPBANKXP)
		LYP=PeekFloat(LKPBank,ICount+CEDKPBANKYP)
		LXS=PeekFloat(LKPBank,ICount+CEDKPBANKXS)
		LYS=PeekFloat(LKPBank,ICount+CEDKPBANKYS)
		LMS=PeekFloat(LKPBank,ICount+CEDKPBANKMS)
		IXP=IXP+LXP*LMS
		IYP=IYP+LYP*LMS
		IXS=IXS+LXS*LMS
		IYS=IYS+LYS*LMS
		IMS=IMS+LMS
	Next
	
	;POSITION/DURSCHNITT
	IXP=IXP/IMS
	IYP=IYP/IMS
	IXS=IXS/IMS
	IYS=IYS/IMS
	
	;WAHL/MODUS
	Select FMode
		Case ABSOLUTE
			For ICount=0 To (LKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
				
				;BANK/WERTE<AUS>WEISUNG
				LHD=PeekInt(LKPBank,ICount+CEDKPBANKHD)
				LXF=PeekFloat(LKPBank,ICount+CEDKPBANKXF)
				LYF=PeekFloat(LKPBank,ICount+CEDKPBANKYF)
				
				;VORBEHANDLUNG
				HideEntity LHD
				
				;NEUPOSITION
				PositionEntity LHD,LXF,LYF,0
				
				;BANK/WERTE<EIN>WEISUNG
				PokeFloat LKPBank,ICount+CEDKPBANKXP,LXF
				PokeFloat LKPBank,ICount+CEDKPBANKYP,LYF
				PokeFloat LKPBank,ICount+CEDKPBANKXS,0
				PokeFloat LKPBank,ICount+CEDKPBANKYS,0
				PokeFloat LKPBank,ICount+CEDKPBANKTP,0
				PokeFloat LKPBank,ICount+CEDKPBANKTS,0
				
				;NACHBEHANDLUNG
				ShowEntity LHD
			Next
		Case RELATIVE
			For ICount=0 To (LKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
				
				;BANK/WERTE<AUS>WEISUNG
				LXP=PeekFloat(LKPBank,ICount+CEDKPBANKXP)
				LYP=PeekFloat(LKPBank,ICount+CEDKPBANKYP)
				
				;WERTE/ZWISCHENRECHNUNG
				IWinkel=ATan2(LXP-IXP,LYP-IYP)+FAngle
				IRadius=Sqr((IXP-LXP)^2+(IYP-LYP)^2)
				
				;BANK/WERTE<EIN>WEISUNG
				PokeFloat(LKPBank,ICount+CEDKPBANKXP,IXP+FXPos+Sin(IWinkel)*IRadius)
				PokeFloat(LKPBank,ICount+CEDKPBANKYP,IYP+FYPos+Cos(IWinkel)*IRadius)
;				PokeFloat(LKPBank,ICount+CEDKPBANKXS,IXS)
;				PokeFloat(LKPBank,ICount+CEDKPBANKYS,IYS)
;				PokeFloat(LKPBank,ICount+CEDKPBANKTS,0)
			Next
		Case ACCSPEED
			For ICount=0 To (LKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
				
				;BANK/WERTE<AUS>WEISUNG
				LXP=PeekFloat(LKPBank,ICount+CEDKPBANKXP)
				LYP=PeekFloat(LKPBank,ICount+CEDKPBANKYP)
				LXS=PeekFloat(LKPBank,ICount+CEDKPBANKXS)
				LYS=PeekFloat(LKPBank,ICount+CEDKPBANKYS)
				LMS=PeekFloat(LKPBank,ICount+CEDKPBANKMS)
				
				;WERTE/ZWISCHENRECHNUNG
				IWinkel=ATan2(IXP-LXP,IYP-LYP)
				IRadius=Sqr((IXP-LXP)^2+(IYP-LYP)^2)/LMS
				
				;BANK/WERTE<EIN>WEISUNG
				PokeFloat(LKPBank,ICount+CEDKPBANKXS,LXS+FXPos-(Cos(IWinkel)*IRadius*FAngle)*0.0001)
				PokeFloat(LKPBank,ICount+CEDKPBANKYS,LYS+FYPos+(Sin(IWinkel)*IRadius*FAngle)*0.0001)
			Next
	End Select
	
	
	
	
End Function
Function TurnKPID(FBank%,FID%,FTS#,FBR#=1)
	
	
	
	
	;BANK/WERTE<AUS>WEISUNG
	Local IKPBank%=PeekInt(FBank,CEDKPBANK)
	Local IKPSize%=PeekInt(FBank,CEDKPSIZE)
	
	;VARIABLENDEKLARATIONEN
	Local IKPCount%
	Local IValue#
	
	;SO/ANDERES/ZEUG/MACHEN
	For IKPCount=0 To (IKPSize-CEDKPBANKSTEP) Step CEDKPBANKSTEP
		If FID=PeekInt(IKPBank,IKPCount+CEDKPBANKID) Then
			IValue=PeekFloat(IKPBank,IKPCount+CEDKPBANKTS)
			PokeFloat IKPBank,IKPCount+CEDKPBANKTS,(IValue-FTS)*FBR
		End If
	Next
	
	
	
	
End Function
Function CollBzQuad3D(FCArea%,FDepth%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IAX1#,IAY1#
	Local IBX1#,IBY1#
	Local ICX1#,ICY1#
	Local IAX2#,IAY2#
	Local IBX2#,IBY2#
	Local ICX2#,ICY2#
	Local IXP1#,IYP1#
	Local IXP2#,IYP2#
	Local IXP3#,IYP3#
	Local IXP4#,IYP4#
	Local ISP1#,ISP2#,ISP3#
	Local IQ#,IOver%
	
	;VARIEBLEN/VORBERECHNUNG
	IXP1=GDrawX1*10
	IYP1=GDrawY1*10
	IXP2=GDrawX2*10
	IYP2=GDrawY2*10
	IXP3=GDrawX3*10
	IYP3=GDrawY3*10
	IXP4=GDrawX4*10
	IYP4=GDrawY4*10
	
	;BEZIÉR/VORBEREITUNG
	ICX1=3*(GDrawX5)
	ICY1=3*(GDrawY5)
	IBX1=3*(GDrawX6+GDrawX2-GDrawX1-GDrawX5)-ICX1
	IBY1=3*(GDrawY6+GDrawY2-GDrawY1-GDrawY5)-ICY1
	IAX1=GDrawX2-GDrawX1-ICX1-IBX1
	IAY1=GDrawY2-GDrawY1-ICY1-IBY1
	
	;BEZIÉR/VORBEREITUNG
	ICX2=3*(GDrawX8)
	ICY2=3*(GDrawY8)
	IBX2=3*(GDrawX7+GDrawX3-GDrawX4-GDrawX8)-ICX2
	IBY2=3*(GDrawY7+GDrawY3-GDrawY4-GDrawY8)-ICY2
	IAX2=GDrawX3-GDrawX4-ICX2-IBX2
	IAY2=GDrawY3-GDrawY4-ICY2-IBY2
	
	;WELCHER/DEPTH/MODUS
	If FDepth>0 Then
		
		;QUADS/DURCHGEHEN
		For IQ=0 To FDepth
			ISP1=(IQ-1)/FDepth
			ISP2=(IQ-0)/FDepth
			ISP3=1.0-ISP2
			IXP1=(((IAX1*ISP1+IBX1)*ISP1+ICX1)*ISP1+GDrawX1)*10
			IYP1=(((IAY1*ISP1+IBY1)*ISP1+ICY1)*ISP1+GDrawY1)*10
			IXP2=(((IAX1*ISP2+IBX1)*ISP2+ICX1)*ISP2+GDrawX1)*10
			IYP2=(((IAY1*ISP2+IBY1)*ISP2+ICY1)*ISP2+GDrawY1)*10
			IXP3=(((IAX2*ISP2+IBX2)*ISP2+ICX2)*ISP2+GDrawX4)*10
			IYP3=(((IAY2*ISP2+IBY2)*ISP2+ICY2)*ISP2+GDrawY4)*10
			IXP4=(((IAX2*ISP1+IBX2)*ISP1+ICX2)*ISP1+GDrawX4)*10
			IYP4=(((IAY2*ISP1+IBY2)*ISP1+ICY2)*ISP1+GDrawY4)*10
			
			;ERSTEN/ÜBERLAUFEN
			If IQ>0 Then
				
				;KOLLISIONSLINIEN(BEZIÉRVERLAUF)EINZEICHNEN
				If (FCArea And $1)>0 Then AddCL(0,IXP1,IYP1,IXP2,IYP2)
				If (FCArea And $4)>0 Then AddCL(0,IXP3,IYP3,IXP4,IYP4)
			End If
		Next
	Else
		
		;KOLLISIONSLINIEN(KANTENTEILE)EINZEICHNEN
		If (FCArea And $1)>0 Then AddCL(0,IXP1,IYP1,IXP2,IYP2)
		If (FCArea And $4)>0 Then AddCL(0,IXP3,IYP3,IXP4,IYP4)
	End If
	
	;KOLLISIONSLINIEN(SEITENTEILE)EINZEICHNEN
	If (FCArea And $2)>0 Then AddCL(0,IXP2,IYP2,IXP3,IYP3)
	If (FCArea And $8)>0 Then AddCL(0,IXP4,IYP4,IXP1,IYP1)
	
	
	
	
End Function
Function DrawImageKP(FDrawHandle%,FDrawX#,FDrawY#,FDrawAngle#,FDrawSize#,FDrawFrame%)
	
	
	
	
	FDrawFrame=FDrawFrame*DRAWBANKSTEP
	
	;DrawBank-Variablen-AUSweisung
	Local LDrawFace%=PeekInt(GDrawFaceBank,FDrawHandle+DRAWBANKFACE+FDrawFrame)
	Local LDrawU1Map#=PeekFloat(GDrawFaceBank,FDrawHandle+DRAWBANKU1MP+FDrawFrame)
	Local LDrawV1Map#=PeekFloat(GDrawFaceBank,FDrawHandle+DRAWBANKV1MP+FDrawFrame)
	Local LDrawU2Map#=PeekFloat(GDrawFaceBank,FDrawHandle+DRAWBANKU2MP+FDrawFrame)
	Local LDrawV2Map#=PeekFloat(GDrawFaceBank,FDrawHandle+DRAWBANKV2MP+FDrawFrame)
	
	;Variablen-Vorberechnung
	FDrawSize=FDrawSize*1.41421
	Local IDrawXPos1#=Cos(45+FDrawAngle)*FDrawSize
	Local IDrawYPos1#=Sin(45+FDrawAngle)*FDrawSize
	Local IDrawXPos2#=Cos(45-FDrawAngle)*FDrawSize
	Local IDrawYPos2#=Sin(45-FDrawAngle)*FDrawSize
	
	;Vertex/Ploygon-Zuweisung/Berechnungen
	Local IDrawV0=AddVertex(LDrawFace,FDrawX-IDrawXPos1,FDrawY+IDrawYPos1,0 ,LDrawU1Map,LDrawV1Map)
	Local IDrawV1=AddVertex(LDrawFace,FDrawX+IDrawXPos2,FDrawY+IDrawYPos2,0 ,LDrawU2Map,LDrawV1Map)
	Local IDrawV2=AddVertex(LDrawFace,FDrawX+IDrawXPos1,FDrawY-IDrawYPos1,0 ,LDrawU2Map,LDrawV2Map)
	Local IDrawV3=AddVertex(LDrawFace,FDrawX-IDrawXPos2,FDrawY-IDrawYPos2,0 ,LDrawU1Map,LDrawV2Map)
	VertexColor LDrawFace,IDrawV0,GDrawGFR,GDrawGFG,GDrawGFB,GDrawGFA
	VertexColor LDrawFace,IDrawV1,GDrawGFR,GDrawGFG,GDrawGFB,GDrawGFA
	VertexColor LDrawFace,IDrawV2,GDrawGFR,GDrawGFG,GDrawGFB,GDrawGFA
	VertexColor LDrawFace,IDrawV3,GDrawGFR,GDrawGFG,GDrawGFB,GDrawGFA
	AddTriangle LDrawFace,IDrawV0,IDrawV1,IDrawV2
	AddTriangle LDrawFace,IDrawV2,IDrawV3,IDrawV0
	
	
	
	
End Function


;DrawSED V.1.0c, for Draw3D2 V.1.0
;29.07.2010
;Autor: hectic
;www.hectic.de


;[Block] DRAWSED/VARIABLENDEKLARATIONEN




Global GDrawCountSED%=0

Type TDrawFlare
	Field BD%
	Field LK%
	Field XP#
	Field YP#
	Field XG#
	Field YG#
	Field RP#
	Field RG#
	Field PA#
	Field AS#
End Type

Type TDrawHaupt
	Field ColorA%[31]
	Field ColorR%[31]
	Field ColorG%[31]
	Field ColorB%[31]
	Field ColorS%[31]
	Field Count1%
	Field Count2%
	Field Count3%
	Field Count4%
	Field Speed1%
	Field Speed2%
	Field Speed3%
	Field Speed4%
	Field Angle1%
	Field Angle2%
	Field Angle3%
	Field Angle4%
End Type




;[End Block]
Function DropSED(FBild%,FHnde%,FXPos#,FYPos#,FXAdd#,FYAdd#,FAngle#=0,FSpeed#=10,FAdder#=1)
	
	
	
	
	Local HSED.TDrawHaupt=Object.TDrawHaupt(FHnde)
	
	If FAdder<1 Then FAdder=FAdder-0.2+Rnd(0.0,0.4)
	
	Local IDrawLoops%=(HSED\Count3+(Rand(HSED\Count4,99+HSED\Count4)>99))*FAdder
	Local IDrawSpeed#=0
	Local IDrawAngle#=0
	Local IDrawCount%=0
	Local IDrawFlare.TDrawFlare
	
	;AUTO/PARTIKEL/DÄMPFUNG
	GDrawCountSED=GDrawCountSED+IDrawLoops
	
	;PARTIKEL/NEU/EINSETZEN
	For IDrawCount=1 To IDrawLoops
		IDrawFlare.TDrawFlare=New TDrawFlare
		IDrawFlare\BD=FBild
		IDrawFlare\LK=FHnde
		IDrawFlare\XP=FXPos
		IDrawFlare\YP=FYPos
		IDrawAngle=Rnd(-HSED\Angle1,+HSED\Angle1)
		IDrawSpeed=(FSpeed*(100-HSED\Speed1)+Rnd(FSpeed*HSED\Speed1))*0.01
		IDrawFlare\XG=FXAdd+Sin(IDrawAngle+FAngle)*IDrawSpeed
		IDrawFlare\YG=FYAdd+Cos(IDrawAngle+FAngle)*IDrawSpeed
		IDrawFlare\RG=IDrawAngle*0.2*HSED\Angle3/(HSED\Angle1+1)+Rnd(-HSED\Angle4,+HSED\Angle4)*0.1
		IDrawFlare\RP=Rnd(0,360)
		IDrawFlare\PA=Rnd(0,HSED\Count1)
		IDrawFlare\AS=31.0/HSED\Count2
	Next
	
	
	
	
End Function
Function LoadSED(FFile$)
	
	
	
	
	If FileType(FFile)=0 Then RuntimeError "File "+FFile+" not found!"
	
	Local IFile%=ReadFile(FFile)
	Local HSED.TDrawHaupt
	Local ICount%=0
	
	HSED.TDrawHaupt=New TDrawHaupt
	
	;DO/NOT/CHANGE/THE/MAGIC/KEYWORD>>SED<<!!!
	;MAGISCHES/SCHLÜSSELWORT>>SED<<NICHT/ÄNDERN!!!
	If Chr$(ReadByte(IFile))<>"S" Then RuntimeError "It's not conform SED format"
	If Chr$(ReadByte(IFile))<>"E" Then RuntimeError "It's not conform SED format"
	If Chr$(ReadByte(IFile))<>"D" Then RuntimeError "It's not conform SED format"
	If Chr$(ReadByte(IFile))<>"1" Then RuntimeError "You need SED-Version 1.0"
	
	GDrawXFire=ReadShort(IFile)-32768 ;EDITOR
	GDrawYFire=ReadShort(IFile)-32768 ;EDITOR
	
	For ICount=0 To 31
		HSED\ColorA[ICount]=ReadByte(IFile)
		HSED\ColorR[ICount]=ReadByte(IFile)
		HSED\ColorG[ICount]=ReadByte(IFile)
		HSED\ColorB[ICount]=ReadByte(IFile)
		HSED\ColorS[ICount]=ReadByte(IFile)
	Next
	
	HSED\Count1=ReadShort(IFile)-32768
	HSED\Count2=ReadShort(IFile)-32768
	HSED\Count3=ReadShort(IFile)-32768
	HSED\Count4=ReadShort(IFile)-32768
	HSED\Speed1=ReadShort(IFile)-32768
	HSED\Speed2=ReadShort(IFile)-32768
	HSED\Speed3=ReadShort(IFile)-32768
	HSED\Speed4=ReadShort(IFile)-32768
	HSED\Angle1=ReadShort(IFile)-32768
	HSED\Angle2=ReadShort(IFile)-32768
	HSED\Angle3=ReadShort(IFile)-32768
	HSED\Angle4=ReadShort(IFile)-32768
	
	CloseFile IFile
	
	Return Handle(HSED)
	
	
	
	
End Function
Function AnimSED()
	
	
	
	
	;FARBWERTE/ZWISCHENSPEICHERN
	Local IDrawGFRSave=GDrawGFR
	Local IDrawGFGSave=GDrawGFG
	Local IDrawGFBSave=GDrawGFB
	Local IDrawGFASave=GDrawGFA
	
	;VARIABLENDEKLARATIONEN
	Local IDrawHaupt.TDrawHaupt
	Local IDrawFlare.TDrawFlare
	Local IDrawCount%=0
	Local IDrawTemp#=0
	
	;ZUVIELE/PARTIKEL/LÖSCHEN
	If GDrawCountSED>12000 Then
		For IDrawFlare.TDrawFlare=Each TDrawFlare
			If Rand(0,GDrawCountSED)>12000 Then Delete IDrawFlare
		Next
	End If
	
	;ALLE/PARTIKEL/DURCHGEHEN
	For IDrawFlare.TDrawFlare=Each TDrawFlare
		
		;PARTIKEL/AMZAHL/ZÄHLER
		IDrawCount=IDrawCount+1
		
		;TYPE/REFERENZZIEHEN
		IDrawHaupt.TDrawHaupt=Object.TDrawHaupt(IDrawFlare\LK)
		
		;PARTIKELFARBE/BESTIMMEN
		GDrawGFR=IDrawHaupt\ColorR[IDrawFlare\PA]
		GDrawGFG=IDrawHaupt\ColorG[IDrawFlare\PA]
		GDrawGFB=IDrawHaupt\ColorB[IDrawFlare\PA]
		GDrawGFA=IDrawHaupt\ColorA[IDrawFlare\PA]/255.0
		
		;PARTIKEL/MODUS/AUSSUCHEN
		Select IDrawHaupt\Angle2
			Case 0 IDrawTemp=IDrawHaupt\ColorS[IDrawFlare\PA]*0.1: DrawLine3D(IDrawFlare\BD,IDrawFlare\XP,IDrawFlare\YP,IDrawFlare\XP+IDrawFlare\XG*IDrawTemp,IDrawFlare\YP+IDrawFlare\YG*IDrawTemp,IDrawTemp,0)
			Case 1 IDrawTemp=IDrawHaupt\ColorS[IDrawFlare\PA]*0.2: DrawLine3D(IDrawFlare\BD,IDrawFlare\XP,IDrawFlare\YP,IDrawFlare\XP+IDrawFlare\XG*IDrawTemp,IDrawFlare\YP+IDrawFlare\YG*IDrawTemp,IDrawTemp,1)
			Case 2 DrawLine3D(IDrawFlare\BD,IDrawFlare\XP,IDrawFlare\YP,IDrawFlare\XP+IDrawFlare\XG,IDrawFlare\YP+IDrawFlare\YG,IDrawHaupt\ColorS[IDrawFlare\PA]*0.2,2)
			Case 3 DrawImage3D(IDrawFlare\BD,IDrawFlare\XP,IDrawFlare\YP,0,IDrawFlare\RP,IDrawHaupt\ColorS[IDrawFlare\PA]/85.0)
		End Select
		
		;WERT/ZWISCHENSPEICHER
		IDrawTemp=1-IDrawHaupt\Speed4*0.005
		
		;PARTIKELVERLAUF/BERECHNEN
		IDrawFlare\XP=IDrawFlare\XP+IDrawFlare\XG
		IDrawFlare\YP=IDrawFlare\YP+IDrawFlare\YG
		IDrawFlare\XG=(IDrawFlare\XG+IDrawHaupt\Speed2*0.01)*IDrawTemp
		IDrawFlare\YG=(IDrawFlare\YG+IDrawHaupt\Speed3*0.01)*IDrawTemp
		IDrawFlare\RP=IDrawFlare\RP+IDrawFlare\RG
		IDrawFlare\RG=IDrawFlare\RG*IDrawTemp
		IDrawFlare\PA=IDrawFlare\PA+IDrawFlare\AS
		If IDrawFlare\PA>31 Then Delete IDrawFlare
	Next
	
	;FARBWERTE/ZURÜCKSETZEN
	GDrawGFR=IDrawGFRSave
	GDrawGFG=IDrawGFGSave
	GDrawGFB=IDrawGFBSave
	GDrawGFA=IDrawGFASave
	
	;AUTO/PARTIKEL/DÄMPFUNG
	GDrawCountSED=IDrawCount
	
	;FUNKTION/RÜCKGABEWERT
	Return IDrawCount
	
	
	
	
End Function


;DrawFLK V.1.0, for Draw3D2 V.1.0
;08.12.2009
;Autor: hectic
;www.hectic.de


;[Block] DRAWFLK/VARIABLENDEKLARATIONEN




Global GDrawDimens#
Global GDrawFlakes#
Global GDrawFading#
Local TFlakes.TFlakes

Type TFlakes
	Field XP#
	Field YP#
	Field ZP#
	Field XS#
	Field YS#
	Field ZS#
	Field RT#
End Type




;[End Block}
Function AnimFlakes4D(FHandle%,FXSpeed#,FYSpeed#,FZSpeed#,FMuddle#=0)
	
	
	
	
	;PARAMETER/VORREINIGUNG
	If FMuddle>1 Then FMuddle=1
	If FMuddle<0 Then FMuddle=0
	FMuddle=FMuddle*0.1
	
	;FACEBANK(AUS)WEISUNGEN
	Local LDrawFace%=PeekInt(GDrawFaceBank,FHandle+DRAWBANKFACE)
	Local LDrawXRan#=PeekFloat(GDrawFaceBank,FHandle+DRAWBANKXRAN)*GDrawFlakes
	Local LDrawYRan#=PeekFloat(GDrawFaceBank,FHandle+DRAWBANKYRAN)*GDrawFlakes
	Local LDrawU1MP#=PeekFloat(GDrawFaceBank,FHandle+DRAWBANKU1MP)
	Local LDrawV1MP#=PeekFloat(GDrawFaceBank,FHandle+DRAWBANKV1MP)
	Local LDrawU2MP#=PeekFloat(GDrawFaceBank,FHandle+DRAWBANKU2MP)
	Local LDrawV2MP#=PeekFloat(GDrawFaceBank,FHandle+DRAWBANKV2MP)
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTCos#
	Local IDrawTSin#
	Local IDrawXPos1#
	Local IDrawYPos1#
	Local IDrawXPos2#
	Local IDrawYPos2#
	Local IDrawV0%
	Local IDrawV1%
	Local IDrawV2%
	Local IDrawV3%
	Local TFlakesXForm#
	Local TFlakesYForm#
	Local TFlakesZForm#
	
	;VARIABLEN/VORBERECHNUNGEN
	Local IDrawLinear#=(0.1-FMuddle)*0.1
	Local IDrawXCamera#=EntityX(GDrawCamera)
	Local IDrawYCamera#=EntityY(GDrawCamera)
	Local IDrawZCamera#=EntityZ(GDrawCamera)
	
	;VARIABLEN/VORBERECHNUNGEN
	TFormVector 0,0,GDrawDimens,GDrawCamera,0
	Local IDrawXFormed#=TFormedX()
	Local IDrawYFormed#=TFormedY()
	Local IDrawZFormed#=TFormedZ()
	
	;VARIABLENDEKLARATIONEN
	Local TFlakes.TFlakes
	Local IDrawDistanz#
	Local IDrawGFA#
	
	;ALLE/FLAKES/ZEICHNEN
	For TFlakes.TFlakes=Each TFlakes
		
		;FLAKES/GESCHWINDIGKEIT
		TFlakes\XS=TFlakes\XS+Rnd(-FMuddle,+FMuddle)-TFlakes\XS*IDrawLinear
		TFlakes\YS=TFlakes\YS+Rnd(-FMuddle,+FMuddle)-TFlakes\YS*IDrawLinear
		TFlakes\ZS=TFlakes\ZS+Rnd(-FMuddle,+FMuddle)-TFlakes\ZS*IDrawLinear
		
		;FLAKES/POSITION
		TFlakes\XP=TFlakes\XP+TFlakes\XS+FXSpeed
		TFlakes\YP=TFlakes\YP+TFlakes\YS+FYSpeed
		TFlakes\ZP=TFlakes\ZP+TFlakes\ZS+FZSpeed
		
		;FLAKES/RAUMGRENZEN
		If (TFlakes\XP-IDrawXCamera-IDrawXFormed)>+GDrawDimens Then TFlakes\XP=TFlakes\XP-GDrawDimens*2
		If (TFlakes\XP-IDrawXCamera-IDrawXFormed)<-GDrawDimens Then TFlakes\XP=TFlakes\XP+GDrawDimens*2
		If (TFlakes\YP-IDrawYCamera-IDrawYFormed)>+GDrawDimens Then TFlakes\YP=TFlakes\YP-GDrawDimens*2
		If (TFlakes\YP-IDrawYCamera-IDrawYFormed)<-GDrawDimens Then TFlakes\YP=TFlakes\YP+GDrawDimens*2
		If (TFlakes\ZP-IDrawZCamera-IDrawZFormed)>+GDrawDimens Then TFlakes\ZP=TFlakes\ZP-GDrawDimens*2
		If (TFlakes\ZP-IDrawZCamera-IDrawZFormed)<-GDrawDimens Then TFlakes\ZP=TFlakes\ZP+GDrawDimens*2
		
		;ENTFERNUNG/FÜR/TRANSPARENZ
		IDrawDistanz=Sqr((IDrawXCamera-TFlakes\XP)*(IDrawXCamera-TFlakes\XP)+(IDrawYCamera-TFlakes\YP)*(IDrawYCamera-TFlakes\YP)+(IDrawZCamera-TFlakes\ZP)*(IDrawZCamera-TFlakes\ZP))*0.55
		
		;TRANSPARENZ/AUSRECHNEN
		IDrawGFA=GDrawFading*(GDrawDimens/IDrawDistanz)-GDrawFading
		
		;RAUMPROJEKTION/VON: 'aMul'
		TFormPoint(TFlakes\XP,TFlakes\YP,TFlakes\ZP,0,GDrawCamera)
		
		;KAMERA/Z-POSITION
		TFlakesZForm=TFormedZ()
		
		;SICHTBARKEIT/PRÜFEN
		If TFlakesZForm>0 Then
			
			;TRANSFORMATION
			TFlakesXForm=TFormedX()
			TFlakesYForm=TFormedY()
			
			;SCHNELLE/UMRECHNUNG/VON: 'aMul'
			IDrawTCos#=Cos(TFlakes\RT)
			IDrawTSin#=Sin(TFlakes\RT)
			IDrawXPos1#=LDrawXRan*IDrawTCos-LDrawYRan*IDrawTSin
			IDrawYPos1#=LDrawYRan*IDrawTCos+LDrawXRan*IDrawTSin
			IDrawXPos2#=LDrawXRan*IDrawTCos+LDrawYRan*IDrawTSin
			IDrawYPos2#=LDrawYRan*IDrawTCos-LDrawXRan*IDrawTSin
			
			;VERTEX/POLYGON/ZUWEISUNGEN
			IDrawV0%=AddVertex(LDrawFace,TFlakesXForm-IDrawXPos1,TFlakesYForm+IDrawYPos1,TFlakesZForm ,LDrawU1MP,LDrawV1MP)
			IDrawV1%=AddVertex(LDrawFace,TFlakesXForm+IDrawXPos2,TFlakesYForm+IDrawYPos2,TFlakesZForm ,LDrawU2MP,LDrawV1MP)
			IDrawV2%=AddVertex(LDrawFace,TFlakesXForm+IDrawXPos1,TFlakesYForm-IDrawYPos1,TFlakesZForm ,LDrawU2MP,LDrawV2MP)
			IDrawV3%=AddVertex(LDrawFace,TFlakesXForm-IDrawXPos2,TFlakesYForm-IDrawYPos2,TFlakesZForm,LDrawU1MP,LDrawV2MP)
			VertexColor LDrawFace,IDrawV0,GDrawGFR,GDrawGFG,GDrawGFB,IDrawGFA
			VertexColor LDrawFace,IDrawV1,GDrawGFR,GDrawGFG,GDrawGFB,IDrawGFA
			VertexColor LDrawFace,IDrawV2,GDrawGFR,GDrawGFG,GDrawGFB,IDrawGFA
			VertexColor LDrawFace,IDrawV3,GDrawGFR,GDrawGFG,GDrawGFB,IDrawGFA
			AddTriangle LDrawFace,IDrawV0,IDrawV1,IDrawV2
			AddTriangle LDrawFace,IDrawV2,IDrawV3,IDrawV0
		EndIf
	Next
	
	
	
	
End Function
Function AnimRifled4D(FHandle%,FXSpeed#,FYSpeed#,FZSpeed#,FMuddle#=0,FExpand#=2)
	
	
	
	
	;PARAMETER/VORREINIGUNG
	If FMuddle>1 Then FMuddle=1
	If FMuddle<0 Then FMuddle=0
	FMuddle=FMuddle*0.1
	
	;FACEBANK(AUS)WEISUNGEN
	Local LDrawFace%=PeekInt(GDrawFaceBank,FHandle+DRAWBANKFACE)
	Local LDrawXRan#=PeekFloat(GDrawFaceBank,FHandle+DRAWBANKXRAN)*GDrawFlakes
	Local LDrawU1MP#=PeekFloat(GDrawFaceBank,FHandle+DRAWBANKU1MP)
	Local LDrawV1MP#=PeekFloat(GDrawFaceBank,FHandle+DRAWBANKV1MP)
	Local LDrawU2MP#=PeekFloat(GDrawFaceBank,FHandle+DRAWBANKU2MP)
	Local LDrawV2MP#=PeekFloat(GDrawFaceBank,FHandle+DRAWBANKV2MP)
	
	;VARIABLENDEKLARATIONEN
	Local IDrawX1Pos#
	Local IDrawY1Pos#
	Local IDrawZ1Pos#
	Local IDrawX2Pos#
	Local IDrawY2Pos#
	Local IDrawZ2Pos#
	Local IDrawLX#
	Local IDrawLY#
	Local IDrawLZ#
	Local IDrawNX#
	Local IDrawNY#
	Local IDrawNZ#
	Local IDrawLN#
	Local IDrawV0%
	Local IDrawV1%
	Local IDrawV2%
	Local IDrawV3%
	
	;VARIABLEN/VORBERECHNUNGEN
	Local IDrawLinear#=(0.1-FMuddle)*0.1
	Local IDrawXCamera#=EntityX(GDrawCamera)
	Local IDrawYCamera#=EntityY(GDrawCamera)
	Local IDrawZCamera#=EntityZ(GDrawCamera)
	
	;VARIABLEN/VORBERECHNUNGEN
	TFormVector 0,0,GDrawDimens,GDrawCamera,0
	Local IDrawXFormed#=TFormedX()
	Local IDrawYFormed#=TFormedY()
	Local IDrawZFormed#=TFormedZ()
	
	;VARIABLENDEKLARATIONEN
	Local TFlakes.TFlakes
	Local IDrawDistanz#
	Local IDrawGFA#
	
	;ALLE/FLAKES/ZEICHNEN
	For TFlakes.TFlakes=Each TFlakes
		
		;FLAKES/GESCHWINDIGKEIT
		TFlakes\XS=TFlakes\XS+Rnd(-FMuddle,+FMuddle)-TFlakes\XS*IDrawLinear
		TFlakes\YS=TFlakes\YS+Rnd(-FMuddle,+FMuddle)-TFlakes\YS*IDrawLinear
		TFlakes\ZS=TFlakes\ZS+Rnd(-FMuddle,+FMuddle)-TFlakes\ZS*IDrawLinear
		
		;FLAKES/POSITION
		TFlakes\XP=TFlakes\XP+TFlakes\XS+FXSpeed
		TFlakes\YP=TFlakes\YP+TFlakes\YS+FYSpeed
		TFlakes\ZP=TFlakes\ZP+TFlakes\ZS+FZSpeed
		
		;FLAKES/RAUMGRENZEN
		If (TFlakes\XP-IDrawXCamera-IDrawXFormed)>+GDrawDimens Then TFlakes\XP=TFlakes\XP-GDrawDimens*2
		If (TFlakes\XP-IDrawXCamera-IDrawXFormed)<-GDrawDimens Then TFlakes\XP=TFlakes\XP+GDrawDimens*2
		If (TFlakes\YP-IDrawYCamera-IDrawYFormed)>+GDrawDimens Then TFlakes\YP=TFlakes\YP-GDrawDimens*2
		If (TFlakes\YP-IDrawYCamera-IDrawYFormed)<-GDrawDimens Then TFlakes\YP=TFlakes\YP+GDrawDimens*2
		If (TFlakes\ZP-IDrawZCamera-IDrawZFormed)>+GDrawDimens Then TFlakes\ZP=TFlakes\ZP-GDrawDimens*2
		If (TFlakes\ZP-IDrawZCamera-IDrawZFormed)<-GDrawDimens Then TFlakes\ZP=TFlakes\ZP+GDrawDimens*2
		
		;ENTFERNUNG/FÜR/TRANSPARENZ
		IDrawDistanz=Sqr((IDrawXCamera-TFlakes\XP)*(IDrawXCamera-TFlakes\XP)+(IDrawYCamera-TFlakes\YP)*(IDrawYCamera-TFlakes\YP)+(IDrawZCamera-TFlakes\ZP)*(IDrawZCamera-TFlakes\ZP))*0.55
		
		;TRANSPARENZ/AUSRECHNEN
		IDrawGFA=GDrawFading*(GDrawDimens/IDrawDistanz)-GDrawFading
		
		;RAUM/KAMERA/TRANSFORMATION
		TFormPoint(TFlakes\XP-(TFlakes\XS+FXSpeed)*FExpand,TFlakes\YP-(TFlakes\YS+FYSpeed)*FExpand,TFlakes\ZP-(TFlakes\ZS+FZSpeed)*FExpand,0,GDrawCamera)
		IDrawX1Pos=TFormedX()
		IDrawY1Pos=TFormedY()
		IDrawZ1Pos=TFormedZ()
		
		;RAUM/KAMERA/TRANSFORMATION
		TFormPoint(TFlakes\XP+(TFlakes\XS+FXSpeed)*FExpand,TFlakes\YP+(TFlakes\YS+FYSpeed)*FExpand,TFlakes\ZP+(TFlakes\ZS+FZSpeed)*FExpand,0,GDrawCamera)
		IDrawX2Pos=TFormedX()
		IDrawY2Pos=TFormedY()
		IDrawZ2Pos=TFormedZ()
		
		;KREUZPRODUKT/BERECHNUNGEN
		IDrawLX=IDrawX1Pos-IDrawX2Pos
		IDrawLY=IDrawY1Pos-IDrawY2Pos
		IDrawLZ=IDrawZ1Pos-IDrawZ2Pos
		
		;KREUZPRODUKT/BERECHNUNGEN
		IDrawNX=(IDrawLY*IDrawZ2Pos)-(IDrawLZ*IDrawY2Pos)
		IDrawNY=(IDrawLZ*IDrawX2Pos)-(IDrawLX*IDrawZ2Pos)
		IDrawNZ=(IDrawLX*IDrawY2Pos)-(IDrawLY*IDrawX2Pos)
		
		;KREUZPRODUKT/BERECHNUNGEN
		IDrawLN=Sqr(IDrawNX*IDrawNX+IDrawNY*IDrawNY+IDrawNZ*IDrawNZ)
		IDrawNX=IDrawNX/IDrawLN*LDrawXRan
		IDrawNY=IDrawNY/IDrawLN*LDrawXRan
		IDrawNZ=IDrawNZ/IDrawLN*LDrawXRan
		
		;VERTEX/POLYGON/ZUWEISUNGEN
		IDrawV0=AddVertex(LDrawFace,IDrawX1Pos+IDrawNX,IDrawY1Pos+IDrawNY,IDrawZ1Pos+IDrawNZ ,LDrawU1MP,LDrawV1MP)
		IDrawV1=AddVertex(LDrawFace,IDrawX2Pos+IDrawNX,IDrawY2Pos+IDrawNY,IDrawZ2Pos+IDrawNZ ,LDrawU2MP,LDrawV1MP)
		IDrawV2=AddVertex(LDrawFace,IDrawX2Pos-IDrawNX,IDrawY2Pos-IDrawNY,IDrawZ2Pos-IDrawNZ ,LDrawU2MP,LDrawV2MP)
		IDrawV3=AddVertex(LDrawFace,IDrawX1Pos-IDrawNX,IDrawY1Pos-IDrawNY,IDrawZ1Pos-IDrawNZ ,LDrawU1MP,LDrawV2MP)
		
		;VERTEX/POLYGON/ZUWEISUNGEN
		VertexColor LDrawFace,IDrawV0,GDrawGFR,GDrawGFG,GDrawGFB,IDrawGFA
		VertexColor LDrawFace,IDrawV1,GDrawGFR,GDrawGFG,GDrawGFB,IDrawGFA
		VertexColor LDrawFace,IDrawV2,GDrawGFR,GDrawGFG,GDrawGFB,IDrawGFA
		VertexColor LDrawFace,IDrawV3,GDrawGFR,GDrawGFG,GDrawGFB,IDrawGFA
		AddTriangle(LDrawFace,IDrawV0,IDrawV1,IDrawV2)
		AddTriangle(LDrawFace,IDrawV2,IDrawV3,IDrawV0)
	Next
	
	
	
	
End Function
Function CreateFlakes4D(FFlakesCount%,FFlakesDimens#,FFlakesFading#=1,FFlakesFlakes#=0.02)
	
	
	
	
	;SICHERHEITSPRÜFUNG
	If FFlakesCount>8000 Then RuntimeError "CreateFlakes4D"+Chr$(10)+Chr$(10)+"Do not create more than 8'000 Flakes!"
	
	;GLOBALENZUWEISUNGEN
	GDrawDimens=FFlakesDimens
	GDrawFading=FFlakesFading
	GDrawFlakes=FFlakesFlakes
	
	;VARIABLENDEKLARATIONEN
	Local TFlakes.TFlakes
	Local IDrawCount%
	
	;FLAKES/ERSTELLEN
	For IDrawCount=1 To FFlakesCount
		
		;NEUE/FLAKES/YEEAAH!
		TFlakes.TFlakes=New TFlakes
		TFlakes\XP=Rnd(-FFlakesDimens,+FFlakesDimens)
		TFlakes\YP=Rnd(-FFlakesDimens,+FFlakesDimens)
		TFlakes\ZP=Rnd(-FFlakesDimens,+FFlakesDimens)
		TFlakes\RT=Rnd(0,360)
	Next
	
	
	
	
End Function


;~IDEal Editor Parameters:
;~F#B#50#7D#94#BD#E4#FF#1C0#1F5#212#26F#28E#2A2#2C2#2E3#2F2#3D4#457#467#4DD
;~F#4F6#54B#578#5A4#5CB#5FF#654#65E#66C#6E1#761
;~C#Blitz3D