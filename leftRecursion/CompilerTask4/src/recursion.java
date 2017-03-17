import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class recursion {

	private static final String filename = "D:/GUC intranet folders/Semester 10/AdvancedComputerLab/Task4_23267/Task4/sample.in";

	public static ArrayList<String> heads = new ArrayList<String>(); //non-terminals
	public static ArrayList<String [] > ruleArray =  new ArrayList<String []>();
	public static ArrayList<String> rules = new ArrayList<String> ();
	public static ArrayList<String> grammar = new ArrayList<String>();
	public static String [] parts;
	public static String [] splitted;
	public static ArrayList<String> terminals = new ArrayList<String>();
	public static ArrayList<String> newRules = new ArrayList<String>();
	
	
	
	
	
	
	public static boolean  Head(String s){
		for(int i=0; i<heads.size();i++){
			String currentchar = heads.get(i);
			if(s.contains(currentchar)){
				return true;
			}
			
		}
		return false;
	}
	
	
	public static String getHead(String s){
		String currentchar ="";
		for(int i=0; i<heads.size();i++){
			 currentchar = heads.get(i);
			if(s.contains(currentchar)){
				return currentchar;
			}
			
		}
		
		
		return currentchar;
	}
	
	//tested
	public static void getNonTerminals(){
	
		for(int i=0; i<ruleArray.size();i++){
			
			String [] current = ruleArray.get(i);
			for(int j=0; j<current.length;j++){
				String rule = current[j];
				if(Head(rule)){
					
					//then ha loop 3l string w a split by el head da
					for(int n=0; n<rule.length();n++){
						char c = rule.charAt(n);
						if(!Head(c+"")){
							terminals.add(c+"");
						}
					}
					
					//String splitvalue = getHead(rule);
					//splitted = rule.split(splitvalue);
					//for(int m=0; m<splitted.length;m++){
					//	nonterminals.add(splitted[m]);
					//}
					
					
				}else{
					terminals.add(rule);
				}
			}
		}
		
	}
	
	public static String [] substitute(String [] currentrule, String [] targetrule,String currenthead){
		 ArrayList<String> result = new  ArrayList<String>();
		
		for(int i=0; i<targetrule.length;i++){
			
			if(! targetrule[i].contains(currenthead)){
				result.add(targetrule[i]);
			}
			else{
				
				String temp = targetrule[i].replaceFirst(currenthead, "");
				for(int j=0; j<currentrule.length;j++){
					String current = currentrule[j] + temp;
					result.add(current);
				}
			}
			
		}
		String [] sub = new String [result.size()];
		for(int i=0;i<result.size();i++){
			sub[i] = result.get(i);
		}
		
		
		return sub;
	}
	
	
	public static void eliminateRecursion( ){
		
		boolean done = false;
		
		for(int i=0; i<heads.size();i++){
			//rule is:
			// head -->rule
			
			String targethead = heads.get(i);
			String [] targetrule = ruleArray.get(i);
			
			if(i==0){
				done =true;
				directRecursion(targethead, targetrule);
			}
			
			for(int j=0;j<=i-1;j++){
				String currenthead = heads.get(j);
				String [] currentrule = ruleArray.get(j);
				
				
				if(j== i){
					directRecursion(targethead,targetrule);
				
				}else{
					
					for(int k=0;k<targetrule.length;k++){
						if(targetrule[k].startsWith(currenthead)){
							//substitute fl targetrule
	//ha5od kol part fel currentrule a concatenate m3 el targetrule without its head
				//then replace el targetrule bel new rule fel arraylist
		     String [] result = substitute(currentrule,  targetrule, currenthead);

		     	ruleArray.set(i, result);
		     	done = true;
		      directRecursion(targethead,result);
						}
					}
				}
			
		    }
			if(done == false){
				directRecursion(targethead,targetrule);
			}
			done = false;
		}
		
		
		
		
	}
	
	//tested
	public static void directRecursion(String head, String [] rule){
		
		boolean found = false;
		
		ArrayList<String> prime = new ArrayList<String>();
		ArrayList<String> notprime = new ArrayList<String>();
		boolean primeflag= false;
		boolean notprimeflag = false;
		
		for(int j=0; j<rule.length;j++){
			String current = rule[j];
			if(current.contains(head)){
				found = true;
			}
		}
		
		if(found == true){
		
		String newRule="";
		 //the direct left recursion
		//divide it into two rules
				
		
		for(int i=0; i<rule.length;i++){
			
			String part = rule[i];
			if(! part.contains(head)){
				
				// newRule = head +" -> " +rule[i] + head+"'";
				//newRules.add(newRule);
				notprimeflag = true;
				if(! notprime.contains(part))
				notprime.add(part);
			}else{
				String result = part.replaceFirst(head, "");
				// newRule = head+"'" +"-> " +result+ head+"'" +" , "+ "!";
				//newRules.add(newRule);
				primeflag = true;
				prime.add(result+ head+"'");
			}
			
		
			
			
		}
		
		if(notprimeflag){
			newRule = head +" -> " +"[";
			for(int m=0;m<notprime.size();m++){
				newRule = newRule + notprime.get(m)+ head+"'" +" ,";
			}
			 newRule = newRule.substring(0, newRule.length()-1);
			
			newRule= newRule +"]";
			newRules.add(newRule);
		}
		if(primeflag){
			newRule = head+"'" +" -> " +"[";
			for(int m=0;m<prime.size();m++){
				newRule = newRule + prime.get(m) +" ,";
			}
			 newRule = newRule.substring(0, newRule.length()-1);
			newRule = newRule +", " +"!" +"]";
			newRules.add(newRule);
		}
		
		}else{
			String newRule = head +" -> " +"[";
			for(int i=0;i<rule.length;i++){
				newRule =  newRule + rule[i] +" ,";
			}
			 newRule = newRule.substring(0, newRule.length()-1);
			newRule = newRule+ "]";
			newRules.add(newRule);
		}
			
	}
	
	
	
	
	public static void main(String []args){
		BufferedReader br = null;
		FileReader fr = null;
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		File fileout = new File("Lab.out");
		
		int count =0;
		
		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			

			fw = new FileWriter(fileout);
			bw = new BufferedWriter(fw);
			
			String sCurrentLine;

		

			while ((sCurrentLine = br.readLine()) != null) {
				
				if(count%2 == 0){
					//even
					heads.add(sCurrentLine);
					
				}else{
					//odd
					rules.add(sCurrentLine);
					parts = sCurrentLine.split("\\|");
					ruleArray.add(parts);
					
				}
				count++;

			}
			
			for(int i=0;i<heads.size();i++){
				System.out.println("HEADS ARE: "+heads.get(i));
			}
			for(int j=0; j<rules.size();j++){
				System.out.println("RULES ARE: " + rules.get(j));
			}
			
			for(int k=0; k<ruleArray.size();k++){
				
				String [] current = ruleArray.get(k);
				for(int m=0; m<current.length;m++){
					System.out.print("Parts contain: " + current[m]);
				}
				System.out.println();
			}
			getNonTerminals();
			for(int n=0;n<terminals.size();n++){
				String current = terminals.get(n);
				System.out.println("terminals are: " + current);
			}
			
			//String head = "E";
			
			//String rule = "E+T";
			//String result = rule.replaceFirst(head, "");
			//System.out.println("Result is " + result);
			
			String head = "A";
			String [] line = new String[4];
			line[0] ="Ac";
			line[1] ="Aad";
			line[2] ="bd";
			line[3] ="c";
			//directRecursion(head, line);
			
			//for(int z=0; z<newRules.size();z++){
			//		System.out.println("new Rule is " + newRules.get(z));
			//	}
			
			//test1
			ArrayList<String> heads = new ArrayList<String>();
			heads.add("E");
			heads.add("T");
			heads.add("F");
			ArrayList<String []> rules = new ArrayList<String []>();
			String [] a1 = new String [2];
			a1[0]= "E+T";
			a1[1]="T";
			String [] a2 = new String [2];
			a2[0]= "T*F";
			a2[1]="F";
			String [] a3 = new String [3];
			a3[0]= "(E)";
			a3[1]="id";
			a3[2] ="number";
			
			rules.add(a1);
			rules.add(a2);
			rules.add(a3);
			
		//	eliminateRecursion(heads, rules);
			
			//test2
			ArrayList<String> heads2 = new ArrayList<String>();
			heads2.add("S");
			heads2.add("A");
			
			ArrayList<String []> rules2 = new ArrayList<String []>();
			String [] a11 = new String [2];
			a11[0]= "Aa";
			a11[1]="b";
			String [] a22 = new String [4];
			a22[0]= "Ac";
			a22[1]="Sd";
			a22[2]="bd";
			a22[3]="c";
			rules2.add(a11);
			rules2.add(a22);
			
			//eliminateRecursion(heads2, rules2);
			
			eliminateRecursion();
			
			for(int z=0; z<newRules.size();z++){
				System.out.println("new Rule is " + newRules.get(z));
				
				String content = newRules.get(z);

				bw.write(content);
				bw.newLine();
			}
			
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();
				
			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
		
	}
	
	
	
	
	
}
