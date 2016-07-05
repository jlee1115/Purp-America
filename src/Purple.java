import javax.swing.JApplet;

import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import java.io.*;
		public class Purple extends JApplet{
		
			public static Region[] regions;
			public static double scalefactor;
			public static double nxmax, nymax;
			public static double xmin, ymin, xmax, ymax;
			public static double N=5;
			public static ArrayList<voteMon> vinae;//new ArrayList <voteMon>();
			
			
			public void init(){
				Scanner scanner = new Scanner(System.in);
				vinae=new ArrayList <voteMon>();
				System.out.println("Casually enter you v fav region mon"); 
				String filename = scanner.next();
				System.out.println("Frazzily enter you v fav yr mon");
				String colorname= scanner.next();
				scanner.close();
				readColors("purple/" + filename + colorname + ".txt" );
				readData("purple/" + filename+ ".txt");
				repaint();
				
			}
			
			public class Region{
				String name;
				ArrayList<Double> xCoordinates = new ArrayList<Double>();
				ArrayList<Double> yCoordinates = new ArrayList<Double>();
				Polygon shape;
				
				public Region(String n)
				{
					name=n;
				}
				public Polygon getShape()
				{
					return shape;
				}
				public String getName()
				{
					return name;
				}
				public void addxPoints(double xcoordinate){
					xCoordinates.add(xcoordinate);
					
				}
				public void addyPoints(double ycoordinate){
					yCoordinates.add(ycoordinate);
				}
				
				
				public void makePolygon(){
					int y= getHeight();
					int x= getWidth();
					int[] xArray = new int [xCoordinates.size()];
					int[] yArray = new int [yCoordinates.size()];
					double nxmax=Math.abs((xmax-xmin));
					double nymax=Math.abs((ymax-ymin));
					double negx=-1*xmin*1.07;
					double negy= -1*ymin*2;
					if(nxmax>=nymax){
						scalefactor=x/nxmax;
						
					}
					else{
						scalefactor=y/nymax;
					}
					for (int i=0; i<xCoordinates.size(); i++)
					{
						xArray[i] = (int) (getWidth()/2-scalefactor*nxmax/2+scalefactor*(xCoordinates.get(i)+negx)*N);
						yArray[i] = (int) (getHeight()/2+scalefactor*nymax/2-scalefactor*(yCoordinates.get(i)+negy)*N);
						
					}
					
					shape = new Polygon(xArray, yArray, xArray.length);
					
				}
			}
			
			public class voteMon{
				String n;
				int red1 = 1;
				int blue1 = 1;
				int green1 = 1;
				 
				public voteMon(String name)
				{
					n=name;
				}
				public void addRed(int red){
					red1=red;
				}
				public void addBlue(int blue){
					blue1=blue;
				}
				public void addGreen(int green){
					green1=green;
				}
				public int getRed()
				{
					return red1;
				}
				public int getBlue()
				{
					return blue1;
				}
				public int getGreen()
				{
					return green1;
				}
				public String getName()
				{
				    return n;
				}
				public Color getColor()
				{
					return new Color((int)(255.0*red1 /(red1 + blue1 + green1)), (int)(255.0*green1 /(red1 + blue1 + green1)), (int)(255.0*blue1 /(red1 + blue1 + green1)));
					 //if(red1>blue1 && red1> green1){
				    //return new Color((int)(255.0*red1 /(red1 + blue1 + green1)), 0, 0);  }
				    //if(blue1>red1 && blue1> green1){
				     //return new Color(0, 0, (int)(255.0*blue1 /(red1 + blue1 + green1))); }
				   // else{
				   // return new Color(0, (int)(255.0*blue1 /(red1 + blue1 + green1)), 0);}
				}
			}
			
			public void readColors(String fileName){
				int currentregion = 0; 
				int counter = 0; 
		
				try{
					 Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));

					 sc.useDelimiter(",");
					 sc.next();
					 sc.next();
					 sc.next();
					 sc.next();
					 while (sc.hasNextLine())
					 {
						 if (!sc.hasNextInt())
					     {
					         	vinae.add(new voteMon (sc.next()));
							    counter++; 
							    
					     }
						 else{
						   
						     if(counter%4 == 1){
						       //vote.addRed(sc.next());
						    	 vinae.get(currentregion).addRed(sc.nextInt());
						         counter++;
						     }
						    if(counter%4==2){
						        //vote.addBlue(sc.next());
						    	 vinae.get(currentregion).addBlue(sc.nextInt());
						        counter++;
						    }
						    if(counter%4==3){
						       //vote.addGreen(sc.next());
						    	vinae.get(currentregion).addGreen(sc.nextInt());
						        counter++;
						        currentregion++; 
						    }
					     }
					 }
					 sc.close();
					 
					 
						
				}
				catch(FileNotFoundException ex) {
			          System.out.println(
			              "Unable to open file '" + 
			              fileName + "'");                
			      }
			}
			
			public void readData(String fileName){
				int currentregion = 0;
				int counter = 0;
				  try {
					  Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
					    xmin=sc.nextDouble();
					    ymin=sc.nextDouble();
						xmax=sc.nextDouble();
						ymax=sc.nextDouble();
						regions = new Region[sc.nextInt()];
						sc.nextLine();
						sc.nextLine();
						regions[0] = new Region(sc.nextLine());
						sc.nextLine();
						sc.nextLine();
				
						while(sc.hasNext()){
							if(sc.hasNextInt())sc.next();
							else if (sc.hasNextDouble()){
								if(counter%2 ==0){
									regions[currentregion].addxPoints(sc.nextDouble());
									counter++;
								}
								else{
									regions[currentregion].addyPoints(sc.nextDouble());
									counter++;
											
								}
							}
							else{
								sc.nextLine();
								sc.nextLine();
								regions[currentregion].makePolygon();
								currentregion++;
								regions[currentregion] = new Region(sc.nextLine());
								sc.nextLine();
								sc.nextLine();
								
							}
							
						}
						regions[currentregion].makePolygon();
						sc.close();
			          }
			
				 catch(FileNotFoundException ex) {
			          System.out.println(
			              "Unable to open file '" + 
			              fileName + "'");                
			      }
			}
			
			public void paint (Graphics g)
			{
			    Graphics2D g2 = (Graphics2D) g; 
			    Color c=null;
		        for (int i = 0; i<regions.length; i++) {
		        	for (int j=0;j<vinae.size();j++)
			        {
		        		 String s = vinae.get(j).getName();
				        if (regions[i].getName().equals(s) || ("\n"+regions[i].getName()).equals(s))
			            {
			            	c=(vinae.get(j)).getColor();
			            } 
			        }
					g2.drawPolygon(regions[i].getShape());
					g2.setColor(c);
					g2.fillPolygon(regions[i].getShape());
		        	
		            
		        }
		        
				
			}
			 
		
		}
