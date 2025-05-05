public class Dessert {
    public int flavor;
    public int price;
    public static int numDesserts = 0;
    public Dessert(int flavor, int price){
        this.flavor = flavor;
        this.price = price;
        numDesserts += 1;
    }
    public void printDessert(){
        System.out.print(flavor+" ");
        System.out.print(price+" ");
        System.out.print(numDesserts);
    }

    public static void main(String[] args){
        System.out.print("I love dessert!");
    }
}
