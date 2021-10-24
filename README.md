# PRiR_Lab2_1
Programowanie równolegle i rozproszone - projekt 1 labolatorium 2
Symulacja Sklepu Internetowego

Projekt składa się z trzech plików: SymulatorSklepu.java , Zamowienie.java, Sklep.java

Na potrzeby testowania czas realizacji zamówienia jest znacznie szybszy niż w rzeczywistości (podawany jest czas realizacji w minutach)
W klasie SymulatorSklepu deklarujemy domyślną liczbę zamówień oraz liczbę pracowników i nasz sklep;

  static int ilosc_zamowien = 1000;
	static int ilosc_pracownikow = 7;
	static Sklep sklep;
  
Klasa Sklep przyjmuje argumenty: ilość pracowników , ilość zamówień
Następnie w pętli startujemy tyle wątków ile jest zmówień:

    sklep = new Sklep(ilosc_pracownikow, ilosc_zamowien);
    for(int i = 0; i < ilosc_zamowien; i++){
			new Zamowienie(i, sklep).start();
		}

W klasie Sklep najpierw definiowane są wszystkie możliwe stany zamówienia:

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

W konstruktorze klasy Sklep na początku wszyscy pracownicy ustawiani są jako dostępni/ gotowi do pracy:

  this.ilosc_zajetych_pracownikow =0;
  
Metoda rozpoczete(int numer) jeżeli jest wolny pracownik przyjmuje zamówienie zmieniając jego stan na PRZYJĘTE , jeśli nie ma wolnych pracowników czeka żeby spróbować ponownie:

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
  
Metoda pracownikZakonczyl() jest wywoływana gdy pracownik przekaże produkt dostawcy , wówczas pracownik jest zdolny przyjąć kolejne zamówienie:

  synchronized int pracownikZakonczyl(){
        ilosc_zajetych_pracownikow--;
        return PRZEKAZANIEDOSTAWCY;
    }
  
Metoda zmniejsz ustawia zamówienie na Zakończone, usuwając je z listy:

  synchronized void zmniejsz(){

      ilosc_zamowien--;
      System.out.println("USUNĄLEM");
      if(ilosc_zamowien == ilosc_pracownikow) System.out.println("ILOSC ZAMÓWIEN JEST TAKA SAMA JAK ILOSC PRACOWNIKÓW");


    }
    
Klasa Zamówienie rozszerza klasę Thread ,
definiowane są w niej stany zamówienia
Po stworzeniu zamówienia w konstruktorze ustawiany jest etap Weryfikacji

  public Zamowienie(int numer, Sklep s){
		this.numer=numer;
		this.stan= WERYFIKACJA;
		this.s = s;
		rand=new Random();
	}
  
Losowo generowana jest cena zamówienia, a także czas trwania poszczególnych stanów zamówienia

    if(stan == WERYFIKACJA){
				System.out.println("Proszę o weryfikacje zamówienia nr: " + numer);
				try{
					sleep(rand.nextInt(2000));
				}
				catch (Exception e){}
				czas_w_mili = System.currentTimeMillis();//początkowy czas w milisekundach
				cena = rand.nextInt(1000);
				stan = s.rozpoczete(numer);
			}
      
Dodatkowo zliczany jest czas trwania zamówienia - liczony jest w milisekundach, dlatego wymagał konwersji do minut za pomocą funkcji:

  public long Czas(long czas){
		return czas/60000;
	}

W przypadku próby dostarczenia paczki przez dostawcę może dojść do sytuacji gdy klient jej nie odbierze - wówczas zostawia się AWIZO. Jest to generowane losowo:

      else if(stan == WYSYLKAWDRODZE){
				czas_teraz_w_mili = System.currentTimeMillis() - czas_w_mili;
				System.out.println("Już jedzie zamówienie nr: " + numer + " , dotychczasowy czas realizacji " + Czas(czas_teraz_w_mili) + "min");
				if(rand.nextInt(3)==1){
					stan = AWIZO;
				}
				else stan = WYSYLKANAMIEJSCU;
			}
      
      
