import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

class Card {
    String value;
    boolean revealed;

    Card(String value) {
        this.value = value;
        this.revealed = false;
    }
}

class MemoryGame {
    ArrayList<Card> cards;

    MemoryGame() {
        cards = new ArrayList<>();
        createCards();
        shuffle();
    }

    void createCards() {
        String[] values = {"A","B","C","D","E","F","G","H"};

        for (String v : values) {
            cards.add(new Card(v));
            cards.add(new Card(v));
        }
    }

    void shuffle() {
        Collections.shuffle(cards);
    }
}

public class Main extends JFrame {

    MemoryGame game;

    JButton[] buttons = new JButton[16];

    int firstIndex = -1;
    int secondIndex = -1;

    public Main() {

        game = new MemoryGame();

        setTitle("Memory Game");
        setSize(400, 400);
        setLayout(new GridLayout(4, 4));

        for (int i = 0; i < 16; i++) {

            buttons[i] = new JButton("?");

            int index = i;

            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleClick(index);
                }
            });

            add(buttons[i]);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void handleClick(int index) {

        Card card = game.cards.get(index);

        if (card.revealed) return;

        buttons[index].setText(card.value);
        card.revealed = true;

        if (firstIndex == -1) {
            firstIndex = index;
            return;
        }

        secondIndex = index;

        checkMatch();
    }

    void checkMatch() {

        Card first = game.cards.get(firstIndex);
        Card second = game.cards.get(secondIndex);

        if (first.value.equals(second.value)) {
            resetSelection();
            checkWin();
        }
        else {
            Timer timer = new Timer(700, new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    first.revealed = false;
                    second.revealed = false;

                    buttons[firstIndex].setText("?");
                    buttons[secondIndex].setText("?");

                    resetSelection();
                }
            });

            timer.setRepeats(false);
            timer.start();
        }
    }

    void resetSelection() {
        firstIndex = -1;
        secondIndex = -1;
    }

    void checkWin() {

        for (Card c : game.cards) {
            if (!c.revealed) {
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "You win!");
    }

    public static void main(String[] args) {
        new Main();
    }
}