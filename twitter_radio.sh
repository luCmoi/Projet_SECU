#! /usr/bin/bash

#echo -e "Lancement du Gestionnaire\n"
#cd ./Gestionnaire_C
#gnome-terminal -e "bash -c \"make cleanall; make; ./gestionnaireC $1 10; exec bash\"" --title="Gestionnaire" 2> /dev/null
#
#
#
#echo -e "Lancement du Diffuseur\n"
#
#cd ../Diffuseur_Java/src
#gnome-terminal --tab -e "bash -c \"javac *.java; java Main $1\"" --title="Diffuseur" 2> /dev/null
#
#cd ../../
#
#echo -e "Lancement du Client\n"
#cd ./Client_Java/src
#gnome-terminal -e "bash -c \"javac *.java; java Client\"" --title="Client" 2> /dev/null
#gnome-terminal -e "bash -c \"javac *.java; java Client\"" --title="Client" 2> /dev/null
#
#cd ..

#gnome-terminal --tab -t "Gestionnaire" -e "bash -c \" cd ./Gestionnaire_C ;make cleanall; make; ./gestionnaireC $1 10; exec bash\""  --tab --title="Diffuseur" -e "bash -c \"cd ./Diffuseur_Java/src;javac *.java; java Main LINUX 225.77.13.99 9999 1234 $1 /media/data/git/Projet_Tweetoradio/diff_un\"" --tab --title="Client" -e "bash -c \"cd ./Client_Java/src; javac *.java; java Client\""
gnome-terminal --tab -t "Gestionnaire" -e "bash -c \" cd ./Gestionnaire_C ;make cleanall; make; ./gestionnaireC $1 10; exec bash\""  --tab --title="Diffuseur" -e "bash -c \"cd ./Diffuseur_Quizz_Java/src;javac *.java; java Main 225.77.13.99 9999 1234 $1 /media/data/git/Projet_Tweetoradio/quizz\"" --tab --title="Client" -e "bash -c \"cd ./Client_Java/src; javac *.java; java Client\""
