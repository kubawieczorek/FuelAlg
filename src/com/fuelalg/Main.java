package com.fuelalg;
/*
* IO:
* I:
* -dane o oleju napędowym (abc)
* -startowa temperatura cieczy, otoczenia, startowa objętość cieczy
* -iteracja/s
* -w każdej iteracji: nowa temperatura otoczenia, cieczy, i objętość cieczy
* -dolewanie z interpolacją
*
*
*
* */
public class Main {

    public static void main(String[] args) {
        StartParametersForm form = new StartParametersForm();
        form.pack();
        form.setVisible(true);
    }


}
