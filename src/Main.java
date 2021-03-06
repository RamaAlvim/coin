import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;



public class Main {


    public static void main(String[] args) {

        int[] tipomoedas = new int[]{10000, 5000, 2000, 1000, 500, 200, 100, 50, 25, 10, 5, 1};
        int[] resposta = new int[tipomoedas.length];
        int[] valores = new int[tipomoedas.length];
        int troco=0;
        Scanner keyboard = new Scanner(System.in);

        System.out.println("qual o valor de troco? ");

        troco = keyboard.nextInt();

        //le o arquivo txt com os valores
        valores=LerValores(tipomoedas,valores);

        System.out.println("Deseja utilizar o backtrack ou o branch and bound? ");
        System.out.println("Entre com 1 para backtrack ou 2 para branch and bound ");

        int opcao=0;

        opcao=keyboard.nextInt();

        if(opcao==1)
        {
            final long tempoInicialBacktrack = System.nanoTime();

            resposta= Backtrack.BackTrackInicio(tipomoedas,resposta,valores,troco);

            final long tempofinalbacktrack=System.nanoTime() - tempoInicialBacktrack;
            System.out.println("o metodo executou em " + (tempofinalbacktrack)+"ns");


            for (int i = 0; i < resposta.length; i++) {
                System.out.println(resposta[i]+ " de "+ tipomoedas[i] +" centavos"  );
            }
        }


        if(opcao==2)
        {
            final long tempoInicialBranchBound = System.nanoTime();

            resposta= BranchBound.BBkinicio(tipomoedas,resposta,valores,troco);

            final long tempofinalbb=System.nanoTime() - tempoInicialBranchBound;
            System.out.println("o metodo executou em " + (tempofinalbb)+"ns");

            for (int i = 0; i < resposta.length; i++) {
                System.out.println(resposta[i]+ " de "+ tipomoedas[i] +" centavos" );
            }
        }




    //    final long tempoInicialBranchBoundvariedades = System.nanoTime();


   //     resposta=BranchBound.BBVariedadekinicio(tipomoedas,resposta,valores,troco);


     //   final long tempofinalbbvariedades=System.nanoTime() - tempoInicialBranchBoundvariedades;
    //    System.out.println("o metodo executou em " + (tempofinalbbvariedades));



  //      for (int i = 0; i < resposta.length; i++) {
     //       System.out.println(resposta[i]+ " de "+ tipomoedas[i] +" centavos" );
     //   }


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


   public static int[] guloso(int[] tipomoedas, int[] resposta, int qualmoeda, int qtd,int troco,int valor)
    {

        //troco nao muda
        //valor eh a soma das moedas que eu adicionei e nao pode passar troco.
        //testar instancia sempre que acrescentar alguma moeda

        //int[] instancia=new int[tipomoedas.length];
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






    public static int[] BackTrackInicio(int[] tipomoedas, int[] resposta,int[] valoresfixo,int troco)
    {
        int[][] instancias=new int[tipomoedas.length][tipomoedas.length];
        int[] instancia;

        for(int i=0;i<tipomoedas.length;i++)
        {
            int[] valores=valoresfixo.clone();
            resposta=new int[tipomoedas.length];
            instancias[i]=BackTrack(tipomoedas,resposta,valores,i,troco,0,i);
        }
        instancia=instancias[0];
        for(int i=1;i<tipomoedas.length;i++)
        {
            instancia =retornamenor(instancia,instancias[i]);
        }
        return instancia;
    }

    private static int[] BackTrack(int[] tipomoedas, int[] resposta,int[] valores, int qualmoeda,int troco,int valor,int ini)
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
            resposta=BackTrack(tipomoedas,resposta,valores,qualmoeda,troco,valor + tipomoedas[qualmoeda],ini);

        }
        if(qualmoeda<tipomoedas.length-1&& valor<troco && valores[qualmoeda]==0) {
            //sem moeda daquele valor
            resposta=BackTrack(tipomoedas,resposta,valores,qualmoeda+1,troco,valor,ini);

        }
        if(qualmoeda<tipomoedas.length-1 && valor>troco && qualmoeda>=ini && valores[qualmoeda]>0)
        {//em valor, retira-se uma moeda do tipo tipomoedas[qualmoeda] da resposta
            //e exclui-se a possibilidade de caber outra moeda do mesmo tipo.
            //e coloca-se a moeda que foi retirada de volta no caixa
            valores[qualmoeda]=valores[qualmoeda]+1;
            resposta[qualmoeda]=resposta[qualmoeda]-1;
            resposta=BackTrack(tipomoedas,resposta,valores,qualmoeda+1,troco,valor-tipomoedas[qualmoeda],ini);
        }

        if(qualmoeda==tipomoedas.length-1 && valor<troco && valores[qualmoeda]>0)
        {//ultima pos
            valores[qualmoeda]=valores[qualmoeda]-1;
            resposta[qualmoeda]=resposta[qualmoeda]+1;

            resposta=BackTrack(tipomoedas,resposta,valores,qualmoeda,troco,valor+tipomoedas[qualmoeda],ini);
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



}//fim backtrack












class BranchBound
{
    public static int[] BBkinicio(int[] tipomoedas, int[] resposta,int[] valoresfixo,int troco)
    {
        int[][] instancias=new int[tipomoedas.length][tipomoedas.length];
        int[] instancia;

        for(int i=0;i<tipomoedas.length;i++)
        {
            int[] valores=valoresfixo.clone();
            resposta=new int[tipomoedas.length];
            instancias[i]=BB(tipomoedas,resposta,valores,i,troco,0,i);
        }
        instancia=instancias[0];
        for(int i=1;i<tipomoedas.length;i++)
        {
            instancia =RetornaMenor(instancia,instancias[i]);
        }
        return instancia;
    }


    public static int[] BBVariedadekinicio(int[] tipomoedas, int[] resposta,int[] valoresfixo,int troco)
    {

        tipomoedas=Reverte(tipomoedas);


        //calcula o maximo de variedades possiveis
        int lb=0;//valor maximo com a variedade maxima
        int varlb=0;//maximo de variedade possivel
        for(int i=0;i<tipomoedas.length;i++)
        {
            if(tipomoedas[i]<troco)
            {
                lb=lb+tipomoedas[i];
                varlb=varlb+1;
            }
        }


        int[][] instancias=new int[tipomoedas.length][tipomoedas.length];
        int[] instancia;

        for(int i=tipomoedas.length-varlb;i<tipomoedas.length;i++)
        {
            int[] valores=valoresfixo.clone();
            resposta=new int[tipomoedas.length];
            instancias[i]=BBvariedade(tipomoedas,resposta,valores,i,troco,0,0);
        }
        instancia=instancias[0];
        for(int i=1;i<tipomoedas.length;i++)
        {
            instancia =RetornaMaiorvariedade(instancia,instancias[i]);
        }
        return instancia;
    }

    private static int[] BB(int[] tipomoedas, int[] resposta,int[] valores, int qualmoeda,int troco,int valor,int ini)
    {
        int ub=troco-valor + tipomoedas[qualmoeda]*valores[qualmoeda];
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
            //se tiver tudo ok, testa o upper bound

            if(ub>=valor) {

                valores[qualmoeda] = valores[qualmoeda] - 1;
                resposta[qualmoeda] = resposta[qualmoeda] + 1;
                resposta = BB(tipomoedas, resposta, valores, qualmoeda, troco, valor + tipomoedas[qualmoeda], ini);

            }
        }
        if(qualmoeda<tipomoedas.length-1&& valor<troco && valores[qualmoeda]==0) {
            //sem moeda daquele valor
            //nao é nem preciso testar o upper bound, ja que nao tem jeito de colocar moedas nesse caso
            resposta=BB(tipomoedas,resposta,valores,qualmoeda+1,troco,valor,ini);

        }
        if(qualmoeda<tipomoedas.length-1 && valor>troco && qualmoeda>=ini && valores[qualmoeda]>0)
        {//em valor, retira-se uma moeda do tipo tipomoedas[qualmoeda] da resposta
            //e exclui-se a possibilidade de caber outra moeda do mesmo tipo.
            //e coloca-se a moeda que foi retirada de volta no caixa
            valores[qualmoeda]=valores[qualmoeda]+1;
            resposta[qualmoeda]=resposta[qualmoeda]-1;
            resposta=BB(tipomoedas,resposta,valores,qualmoeda+1,troco,valor-tipomoedas[qualmoeda],ini);
        }

        if(qualmoeda==tipomoedas.length-1 && valor<troco && valores[qualmoeda]>0)
        {//ultima pos


                valores[qualmoeda] = valores[qualmoeda] - 1;
                resposta[qualmoeda] = resposta[qualmoeda] + 1;

                resposta = BB(tipomoedas, resposta, valores, qualmoeda, troco, valor + tipomoedas[qualmoeda], ini);


        }

        return resposta;


    }

    private static int[] BBvariedade(int[] tipomoedas, int[] resposta,int[] valores, int qualmoeda,int troco,int valor,int var)
    {


        if(tipomoedas[qualmoeda]+valor<=troco && valores[qualmoeda]>0)
        {
            resposta[qualmoeda]=resposta[qualmoeda]+1;
            valores[qualmoeda]=valores[qualmoeda]-1;
            resposta=BBvariedade(tipomoedas,resposta,valores,qualmoeda+1,troco,valor+tipomoedas[qualmoeda],var);

        }
        if(tipomoedas[qualmoeda]+valor>troco && valores[qualmoeda]>0)
        {
            resposta[qualmoeda]=resposta[qualmoeda]-1;
            valores[qualmoeda]=valores[qualmoeda]+1;
            resposta=BBvariedade(tipomoedas,resposta,valores,qualmoeda-1,troco,valor-tipomoedas[qualmoeda],var);
        }
        return resposta;


    }
    public static int[] RetornaMenor(int[] inst1,int[] inst2)
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


    public static int[] RetornaMaiorvariedade(int[] inst1,int[] inst2)
    {
        int qtd1=0,qtd2=0;

        for(int i=0;i<inst1.length;i++)
        {
            if(inst1[i]>0) {
                qtd1++;
            }
            if(inst2[i]>0) {
                qtd2++;
            }
        }
        if(qtd1>qtd2)
        {
            return inst1;
        }
        else
        {
            return inst2;
        }
    }

    public static int[] Reverte(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
    }

}//fim branch bound