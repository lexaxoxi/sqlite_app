package Utils;

import models.DataModel;

import java.sql.SQLException;
import java.util.List;

public class ForOutputTable {

    public String WellsAndEquip (List<DataModel> datamodel) {
        String outputTable = "";
        outputTable += String.format ("%-20s | %-20s\n", "--------------------", "--------------------");
        outputTable += String.format ("%-20s | %-20s\n", "Имя скважины", "Кол-во оборудования");
        outputTable += String.format("%-20s | %-20s\n", "--------------------", "--------------------");


        for (DataModel welldata : datamodel ) {
            outputTable += String.format("%-20s | %-20d\n", welldata.getName(), welldata.getEquipments().size());
        }

        return outputTable;
    }
}
