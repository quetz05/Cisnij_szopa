package View;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import Model.Pozycja;


/**klasa panel odpowiedzialna za rysowanie obiekt�w oraz kontakt z u�ytkownikiem (odbieranie wci�ni�tych przycisk�w)*/
public class Panel extends JPanel implements KeyListener
{
	
	/**mapa zawieraj�ca obrazki do wy�wietlenia na ekran*/
	private Map<String, Image> obrazki;
    
    /**tablica przedstawiajaca kopie planszy poziomu*/
    private Pozycja poziom[][];
    
    /**referencja na ramk� (klasa Ramka)*/
    private Ramka ramka;
    
    

    /**konstruktor klasy panel tworz�cy KeyListenera oraz wczytuj�cy obrazki*/
	public Panel(Ramka mRamka)
	{	
		ramka = mRamka;
		
		//dodanie KeyListenera
		addKeyListener(this);
		wczytajObrazki();
	}
	
	
	   /**metoda odpowiedzialna za muzyk� w tle (tworz�ca oddzielny w�tek)*/
	   public static synchronized void playSound(final String url) 
	   {
		   //nowy w�tek muzyki
		    new Thread(new Runnable() 
		    { 
		      public void run() {
		        try 
		        {
		          //nowy klip
		          Clip clip = AudioSystem.getClip();
		          AudioInputStream inputStream = AudioSystem.getAudioInputStream(Panel.class.getResourceAsStream(url));
		          clip.open(inputStream);
		          //p�tla
		          clip.loop(99999); 
		        } 
		        catch (Exception e) 
		        {
		          System.err.println("B��d pliku d�wi�kowego (muzyka w tle)");
		        }
		      }
		    }).start();
		  }
	
	
	/**metoda wczytuj�ca obrazki do mapy*/
	private void wczytajObrazki()
	{
		obrazki = new HashMap<String, Image>();
		
        //wczytywanie obrazk�w
    	obrazki.put("BLOK",getImage("block.png"));	//0
    	obrazki.put("BOHATER",getImage("hero1.png"));	//1
    	obrazki.put("MUR",getImage("wall.png"));	//2
    	obrazki.put("BOMBA",getImage("bomb.png"));	//3
    	obrazki.put("WYBUCH",getImage("boom.png"));	//4
    	obrazki.put("WROG1",getImage("enemy1.png"));//5
    	obrazki.put("WROG2",getImage("enemy2.png"));//6
    	obrazki.put("WROG3",getImage("enemy3.png"));//7
    	obrazki.put("BOMBA_NAGRODA",getImage("bomb_reward.png"));//8
    	obrazki.put("ZASIEG_NAGRODA",getImage("boom_reward.png"));//9
    	obrazki.put("PORTAL",getImage("portal.png"));//10
    	obrazki.put("PORTAL_OFF",getImage("portal_off.png"));//11
	}
	
	
	/**metoda pobieraj�ca obrazek z danej �cie�ki*/
	private Image getImage(String string)
	{
		  URL obrazekUrl = this.getClass().getResource(string);
		  Toolkit tk = Toolkit.getDefaultToolkit();
		  return tk.getImage(obrazekUrl);
	}

	
	/**metoda paint odpowiedzialna za rysowanie obiekt�w oraz danych na ekranie*/
	public void paint(Graphics g)
	{

		
		if(ramka.czyPauza()==true)
		{
	        //ustawienie koloru i czcionki napisu
	        g.setFont(new Font("Helvetica", Font.BOLD, 60));
	        g.setColor(new Color(0,43,226));
			

	        g.drawString("PAUZA", 280, 200);
	        g.drawString("Wci�nij (p)", 240, 250);
	        


		}
		else
		{

			//czyszczenie planszy
			g.clearRect(0, 0, Ramka.SZEROKOSC, Ramka.WYSOKOSC);
		
			if(ramka.czyKoniecGry()==true)
				ekranKoncowy(g,ramka.czyWygrana());
			else
				rysujPlansze(g);

		}
	}
	
	
	/**metoda rysuj�ca plansz� oraz dane na podstawie danych otrzymanych od klasy Ramka*/
    private void rysujPlansze(Graphics g)
    {

    	poziom = ramka.planszaGry();
    	
      if(poziom!=null)  
      {
		for(int i=0; i< poziom.length; i++)
		{
			for(int j=0; j< poziom[0].length; j++)
			{
				switch (poziom[i][j])
				{
					case WOLNE:
						g.setColor(Color.white);
						g.fillRect(40*j, 40*i,40,40);
						break;
					
					case BLOK:
						g.drawImage(obrazki.get("BLOK"), 40*j, 40*i,this);
						break;
					
					case WOLNE2:
						g.setColor(Color.white);
						g.fillRect(40*j, 40*i,40,40);
						break;
						
					case MUR:
						g.drawImage(obrazki.get("MUR"), 40*j, 40*i,this);
						break;
				
					case BOMBA:
						g.drawImage(obrazki.get("BOMBA"), 40*j, 40*i,this);
						break;
						
					case WYBUCH:
						g.drawImage(obrazki.get("WYBUCH"), 40*j, 40*i,this);
						break;
						
					case ZASIEG_NAGRODA:
						g.drawImage(obrazki.get("ZASIEG_NAGRODA"), 40*j, 40*i,this);
						break;
						
					case BOMBA_NAGRODA:
						g.drawImage(obrazki.get("BOMBA_NAGRODA"), 40*j, 40*i,this);
						break;
	
					case PORTAL:
							g.drawImage(obrazki.get("PORTAL"), 40*j, 40*i,this);
						break;
						
					case PORTAL_OFF:
							g.drawImage(obrazki.get("PORTAL_OFF"), 40*j, 40*i,this);	
						break;
						
					default:
						break;
						
						
				}
					
			}
		}
		
		//rysowanie wrog�w1
		for(int i=0;i<ramka.zwrocDaneObiektu("wrog1",i,"size"); i++)
			g.drawImage(obrazki.get("WROG1"), ramka.zwrocDaneObiektu("wrog1",i,"wsX"), ramka.zwrocDaneObiektu("wrog1",i,"wsY"),this);
		
		//rysowanie wrog�w2
		for(int i=0;i<ramka.zwrocDaneObiektu("wrog2",i,"size"); i++)
			g.drawImage(obrazki.get("WROG2"), ramka.zwrocDaneObiektu("wrog2",i,"wsX"), ramka.zwrocDaneObiektu("wrog2",i,"wsY"),this);
		
		//rysowanie wrog�w3
		for(int i=0;i<ramka.zwrocDaneObiektu("wrog3",i,"size"); i++)
			g.drawImage(obrazki.get("WROG3"), ramka.zwrocDaneObiektu("wrog3",i,"wsX"), ramka.zwrocDaneObiektu("wrog3",i,"wsY"),this);
		
		//rysowanie bohatera
		g.drawImage(obrazki.get("BOHATER"), ramka.zwrocDaneObiektu("hero1",0,"wsX"), ramka.zwrocDaneObiektu("hero1",0,"wsY"),this);
		
		//wy�wietlanie statystyk
		wyswietlStatystyki(g);
      }
   }
    
    /**metoda KeyListenera sprawdzaj�ca jaki klawisz zosta� wci�ni�ty i u�ywaj�ca metody ramki (sygnalKlawisz) do przes�ania informacji dalej*/
	public void keyPressed(KeyEvent e) 
	{

			switch(e.getKeyCode())
			{
			
					//strza�ka w g�r�
					case 38:
						ramka.sygnalKlawisz(Klawisze.UP);
						break;
			
					//strza�ka w d�
					case 40:
						ramka.sygnalKlawisz(Klawisze.DOWN);
						break;
					
					
					//strza�ka w lewo
					case 37:
						ramka.sygnalKlawisz(Klawisze.LEFT);
						break;
							
					//strza�ka w prawo
					case 39:
						ramka.sygnalKlawisz(Klawisze.RIGHT);
						break;
					
					//spacja
					case 32:		
						ramka.sygnalKlawisz(Klawisze.SPACE);
						break;
						
					//pauza
					case 80:		
						ramka.sygnalKlawisz(Klawisze.PAUSE);
						break;
					
					//default:		
						//System.out.println(e.getKeyCode());
						//break;
				}

	}
	
	
	/**metoda odpowiedzialna za wy�wietlanie statystyk na ekran*/
	private void wyswietlStatystyki(Graphics g)
	{
		
        //ustawienie koloru i czcionki napisu
        g.setFont(new Font("Helvetica", Font.ITALIC, 15));
        g.setColor(new Color(0, 0, 0));
        

        g.drawString("BOMBERMAN", 680, 480);
        g.drawString("by Quetz v1.0", 680, 500);
        
        //ustawienie koloru i czcionki napisu
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.setColor(new Color(255, 0, 0));
        
        //poziom
        g.drawString("POZIOM: " + ramka.daneDoWyswietlenia.get("poziom"), 650, 70);
        
        //ustawienie koloru i czcionki napisu
        g.setFont(new Font("Helvetica", Font.BOLD, 16));
        g.setColor(new Color(0, 0, 0));
        
        //zycia
        g.drawString("�ycia: " + ramka.daneDoWyswietlenia.get("zycia"), 650, 100);
        //ilosc bomb
        g.drawString("Bomby: " + ramka.daneDoWyswietlenia.get("iloscBomb"), 650, 120);
        //zasieg bomb
        g.drawString("Zasi�g bomb: " + ramka.daneDoWyswietlenia.get("zasieg"), 650, 140);
        //punkty
        g.drawString("Punkty: " + ramka.daneDoWyswietlenia.get("punkty"), 650, 180);
        
    }
	
	/**Ekran wy�wietlaj�cy PRZEGRA�E�/WYGRA�E� zale�nie od wyniku rozgrywki*/
	private void ekranKoncowy(Graphics g, boolean wygrana)
	{
		
		if(wygrana==true)
		{
	        //ustawienie koloru i czcionki napisu
	        g.setFont(new Font("Helvetica", Font.BOLD, 60));
	        g.setColor(new Color(138,43,226));
			
	        
	        
	        g.drawString("WYGRANA!", 280, 200);
	        g.drawString("Punkty: " + ramka.daneDoWyswietlenia.get("punkty"), 280, 250);
			
		}
		else
		{
			
	        //ustawienie koloru i czcionki napisu
	        g.setFont(new Font("Helvetica", Font.BOLD, 60));
	        g.setColor(new Color(139,0,0));
			
	        
	        g.drawString("PRZEGRANA...", 280, 200);
	        g.drawString("Punkty: " + ramka.daneDoWyswietlenia.get("punkty"), 280, 250);
		}
	}

    /**metoda KeyListenera sprawdzaj�ca jaki klawisz jest wci�ni�ty i u�ywaj�ca metody ramki (sygnalKlawisz) do przes�ania informacji dalej*/
	public void keyReleased(KeyEvent e) 
	{
		switch(e.getKeyCode())
		{
		
				//strza�ka w g�r�
				case 38:
					ramka.sygnalKlawisz(Klawisze.UP);
					break;
		
				//strza�ka w d�
				case 40:
					ramka.sygnalKlawisz(Klawisze.DOWN);
					break;
				
				
				//strza�ka w lewo
				case 37:
					ramka.sygnalKlawisz(Klawisze.LEFT);
					break;
						
				//strza�ka w prawo
				case 39:
					ramka.sygnalKlawisz(Klawisze.RIGHT);
					break;
		}
	}

    /**metoda KeyListenera sprawdzaj�ca jaki klawisz zosta� wci�ni�ty i puszczony i u�ywaj�ca metody ramki (sygnalKlawisz) do przes�ania informacji dalej*/
	public void keyTyped(KeyEvent e) 
	{
		switch(e.getKeyCode())
		{
		
				//strza�ka w g�r�
				case 38:
					ramka.sygnalKlawisz(Klawisze.UP);
					break;
		
				//strza�ka w d�
				case 40:
					ramka.sygnalKlawisz(Klawisze.DOWN);
					break;
				
				
				//strza�ka w lewo
				case 37:
					ramka.sygnalKlawisz(Klawisze.LEFT);
					break;
						
				//strza�ka w prawo
				case 39:
					ramka.sygnalKlawisz(Klawisze.RIGHT);
					break;
		}
	}

}
