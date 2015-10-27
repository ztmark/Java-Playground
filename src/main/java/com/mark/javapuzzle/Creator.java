package com.mark.javapuzzle;

/**
 * Author: Mark
 * Date  : 2015/6/13
 * Time  : 21:43
 */
public class Creator {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            // not a statement , 这是局部变量声明语句(local variable declaration statement)
            // 一个局部变量声明作为一条语句只能直接出现在一个语句块中（{}）
            Creature creature = new Creature();
        }
        System.out.println(Creature.numCreated());
    }

}
