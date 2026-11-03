# CityLink — Entregable 1

Sistema de gestión de reparto urbano desarrollado en Java para Programación I (2026).

## Integrantes
- [Tomas Quiroga]
- [Renzo Aimetta]
- [Mateo Gonzalez]

## Alcance del Entregable 1
- Modelo orientado a objetos con las jerarquías `Pedido` y `Vehiculo`.
- Interfaces `Rastreable` y `Facturable`.
- Gestión mediante arreglos estáticos.
- Registro y búsqueda de clientes, repartidores y pedidos.
- Asignación con validación de capacidad y restricciones de transporte.
- Control de estados, costos, tiempos estimados y comisiones.
- Excepciones propias y validación de entradas.
- Datos iniciales de demostración: 5 clientes, 3 repartidores y 8 pedidos.

## Estructura del Proyecto
Citylink/
├── src/
│   ├── app/
│   ├── excepciones/
│   └── modelo/
├── test/
│   └── app/
├── docs-api/
├── diagrama-clases.png
├── probar.ps1
└── README.md

## Requisitos
- JDK 17 o superior (el proyecto fue compilado y probado con JDK 21).
- PowerShell en Windows o una terminal compatible.

## Compilar y ejecutar
Desde la carpeta raíz del proyecto:

```powershell
New-Item -ItemType Directory -Force .\out | Out-Null
javac -encoding UTF-8 -Xlint:all -Werror -d .\out (Get-ChildItem .\src -Recurse -Filter *.java).FullName
java -Dfile.encoding=UTF-8 -cp .\out app.Main
```

En PowerShell, si la consola no muestra correctamente los acentos:

```powershell
chcp 65001
[Console]::InputEncoding = [System.Text.UTF8Encoding]::new()
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new()
```

## Ejecutar pruebas automatizadas
```powershell
powershell -ExecutionPolicy Bypass -File .\probar.ps1
```

El script compila `src` y `test` con `-Xlint:all -Werror` y ejecuta las diez clases de prueba incluidas.

## Reglas de negocio implementadas
- Costo base: $1000 + distancia × costo por kilómetro del vehículo.
- Pedido urgente: recargo del 50% sobre el subtotal.
- Pedido frágil: adicional fijo de $800.
- Tiempo estimado: (distancia / velocidad) × 60 + minutos extra del vehículo.
- Bicicleta: 10 kg, 15 km/h, $50/km; no transporta pedidos frágiles.
- Moto: 30 kg, 40 km/h, $120/km y 5 minutos extra.
- Camioneta: 500 kg, 30 km/h, $300/km y 10 minutos extra.
- Comisión: 10% del importe de los pedidos entregados.

## Diagrama de clases
Ver [`diagrama-clases.png`](diagrama-clases.png).

## Verificación realizada
- Compilación completa de `src` y `test` con `javac -Xlint:all -Werror`.
- Ejecución de las diez clases de pruebas automatizadas.
- Generación de documentación Javadoc con `-Xdoclint:all`.
- Pruebas manuales de menú, búsqueda con tildes, registro, asignación, restricciones de vehículos, estados, costos y comisiones.

## Uso de herramientas de IA

Se utilizó ChatGPT como herramienta de apoyo durante el desarrollo del proyecto. Su utilización incluyó la revisión de requisitos, identificación y corrección de posibles errores, asistencia en la documentación y elaboración de pruebas.

Las sugerencias propuestas fueron verificadas mediante la compilación del proyecto con `javac -Xlint:all -Werror`, la ejecución de las pruebas automatizadas y pruebas manuales del sistema. Los integrantes del grupo son responsables de revisar, comprender y poder explicar el código entregado.

## Trabajo con Git
El repositorio debe contener commits descriptivos realizados por todos los integrantes. Antes de entregar, cada integrante debe subir sus propios cambios y el grupo debe verificar el historial compartido.
