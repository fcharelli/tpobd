@echo off
echo Limpiando archivos compilados...
echo.

REM Eliminar directorio bin si existe
if exist "bin" (
    rmdir /s /q bin
    echo Directorio 'bin' eliminado.
) else (
    echo No se encontro el directorio 'bin'.
)

echo.
echo Limpieza completada!
echo Ahora puedes ejecutar 'compile.bat' para recompilar el proyecto.
echo.
pause 