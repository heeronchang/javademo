package designpattern.template;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/10
 */
public class CookCaixin extends CookTemplate {
    @Override
    void pourVegetable() {
        System.out.println("下锅的蔬菜是菜心");
    }

    @Override
    void pourSauce() {
        System.out.println("下锅的酱料是蒜蓉");
    }
}
