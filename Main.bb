;! Bug Fix: Early CTD caused by Blitz3D not being ready at this time.
Delay 100

;----------------------------------------------------------------
;! Includes
;----------------------------------------------------------------
; Libraries
Include "Libraries\BlitzUtility\BlitzUtility.bb"
Include "Libraries\InputEx\InputEx.bb"
Include "Libraries\System\Threading.bb"
Include "Libraries\System\ThreadPool.bb"
Include "Libraries\Draw3D2\Includes\Draw3D2.bb"

; Shared
Include "Shared\Version.bb"

; Engine
Include "Engine\LogHandler.bb"
Include "Engine\AssetManager.bb"
Include "Engine\Settings.bb"

; Game Functions
; >> Basic
Include "Modules\BasicTypes.bb"
Include "Modules\BasicVars.bb"

; >> Game Logic
Include "Modules\Controls.bb"
Include "Modules\MainLogic.bb"
Include "Modules\PlayerLogic.bb"

; >> Graphics
Include "Modules\Asset_Loading.bb"
Include "Modules\Starfield.bb"
Include "Modules\Textures.bb"

; >> Math
Include "Modules\BasicData.bb"
Include "Modules\BasicMath.bb"
Include "Modules\Coordinates.bb"

; >> Sound

; >> Utility
Include "Modules\Utilities.bb"

; >> World
Include "Modules\Collisions.bb"
Include "Modules\WorldGenerator.bb"
;~IDEal Editor Parameters:
;~C#Blitz3D