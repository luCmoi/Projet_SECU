#include "communication_diffuseur.h"

int initialise_diffuseur(char *buff, diffuseur_t *diff, short args){
    int i;
    char *token;
    char *a, *b, *c , *d;
    token = strsep(&buff, " ");
    for (i = 0; i < SIZE_ID; ++i) {
        if(i < strlen(token))
            diff->id[i] = token[i];
        else diff->id[i] = '#';

    }

    token = strsep(&buff, " ");
    a = strsep(&token, ".");
    b = strsep(&token, ".");
    c = strsep(&token, ".");
    d = strsep(&token, ".");
    snprintf(diff->ip1, sizeof(diff->ip1), "%03i.%03i.%03i.%03i", atoi(a), atoi(b), atoi(c), atoi(d));

    token = strsep(&buff, " ");
    snprintf(diff->port1, sizeof(diff->port1), "%04i", atoi(token));

    token = strsep(&buff, " ");
    a = strsep(&token, ".");
    b = strsep(&token, ".");
    c = strsep(&token, ".");
    d = strsep(&token, ".");
    snprintf(diff->ip2, sizeof(diff->ip2), "%03i.%03i.%03i.%03i", atoi(a), atoi(b), atoi(c), atoi(d));

    token = strsep(&buff, " ");
    snprintf(diff->port2, sizeof(diff->port2), "%04i", atoi(token));

    diff->id[SIZE_ID] = '\0';
    diff->ip1[SIZE_IP] = '\0';
    diff->port1[SIZE_PORT] = '\0';
    diff->ip2[SIZE_IP] = '\0';
    diff->port1[SIZE_PORT] = '\0';
    if (args & DEBUG){
        //printf("id: %s\nip1: %s\nport1 %s\nip2: %s\nport2 %s\n", diff->id, diff->ip1, diff->port1, diff->ip2, diff->port2);
    }
    return 0;
}

/**
 * Ajoute un diffuseur a la liste des diffuseurs
 * retourne la position du diffuseur dans la liste
 * retourn -1 si liste pleine
 * Verrou
 */
int add_to_list(list_diff_t *listDiffT, char* buff, pthread_mutex_t *verrou, short args){

    pthread_mutex_lock(verrou);
    while (listDiffT->liste[listDiffT->first] != NULL && listDiffT->first < listDiffT->max){
        listDiffT->first++;
    }

    if(listDiffT->first >= listDiffT->max){
        return -1;
    }
    if(args & DEBUG){
        printf("regi : %s", buff);
    }
    listDiffT->liste[listDiffT->first] = malloc(sizeof(diffuseur_t));
    initialise_diffuseur(buff,listDiffT->liste[listDiffT->first], args);
    printf("Ajout de %s dans la liste des diffuseurs\n", listDiffT->liste[listDiffT->first]->id);
    listDiffT->nombre++;
    listDiffT->first++;
    pthread_mutex_unlock(verrou);
    return listDiffT->first-1;
}

/**
 * supprime un diffuseur de la liste des diffuseurs
 * Verrou
 */
void remove_from_list(list_diff_t *listDiffT, int el, pthread_mutex_t *verrou, short args){
    pthread_mutex_lock(verrou);
    printf("Supression de %s de la liste des diffuseurs\n", listDiffT->liste[el]->id);
    if(el == -1 )
        return;
    listDiffT->liste[el] = NULL;
    listDiffT->nombre--;
    listDiffT->first = (listDiffT->first<el)? listDiffT->first: el;
    pthread_mutex_unlock(verrou);
    return;
}



/**
 * Demande au diffuseur s'il est toujours connecté
 * inf-loop
 */
int ask_ruok(int desc_socket, list_diff_t *listDiffT, int place, struct pollfd *p, short args) {
    p[0].fd=desc_socket;
    p[0].events=POLLIN;
    fcntl( desc_socket, F_SETFL, O_NONBLOCK);
    int flag = 1;
    while (1) {
        sleep(10);
        if(args & DEBUG){
            printf("send RUOK à %s\n", listDiffT->liste[place]->id);
        }
        setsockopt(desc_socket, IPPROTO_TCP, TCP_NODELAY, (char *) &flag, sizeof(int));
        send(desc_socket, RUOK, (sizeof(char) * SIZE_RUOK), 0);
        if(args & DEBUG){
            printf("en attente de la réponse de : %s\n", listDiffT->liste[place]->id);
        }
        poll(p,1,4000);
        char *buff = malloc(sizeof(char) * 1024);
        if(p[0].revents==POLLIN){
            ssize_t recu = recv(desc_socket, buff, 1023 * sizeof(char), 0);
            buff[recu+1] = '\0';

            if (strncmp(buff, IMOK, 4) == 0) {
                if(args & DEBUG){
                    printf("réponse reçu de %s : %s\n", listDiffT->liste[place]->id, buff);
                }

            }
            else {
                if(args & DEBUG){
                    printf("FAIL réponse reçu de %s : %s\n", listDiffT->liste[place]->id, buff);
                    printf("%s ve être supprimé de la liste des diffuseurs\n", listDiffT->liste[place]->id);
                }
                return 0;
            }

        }
        else {
            if(args & DEBUG) {
                printf("le diffuseur %s n'a pas répondu\n", listDiffT->liste[place]->id);
            }
            return 0;
        }


    }
}
