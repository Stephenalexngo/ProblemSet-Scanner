import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File("inputfile.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while( (line = br.readLine()) != null){
            String[] arr = line.replace(",", "").split(" ");
        
            for (String word : arr){
                if(!word.equals("")){
                    
                }
            }
        }

        fr.close();
    }
}
