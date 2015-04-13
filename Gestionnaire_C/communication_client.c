#include "communication_client.h"

/**
 * Envoi la liste des diffuseur au client
 * Verrou
 */
void list_diff_to_client(int desc_socket, list_diff_t *listDiffT, pthread_mutex_t *verrou) {
    pthread_mutex_lock(verrou);
    printf("Un clien a demander la list des diffuseurs\n");
    printf_diffuseur_list(listDiffT);
    char mess[SIZE_LINB];
    sprintf(mess, "%s %2d%s", LINB, listDiffT->nombre, END_LINE);

    send(desc_socket, mess, (sizeof(char) * (SIZE_LINB)), 0);
    char item[SIZE_ITEM + 1];
    item[SIZE_ITEM] = '\0';
    int i;

    for (i = 0; i < listDiffT->max; i++) {
        if (listDiffT->liste[i] != NULL) {
            fflush(stdout);
            sprintf(item, "%s %s %s %s %s %s %s", ITEM, listDiffT->liste[i]->id,
                    listDiffT->liste[i]->ip1, listDiffT->liste[i]->port1,
                    listDiffT->liste[i]->ip2, listDiffT->liste[i]->port2, END_LINE);
            item[SIZE_ITEM] = '\0';
            send(desc_socket, item, sizeof(char) * (SIZE_ITEM), 0);
        }

    }
    pthread_mutex_unlock(verrou);
}

