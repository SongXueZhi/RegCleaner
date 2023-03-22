package start;



/**
 * @author lsn
 * @date 2023/3/17 10:33 AM
 */
public class TestDDJ {

    public static void main(String[] args) {

        DDJThread ddjThread1 = new DDJThread("ddmin", "bic", true);
        DDJThread ddjThread2 = new DDJThread("prodd", "bic", true);
        ddjThread1.start();
        ddjThread2.start();

    }


}
