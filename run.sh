#!/bin/bash
timestamp="$(date +"%s")"
echo "starting run script, with timestamp:"
echo $timestamp
mkdir out/$timestamp
java runner 800 600 $timestamp $1 $2 $3 $4 $5 $6 $7 $8 $9 $10 $11 $12 $13 $14 $15 $16 $17 $19
echo "Completed run"
cd out/$timestamp

/Applications/Lilypond.app/Contents/Resources/bin/lilypond -fpng melody.ly
convert melody.png -trim melody.png 1> _Image.log

open melody.png
open melody.midi