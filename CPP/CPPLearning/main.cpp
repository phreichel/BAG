#include <unordered_map>
#include <cctype>
#include <cstring>
#include <string>
#include <fstream>
#include <iostream>

#include <GLFW/glfw3.h>

//=============================================================================
using namespace std;
//=============================================================================

//=============================================================================
int main(int argc, char** argv)
{

    istream *input  = &cin;
    ostream *output = &cout;

    if (argc <= 1) {
        cerr << "usage: CPPLearning <configuration>" << endl;
        return -1;
    }

    ifstream fconfig(argv[1]);

    const int BUFFER_SIZE = 1024;
    char buffer[BUFFER_SIZE];
    string text;
    unordered_map<string,string> properties;
    while (fconfig) {

        fconfig.getline(buffer, BUFFER_SIZE);
        text = buffer;

        int splitPos = text.find_first_of('=');
        if (splitPos == -1) continue;

        string key = text.substr(0, splitPos);
        string value = text.substr(splitPos+1);

        int ks = key.find_first_not_of(" \t\n\r");
        int ke = key.find_last_not_of(" \t\n\r");
        key = key.substr(ks, ke-ks+1);

        int vs = value.find_first_not_of(" \t\n\r");
        int ve = value.find_last_not_of(" \t\n\r");
        value = value.substr(vs, ve-vs+1);

        properties.insert(pair<string,string>(key, value));

    }

    string ifpath = properties["INPUT"];
    if (ifpath.compare("STDIN") != 0) {
        input = new ifstream(ifpath, ios_base::openmode::_S_in);
    }

    string ofpath = properties["OUTPUT"];
    if (ofpath.compare("STDOUT") != 0) {
        output = new ofstream(ofpath, ios_base::openmode::_S_out);
    }

    for (const auto& [key, value] : properties) {
        *output << key << " = " << value << endl;
    }

    (*output).flush();

    if (input  != &cin)  delete input;
    if (output != &cout) delete output;

    return 0;

}
//=============================================================================
