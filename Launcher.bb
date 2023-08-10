Global WinHandle = CreateWindow("beyond.frontiers - Launcher",GraphicsWidth()/2-300,GraphicsHeight()/2-200,600,400,0,0)

intModes=CountGfxModes() 
For t = 1 To intModes 
DebugLog GfxModeWidth(t) + "x" + GfxModeHeight(t) +" at " + GfxModeDepth(t) +"bits depth"
Next 

menu=WindowMenu(WinHandle) 
Game=CreateMenu("Game",0,menu) ; main menu 
CreateMenu "Open Game Folder",1, Game ; child menu 
CreateMenu "",0, Game; Use an empty string to generate separator bars 
CreateMenu "Quit",99, Game ; another child menu 

Resolution=CreateMenu("Resolution",0,menu) ; main menu 
CreateMenu "1280 x 720",40,Resolution
CreateMenu "1280 x 800",41,Resolution
CreateMenu "1360 x 768",42,Resolution
CreateMenu "1600 x 900",43,Resolution
CreateMenu "1920 x 1080",44,Resolution
CreateMenu "2020 x 1440 (Experimental)",45,Resolution




UpdateWindowMenu WinHandle

Global GameDir$=GetEnv$

Repeat 
	If WaitEvent()=$803 Then Exit 
	EID=EventData() ; Event data contains the menu ID specified when creating the menu items 
	Select EID 
					; Events in Case of Game Menu
		Case 1
			ExecFile "explorer.exe"
		
					; Events in Case of Resolution Menu

					; Events in Case of Game Ending
		Case 99
			End 
	End Select

Forever 

End ; bye! 
