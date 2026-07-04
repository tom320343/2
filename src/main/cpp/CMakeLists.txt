cmake_minimum_required(VERSION 3.18.1)
project("signaturecheck")

add_library(signature_check SHARED signature_check.cpp)

find_library(log-lib log)
find_library(android-lib android)

target_link_libraries(signature_check ${log-lib} ${android-lib} crypto)