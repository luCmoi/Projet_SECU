java securiteL3/chiffre c 15 $1> $1.cesar
java securiteL3/dechiffre c 15 $1.cesar >$1.cesar.clair
if diff $1 $1.cesar.clair >&2
then echo Cesar réussi
else echo Cesar raté
fi




java securiteL3/chiffre p "nbvcxwmlkjhgfdsqpoiuytreza" $1> $1.perm
java securiteL3/dechiffre p "nbvcxwmlkjhgfdsqpoiuytreza" $1.perm > $1.perm.clair
if diff $1 $1.perm.clair >&2
then echo chiffre  permutation  réussi
else echo chiffre permutation  raté
fi

java securiteL3/chiffre v  "programme" $1> $1.vige
java securiteL3/dechiffre v "programme" $1.vige > $1.vige.clair
if diff $1 $1.vige.clair  >&2
then echo chiffre Vigenère  réussi
else echo chiffre Vigenère   raté
fi


echo decrypt cesar
java securiteL3/decrypt c  $1.cesar   1  ponctuation >$1.clair
if diff $1 $1.clair  >&2
then echo decrypt cesar mode 1 réussi
else echo decrypt cesar mode 1 raté
fi



java securiteL3/decrypt c  $1.cesar  2  >$1.clair
if diff $1 $1.clair  >&2
then echo decrypt cesar mode 2 réussi
else echo decrypt cesar mode 2 raté
fi



java securiteL3/decrypt c  $1.cesar  3 >$1.clair
if diff $1 $1.clair   >&2
then echo decrypt cesar mode 3 réussi
else echo decrypt cesar mode 3 raté
fi


echo decrypt Vigenère
java securiteL3/decrypt v $1.vige 9 >$1.clair.taille
if diff $1 $1.clair.taille  >&2
then echo decrypt Vigenère réussi
else echo decrypt Vigenère raté
fi

echo decrypt Vigenère sans taille de clef
java securiteL3/decrypt v $1.vige  >$1.clair
if diff $1 $1.clair  >&2
then echo decrypt Vigenère réussi
else echo decrypt Vigenère raté
fi

echo decrypt permutation
java securiteL3/decrypt p $1.perm  >$1.clair
if diff $1 $1.clair  >&2
then echo decrypt Permutation réussi
else echo decrypt Permutation raté
fi
