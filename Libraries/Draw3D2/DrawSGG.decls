.lib " "

SGGOver3D( )
SGGDown3D( )
SGGHit3D( )
SGGSpz3D( )
SGGKey3D( )
SGGEntry( )

SGGOUTLAY( )
SGGBUTTON( )
SGGINTAKE( )
SGGROSTER( )
SGGSLIDER( )

AddingSGG%( handle%, arts%, x_pos%, y_pos%, string$, align%, width% )
AppendSGG%( handle%, string$ )
CreateSGG%( x_pos%, y_pos%, width%, height%, sgg_file$, pivot%, order% )
DeleteSGG%( handle%, list_pos% )
DrawSGG%( handle%, x_pos%, y_pos% )
FreeSGG%( handle% )

GetArtsSGG%( handle% )
GetAlignSGG%( handle% )
GetBoundSGG%( handle% )
GetEntriesSGG%( handle% )
GetListPosSGG%( handle% )
GetSelectSGG%( handle% )
GetStringSGG$( handle% )
GetWidthSGG%( handle% )
GetXPosSGG%( handle% )
GetYPosSGG%( handle% )

SetArtsSGG%( handle%, value% )
SetAlignSGG%( handle%, value% )
SetBoundSGG%( handle%, value% )
SetListPosSGG%( handle%, value% )
SetSelectSGG%( handle%, value% )
SetStringSGG%( handle%, value% )
SetWidthSGG%( handle%, value% )
SetXPosSGG%( handle%, value% )
SetYPosSGG%( handle%, value% )

AddListPosSGG%( handle%, value% )
AddSelectSGG%( handle%, value% )
DrawButton3D%( handle%, x_pos%, y_pos%, string$, align%, width% )
InviteSGG%( handle%, string$, dash%, directory$ )
SortSGG%( handle% )

