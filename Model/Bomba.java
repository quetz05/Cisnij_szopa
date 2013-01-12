package Model;

import java.awt.Rectangle;
import java.util.ArrayList;


/**klasa Bomba tworz�ca obiekty b�d�ce bombami*/
public class Bomba 
{
	/**czas oczekiwania na wybuch bomby*/
	static final int czasMax = 3000;
	/**czas trwania wybuchu*/
	static final int wybuchMax = 500;
	
	/**pozycja x w jakiej znajduje si� bomba w tablicy*/
	int pozX;
	/**pozycja y w jakiej znajduje si� bomba w tablicy*/
	int pozY;
	
	/**czas jaki bomba jest ju� postawiona*/
	long czasTrwaniaBomby;
	/**chwila w ktorej bomba zosta�a postawiona*/
	long czasBombyPoczatkowy;
	/**moment wybuchu bomby*/
	long czasWybuchuBomby;
	/**zmienna sprawdzaj�ca czy wybuch trwa*/
	boolean czyWybuch;
	/**zmienna okre�laj�ca czy bomba jest ju� na planszy i ju� wiadomo gdzie wybuchnie*/
	boolean jestNaPlanszy;
	
	
	/**lista przechowuj�ca wsp�rz�dne wybuch�w*/
	ArrayList<Rectangle> wybuchy;
	
	
	/**konstruktor bomby*/
    Bomba(Bohater hero)
	{
		pozX = hero.getPozX();
		pozY = hero.getPozY();
		
		czasBombyPoczatkowy = System.currentTimeMillis();
		
		czyWybuch = false;
		
		jestNaPlanszy = false;
		
		wybuchy = new ArrayList<Rectangle>();
		
	}
    
	
}
	
	

