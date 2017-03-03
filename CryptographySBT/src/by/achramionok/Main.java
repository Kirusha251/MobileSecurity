package by.achramionok;

import com.sun.javafx.scene.layout.region.Margins;
import javafx.scene.shape.Path;

import javax.xml.bind.DatatypeConverter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.BitSet;

public class Main {

    public static void main(String[] args) {
    	System.out.println((byte)Integer.parseInt("11111111", 2));
    	System.out.println("---------------------------------------------------------------------------------------");
    	//createFileFromHex("B194BAC80A08F53B366D008E58");
    	long start = System.currentTimeMillis();
		System.out.println(CryptographySBT.getHash());
		//CryptographySBT.encrypt(DatatypeConverter.parseHexBinary("E9DEE72C8F0C0FA62DDB49F46F73964706075316ED247A3739CBA38303A98BF6"),DatatypeConverter.parseHexBinary("B194BAC80A08F53B366D008E584A5DE4"));
		long stop = System.currentTimeMillis();
		System.out.println((stop - start)/1000);

    }
	private static void createFileFromHex(String hex){
		try {
			OutputStream os = new FileOutputStream("E:/Projects/GitHubProjects/Bezopasnost/video.mp4");
			byte[] data = DatatypeConverter.parseHexBinary(hex);
			os.write(data);
			os.close();
		} catch (IOException e){

		}
	}
}
