---
agent: 'agent'
description: 'Create a new implementation plan file for new features, refactoring existing code or upgrading packages, design, architecture or infrastructure.'
tools: ['changes', 'search/codebase', 'edit/editFiles', 'extensions', 'fetch', 'githubRepo', 'openSimpleBrowser', 'problems', 'runTasks', 'search', 'search/searchResults', 'runCommands/terminalLastCommand', 'runCommands/terminalSelection', 'testFailure', 'usages', 'vscodeAPI']
---
# Plan de implementación

## Objetivo principal]:

El objetivo es crear un archivo que explique paso a paso cómo se va a hacer una tarea concreta `${input:PlanPurpose}`
El plan debe ser claro, ordenado y fácil de seguir, tanto para una persona como para una IA y siempre se escribirá en castellano (esto es obligatorio).

## Contexto

Este documento sirve como guía de trabajo.
No es para debatir ni interpretar, sino para seguir instrucciones claras y saber qué hay que hacer en cada momento.
## Reglas Básicas

- El plan debe explicar qué se hace y cómo se hace.
- No debe haber frases ambiguas ni confusas.
- Todo debe entenderse sin preguntar nada más.
- Cada parte del plan debe poder hacerse de forma independiente.

## Cómo se Organiza el Plan

El plan se divide en fases.

- Cada fase tiene:
- Un objetivo claro.
- Varias tareas concretas.
- Un criterio para saber cuándo está terminada.

## Normas Importantes

- Cada tarea debe explicar exactamente qué archivo tocar y qué hacer.
- No se deja nada “a elección”.
- Todo debe poder comprobarse (hecho / no hecho).
- Se usan códigos para identificar requisitos y tareas (REQ-, TASK-, etc.).

## Archivo del Plan

- El archivo se guarda en la carpeta `/plan/`
- nombre del archivo: `[purpose]-[component]-[version].md`


## Output File Specifications

- Save implementation plan files in `/plan/` directory
- Use naming convention: `[purpose]-[component]-[version].md`
- Prefijos: `upgrade|refactor|feature|data|infrastructure|process|architecture|design`
- Ejemplo: `upgrade-system-command-4.md`, `feature-auth-module-1.md`
- El archivo debe ser un archivo Markdown válido con una estructura adecuada.

## Estructura obligatoria de la plantilla

Todos los planes de implementación deben cumplir estrictamente con la siguiente plantilla. Cada sección es obligatoria y debe contener contenido específico y práctico. Los agentes de IA deben validar el cumplimiento de la plantilla antes de su ejecución.

## Reglas de validación de plantillas

- Todos los campos preliminares deben estar presentes y correctamente formateados.
- Todos los encabezados de sección deben coincidir exactamente (distingue entre mayúsculas y minúsculas).
- Todos los prefijos de identificador deben seguir el formato especificado.
- Las tablas deben incluir todas las columnas obligatorias.
- No debe quedar texto de marcador de posición en el resultado final.

## Estado

El estado del plan de implementación debe estar claramente definido en la introducción y reflejar su estado actual. El estado puede ser uno de los siguientes (color de estado entre paréntesis): «Completado» (insignia verde brillante), «En progreso» (insignia morado), «Planificado» (insignia azul), «Obsoleto» (insignia roja) o «En espera» (insignia rosado). También debe mostrarse como una insignia en la sección de introducción.
```md
---
goal: [Qué se quiere conseguir con este plan]
version: [Opcional]
date_created: [AAAA-MM-DD]
last_updated: [Opcional]
owner: [Opcional]
status: 'Completed' | 'In progress' | 'Planned' | 'Deprecated' | 'On Hold'
tags: [Opcional]
---

# Introducción

![Estado](https://img.shields.io/badge/status-<status>-blue)

Explicación corta de qué es este plan y para qué sirve.

## 1. Requisitos y Límites

Cosas que **hay que cumplir sí o sí**.

- **REQ-001**: Requisito principal
- **SEC-001**: Tema de seguridad (si aplica)
- **CON-001**: Limitación importante

## 2. Pasos a Seguir

### Fase 1

- **GOAL-001**: Objetivo de esta fase

| Tarea | Qué hay que hacer | Hecho | Fecha |
|------|------------------|-------|-------|
| TASK-001 | Explicación clara de la tarea | | |
| TASK-002 | Otra tarea | | |

### Fase 2

- **GOAL-002**: Objetivo de esta fase

| Tarea | Qué hay que hacer | Hecho | Fecha |
|------|------------------|-------|-------|
| TASK-003 | Tarea siguiente | | |

## 3. Otras Opciones

Otras formas de hacerlo y por qué no se eligieron.

- **ALT-001**: Otra opción descartada

## 4. Dependencias

Cosas que hacen falta para que funcione.

- **DEP-001**: Librería, programa o recurso necesario

## 5. Archivos Afectados

Archivos que se van a tocar.

- **FILE-001**: Nombre del archivo y para qué sirve

## 6. Pruebas

Cómo comprobar que todo funciona bien.

- **TEST-001**: Prueba básica
- **TEST-002**: Otra comprobación

## 7. Riesgos y Suposiciones

Problemas que podrían pasar o cosas que se dan por hechas.

- **RISK-001**: Posible problema
- **ASSUMPTION-001**: Suposición importante

## 8. Enlaces Útiles

[Link to related spec 1]
[Link to relevant external documentation]
```