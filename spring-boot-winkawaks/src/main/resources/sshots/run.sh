#!/bin/bash
 
filename="img1.txt"  


while read line
  do
    wget -c -b -P cps1 $line
  done < $filename
