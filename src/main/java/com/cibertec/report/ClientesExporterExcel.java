package com.cibertec.report;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cibertec.model.Clientes;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class ClientesExporterExcel {
    private XSSFWorkbook libro;
    private XSSFSheet hoja;
    private List<Clientes> listaClientes;

    public ClientesExporterExcel(List<Clientes> listaClientes) {
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("Clientes");
        this.listaClientes = listaClientes;
    }

    private void cabeceraTabla() {
        Row fila = hoja.createRow(0);

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeightInPoints((short) 16);
        estilo.setFont(fuente);

        estilo.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (int i = 0; i < 5; i++) {
            Cell celda = fila.createCell(i);
            celda.setCellValue(getHeaderTitle(i));
            celda.setCellStyle(estilo);
        }

        for (int i = 0; i < 5; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    private String getHeaderTitle(int index) {
        switch (index) {
            case 0:
                return "Código";
            case 1:
                return "Nombres del cliente";
            case 2:
                return "Dirección";
            case 3:
                return "Email";
            case 4:
                return "Telefono";
            default:
                return "";
        }
    }

    private void datosTabla() {
        int numeroFilas = 1;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeightInPoints((short) 14);
        estilo.setFont(fuente);

        for (Clientes cliente : listaClientes) {
            Row fila = hoja.createRow(numeroFilas++);

            createCell(fila, 0, cliente.getIdCliente(), estilo);
            createCell(fila, 1, cliente.getNombre(), estilo);
            createCell(fila, 2, cliente.getDireccion(), estilo);
            createCell(fila, 3, cliente.getEmail(), estilo);
            createCell(fila, 4, cliente.getTelefono(), estilo);
        }
    }

    private void createCell(Row row, int column, Object value, CellStyle style) {
        Cell cell = row.createCell(column);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }
        hoja.autoSizeColumn(column);
        cell.setCellStyle(style);
    }

    public void exportarExcel(HttpServletResponse response) throws IOException {
        cabeceraTabla();
        datosTabla();

        ServletOutputStream outputStream = response.getOutputStream();

        libro.write(outputStream);
        libro.close();
        outputStream.close();
    }
}
