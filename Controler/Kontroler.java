package Controler;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import View.Klawisze;
import View.Panel;
import Model.Bomba;
import Model.Dzialanie;
import Model.Plansza;
import View.Ramka;
import Model.Pozycja;


/**
 * klasa Kontroler, odpowiedzialna za komunikacj� mi�dzy klasami pakiet�w:
 * View i Model oraz podejmuj�ca najwa�niejsze decyzje w programie
 */
public class Kontroler
{
	
   /** poziom gry*/
   private Plansza poziom;
   /** ramka programu*/
   private Ramka ramka;
   
   /**zmienna okre�laj�ca ilo�� poziom�w w grze*/
   private final int iloscPoziomow = 3;
   
   /** w�tek modelu (planszy) */
   private Thread watekModel;
   /** w�tek widoku (ramki) */
   private Thread watekView;
   /**zmienna okre�laj�ca czy gra jest zatrzymana*/
   private boolean pauza = false;
   /**tablica zmiennych okre�laj�ca czy w�tek danego poziomu powinien si� ju� zako�czy�*/
   private List <Boolean> koniecPoziomu;
   
   
   /**zmienna okre�laj�ca czy gra powinna si� zako�czy�*/
   private static boolean koniecGry = false;
   /**zmienna przechowuj�ca warto�� okre�laj�c� wygran�/przegran�*/
   private static boolean wygrana = false;
   /**zmienna przechowuj�ca warto�� okre�laj�c� czy wszyscy wrogowie na mapie zostali zniszczeni i czy posta� gracza nadal ma �ycia*/
   private static boolean koniecRundy = false;
   
   /**metoda zwracaj�ca warto�� zmiennej koniecGry (sprawdzaj�ca czy gra powinna si� zako�czy�)*/
   public boolean czyKoniecGry(){return koniecGry;}
   /**metoda zwracaj�ca warto�� zmiennej wygrana (sprawdzaj�ca czy gracz wygra�)*/
   public boolean czyWygrana(){return wygrana;}
   /**metoda zwracaj�ca warto�� zmiennej koniecRundy (sprawdzaj�ca czy wszystkie potwory zosta�y zniszczone i posta� gracza nadal ma �ycia)*/
   public boolean czyKoniecRundy(){return koniecRundy;}
   /**metoda zwracaj�ca warto�� zmiennej pauza (sprawdzaj�ca czy gra jest zatrzymana)*/
   public boolean czyPauza(){return pauza;}
   /**metoda zwracaj�ca warto�� zmiennej okre�laj�cej zabicie w�tku danego poziomu*/
   public boolean czyZabicWatek(int i){return koniecPoziomu.get(i-1);}
   

   
   /**Konstruktor klasy Kontroler tworz�cy nowy poziom gry i wywo�uj�cy w�tki*/
   public Kontroler()
   {
	   ramka = new Ramka(this);
	   poziom = new Plansza(1,this,2,1,1,20);
	   
	   //wczytywanie danych bohatera
	   czytajDaneBohatera();

       
       koniecPoziomu = new ArrayList<Boolean>();
       
       for(int i=0; i<iloscPoziomow; i++)
    	   koniecPoziomu.add(false);
	   

  	   watekModel = new Thread(poziom);
   	   watekModel.start();
   	   
	   watekView = new Thread(ramka); 
   	   watekView.start();
   		
   	   
   	   Panel.playSound("intro.wav");
   }
   
   /**metoda wczytuj�ca kolejny poziom gry*/
   private void wczytajPoziom(int nrPoziomu)
   {
	   		koniecRundy=false;

			int punkty = poziom.hero1.zwrocPunkty();
			int zycia = poziom.hero1.zwrocIloscZyc();
			int zasieg = poziom.hero1.zwrocZasieg();
			short bomby = (short) poziom.hero1.zwrocMaxBomb();
			
			koniecPoziomu.set(nrPoziomu-1, true);
			
			switch(nrPoziomu)
			{
				case 1:
					poziom = new Plansza(2,this,4,2,2,30);
					break;
				case 2:
					poziom = new Plansza(3,this,2,2,4,25);
					break;

				
				default:
			   		System.out.println("B��d wczytania poziomu...");
					break;

			}
			poziom.hero1.ustawZycia(zycia);
			poziom.hero1.dodajPunkty(punkty);
			poziom.hero1.ustalZasieg(zasieg);
			poziom.hero1.dodajBomby((short)(bomby-1));

			
	  	   	watekModel = new Thread(poziom);
	  	   	watekModel.start();	   
   }
   
   /**metoda wczytuj�ca do mapy dane bohatera, kt�re b�d� potem wy�wietlone na ekranie*/
   public void czytajDaneBohatera()
   {
	   ramka.daneDoWyswietlenia.clear();

	   ramka.daneDoWyswietlenia.put("zycia", poziom.hero1.zwrocIloscZyc());
	   ramka.daneDoWyswietlenia.put("iloscBomb", poziom.hero1.zwrocIloscBomb() );
	   ramka.daneDoWyswietlenia.put("punkty", poziom.hero1.zwrocPunkty() );
	   ramka.daneDoWyswietlenia.put("zasieg", poziom.hero1.zwrocZasieg());
	   ramka.daneDoWyswietlenia.put("poziom", poziom.ktoryPoziom());
   }
   
   
   /**metoda sprawczaj�ca czy powinien zosta� wczytany kolejny poziom*/
   private void czyKolejnyPoziom()
   {
	   if(poziom.wrog.size()==0 && poziom.wrog2.size()==0 && poziom.wrog3.size()==0 && poziom.hero1.zwrocIloscZyc()>0)
	 		  koniecRundy=true;

	   
	   if(poziom.hero1.getPozX() == 1 && poziom.hero1.getPozY()==0 && koniecRundy)
			if(iloscPoziomow!=poziom.ktoryPoziom())
				wczytajPoziom(poziom.ktoryPoziom());

   }
   
   
   

   
   /**metoda sprawdzaj�ca czy gracz ju� wygra�/przegra� rozgrywk� i je�li tak - ko�cz�ca gr�/wczytuj�ca nowy poziom*/
   public boolean czyEkranKoncowy()
   {
	   	
	   	  czyKolejnyPoziom();
	   
	 	  if(koniecRundy && iloscPoziomow==poziom.ktoryPoziom() && poziom.hero1.getPozX() == 1 && poziom.hero1.getPozY()==0)
	 	  {
	 		koniecPoziomu.set(poziom.ktoryPoziom()-1, true);
	 		koniecGry = true;
	 		wygrana=true;
 			return true;
	 		
	 	  }
	 	  else if(poziom.hero1.zwrocIloscZyc()<=0)
	 	  {
	 		  koniecPoziomu.set(poziom.ktoryPoziom()-1, true);
	 		  koniecGry=true;
	 		  wygrana=false;
	 		  return true;

	 	  }
	 	  
	 	  return false;
   }
   
  
   
   /**metoda zwracaj�ca plansz� gry z Modelu*/
   public Pozycja [][] zwrocPlansze() {return poziom.plansza; }
   
   /**metoda zwracaj�ca dane dla wybranego typu stworze� (np. ich pozycja na planszy)*/
   public int zwrocDaneStworzen(String stworzenie, int nr, String dane) 
   { 
	   switch(stworzenie)
	   {
	   		case "hero1":
	   			
	   			switch(dane)
	   			{
	   				case "pozX":
	   					return poziom.hero1.getPozX();
	   				case "pozY":
	   					return poziom.hero1.getPozY();
	   				case "wsX":
	   					return poziom.hero1.getWsX();
	   				case "wsY":
	   					return poziom.hero1.getWsY();
	   			}

	   
	   		
	   		case "wrog1":
	   		
	   			switch(dane)
	   			{
	   				case "pozX":
	   					return poziom.wrog.get(nr).getPozX();
	   				case "pozY":
	   					return poziom.wrog.get(nr).getPozY();
	   				case "wsX":
	   					return poziom.wrog.get(nr).getWsX();
	   				case "wsY":
	   					return poziom.wrog.get(nr).getWsY();
	   				case "size":
	   					return poziom.wrog.size();
	   			}

	   			
	   		case "wrog3":
	   			
	   			switch(dane)
	   			{
	   				case "pozX":
	   					return poziom.wrog3.get(nr).getPozX();
	   				case "pozY":
	   					return poziom.wrog3.get(nr).getPozY();
	   				case "wsX":
	   					return poziom.wrog3.get(nr).getWsX();
	   				case "wsY":
	   					return poziom.wrog3.get(nr).getWsY();
	   				case "size":
	   					return poziom.wrog3.size();
	   			}
	   				   
	   			
	   		case "wrog2":
	   			
	   			switch(dane)
	   			{
	   				case "pozX":
	   					return poziom.wrog2.get(nr).getPozX();
	   				case "pozY":
	   					return poziom.wrog2.get(nr).getPozY();
	   				case "wsX":
	   					return poziom.wrog2.get(nr).getWsX();
	   				case "wsY":
	   					return poziom.wrog2.get(nr).getWsY();
	   				case "size":
	   					return poziom.wrog2.size();
	   			}

	   			
	   }
	   
			return 0;
   }

   
   

   /** funkcja odbierajaca sygnaly klawiszy z View i przekazuj�ca rozkazy do Modelu*/
   public void sterowanie(Klawisze k)
   {
	   
	   if(!poziom.hero1.sprawdzRuch())
	   {
			
		   switch(k)
			{
		   
			
				//strza�ka w g�r�
				case UP:
					poziom.hero1.dzialanie=Dzialanie.GORA;
					break;
				
				//strza�ka w d�
				case DOWN:
					poziom.hero1.dzialanie=Dzialanie.DOL;
					break;
				
				
				//strza�ka w lewo
				case LEFT:
					poziom.hero1.dzialanie=Dzialanie.LEWO;
					break;
						
				//strza�ka w prawo
				case RIGHT:
					poziom.hero1.dzialanie=Dzialanie.PRAWO;
					break;
					
				default:
					poziom.hero1.dzialanie=Dzialanie.NIC;
					break;
				
				//spacja
				case SPACE:		
					poziom.hero1.dzialanie=Dzialanie.BOMBA;
					break;
					
				//pauza
				case PAUSE:
					if(pauza==false)
						pauza=true;
					else
						pauza=false;
					break;
			
			}
	   }
   }

   /**metoda sprawdzaj�ca kolizje mi�dzy stworzeniami na podstawie ich wsp�rz�dnych na planszy*/
   public void sprawdzKolizje()
   {
	   
	   Rectangle bohater = poziom.hero1.zwrocPozycje();

       for(int i = 0; i<poziom.wrog.size(); i++) 
       {
    	   Rectangle wrog1 = poziom.wrog.get(i).zwrocPozycje();
     

           if (bohater.intersects(wrog1)) 
           {
        	   poziom.hero1.godMode=true;
        	   poziom.hero1.strataZycia();
           }
           
       }

       bohater = poziom.hero1.zwrocPozycje();

       for(int i = 0; i<poziom.wrog3.size(); i++) 
       {
    	   Rectangle wrog3 = poziom.wrog3.get(i).zwrocPozycje();
     

           if (bohater.intersects(wrog3)) 
           {
        	   poziom.hero1.godMode=true;
        	   poziom.hero1.strataZycia();
           }
           
       }
       
       bohater = poziom.hero1.zwrocPozycje();

       for(int i = 0; i<poziom.wrog2.size(); i++) 
       {
    	   Rectangle wrog2 = poziom.wrog2.get(i).zwrocPozycje();
     

           if (bohater.intersects(wrog2)) 
           {
        	   poziom.hero1.godMode=true;
        	   poziom.hero1.strataZycia();
           }
           
       }
       
       
     }
		   
		   
   
   /**metoda sprawdzaj�ca kolizje wybuch�w konkretnej bomby ze stworzeniami*/
   public void sprawdzWybuchy(Bomba bomba)
   {
	   ArrayList<Rectangle> ogien = poziom.miejscaWybuchu(bomba);
	   
	   Rectangle bohater;
	   Rectangle wybuch;

	   for(int j = 0; j<ogien.size(); j++) 
	   {
		   	wybuch = ogien.get(j);
		    bohater = poziom.hero1.zwrocPozycje();
		   
		   //sprawdzenie czy bohater nie zosta� trafiony bomb�
           if (bohater.intersects(wybuch) && poziom.hero1.godMode==false)
           {
        	   poziom.hero1.godMode=true;
        	   poziom.hero1.strataZycia();
           }
		   
           //sprawdzenie czy wr�g1 nie zosta� trafiony bomb�
		   for(int i = 0; i<poziom.wrog.size(); i++) 
		   {
			   Rectangle wrog1 = poziom.wrog.get(i).zwrocPozycje();
     

			   if (ogien.get(j).intersects(wrog1)) 
				   poziom.wrog.get(i).strataZycia();
           
		   }
		   
           //sprawdzenie czy wr�g2 nie zosta� trafiony bomb�
		   for(int k = 0; k<poziom.wrog2.size(); k++) 
		   {
			   Rectangle wrog2 = poziom.wrog2.get(k).zwrocPozycje();
     

			   if (ogien.get(j).intersects(wrog2)) 
				   poziom.wrog2.get(k).strataZycia();
           
		   }
		   
           //sprawdzenie czy wr�g3 nie zosta� trafiony bomb�
		   for(int i = 0; i<poziom.wrog3.size(); i++) 
		   {
			   Rectangle wrog3 = poziom.wrog3.get(i).zwrocPozycje();
     

			   if (ogien.get(j).intersects(wrog3)) 
				   poziom.wrog3.get(i).strataZycia();
           
		   }
           
    }
 
  }	   

	
}
