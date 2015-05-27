#! /usr/bin/bash
#! /usr/local/bin/bash

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

gnome-terminal --tab -t "Gestionnaire" -e "bash -c \" cd ./Gestionnaire_C ;make cleanall; make; ./gestionnaireC -d -p $1 -m $2; exec bash\""
gnome-terminal --tab --title="Client" -e "bash -c \"cd ./Client_Java/src; javac *.java; java Client\""
gnome-terminal --tab --title="Diffuseur quizz" -e "bash -c \"cd ./Diffuseur_Quizz_Java/src;javac *.java; java Main 225.077.013.099 8799 5842 $1 localhost ~/Projet_reseau/quizz ; bash \"" --tab --title="Diffuseur un" -e "bash -c \"cd ./Diffuseur_Java/src;javac *.java; java Main un 225.079.013.099 4564 6748 $1 localhost ~/Projet_reseau/diff_un; bash\"" --tab --title="Diffuseur deux" -e "bash -c \"cd ./Diffuseur_Java/src;javac *.java; java Main linux 225.079.007.099 5358 7885 $1 localhost ~/Projet_reseau/linux; bash\""

#gnome-terminal --tab --title="Diffuseur quizz" -e "bash -c \"cd ./Diffuseur_Quizz_Java/src;javac *.java; java Main 225.077.013.099 8799 5842 $1 localhost /media/data/git/Projet_Tweetoradio/quizz ; bash \"" --tab --title="Diffuseur un" -e "bash -c \"cd ./Diffuseur_Java/src;javac *.java; java Main un 225.079.013.099 4564 6748 $1 localhost /media/data/git/Projet_Tweetoradio/diff_un; bash\"" --tab --title="Diffuseur deux" -e "bash -c \"cd ./Diffuseur_Java/src;javac *.java; java Main deux 225.079.007.099 5358 7885 $1 localhost /media/data/git/Projet_Tweetoradio/linux; bash\""
