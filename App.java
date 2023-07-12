import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*
Data de inicialização: 22/06/22
Data da ultima alteração: 03/07/22
Integrantes: Miguel Lima, Rafael Henrique, Samuel Saurino
Objetivo: Desenvolver um programa que auxilie um produtor de máscaras possa organizar seu estoque, vendas e lucro obtido
*/

public class App {  
    public static void main (String[] args) throws IOException {
        //O arquivo Dados.txt é criado caso não exista.
        File novoArquivo = new File("C:\\Windows\\Temp\\Dados.txt");
        FileWriter fw = new FileWriter(novoArquivo, true);

        //Caso o arquivo já exista, nada acontece.
        if (novoArquivo.exists()) {
        }
        //Mas caso o arquivo for criado no momento da execução do programa, ele é iniciado com todas as informações zeradas.
        else {
            for (int a=0; a<4; a++) fw.write("0,0,0,0,0\n");
        }
        fw.close();

        try {
            Scanner scan = new Scanner (System.in);
            Scanner scanner = new Scanner(new FileReader("C:\\Windows\\Temp\\Dados.txt")).useDelimiter("\n");
            String prodBruto;
            String[][] produtos = new String[4][6];
            int[][] produtosBkp = new int[4][6];
            int resp, i=0;

            //O vetor nomes será explicado mais abaixo.
            String[] nomes = new String[4];
            nomes[0] = "Infantil lisa: ";
            nomes[1] = "Infantil estampada: ";
            nomes[2] = "Adulta lisa: ";
            nomes[3] = "Adulta estampada: ";

            //O for aqui serve para repetir o código após a execução dele, para o produtor não ter que fechar o programa e abrir de novo para fazer uma outra alteração.
            for (int repet=0; repet<100; repet++) {
                while (scanner.hasNextLine()) {

                    //A variável salva a linha lida com o scanner, e é repartida em cada "," no vetor logo abaixo.
                    prodBruto = scanner.nextLine();
                    String[] prodVet = prodBruto.split(",");

                    //O for que passa por cada array do vetor, salvando a respectiva informação na coluna da matriz, e convertendo o dado em String para int na matriz de backup.
                    for (int iCol=0; iCol<produtos[0].length; iCol++) {
                        produtos[i][iCol] = prodVet[iCol];
                        produtosBkp[i][iCol] = Integer.parseInt(produtos[i][iCol]);
                    }
                    i++;
                }
                
                //Início do menu, onde o produtor pode escolher oque ele pode acessar.
                System.out.println("Oque você quer acessar? \n1 - Acessar estoque \n2 - Alterar valores de produção e venda \n3 - Entrar no modo de venda \n4 - Gerar relatório");
                resp = scan.nextInt();

                //Um switch onde cada tecla (resp) inicia a função de acordo com a escolha feita anteriormente, a matriz é enviada, alterada e retornada.
                switch (resp) {
                    case 1:

                        //O vetor nomes sendo usado como parâmetro na função do estoque e do relatório.
                        funcEstoque(produtosBkp, nomes);
                        break;
                    case 2:
                        funcValor(produtosBkp);
                        break;
                    case 3:
                        funcVendas(produtosBkp);
                        break;
                    case 4:
                        funcRelatorio(produtosBkp, nomes);
                        break;
                    default:
                        System.out.println("\nErro! Aperte uma tecla válida\n");
                }

                //BufferedWriter que edita o arquivo dados, para que as informações alteradas fiquem salvas mesmo após fechar o programa.
                BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Windows\\Temp\\Dados.txt"));
                for (int li=0; li<produtos.length; li++) {
                    for (int col=0; col<produtos[0].length; col++) {

                        //A matriz de backup retornada com as alterações, sendo convertida de int para String de volta para a matriz principal.
                        produtos[li][col] = String.valueOf(produtosBkp[li][col]);

                        //O BufferedWriter salva o dado da matriz adicionando a vírgula para separar a informação de cada coluna.
                        bw.write(produtos[li][col] + ",");
                    }
                    //A quebra de linha, para separar as informações de cada linha.
                    bw.write("\n");
                }
                bw.close();
            }
        }

        //Erro que aparece caso o programa não encontre o arquivo Dados.txt
        catch (FileNotFoundException e){
            System.out.println("Erro! Arquivo não encontrado");
        }
        
    }

    //Função que organiza o estoque, podendo verificar quantas máscaras estão armazenadas sem ter que gerar o relatório, além de poder alterar
    public static int[][] funcEstoque (int[][] produtos, String[] nomes) throws IOException {
        Scanner scan = new Scanner (System.in);

        //resp começa sendo 1 para poder entrar dentro do while, 
        int resp=1, resp2, adEstoque;

        //um while que serve para o mesmo propósito do primeiro for do main.
        while (resp !=3) {
            System.out.println("\nOque você quer acessar? \n1 - Ver produtos em estoque \n2 - Adicionar novos produtos no estoque \n3 - Voltar ao menu");
            resp = scan.nextInt();

            switch (resp) {
                case 1:
                    System.out.println();
                    for (int i=0; i<4; i++) {

                        //o vetor nomes sendo usado para identificar de qual tipo de máscara é a informação apresentada.
                        System.out.println(nomes[i] + produtos[i][0]);
                    } 
                    break;
                case 2:
                    System.out.println("\nQual produto você quer adicionar? \n1 - Infantil lisa \n2 - Infantil estampada \n3 - Adulta lisa \n4 - Adulta estampada");
                    resp2 = scan.nextInt();
                    
                    if (resp2 == 1 || resp2 == 2 || resp2 == 3 || resp2 == 4) {
                        System.out.print("Digite a quantidade que você quer adicionar: ");
                        adEstoque = scan.nextInt();

                        //A quantidade digitada altera a coluna da matriz que armazena a quantidade de máscara em estoque.
                        produtos[resp2-1][0] += adEstoque;
                    }
                    else System.out.println("Erro! Aperte uma tecla válida");
                    break;
                case 3:
                    System.out.println();
                    break;
                default:
                    System.out.println("Erro! Aperte uma tecla válida");
                    break;
            }
        }
        //A matriz alterada é retornada, que é salva logo em seguida no arquivo Dados.txt
        return produtos;
    }

    public static int[][] funcValor (int[][] produtos) {
        Scanner scan = new Scanner(System.in);
        int resp=1, resp2;
        int valor;

        while (resp != 3) {
            System.out.println("\nOque você quer alterar? \n1 - Alterar custo de produção \n2 - Alterar valor de venda \n3 - Voltar ao menu");
            resp = scan.nextInt();
            if (resp == 1 || resp ==2) {
                System.out.println("\nQual produto você quer alterar? \n1 - Infantil lisa \n2 - Infantil estampada \n3 - Adulta lisa \n4 - Adulta estampada");
                resp2 = scan.nextInt();

                //Switch que separa o tipo de informação que o usuário quer alterar, e altera o respectivo dado na matriz.
                switch (resp) {
                    case 1:
                        if (resp2 == 1 || resp2 == 2 || resp2 == 3 || resp2 == 4) {
                            System.out.print("Digite o novo custo de produção: ");
                            valor = scan.nextInt();

                            //-1 pois o vetor vai de 0 a 3, enquanto as alternativas de resposta vão de 1 a 4.
                            produtos[resp2-1][1] = valor;
                        }
                        else System.out.println("Erro! Aperte uma tecla válida");
                        break;
                    case 2:
                        if (resp2 == 1 || resp2 == 2 || resp2 == 3 || resp2 == 4) {
                            System.out.print("Digite o novo valor de venda: ");
                            valor = scan.nextInt();
                            produtos[resp2-1][2] = valor;
                        }
                        else System.out.println("Erro! Aperte uma tecla válida");
                        break;
                    default:
                        System.out.println("Erro! Aperte uma tecla válida");
                        break;
                }
            }
        }
        //sysout vazio para quebra de linha
        System.out.println();
        return produtos;
    }

    public static int[][] funcVendas (int[][] produtos) {
        Scanner scan = new Scanner(System.in);
        int resp=1, compra;

        while (resp !=5) {
            System.out.println("\nDigite a máscara que você deseja comprar \n1 - Infantil lisa \n2 - Infantil estampada \n3 - Adulta lisa \n4 - Adulta estampada \n5 - voltar ao menu");
            resp = scan.nextInt();
            if (resp == 5) {
                //Não executar nada, quebrar a linha, e voltar para o menu.
                System.out.println();
            }

            //Só executa caso houver máscaras do tipo escolhido disponíveis no estoque.
            else if (produtos[resp-1][0] > 0) {
                System.out.println("Digite a quantidade que você quer comprar");
                compra = scan.nextInt();

                //Só executa caso a quantidade comprada for maior à quantidade disponível no estoque.
                if (compra > produtos[resp-1][0]) {
                    System.out.println("Compra não efetuada! Quantidade digitada maior que a disponível");
                }

                else {
                    //Respectivamente, remove a quantidade de máscaras compradas do estoque, salva a quantidade de máscaras de cada tipo compradas, e salva o lucro individual delas.
                    produtos[resp-1][0] -= compra;
                    produtos[resp-1][3] += compra;
                    produtos[resp-1][4] += ((produtos[resp-1][2] - produtos[resp-1][1])*compra);
                    produtos[resp-1][5] += produtos[resp-1][4];
                }
            }
            else {
                System.out.println("Produto esgotado!");
            }
        }
        return produtos;
    }

    public static int[][] funcRelatorio (int[][] produtos, String[] nomes) throws IOException  {
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Windows\\Temp\\Relatório.txt"));
        int lucroDia=0, lucroTotal=0;

        bw.write("MÁSCARAS EM ESTOQUE\n\n");
        for (int i=0; i<produtos.length; i++) {
            bw.write(nomes[i] + produtos[i][0] + "\n");
        }
        bw.write("\n\nVENDAS E LUCRO\n \nTotal de máscaras vendidas\n");
        for (int i=0; i<produtos.length; i++) {
            bw.write(nomes[i] + produtos[i][3] + "\n");

            //Salva todo lucro individual de cada tipo de máscara em cada variável.
            lucroDia += produtos[i][4];
            lucroTotal += produtos[i][5];
        }
        bw.write("\nLucro do dia: " + lucroDia + "\nLucro total: " + lucroTotal);

        //Zera as vendas e o lucro do dia, restando apenas o lucro total.
        for (int i=0; i<produtos.length; i++) {
            produtos[i][3] = 0;
            produtos[i][4] = 0;
        }

        bw.close();
        System.out.println();
        return produtos;
    }
}