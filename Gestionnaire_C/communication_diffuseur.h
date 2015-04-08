#ifndef TWEETORADIO_COMMUNICATION_DIFFUSEUR_H
#define TWEETORADIO_COMMUNICATION_DIFFUSEUR_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "struct.h"

diffuseur_t* add_to_list(diffuseur_t *head, diffuseur_t *diff,int* nbdiff);
diffuseur_t* remove_from_list(diffuseur_t *head, diffuseur_t *diff);
diffuseur_t * registration_diffuseur(int desc, char* buff, diffuseur_t *head,int* nbdiff);
void printf_diffuseur_list(diffuseur_t *head,int* nbdiff);
int ask_ruok(int desc_socket);


#endif //TWEETORADIO_COMMUNICATION_DIFFUSEUR_H
