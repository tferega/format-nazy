@echo off
setlocal
pushd
cd "%~dp0"

set JVM_PARAMS=-Xss2m -Xmx712m -XX:MaxPermSize=256m -XX:+CMSClassUnloadingEnabled

set TRY_JREBEL=true
set LOG_LEVEL=
set NO_PAUSE=false
set DO_LOOP=false

:PARSER_LOOP
if "%~1"=="" goto :PARSER_END

if "%~1"=="--debug" (
  set LOG_LEVEL="set logLevel:=Level.Debug"
  goto :PARSER_CONTINUE
)

if "%~1"=="--no-jrebel" (
  set TRY_JREBEL=false
  goto :PARSER_CONTINUE
)

if "%~1"=="--loop" (
  set DO_LOOP=true
  goto :PARSER_CONTINUE
)

if "%~1"=="--no-pause" (
  set NO_PAUSE=true
  goto :PARSER_CONTINUE
)

set SBT_PARAMS=%SBT_PARAMS% %1

:PARSER_CONTINUE
shift
goto :PARSER_LOOP
:PARSER_END

set JVM_PARAMS=%JVM_PARAMS%%
if %TRY_JREBEL%.==true. (
  if exist "%JREBEL_HOME%\jrebel.jar" set JVM_PARAMS=%JVM_PARAMS% -noverify -javaagent:"%JREBEL_HOME%\jrebel.jar" %JREBEL_PLUGINS%
)

set STRAP=project\strap
set SBT_PATH=%STRAP%\sbt-launch-0.11.0.jar
set SBT_PARAMS=%LOG_LEVEL%%SBT_PARAMS%

set SBT_URL=http://typesafe.artifactoryonline.com/typesafe/ivy-releases/org.scala-tools.sbt/sbt-launch/0.11.0/sbt-launch.jar
set GRUJ_PATH=%STRAP%\gruj.jar
set GRUJ_PARAMS=%GRUJ_PATH% -c5B15BA0FC63E355D47293DEF4BC2E58DA6F03787 -d %SBT_URL% %SBT_PATH% %SBT_PARAMS%

set RUN_CMD=java %JVM_PARAMS% -jar %GRUJ_PARAMS%

:RUN_LOOP
%RUN_CMD%

if %DO_LOOP%.==true. (
  if %NO_PAUSE%.==false. (
    echo Press Enter to continue or Press CTRL+C to exit!
    pause
  )
  goto :RUN_LOOP
)

popd
endlocal
