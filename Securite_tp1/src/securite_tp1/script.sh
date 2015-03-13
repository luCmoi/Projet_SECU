#! /usr/bin/bash

javac *.java

##PERMUTATION
#java chiffre p "azertyuiopqsdfghjklmwxcvbn"  $1 > $1.chiffreP
#java dechiffre  p "azertyuiopqsdfghjklmwxcvbn"  $1.chiffreP > $1.clairP
#if diff $1 $1.clairP
#    then echo "chiffre Permutation réussi"
#else
#    echo "chiffre Permutation rate"
#fi
#
#
##CESAR
#java chiffre c 15  $1 > $1.chiffreC
#java dechiffre  c 15  $1.chiffreC > $1.clairC
#if diff $1 $1.clairC
#    then echo "chiffre Cesar réussi"
#else
#    echo "chiffre Cesar rate"
#fi

#Vigenere
java chiffre v "renaud"  $1 > $1.chiffreV
java dechiffre  v "renaud"  $1.chiffreV > $1.clairV
if diff $1 $1.clairV
    then echo "chiffre Vigenere réussi"
else
    echo "chiffre Vigenere rate"
fi



#java chiffre c 4  $1> $1.chiffre
#java decrypt  c $1.chiffre  1 licence  >$1.clair
#if diff $1 $1.clair
#    then
#    echo decrypt réussi
#else
#    echo decrypt rate
#fi
