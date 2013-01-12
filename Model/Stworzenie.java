package Model;

import java.awt.Rectangle;
import java.util.Random;


/**abstrakcynja klasa Stworzenie (po której dziedzicz¹ bohater, wrogowie, itd.)*/
public abstract class Stworzenie
{

	/** pozycja x Stworzenia w tablicy*/
	protected int pozX;
	/** pozycja y Stworzenia w tablicy*/
	protected int pozY;
	/** pozycja x Stworzenia w uk³adzie wspó³rzêdnych*/
	protected int wsX;
	/** pozycja y Stworzenia w uk³adzie wspó³rzêdnych*/
	protected int wsY;
	
	/**zmienna okreœlaj¹ca ile razy szybciej porusza siê stworzenie (bazowo = 1)*/
	protected int szybkosc;
	
	/**sprawdza czy stworzenie porusza siê w danej chwili*/
	protected boolean czyRuch;
	
	/**metoda sprawdzaj¹ca czy stworzenie siê porusza (zwracaj¹ca wartoœæ zmiennej czyRuch)*/
	public boolean sprawdzRuch(){return czyRuch;};
	
	/**iloœæ ¿yæ stworzenia*/
	protected int zycia;
	/**metoda zwracaj¹ca iloœæ ¿yæ stworzenia*/
	public int zwrocIloscZyc() {return zycia;};
	/**metoda zmniejszaj¹ca ¿ycie stworzenia o 1*/
	public void strataZycia() { zycia--;};
	
	/**kierunek poruszania siê stworzenia (b¹dŸ co robi)*/
	public Dzialanie dzialanie;
	
	
	/** ustawienie pozycji x Stworzenia w tablicy*/
	public void setPozX(int x) {pozX = x;};
	/** ustawienie pozycji y Stworzenia w tablicy*/
	public void setPozY(int y) {pozY = y;};
	/** ustawienie pozycji x Stworzenia w uk³adzie wspó³rzêdnych*/
	public void setWsX(int x) {wsX = x;};
	/** ustawienie pozycji y Stworzenia w uk³adzie wspó³rzêdnych*/
	public void setWsY(int y) {wsY = y;};
	
	/** zwrócenie pozycji x Stworzenia w tablicy*/
	public int getPozX() {return pozX;};
	/** zwrócenie pozycji y Stworzenia w tablicy*/
	public int getPozY() {return pozY;};
	/** zwrócenie pozycji x Stworzenia w uk³adzie wspó³rzêdnych*/
	public int getWsX() {return wsX;};
	/** zwrócenie pozycji y Stworzenia w uk³adzie wspó³rzêdnych*/
	public int getWsY() {return wsY;};
	/** zwrócenie pozycji Stworzenia na planszy w postaci prostok¹ta*/ 
	public Rectangle zwrocPozycje() {return new Rectangle(wsX, wsY, 40, 40);};
	
	/**metoda odpowiedzialna za rozpoczêcie przemieszczania siê stworzenia z konkretnej pozycji w danym kierunku jeœli to mo¿liwe*/
	protected void ruchJesliMozliwe(int y, int x, Pozycja plansza[][], Dzialanie d, Pozycja p)
	{
		
		if(plansza[pozY+y][pozX+x]!=Pozycja.BLOK && plansza[pozY+y][pozX+x]!=Pozycja.MUR && plansza[pozY+y][pozX+x]!=Pozycja.BOMBA 
		   && plansza[pozY+y][pozX+x]!=Pozycja.PORTAL_OFF && plansza[pozY+y][pozX+x]!=Pozycja.PORTAL)
		{
				if(plansza[pozY+y][pozX+x]!=Pozycja.BOMBA && plansza[pozY+y][pozX+x]!=Pozycja.WYBUCH)
					plansza[pozY+y][pozX+x]=p;
				
				for(int i=0; i<szybkosc; i++)
				{
					wsX=wsX+x;
					wsY=wsY+y;
				}	
				czyRuch = true;
				dzialanie = d;
		}

	}
	
	/**metoda odpowiedzialna za przemieszczenie stworzenia z jednej pozycji na planszy na s¹siedni¹ (zale¿nie od kierunku, dzia³ania)*/
	public void przemieszczenie(int y, int x, Pozycja[][] plansza, Dzialanie dzialanie)
	{
		
		for(int i=0; i<szybkosc; i++)
			if((wsY%40 == 0 && wsX%40 != 0) || (wsY%40 != 0 && wsX%40 == 0) )
			{
				wsY=wsY+y;
				wsX=wsX+x;
			}
		
		if(wsY%40 == 0 && wsX%40 == 0)
		{
			czyRuch = false;

			if(plansza[pozY][pozX]!=Pozycja.BOMBA && plansza[pozY][pozX]!=Pozycja.WYBUCH)
				plansza[pozY][pozX]=Pozycja.WOLNE;
			
			pozX=pozX+x;
			pozY=pozY+y;
		}

		
	}
	
	/**metoda kierujaca ruchem stworzenia (w sposób losowy)*/
	public void ruchStworzenia(Pozycja plansza[][])
	{
	    Random generator = new Random();
		
	    //generowanie losowego kierunku ruchu
		int ruch = generator.nextInt(4)+1;
		
		Dzialanie d = null;
		
		if(ruch == 1)
			d=Dzialanie.GORA;
		else if(ruch == 2)
			d=Dzialanie.DOL;
		else if(ruch == 3)
			d=Dzialanie.LEWO;
		else
			d=Dzialanie.PRAWO;

		//wywolanie ruchu stwora
		ruch(d, plansza);
	}
	
	/**metoda okreœlaj¹ca algorytm ruchu stworzenia*/
	protected void ruch(Dzialanie d, Pozycja plansza[][]){};

}
