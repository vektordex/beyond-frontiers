

;DrawSGG V.1.1, for Draw3D2 V.1.1
;28.09.2010
;Autor: hectic
;www.hectic.de


;[Block] DRAWSGG/VARIABLENDEKLARATIONEN




Const SGGOUTLAY%=1
Const SGGBUTTON%=2
Const SGGINTAKE%=3
Const SGGROSTER%=4
Const SGGSLIDER%=5

Global GDrawS1#=0
Global GDrawS2#=0
Global GDrawS3#=0
Global GDrawS4#=0
Global GDrawS5#=0
Global GDrawS6#=0
Global GDrawS7#=0
Global GDrawS8#=0
Global GDrawPick%=0
Global GDrawInta%=0
Global GDrawTran%=0
Global GDrawSlip%=0
Global SGGOver3D%=0
Global SGGDown3D%=0
Global SGGHit3D%=0
Global SGGSpz3D%=0
Global SGGKey3D%=0
Global SGGEntry%=0

Type SGGHaupt
	Field Bank%
	Field XPos%
	Field YPos%
	Field XRan%
	Field YRan%
	Field Face%
	Field Back%
	Field Glow%
	Field Font%
	Field Str1%
	Field XSpc%
	Field YSpc%
	Field ButtonUV%[17]
	Field IntakeUV%[17]
	Field RosterUV%[19]
	Field SliderUV%[17]
End Type
Type SGGNeben
	Field Arts% ;OUTLAY/BUTTON/ROSTER/INTAKE/SLIDER
	Field XPos% ;X/POSITION
	Field YPos% ;Y/POSITION
	Field Algn% ;TEXT/ALIGN
	Field XRan% ;X/RANGE
	Field SVal% ;SLIDER/VALUE
	Field Bffs% ;AUSWAHLPOSITION
	Field Cffs% ;POSITION/ANZAHL
	Field Sffs% ;SCROLL/POSITION
	Field Bank% ;DAS/BANK/HANDLE
End Type
Type SGGUnter
	Field Item$
End Type




;[End Block]
Function AddingSGG%(FDrawHnde%,FDrawArts%,FDrawXPos%,FDrawYPos%,FDrawStrg$,FDrawAlgn%=1,FDrawXRan%=0,FDRawSVal%=0)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTH.SGGHaupt
	Local IDrawTN.SGGNeben
	
	;HAUPT/TYPE/ID/AUFGREIFEN
	IDrawTH.SGGHaupt=Object.SGGHaupt(FDrawHnde)
	
	;HAUPT/TYPE/ERSTELLEN
	IDrawTN.SGGNeben=New SGGNeben
	IDrawTN\Arts=FDrawArts
	IDrawTN\XPos=FDrawXPos+IDrawTH\XPos
	IDrawTN\YPos=FDrawYPos+IDrawTH\YPos
	IDrawTN\Algn=FDrawAlgn
	IDrawTN\XRan=FDrawXRan
	IDrawTN\SVal=FDRawSVal
	IDrawTN\Bffs=0
	IDrawTN\Cffs=-1
	IDrawTN\Bank=CreateBank(0)
	
	;CAPTION/ITEM/ZUWEISUNG
	AppendSGG(Handle(IDrawTN),FDrawStrg)
	
	;HAUPT/BANK/ID/ANPASSEN
	ResizeBank IDrawTH\Bank,4+BankSize(IDrawTH\Bank)
	PokeInt IDrawTH\Bank,BankSize(IDrawTH\Bank)-4,Handle(IDrawTN)
	
	;RÜCKGABEWERT
	Return Handle(IDrawTN)
	
	
	
	
End Function
Function AppendSGG%(FDrawHnde%,FDrawStrg$)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	Local IDrawTU.SGGUnter
	Local IDrawEiner$=""
	Local IDrawAlles$=""
	Local IDrawValue%=0
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;STRING/DURCHSTÖBERN
	While Not IDrawValue=Len(FDrawStrg)
		
		;NÄCHSTES/ZEICHEN
		IDrawValue=IDrawValue+1
		
		;EIN/ZEICHEN/ANHÄNGEN
		IDrawEiner=Mid(FDrawStrg,IDrawValue,1)
		IDrawAlles=IDrawAlles+IDrawEiner
		
		;TEILER/SUCHEN/ANWENDEN
		If IDrawEiner="|" Or IDrawValue=Len(FDrawStrg) Then
			
			;STRINGEINTRAG/ERSTELLEN
			IDrawTU.SGGUnter=New SGGUnter
			IDrawTU\Item=Replace(IDrawAlles,"|","")
			
			;SICHERHEITSANPASSUNGEN
			IDrawTN\Cffs=BankSize(IDrawTN\Bank)/4
			IDrawTN\Sffs=0
			
			;NEBEN/BANK/ID/ANPASSEN
			ResizeBank IDrawTN\Bank,4+BankSize(IDrawTN\Bank)
			PokeInt IDrawTN\Bank,BankSize(IDrawTN\Bank)-4,Handle(IDrawTU)
			
			;SAMMLERRESET
			IDrawAlles=""
		End If
	Wend
	
	;LEERER/EINGANG
	If Len(FDrawStrg)=0 Then
		
		;STRINGEINTRAG/ERSTELLEN
		IDrawTU.SGGUnter=New SGGUnter
		IDrawTU\Item=""
		
		;NEBEN/BANK/ID/ANPASSEN
		ResizeBank IDrawTN\Bank,4+BankSize(IDrawTN\Bank)
		PokeInt IDrawTN\Bank,BankSize(IDrawTN\Bank)-4,Handle(IDrawTU)
	End If
	
	
	
	
End Function
Function CreateSGG%(FDrawXPos%,FDrawYPos%,FDrawXRan%,FDrawYRan%,FDrawFile$,FDrawPvot%=0,FDrawOrdr%=0)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTH.SGGHaupt
	Local FDrawFileFolder$
	Local IDrawCount%=0
	Local IDrawLoad%=0
	
	;HAUPT/TYPE/ERSTELLEN
	IDrawTH.SGGHaupt=New SGGHaupt 
	IDrawTH\Bank=CreateBank(0)
	IDrawTH\XPos=FDrawXPos
	IDrawTH\YPos=FDrawYPos
	IDrawTH\XRan=FDrawXRan
	IDrawTH\YRan=FDrawYRan
	
	;ALLGEMEINE/FEHLERBEHANDLUNG
	For IDrawCount=Len(FDrawFile)-1 To 1 Step-1
		If Mid(FDrawFile,IDrawCount,1)="/" Or Mid(FDrawFile,IDrawCount,1)="\"
			FDrawFileFolder=Left(FDrawFile,IDrawCount)
			Exit
		End If
	Next
	
	;ALLGEMEINE/FEHLERBEHANDLUNG
	If FileType(FDrawFileFolder+ParseSGG(FDrawFile,"LOADIMAGE3D"))<>1 Then RuntimeError "CreateSGG"+Chr$(10)+Chr$(10)+"typed file in ''"+FDrawFile+"'' Not found!"+Chr(10)+FDrawFileFolder+ParseSGG(FDrawFile,"LOADIMAGE3D")
	
	;SGG/MAINIMAGE/LADEN
	IDrawLoad=LoadImage3D(FDrawFileFolder+ParseSGG(FDrawFile,"LOADIMAGE3D"),2,2,FDrawPvot,FDrawOrdr)
	
	;FACE/WERTEZUWEISUNGEN
	IDrawTH\Face=PeekInt(GDrawFaceBank,IDrawLoad+DRAWBANKFACE)
	
	;SGG/WERTEZUWEISUNGEN
	ParseSGG(FDrawFile,"BACKIMAGE"): IDrawTH\Back=GrabImage3D(IDrawLoad,GDrawS1,GDrawS2,GDrawS3,GDrawS4)
	ParseSGG(FDrawFile,"GLOWIMAGE"): IDrawTH\Glow=GrabImage3D(IDrawLoad,GDrawS1,GDrawS2,GDrawS3,GDrawS4)
	
	;FONT/WERTEZUWEISUNGEN
	ParseSGG(FDrawFile,"FONTRANGE3D"): IDrawTH\Font=FontRange3D(IDrawLoad,GDrawS1,GDrawS2,GDrawS3,GDrawS4,GDrawS5)
	ParseSGG(FDrawFile,"SETFONT3D"): SetFont3D(IDrawTH\Font,GDrawS1,GDrawS2,GDrawS3,GDrawS4)
	IDrawTH\Str1=StringHeight3D(IDrawTH\Font)
	
	;REST/WERTEZUWEISUNGEN
	ParseSGG(FDrawFile,"XYOFFSETS"): IDrawTH\XSpc=GDrawS1: IDrawTH\YSpc=GDrawS2
	
	;SGG/BUTTON/MAIN-UV-MAPPING
	ParseSGG(FDrawFile,"BUTTON_MAIN")
	IDrawTH\ButtonUV[00]=GDrawS1
	IDrawTH\ButtonUV[01]=GDrawS1+GDrawS2
	IDrawTH\ButtonUV[02]=GDrawS1+GDrawS2+GDrawS3
	IDrawTH\ButtonUV[03]=GDrawS1+GDrawS2+GDrawS3+GDrawS4
	IDrawTH\ButtonUV[12]=GDrawS5
	IDrawTH\ButtonUV[13]=GDrawS5+GDrawS6
	
	;SGG/BUTTON/GLOW-UV-MAPPING
	ParseSGG(FDrawFile,"BUTTON_GLOW")
	IDrawTH\ButtonUV[04]=GDrawS1
	IDrawTH\ButtonUV[05]=GDrawS1+GDrawS2
	IDrawTH\ButtonUV[06]=GDrawS1+GDrawS2+GDrawS3
	IDrawTH\ButtonUV[07]=GDrawS1+GDrawS2+GDrawS3+GDrawS4
	IDrawTH\ButtonUV[14]=GDrawS5
	IDrawTH\ButtonUV[15]=GDrawS5+GDrawS6
	
	;SGG/BUTTON/DOWN-UV-MAPPING
	ParseSGG(FDrawFile,"BUTTON_DOWN")
	IDrawTH\ButtonUV[08]=GDrawS1
	IDrawTH\ButtonUV[09]=GDrawS1+GDrawS2
	IDrawTH\ButtonUV[10]=GDrawS1+GDrawS2+GDrawS3
	IDrawTH\ButtonUV[11]=GDrawS1+GDrawS2+GDrawS3+GDrawS4
	IDrawTH\ButtonUV[16]=GDrawS5
	IDrawTH\ButtonUV[17]=GDrawS5+GDrawS6
	
	;SGG/INTAKE/MAIN-UV-MAPPING
	ParseSGG(FDrawFile,"INTAKE_MAIN")
	IDrawTH\IntakeUV[00]=GDrawS1
	IDrawTH\IntakeUV[01]=GDrawS1+GDrawS2
	IDrawTH\IntakeUV[02]=GDrawS1+GDrawS2+GDrawS3
	IDrawTH\IntakeUV[03]=GDrawS1+GDrawS2+GDrawS3+GDrawS4
	IDrawTH\IntakeUV[12]=GDrawS5
	IDrawTH\IntakeUV[13]=GDrawS5+GDrawS6
	
	;SGG/INTAKE/GLOW-UV-MAPPING
	ParseSGG(FDrawFile,"INTAKE_GLOW")
	IDrawTH\IntakeUV[04]=GDrawS1
	IDrawTH\IntakeUV[05]=GDrawS1+GDrawS2
	IDrawTH\IntakeUV[06]=GDrawS1+GDrawS2+GDrawS3
	IDrawTH\IntakeUV[07]=GDrawS1+GDrawS2+GDrawS3+GDrawS4
	IDrawTH\IntakeUV[14]=GDrawS5
	IDrawTH\IntakeUV[15]=GDrawS5+GDrawS6
	
	;SGG/INTAKE/DOWN-UV-MAPPING
	ParseSGG(FDrawFile,"INTAKE_DOWN")
	IDrawTH\IntakeUV[08]=GDrawS1
	IDrawTH\IntakeUV[09]=GDrawS1+GDrawS2
	IDrawTH\IntakeUV[10]=GDrawS1+GDrawS2+GDrawS3
	IDrawTH\IntakeUV[11]=GDrawS1+GDrawS2+GDrawS3+GDrawS4
	IDrawTH\IntakeUV[16]=GDrawS5
	IDrawTH\IntakeUV[17]=GDrawS5+GDrawS6
	
	;SGG/ROSTER/MAIN-UV-MAPPING
	ParseSGG(FDrawFile,"ROSTER_MAIN")
	IDrawTH\RosterUV[00]=GDrawS1
	IDrawTH\RosterUV[01]=GDrawS1+GDrawS2
	IDrawTH\RosterUV[02]=GDrawS1+GDrawS2+GDrawS3
	IDrawTH\RosterUV[03]=GDrawS1+GDrawS2+GDrawS3+GDrawS4
	IDrawTH\RosterUV[12]=GDrawS5
	IDrawTH\RosterUV[13]=GDrawS5+GDrawS6
	IDrawTH\RosterUV[14]=GDrawS5+GDrawS6+GDrawS7
	IDrawTH\RosterUV[15]=GDrawS5+GDrawS6+GDrawS7+GDrawS8
	
	;SGG/ROSTER/GLOW-UV-MAPPING
	ParseSGG(FDrawFile,"ROSTER_GLOW")
	IDrawTH\RosterUV[04]=GDrawS1
	IDrawTH\RosterUV[05]=GDrawS1+GDrawS2
	IDrawTH\RosterUV[06]=GDrawS1+GDrawS2+GDrawS3
	IDrawTH\RosterUV[07]=GDrawS1+GDrawS2+GDrawS3+GDrawS4
	IDrawTH\RosterUV[16]=GDrawS5
	IDrawTH\RosterUV[17]=GDrawS5+GDrawS6
	
	;SGG/ROSTER/DOWN-UV-MAPPING
	ParseSGG(FDrawFile,"ROSTER_DOWN")
	IDrawTH\RosterUV[08]=GDrawS1
	IDrawTH\RosterUV[09]=GDrawS1+GDrawS2
	IDrawTH\RosterUV[10]=GDrawS1+GDrawS2+GDrawS3
	IDrawTH\RosterUV[11]=GDrawS1+GDrawS2+GDrawS3+GDrawS4
	IDrawTH\RosterUV[18]=GDrawS5
	IDrawTH\RosterUV[19]=GDrawS5+GDrawS6
	
	;SGG/SLIDER/MAIN-UV-MAPPING
	ParseSGG(FDrawFile,"SLIDER_MAIN")
	IDrawTH\SliderUV[00]=GDrawS1
	IDrawTH\SliderUV[01]=GDrawS1+GDrawS2
	IDrawTH\SliderUV[10]=GDrawS3
	IDrawTH\SliderUV[11]=GDrawS3+GDrawS4
	
	;SGG/SLIDER/LINE-UV-MAPPING
	ParseSGG(FDrawFile,"SLIDER_LINE")
	IDrawTH\SliderUV[02]=GDrawS1
	IDrawTH\SliderUV[03]=GDrawS1+GDrawS2
	IDrawTH\SliderUV[04]=GDrawS1+GDrawS2+GDrawS3
	IDrawTH\SliderUV[05]=GDrawS1+GDrawS2+GDrawS3+GDrawS4
	IDrawTH\SliderUV[12]=GDrawS5
	IDrawTH\SliderUV[13]=GDrawS5+GDrawS6
	
	;SGG/SLIDER/GLOW-UV-MAPPING
	ParseSGG(FDrawFile,"SLIDER_GLOW")
	IDrawTH\SliderUV[06]=GDrawS1
	IDrawTH\SliderUV[07]=GDrawS1+GDrawS2
	IDrawTH\SliderUV[14]=GDrawS3
	IDrawTH\SliderUV[15]=GDrawS3+GDrawS4
	
	;SGG/SLIDER/DOWN-UV-MAPPING
	ParseSGG(FDrawFile,"SLIDER_DOWN")
	IDrawTH\SliderUV[08]=GDrawS1
	IDrawTH\SliderUV[09]=GDrawS1+GDrawS2
	IDrawTH\SliderUV[16]=GDrawS3
	IDrawTH\SliderUV[17]=GDrawS3+GDrawS4
	
	;RÜCKGABEWERT
	Return Handle(IDrawTH)
	
	
	
	
End Function
Function DeleteSGG%(FDrawHnde%,FDrawSPos%=0)
	
	
	
	
	;VORABSICHERUNGSPRÜFUNG
	If FDrawSPos<0 Then FDrawSPos=0
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	Local IDrawTU.SGGUnter
	Local IDrawCount%
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;PRÜFEN/OB/GÜLTIG/LÖSCHBAR
	If BankSize(IDrawTN\Bank)>4 Then
		
		;PRÜFEN/OB/GÜLTIG/LÖSCHBAR
		If BankSize(IDrawTN\Bank)>(FDrawSPos*4) Then
			
			;UNTER/TYPE/ID/AUFGREIFEN
			IDrawTU.SGGUnter=Object.SGGUnter(PeekInt(IDrawTN\Bank,FDrawSPos*4))
			
			;UNTER/TYPE/ID/LÖSCHEN
			Delete IDrawTU.SGGUnter
			
			;NEBEN/TYPE/ID/EINRÜCKEN
			For IDrawCount=FDrawSPos*4 To BankSize(IDrawTN\Bank)-8 Step 4
				PokeInt(IDrawTN\Bank,IDrawCount,PeekInt(IDrawTN\Bank,IDrawCount+4))
			Next
			
			;NEBEN/BANK/ID/ANPASSEN
			ResizeBank IDrawTN\Bank,BankSize(IDrawTN\Bank)-4
			IDrawTN\Cffs=(BankSize(IDrawTN\Bank)/4)-1
			
			;SICHERHEIT/DURCHFÜHREN
			AddSelectSGG(FDrawHnde,-1)
			AddSelectSGG(FDrawHnde,+1)
		End If
	End If
	
	
	
	
End Function
Function ParseSGG$(FDrawFile$,FDrawWert$)
	
	
	
	
	;FILTER/ANWENDEN
	FDrawFile=Trim(FDrawFile)
	FDrawWert=Trim(FDrawWert)
	FDrawWert=Upper(FDrawWert)
	
	;TRANSPORTER/NULLSTELLUNG
	GDrawS1=0: GDrawS2=0: GDrawS3=0: GDrawS4=0
	GDrawS5=0: GDrawS6=0: GDrawS7=0: GDrawS8=0
	
	;VARIABLENDEKLARATIONEN
	Local IDrawSammler$=""
	Local IDrawZaehler%=0
	Local IDrawEiner$=""
	Local IDrawCount%=0
	Local IDrawFile$=""
	Local IDrawLine$=""
	Local IDrawMid%=0
	
	;WENN/DATEI/EINGESENDET
	If FileType(FDrawFile)=1 Then
		IDrawFile=ReadFile(FDrawFile)
		
		;GESAMTE/DATEI
		While Not Eof(IDrawFile)
			IDrawLine=ReadLine(IDrawFile)
			IDrawLine=Upper(IDrawLine)
			IDrawLine=Trim(IDrawLine)
			
			;SECTION/EINLESEN
			IDrawMid=Instr(IDrawLine,"=")
			If IDrawMid>0 Then
				If FDrawWert=Trim(Left(IDrawLine,IDrawMid-1)) Then
					IDrawLine=Trim(Mid(IDrawLine,IDrawMid+1,256))
					
					;NORMAL/AUSGABE
					If FDrawWert="LOADIMAGE3D" Then
						CloseFile(IDrawFile)
						Return IDrawLine
					End If
					
					;PARSER/AUSGABE
					IDrawLine=Replace(IDrawLine," ","")
					For IDrawCount=1 To Len(IDrawLine)
						IDrawEiner=Mid(IDrawLine,IDrawCount,1)
						IDrawSammler=IDrawSammler+IDrawEiner
						
						;KOMMA/TRENNZEICHEN/SUCHEN
						If IDrawEiner="," Or IDrawCount=Len(IDrawLine) Then
							IDrawZaehler=IDrawZaehler+1
							
							;GLOBALEN/SETZEN
							Select IDrawZaehler
								Case 1 GDrawS1=Replace(IDrawSammler,",","")
								Case 2 GDrawS2=Replace(IDrawSammler,",","")
								Case 3 GDrawS3=Replace(IDrawSammler,",","")
								Case 4 GDrawS4=Replace(IDrawSammler,",","")
								Case 5 GDrawS5=Replace(IDrawSammler,",","")
								Case 6 GDrawS6=Replace(IDrawSammler,",","")
								Case 7 GDrawS7=Replace(IDrawSammler,",","")
								Case 8 GDrawS8=Replace(IDrawSammler,",","")
							End Select
							IDrawSammler=""
						End If
					Next
				End If
			End If
		Wend
		
		;DATEI/SCHLIESSEN
		CloseFile(IDrawFile)
	End If
	
	
	
	
End Function
Function DrawSGG%(FDrawHnde%,FDrawXPos%=0,FDrawYPos%=0)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTH.SGGHaupt
	Local IDrawTN.SGGNeben
	Local IDrawTU.SGGUnter
	Local IDrawTHCount%=0
	Local IDrawTNCount%=0
	Local IDrawYPos%=0
	Local IDrawTemp%=0
	Local IDrawSize%=0
	Local IDrawOver%=0
	Local IDrawApp$=""
	
	;VARIABLENDEKLARATIONEN
	Local IDrawV0%=0, IDrawV1%=0, IDrawV2%=0, IDrawV3%=0
	Local IDrawV4%=0, IDrawV5%=0, IDrawV6%=0, IDrawV7%=0
	Local IDrawV8%=0, IDrawV9%=0, IDrawVA%=0, IDrawVB%=0
	Local IDrawVC%=0, IDrawVD%=0, IDrawVE%=0, IDrawVF%=0
	Local IDrawX#[3]
	Local IDrawY#[3]
	
	;NULLSTELLUNG
	SGGOver3D=0
	SGGDown3D=0
	SGGHit3D=0
	SGGSpz3D=0
	
	;HAUPT/TYPE/ID/AUFGREIFEN
	IDrawTH.SGGHaupt=Object.SGGHaupt(FDrawHnde)
	
	;DRAW3D/HIT/PUFFER
	IDrawTemp=GDrawMouseHit
	
	;HAUPTFELD/EINZEICHNEN
	DrawQuad3D(IDrawTH\Back,FDrawXPos+IDrawTH\XPos-IDrawTH\XRan,FDrawYPos+IDrawTH\YPos+IDrawTH\YRan,FDrawXPos+IDrawTH\XPos+IDrawTH\XRan,FDrawYPos+IDrawTH\YPos+IDrawTH\YRan,FDrawXPos+IDrawTH\XPos+IDrawTH\XRan,FDrawYPos+IDrawTH\YPos-IDrawTH\YRan,FDrawXPos+IDrawTH\XPos-IDrawTH\XRan,FDrawYPos+IDrawTH\YPos-IDrawTH\YRan,IDrawTH\Glow)
	
	;DRAW3D/HIT/PUFFER
	GDrawMouseHit=IDrawTemp
	IDrawOver=MouseOver3D
	
	;TAB/LOGIC/SELECTOR
	If GDrawPick=FDrawHnde Then
		GDrawInta=GDrawInta+GDrawTran
		If GDrawInta<0 Then GDrawInta=(BankSize(IDrawTH\Bank)/4)-1
		If GDrawInta>(BankSize(IDrawTH\Bank)/4)-1 Then GDrawInta=0
	End If
	
	
	
	
	;NEBENEINTRÄGE/AUFGREIFEN
	For IDrawTHCount=0 To (BankSize(IDrawTH\Bank)/4)-1
		
		;NEBEN/TYPE/ID/AUFGREIFEN
		IDrawTN.SGGNeben=Object.SGGNeben(PeekInt(IDrawTH\Bank,IDrawTHCount*4))
		
		;EINZEICHNEN
		Select IDrawTN\Arts
				
				;[Block] DRAWSGG/SGGOUTLAY
				
				
				
				
			Case SGGOUTLAY
				
				;CAPTION/AUSGEBEN
				IDrawTU.SGGUnter=Object.SGGUnter(PeekInt(IDrawTN\Bank,0))
				Text3D(IDrawTH\Font,FDrawXPos+IDrawTN\XPos,FDrawYPos+IDrawTN\YPos,IDrawTU\Item,IDrawTN\Algn,0,IDrawTN\XRan)
				
				
				
				
				;[End Block]
				;[Block] DRAWSGG/SGGBUTTON
				
				
				
				
			Case SGGBUTTON
				
				;TEMPWERTE/VORRECHNEN
				IDrawX[0]=FDrawXPos+IDrawTN\XPos-IDrawTN\XRan+IDrawTH\ButtonUV[02]-IDrawTH\ButtonUV[03]
				IDrawX[1]=FDrawXPos+IDrawTN\XPos-IDrawTN\XRan
				IDrawX[2]=FDrawXPos+IDrawTN\XPos+IDrawTN\XRan
				IDrawX[3]=FDrawXPos+IDrawTN\XPos+IDrawTN\XRan-IDrawTH\ButtonUV[02]+IDrawTH\ButtonUV[03]
				IDrawY[0]=FDrawYPos+IDrawTN\YPos+(IDrawTH\ButtonUV[13]-IDrawTH\ButtonUV[12])*0.5
				IDrawY[1]=FDrawYPos+IDrawTN\YPos-(IDrawTH\ButtonUV[13]-IDrawTH\ButtonUV[12])*0.5
				
				;VERTEX/POLYGON/ZUWEISUNGEN
				IDrawV0=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[0],0 ,IDrawTH\ButtonUV[00],IDrawTH\ButtonUV[12])
				IDrawV1=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[0],0 ,IDrawTH\ButtonUV[01],IDrawTH\ButtonUV[12])
				IDrawV2=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[0],0 ,IDrawTH\ButtonUV[02],IDrawTH\ButtonUV[12])
				IDrawV3=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[0],0 ,IDrawTH\ButtonUV[03],IDrawTH\ButtonUV[12])
				IDrawV4=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[1],0 ,IDrawTH\ButtonUV[00],IDrawTH\ButtonUV[13])
				IDrawV5=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[1],0 ,IDrawTH\ButtonUV[01],IDrawTH\ButtonUV[13])
				IDrawV6=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[1],0 ,IDrawTH\ButtonUV[02],IDrawTH\ButtonUV[13])
				IDrawV7=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[1],0 ,IDrawTH\ButtonUV[03],IDrawTH\ButtonUV[13])
				AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV5: AddTriangle IDrawTH\Face,IDrawV5,IDrawV4,IDrawV0
				AddTriangle IDrawTH\Face,IDrawV1,IDrawV2,IDrawV6: AddTriangle IDrawTH\Face,IDrawV6,IDrawV5,IDrawV1
				AddTriangle IDrawTH\Face,IDrawV2,IDrawV3,IDrawV7: AddTriangle IDrawTH\Face,IDrawV7,IDrawV6,IDrawV2
				
				;CAPTION/AUSGEBEN
				IDrawTU.SGGUnter=Object.SGGUnter(PeekInt(IDrawTN\Bank,IDrawTN\Bffs))
				If IDrawTN\Algn=0 Then Text3D(IDrawTH\Font,IDrawX[1]+IDrawTH\XSpc,FDrawYPos+IDrawTN\YPos,IDrawTU\Item,0)
				If IDrawTN\Algn=1 Then Text3D(IDrawTH\Font,FDrawXPos+IDrawTN\XPos,FDrawYPos+IDrawTN\YPos,IDrawTU\Item,1)
				If IDrawTN\Algn=2 Then Text3D(IDrawTH\Font,IDrawX[2]-IDrawTH\XSpc,FDrawYPos+IDrawTN\YPos,IDrawTU\Item,2)
				
				;MOUSE/CHECK
				If IDrawOver>0 And CheckQuad3D(IDrawX[0],IDrawY[0],IDrawX[3],IDrawY[0],IDrawX[3],IDrawY[1],IDrawX[0],IDrawY[1],1,IDrawTH\Glow)>0 Then
					
					;GLOBALEN/OVER/WERTEVERGABE
					SGGOver3D=PeekInt(IDrawTH\Bank,IDrawTHCount*4)
					
					;BUTTON/VERARBEITUNG
					If MouseDown3D>0 Then
						
						;VERTEX/POLYGON/ZUWEISUNGEN
						IDrawV0=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[0],0 ,IDrawTH\ButtonUV[08],IDrawTH\ButtonUV[16])
						IDrawV1=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[0],0 ,IDrawTH\ButtonUV[09],IDrawTH\ButtonUV[16])
						IDrawV2=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[0],0 ,IDrawTH\ButtonUV[10],IDrawTH\ButtonUV[16])
						IDrawV3=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[0],0 ,IDrawTH\ButtonUV[11],IDrawTH\ButtonUV[16])
						IDrawV4=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[1],0 ,IDrawTH\ButtonUV[08],IDrawTH\ButtonUV[17])
						IDrawV5=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[1],0 ,IDrawTH\ButtonUV[09],IDrawTH\ButtonUV[17])
						IDrawV6=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[1],0 ,IDrawTH\ButtonUV[10],IDrawTH\ButtonUV[17])
						IDrawV7=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[1],0 ,IDrawTH\ButtonUV[11],IDrawTH\ButtonUV[17])
						AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV5: AddTriangle IDrawTH\Face,IDrawV5,IDrawV4,IDrawV0
						AddTriangle IDrawTH\Face,IDrawV1,IDrawV2,IDrawV6: AddTriangle IDrawTH\Face,IDrawV6,IDrawV5,IDrawV1
						AddTriangle IDrawTH\Face,IDrawV2,IDrawV3,IDrawV7: AddTriangle IDrawTH\Face,IDrawV7,IDrawV6,IDrawV2
						
						;SPZ3D
						IDrawOver=2
						
						;SPEZIELLE/MAUSABFRAGE
						If MouseDown3D=2 Then SGGSpz3D=SGGOver3D
						
						;GLOBALEN/WERTEVERGABE
						SGGDown3D=SGGOver3D
						
						;POSITION/MARKIEREN
						If MouseHit3D>0 Then
							IDrawTN\Bffs=IDrawTN\Bffs+4
							If IDrawTN\Bffs=BankSize(IDrawTN\Bank) Then IDrawTN\Bffs=0
							SGGHit3D=SGGOver3D
							SGGSpz3D=SGGOver3D
						End If
					Else
						
						;VERTEX/POLYGON/ZUWEISUNGEN
						IDrawV0=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[0],0 ,IDrawTH\ButtonUV[04],IDrawTH\ButtonUV[14])
						IDrawV1=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[0],0 ,IDrawTH\ButtonUV[05],IDrawTH\ButtonUV[14])
						IDrawV2=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[0],0 ,IDrawTH\ButtonUV[06],IDrawTH\ButtonUV[14])
						IDrawV3=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[0],0 ,IDrawTH\ButtonUV[07],IDrawTH\ButtonUV[14])
						IDrawV4=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[1],0 ,IDrawTH\ButtonUV[04],IDrawTH\ButtonUV[15])
						IDrawV5=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[1],0 ,IDrawTH\ButtonUV[05],IDrawTH\ButtonUV[15])
						IDrawV6=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[1],0 ,IDrawTH\ButtonUV[06],IDrawTH\ButtonUV[15])
						IDrawV7=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[1],0 ,IDrawTH\ButtonUV[07],IDrawTH\ButtonUV[15])
						AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV5: AddTriangle IDrawTH\Face,IDrawV5,IDrawV4,IDrawV0
						AddTriangle IDrawTH\Face,IDrawV1,IDrawV2,IDrawV6: AddTriangle IDrawTH\Face,IDrawV6,IDrawV5,IDrawV1
						AddTriangle IDrawTH\Face,IDrawV2,IDrawV3,IDrawV7: AddTriangle IDrawTH\Face,IDrawV7,IDrawV6,IDrawV2
					End If
				End If
				
				
				
				
				;[End Block]
				;[Block] DRAWSGG/SGGINTAKE
				
				
				
				
			Case SGGINTAKE
				
				;TAB/LOGIC/SELECTOR
				If GDrawPick=FDrawHnde Then
					If GDrawTran<>0 Then
						If GDrawInta=IDrawTHCount Then
							SGGEntry=PeekInt(IDrawTH\Bank,GDrawInta*4)
							GDrawInta=0
							GDrawTran=0
						End If
					End If
				End If
				
				;TEMPWERTE/VORRECHNEN
				IDrawX[0]=FDrawXPos+IDrawTN\XPos-IDrawTN\XRan+IDrawTH\IntakeUV[02]-IDrawTH\IntakeUV[03]
				IDrawX[1]=FDrawXPos+IDrawTN\XPos-IDrawTN\XRan
				IDrawX[2]=FDrawXPos+IDrawTN\XPos+IDrawTN\XRan
				IDrawX[3]=FDrawXPos+IDrawTN\XPos+IDrawTN\XRan-IDrawTH\IntakeUV[02]+IDrawTH\IntakeUV[03]
				IDrawY[0]=FDrawYPos+IDrawTN\YPos+(IDrawTH\ButtonUV[13]-IDrawTH\ButtonUV[12])*0.5
				IDrawY[1]=FDrawYPos+IDrawTN\YPos-(IDrawTH\ButtonUV[13]-IDrawTH\ButtonUV[12])*0.5
				
				;VERTEX/POLYGON/ZUWEISUNGEN
				IDrawV0=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[0],0 ,IDrawTH\IntakeUV[00],IDrawTH\IntakeUV[12])
				IDrawV1=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[0],0 ,IDrawTH\IntakeUV[01],IDrawTH\IntakeUV[12])
				IDrawV2=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[0],0 ,IDrawTH\IntakeUV[02],IDrawTH\IntakeUV[12])
				IDrawV3=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[0],0 ,IDrawTH\IntakeUV[03],IDrawTH\IntakeUV[12])
				IDrawV4=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[1],0 ,IDrawTH\IntakeUV[00],IDrawTH\IntakeUV[13])
				IDrawV5=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[1],0 ,IDrawTH\IntakeUV[01],IDrawTH\IntakeUV[13])
				IDrawV6=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[1],0 ,IDrawTH\IntakeUV[02],IDrawTH\IntakeUV[13])
				IDrawV7=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[1],0 ,IDrawTH\IntakeUV[03],IDrawTH\IntakeUV[13])
				AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV5: AddTriangle IDrawTH\Face,IDrawV5,IDrawV4,IDrawV0
				AddTriangle IDrawTH\Face,IDrawV1,IDrawV2,IDrawV6: AddTriangle IDrawTH\Face,IDrawV6,IDrawV5,IDrawV1
				AddTriangle IDrawTH\Face,IDrawV2,IDrawV3,IDrawV7: AddTriangle IDrawTH\Face,IDrawV7,IDrawV6,IDrawV2
				
				;CAPTION/AUSGEBEN
				If PeekInt(IDrawTH\Bank,IDrawTHCount*4)=SGGEntry Then IDrawApp="|" Else IDrawApp=""
				IDrawTU.SGGUnter=Object.SGGUnter(PeekInt(IDrawTN\Bank,0))
				If IDrawTN\Algn=0 Then Text3D(IDrawTH\Font,IDrawX[1]+IDrawTH\XSpc,FDrawYPos+IDrawTN\YPos,IDrawTU\Item+IDrawApp,0)
				If IDrawTN\Algn=1 Then Text3D(IDrawTH\Font,FDrawXPos+IDrawTN\XPos,FDrawYPos+IDrawTN\YPos,IDrawTU\Item+IDrawApp,1)
				If IDrawTN\Algn=2 Then Text3D(IDrawTH\Font,IDrawX[2]-IDrawTH\XSpc,FDrawYPos+IDrawTN\YPos,IDrawTU\Item+IDrawApp,2)
				
				;TASTATUREINGABE
				If PeekInt(IDrawTH\Bank,IDrawTHCount*4)=SGGEntry Then
					
					;[DELETE]
					If SGGKey3D=8 Then
						If Len(IDrawTU\Item)>0 Then
							IDrawTU\Item=Left(IDrawTU\Item,Len(IDrawTU\Item)-1)
						End If
					End If
					
					;[TABULATOR]
					If SGGKey3D=9 Then
						GDrawTran=Sgn(0.5-(KeyDown(42)+KeyDown(54)))
						GDrawInta=IDrawTHCount
						GDrawPick=FDrawHnde
						IDrawOver=2
					End If
					
					;[ENTER]
					If SGGKey3D=13 Then
						SGGHit3D=PeekInt(IDrawTH\Bank,IDrawTHCount*4)
						SGGSpz3D=PeekInt(IDrawTH\Bank,IDrawTHCount*4)
						SGGEntry=0
						IDrawOver=2
					End If
					
					;[REST]
					If SGGKey3D>31 Then
						If SGGKey3D<>124 Then
							IDrawTU\Item=IDrawTU\Item+Chr$(SGGKey3D)
							If StringWidth3D(IDrawTH\Font,IDrawTU\Item)>(IDrawTN\XRan-IDrawTH\XSpc)*2 Then
								IDrawTU\Item=Left(IDrawTU\Item,Len(IDrawTU\Item)-1)
								IDrawOver=2
							End If
						End If
					End If
				End If
				
				;MOUSE/CHECK
				If IDrawOver>0 And CheckQuad3D(IDrawX[0],IDrawY[0],IDrawX[3],IDrawY[0],IDrawX[3],IDrawY[1],IDrawX[0],IDrawY[1],1,IDrawTH\Glow)>0 Then
					
					;GLOBALEN/OVER/WERTEVERGABE
					SGGOver3D=PeekInt(IDrawTH\Bank,IDrawTHCount*4)
					
					;INTAKE/VERARBEITUNG
					If MouseDown3D Then
						
						;VERTEX/POLYGON/ZUWEISUNGEN
						IDrawV0=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[0],0 ,IDrawTH\IntakeUV[08],IDrawTH\IntakeUV[16])
						IDrawV1=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[0],0 ,IDrawTH\IntakeUV[09],IDrawTH\IntakeUV[16])
						IDrawV2=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[0],0 ,IDrawTH\IntakeUV[10],IDrawTH\IntakeUV[16])
						IDrawV3=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[0],0 ,IDrawTH\IntakeUV[11],IDrawTH\IntakeUV[16])
						IDrawV4=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[1],0 ,IDrawTH\IntakeUV[08],IDrawTH\IntakeUV[17])
						IDrawV5=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[1],0 ,IDrawTH\IntakeUV[09],IDrawTH\IntakeUV[17])
						IDrawV6=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[1],0 ,IDrawTH\IntakeUV[10],IDrawTH\IntakeUV[17])
						IDrawV7=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[1],0 ,IDrawTH\IntakeUV[11],IDrawTH\IntakeUV[17])
						AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV5: AddTriangle IDrawTH\Face,IDrawV5,IDrawV4,IDrawV0
						AddTriangle IDrawTH\Face,IDrawV1,IDrawV2,IDrawV6: AddTriangle IDrawTH\Face,IDrawV6,IDrawV5,IDrawV1
						AddTriangle IDrawTH\Face,IDrawV2,IDrawV3,IDrawV7: AddTriangle IDrawTH\Face,IDrawV7,IDrawV6,IDrawV2
						
						;GLOBALEN/WERTEVERGABE
						SGGDown3D=SGGOver3D
						
						;POSITION/MARKIEREN
						If MouseHit3D>0 Then
							If SGGEntry=0 Or SGGEntry<>PeekInt(IDrawTH\Bank,IDrawTHCount*4) Then SGGEntry=PeekInt(IDrawTH\Bank,IDrawTHCount*4): Else SGGEntry=0
							SGGHit3D=SGGOver3D
							FlushKeys
							IDrawOver=2
						End If
					Else
						
						;VERTEX/POLYGON/ZUWEISUNGEN
						IDrawV0=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[0],0 ,IDrawTH\IntakeUV[04],IDrawTH\IntakeUV[14])
						IDrawV1=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[0],0 ,IDrawTH\IntakeUV[05],IDrawTH\IntakeUV[14])
						IDrawV2=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[0],0 ,IDrawTH\IntakeUV[06],IDrawTH\IntakeUV[14])
						IDrawV3=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[0],0 ,IDrawTH\IntakeUV[07],IDrawTH\IntakeUV[14])
						IDrawV4=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[1],0 ,IDrawTH\IntakeUV[04],IDrawTH\IntakeUV[15])
						IDrawV5=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[1],0 ,IDrawTH\IntakeUV[05],IDrawTH\IntakeUV[15])
						IDrawV6=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[1],0 ,IDrawTH\IntakeUV[06],IDrawTH\IntakeUV[15])
						IDrawV7=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[1],0 ,IDrawTH\IntakeUV[07],IDrawTH\IntakeUV[15])
						AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV5: AddTriangle IDrawTH\Face,IDrawV5,IDrawV4,IDrawV0
						AddTriangle IDrawTH\Face,IDrawV1,IDrawV2,IDrawV6: AddTriangle IDrawTH\Face,IDrawV6,IDrawV5,IDrawV1
						AddTriangle IDrawTH\Face,IDrawV2,IDrawV3,IDrawV7: AddTriangle IDrawTH\Face,IDrawV7,IDrawV6,IDrawV2
					End If
				End If
				
				
				
				
				;[End Block]
				;[Block] DRAWSGG/SGGROSTER
				
				
				
				
			Case SGGROSTER
				
				;TEMPWERTE/VORRECHNEN
				IDrawYPos=FDrawYPos+IDrawTN\YPos+(IDrawTH\Str1*IDrawTN\Cffs)/2
				IDrawTemp=IDrawTH\Str1*IDrawTN\Cffs/2
				
				;TEMPWERTE/VORRECHNEN
				IDrawX[0]=FDrawXPos+IDrawTN\XPos-IDrawTN\XRan+IDrawTH\RosterUV[02]-IDrawTH\RosterUV[03]
				IDrawX[1]=FDrawXPos+IDrawTN\XPos-IDrawTN\XRan
				IDrawX[2]=FDrawXPos+IDrawTN\XPos+IDrawTN\XRan
				IDrawX[3]=FDrawXPos+IDrawTN\XPos+IDrawTN\XRan-IDrawTH\RosterUV[02]+IDrawTH\RosterUV[03]
				IDrawY[0]=FDrawYPos+IDrawTN\YPos+IDrawTH\YSpc+IDrawTemp-IDrawTH\RosterUV[14]+IDrawTH\RosterUV[15]
				IDrawY[1]=FDrawYPos+IDrawTN\YPos+IDrawTH\YSpc+IDrawTemp
				IDrawY[2]=FDrawYPos+IDrawTN\YPos-IDrawTH\YSpc-IDrawTemp
				IDrawY[3]=FDrawYPos+IDrawTN\YPos-IDrawTH\YSpc-IDrawTemp+IDrawTH\RosterUV[14]-IDrawTH\RosterUV[15]
				
				;VERTEX/POLYGON/ZUWEISUNGEN
				IDrawV0=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[0],0 ,IDrawTH\RosterUV[00],IDrawTH\RosterUV[12])
				IDrawV1=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[0],0 ,IDrawTH\RosterUV[01],IDrawTH\RosterUV[12])
				IDrawV2=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[0],0 ,IDrawTH\RosterUV[02],IDrawTH\RosterUV[12])
				IDrawV3=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[0],0 ,IDrawTH\RosterUV[03],IDrawTH\RosterUV[12])
				IDrawV4=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[1],0 ,IDrawTH\RosterUV[00],IDrawTH\RosterUV[13])
				IDrawV5=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[1],0 ,IDrawTH\RosterUV[01],IDrawTH\RosterUV[13])
				IDrawV6=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[1],0 ,IDrawTH\RosterUV[02],IDrawTH\RosterUV[13])
				IDrawV7=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[1],0 ,IDrawTH\RosterUV[03],IDrawTH\RosterUV[13])
				IDrawV8=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[2],0 ,IDrawTH\RosterUV[00],IDrawTH\RosterUV[14])
				IDrawV9=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[2],0 ,IDrawTH\RosterUV[01],IDrawTH\RosterUV[14])
				IDrawVA=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[2],0 ,IDrawTH\RosterUV[02],IDrawTH\RosterUV[14])
				IDrawVB=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[2],0 ,IDrawTH\RosterUV[03],IDrawTH\RosterUV[14])
				IDrawVC=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[3],0 ,IDrawTH\RosterUV[00],IDrawTH\RosterUV[15])
				IDrawVD=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[3],0 ,IDrawTH\RosterUV[01],IDrawTH\RosterUV[15])
				IDrawVE=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[3],0 ,IDrawTH\RosterUV[02],IDrawTH\RosterUV[15])
				IDrawVF=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[3],0 ,IDrawTH\RosterUV[03],IDrawTH\RosterUV[15])
				AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV5: AddTriangle IDrawTH\Face,IDrawV5,IDrawV4,IDrawV0
				AddTriangle IDrawTH\Face,IDrawV1,IDrawV2,IDrawV6: AddTriangle IDrawTH\Face,IDrawV6,IDrawV5,IDrawV1
				AddTriangle IDrawTH\Face,IDrawV2,IDrawV3,IDrawV7: AddTriangle IDrawTH\Face,IDrawV7,IDrawV6,IDrawV2
				AddTriangle IDrawTH\Face,IDrawV4,IDrawV5,IDrawV9: AddTriangle IDrawTH\Face,IDrawV9,IDrawV8,IDrawV4
				AddTriangle IDrawTH\Face,IDrawV5,IDrawV6,IDrawVA: AddTriangle IDrawTH\Face,IDrawVA,IDrawV9,IDrawV5
				AddTriangle IDrawTH\Face,IDrawV6,IDrawV7,IDrawVB: AddTriangle IDrawTH\Face,IDrawVB,IDrawVA,IDrawV6
				AddTriangle IDrawTH\Face,IDrawV8,IDrawV9,IDrawVD: AddTriangle IDrawTH\Face,IDrawVD,IDrawVC,IDrawV8
				AddTriangle IDrawTH\Face,IDrawV9,IDrawVA,IDrawVE: AddTriangle IDrawTH\Face,IDrawVE,IDrawVD,IDrawV9
				AddTriangle IDrawTH\Face,IDrawVA,IDrawVB,IDrawVF: AddTriangle IDrawTH\Face,IDrawVF,IDrawVE,IDrawVA
				
				;ANZAHL/CAPTIONS/BERECHNEN
				IDrawSize=(BankSize(IDrawTN\Bank)-4)/4
				If IDrawTN\Cffs>IDrawSize Then IDrawTemp=IDrawSize: Else IDrawTemp=IDrawTN\Cffs
				
				;CAPTIONS/DURCHGEHEN
				For IDrawTNCount=0 To IDrawTemp
					
					;CAPTION/AUSGEBEN
					IDrawTU.SGGUnter=Object.SGGUnter(PeekInt(IDrawTN\Bank,(IDrawTN\Sffs+IDrawTNCount)*4))
					If IDrawTN\Algn=0 Then Text3D(IDrawTH\Font,IDrawX[1]+IDrawTH\XSpc,IDrawYPos-IDrawTNCount*IDrawTH\Str1,IDrawTU\Item,0)
					If IDrawTN\Algn=1 Then Text3D(IDrawTH\Font,FDrawXPos+IDrawTN\XPos,IDrawYPos-IDrawTNCount*IDrawTH\Str1,IDrawTU\Item,1)
					If IDrawTN\Algn=2 Then Text3D(IDrawTH\Font,IDrawX[2]-IDrawTH\XSpc,IDrawYPos-IDrawTNCount*IDrawTH\Str1,IDrawTU\Item,2)
					
					;TEMPWERTE/VORRECHNEN
					IDrawY[0]=IDrawYPos-IDrawTNCount*IDrawTH\Str1+IDrawTH\Str1/2
					IDrawY[3]=IDrawYPos-IDrawTNCount*IDrawTH\Str1-IDrawTH\Str1/2
					
					;MOUSE/CHECK
					If IDrawOver>0 And CheckQuad3D(IDrawX[0],IDrawY[0],IDrawX[3],IDrawY[0],IDrawX[3],IDrawY[3],IDrawX[0],IDrawY[3],1,IDrawTH\Glow)>0 Then
						
						;GLOBALEN/OVER/WERTEVERGABE
						SGGOver3D=PeekInt(IDrawTH\Bank,IDrawTHCount*4)
						
						;ROSTER/VERARBEITUNG
						If MouseDown3D>0 Then
							
							;VERTEX/POLYGON/ZUWEISUNGEN
							IDrawV0=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[0],0 ,IDrawTH\RosterUV[08],IDrawTH\RosterUV[18])
							IDrawV1=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[0],0 ,IDrawTH\RosterUV[09],IDrawTH\RosterUV[18])
							IDrawV2=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[0],0 ,IDrawTH\RosterUV[10],IDrawTH\RosterUV[18])
							IDrawV3=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[0],0 ,IDrawTH\RosterUV[11],IDrawTH\RosterUV[18])
							IDrawV4=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[3],0 ,IDrawTH\RosterUV[08],IDrawTH\RosterUV[19])
							IDrawV5=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[3],0 ,IDrawTH\RosterUV[09],IDrawTH\RosterUV[19])
							IDrawV6=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[3],0 ,IDrawTH\RosterUV[10],IDrawTH\RosterUV[19])
							IDrawV7=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[3],0 ,IDrawTH\RosterUV[11],IDrawTH\RosterUV[19])
							AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV5: AddTriangle IDrawTH\Face,IDrawV5,IDrawV4,IDrawV0
							AddTriangle IDrawTH\Face,IDrawV1,IDrawV2,IDrawV6: AddTriangle IDrawTH\Face,IDrawV6,IDrawV5,IDrawV1
							AddTriangle IDrawTH\Face,IDrawV2,IDrawV3,IDrawV7: AddTriangle IDrawTH\Face,IDrawV7,IDrawV6,IDrawV2
							
							;GLOBALEN/WERTEVERGABE
							SGGDown3D=SGGOver3D
							
							;POSITION/MARKIEREN
							If MouseHit3D>0 Then
								IDrawTN\Bffs=(IDrawTN\Sffs+IDrawTNCount)*4
								SGGHit3D=SGGOver3D
								IDrawOver=2
							End If
						Else
							
							;VERTEX/POLYGON/ZUWEISUNGEN
							IDrawV0=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[0],0 ,IDrawTH\RosterUV[04],IDrawTH\RosterUV[16])
							IDrawV1=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[0],0 ,IDrawTH\RosterUV[05],IDrawTH\RosterUV[16])
							IDrawV2=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[0],0 ,IDrawTH\RosterUV[06],IDrawTH\RosterUV[16])
							IDrawV3=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[0],0 ,IDrawTH\RosterUV[07],IDrawTH\RosterUV[16])
							IDrawV4=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[3],0 ,IDrawTH\RosterUV[04],IDrawTH\RosterUV[17])
							IDrawV5=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[3],0 ,IDrawTH\RosterUV[05],IDrawTH\RosterUV[17])
							IDrawV6=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[3],0 ,IDrawTH\RosterUV[06],IDrawTH\RosterUV[17])
							IDrawV7=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[3],0 ,IDrawTH\RosterUV[07],IDrawTH\RosterUV[17])
							AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV5: AddTriangle IDrawTH\Face,IDrawV5,IDrawV4,IDrawV0
							AddTriangle IDrawTH\Face,IDrawV1,IDrawV2,IDrawV6: AddTriangle IDrawTH\Face,IDrawV6,IDrawV5,IDrawV1
							AddTriangle IDrawTH\Face,IDrawV2,IDrawV3,IDrawV7: AddTriangle IDrawTH\Face,IDrawV7,IDrawV6,IDrawV2
						End If
					End If
					
					;MARKIERUNG/ZEICHNEN
					If MouseOver3D=0 Then
						If IDrawTN\Bffs=(IDrawTN\Sffs+IDrawTNCount)*4 Then
							
							;VERTEX/POLYGON/ZUWEISUNGEN
							IDrawV0=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[0],0 ,IDrawTH\RosterUV[04],IDrawTH\RosterUV[16])
							IDrawV1=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[0],0 ,IDrawTH\RosterUV[05],IDrawTH\RosterUV[16])
							IDrawV2=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[0],0 ,IDrawTH\RosterUV[06],IDrawTH\RosterUV[16])
							IDrawV3=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[0],0 ,IDrawTH\RosterUV[07],IDrawTH\RosterUV[16])
							IDrawV4=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[3],0 ,IDrawTH\RosterUV[04],IDrawTH\RosterUV[17])
							IDrawV5=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[3],0 ,IDrawTH\RosterUV[05],IDrawTH\RosterUV[17])
							IDrawV6=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[3],0 ,IDrawTH\RosterUV[06],IDrawTH\RosterUV[17])
							IDrawV7=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[3],0 ,IDrawTH\RosterUV[07],IDrawTH\RosterUV[17])
							AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV5: AddTriangle IDrawTH\Face,IDrawV5,IDrawV4,IDrawV0
							AddTriangle IDrawTH\Face,IDrawV1,IDrawV2,IDrawV6: AddTriangle IDrawTH\Face,IDrawV6,IDrawV5,IDrawV1
							AddTriangle IDrawTH\Face,IDrawV2,IDrawV3,IDrawV7: AddTriangle IDrawTH\Face,IDrawV7,IDrawV6,IDrawV2
						End If
					End If
				Next
				
				
				
				
				;[End Block]
				;[Block] DRAWSGG/SGGSLIDER
				
				
				
				
			Case SGGSLIDER ;SLIDER/VON: 'Xaymar'
				
				;TEMPWERTE/VORRECHNEN
				IDrawX[0]=FDrawXPos+IDrawTN\XPos-IDrawTN\XRan+IDrawTH\SliderUV[04]-IDrawTH\SliderUV[05]
				IDrawX[1]=FDrawXPos+IDrawTN\XPos-IDrawTN\XRan
				IDrawX[2]=FDrawXPos+IDrawTN\XPos+IDrawTN\XRan
				IDrawX[3]=FDrawXPos+IDrawTN\XPos+IDrawTN\XRan-IDrawTH\SliderUV[04]+IDrawTH\SliderUV[05]
				IDrawY[0]=FDrawYPos+IDrawTN\YPos+(IDrawTH\SliderUV[13]-IDrawTH\SliderUV[12])*0.5
				IDrawY[1]=FDrawYPos+IDrawTN\YPos-(IDrawTH\SliderUV[13]-IDrawTH\SliderUV[12])*0.5
				
				;VERTEX/POLYGON/LINE
				IDrawV0=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[0],0 ,IDrawTH\SliderUV[02],IDrawTH\SliderUV[12])
				IDrawV1=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[0],0 ,IDrawTH\SliderUV[03],IDrawTH\SliderUV[12])
				IDrawV2=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[0],0 ,IDrawTH\SliderUV[04],IDrawTH\SliderUV[12])
				IDrawV3=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[0],0 ,IDrawTH\SliderUV[05],IDrawTH\SliderUV[12])
				IDrawV4=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[1],0 ,IDrawTH\SliderUV[02],IDrawTH\SliderUV[13])
				IDrawV5=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[1],0 ,IDrawTH\SliderUV[03],IDrawTH\SliderUV[13])
				IDrawV6=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[1],0 ,IDrawTH\SliderUV[04],IDrawTH\SliderUV[13])
				IDrawV7=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[1],0 ,IDrawTH\SliderUV[05],IDrawTH\SliderUV[13])
				AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV5: AddTriangle IDrawTH\Face,IDrawV5,IDrawV4,IDrawV0
				AddTriangle IDrawTH\Face,IDrawV1,IDrawV2,IDrawV6: AddTriangle IDrawTH\Face,IDrawV6,IDrawV5,IDrawV1
				AddTriangle IDrawTH\Face,IDrawV2,IDrawV3,IDrawV7: AddTriangle IDrawTH\Face,IDrawV7,IDrawV6,IDrawV2
				
				;CAPTION/AUSGEBEN
				IDrawTU.SGGUnter=Object.SGGUnter(PeekInt(IDrawTN\Bank,0)): If IDrawTU\Item<>"" Then
					If IDrawTN\Algn=0 Then Text3D(IDrawTH\Font,IDrawX[1]+IDrawTH\XSpc,FDrawYPos+IDrawTN\YPos,IDrawTU\Item,0)
					If IDrawTN\Algn=1 Then Text3D(IDrawTH\Font,FDrawXPos+IDrawTN\XPos,FDrawYPos+IDrawTN\YPos,IDrawTU\Item,1)
					If IDrawTN\Algn=2 Then Text3D(IDrawTH\Font,IDrawX[2]-IDrawTH\XSpc,FDrawYPos+IDrawTN\YPos,IDrawTU\Item,2)
				End If
				
				;WERTE/EINGRENZUNG
				If IDrawTN\SVal>255 Then IDrawTN\SVal=255
				If IDrawTN\SVal<0 Then IDrawTN\SVal=0
				
				;TEMPWERTE/VORRECHNEN
				IDrawX[1]=FDrawXPos+IDrawTN\XPos-IDrawTN\XRan+(IDrawTN\SVal/255.0)*IDrawTN\XRan*2-(IDrawTH\SliderUV[01]-IDrawTH\SliderUV[00])*0.5
				IDrawX[2]=FDrawXPos+IDrawTN\XPos-IDrawTN\XRan+(IDrawTN\SVal/255.0)*IDrawTN\XRan*2+(IDrawTH\SliderUV[01]-IDrawTH\SliderUV[00])*0.5
				IDrawY[0]=FDrawYPos+IDrawTN\YPos+(IDrawTH\SliderUV[11]-IDrawTH\SliderUV[10])*0.5
				IDrawY[1]=FDrawYPos+IDrawTN\YPos-(IDrawTH\SliderUV[11]-IDrawTH\SliderUV[10])*0.5
				
				;VERTEX/POLYGON/MAIN
				IDrawV0=AddVertex(IDrawTH\Face,Floor(IDrawX[1]),IDrawY[0],0 ,IDrawTH\SliderUV[00],IDrawTH\SliderUV[10])
				IDrawV1=AddVertex(IDrawTH\Face,Floor(IDrawX[2]),IDrawY[0],0 ,IDrawTH\SliderUV[01],IDrawTH\SliderUV[10])
				IDrawV2=AddVertex(IDrawTH\Face,Floor(IDrawX[1]),IDrawY[1],0 ,IDrawTH\SliderUV[00],IDrawTH\SliderUV[11])
				IDrawV3=AddVertex(IDrawTH\Face,Floor(IDrawX[2]),IDrawY[1],0 ,IDrawTH\SliderUV[01],IDrawTH\SliderUV[11])
				AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV2 AddTriangle IDrawTH\Face,IDrawV2,IDrawV1,IDrawV3
				
				;MOUSE/CHECK
				;IDrawX[1],IDrawY[0],IDrawX[2],IDrawY[0],IDrawX[2],IDrawY[1],IDrawX[1],IDrawY[1] ;REAGIERT/NUR/AUF/SLIDER
				;IDrawX[0],IDrawY[0],IDrawX[3],IDrawY[0],IDrawX[3],IDrawY[1],IDrawX[0],IDrawY[1] ;REAGIERT/GESAMT/AUF/FLÄCHE
				If IDrawOver>0 And CheckQuad3D(IDrawX[1],IDrawY[0],IDrawX[2],IDrawY[0],IDrawX[2],IDrawY[1],IDrawX[1],IDrawY[1],1,IDrawTH\Glow)>0 Then
					
					;GLOBALEN/OVER/WERTEVERGABE
					SGGOver3D=PeekInt(IDrawTH\Bank,IDrawTHCount*4)
					
					;SLIDER/VERARBEITUNG
					If MouseDown3D>0 Then
						
						;VERTEX/POLYGON/DOWN
						IDrawV0=AddVertex(IDrawTH\Face,Floor(IDrawX[1]),IDrawY[0],0 ,IDrawTH\SliderUV[08],IDrawTH\SliderUV[16])
						IDrawV1=AddVertex(IDrawTH\Face,Floor(IDrawX[2]),IDrawY[0],0 ,IDrawTH\SliderUV[09],IDrawTH\SliderUV[16])
						IDrawV2=AddVertex(IDrawTH\Face,Floor(IDrawX[1]),IDrawY[1],0 ,IDrawTH\SliderUV[08],IDrawTH\SliderUV[17])
						IDrawV3=AddVertex(IDrawTH\Face,Floor(IDrawX[2]),IDrawY[1],0 ,IDrawTH\SliderUV[09],IDrawTH\SliderUV[17])
						AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV2: AddTriangle IDrawTH\Face,IDrawV2,IDrawV1,IDrawV3
						
						;SPZ3D
						IDrawOver=2
						
						;SPEZIELLE/MAUSABFRAGE
						If MouseDown3D=2 Then SGGSpz3D=SGGOver3D
						
						;GLOBALEN/WERTEVERGABE
						SGGDown3D=SGGOver3D
						
						;POSITION/MARKIEREN
						If MouseHit3D>0 Then
							IDrawTN\Bffs=IDrawTN\Bffs+4
							If IDrawTN\Bffs=BankSize(IDrawTN\Bank) Then IDrawTN\Bffs=0
							SGGHit3D=SGGOver3D
							SGGSpz3D=SGGOver3D
						End If
						
						;VARIABLENDEKLARATIONEN
						Local LDrawMesh=PeekInt(GDrawFaceBank,IDrawTH\Back+DRAWBANKMESH)
						TFormPoint(FDrawXPos+IDrawTN\XPos-IDrawTN\XRan,FDrawYPos+IDrawTN\YPos,0,LDrawMesh,0): CameraProject(GDrawCamera,TFormedX(),TFormedY(),TFormedZ()): IDrawX[0]=ProjectedX()-GDrawXSize: IDrawY[0]=GDrawYSize-ProjectedY()
						TFormPoint(FDrawXPos+IDrawTN\XPos+IDrawTN\XRan,FDrawYPos+IDrawTN\YPos,0,LDrawMesh,0): CameraProject(GDrawCamera,TFormedX(),TFormedY(),TFormedZ()): IDrawX[1]=ProjectedX()-GDrawXSize: IDrawY[1]=GDrawYSize-ProjectedY()
						
						Local IDrawXC#
						Local IDrawYC#
						TFormPoint(FDrawXPos+IDrawTN\XPos,FDrawYPos+IDrawTN\YPos,0,LDrawMesh,0): CameraProject(GDrawCamera,TFormedX(),TFormedY(),TFormedZ()): IDrawXC=ProjectedX()-GDrawXSize: IDrawYC=GDrawYSize-ProjectedY()
						
						IDrawX[2]=MouseX3D+(IDrawY[0]-IDrawY[1])
						IDrawY[2]=MouseY3D-(IDrawX[0]-IDrawX[1])
						IDrawX[3]=MouseX3D-(IDrawY[0]-IDrawY[1])
						IDrawY[3]=MouseY3D+(IDrawX[0]-IDrawX[1])
						Local IDrawRN#=(IDrawY[0]-IDrawY[2])*(IDrawX[3]-IDrawX[2])-(IDrawX[0]-IDrawX[2])*(IDrawY[3]-IDrawY[2])
						Local IDrawRD#=(IDrawX[1]-IDrawX[0])*(IDrawY[3]-IDrawY[2])-(IDrawY[1]-IDrawY[0])*(IDrawX[3]-IDrawX[2])
						
						;AKTUELLEN/WERT/SETZEN
						IDrawTN\SVal=Floor(IDrawRN/IDrawRD*255)
						
						;WERTE/EINGRENZUNG
						If IDrawTN\SVal>255 Then IDrawTN\SVal=255
						If IDrawTN\SVal<0 Then IDrawTN\SVal=0
					Else
						
						;VERTEX/POLYGON/GLOW
						IDrawV0=AddVertex(IDrawTH\Face,Floor(IDrawX[1]),IDrawY[0],0 ,IDrawTH\SliderUV[06],IDrawTH\SliderUV[14])
						IDrawV1=AddVertex(IDrawTH\Face,Floor(IDrawX[2]),IDrawY[0],0 ,IDrawTH\SliderUV[07],IDrawTH\SliderUV[14])
						IDrawV2=AddVertex(IDrawTH\Face,Floor(IDrawX[1]),IDrawY[1],0 ,IDrawTH\SliderUV[06],IDrawTH\SliderUV[15])
						IDrawV3=AddVertex(IDrawTH\Face,Floor(IDrawX[2]),IDrawY[1],0 ,IDrawTH\SliderUV[07],IDrawTH\SliderUV[15])
						AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV2: AddTriangle IDrawTH\Face,IDrawV2,IDrawV1,IDrawV3
					End If
				End If
				
				
				
				
				;[End Block]
				
		End Select
	Next
	
	;RÜCKGABEWERT
	Return IDrawOver
	
	
	
	
End Function
Function FreeSGG%(FDrawHnde%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTH.SGGHaupt
	Local IDrawTN.SGGNeben
	Local IDrawTU.SGGUnter
	Local IDrawTHCount%=0
	Local IDrawTNCount%=0
	
	;HAUPT/TYPE/ID/AUFGREIFEN
	IDrawTH.SGGHaupt=Object.SGGHaupt(FDrawHnde)
	
	;NEBENEINTRÄGE/AUFGREIFEN
	For IDrawTHCount=0 To (BankSize(IDrawTH\Bank)/4)-1
		
		;NEBEN/TYPE/ID/AUFGREIFEN
		IDrawTN.SGGNeben=Object.SGGNeben(PeekInt(IDrawTH\Bank,IDrawTHCount*4))
		
		;ALLE/UNTER(ITEMS)LÖSCHEN
		For IDrawTNCount=0 To (BankSize(IDrawTN\Bank)-4)/4
			
			;UNTER/TYPE/ID/AUFGREIFEN
			IDrawTU.SGGUnter=Object.SGGUnter(PeekInt(IDrawTN\Bank,(IDrawTNCount)*4))
			
			;UNTER/TYIE/LÖSCHEN
			Delete IDrawTU
		Next
		
		;NEBEN/BANK/LÖSCHEN
		FreeBank IDrawTN\Bank
		
		;NEBEN/TYIE/LÖSCHEN
		Delete IDrawTN
	Next
	
	;HAUPT/BANK/LÖSCHEN
	FreeBank IDrawTH\Bank
	
	;HAUPT/TYIE/LÖSCHEN
	Delete IDrawTH
	
	;RÜCKGABEWERT
	Return True
	
	
	
	
End Function


;GET/FUNKTIONEN/DER/SGG
Function GetArtsSGG%(FDrawHnde%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;RÜCKGABEWERT
	Return IDrawTN\Arts
	
	
	
	
End Function
Function GetAlignSGG%(FDrawHnde%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;RÜCKGABEWERT
	Return IDrawTN\Algn
	
	
	
	
End Function
Function GetBoundSGG%(FDrawHnde%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;RÜCKGABEWERT
	Return IDrawTN\Cffs+1
	
	
	
	
End Function
Function GetEntriesSGG%(FDrawHnde%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;RÜCKGABEWERT
	Return BankSize(IDrawTN\Bank)/4
	
	
	
	
End Function
Function GetListPosSGG%(FDrawHnde%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;RÜCKGABEWERT
	Return IDrawTN\Sffs
	
	
	
	
End Function
Function GetSelectSGG%(FDrawHnde%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;RÜCKGABEWERT
	If IDrawTN\Arts=5 Then Return IDrawTN\SVal Else Return IDrawTN\Bffs/4
	
	
	
	
End Function
Function GetStringSGG$(FDrawHnde%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	Local IDrawTU.SGGUnter
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;UNTER/TYPE/ID/AUFGREIFEN
	IDrawTU.SGGUnter=Object.SGGUnter(PeekInt(IDrawTN\Bank,IDrawTN\Bffs))
	
	;RÜCKGABEWERT
	Return IDrawTU\Item
	
	
	
	
End Function
Function GetWidthSGG%(FDrawHnde%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;RÜCKGABEWERT
	Return IDrawTN\XRan
	
	
	
	
End Function
Function GetXPosSGG%(FDrawHnde%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;RÜCKGABEWERT
	Return IDrawTN\XPos
	
	
	
	
End Function
Function GetYPosSGG%(FDrawHnde%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;RÜCKGABEWERT
	Return IDrawTN\YPos
	
	
	
	
End Function


;SET/FUNKTIONEN/DER/SGG
Function SetArtsSGG%(FDrawHnde%,FDrawWert%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;NEUEN/WERT/FESTLEGEN
	IDrawTN\Arts=FDrawWert
	If IDrawTN\Arts<1 Then IDrawTN\Arts=1
	If IDrawTN\Arts>5 Then IDrawTN\Arts=5
	
	
	
	
End Function
Function SetAlignSGG%(FDrawHnde%,FDrawWert%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;NEUEN/WERT/FESTLEGEN
	IDrawTN\Algn=FDrawWert
	If IDrawTN\Algn<0 Then IDrawTN\Algn=0
	If IDrawTN\Algn>2 Then IDrawTN\Algn=2
	
	
	
	
End Function
Function SetBoundSGG%(FDrawHnde%,FDrawWert%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	Local IDrawSize%=0
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;NEUEN/WERT/FESTLEGEN
	IDrawTN\Cffs=FDrawWert-1
	IDrawSize=(BankSize(IDrawTN\Bank)-4)/4
	If IDrawTN\Sffs>IDrawSize-IDrawTN\Cffs Then IDrawTN\Sffs=IDrawSize-IDrawTN\Cffs
	If IDrawTN\Sffs<0 Then IDrawTN\Sffs=0
	If IDrawTN\Cffs<0 Then IDrawTN\Cffs=0
	
	
	
	
End Function
Function SetListPosSGG%(FDrawHnde%,FDrawWert%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	Local IDrawSize%=0
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;NEUEN/WERT/FESTLEGEN
	IDrawTN\Sffs=FDrawWert
	IDrawSize=(BankSize(IDrawTN\Bank)-4)/4
	If IDrawTN\Sffs>IDrawSize-IDrawTN\Cffs Then IDrawTN\Sffs=IDrawSize-IDrawTN\Cffs
	If IDrawTN\Sffs<0 Then IDrawTN\Sffs=0
	
	
	
	
End Function
Function SetSelectSGG%(FDrawHnde%,FDrawWert%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	Local IDrawSize%=0
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;ÜBERPRÜFEN/OB/SLIDER
	If IDrawTN\Arts=5 Then
		
		;SLIDER/POS/FESTLEGEN
		IDrawTN\SVal=FDrawWert
		If IDrawTN\SVal<0 Then IDrawTN\SVal=0
		If IDrawTN\SVal>255 Then IDrawTN\SVal=255
	Else
		
		;NICHT/SLIDER/FESTLEGEN
		IDrawTN\Bffs=FDrawWert*4
		If IDrawTN\Bffs<0 Then IDrawTN\Bffs=0
		IDrawSize=(BankSize(IDrawTN\Bank)-4)/4
		If IDrawTN\Bffs>IDrawSize*4 Then IDrawTN\Bffs=IDrawSize*4
		If IDrawTN\Bffs/4>(IDrawTN\Cffs+IDrawTN\Sffs-1) Then IDrawTN\Sffs=IDrawTN\Bffs/4-IDrawTN\Cffs
		If IDrawTN\Bffs/4<(IDrawTN\Sffs) Then IDrawTN\Sffs=IDrawTN\Bffs/4
	End If
	
	
	
	
End Function
Function SetStringSGG%(FDrawHnde%,FDrawStrg$)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	Local IDrawTU.SGGUnter
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;UNTER/TYPE/ID/AUFGREIFEN
	IDrawTU.SGGUnter=Object.SGGUnter(PeekInt(IDrawTN\Bank,IDrawTN\Bffs))
	
	;ITEM/ZUWEISUNG
	IDrawTU\Item=FDrawStrg
	
	
	
	
End Function
Function SetWidthSGG%(FDrawHnde%,FDrawWert%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;NEUEN/WERT/FESTLEGEN
	IDrawTN\XRan=FDrawWert
	If IDrawTN\XRan<4 Then IDrawTN\XRan=4
	
	
	
	
End Function
Function SetXPosSGG%(FDrawHnde%,FDrawWert%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;NEUEN/WERT/FESTLEGEN
	IDrawTN\XPos=FDrawWert
	
	
	
	
End Function
Function SetYPosSGG%(FDrawHnde%,FDrawWert%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;NEUEN/WERT/FESTLEGEN
	IDrawTN\YPos=FDrawWert
	
	
	
	
End Function


;SONSTIGE/ZUSATZFUNKTIONEN
Function AddListPosSGG%(FDrawHnde%,FDrawWert%)
	
	
	
	
	;DURCHPRÜFEN
	SetListPosSGG(FDrawHnde,GetListPosSGG(FDrawHnde)+FDrawWert)
	
	
	
	
End Function
Function AddSelectSGG%(FDrawHnde%,FDrawWert%)
	
	
	
	
	;DURCHPRÜFEN
	SetSelectSGG(FDrawHnde,GetSelectSGG(FDrawHnde)+FDrawWert)
	
	
	
	
End Function
Function DrawButton3D%(FDrawHnde%,FDrawXPos%,FDrawYPos%,FDrawStrg$,FDrawAlgn%,FDrawXRan%)
	
	
	
	
	;PARAMETER/VORREINIGUNG
	If FDrawAlgn<0 Then FDrawAlgn=0
	If FDrawAlgn>2 Then FDrawAlgn=2
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTH.SGGHaupt
	
	;HAUPT/TYPE/ID/AUFGREIFEN
	IDrawTH.SGGHaupt=Object.SGGHaupt(FDrawHnde)
	
	;VARIABLENDEKLARATIONEN
	Local IDrawV0%=0, IDrawV1%=0, IDrawV2%=0, IDrawV3%=0
	Local IDrawV4%=0, IDrawV5%=0, IDrawV6%=0, IDrawV7%=0
	Local IDrawV8%=0, IDrawV9%=0, IDrawVA%=0, IDrawVB%=0
	Local IDrawVC%=0, IDrawVD%=0, IDrawVE%=0, IDrawVF%=0
	Local IDrawX#[3]
	Local IDrawY#[1]
	
	;TEMPWERTE/VORRECHNEN
	IDrawX[0]=FDrawXPos-FDrawXRan+IDrawTH\ButtonUV[02]-IDrawTH\ButtonUV[03]
	IDrawX[1]=FDrawXPos-FDrawXRan
	IDrawX[2]=FDrawXPos+FDrawXRan
	IDrawX[3]=FDrawXPos+FDrawXRan-IDrawTH\ButtonUV[02]+IDrawTH\ButtonUV[03]
	IDrawY[0]=FDrawYPos+(IDrawTH\ButtonUV[13]-IDrawTH\ButtonUV[12])*0.5
	IDrawY[1]=FDrawYPos-(IDrawTH\ButtonUV[13]-IDrawTH\ButtonUV[12])*0.5
	
	;VERTEX/POLYGON/ZUWEISUNGEN
	IDrawV0=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[0],0 ,IDrawTH\ButtonUV[00],IDrawTH\ButtonUV[12])
	IDrawV1=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[0],0 ,IDrawTH\ButtonUV[01],IDrawTH\ButtonUV[12])
	IDrawV2=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[0],0 ,IDrawTH\ButtonUV[02],IDrawTH\ButtonUV[12])
	IDrawV3=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[0],0 ,IDrawTH\ButtonUV[03],IDrawTH\ButtonUV[12])
	IDrawV4=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[1],0 ,IDrawTH\ButtonUV[00],IDrawTH\ButtonUV[13])
	IDrawV5=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[1],0 ,IDrawTH\ButtonUV[01],IDrawTH\ButtonUV[13])
	IDrawV6=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[1],0 ,IDrawTH\ButtonUV[02],IDrawTH\ButtonUV[13])
	IDrawV7=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[1],0 ,IDrawTH\ButtonUV[03],IDrawTH\ButtonUV[13])
	AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV5: AddTriangle IDrawTH\Face,IDrawV5,IDrawV4,IDrawV0
	AddTriangle IDrawTH\Face,IDrawV1,IDrawV2,IDrawV6: AddTriangle IDrawTH\Face,IDrawV6,IDrawV5,IDrawV1
	AddTriangle IDrawTH\Face,IDrawV2,IDrawV3,IDrawV7: AddTriangle IDrawTH\Face,IDrawV7,IDrawV6,IDrawV2
	
	;CAPTION/AUSGEBEN
	If FDrawAlgn=0 Then Text3D(IDrawTH\Font,IDrawX[1]+IDrawTH\XSpc,FDrawYPos,FDrawStrg,0)
	If FDrawAlgn=1 Then Text3D(IDrawTH\Font,FDrawXPos             ,FDrawYPos,FDrawStrg,1)
	If FDrawAlgn=2 Then Text3D(IDrawTH\Font,IDrawX[2]-IDrawTH\XSpc,FDrawYPos,FDrawStrg,2)
	
	;MOUSE/CHECK
	If CheckQuad3D(IDrawX[0],IDrawY[0],IDrawX[3],IDrawY[0],IDrawX[3],IDrawY[1],IDrawX[0],IDrawY[1],1,IDrawTH\Glow)>0 Then
		
		;BUTTON/VERARBEITUNG
		If MouseDown3D>0 Then
			
			;VERTEX/POLYGON/ZUWEISUNGEN
			IDrawV0=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[0],0 ,IDrawTH\ButtonUV[08],IDrawTH\ButtonUV[16])
			IDrawV1=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[0],0 ,IDrawTH\ButtonUV[09],IDrawTH\ButtonUV[16])
			IDrawV2=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[0],0 ,IDrawTH\ButtonUV[10],IDrawTH\ButtonUV[16])
			IDrawV3=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[0],0 ,IDrawTH\ButtonUV[11],IDrawTH\ButtonUV[16])
			IDrawV4=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[1],0 ,IDrawTH\ButtonUV[08],IDrawTH\ButtonUV[17])
			IDrawV5=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[1],0 ,IDrawTH\ButtonUV[09],IDrawTH\ButtonUV[17])
			IDrawV6=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[1],0 ,IDrawTH\ButtonUV[10],IDrawTH\ButtonUV[17])
			IDrawV7=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[1],0 ,IDrawTH\ButtonUV[11],IDrawTH\ButtonUV[17])
			AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV5: AddTriangle IDrawTH\Face,IDrawV5,IDrawV4,IDrawV0
			AddTriangle IDrawTH\Face,IDrawV1,IDrawV2,IDrawV6: AddTriangle IDrawTH\Face,IDrawV6,IDrawV5,IDrawV1
			AddTriangle IDrawTH\Face,IDrawV2,IDrawV3,IDrawV7: AddTriangle IDrawTH\Face,IDrawV7,IDrawV6,IDrawV2
		Else
			
			;VERTEX/POLYGON/ZUWEISUNGEN
			IDrawV0=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[0],0 ,IDrawTH\ButtonUV[04],IDrawTH\ButtonUV[14])
			IDrawV1=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[0],0 ,IDrawTH\ButtonUV[05],IDrawTH\ButtonUV[14])
			IDrawV2=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[0],0 ,IDrawTH\ButtonUV[06],IDrawTH\ButtonUV[14])
			IDrawV3=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[0],0 ,IDrawTH\ButtonUV[07],IDrawTH\ButtonUV[14])
			IDrawV4=AddVertex(IDrawTH\Face,IDrawX[0],IDrawY[1],0 ,IDrawTH\ButtonUV[04],IDrawTH\ButtonUV[15])
			IDrawV5=AddVertex(IDrawTH\Face,IDrawX[1],IDrawY[1],0 ,IDrawTH\ButtonUV[05],IDrawTH\ButtonUV[15])
			IDrawV6=AddVertex(IDrawTH\Face,IDrawX[2],IDrawY[1],0 ,IDrawTH\ButtonUV[06],IDrawTH\ButtonUV[15])
			IDrawV7=AddVertex(IDrawTH\Face,IDrawX[3],IDrawY[1],0 ,IDrawTH\ButtonUV[07],IDrawTH\ButtonUV[15])
			AddTriangle IDrawTH\Face,IDrawV0,IDrawV1,IDrawV5: AddTriangle IDrawTH\Face,IDrawV5,IDrawV4,IDrawV0
			AddTriangle IDrawTH\Face,IDrawV1,IDrawV2,IDrawV6: AddTriangle IDrawTH\Face,IDrawV6,IDrawV5,IDrawV1
			AddTriangle IDrawTH\Face,IDrawV2,IDrawV3,IDrawV7: AddTriangle IDrawTH\Face,IDrawV7,IDrawV6,IDrawV2
		End If
	End If
	
	
	
	
End Function
Function InviteSGG%(FDrawHnde%,FDrawStrg$,FDrawDash%=1,FDrawVerz$="")
	
	
	
	
	;EINGABE/VORABREINIGUNG
	FDrawStrg=Replace(Lower(FDrawStrg),".","")
	FDrawVerz=CurrentDir()+FDrawVerz
	If FDrawDash<0 Then FDrawDash=0
	If FDrawDash>1 Then FDrawDash=1
	
	;VARIABLENDEKLARATIONEN
	Local IDrawEiner$=""
	Local IDrawAlles$=""
	Local IDrawDatei$=""
	Local IDrawValue%=0
	Local IDrawVErz%=0
	
	;STRING/DURCHSTÖBERN
	While Not IDrawValue=Len(FDrawStrg)
		
		;NÄCHSTES/ZEICHEN
		IDrawValue=IDrawValue+1
		
		;EIN/ZEICHEN/ANHÄNGEN
		IDrawEiner=Mid(FDrawStrg,IDrawValue,1)
		IDrawAlles=IDrawAlles+IDrawEiner
		
		;TEILER/SUCHEN/ANWENDEN
		If IDrawEiner="|" Or IDrawValue=Len(FDrawStrg) Then
			
			;STRINGEINTRAG/SÄUBERN
			IDrawAlles=Replace(IDrawAlles,"|","")
			
			;ARBEITSVERZEICHNIS
			IDrawVErz=ReadDir(FDrawVerz)
			
			;BAMM
			Repeat
				IDrawDatei=NextFile$(IDrawVErz)
				If Right(Lower(IDrawDatei),Len(IDrawAlles))=IDrawAlles Then
					If FDrawDash=1 Then IDrawDatei=Replace(IDrawDatei,"."+IDrawAlles,"")
					AppendSGG(FDrawHnde,IDrawDatei)
				End If
			Until IDrawDatei=""
			
			;SAUBER/MACHEN
			CloseDir IDrawVErz
			
			;SAMMLERRESET
			IDrawAlles=""
		End If
	Wend
	
	
	
	
End Function
Function SortSGG%(FDrawHnde%)
	
	
	
	
	;VARIABLENDEKLARATIONEN
	Local IDrawTN.SGGNeben
	Local IDrawTU.SGGUnter
	Local IDrawMMCount%
	Local IDrawTNCount%
	Local IDrawAnzahl%
	Local IDrawItem1$
	Local IDrawItem2$
	Local IDrawPeek1%
	Local IDrawPeek2%
	
	;ANZAHL/EINTRÄGE/SPEICERN
	IDrawAnzahl=GetEntriesSGG%(FDrawHnde)
	
	;NEBEN/TYPE/ID/AUFGREIFEN
	IDrawTN.SGGNeben=Object.SGGNeben(FDrawHnde)
	
	;NÖTIGE/DURCHLÄUFE/MACHEN
	For IDrawMMCount=0 To IDrawAnzahl-2
		For IDrawTNCount=0 To IDrawAnzahl-2
			
			;WERT(1)/AUSLESEN/UND/SICHERN
			IDrawPeek1=PeekInt(IDrawTN\Bank,0+IDrawTNCount*4)
			IDrawTU.SGGUnter=Object.SGGUnter(IDrawPeek1)
			IDrawItem1=Lower(IDrawTU\Item)
			
			;WERT(2)/AUSLESEN/UND/SICHERN
			IDrawPeek2=PeekInt(IDrawTN\Bank,4+IDrawTNCount*4)
			IDrawTU.SGGUnter=Object.SGGUnter(IDrawPeek2)
			IDrawItem2=Lower(IDrawTU\Item)
			
			;WERTE/VERGLEICHEN
			If IDrawItem1>IDrawItem2 Then
				
				;BUBBLESORT/DURCHFÜHREN
				PokeInt(IDrawTN\Bank,0+IDrawTNCount*4,IDrawPeek2)
				PokeInt(IDrawTN\Bank,4+IDrawTNCount*4,IDrawPeek1)
			End If
		Next
	Next
	
	
	
	
End Function




;~IDEal Editor Parameters:
;~F#8#26#38#44#4C#72#AF#157#186#1D7#216#225#282#30B#394#41B#451#463#475#487
;~F#499#4AB#4BD#4D3#4E5#4F7#50C#520#534#54B#561#583#599#5AC#5BE#5D3#5DF#5EB#643#67D
;~C#Blitz3D