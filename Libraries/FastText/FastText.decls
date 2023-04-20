.lib "FastText.dll"
LoadFont_%(fontname$,height%,bold%,italic%,underline%,angle#,smooth%,encoding%):"LoadFont_"
SetFont_%(font%):"SetFont_"
FreeFont_%(font%):"FreeFont_"
FontWidth_%():"FontWidth_"
FontHeight_%():"FontHeight_"
Text_%(x%,y%,text$,cx%,cy%,encoding%):"Text_"
TextRect_%(x%,y%,w%,h%,text$,format%,encoding%):"TextRect_"
TextBackground_(r%,g%,b%):"TextBackground_"
StringWidth_%(text$,encoding%):"StringWidth_"
StringHeight_%(text$,encoding%):"StringHeight_"

.lib " "
TextRect%(x%,y%,w%,h%,text$,format%,encoding%)
TextBackground(r%,g%,b%)