#include <sys/socket.h>
#include "communication_diffuseur.h"

/**
 *  Enregistrement d'un diffuseur a un gestionnaire:
 *      Diff -> Gest
 *          REGI␣id␣ip1␣port1␣ip2␣port2 taille 55 Octets
 *
 *      Gest -> Diff
 *          REOK si enregistrement OK Et ne ferme pas la connection taille 4 Octets
 *          RENO sinon et ferme la connection taille 4 Octets
 *
 *  Verfication des Diffuseurs :
 *      Gest -> Diff
 *          RUOK taille 4 Octets
 *
 *      Diff -> Gest
 *          IMOK le diffuseur est toujour la donc OK taille 4 Octets
 *          Sinon apres un temps donnée on ferme la connection et libere la place pour d'autre diffuseur
 *
*/


int add_to_list(list_diff_t *listDiffT, char* buff){
    /* Iterate through the list till we encounter the last node.*/

    // TODO: check error

    printf("add to list\n");
    fflush(stdout);

    while (listDiffT->liste[listDiffT->first] != NULL){
        if(listDiffT->first >= NOMBRE_MAX_DIFFUSEUR-1){
            return -1;
        }
        listDiffT->first++;
    }
    if(listDiffT->nombre >= NOMBRE_MAX_DIFFUSEUR-1){
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
    listDiffT->liste[listDiffT->first]->id[SIZE_ID] = '\0';
    listDiffT->liste[listDiffT->first]->ip1[SIZE_IP] = '\0';
    listDiffT->liste[listDiffT->first]->port1[SIZE_PORT] = '\0';
    listDiffT->liste[listDiffT->first]->ip2[SIZE_IP] = '\0';
    listDiffT->liste[listDiffT->first]->port1[SIZE_PORT] = '\0';

    listDiffT->nombre++;
    listDiffT->first++;
    return listDiffT->first-1;
}

void remove_from_list(list_diff_t *listDiffT, int el){
    //free(listDiffT->liste[el]);
    listDiffT->liste[el] = NULL;
    listDiffT->nombre--;
    listDiffT->first = (listDiffT->first<el)? listDiffT->first: el;
    return;
}

void printf_diffuseur_list(list_diff_t *listDiffT){
    int i;

    for (i = 0; i < NOMBRE_MAX_DIFFUSEUR; i++) {
        if(listDiffT->liste[i] != NULL)
            printf("diff %d:\t%s:%s:%s:%s:%s\n", i, listDiffT->liste[i]->id,listDiffT->liste[i]->ip1,listDiffT->liste[i]->port1, listDiffT->liste[i]->ip2, listDiffT->liste[i]->port2);
    }
}

// TODO : time out RUOK
int ask_ruok(int desc_socket, list_diff_t *listDiffT, int place) {
    while (1) {
        send(desc_socket, RUOK, (sizeof(char) * SIZE_RUOK), 0);
        char *buff = malloc(sizeof(char) * 1024);
        ssize_t recu = recv(desc_socket, buff, 1023 * sizeof(char), 0);
        buff[recu] = '\0';
        if (strncmp(buff, IMOK, SIZE_IMOK)) {
            printf("PAS OK: %s\n", listDiffT->liste[place]->id);
            remove_from_list(listDiffT, place);
            printf_diffuseur_list(listDiffT);
            return -1;
        }
        printf("IMOK :%s\n", listDiffT->liste[place]->id);
    }

}
