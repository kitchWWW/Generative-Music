#!/bin/bash
timestamp="$(date +"%s")"
echo "starting run script, with timestamp:"
echo $timestamp
mkdir out/$timestamp
javac runner.java
echo "Completed compile portion"

java runner 800 600 $timestamp
echo "Completed run"
cd out/$timestamp

/Applications/Lilypond.app/Contents/Resources/bin/lilypond melody.ly
open melody.pdf
open melody.midi
cd ..
cd ..
python plotStats.py $timestamp
open out/$timestamp/stats.png