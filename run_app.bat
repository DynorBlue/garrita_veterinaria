@echo off
echo 🚀 Iniciando Veterinaria Garrita...
echo.

cd /d "%~dp0"

echo 📦 Compilando aplicación...
call gradlew.bat assembleDebug --no-daemon

if %ERRORLEVEL% EQU 0 (
    echo ✅ Compilación exitosa
    echo 📱 Instalando APK en el dispositivo...
    adb install -r app\build\outputs\apk\debug\app-debug.apk
    
    if %ERRORLEVEL% EQU 0 (
        echo ✅ Aplicación instalada correctamente
        echo 🎯 Iniciando aplicación...
        adb shell am start -n org.utl.veterinaria_garrita/.MainActivity
    ) else (
        echo ❌ Error al instalar la aplicación
        echo 💡 Asegúrate de tener un dispositivo conectado o un emulador corriendo
    )
) else (
    echo ❌ Error en la compilación
)

echo.
pause