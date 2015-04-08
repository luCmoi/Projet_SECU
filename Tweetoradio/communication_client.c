//
// Created by renaud on 28/03/15.
//


#include "communication_client.h"

int list_diff_to_client(int desc_socket, diffuseur_t *head, int nb_diff){
    char mess[SIZE_LINB+1+SIZE_NB_DIFF+3];
    sprintf(mess, "%s %d\n\r", LINB, nb_diff);

    send(desc_socket,mess,(sizeof(char)*(SIZE_LINB+1+SIZE_NB_DIFF+3)),0);

    return 0;
}
