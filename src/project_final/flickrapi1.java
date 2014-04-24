/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project_final;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tingchen
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import project_final.ShakingFrame;
import project_final.BlinkingLabel;
import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.photos.SearchParameters;
import com.aetrion.flickr.photos.PhotoList;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.photos.Photo;

import java.awt.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.sound.midi.*;

import java.util.*;
import java.awt.event.*;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
/**
 *
 * @author tingchen
 */

public class flickrapi1 {
    
    String globalkeyword;
    
    public flickrapi1() {

    }
    String getkw(String kw){
        globalkeyword=kw;
        return kw;
    }
    
    public void testimg(String kw)
            throws ParserConfigurationException, IOException, SAXException, FlickrException {
        String key = "4f06a5f35477e070c74d0fda2c6d417f";
        String svr = "www.flickr.com";
        REST rest = new REST();

        rest.setHost(svr);

        //initialize Flickr object with key and rest
        Flickr flickr = new Flickr(key, rest);
        Flickr.debugStream = false;

        //initialize SearchParameter object, this object stores the search keyword
        SearchParameters searchParams = new SearchParameters();
        searchParams.setSort(SearchParameters.INTERESTINGNESS_ASC);

    //Create tag keyword array
        String[] tags = new String[]{kw};
        searchParams.setTags(tags);

        //Initialize PhotosInterface object
        PhotosInterface photosInterface = flickr.getPhotosInterface();
        //Execute search with entered tags
        PhotoList photoList = photosInterface.search(searchParams, 100, 7);
        String imageURL = "";
        //get search result and fetch the photo object and get small square imag's url
        if (photoList != null) {
            //Get search result and check the size of photo result
            final JFrame frame = new JFrame();   
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            //frame.setBounds(10,10,1000,1000);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel(new BorderLayout());
            
            
            for (int i = 0; i < photoList.size(); i++) {

                //get photo object
                ActionListener listener = new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        frame.dispose();

                    }
                };
                Timer timer = new Timer(5000, listener);
                // timer.setInitialDelay(0);
                Photo photo = (Photo) photoList.get(i);

                /*URL url= new URL(photo.getSmallSquareUrl().toString());
                 URLConnection conn = url.openConnection();
                 InputStream in = conn.getInputStream();*/
                imageURL = photo.getMediumUrl().toString();
    // Case 1

                //Get small square url photo
                StringBuffer strBuf = new StringBuffer();
                strBuf.append("<a href=\"\">");
                strBuf.append("<img border=\"0\" src=\"" + photo.getUrl() + "\">");
                strBuf.append("</a>\n");
                System.out.println("picuture URL: " + strBuf.toString());

                Image image = null;
                try {
                    URL url = new URL(imageURL);
                    image = ImageIO.read(url);

                } catch (IOException e) {
                }
                if (image != null) {
                    JLabel lblimage = new JLabel(new ImageIcon(image));

                    frame.getContentPane().add(lblimage, BorderLayout.CENTER);
                    frame.setSize(500, 600);
                    frame.setVisible(true);
                    ShakingFrame s = new ShakingFrame(frame);
                    /*BlinkingLabel blink = new BlinkingLabel(lblimage,frame);
                     blink.setBlink(true);
                     blink.run();*/
                    s.startShake();
                    timer.start();
    	// s.stopShake();
               }
            }
        }
    }

    public static void main(String[] args){
        PICUI test = new PICUI();
        test.setVisible(true);
       // testimg(test.keyword1, test.keyword2, test.keyword3);
    }

}
