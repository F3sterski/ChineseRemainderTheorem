package crt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class crt {
	
	static long[][] uklad;
	static int index;
	static long Gamma;
	static long Beta;
	
    public static long NWD(long a, long b){
        if (b == 0) return a;  
        else return NWD(b, a%b);
    }
    public static long modInverse(long a, long m) {
        a %= m;
        for(long x = 1; x < m; x++) {
            if((a*x) % m == 1) return x;
        }
		return -1;
    }
	
	private static void ReadUklad(){
        try {
    		
            FileReader fileReader = new FileReader("uklad.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int i=0;
            String line;
            
            uklad = new long[2000][2];
            line = bufferedReader.readLine();
            do{
     
	            Matcher matcher = Pattern.compile("\\d+").matcher(line);
	            matcher.find();
	            uklad[i][0] = Integer.valueOf(matcher.group());	            
	            matcher = Pattern.compile("\\s\\d+").matcher(line);
	            matcher.find();
	            uklad[i][1] = Integer.valueOf(matcher.group().substring(1,matcher.group().length()));
	            i++;
	            line = bufferedReader.readLine();
            }while(line!=null);               
            bufferedReader.close();
            index=i-1;
            
    	}
	    catch(FileNotFoundException ex) {
	        System.err.println("Unable to open file 'uklad.txt'");   
	        ex.printStackTrace();
	        System.exit(0);
	    }
	    catch(IOException ex) {
	        System.err.println("Error reading file 'uklad.txt'");  
	        ex.printStackTrace();
	        System.exit(0);
	    }
	    catch(NullPointerException ex){
	    	ex.printStackTrace();
	    	System.exit(0);
	    }
        
        for(int i=0;i<=index;i++){
        	for(int j=0;j<=index;j++){
        		if(NWD(uklad[i][1],uklad[j][1])!=1 && i!=j){
        	        System.err.println("Niepoprawny uk³ad");  
        	        System.exit(0);
        		}
        	}
        }
        
	}
	
	private static void CountGammaBeta(long x, long y){
		Gamma=1;
		Beta=1;
		if(NWD(x,y)==1){
			Gamma=modInverse(x,y);
		}
		while(x*Gamma+y*Beta!=1){
			Beta--;
		}
	} 
	
	public static long countCrt(){
		long x=0;
		for(int i=1;i<=index;i++){
			long m1=uklad[i-1][1];
			long m2=uklad[i][1];
			long a1=uklad[i-1][0];
			long a2=uklad[i][0];
			CountGammaBeta(m1,m2);
			x=(a2*Gamma*m1+a1*Beta*m2)%(m1*m2);
			if(x<0){
				x+=(m1*m2);
			}
			uklad[i][0] = x ;
			uklad[i][1] = m1*m2 ;			
		}
		
		try {
			FileWriter fileCryptoW = new FileWriter("crt.txt");
			fileCryptoW.write(uklad[index][0]+" "+uklad[index][1]);
			fileCryptoW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	

		return x;
	}

	public static void main(String[] args) {
		ReadUklad();
		countCrt();
	}

}
