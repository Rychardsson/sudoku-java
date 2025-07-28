@echo off
echo.
echo ========================================
echo    🎮 SUDOKU JAVA - COMPILADOR E EXECUTOR
echo ========================================
echo.

REM Criar diretório bin se não existir
if not exist "bin" mkdir bin

echo 🔨 Compilando o projeto...
javac -d bin -sourcepath src src/br/com/dio/Main.java

if %ERRORLEVEL% EQU 0 (
    echo ✅ Compilação concluída com sucesso!
    echo.
    echo 🚀 Iniciando o jogo...
    echo.
    java -cp bin br.com.dio.Main
) else (
    echo ❌ Erro na compilação!
    echo Verifique os arquivos Java e tente novamente.
    pause
)

echo.
echo 👋 Jogo finalizado. Pressione qualquer tecla para sair.
pause >nul
