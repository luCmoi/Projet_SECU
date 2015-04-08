#ifndef TWEETORADIO_COMMUNICATION_CLIENT_H
#define TWEETORADIO_COMMUNICATION_CLIENT_H


#include <sys/socket.h>
#include <stdio.h>
#include "struct.h"

int list_diff_to_client(int desc, diffuseur_t *head, int nb_diff);
#endif //TWEETORADIO_COMMUNICATION_CLIENT_H
