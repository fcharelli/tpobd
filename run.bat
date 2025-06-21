@echo off
setlocal enabledelayedexpansion
echo Ejecutando TP_Persistencia_Poliglota...
echo.

REM Definir el classpath para incluir la carpeta bin y todas las librerías de lib
set "CP=bin;lib*"

echo Usando CLASSPATH: %CP%
echo.

REM Ejecutar la aplicación
java -cp "%CP%" tppoliglota.ui.MainWindow
set RET=%ERRORLEVEL%

if not "%RET%"=="0" (
    echo Error al ejecutar la aplicación!
    echo Verifica que hayas compilado el proyecto primero.
    pause
    exit /b 1
)

echo.
pause