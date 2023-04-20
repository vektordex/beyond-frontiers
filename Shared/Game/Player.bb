;----------------------------------------------------------------
;-- Constant
;----------------------------------------------------------------
Const PLAYER_NAME_CHARACTERS$ = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-*+!? "
Const PLAYER_NAME_MAX_LENGTH% = 32

;----------------------------------------------------------------
;-- Types
;----------------------------------------------------------------
Type Player
	Field SystemAddress			; Network 'Address' (virtual address inside Raknet)
	Field AccountId%			; Account Id for this Player
	
	; Player Information
	Field Position.Position
	Field Rotation.Rotation
	Field Name$					; Player Name
	Field Health#				; Current Health (Hull)
	Field Shield#				; Current Shield
	Field Energy#				; Current Energy (Required to recharge Shield and chat)
	
	; Customization options
	Field ShipId%				; Ship Index (See Ship.bb)
	Field ShipSkinId%			; Skin customization
	Field ColorRGB%				; Color customization
	
	;-- Client-only
	Field ClientPlayer.ClientPlayer
End Type

Type ClientPlayer
	Field Pivot%
	Field Body%
End Type

;----------------------------------------------------------------
;-- Functions
;----------------------------------------------------------------

;~IDEal Editor Parameters:
;~C#BlitzPlus