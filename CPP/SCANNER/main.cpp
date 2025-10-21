#include <iostream>
#include "scanner.hpp"

//--------------------------------------------------------------------------------
using namespace std;
//--------------------------------------------------------------------------------

//--------------------------------------------------------------------------------
int main(int argc, char** argv) {

    cout << "SCANNER EXAMPLE:" << endl;

    Scanner scanner(cin);
    bool run = true;
    while (run) {
        run = scanner.next();
    }

    return scanner.status();

}
//--------------------------------------------------------------------------------
