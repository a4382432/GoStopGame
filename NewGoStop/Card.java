package project;

public class Card implements Comparable<Card> {
    int month;
    int type=0;
    int typeCombination=0;
    Card next=null;
    Card(){
        this.month=0;
        
    }
    Card(int month){
        this.month=month;
        
    }
    Card(int month,int type){
        this(month);
        this.type=type;
        
    }
    Card(int month,int type,int typeCombination){
        this(month,type);
        this.typeCombination=typeCombination;
        
    }
    public Integer getMonth(){
        return month;
    }
    public Integer getType(){
        return type;
    }
    public Integer getTypeCombination(){
        return typeCombination;
    }
    public int compareTo(Card c){//override
        return this.getMonth().compareTo(c.getMonth());
    }
    /* 
    according to API... All elements in the list must implement the Comparable interface. all elements in the list must be mutually comparable (that is, e1.compareTo(e2) must not throw a ClassCastException for any elements e1 and e2 in the list).
    To sort list, u have to make data which are in the list comparable. That's why we need Comparable interface.
    in Comparable interface there is only one method, compareTo(int a).
    as you know, interface method has to be made in the class where implements. So u have to override compareTo() in Card class.
    
    */
    public String toString(){
        String typeStr;
        String typeCombinationStr;
        switch(type){
            case 1:
                typeStr="띠";
                break;
            case 2:
                typeStr="열끗";
                break;
            case 3:
                typeStr="광";
                break;
            case 4:
                typeStr="쌍피";
                break;
            default:
                typeStr="피";
                break;
        }
        switch(typeCombination){
            case 1:
                typeCombinationStr="홍";
                break;
            case 2:
                typeCombinationStr="초";
                break;
            case 3:
                typeCombinationStr="청";
                break;
            case 4:
                typeCombinationStr="고도리";
                break;
            default:
                typeCombinationStr="";
                break;
        }
        return month+"-"+typeStr+"-"+typeCombinationStr;
    }
}
