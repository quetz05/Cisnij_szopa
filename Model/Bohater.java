package Model;



/**Klasa bohatera gracza (kt�rym kieruje gracz)*/
public class Bohater extends Stworzenie 
{
	/**tablica bomb bohatera*/
	Bomba bomby[];
	
	/**tablica sprawdzaj�ca kt�re bomby ju� s� odpalone*/
	boolean odpaloneBomby[];
	
	/**maksymalna ilo�� bomb bohatera*/
	int maxBomb;	
	/**zasi�g bomby bohatera*/
	int zasieg;
	/**obecna ilo�� bomb bohatera*/
	int iloscBomb;
	/**punkty zdobyte przez bohatera*/
	int punkty;

	
	/**okre�la czy bohater jest nie�miertelny*/
	public boolean godMode;
	
	/**metoda zwracaj�ca ilo�� punkt�w bohatera*/
	public int zwrocPunkty(){return punkty;}
	
	/**metoda zwracaj�ca ilo�� bomb bohatera*/
	public int zwrocIloscBomb(){return iloscBomb;}
	
	/**metoda zwracaj�ca maksymaln� ilo�� bomb bohatera*/
	public int zwrocMaxBomb(){return maxBomb;}
	
	/**metoda zwracaj�ca zasi�g bom bohatera*/
	public int zwrocZasieg(){return zasieg;}
	
	/**metoda dodaj�ca punkty bohaterowi*/
	public void dodajPunkty(int nowePunkty){punkty = punkty + nowePunkty;}
	
	/**metoda ustalaj�ca ilo�� �y� bohatera*/
	public void ustawZycia(int noweZycia){zycia = noweZycia;}
	
	/**metoda dodaj�ca jedno �ycie bohaterowi*/
	public void dodajZycie(){zycia++;}

	/**zmienna sprawdzaj�ca czy gracz zdoby� dodatkow� bomb� lub bomby*/
	short dodajBomby;
	
	/**metoda dodaj�ca dan� ilo�� bomb bohaterowi*/
	public void dodajBomby(short bomby){dodajBomby=bomby;}
	
	/**metoda ustalaj�ca zasi�g bomb bohatera*/
	public void ustalZasieg(int nZasieg){zasieg = nZasieg;}

	
	/**Konstruktor bohatera ustawiaj�cy go na danej pozycji*/
	public Bohater(int x, int y)
	{
		czyRuch = false;
		
		szybkosc = 3;
		
		dzialanie = Dzialanie.NIC;
		
		godMode=false;
		
		pozX = x;
		pozY = y;
		wsX = x*40;
		wsY = y*40;
		
		zasieg = 1;
		maxBomb = 1;
		iloscBomb = maxBomb;
		zycia = 3;
		punkty = 0;
		
		bomby = new Bomba[maxBomb];
		odpaloneBomby = new boolean[maxBomb];
		
		dodajBomby=0;
	}
	

	/**metoda odpowiedzialna za postawienie bomby na planszy (przez bohatera) */
	void postawBombe(Bomba bomba, int nr, Plansza plansza)
	{

			bomba.czasTrwaniaBomby = System.currentTimeMillis() - bomba.czasBombyPoczatkowy;
			
			if(bomba.jestNaPlanszy==false)
			{
				bomba.jestNaPlanszy=true;
				plansza.zasiegWybuchu(bomba);
			}
		
			if(bomba.czasTrwaniaBomby>=Bomba.czasMax)
			{

				if(bomba.czyWybuch == false)
				{
					plansza.wybuchBomby(bomba);
					bomba.czyWybuch = true;
					bomba.czasWybuchuBomby = System.currentTimeMillis();	
				}
				
				
				if(System.currentTimeMillis() - bomba.czasWybuchuBomby >= Bomba.wybuchMax && bomba.czyWybuch == true)
				{
					bomba.czyWybuch = false;
					odpaloneBomby[nr]=false;
					iloscBomb++;
					plansza.koniecWybuchu(bomba);
					godMode=false;
				}
			}
	}


	
	
	/**metoda kieruj�ca ruchem bohatera*/
	public void ruchStworzenia(Pozycja plansza[][])
	{
		
		if(czyRuch==true)
		{
			przemieszczenie(dzialanie, plansza);
		}
		else if(plansza[pozY][pozX]==Pozycja.PORTAL_OFF && dzialanie==Dzialanie.DOL)
		{
			if(plansza[pozY+1][pozX]!=Pozycja.WYBUCH)
				plansza[pozY+1][pozX]=Pozycja.BOHATER;
			
			for(int i=0; i<szybkosc; i++)
				wsY++;
			czyRuch = true;
			dzialanie = Dzialanie.DOL;
			
		}
		else if(czyRuch==false && plansza[pozY][pozX]!=Pozycja.PORTAL_OFF)
		{
			switch(dzialanie)
			{
			
				case GORA:
					ruchJesliMozliwe(-1,0,plansza,dzialanie,Pozycja.BOHATER);
					break;
			
				case DOL:
					ruchJesliMozliwe(1,0,plansza,dzialanie,Pozycja.BOHATER);
					break;
			
				case LEWO:
					ruchJesliMozliwe(0,-1,plansza,dzialanie,Pozycja.BOHATER);
					break;
			
				case PRAWO:
					ruchJesliMozliwe(0,1,plansza,dzialanie,Pozycja.BOHATER);
					break;
					
				case BOMBA:
					if(iloscBomb>0 && plansza[pozY][pozX]!=Pozycja.BOMBA)
					{
						
						for (int i=0; i<maxBomb; i++)
						{
							if(odpaloneBomby[i]==false)
							{
								odpaloneBomby[i]=true;
								bomby[i] = new Bomba(this);
								plansza[pozY][pozX]=Pozycja.BOMBA;
								--iloscBomb;
								break;
							}
						}
					}
					
					default:
						break;

			}
			
		}

	}
	
	/**metoda odpowiedzialna za przemieszczenie bohatera z jednej pozycji na mapie na s�siedni�*/
	public void przemieszczenie(Dzialanie dzialanie, Pozycja[][] plansza)
	{
			switch(dzialanie)
			{
				case GORA:
					
					for(int i=0; i<szybkosc; i++)
						if(wsY%40 != 0)
							wsY--;

					if(wsY%40 == 0)
					{

						czyRuch = false;

						if(plansza[pozY][pozX]!=Pozycja.BOMBA && plansza[pozY][pozX]!=Pozycja.PORTAL && plansza[pozY][pozX]!=Pozycja.WYBUCH && plansza[pozY][pozX]!=Pozycja.PORTAL_OFF)
							plansza[pozY][pozX]=Pozycja.WOLNE;
						

						pozY--;		
						
						this.dzialanie = Dzialanie.NIC;

					}
					
					break;
				
				case DOL:
					
					for(int i=0; i<szybkosc; i++)
						if(wsY%40 != 0)
							wsY++;
					
					
					if(wsY%40 == 0)
					{

						
						czyRuch = false;

						if(plansza[pozY][pozX]!=Pozycja.BOMBA && plansza[pozY][pozX]!=Pozycja.PORTAL && plansza[pozY][pozX]!=Pozycja.WYBUCH && plansza[pozY][pozX]!=Pozycja.PORTAL_OFF)
							plansza[pozY][pozX]=Pozycja.WOLNE;
						

						
						pozY++;
						
						this.dzialanie = Dzialanie.NIC;
					}
					
					break;
					
				case LEWO:
					
					for(int i=0; i<szybkosc; i++)
						if(wsX%40 != 0)
							wsX--;

					if(wsX%40 == 0)
					{
						czyRuch = false;
						

						if(plansza[pozY][pozX]!=Pozycja.BOMBA && plansza[pozY][pozX]!=Pozycja.PORTAL && plansza[pozY][pozX]!=Pozycja.WYBUCH && plansza[pozY][pozX]!=Pozycja.PORTAL_OFF)
							plansza[pozY][pozX]=Pozycja.WOLNE;
						

						
						pozX--;	
						this.dzialanie = Dzialanie.NIC;

					}
					
					break;
					
				case PRAWO:
					
					for(int i=0; i<szybkosc; i++)
						if(wsX%40 != 0)
							wsX++;

					if(wsX%40 == 0)
					{
						czyRuch = false;

						if(plansza[pozY][pozX]!=Pozycja.BOMBA && plansza[pozY][pozX]!=Pozycja.PORTAL  && plansza[pozY][pozX]!=Pozycja.WYBUCH && plansza[pozY][pozX]!=Pozycja.PORTAL_OFF)
							plansza[pozY][pozX]=Pozycja.WOLNE;
						pozX++;		

						this.dzialanie = Dzialanie.NIC;
					}
					
					break;
					
					
					
				default:
					break;

			}

	}
	
	/**metoda odpowiedzialna za rozpocz�cie przemieszczania si� bohatera w danym kierunku je�li to mo�liwe*/
	protected void ruchJesliMozliwe(int y, int x, Pozycja plansza[][], Dzialanie d, Pozycja p)
	{
		
		if(plansza[pozY+y][pozX+x]!=Pozycja.BLOK && plansza[pozY+y][pozX+x]!=Pozycja.MUR && plansza[pozY+y][pozX+x]!=Pozycja.BOMBA 
		   && plansza[pozY+y][pozX+x]!=Pozycja.PORTAL_OFF)
		{
				if(plansza[pozY+y][pozX+x]==Pozycja.ZASIEG_NAGRODA)
					zasieg++;
				else if(plansza[pozY+y][pozX+x]==Pozycja.BOMBA_NAGRODA)
					dodajBomby++;
				
				if(plansza[pozY+y][pozX+x]!=Pozycja.BOMBA && plansza[pozY+y][pozX+x]!=Pozycja.WYBUCH 
				   && plansza[pozY+y][pozX+x]!=Pozycja.PORTAL_OFF && plansza[pozY+y][pozX+x]!=Pozycja.PORTAL)
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
	
	
	
	/**metoda odpowiedzialna za zmian� parametr�w bohatera po stracie �ycia*/
	public synchronized void strataZycia()
	{
		zycia--;

		dzialanie = Dzialanie.NIC;
		czyRuch = false;

		wsY = 0;
		wsX = 40;
		pozX = 1;
		pozY = 0;
		
		punkty = punkty - 100;

	}

	
}


