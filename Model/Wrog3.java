package Model;

/**Klasa wroga typu 3 (szybki, standardowy, porusza si� losowo)*/
public class Wrog3 extends Stworzenie 
{

	/**ilosc punkt�w za zabicie wroga*/
	static final int punkty=300;
	
	/**konstruktor wroga3 (ustalaj�cy ilo�� �y� i szybko��)*/
	public Wrog3()
	{
		
		zycia = 1;
		czyRuch = false;
		szybkosc = 4;

	}
	
	
	
	/**metoda definiuj�ca spos�b poruszania sie wroga*/
	protected void ruch(Dzialanie d, Pozycja plansza[][]) 
	{
		
		//jesli zostal rozpoczety ruch w jak�� stron� - dalsza animacja ruchu
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
						ruchJesliMozliwe(-1,0,plansza,d,Pozycja.WROG3);
						break;
					
					case DOL:
						ruchJesliMozliwe(1,0,plansza,d,Pozycja.WROG3);
						break;
					
					case LEWO:
						ruchJesliMozliwe(0,-1,plansza,d,Pozycja.WROG3);
						break;
					
					case PRAWO:
						ruchJesliMozliwe(0,1,plansza,d,Pozycja.WROG3);
						break;
					
					default:
						break;
		}
	}
	
	
	
	
}
