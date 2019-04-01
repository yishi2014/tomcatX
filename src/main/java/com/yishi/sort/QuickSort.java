package com.yishi.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    public static void quickSort(int[] arr, int start, int end) {
        if (start == end) return;
        int i = start+1;
        int j = end;
        int pivot = arr[start];

        while (true) {
            while (arr[i] <= pivot && i < end) {
                i++;
            }
            while (arr[j] >= pivot && j > start+1) {
                j--;
            }
            if (i < j)
                swap(arr, i, j);
            else if (i >= j) {
                if (arr[j] < pivot)
                    swap(arr, start, j);
                break;
            }

        }
        quickSort(arr, start, j - 1);
        quickSort(arr, j, end);


    }

    private static void swap(int[] arr, int i, int j) {
        arr[i] ^= arr[j];
        arr[j] ^= arr[i];
        arr[i] ^= arr[j];

    }
    public static void main(String[] args) {
        int [] arr={0,1,2,3,4,5,6,7,8,9};

        Random random=new Random();
        int capacity=10;
//        int []arr=new int[capacity];
        for(int i=0;i<capacity;i++){
            swap(arr,random.nextInt(10),random.nextInt(10));
        }
        System.out.println(Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
//        int odd=0;
//        for(int i=0;i<10; i++){
//
//            System.out.println(i^1);
//        }
    }




}
