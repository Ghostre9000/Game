package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameM implements ActionListener {
    JFrame frame = new JFrame("Gra Memory");//menu

    JPanel field = new JPanel();
    JPanel menu = new JPanel();
    JPanel menu2 = new JPanel();
    JPanel menu3 = new JPanel();
    JPanel mini = new JPanel();

    JPanel start_screen = new JPanel();
    JPanel end_screen = new JPanel();
    JPanel instruct_screen = new JPanel();
//guziki
    JButton[] btn = new JButton[20];
    JButton start = new JButton("Start");
    JButton over = new JButton("Wyjscie");
    JButton easy = new JButton("Tryb łatwy");
    JButton hard = new JButton("Tryb trudny");
    JButton inst = new JButton("Opis gry");
    JButton goBack = new JButton("Menu");
//generowanie planszy
    Random randomGenerator = new Random();
    private boolean purgatory = false;
    int level=0;
    int score=0;

    String[] board;
    Boolean shown = true;
    int temp=30;
    int temp2=30;
    String[] a =new String[10];
    boolean eh=true;

    private final JTextField text = new JTextField(10);
    private final JTextArea instructM = new JTextArea("Gra Memory\nKażde niepoprawne klikniecie się nalicza.\n Może byc od 1 do 10 par.\nTryb latwy ma bardzo rózne znaki\nTryb trudny ma podobne.");
    public GameM(){
        //layout gry
        frame.setSize(500,300);
        frame.setLocation(500,300);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start_screen.setLayout(new BorderLayout());
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu2.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu3.setLayout(new FlowLayout(FlowLayout.CENTER));
        mini.setLayout(new FlowLayout(FlowLayout.CENTER));

        start_screen.add(menu, BorderLayout.NORTH);
        start_screen.add(menu3, BorderLayout.CENTER);
        start_screen.add(menu2, BorderLayout.SOUTH);
        menu3.add(mini, BorderLayout.CENTER);
        JLabel label = new JLabel("Ilosc par od 1 do 10");
        menu.add(label);
        menu.add(text);
        mini.add(easy, BorderLayout.NORTH);
        mini.add(hard, BorderLayout.NORTH);
        mini.add(inst, BorderLayout.SOUTH);


        start.addActionListener(this);
        start.setEnabled(true);
        menu2.add(start);
        over.addActionListener(this);
        over.setEnabled(true);
        menu2.add(over);
        easy.addActionListener(this);
        easy.setEnabled(true);
        hard.addActionListener(this);
        hard.setEnabled(true);
        inst.addActionListener(this);
        inst.setEnabled(true);


        frame.add(start_screen, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public void setUpGame(int x,Boolean what){
        //przygotowanie gry
        level=x;
        clearMain();

        board = new String[2*x];
        for(int i=0;i<(x*2);i++){
            btn[i] = new JButton("");
            btn[i].setBackground(new Color(220, 220, 220));
            btn[i].addActionListener(this);
            btn[i].setEnabled(true);
            field.add(btn[i]);

        }
        //tryby
        String[] b = {":;","::","IL","II","<>","^`","(>","<)","'`","><"};
        String[] c = {"Walet","Dama","Król","AS","Joker","Serce","Trefl","Karo","Pik","Dziesiątka"};
        if(what) a=c;
        else a=b;

        for(int i=0;i<x;i++){
            for(int z=0;z<2;z++){
                while(true){
                    int y = randomGenerator.nextInt(x*2);
                    if(board[y]==null){
                        btn[y].setText(a[i]);
                        board[y]=a[i];
                        break;
                    }
                }
            }


        }
        createBoard();

    }
    //ukrywanie po kliknieciu
    public void hideField(int x){
        for(int i=0;i<(x*2);i++){
            btn[i].setText("");
        }
        shown=false;
    }
    public void switchSpot(int i){//Pokazanie znaku lub ukrycie
        if(!board[i].equals("done")){//Czy juz odkryte^
            if(btn[i].getText().equals("")){
                btn[i].setText(board[i]);
            } else {
                btn[i].setText("");
            }
        }
    }
    public boolean checkWin(){//Wygrana sprawdzenie
        for(int i=0;i<(level*2);i++){
            if (!board[i].equals("done")) return false;
        }
        winner();
        return true;
    }
    public void winner(){

        start_screen.remove(field);
        start_screen.add(end_screen, BorderLayout.CENTER);
        end_screen.add(new TextField("WYGRANA!"), BorderLayout.NORTH);
        end_screen.add(new TextField("Nietrafiłeś: " + score), BorderLayout.SOUTH);
        end_screen.add(goBack);
        goBack.setEnabled(true);
        goBack.addActionListener(this);




    }
    public void goToMainScreen(){//powrot
        new GameM();
    }
    public void createBoard(){//gui planszy
        field.setLayout(new BorderLayout());
        start_screen.add(field, BorderLayout.CENTER);

        field.setLayout(new GridLayout(5,4,2,2));
        field.setBackground(Color.black);
        field.requestFocus();
    }
    public void clearMain(){//Czyszczenie menu dla ekranu planszy lub opisu gry
        start_screen.remove(menu);
        start_screen.remove(menu2);
        start_screen.remove(menu3);

        start_screen.revalidate();
        start_screen.repaint();
    }
    public void actionPerformed(ActionEvent click){
        Object source = click.getSource();
        if(purgatory){
            switchSpot(temp2);
            switchSpot(temp);
            score++;
            temp=(level*2);
            temp2=30;
            purgatory=false;
        }
        if(source==start){ //Sprawdza ilosc par i trudnosc po czym zaczyna gre
            try{
                level = Integer.parseInt(text.getText());
            } catch (Exception e){
                level=1;
            }

            setUpGame(level, eh);
        }
        if(source==over){//wychodzenie
            System.exit(0);
        }
        if(source==inst){//Ekran opisu
            clearMain();

            start_screen.add(instruct_screen, BorderLayout.NORTH);

            JPanel one = new JPanel();
            one.setLayout(new FlowLayout(FlowLayout.CENTER));
            JPanel two = new JPanel();
            two.setLayout(new FlowLayout(FlowLayout.CENTER));
            instruct_screen.setLayout(new BorderLayout());
            instruct_screen.add(one, BorderLayout.NORTH);
            instruct_screen.add(two, BorderLayout.SOUTH);

            one.add(instructM);
            two.add(goBack);
            goBack.addActionListener(this);
            goBack.setEnabled(true);
        }
        if(source==goBack){//Powrot do menu
            frame.dispose();
            goToMainScreen();
        }
        if(source==easy){//Zmienia kolor guzika trudnosci po wyborze
            eh=true;
            easy.setForeground(Color.BLUE);
            hard.setForeground(Color.BLACK);
        } else if(source==hard){
            eh=false;
            hard.setForeground(Color.BLUE);
            easy.setForeground(Color.BLACK);
        }

        for(int i =0;i<(level*2);i++){//dzialanie gry dla guzikow
            if(source==btn[i]){
                if(shown){
                    hideField(level);//ukrawa pola po pierwszym nacisnieciu na jakis guzik
                }else{//faktyczna akcja gry
                    switchSpot(i);
                    if(temp>=(level*2)){
                        temp=i;
                    } else {
                        if((!board[temp].equals(board[i]))||(temp==i)){
                            temp2=i;
                            purgatory=true;


                        } else {
                            board[i]="done";
                            board[temp]="done";
                            checkWin();
                            temp=(level*2);
                        }

                    }
                }

            }


        }


    }
    public static void main(String[] args) {
        new GameM();
    }

}
	