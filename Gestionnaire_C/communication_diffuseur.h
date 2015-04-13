#ifndef TWEETORADIO_COMMUNICATION_DIFFUSEUR_H
#define TWEETORADIO_COMMUNICATION_DIFFUSEUR_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/poll.h>
#include <fcntl.h>
#include <netinet/in.h>
#include <netinet/tcp.h>
#include "struct.h"


int add_to_list(list_diff_t *listDiffT, char* buff, int max);
void remove_from_list(list_diff_t *listDiffT, int el);
void printf_diffuseur_list(list_diff_t *listDiffT, int max);
int ask_ruok(int desc_socket, list_diff_t *listDiffT, int place, struct pollfd *p);


#endif //TWEETORADIO_COMMUNICATION_DIFFUSEUR_H