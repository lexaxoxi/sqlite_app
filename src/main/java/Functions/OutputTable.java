package Functions;

import Utils.ForOutputTable;
import Utils.ForOutputXml;
import models.DataModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class OutputTable implements Methods {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean start() {
        System.out.println("Укажите названия скважин через запятую или пробел, по которым необходима информация:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String[] names = input.split(" |,");
        for (int i = 0; i < names.length; i++) {
            names [i] = names [i].trim();
        }

        System.out.println(names);

        ForOutputXml forOutputXml = new ForOutputXml();
        ForOutputTable forOutputTable = new ForOutputTable();
        List<DataModel> datamodel = forOutputXml.getWellEquipTable(names);

        System.out.println(forOutputTable.WellsAndEquip(datamodel));

        return true;
    }
}