/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.utils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

/**
 *
 * @author Usss
 */
public class MyPageEvent extends PdfPageEventHelper  {
	  protected Phrase header = new Phrase("header");
	 
	  protected ResourceBundleManager bundleManager = new ResourceBundleManager();
	  protected String leyenda=null;
	  protected String dir;
	  protected int numHojasFolleto;

	  protected PdfPTable footer = new PdfPTable(4);

	  public MyPageEvent( String dir , int numHojasFolleto  ) {
	    footer.setTotalWidth(300);
	    this.dir=dir;
	    this.numHojasFolleto=numHojasFolleto;
	    /*
	    footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    footer.addCell(new Phrase(new Chunk("First").setAction(new PdfAction(PdfAction.FIRSTPAGE))));
	    footer.addCell(new Phrase(new Chunk("Prev").setAction(new PdfAction(PdfAction.PREVPAGE))));
	    footer.addCell(new Phrase(new Chunk("Next").setAction(new PdfAction(PdfAction.NEXTPAGE))));
	    footer.addCell(new Phrase(new Chunk("Last").setAction(new PdfAction(PdfAction.LASTPAGE))));
	    pdfDocInfo.setNumPages(0);
	    leyenda = bundleManager.getValue("LEYENDA_PDF"); */
	     
	  }

	   
	  public void onStartPage(PdfWriter writer, Document document){
		  
		  try{
	      	  
		 // drawWaterMark(writer,document);
			  if( this.numHojasFolleto>1 ){
			     drawHeader(writer,document);
			  }
		  
		  }catch(Exception e){
			  e.printStackTrace();
		  } 
	  }
	 
	  
	  public void drawHeader(PdfWriter writer, Document document)
		        throws DocumentException {
		  
		  PdfContentByte cb = writer.getDirectContent();
		 		 		  
		  cb.saveState();
	      PdfGState state = new PdfGState();
	      
	      
	        cb.setGState(state);
	        /*
	        cb.setRGBColorFill(0xFF, 0xFF, 0xFF);
	        cb.setLineWidth(0.5f);
	     
	        cb.rectangle(30, 745+10 , 535 , 78);
	        
	        cb.moveTo(390, 745+26);
	        cb.lineTo(390+175, 745 + 26 );
	        
	        cb.moveTo(468-27  , 745+10);
	        cb.lineTo(468-27 , 745 + 32 +10);
	        
	       // cb.rectangle(415, 745+10 , 150 , 32);
	        cb.rectangle(390, 745+10 , 175 , 32);
	        cb.fillStroke();
	        */
	        cb.restoreState(); 
	       // String file=dir + "/resources/images/ENCABEZADO1.jpg";
	        String file=dir + "/resources/images/";
	        if( writer.getCurrentPageNumber()>=3 ){
	        	file+="header_3.jpg";
	        }else{
	        	file+="header_"+ writer.getCurrentPageNumber()+".jpg";
	        }
	        
	        
	        
	        System.out.println("file header :"+ file );
	        
	        try{
	           //Image image = Image.getInstance( "c:/jmpa/folleto_header.jpg" );
	        	 Image image = Image.getInstance( file );
	           
	           image.setAbsolutePosition(0f, 712f);  
	            
	          
	  		                        	                   
	            image.scaleAbsolute(612 , 80);
               cb.addImage(image);
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
		  
	        
	      //Clasificacion
		  
      }
		        
		    
 
	  
	  public void onEndPage(PdfWriter writer, Document document) {
		 if( this.numHojasFolleto>1 ){
		    putFooter(writer,document);
		 }  
			  if( writer.getCurrentPageNumber()==1 ){
				//putPortada(writer,document, pdfDocInfo.getNombreDoc() ); 
			  }
			 
	
		  
		 
	  }
	  
	  
	 

	 
	  public void putFooter(PdfWriter writer, Document document ){
		  
	       // String file=dir + "/resources/images/PIE1.jpg";
		  
	        String file=dir + "/resources/images/";
	        if( writer.getCurrentPageNumber()>=4 ){
	        	file+="footer_4.jpg";
	        }else{
	        	file+="footer_"+ writer.getCurrentPageNumber()+".jpg";
	        }		  
		  
	        System.out.println("file header :"+ file );
	        
	        try{
	        	PdfContentByte cb = writer.getDirectContent();
	           //Image image = Image.getInstance( "c:/jmpa/folleto_header.jpg" );
	        	 Image image = Image.getInstance( file );
	           
	           image.setAbsolutePosition(0f, 1f);  
	            
	          
	  		                        	                   
	            image.scaleAbsolute(612 , 80);
             cb.addImage(image);
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        
		  
	  }
	  
	 
	  
	  public void drawWaterMark(PdfWriter writer , Document document){
		  try{		  
			 
		    Image image = Image.getInstance( "c:/jmpa/logo_famsa3.png" );
            image.setAbsolutePosition(135f, 390f);  
            
            PdfContentByte cb = writer.getDirectContentUnder();
  		                        	                   
            image.scaleAbsolute(400 , 200);
            document.add(image);          
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	  }
	  
	  
	 
	  
	 
     

	  
	}
