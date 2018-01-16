package com.mark.designpattern.builderpattern;

/**
 * Author: Mark
 * Date  : 2018/1/16
 */
public class PizzaMain {


    public static void main(String[] args) {
        final NyPizza nyPizza = new NyPizza.Builder(NyPizza.Size.LARGE).addTopping(Pizza.Topping.ONION).addTopping(Pizza.Topping.SAUSAGE).build();
        final Calzone calzone = new Calzone.Builder().addTopping(Pizza.Topping.SAUSAGE).addTopping(Pizza.Topping.ONION).sauceInside(true).build();
        System.out.println(nyPizza);
        System.out.println(calzone);
    }
}
