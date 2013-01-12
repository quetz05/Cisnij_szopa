package View;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import Controler.Kontroler;
import Model.Pozycja;



/**klasa Ramki programu okreœlaj¹ca ramkê programu*/
public class Ramka extends JFrame implements Runnable
{
	
	/**szerokoœæ ramki*/
	static final int SZEROKOSC = 800;
	/**wysokoœæ ramki*/
    static final int WYSOKOSC = 540;
    /**opóŸnienie w¹tku View*/
    private static final int opoznienie=50;
    /**referencja do kontrolera (klasa Kontroler)*/
    private Kontroler kontroler;
    /**mapa zawieraj¹ca dane do wyœwietlenia na ekranie*/
    public Map<String, Integer> daneDoWyswietlenia;
    
	

	/**konstruktor ramki odpowiedzialny za stworzenie ramki, obiektu klasy Panel oraz potrzebnych elementów dla View*/
    public Ramka(Kontroler gra)
    {
    	
    	kontroler = gra;
    	
    	daneDoWyswietlenia = new HashMap<String, Integer>();
    	
    	//zapewnienie braku mo¿liwoœci zmiany wielkoœci ramki
    	setResizable(false);
    	//zapewnia mo¿liwoœæ zamkniêcia ramki w dowolnej chwili
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	//ustalenie rozmiaru ramki
    	setSize(SZEROKOSC, WYSOKOSC);
    	//ustawienie koloru t³a
    	setBackground(Color.white);
    	//tytu³ ramki
    	setTitle("BOMBERMAN by Quetz v1.0");
    	//w³¹cza widocznoœæ ramki
    	setVisible(true);

    	
   	
    	//tworzenie obiektu: panel
    	Panel panel = new Panel(this);
    	add(panel);
    	panel.setFocusable(true);

    }
    
    
    /**metoda run() w¹tku View odpowiedzialna za wywo³ywanie metody "repaint()" i pobieraj¹ca odpowiednie dane*/
    public void run() 
    {
    	
    	  //czas poprzedni; ró¿nica czasu
		  long czasPrzed, roznicaCzasu, sleep;

	      czasPrzed = System.currentTimeMillis();
	      


	      while(true)
	      {
	    	  	    	 
	    	  
	    	  	//wczytywanie danych bohatera do mapy
		      	kontroler.czytajDaneBohatera();
	    	  
	    	  	//wywo³anie metody repaint()
	            repaint();

	            roznicaCzasu = System.currentTimeMillis() - czasPrzed;
	            sleep = opoznienie - roznicaCzasu;

	            if (sleep < 0)
	                sleep = 2;
	            
	            try 
	            {
	                Thread.sleep(sleep);
	            } catch (InterruptedException e) {
	                System.out.println("B³¹d w¹tku View");
	            }

	            czasPrzed = System.currentTimeMillis();
	        }
	}

    
    /**metoda odbieraj¹ca sygna³ od klasy Panel jaki przycisk zosta³ wciœniêty i wysy³aj¹ca informacjê do kontrolera*/
    void sygnalKlawisz(Klawisze k)
    {
		switch(k)
		{
		
			//strza³ka w górê
			case UP:
				kontroler.sterowanie(Klawisze.UP);
				break;
			
			//strza³ka w dó³
			case DOWN:
				kontroler.sterowanie(Klawisze.DOWN);
				break;
			
			//strza³ka w lewo
			case LEFT:
				kontroler.sterowanie(Klawisze.LEFT);
				break;
					
			//strza³ka w prawo
			case RIGHT:
				kontroler.sterowanie(Klawisze.RIGHT);
				break;
			
			//spacja
			case SPACE:		
				kontroler.sterowanie(Klawisze.SPACE);
				break;
				
			case PAUSE:
				kontroler.sterowanie(Klawisze.PAUSE);
				break;
		
		}	
    	
    }
    

    /**metoda zwracaj¹ca planszê gry z kontrolera*/
    Pozycja[][] planszaGry() { return kontroler.zwrocPlansze(); }
    
    /**metoda zwracaj¹ca dane stworzenia, które maj¹ wyœwietliæ siê na ekranie*/
    int zwrocDaneObiektu(String wrog, int nr, String dane) {return kontroler.zwrocDaneStworzen(wrog,nr,dane);}
    	
    /**metoda zwracaj¹ca wartoœæ zmiennej z kontrolera odpowiedzialnej za koniec gry*/
    boolean czyKoniecGry(){return kontroler.czyKoniecGry();}
    /**metoda zwracaj¹ca wartoœæ zmiennej z kontrolera odpowiedzialnej za wygran¹*/
    boolean czyWygrana(){return kontroler.czyWygrana();}
    /**metoda sprawdzaj¹ca czy trwa pauza*/
    boolean czyPauza(){return kontroler.czyPauza();}
}
