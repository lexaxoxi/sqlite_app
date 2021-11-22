package app;

import Functions.CreatingWell;
import Functions.Methods;
import Functions.OutPutXml;
import Functions.OutputTable;
import Utils.Connecting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Connecting db;


    public static void main(String[] args) {

        Map <String, Methods> functions = new HashMap<>();
        functions.put("1" , new CreatingWell());
        functions.put("2" , new OutputTable());
        functions.put("3" , new OutPutXml());

        try {
            db = new Connecting();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String cmd = "";

        if(args.length == 0) {
            System.out.println("Введите число [1] - Создание оборудования на указанной скважине");
            System.out.println("Введите число [2] - Вывод таблицы об оборудовании на скважинах");
            System.out.println("Введите число [3] - Вывод файла об оборудовании на скважинах");

            try {
                BufferedReader is = new BufferedReader(
                        new InputStreamReader(System.in)
                );
                cmd = is.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            cmd = args[0];
        }

        if (functions.containsKey(cmd)){
            functions.get(cmd).start();
        }
    }

    public static Connecting getDb() {
        return db;
    }
}