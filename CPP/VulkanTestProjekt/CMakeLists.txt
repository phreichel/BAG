cmake_minimum_required(VERSION 3.16)

project(VulkanTestProjekt LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(Vulkan REQUIRED)

add_executable(VulkanTestProjekt main.cpp)

target_link_libraries(VulkanTestProjekt PRIVATE Vulkan::Vulkan)

include(GNUInstallDirs)
install(TARGETS VulkanTestProjekt
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
    RUNTIME DESTINATION ${CMAKE_INSTALL_BINDIR}
)
