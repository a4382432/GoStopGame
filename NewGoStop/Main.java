package project;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
       
       
        //filereader 제대로 되려면 인코더 BOM 없는 utf8을 써야한다는게 확실한데 이걸 어케 해야하는건지...
        System.setProperty("file.encoding","UTF-8");
        Controller control=new Controller();
        control.controlCenter();
        
    }

}
