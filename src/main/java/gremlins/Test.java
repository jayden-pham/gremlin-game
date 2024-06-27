class Animal {

    public Animal() {

    }
}

class Wolf extends Animal {
    public Wolf() {

    }

    public void attack() {
        System.out.println("hi");
    }
}

public class Test {

    public static void main(String[] args) {
        Animal a = new Wolf();
        a.attack();
    }
}