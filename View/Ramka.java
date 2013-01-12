package View;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import Controler.Kontroler;
import Model.Pozycja;



/**klasa Ramki programu okre�laj�ca ramk� programu*/
public class Ramka extends JFrame implements Runnable
{
	
	/**szeroko�� ramki*/
	static final int SZEROKOSC = 800;
	/**wysoko�� ramki*/
    static final int WYSOKOSC = 540;
    /**op�nienie w�tku View*/
    private static final int opoznienie=50;
    /**referencja do kontrolera (klasa Kontroler)*/
    private Kontroler kontroler;
    /**mapa zawieraj�ca dane do wy�wietlenia na ekranie*/
    public Map<String, Integer> daneDoWyswietlenia;
    
	

	/**konstruktor ramki odpowiedzialny za stworzenie ramki, obiektu klasy Panel oraz potrzebnych element�w dla View*/
    public Ramka(Kontroler gra)
    {
    	
    	kontroler = gra;
    	
    	daneDoWyswietlenia = new HashMap<String, Integer>();
    	
    	//zapewnienie braku mo�liwo�ci zmiany wielko�ci ramki
    	setResizable(false);
    	//zapewnia mo�liwo�� zamkni�cia ramki w dowolnej chwili
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	//ustalenie rozmiaru ramki
    	setSize(SZEROKOSC, WYSOKOSC);
    	//ustawienie koloru t�a
    	setBackground(Color.white);
    	//tytu� ramki
    	setTitle("BOMBERMAN by Quetz v1.0");
    	//w��cza widoczno�� ramki
    	setVisible(true);

    	
   	
    	//tworzenie obiektu: panel
    	Panel panel = new Panel(this);
    	add(panel);
    	panel.setFocusable(true);

    }
    
    
    /**metoda run() w�tku View odpowiedzialna za wywo�ywanie metody "repaint()" i pobieraj�ca odpowiednie dane*/
    public void run() 
    {
    	
    	  //czas poprzedni; r�nica czasu
		  long czasPrzed, roznicaCzasu, sleep;

	      czasPrzed = System.currentTimeMillis();
	      


	      while(true)
	      {
	    	  	    	 
	    	  
	    	  	//wczytywanie danych bohatera do mapy
		      	kontroler.czytajDaneBohatera();
	    	  
	    	  	//wywo�anie metody repaint()
	            repaint();

	            roznicaCzasu = System.currentTimeMillis() - czasPrzed;
	            sleep = opoznienie - roznicaCzasu;

	            if (sleep < 0)
	                sleep = 2;
	            
	            try 
	            {
	                Thread.sleep(sleep);
	            } catch (InterruptedException e) {
	                System.out.println("B��d w�tku View");
	            }

	            czasPrzed = System.currentTimeMillis();
	        }
	}

    
    /**metoda odbieraj�ca sygna� od klasy Panel jaki przycisk zosta� wci�ni�ty i wysy�aj�ca informacj� do kontrolera*/
    void sygnalKlawisz(Klawisze k)
    {
		switch(k)
		{
		
			//strza�ka w g�r�
			case UP:
				kontroler.sterowanie(Klawisze.UP);
				break;
			
			//strza�ka w d�
			case DOWN:
				kontroler.sterowanie(Klawisze.DOWN);
				break;
			
			//strza�ka w lewo
			case LEFT:
				kontroler.sterowanie(Klawisze.LEFT);
				break;
					
			//strza�ka w prawo
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
    

    /**metoda zwracaj�ca plansz� gry z kontrolera*/
    Pozycja[][] planszaGry() { return kontroler.zwrocPlansze(); }
    
    /**metoda zwracaj�ca dane stworzenia, kt�re maj� wy�wietli� si� na ekranie*/
    int zwrocDaneObiektu(String wrog, int nr, String dane) {return kontroler.zwrocDaneStworzen(wrog,nr,dane);}
    	
    /**metoda zwracaj�ca warto�� zmiennej z kontrolera odpowiedzialnej za koniec gry*/
    boolean czyKoniecGry(){return kontroler.czyKoniecGry();}
    /**metoda zwracaj�ca warto�� zmiennej z kontrolera odpowiedzialnej za wygran�*/
    boolean czyWygrana(){return kontroler.czyWygrana();}
    /**metoda sprawdzaj�ca czy trwa pauza*/
    boolean czyPauza(){return kontroler.czyPauza();}
}
