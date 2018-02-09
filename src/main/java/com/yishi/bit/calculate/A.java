package com.yishi.bit.calculate;

public class A {
    public static void main(String[] args) {
//        for (int i =0; i <= 100; i++) {
//            System.out.format("%32s%n",Integer.toBinaryString(i));
//            System.out.format("%32s%n",Integer.toBinaryString(i+1));
//            System.out.format("%32s%n",Integer.toBinaryString(aa(i)));
//            System.out.println(aa(i));
//            System.out.print(i);
//            System.out.print(":");
//            System.out.println(aa(i));
//            System.out.println("-----------------------------------");
//
//        }
//
//        System.out.format("%32s%n", Integer.toBinaryString(9));
//        System.out.format("%32s%n", Integer.toBinaryString(-9));
//        System.out.println(reverse(-9));
//        System.out.println(reverse(9));
        System.out.println(0x7fffffff+1);
    }

    public static int reverse(int x) {
        return ~x + 1;
    }

    //判断一个数是否是2的n次方
    //x&x-1将x从右到左数遇到的第一的1置为0,当只有x的第一位为1时，x为2的n次方  此时结果为0
    //若x为奇数则此操作将得到一个小于1的偶数即x-1(即2的0次方)
    //若x为偶数则此操作将使x丢掉一个大小为2的n次方的值
    //n的值为从右往左数第一个1的位置（从0开始数）
    public static boolean isPower(int x) {
        return (x & (x - 1)) == 0;
    }

    //x|(x+1)将x从右到左数遇到的第一的第一个0置为1
    //若x为偶数 则此操作将得到一个x+1(即2的0次方)
    //若x为奇数 则x将会增长一个大小为2的n次方的值
    //n的值为从右往左数第一个1的位置（从0开始数）
    //对型如2的n次方减1的值 得到的结果为2的n+1次方减1
    //如果x为0x7fff ffff则此操作会将x的正负标志位更改 得到-1
    public static int changLast0(int x) {
        return x | (x + 1);
    }

    //x&(x+1)将x从右到左遇到的从第一位开始的连续的1置为0
    //1000 1111将变为 1000 0000
    //1000 1110将不变
    //若x型如2的n次方减1 则结果为0 即x的所有为均为1，（标志位除外）
    public static int changLast1s(int x) {
        return x & (x + 1);
    }

    //x|(x-1)将x从右到左遇到的从第一位开始的连续的0置为1
    //若x为0数 则结果必定变为奇数
    //若x为奇数则结果为x
    public static int changLast0s(int x) {
        return x | (x - 1);
    }

    //此运算将x从右到左数第一个遇到的0置为1 其余位皆置为0，如果找不到0 则结果为0
    //此操作的得到2的n次方的结果
    public static int aa(int x) {
        return ~x & (x + 1);
    }
}
