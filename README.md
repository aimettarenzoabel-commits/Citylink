# CityLink — Proyecto Integrador de Programación I

CityLink es un sistema de gestión de reparto urbano desarrollado en Java para la materia Programación I (2026). El objetivo del proyecto es administrar clientes, repartidores y pedidos, permitiendo organizar las asignaciones y controlar el proceso de entrega.

## Integrantes

- Tomas Quiroga
- Renzo Aimetta
- Mateo Gonzalez

## Alcance del proyecto

Durante el desarrollo del Entregable 1 se implementó el modelo principal del sistema, utilizando programación orientada a objetos para representar los distintos elementos que intervienen en el servicio de reparto.

Entre las funcionalidades desarrolladas se encuentran:

- Registro y búsqueda de clientes, repartidores y pedidos.
- Gestión de pedidos normales, urgentes y frágiles.
- Administración de vehículos mediante bicicletas, motos y camionetas.
- Asignación de pedidos a repartidores, teniendo en cuenta las características y capacidades de cada vehículo.
- Control de los estados de los pedidos y sus transiciones.
- Cálculo de costos, tiempos estimados de entrega e importes.
- Cálculo de comisiones de los repartidores.
- Eliminación de registros, respetando las relaciones y restricciones del sistema.
- Validación de datos y manejo de excepciones propias.

El sistema utiliza arreglos estáticos para almacenar la información y cuenta con datos iniciales de demostración: 5 clientes, 3 repartidores y 8 pedidos.

## Estructura del proyecto

El proyecto se encuentra organizado de la siguiente manera:

```text
Citylink/
├── src/
│   ├── app/
│   ├── excepciones/
│   └── modelo/
├── test/
│   └── app/
├── docs-api/
├── out/
├── diagrama-clases.dot
├── diagrama-clases.png
├── ejecutar-citylink.ps1
├── probar.ps1
└── README.md
```

- `src/`: contiene el código fuente de la aplicación.
- `app/`: contiene la clase principal y la interfaz de consola.
- `excepciones/`: contiene las excepciones propias del sistema.
- `modelo/`: contiene las clases que representan clientes, pedidos, repartidores y vehículos.
- `test/`: contiene las clases utilizadas para probar el funcionamiento del sistema.
- `docs-api/`: contiene la documentación generada mediante Javadoc.
- `out/`: se utiliza para almacenar los archivos compilados.
- `diagrama-clases.dot` y `diagrama-clases.png`: contienen el diagrama de clases del proyecto.

## Requisitos

Para compilar y ejecutar el proyecto se necesita:

- JDK 17 o superior. El proyecto fue compilado y probado con JDK 21.
- PowerShell en Windows o una terminal compatible.

## Compilación, ejecución y pruebas

Para facilitar el uso del sistema se incorporaron dos scripts de PowerShell: uno para iniciar la aplicación y otro para ejecutar las pruebas automatizadas.

### Ejecutar CityLink

Se desarrolló el script `ejecutar-citylink.ps1`, que automatiza la preparación de la carpeta de compilación, configura la codificación de la consola, compila los archivos Java y ejecuta la aplicación.

Desde la carpeta raíz del proyecto se puede iniciar con:

```powershell
powershell -ExecutionPolicy Bypass -File .\ejecutar-citylink.ps1
```

La compilación se realiza utilizando `-Xlint:all -Werror`, por lo que también se controlan las advertencias del compilador.

### Compilación y ejecución manual

También se puede compilar y ejecutar el sistema directamente desde PowerShell:

```powershell
$fuentes = (Get-ChildItem .\src -Recurse -Filter '*.java').FullName

New-Item -ItemType Directory -Force .\out | Out-Null

javac -encoding UTF-8 -Xlint:all -Werror -d .\out $fuentes

java '-Dfile.encoding=UTF-8' -cp .\out app.Main
```

### Pruebas automatizadas

Para comprobar el funcionamiento del proyecto se desarrolló el script `probar.ps1`, que compila las fuentes y ejecuta las clases de prueba incluidas.

Se puede ejecutar desde la carpeta raíz mediante:

```powershell
powershell -ExecutionPolicy Bypass -File .\probar.ps1
```

Las pruebas verifican, entre otras cosas, el registro y búsqueda de datos, las asignaciones, las restricciones de los vehículos, los estados de los pedidos, los cálculos, las eliminaciones y el manejo de excepciones.

En la última ejecución realizada, la compilación finalizó sin advertencias y las 30 pruebas de aceptación fueron aprobadas. También finalizaron correctamente las pruebas adicionales del modelo.

## Reglas de negocio implementadas

Para realizar los cálculos y controlar las operaciones del sistema se establecieron las siguientes reglas:

- **Costo base:** $1000 + distancia × costo por kilómetro del vehículo.
- **Pedido urgente:** recargo del 50% sobre el subtotal.
- **Pedido frágil:** adicional fijo de $800.
- **Tiempo estimado:** (distancia / velocidad) × 60 + minutos extra del vehículo.
- **Bicicleta:** capacidad de 10 kg, velocidad de 15 km/h y costo de $50/km. No permite transportar pedidos frágiles.
- **Moto:** capacidad de 30 kg, velocidad de 40 km/h, costo de $120/km y 5 minutos extra.
- **Camioneta:** capacidad de 500 kg, velocidad de 30 km/h, costo de $300/km y 10 minutos extra.
- **Comisión:** 10% del importe de los pedidos entregados.

## Diagrama de clases

El diseño del modelo se representa mediante un diagrama de clases que muestra las principales clases del sistema y sus relaciones.

Se puede consultar desde el siguiente archivo: [diagrama-clases.png](diagrama-clases.png).

## Documentación y verificación

Durante el desarrollo se generó la documentación de las clases mediante Javadoc, utilizando la opción `-Xdoclint:all`.

Además, se realizaron pruebas automatizadas y verificaciones del modelo para comprobar el cumplimiento de las reglas de negocio, el comportamiento de las clases y la integridad de las asignaciones.

La última compilación de la aplicación y las pruebas automatizadas finalizaron correctamente. La nueva interfaz de consola compila sin advertencias; sus distintos submenús todavía no cuentan con una verificación manual completa.

## Uso de herramientas de IA

Se utilizó ChatGPT como herramienta de apoyo durante el desarrollo del proyecto. Se recurrió a ella para revisar requisitos, analizar posibles errores, realizar correcciones, trabajar sobre la documentación y preparar pruebas.

Las sugerencias recibidas fueron revisadas y verificadas mediante la compilación y ejecución del proyecto. Como equipo, somos responsables de comprender el funcionamiento del sistema y poder explicar las decisiones tomadas durante su desarrollo.

## Control de versiones y trabajo en equipo

Para organizar el desarrollo se utilizó Git y GitHub, trabajando sobre un repositorio compartido.

Se utilizaron ramas para separar los cambios y commits descriptivos para registrar las modificaciones realizadas. También se incorporaron Pull Requests para revisar e integrar los cambios en el proyecto, manteniendo un historial de las modificaciones realizadas.