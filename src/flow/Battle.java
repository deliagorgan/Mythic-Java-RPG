package flow;/* Această interfață definește metode comune pentru toate entitățile de tip tema1.src.Character sau tema1.src.Enemy:

public void receiveDamage(int);

Înregistrarea unei pierderi de viață.
În funcție de cele două atribute secundare ale jucătorului, va exista o șansă de 50% ca damage-ul primit să se înjumătățească.
public int getDamage();

Calcularea valorii corespunzătoare damage-ului pe care entitatea îl aplică. Modul în care calculați damage-ul oferit
rămâne la latitudinea voastră.
În funcție de atributul principal al jucătorului, va exista o șansă de 50% ca damage-ul oferit să se dubleze.
Dacă se folosește o abilitate, la damage-ul inițial se va adăuga și damage-ul corespunzător abilității.
Observații

Un atac obișnuit nu va costa mană.
Sunteți liberi să alegeți formulele după care se calculează damage-ul, însă trebuie să aveți
în vedere cerințele speciale de dublare/înjumătățire a valorilor menționate.*/


public interface Battle {
    public void receiveDamage(int damage);
    public int getDamage();
}
