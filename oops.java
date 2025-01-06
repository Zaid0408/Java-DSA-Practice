import java.util.*;

public class oops {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
    }
}

//Inheritance
class Animal{
    String color;
    void eats(){
        System.out.println("eats");
    }
    void breathes(){
        System.out.println("breathes");
    }
}
class fish extends Animal{ // till here is single level inheritance
    void eats(){//method overriding in polymorphism
        super.eats();//will call parent class eats func 
        System.out.println("eats in water");// fish object will call this eats func instead of class function
    }
    void swims(){
        System.out.println("swims");
    }
}
class shark extends fish{ // till here is multi level inheritance
    void color(){
        super.breathes();
        System.out.println("Grey color");
    }
    
}
class mammal extends Animal{ // bird fish and mammal class display hierarchial inheritance where both have same parent animal
    
    void walks(){
        //super.color="Green";// changes color to green in animal class and for animal objects as well
        System.out.println("walks");
    }
}
class bird extends Animal{ //  bird fish and mammal class display hierarchial inheritance where both have same parent animal
    void fly(){
        System.out.println("fly");
    }
}


//Interfaces:
interface ChessPlayer { //all methods are abstract and of default type
    void moves(); // have not defined moves meaning the class can define the moves() function on their own this is just a blueprint
}

class Queen implements ChessPlayer{
    public void moves()
    {
        System.out.println("Moves Up down Left right diagnol in any number of steps. ");
    }
}
class Rook implements ChessPlayer{
    public void moves()
    {
        System.out.println("Moves Up down Left right in any number of steps. ");
    }
}
class Bishop implements ChessPlayer{
    public void moves()
    {
        System.out.println("Moves diagnol in any number of steps. ");
    }
}
class King implements ChessPlayer{
    public void moves()
    {
        System.out.println("Moves Up down Left right diagnol in one step. ");
    }
}

//Multilevel inheritance
interface herbivore{
    void eatsGrass();
}
interface carnivore{
    void eatsMeat();
}
class bear implements herbivore,carnivore{
    public void eatsGrass(){

    }
    public void eatsMeat(){

    }
}


class pen{// destructors are called automatically by garbage collector in java
    int tip;
    String color;
    int prop[];
    pen(){//default
        tip=0;
        color="";
    }
    pen(String newColor,int newTip){//parameterized
        color=newColor;
        tip=newTip;
    }
    pen(pen p1)//copy constructor //also shallow copy
    {
        this.color=p1.color;
        this.tip=p1.tip;
        this.prop=p1.prop;
    }
    //deep copy
    // pen(pen p1)//copy constructor 
    // {
    //     this.color=p1.color;
    //     this.tip=p1.tip;
            // for(int i=0; i<prop.length;i++){
            //     this.prop[i]=p1.prop[i];
            // }
    //     
    // }
    void setColor(String newColor){// setters
        this.color=newColor;
    }
    void setTip(int newTip){
        this.tip=newTip;
    }
    String getColor(){//getters 
        return this.color;
    }
    int getTip(){
        return this.tip;
    }
}