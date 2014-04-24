
package project_final2;
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
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
/**
 *
 * @author tingchen
 */
public class flickrfinal {
	int x;
	int y;
	int update;
	String kw;
	public flickrfinal(int a){
		if (a == 1){//sad
    		x = 20;
    		y = 20;
    		update = 120;
    		 kw ="sad";
    	}
    	else if (a == 2){//clam
    		x = 30;
    		y =	30;
    		update = 60;
    		 kw = "clam";
    	}
    	else if ( a == 3){//happy
    		x = 40;
    		y = 40;
    		update = 40;
    		 kw = "happy";
    	}
	}
	public flickrfinal(){
		
	}
    ShakingFrame s;
	JFrame frame;
 // public void flicker() throws ParserConfigurationException, IOException, SAXException, FlickrException{
	synchronized public void testimg(String kw1) throws ParserConfigurationException, IOException, SAXException, FlickrException, InterruptedException {
//public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, FlickrException, InterruptedException{
	String key="4f06a5f35477e070c74d0fda2c6d417f";
    String svr="www.flickr.com";
    REST rest=new REST();

   
   
    rest.setHost(svr);
   
    //initialize Flickr object with key and rest
    Flickr flickr=new Flickr(key,rest);
    Flickr.debugStream=false;

    //initialize SearchParameter object, this object stores the search keyword
    SearchParameters searchParams=new SearchParameters();
    searchParams.setSort(SearchParameters.INTERESTINGNESS_ASC);
   
    //Create tag keyword array
    String[] tags=new String[]{kw};
    System.out.println(kw);
    searchParams.setTags(tags);

    //Initialize PhotosInterface object
    PhotosInterface photosInterface=flickr.getPhotosInterface();
    //Execute search with entered tags
    PhotoList photoList=photosInterface.search(searchParams,10,7);
    String imageURL = "";
    //get search result and fetch the photo object and get small square imag's url
    if(photoList!=null){
       //Get search result and check the size of photo result
    
  	 //the following loop extracts images from flickr and then save to local files
    for(int i=0;i<photoList.size();i++){
 
          Photo photo=(Photo)photoList.get(i);
         imageURL = photo.getMediumUrl().toString();
          /*StringBuffer strBuf=new StringBuffer();
          strBuf.append("<a href=\"\">");
          strBuf.append("<img border=\"0\" src=\""+photo.getUrl()+"\">");
          strBuf.append("</a>\n");
          System.out.println("picuture URL: " + strBuf.toString());*/
          
    try{  	

    	 URL url = new URL(imageURL);
    	 InputStream in = new BufferedInputStream(url.openStream());
    	 ByteArrayOutputStream out = new ByteArrayOutputStream();
    	 byte[] buf = new byte[1024];
    	 int n = 0;
    	 while (-1!=(n=in.read(buf)))
    	 {
    	    out.write(buf, 0, n);
    	 }
    	 out.close();
    	 in.close();
    	 byte[] response = out.toByteArray();
    	 FileOutputStream fos = new FileOutputStream("Image" + i + ".jpg");
    	 fos.write(response);
    	 fos.close();
    }
    catch(IOException e){}
    
    }
    

}  //end if
    
    frame = new JFrame();
    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	//frame.setBounds(10,10,1000,1000);
  	frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
  	 JPanel mainPanel = new JPanel(new BorderLayout());
  	 mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
  	 
   
  	 for(int j=0;j<18;j++){
  	 
    	BufferedImage image = null;
    	try {
    		
    	    image = ImageIO.read(new File("Image" + j + ".jpg"));
    	    System.out.println("I load image" + j);
    	} 
        catch (IOException e) {
    	}

    	    	
    	    	JLabel lblimage = new JLabel(new ImageIcon(image));
    	        
    	    	//frame.getContentPane().add(lblimage,BorderLayout.CENTER);
    	    	mainPanel.add(BorderLayout.CENTER,lblimage);
    	    	frame.getContentPane().add(mainPanel);
    	    	frame.pack();
    	        s = new ShakingFrame(frame);
    	     	s.startShake(update, x, y);
    	       // mainPanel.updateUI();
    	        Thread.sleep(1000);
    	    	mainPanel.remove(lblimage);
    	    	
    	
    }//end display for loop
         
         s.stopShake2();
}//end method


        }


