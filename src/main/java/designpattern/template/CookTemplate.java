package designpattern.template;

/**
 * @author 叽嗒嘎叽
 * @since 2024/8/10
 */
public abstract class CookTemplate {

    /**
     * 炒菜流程固定
     */
    final void cookProcess() {
        pourOil();
        heatOil();
        pourVegetable();
        pourSauce();
        fry();
    }

    /**
     * 倒油 操作固定
     */
    void pourOil() {
        System.out.println("倒油");
    }

    /**
     * 热油 操作固定
     */
    void heatOil() {
        System.out.println("热油");
    }

    /**
     * 倒蔬菜 操作抽象，由子类实现
     */
    abstract void pourVegetable();

    /**
     * 倒调味料 操作抽象，由子类实现
     */
    abstract void pourSauce();

    void fry() {
        System.out.println("翻炒");
    }
}
