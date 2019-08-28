package project;

import java.util.ArrayList;
import java.util.Collections;


public class Deck {
    
     Card [][]card=new Card[13][4];
    Card []tmpDeck=null;
    
    Deck(){
        
       
       
        for(int i=1;i<card.length-1;i++){
            card[i][2]=new Card(i);
            card[i][3]=new Card(i);
        }
        ////////똥////
        card[1][0]=new Card(1,CardType.TI,CardCombination.HONG);
        card[2][1]=new Card(2,CardType.TI,CardCombination.HONG);
        card[3][1]=new Card(3,CardType.TI,CardCombination.HONG);
        //////홍/////
        card[4][1]=new Card(4,CardType.TI,CardCombination.CHO);
        card[5][1]=new Card(5,CardType.TI,CardCombination.CHO);
        card[7][1]=new Card(7,CardType.TI,CardCombination.CHO);
        //////초/////
        card[6][1]=new Card(6,CardType.TI,CardCombination.CHUNG);
        card[9][1]=new Card(9,CardType.TI,CardCombination.CHUNG);
        card[10][1]=new Card(10,CardType.TI,CardCombination.CHUNG);
        /////청////
        card[12][2]=new Card(12,CardType.TI);
        ////띠////
        card[2][0]=new Card(2,CardType.YULKKUT,CardCombination.GODORI);
        card[4][0]=new Card(4,CardType.YULKKUT,CardCombination.GODORI);
        card[5][0]=new Card(5,CardType.YULKKUT);
        card[6][0]=new Card(6,CardType.YULKKUT);
        card[7][0]=new Card(7,CardType.YULKKUT);
        card[8][1]=new Card(8,CardType.YULKKUT,CardCombination.GODORI);
        card[9][0]=new Card(9,CardType.YULKKUT);//국진
        card[10][0]=new Card(10,CardType.YULKKUT,CardCombination.CHUNG);
        card[12][1]=new Card(12,CardType.YULKKUT);
        /////열끗...국진도 일단은 열끗으로 설정////
        card[1][1]=new Card(1,CardType.KWANG);
        card[3][0]=new Card(3,CardType.KWANG);
        card[8][0]=new Card(8,CardType.KWANG);
        card[11][0]=new Card(11,CardType.KWANG);
        card[12][0]=new Card(12,CardType.KWANG);
        /////광//////////
        card[11][1]=new Card(11,CardType.SSANGPI);
        card[12][3]=new Card(12,CardType.SSANGPI);
        /////쌍피/////
        
    }
    void summary(){
        for(int i=1;i<card.length;i++){
            for(int j=0;j<card[1].length;j++)
                System.out.println(card[i][j].toString());
            System.out.println();
        }
    }
    public ArrayList<Card> mkDeck(){
        tmpDeck=new Card[(card.length-1)*(card[1].length)];
        int ptr=0;
        for(int i=1;i<=card.length-1;i++){
            System.arraycopy(card[i],0,tmpDeck,ptr,card[i].length);
            ptr+=card[i].length;
        }
        shuffle();
        
        ArrayList<Card> deck=new ArrayList<>();
        
        for(int i=0;i<48;i++)
            deck.add(tmpDeck[i]);
       
        return deck;
    }
    void shuffle(){
        int x,y;
        for(int i=0;i<tmpDeck.length*2;i++){
            x=(int)(Math.random()*tmpDeck.length);
            y=(int)(Math.random()*tmpDeck.length);
            
            Card tmp;
            tmp=tmpDeck[x];
            tmpDeck[x]=tmpDeck[y];
            tmpDeck[y]=tmp;
        }
       
    }
   
}
