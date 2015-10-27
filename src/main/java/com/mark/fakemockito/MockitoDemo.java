package com.mark.fakemockito;

import java.util.List;

import static org.mockito.Mockito.*;


/**
 * Author: Mark
 * Date  : 15/10/27.
 */
public class MockitoDemo {


    public static void main(String[] args) {
        List list = mock(List.class);
        when(list.get(0)).thenReturn("0");
        when(list.get(1)).thenReturn("1");

        System.out.println(list.get(0));
        System.out.println(list.get(0));
        System.out.println(list.get(1));

        //verify(list).get(0); // just exactly once
        verify(list, times(2)).get(0);
        verify(list, times(3)).get(anyInt());



        List mylist = FakeMockito.mock(List.class);
        FakeMockito.when(mylist.get(0)).thenReturn("0");

        System.out.println(mylist.get(0));
        System.out.println(mylist.get(0));

        FakeMockito.verify(mylist).get(0);
    }

}
