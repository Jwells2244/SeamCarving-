import java.util.ArrayList;
import java.util.Arrays;

public class SeamCarver {
    private int width;
    private int height;
    private Picture pictureCopy;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("argument to SeamCarver() is null\n");
        }

        pictureCopy = new Picture(picture);
        width = picture.width();
        height = picture.height();
    }

    // current picture
    public Picture picture() {
        return new Picture(pictureCopy);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validateColumnIndex(x);
        validateRowIndex(y);

        // border pixels
        if (x == 0 || x == width -1 || y == 0 || y == height -1) {
            return 1000;
        }

        int up, down, left, right;
        up = pictureCopy.getRGB(x, y - 1);
        down = pictureCopy.getRGB(x, y + 1);
        left = pictureCopy.getRGB(x - 1, y);
        right = pictureCopy.getRGB(x + 1, y);
        double gradientY = gradient(up, down);
        double gradientX = gradient(left, right);

        return Math.sqrt(gradientX + gradientY);
    }

    private double gradient(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xFF;
        int g1 = (rgb1 >>  8) & 0xFF;
        int b1 = (rgb1 >>  0) & 0xFF;
        int r2 = (rgb2 >> 16) & 0xFF;
        int g2 = (rgb2 >>  8) & 0xFF;
        int b2 = (rgb2 >>  0) & 0xFF;

        return Math.pow(r1 - r2, 2) + Math.pow(g1 - g2, 2)
                + Math.pow(b1 - b2, 2);
    }


    // sequence of indices for Vertical seam
    public int[] findHorizontalSeam() {
        double[][] energy = new double[width][height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                energy[col][row] = energy(col, row);
            }
        }
        /*energy = new double[][]{{23.39, 67.35, 80.56, 12.51}, //TODO this was a non dynamic solution that did not factor in
                                                                    the idea that the shortest path could be something that didnt
                                                                    start on the lowest value.
                            {85.19, 58.58, 91.75, 28.1},
                            {74.5, 85.8, 10.2, 43.56},
                            {13.1, 16.7, 19.5, 25.0},
                            {98.2, 45.76, 67.2, 14.5}};*/
        /*energy = new double[][] {
                {67.3, 98.4, 53.2, 130.1},
                {73.8, 22.9, 126.4, 74.6},
                {47.5, 34.1, 115.6, 28.7},
                {84.9, 132.5, 91.2, 43.8},
                {129.6, 68.2, 97.3, 21.5}
        };*/

        /*int[] hSeam = new int[width];
        int startingIndex = 0;
        for (int i = 0; i < height - 1; i++){
            if (i == 0){
                for (int x = 0; x < width; x++){
                    if (energy[i][x] < energy[i][startingIndex]){
                        startingIndex = x;
                    }
                }
                hSeam[i] = startingIndex;
            }
            else{
                double[] options = new double[3];
                if (startingIndex != 0){ //Setting int[0], the startingIndex-1, the top right check
                    options[0] = energy[startingIndex-1][i];
                }
                else{
                    options[0] = Double.MAX_VALUE;
                }
                options[1] = energy[startingIndex][i]; //Setting option 2, the easy check, the straight across
                if (startingIndex != width){ //Setting int[0], the startingIndex+1, the bottom right check
                    options[2] = energy[startingIndex+1][i];
                }
                else{
                    options[2] = Double.MAX_VALUE;
                }
                System.out.println(Arrays.toString(options));

                //Now that options is set, we find the lowest val
                int addIndex = findLowestValueInDoubleArray(options);
                if (addIndex == 0){
                    addIndex = startingIndex-1;
                }
                else if (addIndex == 1){
                    addIndex = startingIndex;
                }
                else if (addIndex == 2){
                    addIndex = startingIndex+1;
                }
                startingIndex = addIndex;
                hSeam[i] = addIndex;
            }
        }*/
        int rows = energy.length;
        int cols = energy[0].length;
        double[][] cost = new double[rows][cols];
        ArrayList<Integer>[][] paths = new ArrayList[rows][cols];
        for(int i = rows - 1; i >= 0; i--){
            for(int j = 0; j < cols; j++){
                paths[i][j] = new ArrayList<>();
            }
        }

        for(int i = 0; i < cols; i++){
            cost[rows - 1][i] = energy[rows - 1][i];
            paths[rows - 1][i].add(i);
        }
        for (int i = rows - 2; i >= 0; i--) {
            for (int j = 0; j < cols; j++) {
                double left, right;
                if (j > 0) {
                    left = cost[i+1][j-1];
                } else {
                    left = Double.MAX_VALUE;
                }
                if (j < cols - 1) {
                    right = cost[i+1][j+1];
                } else {
                    right = Double.MAX_VALUE;
                }
                double down = cost[i+1][j];
                double min = Math.min(Math.min(left, right), down);
                int index;
                if(min == left){
                    index = j - 1;
                }
                else if(min == right){
                    index = j + 1;
                }
                else{
                    index = j;
                }
                cost[i][j] = energy[i][j] + min;
                paths[i][j].addAll(paths[i+1][index]);
                paths[i][j].add(0, j);
            }
        }
        int shortestIndex = 0;
        for (int j = 1; j < cols; j++) {
            if(cost[0][j] < cost[0][shortestIndex]){
                shortestIndex = j;
            }
        }
        return paths[0][shortestIndex].stream().mapToInt(i -> i).toArray();
    }


    public int findLowestValueInDoubleArray(double[] arr){
        if (arr.length == 0){return Integer.MAX_VALUE;}//This is not supposed to happen
        int lowestIndex = 0;
        for (int i = 0; i < arr.length; i++){
            if (arr[lowestIndex]>arr[i] && arr[i] != Double.MAX_VALUE){
                lowestIndex = i;
            }
        }
        return lowestIndex;
    }

    // sequence of indices for Horizontal seam
    public int[] findVerticalSeam() {
        double[][] energy = new double[width][height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                energy[col][row] = energy(col, row);
            }
        }
        int rows = energy.length;
        int cols = energy[0].length;
        double[][] cost = new double[rows][cols];
        ArrayList<Integer>[][] paths = new ArrayList[rows][cols];
        for(int i = rows - 1; i >= 0; i--){
            for(int j = 0; j < cols; j++){
                paths[i][j] = new ArrayList<>();
            }
        }
        for(int i = 0 ; i < rows; i++){
            cost[i][cols - 1] = energy[i][cols - 1];
            paths[i][cols - 1].add(i);
        }
        for (int i = cols - 2; i >= 0; i--) {
            for (int j = 0; j < rows; j++) {
                double up, down;
                if (j > 0) {
                    up = cost[j-1][i+1];
                } else {
                    up = Double.MAX_VALUE;
                }
                if (j < rows - 1) {
                    down = cost[j+1][i+1];
                } else {
                    down = Double.MAX_VALUE;
                }
                double left = cost[j][i+1];
                double min = Math.min(Math.min(down, up), left);
                int index;
                if(min == up){
                    index = j - 1;
                }
                else if(min == down){
                    index = j + 1;
                }
                else{
                    index = j;
                }
                cost[j][i] = energy[j][i] + min;
                paths[j][i].addAll(paths[index][i+1]);
                paths[j][i].add(0, j);
            }
        }
        int shortestIndex = 0;
        for (int i = 1; i < rows; i++) {
            if(cost[i][0] < cost[shortestIndex][0]){
                shortestIndex = i;
            }
        }

        return paths[shortestIndex][0].stream().mapToInt(i -> i).toArray();
    }


    // remove Vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("the argument to removeVerticalSeam() is null\n");
        }
        if (seam.length != height) {
            throw new IllegalArgumentException("the length of seam not equal height\n");
        }
        validateSeam(seam);
        if (width <= 1) {
            throw new IllegalArgumentException("the width of the picture is less than or equal to 1\n");
        }

        Picture tmpPicture = new Picture(width - 1, height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width - 1; col++) {
                validateColumnIndex(seam[row]);
                if (col < seam[row]) {
                    tmpPicture.setRGB(col, row, pictureCopy.getRGB(col, row));
                } else {
                    tmpPicture.setRGB(col, row, pictureCopy.getRGB(col + 1, row));
                }
            }
        }
        pictureCopy = tmpPicture;
        width--;
    }

    // remove Horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("the argument to removeHorizontalSeam() is null\n");
        }
        if (seam.length != width) {
            throw new IllegalArgumentException("the length of seam not equal width\n");
        }
        validateSeam(seam);
        if (height <= 1) {
            throw new IllegalArgumentException("the height of the picture is less than or equal to 1\n");
        }

        Picture tmpPicture = new Picture(width, height - 1);
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height - 1; row++) {
                validateRowIndex(seam[col]);
                if (row < seam[col]) {
                    tmpPicture.setRGB(col, row, pictureCopy.getRGB(col, row));
                } else {
                    tmpPicture.setRGB(col, row, pictureCopy.getRGB(col, row + 1));
                }
            }
        }
        pictureCopy = tmpPicture;
        height--;
    }

    // transpose the current pictureCopy
    private void transpose() {
        Picture tmpPicture = new Picture(height, width);
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                tmpPicture.setRGB(col, row, pictureCopy.getRGB(row, col));
            }
        }
        pictureCopy = tmpPicture;
        int tmp = height;
        height = width;
        width = tmp;
    }

    // make sure column index is bewteen 0 and width - 1
    private void validateColumnIndex(int col) {
        if (col < 0 || col > width -1) {
            throw new IllegalArgumentException("colmun index is outside its prescribed range\n");
        }
    }

    // make sure row index is bewteen 0 and height - 1
    private void validateRowIndex(int row) {
        if (row < 0 || row > height -1) {
            throw new IllegalArgumentException("row index is outside its prescribed range\n");
        }
    }

    // make sure two adjacent entries differ within 1
    private void validateSeam(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException("two adjacent entries differ by more than 1 in seam\n");
            }
        }
    }
}