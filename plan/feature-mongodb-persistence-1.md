---
goal: Integrar MongoDB Realm en la aplicación SimonMeDijo para persistencia local de récords sin backend intermedio
version: 1.0
date_created: 2026-01-19
last_updated: 2026-01-19
owner: Development Team
status: Planned
tags: [database, persistence, mongodb, realm, local-storage]
---

# Introducción

![Estado](https://img.shields.io/badge/status-Planned-blue)

Este plan describe la implementación de **MongoDB Realm** como capa de persistencia local en la aplicación **SimonMeDijo**. El objetivo es reemplazar/complementar la actual implementación de Room con MongoDB Realm, permitiendo guardar automáticamente los récords con timestamp sin necesidad de un backend intermedio.

La integración será completamente local, utilizando Realm como base de datos embebida en el dispositivo Android. Se guardarán los registros de récord con la fecha exacta de obtención.

---

## 1. Requisitos y Límites

Cosas que **hay que cumplir sí o sí**.

- **REQ-001**: La persistencia debe funcionar 100% localmente en el dispositivo, sin conexión a servidor externo
- **REQ-002**: Cada récord debe incluir: puntuación máxima (ronda), fecha/hora y timestamp en ms
- **REQ-003**: Mantener compatibilidad con la arquitectura actual (ViewModel, Compose, Datos singleton)
- **REQ-004**: El registro de récords debe ser automático al finalizar el juego con puntuación superior
- **REQ-005**: Se debe mantener la interfaz HistorialRecord existente para permitir cambios futuros
- **SEC-001**: Los datos deben estar encriptados automáticamente por Realm
- **SEC-002**: No debe haber credenciales o claves de API en el código fuente
- **CON-001**: Mínimo SDK 24 (ya soportado por el proyecto)
- **CON-002**: No agregar dependencias que causen conflictos con las librerías existentes

---

## 2. Pasos a Seguir

### Fase 1: Preparación y Configuración de Dependencias

- **GOAL-001**: Agregar MongoDB Realm al proyecto y configurarlo correctamente

| Tarea | Qué hay que hacer | Hecho | Fecha |
|------|------------------|-------|-------|
| TASK-101 | Crear rama `feature/mongodb-persistence` desde `develop` | | |
| TASK-102 | Agregar dependencia de MongoDB Realm al `build.gradle.kts` (versión 12.0+) | | |
| TASK-103 | Agregar plugin de Realm al `build.gradle.kts` | | |
| TASK-104 | Sincronizar Gradle y verificar que no hay conflictos con Room | | |
| TASK-105 | Crear issue descriptivo en GitHub: "Setup MongoDB Realm para persistencia local" | | |

### Fase 2: Crear Modelos de Datos para MongoDB Realm

- **GOAL-002**: Definir la estructura de datos que Realm manejará

| Tarea | Qué hay que hacer | Hecho | Fecha |
|------|------------------|-------|-------|
| TASK-201 | Crear archivo `RealmRecordModel.kt` en `data/` con modelo Realm para récords | | |
| TASK-202 | El modelo debe incluir: id (PrimaryKey), maxRonda, fechaTexto, tiempoMS | | |
| TASK-203 | Agregar anotaciones de Realm (@RealmObject, @PrimaryKey) correctamente | | |
| TASK-204 | Hacer commit: "feat(data): Add Realm model for record persistence" | | |

### Fase 3: Implementar la Capa de Acceso a Datos

- **GOAL-003**: Crear la clase que gestiona todas las operaciones con MongoDB Realm

| Tarea | Qué hay que hacer | Hecho | Fecha |
|------|------------------|-------|-------|
| TASK-301 | Crear archivo `RecordRealmRepository.kt` en `data/` | | |
| TASK-302 | Implementar método `guardarRecord(record: Record): Boolean` con manejo de transacciones | | |
| TASK-303 | Implementar método `obtenerRecordActual(): Record?` | | |
| TASK-304 | Implementar método `obtenerRondaMaxima(): Int` | | |
| TASK-305 | Implementar método `obtenerHistorialCompleto(): List<Record>` para auditoría futura | | |
| TASK-306 | Agregar manejo robusto de errores (try-catch) en todas las operaciones | | |
| TASK-307 | Asegurar que Realm se abre/cierra correctamente en cada operación | | |
| TASK-308 | Hacer commit: "feat(data): Implement RecordRealmRepository with CRUD operations" | | |

### Fase 4: Adaptar HistorialRecord para Realm

- **GOAL-004**: Crear implementación de HistorialRecord usando MongoDB Realm

| Tarea | Qué hay que hacer | Hecho | Fecha |
|------|------------------|-------|-------|
| TASK-401 | Crear archivo `RecordRealmImpl.kt` que implemente HistorialRecord | | |
| TASK-402 | Implementar los 3 métodos: guardarRecord, cargarRecord, obtenerRondaRecord | | |
| TASK-403 | Delegar la lógica a RecordRealmRepository | | |
| TASK-404 | Asegurar que usa suspend/corrutinas correctamente | | |
| TASK-405 | Hacer commit: "feat(data): Implement HistorialRecord with Realm backend" | | |

### Fase 5: Integración con ViewModel y Lógica del Juego

- **GOAL-005**: Conectar MongoDB Realm con la lógica existente del juego

| Tarea | Qué hay que hacer | Hecho | Fecha |
|------|------------------|-------|-------|
| TASK-501 | Modificar `MyVM.kt` para usar RecordRealmImpl en lugar de RecordRoom | | |
| TASK-502 | Actualizar método `inicializarHistorial(context: Context)` | | |
| TASK-503 | Crear método `guardarRecordAlFinalizarJuego()` en MyVM | | |
| TASK-504 | Integrar guardado automático al estado FINALIZADO del juego | | |
| TASK-505 | Asegurar que solo se guarda si es nuevo récord (ronda > recordActual) | | |
| TASK-506 | Hacer commit: "feat(vm): Integrate MongoDB Realm persistence in MyVM" | | |

### Fase 6: Actualizar UI para mostrar información de Realm

- **GOAL-006**: Mostrar en la interfaz los datos persistidos correctamente

| Tarea | Qué hay que hacer | Hecho | Fecha |
|------|------------------|-------|-------|
| TASK-601 | Actualizar `UI.kt` para mostrar la fecha del récord desde Realm | | |
| TASK-602 | Mostrar el timestamp de cuándo se obtuvo el récord en pantalla | | |
| TASK-603 | Crear composable informativo del historial de récords (opcional para MVP) | | |
| TASK-604 | Hacer commit: "feat(ui): Display MongoDB Realm record metadata in UI" | | |

### Fase 7: Testing y Validación

- **GOAL-007**: Verificar que todo funciona correctamente

| Tarea | Qué hay que hacer | Hecho | Fecha |
|------|------------------|-------|-------|
| TASK-701 | Crear tests unitarios en `RecordRealmRepositoryTest.kt` | | |
| TASK-702 | Verificar que guardarRecord persiste correctamente | | |
| TASK-703 | Verificar que cargarRecord recupera la información guardada | | |
| TASK-704 | Verificar que el timestamp se guarda en milisegundos correctamente | | |
| TASK-705 | Probar flujo completo: jugar, terminar, guardar récord en Realm | | |
| TASK-706 | Hacer commit: "test(data): Add unit tests for Realm persistence" | | |

### Fase 8: Limpieza y Documentación

- **GOAL-008**: Finalizar la implementación con documentación y cleanup

| Tarea | Qué hay que hacer | Hecho | Fecha |
|------|------------------|-------|-------|
| TASK-801 | Remover o deprecar RecordRoom si es completamente reemplazado | | |
| TASK-802 | Actualizar Readme.md con información sobre MongoDB Realm | | |
| TASK-803 | Agregar comentarios de documentación en clases principales | | |
| TASK-804 | Hacer merge de rama `feature/mongodb-persistence` a `develop` | | |
| TASK-805 | Crear tag con versión `v1.1` en `develop` | | |
| TASK-806 | Hacer commit final: "docs(readme): Update documentation for MongoDB Realm integration" | | |

---

## 3. Otras Opciones

Alternativas consideradas y por qué se descartaron.

- **ALT-001**: Usar Firebase Firestore → Requiere backend y credenciales. No cumple con REQ-001
- **ALT-002**: Mantener solo Room → Funciona, pero Realm es más moderno y eficiente para este caso
- **ALT-003**: Usar SharedPreferences → Muy limitado para datos complejos y sin encriptación automática
- **ALT-004**: Usar SQLite directamente → Sin encriptación automática ni sincronización

---

## 4. Dependencias

Cosas que hacen falta para que funcione.

- **DEP-001**: MongoDB Realm SDK para Android (versión 12.0+)
- **DEP-002**: Kotlin Coroutines (ya incluido en el proyecto)
- **DEP-003**: Contexto de la aplicación Android (ya disponible)
- **DEP-004**: Java 11+ (ya configurado en el proyecto)

---

## 5. Archivos Afectados

Archivos que se van a crear o modificar.

### Nuevos archivos:
- **FILE-001**: `app/src/main/java/com/dam/simonmedijo/data/RealmRecordModel.kt` - Modelo de datos para Realm
- **FILE-002**: `app/src/main/java/com/dam/simonmedijo/data/RecordRealmRepository.kt` - Repository con operaciones CRUD
- **FILE-003**: `app/src/main/java/com/dam/simonmedijo/data/RecordRealmImpl.kt` - Implementación de HistorialRecord con Realm
- **FILE-004**: `app/src/test/java/com/dam/simonmedijo/data/RecordRealmRepositoryTest.kt` - Tests unitarios

### Archivos modificados:
- **FILE-005**: `app/build.gradle.kts` - Agregar dependencia de Realm y plugin
- **FILE-006**: `app/src/main/java/com/dam/simonmedijo/MyVM.kt` - Integrar Realm repository
- **FILE-007**: `app/src/main/java/com/dam/simonmedijo/UI.kt` - Mostrar fecha del récord
- **FILE-008**: `Readme.md` - Documentar la integración

### Archivos potencialmente deprecados:
- **FILE-009**: `app/src/main/java/com/dam/simonmedijo/data/RecordRoom.kt` - (considerar mantener para compatibilidad hacia atrás)

---

## 6. Pruebas

Cómo comprobar que todo funciona bien.

- **TEST-001**: Lanzar la app en emulador/dispositivo y jugar hasta obtener un récord
- **TEST-002**: Cerrar la app y verificar en Realm Browser que el récord se persistió
- **TEST-003**: Reiniciar la app y verificar que carga el récord guardado con fecha correcta
- **TEST-004**: Obtener un nuevo récord y verificar que se actualiza la fecha
- **TEST-005**: Ejecutar tests unitarios: `./gradlew test`
- **TEST-006**: Verificar que la fecha se muestra correctamente en UI con formato dd/MM/yyyy HH:mm:ss
- **TEST-007**: Verificar en logcat que no hay excepciones de Realm
- **TEST-008**: Desinstalar la app completamente y reinstalar - el Realm debe estar vacío

---

## 7. Riesgos y Suposiciones

Problemas que podrían pasar o cosas que se dan por hechas.

- **RISK-001**: Conflictos de versión entre Realm y Room - Mitiga: Probar localmente primero
- **RISK-002**: Pérdida de datos si la app falla durante transacción - Mitiga: Usar transacciones atómicas
- **RISK-003**: Performance si hay muchos registros - Mitiga: Índices en Realm, solo guardar el mejor récord
- **ASSUMPTION-001**: Se asume que el proyecto ya está funcional con Room
- **ASSUMPTION-002**: Se asume que hay acceso a escribir en el almacenamiento del dispositivo
- **ASSUMPTION-003**: Los permisos de almacenamiento ya están configurados en AndroidManifest.xml

---

## 8. Comandos de Git y Issues

Comandos y referencias para ejecutar este plan.

### Ramas:
```bash
# Crear rama de feature
git checkout -b feature/mongodb-persistence develop

# Al terminar, hacer merge
git checkout develop
git merge --no-ff feature/mongodb-persistence
git tag v1.1
```

### Issues a crear en GitHub:
1. **[FEATURE] Setup MongoDB Realm para persistencia local** - TASK-101-105
2. **[FEATURE] Crear modelos de datos para Realm** - TASK-201-204
3. **[FEATURE] Implementar RecordRealmRepository** - TASK-301-308
4. **[FEATURE] Adaptar HistorialRecord para Realm** - TASK-401-405
5. **[FEATURE] Integrar Realm en ViewModel y lógica del juego** - TASK-501-506
6. **[FEATURE] Actualizar UI para mostrar datos de Realm** - TASK-601-604
7. **[TESTING] Crear tests unitarios para Realm** - TASK-701-706
8. **[DOCS] Documentación y finalización** - TASK-801-806

### Commits esperados:
```
feat(gradle): Add MongoDB Realm dependency and plugin
feat(data): Add Realm model for record persistence
feat(data): Implement RecordRealmRepository with CRUD operations
feat(data): Implement HistorialRecord with Realm backend
feat(vm): Integrate MongoDB Realm persistence in MyVM
feat(ui): Display MongoDB Realm record metadata in UI
test(data): Add unit tests for Realm persistence
docs(readme): Update documentation for MongoDB Realm integration
```

---

## 9. Enlaces Útiles

- [MongoDB Realm Android Documentation](https://www.mongodb.com/docs/realm/sdk/kotlin/)
- [Realm Kotlin SDK GitHub](https://github.com/realm/realm-kotlin)
- [SemVer Specification](https://semver.org/)
- [Gitflow Workflow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow)
