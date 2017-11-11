

public class Main {


    public static void main(String[] args) {
        int[] tiposmoedas= {10000,5000,2000,1000,500,200, 100, 50, 25, 10, 5, 1};
        // {1,5,10,25,50,100,200,500,1000,2000,5000,10000}
        int[] resposta=new int[11];
        int tot=Integer.MAX_VALUE;

        for(int i =0;i<11;i++)
        {
            resposta[i]=0;
        }
        //bruteforce.bruteforcemin(resposta,tiposmoedas,valor,tot);
        //é necessario multiplicar o valor por
        System.out.println(bruteforce.encontraoI(tiposmoedas,200));
    }

}

class bruteforce {


    public static int[] bruteforcemin(int[]resposta, int[] tiposmoedas,int valor,int tot)
    {   int[]temp=resposta;

        int soma=valor;
        int resto=0;
        for( int inicio=0;inicio<tiposmoedas.length-1;inicio++)
        {
            int quantasvezescabe = 0;
            resto=valor;

            for (int i = inicio; i < tiposmoedas.length; i++) {

                if (tiposmoedas[i] <=resto) {
                    quantasvezescabe = soma / tiposmoedas[i];
                    resto = soma % tiposmoedas[i];
                    resposta[i] = quantasvezescabe;
                }
            }

            if(contamoedas(resposta)<contamoedas(temp))
            {

            }
        }

        return resposta;

    }

    public static int contamoedas(int[] resposta)
    {
        int quantas=0;

        for(int i=0;i<resposta.length;i++)
        {
            if(resposta[i]>1)
            {
                quantas=quantas+resposta[i];

            }
        }

        return quantas;
    }

    public void printresposta(int[] resposta)
    {
        for(int i=0;i<resposta.length;i++)
        {

            System.out.print(resposta[i]+" ");
        }

    }


    public static int encontraoI(int[] tiposmoedas,int valor )
    {
        int izao=0;
        for(int i=0;i<tiposmoedas.length;i++)
        {
            if(tiposmoedas[i]<=valor)
            {
                izao=i;
                break;
            }

        }
        return izao;
    }
}
