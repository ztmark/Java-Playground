package com.mark.designpattern.builderpattern;

/**
 * Author: Mark
 * Date  : 2018/1/16
 */
public class Calzone extends Pizza {

    private final boolean sauceInside;

    private Calzone(Builder builder) {
        super(builder);
        sauceInside = builder.sauceInside;
    }

    public static class Builder extends Pizza.Builder<Builder> {

        private boolean sauceInside;

        public Builder sauceInside(boolean sauceInside) {
            this.sauceInside = sauceInside;
            return this;
        }

        @Override
        Calzone build() {
            return new Calzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Calzone{");
        sb.append(super.toString());
        sb.append("sauceInside=").append(sauceInside);
        sb.append('}');
        return sb.toString();
    }
}
