# Persistencia con Room en Simon Me Dijo

Este proyecto usa Room para guardar el récord máximo de un juego de memoria estilo Simon Says.

## Arquitectura de Room

### 1. Entidad: `RecordEntity.kt`
Representa la tabla de la base de datos:
- `id: Int` → único registro.
- `maxRonda: Int` → número máximo de rondas alcanzadas.
- `fechaTexto: String` → fecha del récord.
- `tiempoMS: Long` → timestamp.

### 2. DAO: `RecordDao.kt`
Define las operaciones sobre la tabla:
- `getRecord()`: obtiene el récord actual.
- `saveRecord(record: RecordEntity)`: guarda o reemplaza el récord.

### 3. Base de datos: `AppDatabase.kt`
- Define la base de datos Room y expone el DAO.
- `abstract fun recordDao(): RecordDao`

### 4. Interfaz: `HistorialRecord.kt`
- Permite trabajar con récords sin depender de la implementación.
- Métodos:
    - `guardarRecord(record: Record)`
    - `cargarRecord(): Record?`
    - `obtenerRondaRecord(): Int`

### 5. Implementación: `RecordRoom.kt`
- Implementa `HistorialRecord` usando Room.
- Usa `withContext(Dispatchers.IO)` para no bloquear la UI.
- Funciona con **coroutines** para operaciones asíncronas.

## Flujo
1. Al iniciar la app, se carga el récord con `cargarRecord()`.
2. Al finalizar una ronda, se comprueba si hay nuevo récord y se guarda con `guardarRecord()`.
3. La UI observa `MutableStateFlow` y actualiza el récord automáticamente.

## Beneficios
- Persistencia segura y eficiente.
- Operaciones asíncronas integradas con coroutines.
- Fácil mantenimiento y cambio de implementación gracias a la interfaz `HistorialRecord`.

classDiagram
%% ENTIDADES %%
class RecordEntity {
+Int id
+Int maxRonda
+String fechaTexto
+Long tiempoMS
}

## Diagrama de Clases (Room y Juego)

```mermaid
classDiagram
%% ENTIDADES %%
class RecordEntity {
+Int id
+Int maxRonda
+String fechaTexto
+Long tiempoMS
}

%% DAO %%
class RecordDao {
+suspend getRecord(): RecordEntity?
+suspend saveRecord(record: RecordEntity)
}

%% BASE DE DATOS %%
class AppDatabase {
+recordDao(): RecordDao
}

%% INTERFAZ %%
class HistorialRecord {
+suspend guardarRecord(record: Record)
+suspend cargarRecord(): Record?
+suspend obtenerRondaRecord(): Int
}

%% IMPLEMENTACION %%
class RecordRoom {
-db: AppDatabase
-dao: RecordDao
+suspend guardarRecord(record: Record)
+suspend cargarRecord(): Record?
+suspend obtenerRondaRecord(): Int
}

%% VIEWMODEL %%
class MyVM {
+inicializarHistorial(context)
+cargarRecordInicial()
+comprobarRecord()
+iniciarJuego()
+colorSeleccionado(color)
}

%% DATOS DEL JUEGO %%
class Datos {
+ronda: MutableStateFlow<Int>
+record: MutableStateFlow<Int>
+estado: MutableStateFlow<Estado>
+secuencia: MutableStateFlow<List<Colores>>
+currentColorEncendido: MutableStateFlow<Colores?>
}

%% UI %%
class Botonera {
+IU(viewModel: MyVM)
}

%% CONEXIONES ROOM %%
RecordRoom --> HistorialRecord : implementa
RecordRoom --> AppDatabase : usa
AppDatabase --> RecordDao : contiene
RecordDao --> RecordEntity : maneja

%% CONEXIONES CON VIEWMODEL %%
MyVM --> RecordRoom : usa para guardar/cargar record
MyVM --> Datos : actualiza estado del juego y record

%% CONEXIONES UI %%
Botonera --> MyVM : llama funciones de juego
Botonera --> Datos : observa MutableStateFlow para UI reactiva
```