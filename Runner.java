import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.io.*;

public class Runner {

    public static void main(String[] args) throws IOException{
        // TODO: write the runner so that it follows the same format as the directions
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter input image: ");
        String inputFileName = scanner.nextLine();


        Picture inputPicture = new Picture(inputFileName);
        SeamCarver run = new SeamCarver(inputPicture);

        System.out.println("How many vertical seams to remove: ");
        int vSeams = scanner.nextInt();

        System.out.println("How many horizontal seams to remove: ");
        int hSeams = scanner.nextInt();
        Instant start = Instant.now();
        for (int i = 0; i < vSeams; i++){
            int[] vSeam = run.findVerticalSeam();
            run.removeVerticalSeam(vSeam);
        }
        for (int i = 0; i < hSeams; i++){
            int[] hSeam = run.findHorizontalSeam();
            run.removeHorizontalSeam(hSeam);
        }
        Instant end = Instant.now();
        long time = Duration.between(start, end).toMillis();
        System.out.print("Enter output image: ");
        String outputFile = scanner.next();

        run.picture().save(outputFile);
        scanner.close();

        System.out.println("Image saved in " + time + " ms...");
    }
}
