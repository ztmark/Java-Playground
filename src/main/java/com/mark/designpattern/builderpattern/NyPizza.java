package com.mark.designpattern.builderpattern;

import java.util.Objects;

/**
 * Author: Mark
 * Date  : 2018/1/16
 */
public class NyPizza extends Pizza {

    public enum Size {
        SMALL, MEDIUM, LARGE
    }

    private final Size size;

    private NyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }


    public static class Builder extends Pizza.Builder<Builder> {

        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NyPizza{");
        sb.append(super.toString());
        sb.append("size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}
