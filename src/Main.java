import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) {

        int[] tipomoedas = new int[]{10000, 5000, 2000, 1000, 500, 200, 100, 50, 25, 10, 5, 1};
        int[] resposta = new int[tipomoedas.length];
        int[] valores = new int[tipomoedas.length];
        int troco = 100;


        valores=LerValores(tipomoedas,valores);

     /*   for (int i = 0; i < valores.length; i++) {
            System.out.println(valores[i]);
        }
      */

        // resposta= Backtrack.guloso(tipomoedas,resposta,0,0,19,0);
          resposta= Backtrack.backtrackinicio(tipomoedas,resposta,valores,troco);

        for (int i = 0; i < resposta.length; i++) {
            System.out.println(resposta[i]);
        }

    }//fim main





   static int[] LerValores(int[] tipomoedas,int[] valores) {

       //System.setProperty("user.dir","/home/rama/IdeaProjects/coin/src/");
       String file = "valores.txt";

       Scanner scanner;
        try {


            scanner=new Scanner(new FileReader(file)).useDelimiter("=|\n");

            while (scanner.hasNext())
            {
                int valor = Integer.parseInt(scanner.next());
                int quantidade= Integer.parseInt( scanner.next());
                valores=qtd_valores(tipomoedas,valores,valor,quantidade);
            }


        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }


        return valores;
    }

    public static int[] qtd_valores(int[] tipomoedas,int[] valores,int qualmoeda, int qtd)
    {
        for(int i=0;i<valores.length;i++)
        {
            if(qualmoeda==tipomoedas[i])
            {
                valores[i]=qtd;
            }
        }

            return valores;
    }

}//fim Main class


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
    public static int[] backtrackinicio(int[] tipomoedas, int[] resposta,int[] valoresfixo,int troco)
    {
        int[][] instancias=new int[tipomoedas.length][tipomoedas.length];
        int[] instancia;

        for(int i=0;i<tipomoedas.length;i++)
        {
            int[] valores=valoresfixo.clone();
            resposta=new int[tipomoedas.length];
            instancias[i]=backtrack(tipomoedas,resposta,valores,i,troco,0,i);
        }
        instancia=instancias[0];
        for(int i=1;i<tipomoedas.length;i++)
        {
         instancia =retornamenor(instancia,instancias[i]);
        }
        return instancia;
    }

    private static int[] backtrack(int[] tipomoedas, int[] resposta,int[] valores, int qualmoeda,int troco,int valor,int ini)
    {
        if(valor==troco && valores[qualmoeda]>=0)
        {//ok
            //achou troco
            return resposta;

        }
        if(valor<troco && qualmoeda==tipomoedas.length-1 && valores[tipomoedas.length-1]==0)
        {//sem troco
            //sem moeda daquele valor pra acrescentar ao troco
            //sem mais moedas para dar

            resposta=new int[resposta.length];//vetor com apenas 0
            return resposta;
        }
        if(qualmoeda<tipomoedas.length-1&& valor<troco && valores[qualmoeda]>0) {
            //em valor acrescenta-se uma moeda do tipo tipomoedas[qualmoeda] na resposta
            //e se deixa aberta a possibilidade de colocar outra moeda igual
            //testa se existe a cedula ou moeda na quantia
            valores[qualmoeda]=valores[qualmoeda]-1;
            resposta[qualmoeda]=resposta[qualmoeda]+1;
            resposta=backtrack(tipomoedas,resposta,valores,qualmoeda,troco,valor + tipomoedas[qualmoeda],ini);

        }
        if(qualmoeda<tipomoedas.length-1&& valor<troco && valores[qualmoeda]==0) {
            //sem moeda daquele valor
            resposta=backtrack(tipomoedas,resposta,valores,qualmoeda+1,troco,valor,ini);

        }
        if(qualmoeda<tipomoedas.length-1 && valor>troco && qualmoeda>=ini && valores[qualmoeda]>0)
        {//em valor, retira-se uma moeda do tipo tipomoedas[qualmoeda] da resposta
            //e exclui-se a possibilidade de caber outra moeda do mesmo tipo.
            //e coloca-se a moeda que foi retirada de volta no caixa
            valores[qualmoeda]=valores[qualmoeda]+1;
            resposta[qualmoeda]=resposta[qualmoeda]-1;
            resposta=backtrack(tipomoedas,resposta,valores,qualmoeda+1,troco,valor-tipomoedas[qualmoeda],ini);
        }

        if(qualmoeda==tipomoedas.length-1 && valor<troco && valores[qualmoeda]>0)
        {//ultima pos
            valores[qualmoeda]=valores[qualmoeda]-1;
            resposta[qualmoeda]=resposta[qualmoeda]+1;

            resposta=backtrack(tipomoedas,resposta,valores,qualmoeda,troco,valor+tipomoedas[qualmoeda],ini);
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
    if(qtd1<qtd2&& qtd1!=0)
    {
        return inst1;
    }
    else if (qtd2<qtd1 && qtd2!=0)
        {
        return inst2;
    }
    else {return inst1;}
}




}