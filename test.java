import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test {
        
    //thread pool size
    private static final int threadPoolSIze = 4;
    private static final String inputFileName = "words.txt";
    private static final String outputFileName = "memory.txt";

    //to check for words that start with A or a
    private static final String letterCheck = "^[Aa].*";

    public static void main(String[] args) throws IOException {
        //creating a thread pool using the fixed size from above
            ExecutorService executor = Executors.newFixedThreadPool(threadPoolSIze);
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //this check if the readLine matches the the requirement A or a
                if (line.matches(letterCheck)) {
                    //creates new task to write the line to the memory.txt file
                    executor.submit(new WriteToFileTask(line));
                }
            }
        }
        executor.shutdown();
    }
    //clas that write the word to new file
    private static class WriteToFileTask implements Runnable {
        //temp holder of the word
        private final String word;
            //write the file using the word given in the parameter
        public WriteToFileTask(String word) {
                        
                        this.word = word;
                        //simple print statement to show every time a word is written to new file

            System.out.println("Writing words to file......");
            System.out.println("\n");
        }

        @Override
        public void run() {
            //synchronize acces to memory.txt
            synchronized (test.class) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true))) {
                    //writes the word to the new output file 
                //int num = 1;
                  //  while(word!=null){
                   writer.write("Copied word: ");
                    writer.write(word);
                    writer.newLine();
                   // num++;
                   // }

                   
                } catch (IOException e) {
                    //handles errors
                    System.err.println("Error writing this word to file.....: " + word);
                }
            }
        }
    }
}
