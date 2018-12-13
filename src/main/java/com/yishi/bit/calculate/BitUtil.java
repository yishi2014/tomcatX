package com.yishi.bit.calculate;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class BitUtil {
    private static final byte[] INT_BYTES = {
            (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16, (byte) 17, (byte) 18, (byte) 19, (byte) 20, (byte) 21, (byte) 22, (byte) 23, (byte) 24, (byte) 25, (byte) 26, (byte) 27, (byte) 28, (byte) 29, (byte) 30, (byte) 31, (byte) 32, (byte) 33, (byte) 34, (byte) 35, (byte) 36, (byte) 37, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 43, (byte) 44, (byte) 45, (byte) 46, (byte) 47, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 58, (byte) 59, (byte) 60, (byte) 61, (byte) 62, (byte) 63, (byte) 64, (byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 91, (byte) 92, (byte) 93, (byte) 94, (byte) 95, (byte) 96, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 123, (byte) 124, (byte) 125, (byte) 126, (byte) 127, (byte) -128, (byte) -127, (byte) -126, (byte) -125, (byte) -124, (byte) -123, (byte) -122, (byte) -121, (byte) -120, (byte) -119, (byte) -118, (byte) -117, (byte) -116, (byte) -115, (byte) -114, (byte) -113, (byte) -112, (byte) -111, (byte) -110, (byte) -109, (byte) -108, (byte) -107, (byte) -106, (byte) -105, (byte) -104, (byte) -103, (byte) -102, (byte) -101, (byte) -100, (byte) -99, (byte) -98, (byte) -97, (byte) -96, (byte) -95, (byte) -94, (byte) -93, (byte) -92, (byte) -91, (byte) -90, (byte) -89, (byte) -88, (byte) -87, (byte) -86, (byte) -85, (byte) -84, (byte) -83, (byte) -82, (byte) -81, (byte) -80, (byte) -79, (byte) -78, (byte) -77, (byte) -76, (byte) -75, (byte) -74, (byte) -73, (byte) -72, (byte) -71, (byte) -70, (byte) -69, (byte) -68, (byte) -67, (byte) -66, (byte) -65, (byte) -64, (byte) -63, (byte) -62, (byte) -61, (byte) -60, (byte) -59, (byte) -58, (byte) -57, (byte) -56, (byte) -55, (byte) -54, (byte) -53, (byte) -52, (byte) -51, (byte) -50, (byte) -49, (byte) -48, (byte) -47, (byte) -46, (byte) -45, (byte) -44, (byte) -43, (byte) -42, (byte) -41, (byte) -40, (byte) -39, (byte) -38, (byte) -37, (byte) -36, (byte) -35, (byte) -34, (byte) -33, (byte) -32, (byte) -31, (byte) -30, (byte) -29, (byte) -28, (byte) -27, (byte) -26, (byte) -25, (byte) -24, (byte) -23, (byte) -22, (byte) -21, (byte) -20, (byte) -19, (byte) -18, (byte) -17, (byte) -16, (byte) -15, (byte) -14, (byte) -13, (byte) -12, (byte) -11, (byte) -10, (byte) -9, (byte) -8, (byte) -7, (byte) -6, (byte) -5, (byte) -4, (byte) -3, (byte) -2, (byte) -1
    };
    public static byte int2Byte(int i){
        if(i>255||i<0){
            throw new RuntimeException("can not convert this int to a byte");
        }
        return INT_BYTES[i];
    }
    public static int byte2Int(byte b){
        return b&0xff;
    }
    public static byte[] getByteFromStr(String str){
        if(str==null||str.length()==0)
            throw new RuntimeException("not a hex string");
        if(str.length()%2!=0)
            throw new RuntimeException("can not parse");
        byte[] bytes=new byte[str.length()/2];
        for(int i=0;i<bytes.length;i++){
            bytes[i]=INT_BYTES[Integer.valueOf(str.substring(2*i, 2*(i+1)),16)];
        }
        return bytes;
    }

    //-1:1111 1111
    //1111 1111 1111 1111 1111 1111 1111 1111
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(Arrays.toString(getByteFromStr("554a")));
        System.out.println(new String(getByteFromStr("554a"),"utf-16be"));
//        byte b=INT_BYTES[127];
//        byte c= (byte) (b+1);
//        System.out.println(c);
    }
}
