package com.mark.designpattern.builderpattern;

import java.util.EnumSet;
import java.util.Set;

/**
 * Author: Mark
 * Date  : 2018/1/16
 */
public abstract class Pizza {

    public enum Topping {
        HAM, MUSHROOM, ONION, PEPPER, SAUSAGE
    }

    private final Set<Topping> toppings;

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone();
    }

    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(topping);
            return self();
        }

        abstract Pizza build();

        // subclass must override this method to return "this"
        protected abstract T self();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pizza{");
        sb.append("toppings=").append(toppings);
        sb.append('}');
        return sb.toString();
    }
}
