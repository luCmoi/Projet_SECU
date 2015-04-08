cmake_minimum_required(VERSION 3.1)
project(Tweetoradio)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -Werror -pthread")

set(SOURCE_FILES main.c)
add_executable(Tweetoradio ${SOURCE_FILES} communication_client.h communication_client.c communication_diffuseur.h communication_diffuseur.c struct.h)