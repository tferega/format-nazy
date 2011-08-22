#!/bin/bash
 
echo Disabled JRebel for faster compilation!
JREBEL_HOME="[off]"

echo Entering continuous compilation loop for all projects...
`dirname $0`/sbt.sh "$@" ~compile
