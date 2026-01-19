# Simón Me Dijo

**Simón Me Dijo** es un proyecto en Android con **Jetpack Compose** que implementa la lógica de un juego de memoria.

---

## Diagrama de flujo del juego

```mermaid
flowchart TD
    A(["IDLE"]) -- Presionar Start --> B(["Genera la secuencia"])
    B -- Termina la secuencia --> C(["Usuario Replica Secuencia"])
    C -- Fallo/Error --> E(["Final"])
    C -- Acierta la secuencia --> B
    E -- Presiona Restart --> A
```

---

## Estructura del código

### 1. `Datos`

Objeto singleton que almacena los datos del juego, incluyendo la ronda actual, récord, estado del juego, secuencia de colores, color actualmente encendido y sonidos.

### 2. `Estado`

Enum que define los **cuatro estados del juego** y sus propiedades:

| Estado                | Botonera activa | Botón Start activo | Mensaje en pantalla             |
| --------------------- | --------------- | ------------------ | ------------------------------- |
| **IDLE**              | ❌               | ✅                  | "Presiona Start para comenzar"  |
| **GENERAR_SECUENCIA** | ❌               | ❌                  | "Generando secuencia"           |
| **ELECCION_USUARIO**  | ✅               | ❌                  | "Adivina la secuencia"          |
| **FINALIZADO**        | ❌               | ✅                  | "Fallaste, vuelve a intentarlo" |

### 3. `Colores`

Enum que define los colores usados en el juego con su **representación visual (`Color`)** y **texto descriptivo (`txt`)**:

* `CLASE_ROJO`, `CLASE_VERDE`, `CLASE_AZUL`, `CLASE_MORADO`

### 4. `MyVM`

ViewModel que centraliza la lógica del juego, gestionando la secuencia de colores, el estado del juego, las rondas y el récord.

### 5. UI (Jetpack Compose)

Contiene la interfaz del juego, incluyendo los botones de colores, el panel de texto, los contadores de ronda y récord, y la reproducción de sonidos según la secuencia y el estado.

### 6. Persistencia de Datos con MongoDB Realm

A partir de la versión 1.1, la aplicación utiliza **MongoDB Realm** para persistencia local de récords sin necesidad de backend:

#### Componentes:

- **`RealmRecordModel.kt`**: Modelo de datos que Realm persiste automáticamente
- **`RecordRealmRepository.kt`**: Repository con operaciones CRUD (Create, Read, Update, Delete)
- **`RecordRealmImpl.kt`**: Implementación de la interfaz `HistorialRecord` con Realm
- **`HistorialRecord.kt`**: Interfaz que permite cambiar la implementación de persistencia

#### Características:

✅ **Persistencia 100% local** en el dispositivo sin conexión a internet  
✅ **Encriptación automática** de datos sensibles  
✅ **Timestamp automático** en milisegundos  
✅ **Fecha legible** en formato dd/MM/yyyy HH:mm:ss  
✅ **Transacciones atómicas** garantizando consistencia de datos  
✅ **Sin backend** necesario

#### Flujo de persistencia:

1. El usuario termina el juego con un nuevo récord
2. Se crea un objeto `Record` con la ronda máxima y timestamp actual
3. Se guarda automáticamente en MongoDB Realm
4. Al reiniciar la app, se carga el récord guardado
5. La interfaz muestra la ronda máxima y la fecha en que se obtuvo

---

## Flujo de estados en el código

1. **IDLE** → solo se permite presionar Start.
2. **GENERAR_SECUENCIA** → se muestran los colores de la secuencia uno a uno.
3. **ELECCION_USUARIO** → el usuario puede presionar los botones para reproducir la secuencia.
4. **FINALIZADO** → se bloquean los botones y se permite reiniciar el juego.

El flujo está controlado por `Datos.estado` y el `ViewModel` (`MyVM`), usando `MutableStateFlow` para que la UI reaccione automáticamente.
