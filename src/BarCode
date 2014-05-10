import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
 
 
public class BarCode {
        private ArrayList<Data> tokens;
       
        public ArrayList<Data> getTokens() {
			return tokens;
		}

		public void setTokens(ArrayList<Data> tokens) {
			this.tokens = tokens;
		}

		BarCode(File input) throws FileNotFoundException{
                tokens = new ArrayList<Data>();
                loadFromFile(input);
                System.out.println("the data already in the list");
        }
       
        public void loadFromFile(File inputFile) throws FileNotFoundException{
                if(inputFile.canRead()){
                        Scanner reader = new Scanner(inputFile);
                        while(reader.hasNext()){
                                Data temp = new Data();
                                temp.setGeneratedNumber(Integer.parseInt(reader.nextLine()));
                                temp.setPackageData(reader.nextLine());
                                temp.setDataSent(reader.nextLine());
                                temp.setRecievedData(reader.nextLine());
                                temp.setIDNumber(Integer.parseInt(reader.nextLine()));
                                temp.setChoice(reader.nextLine());
                                tokens.add(temp);
                        }
                        System.out.println(1);
                        reader.close();
                }
                else{
                        System.out.println(0);
                        tokens = null;
                }
        }
        public int FindWithGeneratedCode(int number){
                int i = 0;
                while(i < tokens.size() ){
                        if(tokens.get(i).getGeneratedNumber() == number){
                               return i;
                        }
                        i++;
                }
                return -1;
        }
        
        public void addToList(Data temp){
    		tokens.add(temp);
    	}
}
