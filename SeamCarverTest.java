import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SeamCarverTest {
    // TODO: Provide accuracy tests on the methods marked with TODO && provide your own images to SeamCarve! (original + seamcarved)

    @Test
    public void SeamCarverTest1() {
        //This test will test for the removal of both vertical and horizontal seams
        //This produces Picture1test.bmp
        Picture picture1 = new Picture("Picture1.bmp");
        SeamCarver run = new SeamCarver(picture1);

        assertEquals(468, run.width());
        assertEquals(263, run.height());

        int[] hSeam = run.findHorizontalSeam();
        assertEquals(run.width(), hSeam.length);
        assertEquals("[31, 32, 32, 33, 33, 33, 33, 34, 35, 35, 35, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 35, 36, 37, 38, 39, 39, 40, 39, 39, 38, 38, 39, 40, 41, 41, 42, 42, 41, 41, 40, 40, 39, 40, 41, 40, 41, 42, 43, 44, 45, 45, 46, 45, 46, 47, 48, 49, 48, 48, 48, 48, 48, 48, 48, 49, 49, 48, 49, 48, 49, 48, 49, 48, 47, 46, 45, 44, 45, 46, 47, 48, 48, 48, 49, 50, 49, 48, 49, 48, 49, 48, 49, 48, 49, 48, 48, 47, 48, 48, 48, 47, 46, 45, 46, 47, 48, 49, 50, 50, 50, 51, 52, 51, 52, 51, 52, 51, 50, 50, 50, 50, 50, 50, 50, 50, 49, 48, 49, 48, 49, 48, 49, 49, 50, 49, 50, 49, 50, 51, 51, 52, 51, 50, 49, 50, 50, 50, 51, 51, 51, 50, 49, 50, 50, 49, 48, 48, 49, 50, 49, 50, 50, 50, 51, 52, 53, 54, 55, 55, 56, 56, 56, 56, 56, 56, 57, 57, 57, 57, 57, 56, 55, 56, 56, 56, 57, 56, 57, 56, 55, 56, 55, 56, 57, 56, 55, 55, 56, 57, 57, 57, 57, 57, 57, 57, 57, 56, 56, 56, 56, 57, 57, 57, 58, 59, 60, 61, 60, 61, 60, 61, 62, 61, 60, 61, 60, 61, 60, 59, 58, 57, 57, 57, 57, 57, 57, 57, 57, 56, 56, 56, 57, 58, 59, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 59, 58, 57, 56, 57, 57, 56, 55, 54, 53, 52, 52, 51, 50, 49, 48, 48, 47, 46, 45, 45, 44, 43, 43, 43, 44, 43, 43, 43, 42, 41, 41, 40, 39, 40, 40, 40, 41, 40, 41, 42, 42, 41, 41, 40, 40, 39, 38, 37, 38, 39, 38, 39, 40, 41, 41, 42, 42, 42, 42, 41, 41, 41, 41, 41, 40, 40, 41, 42, 41, 40, 41, 42, 42, 42, 42, 42, 43, 42, 42, 42, 41, 42, 41, 42, 42, 43, 44, 45, 46, 47, 48, 49, 49, 49, 48, 48, 49, 48, 47, 46, 47, 48, 49, 48, 49, 48, 49, 48, 49, 48, 49, 48, 49, 48, 49, 49, 49, 50, 50, 50, 49, 48, 48, 47, 46, 46, 47, 46, 45, 46, 47, 48, 47, 48, 47, 48, 47, 48, 47, 47, 48, 48, 49, 50, 50, 50, 49, 48, 49, 48, 49, 48, 49, 48, 47, 48, 47, 48, 47, 48, 49, 50, 50, 49, 49, 50, 49, 48, 48, 48, 48, 49, 49, 50, 49, 48, 49, 48, 47, 48, 47, 46, 45, 44, 45, 46, 47, 48, 47, 47, 48, 49, 49, 50, 50, 49, 48, 47, 46, 46, 46, 46, 46, 46, 46, 46, 46, 45]", Arrays.toString(hSeam));


        run.removeHorizontalSeam(hSeam);
        assertEquals(262, run.height());
        assertEquals(468, run.width());

        int[] vSeam = run.findVerticalSeam();
        assertEquals(run.height(), vSeam.length);
        assertEquals("[425, 426, 425, 424, 423, 422, 422, 423, 422, 422, 422, 421, 420, 419, 418, 417, 416, 415, 414, 415, 415, 414, 413, 412, 411, 410, 409, 408, 408, 409, 410, 411, 412, 411, 412, 412, 411, 410, 409, 408, 407, 406, 405, 404, 405, 406, 407, 406, 407, 406, 406, 407, 407, 408, 409, 409, 408, 407, 408, 407, 406, 405, 404, 405, 405, 404, 403, 402, 401, 402, 403, 402, 402, 402, 401, 400, 400, 399, 400, 401, 401, 402, 402, 403, 404, 404, 403, 402, 401, 401, 400, 400, 400, 401, 402, 402, 401, 400, 401, 400, 399, 399, 398, 397, 397, 397, 397, 398, 397, 396, 395, 394, 393, 392, 391, 392, 393, 394, 395, 395, 396, 396, 395, 396, 397, 398, 399, 400, 401, 402, 403, 404, 405, 406, 406, 405, 405, 406, 407, 408, 407, 408, 408, 407, 407, 408, 408, 409, 410, 411, 412, 413, 413, 412, 411, 411, 411, 411, 412, 413, 414, 415, 415, 416, 416, 417, 416, 415, 416, 415, 415, 416, 416, 416, 417, 417, 416, 415, 415, 414, 413, 414, 414, 413, 414, 415, 415, 416, 416, 415, 416, 416, 417, 417, 418, 419, 420, 419, 418, 417, 418, 419, 418, 419, 418, 419, 418, 417, 416, 415, 414, 415, 416, 416, 416, 415, 414, 414, 415, 416, 416, 417, 416, 415, 415, 416, 417, 416, 417, 416, 417, 418, 419, 418, 418, 417, 416, 415, 414, 415, 415, 414, 413, 412, 411, 411, 412, 412, 413, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 420, 419, 418]", Arrays.toString(vSeam));

        run.removeVerticalSeam(vSeam);
        assertEquals(262, run.height());
        assertEquals(467, run.width());

        run.picture().save("Picture1test.bmp");
    }

    @Test
    public void SeamCarverTest2() {
        //This test will test for the removal of many vertical and horizontal seams
        Picture picture1 = new Picture("Picture1.bmp");
        SeamCarver run = new SeamCarver(picture1);

        assertEquals(468, run.width());
        assertEquals(263, run.height());

        for (int i = 0; i<10; i++){
            int[] hSeam = run.findHorizontalSeam();
            run.removeHorizontalSeam(hSeam);
        }

        assertEquals(468, run.width());
        assertEquals(253, run.height());


        for (int i = 0; i<15; i++){
            int[] vSeam = run.findVerticalSeam();
            run.removeVerticalSeam(vSeam);
        }
        assertEquals(453, run.width());
        assertEquals(253, run.height());
    }

    @Test //This test produces the lotrtest.jpg that got submitted with this project
    public void removeHorizontals(){
        Picture picture1 = new Picture("lotr.jpg");
        SeamCarver run = new SeamCarver(picture1);

        assertEquals(800, run.width());
        assertEquals(278, run.height());

        for (int i = 0; i<10; i++){
            int[] hSeam = run.findHorizontalSeam();
            run.removeHorizontalSeam(hSeam);
        }

        assertEquals(800, run.width());
        assertEquals(268, run.height());

        run.picture().save("lotrtest.jpg");

    }

    @Test //This test produces the lotrtest2.jpg image that has been submitted
    public void removeVerticals(){
        Picture picture1 = new Picture("lotr.jpg");
        SeamCarver run = new SeamCarver(picture1);

        assertEquals(800, run.width());
        assertEquals(278, run.height());

        for (int i = 0; i<25; i++){
            int[] vSeam = run.findVerticalSeam();
            run.removeVerticalSeam(vSeam);
        }

        assertEquals(775, run.width());
        assertEquals(278, run.height());

        run.picture().save("lotrtest2.jpg");
    }

    @Test
    public void removeVerticalsSpeedTest(){
        Picture picture1 = new Picture("lotr.jpg");
        SeamCarver run = new SeamCarver(picture1);

        assertEquals(800, run.width());
        assertEquals(278, run.height());
        Instant start = Instant.now();
        for (int i = 0; i<25; i++){
            int[] vSeam = run.findVerticalSeam();
            run.removeVerticalSeam(vSeam);
        }
        Instant end = Instant.now();
        assertEquals(775, run.width());
        assertEquals(278, run.height());
        long time = Duration.between(start, end).toMillis();

        assertTrue(time<5000);

    }


    @Test
    public void removeHorizontalsSpeedTest(){
        Picture picture1 = new Picture("lotr.jpg");
        SeamCarver run = new SeamCarver(picture1);

        assertEquals(800, run.width());
        assertEquals(278, run.height());
        Instant start = Instant.now();
        for (int i = 0; i<15; i++){
            int[] hSeam = run.findHorizontalSeam();
            run.removeHorizontalSeam(hSeam);
        }
        Instant end = Instant.now();
        assertEquals(800, run.width());
        assertEquals(263, run.height());
        long time = Duration.between(start, end).toMillis();

        assertTrue(time<10000); //This image is really flat and wide, so it takes a while
    }

    @Test
    public void errorCatching(){
        Picture picture = new Picture("Picture1.bmp");
        SeamCarver seamCarver = new SeamCarver(picture);
        int[] seam = seamCarver.findHorizontalSeam();
        int height = seamCarver.height();
        seamCarver.removeHorizontalSeam(seam);
        assertEquals(height - 1, seamCarver.height());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            seamCarver.removeHorizontalSeam(null);
        });
        assertEquals(exception.getMessage(),"the argument to removeHorizontalSeam() is null\n");
        exception = assertThrows(IllegalArgumentException.class, () -> {
            seamCarver.removeHorizontalSeam(new int[]{2, 3, 4, 5});
        });
        assertEquals(exception.getMessage(),"the length of seam not equal width\n");
        exception = assertThrows(IllegalArgumentException.class, () -> {
            for(int i = 0; i < height; i++){
                seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
            }
        });
        assertEquals(exception.getMessage(),"the height of the picture is less than or equal to 1\n");
    }
}