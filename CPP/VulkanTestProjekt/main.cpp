#include <iostream>
#include <vulkan/vulkan.h>


//=============================================================================
using namespace std;
//=============================================================================


//=============================================================================
VkApplicationInfo applicationInfo{
    VK_STRUCTURE_TYPE_APPLICATION_INFO,
    "VulkanTestProject",
    nullptr,
    VK_VERSION_1_0,
    nullptr,
    0,
    VK_API_VERSION_1_0
};
//=============================================================================


//=============================================================================
VkInstanceCreateInfo instanceCreateInfo {
    VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO,
    nullptr,
    0,
    &applicationInfo,
    0,
    0
};
//=============================================================================


//=============================================================================
int main() {

    cout << "BEGIN" << endl;

    VkResult result;
    int stage = 0;

    //-------------------------------------------------------------------------

    VkInstance instance = VK_NULL_HANDLE;

    if (stage == 0) {

        cout << "CREATE INSTANCE" << endl;
        result = vkCreateInstance(
            &instanceCreateInfo,
            nullptr,
            &instance
            );

        if (result != VK_SUCCESS) {
            cout << "RESULT: " << result << endl;
            cout << "FAILED TO CREATE INSTANCE\n";
        } else {
            stage++;
        }

    }

    //-------------------------------------------------------------------------

    uint32_t nPhysicalDevices = 16;
    VkPhysicalDevice arrPhysicalDevices[nPhysicalDevices];
    if (stage == 1) {

        cout << "ENUMERATE PHYSICAL DEVICES" << endl;

        result = vkEnumeratePhysicalDevices(
            instance,
            &nPhysicalDevices,
            arrPhysicalDevices
            );

        if (result != VK_SUCCESS) {
            cout << "RESULT: " << result << endl;
        } else {
            stage++;
            cout << "NDEVICES: " << nPhysicalDevices << endl;
        }

    }

    //-------------------------------------------------------------------------

    VkPhysicalDevice physicalDevice = VK_NULL_HANDLE;
    if (stage == 2) {

        int maxScore  = -1;
        for (int i=0; i<nPhysicalDevices; i++) {

            VkPhysicalDevice currentPhysicalDevice = arrPhysicalDevices[i];

            VkPhysicalDeviceProperties properties;
            vkGetPhysicalDeviceProperties(currentPhysicalDevice, &properties);

            int score = 0;
            switch (properties.deviceType) {
            case VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU:      score += 10 * 100;  break;
            case VK_PHYSICAL_DEVICE_TYPE_INTEGRATED_GPU:    score +=  5 * 100;  break;
            case VK_PHYSICAL_DEVICE_TYPE_VIRTUAL_GPU:       score +=  2 * 100;  break;
            case VK_PHYSICAL_DEVICE_TYPE_CPU:               score +=  1 * 100;  break;
            case VK_PHYSICAL_DEVICE_TYPE_OTHER:
            default:                                        score +=  0 * 100;  break;
            }
            score += properties.limits.maxImageDimension2D;

            if (score > maxScore) {
                maxScore = score;
                physicalDevice = currentPhysicalDevice;
            }

        }

        if (physicalDevice == VK_NULL_HANDLE) {
            cout << "NO SUITABLE DEVICE FOUND.";
        } else {
            stage++;
        }

    }

    //-------------------------------------------------------------------------

    int selectedFamilyProperty = -1;
    VkQueueFamilyProperties queueFamilyProperty{};
    if (stage == 3) {

        cout << "ENUMERATE QUEUE FAMILIES" << endl;

        uint32_t nQueueFamilies = 16;
        VkQueueFamilyProperties arrQueueFamilyProperties[nQueueFamilies];
        vkGetPhysicalDeviceQueueFamilyProperties(
            physicalDevice,
            &nQueueFamilies,
            arrQueueFamilyProperties
            );

        int bestScore = -1;
        for (int i=0; i<nQueueFamilies; i++) {
            VkQueueFamilyProperties queueFamilyProperty = arrQueueFamilyProperties[i];
            VkQueueFlags flags = queueFamilyProperty.queueFlags;
            int score = 0;
            if ((flags & VK_QUEUE_GRAPHICS_BIT) == 0) continue;
            if ((flags & VK_QUEUE_SPARSE_BINDING_BIT) != 0) score += 100;
            if ((flags & VK_QUEUE_COMPUTE_BIT)  != 0) score += 10;
            if (score > bestScore) {
                bestScore = score;
                selectedFamilyProperty = i;
            }
        }

        if (selectedFamilyProperty == -1) {
            cout << "NO SUITABLE QUEUE FOUND.";
        } else {
            queueFamilyProperty = arrQueueFamilyProperties[selectedFamilyProperty];
            stage++;
        }

    }

    //-------------------------------------------------------------------------

    VkDevice device = VK_NULL_HANDLE;
    if (stage == 4) {

        cout << "CREATE DEVICE" << endl;

        float queuePriority = 1.0f;
        VkDeviceQueueCreateInfo queueCreateInfo{};
        queueCreateInfo.sType            = VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO;
        queueCreateInfo.queueFamilyIndex = selectedFamilyProperty;
        queueCreateInfo.queueCount       = 1;
        queueCreateInfo.pQueuePriorities = &queuePriority;

        VkDeviceCreateInfo deviceCreateInfo{};
        deviceCreateInfo.sType                   = VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO;
        deviceCreateInfo.queueCreateInfoCount    = 1;
        deviceCreateInfo.pQueueCreateInfos       = &queueCreateInfo;

        result = vkCreateDevice(
            physicalDevice,
            &deviceCreateInfo,
            nullptr,
            &device
            );

        if (result != VK_SUCCESS) {
            cout << "RESULT: " << result << endl;
            cout << "FAILED TO CREATE LOGICAL DEVICE\n";
        } else {
            stage++;
        }

    }

    //-------------------------------------------------------------------------

    VkQueue graphicsQueue = VK_NULL_HANDLE;
    if (stage == 5) {

        vkGetDeviceQueue(
            device,
            selectedFamilyProperty,
            0,
            &graphicsQueue
        );

    }

    //-------------------------------------------------------------------------

    VkCommandPool commandPool = VK_NULL_HANDLE;
    if (stage == 5) {

        cout << "CREATE COMMAND POOL" << endl;

        VkCommandPoolCreateInfo commandPoolCreateInfo{};
        commandPoolCreateInfo.sType = VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO;
        commandPoolCreateInfo.queueFamilyIndex = selectedFamilyProperty;
        commandPoolCreateInfo.flags = VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT;

        result = vkCreateCommandPool(
            device,
            &commandPoolCreateInfo,
            nullptr,
            &commandPool
            );

        if (result != VK_SUCCESS) {
            cout << "RESULT: " << result << endl;
            cout << "FAILED TO CREATE COMMAND POOL\n";
        } else {
            stage++;
        }

    }

    //-------------------------------------------------------------------------

    VkCommandBuffer commandBuffer = VK_NULL_HANDLE;
    if (stage == 6) {

        cout << "ALLOCATE COMMAND BUFFER" << endl;

        VkCommandBufferAllocateInfo commandBufferAllocateInfo{};
        commandBufferAllocateInfo.sType = VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO;
        commandBufferAllocateInfo.commandPool = commandPool;
        commandBufferAllocateInfo.commandBufferCount = 1;
        commandBufferAllocateInfo.level = VK_COMMAND_BUFFER_LEVEL_PRIMARY;

        result = vkAllocateCommandBuffers(
            device,
            &commandBufferAllocateInfo,
            &commandBuffer
            );

        if (result != VK_SUCCESS) {
            cout << "RESULT: " << result << endl;
            cout << "FAILED TO ALLOCATE COMMAND BUFFER\n";
        } else {
            stage++;
        }
    }

    //-------------------------------------------------------------------------

    if (stage == 7) {

        cout << "BEGIN COMMAND BUFFER" << endl;

        VkCommandBufferBeginInfo beginInfo{};
        beginInfo.sType = VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO;

        result = vkBeginCommandBuffer(
            commandBuffer,
            &beginInfo
        );

        if (result != VK_SUCCESS) {
            cout << "BEGIN CMD RESULT: " << result << endl;
        } else {
            stage++;
        }
    }

    if (stage == 8) {

        cout << "END COMMAND BUFFER" << endl;

        result = vkEndCommandBuffer(
            commandBuffer
        );

        if (result != VK_SUCCESS) {
            cout << "END CMD RESULT: " << result << endl;
        } else {
            stage++;
        }
    }

    //-------------------------------------------------------------------------

    switch (stage) {
    case 9:
    case 8:
    case 7:
        // cout << "DESTROY COMMAND BUFFER" << endl;
        // vkDestroyCommandBuffer(device, commandBuffer, nullptr);
    case 6:
        cout << "DESTROY COMMAND POOL" << endl;
        vkDestroyCommandPool(device, commandPool, nullptr);
    case 5:
        cout << "DESTROY DEVICE" << endl;
        vkDestroyDevice(device, nullptr);
    case 4:
    case 3:
    case 2:
    case 1:
        cout << "DESTROY INSTANCE" << endl;
        vkDestroyInstance(instance, nullptr);
    default: break;
    };

    //-------------------------------------------------------------------------

    cout << "END" << endl;

    return 0;

}
//=============================================================================
