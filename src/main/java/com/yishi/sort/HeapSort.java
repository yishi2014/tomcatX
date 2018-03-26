package com.yishi.sort;

import java.util.Arrays;
import java.util.Random;

public class HeapSort {
    public static int getlevel(int length){
        int i=1;
        int level=0;
        do{i<<=1;level++;}
        while(i<length);
        return level;
    }
    public static boolean hasLeftChild(int []arr,int index,int length){
        return 2*index+1<length;
    }
    public static int getleftChild(int [] arr,int index){
        return arr[2*index+1];
    }
    public static void setLeftChildVal(int arr[],int index,int val){
        arr[2*index+1]=val;
    }
    public static void setRightChildVal(int arr[],int index,int val){
        arr[2*index+2]=val;
    }
    public static boolean hasRightChild(int []arr,int index,int length){
        return 2*index+2<length;
    }
    public static int getRightChild(int [] arr,int index){
        return arr[2*index+2];
    }
    public static void swap(int[] arr,int index,int length){
        int top=arr[index];
        if(hasLeftChild(arr,index,length)){
            int left=getleftChild(arr,index);
            if(top<left){
                int tmp=top;
                arr[index]=left;
                setLeftChildVal(arr,index,tmp);
            }
        }else{
            return ;
        }
        if(hasRightChild(arr,index,length)){
            int right=getRightChild(arr,index);
            top=arr[index];
            if(top<right){
                int tmp=top;
                arr[index]=right;
                setRightChildVal(arr,index,tmp);
            }

        }
    }
    public static void heapSort(int[] arr) {
        int length=arr.length;
        while(length>1){
            int maxLevel=getlevel(length);
            for(int i=maxLevel-2;i>=0;i--){
                for(int j=(1<<i)-1;j<(1<<(i+1))-1;j++){
                    swap(arr,j,length);
                }
            }
            arr[0]^=arr[length-1];
            arr[length-1]^=arr[0];
            arr[0]^=arr[length-1];
            length--;
        }

    }

    public static void main(String[] args) {
        Random random = new Random();
        int capacity = 10;
        int[] arr = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            arr[i] = random.nextInt(10);
        }
        System.out.println(Arrays.toString(arr));

        heapSort(arr);
        System.out.println(Arrays.toString(arr));
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

    }
}
