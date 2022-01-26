#include <iostream>

using namespace std;

int main()
{
    float pagoxingreso = 0.01718750f;
    int CapacidadEstadio = 48712;
    float DineroAcumulado = 0;
    int numeroMaxAsistentes = (50 * CapacidadEstadio); // 2435600
    int ConteoAsistentes = 0;

    while (ConteoAsistentes < numeroMaxAsistentes)
    {
        ConteoAsistentes++;
        DineroAcumulado += pagoxingreso;
    }

    cout << "=============================" << endl;
    //System.out.println("Iteracion " + ConteoAsistentes);
    cout << "Girar cheque por: " << DineroAcumulado << endl;
    cout << "Pago que deberia haber sido: " << (numeroMaxAsistentes * pagoxingreso) << endl;
    cout << "=============================" << endl;

    return 0;
}
