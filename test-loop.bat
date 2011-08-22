@echo off
setlocal

echo Executing tests...
call "%~dp0sbt.bat" "project Core" test

echo.
echo Testing finished, press any key to re-run...
pause

call "%~f0"
