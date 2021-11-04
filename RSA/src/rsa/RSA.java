/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsa;

import javax.swing.JOptionPane;
import java.math.BigInteger;
import java.util.Random;


/**
 *
 * @author eduardo
 */
public class RSA {
    int tamanio_pri;
    BigInteger cal;
    BigInteger e, t;
    BigInteger w, r, q; 
    
    public RSA(int tam_Primo){
        this.tamanio_pri = tam_Primo;
    }
    
    //generamos los numeros primos 
    public void generarPrimos(){
        r = new BigInteger(tamanio_pri, 10, new Random());
        do q = new BigInteger(tamanio_pri, 10, new Random());
        while(q.compareTo(r) == 0);
    }
    
    
    public void generarClaves(){
        w = r.multiply(q);
        
        cal = r.subtract(BigInteger.valueOf(1));
        cal = cal.multiply(q.subtract(BigInteger.valueOf(1)));
        
        //se elige el coprimo
        
        do e = new BigInteger(2*tamanio_pri, new Random());
        while((e.compareTo(cal) != -1) || (e.gcd(cal).compareTo(BigInteger.valueOf(1)) != 0));
        
        
        // Se calcula t=multiplo inversor
        
        t = e.modInverse(cal);
    }
    
//cifrado
    
    
    public BigInteger[] cifrar(String mensaje){
        
    // C = M^e mod(cal)
        
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        
        BigInteger[] bigdigitos = new BigInteger[digitos.length];
      
        for(i = 0; i < bigdigitos.length; ++i){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        
        BigInteger[] cifrado = new BigInteger[bigdigitos.length];
        
        for(i = 0; i < bigdigitos.length; ++i){
            cifrado[i] = bigdigitos[i].modPow(e, w);
        }
        
        return cifrado;
    }
//descifrar
    
    public String descifrar(BigInteger[] cifrado){
        
        //M = C^t mod w
        
        BigInteger[] descifrado = new BigInteger[cifrado.length];

        for(int i = 0; i < descifrado.length; ++i){
            descifrado[i] = cifrado[i].modPow(t, w);
        }
        
        char[] charArray = new char[descifrado.length];
        
        for(int i = 0; i < descifrado.length; ++i){
            charArray[i] = (char)(descifrado[i].intValue());
        }
        
        return (new String(charArray));
        
    }
}
