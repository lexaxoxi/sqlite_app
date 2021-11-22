package Functions;

import app.Main;
import models.Equipment;
import models.Well;

import java.util.ArrayList;
import java.util.Scanner;

public class CreatingWell implements Methods {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean start() {
        int start_quantity = 0;
        String well_name = null;

        Scanner scanner;
        boolean doing = true;
        while (doing) {
            System.out.println("Количество оборудования: ");
            scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                start_quantity = scanner.nextInt();
                doing = false;
            }
            else {
                System.out.println("Пожалуйста, попробуйте еще, число должно быть целым");
            }
        }

        scanner = new Scanner(System.in);
        System.out.println("Введите имя скважины");
        well_name = scanner.nextLine();

        int well_id;
        Well well = Main.getDb().findWellByName(well_name);
        if (well == null) {
            well = new Well(0,well_name);
            well_id = Main.getDb().createWell(well);
        } else {
            well_id = well.getId();
        }

        ArrayList<Equipment> equipments = new ArrayList<>();
        int maxId = Main.getDb().getMaxEquipmentId();

        for (int i = 0; i <start_quantity; i++) {
            ++maxId;
            String equipment_name = "EQ" + String.format("%04d", maxId);
            Equipment equipment = new Equipment(0,equipment_name,well_id);
            equipments.add(equipment);
        }

        int count = Main.getDb().createEquipments(equipments);
        System.out.println("В БД добавлено " + count + " записей");

        return true;
    }
}
