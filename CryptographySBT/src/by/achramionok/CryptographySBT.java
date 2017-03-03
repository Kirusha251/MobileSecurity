package by.achramionok;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.apache.commons.lang3.ArrayUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.StringJoiner;

/**
 * Created by Kirill on 22.02.2017.
 */
public class CryptographySBT {
    private static byte[] X_bytes = new byte[32];
    private static String H_value ="B194BAC80A08F53B366D008E584A5DE48504FA9D1BB6C7AC252E72C202FDCE0D";
    private static String[] H_TABLE = {"10110001", "10010100", "10111010", "11001000", "00001010", "00001000", "11110101", "00111011", "00110110", "01101101", "00000000", "10001110", "01011000", "01001010", "01011101", "11100100", "10000101", "00000100", "11111010", "10011101", "00011011", "10110110", "11000111", "10101100", "00100101", "00101110", "01110010", "11000010", "00000010", "11111101", "11001110", "00001101", "01011011", "11100011", "11010110", "00010010", "00010111", "10111001", "01100001", "10000001", "11111110", "01100111", "10000110", "10101101", "01110001", "01101011", "10001001", "00001011", "01011100", "10110000", "11000000", "11111111", "00110011", "11000011", "01010110", "10111000", "00110101", "11000100", "00000101", "10101110", "11011000", "11100000", "01111111", "10011001", "11100001", "00101011", "11011100", "00011010", "11100010", "10000010", "01010111", "11101100", "01110000", "00111111", "11001100", "11110000", "10010101", "11101110", "10001101", "11110001", "11000001", "10101011", "01110110", "00111000", "10011111", "11100110", "01111000", "11001010", "11110111", "11000110", "11111000", "01100000", "11010101", "10111011", "10011100", "01001111", "11110011", "00111100", "01100101", "01111011", "01100011", "01111100", "00110000", "01101010", "11011101", "01001110", "10100111", "01111001", "10011110", "10110010", "00111101", "00110001", "00111110", "10011000", "10110101", "01101110", "00100111", "11010011", "10111100", "11001111", "01011001", "00011110", "00011000", "00011111", "01001100", "01011010", "10110111", "10010011", "11101001", "11011110", "11100111", "00101100", "10001111", "00001100", "00001111", "10100110", "00101101", "11011011", "01001001", "11110100", "01101111", "01110011", "10010110", "01000111", "00000110", "00000111", "01010011", "00010110", "11101101", "00100100", "01111010", "00110111", "00111001", "11001011", "10100011", "10000011", "00000011", "10101001", "10001011", "11110110", "10010010", "10111101", "10011011", "00011100", "11100101", "11010001", "01000001", "00000001", "01010100", "01000101", "11111011", "11001001", "01011110", "01001101", "00001110", "11110010", "01101000", "00100000", "10000000", "10101010", "00100010", "01111101", "01100100", "00101111", "00100110", "10000111", "11111001", "00110100", "10010000", "01000000", "01010101", "00010001", "10111110", "00110010", "10010111", "00010011", "01000011", "11111100", "10011010", "01001000", "10100000", "00101010", "10001000", "01011111", "00011001", "01001011", "00001001", "10100001", "01111110", "11001101", "10100100", "11010000", "00010101", "01000100", "10101111", "10001100", "10100101", "10000100", "01010000", "10111111", "01100110", "11010010", "11101000", "10001010", "10100010", "11010111", "01000110", "01010010", "01000010", "10101000", "11011111", "10110011", "01101001", "01110100", "11000101", "01010001", "11101011", "00100011", "00101001", "00100001", "11010100", "11101111", "11011001", "10110100", "00111010", "01100010", "00101000", "01110101", "10010001", "00010100", "00010000", "11101010", "01110111", "01101100", "11011010", "00011101"};
    private static byte[] s_bytes = new byte[16];
    private static byte[] h_bytes = DatatypeConverter.parseHexBinary(H_value);
    private static byte MASK = (byte)Integer.parseInt("11111111", 2);
    private static byte[] a_X1;
    private static byte[] b_X2;
    private static byte[] d_X4;
    private static byte[] c_X3;
    private static byte[] Y;
    private static long Two_In32 = 4294967296L;
    private static long Two_In24 = 16777216L;
    private static long Two_In16 = 65536L;
    private static long Two_In8 = 256L;
    private static int T;
    private static int flag;
    private static long length;
    private static long Start;
    private static long Stop;
    public static String getHash(){
        RandomAccessFile file = null;
        long point = 0;
        long current;
        try {
            file = new RandomAccessFile("E:/Projects/GitHubProjects/Bezopasnost/video.mp4", "rw");
            length = file.length();
            point = ((int)(length/32)*32);
            while (file.getFilePointer()!= length) {
                //System.out.println(file.getFilePointer());
                if(file.getFilePointer() == point){
                flag = file.read(X_bytes = new byte[(int)(length-file.getFilePointer())]);
                    madeMultiple();
                }
                else {
                flag = file.read(X_bytes);
                }
                s_bytes = XOR(s_bytes,getDisplay1(ArrayUtils.addAll(X_bytes,h_bytes)));
                h_bytes = getDisplay2(ArrayUtils.addAll(X_bytes,h_bytes));
            }

        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        long fileLength = length*8;
        Y = getDisplay2(ArrayUtils.addAll(getWordForFileLength(fileLength),ArrayUtils.addAll(s_bytes,h_bytes)));
        return DatatypeConverter.printHexBinary(Y);

    }

    public static void madeMultiple(){
        T  = (X_bytes.length%32);
        for(int i = 0; i < (32-T);i++){
            X_bytes = Arrays.copyOf(X_bytes,32);
        }
    }

    public static byte[] getDisplay1(byte[] XandH){
        byte [] u1_d1 = Arrays.copyOfRange(XandH,0,16);
        byte [] u2_d1 = Arrays.copyOfRange(XandH,16,32);
        byte [] u3_d1 = Arrays.copyOfRange(XandH,32,48);
        byte [] u4_d1 = Arrays.copyOfRange(XandH,48,64);
        byte [] encrypt_result = encrypt(ArrayUtils.addAll(u1_d1,u2_d1),XOR(u3_d1,u4_d1));
        //byte [] ds = XOR((XOR(encrypt_result,u3_d1)),u4_d1);
        //System.out.println(ds);
        return XOR((XOR(encrypt_result,u3_d1)),u4_d1);
    }
    public static byte[] getDisplay2(byte[] XandH){
        byte [] u1_d2 = Arrays.copyOfRange(XandH,0,32);
        byte [] u2_d2 = Arrays.copyOfRange(XandH,32,64);
        byte [] u1 = Arrays.copyOfRange(XandH,0, 16);
        byte [] u2 = Arrays.copyOfRange(XandH,16,32);
       // byte [] mask = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        byte [] key_1 = ArrayUtils.addAll(getDisplay1(XandH),Arrays.copyOfRange(XandH,48,64));//u4
        byte [] key_2 = ArrayUtils.addAll(XORWithOnes(getDisplay1(XandH)),Arrays.copyOfRange(XandH,32,48));//u3
        byte [] first_encrypt_path_result = XOR(encrypt(key_1,u1),u1);//u1)d2
        byte [] second_encrypt_path_result = XOR(encrypt(key_2,u2),u2);//u2_d2

        return ArrayUtils.addAll(first_encrypt_path_result,second_encrypt_path_result);
    }

    public static byte[] XOR(byte[] u_bytes1, byte[] u_bytes2) {
        byte [] result = new byte[u_bytes1.length];
        for (int i = 0; i < u_bytes1.length; i++) {
            //u_bytes2[i] ^= u_bytes1[i];
            result[i] = (byte)(u_bytes1[i] ^ u_bytes2[i]);
        }
        return result;
    }

    private static byte[] XORWithOnes(byte[] array){
        byte[] result = new byte[array.length];
        int currentIndex;
        for (currentIndex = 0; currentIndex < array.length; currentIndex++){
            result[currentIndex] = (byte)(array[currentIndex] ^ MASK);
        }
        return result;
    }

    public static byte[] encrypt( byte key[], byte [] value){
        ArrayList<byte[]> K;
        a_X1 = Arrays.copyOfRange(value,0,4);
        b_X2 = Arrays.copyOfRange(value, 4, 8);
        c_X3 = Arrays.copyOfRange(value,8,12);
        d_X4 = Arrays.copyOfRange(value,12,16);
        byte [] e = new byte[4];
        byte [] buff;
        K = getK(key);
        for(int i = 1; i <= 8; i++){
            b_X2 = XOR(b_X2, G(5, square_plus(a_X1, K.get((7*i - 6 -1)))));
//            printTestResult(b_X2);
            c_X3 =  XOR(c_X3, G(21, square_plus(d_X4, K.get((7*i - 5 -1)))));
//            printTestResult(c_X3);
            a_X1 = square_minus(a_X1, G(13, square_plus(b_X2, K.get(7*i - 4 - 1))));
//            printTestResult(a_X1);
            e = XOR(G(21, square_plus(K.get(7*i - 3 -1), square_plus(b_X2, c_X3))),getWord(i));
//            printTestResult(e);
            b_X2 = square_plus(b_X2, e);
//            printTestResult(b_X2);
            c_X3 = square_minus(c_X3, e);
//            printTestResult(c_X3);
            d_X4 = square_plus(d_X4, G(13, square_plus(c_X3, K.get(7*i - 2 -1))));
//            printTestResult(d_X4);
            b_X2 = XOR(b_X2, G(21, square_plus(a_X1, K.get((7*i - 1 -1)))));
//            printTestResult(b_X2);
            c_X3 = XOR(c_X3, G(5, square_plus(d_X4, K.get((7*i - 1 )))));
//            printTestResult(c_X3);

            buff = a_X1;
            a_X1 = b_X2;
            b_X2 = buff;

            buff = c_X3;
            c_X3 = d_X4;
            d_X4 = buff;

            buff = b_X2;
            b_X2 = c_X3;
            c_X3 = buff;

        }
//        System.out.println("-------------------------------------------------------------------");
        //printTestResult(ArrayUtils.addAll(ArrayUtils.addAll(b_X2,d_X4),ArrayUtils.addAll(a_X1,c_X3)));
        return ArrayUtils.addAll(ArrayUtils.addAll(b_X2,d_X4),ArrayUtils.addAll(a_X1,c_X3));
    }
    public static ArrayList<byte[]> getK(byte key[]){
        ArrayList<byte[]> K = new ArrayList<>();
        int pos = 0;
        for(int i = 0; i < 56; i++){
            if(pos >= 8){
                pos = 0;
            }
            K.add(Arrays.copyOfRange(key,pos*4,(pos +1)*4));
            pos++;
        }
        return K;
    }

    public static byte [] G(int r,byte [] value){
        return RotHi(r,value);
    }

    private static int signedByteToInteger(byte b) {
        return b & 0xFF;
    }

    public static long getAccordance(byte[] value){
        long acc = signedByteToInteger(value[0]);
        acc += signedByteToInteger(value[1]) * Two_In8;
        acc += signedByteToInteger(value[2]) * Two_In16;
        acc += signedByteToInteger(value[3]) * Two_In24;
//        if(value.length ==4) {
//            for(int i = 0;i < value.length; i++) {
//                acc += signedByteToInteger(value[i]) * Math.pow(2,8*i);
//            }
//        }
        return acc;
    }
    public static byte [] getWordForFileLength(long lg){
        byte[] buff = ByteBuffer.allocate(8).putLong(lg).array();
        byte[] result = new byte[16];
        for (int i = 0; i < buff.length; i++){
            result[i] = buff[buff.length - i - 1];
        }
        return result;
    }
    public static byte[] getWord(long acc){
        acc = acc % Two_In32; // добавил лонг --------------------------------------------------------!!!!!!!!!
        byte [] buffer = ByteBuffer.allocate(8).putLong(acc).array();

        return new byte[]{buffer[7],buffer[6],buffer[5],buffer[4]};
    }

    public static byte [] square_plus(byte [] u, byte []v){
        return getWord(getAccordance(u)+getAccordance(v));
    }

    public static byte [] square_minus(byte [] u, byte[] v){
        return getWord(getAccordance(u) - getAccordance(v));
    }

    public static byte [] RotHi(int cycle,byte [] val){
        String string = H_TABLE[signedByteToInteger(val[3])] +H_TABLE[signedByteToInteger(val[2])] +
                H_TABLE[signedByteToInteger(val[1])] +
                H_TABLE[signedByteToInteger(val[0])] ;
        String swap = string.substring(cycle) + string.substring(0,cycle);
        Long buff = Long.parseLong(swap,2);
        byte[] b =  Arrays.copyOfRange(ByteBuffer.allocate(8).putLong(buff).array(),4,8);
       return new byte[]{b[3], b[2], b[1], b[0]};
    }

    public static void printTestResult(byte [] b) {
        System.out.println(DatatypeConverter.printHexBinary(b));
    }
    public static void Start(){
        Start = System.currentTimeMillis();
    }
    public static void Stop(){
        Stop = System.currentTimeMillis();
        System.out.println(Stop - Start);
    }
}
