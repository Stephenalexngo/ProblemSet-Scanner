import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

class TokenIdentifier{

    // STATES
    private static final int q0 = 0;
    private static final int q1 = 1;
    private static final int q2 = 2;
    private static final int q3 = 3;
    private static final int q4 = 4;
    private static final int q5 = 5;
    private static final int q6 = 6;
    private static final int q7 = 7;
    private static final int q8 = 8;
    private static final int q9 = 9;
    private static final int q10 = 10;
    private static final int q11 = 11;
    private static final int q12 = 12;
    private static final int q13 = 13;
    private static final int q14 = 14;
    private static final int q15 = 15;
    private static final int q16 = 16;
    private static final int q17 = 17;
    private static final int garb = -1;

    private int state;

    // ALL STATES TRANSITIONS
    static private int checker(int currstate, char character) {
        switch (currstate) {
            case q0: 
                switch (character) {
                    case '$': return q1;
                    case 'F': return q2;
                    case 'R': return q2;
                    case 'D': return q7;
                    default: return garb;
            }
            case q1: 
                switch (character) {
                    case 'F': return q2;
                    case 'R': return q2;
                    case '0': return q4;
                    case '1': return q5;
                    case '2': return q5;
                    case '3': return q3;
                    case '4': return q4;
                    case '5': return q4;
                    case '6': return q4;
                    case '7': return q4;
                    case '8': return q4;
                    case '9': return q4;
                    default: return garb;
            }
            case q2: 
                switch (character) {
                    case '0': return q4;
                    case '1': return q5;
                    case '2': return q5;
                    case '3': return q3;
                    case '4': return q4;
                    case '5': return q4;
                    case '6': return q4;
                    case '7': return q4;
                    case '8': return q4;
                    case '9': return q4;
                    default: return garb;
            }
            case q3: 
                switch (character) {
                    case '0': return q4;
                    case '1': return q4;
                    default: return garb;
            }
            case q5: 
                switch (character) {
                    case '0': return q6;
                    case '1': return q6;
                    case '2': return q6;
                    case '3': return q6;
                    case '4': return q6;
                    case '5': return q6;
                    case '6': return q6;
                    case '7': return q6;
                    case '8': return q6;
                    case '9': return q6;
                    default: return garb;
            }
            case q7: 
                switch (character) {
                    case 'A': return q13;
                    case 'M': return q8;
                    default: return garb;
            }
            case q8: 
                switch (character) {
                    case 'U': return q9;
                    default: return garb;
            }
            case q9: 
                switch (character) {
                    case 'L': return q10;
                    default: return garb;
            }
            case q10: 
                switch (character) {
                    case 'T': return q11;
                    default: return garb;
            }
            case q11: 
                switch (character) {
                    case 'U': return q12;
                    default: return garb;
            }
            case q13: 
                switch (character) {
                    case 'D': return q14;
                    default: return garb;
            }
            case q14: 
                switch (character) {
                    case 'D': return q15;
                    default: return garb;
            }
            case q15: 
                switch (character) {
                    case 'I': return q16;
                    case 'U': return q17;
                    default: return garb;
            }
            case q16: 
                switch (character) {
                    case 'U': return q17;
                    default: return garb;
            }
            default: 
                return garb;
        }
    }

    // GET EVERY CHARACTER IN THE WORD TOKEN THEN CHECK
    public void processToken(String word) {
        for (int x = 0; x < word.length(); x++) {
            char character = word.charAt(x);
            state = checker(state, character);
        }
    }

    // CHECKER IF THE STATE IS AT THE FINAL STATE
    public boolean isAccepted(){
        return (state == q3 || state == q4 || state == q5 || state == q6 || state == q11 || state == q12 || state == q17);
    }

    // RESET THE CURRENT STATE TO THE START STATE
    public void resetState(){
        state = q0;
    }
}

class Token{
    enum Tokentype{
        KEYWORD,
        ERROR,
        GPR,
        FPR
    }

    public String lexeme;
    public Tokentype tokentype;

    public Token(String word){
        this.lexeme = word;
        this.tokentype = identype(word);
    }

    public static Tokentype identype(String word){
        if(word.length() < 2 || word.length() > 6)
            return Token.Tokentype.ERROR;
        else{
            TokenIdentifier tokeniden = new TokenIdentifier();
            tokeniden.resetState();
            tokeniden.processToken(word);

            if(tokeniden.isAccepted()){
                if(word.charAt(0) == 'D')
                    return Token.Tokentype.KEYWORD;
                else if(word.charAt(0) == 'F' || word.charAt(1) == 'F')
                    return Token.Tokentype.FPR;
                else
                    return Token.Tokentype.GPR;
            }
            else
                return Token.Tokentype.ERROR;
        }  
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File("inputfile.txt");
        FileReader fr = new FileReader(file);
        FileWriter fw = new FileWriter("output.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;

        while( (line = br.readLine()) != null){
            String[] arr = line.replace(",", "").split(" ");
            int curr = 0;
            
            for (String word : arr){
                if(!word.equals("")){
                    Token tk = new Token(word);
                    
                    if(curr == 0)
                        fw.append(tk.tokentype.toString());
                    else
                        fw.append(" " + tk.tokentype.toString());
                }
                curr++;
            }

            fw.append("\n");
        }

        fr.close();
        fw.close();

        System.out.println("Successful Tokenization");
    }
}
