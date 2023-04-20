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

Include "Shared\Version.bb"

; Engine
Include "Engine\LogHandler.bb"
Include "Engine\AssetManager.bb"
Include "Engine\Settings.bb"

; Game Functions
Include "Modules\LIBxDUST.bb"
Include "Modules\Base_Vars.bb"
Include "Modules\utilities.bb"	
Include "Modules\pcl-cloud2_6.bb"
Include "Modules\Math.bb"
Include "Modules\Graphics.bb"
Include "Modules\AI_Bots.bb"
Include "Modules\mainloop_controls_ui.bb"
Include "Modules\mainloop_graphics.bb"
Include "Modules\worldgen.bb"
Include "Modules\twitch.bb"
Include "Modules\Collisions.bb"
;~IDEal Editor Parameters:
;~C#Blitz3D