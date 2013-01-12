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


/**klasa panel odpowiedzialna za rysowanie obiektów oraz kontakt z u¿ytkownikiem (odbieranie wciœniêtych przycisków)*/
public class Panel extends JPanel implements KeyListener
{
	
	/**mapa zawieraj¹ca obrazki do wyœwietlenia na ekran*/
	private Map<String, Image> obrazki;
    
    /**tablica przedstawiajaca kopie planszy poziomu*/
    private Pozycja poziom[][];
    
    /**referencja na ramkê (klasa Ramka)*/
    private Ramka ramka;
    
    

    /**konstruktor klasy panel tworz¹cy KeyListenera oraz wczytuj¹cy obrazki*/
	public Panel(Ramka mRamka)
	{	
		ramka = mRamka;
		
		//dodanie KeyListenera
		addKeyListener(this);
		wczytajObrazki();
	}
	
	
	   /**metoda odpowiedzialna za muzykê w tle (tworz¹ca oddzielny w¹tek)*/
	   public static synchronized void playSound(final String url) 
	   {
		   //nowy w¹tek muzyki
		    new Thread(new Runnable() 
		    { 
		      public void run() {
		        try 
		        {
		          //nowy klip
		          Clip clip = AudioSystem.getClip();
		          AudioInputStream inputStream = AudioSystem.getAudioInputStream(Panel.class.getResourceAsStream(url));
		          clip.open(inputStream);
		          //pêtla
		          clip.loop(99999); 
		        } 
		        catch (Exception e) 
		        {
		          System.err.println("B³¹d pliku dŸwiêkowego (muzyka w tle)");
		        }
		      }
		    }).start();
		  }
	
	
	/**metoda wczytuj¹ca obrazki do mapy*/
	private void wczytajObrazki()
	{
		obrazki = new HashMap<String, Image>();
		
        //wczytywanie obrazków
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
	
	
	/**metoda pobieraj¹ca obrazek z danej œcie¿ki*/
	private Image getImage(String string)
	{
		  URL obrazekUrl = this.getClass().getResource(string);
		  Toolkit tk = Toolkit.getDefaultToolkit();
		  return tk.getImage(obrazekUrl);
	}

	
	/**metoda paint odpowiedzialna za rysowanie obiektów oraz danych na ekranie*/
	public void paint(Graphics g)
	{

		
		if(ramka.czyPauza()==true)
		{
	        //ustawienie koloru i czcionki napisu
	        g.setFont(new Font("Helvetica", Font.BOLD, 60));
	        g.setColor(new Color(0,43,226));
			

	        g.drawString("PAUZA", 280, 200);
	        g.drawString("Wciœnij (p)", 240, 250);
	        


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
	
	
	/**metoda rysuj¹ca planszê oraz dane na podstawie danych otrzymanych od klasy Ramka*/
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
		
		//rysowanie wrogów1
		for(int i=0;i<ramka.zwrocDaneObiektu("wrog1",i,"size"); i++)
			g.drawImage(obrazki.get("WROG1"), ramka.zwrocDaneObiektu("wrog1",i,"wsX"), ramka.zwrocDaneObiektu("wrog1",i,"wsY"),this);
		
		//rysowanie wrogów2
		for(int i=0;i<ramka.zwrocDaneObiektu("wrog2",i,"size"); i++)
			g.drawImage(obrazki.get("WROG2"), ramka.zwrocDaneObiektu("wrog2",i,"wsX"), ramka.zwrocDaneObiektu("wrog2",i,"wsY"),this);
		
		//rysowanie wrogów3
		for(int i=0;i<ramka.zwrocDaneObiektu("wrog3",i,"size"); i++)
			g.drawImage(obrazki.get("WROG3"), ramka.zwrocDaneObiektu("wrog3",i,"wsX"), ramka.zwrocDaneObiektu("wrog3",i,"wsY"),this);
		
		//rysowanie bohatera
		g.drawImage(obrazki.get("BOHATER"), ramka.zwrocDaneObiektu("hero1",0,"wsX"), ramka.zwrocDaneObiektu("hero1",0,"wsY"),this);
		
		//wyœwietlanie statystyk
		wyswietlStatystyki(g);
      }
   }
    
    /**metoda KeyListenera sprawdzaj¹ca jaki klawisz zosta³ wciœniêty i u¿ywaj¹ca metody ramki (sygnalKlawisz) do przes³ania informacji dalej*/
	public void keyPressed(KeyEvent e) 
	{

			switch(e.getKeyCode())
			{
			
					//strza³ka w górê
					case 38:
						ramka.sygnalKlawisz(Klawisze.UP);
						break;
			
					//strza³ka w dó³
					case 40:
						ramka.sygnalKlawisz(Klawisze.DOWN);
						break;
					
					
					//strza³ka w lewo
					case 37:
						ramka.sygnalKlawisz(Klawisze.LEFT);
						break;
							
					//strza³ka w prawo
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
	
	
	/**metoda odpowiedzialna za wyœwietlanie statystyk na ekran*/
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
        g.drawString("¯ycia: " + ramka.daneDoWyswietlenia.get("zycia"), 650, 100);
        //ilosc bomb
        g.drawString("Bomby: " + ramka.daneDoWyswietlenia.get("iloscBomb"), 650, 120);
        //zasieg bomb
        g.drawString("Zasiêg bomb: " + ramka.daneDoWyswietlenia.get("zasieg"), 650, 140);
        //punkty
        g.drawString("Punkty: " + ramka.daneDoWyswietlenia.get("punkty"), 650, 180);
        
    }
	
	/**Ekran wyœwietlaj¹cy PRZEGRA£EŒ/WYGRA£EŒ zale¿nie od wyniku rozgrywki*/
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

    /**metoda KeyListenera sprawdzaj¹ca jaki klawisz jest wciœniêty i u¿ywaj¹ca metody ramki (sygnalKlawisz) do przes³ania informacji dalej*/
	public void keyReleased(KeyEvent e) 
	{
		switch(e.getKeyCode())
		{
		
				//strza³ka w górê
				case 38:
					ramka.sygnalKlawisz(Klawisze.UP);
					break;
		
				//strza³ka w dó³
				case 40:
					ramka.sygnalKlawisz(Klawisze.DOWN);
					break;
				
				
				//strza³ka w lewo
				case 37:
					ramka.sygnalKlawisz(Klawisze.LEFT);
					break;
						
				//strza³ka w prawo
				case 39:
					ramka.sygnalKlawisz(Klawisze.RIGHT);
					break;
		}
	}

    /**metoda KeyListenera sprawdzaj¹ca jaki klawisz zosta³ wciœniêty i puszczony i u¿ywaj¹ca metody ramki (sygnalKlawisz) do przes³ania informacji dalej*/
	public void keyTyped(KeyEvent e) 
	{
		switch(e.getKeyCode())
		{
		
				//strza³ka w górê
				case 38:
					ramka.sygnalKlawisz(Klawisze.UP);
					break;
		
				//strza³ka w dó³
				case 40:
					ramka.sygnalKlawisz(Klawisze.DOWN);
					break;
				
				
				//strza³ka w lewo
				case 37:
					ramka.sygnalKlawisz(Klawisze.LEFT);
					break;
						
				//strza³ka w prawo
				case 39:
					ramka.sygnalKlawisz(Klawisze.RIGHT);
					break;
		}
	}

}
