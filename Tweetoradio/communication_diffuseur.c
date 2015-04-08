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
diffuseur_t * registration_diffuseur(int desc, char* buff, diffuseur_t *head,int* nbdiff){
    printf("REGI DIFF\n");
    // recupe port
    diffuseur_t *new_diff = malloc(sizeof(diffuseur_t));

    // TODO: check error

    strcpy(new_diff->id, strsep(&buff, " "));
    new_diff->id[SIZE_ID] = '\0';
    strcpy(new_diff->ip1, strsep(&buff, " "));
    new_diff->ip1[SIZE_IP] = '\0';
    strcpy(new_diff->port1, strsep(&buff, " "));
    new_diff->port1[SIZE_PORT] = '\0';
    strcpy(new_diff->ip2, strsep(&buff, " "));
    new_diff->ip2[SIZE_IP] = '\0';
    strcpy(new_diff->port2, strsep(&buff, " "));
    new_diff->port1[SIZE_PORT] = '\0';

    return add_to_list(head, new_diff,nbdiff);;
}



diffuseur_t* add_to_list(diffuseur_t *head, diffuseur_t *diff, int* nbdiff){
    if(NULL == head){
        printf("HEad = null");
        diff->next = NULL;
        (*nbdiff)++;
        return diff;
    }
    if(NULL == diff)
    {
        printf("\n Node creation failed \n");
        return NULL;
    }
    printf("HEad != null");
    (*nbdiff)++;
    diff->next = head;

    return diff;
}

diffuseur_t* remove_from_list(diffuseur_t *head, diffuseur_t *diff){
    return NULL;
}

void printf_diffuseur_list(diffuseur_t *head,int* nbdiff){
    printf("List Diffuseur REGI : %d\n",*nbdiff);
    diffuseur_t *tmp = head;
    int i=2;
    printf("diff 1:\t%s:%s:%s:%s:%s\t next : %p\n", head->id,head->ip1,head->port1, head->ip2, head->port2, head->next);
    while ((tmp = tmp->next) != NULL){
        printf("diff %d:\t%s:%s:%s:%s:%s\t next : %p\n",i, tmp->id,tmp->ip1,tmp->port1, tmp->ip2, tmp->port2, tmp->next);
        i++;
    }
}

// TODO : time out RUOK
int ask_ruok(int desc_socket){
    struct timeval tv;

    tv.tv_sec = 10;
    tv.tv_usec = 0;
    while (1){
        send(desc_socket,"RUOK\n\r",(sizeof(char)*6),0);
        setsockopt(desc_socket, SOL_SOCKET, SO_RCVTIMEO, (char *)&tv,sizeof(struct timeval));

        char *buff = malloc(sizeof(char)*1024);
        ssize_t recu=recv(desc_socket,buff,1023*sizeof(char),0);
        buff[recu]='\0';
        if(strncmp(buff, IMOK, 4)){
            printf("NON EGAUX\n");
           return -1;
        }

    }


}