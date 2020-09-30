/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrastra_y_suelta;

import java.awt.HeadlessException;
import java.awt.ScrollPane;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author 4L3
 */
public class DropXlsx implements DropTargetListener {

    private JTable jtable;
    private JScrollPane scroll;
    private JLabel label;
    private DefaultTableModel tableModel;
    protected DropTarget dropTarget;
    DecimalFormat formatDecimal = new DecimalFormat("#");

    /**
     * Constructor de clase
     */
    public DropXlsx() {
    }

    public void setJtable(JTable jtable,JScrollPane scroll,JLabel label) {
        this.jtable = jtable;
        this.scroll=scroll;
        this.label=label;
        dropTarget = new DropTarget(jtable, this);
        tableModel = (DefaultTableModel) jtable.getModel();
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {/*...*/
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {/*...*/
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {/*...*/
    }

    @Override
    public void dragExit(DropTargetEvent dte) {/*...*/
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            /* proporciona datos para operaciones de transferencia en swing */
            Transferable tr = dtde.getTransferable();
            /* Devuelve una array de objetos DataFlavor */
            DataFlavor[] flavors = tr.getTransferDataFlavors();
            if (flavors.length > 0) {
                /* Si existe una lista de objetos de archivo */
                if (flavors[0].isFlavorJavaFileListType()) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    /* obtiene un List con los archivos arrastrados al componente */
                    java.util.List list = (java.util.List) tr.getTransferData(flavors[0]);
                    if (!list.isEmpty()) {
                        /* abre el primer archivo */
                        File file = new File(list.get(0).toString());
                        if (file.exists()) {
                             Runnable runnable1 = new Runnable() {
            public void run() {
                scroll.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
                jtable.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
                label.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
              //  visual.this.setEnabled(false);
                //inicio metodo
                            /* Si el archivo corresponde a un archivo excel *.xlsx */
                            if (file.getName().endsWith("xlsx")) {
                                readXLSX(file);
                            } else {
//                                JOptionPane.showMessageDialog(null, "No es un archivo *.xlsx valido", "Error", JOptionPane.ERROR_MESSAGE);
                                System.out.println("arrastrado dentro ");
                                try {
                                    
                                    HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));

                                    HSSFSheet sheet = wb.getSheetAt(0);

                                    Row row;
                                    String[] datos;
                                    int maxCol = 0;
                    for (int a = 0; a <= sheet.getLastRowNum(); a++) {
                        if (sheet.getRow(a) != null) {
                            if (sheet.getRow(a).getLastCellNum() > maxCol) {
                                maxCol = sheet.getRow(a).getLastCellNum();
                            }
                        }
                    }
if (maxCol > 0) {
                        while (tableModel.getRowCount() != 0) {
                            tableModel.removeRow(0);
                        }
                                    for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                                        row = sheet.getRow(i);
                                        int cols = row.getPhysicalNumberOfCells();
                                        datos = new String[cols];

                                        for (int j = 0; j < cols; j++) {
                                            try {
                                                datos[j] = row.getCell(j).getStringCellValue();
                                            } catch (Exception e) {
                                                datos[j] = formatDecimal.format(row.getCell(j).getNumericCellValue());
                                            }
                                        }
//                if (i == 0) {
//                    modelo.setColumnIdentifiers(datos);
//                } else {
//                    modelo.addRow(datos);
//                }
                                        tableModel.addRow(datos);
                                    }
                                    jtable.setModel(tableModel);
}else{
    JOptionPane.showMessageDialog(null, "Nada que importar", "Error", JOptionPane.ERROR_MESSAGE);
}
                                } catch (Exception e) {
                                    System.out.println("general " + e);
                                }
                            }
                            
                            //fin metodo
              //  visual.this.setEnabled(true);
                scroll.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                jtable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                label.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        };
        Thread t1 = new Thread(runnable1);
        t1.start();

                            
                            
                            
                        } else {
                            System.err.println("error archivo no existe ");
                        }
                    }
                    dtde.dropComplete(true);
                    return;
                }
            }
            dtde.rejectDrop();
        } catch (UnsupportedFlavorException | IOException | HeadlessException ex) {
            System.err.println(ex.getMessage());
            dtde.rejectDrop();
        }
    }

    /**
     * Lee un archivo excel (Primera hoja)
     *
     * @param file Archivo excel
     */
    public void readXLSX(File file) {
        
        try {
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheet = wb.getSheetAt(0);//primeta hoja            
            Row row;
            Cell cell;

            //obtiene cantidad total de columnas con contenido
            int maxCol = 0;
            for (int a = 0; a <= sheet.getLastRowNum(); a++) {
                if (sheet.getRow(a) != null) {
                    if (sheet.getRow(a).getLastCellNum() > maxCol) {
                        maxCol = sheet.getRow(a).getLastCellNum();
                    }
                }
            }
            if (maxCol > 0) {
                //Añade encabezado a la tabla
//                for (int i = 1; i <= maxCol; i++) {
//                    tableModel.addColumn("Col." + i);
//                }                
                while (tableModel.getRowCount() != 0) {
            tableModel.removeRow(0);
        }
                //recorre fila por fila
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {

                    int index = 0;
                    row = rowIterator.next();

                    Object[] obj = new Object[row.getLastCellNum()];
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        cell = cellIterator.next();
                        //contenido para celdas vacias
                        while (index < cell.getColumnIndex()) {
                            obj[index] = "";
                            index += 1;
                        }
                        //extrae contenido de archivo excel
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_BOOLEAN:
                                obj[index] = cell.getBooleanCellValue();
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                obj[index] = formatDecimal.format(cell.getNumericCellValue());
                                break;
                            case Cell.CELL_TYPE_STRING:
                                obj[index] = cell.getStringCellValue();
                                break;
                            case Cell.CELL_TYPE_BLANK:
                                obj[index] = " ";
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                obj[index] = cell.getCellFormula();
                                break;
                            default:
                                obj[index] = "";
                                break;
                        }
                        index += 1;
                    }
                    tableModel.addRow(obj);
                }
                jtable.setModel(tableModel);
            } else {
                JOptionPane.showMessageDialog(null, "Nada que importar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            System.err.println("" + ex.getMessage());
        }
    }

}//DropXlsx:end
