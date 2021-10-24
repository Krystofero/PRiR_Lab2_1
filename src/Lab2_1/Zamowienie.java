package Lab2_1;


import java.util.Random;


public class Zamowienie extends Thread {
	//definicja stanu zamówienia
	static int WERYFIKACJA = 1;
	static int PRZYJETE = 2;
	static int KOMPLETOWANIE = 3;
	static int PAKOWANIE = 4;
	static int PRZEKAZANIEDOSTAWCY = 5;
	static int UDOSTAWCY = 6;
	static int WYSYLKAWDRODZE = 7;
	static int AWIZO = 8;
	static int WYSYLKANAMIEJSCU = 9;
	static int ZAKONCZONE = 10;
	static int cena;

	int numer;	//numer zamówienia
	long czas_w_mili;
	long czas_teraz_w_mili; //czas realizacji zamówienia w milisekundach
	int stan;	//stan zamówienia
	Sklep s;
	Random rand;
	public Zamowienie(int numer, Sklep s){
		this.numer=numer;
		this.stan= WERYFIKACJA;
		this.s = s;
		rand=new Random();
	}

	//funkcja zwracająca mijający czas w minutach
	public long Czas(long czas){
		return czas/60000;
	}

	public void run(){
		while(true){
			if(stan == WERYFIKACJA){
				System.out.println("Proszę o weryfikacje zamówienia nr: " + numer);
				try{
					sleep(rand.nextInt(2000));
				}
				catch (Exception e){}
				czas_w_mili = System.currentTimeMillis();//początkowy czas w milisekundach
				//stan = PRZYJETE;
				cena = rand.nextInt(1000);
				stan = s.rozpoczete(numer);
			}
			else if(stan == PRZYJETE){
				System.out.println("Przyjąłem zamówienie nr: " + numer + " , cena: " + cena + "zł");
				stan = KOMPLETOWANIE;
			}
			else if(stan == KOMPLETOWANIE){
				System.out.println("Kompletujemy zamówienie nr: "+numer);
				stan = PAKOWANIE;
				try{
					sleep(rand.nextInt(20000));
				}
				catch (Exception e){}
			}
			else if(stan == PAKOWANIE){
				try{
					sleep(rand.nextInt(20000));
				}
				catch (Exception e){}
				czas_teraz_w_mili = System.currentTimeMillis() - czas_w_mili;
				System.out.println("Pakujemy zamówienie nr: " + numer + " , dotychczasowy czas realizacji " + Czas(czas_teraz_w_mili) + "min");
				stan = s.pracownikZakonczyl();
			}
			else if(stan == PRZEKAZANIEDOSTAWCY){
				try{
					sleep(rand.nextInt(20000));
				}
				catch (Exception e){}
				czas_teraz_w_mili = System.currentTimeMillis() - czas_w_mili;
				System.out.println("Prosze o pozwolenie na wysyłkę zamówienia nr: " + numer + " , dotychczasowy czas realizacji " + Czas(czas_teraz_w_mili) + "min");
				stan = UDOSTAWCY;
			}
			else if(stan == UDOSTAWCY){
				try{
					sleep(rand.nextInt(20000));
				}
				catch (Exception e){}
				czas_teraz_w_mili = System.currentTimeMillis() - czas_w_mili;
				System.out.println("Niebawem wyślemy zamówienie nr: " + numer + " , dotychczasowy czas realizacji " + Czas(czas_teraz_w_mili) + "min");
				stan = WYSYLKAWDRODZE;
				try{
					sleep(rand.nextInt(20000));
				}
				catch (Exception e){}
			}
			else if(stan == WYSYLKAWDRODZE){
				czas_teraz_w_mili = System.currentTimeMillis() - czas_w_mili;
				System.out.println("Już jedzie zamówienie nr: " + numer + " , dotychczasowy czas realizacji " + Czas(czas_teraz_w_mili) + "min");
				if(rand.nextInt(3)==1){
					stan = AWIZO;
				}
				else stan = WYSYLKANAMIEJSCU;
			}
			else if(stan == AWIZO){
				czas_teraz_w_mili = System.currentTimeMillis() - czas_w_mili;
				System.out.println("AWIZO zamówienie nr: " + numer + " , dotychczasowy czas realizacji " + Czas(czas_teraz_w_mili) + "min");
				stan = WYSYLKANAMIEJSCU;
				try{
					sleep(rand.nextInt(30000));
				}
				catch (Exception e){}
			}
			else if(stan == WYSYLKANAMIEJSCU){
				czas_teraz_w_mili = System.currentTimeMillis() - czas_w_mili;
				System.out.println("Dostarczono zamówienie nr: " + numer + " , dotychczasowy czas realizacji " + Czas(czas_teraz_w_mili) + "min");
				stan = ZAKONCZONE;
			}
			else if(stan == ZAKONCZONE){
				czas_teraz_w_mili = System.currentTimeMillis() - czas_w_mili; // czas wykonania programu w milisekundach.
				System.out.println("Zakończono realizację zamówienia nr: " + numer + ", czas realizacji: " + Czas(czas_teraz_w_mili) + "min");
				s.zmniejsz();
			//	System.exit(0);
			}
		}
	}

}
