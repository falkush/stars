//by falkush
package stars;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Principal {

 

    public static void main(String[] args) throws IOException {
    	int nbstars = 500000;
    	long starsize = 1000000000000000L;
    	long v=1;
    	long length = (long)Math.pow(2, 62);
    	
    	
    	int i,j,k,z;
    	int col;
    	int[] spos;
    	int thc;
    	int tmp;
    	int r,g,b;
    	int rgbval;
    	int start,end,starty,endy;
    	long d;
    	Color gray;
        long[][] stars = new long[nbstars][4];
        
        BufferedImage frame = new BufferedImage(1920,1080,BufferedImage.TYPE_INT_RGB);
        
        for(i=0;i<nbstars;i++)
        {
        	stars[i][0]=(long)(Math.random() * length); //z
        	stars[i][1]=(long)(Math.random() * length); //x
        	stars[i][2]=(long)(Math.random() * length); //y
        	stars[i][3]=(long)(Math.random() * 8);
        	if((int)(Math.random()*2)==0) stars[i][1]*=-1;
        	if((int)(Math.random()*2)==0) stars[i][2]*=-1;
        }
        Arrays.sort(stars, (a, c) -> Long.compare(a[0], c[0]));
        
        for(z=1;z<2000;z++)
        {
        	v=100000000000L*(long)Math.pow(z, 2);
	        //drawing
	        for(i=0;i<1920;i++)
	        {
	        	for(j=0;j<1080;j++)
	        	{
	        		frame.setRGB(i, j, Color.BLACK.getRGB()) ;
	        	}
	        }
	        
	        for(i=nbstars-1;i>=0;i--)
	        {
	        	spos=pos(stars[i]);
	        	d=distance(stars[i]);
	        	thc=thicc(d,starsize);
	        	if(stars[i][3]==0)
	        	{
	        		col=Math.max((int)((double)765-((double)765*(double)d/(double)length)),0);
	        		b=Math.min(255, col);
	        		g=Math.max(Math.min(255, col-255),0);
	        		r=Math.max(Math.min(100, col-(2*255)),0);
	        		gray=new Color(r,g,b);
		        	rgbval=gray.getRGB();
	        	}
	        	else
	        	{
	        		col=Math.max((int)((double)255-((double)255*(double)d/(double)length)),0);
	        		gray=new Color(col,col,col);
		        	rgbval=gray.getRGB();
	        	}

	        	
	        	
	        	
	        	start=Math.max(-960, spos[0]-thc+1); //resolution is hard-coded :(
	        	end=Math.min(959,spos[0]+thc-1);
	        	
	        	for(j=start;j<=end;j++) //change of variables should be done sooner for optimization
	        	{
	        		tmp=(int)Math.sqrt(Math.pow(thc,2)-Math.pow(j-spos[0],2));
	        		starty=Math.max(-540, spos[1]-tmp+1);
	        		endy=Math.min(539, spos[1]+tmp-1);
	        		for(k=starty;k<=endy;k++)
	        		{
	        			frame.setRGB(j+960, (-1)*(k-539), rgbval);
	        		}
	        	}
	        }
	        
	        //saving bmp
	        ImageIO.write(frame, "bmp", new File("D:/video/img"+String.format("%04d", z)+".bmp/"));
	      
	        //moving stars (should be done within the drawing loop)
	        j=0;
	        while(stars[j][0]-v<=0) {j++;} //stars to replace
	        
	        for(i=0;i<j;i++)
	        {
	        	stars[i][0]=length-1L-(long)(Math.random() * v);
	        	stars[i][1]=(long)(Math.random() * length);
	        	stars[i][2]=(long)(Math.random() * length);
	        	stars[i][3]=(long)(Math.random() * 8);
	        	if((int)(Math.random()*2)==0) stars[i][1]*=-1;
	        	if((int)(Math.random()*2)==0) stars[i][2]*=-1;
	        }
	        
	        for(i=j;i<nbstars;i++) stars[i][0]-=v;
	        
	        Arrays.sort(stars, (a, c) -> Long.compare(a[0], c[0]));
	        System.out.println(z);
        }
    }
    
    public static long distance(long[] star)
    {
    	return BigInteger.valueOf(star[0]).pow(2).add(BigInteger.valueOf(star[1]).pow(2)).add(BigInteger.valueOf(star[2]).pow(2)).sqrt().longValue();
    }

    public static int[] pos(long[] star)
    {
    	int[] ret = new int[2];
    	
    	ret[0]=(int)Math.floor(2880*Math.atan((double)star[1]/(double)star[0])/Math.PI);
    	ret[1]=(int)Math.floor(2880*Math.atan((double)star[2]/(double)star[0])/Math.PI);
    	
    	return ret;
    }
    
    public static int thicc(long dist, long size)
    {
    	double tmp;
    	
    	tmp=(double)2880*Math.atan((double)size/(double)dist)/Math.PI;
    	
    	return (int)Math.ceil(tmp);
    }
}