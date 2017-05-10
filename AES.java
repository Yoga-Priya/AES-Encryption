import java.math.BigInteger;
import java.util.Scanner;
//import java.util.Arrays;

public class AES {

	public static void main(String[] args) {
Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the 128 bit key text in hexdec");
		String ip_hex=sc.next();
		System.out.println("Enter the number of rounds needed");
		 int r=sc.nextInt();
		int[] ip=new int[128];
		String[] key=new String[16];
		String[] w=new String[4*r+4];
		for(int i=0;i<w.length;i++)
			w[i]="";
		String temp=hexToBinary(ip_hex);
		while(temp.length()!=128)
			temp="0"+temp;
		System.out.println("binary value "+temp);

		for(int i=0;i<128;i++) {
		    ip[i] = Integer.parseInt(String.valueOf(temp.charAt(i)));
		  };
		  for(int i=0;i<4;i++){
			  w[i]=temp.substring(32*i, 32*i+32);
		  
		  }
		 
		for(int i=0;i<w.length;i++)
			  System.out.println(w[i]);

		//get the number of rounds for which key is to be generated
		
		  int[] wn0=new int[32];
		  int[] wn1=new int[32];
		  int[] wn2=new int[32];
		  int[] wn3=new int[32];
		 
System.out.println("w length is "+w[0].length());
		  for(int i=0;i<r;i++){
			  System.out.println("For round "+(i+1));
			  for(int q=0;q<4;q++){
				  while(w[4*i+q].length()!=32)
					  w[4*i+q]="0"+w[4*i+q];
			  }
			  for(int i1=0;i1<32;i1++) {
				    wn0[i1] = Integer.parseInt(String.valueOf(w[(4*i)].charAt(i1)));
				    wn1[i1] = Integer.parseInt(String.valueOf(w[4*i+1].charAt(i1)));
				    wn2[i1] = Integer.parseInt(String.valueOf(w[(4*i)+2].charAt(i1)));
				    wn3[i1] = Integer.parseInt(String.valueOf(w[(4*i)+3].charAt(i1)));

			  }
			// System.out.println("w3 is"+Arrays.toString(wn3));
			  String gf_op=g_func(wn3, i);
			  System.out.println("ip are "+gf_op+"-"+BinaryToHex(w[4*i]));
			  String r_key0=xor(gf_op,BinaryToHex(w[4*i]));
			  String r_key1=xor(r_key0,BinaryToHex(w[4*i+1]));
			  String r_key2=xor(r_key1,BinaryToHex(w[4*i+2]));
			  String r_key3=xor(r_key2,BinaryToHex(w[4*i+3]));
w[(4*i)+4]=hexToBinary(r_key0);
w[(4*i)+5]=hexToBinary(r_key1);
w[(4*i)+6]=hexToBinary(r_key2);
w[(4*i)+7]=hexToBinary(r_key3);
System.out.println(w[i+7]);

		  }
		  
	}
	public static String g_func(int[] wr_key,int r){
		int[] temp=new int[32];
		temp=wr_key;
		//shifing rows
	     for (int i = 0; i < 8; i++) {
	            int first = temp[0];
	            System.arraycopy( temp, 1, temp, 0, temp.length - 1);
	            temp[temp.length - 1] = first;
	        }
	      String tmp_string="";
for(int i=0;i<temp.length;i++)
	tmp_string+=temp[i];
	     System.out.println("in binary shifting rows "+tmp_string);
	     System.out.println("After Shifting rows "+BinaryToHex(tmp_string));
	     String hexstring=BinaryToHex(tmp_string);
	     //Substitute Byte
	     String[][] sub_box={ {"63","7c","77","7b","f2","6b","6f","c5","30","01","67","2b","fe","d7","ab","76"},
	    		 {"ca","82","c9","7d","fa","59","47","f0","ad","d4","a2","af","9c","a4","72","c0"},
	    		 {"b7","fd","93","26","36","3f","f7","cc","34","a5","e5","f1","71","d8","31","15"},
	    		 {"04","c7","23","c3","18","96","05","9a","07","12","80","e2","eb","27","b2","75"},
	    		 {"09","83","2c","1a","1b","6e","5a","a0","52","3b","d6","b3","29","e3","2f","84"},
	    		 {"53","d1","00","ed","20","fc","b1","5b","6a","cb","be","39","4a","4c","58","cf"},
	    		 {"d0","ef","aa","fb","43","4d","33","85","45","f9","02","7f","50","3c","9f","a8"},
	    		 {"51","a3","40","8f","92","9d","38","f5","bc","b6","da","21","10","ff","f3","d2"},
	    		 {"cd","0c","13","ec","5f","97","44","17","c4","a7","7e","3d","64","5d","19","73"},
	    		 {"60","81","4f","dc","22","2a","90","88","46","ee","b8","14","de","5e","0b","db"},
	    		 {"e0","32","3a","0a","49","06","24","5c","c2","d3","ac","62","91","95","e4","79"},
	    		 {"e7","c8","37","6d","8d","d5","4e","a9","6c","56","f4","ea","65","7a","ae","08"},
	    		 {"ba","78","25","2e","1c","a6","b4","c6","e8","dd","74","1f","4b","bd","8b","8a"},
	    		 {"70","3e","b5","66","48","03","f6","0e","61","35","57","b9","86","c1","1d","9e"},
	    		 {"e1","f8","98","11","69","d9","8e","94","9b","1e","87","e9","ce","55","28","df"},
	    		 {"8c","a1","89","0d","bf","e6","42","68","41","99","2d","0f","b0","54","bb","16"}};
	     	String sbox_output="";
	    	 sbox_output+=sub_box[hexToDec(hexstring.charAt(0))][hexToDec(hexstring.charAt(1))]+
	    	 sub_box[hexToDec(hexstring.charAt(2))][hexToDec(hexstring.charAt(3))]+
	    	 sub_box[hexToDec(hexstring.charAt(4))][hexToDec(hexstring.charAt(5))]+
	    	 sub_box[hexToDec(hexstring.charAt(6))][hexToDec(hexstring.charAt(7))];
	    	 System.out.println("After Sbox "+sbox_output);
	     	     
	     //xor
	     String R[]={"01000000","02000000","04000000","08000000","10000000","20000000","40000000","80000000","1B000000","36000000"};
	    System.out.println("End of function "+xor(sbox_output,R[r]));
	     String gf=xor(sbox_output,R[r]);
		return gf;
		
	}
	public static String hexToBinary(String hex) {
	    return new BigInteger(hex, 16).toString(2);
	}
	public static int hexToDec(char c) {
		String c1=Character.toString(c);
	    String x= new BigInteger(c1, 16).toString(10);
	    int temp=Integer.parseInt(x);
	    return temp;
	}
	public static String BinaryToHex(String binary) {
		String hexString="";
		int digitNumber = 1;
	    int sum = 0;
	    for(int i = 0; i < binary.length(); i++){
	        if(digitNumber == 1)
	            sum+=Integer.parseInt(binary.charAt(i) + "")*8;
	        else if(digitNumber == 2)
	            sum+=Integer.parseInt(binary.charAt(i) + "")*4;
	        else if(digitNumber == 3)
	            sum+=Integer.parseInt(binary.charAt(i) + "")*2;
	        else if(digitNumber == 4 || i < binary.length()+1){
	            sum+=Integer.parseInt(binary.charAt(i) + "")*1;
	            digitNumber = 0;
	            if(sum < 10)
	                //System.out.print(sum);
	            	hexString+=sum;
	            else if(sum == 10)
	               // System.out.print("A");
	            	hexString+="A";

	            else if(sum == 11)
	               // System.out.print("B");
	            	hexString+="B";

	            else if(sum == 12)
	               // System.out.print("C");
	            	hexString+="C";

	            else if(sum == 13)
	              //  System.out.print("D");
	            	hexString+="D";

	            else if(sum == 14)
	              //  System.out.print("E");
	            	hexString+="E";

	            else if(sum == 15)
	              //  System.out.print("F");
	            	hexString+="F";

	            sum=0;
	        }
	        digitNumber++;  
	    }
		  return hexString;
	}
	
	public static String xor(String one_ip,String two_ip){
		 BigInteger one = new BigInteger(one_ip, 16);
		    BigInteger two = new BigInteger(two_ip, 16);
		     
		     BigInteger three = one.xor(two);	
		     System.out.println("After xor "+three.toString(16));

			
		return three.toString(16);
		
	}

}
