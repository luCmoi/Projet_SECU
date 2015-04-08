#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <errno.h>
#include <unistd.h>
#include <string.h>
#include <sys/socket.h>
#include <netdb.h>
#include <pthread.h>
#include <err.h>
#include "communication_diffuseur.h"
#include "communication_client.h"


diffuseur_t *head = NULL;
int NB_DIFF = 0;

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
        //head = malloc(sizeof(diffuseur_t));
        head = registration_diffuseur(descSock, buff, head,&NB_DIFF);

        /* print diff*/
        printf_diffuseur_list(head,&NB_DIFF);
        ask_ruok(descSock);
        printf("FIN");
    }
    else if(strncmp(name,LIST, 4) == 0) {
        retour = list_diff_to_client(descSock,head,NB_DIFF);
    }

    if(retour == -1){
        errx(2, "Erreur : connexion client/diffuseur");
    }

    free(desc_client_diff);
    return NULL;

}

int main(int argc, const char* argv[]) {
    //Variable pour les retour et erreur
    int retour = 0;
    //Socket Server IPV4
    int sockServer = socket(PF_INET,SOCK_STREAM,0);
    struct sockaddr_in address_sock;

    address_sock.sin_family=AF_INET;
    address_sock.sin_port=htons(DEFAULT_PORT);
    //Adresse quelqu'onque car localHost
    address_sock.sin_addr.s_addr=htonl(INADDR_ANY);

    //Fonction liant la socket a un port de la machine lui fournir un nom
    retour = bind(sockServer,(struct sockaddr *)&address_sock,sizeof(struct sockaddr_in));
    if(retour == -1){
        perror(strerror(errno));
        exit(1);
    }
    //fonction lancant l'écoute par default 0 nombre de client en attente
    retour = listen(sockServer,0);
    if(retour == -1){
        perror(strerror(errno));
        exit(1);
    }

    while(42){
        //Stocke l'addresse de l'appelant
        struct sockaddr_in caller;
        //Doit être init sinon bug
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

        /*
         *Creation de thread
         *via pthread
         *Memoire partagé
         *
         */
        pthread_t th;
        retour = pthread_create(&th,NULL,fonctionThread,descSock);
        if(retour != 0){
            perror(strerror(errno));
            close(*descSock);
            free(descSock);
            continue;
        }
        //pthread_join(th,NULL);

    }
    close(sockServer);
    return 0;
}