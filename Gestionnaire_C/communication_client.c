//
// Created by renaud on 28/03/15.
//


#include "communication_client.h"

void list_diff_to_client(int desc_socket, list_diff_t *listDiffT){
    char mess[SIZE_LINB];
    sprintf(mess, "%s %2d%s", LINB, listDiffT->nombre, END_LINE);

    send(desc_socket,mess,(sizeof(char)*(SIZE_LINB)),0);
    char item[SIZE_ITEM];
    item[SIZE_ITEM] = '\0';
    int i;
    for(i = 0; i < NOMBRE_MAX_DIFFUSEUR; i++){
        if(listDiffT->liste[i] != NULL){
            sprintf(item, "%s %s %s %s %s %s %s", ITEM, listDiffT->liste[i]->id,
                    listDiffT->liste[i]->ip1, listDiffT->liste[i]->port1,
                    listDiffT->liste[i]->ip2, listDiffT->liste[i]->port2, END_LINE);
            send(desc_socket, item, sizeof(char)*(SIZE_ITEM+1), 0);
        }

    }
}
