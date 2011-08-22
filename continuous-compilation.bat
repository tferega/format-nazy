@echo off
setlocal

echo Disabled JRebel for faster compilation!
set JREBEL_HOME=[off]

echo Entering continuous compilation loop for all projects...
call "%~dp0sbt.bat" %* ~compile
