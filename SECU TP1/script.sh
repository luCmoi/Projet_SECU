#! /usr/bin/bash

javac securiteL3/*.java

#PERMUTATION
java securiteL3/Chiffre p "azertyuiopqsdfghjklmwxcvbn"  $1 > $1.chiffreP
java securiteL3/Decrypt  p $1.chiffreP 26 > $1.decryptP
if diff $1 $1.decryptP
    then echo -e "chiffre securiteL3.Permutation réussi"
else
    echo -e "chiffre securiteL3.Permutation rate"
fi


#CESAR
#java securiteL3/Chiffre c 15  $1 > $1.chiffreC
#java securiteL3/Dechiffre  c 15  $1.chiffreC > $1.clairC
#if diff $1 $1.clairC
#    then echo -e "chiffre securiteL3.Cesar réussi"
#else
#    echo -e "chiffre securiteL3.Cesar rate"
#fi




echo -e "\e[0;34m###########\e[0m"
echo -e "\e[0;34m## CESAR ##\e[0m"
echo -e "\e[0;34m###########\e[0m"
java securiteL3/Chiffre c 15  $1 > $1.chiffreC

echo -e "\e[0;34mDechiffre \e[0m"
#java securiteL3/Chiffre c 15  $1 > $1.chiffreC
java securiteL3/Dechiffre  c 15  $1.chiffreC > $1.clairC
if diff $1 $1.clairC
    then echo -e "\e[0;32mchiffre securiteL3.Cesar réussi \e[0m"
else
    echo -e "\e[0;31mchiffre securiteL3.Cesar rate \e[0m"
fi

echo -e ""
echo -e "\e[0;34mDecrypt mot connu\e[0m"
#java securiteL3/Chiffre c 22  $1 > $1.chiffreC
java securiteL3.Decrypt c $1.chiffreC 1 modifieriez > $1.decryptCc
if diff -q $1 $1.decryptCc
    then echo -e "\e[0;32mDecrypt securiteL3.Cesar réussi\e[0m"
else
    echo -e "\e[0;31mDecrypt securiteL3.Cesar rate \e[0m"
fi

echo -e ""
echo -e "\e[0;34mDecrypt FREQ \e[0m"
#java securiteL3/Chiffre c 22  $1 > $1.chiffreC
java securiteL3.Decrypt c $1.chiffreC 2 > $1.decryptC
if diff -q $1 $1.decryptC
    then echo -e "\e[0;32mDecrypt securiteL3.Cesar réussi \e[0m"
else
    echo -e "\e[0;31mDecrypt securiteL3.Cesar rate \e[0m"
fi

echo -e ""
echo -e "\e[0;34mDecrypt FORCE BRUT\e[0m"
#java securiteL3/Chiffre c 22  $1 > $1.chiffreC
java securiteL3.Decrypt c $1.chiffreC 3 > $1.decryptC
if diff -q $1 $1.decryptC
    then echo -e "\e[0;32mDecrypt securiteL3.Cesar réussi\e[0m"
else
    echo -e "\e[0;31mDecrypt securiteL3.Cesar rate\e[0m"
fi


echo -e ""
echo -e "\e[0;34m##############\e[0m"
echo -e "\e[0;34m## VIGENERE ##\e[0m"
echo -e "\e[0;34m##############\e[0m"
echo -e ""

java securiteL3/Chiffre v "azertyuiopqsdfghjklmwxcvbn"  $1 > $1.chiffreV

echo -e "\e[0;34mDechiffre\e[0m"
#java securiteL3/Chiffre v "azertyuiopqsdfghjklmwxcvbn"  $1 > $1.chiffreV
java securiteL3/Dechiffre  v "azertyuiopqsdfghjklmwxcvbn"  $1.chiffreV > $1.clairV
if diff -q $1 $1.clairV
    then echo -e "\e[0;32mDechiffre securiteL3.Vigenere réussi\e[0m"
else
    echo -e "\e[0;31mDechiffre securiteL3.Vigenere rate\e[0m"
fi

echo -e ""

echo -e "\e[0;34mDecrypt avec taille\e[0m"
#java securiteL3/Chiffre v "azertyuiopqsdfghjklmwxcvbn"  $1 > $1.chiffreV
java securiteL3.Decrypt  v $1.chiffreV  26  >$1.decryptV
if diff -q $1 $1.decryptV
    then
    echo -e "\e[0;32mDecrypt securiteL3.Vigenere réussi \e[0m"
else
    echo -e "\e[0;31mDecrypt securiteL3.Vigenere rate \e[0m"
fi



echo -e ""
echo -e "\e[0;34mDecrypt sans taille \e[0m"
#java securiteL3/Chiffre v "azertyuiopqsdfghjklmwxcvbn"  $1 > $1.chiffreV
java securiteL3.Decrypt  v $1.chiffreV >$1.decryptV
if diff -q $1 $1.decryptV
    then
    echo -e "\e[0;32mDecrypt securiteL3.Vigenere réussi \e[0m"
else
    echo -e "\e[0;31mDecrypt securiteL3.Vigenere rate \e[0m"
fi
