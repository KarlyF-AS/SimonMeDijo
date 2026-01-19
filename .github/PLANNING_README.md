Integracion MongoDB en SimonMeDijo - Planificacion para Agentes IA

DESCRIPCION GENERAL
===================

Este repositorio contiene la planificacion completa para integrar MongoDB
en la aplicacion SimonMeDijo, manteniendo compatibilidad con sistemas
de persistencia existentes (SharedPreferences y SQLite).

La planificacion esta estructurada para que agentes IA (como GitHub Copilot,
Claude, etc.) puedan ejecutar cada tarea de forma independiente y secuencial.

ESTRUCTURA DEL PROYECTO
=======================

Archivos de Planificacion:
  MASTER_PLAN.txt
    - Plan maestro de alto nivel
    - Arquitectura general
    - Cronograma estimado
    - Fases y hitos

  .github/issues/ISSUES_MASTER.txt
    - 15 issues detallados
    - Tareas especificas
    - Criterios de aceptacion
    - Detalles tecnicos

Documentacion Actual:
  Readme.md
    - Descripcion de la aplicacion
  app/build.gradle.kts
    - Configuracion Gradle actual

Codigo Existente:
  app/src/main/java/com/dam/simonmedijo/
    - MyVM.kt (ViewModel)
    - UI.kt (Interfaz con Compose)
    - Datos.kt (Singleton global)
    - RecordRoom.kt (Implementacion Room)
    - AppDatabase.kt (Configuracion Room)
    - RecordEntity.kt (Entidad Room)
    - RecordDAO.kt (DAO Room)

ARQUITECTURA PROPUESTA
======================

Vista Actual (v1.0 - v1.1):
  UI ↓ ViewModel ↓ Repository (SharedPreferences / Room)

Vista Propuesta (v1.2):
  UI ↓ ViewModel ↓ RecordsManager ↓ 3 Backends Simultaneos
                                  ├─ SharedPreferences
                                  ├─ SQLite/Room
                                  └─ MongoDB Atlas

Beneficios:
  - Mantener compatibilidad 100% con codigo existente
  - Agregar capacidad de sincronizacion en cloud
  - Permitir jugar offline y sincronizar luego
  - Soporte para multiples usuarios
  - Auditoria y backup centralizado

PROCESO DE EJECUCION PARA AGENTES IA
====================================

1. LECTURA INICIAL:
   a) Leer MASTER_PLAN.txt para contexto general
   b) Leer este README.md para entender estructura
   c) Leer .github/issues/ISSUES_MASTER.txt para detalles

2. SELECCIONAR ISSUE:
   Elegir un issue del 1 al 15 (en orden recomendado)
   Leer completamente la descripcion del issue

3. CREAR RAMA:
   git checkout develop
   git pull
   git checkout -b feature/[nombre-rama]
   
   Nombres de ramas por FASE:
   - feature/records-structure (Issues 1-3)
   - feature/records-local-backend (Issues 4-5)
   - feature/records-remote-backend (Issues 6-8)
   - feature/records-manager (Issues 9-10)
   - feature/records-testing (Issues 11-12)
   - feature/records-ui-migration (Issues 13-14)
   - feature/records-docs (Issue 15)

4. IMPLEMENTACION:
   - Crear archivos/directorios especificados
   - Escribir codigo segun especificaciones
   - Incluir documentacion en codigo (KDoc)
   - No hacer cambios fuera del scope del issue

5. TESTING:
   - Ejecutar: ./gradlew test
   - Ejecutar: ./gradlew build
   - Verificar no hay errores
   - Agregar tests si el issue lo especifica

6. COMMIT:
   git add -A
   git commit -m "feat: [Titulo del issue] (#N)"
   
   Formato de commit:
   - Usar tipo: feat, fix, docs, test, refactor
   - Incluir numero de issue: #N
   - Mensaje descriptivo pero conciso

7. PULL REQUEST:
   git push origin feature/[nombre-rama]
   Crear PR en GitHub con descripcion del issue

DEPENDENCIAS A AGREGAR
======================

En app/build.gradle.kts (segun avance):

MongoDB/Realm:
  // Issue #6 (Remote Backend Setup)
  io.realm:realm-android-sdk:10.15.0

HTTP/Networking:
  // Issue #7 (MongoDB Repository)
  com.squareup.retrofit2:retrofit:2.9.0
  com.squareup.okhttp3:okhttp:4.11.0
  com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0

Serialization:
  // Issue #7 (MongoDB Repository)
  org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1

Background Work:
  // Issue #8 (Sync Worker)
  androidx.work:work-runtime-ktx:2.8.1

GITFLOW WORKFLOW
================

Ramas principales:
  main → produccion (releases stable)
  develop → integracion continua (merge de features)
  feature/* → desarrollo de features (crear desde develop)
  hotfix/* → correcciones urgentes (crear desde main)

Secuencia de merge:
  1. feature/records-structure → develop
  2. feature/records-local-backend → develop
  3. feature/records-remote-backend → develop
  4. feature/records-manager → develop
  5. feature/records-testing → develop
  6. feature/records-ui-migration → develop
  7. feature/records-docs → develop
  8. develop → main (crear tag v1.2)

VERSIONADO SEMANTICO (SemVer)
=============================

Versiones:
  v1.0 → Release inicial
  v1.1 → Realm local (MongoDB Realm embebido)
  v1.2 → Multi-backend (Objetivo actual)
  v2.0 → App completa con todas features

Incrementos:
  MAJOR: Breaking changes en API publica
  MINOR: Nuevas features sin breaking changes
  PATCH: Bug fixes

CONFIGURACION POR AMBIENTE
==========================

Desarrollo (local.properties):
  MONGODB_URI=mongodb+srv://user:pass@cluster-dev.mongodb.net/simonmedijo_dev
  MONGODB_API_KEY=development-key-xxx
  SYNC_INTERVAL=1 (minuto, para testing rapido)
  LOG_LEVEL=DEBUG

Produccion (variables de entorno/build config):
  MONGODB_URI=mongodb+srv://user:pass@cluster-prod.mongodb.net/simonmedijo
  MONGODB_API_KEY=production-key-xxx (inyectar en build time)
  SYNC_INTERVAL=15 (minutos)
  LOG_LEVEL=INFO

SEGURIDAD
=========

No hacer:
  ✗ Guardar credenciales MongoDB en codigo
  ✗ Commitear local.properties o .env
  ✗ Exponer API keys en logs publicos
  ✗ Enviar datos sin SSL/TLS
  ✗ Permitir SQL injection o data injection

Si hacer:
  ✓ Usar variables de entorno
  ✓ Agregar credenciales a gitignore
  ✓ Validar todas las entradas
  ✓ Encriptar datos sensibles
  ✓ Usar AuthInterceptor en Retrofit
  ✓ Rotar API keys regularmente
  ✓ Revisar logs en busca de leaks

TESTING
=======

Ejecutar tests:
  ./gradlew test                 # Todos los tests unitarios
  ./gradlew testDebug            # Tests en build debug
  ./gradlew connectedAndroidTest # Tests en dispositivo/emulador

Metricas:
  Cobertura minima: 80%
  Todos los tests deben pasar
  No hay warnings en build

Tipos de tests:
  Unit tests:   app/src/test/java/...
  Android tests: app/src/androidTest/java/...
  Integration tests: con mocks de backends

DOCUMENTACION
=============

Estructura esperada:
  docs/
    ARCHITECTURE.md      → Patrones y diseño
    MONGODB_SETUP.md     → Como crear cluster
    API.md              → Referencia de APIs
    TESTING.md          → Como escribir tests
    DEPLOYMENT.md       → Como deployar
    MIGRATION.md        → Migracion de datos
    SYNC_STRATEGY.md    → Estrategia de sync

KDoc en codigo:
  - Todas las clases publicas documentadas
  - Todos los metodos publicos con @param y @return
  - Ejemplos de uso donde complejo
  - Notas sobre thread-safety

APOYO PARA AGENTES IA
====================

Este proyecto esta diseñado para que agentes IA como:
  - GitHub Copilot
  - Claude (Anthropic)
  - Codeium
  - Jetbrains AI
  O cualquier otro asistente

puedan ejecutar las tareas sin ambiguedad.

Cada issue tiene:
  ✓ Descripcion clara del objetivo
  ✓ Lista detallada de tareas
  ✓ Detalles tecnicos especificos
  ✓ Criterios de aceptacion definidos
  ✓ Nombres exactos de archivos/clases
  ✓ Interfaces y metodos esperados
  ✓ Tests que deben pasar

El agente IA debe:
  1. Leer completamente el issue
  2. Crear archivos/estructura indicada
  3. Implementar segun especificacion
  4. Escribir tests si se piden
  5. Hacer commit con formato especificado
  6. Reportar cuando termine

MONITOREO DE PROGRESO
====================

Para ver progreso de los 15 issues:
  grep "Aceptacion" .github/issues/ISSUES_MASTER.txt | wc -l  (debe ser 15)
  
Marcar como completo:
  1. Pull request mergeado a develop
  2. Tests pasando
  3. Documentacion actualizada
  4. Tag creado si es hito

PROXIMO PASO DESPUES DE v1.2
=============================

Una vez completada la integracion multi-backend (v1.2):
  - Agregar autenticacion de usuarios
  - Crear dashboard web con estadisticas
  - Implementar leaderboard global
  - Agregar social features (compartir scores)
  - Optimizar rendimiento de sync
  - Internacionalizacion (i18n)

CONTACTO Y PREGUNTAS
====================

Documentacion:
  - MASTER_PLAN.txt para contexto
  - .github/issues/ISSUES_MASTER.txt para detalles
  - docs/ para guias especificas

Cada issue tiene seccion "Consideraciones" con notas importantes.

Para agentes IA: Seguir exactamente el formato de cada issue.
El codigo generado debe ser production-ready.

---

Fecha de Creacion: 2026-01-19
Version del Plan: 1.0
Estado: Listo para Agentes IA
Target Version: v1.2

