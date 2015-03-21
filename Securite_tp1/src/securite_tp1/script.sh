#! /usr/bin/bash

javac securiteL3/*.java

#PERMUTATION
#java securiteL3/Chiffre p "azertyuiopqsdfghjklmwxcvbn"  $1 > $1.chiffreP
#java securiteL3/Dechiffre  p "azertyuiopqsdfghjklmwxcvbn"  $1.chiffreP > $1.clairP
#if diff $1 $1.clairP
#    then echo "chiffre securiteL3.Permutation réussi"
#else
#    echo "chiffre securiteL3.Permutation rate"
#fi


#CESAR
#java securiteL3/Chiffre c 15  $1 > $1.chiffreC
#java securiteL3/Dechiffre  c 15  $1.chiffreC > $1.clairC
#if diff $1 $1.clairC
#    then echo "chiffre securiteL3.Cesar réussi"
#else
#    echo "chiffre securiteL3.Cesar rate"
#fi


java securiteL3/Chiffre c 22  $1 > $1.chiffreC
java securiteL3.Decrypt c 3 $1.chiffreC 2 >$1.decryptC
if diff $1 $1.decryptC
    then echo "chiffre securiteL3.Cesar réussi"
else
    echo "chiffre securiteL3.Cesar rate"
fi




##############
## VIGENERE ##
##############
#echo -e "\n\n"
#
#echo -e "#VIGENERE Chiffre/Dechiffre"
#java securiteL3/Chiffre v "renaud"  $1 > $1.chiffreV
#java securiteL3/Dechiffre  v "renaud"  $1.chiffreV > $1.clairV
#if diff $1 $1.clairV
#    then echo "chiffre securiteL3.Vigenere réussi"
#else
#    echo "chiffre securiteL3.Vigenere rate"
#fi
#
#echo -e "\n\n"
#
#echo -e "#VIGENERE Chiffre/Decrypt avec taille"
#java securiteL3/Chiffre v "azertyuiopqsdfghjklmwxcvbn"  $1 > $1.chiffreV
#java securiteL3.Decrypt  v $1.chiffreV  26   >$1.decryptV
#if diff $1 $1.decryptV
#    then
#    echo decrypt securiteL3.Vigenere réussi
#else
#    echo decrypt securiteL3.Vigenere rate
#fi



#echo -e "\n\n"
#echo -e "#VIGENERE Chiffre/Decrypt sans taille"
#java securiteL3/Chiffre v "azertyuiopqsdfghjklmwxcvbn"  $1 > $1.chiffreV
#java securiteL3.Decrypt  v $1.chiffreV >$1.decryptV
#if diff $1 $1.decryptV
#    then
#    echo decrypt securiteL3.Vigenere réussi
#else
#    echo decrypt securiteL3.Vigenere rate
#fi
