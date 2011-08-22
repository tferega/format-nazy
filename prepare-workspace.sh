#!/bin/bash

echo Disabled JRebel for faster compilation!
JREBEL_HOME="[off]"

echo Performing dependancy update for all projects...
echo Will also create Eclipse .project and .classpath files...
`dirname $0`/sbt.sh "$@" update eclipse

