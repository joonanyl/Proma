package r8.util;

import javafx.scene.control.TableView;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import r8.model.Event;
import r8.model.appState.AppState;

import java.io.FileOutputStream;
import java.time.LocalDate;

public class ExportUtil {

    public boolean exportExcel(TableView<Event> tableView) {

        try {
            Workbook workbook = new HSSFWorkbook();
            Sheet spreadsheet = workbook.createSheet("test6000");

            Row row = spreadsheet.createRow(0);

            for (int j = 0; j < tableView.getColumns().size(); j++) {
                row.createCell(j).setCellValue(tableView.getColumns().get(j).getText());
            }

            for (int i = 0; i < tableView.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < tableView.getColumns().size(); j++) {
                    if(tableView.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j).setCellValue(tableView.getColumns().get(j).getCellData(i).toString());
                    }
                    else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }

            String name = (AppState.getInstance().getLoggedAccount().getFirstName() + AppState.getInstance().getLoggedAccount().getLastName());
            FileOutputStream fileOut = new FileOutputStream(LocalDate.now() + "_" + name + "_export.xls");
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("Export successful");
            return true;
        } catch (Exception e) {
            System.out.println("Error occurred during export operation: " + e);
        }
        return false;
    }
}
