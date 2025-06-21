@echo off
setlocal enabledelayedexpansion
echo ===============================================
echo  Compilando proyecto TP_Persistencia_Poliglota
echo ===============================================
echo.

REM Crear directorio bin si no existe
if not exist "bin" (
    mkdir bin
    echo [INFO] Se creo la carpeta bin
)

REM Definir classpath (todos los .jar en /lib)
set "CP=lib\*"

echo [INFO] Usando CLASSPATH: %CP%
echo.

REM --- COMPILAR MODELOS ---
echo [INFO] Compilando modelos...
javac -cp "%CP%" -d bin src\tppoliglota\model\*.java
set RET=%ERRORLEVEL%
if not "%RET%"=="0" (
    echo [ERROR] Fallo compilacion de modelos
    pause
    exit /b 1
)

REM --- UTILIDADES ---
echo [INFO] Compilando utilidades...
javac -cp "%CP%;bin" -d bin src\tppoliglota\utils\*.java
set RET=%ERRORLEVEL%
if not "%RET%"=="0" (
    echo [ERROR] Fallo compilacion de utilidades
    pause
    exit /b 1
)

REM --- DAOs ---
echo [INFO] Compilando DAOs...
javac -cp "%CP%;bin" -d bin ^
 src\tppoliglota\dao\mongo\*.java ^
 src\tppoliglota\dao\redis\*.java ^
 src\tppoliglota\dao\neo4j\*.java ^
 src\tppoliglota\dao\cassandra\*.java
set RET=%ERRORLEVEL%
if not "%RET%"=="0" (
    echo [ERROR] Fallo compilacion de DAOs
    pause
    exit /b 1
)

REM --- CONTROLADORES ---
echo [INFO] Compilando controladores...
javac -cp "%CP%;bin" -d bin src\tppoliglota\controller\*.java
set RET=%ERRORLEVEL%
if not "%RET%"=="0" (
    echo [ERROR] Fallo compilacion de controladores
    pause
    exit /b 1
)

REM --- SERVICIOS ---
echo [INFO] Compilando servicios...
javac -cp "%CP%;bin" -d bin src\tppoliglota\service\*.java
set RET=%ERRORLEVEL%
if not "%RET%"=="0" (
    echo [ERROR] Fallo compilacion de servicios
    pause
    exit /b 1
)

REM --- UI ---
echo [INFO] Compilando interfaz de usuario...
javac -cp "%CP%;bin" -d bin src\tppoliglota\ui\*.java
set RET=%ERRORLEVEL%
if not "%RET%"=="0" (
    echo [ERROR] Fallo compilacion de interfaz de usuario
    pause
    exit /b 1
)

echo.
echo ✅ Compilación completada exitosamente.
echo 🔄 Archivos .class generados en: bin\
echo.
pause