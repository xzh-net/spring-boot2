#!/bin/bash
 
filename="cps1-172.txt"  


while read line
  do
    wget -c -b -P roms $line
  done < $filename
