package br.ufpa.lprad.predict.redebayesiana.espacoCombinacional;

import java.util.Vector;

/**
 *
 * @author J. Gabriel Lima
 */
public class EspacoCombinacional {
    
 
Vector vetor;
public static Vector[] comb;
Vector prec;

 

    public EspacoCombinacional(Vector precedentes)
    {
              
        comb = new Vector[retornaEspaco(precedentes)];
        for(int i=0; i<comb.length; i++)
            comb[i]=new Vector();
     
        int r=(retornaEspaco(precedentes)/2);
        for(int i=0; i<precedentes.size(); i++){
            int cont=1;
            boolean b = true;
            //System.out.println("r: "+r);
            for(int j=0; j<comb.length; j++,cont++){
                if(b)
                    comb[j].add(precedentes.elementAt(i));  
                if(cont==r){
                    b=!b;
                    cont=0;
                }
            }            
            r=r/2;
        }
        
        int contador=0;
        for(int i=0; i<comb.length; i++)
        {
            if(comb[i].size()!=0)
                contador++;
        }
        
        Vector[] aux= new Vector[contador];
        contador=0;
        for(int i=0; i<comb.length; i++)
        {
            if(comb[i].size()!=0)
            {
                aux[contador]=comb[i];
                contador++;
            }
        }
        
        comb=aux;
        
        
    }
    
    public int retornaEspaco(Vector prec){
        int tam = 1;
        
        for(int i=1; i<=prec.size(); i++)
            tam+=fatorial(prec.size())/(fatorial(i)*fatorial(prec.size()-i));

        return tam;
    }
    
    public double fatorial(double f){
      double var=1;
      if (f == 0)
          return 1;
      else{
          while(f>0){
              var*=f;
              f--;
          }
      }
      return var;
    }
    
    public static void main(String[] args){
        
        Vector vetor = new Vector();
        for(int i=1; i<5; i++)
        {
            vetor.add("x"+i);
        }
            
        EspacoCombinacional q = new EspacoCombinacional(vetor);
        
        Vector[] resposta = comb;

        for(int i=0;i<resposta.length; i++)
        {
            System.out.println("vetor["+i+"]: "+resposta[i]);
        }
        
    }
}
    

