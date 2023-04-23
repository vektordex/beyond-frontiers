Function Prepare_Camera()
; Scene: Camera
CameraScene = CreateCamera()
CameraRange CameraScene, CAMERA_NEAR, CAMERA_FAR
CameraZoom CameraScene, 1.0 / Tan(90 / 2.0) ;!ToDo: Field of View settings, see line below.
;CameraZoom CameraScene, 1.0 / Tan(FOV# / 2.0) 

; UI: Camera
CameraUI = CreateCamera()
CameraClsMode CameraUI, 0, 1
CameraClsColor CameraUI, 127, 127, 127
CameraRange CameraUI, DRAWDISTANCE-64, DRAWDISTANCE+64
DrawInit3D(CameraUI)
HideEntity CameraUI

; Scene: Create a VirtualScene Object (hiding and showing where needed)
;Scene.VirtualScene = VirtualScene_Create()

; Data: Data Root (Invisible loaded Geometry)
SceneDataRoot = CreatePivot()
;EntityAlpha SceneDataRoot, 0
HideEntity SceneDataRoot
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D