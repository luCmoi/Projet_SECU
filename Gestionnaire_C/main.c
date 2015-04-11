#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include <unistd.h>
#include <string.h>
#include <sys/socket.h>
#include <netdb.h>
#include <pthread.h>
#include "communication_diffuseur.h"
#include "communication_client.h"


list_diff_t *liste_diffuseur;

int args(int argc, const char* argv[]){
    if(argc == 2){

    }
    return 0;
}

void * fonctionThread(void * desc_client_diff){
    int descSock = *(int *)desc_client_diff;
    char *buff = malloc(sizeof(char)*1024);
    ssize_t recu=recv(descSock,buff,1023*sizeof(char),0);
    buff[recu]='\0';
    int retour = 0;
    char * name = strsep(&buff, " ");
    if (strncmp(name,REGI, 4) == 0){
        int place = add_to_list(liste_diffuseur, buff);
        #ifdef DEBUG
        printf_diffuseur_list(liste_diffuseur);
        #endif
        if(place != -1) retour = ask_ruok(descSock, liste_diffuseur, place);
    }
    else if(strncmp(name,LIST, 4) == 0) {
        list_diff_to_client(descSock,liste_diffuseur);

    }

    if(retour == -1){
        //errx(2, "Erreur : connexion client/diffuseur");

    }
    close(descSock);
    return NULL;

}

int main(int argc, const char* argv[]) {

    int i;
    liste_diffuseur = malloc(sizeof(list_diff_t));
    liste_diffuseur->liste  = malloc(sizeof(diffuseur_t*)*NOMBRE_MAX_DIFFUSEUR);
    for(i = 0; i<NOMBRE_MAX_DIFFUSEUR; i++){
        liste_diffuseur->liste[i] = NULL;
    };
    liste_diffuseur->first = 0;
    liste_diffuseur->nombre = 0;


    int retour = 0;
    int sockServer = socket(PF_INET,SOCK_STREAM,0);
    struct sockaddr_in address_sock;

    address_sock.sin_family=AF_INET;
    address_sock.sin_port=htons(DEFAULT_PORT);
    address_sock.sin_addr.s_addr=htonl(INADDR_ANY);

    retour = bind(sockServer,(struct sockaddr *)&address_sock,sizeof(struct sockaddr_in));
    if(retour == -1){
        perror(strerror(errno));
        exit(1);
    }
    retour = listen(sockServer,0);
    if(retour == -1){
        perror(strerror(errno));
        exit(1);
    }

    while(1){
        struct sockaddr_in caller;
        socklen_t size=sizeof(caller);
        int * descSock = malloc(sizeof(int));

        if(descSock == NULL){
            exit(1);
        }

        *descSock = accept(sockServer,(struct sockaddr *)&caller,&size);
        if(retour == -1){
            perror(strerror(errno));
            continue;
        }
        pthread_t th;
        retour = pthread_create(&th,NULL,fonctionThread,descSock);
        if(retour != 0){
            perror(strerror(errno));
            close(*descSock);
            free(descSock);
            continue;
        }

    }
    close(sockServer);
    return 0;
}
