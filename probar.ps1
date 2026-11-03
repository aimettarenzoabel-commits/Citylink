$ErrorActionPreference = 'Stop'

Write-Host '=== CITYLINK | PRUEBAS DEL ENTREGABLE 1 ===' -ForegroundColor Cyan

if (Test-Path .\out) {
    Remove-Item .\out -Recurse -Force
}
New-Item -ItemType Directory -Path .\out | Out-Null

$fuentes = @(
    (Get-ChildItem .\src -Recurse -Filter *.java).FullName
    (Get-ChildItem .\test -Recurse -Filter *.java).FullName
)

Write-Host "`n[1/2] Compilando fuentes con -Xlint:all -Werror..." -ForegroundColor Yellow
& javac -Xlint:all -Werror -d .\out $fuentes
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
    'PruebasEntregable1'
)

Write-Host "`n[2/2] Ejecutando pruebas..." -ForegroundColor Yellow
foreach ($prueba in $pruebas) {
    Write-Host "`n========== $prueba ==========" -ForegroundColor Cyan
    & java -cp .\out "app.$prueba"
    if ($LASTEXITCODE -ne 0) {
        throw "Falló la prueba $prueba."
    }
}

Write-Host "`nTodas las pruebas finalizaron correctamente." -ForegroundColor Green
Write-Host 'Para iniciar la aplicación, ejecutá: java -cp out app.Main' -ForegroundColor Gray
