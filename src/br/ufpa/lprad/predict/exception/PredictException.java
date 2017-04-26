/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.lprad.predict.exception;

/**
 *
 * @author Arilene
 */
public class PredictException extends Exception{

    public PredictException(Throwable cause) {
        super(cause);
    }

    public PredictException(String message, Throwable cause) {
        super(message, cause);
    }

    public PredictException(String message) {
        super(message);
    }

    public PredictException() {
    }

    
}
