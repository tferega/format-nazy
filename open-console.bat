@echo off
setlocal

echo Opening up the Scala console...
call "%~dp0sbt.bat" %* "project Core" console
