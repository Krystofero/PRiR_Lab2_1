package Lab2_1;


public class Sklep {
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
	
	int ilosc_pracownikow;
	int ilosc_zajetych_pracownikow;
	int ilosc_zamowien;
	Sklep(int ilosc_pracownikow, int ilosc_zamowien){
		this.ilosc_pracownikow = ilosc_pracownikow;
		this.ilosc_zamowien = ilosc_zamowien;
		this.ilosc_zajetych_pracownikow =0;
	}
	synchronized int rozpoczete(int numer){

		if(ilosc_zajetych_pracownikow < ilosc_pracownikow){
			ilosc_zajetych_pracownikow++;
			System.out.println("Rozpoczęcie realizacji zamówienia nr: " + numer);
			System.out.println("ilość zajętych pracowników: " + ilosc_zajetych_pracownikow);
			return PRZYJETE;
		}
		else{
			try{

				  Thread.currentThread().sleep(10000);//sleep for 1000 ms

				}
				catch(Exception ie){
				}
		}
		return PRZYJETE;
	}
	synchronized int pracownikZakonczyl(){
			ilosc_zajetych_pracownikow--;
			return PRZEKAZANIEDOSTAWCY;
	}
	synchronized void zmniejsz(){

		ilosc_zamowien--;
		System.out.println("USUNĄLEM");
		if(ilosc_zamowien == ilosc_pracownikow) System.out.println("ILOSC ZAMÓWIEN JEST TAKA SAMA JAK ILOSC PRACOWNIKÓW");
	

	}

}
