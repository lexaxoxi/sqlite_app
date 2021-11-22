package Functions;

import Utils.ExportInXml;

import java.util.Scanner;

public class OutPutXml implements Methods {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean start() {
        System.out.println("Укажите название файла(без расширения). Файл сохраниться в корне программы: ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        ExportInXml file = new ExportInXml();
        file.setFile(fileName + ".xml");
        try {
            file.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;

    }
}
