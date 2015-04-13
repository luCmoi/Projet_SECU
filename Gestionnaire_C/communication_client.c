#include <netinet/in.h>
#include <netinet/tcp.h>
#include "communication_client.h"

void list_diff_to_client(int desc_socket, list_diff_t *listDiffT,int max, pthread_mutex_t *verrou) {
    pthread_mutex_lock(verrou);
    printf("Un clien a demander la list des diffuseurs\n");
    printf_diffuseur_list(listDiffT, max);
    char mess[SIZE_LINB];
    sprintf(mess, "%s %2d%s", LINB, listDiffT->nombre, END_LINE);

    send(desc_socket, mess, (sizeof(char) * (SIZE_LINB)), 0);
    char item[SIZE_ITEM + 1];
    item[SIZE_ITEM] = '\0';
    int i;

    for (i = 0; i < max; i++) {
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

