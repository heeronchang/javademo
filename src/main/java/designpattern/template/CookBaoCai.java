package designpattern.template;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/10
 */
public class CookBaoCai extends CookTemplate {
    @Override
    void pourVegetable() {
        System.out.println("下锅的蔬菜是包菜");
    }

    @Override
    void pourSauce() {
        System.out.println("下锅的酱料是辣椒");
    }
}
