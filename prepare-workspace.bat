@echo off
setlocal

echo Disabled JRebel for faster compilation!
set JREBEL_HOME=[off]

echo Performing dependancy update for all projects...
echo Will also create Eclipse .project and .classpath files...
call "%~dp0sbt.bat" %* update eclipse

