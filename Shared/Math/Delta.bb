;----------------------------------------------------------------
;! Types
;----------------------------------------------------------------
Type Delta
	Field Begin%, End%, Delta%
	Field Expected
	
	Field Multiplier#
	Field BalancedMultiplier#
End Type

;----------------------------------------------------------------
;! Functions
;----------------------------------------------------------------
Function Delta_Initialize.Delta(Expected)
	Local Delta.Delta			= New Delta
	Delta\Begin					= MilliSecs()
	Delta\End					= MilliSecs()
	Delta\Expected				= Expected
	Delta\Delta					= Delta\Expected
	Delta\Multiplier			= 1.0
	Delta\BalancedMultiplier	= 1.0
	
	Return Delta
End Function

Function Delta_Begin(Delta.Delta)
	Delta\Begin = MilliSecs()
End Function

Function Delta_End(Delta.Delta)
	Delta\End	= MilliSecs()
	Delta\Delta	= Delta\End - Delta\Begin
	
	Delta\Multiplier			= Delta\Delta / Delta\Expected
	Delta\BalancedMultiplier	= (Delta\BalancedMultiplier * 0.9) + (Delta\Multiplier * 0.1)
	
	If Delta\Multiplier <= 0 Then Delta\Multiplier = 1.0
	If Delta\BalancedMultiplier <= 0 Then Delta\BalancedMultiplier = 1.0
	If Delta\BalancedMultiplier <= 0 Then Delta\BalancedMultiplier = 1.0
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D