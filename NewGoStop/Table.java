package project;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.io.File;
import project.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Iterator;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

public class Table{
    
    Deck d=null;
    ArrayList<Card> deck;
    ArrayList<Card> []player;
    ArrayList<Card> []acquired;//딴패
    ArrayList<Card> opened;//깔린 패
    static int GO;
    int prevPoint;
    boolean gs;
    int auto(){
        int num=(int)(Math.random()*player[1].size())+1;
        return num;
    }
    Table(){
        d=new Deck();//배열로 구성된 deck 생성
        deck=d.mkDeck();//ArrayList interface에 이식
        
        player=new ArrayList[2];
        player[0]=new ArrayList<Card>();
        player[1]=new ArrayList<Card>();
        
        acquired=new ArrayList[2];
        acquired[0]=new ArrayList<Card>();
        acquired[1]=new ArrayList<Card>();
        
        opened=new ArrayList<Card>();
        
         GO=0;
        gs=true;
        prevPoint=2;//3점부터 go stop결정하는 루프들어가기 떄문..status()부분 참고하기
    }      
    boolean returnGS(){
        return gs;
    }
    int returnPlayerDeckCnt(boolean who){
        int idx;
        if(who)
            idx=0;
        else
            idx=1;
        return player[idx].size();
    }
    ArrayList<Card> returnDeck(){
        return deck;
    }
    int returnDeckCnt(){
        return deck.size();
    }
    int returnAcquiredCnt(int idx){
        return acquired[idx-1].size();
    }
    void distribute(){
        for(int i=0;i<5;i++)
            player[0].add(deck.remove(0));
        for(int i=0;i<5;i++)
             player[1].add(deck.remove(0));
        for(int i=0;i<4;i++)
            opened.add(deck.remove(0));
         for(int i=0;i<5;i++)
            player[0].add(deck.remove(0));
        for(int i=0;i<5;i++)
             player[1].add(deck.remove(0));
        for(int i=0;i<4;i++)
            opened.add(deck.remove(0));
        
        Collections.sort(player[0]);
        Collections.sort(player[1]);
        Collections.sort(opened);
    }
    void sortFunc(){
        Collections.sort(deck);
    }
    
    void show(){
        System.out.println("=================================================================================");
         System.out.println("opened ");
        System.out.println(opened);
        System.out.println();
        
        System.out.println("acquired by player1");
        if(acquired[0].isEmpty())
           System.out.println("null");
        else
            System.out.println(acquired[0]);
        System.out.println();
        
        System.out.println("player1");
        for(int i=0;i<player[0].size();i++)
            System.out.print((i+1)+". "+player[0].get(i).toString()+"  ");
         System.out.println();System.out.println();
        
          System.out.println("acquired by player2");
        if(acquired[1].isEmpty())
           System.out.println("null");
        else
            System.out.println(acquired[1]);
        System.out.println();
        System.out.println("player2");
        for(int i=0;i<player[1].size();i++)
            System.out.print((i+1)+". "+player[1].get(i).toString()+"  ");
         System.out.println();System.out.println();
    }
    
    void show(boolean who){
        int idx;
        if(who)
            idx=0;
        else
            idx=1;
        
         System.out.println("=================================================================================");
         System.out.println("opened ");
        System.out.println(opened);
        System.out.println();
        
        System.out.println("acquired by player"+(idx+1));
        if(acquired[idx].isEmpty())
           System.out.println("null");
        else
            System.out.println(acquired[idx]);
        System.out.println();
        
        System.out.println("player"+(idx+1));
        for(int i=0;i<player[idx].size();i++)
            System.out.print((i+1)+". "+player[idx].get(i).toString()+"  ");
         System.out.println();System.out.println();
        
        //status(who);
    }
    
    void throwCard(int num,boolean who){
        Iterator<Card> itr=opened.iterator();
        Card[] t_ptr=new Card[4];
        
        int idx1;//player의 인덱스 구분
        int idx2=0;
        if(who)
            idx1=0;
        else
            idx1=1;
        Card card=player[idx1].get(num-1);
         int month=player[idx1].get(num-1).getMonth();
        player[idx1].remove(num-1);
        t_ptr[idx2++]=card;
        
        int t_cnt=countCard(opened,card,t_ptr);
       
        if(t_cnt==0){
            opened.add(t_ptr[0]);
            matchCard(idx1,t_ptr,t_cnt);
        }
        else if(t_cnt==1){
            opened.add(t_ptr[0]);
            matchCard(idx1,t_ptr,t_cnt);
        }
        else if(t_cnt==2){
            pickOne(t_ptr);
            opened.add(t_ptr[0]);
            matchCard(idx1,t_ptr,t_cnt);
        }
        else{//다 먹기
            opened.add(t_ptr[0]);
            matchCard(idx1,t_ptr,t_cnt);
        }  
    }   
    int countCard(ArrayList<Card> array,Card obj,Card[] ptr){
        int cnt=0;
        int i=0;
        int idx2=1;
        int month=obj.getMonth();
      
        for(int k=0;k<array.size();k++){
            if(month==array.get(k).getMonth()){
                ptr[idx2++]=opened.get(k);
                cnt++;
            }
        }
        return cnt;
        
    }
    void matchCard(int idx1,Card[] t_ptr,int t_cnt){
         Card[] m_ptr=new Card[4];
        int idx2=0;
        Iterator itr=opened.iterator();
        Card card=deck.remove(0);
        m_ptr[idx2++]=card;
        int month=card.getMonth();
        int m_cnt=countCard(opened,card,m_ptr);
       
      //  System.out.println("open card : "+card);
        
        if(t_cnt==0){
            if(m_cnt==0)
                opened.add(m_ptr[0]);
            else if(m_cnt==1){
                opened.add(m_ptr[0]);
                organizer(idx1,m_ptr[0]);//organizer()를 쓰려면 opened에 해당 객체가 반드시 있어야 한다.
                organizer(idx1,m_ptr[1]);
            }
            else if(m_cnt==2){
                pickOne(m_ptr);
                opened.add(m_ptr[0]);
                organizer(idx1,m_ptr[0]);
                organizer(idx1,m_ptr[1]);
            }
            else if(m_cnt==3){
                opened.add(m_ptr[0]);
                for(int k=0;k<4;k++)
                    organizer(idx1,m_ptr[k]);
            }
        }
        else if(t_cnt==1){
            if(m_cnt==0){
                opened.add(m_ptr[0]);
                organizer(idx1,t_ptr[0]);
                organizer(idx1,t_ptr[1]);
            }
            else if(m_cnt==1){
                opened.add(m_ptr[0]);
                 organizer(idx1,m_ptr[0]);
                organizer(idx1,m_ptr[1]);
                 organizer(idx1,t_ptr[0]);
                organizer(idx1,t_ptr[1]);
            }
            else if(m_cnt==2){
                if(t_ptr[0].month==m_ptr[0].month)
                    opened.add(m_ptr[0]);//뻑
                else{
                    pickOne(m_ptr);
                    opened.add(m_ptr[0]);
                    organizer(idx1,m_ptr[0]);
                    organizer(idx1,m_ptr[1]);
                    organizer(idx1,t_ptr[0]);
                    organizer(idx1,t_ptr[1]);
                    
                }
            }
            else{//다먹기
                opened.add(m_ptr[0]);
                organizer(idx1,t_ptr[0]);
                organizer(idx1,t_ptr[1]);
                for(int k=0;k<4;k++)
                    organizer(idx1,m_ptr[k]);
            }
        }
        else if(t_cnt==2){
            if(m_cnt==0){
                organizer(idx1,t_ptr[0]);
                organizer(idx1,t_ptr[1]);
            }
            else if(m_cnt==1){
                opened.add(m_ptr[0]);
                organizer(idx1,m_ptr[0]);
                organizer(idx1,m_ptr[1]);
                organizer(idx1,t_ptr[0]);
                organizer(idx1,t_ptr[1]);
            }
            else if(m_cnt==2){
                opened.add(m_ptr[0]);
                pickOne(m_ptr);
                organizer(idx1,m_ptr[0]);
                organizer(idx1,m_ptr[1]);
                organizer(idx1,t_ptr[0]);
                organizer(idx1,t_ptr[1]);
            }
            else{
                opened.add(m_ptr[0]);
                organizer(idx1,t_ptr[0]);
                organizer(idx1,t_ptr[1]);
                for(int k=0;k<4;k++)
                    organizer(idx1,m_ptr[k]);
            }
        }
        else{
            for(int k=0;k<4;k++)
                organizer(idx1,t_ptr[k]);
            if(m_cnt==0){
                opened.add(m_ptr[0]);
            }
            else if(m_cnt==1){
                opened.add(m_ptr[0]);
                organizer(idx1,m_ptr[0]);
                organizer(idx1,m_ptr[1]);
            }
            else if(m_cnt==2){
                opened.add(m_ptr[0]);
                pickOne(m_ptr);
                organizer(idx1,m_ptr[0]);
                organizer(idx1,m_ptr[1]);
            }
            else{
                opened.add(m_ptr[0]);
                for(int k=0;k<4;k++)
                    organizer(idx1,m_ptr[k]);
            }
            
        }
        
        sort(opened);
        sort(acquired[0]);
        sort(acquired[1]);
        boolean who=true;
        if(idx1==0)
            who=true;
        else
            who=false;
        
        status(who,false);//go-stop할 상황인지만 체크
        
    }
    void organizer(int idx1,Card card){
        acquired[idx1].add(card);
        remover(card);
    }
    void remover(Card card){//nullPointerException + acquired에 추가가 안됨....
        int tmp=0;
        for(int k=0;k<opened.size();k++){
            if(card==opened.get(k)){//nullPointerException
               tmp=k;
            }
        }
        opened.remove(tmp);
        
    }    
    void pickOne(Card[] ptr){
        Scanner sc=new Scanner(System.in);
        int pick;
        System.out.println("pick one card!");
       System.out.println("1. "+ptr[1]+" 2. "+ptr[2]);
        while(true){
            pick=sc.nextInt();
            try{
                if(pick>2)
                  throw new ArrayIndexOutOfBoundsException();
                else
                    break;
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println("input wrong number!!again!!");
            }
        }
            if(pick==1)
                ptr[2]=null;
            else
                ptr[1]=ptr[2];
        
            
    }
    void sort(ArrayList<Card> array){
        Collections.sort(array);
    }
    static void plusGo(){
        GO++;
    }
    int returnGo(){
        return GO;
    }
   
    void status(boolean who,boolean show){
        int idx;
        int type;
        int combination;
        int point=0;
        int multiplier=2;
        int go_cnt=0;
        
        boolean bi_kwang=false;
        //boolean gs=true;
        //type
        int ti=0;//1
        int yulkkut=0;//2
        int kwang=0;//3
        int ssangpi=0;//4
        int pi=0;//etc
        //combination
        int hong=0;//1
        int cho=0;//2
        int chung=0;//3
        int godori=0;//4
        
        if(who)
            idx=0;
        else
            idx=1;
        //type combination별로 갯수 세기
        
        for(int i=0;i<acquired[idx].size();i++){
            type=acquired[idx].get(i).getType();
            combination=acquired[idx].get(i).getTypeCombination();
            
            if(type==1)
                ti++;
            else if(type==2)
                yulkkut++;
            else if(type==3)
                kwang++;
            else if(type==4){
                ssangpi++;
                pi++;pi++;
            }
            else
                pi++;
            
            if(acquired[idx].get(i).getMonth()==12 && acquired[idx].get(i).getType()==3)//비광인 경우
                bi_kwang=true;
            
            if(combination==1)
                hong++;
            else if(combination==2)
                cho++;
            else if(combination==3)
                chung++;
            else if(combination==4)
                godori++;
            else{}
            
        }
        
        if(hong==3)
            point+=3;
        if(cho==3)
            point+=3;
        if(chung==3)
            point+=3;
        if(godori==3)
            point+=3;

        if(pi>=10){
            point+=1;
            point+=pi%10;
        }
        if(kwang>=3){
            if(bi_kwang){
                point+=2;
                point+=kwang%3;
            }
            else{
                point+=3;
                point+=kwang%3;
            }
        }
        if(ti>=5){
            point+=1;
            point+=ti%5;
        }
        if(yulkkut>=5){
            point+=1;
            point+=yulkkut%5;
        }
        
        
        if(point>=3 && point>prevPoint){//과거 점수랑 비교해서 1점이라도 오르면 그때 go-stop고를수 있어서 비교용으로 필요
            prevPoint=point;
            System.out.println("현재 상황");
            System.out.println("광:"+kwang+" 띠:"+ti+" 피:"+pi+" 열끗:"+yulkkut+"    "+point+"점");
            System.out.println("홍:"+hong+" 청:"+chung+" 초:"+cho+" 고도리:"+godori);
            if(who==true)
                gs=goStop();
            else
                gs=false;
            if(gs==true){
                plusGo();
                go_cnt=returnGo();
                System.out.println(go_cnt+"고!!");
               
                /*
                if(go_cnt>=3){
                    multiplier=multiplier*(int)Math.pow(2,go_cnt%3);
                    point*=multiplier;
                }
               *///이건 나중에 결과 나오고 난담에 돈계산 할떄 고민할 문제지 지금하는거 아님
                
            }
            
        }
        if(show==true){
            System.out.println("광:"+kwang+" 띠:"+ti+" 피:"+pi+" 열끗:"+yulkkut+"    "+point+"점");
            System.out.println("홍:"+hong+" 청:"+chung+" 초:"+cho+" 고도리:"+godori);
        }
        //return gs;
            
    }
   
    boolean goStop(){
        Scanner sc=new Scanner(System.in);
        System.out.println("1. go    2. stop");
        int num;
        while(true){
            try{
                num=sc.nextInt();
                if(num>2)
                    throw new ArrayIndexOutOfBoundsException();
                else
                    break;
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println("input again!!");
            }
        }
        if(num==1)
            return true;
        else 
            return false;
    }
    void JSONEncoder(){
        JSONObject json_obj=new JSONObject();
        JSONObject[] json_sub_obj=new JSONObject[2];
        json_sub_obj[0]=new JSONObject();
        json_sub_obj[1]=new JSONObject();
        JSONArray json_array=new JSONArray();
        
        //json_sub_obj.put("deck",deck);//JSONObject에 추가하려면 put()
        //json_sub_obj.put("opened",opened);
        
        json_obj.put("deck",deck);
        json_obj.put("opened",opened);
        
        json_obj.put("p1",player[0]);
        json_obj.put("p1 acquired",acquired[0]);
        
        json_obj.put("p2",player[1]);
        json_obj.put("p2 acquired",acquired[1]);
        
        
       try(FileWriter file=new FileWriter("myJson.json")){
          
           file.write(json_obj.toString());
           file.flush();
       }catch(IOException e){
           e.printStackTrace();
       }
        System.out.println(json_obj);
    }
    void JSONDecoder(){
        JSONParser parser=new JSONParser();
        try{
            
            Object obj=parser.parse(new FileReader("myJson.json"));
            JSONObject jsonObject=(JSONObject)obj;
            JSONArray opened=(JSONArray) jsonObject.get("opened");
            JSONArray deck=(JSONArray)jsonObject.get("deck");
            JSONArray p1=(JSONArray) jsonObject.get("p1");
            JSONArray p2=(JSONArray) jsonObject.get("p2");
            JSONArray p1Acquired=(JSONArray)jsonObject.get("acquired[0]");
            JSONArray p2Acquired=(JSONArray)jsonObject.get("acquired[1]");
            
           
            Iterator<String> itr=opened.iterator();
            while(itr.hasNext())
               System.out.println("opened: "+itr.next());
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}