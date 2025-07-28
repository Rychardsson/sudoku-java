@echo off
chcp 65001 > nul
echo.
echo ========================================
echo    SUDOKU JAVA - COMPILADOR E EXECUTOR
echo ========================================
echo.

REM Criar diretorio bin se nao existir
if not exist "bin" mkdir bin

echo Compilando o projeto...
javac -encoding UTF-8 -d bin -sourcepath src src/br/com/dio/Main.java

if %ERRORLEVEL% EQU 0 (
    echo [OK] Compilacao concluida com sucesso!
    echo.
    echo Iniciando o jogo...
    echo.
    java -cp bin br.com.dio.Main
) else (
    echo [X] Erro na compilacao!
    echo Verifique os arquivos Java e tente novamente.
    pause
)

echo.
echo Jogo finalizado. Pressione qualquer tecla para sair.
pause >nul
