#include "struct.h"
/**
 * Affiche la liste des diffuseurs
 */
void printf_diffuseur_list(list_diff_t *listDiffT){
    int i;
    printf("Liste diffuseur\n");
    printf("-------------\n");
    for (i = 0; i < listDiffT->max; i++) {
        if(listDiffT->liste[i] != NULL)
            printf("diff %d: \t%s\t%s\t%s\t%s\t%s\n", i, listDiffT->liste[i]->id,listDiffT->liste[i]->ip1,listDiffT->liste[i]->port1, listDiffT->liste[i]->ip2, listDiffT->liste[i]->port2);
    }
    printf("-------------\n");
}
