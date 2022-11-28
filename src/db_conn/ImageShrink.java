/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_conn;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author Kunaal
 */
public class ImageShrink {

    public static void main(String[] args) {
        new Db_Conn().loadFeedPostData();

        for (int i = 0; i < Db_Conn.user_post_url.size(); i++) {
            try {

                File input = new File(Db_Conn.user_post_url.get(i).getUser_post_url());
                System.out.println(Db_Conn.user_post_url.get(i).getUser_post_url());
                BufferedImage image = ImageIO.read(input);

                File compressedImageFile = new File("D:/supportdata/post/compress/" + Db_Conn.user_post_url.get(i).getUser_post_url().substring(20));
                ImageWriter writer;
                ImageOutputStream ios;
                try (OutputStream os = new FileOutputStream(compressedImageFile)) {
                    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
                    writer = (ImageWriter) writers.next();
                    ios = ImageIO.createImageOutputStream(os);
                    writer.setOutput(ios);
                    ImageWriteParam param = writer.getDefaultWriteParam();
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(0.01f);  // Change the quality value you prefer
                    writer.write(null, new IIOImage(image, null, null), param);
                }
                ios.close();
                writer.dispose();
            } catch (IOException ex) {
                Logger.getLogger(ImageShrink.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
