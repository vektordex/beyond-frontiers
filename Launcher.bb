Global WinHandle = CreateWindow("beyond.frontiers - Launcher",GraphicsWidth()/2-300,GraphicsHeight()/2-200,600,400,0,0)

intModes=CountGfxModes() 
For t = 1 To intModes 
DebugLog GfxModeWidth(t) + "x" + GfxModeHeight(t) +" at " + GfxModeDepth(t) +"bits depth"
Next 

menu=WindowMenu(WinHandle) 
file=CreateMenu("Game",0,menu) ; main menu 
CreateMenu "OpenFolder",1,file ; child menu 
CreateMenu "",0,file ; Use an empty string to generate separator bars 
CreateMenu "Quit",3,file ; another child menu 

UpdateWindowMenu WinHandle

Repeat 
	If WaitEvent()=$803 Then Exit 



Forever 

End ; bye! 
