;----------------------------------------------------------------
;! Constants
;----------------------------------------------------------------
Const COLLISION_GROUP_WORLD										= 1
Const COLLISION_GROUP_PLAYER									= 2
Const COLLISION_GROUP_PROJECTILE								= 3

;----------------------------------------------------------------
;! Functions
;----------------------------------------------------------------
Function Collisions_Initialize()
	Collisions COLLISION_GROUP_PLAYER, COLLISION_GROUP_WORLD, 2, 2
	Collisions COLLISION_GROUP_PROJECTILE, COLLISION_GROUP_PLAYER, 3, 0
	Collisions COLLISION_GROUP_PROJECTILE, COLLISION_GROUP_WORLD, 2, 0
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D