package Lab2_1;


public class SymulatorSklepu {

	static int ilosc_zamowien = 1000;
	static int ilosc_pracownikow = 7;
	static Sklep sklep;
	public SymulatorSklepu(){

	}
	public static void main(String[] args) {
		sklep = new Sklep(ilosc_pracownikow, ilosc_zamowien);
		for(int i = 0; i < ilosc_zamowien; i++){
			new Zamowienie(i, sklep).start();
		}

	}

}
