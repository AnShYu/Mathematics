package mathematics;

import java.util.ArrayList;
import java.util.List;

public class TestForModulus {
    public static void main(String[] args) {
        long FLIP_NUMBER = 5000000000l;
        int e = 10,
            m =12,
            n = 15;


        long m_power_e = 1;
        List<Long> listOfRemainders = new ArrayList<>();
        for (int i = 0; i < e; i++) {
            m_power_e = m_power_e * m;
            if (m_power_e >= FLIP_NUMBER) {
                long remainder = m_power_e % n;
                listOfRemainders.add(remainder);
                m_power_e = 1;
            }
        }
        long remainder = m_power_e % n;
        listOfRemainders.add(remainder);
        long c = 1;
        for (long x: listOfRemainders) {
            c = c * x;
        }
        c = c % n;
        System.out.println("c: " +c);
    }
}
