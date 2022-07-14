//https://howtodoinjava.com/java/io/java-read-file-to-string-examples/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {

    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then read link upto next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int openBracket = markdown.indexOf("[", currentIndex);
            int closeBracket = markdown.indexOf("]", openBracket);
            int openParen = markdown.indexOf("(", closeBracket);
            int closeParen = markdown.indexOf(")", openParen);

            int codeBlockOpen;
            codeBlockOpen = markdown.indexOf("```", currentIndex);
            if(currentIndex != 0){
                codeBlockOpen = markdown.indexOf("\n```", currentIndex);
            }
            if(codeBlockOpen != -1) {
                int codeBlockClose = markdown.indexOf("\n```", codeBlockOpen);
                if(codeBlockClose !=-1){
                    currentIndex = codeBlockClose + 1;
                    continue;
                }
            }

            if(openBracket == -1 || closeBracket == -1
                  || closeParen == -1 || openParen == -1) {
                return toReturn;
            }

            if(openParen != closeBracket + 1){
                currentIndex = openParen + 1;
                continue;
            }

            if(openBracket != 0 && markdown.charAt(openBracket - 1) == '!'){
                currentIndex = closeParen + 1;
                continue;
            }

            toReturn.add(markdown.substring(openParen + 1, closeParen));
            currentIndex = closeParen + 1;
        }
        return toReturn;
    }


    public static void main(String[] args) throws IOException {
        //this is the main method of MarkdownParse
        Path fileName = Path.of(args[0]);
        String content = Files.readString(fileName);
        ArrayList<String> links = getLinks(content);
	    System.out.println(links);
    }
}
