import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
                    default: return garb;
            } // not fix below
            case q1: 
                switch (character) {
                    case '0': return q2;
                    case '1': return q0;
                    default: return q3;
            }
            case q2: 
                switch (character) {
                    case '0': return q1;
                    case '1': return q2;
                    default: return q3;
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
        BufferedReader br = new BufferedReader(fr);
        String line;

        while( (line = br.readLine()) != null){
            String[] arr = line.replace(",", "").split(" ");
            int curr = 0;

            for (String word : arr){
                if(!word.equals("")){
                    Token tk = new Token(word);
                    // FILE OUTPUT DAPAT TESTING
                    if(curr == 0)
                        System.out.print(tk.tokentype);
                    else
                        System.out.print(" " + tk.tokentype);
                }
                curr++;
            }

            System.out.println("");
        }

        fr.close();
    }
}
