import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
public class CircleDetector {

    public static void main(String[] args){
        BufferedImage img = null;

        try {
            File file = new File("src/"+args[0]);
            System.out.println("File exists:"+file.exists());

            img = ImageIO.read(file);
            System.out.println("Image read");
        }
        catch(IOException e) {
            System.err.println(e);
        }

        ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp colorConvert = new ColorConvertOp(colorSpace, null);
        img = colorConvert.filter(img, null);

        BufferedImage blurredImage = gaussianBlur(img);
        BufferedImage gradientImage = sobelIntensityGradient(blurredImage);


        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(gradientImage)));
        frame.pack();
        frame.setVisible(true);


    }


    public static BufferedImage gaussianBlur(BufferedImage img){
        float[] gaussianBlurMatrix = {1/16f ,1/8f,1/16f,1/8f,1/4f,1/8f,1/16f,1/8f,1/16f};
        BufferedImageOp blurImageOperator = new ConvolveOp(new Kernel(3,3,gaussianBlurMatrix));
        BufferedImage blurredImage = null;
        blurredImage = blurImageOperator.filter(img,blurredImage);
        return blurredImage;

        /* int width = img.getWidth();
        int height = img.getHeight();

        System.out.println("Width:"+width);
        System.out.println("Height:"+height);

        int[][] grayImageBytes = new int[height][width];


        for(int i = 0; i< height; i++){
            for(int j=0; j< width; j++) {
                int red = (img.getRGB(j, i) >> 16) & 0xFF;
                int green = (img.getRGB(j, i) >> 8) & 0xFF;
                int blue = img.getRGB(j, i) & 0xFF;

                int intensity = (int) (0.2989 * red + 0.5870 * green + 0.1140 * blue);

                grayImageBytes[i][j] = intensity;
            }
        }


        for(int i = 0; i< height; i++) {
            for (int j = 0; j < width; j++) {


            }
        }*/
    }

    public static int rgbToIntensity(int rgb){
        int blue = rgb & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int red = (rgb >> 16) & 0xFF;

        int intensity = (int) (0.2989 * red + 0.5870 * green + 0.1140 * blue);
        return intensity;

    }


    public static BufferedImage sobelIntensityGradient(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage gradientImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        for(int y=1; y < height-1;y++){
            for(int x=1; x< width -1 ;x++){

                int p1 = rgbToIntensity(img.getRGB(x-1,y-1));
                int p2 = rgbToIntensity(img.getRGB(x,y-1));
                int p3 = rgbToIntensity(img.getRGB(x+1,y+1));
                int p4 = rgbToIntensity(img.getRGB(x-1,y));
                int p6 = rgbToIntensity(img.getRGB(x+1,y));
                int p7 = rgbToIntensity(img.getRGB(x-1,y+1));
                int p8 = rgbToIntensity(img.getRGB(x,y+1));
                int p9 = rgbToIntensity(img.getRGB(x+1,y+1));

                int G = (int)Math.sqrt(Math.pow(-p1+p3-(2*p4)+(2*p6)-p7+p8 ,2)+Math.pow(p1+(2*p2)+p3-p7-(2*p8)-p9,2));
                gradientImage.setRGB(x, y, G);

            }
        }
        return gradientImage;
    }

    public static BufferedImage edgeHystersis(BufferedImage img){

        int width = img.getWidth();
        int height = img.getHeight();

//https://dadruid5.wordpress.com/2014/08/20/canny-edge-detection-in-java/
        //http://homepages.inf.ed.ac.uk/rbf/HIPR2/sobel.htm


        return null;

    }


    }
