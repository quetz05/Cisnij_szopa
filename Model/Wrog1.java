package Model;


/**Klasa wroga typu 1 (wolny, standardowy, z priorytetem poruszania siê w dó³ i prawo)*/
public class Wrog1 extends Stworzenie 
{
	/**ilosc punktów za zabicie wroga*/
	static final int punkty=100;
	
	/**konstruktor wroga1 (ustalaj¹cy iloœæ ¿yæ i szybkoœæ)*/
	public Wrog1()
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
		else
			switch(d)
			{
			
				case GORA:
					ruchJesliMozliwe(-1,0,plansza,d,Pozycja.WROG1);
					break;
				
				case DOL:
					ruchJesliMozliwe(1,0,plansza,d,Pozycja.WROG1);
					break;
				
				case LEWO:
					ruchJesliMozliwe(0,-1,plansza,d,Pozycja.WROG1);
					break;
				
				case PRAWO:
					ruchJesliMozliwe(0,1,plansza,d,Pozycja.WROG1);
					break;
				
				default:
					break;
			}
	}

	/**metoda odpowiedzialna za przemieszczenie wroga1 z jednej pozycji na mapie na s¹siedni¹*/
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

			if(plansza[pozY][pozX]!=Pozycja.BOMBA && plansza[pozY][pozX]!=Pozycja.WYBUCH)
				plansza[pozY][pozX]=Pozycja.WOLNE;
			
			pozX=pozX+x;
			pozY=pozY+y;
			
			if(dzialanie == Dzialanie.PRAWO)
				ruchJesliMozliwe(0,1,plansza,dzialanie,Pozycja.WROG1);
			else if(dzialanie == Dzialanie.DOL)
				ruchJesliMozliwe(1,0,plansza,dzialanie,Pozycja.WROG1);
		}

		
	}
	
	
}
