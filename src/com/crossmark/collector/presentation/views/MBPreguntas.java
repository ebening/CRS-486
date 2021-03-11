/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views;

import com.crossmark.collector.business.domain.Respuestas;
import com.crossmark.collector.business.services.ServicePreguntas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RIGG
 */
public class MBPreguntas implements Serializable {

    private ServicePreguntas servicePreguntas;
    private int idTipoRespuesta;

    //----valores Rendered
    private boolean verTexto = false;
    private boolean verEntero = false;
    private boolean verDecimal = false;
    private boolean verInstruccion = false;
    private boolean verCheck = false;
    private boolean verOpcionMultiple = false;
    private boolean verSeleccionMultiple = false;
    
    
    //------valores de la pregunta
    
    private String preguntaOID;
    private String seccionOID;
    private String textoPregunta;
    private String mensaje;
    private int ordenPregunta;
    private boolean activa;
    private boolean enabled;
    private boolean visible;
    private String variable;
    private int tipoRespuesta;
    private String alerta;
    private int subCategoria;
    private boolean bancoPreguntas;
    private int categoria;
    private boolean requerida;
    
    
    
    //---------valores de las opciones
    private int longitudMaxima;
    private int valorMinimo;
    private int valorMaximo;
    private int numeroDecimales;
    private String nombreArchivo;
    private String valorAlerta;
    

    

    public boolean isRequerida() {
		return requerida;
	}



	public void setRequerida(boolean requerida) {
		this.requerida = requerida;
	}



	private List<Respuestas> listaTipoRespuesta = new ArrayList<Respuestas>();

    public List<Respuestas> traerListaTipoRespuesta() {

        this.setListaTipoRespuesta(servicePreguntas.listaTipoRespuestas());

        return getListaTipoRespuesta();
    }
    
    
    
    public String preguntaAgregada(){
    	String miPreguntaOID = "";
    	
    	miPreguntaOID = servicePreguntas.preguntaAgregada(
    			"", 
    			seccionOID, 
    			textoPregunta, 
    			mensaje, 
    			ordenPregunta, 
    			true, 
    			true, 
    			true, 
    			variable, 
    			idTipoRespuesta, 
    			alerta, 
    			subCategoria, 
    			bancoPreguntas, 
    			categoria, 
    			requerida,
                        longitudMaxima,
                        valorMinimo,
                        valorMaximo,
                        numeroDecimales,nombreArchivo,valorAlerta);
    	
    	return miPreguntaOID;
    }
    
    
    
    
    
    
    
    
    
    
    

    public void configuraPregunta() {

        if (this.idTipoRespuesta == 0) {

        } else if (this.idTipoRespuesta == 1) {

            verTexto = true;
            verEntero = false;
            verDecimal = false;
            verInstruccion = false;
            verCheck = false;
            verOpcionMultiple = false;
            verSeleccionMultiple = false;

        } else if (this.idTipoRespuesta == 2) {

            verTexto = false;
            verEntero = true;
            verDecimal = false;
            verInstruccion = false;
            verCheck = false;
            verOpcionMultiple = false;
            verSeleccionMultiple = false;

        } else if (this.idTipoRespuesta == 3) {

            verTexto = false;
            verEntero = false;
            verDecimal = true;
            verInstruccion = false;
            verCheck = false;
            verOpcionMultiple = false;
            verSeleccionMultiple = false;

        } else if (this.idTipoRespuesta == 4) {

        } else if (this.idTipoRespuesta == 5) {

        } else if (this.idTipoRespuesta == 6) {

            verTexto = false;
            verEntero = false;
            verDecimal = false;
            verInstruccion = false;
            verCheck = false;
            verOpcionMultiple = true;
            verSeleccionMultiple = false;

        } else if (this.idTipoRespuesta == 7) {

            verTexto = false;
            verEntero = false;
            verDecimal = false;
            verInstruccion = false;
            verCheck = true;
            verOpcionMultiple = false;
            verSeleccionMultiple = false;

        } else if (this.idTipoRespuesta == 8) {

            verTexto = false;
            verEntero = false;
            verDecimal = false;
            verInstruccion = true;
            verCheck = false;
            verOpcionMultiple = false;
            verSeleccionMultiple = false;

        }

    }

    /**
     * @return the listaTipoRespuesta
     */
    public List<Respuestas> getListaTipoRespuesta() {
        return listaTipoRespuesta;
    }

    /**
     * @param listaTipoRespuesta the listaTipoRespuesta to set
     */
    public void setListaTipoRespuesta(List<Respuestas> listaTipoRespuesta) {
        this.listaTipoRespuesta = listaTipoRespuesta;
    }

    /**
     * @return the servicePreguntas
     */
    public ServicePreguntas getServicePreguntas() {
        return servicePreguntas;
    }

    /**
     * @param servicePreguntas the servicePreguntas to set
     */
    public void setServicePreguntas(ServicePreguntas servicePreguntas) {
        this.servicePreguntas = servicePreguntas;
    }

    /**
     * @return the idTipoRespuesta
     */
    public int getIdTipoRespuesta() {
        return idTipoRespuesta;
    }

    /**
     * @param idTipoRespuesta the idTipoRespuesta to set
     */
    public void setIdTipoRespuesta(int idTipoRespuesta) {
        this.idTipoRespuesta = idTipoRespuesta;
    }

    /**
     * @return the verTexto
     */
    public boolean isVerTexto() {
        return verTexto;
    }

    /**
     * @param verTexto the verTexto to set
     */
    public void setVerTexto(boolean verTexto) {
        this.verTexto = verTexto;
    }

    /**
     * @return the verEntero
     */
    public boolean isVerEntero() {
        return verEntero;
    }

    /**
     * @param verEntero the verEntero to set
     */
    public void setVerEntero(boolean verEntero) {
        this.verEntero = verEntero;
    }

    /**
     * @return the verDecimal
     */
    public boolean isVerDecimal() {
        return verDecimal;
    }

    /**
     * @param verDecimal the verDecimal to set
     */
    public void setVerDecimal(boolean verDecimal) {
        this.verDecimal = verDecimal;
    }

    /**
     * @return the verInstruccion
     */
    public boolean isVerInstruccion() {
        return verInstruccion;
    }

    /**
     * @param verInstruccion the verInstruccion to set
     */
    public void setVerInstruccion(boolean verInstruccion) {
        this.verInstruccion = verInstruccion;
    }

    /**
     * @return the verCheck
     */
    public boolean isVerCheck() {
        return verCheck;
    }

    /**
     * @param verCheck the verCheck to set
     */
    public void setVerCheck(boolean verCheck) {
        this.verCheck = verCheck;
    }

    /**
     * @return the verOpcionMultiple
     */
    public boolean isVerOpcionMultiple() {
        return verOpcionMultiple;
    }

    /**
     * @param verOpcionMultiple the verOpcionMultiple to set
     */
    public void setVerOpcionMultiple(boolean verOpcionMultiple) {
        this.verOpcionMultiple = verOpcionMultiple;
    }

    /**
     * @return the verSeleccionMultiple
     */
    public boolean isVerSeleccionMultiple() {
        return verSeleccionMultiple;
    }

    /**
     * @param verSeleccionMultiple the verSeleccionMultiple to set
     */
    public void setVerSeleccionMultiple(boolean verSeleccionMultiple) {
        this.verSeleccionMultiple = verSeleccionMultiple;
    }



	public String getPreguntaOID() {
		return preguntaOID;
	}



	public void setPreguntaOID(String preguntaOID) {
		this.preguntaOID = preguntaOID;
	}



	public String getSeccionOID() {
		return seccionOID;
	}



	public void setSeccionOID(String seccionOID) {
		this.seccionOID = seccionOID;
	}



	public String getTextoPregunta() {
		return textoPregunta;
	}



	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}



	public String getMensaje() {
		return mensaje;
	}



	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}



	public int getOrdenPregunta() {
		return ordenPregunta;
	}



	public void setOrdenPregunta(int ordenPregunta) {
		this.ordenPregunta = ordenPregunta;
	}



	public boolean isActiva() {
		return activa;
	}



	public void setActiva(boolean activa) {
		this.activa = activa;
	}



	public boolean isEnabled() {
		return enabled;
	}



	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}



	public boolean isVisible() {
		return visible;
	}



	public void setVisible(boolean visible) {
		this.visible = visible;
	}



	public String getVariable() {
		return variable;
	}



	public void setVariable(String variable) {
		this.variable = variable;
	}



	public int getTipoRespuesta() {
		return tipoRespuesta;
	}



	public void setTipoRespuesta(int tipoRespuesta) {
		this.tipoRespuesta = tipoRespuesta;
	}



	public String getAlerta() {
		return alerta;
	}



	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}



	public int getSubCategoria() {
		return subCategoria;
	}



	public void setSubCategoria(int subCategoria) {
		this.subCategoria = subCategoria;
	}



	public boolean isBancoPreguntas() {
		return bancoPreguntas;
	}



	public void setBancoPreguntas(boolean bancoPreguntas) {
		this.bancoPreguntas = bancoPreguntas;
	}



	public int getCategoria() {
		return categoria;
	}



	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

    /**
     * @return the longitudMaxima
     */
    public int getLongitudMaxima() {
        return longitudMaxima;
    }

    /**
     * @param longitudMaxima the longitudMaxima to set
     */
    public void setLongitudMaxima(int longitudMaxima) {
        this.longitudMaxima = longitudMaxima;
    }

    /**
     * @return the valorMinimo
     */
    public int getValorMinimo() {
        return valorMinimo;
    }

    /**
     * @param valorMinimo the valorMinimo to set
     */
    public void setValorMinimo(int valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    /**
     * @return the valorMaximo
     */
    public int getValorMaximo() {
        return valorMaximo;
    }

    /**
     * @param valorMaximo the valorMaximo to set
     */
    public void setValorMaximo(int valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    /**
     * @return the numeroDecimales
     */
    public int getNumeroDecimales() {
        return numeroDecimales;
    }

    /**
     * @param numeroDecimales the numeroDecimales to set
     */
    public void setNumeroDecimales(int numeroDecimales) {
        this.numeroDecimales = numeroDecimales;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getValorAlerta() {
        return valorAlerta;
    }

    public void setValorAlerta(String valorAlerta) {
        this.valorAlerta = valorAlerta;
    }
    
    

}
