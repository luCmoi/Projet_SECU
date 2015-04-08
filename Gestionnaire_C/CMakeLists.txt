cmake_minimum_required(VERSION 3.1)
project(Gestionnaire_C)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -Werror -pthread")

set(SOURCE_FILES main.c)
add_executable(Gestionnaire_C ${SOURCE_FILES} communication_client.h communication_client.c communication_diffuseur.h communication_diffuseur.c struct.h)