import java.util.*;


public class Main {


    public static void main(String[] args) {

        int[] tipomoedas = new int[]{10000, 5000, 2000, 1000, 500, 200, 100, 50, 25, 10, 5, 1};
        int[] resposta=new int[tipomoedas.length];
        int troco=31;
       // resposta= Backtrack.guloso(tipomoedas,resposta,0,0,19,0);
        resposta= Backtrack.backtrackinicio(tipomoedas,resposta,troco);



        for(int i=0;i<resposta.length;i++)
        {
            System.out.println(resposta[i]);
        }

    }
}


class Backtrack
{
    //troco nao muda
    //valor eh a soma das moedas que eu adicionei e nao pode passar troco.
   //testar instancia sempre que acrescentar alguma moeda


   public static int[] guloso(int[] tipomoedas, int[] resposta, int qualmoeda, int qtd,int troco,int valor)
    {//int[] instancia=new int[tipomoedas.length];
        if(valor==troco)
        {
            return resposta;
        }
        if(  (troco-valor) >tipomoedas[qualmoeda])
        {
            resposta[qualmoeda]=resposta[qualmoeda]+1;
            resposta=guloso(tipomoedas,resposta,qualmoeda,qtd+1,troco,valor+tipomoedas[qualmoeda]);
        }
        if(qualmoeda<tipomoedas.length-1 && troco-valor<=tipomoedas[qualmoeda]  )
        {
            resposta=guloso(tipomoedas,resposta,qualmoeda+1,qtd,troco,valor);
        }
        //testar
     /*   if(qualmoeda<=tipomoedas.length-1 && troco-valor ==tipomoedas[qualmoeda])
        {//igual a ultima pos
            resposta[qualmoeda]=resposta[qualmoeda]+1;
            resposta=guloso(tipomoedas,resposta,qualmoeda,qtd+1,troco,valor+tipomoedas[qualmoeda]);
        }*/
        if(qualmoeda==tipomoedas.length-1 && troco-valor ==tipomoedas[qualmoeda])
        {//igual a ultima pos
            resposta[qualmoeda]=resposta[qualmoeda]+1;
            resposta=guloso(tipomoedas,resposta,qualmoeda,qtd+1,troco,valor+tipomoedas[qualmoeda]);
        }


        return resposta;
    }
    public static int[] backtrackinicio(int[] tipomoedas, int[] resposta,int troco)
    {int[][] instancias=new int[tipomoedas.length][tipomoedas.length];
        int[] instancia=new int[tipomoedas.length];
        for(int i=0;i<tipomoedas.length;i++)
        {
            resposta=new int[tipomoedas.length];
            instancias[i]=backtrack(tipomoedas,resposta,i,troco,0,i);
        }
        instancia=instancias[0];
        for(int i=1;i<tipomoedas.length;i++)
        {
         instancia =retornamenor(instancia,instancias[i]);
        }
        return instancia;
    }

    private static int[] backtrack(int[] tipomoedas, int[] resposta, int qualmoeda,int troco,int valor,int ini)
    {
        if(valor==troco)
        {//ok
            return resposta;

        }
        if(qualmoeda<tipomoedas.length-1&& valor<troco) {
            //em valor acrescenta-se uma moeda do tipo tipomoedas[qualmoeda] na resposta
            //e se deixa aberta a possibilidade de colocar outra moeda igual
            resposta[qualmoeda]=resposta[qualmoeda]+1;
            resposta=backtrack(tipomoedas,resposta,qualmoeda,troco,valor + tipomoedas[qualmoeda],ini);

        }
        if(qualmoeda<tipomoedas.length-1 && valor>troco && qualmoeda>=ini)
        {//em valor, retira-se uma moeda do tipo tipomoedas[qualmoeda] da resposta
            //e exclui-se a possibilidade de caber outra moeda do mesmo tipo.


            resposta[qualmoeda]=resposta[qualmoeda]-1;
            resposta=backtrack(tipomoedas,resposta,qualmoeda+1,troco,valor-tipomoedas[qualmoeda],ini);
        }

        if(qualmoeda==tipomoedas.length-1 && valor<troco)
        {//ultima pos
            resposta[qualmoeda]=resposta[qualmoeda]+1;
            resposta=backtrack(tipomoedas,resposta,qualmoeda,troco,valor+tipomoedas[qualmoeda],ini);
        }

        return resposta;


    }


public static int[] retornamenor(int[] inst1,int[] inst2)
{
    int qtd1=0,qtd2=0;

    for(int i=0;i<inst1.length;i++)
    {
        qtd1=qtd1+inst1[i];
        qtd2=qtd2+inst2[i];
    }
    if(qtd1<qtd2)
    {
        return inst1;
    }
    else
        {
        return inst2;
    }
}




}