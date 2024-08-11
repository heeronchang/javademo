package designpattern.template;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/10
 */
public class TemplateTest {
    public static void main(String[] args) {
        CookBaoCai cookBaoCai = new CookBaoCai();
        cookBaoCai.cookProcess();

        System.out.println("------");
        CookCaixin cookCaixin = new CookCaixin();
        cookCaixin.cookProcess();
    }
}
