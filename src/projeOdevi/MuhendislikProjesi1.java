package projeOdevi;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Scanner;
import static java.lang.Math.pow;
class projeOdev {
    public static void main(String[] args) {
        final int tableSize = 211;                          //tablonun boyutu belirleniyor
        String kelimeler[] = new String[100];               //kelimeleri tutan dizi ve boyutu belirleniyor
        int asciKarsilik[] = new int[100];
        Hashtable<Integer,String> HashTable=  new Hashtable<>(tableSize);       //tablo olusturuluyor
        try {
            FileInputStream fStream = new FileInputStream("kelimeler.txt");          //dosya olusturuluyor ve dosyanin nerede oldugu tanimlaniyor
            DataInputStream sStream = new DataInputStream(fStream);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(sStream));
            for (int i = 0; i < kelimeler.length; i++) {
                kelimeler[i] = bReader.readLine();           //kelimeler tek tek okunup kelimeler adli diziye aktarliyor
            }
            sStream.close();             //dosya kapatiliyor
        } catch (Exception e) {
            System.err.println("Hatalar : " + e.getMessage());      //eger dosyanin acilmasinda hata varsa ekrana hata mesaji veiliyor
        }
        asciKarsilikAlanFunction(asciKarsilik,kelimeler);                            //kelimelerin ascii karsiligini  bulan fonksiyon
        tabloYerlestirmeFunction(HashTable,asciKarsilik,kelimeler,tableSize);        //kelimeleri tabloya yerlestiren  fonksiyon
        System.out.println(HashTable);                          //kelimeler yerlesmis halde ekrana veriliyor
        for (int i=0;i<3;i++){
             System.out.println("\n\n\n\nlutfen bir kelime giriniz");

            Scanner ara= new Scanner(System.in);            //kullanicidan  deger girilmesi icin  metot olusturuluyor
            String girilenKelime= ara.next();               //kullanicidan  deger  girilmesi bekleniyor
            AramaFunction(HashTable,girilenKelime,tableSize); //girilen kelimeyi arayan fonksiyon
        }
    }

    private static void asciKarsilikAlanFunction(int ascii [],String kelimeler[]){

        for (int j = 0; j <kelimeler.length; j++) {
            String kelime=kelimeler[j];   //gelen kelimeler kelime adli degiskene ataniyor
            char kdizi[]=new char [10];   //kelimenin karakterlere ayrirmak icin char tipinde dizi tanimlaniyor
            for (int k=0; k < kelime.length();k++){
                kdizi[k]=kelime.charAt(k);  //aranan kelime karakterlere ayriliyor
            }
            int Kasci=0; //toplam ascii degerin tutulacagi degisken tanimlaniyor
            for(int i=0;i<kelime.length();i++){

                Kasci=Kasci+(i+1)*(int) kdizi[i]; //kelimenin  ascii karsiligi alinip toplaniyor
            }
            ascii[j]=Kasci; // tum kelimelerin ascii karsiliklari  ascii[]  adli diziye ataniyor
        }

    }
    private static void tabloYerlestirmeFunction(Hashtable<Integer, String> hashTable,int[] asciKarsilik,  String[] kelimeler, int tableSize){
        int key=0;  // her kelime icin key degerinin tutuldugu  dugisken tanimlaniyor
        for(int i=0;i<kelimeler.length;i++){ // i==0 dan kelimeler dizisinin boyutuna kadar calistiriliyor //*** 1.dongu
            for(int j=0;j<tableSize;j++){ //j==0 dan tablonun boyutuna kadar calistiriliyor  //*** 2.dongu
                key = (int) ((asciKarsilik[i]+pow(j,2))%tableSize); //girilen kelimenin tabloda  yerlecsecegi yer belirleniyor
                if(!hashTable.containsKey(key)) //kelimenin yerlesmesi gereken  yer kontrol ediliyor eger  bossa  kosul saglaniyor eger kosul saglanmazsa 2. yerlesmesi gereken yer belirlenecek
                {
                    hashTable.put(key,kelimeler[i]);  //kelmeler tabloya yerlestiriliyor
                    break; // 2. donguden cikip ilk donguden devam ediliyor
                }
            }
        }
    }
    private static void AramaFunction(Hashtable<Integer, String> HashTable, String girilenKelime,int tableSize) {

        int Arananascii = 0; //aranan kelimenin ascii karsiligini tutan degisken

        for (int i = 0; i < girilenKelime.length(); i++) {  //dongu girilen kelimenin boyutu kadar calistiriliyor
            char Kkarakter = girilenKelime.charAt(i);                  //kelime karakterlere cevriiyor
            int Kasci = (int) Kkarakter; //karakterler ascii ye cevriliyor
            Arananascii = Arananascii + (i + 1) * Kasci;                //girilen kelimenin ascii karsiligi bulunuyor
        }
        int key; //aranan kelimenin yerlesecegi yeritutan degisken
        for (int j = 0; j < tableSize; j++) {  //dongu 0'dan tablonun boyutuna kadar calistiriliyor
            key = (int) ((Arananascii + pow(j, 2)) % tableSize); //aranan kelimenin tabloda olasi yerlesecegi  yer belirleniyor
            if (!HashTable.containsKey(key)) { //eger kelimenin yerlesmesi gereken yer bossa  kosul saglaniyor ve kelime bulunamiyor
                System.out.println("Aradiginiz kelime bulunamadi");
                harfYerdegistir(HashTable,girilenKelime,tableSize); //kelime bulunmadigi icin kelimenin harflerinin yerleri degistirerek aramaya gonderiliyor
                HarfCikar(HashTable, girilenKelime, tableSize);  //kelime bulunmadigi icin harf cikarma fonksiyonuna gonderiliyor
                break; //dongu kiriliyor
            }
            if (girilenKelime.equals(HashTable.get(key))) { //kelimenin olasi yerlesecegi yer doluysa aranan  kelime ile yerlesen kelime esit olup olmadigi kontrol ediliyor
                System.out.println("Aradiginiz kelime    "+key +".inci hucrede  mevcuttur "); // kosul saglanirsa kelime bulunuyor
                break; //dongu kiriliyor
            }
        }
    }
    
    
    
    
    
    
    private static void HarfCikar(Hashtable<Integer,String>HashTable, String girilenKelime, int tableSize){
        char kdizi[]=new char[girilenKelime.length()]; //aranan kelimeyi karakterlere cevirmek icin char tipinde dizi tanimlaniyor
        char k[]=new char[girilenKelime.length()-1];  //aranan  kelimenin bir harf eksiltilmis hali icin char tipinde kelimenin bir harf eksigi boyutunda  dizi tanimlaniyor
        int  j=0; // hangi harfin silinecegini tutan index
        char temp;  //gecici degisken
        try {

            for (int i=1;i<girilenKelime.length()+1;i++){  //dongu aranan kelimenin uzunlugu kadar calistiriliyor
                String eskiHal=girilenKelime; //girilen kelimenin eski halini korumasi icin eskihal degiskenine ataniyor
                for (int l=0;l<eskiHal.length();l++){ //dongu girilen kelimenin uzunlugu kadar calisiyor
                    kdizi[l]=eskiHal.charAt(l); //girilen kelime karakterlere donusturuluyor
                }
                while (j<girilenKelime.length()-1){ //dongu girilen kelimenin 1 eksigi kadar calisiyor
                    temp=kdizi[j]; // karakter gecici degiskene ataniyor
                    kdizi[j]=kdizi[j+1]; // bir sonkari  karakter bir oncekinine ataniyor
                    j++; ///sonraki harf icin
                }j=i;  // j  dongudeki i degerine bagli degisiyor

                for (int a=0;a<girilenKelime.length()-1;a++){//dongu girilen kelimenin 1 eksigi kadar calisiyor. Bunun amaci en son karakteri ortadan kaldirmak
                    k[a]=kdizi[a];
                }
                String  yeni=new String(k); //harf eksiltilmis dizi birlestiriliyor
                harfCikararakArama(HashTable,yeni,tableSize); //harf cikarilmis haliyle arama fonksiyonuna gonderiliyor
            }
        }catch (Exception e){
            System.out.println("hata: " +e);
        }
    }
    
    
    
    
    
    
    
    
    private static void harfCikararakArama(Hashtable<Integer,String>hashTable,String arananKelime,int tableSize ){

        int Arananascii=0; //harf cikartilmis kelimenin ascii karsiligini tutan degisken

        for (int i = 0; i < arananKelime.length(); i++) {
            char Kkarakter = arananKelime.charAt(i);                   //kelime karakterlere cevriiyor
            int Kasci = (int) Kkarakter; //harfler ascii ye donusturuluyor
            Arananascii = Arananascii  + (i+1)*Kasci; //kelime askiye donusturuluyor
        }
        int key; //aranan kelimenin yerlesecegi yeri belirleyen degisken
        for(int j=0;j<tableSize;j++){
            key = (int) ((Arananascii+pow(j,2))%tableSize); //kelimenin yerlesmesi geeken yer belirleniyor
            if(!hashTable.containsKey(key)){ //eger kelimenin yerlesmesi gereken yer bossa bu kelime  yoktur
                break; //dongu kiriliyor
            }
            if(arananKelime.equals(hashTable.get(key))){ //eger kelimenin yerlesecegi yer doluysa arana kelime ile tablodaki kelime karsilastiriliyor ayni ise kelime bulunuyor
                System.out.println("fakat "+ arananKelime+"  kelimesi " +key +".inci hucrede mevcuttur");
                break; //dongu kiriliyor
            }

        }

    }
    
    
    
    
    
    
    
    
    
    
    private static void harfYerdegistir(Hashtable<Integer,String> HashTable,String girilenKelime,int tableSize){
        char[] kdizi=new char[girilenKelime.length()];
        char temp;
        String yeni=" ";// harf degistirilmis kelimeyi tutan degisken
        for (int i=0;i<girilenKelime.length();i++){
            kdizi[i]=girilenKelime.charAt(i); //girilen kelime karakterlere cevriliyor
        }
        for (int j=0;j<girilenKelime.length();j++){
            try {
                temp=kdizi[j];          // kelimenin
                kdizi[j]=kdizi[j+1];  ///      harfleri
                kdizi[j+1]=temp;        ///    yerdegistiriyor
              
                yeni= new String(kdizi); //yeni kelime yeni adinda degiskene ataniyor
                harfDegistirArama(HashTable,yeni,tableSize); //aramaya gonderiliyor
                temp=kdizi[j];      //kelime
                kdizi[j]=kdizi[j+1];//eskihaline
                kdizi[j+1]=temp;    //donusturuluyor
           }catch (Exception e){
                System.err.println();
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    private static void harfDegistirArama(Hashtable<Integer,String>hashTable,String arananKelime,int tableSize){

        int Arananascii=0; //aranan kelimenin ascii karsiligini tutan degisken

        for (int i = 0; i < arananKelime.length(); i++) {
            char Kkarakter = arananKelime.charAt(i);                  //kelime karakterlere cevriiyor
            int Kasci = (int) Kkarakter;  //harfler ascii karsliklarina donusturluor
            Arananascii = Arananascii  + (i+1)*Kasci;//kelimenin ascii karsiligi bulunuyor
        }

        int key; //tabloda yerlesegegi yeri tutan degisken


        for(int j=0;j<tableSize;j++){
            key = (int) ((Arananascii+pow(j,2))%tableSize); //yerleseceggi yer belrleniyor
            if(!hashTable.containsKey(key)){//eger yerlesmesi gereken yer bossa kosul saglaniyor ve kelime bulunamiyor
                break;
            }
            if(arananKelime.equals(hashTable.get(key))){//eger yerlesecegi yer doluysa yerleseccegi yerdeki kelime ile ayni olup olmadigi kontrol ediliyor kosul saglaniyorsa kelime bulunuyor
                System.out.println("fakat "+ arananKelime+"  kelimesi "+key+".inci hucrede mevcuttur");
                break;
            }
        }
    }
}