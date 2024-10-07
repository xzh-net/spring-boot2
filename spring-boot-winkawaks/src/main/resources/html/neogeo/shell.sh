!/bin/bash
 
filename="neogeo-285.txt"  


while read line
  do
    wget -c -b -P roms $line
  done < $filename
