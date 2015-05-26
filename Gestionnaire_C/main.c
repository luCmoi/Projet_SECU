#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include <unistd.h>
#include <string.h>
#include <sys/socket.h>
#include <netdb.h>
#include <pthread.h>
#include <sys/poll.h>
#include "communication_diffuseur.h"
#include "communication_client.h"

pthread_mutex_t pthread_mutex_t1= PTHREAD_MUTEX_INITIALIZER;
list_diff_t *liste_diffuseur;
short args = 0;


void* fonctionThread(void *desc_client_diff){
    int descSock = *(int *)desc_client_diff;

    struct pollfd p[1];
    char *buff = malloc(sizeof(char)*1024);
    ssize_t recu = recv(descSock, buff, 1023 * sizeof(char), 0);
    buff[recu] = '\0';
    int retour = 1;
    int place = -1;
    char * name = strsep(&buff, " ");
    if (strncmp(name,REGI, 4) == 0){
        //TODO : ajouter un check de la taille du message recu
        if(recu != SIZE_REGI){
            printf("WARNING message recu taille != 57 : %s\n", name);
            printf("taille : %ld\n", recu);
            return NULL;
        }

        place = add_to_list(liste_diffuseur, buff, &pthread_mutex_t1, args);

        printf_diffuseur_list(liste_diffuseur);
        if(place != -1) {
            send(descSock, REOK, sizeof(char)*(SIZE_REOK), 0);
            retour = ask_ruok(descSock, liste_diffuseur,place, p, args );
        }
        else{
            send(descSock, RENO, sizeof(char)*(SIZE_RENO), 0);
        }
    }
    else if(strncmp(name,LIST, 4) == 0) {
        //TODO : ajouter un check de la taille du message recu
        if(recu != SIZE_LIST){
            printf("WARNING message recu taille != 57 : %s\n", name);
            printf("taille : %ld\n", recu);
            return NULL;
        }
        list_diff_to_client(descSock,liste_diffuseur, &pthread_mutex_t1, args);

    }

    if(!retour) {
        remove_from_list(liste_diffuseur, place, &pthread_mutex_t1, args);
        printf_diffuseur_list(liste_diffuseur);
        fflush(stdout);
    }
    close(descSock);
    return NULL;

}

void parse_arg(int argc, const char* argv[], int *port, int* max){
    *port = DEFAULT_PORT;
    *max = NOMBRE_MAX_DIFFUSEUR;
    int error = 0;
    int mod = 0;
    if (argc < 2) return;
    int i;
    for(i = 1; i < argc; i++){
        if(strcmp(argv[i], "-d") == 0 || strcmp(argv[i], "--debug") == 0) {
            if (args & DEBUG) error = -1; else args |= DEBUG;
        }
        else if(strcmp(argv[i], "-p") == 0 || strcmp(argv[i], "--port") == 0) {
            if (args & PORT) error = -2; else { args |= PORT; mod = 1;}
        }
        else if(strcmp(argv[i], "-m") == 0 || strcmp(argv[i], "--max") == 0) {
            if (args & MAX) error = -3; else { args |= MAX; mod = 2;}
        }
        else {
            if(mod == 1)*port = atoi(argv[i]);
            else if(mod == 2)*max = atoi(argv[i]);
            else error = -4;
        }

        if (error != 0){
            printf("erreur dans les arguments\n");
            printf("./gestionnaireC [-d | --debug] [-p num_port | --port num_port] [-m nb_max| --max nb_max]\n");
            exit(-1);
        }


    }



}

int main(int argc, const char* argv[]) {

    int port;
    int max_diff;
    parse_arg(argc, argv, &port, &max_diff);
    int i;
    liste_diffuseur = malloc(sizeof(list_diff_t*));
    liste_diffuseur->liste  = malloc(sizeof(diffuseur_t*)*max_diff);
    for(i = 0; i<max_diff; i++){
        liste_diffuseur->liste[i] = NULL;
    };
    liste_diffuseur->first = 0;
    liste_diffuseur->nombre = 0;
    liste_diffuseur->max = max_diff;


    int retour = 0;
    int sockServer = socket(PF_INET,SOCK_STREAM,0);
    struct sockaddr_in address_sock;

    address_sock.sin_family=AF_INET;
    address_sock.sin_port=htons(port);
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
