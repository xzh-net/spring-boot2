#!/bin/bash
 
filename="cps2-252.txt"  


while read line
  do
    wget -c -b -P roms $line
  done < $filename
