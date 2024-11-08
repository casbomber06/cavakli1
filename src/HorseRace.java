import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HorseRace {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ottieni la lunghezza del percorso dalla console
        System.out.print("Inserisci la lunghezza del percorso in metri: ");
        int lunghezzaPercorso = scanner.nextInt();
        scanner.nextLine();  // Consuma il newline

        // Ottieni il numero di cavalli dalla console
        System.out.print("Inserisci il numero di cavalli: ");
        int numeroCavalli = scanner.nextInt();
        scanner.nextLine();  // Consuma il newline

        // Crea i cavalli
        List<Cavallo> cavalli = new ArrayList<>();
        for (int i = 0; i < numeroCavalli; i++) {
            System.out.print("Inserisci il nome del cavallo " + (i + 1) + ": ");
            String nome = scanner.nextLine();
            cavalli.add(new Cavallo(nome, lunghezzaPercorso));
        }

        // Avvia la gara
        for (Cavallo cavallo : cavalli) {
            cavallo.start();
        }

        boolean garaFinita = false;
        while (!garaFinita) {
            garaFinita = true;
            for (Cavallo cavallo : cavalli) {
                if (cavallo.isRunning()) {
                    garaFinita = false;
                    break;
                }
            }

            // Mescola casualmente l'ordine dei cavalli
            Collections.shuffle(cavalli);

            // Stampa la posizione dei cavalli
            System.out.println("Posizione attuale:");
            for (Cavallo cavallo : cavalli) {
                System.out.println(cavallo.getNome() + ": " + cavallo.getMetriPercorsi() + " metri");
            }

            try {
                Thread.sleep(1000); // Pausa di 1 secondo tra gli aggiornamenti
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Annuncia il vincitore
        cavalli.sort((c1, c2) -> c2.getMetriPercorsi() - c1.getMetriPercorsi());
        System.out.println("Il cavallo vincente è " + cavalli.get(0).getNome() + " con " + cavalli.get(0).getMetriPercorsi() + " metri percorsi!");

        System.out.println("Gara terminata!");
    }
}

class Cavallo extends Thread {
    private String nome;
    private int lunghezzaPercorso;
    private int metriPercorsi = 0;
    private boolean running = true;

    public Cavallo(String nome, int lunghezzaPercorso) {
        this.nome = nome;
        this.lunghezzaPercorso = lunghezzaPercorso;
    }

    public String getNome() {
        return nome;
    }

    public int getMetriPercorsi() {
        return metriPercorsi;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        while (metriPercorsi < lunghezzaPercorso) {
            int step = (int) (Math.random() * 10) + 1; // Corri tra 1 e 10 metri
            metriPercorsi += step;

            try {
                Thread.sleep(100); // Pausa di 100ms per simulare l'avanzamento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        running = false;
        System.out.println(nome + " ha terminato la gara!");
    }
}