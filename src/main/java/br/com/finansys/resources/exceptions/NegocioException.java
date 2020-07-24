package br.com.finansys.resources.exceptions;

// import java.io.Serializable;

public class NegocioException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public NegocioException() {
        super();
    }

    public NegocioException(String msg){
        super(msg);
    }

    public NegocioException(String msg, Exception e){
        super(msg, e);
    }
}