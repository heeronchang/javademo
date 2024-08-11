package collection;

import java.util.HashSet;
import java.util.Set;

public class SetTest {
    private static class Bullet {
        private String size;

        public Bullet(String size) {
            this.size = size;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }

    public static void main(String[] args) {
        Set set = new HashSet<>();
        Bullet bullet = new Bullet("9mm");
        System.out.println(bullet.getSize());
        set.add(bullet);
        set.add(bullet);
        System.out.println(set.size());
        set.stream().forEach(System.out::println);
    }
}
