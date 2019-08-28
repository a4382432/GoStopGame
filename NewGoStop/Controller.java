package project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.json.simple.JSONObject;

class Controller{
    View view;
    Table table;
    Controller(){
        view=new View();
       // table=new Table();
    }
    public void controlCenter(){
        while(true){
            int num=getMenuNum();
            switch(num){
                case 1:
                    newGame();
                    break;
                case 2:
                    //loadGame();
                    System.out.println("no UTF-8..So..I quit to make it.Fuck u UTF-8 with BOM");
                    break;
                case 3:
                    info();
                    break;
                case 4:
                    System.out.println("Game Over..Adius");
                   return;//종료
                   
                default:    
            }
            
        }
    }
    public int getMenuNum(){
        //1.new game 2.load game 3.info 4.exit
        Scanner sc=new Scanner(System.in);
        int num;
        while(true){
            view.menu();
            num=sc.nextInt();
            try{
               
                if(num>4)
                   throw new ArrayIndexOutOfBoundsException();
                else
                    break;
            }catch(ArrayIndexOutOfBoundsException e){
                 System.out.println("input order again");
            }
        }
        
        return num;
        
    }
    public int getChoice(boolean who){//who is to distinguish who's turn.
        Scanner sc=new Scanner(System.in);
        int turn=0;
        if(who)
            turn=1;
        else
            turn=2;
        
        table.show(who);
        table.status(who,true);
         System.out.println("(press 100 to stop and save game)");
        System.out.print("player"+turn+" 차례: ");
        int num=sc.nextInt();
        System.out.println();
        if(num==100){
            table.JSONEncoder();
            //table.JSONDecoder();
        }
        return num;
    }
   
    public void newGame(){
        table=new Table();
        boolean who=true;//true=player[0],false=player[1]
        int num;
        boolean gs=true;
        table.distribute();
       // table.JSONEncoder();
        while(!table.deck.isEmpty()){
            while(true){
                if(who==true)
                    num=getChoice(who);
                else
                    num=table.auto();
                if(num==100){
                    System.out.println("saved!!");
                    return;
                }
                try{
                    
                    if(num>table.returnPlayerDeckCnt(who))//값 잘못넣었을때..
                       throw new ArrayIndexOutOfBoundsException();
                    else
                        break;
                }catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("input wrong!!again!!");
                }
            }
                
                table.throwCard(num,who);
                //카드간에 상호작용 발생
             
                gs=table.returnGS();
                int idx=0;
                if(who)
                    idx=0;
                else
                    idx=1; 
                if(gs==false){
                    System.out.println("stop!!game over!!");
                    System.out.println("player"+(idx+1)+" win!!");
                    break;
                }
                   
                who=!who;
        }
        if(table.deck.isEmpty()){
            table.show(!who);
            //마지막까지 갔을떄 점수 계산해서 누가 이겼는지 알려죽
        }
        
        
    }
    void info(){
        System.out.println("=================================================================================");
        System.out.println("made by chan min kim");
        System.out.println("kim has copyright forever...");
        System.out.println("this is my first project that completed by myself without any help. It was though but worth it..");
        System.out.println("=================================================================================");
    }
}