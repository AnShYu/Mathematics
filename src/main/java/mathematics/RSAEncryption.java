package mathematics;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RSAEncryption {
    // c = m^e mod n
    // m = c^d mod n
    // d = e^(-1) modfi(n)
    // m - открытый текст; с - шифртекст; d - приватный ключ; e и n - публичный ключ
    //Сначала подберем p и q, простые числа. n = p*q
//    Подберем e 3 до фиn
//    решить уравнение ex + fin y   = 1


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите предельное простое число: ");
        int max_num = scanner.nextInt();
        System.out.println("Введите номер простого числа для p: ");
        int p_num = scanner.nextInt();
        System.out.println("Введите номер простого числа для q: ");
        int q_num = scanner.nextInt();

        int p = generatePrimeNumber(p_num, max_num);
        System.out.println("p: " +p);

        int q = generatePrimeNumber(q_num, max_num);
        System.out.println("q: " +q);

        int n = p*q;
        System.out.println("n: " +n);

        int phi_n = (p-1)*(q-1);
        System.out.println("phi_n: " +phi_n);

        int e = generateE(phi_n, p_num, max_num);
        System.out.println("e: " +e);

        int d = getPrivateKey(e, phi_n);
        System.out.println("d: " +d);


        System.out.println("Введите число, которое нужно зашифровать: ");
        int m = scanner.nextInt();

        BigInteger m_power_e = new BigInteger("1");
        for (int i = 0; i < e; i++) {
            m_power_e = m_power_e.multiply(new BigInteger(Integer.toString(m)));
        }
        BigInteger c = m_power_e.mod(new BigInteger(Integer.toString(n)));
        System.out.println("c: " + c);


        BigInteger c_power_d = new BigInteger("1");
        for (int i = 0; i < d; i++) {
            c_power_d = c_power_d.multiply(c);
        }
        BigInteger m_deciphered = c_power_d.mod(new BigInteger(Integer.toString(n)));
        System.out.println("Дешифрованное m: " +m_deciphered);


    }

    // Считаем d
    private static int getPrivateKey (int x, int y) {
        //        e x + phi_n y = 1
        int e = x;
        int phi_n = y;


        int k0 = e,
                k1 = phi_n,
                q = k0 / k1,
                k;
        int s0 = 1,
                s1 = 0,
                s;
        List<Integer> sList = new ArrayList<>();
        do {
            k = k0 - q*k1;
            if (k == 0) {
                break;
            }
            k0 = k1;
            k1 = k;
            s = s0 - q*s1;
            s0 = s1;
            s1 = s;
            sList.add(s);
            q = k0 / k1;
        } while (k!=0);
        int d_temp = sList.get(sList.size() - 1);
        int t = 1;
        while (d_temp < 0) {
            d_temp = d_temp + phi_n * t;
            t++;
        }
        int d = d_temp;
        return d;
    }

    // Получаем простые числа для p и q
    private static int generatePrimeNumber (int x, int y) {
        return SieveOfEratosthenes.getPrimeNumber(x,y);
    }

    // Подбираем e
    private static int generateE (int phi_n, int x, int z) {
        int j = 1;
        while (SieveOfEratosthenes.getPrimeNumber(x-j, z) > (phi_n - 1)) {
            j++;
        }
        int e = SieveOfEratosthenes.getPrimeNumber(x-j, z);
        if (e <= 3) {
            e = 5;
        }
        return e;
    }
}
