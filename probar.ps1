
$ErrorActionPreference = 'Stop'

Write-Host '=== CITYLINK | PRUEBAS DEL PROYECTO ===' -ForegroundColor Cyan

if (Test-Path .\out) {
    Remove-Item .\out -Recurse -Force
}
New-Item -ItemType Directory -Path .\out | Out-Null

$fuentes = @(
    (Get-ChildItem .\src -Recurse -Filter *.java).FullName
    (Get-ChildItem .\test -Recurse -Filter *.java).FullName
)

Write-Host "`n[1/3] Compilando fuentes con -Xlint:all -Werror..." -ForegroundColor Yellow
& javac -encoding UTF-8 -Xlint:all -Werror -d .\out $fuentes

if ($LASTEXITCODE -ne 0) {
    throw 'La compilación falló.'
}

Write-Host 'Compilación correcta, sin advertencias.' -ForegroundColor Green

$pruebas = @(
    'PruebaPedidos',
    'PruebaRepartidor',
    'PruebaCitylink',
    'PruebaIntegridadAsignaciones',
    'PruebaProteccionDatosPedido',
    'PruebaRegistroCitylink',
    'PruebaEliminacionesCitylink',
    'PruebaConsultasCitylink',
    'PruebaCorreccionesModelo',
    'PruebasEntregable1',
    'PruebaDeshacer',
    'PruebaAlgoritmos',
    'PruebaAtenderSiguiente',
    'PruebaDeshacerAtenderSiguiente'
)

Write-Host "`n[2/3] Ejecutando $($pruebas.Count) clases de prueba..." -ForegroundColor Yellow

foreach ($prueba in $pruebas) {
    Write-Host "`n========== $prueba ==========" -ForegroundColor Cyan
    & java '-Dfile.encoding=UTF-8' -cp .\out "app.$prueba"

    if ($LASTEXITCODE -ne 0) {
        throw "Falló la prueba $prueba."
    }
}

Write-Host "`n[3/3] Verificación final..." -ForegroundColor Yellow
Write-Host 'Todas las pruebas finalizaron correctamente.' -ForegroundColor Green
Write-Host 'Para iniciar la aplicación, ejecutá: .\ejecutar-citylink.ps1' -ForegroundColor Gray
