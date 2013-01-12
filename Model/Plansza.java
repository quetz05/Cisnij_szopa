package Model;

import java.awt.Rectangle;
import java.util.*;
import Controler.Kontroler;


/**Klasa planszy, tworz¹ca mapê poziomu i obiekty na niej*/
public class Plansza implements Runnable
{

	/**bohater - postaæ gracza*/
	public Bohater hero1 = new Bohater(1,0);
	
	/**ilosc wrogow typu 1*/
	private int iloscWrogow1;
	/**ilosc wrogow typu 2*/
	private int iloscWrogow2;
	/**ilosc wrogow typu 3*/
	private int iloscWrogow3;
	
	/**zmienna okreœlaj¹ca nr poziomu*/
	private int nrPoziomu;
	
	/**ilosc murków na planszy*/
	private int iloscMurow;
	
	/**lista wrogow typu 1*/
	public List<Wrog1> wrog;
	
	/**lista wrogow typu 1*/
	public List<Wrog2> wrog2;
	
	/**lista wrogow typu 3*/
	public List<Wrog3> wrog3;
	
	/**dwuwymiarowa plansza do gry*/
	public Pozycja plansza[][];
	
	/**opóŸnienie w¹tku model*/
    private static final int opoznienie=25;

    /**referencja na kontroler*/
    private Kontroler kontroler;
    
    /**metoda zwracaj¹ca listê miejsc wybuchu bomby*/
	public ArrayList<Rectangle> miejscaWybuchu(Bomba bomba){return bomba.wybuchy;}
    
    /**konstruktor klasy Plansza tworz¹cy now¹ planszê oraz w¹tek View przyjmuj¹c dane: nr poziomu, referencjê na kontroler, iloœæ poszczególnych wrogów oraz murów*/
    public Plansza(int poziom, Kontroler gra, int w1, int w2, int w3, int mury )
    {
    	iloscWrogow1 = w1;
    	iloscWrogow2 = w2;
    	iloscWrogow3 = w3;
    	iloscMurow = mury;
    	nrPoziomu=poziom;
    	
    	kontroler = gra;
    	
    	plansza = new Pozycja [13][15];
    	
    	wrog = new LinkedList<Wrog1>();
    	wrog2 = new LinkedList<Wrog2>();
    	wrog3 = new LinkedList<Wrog3>();
    	
    	tworzPoziom();
    	
    }
    
	/**metoda zwracaj¹ca nr poziomu*/
	public int ktoryPoziom(){return nrPoziomu;}
    
    
	 /**tworzenie planszy poziomu i umieszczanie na niej obiektów*/
	 public void tworzPoziom()
	 {
			
			Pozycja[][] nowaPlansza = tworzPlansze();
					
			tworzMurki(nowaPlansza);
			tworzWrogow(nowaPlansza);
					
			//przypisanie nowej planszy do zmiennej
			for(int i=0; i<plansza.length;i++)
				for(int j=0; j< plansza[0].length;j++)
					plansza[i][j]=nowaPlansza[i][j];
			
	}
	
	
	/**tworzenie wrogow na planszy*/
	private void tworzWrogow(Pozycja[][] nowaPlansza)
	{
				//wstawianie wrogow1
				for(int z = 0; z<iloscWrogow1;z++)
					wrog.add(new Wrog1());
				
				int stworzeniWrogowie1 = 0;
				
				while(stworzeniWrogowie1<iloscWrogow1)
				{
					if(tworzWroga(Pozycja.WROG1, nowaPlansza, wrog.get(stworzeniWrogowie1))==true)
						++stworzeniWrogowie1;
				}
				
				
				//wstawianie wrogow2
				for(int z = 0; z<iloscWrogow2;z++)
					wrog2.add(new Wrog2());
				
				int stworzeniWrogowie2 = 0;
				
				while(stworzeniWrogowie2<iloscWrogow2)
				{
					if(tworzWroga(Pozycja.WROG2, nowaPlansza, wrog2.get(stworzeniWrogowie2))==true)
						++stworzeniWrogowie2;
				}
				
				
				//wstawianie wrogow3
				for(int z = 0; z<iloscWrogow3;z++)
					wrog3.add(new Wrog3());
				
				int stworzeniWrogowie3 = 0;
				
				while(stworzeniWrogowie3<iloscWrogow3)
				{
					if(tworzWroga(Pozycja.WROG3, nowaPlansza, wrog3.get(stworzeniWrogowie3))==true)
						++stworzeniWrogowie3;
				}
		
	}
	
	
	
	/**tworzenie murkow na planszy*/
	private void tworzMurki(Pozycja[][] nowaPlansza)
	{

		int iloscMurkow = 0;
		
		//wstawianie murkow
		while(iloscMurkow<iloscMurow)
		{
			if(tworzMurek(nowaPlansza)==true)
				++iloscMurkow;		
		}
		
	}
	
	
	/**tworzenie szkieletu planszy*/
	private Pozycja[][] tworzPlansze()
	{

				//nowa plansza
				// WOLNE - wolna przestrzen
				// BLOK - niezniszczalny BLOK
				// WOLNE2 - pole musi pozostaæ puste!
				// MUR - murek
				// BOMBA - bomba
				// WYBUCH - wybuch
				// WROG1 - wrog1
				// BOHATER - bohater
				Pozycja nowaPlansza [][] = {
					{Pozycja.BLOK	,Pozycja.PORTAL_OFF	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	},
					{Pozycja.BLOK	,Pozycja.BOHATER	,Pozycja.WOLNE2	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.BLOK	},
					{Pozycja.BLOK	,Pozycja.WOLNE2		,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	},
					{Pozycja.BLOK	,Pozycja.WOLNE  	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.BLOK	},
					{Pozycja.BLOK	,Pozycja.WOLNE 		,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	},
					{Pozycja.BLOK	,Pozycja.WOLNE 		,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.BLOK	},
					{Pozycja.BLOK	,Pozycja.WOLNE 		,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	},
					{Pozycja.BLOK	,Pozycja.WOLNE 		,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.BLOK	},
					{Pozycja.BLOK	,Pozycja.WOLNE 		,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	},
					{Pozycja.BLOK	,Pozycja.WOLNE 		,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.BLOK	},
					{Pozycja.BLOK	,Pozycja.WOLNE 		,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	,Pozycja.WOLNE	,Pozycja.BLOK	},
					{Pozycja.BLOK	,Pozycja.WOLNE 		,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.WOLNE	,Pozycja.BLOK	},
					{Pozycja.BLOK	,Pozycja.BLOK		,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	,Pozycja.BLOK	},
					
				};

		return nowaPlansza;

	}
   
    
    /**metoda tworz¹ca losowo nagrody podczas wybuchu bomby*/
    private int tworzNagrody()
    {
    	Random generator = new Random();
 		
 		int losowanie = generator.nextInt(8);
 		
 		if(losowanie==4)
 			return 1;
 		else if(losowanie==5)
 			return 2;

 		return 0;
    }
    
    
    
	/**metoda sprawdzaj¹ca w jakim losowym miejscu mo¿na postawiæ murek*/
	private boolean tworzMurek(Pozycja [][]tablica)
	{
		
	    Random generator = new Random();
		
		int murekX = generator.nextInt(13)+1;
		int murekY = generator.nextInt(11)+1;
		
		if(tablica[murekY][murekX]==Pozycja.WOLNE)
		{
			tablica[murekY][murekX]=Pozycja.MUR;
			return true;
		}
		else
			return false;		
	}
	
	
	/**metoda sprawdzaj¹ca w jakim losowym miejscu mo¿na stworzyæ wroga danego typu*/
	private boolean tworzWroga(Pozycja pozycja, Pozycja [][]tablica, Stworzenie wrog)
	{
			
		    Random generator = new Random();
			
			int wrogX = generator.nextInt(13)+1;
			int wrogY = generator.nextInt(11)+1;
			
			if(tablica[wrogY][wrogX]==Pozycja.WOLNE ||tablica[wrogY][wrogX]==pozycja)
			{
				tablica[wrogY][wrogX]=pozycja;
				wrog.setPozX(wrogX);
				wrog.setPozY(wrogY);
				wrog.setWsX(wrogX*40);
				wrog.setWsY(wrogY*40);
				return true;
			}
			else
				return false;		
	}
	


	/**metoda odpowiedzialna za "wygaszenie" bomby gdy skoñczy siê wybuch*/
    void koniecWybuchu(Bomba bomba)
	{

    	kontroler.sprawdzWybuchy(bomba);
    	
		int x;
		int y;

    	
    	for(int i=0;i<bomba.wybuchy.size();i++)
    	{
    		y = (int)bomba.wybuchy.get(i).getY();
    		x = (int)bomba.wybuchy.get(i).getX();
		
    		if(plansza[y/40][x/40]!=Pozycja.BOMBA && plansza[y/40][x/40]!=Pozycja.BOMBA_NAGRODA && plansza[y/40][x/40]!=Pozycja.ZASIEG_NAGRODA)
    			plansza[y/40][x/40]=Pozycja.WOLNE;
		
    	}
		
		bomba.wybuchy.clear();

	}
	
	
	
	
	/**metoda odpowiedzialna za wybuch bomby na planszy*/
	void wybuchBomby(Bomba bomba)
	{

		int y = (int)bomba.wybuchy.get(0).getY();
		int x = (int)bomba.wybuchy.get(0).getX();
		int nagroda;
		
		plansza[y/40][x/40]=Pozycja.WYBUCH;
		
		for(int i=0; i<bomba.wybuchy.size();i++)
		{
			y = (int)bomba.wybuchy.get(i).getY();
			x = (int)bomba.wybuchy.get(i).getX();
			
			if(plansza[y/40][x/40]==Pozycja.MUR)
			{
				nagroda = tworzNagrody();
				
				if(nagroda==1)
					plansza[y/40][x/40]=Pozycja.BOMBA_NAGRODA;
				
				else if(nagroda==2)
					plansza[y/40][x/40]=Pozycja.ZASIEG_NAGRODA;
				else
					plansza[y/40][x/40]=Pozycja.WYBUCH;

			}
			else if(plansza[y/40][x/40]!=Pozycja.BOMBA)
					plansza[y/40][x/40]=Pozycja.WYBUCH;
		}
		
		
		kontroler.sprawdzWybuchy(bomba);
		
		
	}
	
	
	/**metoda obliczaj¹ca zasiêg wybuchu bomby*/
	void zasiegWybuchu(Bomba bomba)
	{
		
		int x = bomba.pozX;
		int y = bomba.pozY;

		bomba.wybuchy.add(new Rectangle(x*40,y*40,40,40));

		//wybuch w dó³
		promienWybuchu(1, 0, bomba);
		//wybuch w górê
		promienWybuchu(-1, 0, bomba);
		//wybuch w prawo
		promienWybuchu(0, 1, bomba);
		//wybuch w lewo
		promienWybuchu(0, -1, bomba);
		
	}
	
	/**metoda obliczaj¹ca promieñ wybuchu bomby w jednym kierunku*/
	private void promienWybuchu(int a, int b, Bomba bomba)
	{
		
			int x = bomba.pozX;
			int y = bomba.pozY;

			for(int i = 0; i<hero1.zasieg; i++)
			{
				//trafienie na murek
				if(plansza[y+a][x+b]==Pozycja.MUR)
				{
					bomba.wybuchy.add(new Rectangle((x+b)*40,(y+a)*40,40,40));
					break;
				}
				else if(plansza[y+a][x+b]!=Pozycja.BLOK && plansza[y+a][x+b]!=Pozycja.PORTAL && plansza[y+a][x+b]!=Pozycja.PORTAL_OFF)
				{
						bomba.wybuchy.add(new Rectangle((x+b)*40,(y+a)*40,40,40));
						y=y+a;
						x=x+b;
				}
				else 
					break;
				}
		
		
	}
	

	   /**metoda wywo³uj¹ca funkcje steruj¹ce Stworzeniami na mapie oraz odpowiedzialna za bomby*/
	   private void sterowanieStworzeniami()
	   {
		   
		   //ruch bohatera
			hero1.ruchStworzenia(plansza);
		   
		   

	 	  //ruch wrogów nr 1
	 	  for(int j = 0; j<wrog.size(); j++)
	 	  {
	 		  if(wrog.get(j).zwrocIloscZyc()<=0)
	 		  {
	 				wrog.remove(j);
	 				hero1.dodajPunkty(Wrog1.punkty);
	 				continue;
	 		  }
	 		  wrog.get(j).ruchStworzenia(plansza);
	 	  }
	 	  
	 	  
	 	  //ruch wrogów nr 2
	 	  for(int l = 0; l<wrog2.size(); l++)
	 	  {
	 		  if(wrog2.get(l).zwrocIloscZyc()<=0)
	 		  {
	 				wrog2.remove(l);
	 				hero1.dodajPunkty(Wrog2.punkty);
	 				continue;
	 		  }
	 		  wrog2.get(l).ruchStworzenia(plansza);
	 	  }
	 	  
	 	  
	 	  //ruch wrogów nr 3
	 	  for(int k = 0; k<wrog3.size(); k++)
	 	  {
	 		  if(wrog3.get(k).zwrocIloscZyc()<=0)
	 		  {
	 				wrog3.remove(k);
	 				hero1.dodajPunkty(Wrog3.punkty);
	 				continue;
	 		  }
	 		  wrog3.get(k).ruchStworzenia(plansza);
	 	  }
	 	  
		  
		  kontroler.czyEkranKoncowy();

	   }
	
	/**metoda steruj¹ca wybuchami bomb na planszy*/   
	private void sterowanieBombami()
	{
	 	  
		  for(int i = 0; i<hero1.maxBomb; i++)
		  {
	 		  if(hero1.odpaloneBomby[i])
	 			  hero1.postawBombe(hero1.bomby[i], i, this);
	 		  
	 		  if(hero1.dodajBomby>0 && hero1.iloscBomb==hero1.maxBomb)
	 		  {
					hero1.maxBomb= hero1.maxBomb+hero1.dodajBomby;
					hero1.dodajBomby=0;
					hero1.iloscBomb=hero1.maxBomb;
					hero1.bomby = new Bomba[hero1.maxBomb];
					hero1.odpaloneBomby = new boolean[hero1.maxBomb];
	 		  }
	 		  
		  }
	}
	   
	 

	/**metoda run() w¹tku View*/
	public void run() 
	{
		 
		  long czasPrzed, roznicaCzasu, sleep = 0;

		  //czas obecny w ms
	      czasPrzed = System.currentTimeMillis();

	      while(!kontroler.czyZabicWatek(nrPoziomu))
	      {

	    	  if(kontroler.czyPauza()==false)
	    	  {

	    	  	sterowanieStworzeniami();
	    	  	sterowanieBombami();
	    	  	
				kontroler.sprawdzKolizje();
				
				if(kontroler.czyKoniecRundy())
					plansza[0][1]=Pozycja.PORTAL;
	    	  
	    	  
	    	  	//roznica czasu
	            roznicaCzasu = System.currentTimeMillis() - czasPrzed;
	            //uspienie na konkretny czas watku View
	            sleep = opoznienie - roznicaCzasu;
	    	  

	            if (sleep < 0)
	                sleep = 2;
	            
	            try 
	            {
	                Thread.sleep(sleep);
	            } 
	            catch (InterruptedException e) 
	            {
	                System.out.println("B³¹d w¹tku Model");
	            }

	            czasPrzed = System.currentTimeMillis();
	    	  }
	    	  
	        }
	}


	
	
}

