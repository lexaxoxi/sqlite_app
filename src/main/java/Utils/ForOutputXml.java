package Utils;

import app.Main;
import models.DataModel;
import models.Well;

import java.util.ArrayList;
import java.util.List;

public class ForOutputXml {

    public List<DataModel> getWellEquipTable (String[] namesOfWells) {
        List<DataModel> wellEquip = new ArrayList<>();

        for (String names : namesOfWells) {
            DataModel dataModel = new DataModel();
            Well well = Main.getDb().findWellByName(names);
            if (well !=null) {
                dataModel.setId(well.getId());
                dataModel.setName(well.getName());
                dataModel.setEquipments(Main.getDb().findEquipmentByWell(well.getId()));
                wellEquip.add(dataModel);
            }

        }
        return wellEquip;
    }

    public List <DataModel> getWellInXml () {
        List <DataModel> wellEquip = new ArrayList<>();
        List <Well> wells = Main.getDb().findAllWells();

        for (Well well : wells) {
            DataModel dataModel = new DataModel();
            if (well != null) {
                dataModel.setId(well.getId());
                dataModel.setName(well.getName());
                dataModel.setEquipments(Main.getDb().findEquipmentByWell(well.getId()));
                wellEquip.add(dataModel);
            }
        }
        return wellEquip;
    }
}
