package com.cibertec.report;

import java.awt.Color;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.cibertec.interfaceService.ICategoriaService;
import com.cibertec.interfaceService.IMarcaService;
import com.cibertec.model.Categorias;
import com.cibertec.model.Marca;
import com.cibertec.model.Productos;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;


public class ProductoExporterPDF {
	
	private List<Productos> listaProductos;
    private ICategoriaService categoriaService;
    private IMarcaService marcaService;

	public ProductoExporterPDF(List<Productos> listaProductos, ICategoriaService categoriaService,
			IMarcaService marcaService) {
		this.listaProductos = listaProductos;
		this.categoriaService = categoriaService;
		this.marcaService = marcaService;
	}

	private void cabeceraTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(new Color(50, 150, 200)); // Cambio de color del fondo
        celda.setPadding(8);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);
        fuente.setStyle(Font.BOLD); // Fuente en negrita

        celda.setPhrase(new Phrase("Código", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Descripción", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Categoría", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Marca", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Precio", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Stock", fuente));
        tabla.addCell(celda);
    }

    private void datosTabla(PdfPTable tabla) {
        for (Productos productos : listaProductos) {
            tabla.addCell(String.valueOf(productos.getIdProducto()));
            tabla.addCell(productos.getDescripcion());

            // Obtener el nombre de la categoría usando el servicio
            Optional<Categorias> categoria = categoriaService.listarId(productos.getId_categoria());
            tabla.addCell(categoria.isPresent() ? categoria.get().getNombre_categoria() : "");

            // Obtener el nombre de la marca usando el servicio
            Optional<Marca> marca = marcaService.listarId(productos.getId_marca());
            tabla.addCell(marca.isPresent() ? marca.get().getNombre_marca() : "");

			tabla.addCell(formatPrecioMonedaLocal(productos.getPrecio()));
			tabla.addCell(String.valueOf(productos.getStock()));
		}
	}

	// Función para formatear el precio a la moneda local (Soles - S/.)
	private String formatPrecioMonedaLocal(double precio) {
		NumberFormat formatoMoneda = NumberFormat
				.getCurrencyInstance(new Locale.Builder().setLanguage("es").setRegion("PE").build());
		return formatoMoneda.format(precio);
	}

	public void exportar(HttpServletResponse response) throws DocumentException, IOException {
		Document doc = new Document(PageSize.A4);
		PdfWriter.getInstance(doc, response.getOutputStream());

		doc.open();

		Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA);
		fuenteTitulo.setColor(Color.BLUE);
		fuenteTitulo.setSize(24); // Aumentar tamaño del título

		Paragraph titulo = new Paragraph("Lista de Productos", fuenteTitulo);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		doc.add(titulo);

		PdfPTable tabla = new PdfPTable(6);
		tabla.setWidthPercentage(100); // Ajustar ancho al 100%
		tabla.setSpacingBefore(20);
		tabla.setWidths(new float[] { 1, 3, 3, 2, 2, 2 }); // Ajustar ancho de columnas

		cabeceraTabla(tabla);
		datosTabla(tabla);

		doc.add(tabla);
		doc.close();
	}

}
