/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas.objects;

import com.crossmark.collector.business.domain.Encuestas;
import com.crossmark.collector.business.domain.Opciones;
import com.crossmark.collector.business.domain.Preguntas;
import com.crossmark.collector.business.domain.Proyectos;
import com.crossmark.collector.business.domain.Secciones;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author jdominguez
 */
public class ProyectoPdf {
    private static final Font BASE_FONT = new Font(Font.FontFamily.COURIER, 10, Font.NORMAL, BaseColor.BLACK);
    private static final Font BASE_FONTBOLD = new Font(Font.FontFamily.COURIER, 10, Font.BOLD);
    private static final Font smallBold = new Font(Font.FontFamily.COURIER, 10,Font.BOLD);
    private static final String dir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");
    
    private static final int RRED_RECURSIVA = 0;
    private static final int RGREEN_RECURSIVA = 204;
    private static final int RBLACK_RECURSIVA = 0;

    public static void createPdf(Proyectos proyecto) throws DocumentException, FileNotFoundException, IOException {
        File pdfFile = new File(dir+"/preview/VistaPreviaProyectos.pdf");
        if(pdfFile.exists()){
            pdfFile.delete();
        }
        Document doc = new Document();
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(pdfFile));
        
//        TableHeader event = new TableHeader();
//        writer.setPageEvent(event);
        doc.setPageSize(PageSize.LETTER);
        doc.open();
        addHeader(proyecto, doc);
        addEncuestas(proyecto, doc, writer);
        doc.close();
    }
    
    private static void addEncuestas(Proyectos proyecto, Document doc, PdfWriter writer) throws DocumentException, IOException{
        Paragraph salto = new Paragraph();
        addEmptyLine(salto, 1);
        doc.add(salto);
        DottedLineSeparator dottedLine = new DottedLineSeparator();
        LineSeparator basicLine = new LineSeparator();
      //  doc.add(basicLine);
        List<Encuestas> encuestas = proyecto.getListaEncuestasProyecto() == null ? new ArrayList<Encuestas>() : proyecto.getListaEncuestasProyecto();
        if(encuestas.isEmpty()){
            doc.add(new Paragraph("Este proyecto no tiene encuestas"));
        }else{
            int contador = 1;
            for(Encuestas enc : encuestas){
                if(contador++ > 1){
                    doc.newPage();
                }
                doc.add(basicLine);
                doc.add(new Paragraph("Encuesta - " + enc.getNombreEncuesta(), BASE_FONTBOLD));
                doc.add(salto);
                doc.add(dottedLine);
                List<Secciones> secs = enc.getListaSecciones();
                if(secs.isEmpty()){
                    doc.add(new Paragraph("Esta encuesta no tiene secciones", BASE_FONT));
                    doc.add(salto);
                }else{
                    for(Secciones s : secs){
//                        if(s.isActiva()){
                            addSeccion(s, doc, writer);
                            doc.add(salto);
                            doc.add(dottedLine);
//                        }
                    }
                }
            }
        }
        
    }
    
    private static void addSeccion(Secciones s, Document doc, PdfWriter writer) throws DocumentException, IOException{
        doc.add(new Paragraph(s.getNombreSeccion(),BASE_FONTBOLD));

        if(s.isCiclica()){
            String tipo = "Pregunta";
            if(s.isControlUsuario()){
                tipo = "Control de usuario";
            } 
            PlaceChunck("Recursiva: "+tipo, 250, (int) writer.getVerticalPosition(true), 9, writer, RRED_RECURSIVA, RGREEN_RECURSIVA, RBLACK_RECURSIVA);
            PlaceChunck(s.getPreguntaDominante(), 250, ((int) writer.getVerticalPosition(true)) - 9, 9, writer, RRED_RECURSIVA, RGREEN_RECURSIVA, RBLACK_RECURSIVA);
        }
        
        List<Preguntas> preguntas = s.getMiListaPreguntas();
        Paragraph salto = new Paragraph();
        addEmptyLine(salto, 1);
        doc.add(salto);
        if(preguntas == null || preguntas.isEmpty()){
            doc.add(new Paragraph("Esta seccion no tiene preguntas configuradas", BASE_FONT));
        }else{
            for(Preguntas p : preguntas){
                addPreguntas(p, doc, writer);               
            }
        }
        
    }
    
    private static void addPreguntas(Preguntas p, Document doc, PdfWriter writer) throws DocumentException, IOException{
        Paragraph salto = new Paragraph();
        Paragraph saltoPregunta = new Paragraph();
        addEmptyLine(salto, 1);
        addEmptyLine(saltoPregunta, 2);
        List<Opciones> opciones = (List<Opciones>) (p.getMisOpciones() == null ? new ArrayList<>() : p.getMisOpciones());
        doc.add(new Paragraph(p.getOrden() + ") " + p.getDescripcionPregunta(), BASE_FONT));
        if(p.getMensajesPregunta() != null){
            PlaceChunck(p.getMensajesPregunta(), 400, (int) writer.getVerticalPosition(true), 9, writer, 0, 0, 0);
        }
        if(!opciones.isEmpty()){
            for(Opciones op : opciones){
                String car;
                if(p.getIdTipoPregunta() == 6){
                    car = "O";
                }else if(p.getIdTipoPregunta() == 7){
                    car = "[]";
                }else{
                    car = "*";
                }
                doc.add(salto);
                String cadena = car + " " + op.getTextoOpcion();
                PlaceChunck(cadena, 50,((int) writer.getVerticalPosition(true)), 10, writer, 0 ,0 ,0);
                if(op.isCondicionada()){
                    StringBuilder sb = new StringBuilder();
                    sb.append(op.getTipoAccion()).append(": ");
                    sb.append(op.getSeccionAccion() == null ? "" : op.getSeccionAccion());
                    sb.append("-").append(op.getPreguntaAccion() == null ? "" : op.getPreguntaAccion());
                    PlaceChunck(sb.toString(), 
                            175, 
                            ((int) writer.getVerticalPosition(true))-5, 
                            8, writer, RRED_RECURSIVA, RGREEN_RECURSIVA, RBLACK_RECURSIVA);
                }
            }
            doc.add(salto);
        }else{
            doc.add(saltoPregunta);
        }
    }
    
    private static void addHeader(Proyectos proyecto, Document doc) throws DocumentException{
    /*    Paragraph tit = new Paragraph(proyecto.getNombreProyecto(),catFont);
        tit.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        doc.add(tit); */
        Paragraph salto = new Paragraph();
        doc.add(addLogo());
        addEmptyLine(salto, 1);
        LineSeparator line = new LineSeparator();
        doc.add(salto);
        doc.add(line);
        doc.add(tableDatosProyectos(proyecto));
    }
    
    private static PdfPTable tableDatosProyectos(Proyectos proyecto) throws DocumentException{
        // ******** Prepara la tabla de los datos del proyecto ******** //
        PdfPTable datos = new PdfPTable(2);
        datos.setWidthPercentage(100);
        datos.setSpacingBefore(20f);
        datos.setHorizontalAlignment(Element.ALIGN_LEFT);
        float [] columnWidths = {30,70};
        datos.setWidths(columnWidths);
        
        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        Phrase column;
        Phrase phrase;
        for(int i = 0; i < 7; i++){
            switch(i){
                case 0: column = new Phrase("Cliente", BASE_FONT);
                        phrase = new Phrase(proyecto.getNombreCliente(), smallBold);
                        break;
                case 1: column = new Phrase("Clave Proyecto", BASE_FONT);
                        phrase = new Phrase(proyecto.getClaveProyecto(), smallBold);
                        break;
                case 2: column = new Phrase("Nombre Proyecto", BASE_FONT);
                        phrase = new Phrase(proyecto.getNombreProyecto(), smallBold);
                        break;
                case 3: column = new Phrase("Descripcion Proyecto", BASE_FONT);
                        phrase = new Phrase(proyecto.getDescripcionProyecto(), smallBold);
                        break;
                case 4: column = new Phrase("Fecha visible", BASE_FONT);
                        phrase = new Phrase(sfd.format(proyecto.getFechaVisible()), smallBold);
                        break;
                case 5: column = new Phrase("Fecha inicio vigencia", BASE_FONT);
                        phrase = new Phrase(sfd.format(proyecto.getFechaInicio()), smallBold);
                        break;
                case 6: column = new Phrase("Fecha fin vigencia", BASE_FONT);
                        phrase = new Phrase(sfd.format(proyecto.getFechaFin()), smallBold);
                        break;
                default:column = new Phrase("");
                        phrase = new Phrase("");
                        break;
            }
            
            PdfPCell cell = new PdfPCell(column);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(0);
            datos.addCell(cell);

            cell = new PdfPCell(phrase);
            cell.setBackgroundColor(BaseColor.WHITE);
            cell.setBorder(0);
            datos.addCell(cell);
        }
        
        return datos;
    }
    
    private static Image addLogo(){
        Image logo = null;
        try {
            logo = Image.getInstance(dir + "/images/logopdf.jpg");
        /*    Dimension imgSize = new Dimension((int)logo.getWidth(),(int)logo.getHeight());
            Dimension boundary = new Dimension(500,500);
            Dimension image = getScaledDimension(imgSize, boundary); */

            logo.setAbsolutePosition(430, 740);
//            logo.scaleAbsolute(image.height,image.width);
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(ProyectoPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logo;
    }

    private static void PlaceChunck(String text, int x, int y, int fontSize, PdfWriter writer, int r, int g, int b) throws DocumentException, IOException {
      PdfContentByte cb = writer.getDirectContent();
      BaseFont bf = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
      cb.saveState();
      cb.beginText();
      cb.moveText(x, y);
      cb.setRGBColorFill(r, g, b);
      cb.setFontAndSize(bf, fontSize);
      cb.showText(text);
      cb.endText();
      cb.restoreState();
    } 
    
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
          paragraph.add(new Paragraph(" "));
        }
    }
    
    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
    
    static class TableHeader extends PdfPageEventHelper{
        String header;
        PdfTemplate total;
        
        public void setHeader(String header){
            this.header = header;
        }
        
        @Override
        public void onOpenDocument(PdfWriter writer, Document doc){
            total = writer.getDirectContent().createTemplate(100, 100);
        }
        
        @Override
        public void onEndPage(PdfWriter writer, Document doc){
            PdfPTable table = new PdfPTable(3);
            try{
                table.setWidths(new int[]{24, 24, 2});
                table.setTotalWidth(527);
                table.setLockedWidth(true);
                table.getDefaultCell().setFixedHeight(20);
                table.getDefaultCell().setBorder(Rectangle.BOTTOM);
                table.addCell(header);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(String.format("Página %d de", writer.getPageNumber()));
                PdfPCell cell = new PdfPCell(Image.getInstance(total));
                cell.setBorder(Rectangle.BOTTOM);
                table.addCell(cell);
                table.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
            }catch(DocumentException ex){
                
            }                 
        }
        
        @Override
        public void onCloseDocument(PdfWriter writer, Document doc){
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 50, 0);
        }
    }
}
