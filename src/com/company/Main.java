package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Adatok
{
    public String Telepules;
    public Date Idő;
    public  String Szelirany;
    public  String Erosseg;
    public  int Homerseklet;
    public Adatok(String sor)
    {
        try
        {
            String[] split = sor.split(" ");
            Telepules =split[0];
            SimpleDateFormat datum = new SimpleDateFormat("HHmm");
            Idő = datum.parse(split[1]);
            Szelirany =split[2].substring(0,3);
            Erosseg =split[2].substring(3,5);
            Homerseklet = Integer.parseInt(split[3]);
        }
        catch(ParseException e)
        {
            System.out.println("Hiba");
        }

    }
}
public class Main {

    public static void main(String[] args) throws Exception
    {
        ArrayList<Adatok> lista = new ArrayList<Adatok>();
        File doku = new File("tavirathu13.txt");
        Scanner beolvasas = new Scanner(doku);
        while(beolvasas.hasNextLine())
        {
            lista.add(new Adatok(beolvasas.nextLine()));
        }
        beolvasas.close();
        System.out.println("2. feladat\nAdja meg egy település kódját! Település: ");
        Scanner sor = new Scanner(System.in);
        String telepules = sor.next();
        ArrayList<Date> meresi = new ArrayList<Date>();
        int min =1000;
        int max = -100;
        Adatok minA = lista.get(0);
        Adatok maxA = lista.get(0);
        for(Adatok x: lista)
        {
            if(x.Telepules.equals(telepules))
            {

                meresi.add(x.Idő);

            }
        }

        for (Adatok x: lista)
        {
            if(x.Homerseklet>max)
            {
                max = x.Homerseklet;
                maxA =x;
            }
            if (x.Homerseklet<min)
            {
                min = x.Homerseklet;
                minA = x;
            }

        }


        Date idő = meresi.get(meresi.size()-1);
        System.out.printf("Az utolsó mérési adat a megadott településről %d:%d-kor érkezett.\n",idő.getHours(),idő.getMinutes());
        System.out.println("3. feladat");
        System.out.printf("A legalacsonyabb hőmérséklet: %s %d:%d %d fok.\n", minA.Telepules,minA.Idő.getHours(),minA.Idő.getMinutes(),minA.Homerseklet);
        System.out.printf("A legmagasabb hőmérséklet: %s %d:%d %d fok.\n", maxA.Telepules,maxA.Idő.getHours(),maxA.Idő.getMinutes(),maxA.Homerseklet);
        System.out.println("4. feladat");
        boolean szelcsend = false;
        for (Adatok x: lista)
        {
            String szel = x.Erosseg+x.Szelirany;
            if(szel.equals("00000"))
            {
                szelcsend =true;
                System.out.printf("%s %d:%d\n", x.Telepules,x.Idő.getHours(),x.Idő.getMinutes());
            }
        }
        if(!szelcsend)
        {
            System.out.println("Nem volt szélcsend a mérések idején.");
        }
        HashSet<String> varosok = new HashSet<String>();
        for (Adatok x: lista)
        {
            varosok.add(x.Telepules);
        }
        System.out.println("5. feladat");
        ArrayList<Integer> nem = new ArrayList<>();
        nem.add(1);
        nem.add(7);
        nem.add(13);
        nem.add(19);
        for (String Varos: varosok)
        {
            boolean na;
            int szam,ho;
            szam=1;
            ho=0;
            min =1000;
            max = -1000;
            HashSet<Integer> orak = new HashSet<Integer>();
            for (Adatok adat: lista)
            {

                if(adat.Telepules.equals(Varos))
                {
                    orak.add(adat.Idő.getHours());
                    ho+=adat.Homerseklet;
                    szam++;
                    if(adat.Homerseklet>max)max = adat.Homerseklet;
                    if(adat.Homerseklet<min)min = adat.Homerseklet;
                }
            }
            na = orak.containsAll(nem);
            if(!na) System.out.printf("%s NA; ",Varos);
            else
            {
                int eredmeny = (int)(ho/szam);
                System.out.printf("%s Középhőmérséklet: %d; ",Varos, eredmeny);

            }
            System.out.printf("Hőmérséklet-ingadozás: %d\n", max-min);


        }
        System.out.println("6. feladat\nA fájlok elkészültek");
        for (String y: varosok)
        {
            FileWriter kiiras = new FileWriter(y+".txt");
            kiiras.write(y+"\n");
            for (Adatok x: lista)
            {

                if(y.equals(x.Telepules))
                {

                    kiiras.write(String.format("%d:%d ",x.Idő.getHours(),x.Idő.getMinutes()));


                    for (int i =0;i<Integer.parseInt(x.Erosseg);i++)
                    {

                        kiiras.write("#");
                    }

                    kiiras.append("\n");
                }

            }
            kiiras.close();
        }

    }
}
