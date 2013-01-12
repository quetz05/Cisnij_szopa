package Model;


/**Klasa wroga typu 2 (wolny, przechodzi przez mury, z priorytetem poruszania siê w górê i w lewo)*/
public class Wrog2 extends Stworzenie
{
	/**ilosc punktów za zabicie wroga*/
	static final int punkty=250;
	
	/**konstruktor wroga2 (ustalaj¹cy iloœæ ¿yæ i szybkoœæ)*/
	public Wrog2()
	{
		
		zycia = 1;
		czyRuch = false;
		szybkosc = 1;

	}

	

	/**metoda definiuj¹ca sposób poruszania sie wroga*/
	protected void ruch(Dzialanie d, Pozycja plansza[][]) 
	{
		
		//jesli zostal rozpoczety ruch w jak¹œ stronê - dalsza animacja ruchu
		if (czyRuch ==true)
		{
			switch(dzialanie)
			{
			
					case GORA:
						przemieszczenie(-1, 0, plansza, dzialanie);
						break;
					
					case DOL:
						przemieszczenie(1, 0, plansza, dzialanie);
						break;
					
					case LEWO:
						przemieszczenie(0, -1, plansza, dzialanie);
						break;
					
					case PRAWO:
						przemieszczenie(0, 1, plansza, dzialanie);
						break;
					
					default:
						break;

			}
		}
		//wybranie kolejnego kierunku ruchu
		else
			switch(d)
			{
			
				case GORA:
					ruchJesliMozliwe(-1,0,plansza,d,Pozycja.WROG2);
					break;
			
				case DOL:
					ruchJesliMozliwe(1,0,plansza,d,Pozycja.WROG2);
					break;
			
				case LEWO:
					ruchJesliMozliwe(0,-1,plansza,d,Pozycja.WROG2);
					break;
			
				case PRAWO:
					ruchJesliMozliwe(0,1,plansza,d,Pozycja.WROG2);
					break;
			
				default:
					break;

		}
	}
	
	/**metoda odpowiedzialna za rozpoczêcie przemieszczania siê wroga 3 w danym kierunku jeœli to mo¿liwe (mo¿liwoœæ przechodzenia przez mury)*/
	protected void ruchJesliMozliwe(int y, int x, Pozycja plansza[][], Dzialanie d, Pozycja p)
	{
		
		if(plansza[pozY+y][pozX+x]!=Pozycja.BLOK && plansza[pozY+y][pozX+x]!=Pozycja.BOMBA 
		   && plansza[pozY+y][pozX+x]!=Pozycja.PORTAL_OFF && plansza[pozY+y][pozX+x]!=Pozycja.PORTAL)
		{
			
				if(plansza[pozY+y][pozX+x]!=Pozycja.BOMBA && plansza[pozY+y][pozX+x]!=Pozycja.WYBUCH && plansza[pozY+y][pozX+x]!=Pozycja.MUR)
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
	
	/**metoda odpowiedzialna za przemieszczenie wroga2 z jednej pozycji na mapie na s¹siedni¹*/
	public void przemieszczenie(int y, int x, Pozycja[][] plansza, Dzialanie d)
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

			if(plansza[pozY][pozX]!=Pozycja.BOMBA && plansza[pozY][pozX]!=Pozycja.WYBUCH && plansza[pozY][pozX]!=Pozycja.MUR)
				plansza[pozY][pozX]=Pozycja.WOLNE;
			
			pozX=pozX+x;
			pozY=pozY+y;
			
			if(dzialanie == Dzialanie.LEWO)
				ruchJesliMozliwe(0,-1,plansza,dzialanie,Pozycja.WROG2);
			else if(dzialanie == Dzialanie.GORA)
				ruchJesliMozliwe(-1,0,plansza,dzialanie,Pozycja.WROG2);
		}

		
	}
	
	
	
	
	
	
	
}
