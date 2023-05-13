Function Prepare_Camera()
; Scene: Camera
CameraScene = CreateCamera()
CameraRange CameraScene, CAMERA_NEAR, CAMERA_FAR
CameraZoom CameraScene, 1.0 / Tan(90 / 2.0) ;!ToDo: Field of View settings, see line below.
;CameraZoom CameraScene, 1.0 / Tan(FOV# / 2.0) 

End Function
;~IDEal Editor Parameters:
;~C#Blitz3D