package com.cibertec.report;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cibertec.interfaceService.ICategoriaService;
import com.cibertec.interfaceService.IMarcaService;
import com.cibertec.model.Categorias;
import com.cibertec.model.Marca;
import com.cibertec.model.Productos;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;


public class ProductoExporterExcel {
	
	private XSSFWorkbook libro;
	private XSSFSheet hoja;
	private List<Productos> listaProductos;
	private ICategoriaService categoriaService;
    private IMarcaService marcaService;
    
	public ProductoExporterExcel(List<Productos> listaProductos,
			ICategoriaService categoriaService, IMarcaService marcaService) {
		libro = new XSSFWorkbook();
		hoja = libro.createSheet("Productos");
		this.listaProductos = listaProductos;
		this.categoriaService = categoriaService;
		this.marcaService = marcaService;
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

        for (int i = 0; i < 6; i++) {
            Cell celda = fila.createCell(i);
            celda.setCellValue(getHeaderTitle(i));
            celda.setCellStyle(estilo);
        }

        for (int i = 0; i < 6; i++) {
            hoja.autoSizeColumn(i);
        }
    }

    private String getHeaderTitle(int index) {
        switch (index) {
            case 0:
                return "Código";
            case 1:
                return "Descripción";
            case 2:
                return "Categoría";
            case 3:
                return "Marca";
            case 4:
                return "Precio";
            case 5:
                return "Stock";
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

        for (Productos productos : listaProductos) {
            Row fila = hoja.createRow(numeroFilas++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(productos.getIdProducto());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(productos.getDescripcion());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            Optional<Categorias> categoria = categoriaService.listarId(productos.getId_categoria());
            celda = fila.createCell(2);
            celda.setCellValue(categoria.isPresent() ? categoria.get().getNombre_categoria() : "");
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            Optional<Marca> marca = marcaService.listarId(productos.getId_marca());
            celda = fila.createCell(3);
            celda.setCellValue(marca.isPresent() ? marca.get().getNombre_marca() : "");
            hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(formatPrecioMonedaLocal(productos.getPrecio()));
            hoja.autoSizeColumn(4);
            celda.setCellStyle(estilo);

            celda = fila.createCell(5);
            celda.setCellValue(productos.getStock());
            hoja.autoSizeColumn(5);
            celda.setCellStyle(estilo);
        }
    }

    private String formatPrecioMonedaLocal(double precio) {
        Locale loc = new Locale.Builder().setLanguage("es").setRegion("PE").build();
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(loc);
        Currency moneda = Currency.getInstance("PEN");
        formatoMoneda.setCurrency(moneda);
        return formatoMoneda.format(precio);
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
