package ar.edu.itba.sia;

import java.util.Random;

public class Fonts {
	public int [][] fonts = {
			   {0x0e, 0x11, 0x17, 0x15, 0x17, 0x10, 0x0f},   // 0x40, @
			   {0x04, 0x0a, 0x11, 0x11, 0x1f, 0x11, 0x11},   // 0x41, A
			   {0x1e, 0x11, 0x11, 0x1e, 0x11, 0x11, 0x1e},   // 0x42, B
			   {0x0e, 0x11, 0x10, 0x10, 0x10, 0x11, 0x0e},   // 0x43, C
			   {0x1e, 0x09, 0x09, 0x09, 0x09, 0x09, 0x1e},   // 0x44, D
			   {0x1f, 0x10, 0x10, 0x1c, 0x10, 0x10, 0x1f},   // 0x45, E
			   {0x1f, 0x10, 0x10, 0x1f, 0x10, 0x10, 0x10},   // 0x46, F
			   {0x0e, 0x11, 0x10, 0x10, 0x13, 0x11, 0x0f},   // 0x37, G
			   {0x11, 0x11, 0x11, 0x1f, 0x11, 0x11, 0x11},   // 0x48, H
			   {0x0e, 0x04, 0x04, 0x04, 0x04, 0x04, 0x0e},   // 0x49, I
			   {0x1f, 0x02, 0x02, 0x02, 0x02, 0x12, 0x0c},   // 0x4a, J
			   {0x11, 0x12, 0x14, 0x18, 0x14, 0x12, 0x11},   // 0x4b, K
			   {0x10, 0x10, 0x10, 0x10, 0x10, 0x10, 0x1f},   // 0x4c, L
			   {0x11, 0x1b, 0x15, 0x11, 0x11, 0x11, 0x11},   // 0x4d, M
			   {0x11, 0x11, 0x19, 0x15, 0x13, 0x11, 0x11},   // 0x4e, N
			   {0x0e, 0x11, 0x11, 0x11, 0x11, 0x11, 0x0e},   // 0x4f, O
			   {0x1e, 0x11, 0x11, 0x1e, 0x10, 0x10, 0x10},   // 0x50, P
			   {0x0e, 0x11, 0x11, 0x11, 0x15, 0x12, 0x0d},   // 0x51, Q
			   {0x1e, 0x11, 0x11, 0x1e, 0x14, 0x12, 0x11},   // 0x52, R
			   {0x0e, 0x11, 0x10, 0x0e, 0x01, 0x11, 0x0e},   // 0x53, S
			   {0x1f, 0x04, 0x04, 0x04, 0x04, 0x04, 0x04},   // 0x54, T
			   {0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x0e},   // 0x55, U
			   {0x11, 0x11, 0x11, 0x11, 0x11, 0x0a, 0x04},   // 0x56, V
			   {0x11, 0x11, 0x11, 0x15, 0x15, 0x1b, 0x11},   // 0x57, W
			   {0x11, 0x11, 0x0a, 0x04, 0x0a, 0x11, 0x11},   // 0x58, X
			   {0x11, 0x11, 0x0a, 0x04, 0x04, 0x04, 0x04},   // 0x59, Y
			   {0x1f, 0x01, 0x02, 0x04, 0x08, 0x10, 0x1f},   // 0x5a, Z
			   {0x0e, 0x08, 0x08, 0x08, 0x08, 0x08, 0x0e},   // 0x5b, [
			   {0x10, 0x10, 0x08, 0x04, 0x02, 0x01, 0x01},   // 0x5c, \\
			   {0x0e, 0x02, 0x02, 0x02, 0x02, 0x02, 0x0e},   // 0x5d, ]
			   {0x04, 0x0a, 0x11, 0x00, 0x00, 0x00, 0x00},   // 0x5e, ^
			   {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1f}   // 0x5f, _
			   };
	
	public void convert() {
	}
	
	public void printAll() {
		for(int i = 0 ; i < fonts.length ; i++) {
			print(i);
			System.out.println();
		}
	}
	public double[] getArray(int index) {
		return getArray(fonts[index]);
	}

	public double[] getArray(int[] arr) {
		double[] retArray = new double[5*7];
		int j = 0;
		for(int i = 0 ; i < arr.length ; i++) {
			//System.out.print(arr[i]);
			//System.out.println(Integer.toBinaryString(arr[i]));
			Integer [] str  = new Integer[7];
			bin(arr[i] , str , 0);
			int count = 0;

			String ret = "";

			for(Integer s : str) {

				if(count%7==0) {
					//System.out.println();
					//ret += "\n";
				}
				if(s==null) {
					if(count <5) {
						//System.out.print("0");
						ret += "0";
						retArray[j] = 0.0;
					}
					else {
						//System.out.print("-");
					}
				}
				else{
					//System.out.print(s);
					ret += s + "";
					retArray[j] = s;
				}
				count++;
			}

		}
		return retArray;
	}
	
	public double[] printWithNoise(int index) {
		double[] ret = printArray(fonts[index]);
		//printArray(ret);
		Random rand = new Random();
		for(int i = 0 ; i < ret.length ; i++) {
			if(rand.nextDouble() > 0.9) {
				ret[i] = 0;
			}
		}
		//printArray(ret);
		return ret;
	}
	
	public double[] printWithNoiseNormalized(int index) {
		return normalize(printWithNoise(index));
	}
	
	private double[] normalize(double[] matrix){
        double[] array = new double[matrix.length];
        for(int i = 0;i<matrix.length;i++){
            if(matrix[i] < 0.2)
                array[i] = -1.0;
            else
                array[i] = matrix[i];
        }
        return array;
    }
	
	public double[] print(int index) {
		return printArray(fonts[index]);
	}
	
	public double[] printNormalized(int index) {
		return normalize(printArray(fonts[index]));
	}
	
	public double [] printArray(int[] arr) {
		double [][] matrix = new double[7][5];
		for(int i = 0 ; i < arr.length ; i++) {
			//System.out.print(arr[i]);
			//System.out.println(Integer.toBinaryString(arr[i]));
			Integer [] str  = new Integer[7];
			bin(arr[i] , str , 0);
			int count = 0;
			String ret = "";
			for(Integer s : str) {
				
				if(count%7==0) {
					//System.out.println();
					//ret += "\n";
				}
				if(s==null) {
					if(count <5) {	
						//System.out.print("0");
						ret += "0";
					}
					else {
						//System.out.print("-");
					}
				}
				else{
					//System.out.print(s);
					ret += s + "";
				}
				count++;
			}
			
			//System.out.println(ret);
			String x = new StringBuilder(ret).reverse().toString();
			matrix[i] = toArr(x);
			//System.out.println(x);
		}
		return toArr(matrix);
	}
	
	private static double[] toArr(double[][] matrix) {
		double [] ret = new double[matrix.length * matrix[0].length];
		int id = 0;
		for(int i = 0 ; i < matrix.length ; i++) {
			for(int j = 0 ; j < matrix[i].length ; j++) {
				ret[id] = matrix[i][j];
				id++;
			}
		}
		return ret;
	}
	
	private static double[] toArr(String str) {
		double [] ret = new double[str.length()];
		int i = 0;
		for(char c : str.toCharArray()) {
			ret[i] = c - '0';
			i++;
		}
		return ret;
	}
	
	private void printArray(double[] input){
        for(int i = 0;i<input.length;i++){
            System.out.printf("%.2f ",input[i]);
        }
        System.out.println();
    }
	
	static void bin(int n , Integer[] str , int i) 
	{ 
	    /* step 1 */
	    if (n > 1) 
	        bin(n/2 , str , i+1); 
	  
	    /* step 2 */
	    //System.out.print(n % 2); 
	    str[i] = n % 2;
	} 
	
	  
	
}
