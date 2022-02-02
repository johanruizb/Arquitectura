#include <iostream>

using namespace std;

int main(int argc, char const *argv[])
{
    float pagoxingreso = 0.01718750f;
    float DineroAcumulado = 0;
    int CapacidadEstadio = 48712;
    int numeroMaxAsistentes = (50 * CapacidadEstadio); // 2435600
    int ConteoAsistentes = 0;

    float temporada = 0.0f;

    cout << "=============================" << endl;

    float delta = 0.0f;
    while (true)
    {
        ConteoAsistentes++;
        DineroAcumulado = DineroAcumulado + pagoxingreso;

        if (delta == DineroAcumulado)
        {
            temporada = (ConteoAsistentes / numeroMaxAsistentes);

            cout << "Temporada " << temporada << endl;
            cout << "Girar cheque por: " << DineroAcumulado << endl;
            cout << "Pago que deberia haber sido: " << (ConteoAsistentes * pagoxingreso) << endl;
            cout << "Asistentes " << ConteoAsistentes << endl;
            cout << "Delta " << delta << endl;
            break;
        }
        
        delta = DineroAcumulado;
    }

    cout << "=============================";

    return 0;
}
