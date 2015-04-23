#! /usr/bin/bash

echo "Lancement du Gestionnaire\n"
cd ./Gestionnaire_C
gnome-terminal -e "bash -c \"make cleanall; make; ./gestionnaireC 7895 10; exec bash\""



echo "Lancement du Diffuseur\n"

cd ../Diffuseur_Java/src
gnome-terminal -e "bash -c \"javac *.java; java Main\""

cd ../../

echo "Lancement du Client\n"
cd ./Client_Java/src
gnome-terminal -e "bash -c \"javac *.java; java Client\""

cd ../..
