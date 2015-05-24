#! /usr/bin/bash
port=$1

echo -e "Lancement du Gestionnaire\n"
cd ./Gestionnaire_C
gnome-terminal -e "bash -c \"make cleanall; make; ./gestionnaireC $1 10; exec bash\"" --title="Gestionnaire" 2> /dev/null



echo -e "Lancement du Diffuseur\n"

cd ../Diffuseur_Java/src
gnome-terminal --tab -e "bash -c \"javac *.java; java Main $1\"" --title="Diffuseur" 2> /dev/null

cd ../../

echo -e "Lancement du Client\n"
cd ./Client_Java/src
gnome-terminal -e "bash -c \"javac *.java; java Client\"" --title="Client" 2> /dev/null

cd ..
