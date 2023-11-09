package it.unito.iumtweb.springboot.numberSum;

public class NumberSumClass {
  int number1;
  int number2;
  int sum;

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int computeSum() {
        setSum(this.number1+this.number2);
        return sum;
    }
}
