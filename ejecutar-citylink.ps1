# Ejecuta CityLink en PowerShell con fondo negro y texto verde (Windows color 0A).
$ErrorActionPreference = 'Stop'

chcp 65001 | Out-Null
[Console]::InputEncoding = [System.Text.UTF8Encoding]::new()
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new()

try {
    $Host.UI.RawUI.BackgroundColor = 'Black'
    $Host.UI.RawUI.ForegroundColor = 'Green'
    Clear-Host
} catch {
    Write-Host 'No se pudo cambiar el color de la consola; se continuara igualmente.'
}

if (Test-Path '.\out') {
    Remove-Item '.\out' -Recurse -Force
}
New-Item -ItemType Directory -Path '.\out' | Out-Null

$fuentes = (Get-ChildItem '.\src' -Recurse -Filter '*.java').FullName

if (-not $fuentes) {
    throw 'No se encontraron archivos Java en src.'
}

Write-Host 'Compilando CityLink...'
& javac -encoding UTF-8 -Xlint:all -Werror -d '.\out' $fuentes

if ($LASTEXITCODE -ne 0) {
    throw 'La compilacion fallo. CityLink no se inicio.'
}

Clear-Host
& java '-Dfile.encoding=UTF-8' -cp '.\out' app.Main

if ($LASTEXITCODE -ne 0) {
    throw 'CityLink finalizo con un error.'
}
