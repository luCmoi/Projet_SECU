
#include "communication_diffuseur.h"

/**
 * Ajoute un diffuseur a la liste des diffuseurs
 * retourne la position du diffuseur dans la liste
 * retourn -1 si liste pleine
 */
int add_to_list(list_diff_t *listDiffT, char* buff, int max, pthread_mutex_t *verrou){

    pthread_mutex_lock(verrou);
    while (listDiffT->liste[listDiffT->first] != NULL){
        if(listDiffT->first >= max){
            return -1;
        }
        listDiffT->first++;
    }
    if(listDiffT->nombre > max){
        return -1;
    }
    listDiffT->liste[listDiffT->first] = malloc(sizeof(diffuseur_t));
    int i;

    char *tmp = strsep(&buff, " ");
    for (i = 0; i < SIZE_ID; ++i) {
        if(i < strlen(tmp))
            listDiffT->liste[listDiffT->first]->id[i] = tmp[i];
        else listDiffT->liste[listDiffT->first]->id[i] = '#';

    }
    tmp = strsep(&buff, " ");
    for (i = 0; i < SIZE_IP; ++i) {
        if(i < strlen(tmp))
            listDiffT->liste[listDiffT->first]->ip1[i] = tmp[i];
        else listDiffT->liste[listDiffT->first]->ip1[i] = '#';
    }
    tmp = strsep(&buff, " ");
    for (i = 0; i < SIZE_PORT; ++i) {
        if(i < strlen(tmp))
            listDiffT->liste[listDiffT->first]->port1[i] = tmp[i];
        else listDiffT->liste[listDiffT->first]->port1[i] = '#';
    }
    for (i = 0; i < SIZE_IP; ++i) {
        if(i < strlen(tmp))
            listDiffT->liste[listDiffT->first]->ip2[i] = tmp[i];
        else listDiffT->liste[listDiffT->first]->ip2[i] = '#';
    }
    for (i = 0; i < SIZE_PORT; ++i) {
        if(i < strlen(tmp))
            listDiffT->liste[listDiffT->first]->port2[i] = tmp[i];
        else listDiffT->liste[listDiffT->first]->port2[i] = '#';
    }
    printf("Ajout de %s dans la liste des diffuseurs\n", listDiffT->liste[listDiffT->first]->id);

    listDiffT->liste[listDiffT->first]->id[SIZE_ID] = '\0';
    listDiffT->liste[listDiffT->first]->ip1[SIZE_IP] = '\0';
    listDiffT->liste[listDiffT->first]->port1[SIZE_PORT] = '\0';
    listDiffT->liste[listDiffT->first]->ip2[SIZE_IP] = '\0';
    listDiffT->liste[listDiffT->first]->port1[SIZE_PORT] = '\0';

    listDiffT->nombre++;
    listDiffT->first++;

    pthread_mutex_unlock(verrou);
    return listDiffT->first-1;
}

/**
 * supprime un diffuseur de la liste des diffuseurs
 */
void remove_from_list(list_diff_t *listDiffT, int el, pthread_mutex_t *verrou){
    pthread_mutex_lock(verrou);
    printf("Supress de %s de la liste des diffuseurs\n", listDiffT->liste[el]->id);

    if(el == -1 )
        return;

    listDiffT->liste[el] = NULL;
    listDiffT->nombre--;
    listDiffT->first = (listDiffT->first<el)? listDiffT->first: el;
    pthread_mutex_unlock(verrou);
    return;
}



/**
 * Demande au diffuseur s'il est toujour connecté
 * inf-loop
 */
// TODO : time out RUOK
int ask_ruok(int desc_socket, list_diff_t *listDiffT, int place, struct pollfd *p) {
    p[0].fd=desc_socket;
    p[0].events=POLLIN;
    fcntl( desc_socket, F_SETFL, O_NONBLOCK);
    int flag = 1;
    while (1) {
        #ifdef DEBUG
        printf("%s; send RUOK\n", listDiffT->liste[place]->id);
        #endif
        setsockopt(desc_socket, IPPROTO_TCP, TCP_NODELAY, (char *) &flag, sizeof(int));
        send(desc_socket, RUOK, (sizeof(char) * SIZE_RUOK), 0);

        #ifdef DEBUG
        printf("%s: poll 10sec\n", listDiffT->liste[place]->id);
        #endif
        poll(p,1,10000);
        char *buff = malloc(sizeof(char) * 1024);
        if(p[0].revents==POLLIN){
            ssize_t recu = recv(desc_socket, buff, 1023 * sizeof(char), 0);
            buff[recu] = '\0';
            //TODO : changer la taille de strncmp a SIZE_IMOK
            if (strncmp(buff, IMOK, 4)) {
                /*#ifdef DEBUG
                printf("%s: != IMOK %s\n", listDiffT->liste[place]->id, buff);
                #endif*/
                return 0;
            }
            #ifdef DEBUG
            printf("%s :IMOK\n", listDiffT->liste[place]->id);
            #endif
        }
        else {
            #ifdef DEBUG
            printf("%s: TIME OUT\n", listDiffT->liste[place]->id);
            #endif
            return 0;
        }


    }
}
