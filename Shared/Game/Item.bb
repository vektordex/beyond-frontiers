;----------------------------------------------------------------
;-- Constants
;----------------------------------------------------------------
Const ITEMDATABASE_SQL_INITIALIZE$			= "CREATE TABLE IF NOT EXISTS items (Id INTEGER PRIMARY KEY AUTO_INCREMENT, Name TEXT, Description TEXT, Tier INTEGER, Credits REAL, IconId INTEGER);"
Const ITEMDATABASE_SQL_ADD$					= "INSERT OR REPLACE INTO items VALUES (\0, \1, \2, \3, \4, \5);"
Const ITEMDATABASE_SQL_REMOVE$				= "DELETE FROM items INDEXED BY Id WHERE Id = \0;"
Const ITEMDATABASE_SQL_RETRIEVE$			= "SELECT * FROM items INDEXED BY Id;"
Const ITEMDATABASE_SQL_SEARCH_ID$			= "SELECT * FROM items INDEXED BY Id WHERE ID = \0;"
Const ITEMDATABASE_SQL_SEARCH_NAME$			= "SELECT * FROM items INDEXED BY Id WHERE Name LIKE \0;"
Const ITEMDATABASE_SQL_SEARCH_DESCRIPTION$	= "SELECT * FROM items INDEXED BY Id WHERE Description LIKE \0;"

Const ITEM_SQL_DELETE$			= "DELETE FROM items WHERE Id = \0;"
;Const ITEM_SQL_GET_BY_NAME$		= "SELECT * FROM items WHERE Name LIKE \0;"
;Const ITEM_SQL_GET_BY_WORTH$	= "SELECT * FROM items WHERE Credits >= \0 AND Credits <= \0;"

;----------------------------------------------------------------
;-- Types
;----------------------------------------------------------------
Type Item
	Field Id
	
	; Text stuff
	Field Name$
	Field Description$
	
	; Credits
	Field Credits
	
	; Visuals
	Field IconId
End Type

Type ItemObject
	Field Position.Position
	
	Field ItemId%, Quality#, Amount%
End Type

;----------------------------------------------------------------
;-- Globals
;----------------------------------------------------------------
Global ItemDatabase_Initialized	= False
Global ItemDatabase_DB%			= 0

;----------------------------------------------------------------
;-- Functions
;----------------------------------------------------------------
Function ItemDatabase_Initialize(Database)
	ItemDatabase_DB = Database
	If ItemDatabase_DB
		If ExecuteSQL(ITEMDATABASE_SQL_INITIALIZE, ItemDatabase_DB, DEBUG)
			ItemDatabase_Initialized = True
			Return False
		Else
			Return True
		EndIf
	Else
		Return True
	EndIf
End Function

Function ItemDatabase_Load()
	If ItemDatabase_Initialized Then
		Local Stmt = PrepareSQL(ITEMDATABASE_SQL_RETRIEVE, ItemDatabase_DB, True, DEBUG)
		;While 
	EndIf
End Function

Function ItemDatabase_LoadSingle.Item(Id%)
	
End Function

Function ItemDatabase_GetById.Item(Id%)
	
End Function

Function ItemDatabase_SaveSingle(Id%, Item.Item)
	
End Function

Function ItemDatabase_Save()
	
End Function

Function ItemDatabase_UnloadSingle(Id%, Item.Item)
	
End Function

Function ItemDatabase_Unload()
	
End Function

Function ItemDatabase_Deinitialize()
	ItemDatabase_Initialized = False
End Function
;~IDEal Editor Parameters:
;~F#20
;~C#BlitzPlus