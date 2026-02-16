cmake_minimum_required(VERSION 3.5)

project(CPPLearning LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

add_executable(CPPLearning main.cpp
    properties)

include(GNUInstallDirs)
install(TARGETS CPPLearning
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
    RUNTIME DESTINATION ${CMAKE_INSTALL_BINDIR}
)
