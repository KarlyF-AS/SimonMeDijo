---
description: 'Mejores prácticas y convenciones para scripts de shell en bash, sh, zsh y otros shells'
applyTo: '**/*.sh'
---

# Guía de Scripting en Shell

Instrucciones para escribir scripts de shell limpios, seguros y mantenibles para bash, sh, zsh y otros shells.

## Principios Generales

- Genera código limpio, simple y conciso
- Asegura que los scripts sean fácilmente legibles y comprensibles
- Añade comentarios donde sea útil para entender cómo funciona el script
- Genera mensajes `echo` concisos y simples para mostrar el estado de ejecución
- Evita mensajes `echo` innecesarios y registros excesivos
- Usa `shellcheck` para análisis estático cuando esté disponible
- Asume que los scripts son para automatización y pruebas en lugar de sistemas de producción, a menos que se especifique lo contrario
- Prefiere expansiones seguras: comillas dobles en referencias de variables (`"$var"`), usa `${var}` para claridad, y evita `eval`
- Usa características modernas de Bash (`[[ ]]`, `local`, arrays) cuando los requisitos de portabilidad lo permitan; recurre a construcciones POSIX solo cuando sea necesario
- Elige analizadores confiables para datos estructurados en lugar de procesamiento de texto ad-hoc

## Manejo de Errores y Seguridad

- Siempre habilita `set -euo pipefail` para fallar rápidamente ante errores, capturar variables no definidas y detectar fallos en pipelines
- Valida todos los parámetros requeridos antes de la ejecución
- Proporciona mensajes de error claros con contexto
- Usa `trap` para limpiar recursos temporales o manejar salidas inesperadas cuando el script termina
- Declara valores inmutables con `readonly` (o `declare -r`) para prevenir reasignaciones accidentales
- Usa `mktemp` para crear archivos o directorios temporales de forma segura y asegúrate de eliminarlos en tu manejador de limpieza

## Estructura del Script

- Comienza con un shebang claro: `#!/bin/bash` a menos que se especifique lo contrario
- Incluye un comentario de encabezado explicando el propósito del script
- Define valores predeterminados para todas las variables al principio
- Usa funciones para bloques de código reutilizables
- Crea funciones reutilizables en lugar de repetir bloques de código similares
- Mantén el flujo de ejecución principal limpio y legible

## Trabajando con JSON y YAML

- Prefiere analizadores dedicados (`jq` para JSON, `yq` para YAML—o `jq` en JSON convertido mediante `yq`) en lugar de procesamiento de texto ad-hoc con `grep`, `awk` o división de cadenas en shell
- Cuando `jq`/`yq` no estén disponibles o no sean apropiados, elige el siguiente analizador más confiable disponible en tu entorno, y sé explícito sobre cómo debe usarse de forma segura
- Valida que los campos requeridos existan y maneja rutas de datos faltantes/inválidas explícitamente (ej., verificando el estado de salida de `jq` o usando `// empty`)
- Entrecomilla los filtros de jq/yq para prevenir expansión del shell y prefiere `--raw-output` cuando necesites cadenas simples
- Trata los errores del analizador como fatales: combina con `set -euo pipefail` o prueba el éxito del comando antes de usar los resultados
- Documenta las dependencias del analizador al principio del script y falla rápidamente con un mensaje útil si `jq`/`yq` (o herramientas alternativas) son requeridas pero no están instaladas

```bash
#!/bin/bash

# ============================================================================
# Descripción del Script Aquí
# ============================================================================

set -euo pipefail

cleanup() {
    # Remove temporary resources or perform other teardown steps as needed
    if [[ -n "${TEMP_DIR:-}" && -d "$TEMP_DIR" ]]; then
        rm -rf "$TEMP_DIR"
    fi
}

trap cleanup EXIT

# Default values
RESOURCE_GROUP=""
REQUIRED_PARAM=""
OPTIONAL_PARAM="default-value"
readonly SCRIPT_NAME="$(basename "$0")"

TEMP_DIR=""

# Functions
usage() {
    echo "Usage: $SCRIPT_NAME [OPTIONS]"
    echo "Options:"
    echo "  -g, --resource-group   Resource group (required)"
    echo "  -h, --help            Show this help"
    exit 0
}

validate_requirements() {
    if [[ -z "$RESOURCE_GROUP" ]]; then
        echo "Error: Resource group is required"
        exit 1
    fi
}

main() {
    validate_requirements

    TEMP_DIR="$(mktemp -d)"
    if [[ ! -d "$TEMP_DIR" ]]; then
        echo "Error: failed to create temporary directory" >&2
        exit 1
    fi
    
    echo "============================================================================"
    echo "Script Execution Started"
    echo "============================================================================"
    
    # Main logic here
    
    echo "============================================================================"
    echo "Script Execution Completed"
    echo "============================================================================"
}

# Parse arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -g|--resource-group)
            RESOURCE_GROUP="$2"
            shift 2
            ;;
        -h|--help)
            usage
            ;;
        *)
            echo "Unknown option: $1"
            exit 1
            ;;
    esac
done

# Execute main function
main "$@"